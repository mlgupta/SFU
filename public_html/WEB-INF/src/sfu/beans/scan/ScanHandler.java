package sfu.beans.scan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.quartz.Scheduler;

import sfu.actionforms.job.JobCreateForm;

import sfu.beans.auditTrail.AuditTrail;
import sfu.beans.auditTrail.AuditTrailBean;
import sfu.beans.job.DocUploadJob;
import sfu.beans.job.FaxJob;
import sfu.beans.job.MailJob;
import sfu.beans.scheduler.SchedulerConstants;

public class ScanHandler extends Thread {
  Logger logger = Logger.getLogger("SfuLogger");

  Process proc = null;

  String pattern = null, scannedDocOutputPath = null;

  JobCreateForm jobCreateForm;

  boolean asSingleFile;

  Boolean scanningDone;

  Scheduler sched = null;

  InitiateScan initiateScan = null;

  HttpSession httpSession = null;

  boolean isMailChecked = false;

  boolean isUploadChecked = false;

  boolean isFaxChecked = false;

  public ScanHandler(Process proc, HttpSession httpSession,
                     JobCreateForm jobCreateForm, Scheduler sched,
                     String pattern, boolean asSingleFile,
                     String scannedDocOutputPath) {
    logger.info("---Initializing ScanHandler constructor---");
    initiateScan =
      new InitiateScan(httpSession, jobCreateForm, sched, pattern, asSingleFile,
                                    scannedDocOutputPath);
    this.proc = proc;

    this.sched = sched;

    this.jobCreateForm = jobCreateForm;
    this.pattern = pattern;

    this.asSingleFile = asSingleFile;

    this.scannedDocOutputPath = scannedDocOutputPath;
    this.httpSession = httpSession;
    logger.info("---Exiting ScanHandler constructor---");
  }

