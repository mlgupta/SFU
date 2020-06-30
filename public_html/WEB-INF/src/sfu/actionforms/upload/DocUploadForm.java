package sfu.actionforms.upload;

import org.apache.struts.validator.ValidatorForm;

public class DocUploadForm extends ValidatorForm {
    String radNoOfDocuments;

    String radNamingMethod;

    String txtDocName;

    String txtDocSuffix;

    String chkDocSuffix;

    String txtDocNo;

    String txtDateAndTime;

    String month;

    String year;

    String day;

    String hours;

    String minutes;

    String seconds;

    String timezone;

    String txtDestinationFolderPath;

    String chkSingleFile;

    String hdnOutputFormat;

    public String getRadNoOfDocuments() {
        return radNoOfDocuments;
    }

    public void setRadNoOfDocuments(String radNoOfDocuments) {
        this.radNoOfDocuments = radNoOfDocuments;
    }

    public String getRadNamingMethod() {
        return radNamingMethod;
    }

    public void setRadNamingMethod(String radNamingMethod) {
        this.radNamingMethod = radNamingMethod;
    }

    public String getTxtDocName() {
        return txtDocName;
    }

    public void setTxtDocName(String txtDocName) {
        this.txtDocName = txtDocName;
    }

    public String getTxtDocSuffix() {
        return txtDocSuffix;
    }

    public void setTxtDocSuffix(String txtDocSuffix) {
        this.txtDocSuffix = txtDocSuffix;
    }

    public String getChkDocSuffix() {
        return chkDocSuffix;
    }

    public void setChkDocSuffix(String chkDocSuffix) {
        this.chkDocSuffix = chkDocSuffix;
    }


    public String getTxtDocNo() {
        return txtDocNo;
    }

    public void setTxtDocNo(String txtDocNo) {
        this.txtDocNo = txtDocNo;
    }

    public String getTxtDateAndTime() {
        return txtDateAndTime;
    }

    public void setTxtDateAndTime(String txtDateAndTime) {
        this.txtDateAndTime = txtDateAndTime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTxtDestinationFolderPath() {
        return txtDestinationFolderPath;
    }

    public void setTxtDestinationFolderPath(String txtDestinationFolderPath) {
        this.txtDestinationFolderPath = txtDestinationFolderPath;
    }

    public String getChkSingleFile() {
        return chkSingleFile;
    }

    public void setChkSingleFile(String chkSingleFile) {
        this.chkSingleFile = chkSingleFile;
    }

    public String getHdnOutputFormat() {
        return hdnOutputFormat;
    }

    public void setHdnOutputFormat(String hdnOutputFormat) {
        this.hdnOutputFormat = hdnOutputFormat;
    }

}
