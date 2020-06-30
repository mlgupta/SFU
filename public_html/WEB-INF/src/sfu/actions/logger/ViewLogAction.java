package sfu.actions.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sfu.actionforms.logger.ViewLogForm;

import sfu.beans.logger.Log;

public class ViewLogAction extends Action {

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
    Logger logger = Logger.getLogger("SfuLogger");
    ViewLogForm viewLogForm = new ViewLogForm();
    Log log = new Log();
    String viewLog = null;
    String radLogType = null;
    int viewLinesTillEndStartingFrom = 0;
    int viewLinesFromStartTill = 0;
    String logPath = null;
    ActionMessages messages = new ActionMessages();
    try {
      logger.debug("------Entering ViewLogAction------");
      //logPath="/home/ias/SFUProj/public_html/sqlnet.log";
      ServletContext context = servlet.getServletContext();
      File logFileProp =
        new File((String)context.getAttribute("contextPath") + File.separator +
                                  "WEB-INF" + File.separator +
                                  "log4j.properties");
      FileInputStream fis = new FileInputStream(logFileProp);
      Properties logProperties = new Properties();
      logProperties.load(fis);
      logPath = logProperties.getProperty("log4j.appender.R.File");
      //
      File fle = new File(".");
      logger.debug("Absolute path Path: " + fle.getAbsolutePath());
      fle = new File(logPath);
      logger.debug("Log file Path: " + fle.getAbsolutePath());

      //
      System.out.println(logPath);
      if (request.getParameter("radLogType") != null) {
        radLogType =
          ((String)PropertyUtils.getSimpleProperty(form, "radLogType")).trim();
        System.out.println(radLogType);
      } else {
        radLogType = viewLogForm.getRadLogType();
      }
      if (request.getParameter("txtViewLinesTillEndStartingFrom") != null) {
        viewLinesTillEndStartingFrom =
          (Integer.parseInt(request.getParameter("txtViewLinesTillEndStartingFrom")));
      } else {
        viewLinesTillEndStartingFrom =
          viewLogForm.getTxtViewLinesTillEndStartingFrom();
      }

      if ((request.getParameter("txtViewLinesFromStartTill")) != null) {
        viewLinesFromStartTill =
          (Integer.parseInt(request.getParameter("txtViewLinesFromStartTill")));
      } else {
        viewLinesFromStartTill = 0;
      }

      if (radLogType != null) {
        viewLogForm.setRadLogType(radLogType);
        if ((radLogType.equalsIgnoreCase("2")) &&
            (viewLinesFromStartTill != 0)) {
          viewLogForm.setTxtViewLinesFromStartTill(viewLinesFromStartTill);
          viewLogForm.setPosition("start");
          viewLog =
            log.getLog(viewLogForm.getPosition(), viewLinesFromStartTill,
                               logPath);
        } else if ((radLogType.equalsIgnoreCase("3")) &&
                   (viewLinesTillEndStartingFrom != 0)) {
          viewLogForm
          .setTxtViewLinesTillEndStartingFrom(viewLinesTillEndStartingFrom);
          viewLogForm.setPosition("end");
          viewLog =
            log.getLog(viewLogForm.getPosition(), viewLinesTillEndStartingFrom,
                               logPath);
        } else if ((radLogType.equalsIgnoreCase("1"))) {
          viewLogForm.setPosition("full");
          viewLog = log.getLog(viewLogForm.getPosition(), 0, logPath);
        }
      }

      if (viewLog != null) {
        viewLogForm.setDisplayLog(viewLog);
      }

      request.setAttribute(mapping.getAttribute(), viewLogForm);
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException",e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      logger.info("------Exiting ViewLogAction------");
    }
    return mapping.findForward("success");
  }
}
