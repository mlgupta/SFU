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

import sfu.beans.user.UserDelete;

public class UserDeleteAction extends Action {
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
    String userId = null;

    ActionMessages messages = new ActionMessages();

    try {
      logger.info("------Entering UserDeleteAction------");
      userId = request.getParameter("radSelect");
      ServletContext context = servlet.getServletContext();
      conn = (Connection)context.getAttribute("conn");
      logger.info("Entering Outlet Delete Action");
      UserDelete.deleteUser(userId, conn);

    } catch (SQLException e) {
      e.printStackTrace();
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
      logger.info("------Exiting UserDeleteAction------");
    }

    return mapping.findForward("success");
  }
}
