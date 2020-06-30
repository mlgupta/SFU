package sfu.actions.configuration;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import java.io.IOException;

import javax.servlet.ServletException;

import sfu.beans.configuration.PatchConfig;

public class PatchConfigurationAction extends Action {
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
        String txtLocalPatchFolder = "";
        String txtRemotePatchFolder = "";
        String txtPatchCheckInterval = "";
        String cboPatchCheckInterval = "";
        try {
            logger.info("------Entering PatchConfigurationAction------");
            if (request.getParameter("txtLocalPatchFolder") != null) {
                txtLocalPatchFolder =
                    request.getParameter("txtLocalPatchFolder");
            }

            if (request.getParameter("txtRemotePatchFolder") != null) {
                txtRemotePatchFolder =
                    request.getParameter("txtRemotePatchFolder");
            }

            if (request.getParameter("txtPatchCheckInterval") != null) {
                txtPatchCheckInterval =
                    request.getParameter("txtPatchCheckInterval");
            }

            if (request.getParameter("cboPatchCheckInterval") != null) {
                cboPatchCheckInterval =
                    request.getParameter("cboPatchCheckInterval");
            }

            PatchConfig patchConfig =
                new PatchConfig((String)servlet.getServletContext()
                                                      .getAttribute("xmlFilePrefix"));
            patchConfig
            .writeNewSettings(txtLocalPatchFolder, txtRemotePatchFolder,
                                         txtPatchCheckInterval,
                                         cboPatchCheckInterval);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("------Exiting PatchConfigurationAction------");
        }
        return mapping.findForward("success");
    }
}
