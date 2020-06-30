package sfu.beans.scheduler;


import java.io.*;

import java.util.*;

import javax.activation.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.*;

import org.quartz.*;

import sfu.beans.scan.JobFilesManagement;

/**
 *	Purpose: This class does the job of sending e-mails with the configured content
 *           to the configured recipient.It implements the org.quartz.Job interface
 *           and overrides its execute() method.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SendMailJob implements Job {


  Logger logger = Logger.getLogger("SfuLogger");

  JobDataMap data = null;

  Scheduler sched = null;

  String smtpHost = null;

  JobDetail jobDetail = null;

  String parentFolder = null;

  SchedulerContext schedContext = null;

  boolean isJobFileRequired;

  //String isUploadJobExecuting = null;

  //String isFaxJobExecuting = null;

  /**
     *	Purpose: To override execute method of org.quartz.Job interface and 
     *           provide send mail functionality
     *  @param   context - JobExecutionContext associated with this Job.The context
     *           provides all the necessary information to execute this job.
     */
  public void execute(JobExecutionContext context) throws JobExecutionException {
    String mailDesc = null;

    try {
      logger.info("------Entering Mail Job execute() method------");

      //      context.put("isMailJobExecuting", "true");
      //isUploadJobExecuting=(String)context.get("isUploadJobExecuting");
      //isFaxJobExecuting=(String)context.get("isFaxJobExecuting");

      sched = context.getScheduler();
      schedContext = sched.getContext();
      jobDetail = context.getJobDetail();
      data = context.getJobDetail().getJobDataMap();
      logger.info(context.getJobDetail().getFullName() + " Executing");
      smtpHost = data.getString(SchedulerConstants.PROP_SMTP_HOST);
      String to = data.getString(SchedulerConstants.PROP_RECIPIENT);
      String subject = data.getString(SchedulerConstants.PROP_SUBJECT);
      mailDesc = "'" + subject + "' to: " + to;
      String cc = data.getString(SchedulerConstants.PROP_CC_RECIPIENT);
      String bcc = data.getString(SchedulerConstants.PROP_BCC_RECIPIENT);
      String from = data.getString(SchedulerConstants.PROP_SENDER);
      String replyTo = data.getString(SchedulerConstants.PROP_REPLY_TO);
      String message = data.getString(SchedulerConstants.PROP_MESSAGE);
      parentFolder = data.getString(SchedulerConstants.PARENT_FOLDER);
      ArrayList attachments =
        (ArrayList)data.get(SchedulerConstants.PROP_ATTACHMENTS);
      attachments.trimToSize();
      if (attachments.size() != 0) {
        if (smtpHost == null || smtpHost.trim().length() == 0)
          throw new IllegalArgumentException("PROP_SMTP_HOST not specified.");
        if (to == null || to.trim().length() == 0)
          throw new IllegalArgumentException("PROP_RECIPIENT not specified.");
        if (from == null || from.trim().length() == 0)
          throw new IllegalArgumentException("PROP_SENDER not specified.");
        /*if (subject == null || subject.trim().length() == 0)
                        throw new IllegalArgumentException(
                                "PROP_SUBJECT not specified.");
                if (message == null || message.trim().length() == 0)
                        throw new IllegalArgumentException(
                                "PROP_MESSAGE not specified.");
                if (attachments == null || attachments.size() == 0)
                        throw new IllegalArgumentException(
                                "PROP_ATTACHMENTS not specified."); */

        if (cc != null && cc.trim().length() == 0)
          cc = null;
        if (bcc != null && bcc.trim().length() == 0)
          bcc = null;
        if (replyTo != null && replyTo.trim().length() == 0)
          replyTo = null;

        logger.info("Sending message " + mailDesc);


        boolean success =
          sendMail(smtpHost, to, cc, bcc, from, replyTo, subject, message,
                                   attachments);
        if (success) {
          logger.info("Mail sent successfully");
          //          schedContext.put("isMailJobExecuting", "false");
          //freeJobFiles();
        } else {
          logger.info("Unable to send mail: " + mailDesc);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      logger.error("1");
      try {
        if (data != null) {
          data.put(SchedulerConstants.PROP_SMTP_HOST, smtpHost);
          String jobName = jobDetail.getName();
          String jobGroup = jobDetail.getGroup();
          //String jobMaxCount = (String)data.get(SchedulerConstants.MAX_COUNT);
          
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
            JobDetail mailJobDetail =
              new JobDetail(jobName, SchedulerConstants.MAIL_JOB,
                                                    SendMailJob.class, false,
                                                    true, true);
            mailJobDetail.setJobDataMap(data);
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
                new SimpleTrigger(newTriggerName, SchedulerConstants.MAIL_JOB);
            } else {
              newTrigger =
                new SimpleTrigger(newTriggerName, SchedulerConstants.MAIL_JOB,
                                             newStartTime);
            }
            Trigger trigger[] =
              sched.getTriggersOfJob(jobName, SchedulerConstants.MAIL_JOB);
            mailJobDetail.validate();
            if (trigger == null || trigger.length == 0) {
              sched.scheduleJob(mailJobDetail, newTrigger);
              logger.debug("Old Trigger Not Found");
            } else {
              String oldTriggerName = trigger[0].getName();
              sched
              .rescheduleJob(oldTriggerName, SchedulerConstants.MAIL_JOB, newTrigger);
              logger.debug("Old Trigger Found");
            }

            // JobScheduler jobScheduler=new JobScheduler(sched);        

          /*} else {
            logger
            .debug("Mail Job's Retrial Count value reached its Max Count Limit.");
            //            schedContext.put("isMailJobExecuting", "false");
            freeJobFiles();
          }*/

        }

      } catch (Exception jobAddExcep) {
        logger.error(jobAddExcep);
      }
      throw new JobExecutionException("Unable to send mail: " + mailDesc, e,
                                      false);
    } finally {
      logger.info("------Exiting Mail Job execute() method------");

    }
  }

  /*
  private void freeJobFiles() throws SchedulerException {
    isJobFileRequired =
      JobFilesManagement.alterProperties(parentFolder, "curr_dir_files_req_for_mail_job",
                                                           "false");
    logger
    .debug("Are Files Required For Other Jobs : " + isJobFileRequired);
    if (!isJobFileRequired) {
      delJobFiles();
    } 
  }


  private void delJobFiles() throws SchedulerException {
    //Deleting files after they are not required for any other types of job(ie: Fax/Upload)
    try {
      logger.debug("Deleting job files of mail job.");
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
     *	Purpose: Sends the mail.
     *           
     *  @param   smtpHost - The SMTP server IP/Domain name.
     *  @param   to - Email address of the recipient of mail.
     *  @param   cc - Email address of the recipient who will receive a carbon copy of mail.
     *  @param   from - Email address of the sender of mail.
     *  @param   replyTo - Email address of the person to whom reply should be forwarded to.
     *  @param   subject - Subject of the message.
     *  @param   message - The message written in plane text.
     *  @param   attachments - A list of all the attachments.                 
     */
  private boolean sendMail(String smtpHost, String to, String cc, String bcc,
                           String from, String replyTo, String subject,
                           String message,
                           ArrayList attachments) throws Exception {
    boolean success = false;
    MimeMessage mimeMessage;
    try {
      mimeMessage =
        prepareMimeMessage(smtpHost, to, cc, bcc, from, replyTo, subject,
                                       message, attachments);
      if (mimeMessage != null) {
        Transport.send(mimeMessage);
        success = true;
      }
    } catch (Exception e) {
      logger.error(e);
      throw e;
    } finally {
      mimeMessage = null;
    }
    return success;
  }

  /**
     *	Purpose: Creates a mime message with the parameters passed.This mime message 
     *           contains all the required properties for the mail. 
     *           
     *  @param   smtpHost - The SMTP server IP/Domain name.
     *  @param   to - Email address of the recipient of mail.
     *  @param   cc - Email address of the recipient who will receive a carbon copy of mail.
     *  @param   from - Email address of the sender of mail.
     *  @param   replyTo - Email address of the person to whom reply should be forwarded to.
     *  @param   subject - Subject of the message.
     *  @param   message - The message written in plane text.
     *  @param   attachments - A list of all the attachments.
     *  @return  Mime message which is actually sent.                 
     */
  private MimeMessage prepareMimeMessage(String smtpHost, String to, String cc,
                                         String bcc, String from,
                                         String replyTo, String subject,
                                         String message,
                                         ArrayList attachments) throws Exception {
    MimeMessage mimeMessage = null;
    Session session = null;
    try {
      Properties properties = new Properties();
      properties.put("mail.smtp.host", smtpHost);
      logger.info("SMTP Host=" + smtpHost);
      session = Session.getDefaultInstance(properties, null);
      mimeMessage = new MimeMessage(session);
      Address[] toAddresses = InternetAddress.parse(to);
      mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
      if (cc != null) {
        Address[] ccAddresses = InternetAddress.parse(cc);
        mimeMessage.setRecipients(Message.RecipientType.CC, ccAddresses);
      }

      if (bcc != null) {
        Address[] bccAddresses = InternetAddress.parse(bcc);
        mimeMessage.setRecipients(Message.RecipientType.BCC, bccAddresses);
      }

      mimeMessage.setFrom(new InternetAddress(from));
      if (replyTo != null)
        mimeMessage
        .setReplyTo(new InternetAddress[] { new InternetAddress(replyTo) });
      mimeMessage.setSubject(subject);

      mimeMessage.setSentDate(new Date());

      MimeBodyPart messageText = new MimeBodyPart();

      messageText.setText(message);

      Multipart mimeMultiPart = new MimeMultipart();
      mimeMultiPart.addBodyPart(messageText);
      attachments.trimToSize();
      int numAttachments = attachments.size();

      if (numAttachments > 0) {
        logger.info("Processing attachments");
        for (int index = 0; index < numAttachments; index++) {
          FileByteArray fileArr = null;
          File attachedFile = new File((String)attachments.get(index));
          InputStream fileInputStream =
            new FileInputStream((String)attachments.get(index));
          int fileSize = fileInputStream.available();
          fileArr = new FileByteArray();
          byte[] fileBytes = new byte[fileSize];
          fileInputStream.read(fileBytes, 0, fileSize);
          String fileName = attachedFile.getName();
          String mimeType =
            new MimetypesFileTypeMap().getContentType(attachedFile);
          fileArr.setFileBytes(fileBytes);
          fileArr.setFileName(fileName);
          fileArr.setMimeType(mimeType);


          ByteArrayDataSource bds =
            new ByteArrayDataSource(fileArr.getFileBytes(),
                                                            fileArr.getMimeType(),
                                                            fileArr
                                                            .getFileName());

          MimeBodyPart attachment = new MimeBodyPart();

          attachment.setDataHandler(new DataHandler(bds));

          attachment.setFileName(bds.getName());

          mimeMultiPart.addBodyPart(attachment);

        }
        logger.info("Attachment process complete");
      }
      mimeMessage.setContent(mimeMultiPart);
    } catch (Exception e) {
      logger.error(e);
      throw e;
    } finally {
      session = null;
    }
    return mimeMessage;
  }

}
