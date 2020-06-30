package sfu.actionforms.user;

import org.apache.struts.action.ActionForm;

public class UserListSelectForm extends ActionForm {
    private String txtSearchUserName;

    private String radSelect;

    private String hdnOpenerControl;

    private String hdnSearchPageNo;

    private String hdnSearchPageCount;

    private String operation;

    /**
     * Purpose : Returns  chkSelect array.
     * @return : String[] 
     */
    public String getRadSelect() {
        return radSelect;
    }

    /**
     * Purpose : Returns txtSearchByUserName.
     * @return : String
     */
    public String getTxtSearchUserName() {
        return txtSearchUserName;
    }

    /**
     * Purpose : Sets the value of txtSearchByUserName.
     * @param  : newTxtSearchByUserName Value of txtSearchByUserName from the form
     */
    public void setTxtSearchUserName(String txtSearchUserName) {
        this.txtSearchUserName = txtSearchUserName;
    }

    /**
     * Purpose : Returns control.
     * @return : String
     */
    public String getHdnOpenerControl() {
        return hdnOpenerControl;
    }

    /**
     * Purpose : Sets the value of control.
     * @param  : newControl Value of control from the form
     */
    public void setHdnOpenerControl(String hdnOpenerControl) {
        this.hdnOpenerControl = hdnOpenerControl;
    }

    /**
     * Purpose : Returns txtPageNo.
     * @return : String
     */
    public String getHdnSearchPageNo() {
        return hdnSearchPageNo;
    }

    /**
     * Purpose : Sets the value of txtPageNo.
     * @param  : newTxtPageNo Value of txtPageNo from the form
     */
    public void setHdnSearchPageNo(String hdnSearchPageNo) {
        this.hdnSearchPageNo = hdnSearchPageNo;
    }

    /**
     * Purpose : Returns txtPageCount.
     * @return : String
     */
    public String getHdnSearchPageCount() {
        return hdnSearchPageCount;
    }

    /**
     * Purpose : Sets the value of txtPageCount.
     * @param  : newTxtPageCount Value of txtPageCount from the form
     */
    public void setHdnSearchPageCount(String hdnSearchPageCount) {
        this.hdnSearchPageCount = hdnSearchPageCount;
    }

    /**
     * Purpose : Returns operation.
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

    public void setRadSelect(String radSelect) {
        this.radSelect = radSelect;
    }
}
