package sfu.actionforms.configuration;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ReplicationConfigurationForm extends ActionForm {
    String txtParentFolder;

    String txtReplicationInterval;

    String txtTimeout;

    String[] cboReplicationInterval;

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

    public String getTxtParentFolder() {
        return txtParentFolder;
    }

    public void setTxtParentFolder(String txtParentFolder) {
        this.txtParentFolder = txtParentFolder;
    }

    public String getTxtReplicationInterval() {
        return txtReplicationInterval;
    }

    public void setTxtReplicationInterval(String txtReplicationInterval) {
        this.txtReplicationInterval = txtReplicationInterval;
    }

    public String getTxtTimeout() {
        return txtTimeout;
    }

    public void setTxtTimeout(String txtTimeout) {
        this.txtTimeout = txtTimeout;
    }

    public String[] getCboReplicationInterval() {
        return cboReplicationInterval;
    }

    public void setCboReplicationInterval(String[] cboReplicationInterval) {
        this.cboReplicationInterval = cboReplicationInterval;
    }
}
