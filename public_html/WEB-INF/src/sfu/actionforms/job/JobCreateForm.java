package sfu.actionforms.job;

import org.apache.struts.validator.ValidatorForm;

public class JobCreateForm extends ValidatorForm {
  
  String radNoOfDocuments;
  String radNamingMethod;
  String txtDocName;
  String txtDocSuffix;
  String chkDocSuffix;
  String txtDocNo;
  
//  String txtUploadDateAndTime;
//  String txtMailDateAndTime;
  String month;
  String txtMailTo;
  String txaMail;
  String txtCc;
  String txtBcc;
  String txtSubject;
  String smtpHost;
//  String txtFaxDateAndTime;
  String txtFaxTo;
  String txtCompanyName;
  String txtFaxNumber;
//  String monthUpload;
//  String yearUpload;
//  String dayUpload;
//  String hoursUpload;
//  String minutesUpload;
//  String secondsUpload;
  String timezoneMail;
 
//  String monthMail;
//  String yearMail;
//  String dayMail;
//  String hoursMail;
//  String minutesMail;
//  String secondsMail;
  String timezoneFax;
  String txaFax;
//  String monthFax;
//  String yearFax;
//  String dayFax;
//  String hoursFax;
//  String minutesFax;
//  String secondsFax;
  String txtDestinationFolderPath;
  String chkSingleFile;
  String hdnOutputFormat;
  String cboOutputFormat;
  
  String hdnCreator;
  String hdnJobRetrialCount;
  String hdnErrorMesg;
  String hdnJobMaxCount;
  String hdnJobRetrialInterval;
  String hdnJobErrorMessage;
  String hdnMode;
  String hdnDpi;
  String hdnJobFolder;
  String hdnSenderEmailAddress;
  String chkUpload;
  String chkMail;
  String chkFax;
  
  String year;
  String day;
  String hours;
  String minutes;
  String seconds;
  String txtJobExecDateAndTime;
  
  String hdnDevice;
  String hdnModel;
  

  public void setRadNoOfDocuments(String radNoOfDocuments) {
    this.radNoOfDocuments = radNoOfDocuments;
  }

  public String getRadNoOfDocuments() {
    return radNoOfDocuments;
  }

  public void setRadNamingMethod(String radNamingMethod) {
    this.radNamingMethod = radNamingMethod;
  }

  public String getRadNamingMethod() {
    return radNamingMethod;
  }

  public void setTxtDocName(String txtDocName) {
    this.txtDocName = txtDocName;
  }

  public String getTxtDocName() {
    return txtDocName;
  }

  public void setTxtDocSuffix(String txtDocSuffix) {
    this.txtDocSuffix = txtDocSuffix;
  }

  public String getTxtDocSuffix() {
    return txtDocSuffix;
  }

  public void setChkDocSuffix(String chkDocSuffix) {
    this.chkDocSuffix = chkDocSuffix;
  }

  public String getChkDocSuffix() {
    return chkDocSuffix;
  }

  public void setTxtDocNo(String txtDocNo) {
    this.txtDocNo = txtDocNo;
  }

  public String getTxtDocNo() {
    return txtDocNo;
  }

//  public void setTxtUploadDateAndTime(String txtUploadDateAndTime) {
//    this.txtUploadDateAndTime = txtUploadDateAndTime;
//  }
//
//  public String getTxtUploadDateAndTime() {
//    return txtUploadDateAndTime;
//  }
//
//  public void setTxtMailDateAndTime(String txtMailDateAndTime) {
//    this.txtMailDateAndTime = txtMailDateAndTime;
//  }
//
//  public String getTxtMailDateAndTime() {
//    return txtMailDateAndTime;
//  }

  public void setTxtMailTo(String txtMailTo) {
    this.txtMailTo = txtMailTo;
  }

  public String getTxtMailTo() {
    return txtMailTo;
  }

  public void setTxaMail(String txaMail) {
    this.txaMail = txaMail;
  }

  public String getTxaMail() {
    return txaMail;
  }

  public void setTxtCc(String txtCc) {
    this.txtCc = txtCc;
  }

  public String getTxtCc() {
    return txtCc;
  }

  public void setTxtBcc(String txtBcc) {
    this.txtBcc = txtBcc;
  }

  public String getTxtBcc() {
    return txtBcc;
  }

  public void setTxtSubject(String txtSubject) {
    this.txtSubject = txtSubject;
  }

  public String getTxtSubject() {
    return txtSubject;
  }

  public void setSmtpHost(String smtpHost) {
    this.smtpHost = smtpHost;
  }

