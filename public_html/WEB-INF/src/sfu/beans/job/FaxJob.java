package sfu.beans.job;


import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.JobDataMap;
import org.quartz.Scheduler;

import sfu.beans.scheduler.DateHelper;
import sfu.beans.scheduler.JobCreator;
import sfu.beans.scheduler.JobScheduler;

//Java API
//Servlet API
//Struts API


public class FaxJob {
  Logger logger = Logger.getLogger("SfuLogger");

  //private static String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

  public void submit(String creator, String senderEmailAddress, String txtTo,
                     String txtCompanyName, String txtFaxNumber, String txaFax,
                     String jobRetrialCount, String errorMesg,
                     String jobMaxCount, String jobRetrialInterval,
                     String jobErrorMessage, int day, int month, int year,
                     int hours, int minutes, int seconds, Scheduler sched,
                     String parentFolder, String pattern) {

    String[] lstAttachment;

    ArrayList attachments = new ArrayList();
    try {
      logger.info("------Entering " + this.getClass().getName() + "------");
      JobScheduler jobSched = new JobScheduler(sched);
      lstAttachment = sfu.beans.misc.Filter.getFileList(parentFolder, pattern);
      if (lstAttachment != null) {
        for (int index = 0; index < lstAttachment.length; index++) {
          if (!lstAttachment[index].endsWith("properties")) {
            String docFile = parentFolder + "/" + lstAttachment[index];
            logger
            .debug("docFile after applying filter for pattern '" + pattern +
                         "' : " + docFile);
            attachments.add(docFile);
          }
        }
      }
      JobCreator jc = new JobCreator();
      Date startTime =
        DateHelper.getDate(year, month, day, hours, minutes, seconds);
      JobDataMap jobData =
        jc.createFaxData(txtTo, senderEmailAddress, txtCompanyName,
                                            txtFaxNumber, txaFax, startTime,
                                            creator, jobRetrialCount,
                                            jobRetrialInterval, jobMaxCount,
                                            jobErrorMessage, parentFolder,
                                            attachments);
      //String timezone removed
      jobSched.addJob(jobData);
      System.out.println("Job added successfully");
    } catch (Exception exception) {
      exception.printStackTrace();
      logger.error(exception);
    } finally {
      logger.info("------Exiting " + this.getClass().getName() + "------");
    }
  }
}
