
package sfu.actions.job;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

import sfu.actionforms.job.JobListForm;

import sfu.beans.job.JobUtils;
import sfu.beans.scheduler.SchedulerConstants;

/**
 * @purpose To delete any job from the scheduler list.
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModifiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobDeleteAction extends Action {


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
    Logger logger = Logger.getLogger("SfuLogger");

    HttpSession httpSession = null;

    Scheduler jobScheduler = null;
    JobListForm jobListForm = null;
    ActionErrors errors = new ActionErrors();
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering JobDeleteAction------");
      logger.debug("Initializing Variable ...");
      jobListForm = (JobListForm)form;

      logger
      .debug("jobtype in jobDeleteAction: " + request.getParameter("jobType"));

      if (request.getParameter("jobType") != null) {
        jobListForm.setJobType(request.getParameter("jobType"));
      }
      httpSession = request.getSession(false);
      ServletContext context = httpSession.getServletContext();
      if (context.getAttribute("scheduler") != null) {
        jobScheduler = (Scheduler)context.getAttribute("scheduler");
      } else {
      }
      String jobWithGroupName = (String)httpSession.getAttribute("radSelect");
      String[] jobInf = new String(jobWithGroupName).split(" ");
      //
      JobDetail jobDetail = jobScheduler.getJobDetail(jobInf[1], jobInf[0]);
      JobDataMap jobDataMap = jobDetail.getJobDataMap();
      String parentFolder =
        jobDataMap.getString(SchedulerConstants.PARENT_FOLDER);
      String jobGroup = jobInf[0];
      logger.debug("Folder to be deleted: " + parentFolder);
      if (parentFolder != null) {
        JobUtils.freeJobFiles(parentFolder, jobGroup);
      }

      jobScheduler.deleteJob(jobInf[1], jobInf[0]);

      ActionMessage msg =
        new ActionMessage("job.delete.ok", jobInf[1], jobInf[0]);
      messages.add("message1", msg);
      httpSession.setAttribute("messages", messages);

    } catch (Exception e) {
      logger.info(e);
      ActionError editError = new ActionError("errors.catchall", e);
      errors.add(ActionErrors.GLOBAL_ERROR, editError);
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
    } finally {
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      logger.info("------Exiting JobDeleteAction------");
    }
    if (!errors.isEmpty()) {
      httpSession.setAttribute("errors", errors);
      return mapping.findForward("success");
    }

    return mapping.findForward("success");
  }
}
