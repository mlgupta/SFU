package sfu.actions.user;

import java.io.IOException;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sfu.actionforms.user.UserForm;

import sfu.beans.user.UserModify;

public class UserModifyB4Action extends Action {
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
        UserForm userForm = null;
        String userId = null;
        Connection conn = null;
        ServletContext context = null;
        try {
            logger.info("------Entering UserModifyB4Action------");
            if (isCancelled(request)) {
                return mapping.getInputForward();
            }
            userId = request.getParameter("radSelect");
            context = servlet.getServletContext();
            conn = (Connection)context.getAttribute("conn");
            userForm = UserModify.viewUser(userId, conn);
            userForm
            .setHdnSearchPageNo(request.getParameter("hdnSearchPageNo"));
            request.setAttribute(mapping.getAttribute(), userForm);
        } catch (Exception e) {
            logger.error("exception:" + e);
            return mapping.getInputForward();
        } finally {
            logger.info("------Exiting UserModifyB4Action------");
        }
        return mapping.findForward("success");
    }
}
