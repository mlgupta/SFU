package sfu.actions.job.fax;

import java.io.File;
import java.io.IOException;

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

import sfu.actionforms.job.fax.FaxForm;

import sfu.beans.configuration.JobConfig;
import sfu.beans.configuration.ScannerConfig;
import sfu.beans.scan.InitiateScan;
import sfu.beans.user.UserProfile;

public class FaxAction extends Action {
    Logger logger = Logger.getLogger("SfuLogger");

    /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @return 
   * @throws java.io.IOException
   * @throws javax.servlet.ServletException
   */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws IOException,
                                                                                                  ServletException {
        ActionErrors errors = new ActionErrors();
        String creator = "";
        String senderEmailAddress = "";
        String txtTo = "";
        String txaFax = "";
        String[] lstAttachment;
        String day = "";
        String month = "";
        String year = "";
        String hours = "";
        String minutes = "";
        String timezone = "";
        String seconds = "";
        String radDocSelection = "";
        String radNaming = "";
        //String txtPrefix="";
        String txtName = "";
        String txtSuffix = "";
        //String chkPrefix="";
        String chkSuffix = "";
        HttpSession httpSession = null;
        String jobRetrialCount = "0";
        String errorMesg = "";
        String jobMaxCount = null;
        String jobRetrialInterval = null;
        String jobErrorMessage = "NA";
        String parentFolder = "";
        String pattern = null;
        String tsString = null;
        ServletContext context = null;
        FaxForm faxForm = null;
        String xmlFilePrefix = null;
        String dpi = null;
        String outputFormat = null;
        String mode = null;
        String faxJobFolder = null;
        String noOfDoc = null;
        String txtCompanyName = null;
        String txtFaxNumber = null;
        boolean asSingleFile = true;
        Boolean scanCancel = new Boolean(false);
        try {
            logger.info("------Entering FaxAction------");
            httpSession = request.getSession(false);
            context = servlet.getServletContext();
            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
            faxForm = (FaxForm)form;
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
            noOfDoc = faxForm.getTxtDocNo();
            if (request.getParameter("txtTo") != null) {
                txtTo = request.getParameter("txtTo");
            }
            if (request.getParameter("txaFax") != null) {
                txaFax = request.getParameter("txaFax");
            }
            if (request.getParameter("day") != null) {
                day = request.getParameter("day");
            }
            if (request.getParameter("month") != null) {
                month = request.getParameter("month");
            }
            if (request.getParameter("year") != null) {
                year = request.getParameter("year");
            }
            if (request.getParameter("hours") != null) {
                hours = request.getParameter("hours");
            }
            if (request.getParameter("minutes") != null) {
                minutes = request.getParameter("minutes");
            }
            if (request.getParameter("seconds") != null) {
                seconds = request.getParameter("seconds");
            }
            if (request.getParameter("radDocSelection") != null) {
                radDocSelection = request.getParameter("radDocSelection");
            }
            if (request.getParameter("radNaming") != null) {
                radNaming = request.getParameter("radNaming");
            }
            if (request.getParameter("txtName") != null) {
                txtName = request.getParameter("txtName");
            }
            if (request.getParameter("txtSuffix") != null) {
                txtSuffix = request.getParameter("txtSuffix");
            }
            if (request.getParameter("txtCompanyName") != null) {
                txtCompanyName = request.getParameter("txtCompanyName");
            }
            if (request.getParameter("txtFaxNumber") != null) {
                txtFaxNumber = request.getParameter("txtFaxNumber");
            }

            JobConfig jobConfig = new JobConfig(xmlFilePrefix);
            jobMaxCount = jobConfig.getMaxNumberOfRetries();
            // (String)httpSession.getServletContext().getAttribute("jobRetrialCount");
            jobRetrialInterval = jobConfig.getRetrialInterval();
            //(String)httpSession.getServletContext().getAttribute("jobRetrialInterval");

            logger.debug("RETRIAL-COUNT: " + jobRetrialCount);
            logger.debug("RETRIAL-MAX-COUNT: " + jobMaxCount);
            logger.debug("RETRIAL-INTERVAL: " + jobRetrialInterval);

            creator =
                ((UserProfile)httpSession.getAttribute("userProfile")).getUserId();

            senderEmailAddress = "root@linux.dbsentry.com";
            ScannerConfig scannerConfig = new ScannerConfig(xmlFilePrefix);

            //Setting The Parameters For Scanner
            dpi = scannerConfig.getDPI();
            faxJobFolder = scannerConfig.getJobFolder();
            mode = scannerConfig.getMode();
            outputFormat = scannerConfig.getOutputFormat();

            //Creating new folder for scanned documents which will be used as src for mail job;
            String currentFaxJobFolderSrc =
                faxJobFolder + File.separator + creator + File.separator +
                year + "-" + day + "-" + month + ":" + hours + ":" + minutes +
                ":" + seconds;
            File td = new File(currentFaxJobFolderSrc);
            if (!td.exists()) {
                td.mkdirs();
            }
            parentFolder = currentFaxJobFolderSrc;
            //parentFolder="/home/amit/Scan/Jobs/amishra/test";//HARDCODED-----------------------------------
            logger
            .debug("scanner Parameters (" + "dpi: " + dpi + "currentFaxJobFolderSrc: " +
                         currentFaxJobFolderSrc + "mode: " + mode +
                         "outputFormat: " + outputFormat + ")");

            //InitiateScan faxScan=new InitiateScan(httpSession, txtName,noOfDoc,asSingleFile,dpi,mode,outputFormat,currentFaxJobFolderSrc);
            Scheduler sched = (Scheduler)context.getAttribute("scheduler");

            //faxScan.compressPage();
            //String numPagesScanned=new String().valueOf(faxScan.convertPage());

            //String numPagesScanned="2";//to be commented


            if (sched.isShutdown()) {
                ActionError faxError = new ActionError("scheduler.stopped");
                errors.add(ActionErrors.GLOBAL_ERROR, faxError);
            } else {
                /*InitiateScan faxScan =
                    new InitiateScan(httpSession, creator, senderEmailAddress,
                                                        txtTo, txtCompanyName,
                                                        txtFaxNumber, txaFax,
                                                        jobRetrialCount,
                                                        errorMesg, jobMaxCount,
                                                        jobRetrialInterval,
                                                        jobErrorMessage,
                                                        Integer.parseInt(day),
                                                        Integer
                                                        .parseInt(month),
                                                        Integer.parseInt(year),
                                                        Integer
                                                        .parseInt(hours),
                                                        Integer
                                                        .parseInt(minutes),
                                                        Integer
                                                        .parseInt(seconds),
                                                        sched, parentFolder,
                                                        pattern, txtName,
                                                        noOfDoc, asSingleFile,
                                                        dpi, mode,
                                                        outputFormat,
                                                        currentFaxJobFolderSrc);
                faxScan.scanPage();*/

                /*FaxJob faxJob=new FaxJob();  
        faxJob.submit( creator,
                        senderEmailAddress,
                        txtTo,
                        txtCompanyName,
                        txtFaxNumber,
                        txaFax, 
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
                        pattern
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
      auditTrailBean.setActionType(SchedulerConstants.FAX_JOB);
      auditTrailBean.setPerformedDate(tsPerformedDate.toString());
      auditTrailBean.setScheduledDate(tsScheduledDate.toString());
      auditTrailBean.setPagesScanned(numPagesScanned);
      auditTrailBean.setFileName(txtName);
      auditTrailBean.setToAddress(txtTo);
      Connection conn= (Connection)context.getAttribute("conn");
      AuditTrail.doAuditTrail(conn,auditTrailBean);
      */
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("------Exiting FaxAction------");
        }
        return mapping.findForward("success");
    }
}
