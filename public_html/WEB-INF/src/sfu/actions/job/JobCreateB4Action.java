package sfu.actions.job;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sfu.actionforms.job.JobCreateForm;

import sfu.beans.configuration.JobConfig;
import sfu.beans.configuration.ReplicationConfig;
import sfu.beans.configuration.ScannerConfig;
import sfu.beans.configuration.ScannerOptionParser;
import sfu.beans.configuration.SmtpConfig;
import sfu.beans.scan.ScannerOptionBean;
import sfu.beans.scan.ScannerOptionsOperation;
import sfu.beans.user.UserProfile;


/**
 * @purpose To set the scanner specific informations and other parameters from 
 * the configuration, for the files which will be scanned.
 * @version 1.0
 * @dateOfCreation 10-04-2006
 * @lastModifiedDate 19-06-2006
 * @lastModifiedBy Amit Mishra
 */
public class JobCreateB4Action extends Action {
  Logger logger = Logger.getLogger("SfuLogger");

  /**
    * This is the main action called from the Struts framework.
    * @param mapping The ActionMapping used to select this instance.
    * @param form The optional ActionForm bean for this request.
    * @param request The HTTP Request we are processing.
    * @param response The HTTP Response we are processing.
    */
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) {
    ServletContext context = null;
    String xmlFilePrefix = null;
    JobCreateForm jobCreateForm = null;
    UserProfile userProfile;
    ReplicationConfig replicationConfig;
    ScannerConfig scannerConfig;
    SmtpConfig smtpConfig;
    JobConfig jobConfig;
    ArrayList scannerOptions = null;
    ArrayList scannerOptionFromFile = null;
    String propsFilePath = null;


    try {
      logger.info("------Entering JobCreateB4Action------");

      //jobCreateForm = new JobCreateForm();
      jobCreateForm = (JobCreateForm)form;

      if (jobCreateForm.getRadNamingMethod() == null ||
          jobCreateForm.getRadNamingMethod().equalsIgnoreCase("auto")) {
        jobCreateForm.setTxtDocName("");
      }

      HttpSession httpSession = request.getSession(false);
      userProfile = (UserProfile)httpSession.getAttribute("userProfile");
      context = servlet.getServletContext();

      xmlFilePrefix = (String)context.getAttribute("xmlFilePrefix");


      replicationConfig = new ReplicationConfig(xmlFilePrefix);

      scannerConfig = new ScannerConfig(xmlFilePrefix);

      smtpConfig = new SmtpConfig(xmlFilePrefix);

      if (userProfile.getUserDirectory() != null &&
          userProfile.getUserDirectory().trim().length() != 0) {
        jobCreateForm
        .setTxtDestinationFolderPath(userProfile.getUserDirectory());
        jobCreateForm.setHdnSenderEmailAddress(userProfile.getEmailId());
      } else {
        jobCreateForm
        .setTxtDestinationFolderPath(replicationConfig.getParentFolder());
      }

      if (jobCreateForm.getCboOutputFormat() == null ||
          jobCreateForm.getCboOutputFormat().trim().length() == 0) {
        jobCreateForm
        .setCboOutputFormat(scannerConfig.getOutputFormat().toLowerCase());
      }
      jobCreateForm.setSmtpHost(smtpConfig.getIPName());

      jobCreateForm.setHdnCreator(userProfile.getUserId());
      jobCreateForm.setHdnJobRetrialCount("0");
      jobCreateForm.setHdnErrorMesg("NA");


      jobConfig = new JobConfig(xmlFilePrefix);
      jobCreateForm.setHdnJobMaxCount(jobConfig.getMaxNumberOfRetries());
      jobCreateForm.setHdnJobRetrialInterval(jobConfig.getRetrialInterval());
      jobCreateForm.setHdnJobErrorMessage("NA");

      //setting options
      if (httpSession.getAttribute("scannerOptionFromFile") == null) {
        scannerOptions =
          ScannerOptionParser.getOptions(scannerConfig.getScanningDevice());
        propsFilePath = (String)context.getAttribute("contextPath");
        scannerOptionFromFile = new ArrayList();

        for (int i = 0; i < scannerOptions.size(); i++) {
          ScannerOptionBean scannerOptionHelpBean =
            (ScannerOptionBean)scannerOptions.get(i);
          ScannerOptionBean scannerOptionBean = new ScannerOptionBean();
          String txtOptionName = scannerOptionHelpBean.getOptionName();
          String txtOptionvalue =
            ScannerOptionsOperation.getKeyValue(propsFilePath, txtOptionName);
          if (txtOptionvalue != null) {
            scannerOptionBean.setOptionName(txtOptionName);
            scannerOptionBean.setOptionValue(txtOptionvalue);
            scannerOptionFromFile.add(scannerOptionBean);
            logger
            .debug("name: " + txtOptionName + " value: " + txtOptionvalue);
          }
          httpSession
          .setAttribute("scannerOptionFromFile", scannerOptionFromFile);
        }
      } else {
        logger.debug("scannerOptionFromFile is in session.");
      }

      jobCreateForm.setHdnDevice(scannerConfig.getScanningDevice());
      jobCreateForm.setHdnModel(scannerConfig.getScannerModel());
      jobCreateForm.setHdnDpi(scannerConfig.getDPI());
      jobCreateForm.setHdnMode(scannerConfig.getMode());
      jobCreateForm.setHdnJobFolder(scannerConfig.getJobFolder());

      request.setAttribute(mapping.getAttribute(), jobCreateForm);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
    } finally {
      logger.info("------Exiting JobCreateB4Action------");
    }
    return mapping.findForward("success");
  }
}
