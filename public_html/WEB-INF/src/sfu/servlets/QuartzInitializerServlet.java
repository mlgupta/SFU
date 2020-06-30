package sfu.servlets;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import sfu.beans.scheduler.ReplicateJob;
import sfu.beans.scheduler.SchedulerConstants;


/**
 *	Purpose:  A Servlet that is used to initialize Quartz Scheduler, if configured as a
 *            load-on-startup servlet in a web application.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class QuartzInitializerServlet extends HttpServlet {
  Logger logger = Logger.getLogger("SfuLogger");

  public void init(ServletConfig cfg) throws javax.servlet.ServletException {
    logger.info("Entering init() method");
    super.init(cfg);
    String prefix = cfg.getServletContext().getRealPath("/");
    String quartzFile = getInitParameter("quartz-init-file");
//    String isMailJobExecuting = "false";
//    String isUploadJobExecuting = "false";
//    String isFaxJobExecuting = "false";

    logger
    .info("Quartz Initializer Servlet loaded, initializing Scheduler...");

    try {

      String schedulerPath = prefix + quartzFile;
      StdSchedulerFactory stFact = new StdSchedulerFactory(schedulerPath);
      Scheduler sched = stFact.getScheduler();
      sched.start();
      cfg.getServletContext().setAttribute("scheduler", sched);
      cfg.getServletContext().setAttribute("schedulerPath", schedulerPath);
      sched.getContext().put("contextPath", prefix);

      sched.getContext()
      .put("xmlFilePrefix", (String)cfg.getServletContext().getAttribute("xmlFilePrefix"));

      sched.getContext().put("isMailJobExecuting", "false");
      sched.getContext().put("isUploadJobExecuting", "false");
      sched.getContext().put("isFaxJobExecuting", "false");

      logger.info("Quartz Initialized and Started successfully");
      Trigger replicateTrigger =
        sched.getTrigger(SchedulerConstants.REPLICATE_TRIGGER_NAME,
                                                  sched.DEFAULT_GROUP);
      if (replicateTrigger == null) {
        logger.info("No Replicate Job Scheduled.");
        logger.info("Scheduling Replicate Job.");
        replicateTrigger =
          new SimpleTrigger(SchedulerConstants.REPLICATE_TRIGGER_NAME,
                                             sched.DEFAULT_GROUP, new Date(),
                                             null,
                                             SimpleTrigger.REPEAT_INDEFINITELY,
                                             Long
                                             .parseLong(SchedulerConstants.REPLICATE_PERIOD));
        JobDetail replicateDetail =
          new JobDetail(SchedulerConstants.REPLICATE_JOB_NAME,
                                                  sched.DEFAULT_GROUP,
                                                  ReplicateJob.class, false,
                                                  false, true);
        sched.scheduleJob(replicateDetail, replicateTrigger);
        logger.info("Replicate Job Scheduled");
      }

    } catch (Exception e) {
      e.printStackTrace();
      logger.info("Quartz Scheduler failed to initialize: " + e.toString());
      throw new ServletException(e);
    }
  }

  public void destroy() {
    try {
      Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
      if (sched != null)
        sched.shutdown();
    } catch (Exception e) {
      logger
      .error("Quartz Scheduler failed to shutdown cleanly: " + e.toString());
      e.printStackTrace();
    }

    logger.info("Quartz Scheduler successful shutdown.");
  }

  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException,
                                                                                      IOException {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                                                                                     IOException {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }

}
