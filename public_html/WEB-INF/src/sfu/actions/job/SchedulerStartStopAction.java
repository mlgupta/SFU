/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: SchedulerStartStopAction.java,v 20040220.6 2005/07/14 06:42:00 suved Exp $
 *****************************************************************************
 */
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

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

//Java API
//Servlet API
//Struts API


public class SchedulerStartStopAction extends Action {

  private static String START_ACTION = "Start";

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

    //        Locale locale = getLocale(request);
    //UserInfo userInfo = null;
    HttpSession httpSession = null;
    //        RescheduleForm rescheduleForm = new RescheduleForm();
    Scheduler jobScheduler = null;
    String action = null;
    ActionErrors errors = new ActionErrors();
    ActionMessages messages = new ActionMessages();
    try {
      logger.info("------Entering SchedulerStartStopAction------");
      logger.debug("Initializing Variable ...");
      httpSession = request.getSession(false);
      if (httpSession.getAttribute("action") != null) {
        action = (String)httpSession.getAttribute("action");
        httpSession.removeAttribute("action");
        logger.debug("***action: " + action);
      }
      ServletContext context = httpSession.getServletContext();
      if (context.getAttribute("scheduler") != null) {
        jobScheduler = (Scheduler)context.getAttribute("scheduler");
      } else {

      }
      ActionMessage msg = null;
      if (action.equals(START_ACTION)) {
        context.removeAttribute("scheduler");
        String schedulerPath = (String)context.getAttribute("schedulerPath");
        StdSchedulerFactory stFact = new StdSchedulerFactory(schedulerPath);
        Scheduler sched = stFact.getScheduler();
        sched.start();
        context.setAttribute("scheduler", sched);
        msg = new ActionMessage("scheduler.start.ok");
        logger.debug("Scheduler Started successfully ... ");
      } else {
        jobScheduler.shutdown();
        msg = new ActionMessage("scheduler.stop.ok");
        logger.debug("Scheduler stopped successfully ... ");
      }


      messages.add("message1", msg);
      saveMessages(request, messages);
      httpSession.setAttribute("messages", messages);

    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      ActionError editError = new ActionError("errors.catchall", e);
      errors.add(ActionErrors.GLOBAL_ERROR, editError);
      ActionMessage msg = new ActionMessage("msg.JavaException",e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      logger.info("------Exiting SchedulerStartStopAction------");
    }
    if (!errors.isEmpty()) {
      httpSession.setAttribute("errors", errors);
      return mapping.findForward("success");
    }

    return mapping.findForward("success");
  }
}
