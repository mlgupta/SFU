package sfu.actions.user;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

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

import sfu.actionforms.user.UserForm;

import sfu.beans.misc.ErrorConstants;
import sfu.beans.user.UserCreate;

public class UserCreateAction extends Action {
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
    String userId = null;
    String userName = null;
    String emailId = null;
    String password = null;
    String confirmPassword = null;
    String isLocked = null;
    String isAdmin = null;
    Connection conn = null;

    Logger logger = Logger.getLogger("SfuLogger");
    ActionMessages messages = new ActionMessages();
    UserForm userForm = (UserForm)(form);
    try {
      logger.info("------Entering UserCreateAction------");

      request.setAttribute("hdnSearchPageNo", userForm.getHdnSearchPageNo());
      logger
      .debug("userForm.getHdnSearchPageNo()" + userForm.getHdnSearchPageNo());
      //logger.debug("");

      if (isCancelled(request)) {
        return mapping.findForward("success");
      }

      userId =
        ((String)PropertyUtils.getSimpleProperty(form, "txtUserId")).trim();
      emailId =
        ((String)PropertyUtils.getSimpleProperty(form, "txtEmailId")).trim();
      userName =
        ((String)PropertyUtils.getSimpleProperty(form, "txtUserName")).trim();
      logger.debug("***userName***: " + userName);
      password =
        ((String)PropertyUtils.getSimpleProperty(form, "txtPassword")).trim();
      confirmPassword =
        ((String)PropertyUtils.getSimpleProperty(form, "txtConfirmPassword"))
        .trim();

      isLocked =
        ((String)PropertyUtils.getSimpleProperty(form, "chkIsLocked")).trim();
      if (isLocked != null && isLocked.equals("on")) {
        isLocked = "t";
      } else {
        isLocked = "f";
      }

      logger.debug("***isLocked:***: " + isLocked);

      isAdmin =
        ((String)PropertyUtils.getSimpleProperty(form, "chkIsAdmin")).trim();


      if (isAdmin != null && isAdmin.equals("on")) {
        isAdmin = "t";
      } else {
        isAdmin = "f";
      }
      logger.debug("***isAdmin:***: " + isAdmin);

      UserCreate userCreate = new UserCreate();
      ServletContext context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");
      userCreate
      .createUser(conn, userId, userName, emailId, password, isLocked,
                            isAdmin);

    } catch (SQLException e) {
      logger.error("Severity(ErrorCode) :" + e.getErrorCode());
      logger.error("Message :" + e.getMessage());
      logger.error("SqlState :" + e.getSQLState());
      //if((e.getMessage().indexOf(ErrorConstants.UNIQUE.getErrorValue()))>-1){
      if (e.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode())) {
        ActionMessage msg =
          new ActionMessage("msg.Unique", userForm.getTxtUserId());
        messages.add("message1", msg);
      }

      saveMessages(request, messages);
      e.printStackTrace();
      return mapping.getInputForward();

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
      logger.info("------Exiting UserCreateAction------");
    }
    return mapping.findForward("success");
  }
}
