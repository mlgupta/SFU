package sfu.beans.scheduler;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import sfu.beans.scan.JobFilesManagement;
import sfu.beans.scan.StreamHandler;


/**
 *	Purpose: This class does the job of sending fax with the configured content
 *           to the configured recipient.It implements the org.quartz.Job interface
 *           and overrides its execute() method.
 *           note: This program will only work on systems which have HylaFAX client program
 *                 installed and configured properly with the HylaFAX server.
 *                 
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SendFaxJob implements Job {


  private Logger logger = Logger.getLogger("SfuLogger");
  //private boolean errorOccured=false;
   JobDataMap data = null;
   Scheduler sched = null;
   JobDetail jobDetail = null;
   SchedulerContext schedContext = null;
   String parentFolder=null;
   boolean isJobFileRequired;

  /**
     *	Purpose: To override execute method of org.quartz.Job interface and 
     *           provide send fax functionality
     *  @param   context - JobExecutionContext associated with this Job.The context
     *           provides all the necessary information to execute this job.
     */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    String faxDesc = null;
    boolean success=false;
    try {
      logger.info("------Entering Fax Job execute() method------");
      
      jobDetail = context.getJobDetail();
      data = context.getJobDetail().getJobDataMap();
      sched = context.getScheduler();
      schedContext = sched.getContext();
      
      logger
      .debug("From SendFaxJob:" + context.getJobDetail().getFullName() + " Executing");

      String faxNo = data.getString(SchedulerConstants.PROP_FAX_NUMBER);
      String to = data.getString(SchedulerConstants.PROP_FAX_TO);
      String companyName = data.getString(SchedulerConstants.COMPANY_NAME);
      String comments = data.getString(SchedulerConstants.PROP_FAX_COMMENTS);
      parentFolder=data.getString(SchedulerConstants.PARENT_FOLDER);
      //String faxTempDir=data.getString(SchedulerConstants.FAX_TEMP_DIR);
      //String creator = data.getString(SchedulerConstants.JOB_CREATOR);
      logger.info("Fax Number is:" + faxNo);

      String from = data.getString(SchedulerConstants.PROP_SENDER);
      ArrayList attachments =
        (ArrayList)data.get(SchedulerConstants.PROP_ATTACHMENTS);

      if (faxNo == null || faxNo.trim().length() == 0) {
        throw new IllegalArgumentException("PROP_FAX_NUMBER not specified.");
      }

      if (from == null || from.trim().length() == 0) {
        throw new IllegalArgumentException("PROP_SENDER not specified.");
      }

      if (attachments == null || attachments.size() == 0) {
        throw new IllegalArgumentException("PROP_ATTACHMENTS not specified.");
      }
      faxDesc = "' to: " + faxNo;

      logger.debug("From SendFaxJob:Sending fax " + faxDesc);

      success=sendFax(to, faxNo, from, companyName, comments, attachments);
      if(success){
        logger.info("Job faxed successfully");
        //freeJobFiles();
      }else {
        logger.info("Unable to fax job: "+ faxDesc);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      logger.error("1");
      try {
        if (data != null) {
          logger.debug("sdf");
          //
          String jobName = jobDetail.getName();
          String jobGroup = jobDetail.getGroup();
          //String jobMaxCount = (String)data.get(SchedulerConstants.MAX_COUNT);
          //ArrayList attachments=(ArrayList)data.get(SchedulerConstants.PROP_ATTACHMENTS);
          String jobRetrialCount =
            (String)data.get(SchedulerConstants.RETRIAL_COUNT);
          logger.debug("jobRetrialCount: " + jobRetrialCount);
          //int maxCount = Integer.parseInt(jobMaxCount);
          int retrialCount = Integer.parseInt(jobRetrialCount);
          logger.debug("retrialCount: " + retrialCount);
            /*//Dont check for max count limit//
             if (retrialCount < maxCount) {   
            */
            String jobRetrialInterval =
              (String)data.get(SchedulerConstants.RETRIAL_INTERVAL);
            logger.debug("jobRetrialInterval" + jobRetrialInterval);
            Date oldStartTime =
              (Date)data.get(SchedulerConstants.EXECUTION_TIME);
            logger.debug("oldStartTime" + oldStartTime);
            Date newStartTime =
              DateHelper.addMinutes(oldStartTime, Integer.parseInt(jobRetrialInterval));
            logger.debug("newStartTime: " + newStartTime);

            //putting new values in jobDataMap
            data.put(SchedulerConstants.EXECUTION_TIME, newStartTime);
            data
            .put(SchedulerConstants.RETRIAL_COUNT, new String().valueOf(++retrialCount));
            data.put(SchedulerConstants.ERROR_MESG, e.toString());

            sched.deleteJob(jobName, jobGroup);
            JobDetail faxJobDetail =
              new JobDetail(jobName, SchedulerConstants.FAX_JOB,
                                                   SendFaxJob.class, false,
                                                   true, true);
            faxJobDetail.setJobDataMap(data);
            Date dateOfSubmission =
              (Date)data.get(SchedulerConstants.CREATE_TIME);
            logger.info("Job Create Time" + dateOfSubmission);
            String creator = (String)data.get(SchedulerConstants.JOB_CREATOR);
            String namePrefix =
              creator + DateHelper.format(newStartTime, "HH:mm:ss--yyyy-MM-dd-z");
            String newTriggerName =
              SchedulerConstants.TRIGGER_NAME_SUFFIX + namePrefix;
            SimpleTrigger newTrigger = null;
            if (newStartTime.compareTo(new Date()) <= 0) {
              newTrigger =
                new SimpleTrigger(newTriggerName, SchedulerConstants.FAX_JOB);
            } else {
              newTrigger =
                new SimpleTrigger(newTriggerName, SchedulerConstants.FAX_JOB,
                                             newStartTime);
            }
            Trigger trigger[] =
              sched.getTriggersOfJob(jobName, SchedulerConstants.FAX_JOB);
            faxJobDetail.validate();
            if (trigger == null || trigger.length == 0) {
              sched.scheduleJob(faxJobDetail, newTrigger);
              logger.debug("Old Trigger Not Found");
            } else {
              String oldTriggerName = trigger[0].getName();
              sched
              .rescheduleJob(oldTriggerName, SchedulerConstants.FAX_JOB, newTrigger);
              logger.debug("Old Trigger Found");
            }
          /*
          }else {
            logger
            .debug("Fax Job's Retrial Count value reached its Max Count Limit.");
            freeJobFiles();
          }*/
        } else {
          logger.debug("Could not retrieve data for the job");
        }
      } catch (Exception jobAddExcep) {
        logger.error(jobAddExcep);
      }
      throw new JobExecutionException("Unable to send fax: " + faxDesc, e,
                                      false);
    }finally{
      logger.info("------Exiting Fax Job execute() method------");
      
    }
  }

  /*
  private void freeJobFiles() throws SchedulerException {
    isJobFileRequired =
      JobFilesManagement.alterProperties(parentFolder, "curr_dir_files_req_for_fax_job",
                                                           "false");
    logger
    .debug("Are Files Required For Other Jobs : " + isJobFileRequired);
    if (!isJobFileRequired) {
      delJobFiles();
    }
  }

  private void delJobFiles() throws SchedulerException {
    //Deleting files after they are not required for any other types of job(ie: Fax/Mail)
    try {
      logger.debug("Deleting job files of fax job.");
      File parentFolderToDelete = new File(parentFolder);
      File[] fileToDeleteArray = parentFolderToDelete.listFiles();
      for (int index = 0; index < fileToDeleteArray.length; index++) {
        fileToDeleteArray[index].delete();
      }
      parentFolderToDelete.delete();
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
  }
  */

  /**
     *	Purpose: Fax a document.It calls a native program 'sendfax' to do this job,so HylaFAX client
     *           should be properly installed prior to its usage.
     *  @param   faxNo - Recipient's fax number.
     *  @param   filesToFax - List of all the files to be faxed.                
     */
  public boolean sendFax(String to, String faxNo, String from, String companyName,
                      String comments, 
                      ArrayList filesToFax) throws Exception {
    boolean success = false;
    try {
      logger.debug("Entering sendFax() method");
      String fileNamesWithPath = "";
      //ArrayList fileList = new ArrayList();
      filesToFax.trimToSize();

      int numAttachments = filesToFax.size();
      if (numAttachments > 0) {
        logger.info("Processing attachments");
        for (int index = 0; index < numAttachments; index++) {
          fileNamesWithPath =
            fileNamesWithPath + " " + (String)filesToFax.get(index);
        }
        logger.debug("fileNamesWithPath: " + fileNamesWithPath);
      } else {
        throw new Exception("No file in attachment");
      }
      String cmd =
        SchedulerConstants.FAX_CMD + "-c '" + comments + "' -f '" + from +
        "' -x '" + companyName + "' -d '" + to + "@" + faxNo + "' " +
        fileNamesWithPath;
      Runtime rt = Runtime.getRuntime();
      logger.info("Executing " + cmd);
      //Process proc = rt.exec(cmd);
      Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      int exitVal = proc.waitFor();
      // any error message?
      StreamHandler errorHandler =
        new StreamHandler(proc.getErrorStream(), "TYPE_ERROR");

      // any output?
      StreamHandler outputHandler =
        new StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");

      // start them 
      errorHandler.start();
      outputHandler.start();

      // any error???
      if (exitVal == 0) {
        logger
        .info("From SendFaxJob:Fax " + fileNamesWithPath + " sent successfully to the Fax Server");
        success = true;
      } else {
        logger.info("From SendFaxJob:Fax " + fileNamesWithPath + " failed");
        logger.info("Possible reasons:");
        logger
        .info("HylaFAX client is not installed or 'sendfax' is not present in PATH");
        throw new Exception("HylaFAX client is not installed or 'sendfax' is not present in PATH");
      }

      /*for (int index = 0; index < fileList.size(); index++) {
        String fileNameWithPath = (String)fileList.get(index);
        File fileToFax = new File(fileNameWithPath);
        if (fileToFax.exists()) {
          fileToFax.delete();
        }
      }*/
      
    } catch (Exception e) {
      logger.error(e);
      throw e;
    } finally {
      logger.debug("Exiting sendFax() method");
    }
    return success;
  }
  // end func sendFax
}
// end Class sendFaxJob


/*class StreamHandler extends Thread {
  private static String TYPE_ERROR = "ERROR";

  private static String TYPE_OUTPUT = "OUTPUT";

  InputStream is;

  String type;

  private Logger logger = Logger.getLogger("SfuLogger");

  StreamHandler(InputStream is, String type) {
    this.is = is;
    this.type = type;
  }

  public void run() {
    try {
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null) {
        logger.info(type + ">" + line);
      }
    } catch (IOException ioe) {
      logger.error(ioe);
    }
  }
}
*/

