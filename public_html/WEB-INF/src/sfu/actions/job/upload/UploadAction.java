package sfu.actions.job.upload;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;

import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.quartz.Scheduler;

import sfu.actionforms.upload.DocUploadForm;

import sfu.beans.configuration.JobConfig;
import sfu.beans.configuration.ScannerConfig;
import sfu.beans.scan.InitiateScan;
import sfu.beans.user.UserProfile;


//import sfu.beans.job.MailJob;

public class UploadAction extends Action {
    Logger logger = Logger.getLogger("SfuLogger");

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException,
                                                                                                  ServletException {
        ActionErrors errors = new ActionErrors();
        String creator = "";
        String day = "";
        String month = "";
        String year = "";
        String hours = "";
        String minutes = "";
        // String timezone="";
        String seconds = "";
        String radDocSelection = "";
        String radNaming = "";
        String txtName = "";
        String txtSuffix = "";
        String chkSuffix = "";
        HttpSession httpSession = null;
        String jobRetrialCount = "0";
        String errorMesg = "NA";
        String jobMaxCount = null;
        String jobRetrialInterval = null;
        String jobErrorMessage = "NA";
        String parentFolder = "";
        String destinationFolder = "";
        String pattern = null;
        String tsString = null;
        ServletContext context = null;
        String xmlFilePrefix = null;
        String dpi = null;
        String outputFormat = null;
        String mode = null;
        String uploadJobFolder = null;
        DocUploadForm docUploadForm = null;
        String noOfDoc = null;
        boolean asSingleFile = false;
        Boolean scanCancel;
        scanCancel = new Boolean(false);
        Connection conn;
        Statement stmt;
        String query;
        UserProfile userProfile;
        try {
            logger.info("------Entering UploadAction------");
            httpSession = request.getSession(false);
            userProfile = (UserProfile)httpSession.getAttribute("userProfile");
            context = servlet.getServletContext();
            conn = (Connection)context.getAttribute("conn");
            stmt = conn.createStatement();
            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
            docUploadForm = (DocUploadForm)form;

            httpSession.setAttribute("scanCancel", scanCancel);

            if (isCancelled(request)) {
                Process proc = (Process)httpSession.getAttribute("scanProc");
                if (proc != null) {
                    logger.debug("SCAN PROCESS STATUS: proc is not null");
                    scanCancel = new Boolean(true);
                    httpSession.setAttribute("scanCancel", scanCancel);
                    proc.destroy();
                    httpSession.removeAttribute("scanProc");
                } else {
                    logger.debug("SCAN PROCESS STATUS: proc is null");
                }
                logger.debug("Scanning Cancelled ...");
                return mapping.findForward("success");
            }

            if (docUploadForm.getDay() != null) {
                day = docUploadForm.getDay();
            }
            if (docUploadForm.getMonth() != null) {
                month = docUploadForm.getMonth();
            }
            if (docUploadForm.getYear() != null) {
                year = docUploadForm.getYear();
            }
            if (docUploadForm.getHours() != null) {
                hours = docUploadForm.getHours();
            }
            if (docUploadForm.getMinutes() != null) {
                minutes = docUploadForm.getMinutes();
            }
            //if(docUploadForm.getTimezone()!=null){timezone=docUploadForm.getTimezone();}
            if (docUploadForm.getSeconds() != null) {
                seconds = docUploadForm.getSeconds();
            }

            radDocSelection = docUploadForm.getRadNoOfDocuments();
            logger.debug("radDocSelection: " + radDocSelection);
            noOfDoc = docUploadForm.getTxtDocNo();
            logger.debug("***noOfDoc: " + noOfDoc);
            radNaming = radDocSelection = docUploadForm.getRadNamingMethod();
            txtName = docUploadForm.getTxtDocName();

            logger.debug("***txtName: " + txtName);
            if (request.getParameter("txtSuffix") != null) {
                txtSuffix = request.getParameter("txtSuffix");
            }
            if (request.getParameter("chkSuffix") != null) {
                chkSuffix = request.getParameter("chkSuffix");
            }
            if (request.getParameter("chkSingleFile") != null) {
                asSingleFile = true;
            }
            logger.debug("asSingleFile: " + asSingleFile);

            if (request.getParameter("txtDestinationFolderPath") != null) {
                destinationFolder =
                    request.getParameter("txtDestinationFolderPath");

                query = "update users set def_user_dir=";
                query += " '" + destinationFolder + "' where 1=1 ";
                query += " and user_id='" + userProfile.getUserId() + "'";

                if (userProfile.getUserDirectory() == null) {
                    logger.debug("Executing query: ... \n" + query);
                    logger.debug("Currently User_DIR set to: null");
                    stmt.execute(query);
                    userProfile
                    .setUserDirectory(docUploadForm.getTxtDestinationFolderPath());
                    logger
                    .debug("User_DIR set to: " + docUploadForm.getTxtDestinationFolderPath());
                } else if (!userProfile.getUserDirectory()
                           .equals(destinationFolder)) {
                    logger
                    .debug("Currently User_DIR set to: " + userProfile.getUserDirectory());
                    logger.debug("Executing query: ... \n" + query);
                    stmt.execute(query);
                    userProfile
                    .setUserDirectory(docUploadForm.getTxtDestinationFolderPath());
                    logger
                    .debug("User_DIR set to: " + docUploadForm.getTxtDestinationFolderPath());
                }
            }


            JobConfig jobConfig = new JobConfig(xmlFilePrefix);
            jobMaxCount = jobConfig.getMaxNumberOfRetries();
            // (String)httpSession.getServletContext().getAttribute("jobRetrialCount");
            jobRetrialInterval = jobConfig.getRetrialInterval();
            //(String)httpSession.getServletContext().getAttribute("jobRetrialInterval");

            logger.debug("RETRIAL-COUNT: " + jobRetrialCount);
            logger.debug("RETRIAL-MAX-COUNT: " + jobMaxCount);
            logger.debug("RETRIAL-INTERVAL: " + jobRetrialInterval);

            creator = userProfile.getUserId();

            logger.debug("DocUpload job creator: " + creator);

            ScannerConfig scannerConfig = new ScannerConfig(xmlFilePrefix);

            //Setting The Parameters For Scanner
            dpi = scannerConfig.getDPI();
            uploadJobFolder = scannerConfig.getJobFolder();
            mode = scannerConfig.getMode();
            outputFormat = scannerConfig.getOutputFormat();
            //Creating new folder for scanned documents which will be used as src for upload job;
            String currentUploadJobFolderSrc =
                uploadJobFolder + File.separator + creator + File.separator +
                year + "-" + day + "-" + month + ":" + hours + ":" + minutes +
                ":" + seconds;
            File td = new File(currentUploadJobFolderSrc);
            if (!td.exists()) {
                td.mkdirs();
            }
            parentFolder = currentUploadJobFolderSrc;
            logger
            .debug("scanner Parameters (" + "dpi: " + dpi + "currentUploadJobFolderSrc: " +
                         currentUploadJobFolderSrc + "mode: " + mode +
                         "outputFormat: " + outputFormat + ")");

            Scheduler sched = (Scheduler)context.getAttribute("scheduler");
            //InitiateScan docUploadScan=new InitiateScan(httpSession, txtName,noOfDoc,asSingleFile,dpi,mode,outputFormat,currentUploadJobFolderSrc);

            //docUploadScan.compressPage();
            //String numPagesScanned=new String().valueOf(docUploadScan.convertPage());


            logger
            .debug("day+month+year+hours+minutes+seconds: " + day + month +
                         year + hours + minutes + ":" + seconds);

            if (sched.isShutdown()) {
                ActionError docUploadError =
                    new ActionError("scheduler.stopped");
                errors.add(ActionErrors.GLOBAL_ERROR, docUploadError);
            } else {
                /*InitiateScan docUploadScan =
                    new InitiateScan(httpSession, creator, jobRetrialCount,
                                                              errorMesg,
                                                              jobMaxCount,
                                                              jobRetrialInterval,
                                                              jobErrorMessage,
                                                              Integer
                                                              .parseInt(day),
                                                              Integer
                                                              .parseInt(month),
                                                              Integer
                                                              .parseInt(year),
                                                              Integer
                                                              .parseInt(hours),
                                                              Integer
                                                              .parseInt(minutes),
                                                              Integer
                                                              .parseInt(seconds),
                                                              sched,
                                                              parentFolder,
                                                              pattern,
                                                              destinationFolder,
                                                              txtName, noOfDoc,
                                                              asSingleFile,
                                                              dpi, mode,
                                                              outputFormat,
                                                              currentUploadJobFolderSrc);
                docUploadScan.scanPage();*/

                /*
        DocUploadJob docUploadJob=new DocUploadJob();
        docUploadJob.submit(creator,
                            jobRetrialCount,
                            errorMesg,
                            jobMaxCount,
                            jobRetrialInterval,
                            jobErrorMessage,
                            Integer.parseInt(day),
                            Integer.parseInt(month),
                            Integer.parseInt(year),
                            Integer.parseInt(hours),
                            Integer.parseInt(minutes),
                            Integer.parseInt(seconds),                            
                            sched,
                            parentFolder,
                            pattern,
                            destinationFolder
                           );
          
      }
      
      AuditTrailBean auditTrailBean=new AuditTrailBean();
      Timestamp tsPerformedDate=null;//in yyyy-mm-dd hh:mm:ss.fffffffff or yyyy-mm-dd hh:mm:ss format only.
      Timestamp tsScheduledDate=null;
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date currentDate = new Date();
      tsPerformedDate=tsPerformedDate.valueOf(formatter.format(currentDate));
      tsScheduledDate=tsScheduledDate.valueOf(year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds);
      auditTrailBean.setUserId(creator);
      auditTrailBean.setActionType(SchedulerConstants.UPLOAD_JOB);
      auditTrailBean.setPerformedDate(tsPerformedDate.toString());
      auditTrailBean.setScheduledDate(tsScheduledDate.toString());
      auditTrailBean.setPagesScanned(numPagesScanned);
      auditTrailBean.setFileName(txtName);
      auditTrailBean.setToAddress("NA");
      Connection conn= (Connection)context.getAttribute("conn");
      AuditTrail.doAuditTrail(conn,auditTrailBean);
      */
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("------Exiting UploadAction------");
        }

        return mapping.findForward("success");
    }
}
