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

import sfu.actionforms.user.UserListForm;

import sfu.beans.user.UserList;
import sfu.beans.user.UserProfile;


public class UserListAction extends Action {
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
    UserListForm userListForm = null;
    ArrayList users = null;
    Connection conn = null;
    ServletContext context = null;
    
    Logger logger = Logger.getLogger("SfuLogger");
    ActionMessages messages = new ActionMessages();
    
    try {
      logger.info("------Entering UserListAction------");

      HttpSession session = request.getSession(false);
      UserProfile userProfile =
        (UserProfile)session.getAttribute("userProfile");
      int numberOfRecords = Integer.parseInt(userProfile.getNumberOfRecords());

      context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");
      userListForm = (UserListForm)form;
      if (request.getAttribute("hdnSearchPageNo") != null) {
        userListForm
        .setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
        // To return to the previous list page after create modify.
        logger
        .debug("***page no in list action afetr create***" + userListForm.getHdnSearchPageNo());
      }
      users = UserList.getUsers(userListForm, conn, numberOfRecords);
      logger.debug("***page number*** " + userListForm.getHdnSearchPageNo());

      userListForm.setRadSelect("");
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
      logger.info("------Exiting UserListAction------");
    }
    return mapping.findForward("success");
  }
}
