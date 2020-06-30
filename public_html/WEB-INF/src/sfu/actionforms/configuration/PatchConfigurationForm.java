package sfu.actionforms.configuration;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class PatchConfigurationForm extends ActionForm {
    String txtLocalPatchFolder;

    String txtRemotePatchFolder;

    String txtPatchCheckInterval;

    String[] cboPatchCheckInterval;

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

    public String getTxtLocalPatchFolder() {
        return txtLocalPatchFolder;
    }

    public void setTxtLocalPatchFolder(String txtLocalPatchFolder) {
        this.txtLocalPatchFolder = txtLocalPatchFolder;
    }

    public String getTxtRemotePatchFolder() {
        return txtRemotePatchFolder;
    }

    public void setTxtRemotePatchFolder(String txtRemotePatchFolder) {
        this.txtRemotePatchFolder = txtRemotePatchFolder;
    }

    public String getTxtPatchCheckInterval() {
        return txtPatchCheckInterval;
    }

    public void setTxtPatchCheckInterval(String txtPatchCheckInterval) {
        this.txtPatchCheckInterval = txtPatchCheckInterval;
    }

    public String[] getCboPatchCheckInterval() {
        return cboPatchCheckInterval;
    }

    public void setCboPatchCheckInterval(String[] cboPatchCheckInterval) {
        this.cboPatchCheckInterval = cboPatchCheckInterval;
    }
}
