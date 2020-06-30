package sfu.beans.job;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;

import sfu.beans.scheduler.DateHelper;
import sfu.beans.scheduler.JobCreator;
import sfu.beans.scheduler.JobScheduler;


public class DocUploadJob {
  Logger logger = Logger.getLogger("SfuLogger");

  //private static String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

  public void submit(String creator, String jobRetrialCount, String errorMesg,
                     String jobMaxCount, String jobRetrialInterval,
                     String jobErrorMessage, int day, int month, int year,
                     int hours, int minutes, int seconds, Scheduler sched,
                     String parentFolder, String pattern,
                     String destinationFolder) {

    String[] lstDocuments;

    ArrayList documents = new ArrayList();
    try {
      logger.info("------Entering " + this.getClass().getName() + "------");
      JobScheduler jobSched = new JobScheduler(sched);
      lstDocuments = sfu.beans.misc.Filter.getFileList(parentFolder, pattern);

      if (lstDocuments != null) {
        for (int index = 0; index < lstDocuments.length; index++) {
          if (!lstDocuments[index].endsWith("properties")) {
            String docFile = parentFolder + "/" + lstDocuments[index];
            logger
            .debug("docFile after applying filter for pattern '" + pattern +
                         "' : " + docFile);
            documents.add(docFile);
          }
        }
      }
      JobCreator jc = new JobCreator();
      Date startTime =
        DateHelper.getDate(year, month, day, hours, minutes, seconds);
      JobDataMap jobData =
        jc.createDocUploadData(creator, startTime, jobRetrialCount,
                                                  jobRetrialInterval,
                                                  jobMaxCount, jobErrorMessage,
                                                  documents, parentFolder,
                                                  destinationFolder);
      jobSched.addJob(jobData);
      logger.debug("Job added successfully");

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());

    } finally {
      logger.info("------Exiting " + this.getClass().getName() + "------");
    }

  }
}