  public String getSmtpHost() {
    return smtpHost;
  }

//  public void setTxtFaxDateAndTime(String txtFaxDateAndTime) {
//    this.txtFaxDateAndTime = txtFaxDateAndTime;
//  }
//
//  public String getTxtFaxDateAndTime() {
//    return txtFaxDateAndTime;
//  }

  public void setTxtFaxTo(String txtFaxTo) {
    this.txtFaxTo = txtFaxTo;
  }

  public String getTxtFaxTo() {
    return txtFaxTo;
  }

  public void setTxtCompanyName(String txtCompanyName) {
    this.txtCompanyName = txtCompanyName;
  }

  public String getTxtCompanyName() {
    return txtCompanyName;
  }

  public void setTxtFaxNumber(String txtFaxNumber) {
    this.txtFaxNumber = txtFaxNumber;
  }

  public String getTxtFaxNumber() {
    return txtFaxNumber;
  }

//  public void setMonthUpload(String monthUpload) {
//    this.monthUpload = monthUpload;
//  }
//
//  public String getMonthUpload() {
//    return monthUpload;
//  }
//
//  public void setYearUpload(String yearUpload) {
//    this.yearUpload = yearUpload;
//  }
//
//  public String getYearUpload() {
//    return yearUpload;
//  }
//
//  public void setDayUpload(String dayUpload) {
//    this.dayUpload = dayUpload;
//  }
//
//  public String getDayUpload() {
//    return dayUpload;
//  }
//
//  public void setHoursUpload(String hoursUpload) {
//    this.hoursUpload = hoursUpload;
//  }
//
//  public String getHoursUpload() {
//    return hoursUpload;
//  }
//
//  public void setMinutesUpload(String minutesUpload) {
//    this.minutesUpload = minutesUpload;
//  }
//
//  public String getMinutesUpload() {
//    return minutesUpload;
//  }
//
//  public void setSecondsUpload(String secondsUpload) {
//    this.secondsUpload = secondsUpload;
//  }
//
//  public String getSecondsUpload() {
//    return secondsUpload;
//  }

  public void setTimezoneMail(String timezoneMail) {
    this.timezoneMail = timezoneMail;
  }

  public String getTimezoneMail() {
    return timezoneMail;
  }

//  public void setMonthMail(String monthMail) {
//    this.monthMail = monthMail;
//  }
//
//  public String getMonthMail() {
//    return monthMail;
//  }
//
//  public void setYearMail(String yearMail) {
//    this.yearMail = yearMail;
//  }
//
//  public String getYearMail() {
//    return yearMail;
//  }
//
//  public void setDayMail(String dayMail) {
//    this.dayMail = dayMail;
//  }
//
//  public String getDayMail() {
//    return dayMail;
//  }
//
//  public void setHoursMail(String hoursMail) {
//    this.hoursMail = hoursMail;
//  }
//
//  public String getHoursMail() {
//    return hoursMail;
//  }
//
//  public void setMinutesMail(String minutesMail) {
//    this.minutesMail = minutesMail;
//  }
//
//  public String getMinutesMail() {
//    return minutesMail;
//  }
//
//  public void setSecondsMail(String secondsMail) {
//    this.secondsMail = secondsMail;
//  }
//
//  public String getSecondsMail() {
//    return secondsMail;
//  }

  public void setTimezoneFax(String timezoneFax) {
    this.timezoneFax = timezoneFax;
  }

  public String getTimezoneFax() {
    return timezoneFax;
  }

//  public void setMonthFax(String monthFax) {
//    this.monthFax = monthFax;
//  }
//
//  public String getMonthFax() {
//    return monthFax;
//  }
//
//  public void setYearFax(String yearFax) {
//    this.yearFax = yearFax;
//  }
//
//  public String getYearFax() {
//    return yearFax;
//  }
//
//  public void setDayFax(String dayFax) {
//    this.dayFax = dayFax;
//  }
//
//  public String getDayFax() {
//    return dayFax;
//  }
//
//  public void setHoursFax(String hoursFax) {
//    this.hoursFax = hoursFax;
//  }
//
//  public String getHoursFax() {
//    return hoursFax;
//  }
//
//  public void setMinutesFax(String minutesFax) {
//    this.minutesFax = minutesFax;
//  }
//
//  public String getMinutesFax() {
//    return minutesFax;
//  }
//
//  public void setSecondsFax(String secondsFax) {
//    this.secondsFax = secondsFax;
//  }
//
//  public String getSecondsFax() {
//    return secondsFax;
//  }

  public void setTxtDestinationFolderPath(String txtDestinationFolderPath) {
    this.txtDestinationFolderPath = txtDestinationFolderPath;
  }

