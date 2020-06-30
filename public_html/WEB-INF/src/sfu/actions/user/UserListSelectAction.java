package sfu.actions.user;

import java.io.IOException;

import java.sql.Connection;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sfu.actionforms.user.UserListSelectForm;

import sfu.beans.user.UserListSelect;
import sfu.beans.user.UserProfile;


public class UserListSelectAction extends Action {
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
    String openerControl = null;

    UserListSelectForm userListSelectForm = null;
    ArrayList users = null;
    Connection conn = null;
    ServletContext context = null;
    ActionMessages messages = new ActionMessages();
    Logger logger = Logger.getLogger("SfuLogger");
    try {
      logger.info("------Entering UserListSelectAction------");
      userListSelectForm = (UserListSelectForm)form;

      HttpSession httpSession = request.getSession(false);
      UserProfile userProfile =
        (UserProfile)httpSession.getAttribute("userProfile");
      int numberOfRecords = Integer.parseInt(userProfile.getNumberOfRecords());
      logger.debug("numberOfRecords: " + numberOfRecords);

      if (request.getParameter("openerControl") != null) {
        openerControl = request.getParameter("openerControl");
      } else if (request.getParameter("hdnOpenerControl") != null) {
        openerControl = request.getParameter("hdnOpenerControl");
      }
      logger.debug("openerControl***: " + openerControl);

      context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");

      if (request.getAttribute("hdnSearchPageNo") != null) {
        userListSelectForm
        .setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
        // To return to the previous list page after create modify.
        logger
        .debug("***page no in list action afetr create***" + userListSelectForm
                     .getHdnSearchPageNo());
      }
      users =
        UserListSelect.getUsers(userListSelectForm, conn, numberOfRecords);
      logger
      .debug("***page number*** " + userListSelectForm.getHdnSearchPageNo());

      userListSelectForm.setHdnOpenerControl(openerControl);
      userListSelectForm.setRadSelect("");
      request.setAttribute("users", users);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      logger.info("------Exiting UserListSelectAction------");
    }
    return mapping.findForward("success");
  }
}
