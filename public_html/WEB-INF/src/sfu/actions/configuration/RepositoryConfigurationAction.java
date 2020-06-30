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

import sfu.beans.configuration.RepositoryConfig;

public class RepositoryConfigurationAction extends Action {
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
    logger.info("------Entering RepositoryConfigurationAction------");
    String txtIpName = "";
    String txtRepositoryConnectString = "";
    String txtUser = "";
    String txtPassword = "";
    ActionMessages messages = new ActionMessages();
    try {
      if (request.getParameter("txtIpName") != null) {
        txtIpName = request.getParameter("txtIpName");
      }

      if (request.getParameter("txtRepositoryConnectString") != null) {
        txtRepositoryConnectString =
          request.getParameter("txtRepositoryConnectString");
      }

      if (request.getParameter("txtUser") != null) {
        txtUser = request.getParameter("txtUser");
      }

      if (request.getParameter("txtPassword") != null) {
        txtPassword = request.getParameter("txtPassword");
      }

      RepositoryConfig repositoryConfig =
        new RepositoryConfig((String)servlet.getServletContext()
                                                               .getAttribute("xmlFilePrefix"));
      repositoryConfig
      .writeNewSettings(txtIpName, txtRepositoryConnectString, txtUser,
                                        txtPassword);
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      logger.info("------Exiting RepositoryConfigurationAction------");
    }
    return mapping.findForward("success");
  }
}
