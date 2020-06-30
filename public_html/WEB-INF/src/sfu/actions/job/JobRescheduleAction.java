
package sfu.actions.job;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
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
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import sfu.beans.scheduler.DateHelper;
import sfu.beans.scheduler.SchedulerConstants;

/**
 * @purpose To reschedule any job which has not been completed yet.
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModifiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobRescheduleAction extends Action {

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

    //Locale locale = getLocale(request);
    //UserInfo userInfo = null;
    HttpSession httpSession = null;
    Scheduler jobScheduler = null;
    String creator = null;
    Trigger newTrigger = null;
    String newTriggerName = null;
    String oldTriggerName = null;
    String triggerType = null;
    String jobName = null;
    String jobType = null;
    int day;
    int month;
    int year;
    int hours;
    int minutes;
    int seconds;
    //String timezone;
    ActionErrors errors = new ActionErrors();
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering JobRescheduleAction------");
      if(isCancelled(request)){
        logger.debug("Rescheduling of job canceled");
        ActionMessage msg =
          new ActionMessage("job.reschedule.cancel", jobName, jobType);
        messages.add("message1",msg);
        saveMessages(request,messages);
        return mapping.findForward("success");
      }
      oldTriggerName =
        (String)PropertyUtils.getSimpleProperty(form, "triggerName");
      triggerType =
        (String)PropertyUtils.getSimpleProperty(form, "triggerType");
      creator = (String)PropertyUtils.getSimpleProperty(form, "txtUser");
      jobName = (String)PropertyUtils.getSimpleProperty(form, "txtJobName");
      jobType = (String)PropertyUtils.getSimpleProperty(form, "txtJobType");
      day =
        Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "day"))
                             .trim());
      month =
        Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "month"))
                               .trim());
      year =
        Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "year"))
                              .trim());
      hours =
        Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "hours"))
                               .trim());
      minutes =
        Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "minutes"))
                                 .trim());
      seconds =
        Integer.parseInt(((String)PropertyUtils.getSimpleProperty(form, "seconds"))
                                 .trim());

      logger.debug("hour: " + hours);
      logger.debug("minutes: " + minutes);
      logger.debug("seconds: " + seconds);

      //timezone=((String)PropertyUtils.getSimpleProperty(form, "timezone")).trim();
      httpSession = request.getSession(false);
      ServletContext context = httpSession.getServletContext();
      if (context.getAttribute("scheduler") != null) {
        jobScheduler = (Scheduler)context.getAttribute("scheduler");
      } else {

      }
      logger.info("Scheduler Acquired");
      //TimeZone userTimeZone=TimeZone.getTimeZone(timezone);
      //Date startTime=DateHelper.getDate(year,month,day,hours,minutes,seconds,userTimeZone);
      Date startTime =
        DateHelper.getDate(year, month, day, hours, minutes, seconds);
      JobDataMap jobData =
        jobScheduler.getJobDetail(jobName, jobType).getJobDataMap();
      logger.info("JobData Acquired");
      Date dateOfSubmission =
        (Date)jobData.get(SchedulerConstants.CREATE_TIME);
      logger.info("Job Create Time" + dateOfSubmission);
      // String namePrefix=creator+DateHelper.format(dateOfSubmission,"HH:mm:ss--yyyy-MM-dd-z");            
      String namePrefix =
        creator + DateHelper.format(new Date(), "HH:mm:ss--yyyy-MM-dd-z");
      newTriggerName = SchedulerConstants.TRIGGER_NAME_SUFFIX + namePrefix;
      if (startTime.compareTo(new Date()) <= 0) {
        newTrigger = new SimpleTrigger(newTriggerName, triggerType);
      } else {

        newTrigger = new SimpleTrigger(newTriggerName, triggerType, startTime);
      }
      jobData.put(SchedulerConstants.EXECUTION_TIME, startTime);
      JobDetail jobDetail = jobScheduler.getJobDetail(jobName, jobType);
      jobDetail.setJobDataMap(jobData);
      newTrigger.setJobGroup(jobType);
      newTrigger.setJobName(jobName);
      jobScheduler.deleteJob(jobName, jobType);
      jobScheduler.scheduleJob(jobDetail, newTrigger);

      ActionMessage msg =
        new ActionMessage("job.reschedule.ok", jobName, jobType);
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
      logger.info("------Exiting JobRescheduleAction------");
    }
    if (!errors.isEmpty()) {
      saveErrors(request, errors);
      return (mapping.getInputForward());
    }

    return mapping.findForward("success");
  }
}
