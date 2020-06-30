package sfu.actions.configuration;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;

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

import sfu.actionforms.configuration.ScannerConfigurationForm;

import sfu.beans.scan.ScannerInfoBean;
import sfu.beans.scan.ScannerOptionBean;
import sfu.beans.scan.ScannerOptionsOperation;


public class ScannerSpecificConfigurationAction extends Action {
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

    String txtOptionName = null;
    String txtOptionValue = null;
    String propFilePath = null;
    //String optionType = null;
    HttpSession httpSession = null;
    ArrayList optionsToAdd = null;
    ScannerOptionBean scannerOptionBean = null;
    ScannerOptionBean scannerOptionHelpBean = null;
    ScannerConfigurationForm scannerConfigurationForm = null;

    try {
      logger.info("------Entering ScannerSpecificConfigurationB4Action------");
      context = servlet.getServletContext();
      httpSession = request.getSession(false);
      request.setAttribute("optionType", "scannerSpecificOption");
      if (isCancelled(request)) {
        logger.debug("cancel pressed");
        return mapping.findForward("success");
      }
      propFilePath =
        (String)servlet.getServletContext().getAttribute("contextPath");

      scannerConfigurationForm = (ScannerConfigurationForm)form;

      propFilePath =
        (String)servlet.getServletContext().getAttribute("contextPath");

      //ScannerOptionsOperation.createScannerOptionsProperties(propFilePath);
      //
      ArrayList scannerOptions =
        (ArrayList)httpSession.getAttribute("scannerOptions");
      if (scannerOptions != null) {
        optionsToAdd = new ArrayList();
        for (int index = 0; index < scannerOptions.size(); index++) {
          scannerOptionHelpBean = (ScannerOptionBean)scannerOptions.get(index);

          String param = scannerOptionHelpBean.getOptionName();

          logger.debug("" + param);
          logger.debug(""+request.getParameter(param));
          
          txtOptionName = param;
          txtOptionValue = request.getParameter(param);
          if (request.getParameter("chk" + param) != null) {
            scannerOptionBean = new ScannerOptionBean();
            scannerOptionBean.setOptionName(txtOptionName);
            scannerOptionBean.setOptionValue(txtOptionValue);
            optionsToAdd.add(scannerOptionBean);
          }
        }
        ScannerOptionsOperation
        .createScannerOptionsProperties(propFilePath, optionsToAdd);


      }
      //
      /*
       Enumeration enParam=request.getParameterNames();
       while (enParam.hasMoreElements()){
         String param=(String)enParam.nextElement();
         
         logger.debug(""+param);
         logger.debug(request.getParameter(param));
         //logger.debug("check: "+request.getParameter("chk"+param));
          txtOptionName=param;
          txtOptionValue=request.getParameter(param);
         if(request.getParameter("chk"+param)!=null){
           ScannerOptionsOperation.addProperty(propFilePath,txtOptionName,txtOptionValue);           
         }else{
           ScannerOptionsOperation.removeProperty(propFilePath,txtOptionName);           
         }
       }
       */

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      logger.info("------Exiting ScannerSpecificConfigurationB4Action------");
      httpSession.removeAttribute("scannerOptions");
    }
    return mapping.findForward("success");
  }


}
