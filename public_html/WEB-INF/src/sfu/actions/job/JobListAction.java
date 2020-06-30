package sfu.actions.job;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;

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

import org.quartz.Scheduler;

import sfu.actionforms.job.JobListForm;

import sfu.beans.scheduler.DateHelper;
import sfu.beans.scheduler.SchedulerConstants;
import sfu.beans.scheduler.SearchJobs;
import sfu.beans.user.UserProfile;


/**
 * @purpose To list all the jobs in the scheduler.
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModifiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobListAction extends Action {
  private static String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

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
    logger.info("Fetching Job List ...");
    HttpSession httpSession = null;
    Scheduler jobScheduler = null;

    int numberOfRecords;
    int pageNumber = 1;
    int pageCount = 0;
    String searchByUserName = null;
    String searchByJobType = "ALL";
    String createFromDate = null;
    Date dCreateFromDate = null;
    String createToDate = null;
    Date dCreateToDate = null;
    String dispatchFromDate = null;
    Date dDispatchFromDate = null;
    String dispatchToDate = null;
    Date dDispatchToDate = null;
    ArrayList jobs = new ArrayList();
    JobListForm jobListForm = null;
    ActionErrors errors = new ActionErrors();
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering JobListAction------");
      logger.debug("Initializing Variable ...");
      httpSession = request.getSession(false);

      UserProfile userProfile =
        (UserProfile)httpSession.getAttribute("userProfile");
      numberOfRecords = Integer.parseInt(userProfile.getNumberOfRecords());

      if (httpSession.getAttribute("pageNumber") != null) {
        pageNumber =
          Integer.parseInt(httpSession.getAttribute("pageNumber").toString());
        httpSession.removeAttribute("pageNumber");
      }

      if (httpSession.getAttribute("messages") != null) {
        logger.debug("Saving action message in request stream");
        saveMessages(request,
                     (ActionMessages)httpSession.getAttribute("messages"));
        httpSession.removeAttribute("messages");
      }
      if (httpSession.getAttribute("errors") != null) {
        logger.debug("Saving action errors in request stream");
        saveErrors(request, (ActionErrors)httpSession.getAttribute("errors"));
        httpSession.removeAttribute("errors");
      }
      jobListForm = (JobListForm)form;
      ServletContext context = httpSession.getServletContext();
      jobScheduler = (Scheduler)context.getAttribute("scheduler");

      ActionMessage msg = null;
      if (jobScheduler.isShutdown()) {
        jobListForm.setIsSchedulerStopped("true");
        msg = new ActionMessage("scheduler.start");
        messages.add("message1", msg);
        saveMessages(request, messages);

      } else {
        jobListForm.setIsSchedulerStopped("false");
        if (httpSession.getAttribute("txtSearchByUserName") != null) {
          searchByUserName =
            (String)httpSession.getAttribute("txtSearchByUserName");
          logger.debug("searchByUserName***: " + searchByUserName);
          httpSession.removeAttribute("txtSearchByUserName");
          jobListForm.setTxtSearchByUserName(searchByUserName);
        } else {
          jobListForm.setTxtSearchByUserName("");
        }
        logger
        .debug("cboSearchByJobType: " + (String)httpSession.getAttribute("cboSearchByJobType"));

        if (httpSession.getAttribute("cboSearchByJobType") != null &&
            ((String)httpSession.getAttribute("cboSearchByJobType")).trim()
            .length() != 0) {
          searchByJobType =
            (String)httpSession.getAttribute("cboSearchByJobType");
          httpSession.removeAttribute("cboSearchByJobType");
          jobListForm.setCboSearchByJobType(new String[] { searchByJobType });
          jobListForm.setJobType(jobListForm.getCboSearchByJobType()[0]);
        } else {
          jobListForm.setJobType(SchedulerConstants.ALL_JOBS);
        }

        if (httpSession.getAttribute("txtCreateFromDate") != null) {
          createFromDate =
            (String)httpSession.getAttribute("txtCreateFromDate");
          dCreateFromDate = DateHelper.parse(createFromDate, DATE_FORMAT);
          httpSession.removeAttribute("txtCreateFromDate");
          jobListForm.setTxtCreateFromDate(createFromDate);
        } else {
          jobListForm.setTxtCreateFromDate("");
        }

        if (httpSession.getAttribute("txtCreateToDate") != null) {
          createToDate = (String)httpSession.getAttribute("txtCreateToDate");
          dCreateToDate = DateHelper.parse(createToDate, DATE_FORMAT);
          httpSession.removeAttribute("txtCreateToDate");
          jobListForm.setTxtCreateToDate(createToDate);
        } else {
          jobListForm.setTxtCreateToDate("");
        }

        if (httpSession.getAttribute("txtDispatchFromDate") != null) {
          dispatchFromDate =
            (String)httpSession.getAttribute("txtDispatchFromDate");
          dDispatchFromDate = DateHelper.parse(dispatchFromDate, DATE_FORMAT);
          httpSession.removeAttribute("txtDispatchFromDate");
          jobListForm.setTxtDispatchFromDate(dispatchFromDate);
        } else {
          jobListForm.setTxtDispatchFromDate("");
        }

        if (httpSession.getAttribute("txtDispatchToDate") != null) {
          dispatchToDate =
            (String)httpSession.getAttribute("txtDispatchToDate");
          dDispatchToDate = DateHelper.parse(dispatchToDate, DATE_FORMAT);
          httpSession.removeAttribute("txtDispatchToDate");
          jobListForm.setTxtDispatchToDate(dispatchToDate);
        } else {
          jobListForm.setTxtDispatchToDate("");
        }

        System.out.println("Time when search started " + new Date());
        SearchJobs searchJobs = new SearchJobs();
        jobs =
          searchJobs.findJobs(jobScheduler, new String[] { "executionTime" },
                                   searchByUserName, searchByJobType,
                                   dCreateFromDate, dCreateToDate,
                                   dDispatchFromDate, dDispatchToDate,
                                   pageNumber, numberOfRecords);
        pageCount = searchJobs.pageCount;
        if (pageNumber > pageCount) {
          pageNumber = pageCount;
        }
      }

      logger.debug("SEARCH JOB TYPE: " + searchByJobType);
      jobListForm.setTxtPageCount(new String().valueOf(pageCount));
      logger.debug("pageCount : " + pageCount);
      jobListForm.setTxtPageNo(new String().valueOf(pageNumber));
      logger.debug("pageNumber : " + pageNumber);
      request.setAttribute("jobs", jobs);
      request.setAttribute("JobListForm", jobListForm);
      System.out.println(jobs);
    } catch (Exception e) {
      logger.error("Fetching Job List Aborted");
      e.printStackTrace();
      ActionError editError = new ActionError("errors.catchall", e);
      errors.add(ActionErrors.GLOBAL_ERROR, editError);
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
    } finally {
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      logger.info("------Exiting JobListAction------");
    }
    if (!errors.isEmpty()) {
      saveErrors(request, errors);
      return (mapping.getInputForward());
    }
    logger.info("Number of records fetched : " + jobs.size());
    logger.info("Fetching Job List Complete");

    return mapping.findForward("success");
  }
}
