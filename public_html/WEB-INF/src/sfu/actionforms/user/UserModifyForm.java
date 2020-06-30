package sfu.actionforms.user;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class UserModifyForm extends ActionForm {
    String txtUserName;

    String txtPassword;

    String txtConfirmPassword;

    String txtFullName;

    String txtExpirationDateTime;

    String[] cboLoginShell;

    String txtHomeFolder;

    String chkExpiration;

    String chkIsLocked;

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

    public String getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        this.txtUserName = txtUserName;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public String getTxtConfirmPassword() {
        return txtConfirmPassword;
    }

    public void setTxtConfirmPassword(String txtConfirmPassword) {
        this.txtConfirmPassword = txtConfirmPassword;
    }

    public String getTxtFullName() {
        return txtFullName;
    }

    public void setTxtFullName(String txtFullName) {
        this.txtFullName = txtFullName;
    }

    public String getTxtExpirationDateTime() {
        return txtExpirationDateTime;
    }

    public void setTxtExpirationDateTime(String txtExpirationDateTime) {
        this.txtExpirationDateTime = txtExpirationDateTime;
    }

    public String[] getCboLoginShell() {
        return cboLoginShell;
    }

    public void setCboLoginShell(String[] cboLoginShell) {
        this.cboLoginShell = cboLoginShell;
    }

    public String getTxtHomeFolder() {
        return txtHomeFolder;
    }

    public void setTxtHomeFolder(String txtHomeFolder) {
        this.txtHomeFolder = txtHomeFolder;
    }

    public String getChkExpiration() {
        return chkExpiration;
    }

    public void setChkExpiration(String chkExpiration) {
        this.chkExpiration = chkExpiration;
    }

    public String getChkIsLocked() {
        return chkIsLocked;
    }

    public void setChkIsLocked(String chkIsLocked) {
        this.chkIsLocked = chkIsLocked;
    }
}
