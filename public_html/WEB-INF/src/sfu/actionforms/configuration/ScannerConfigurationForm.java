package sfu.actionforms.configuration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class ScannerConfigurationForm extends ActionForm {

  String[] cboScanningDevice;
  
  String[] cboScannerModel;
  
  String[] cboMode;

  String[] cboOutputFormat;

  String chkColorEnable;

  String fleJobFolder;

  String[] cboDpi;
  
  //String txtOptionName;
  
  //String txtOptionValue;
  
  String hdnOptionType;
  
  String cboScannerOption;
  
  String hdnOptionName;

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


  public String[] getCboMode() {
    return cboMode;
  }

  public void setCboMode(String[] cboMode) {
    this.cboMode = cboMode;
  }

  public String[] getCboOutputFormat() {
    return cboOutputFormat;
  }

  public void setCboOutputFormat(String[] cboOutputFormat) {
    this.cboOutputFormat = cboOutputFormat;
  }

  public String getChkColorEnable() {
    return chkColorEnable;
  }

  public void setChkColorEnable(String chkColorEnable) {
    this.chkColorEnable = chkColorEnable;
  }

  public String getFleJobFolder() {
    return fleJobFolder;
  }

  public void setFleJobFolder(String fleJobFolder) {
    this.fleJobFolder = fleJobFolder;
  }

  public String[] getCboDpi() {
    return cboDpi;
  }

  public void setCboDpi(String[] cboDpi) {
    this.cboDpi = cboDpi;
  }


  public void setCboScanningDevice(String[] cboScanningDevice) {
    this.cboScanningDevice = cboScanningDevice;
  }

  public String[] getCboScanningDevice() {
    return cboScanningDevice;
  }

  public void setCboScannerModel(String[] cboScannerModel) {
    this.cboScannerModel = cboScannerModel;
  }

  public String[] getCboScannerModel() {
    return cboScannerModel;
  }

//  public void setTxtOptionName(String txtOptionName) {
//    this.txtOptionName = txtOptionName;
//  }
//
//  public String getTxtOptionName() {
//    return txtOptionName;
//  }
//
//  public void setTxtOptionValue(String txtOptionValue) {
//    this.txtOptionValue = txtOptionValue;
//  }
//
//  public String getTxtOptionValue() {
//    return txtOptionValue;
//  }

  public void setHdnOptionType(String hdnOptionType) {
    this.hdnOptionType = hdnOptionType;
  }

  public String getHdnOptionType() {
    return hdnOptionType;
  }

  public void setCboScannerOption(String cboScannerOption) {
    this.cboScannerOption = cboScannerOption;
  }

  public String getCboScannerOption() {
    return cboScannerOption;
  }

  public void setHdnOptionName(String hdnOptionName) {
    this.hdnOptionName = hdnOptionName;
  }

  public String getHdnOptionName() {
    return hdnOptionName;
  }
}
