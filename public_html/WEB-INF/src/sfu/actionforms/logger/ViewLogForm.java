package sfu.actionforms.logger;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ViewLogForm extends ActionForm {

    public String displayLog;

    public String radLogType = "3";

    public int txtViewLinesTillEndStartingFrom = 25;

    public int txtViewLinesFromStartTill;

    public String position;

    public String getDisplayLog() {
        return displayLog;
    }

    public void setDisplayLog(String displayLog) {
        this.displayLog = displayLog;
    }

    public String getRadLogType() {
        return radLogType;
    }

    public void setRadLogType(String radLogType) {
        this.radLogType = radLogType;
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

    public int getTxtViewLinesFromStartTill() {
        return txtViewLinesFromStartTill;
    }

    public void setTxtViewLinesFromStartTill(int txtViewLinesFromStartTill) {
        this.txtViewLinesFromStartTill = txtViewLinesFromStartTill;
    }

    public int getTxtViewLinesTillEndStartingFrom() {
        return txtViewLinesTillEndStartingFrom;
    }

    public void setTxtViewLinesTillEndStartingFrom(int txtViewLinesTillEndStartingFrom) {
        this.txtViewLinesTillEndStartingFrom = txtViewLinesTillEndStartingFrom;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
