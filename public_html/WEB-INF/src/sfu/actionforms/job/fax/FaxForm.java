package sfu.actionforms.job.fax;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

import sfu.beans.scheduler.DateHelper;

public class FaxForm extends ValidatorForm {

    private String txtTo;

    private String txtSubject;

    private String txaMail;

    private String[] lstAttachment;

    private String txtSendTime =
        DateHelper.format(new Date(), "MM/dd/yyyy HH:mm:ss");

    private String day;

    private String month;

    private String year;

    private String hours;

    private String minutes;

    private String seconds;

    String radDocSelection;

    String radNaming;

    String txtName;

    String chkSuffix;

    String txtDocNo;

    String chkSingleFile;

    String hdnOutputFormat;

    String smtpHost;

    String txtCompanyName;

    String txtFaxNumber;

    /**
     * Purpose   : Returns txtTo.
     * @return   : String
     */
    public String getTxtTo() {
        return txtTo;
    }

    /**
     * Purpose   : Sets the value of txtTo.
     * @param    : newTxtTo Value of txtTo from the form
     */
    public void setTxtTo(String newTxtTo) {
        txtTo = newTxtTo;
    }


    /**
     * Purpose   : Returns txtSubject.
     * @return   : String
     */
    public String getTxtSubject() {
        return txtSubject;
    }

    /**
     * Purpose   : Sets the value of txtSubject.
     * @param    : newTxtSubject Value of txtSubject from the form
     */
    public void setTxtSubject(String newTxtSubject) {
        txtSubject = newTxtSubject;
    }

    /**
     * Purpose   : Returns txaMail.
     * @return   : String
     */
    public String getTxaMail() {
        return txaMail;
    }

    /**
     * Purpose   : Sets the value of txaMail.
     * @param    : newTxaMail Value of txaMail from the form.
     */
    public void setTxaMail(String newTxaMail) {
        txaMail = newTxaMail;
    }

    /**
     * Purpose   : Returns array of Attachment.
     * @return   : An String Array.
     */
    public String[] getLstAttachment() {
        return lstAttachment;
    }

    /**
     * Purpose   : Sets the value of lstAttachment.
     * @param    : newLstAttachment Value of lstAttachment from the form
     */
    public void setLstAttachment(String[] newLstAttachment) {
        lstAttachment = newLstAttachment;
    }

    public String getTxtSendTime() {
        return txtSendTime;
    }

    public void setTxtSendTime(String newTxtSendTime) {
        txtSendTime = newTxtSendTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String newDay) {
        day = newDay;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String newMonth) {
        month = newMonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String newYear) {
        year = newYear;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String newHours) {
        hours = newHours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String newMinutes) {
        minutes = newMinutes;
    }


    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String newSeconds) {
        seconds = newSeconds;
    }

    public String getRadDocSelection() {
        return radDocSelection;
    }

    public void setRadDocSelection(String radDocSelection) {
        this.radDocSelection = radDocSelection;
    }

    public String getRadNaming() {
        return radNaming;
    }

    public void setRadNaming(String radNaming) {
        this.radNaming = radNaming;
    }


    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }


    public String getChkSuffix() {
        return chkSuffix;
    }

    public void setChkSuffix(String chkSuffix) {
        this.chkSuffix = chkSuffix;
    }

    public String getTxtDocNo() {
        return txtDocNo;
    }

    public void setTxtDocNo(String txtDocNo) {
        this.txtDocNo = txtDocNo;
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

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getTxtCompanyName() {
        return txtCompanyName;
    }

    public void setTxtCompanyName(String txtCompanyName) {
        this.txtCompanyName = txtCompanyName;
    }

    public String getTxtFaxNumber() {
        return txtFaxNumber;
    }

    public void setTxtFaxNumber(String txtFaxNumber) {
        this.txtFaxNumber = txtFaxNumber;
    }
}
