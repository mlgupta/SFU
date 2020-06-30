package sfu.actionforms.user;

import org.apache.struts.validator.ValidatorForm;

public class UserForm extends ValidatorForm {

    String txtUserName;

    String txtPassword;

    String txtConfirmPassword;

    String chkIsLocked = "false";

    String txtUserId;
    
    String txtEmailId;

    String txtUserStatus;

    String chkIsAdmin = "false";

    String hdnSearchPageNo;


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

    public String getChkIsLocked() {
        return chkIsLocked;
    }

    public void setChkIsLocked(String chkIsLocked) {
        this.chkIsLocked = chkIsLocked;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }

    public String getTxtUserStatus() {
        return txtUserStatus;
    }

    public void setTxtUserStatus(String txtUserStatus) {
        this.txtUserStatus = txtUserStatus;
    }

    public String getChkIsAdmin() {
        return chkIsAdmin;
    }

    public void setChkIsAdmin(String chkIsAdmin) {
        this.chkIsAdmin = chkIsAdmin;
    }

    public String getHdnSearchPageNo() {
        return hdnSearchPageNo;
    }

    public void setHdnSearchPageNo(String hdnSearchPageNo) {
        this.hdnSearchPageNo = hdnSearchPageNo;
    }

  public void setTxtEmailId(String txtEmailId) {
    this.txtEmailId = txtEmailId;
  }

  public String getTxtEmailId() {
    return txtEmailId;
  }
}
