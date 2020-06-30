package sfu.actions.job;



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

/**
 * @purpose To looks for an "operation" parameter and uses it to find a forward in the mapping and then uses it.
 * @author Mishra Amit
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModifiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobRelayAction extends Action {
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
                               HttpServletResponse response) {
    String operation = null;
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering JobRelayAction------");
      operation = request.getParameter("operation");
      logger.debug("***operation***:" + operation);
      HttpSession httpSession = request.getSession(false);

      if (operation.equals("job_reschedule") ||
          operation.equals("job_reschedule_user") ||
          operation.equals("job_delete") || 
          operation.equals("job_delete_user") ||
          operation.equals("scheduler_start_stop") ||
          operation.equals("page_scheduler") ||
          operation.equals("page_scheduler_user") ||
          operation.equals("search_scheduler") ||
          operation.equals("search_scheduler_user") ||
          operation.equals("cancel_scheduler")) {
        httpSession
        .setAttribute("radSelect", request.getParameter("radSelect"));

        if (request.getParameter("txtSearchByUserName") != null &&
            !(request.getParameter("txtSearchByUserName")).equals("")) {
          httpSession
          .setAttribute("txtSearchByUserName", request.getParameter("txtSearchByUserName"));
        }

        if (request.getParameter("cboSearchByJobType") != null &&
            !(request.getParameter("cboSearchByJobType")).equals("")) {
          httpSession
          .setAttribute("cboSearchByJobType", request.getParameter("cboSearchByJobType"));
        }

        if (request.getParameter("txtCreateFromDate") != null &&
            !(request.getParameter("txtCreateFromDate")).equals("")) {
          httpSession
          .setAttribute("txtCreateFromDate", request.getParameter("txtCreateFromDate"));
        }

        if (request.getParameter("txtCreateToDate") != null &&
            !(request.getParameter("txtCreateToDate")).equals("")) {
          httpSession
          .setAttribute("txtCreateToDate", request.getParameter("txtCreateToDate"));
        }

        if (request.getParameter("txtDispatchFromDate") != null &&
            !(request.getParameter("txtDispatchFromDate")).equals("")) {
          httpSession
          .setAttribute("txtDispatchFromDate", request.getParameter("txtDispatchFromDate"));
        }

        if (request.getParameter("txtDispatchToDate") != null &&
            !(request.getParameter("txtDispatchToDate")).equals("")) {
          httpSession
          .setAttribute("txtDispatchToDate", request.getParameter("txtDispatchToDate"));
        }

        /*if(operation.equals("scheduler_start_stop")){
                if(request.getParameter("action")!=null && !((String)request.getParameter("action")).equals("")){
                    httpSession.setAttribute("action",request.getParameter("action"));
                    logger.debug("action: "+request.getParameter("action"));
                }
              }*/
        if (operation.equals("scheduler_start_stop")) {
          if (request.getParameter("hdnSchedulerStartStopOperation") != null &&
              !(request.getParameter("hdnSchedulerStartStopOperation"))
              .equals("")) {
            httpSession
            .setAttribute("action", request.getParameter("hdnSchedulerStartStopOperation"));
            logger
            .debug("action: " + request.getParameter("hdnSchedulerStartStopOperation"));
          }
        }


        httpSession
        .setAttribute("pageNumber", request.getParameter("txtPageNo"));

      }


      if (operation == null) {
        return new ActionForward("error");
      }
      if (mapping.findForward(operation) == null) {
        return new ActionForward("error");
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
    } finally {
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      logger.info("------Exiting JobRelayAction------");
    }
    return mapping.findForward(operation);
  }
}
