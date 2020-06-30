package sfu.actions.configuration;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;

import sfu.beans.configuration.ReplicationConfig;
import sfu.beans.scheduler.SchedulerConstants;

public class ReplicationConfigurationAction extends Action {
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
    String txtReplicationInterval = "";
    String cboReplicationInterval = "";
    String txtParentFolder = "";
    String txtTimeout = "";
    long replicationIntervalInMillis = 0;
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering ReplicationConfigurationAction------");
      if (request.getParameter("txtReplicationInterval") != null) {
        txtReplicationInterval =
          request.getParameter("txtReplicationInterval");
      }

      if (request.getParameter("cboReplicationInterval") != null) {
        cboReplicationInterval =
          request.getParameter("cboReplicationInterval");
      }

      if (request.getParameter("txtParentFolder") != null) {
        txtParentFolder = request.getParameter("txtParentFolder");
      }

      if (request.getParameter("txtTimeout") != null) {
        txtTimeout = request.getParameter("txtTimeout");
      }

      ReplicationConfig replicationConfig =
        new ReplicationConfig((String)servlet.getServletContext()
                                                                  .getAttribute("xmlFilePrefix"));
      replicationConfig
      .writeNewSettings(txtReplicationInterval, cboReplicationInterval,
                                         txtParentFolder, txtTimeout);
      if (cboReplicationInterval.equals("hour")) {
        replicationIntervalInMillis =
          Long.parseLong(txtReplicationInterval) * 60 * 60 * 1000;
      } else if (cboReplicationInterval.equals("minute")) {
        replicationIntervalInMillis =
          Long.parseLong(txtReplicationInterval) * 60 * 1000;
      }
      Scheduler sched =
        (Scheduler)(request.getSession().getServletContext().getAttribute("scheduler"));
      logger.info("Rescheduling Replicate Job.");
      SimpleTrigger replicateTrigger =
        new SimpleTrigger(SchedulerConstants.REPLICATE_TRIGGER_NAME,
                                                         sched.DEFAULT_GROUP,
                                                         new Date(), null,
                                                         SimpleTrigger
                                                         .REPEAT_INDEFINITELY,
                                                         replicationIntervalInMillis);
      replicateTrigger.setJobName(SchedulerConstants.REPLICATE_JOB_NAME);
      replicateTrigger.setJobGroup(sched.DEFAULT_GROUP);

      sched
      .rescheduleJob(SchedulerConstants.REPLICATE_TRIGGER_NAME, sched.DEFAULT_GROUP,
                          replicateTrigger);
      logger.info("Replicate Job rescheduled successfully");

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException", e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
    } finally {
      if (!messages.isEmpty()) {
        saveMessages(request, messages);
      }
      logger.info("------Exiting ReplicationConfigurationAction------");
    }
    return mapping.findForward("success");
  }
}
