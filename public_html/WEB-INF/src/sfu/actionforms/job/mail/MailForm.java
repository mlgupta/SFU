package sfu.actionforms.job.mail;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import sfu.beans.scheduler.DateHelper;

public class MailForm extends ValidatorForm {

    private String txtTo;

    private String txtCc;

    private String txtBcc;

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

    private String timezone;

    private String seconds;

    String radDocSelection;

    String radNaming;

    String txtPrefix;

    String txtName;

    String txtSuffix;

    String chkPrefix;

    String chkSuffix;

    String txtDocNo;

    String chkSingleFile;

    String hdnOutputFormat;

    String smtpHost;

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
     * Purpose   : Returns txtCc.
     * @return   : String
     */
    public String getTxtCc() {
        return txtCc;
    }

    /**
     * Purpose   : Sets the value of txtCc.
     * @param    : newTxtCc Value of txtCc from the form
     */
    public void setTxtCc(String newTxtCc) {
        txtCc = newTxtCc;
    }

    /**
     * Purpose   : Returns txtBCc.
     * @return   : String
     */
    public String getTxtBcc() {
        return txtBcc;
    }

    /**
     * Purpose   : Sets the value of txtBCc.
     * @param    : newTxtBCc Value of txtBCc from the form
     */
    public void setTxtBcc(String txtBcc) {
        this.txtBcc = txtBcc;
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String newTimezone) {
        timezone = newTimezone;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String newSeconds) {
        seconds = newSeconds;
    }


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

    public String getTxtPrefix() {
        return txtPrefix;
    }

    public void setTxtPrefix(String txtPrefix) {
        this.txtPrefix = txtPrefix;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtSuffix() {
        return txtSuffix;
    }

    public void setTxtSuffix(String txtSuffix) {
        this.txtSuffix = txtSuffix;
    }

    public String getChkPrefix() {
        return chkPrefix;
    }

    public void setChkPrefix(String chkPrefix) {
        this.chkPrefix = chkPrefix;
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
}
