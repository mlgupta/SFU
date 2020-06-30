package sfu.actions.configuration;

import java.io.File;

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

import sfu.actionforms.configuration.ScannerConfigurationForm;

import sfu.beans.configuration.ScannerConfig;
import sfu.beans.scan.ScannerOptionsOperation;

public class ScannerConfigurationAction extends Action {
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
    String cboScanningDevice = "";
    String cboScannerModel="";
    String cboDpi = "";
    String cboMode = "";
    String cboOutputFormat = "";
    String chkColorEnable = "";
    String fleJobFolder = "";
    
    
    
    ActionMessages messages = new ActionMessages();

    try {
      logger.info("------Entering ScannerConfigurationAction------");
      
      request.setAttribute("optionType","commonOption");
      if (request.getParameter("cboScanningDevice") != null) {
        cboScanningDevice = request.getParameter("cboScanningDevice");
      }
      
      if (request.getParameter("cboScannerModel") != null) {
        cboScannerModel = request.getParameter("cboScannerModel");
      }
      
      if (request.getParameter("cboDpi") != null) {
        cboDpi = request.getParameter("cboDpi");
      }

      if (request.getParameter("cboMode") != null) {
        cboMode = request.getParameter("cboMode");
      }

      if (request.getParameter("cboOutputFormat") != null) {
        cboOutputFormat = request.getParameter("cboOutputFormat");
      }

      if (request.getParameter("chkColorEnable") != null) {
        chkColorEnable = "yes";
      }
      if (request.getParameter("fleJobFolder") != null) {
        fleJobFolder = request.getParameter("fleJobFolder");
        File td = new File(fleJobFolder);
        if (td.exists()) {
          logger.debug("Folder Already Exists.");
          logger.debug("Path: " + td.getAbsolutePath());
        } else {
          logger
          .debug("Creating New Folder For Job... '" + fleJobFolder + "'");
          if (td.mkdirs()) {
            logger
            .debug(" Folder '" + fleJobFolder + "' Created Successfully.");
            logger.debug("Path: " + td.getAbsolutePath());
          } else {
            logger
            .debug(" Could Not Create Folder For Job: '" + fleJobFolder + "'");
          }
        }
      }
      ScannerConfig scannerConfig =
        new ScannerConfig((String)servlet.getServletContext()
                                                      .getAttribute("xmlFilePrefix"));
      scannerConfig
      .writeNewSettings(cboScanningDevice,cboScannerModel,cboDpi, cboMode, cboOutputFormat, chkColorEnable,
                                     fleJobFolder);
      
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
      logger.info("------Exiting ScannerConfigurationAction------");
    }
    return mapping.findForward("success");
  }
}
