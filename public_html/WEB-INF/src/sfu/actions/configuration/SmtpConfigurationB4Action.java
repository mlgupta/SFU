package sfu.actions.configuration;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import java.io.IOException;

import javax.servlet.ServletException;

import sfu.actionforms.configuration.SmtpConfigurationForm;

import sfu.beans.configuration.SmtpConfig;

public class SmtpConfigurationB4Action extends Action {
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
        ServletContext context = null;
        String xmlFilePrefix = null;

        try {
            logger.info("------Entering SmtpConfigurationB4Action------");

            context = servlet.getServletContext();
            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");

            SmtpConfig smtpConfig = new SmtpConfig(xmlFilePrefix);
            SmtpConfigurationForm smtpConfigurationForm =
                new SmtpConfigurationForm();
            smtpConfigurationForm.setIpName(smtpConfig.getIPName());
            smtpConfigurationForm.setPort(smtpConfig.getPort());
            request
            .setAttribute("SmtpConfigurationForm", smtpConfigurationForm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("------Exiting SmtpConfigurationB4Action------");
        }
        return mapping.findForward("success");
    }
}
