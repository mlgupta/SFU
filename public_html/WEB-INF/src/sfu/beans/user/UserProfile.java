package sfu.beans.user;

import java.util.*;

public class UserProfile {
    private String userProfileTblPk;

    private String userId;

    private String userName;

    private String numberOfRecords;
    
    private String emailId;
    
    private String userDirectory;

    String isAdmin;

    public String getUserProfileTblPk() {
        return userProfileTblPk;
    }

    public void setUserProfileTblPk(String newUserProfileTblPk) {
        userProfileTblPk = newUserProfileTblPk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String newUserId) {
        userId = newUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String newUserName) {
        userName = newUserName;
    }


    public String getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(String newNumberOfRecords) {
        numberOfRecords = newNumberOfRecords;
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

    public void setUserDirectory(String userDirectory) {
        this.userDirectory = userDirectory;
    }

    public String getUserDirectory() {
        return userDirectory;
    }
    
}