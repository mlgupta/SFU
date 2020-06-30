package sfu.actions.user;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import sfu.actionforms.user.UserForm;

import sfu.beans.misc.ErrorConstants;
import sfu.beans.user.UserModify;


public class UserModifyAction extends Action {
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
    ServletContext context = null;
    Connection conn = null;
    UserForm userForm = null;
    Logger logger = Logger.getLogger("SfuLogger");
    ActionMessages messages = new ActionMessages();

    try {
      logger.info("------Entering UserModifyAction------");

      userForm = (UserForm)form;
      request.setAttribute("hdnSearchPageNo", userForm.getHdnSearchPageNo());

      if (isCancelled(request)) {
        return mapping.findForward("success");
      }

      context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");

      UserModify userModify = new UserModify();
      userModify.modifyUser(conn, userForm);
    } catch (SQLException e) {
      logger.error("Severity(ErrorCode) :" + e.getErrorCode());
      logger.error("Message :" + e.getMessage());
      logger.error("SqlState :" + e.getSQLState());
      if (e.getSQLState().equals(ErrorConstants.UNIQUE.getErrorCode())) {
        ActionMessage msg =
          new ActionMessage("msg.Unique", userForm.getTxtUserId());
        messages.add("message1", msg);
      }
      saveMessages(request, messages);
      e.printStackTrace();
      return mapping.getInputForward();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      logger.info("------Exiting UserModifyAction------");
    }
    return mapping.findForward("success");
  }
}
