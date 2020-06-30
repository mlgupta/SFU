package sfu.actions.configuration;

import java.io.BufferedReader;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;

import javax.servlet.ServletException;

import javax.servlet.http.HttpSession;

import sfu.actionforms.configuration.ScannerConfigurationForm;

import sfu.beans.configuration.ScannerConfig;
import sfu.beans.configuration.ScannerOptionParser;
import sfu.beans.scan.ScannerInfoBean;
import sfu.beans.scan.ScannerOptionsOperation;

public class ScannerConfigurationB4Action extends Action {
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
    String propFilePath = null;
    ArrayList scannerList=null;
    ArrayList scannerOptions=null;
    ArrayList scannerOptionsFormFile=null;
    HttpSession httpSession=null;
    

    try {
      logger.info("------Entering ScannerConfigurationB4Action------");

      context = servlet.getServletContext();
      xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");
      httpSession=request.getSession(false);
      ScannerConfigurationForm scannerConfigurationForm =
        new ScannerConfigurationForm();
      
      scannerConfigurationForm.setHdnOptionType("");
      if(request.getAttribute("optionType")!=null){
        if(((String)request.getAttribute("optionType")).equals("scannerSpecificOption")){
          scannerConfigurationForm.setHdnOptionType("scannerSpecificOption");
        }else if(((String)request.getAttribute("optionType")).equals("commonOption")){
          scannerConfigurationForm.setHdnOptionType("commonOption");
        }        
      }
      
      propFilePath=(String)servlet.getServletContext().getAttribute("contextPath");
      scannerList=getScannerList();
      if(scannerList.size()!=0){//If any scanner is connected.
        ScannerInfoBean scannerInfoBean=(ScannerInfoBean)scannerList.get(0);
        
        //get the options for the scanner device connected.
        scannerOptions=ScannerOptionParser.getOptions(scannerInfoBean.getDevice());
        
        //get the already set scanner options from the property file.
        scannerOptionsFormFile=ScannerOptionsOperation.getScannerOptions(propFilePath);
      }
      //scannerOptions=ScannerOptionsOperation.getScannerOptions(propFilePath);
      
      ScannerConfig scannerConfig = new ScannerConfig(xmlFilePrefix);
      
      scannerConfigurationForm
      .setCboScanningDevice(new String[] { scannerConfig.getScanningDevice() });
      scannerConfigurationForm
      .setCboDpi(new String[] { scannerConfig.getDPI() });
      scannerConfigurationForm
      .setCboMode(new String[] { scannerConfig.getMode() });
      scannerConfigurationForm
      .setCboOutputFormat(new String[] { scannerConfig.getOutputFormat() });
      scannerConfigurationForm.setFleJobFolder(scannerConfig.getJobFolder());
      if (scannerConfig.getColorEnable().equals("yes")) {
        scannerConfigurationForm.setChkColorEnable("on");
      }
      request
      .setAttribute("ScannerConfigurationForm", scannerConfigurationForm);
      request.setAttribute("scannerList",scannerList);
      httpSession.setAttribute("scannerOptions",scannerOptions);
      //request.setAttribute("scannerOptions",scannerOptions);
      request.setAttribute("scannerOptionsFormFile",scannerOptionsFormFile);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      logger.info("------Exiting ScannerConfigurationB4Action------");
    }
    return mapping.findForward("success");
  }

  private ArrayList getScannerList() throws IOException, InterruptedException {
    ArrayList scannerList=null;
    Process proc;
    String[] scannerInfoArray=null;
    final String SPLITTER="splitter";
    try {
      scannerList=new ArrayList();
      Runtime rt=Runtime.getRuntime();
      String cmd="scanimage -f \"%d "+SPLITTER+" %m\"";
      logger.debug("Executing: "+cmd);
      proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      proc.waitFor();
      InputStreamReader isr = new InputStreamReader(proc.getInputStream());
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null) {
        ScannerInfoBean scannerInfoBean=new ScannerInfoBean();
        
        logger.debug("Scanner" + line);
        
        scannerInfoArray=line.split(SPLITTER);
        
        scannerInfoBean.setDevice(scannerInfoArray[0].trim());
        scannerInfoBean.setModel(scannerInfoArray[1].trim());
        
        scannerList.add(scannerInfoBean);
      }
    }
    catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
    return scannerList;
  }
}
