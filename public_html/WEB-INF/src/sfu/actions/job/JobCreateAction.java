
package sfu.actions.job;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Calendar;

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

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.quartz.Scheduler;

import sfu.actionforms.job.JobCreateForm;

import sfu.beans.scan.InitiateScan;

import sfu.beans.user.UserProfile;


/**
 * @purpose To create job for mail, fax and upload and scheduling it.
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModfiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobCreateAction extends Action {

  Logger logger = Logger.getLogger("SfuLogger");


  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
                                                                                                ServletException {
    logger.info("------Entering JobCreateAction------");
    ActionErrors errors = new ActionErrors();
    HttpSession httpSession = null;
    String destinationFolder = "";
    String pattern = null;
    ServletContext context = null;
    String xmlFilePrefix = null;
    JobCreateForm jobCreateForm = null;
    boolean asSingleFile = false;
    Boolean scanCancel = Boolean.valueOf(false);
    Boolean scanningDone=Boolean.valueOf(false);
    Connection conn;
    Statement stmt;
    String query;
    UserProfile userProfile;
    
    ActionMessages messages=new ActionMessages();


    try {
      logger.info("------Entering JobCreateAction------");
      
      httpSession = request.getSession(false);
      userProfile = (UserProfile)httpSession.getAttribute("userProfile");
      context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");
      stmt = conn.createStatement();
      xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
      jobCreateForm = (JobCreateForm)form;
      
      httpSession.setAttribute("scanCancel", scanCancel);
      httpSession.setAttribute("scanningDone",scanningDone);
      
      if (isCancelled(request)) {
        Process proc = (Process)httpSession.getAttribute("scanProc");
        if (proc != null) {
          logger.debug("SCAN PROCESS STATUS: proc is not null");
          scanCancel = Boolean.valueOf(true);
          httpSession.setAttribute("scanCancel", scanCancel);
          proc.destroy();
          httpSession.removeAttribute("scanProc");
          ActionMessage msg=new ActionMessage("msg.ScanCancelled");
          messages.add(ActionMessages.GLOBAL_MESSAGE,msg);
        } else {
          logger.debug("SCAN PROCESS STATUS: proc is null");
        }
        if(httpSession.getAttribute("scanningDone")!=null){
          httpSession.removeAttribute("scanningDone");
        }
        logger.debug("Scanning Cancelled ...");
        return mapping.findForward("success");
      }

      if (jobCreateForm.getChkSingleFile() != null) {
        asSingleFile = true;
      }

      logger.debug("asSingleFile: " + asSingleFile);

      if (jobCreateForm.getTxtDestinationFolderPath() != null &&
          jobCreateForm.getTxtDestinationFolderPath().trim().length() != 0) {

        //pattern=jobCreateForm.getCboOutputFormat();
        destinationFolder = jobCreateForm.getTxtDestinationFolderPath();

        query = "update users set def_user_dir=";
        query += " '" + destinationFolder + "' where 1=1 ";
        query += " and user_id='" + userProfile.getUserId() + "'";

        if (userProfile.getUserDirectory() == null) {
          logger.debug("Executing query: ... \n" + query);
          logger.debug("Currently User_DIR set to: null");
          stmt.execute(query);
          userProfile
          .setUserDirectory(jobCreateForm.getTxtDestinationFolderPath());
          logger
          .debug("User_DIR set to: " + jobCreateForm.getTxtDestinationFolderPath());
        } else if (!userProfile.getUserDirectory().equals(destinationFolder)) {
          logger
          .debug("Currently User_DIR set to: " + userProfile.getUserDirectory());
          logger.debug("Executing query: ... \n" + query);
          stmt.execute(query);
          userProfile
          .setUserDirectory(jobCreateForm.getTxtDestinationFolderPath());
          logger
          .debug("User_DIR set to: " + jobCreateForm.getTxtDestinationFolderPath());
        }
      }

      logger.debug("RETRIAL-COUNT: " + jobCreateForm.getHdnJobRetrialCount());
      //logger.debug("RETRIAL-MAX-COUNT: " + jobCreateForm.getHdnJobMaxCount());
      logger
      .debug("RETRIAL-INTERVAL: " + jobCreateForm.getHdnJobRetrialInterval());
      logger.debug("DocUpload job creator: " + jobCreateForm.getHdnCreator());

      logger.debug("Output Format: " + jobCreateForm.getCboOutputFormat());
      //Creating new folder for scanned documents which will be used as src for upload/mail/fax job;
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
      String currentDateString = formater.format(cal.getTime());

      String currentJobFolderSrc =
        jobCreateForm.getHdnJobFolder() + File.separator +
        jobCreateForm.getHdnCreator() + File.separator + currentDateString;
      //String currentJobFolderSrc =
      //uploadJobFolder + File.separator + creator + File.separator + year +
      //"-" + day + "-" + month + ":" + hours + ":" + minutes + ":" + seconds;
      File td = new File(currentJobFolderSrc);
      if (!td.exists()) {
        td.mkdirs();
      }

      logger
      .debug("scanner Parameters (" + "dpi: " + jobCreateForm.getHdnDpi() +
                   "currentJobFolderSrc: " + currentJobFolderSrc + "mode: " +
                   jobCreateForm.getHdnMode() + "outputFormat: " +
                   jobCreateForm.getCboOutputFormat() + ")");

      Scheduler sched = (Scheduler)context.getAttribute("scheduler");

      if (sched.isShutdown()) {
        ActionError docUploadError = new ActionError("scheduler.stopped");
        errors.add(ActionErrors.GLOBAL_ERROR, docUploadError);
        ActionMessage msg = new ActionMessage("scheduler.stopped");
        messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
        return mapping.getInputForward();
      } else {
        InitiateScan initiateScan =
          new InitiateScan(httpSession, jobCreateForm, sched, pattern,
                                                     asSingleFile,
                                                     currentJobFolderSrc);
        initiateScan.scanPage();

      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
      ActionMessage msg = new ActionMessage("msg.JavaException",e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
    } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      logger.info("------Exiting JobCreateAction------");
    }

    return mapping.findForward("success");
  }
}
