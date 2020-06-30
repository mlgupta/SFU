package sfu.actions.auditTrail;

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
import org.apache.struts.action.ActionMessages;

import sfu.actionforms.auditTrail.AuditTrailListForm;

import sfu.beans.auditTrail.AuditTrail;

public class AuditTrailDeleteAction extends Action {
    Logger logger = Logger.getLogger("sfuLogger");

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
        Connection conn = null;
        String[] performedDateArray = null;
        String performedDateString = "";
        AuditTrailListForm auditTrailListForm = null;

        ActionMessages messages = new ActionMessages();

        try {
            logger.info("------Entering AuditTrailDeleteAction------");
            auditTrailListForm = (AuditTrailListForm)form;

            performedDateArray = auditTrailListForm.getChkSelect();

            logger.debug("performedDate: " + performedDateArray);
            System.out.println("performedDate: " + performedDateArray);
            for (int i = 0; i < performedDateArray.length; i++) {
                performedDateString =
                    performedDateString + "'" + performedDateArray[i] + "',";
            }
            performedDateString =
                performedDateString.substring(0, performedDateString.length() -
                                                                1);
            System.out.println("performedDateString: " + performedDateString);
            ServletContext context = servlet.getServletContext();
            conn = (Connection)context.getAttribute("conn");
            AuditTrail.deleteAuditTrail(performedDateString, conn);

        } catch (SQLException e) {
            //logger.debug(ErrorConstants.REFERED.getErrorValue());
            //logger.debug(Integer.toString(e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue())));
            //if((e.getMessage().indexOf(ErrorConstants.REFERED.getErrorValue()))>-1){
            //  try{
            //    outletId=Operations.getOutletId(outletTblPk,dataSource);
            //  }catch(Exception ex){
            //    logger.error("Error getOutletId"+ex);
            //  }

            //ActionMessage msg=new ActionMessage("msg.Refered",outletId);
            //messages.add("message1" ,msg);
            //saveMessages(request,messages);
            //}
            //saveMessages(request,messages);
            logger.error("Error Code:" + e.getErrorCode());
            logger.error("exception message:" + e.getMessage());
            logger.error("sql state : " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("exception message:" + e.getMessage());
            e.printStackTrace();
        } finally {
            logger.info("------Exiting AuditTrailDeleteAction------");
        }

        return mapping.findForward("success");
    }
}
