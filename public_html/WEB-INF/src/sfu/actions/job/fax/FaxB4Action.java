package sfu.actions.job.fax;

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

import sfu.actionforms.job.fax.FaxForm;

import sfu.beans.configuration.ReplicationConfig;
import sfu.beans.configuration.SmtpConfig;

public class FaxB4Action extends Action {
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
        FaxForm faxForm = null;

        try {
            logger.info("------Entering FaxB4Action------");
            faxForm = new FaxForm();

            context = servlet.getServletContext();
            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
            ReplicationConfig replicationConfig =
                new ReplicationConfig(xmlFilePrefix);
            SmtpConfig smtpConfig = new SmtpConfig(xmlFilePrefix);

            faxForm.setHdnOutputFormat("pdf");
            faxForm.setSmtpHost(smtpConfig.getIPName());

            request.setAttribute(mapping.getAttribute(), faxForm);
        } catch (Exception e) {

        } finally {
            logger.info("------Exiting FaxB4Action------");
        }
        return mapping.findForward("success");
    }
}
