package sfu.beans.user;

public class UserListBean {
    private String userName;

    private String userId;
    
    private String emailId;

    private String isLocked;

    private String isAdmin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }


    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }


  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getEmailId() {
    return emailId;
  }
}
