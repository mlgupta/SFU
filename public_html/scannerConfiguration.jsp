<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
        <title>Scanner Configuration</title>
        <link href="main.css" rel="stylesheet" type="text/css"></link>
        <style type="text/css">
        
<!--
body {
background-color: #C5E2E5;
margin-left: 0px;
margin-top: 0px;
margin-right: 0px;
margin-bottom: 0px;
}
-->
</style>
      
      <script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
      
      <script language="Javascript1.1">
      <!--
        function callSubmit(optionType){
          if(optionType=="commonOption"){
            var thisForm=document.forms[0];
            thisForm.hdnOptionType.value=optionType;
            thisForm.submit();
          }
          if(optionType=="scannerSpecificOption"){
            var thisForm=document.forms[1];
            thisForm.hdnOptionType.value=optionType;
            //alert(thisForm.hdnOptionName[1].value);
            if(typeof thisForm.hdnOptionName.length !='undefined'){
              for(var index=0;  index<thisForm.hdnOptionName.length; index++){
                var optionName=thisForm.hdnOptionName[index].value;
                var chkObj= MM_findObj('chk'+optionName);
                /*
                //validation for text box starts
                var txtObj= MM_findObj(optionName);
                if(chkObj.checked && trim(txtObj.value).length<=0){
                  alert("Enter the value for option '"+txtObj.name+"'");
                  return false;
                }
                //validation for text box ends
                */
                
                //validation for combo box starts
                var cboObj= MM_findObj(optionName);
                if(chkObj.checked && trim(cboObj.value).length<=0){
                  alert("Enter the value for option '"+txtObj.name+"'");
                  return false;
                }
                if(chkObj.checked && trim(cboObj.value)=="inactive"){
                  alert("Can not select inactive option '"+cboObj.name+"'.");
                  return false;
                }
                //validation for combo box ends
              }
            }else{
              var optionName=thisForm.hdnOptionName.value;
              var chkObj= MM_findObj('chk'+optionName);
              var txtObj= MM_findObj(optionName);
              if(chkObj.checked && trim(txtObj.value).length<=0){
                  alert("Enter the value for option '"+txtObj.name+"'");
                  return false;
              }
            }
            thisForm.submit();
          }
        }
        
        /*function scannerOptionOperation(operation){
          thisForm=document.forms[0];
          if(operation=='add'){
            openWindow('scannerOptionAddB4Action.do?operation='+operation+'thisForm='+thisForm,'scannerOptionAdd',100,250,0,0,true);  
          }
          if(operation=='edit'){
            var cboScannerOption=thisForm.cboScannerOption.value;
            if(trim(cboScannerOption).length!=0){
              openWindow('scannerOptionEditB4Action.do?cboScannerOption='+cboScannerOption,'scannerOptionEdit',100,250,0,0,true);  
            }else{
              alert("Select any option to edit.");
            }
          }
        }*/
        
        function selectOption(optionObj){
          thisForm=document.forms[1];
          thisForm.cboScannerOption.value=optionObj;
        }
        
        function window_onload(){
          var thisForm=document.forms[0];
          //alert(thisForm.hdnOptionType.value);
          if(thisForm.hdnOptionType.value=="commonOption"){
            MM_showHideLayers('scannerSpecificOption','','hide','commonOption','','show');
          }if(thisForm.hdnOptionType.value=="scannerSpecificOption"){
            MM_showHideLayers('scannerSpecificOption','','show','commonOption','','hide');
          }
        }
        
        
      -->
      </script>
      
    </head>
    <body onload="window_onload();">
        <table  width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="20px" background="images/tile_20px.gif"
                    class="bdrBottomColor_336666">
                    <div align="center" class="heading_1">Scanner Configuration</div>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
              <td align="center" >
                <table border="0" width="450px" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="106px" class="bdrBottomColor_336666">
                       <!--<a onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_common_options.gif',1)"><img src="images/tab_common_options.gif" alt="Common Options" name="commonOptions" height="20" border="0"></a>
                       <a onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_scanner_specific_options.gif',1)"><img src="images/tab_scanner_specific_options.gif" alt="Scanner Specific Options" name="tabScannerSpecificOptions" height="25" border="0"></a>
                       -->
                      <div>
                       <html:button  property="1" styleClass="buttons" value="Common Options" onclick="MM_showHideLayers('scannerSpecificOption','','hide','commonOption','','show');"><img src="images/tab_common_options.gif" alt="Common Options" name="commonOptions" height="25" border="0"/></html:button>
                      </div>
                    </td>
                    <td class="bdrBottomColor_336666">
                       <!--<a onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_common_options.gif',1)"><img src="images/tab_common_options.gif" alt="Common Options" name="commonOptions" height="20" border="0"></a>
                       <a onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_scanner_specific_options.gif',1)"><img src="images/tab_scanner_specific_options.gif" alt="Scanner Specific Options" name="tabScannerSpecificOptions" height="25" border="0"></a>
                       -->
                       <html:button property="2" styleClass="buttons" value="Scanner Specific Options" onclick="MM_showHideLayers('scannerSpecificOption','','show','commonOption','','hide');"/>
                    </td>
                  </tr>
                  
                  <tr>
                    <td colspan="2" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666" height="340px" align="center">
                    <!--COMMON SCANNER OPTION STARTS-->            
                  <fieldset id="commonOption" class="bdrColor_336666" style="width:380px;height:280px;margin-top:15px">
                    <legend >Common Options:</legend>
                    <html:form action="scannerConfigurationAction.do"
                            enctype="multipart/form-data">
                      <html:hidden property="hdnOptionType" />
                          <bean:define name="ScannerConfigurationForm"
                               type="sfu.actionforms.configuration.ScannerConfigurationForm"
                               id="scannerConfigurationForm"/>
                      <table id="tblCommonOption" width="340px" border="0" align="center" cellspacing="8">
                          <tr>
                              <td width="221">
                                  <div align="right">
                                      <bean:message key="lbl.ScanningDevice"/>
                                  </div>
                              </td>
                              <td width="219">
                                  <html:select name="scannerConfigurationForm"
                                               property="cboScanningDevice"
                                               styleClass="bdrColor_336666"
                                               style="width:125px">
                                    <logic:notEmpty name="scannerList" >
                                    <logic:iterate id="scannerInfo" name="scannerList">
                                      <bean:define id="device" name="scannerInfo" property="device" />
                                      <bean:define id="model" name="scannerInfo" property="model" />
                                      
                                      <html:option value="<%=(String)device%>"><bean:write name="scannerInfo" property="model"/> </html:option>
                                      <html:hidden name="scannerConfigurationForm" property="cboScannerModel" value="<%=(String)model%>"/>
                                    </logic:iterate>
                                    </logic:notEmpty>
                                    <logic:empty name="scannerList" >
                                      <html:option value="0"><bean:message key="cbo.NoScannerFound" /></html:option>
                                    </logic:empty>
                                  </html:select>
                              </td>
                          </tr>
                          <tr>
                              <td width="221">
                                  <div align="right">
                                      <bean:message key="lbl.DPI"/>
                                  </div>
                              </td>
                              <td width="219">
                                  <html:select name="scannerConfigurationForm"
                                               property="cboDpi"
                                               styleClass="bdrColor_336666"
                                               style="width:125px">
                                      <html:option value="100">100</html:option>
                                      <html:option value="200">200</html:option>
                                      <html:option value="300">300</html:option>
                                      <html:option value="400">400</html:option>
                                      <html:option value="500">500</html:option>
                                  </html:select>
                              </td>
                          </tr>
                          <tr>
                              <td>
                                  <div align="right">
                                      <bean:message key="lbl.Mode"/>
                                  </div>
                              </td>
                              <td>
                                  <html:select name="scannerConfigurationForm"
                                               property="cboMode"
                                               styleClass="bdrColor_336666"
                                               style="width:125px">
                                      <html:option value="Color">Color</html:option>
                                      <html:option value="Lineart">B/W</html:option>
                                  </html:select>
                              </td>
                          </tr>
                          <tr>
                              <td>
                                  <div align="right">
                                      <bean:message key="lbl.JobFolder"/>
                                  </div>
                              </td>
                              <td>
                                  <html:text name="scannerConfigurationForm"
                                             property="fleJobFolder"
                                             styleClass="bdrColor_336666"
                                             style="width:125px"/>
                              </td>
                          </tr>
                          <tr>
                              <td>
                                  <div align="right">
                                      <bean:message key="lbl.DefaultOutputFormat"/>
                                  </div>
                              </td>
                              <td>
                                  <html:select name="scannerConfigurationForm"
                                               property="cboOutputFormat"
                                               styleClass="bdrColor_336666"
                                               style="width:125px">
                                      <html:option value="jpeg">jpeg</html:option>
                                      <html:option value="pdf">pdf</html:option>
                                      <html:option value="png">png</html:option>
                                      <html:option value="tiff">tiff</html:option>
                                  </html:select>
                              </td>
                          </tr>
        
                          <tr>
                          
                              <td>&nbsp;</td>
                              <td>
                                  <html:button property="btnOk" styleClass="buttons"
                                         style="width:60px;" onclick="callSubmit('commonOption');" ><bean:message key="btn.Ok" /></html:button>
                                  <html:cancel  styleClass="buttons"
                                          style="width:60px;"><bean:message key="btn.Cancel" /></html:cancel>
                              </td>
                          </tr>
                      </table>
                    </html:form>
                  </fieldset>
              <!--COMMON SCANNER OPTION ENDS-->                        
                  
              <!--SCANNER SPECIFIC OPTION STARTS-->
      
                  <fieldset id="scannerSpecificOption" class="bdrColor_336666" style="width:380px;height:280px;margin-left:20px;margin-right:20px;margin-top:15px;display:none" >
                    <legend>Scanner Specific Options:</legend>
                    <html:form action="/scannerSpecificConfigurationAction.do"  >
                    <html:hidden property="hdnOptionType" />
                      <table>
                        <tr>
                          <td align="center">
                            <div class="bdrColor_336666" style="align:center;margin-bottom:10px;overflow:auto;height:200px">
                              <table id="tblScannerSpecificOption"  width="345px" border="0" >
                                <tr>
                                  <th width="20px">&nbsp;</th>
                                  <th width="160px">Option</th>
                                  <th width="160px">Value</th>
                                </tr>
                                <logic:notEmpty name="scannerOptions" >
                                <!--Options For Text Box Starts-->
                                <%--
                                <logic:iterate id="scannerOptionAndHelp" name="scannerOptions" indexId="indxPrefix">
                                  <bean:define id="optionName" name="scannerOptionAndHelp" property="optionName" />
                                  <bean:define id="optionHelp" name="scannerOptionAndHelp" property="optionHelp" />
                                  <html:hidden property="hdnOptionName" value="<%=(String)optionName%>" />
                                  <tr>
                                    <td>
                                      <logic:notEmpty name="scannerOptionsFormFile" >
                                        <%boolean valurFound = false; %>
                                        <logic:iterate id="scannerOptionBean" name="scannerOptionsFormFile" indexId="index" >
                                          <logic:equal name="scannerOptionBean" property="optionName" value="<%=(String)optionName%>" >
                                            <input type="checkbox" id="chk<%=(String)optionName%>" name="chk<%=(String)optionName%>" checked />
                                            <% valurFound = true; %>
                                          </logic:equal>
                                        </logic:iterate>
                                        <%if(!valurFound){
                                        %>
                                          <input type="checkbox" id="chk<%=(String)optionName%>" name="chk<%=(String)optionName%>" />
                                        <%
                                        }
                                        %>
                                      </logic:notEmpty>
                                      <logic:empty name="scannerOptionsFormFile" >
                                        <input type="checkbox" id="chk<%=(String)optionName%>" name="chk<%=(String)optionName%>" />
                                      </logic:empty>
                                    </td>
                                    <td> 
                                      <bean:write name="scannerOptionAndHelp" property="optionName" />
                                    </td>
                                    <td>
                                      <logic:notEmpty name="scannerOptionsFormFile" >
                                        <%boolean valurFound = false; %>
                                        <logic:iterate id="scannerOptionBean" name="scannerOptionsFormFile" indexId="index" >
                                          <logic:equal name="scannerOptionBean" property="optionName" value="<%=(String)optionName%>" >
                                            <input type="text" id="<%=(String)optionName%>" name="<%=(String)optionName%>" title="<%=(String)optionHelp%>" value='<bean:write name="scannerOptionBean" property="optionValue" />'  class="bdrColor_336666" />
                                            <%valurFound = true; %>
                                          </logic:equal>
                                        </logic:iterate>
                                        <%if(!valurFound){
                                        %>
                                          <input type="text" id="<%=(String)optionName%>" name="<%=(String)optionName%>" title="<%=(String)optionHelp%>" class="bdrColor_336666" />
                                        <%
                                        }
                                        %>
                                      </logic:notEmpty>
                                      <logic:empty name="scannerOptionsFormFile" >
                                        <input type="text" id="<%=(String)optionName%>" name="<%=(String)optionName%>" title="<%=(String)optionHelp%>" class="bdrColor_336666" />
                                      </logic:empty>
                                    </td>
                                    <td>
                                    </td>
                                  </tr>
                                </logic:iterate>
                                --%>
                                <!--Options For Text Box Ends-->
                                
                                <!--Options For Combo Box Starts-->
                                <logic:iterate id="scannerOptionAndHelp" name="scannerOptions" indexId="indxPrefix">
                                  <bean:define id="optionName" name="scannerOptionAndHelp" property="optionName" />
                                  <bean:define id="optionHelp" name="scannerOptionAndHelp" property="optionHelp" />
                                  <html:hidden property="hdnOptionName" value="<%=(String)optionName%>" />
                                  <tr>
                                    <td>
                                      <logic:notEmpty name="scannerOptionsFormFile" >
                                        <%boolean valurFound = false; %>
                                        <logic:iterate id="scannerOptionBean" name="scannerOptionsFormFile" indexId="index" >
                                          <logic:equal name="scannerOptionBean" property="optionName" value="<%=(String)optionName%>" >
                                            <input type="checkbox" id="chk<%=(String)optionName%>" name="chk<%=(String)optionName%>" checked />
                                            <% valurFound = true; %>
                                          </logic:equal>
                                        </logic:iterate>
                                        <%if(!valurFound){
                                        %>
                                          <input type="checkbox" id="chk<%=(String)optionName%>" name="chk<%=(String)optionName%>" />
                                        <%
                                        }
                                        %>
                                      </logic:notEmpty>
                                      <logic:empty name="scannerOptionsFormFile" >
                                        <input type="checkbox" id="chk<%=(String)optionName%>" name="chk<%=(String)optionName%>" />
                                      </logic:empty>
                                    </td>
                                    <td> 
                                      <bean:write name="scannerOptionAndHelp" property="optionName" />
                                    </td>
                                    <td>
                                    <%
                                    /*
                                    String[] tempOptionHelpArray1=((String)optionHelp).split("[^0-9]");
                                    java.util.ArrayList tempOptionHelpArrayList1=new java.util.ArrayList();
                                      for(int j=0;j<tempOptionHelpArray1.length;j++){
                                        if(tempOptionHelpArray1[j].trim().length()>0){
                                          tempOptionHelpArrayList1.add(tempOptionHelpArray1[j].trim());
                                        }
                                      }
                                      for(int j=0;j<tempOptionHelpArrayList1.size();j++){
                                        out.println(tempOptionHelpArrayList1.get(j));  
                                      }
                                      
                                     out.print("Size: "+tempOptionHelpArrayList1.size());
                                     */
                                    %>
                                      <select id="<%=(String)optionName%>" name="<%=(String)optionName%>" class="bdrColor_336666" value='<bean:write name="scannerOptionBean" property="optionValue" />' style="width:125px" >
                                      <%
                                        try{
                                          String[] optionHelpArray=null;
                                          if(((String)optionHelp).indexOf("inactive")>=0){
                                            optionHelpArray=new String[]{"inactive"};
                                          }else if(((String)optionHelp).indexOf("..")>=0){
                                            String[] tempOptionHelpArray=((String)optionHelp).split("[^0-9]");
                                            java.util.ArrayList tempOptionHelpArrayList=new java.util.ArrayList();
                                            for(int j=0;j<tempOptionHelpArray.length;j++){
                                              if(tempOptionHelpArray[j].trim().length()>0){
                                                tempOptionHelpArrayList.add(tempOptionHelpArray[j].trim());
                                              }
                                            }
                                            int startVal=Integer.parseInt(((String)tempOptionHelpArrayList.get(0)));
                                            int endVal=Integer.parseInt(((String)tempOptionHelpArrayList.get(1)));
                                            System.out.println(startVal+" "+endVal);
                                            int diff=endVal-startVal;
                                            optionHelpArray=new String[diff+2];
                                            for(int i=startVal;i<=endVal;i++){
                                              int index=i-startVal;
                                              optionHelpArray[index]=String.valueOf(i);
                                            }
                                            
                                          }
                                          else{
                                            optionHelpArray=((String)optionHelp).split("[^..^0-9^a-z^A-Z]");
                                          }
                                          if(!optionHelpArray[0].equalsIgnoreCase("inactive")){
                                            for(int i=0;i<optionHelpArray.length-1;i++){
                                              String parsedOptionName=((String)optionHelpArray[i]).trim();
                                              if(parsedOptionName.length()!=0){
                                      %>
                                              <logic:notEmpty name="scannerOptionsFormFile" >
                                                <%boolean valurFound = false; %>
                                                <logic:iterate id="scannerOptionBean" name="scannerOptionsFormFile" indexId="index" >
                                                  <logic:equal name="scannerOptionBean" property="optionName" value="<%=(String)optionName%>" >
                                                    <logic:equal name="scannerOptionBean" property="optionValue" value="<%=(String)parsedOptionName%>" >
                                                      <option value='<%=(String)parsedOptionName%>' selected="true" > <%=(String)parsedOptionName%></option>
                                                      <%valurFound = true; %>
                                                    </logic:equal>
                                                  </logic:equal>
                                                </logic:iterate>
                                                <%if(!valurFound){
                                                %>
                                                  <option value='<%=(String)parsedOptionName%>' > <%=(String)parsedOptionName%></option>
                                                <%
                                                }
                                                %>
                                              </logic:notEmpty>
                                              <logic:empty name="scannerOptionsFormFile" >
                                                <option value='<%=(String)parsedOptionName%>' > <%=(String)parsedOptionName%></option>
                                              </logic:empty>
                                      <%
                                              }
                                            }
                                          }else{
                                      %>
                                            <option value='<%=(String)optionHelpArray[0]%>' > <%=(String)optionHelpArray[0]%></option>
                                      <%
                                          }
                                        }catch(Exception e){
                                          System.out.println(e.toString());
                                          e.printStackTrace();
                                        }
                                      %>
                                      </select>
                                    </td>
                                    <td>
                                    </td>
                                  </tr>
                                </logic:iterate>
                                <!--Options For Combo Box Ends-->
                                </logic:notEmpty>
                                <logic:empty name="scannerOptions" >
                                  <tr>
                                    <td colspan="3" align="center" height="100px">
                                      <bean:message key="info.no_item_found" />
                                    </td>
                                  </tr>
                                </logic:empty>
                                <tr><td colspan="3">&nbsp;</td></tr>
                              </table>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <td align="right">
                            <html:button property="btnOk" styleClass="buttons" style="width:60px;" onclick="callSubmit('scannerSpecificOption');" ><bean:message key="btn.Ok" /></html:button>
                            <html:cancel  styleClass="buttons" style="width:60px;"><bean:message key="btn.Cancel" /></html:cancel>
                          </td>
                        </tr>
                      </table>
                    </html:form>
                  </fieldset>
                  <!--SCANNER SPECIFIC OPTION ENDS-->
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            
        </table>
            <p>&nbsp;</p>
            
            
      </body>
</html>

  