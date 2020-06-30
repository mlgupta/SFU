package sfu.actions.job.mail;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sfu.actionforms.job.mail.MailForm;

import sfu.beans.configuration.ReplicationConfig;
import sfu.beans.configuration.ScannerConfig;
import sfu.beans.configuration.SmtpConfig;

public class MailB4Action extends Action {
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
        MailForm mailForm = null;

        try {
            logger.info("------Entering MailB4Action------");
            mailForm = new MailForm();

            context = servlet.getServletContext();
            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
            ReplicationConfig replicationConfig =
                new ReplicationConfig(xmlFilePrefix);
            ScannerConfig scannerConfig = new ScannerConfig(xmlFilePrefix);
            SmtpConfig smtpConfig = new SmtpConfig(xmlFilePrefix);

            mailForm
            .setHdnOutputFormat(scannerConfig.getOutputFormat().toLowerCase());
            mailForm.setSmtpHost(smtpConfig.getIPName());

            request.setAttribute(mapping.getAttribute(), mailForm);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            logger.info("------Exiting MailB4Action------");
        }
        return mapping.findForward("success");
    }
}
