package sfu.beans.scheduler;

import dbsentry.filesync.client.FsFileSystemOperationsRemote;
import dbsentry.filesync.client.jxta.JxtaClient;
import dbsentry.filesync.common.FsExceptionHolder;
import dbsentry.filesync.common.FsMessage;
import dbsentry.filesync.common.FsResponse;
import dbsentry.filesync.common.FsUser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

public class UploadJob implements Job {
  Logger logger = Logger.getLogger("SfuLogger");

  JobDataMap data = null;

  Scheduler sched = null;

  JobDetail jobDetail = null;

  SchedulerContext schedContext = null;

  boolean isClientInitialized = false;

  String fs = File.separator;

  String jxtaConfigPath = null;
  
  String parentFolder=null;
  
  boolean isJobFileRequired;

  public UploadJob() {

  }

  public void execute(JobExecutionContext context) throws JobExecutionException {
    String jobDesc = null;
    boolean success = false;
    String[] documentsStringArray = null;
    try {
      logger.info("------Entering Upload Job execute() method------");
      sched = context.getScheduler();
      schedContext = sched.getContext();
      //      schedContext.put("isUploadJobExecuting","true");
      initializeJxta(schedContext);
      jobDetail = context.getJobDetail();
      data = context.getJobDetail().getJobDataMap();
      logger.info(context.getJobDetail().getFullName() + " Executing");
      parentFolder = data.getString(SchedulerConstants.PARENT_FOLDER);
      String destinationFolder =
        data.getString(SchedulerConstants.PROP_DESTINATION_FOLDER);

      ArrayList documents =
        (ArrayList)data.get(SchedulerConstants.PROP_DOCUMENTS);

      if (destinationFolder == null || destinationFolder.trim().length() == 0)
        throw new IllegalArgumentException("PROP_DESTINATION_FOLDER not specified.");
      if (documents == null || documents.size() == 0)
        throw new IllegalArgumentException("PROP_DOCUMENTS not specified.");

      documentsStringArray =
        (String[])(documents.toArray(new String[documents.size()]));
      if (documentsStringArray.length != 0) {
        String[] documentPathStringArray =
          new String[documentsStringArray.length + 2];
        for (int i = 0; i <= documentsStringArray.length - 1; i++) {
          documentPathStringArray[i] = documentsStringArray[i];
        }
        documentPathStringArray[documentPathStringArray.length - 2] =
          parentFolder;
        documentPathStringArray[documentPathStringArray.length - 1] =
          destinationFolder;
        for (int i = 0; i <= documentPathStringArray.length - 1; i++) {
          logger
          .debug("documentPathStringArray[" + i + "]: " + documentPathStringArray[i]);
        }
        success = upload(documentPathStringArray);
        if (success) {
          logger.info("Job uploaded successfully");
          //freeJobFiles();
        } else {
          logger.info("Unable to upload job: " + jobDesc);
        }
      } else {
        logger.error("No documents found in the job to upload");
      }
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      logger.error("1");
      try {
        if (data != null) {
          String jobName = jobDetail.getName();
          String jobGroup = jobDetail.getGroup();
          String jobMaxCount = (String)data.get(SchedulerConstants.MAX_COUNT);
          
          String jobRetrialCount =
            (String)data.get(SchedulerConstants.RETRIAL_COUNT);
          
          //int maxCount = Integer.parseInt(jobMaxCount);
          
          int retrialCount = Integer.parseInt(jobRetrialCount);
          
            /*//Dont check for max count limit//
             if (retrialCount < maxCount) {   
            */
            String jobRetrialInterval =
              (String)data.get(SchedulerConstants.RETRIAL_INTERVAL);

            Date oldStartTime =
              (Date)data.get(SchedulerConstants.EXECUTION_TIME);

            Date newStartTime =
              DateHelper.addMinutes(oldStartTime, Integer.parseInt(jobRetrialInterval));

            data.put(SchedulerConstants.EXECUTION_TIME, newStartTime);

            data
            .put(SchedulerConstants.RETRIAL_COUNT, new String().valueOf(++retrialCount));

            data.put(SchedulerConstants.ERROR_MESG, e.toString());
            sched.deleteJob(jobName, jobGroup);

            JobDetail uploadJobDetail =
              new JobDetail(jobName, SchedulerConstants.UPLOAD_JOB,
                                                      UploadJob.class, false,
                                                      true, true);
            uploadJobDetail.setJobDataMap(data);
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
                new SimpleTrigger(newTriggerName, SchedulerConstants.UPLOAD_JOB);
            } else {
              newTrigger =
                new SimpleTrigger(newTriggerName, SchedulerConstants.UPLOAD_JOB,
                                             newStartTime);
            }
            Trigger trigger[] =
              sched.getTriggersOfJob(jobName, SchedulerConstants.UPLOAD_JOB);
            uploadJobDetail.validate();
            if (trigger == null || trigger.length == 0) {
              sched.scheduleJob(uploadJobDetail, newTrigger);
              logger.debug("Old Trigger Not Found");
            } else {
              String oldTriggerName = trigger[0].getName();
              sched
              .rescheduleJob(oldTriggerName, SchedulerConstants.UPLOAD_JOB,
                                  newTrigger);
              logger.debug("Old Trigger Found");
            }
          /*} else {
            logger
            .debug("Upload Job's Retrial Count value reached its Max Count Limit.");
            freeJobFiles();
          }*/
        }
      } catch (Exception jobAddExcep) {
        logger.error(jobAddExcep.getMessage());
      }
      throw new JobExecutionException("Unable to upload the job: " + jobDesc,
                                      e, false);
    } finally {
      logger.info("------Exiting Upload Job execute() method------");
    }
  }

  /*
  private void freeJobFiles() throws SchedulerException {
    isJobFileRequired =
      JobFilesManagement.alterProperties(parentFolder, "curr_dir_files_req_for_upload_job",
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
      logger.debug("Deleting job files of upload job.");
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

  public UploadJob.HandleJxtaConfiguration handleJxtaConfiguration;

  /**
   * Handles all remote operation
   */
  public FsFileSystemOperationsRemote fsFileSystemOperationsRemote;

  private JxtaClient jxtaClient;

  /**
   * A constant to indicate which class will process the response.
   */
  private static final String FOR_THIS_CLASS = "FOR_THIS_CLASS";

  /**
   * this object is used to provide some synchronization between the request and response.
   */
  private static final String completeLock = "completeLock";
  //private Logger logger;

  /**
   * Instantiates the UploadJob class
   */
  public void initializeJxta(SchedulerContext schedContext) {

    jxtaConfigPath = schedContext.get("contextPath") + "config" + fs;
    logger.debug("jxtaConfigPath: " + jxtaConfigPath);

    initializeLogger();


    fsFileSystemOperationsRemote =
      (FsFileSystemOperationsRemote)schedContext.get("fsFileSystemOperationsRemote");

    if (fsFileSystemOperationsRemote != null) {
      logger.debug("Getting fsFileSystemOperationsRemote from sched context");
    } else {
      //String userHome = System.getProperty("user.home");
      Logger logger = Logger.getLogger("ClientLogger");
      File socketAdv = new File(jxtaConfigPath + "socket.adv");
      System.out.println(socketAdv.getAbsolutePath());
      File jxtaConfig = new File(jxtaConfigPath + "jxta_config.xml");
      File encrDecrPassword = new File(jxtaConfigPath + "enc_dec_key.txt");
      File platformConfig = new File(jxtaConfigPath + "PlatformConfig");
      jxtaClient =
        new JxtaClient(logger, socketAdv, jxtaConfig, encrDecrPassword,
                                  platformConfig);
      handleJxtaConfiguration = new UploadJob.HandleJxtaConfiguration();
      jxtaClient.addPropertyChangeSupport(handleJxtaConfiguration);
      //schedContext.put("jxtaClient",jxtaClient );
      Thread jxtaThread = new Thread(jxtaClient);
      jxtaThread.start();
      try {
        jxtaThread.join();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }

  }


  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    int length = args.length;
    if (length >= 3) {
      UploadJob UploadJob = new UploadJob();
      UploadJob.upload(args);
    } else {
      System.out.println("Invalid number of arguments or format");
      System.out.println("\nUsage Syntax");
      System.out
      .println("\tjava -jar UploadExample.jar \"file/folder complete path\" src dest");
    }
  }

  /**
   * Establishes connection with the FileSync server, uploads the folder/file and 
   * disconnects from the server .
   * @param args Array of arguments passed to the app.
   */
  public boolean upload(String[] args) {
    Stack fileStack = new Stack();
    boolean success = false;
    int itemCount = args.length - 2;
    for (int index = 0; index < itemCount; index++) {
      fileStack.add(args[index]);
    }
    System.out.println("Uploading file");
    FsUser fsUser = new FsUser();
    fsUser.setUserId("system");
    fsUser.setUserPassword("system");
    fsFileSystemOperationsRemote.connectUser(fsUser, FOR_THIS_CLASS);
    waitUntilCompleted();
    fsFileSystemOperationsRemote
    .uploadItem(null, fileStack, args[itemCount], args[itemCount + 1],
                                            FOR_THIS_CLASS);
    waitUntilCompleted();
    logger.debug("File uploaded");
    fsFileSystemOperationsRemote.disconnectUser(FOR_THIS_CLASS);
    waitUntilCompleted();
    success = true;
    return success;
    //System.exit(0);
  }

  /**
   * Initialize logger
   */
  private void initializeLogger() {
    try {
      System.out.println("******3Initializing Logger...");
      String userHome = System.getProperty("user.home");

      File logFolder = new File(userHome + "/.dbsfs/log");
      if (!logFolder.exists()) {
        logFolder.mkdirs();
      }

      File file = new File(jxtaConfigPath + "log4j.properties");
      if (file.exists()) {
        PropertyConfigurator.configureAndWatch(file.getAbsolutePath(), 2000);
      } else {
        System.out
        .println("The application was unable to initialize logger properly.");
        System.out
        .println("log4j-initialization-file : '" + file.getAbsolutePath() +
                           "'");
        System.out.println("The application will exit now!");
        //System.exit(1);
      }

      logger = Logger.getLogger("ClientLogger");
      logger.info("Logger initialized successfully");

    } catch (Exception ex) {
      ex.printStackTrace();
      //System.exit(1);
    }
  }


  /**
   * Used to handle jxta configuration notification. and initialize the app for 
   * communication with the FileSync server
   */
  private class HandleJxtaConfiguration implements PropertyChangeListener {
    Boolean jxtaConfigured = null;

    public void propertyChange(PropertyChangeEvent evt) {
      Logger logger = Logger.getLogger("ClientLogger");
      if (!isClientInitialized) {
        jxtaClient = (JxtaClient)evt.getSource();
        jxtaConfigured = (Boolean)evt.getNewValue();
      } else {
        jxtaConfigured = Boolean.valueOf(true);
      }

      if (jxtaConfigured.booleanValue()) {
        try {
          File encrDecrPassword = new File(jxtaConfigPath + "enc_dec_key.txt");
          fsFileSystemOperationsRemote =
            new FsFileSystemOperationsRemote(logger, jxtaClient,
                                                                          encrDecrPassword);

          fsFileSystemOperationsRemote
          .addPropertyChangeSupport(new PropertyChangeListener() {
                                                                  public void propertyChange(PropertyChangeEvent evt) {
                                                                    // TODO:  Implement this java.beans.PropertyChangeListener abstract method
                                                                    propertyChangeFileSystemOperationRemote(evt);
                                                                  }
                                                                }
          );
          schedContext
          .put("fsFileSystemOperationsRemote", fsFileSystemOperationsRemote);

        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        logger.debug("Jxta server not found");
      }
    }

  }

  /**
   * Handles notification after each request/response completion
   * @param evt
   */
  public void propertyChangeFileSystemOperationRemote(PropertyChangeEvent evt) {
    logger.debug("evt.getSource() : " + evt.getSource());
    FsResponse fsResponse;
    //FsExceptionHolder fsExceptionHolder;
    if (evt.getSource().equals(FOR_THIS_CLASS)) {
      if (evt.getPropertyName().equals("fsResponse")) {
        fsResponse = (FsResponse)evt.getNewValue();
        logger
        .debug("fsResponse.getResponseCode() : " + fsResponse.getResponseCode());
        handleResponse(fsResponse);
        processCompleted();
      }
    }
  }

  /**
   * The class which actually handles the notification
   * @param fsResponse FsResponse object as parameter
   */
  private void handleResponse(FsResponse fsResponse) {
    FsExceptionHolder fsExceptionHolder;
    //String homeFolder;

    try {
      int responseCode = fsResponse.getResponseCode();
      switch (responseCode) {
      case FsResponse.CONNECT :
        Boolean connectionSuccessFul = (Boolean)fsResponse.getData();
        if (connectionSuccessFul.booleanValue()) {
          logger.info("Connected to the server");
        } else {
          fsExceptionHolder = fsResponse.getFsExceptionHolder();
          logger.error(fsExceptionHolder.getErrorMessage());
          logger.info("Invalid userid/password");
        }
        break;
      case FsMessage.DISCONNECT :
        logger.info("User Disconnected");
        break;
      case FsResponse.DOWNLOAD_COMPLETED :
        //JOptionPane.showMessageDialog(this,"File(s) Downloaded successfully");
        logger.info("Download Completed");
        break;
      case FsResponse.DOWNLOAD_FAILURE :
        logger.info("Download failure");
        break;
      case FsResponse.DOWNLOAD_CANCELED :
        logger.info("Download canceled");
        break;
      case FsResponse.UPLOAD_COMPLETED :
        logger.info("Upload Complete");
        break;
      case FsResponse.UPLOAD_FAILURE :
        logger.info("Upload Failure");
        if (fsResponse.getFsExceptionHolder().getErrorCode() == 30002) {
          logger
          .info("Failed to upload, access denied for the specified destination folder.");
        }
        break;
      case FsMessage.UPLOAD_CANCELED :
        logger.info("Upload canceled");
        break;
      case FsMessage.FETAL_ERROR :
        logger.error("Fetal Error");
        break;
      case FsMessage.ERROR_MESSAGE :
        fsExceptionHolder = fsResponse.getFsExceptionHolder();
        logger.error(fsExceptionHolder);
        break;
      }
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
  }

  /**
   * Makes the thread to wait until the completion of the request
   */
  private void waitUntilCompleted() {
    try {
      synchronized (completeLock) {
        completeLock.wait();
      }
      logger.debug("Done.");
    } catch (InterruptedException e) {
      logger.error("Interrupted.");
    }
  }


  /**
   * Notifies the waiting thread after completion of the request.
   */
  private void processCompleted() {
    synchronized (completeLock) {
      completeLock.notify();
    }
  }

}