  public String getTxtDestinationFolderPath() {
    return txtDestinationFolderPath;
  }

  public void setChkSingleFile(String chkSingleFile) {
    this.chkSingleFile = chkSingleFile;
  }

  public String getChkSingleFile() {
    return chkSingleFile;
  }

  public void setHdnOutputFormat(String hdnOutputFormat) {
    this.hdnOutputFormat = hdnOutputFormat;
  }

  public String getHdnOutputFormat() {
    return hdnOutputFormat;
  }

  public void setCboOutputFormat(String cboOutputFormat) {
    this.cboOutputFormat = cboOutputFormat;
  }

  public String getCboOutputFormat() {
    return cboOutputFormat;
  }

  public void setHdnCreator(String hdnCreator) {
    this.hdnCreator = hdnCreator;
  }

  public String getHdnCreator() {
    return hdnCreator;
  }

  public void setHdnJobRetrialCount(String hdnJobRetrialCount) {
    this.hdnJobRetrialCount = hdnJobRetrialCount;
  }

  public String getHdnJobRetrialCount() {
    return hdnJobRetrialCount;
  }

  public void setHdnErrorMesg(String hdnErrorMesg) {
    this.hdnErrorMesg = hdnErrorMesg;
  }

  public String getHdnErrorMesg() {
    return hdnErrorMesg;
  }

  public void setHdnJobMaxCount(String hdnJobMaxCount) {
    this.hdnJobMaxCount = hdnJobMaxCount;
  }

  public String getHdnJobMaxCount() {
    return hdnJobMaxCount;
  }

  public void setHdnJobRetrialInterval(String hdnJobRetrialInterval) {
    this.hdnJobRetrialInterval = hdnJobRetrialInterval;
  }

  public String getHdnJobRetrialInterval() {
    return hdnJobRetrialInterval;
  }

  public void setHdnJobErrorMessage(String hdnJobErrorMessage) {
    this.hdnJobErrorMessage = hdnJobErrorMessage;
  }

  public String getHdnJobErrorMessage() {
    return hdnJobErrorMessage;
  }

  public void setHdnMode(String hdnMode) {
    this.hdnMode = hdnMode;
  }

  public String getHdnMode() {
    return hdnMode;
  }

  public void setHdnDpi(String hdnDpi) {
    this.hdnDpi = hdnDpi;
  }

  public String getHdnDpi() {
    return hdnDpi;
  }

  public void setHdnJobFolder(String hdnJobFolder) {
    this.hdnJobFolder = hdnJobFolder;
  }

  public String getHdnJobFolder() {
    return hdnJobFolder;
  }

  public void setHdnSenderEmailAddress(String hdnSenderEmailAddress) {
    this.hdnSenderEmailAddress = hdnSenderEmailAddress;
  }

  public String getHdnSenderEmailAddress() {
    return hdnSenderEmailAddress;
  }

  public void setChkUpload(String chkUpload) {
    this.chkUpload = chkUpload;
  }

  public String getChkUpload() {
    return chkUpload;
  }

  public void setChkMail(String chkMail) {
    this.chkMail = chkMail;
  }

  public String getChkMail() {
    return chkMail;
  }

  public void setChkFax(String chkFax) {
    this.chkFax = chkFax;
  }

  public String getChkFax() {
    return chkFax;
  }

  public void setTxaFax(String txaFax) {
    this.txaFax = txaFax;
  }

  public String getTxaFax() {
    return txaFax;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public String getMonth() {
    return month;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public String getYear() {
    return year;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public String getDay() {
    return day;
  }

  public void setHours(String hours) {
    this.hours = hours;
  }

  public String getHours() {
    return hours;
  }

  public void setMinutes(String minutes) {
    this.minutes = minutes;
  }

  public String getMinutes() {
    return minutes;
  }

  public void setSeconds(String seconds) {
    this.seconds = seconds;
  }

  public String getSeconds() {
    return seconds;
  }

  public void setTxtJobExecDateAndTime(String txtJobExecDateAndTime) {
    this.txtJobExecDateAndTime = txtJobExecDateAndTime;
  }

  public String getTxtJobExecDateAndTime() {
    return txtJobExecDateAndTime;
  }

  public void setHdnDevice(String hdnDevice) {
    this.hdnDevice = hdnDevice;
  }

  public String getHdnDevice() {
    return hdnDevice;
  }

  public void setHdnModel(String hdnModel) {
    this.hdnModel = hdnModel;
  }

  public String getHdnModel() {
    return hdnModel;
  }
}


