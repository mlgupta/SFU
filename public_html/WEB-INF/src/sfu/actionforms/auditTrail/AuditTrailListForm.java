package sfu.actionforms.auditTrail;

import org.apache.struts.action.ActionForm;

public class AuditTrailListForm extends ActionForm {
    String hdnSearchPageNo;

    String hdnSearchPageCount;

    String[] chkSelect;

    String txtActionType;

    String txtPerformedFromDate;

    String txtScheduledFromDate;

    String txtPagesScanned;

    String txtFileName;

    String txtToAddress;

    String timezone;

    String txtPerformedToDate;

    String txtScheduledToDate;

    String[] cboSearchByActionType;

    String actionType;

    String txtSearchByUserName;

    public AuditTrailListForm() {
    }

    public String getHdnSearchPageNo() {
        return hdnSearchPageNo;
    }

    public void setHdnSearchPageNo(String hdnSearchPageNo) {
        this.hdnSearchPageNo = hdnSearchPageNo;
    }

    public String getHdnSearchPageCount() {
        return hdnSearchPageCount;
    }

    public void setHdnSearchPageCount(String hdnSearchPageCount) {
        this.hdnSearchPageCount = hdnSearchPageCount;
    }

    public String[] getChkSelect() {
        return chkSelect;
    }

    public void setChkSelect(String[] chkSelect) {
        this.chkSelect = chkSelect;
    }


    public String getTxtActionType() {
        return txtActionType;
    }

    public void setTxtActionType(String txtActionType) {
        this.txtActionType = txtActionType;
    }

    public String getTxtPerformedFromDate() {
        return txtPerformedFromDate;
    }

    public void setTxtPerformedFromDate(String txtPerformedFromDate) {
        this.txtPerformedFromDate = txtPerformedFromDate;
    }

    public String getTxtScheduledFromDate() {
        return txtScheduledFromDate;
    }

    public void setTxtScheduledFromDate(String txtScheduledFromDate) {
        this.txtScheduledFromDate = txtScheduledFromDate;
    }

    public String getTxtPagesScanned() {
        return txtPagesScanned;
    }

    public void setTxtPagesScanned(String txtPagesScanned) {
        this.txtPagesScanned = txtPagesScanned;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public String getTxtToAddress() {
        return txtToAddress;
    }

    public void setTxtToAddress(String txtToAddress) {
        this.txtToAddress = txtToAddress;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTxtPerformedToDate() {
        return txtPerformedToDate;
    }

    public void setTxtPerformedToDate(String txtPerformedToDate) {
        this.txtPerformedToDate = txtPerformedToDate;
    }

    public String getTxtScheduledToDate() {
        return txtScheduledToDate;
    }

    public void setTxtScheduledToDate(String txtScheduledToDate) {
        this.txtScheduledToDate = txtScheduledToDate;
    }

    public String[] getCboSearchByActionType() {
        return cboSearchByActionType;
    }

    public void setCboSearchByActionType(String[] cboSearchByActionType) {
        this.cboSearchByActionType = cboSearchByActionType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTxtSearchByUserName() {
        return txtSearchByUserName;
    }

    public void setTxtSearchByUserName(String txtSearchByUserName) {
        this.txtSearchByUserName = txtSearchByUserName;
    }
}
