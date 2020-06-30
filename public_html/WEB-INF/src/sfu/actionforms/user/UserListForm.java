package sfu.actionforms.user;

import org.apache.struts.action.ActionForm;

public class UserListForm extends ActionForm {
    String radSelect;

    String txtSearchUserName;

    String hdnSearchPageNo;

    String hdnSearchPageCount;


    public String getRadSelect() {
        return radSelect;
    }

    public void setRadSelect(String radSelect) {
        this.radSelect = radSelect;
    }

    public String getTxtSearchUserName() {
        return txtSearchUserName;
    }

    public void setTxtSearchUserName(String txtSearchUserName) {
        this.txtSearchUserName = txtSearchUserName;
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
}
