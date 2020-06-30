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

import sfu.actionforms.configuration.ConfigurationMainForm;

public class ConfigurationMainAction extends Action {
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
    ActionMessages messages = new ActionMessages();
    Logger logger = Logger.getLogger("SfuLogger");
    try {
      logger.info("------Entering ConfigurationMainAction------");
      ConfigurationMainForm configurationMainForm =
        new ConfigurationMainForm();
      logger.debug(request.getParameter("pageToCall"));
      if (request.getParameter("pageToCall") != null &&
          !(request.getParameter("pageToCall").equals(""))) {
        configurationMainForm
        .setPageToCall(request.getParameter("pageToCall"));
      } else {
        configurationMainForm
        .setPageToCall("repositoryConfigurationB4Action.do");
      }
      request.setAttribute("ConfigurationMainForm", configurationMainForm);
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      logger.info("------Exiting ConfigurationMainAction------");
    }
    return mapping.findForward("success");
  }
}
