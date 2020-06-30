package sfu.actionforms.configuration;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class JobConfigurationForm extends ActionForm {
    String txtMaxUploadSize;

    String txtMaxMailSize;

    String[] cboMaxFaxSize;

    String[] cboUploadSizeUnit;

    String[] cboMailSizeUnit;

    String[] cboFaxSizeUnit;

    String txtMaxPagesToFax;

    String txtMaxNumberOfRetries;

    String txtMaxFaxSize;

    String txtRetrialInterval;

    /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }

    /**
   * Validate all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   * @return ActionErrors A list of all errors found.
   */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return super.validate(mapping, request);
    }

    public String getTxtMaxUploadSize() {
        return txtMaxUploadSize;
    }

    public void setTxtMaxUploadSize(String txtMaxUploadSize) {
        this.txtMaxUploadSize = txtMaxUploadSize;
    }

    public String getTxtMaxMailSize() {
        return txtMaxMailSize;
    }

    public void setTxtMaxMailSize(String txtMaxMailSize) {
        this.txtMaxMailSize = txtMaxMailSize;
    }

    public String[] getCboMaxFaxSize() {
        return cboMaxFaxSize;
    }

    public void setCboMaxFaxSize(String[] txtMaxFaxSize) {
        this.cboMaxFaxSize = txtMaxFaxSize;
    }

    public String[] getCboUploadSizeUnit() {
        return cboUploadSizeUnit;
    }

    public void setCboUploadSizeUnit(String txtUploadSizeUnit) {
        this.cboUploadSizeUnit = cboUploadSizeUnit;
    }

    public String[] getCboMailSizeUnit() {
        return cboMailSizeUnit;
    }

    public void setCboMailSizeUnit(String[] txtMailSizeUnit) {
        this.cboMailSizeUnit = txtMailSizeUnit;
    }

    public String[] getCboFaxSizeUnit() {
        return cboFaxSizeUnit;
    }

    public void setCboFaxSizeUnit(String[] txtFaxSizeUnit) {
        this.cboFaxSizeUnit = txtFaxSizeUnit;
    }

    public String getTxtMaxPagesToFax() {
        return txtMaxPagesToFax;
    }

    public void setTxtMaxPagesToFax(String txtMaxPagesToFax) {
        this.txtMaxPagesToFax = txtMaxPagesToFax;
    }

    public String getTxtMaxNumberOfRetries() {
        return txtMaxNumberOfRetries;
    }

    public void setTxtMaxNumberOfRetries(String txtMaxNumberOfRetries) {
        this.txtMaxNumberOfRetries = txtMaxNumberOfRetries;
    }

    public String getTxtMaxFaxSize() {
        return txtMaxFaxSize;
    }

    public void setTxtMaxFaxSize(String txtMaxFaxSize) {
        this.txtMaxFaxSize = txtMaxFaxSize;
    }

    public void setCboUploadSizeUnit(String[] cboUploadSizeUnit) {
        this.cboUploadSizeUnit = cboUploadSizeUnit;
    }

    public String getTxtRetrialInterval() {
        return txtRetrialInterval;
    }

    public void setTxtRetrialInterval(String txtRetrialInterval) {
        this.txtRetrialInterval = txtRetrialInterval;
    }
}
