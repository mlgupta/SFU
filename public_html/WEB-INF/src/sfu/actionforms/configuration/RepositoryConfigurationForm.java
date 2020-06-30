package sfu.actionforms.configuration;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class RepositoryConfigurationForm extends ActionForm {
    String txtUser;

    String txtPassword;

    String txtRepositoryConnectString;

    String txtIpName;

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

    public String getTxtUser() {
        return txtUser;
    }

    public void setTxtUser(String txtUser) {
        this.txtUser = txtUser;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public String getTxtRepositoryConnectString() {
        return txtRepositoryConnectString;
    }

    public void setTxtRepositoryConnectString(String txtRepositoryConnectString) {
        this.txtRepositoryConnectString = txtRepositoryConnectString;
    }

    public String getTxtIpName() {
        return txtIpName;
    }

    public void setTxtIpName(String txtIpName) {
        this.txtIpName = txtIpName;
    }
}
