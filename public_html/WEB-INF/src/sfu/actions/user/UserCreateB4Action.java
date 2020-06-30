package sfu.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sfu.actionforms.user.UserForm;

public class UserCreateB4Action extends Action {
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
  Logger logger = Logger.getLogger("SfuLogger");

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
                                                                                                ServletException {
    UserForm userForm = null;

    userForm = new UserForm();

    try {
      logger.info("------Entering UserCreateB4Action------");

      userForm.setTxtUserId("");
      userForm.setTxtUserName("");
      userForm.setTxtEmailId("");
      userForm.setTxtPassword("");
      userForm.setTxtUserStatus("");
      userForm.setTxtConfirmPassword("");
      userForm.setChkIsLocked("false");
      userForm.setChkIsAdmin("false");
      userForm.setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
      logger
      .debug("page no. in createB4 action" + userForm.getHdnSearchPageNo());
      request.setAttribute(mapping.getAttribute(), userForm);
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    } finally {
      logger.info("------Exiting UserCreateB4Action------");
    }

    return mapping.findForward("success");
  }
}
