package sfu.actions.auditTrail;

import java.io.IOException;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import java.util.TimeZone;

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

import sfu.actionforms.auditTrail.AuditTrailListForm;
import sfu.actionforms.user.UserListForm;

import sfu.beans.auditTrail.AuditTrail;
import sfu.beans.scheduler.SchedulerConstants;
import sfu.beans.user.UserList;
import sfu.beans.user.UserListBean;


public class AuditTrailListAction extends Action {


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
        String actionType = null;
        String performedFromDate = null;
        String scheduledDate = null;
        String pagesScanned = null;
        String fileName = null;
        String to = null;
        AuditTrailListForm auditTrailListForm = null;
        ArrayList auditTrails = null;
        Connection conn = null;
        ServletContext context = null;
        String[] sArray = null;
        String searchByActionType = null;
        //HttpSession httpSession=null;
        int numberOfRecords = 5;
        //Change this hard coded value
        Logger logger = Logger.getLogger("SfuLogger");
        //HttpSession httpSession=request.getSession(true);
        //Hashtable userTableInSession=new Hashtable();
        try {
            logger.info("------Entering AuditTrailListAction------");
            //httpSession = request.getSession(false);

            context = servlet.getServletContext();
            conn = (Connection)context.getAttribute("conn");
            auditTrailListForm = (AuditTrailListForm)form;

            if (request.getAttribute("hdnSearchPageNo") != null) {
                auditTrailListForm
                .setHdnSearchPageNo((String)request.getAttribute("hdnSearchPageNo"));
                // To return to the previous list page after create modify.
                logger
                .debug("***page no in list action afetr create***" + auditTrailListForm
                             .getHdnSearchPageNo());
            }

            if (request.getParameter("cboSearchByActionType") != null &&
                ((String)request.getParameter("cboSearchByActionType")).trim()
                .length() != 0) {
                searchByActionType =
                    request.getParameter("cboSearchByActionType");
                auditTrailListForm
                .setCboSearchByActionType(new String[] { searchByActionType });
                auditTrailListForm
                .setActionType(auditTrailListForm.getCboSearchByActionType()[0]);
            } else {
                auditTrailListForm.setActionType(SchedulerConstants.ALL_JOBS);
            }

            auditTrails =
                AuditTrail.getAuditTrails(auditTrailListForm, conn, numberOfRecords);

            logger
            .debug("***page number*** " + auditTrailListForm.getHdnSearchPageNo());
            //auditTrailListForm.setTimezone(TimeZone.getDefault().getID());
            auditTrailListForm.setChkSelect(sArray);

            request.setAttribute(mapping.getAttribute(), auditTrailListForm);
            request.setAttribute("auditTrails", auditTrails);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("------Exiting AuditTrailListAction------");
        }
        return mapping.findForward("success");
    }
}