  public void run() {
    try {
      logger.info("---Entering run() method of ScanHandler---");
      InputStreamReader isr = null;
      BufferedReader br = null;
      FileWriter fw = null;
      File tempMsgFile = null;
      //logger.debug("Waiting for some seconds .....");
      //sleep(10000);
      //      Writing errors in a temp file starts.
      try {

        isr = new InputStreamReader(proc.getErrorStream());
        br = new BufferedReader(isr);
        String line = null;
        String tempFileParentPathString =
          (String)httpSession.getServletContext().getAttribute("contextPath");
        logger
        .debug("tempFilePath: " + tempFileParentPathString + "temp_msg" + File
                     .separator + "msg.tmp");

        tempMsgFile =
          new File(tempFileParentPathString + "temp_msg" + File.separator +
                               "msg.tmp");
        //delete already existing file
        if (tempMsgFile.exists()) {
          tempMsgFile.delete();
        }
        while ((line = br.readLine()) != null) {
          if (tempMsgFile.exists()) {
            fw = new FileWriter(tempMsgFile, true);
          } else {
            fw = new FileWriter(tempMsgFile);
          }
          fw.write(line + "\n");
          fw.flush();
          fw.close();
        }
      } catch (IOException e) {
        logger.error(e.toString());
        e.printStackTrace();
      } finally {
        if (fw != null) {
          fw.close();
        }
        if (br != null) {
          br.close();
        }
        if (isr != null) {
          isr.close();
        }
        //if(tempMsgFile!=null){
        //  tempMsgFile.delete();              
        //}
      }
      //      Writing errors in a temp file ends.
      int exitVal = proc.waitFor();
      scanningDone = (Boolean)httpSession.getAttribute("scanningDone");
      if (scanningDone != null) {
        scanningDone = Boolean.valueOf(true);
        httpSession.setAttribute("scanningDone", scanningDone);
      }

      String numPagesScanned = null;

      try {
        //initiateScan.compressPage();
        numPagesScanned = new String().valueOf(initiateScan.convertPage());
        // any error message?
        StreamHandler errorHandler =
          new StreamHandler(proc.getErrorStream(), "TYPE_INFO");
        // any output?
        StreamHandler outputHandler =
          new StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");
        // start them 
        errorHandler.start();
        outputHandler.start();

      } catch (Exception e) {
        logger.error(e.toString());
        e.printStackTrace();
      }

      if (jobCreateForm.getChkMail() != null &&
          jobCreateForm.getChkMail().equalsIgnoreCase("mail")) {
        isMailChecked = true;
      }
      if (jobCreateForm.getChkUpload() != null &&
          jobCreateForm.getChkUpload().equalsIgnoreCase("upload")) {
        isUploadChecked = true;
      }
      if (jobCreateForm.getChkFax() != null &&
          jobCreateForm.getChkFax().equalsIgnoreCase("fax")) {
        isFaxChecked = true;
      }

      JobFilesManagement
      .createJobFileProperties(scannedDocOutputPath, isMailChecked,
                                                 isUploadChecked,
                                                 isFaxChecked);

      logger.debug("------------------Exit Value=" + exitVal);
      Boolean scanCancel = (Boolean)httpSession.getAttribute("scanCancel");
      if ((exitVal == 0||exitVal == 7 || exitVal == 9) && !scanCancel.booleanValue()) {
        if (httpSession.getAttribute("scanCancel") != null) {
          httpSession.removeAttribute("scanCancel");
        }
        logger.debug("From ScanPage:  Scanning Successfull");

        if (isMailChecked) {
          //for catching exceptions in mail job.
          try {
            logger.debug("Submitting Mail Job ...");

            MailJob mailJob = new MailJob();
            mailJob
            .submit(jobCreateForm.getHdnCreator(), jobCreateForm.getHdnSenderEmailAddress(),
                           jobCreateForm.getTxtMailTo(),
                           jobCreateForm.getTxtCc(), jobCreateForm.getTxtBcc(),
                           jobCreateForm.getTxtSubject(),
                           jobCreateForm.getTxaMail(),
                           jobCreateForm.getHdnJobRetrialCount(),
                           jobCreateForm.getHdnErrorMesg(),
                           jobCreateForm.getHdnJobMaxCount(),
                           jobCreateForm.getHdnJobRetrialInterval(),
                           jobCreateForm.getHdnJobErrorMessage(),
                           Integer.parseInt(jobCreateForm.getDay()),
                           Integer.parseInt(jobCreateForm.getMonth()),
                           Integer.parseInt(jobCreateForm.getYear()),
                           Integer.parseInt(jobCreateForm.getHours()),
                           Integer.parseInt(jobCreateForm.getMinutes()),
                           Integer.parseInt(jobCreateForm.getSeconds()),
                           jobCreateForm.getSmtpHost(), sched,
                           scannedDocOutputPath, pattern);
            AuditTrailBean auditTrailBean = new AuditTrailBean();
            Timestamp tsPerformedDate = null;
            //in yyyy-mm-dd hh:mm:ss.fffffffff or yyyy-mm-dd hh:mm:ss format only.
            Timestamp tsScheduledDate = null;
            SimpleDateFormat formatter =
              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            tsPerformedDate =
              tsPerformedDate.valueOf(formatter.format(currentDate));
            tsScheduledDate =
              tsScheduledDate.valueOf(Integer.parseInt(jobCreateForm
                                                                       .getYear()) +
                                                      "-" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getMonth()) +
                                                      "-" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getDay()) +
                                                      " " +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getHours()) +
                                                      ":" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getMinutes()) +
                                                      ":" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getSeconds()));
            auditTrailBean.setUserId(jobCreateForm.getHdnCreator());
            auditTrailBean.setActionType(SchedulerConstants.MAIL_JOB);
            auditTrailBean.setPerformedDate(tsPerformedDate.toString());
            auditTrailBean.setScheduledDate(tsScheduledDate.toString());
            auditTrailBean.setPagesScanned(numPagesScanned);
            auditTrailBean.setFileName(jobCreateForm.getTxtDocName());
            auditTrailBean.setToAddress(jobCreateForm.getTxtMailTo());
            Connection conn =
              (Connection)httpSession.getServletContext().getAttribute("conn");
            AuditTrail.doAuditTrail(conn, auditTrailBean);
          } catch (Exception e) {
            logger.error("Exception in Mail Submit: " + e.toString());
            e.printStackTrace();
          }
        }
        if (isUploadChecked) {
          //for catching exceptions in upload job.
          try {
            logger.debug("Submitting Upload Job ...");

            DocUploadJob docUploadJob = new DocUploadJob();
            docUploadJob
            .submit(jobCreateForm.getHdnCreator(), jobCreateForm.getHdnJobRetrialCount(),
                                jobCreateForm.getHdnErrorMesg(),
                                jobCreateForm.getHdnJobMaxCount(),
                                jobCreateForm.getHdnJobRetrialInterval(),
                                jobCreateForm.getHdnJobErrorMessage(),
                                Integer.parseInt(jobCreateForm.getDay()),
                                Integer
                                .parseInt(jobCreateForm.getMonth()),
                                Integer
                                .parseInt(jobCreateForm.getYear()),
                                Integer
                                .parseInt(jobCreateForm.getHours()),
                                Integer
                                .parseInt(jobCreateForm.getMinutes()),
                                Integer
                                .parseInt(jobCreateForm.getSeconds()),
                                sched, scannedDocOutputPath, pattern,
                                jobCreateForm.getTxtDestinationFolderPath());
            AuditTrailBean auditTrailBean = new AuditTrailBean();
            Timestamp tsPerformedDate = null;
            //in yyyy-mm-dd hh:mm:ss.fffffffff or yyyy-mm-dd hh:mm:ss format only.
            Timestamp tsScheduledDate = null;
            SimpleDateFormat formatter =
              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            tsPerformedDate =
              tsPerformedDate.valueOf(formatter.format(currentDate));
            tsScheduledDate =
              tsScheduledDate.valueOf(Integer.parseInt(jobCreateForm
                                                                       .getYear()) +
                                                      "-" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getMonth()) +
                                                      "-" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getDay()) +
                                                      " " +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getHours()) +
                                                      ":" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getMinutes()) +
                                                      ":" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getSeconds()));
            auditTrailBean.setUserId(jobCreateForm.getHdnCreator());
            auditTrailBean.setActionType(SchedulerConstants.UPLOAD_JOB);
            auditTrailBean.setPerformedDate(tsPerformedDate.toString());
            auditTrailBean.setScheduledDate(tsScheduledDate.toString());
            auditTrailBean.setPagesScanned(numPagesScanned);
            auditTrailBean.setFileName(jobCreateForm.getTxtDocName());
            auditTrailBean.setToAddress("NA");
            Connection conn =
              (Connection)httpSession.getServletContext().getAttribute("conn");
            AuditTrail.doAuditTrail(conn, auditTrailBean);
          } catch (Exception e) {
            logger.error("Exception In Upload Submit: " + e.toString());
            e.printStackTrace();
          }
        }
        if (isFaxChecked) {
          //for catching exceptions in fax job.
          try {
            logger.debug("Submitting Fax Job ...");

            FaxJob faxJob = new FaxJob();
            faxJob
            .submit(jobCreateForm.getHdnCreator(), jobCreateForm.getHdnSenderEmailAddress(),
                          jobCreateForm.getTxtFaxTo(),
                          jobCreateForm.getTxtCompanyName(),
                          jobCreateForm.getTxtFaxNumber(),
                          jobCreateForm.getTxaFax(),
                          jobCreateForm.getHdnJobRetrialCount(),
                          jobCreateForm.getHdnErrorMesg(),
                          jobCreateForm.getHdnJobMaxCount(),
                          jobCreateForm.getHdnJobRetrialInterval(),
                          jobCreateForm.getHdnJobErrorMessage(),
                          Integer.parseInt(jobCreateForm.getDay()),
                          Integer.parseInt(jobCreateForm.getMonth()),
                          Integer.parseInt(jobCreateForm.getYear()),
                          Integer.parseInt(jobCreateForm.getHours()),
                          Integer.parseInt(jobCreateForm.getMinutes()),
                          Integer.parseInt(jobCreateForm.getSeconds()),
                          sched, scannedDocOutputPath, pattern);
            AuditTrailBean auditTrailBean = new AuditTrailBean();
            Timestamp tsPerformedDate = null;
            //in yyyy-mm-dd hh:mm:ss.fffffffff or yyyy-mm-dd hh:mm:ss format only.
            Timestamp tsScheduledDate = null;
            SimpleDateFormat formatter =
              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            tsPerformedDate =
              tsPerformedDate.valueOf(formatter.format(currentDate));
            tsScheduledDate =
              tsScheduledDate.valueOf(Integer.parseInt(jobCreateForm
                                                                       .getYear()) +
                                                      "-" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getMonth()) +
                                                      "-" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getDay()) +
                                                      " " +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getHours()) +
                                                      ":" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getMinutes()) +
                                                      ":" +
                                                      Integer.parseInt(jobCreateForm
                                                                             .getSeconds()));
            auditTrailBean.setUserId(jobCreateForm.getHdnCreator());
            auditTrailBean.setActionType(SchedulerConstants.FAX_JOB);
            auditTrailBean.setPerformedDate(tsPerformedDate.toString());
            auditTrailBean.setScheduledDate(tsScheduledDate.toString());
            auditTrailBean.setPagesScanned(numPagesScanned);
            auditTrailBean.setFileName(jobCreateForm.getTxtDocName());
            auditTrailBean.setToAddress(jobCreateForm.getTxtFaxTo());
            Connection conn =
              (Connection)httpSession.getServletContext().getAttribute("conn");
            AuditTrail.doAuditTrail(conn, auditTrailBean);
          } catch (Exception e) {
            logger.error("Exception In Fax Submit: " + e.toString());
            e.printStackTrace();
          }
        }
      } else {
        logger.debug("From ScanPage:  Scanning UnSuccessfull");
        File folderToDel = new File(scannedDocOutputPath);
        File[] fileArray = folderToDel.listFiles();
        logger.debug("Number of file: " + fileArray.length);
        logger.debug("Files to delete from " + folderToDel + " ..........");
        for (int i = 0; i < fileArray.length; i++) {
          logger.debug("fileArray[" + i + "]: " + fileArray[i]);
          fileArray[i].delete();
          logger.debug("deleted");
        }
        logger.debug("Deleting " + folderToDel);
        if (folderToDel.delete()) {
          logger.debug(folderToDel + " Deleted Successfully.");
        } else {
          logger.debug(folderToDel + " Delition Failed.");
        }
        throw new Exception("Scanning unsuccessfull so no job scheduled");
      }
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
    } finally {
      logger.info("---Exiting run() method of ScanHandler---");
      if (httpSession.getAttribute("scanProc") != null) {
        httpSession.removeAttribute("scanProc");
      }
    }
  }
}
