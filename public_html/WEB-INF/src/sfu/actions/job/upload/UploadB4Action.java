package sfu.actions.job.upload;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sfu.actionforms.upload.DocUploadForm;

import sfu.beans.configuration.ReplicationConfig;
import sfu.beans.configuration.ScannerConfig;
import sfu.beans.user.UserProfile;


//import sfu.beans.job.MailJob;

public class UploadB4Action extends Action {
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
        DocUploadForm docUploadForm = null;
        UserProfile userProfile;
        try {
            logger.info("------Entering UploadB4Action------");

            docUploadForm = new DocUploadForm();
            HttpSession httpSession = request.getSession(false);
            userProfile = (UserProfile)httpSession.getAttribute("userProfile");
            context = servlet.getServletContext();

            xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
            ReplicationConfig replicationConfig =
                new ReplicationConfig(xmlFilePrefix);
            ScannerConfig scannerConfig = new ScannerConfig(xmlFilePrefix);

            if (userProfile.getUserDirectory() != null &&
                userProfile.getUserDirectory().trim().length() != 0) {
                docUploadForm
                .setTxtDestinationFolderPath(userProfile.getUserDirectory());
            } else {
                docUploadForm
                .setTxtDestinationFolderPath(replicationConfig.getParentFolder());
            }

            docUploadForm
            .setHdnOutputFormat(scannerConfig.getOutputFormat().toLowerCase());
            request.setAttribute(mapping.getAttribute(), docUploadForm);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            logger.info("------Exiting UploadB4Action------");
        }
        return mapping.findForward("success");
    }
}
