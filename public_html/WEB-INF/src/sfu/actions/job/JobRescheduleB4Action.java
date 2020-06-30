
package sfu.actions.job;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import sfu.actionforms.job.RescheduleForm;

import sfu.beans.scheduler.DateHelper;
import sfu.beans.scheduler.SchedulerConstants;


/**
 * @purpose To fetch the previous scheduled date and to show it in the jsp.
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModifiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobRescheduleB4Action extends Action {



  private static String DATE_FORMAT = "MMM dd, yyyy HH:mm:ss";

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
    //Initialize logger
    Logger logger = Logger.getLogger("SfuLogger");

    //MessageResources messages = getResources(request);
    //UserInfo userInfo = null;
    HttpSession httpSession = null;
    RescheduleForm rescheduleForm = new RescheduleForm();
    Scheduler jobScheduler = null;
    Date createDate = null;
    Date executionDate = null;
    String creator = null;
    JobDataMap jobData = null;
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering JobRescheduleB4Action------");
      logger.debug("Initializing Variable ...");
      httpSession = request.getSession(false);
      ServletContext context = httpSession.getServletContext();
      if (context.getAttribute("scheduler") != null) {
        jobScheduler = (Scheduler)context.getAttribute("scheduler");
      } else {

      }
      String jobWithGroupName = (String)httpSession.getAttribute("radSelect");
      logger.debug("jobWithGroupName: "+jobWithGroupName);
      String[] jobInf = new String(jobWithGroupName).split(" ");
      Trigger trigger[] = jobScheduler.getTriggersOfJob(jobInf[1], jobInf[0]);
      if (trigger == null || trigger.length == 0) {
        ActionMessage msg = new ActionMessage("job.dispatch.reschedule");
        messages.add("message1", msg);
        saveMessages(request, messages);
        logger
        .debug("No Trigger Found : Switching to " + mapping.getInputForward()
                     .getClass());
        return (mapping.getInputForward());
      }
      JobDetail jobDetail = jobScheduler.getJobDetail(jobInf[1], jobInf[0]);
      jobData = jobDetail.getJobDataMap();
      createDate = (Date)jobData.get(SchedulerConstants.CREATE_TIME);
      executionDate = trigger[0].getStartTime();
      creator = jobData.getString(SchedulerConstants.JOB_CREATOR);
      rescheduleForm.setTriggerName(trigger[0].getName());

      logger.debug("**** TriggerName: " + rescheduleForm.getTriggerName());

      rescheduleForm.setTriggerType(trigger[0].getGroup());
      rescheduleForm.setTxtJobName(jobInf[1]);
      rescheduleForm.setTxtJobType(jobInf[0]);
      rescheduleForm.setTxtUser(creator);
      rescheduleForm
      .setTxtCreateDate(DateHelper.format(createDate, DATE_FORMAT));

      logger.debug("createDate: " + rescheduleForm.getTxtCreateDate());

      rescheduleForm
      .setYear(new String().valueOf(DateHelper.getYear(executionDate)));
      rescheduleForm
      .setMonth(new String().valueOf(DateHelper.getMonth(executionDate)));
      rescheduleForm
      .setDay(new String().valueOf(DateHelper.getDay(executionDate)));
      rescheduleForm
      .setHours(new String().valueOf(DateHelper.getHour(executionDate)));
      rescheduleForm
      .setMinutes(new String().valueOf(DateHelper.getMinute(executionDate)));
      rescheduleForm
      .setSeconds(new String().valueOf(DateHelper.getSecond(executionDate)));
      //rescheduleForm.setTimezone(TimeZone.getDefault().getID());
      logger.debug("executionDate: " + executionDate);
      logger
      .debug("executionDate Month : " + DateHelper.getMonth(executionDate));
      logger
      .debug("executionDate Hour : " + DateHelper.getHour(executionDate));
      logger
      .debug("executionDate Minute : " + DateHelper.getMinute(executionDate));
      logger
      .debug("executionDate Seconds : " + DateHelper.getSecond(executionDate));
      request.setAttribute(mapping.getAttribute(), rescheduleForm);
    } catch (Exception e) {
      logger.error(e.toString());
      logger.error(e.getMessage());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("errors.catchall", e);
      messages.add("messageAll", msg);
    } finally {
      logger.info("------Exiting JobRescheduleB4Action------");
    }
    if (!messages.isEmpty()) {
      saveMessages(request, messages);
      return (mapping.getInputForward());
    }

    return mapping.findForward("success");
  }
}
