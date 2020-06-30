package sfu.actions.login;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

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

import sfu.actionforms.login.LoginForm;

import sfu.beans.misc.Constants;
import sfu.beans.user.UserAuthenticate;
import sfu.beans.user.UserProfile;

public class LoginAction extends Action {
  Logger logger = Logger.getLogger("SfuLogger");

  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
                                                                                                ServletException {
    ActionMessages messages = new ActionMessages();
    LoginForm loginForm = null;
    //UserProfile userProfile=null;
    ServletContext context = null;
    HttpSession httpSession = null;
    //String physicalPath = null;
    String userType = null;
    String userId = null;
    String userPassword = null;
    Connection conn = null;
    UserProfile userProfile = null;

    try {
      logger.info("------Entering LoginAction------");

      context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");
      loginForm = (LoginForm)form;
      userId = loginForm.getTxtLoginId();
      userPassword = loginForm.getTxtLoginPassword();
      httpSession = request.getSession(true);
      //httpSession.setMaxInactiveInterval(10);
      //physicalPath = httpSession.getServletContext().getRealPath("/");
      //userProfile=new UserProfile();
      if((UserProfile)httpSession.getAttribute("userProfile")!=null){
        userProfile=(UserProfile)httpSession.getAttribute("userProfile");
        if(userProfile.getIsAdmin().equalsIgnoreCase("true")){
          return mapping.findForward("admin");
        }else{
          return mapping.findForward("general");
        }
      }else{
        userProfile = new UserProfile();  
        userType =
          UserAuthenticate.authenticateUser(userId, userPassword, conn, userProfile);
        logger.debug("userType: " + userType);
        if (userType != null && userType.equals(Constants.ADMIN.toString())) {
          httpSession.setAttribute("userProfile", userProfile);
          return mapping.findForward("admin");
        } else if (userType != null &&
                   userType.equals(Constants.GENERAL.toString())) {
          httpSession.setAttribute("userProfile", userProfile);
          return mapping.findForward("general");
        } else if (userType != null &&
                   userType.equals(Constants.LOCKED.toString())) {
          ActionMessage msg = new ActionMessage("msg.UserLocked", userId);
          messages.add("message1", msg);
          return mapping.getInputForward();
        } else {
          ActionMessage msg = new ActionMessage("msg.LoginFailed");
          messages.add("message1", msg);
        }
      }
    } catch (SQLException e) {
      logger.error("SqlException Caught: " + e);
    } catch (Exception e) {
      logger.error("Exception Caught: " + e.toString());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
    } finally {
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      logger.info("------Exiting LoginAction------");
    }
    saveMessages(request, messages);

    return mapping.getInputForward();
  }


}
