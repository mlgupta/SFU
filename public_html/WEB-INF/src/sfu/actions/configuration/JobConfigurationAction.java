package sfu.actions.configuration;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sfu.beans.configuration.JobConfig;

public class JobConfigurationAction extends Action {
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
    String txtMaxUploadSize = "";
    String cboUploadSizeUnit = "";
    String txtMaxMailSize = "";
    String cboMailSizeUnit = "";
    String txtMaxFaxSize = "";
    String cboFaxSizeUnit = "";
    String txtMaxPagesToFax = "";
    String txtMaxNumberOfRetries = "";
    String txtRetrialInterval = "";
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering JobConfigurationAction------");

      if (request.getParameter("txtMaxUploadSize") != null) {
        txtMaxUploadSize = request.getParameter("txtMaxUploadSize");
      }

      if (request.getParameter("cboUploadSizeUnit") != null) {
        cboUploadSizeUnit = request.getParameter("cboUploadSizeUnit");
      }

      if (request.getParameter("txtMaxMailSize") != null) {
        txtMaxMailSize = request.getParameter("txtMaxMailSize");
      }

      if (request.getParameter("cboMailSizeUnit") != null) {
        cboMailSizeUnit = request.getParameter("cboMailSizeUnit");
      }

      if (request.getParameter("txtMaxFaxSize") != null) {
        txtMaxFaxSize = request.getParameter("txtMaxFaxSize");
      }

      if (request.getParameter("cboFaxSizeUnit") != null) {
        cboFaxSizeUnit = request.getParameter("cboFaxSizeUnit");
      }

      if (request.getParameter("txtMaxPagesToFax") != null) {
        txtMaxPagesToFax = request.getParameter("txtMaxPagesToFax");
      }

      if (request.getParameter("txtMaxNumberOfRetries") != null) {
        txtMaxNumberOfRetries = request.getParameter("txtMaxNumberOfRetries");
      }
      if (request.getParameter("txtRetrialInterval") != null) {
        txtRetrialInterval = request.getParameter("txtRetrialInterval");
      }

      JobConfig jobConfig =
        new JobConfig((String)servlet.getServletContext().getAttribute("xmlFilePrefix"));
      jobConfig
      .writeNewSettings(txtMaxUploadSize, cboUploadSizeUnit, txtMaxMailSize,
                                 cboMailSizeUnit, txtMaxFaxSize,
                                 cboFaxSizeUnit, txtMaxPagesToFax,
                                 txtMaxNumberOfRetries, txtRetrialInterval);
    } catch (Exception e) {
      e.printStackTrace();
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if (!messages.isEmpty()) {
        saveMessages(request,messages);
      }
      logger.info("------Exiting JobConfigurationAction------");
    }
    return mapping.findForward("success");
  }
}
