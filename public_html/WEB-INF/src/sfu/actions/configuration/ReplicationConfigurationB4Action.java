package sfu.actions.configuration;

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

import sfu.actionforms.configuration.ReplicationConfigurationForm;

import sfu.beans.configuration.ReplicationConfig;

public class ReplicationConfigurationB4Action extends Action {
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
            logger
            .info("------Entering ReplicationConfigurationB4Action------");

            context = servlet.getServletContext();
            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");

            ReplicationConfig replicationConfig =
                new ReplicationConfig(xmlFilePrefix);
            ReplicationConfigurationForm replicationConfigurationForm =
                new ReplicationConfigurationForm();
            replicationConfigurationForm
            .setTxtParentFolder(replicationConfig.getParentFolder());
            replicationConfigurationForm
            .setTxtReplicationInterval(replicationConfig
                                                                   .getReplicationInterval());
            replicationConfigurationForm
            .setTxtTimeout(replicationConfig.getTimeout());
            replicationConfigurationForm
            .setCboReplicationInterval(new String[] { replicationConfig
                                                                                  .getIntervalUnit() });
            request
            .setAttribute("ReplicationConfigurationForm", replicationConfigurationForm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger
            .info("------Exiting ReplicationConfigurationB4Action------");
        }
        return mapping.findForward("success");
    }
}
