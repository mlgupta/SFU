package sfu.actionforms.job;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class JobListForm extends ActionForm {
    private String radSelect;

    private String[] cboSearchByJobType;
    
    private String searchByJobType;

    private String txtSearchByUserName;

    private String operation;

    private String txtPageNo;

    private String txtPageCount;

    private String txtCreateFromDate;

    private String txtCreateToDate;

    private String txtDispatchFromDate;

    private String txtDispatchToDate;

    private String isSchedulerStopped;

    private String timezone;

    String hdnSchedulerStartStopOperation;

    String jobType;

    /**
     * Purpose : Returns an Array of cboSearchByJobType.
     * @return : String Array
     */
    public String[] getCboSearchByJobType() {
        return cboSearchByJobType;
    }

    /**
     * Purpose : Sets the value of cboSearchByJobType.
     * @param  : newCboSearchByJobType Value of cboSearchByJobType from the form
     */
    public void setCboSearchByJobType(String[] newCboSearchByJobType) {
        cboSearchByJobType = newCboSearchByJobType;
    }

    /**
     * Purpose : Returns value of operation
     * @return : String 
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Purpose : Sets the value of operation.
     * @param  : newOperation Value of operation from the form
     */
    public void setOperation(String newOperation) {
        operation = newOperation;
    }

    /**
     * Purpose : Returns value of radSelect
     * @return : String 
     */
    public String getRadSelect() {
        return radSelect;
    }

    /**
     * Purpose : Sets the value of radSelect.
     * @param  : newRadSelect Value of radSelect from the form
     */
    public void setRadSelect(String newRadSelect) {
        radSelect = newRadSelect;
    }

    /**
     * Purpose : Returns value of txtPageCount
     * @return : String 
     */
    public String getTxtPageCount() {
        return txtPageCount;
    }

    /**
     * Purpose : Sets the value of txtPageCount.
     * @param  : newTxtPageCount Value of txtPageCount from the form
     */
    public void setTxtPageCount(String newTxtPageCount) {
        txtPageCount = newTxtPageCount;
    }

    /**
     * Purpose : Returns value of txtPageNo
     * @return : String 
     */
    public String getTxtPageNo() {
        return txtPageNo;
    }

    /**
     * Purpose : Sets the value of txtPageNo.
     * @param  : newTxtPageNo Value of txtPageNo from the form
     */
    public void setTxtPageNo(String newTxtPageNo) {
        txtPageNo = newTxtPageNo;
    }

    /**
     * Purpose : Returns value of txtSearchByUserName
     * @return : String 
     */
    public String getTxtSearchByUserName() {
        return txtSearchByUserName;
    }

    /**
     * Purpose : Sets the value of txtSearchByUserName.
     * @param  : newTxtSearchByUserName Value of txtSearchByUserName from the form
     */
    public void setTxtSearchByUserName(String newTxtSearchByUserName) {
        txtSearchByUserName = newTxtSearchByUserName;
    }

    /**
     * Purpose : Returns value of txtCreatedFromDate
     * @return : String 
     */
    public String getTxtCreateFromDate() {
        return txtCreateFromDate;
    }

    /**
     * Purpose : Sets the value of txtCreatedFromDate.
     * @param  : newTxtCreatedFromDate Value of txtCreatedFromDate from the form
     */
    public void setTxtCreateFromDate(String newTxtCreateFromDate) {
        txtCreateFromDate = newTxtCreateFromDate;
    }

    /**
     * Purpose : Returns value of txtCreatedToDate
     * @return : String 
     */
    public String getTxtCreateToDate() {
        return txtCreateToDate;
    }

    /**
     * Purpose : Sets the value of txtCreatedToDate.
     * @param  : newTxtCreatedToDate Value of txtCreatedToDate from the form
     */
    public void setTxtCreateToDate(String newTxtCreateToDate) {
        txtCreateToDate = newTxtCreateToDate;
    }

    /**
     * Purpose : Returns value of txtDispatchFromDate
     * @return : String 
     */
    public String getTxtDispatchFromDate() {
        return txtDispatchFromDate;
    }

    /**
     * Purpose : Sets the value of txtDispatchFromDate.
     * @param  : newTxtDispatchFromDate Value of txtDispatchFromDate from the form
     */
    public void setTxtDispatchFromDate(String newTxtDispatchFromDate) {
        txtDispatchFromDate = newTxtDispatchFromDate;
    }

    /**
     * Purpose : Returns value of txtDispatchToDate
     * @return : String 
     */
    public String getTxtDispatchToDate() {
        return txtDispatchToDate;
    }

    /**
     * Purpose : Sets the value of txtDispatchToDate.
     * @param  : newTxtDispatchToDate Value of txtDispatchToDate from the form
     */
    public void setTxtDispatchToDate(String newTxtDispatchToDate) {
        txtDispatchToDate = newTxtDispatchToDate;
    }

    public String getIsSchedulerStopped() {
        return isSchedulerStopped;
    }

    public void setIsSchedulerStopped(String newIsSchedulerStopped) {
        isSchedulerStopped = newIsSchedulerStopped;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String newTimezone) {
        timezone = newTimezone;
    }

    public String getHdnSchedulerStartStopOperation() {
        return hdnSchedulerStartStopOperation;
    }

    public void setHdnSchedulerStartStopOperation(String hdnSchedulerStartStopOperation) {
        this.hdnSchedulerStartStopOperation = hdnSchedulerStartStopOperation;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }


  public void setSearchByJobType(String searchByJobType) {
    this.searchByJobType = searchByJobType;
  }

  public String getSearchByJobType() {
    return searchByJobType;
  }
}
