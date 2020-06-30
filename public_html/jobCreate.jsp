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
    <title>Job Create</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
    <link href="main.css" rel="stylesheet" type="text/css"></link>
    
    <script type="text/javascript" src="datepicker.js"></script>
    <script type="text/javascript" src="useragent.js"></script>
    <script type="text/javascript" src="treeview.js"></script>
    <script type="text/javascript" src="/temp/ReplicateTree.js"></script>
    <script type="text/javascript" src="general.js"></script>
    <script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function lookuponclick(){
  openWindow("folderDocSelectB4Action.do?heading=<bean:message key="lbl.ChoosePath" />&foldersOnly=true&openerControl=txtDestinationFolderPath&rootFolder=<bean:write name="jobCreateForm" property="txtDestinationFolderPath" />","searchLookUp",495,390,0,0,true);
  document.forms[0].target='searchLookUp';
}

//-->
</script>
    <script type="text/javascript">
    var jobExecDateAndTime = new Calendar("jobExecDateAndTime",0,0);
    jobExecDateAndTime.onclick="setJobExecDateValues()";
    jobExecDateAndTime.onclear="clearDateValues()";
    jobExecDateAndTime.clearDisabled="true";
    jobExecDateAndTime.noTimezone=true;
    jobExecDateAndTime.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    jobExecDateAndTime.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    jobExecDateAndTime.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    jobExecDateAndTime.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    jobExecDateAndTime.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    jobExecDateAndTime.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    jobExecDateAndTime.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    jobExecDateAndTime.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    jobExecDateAndTime.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    jobExecDateAndTime.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    jobExecDateAndTime.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    jobExecDateAndTime.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    jobExecDateAndTime.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    
    function setJobExecDateValues(){
      document.forms[0].day.value=jobExecDateAndTime.getDay();
      document.forms[0].month.value=jobExecDateAndTime.getMonth();
      document.forms[0].year.value=jobExecDateAndTime.getYear();
      document.forms[0].hours.value=jobExecDateAndTime.getHours();
      document.forms[0].minutes.value=jobExecDateAndTime.getMinutes();
      //document.forms[0].timezone.value=jobExecDateAndTime.getTimezone();
      document.forms[0].seconds.value=jobExecDateAndTime.getSeconds();
      
    }
    //function  selectedValues(dtPickerObj, txtDateTimeName){
    //  var dateString=dtPickerObj.getMonth();
    //  dateString+="/" +dtPickerObj.getDay();
    //  dateString+="/" +dtPickerObj.getYear();
    //  dateString+=" " +dtPickerObj.getHours();
    //  dateString+=":" +dtPickerObj.getMinutes();
    //  dateString+=":" +dtPickerObj.getSeconds();
    //  eval("document.forms[0]."+txtDateTimeName).value=dateString;
    //}  

    function  clearValues(txtJobExecDateTimeName){
        eval("document.forms[0]."+txtJobExecDateTimeName).value="";
    }
    </script>

 
            
</script>
    <script type="text/javascript">
  
  function callSubmit(thisForm){
    
      if(thisForm.txtDocNo.disabled==false && trim(thisForm.txtDocNo.value).length==0){
        alert('<bean:message key="alert.NoOfDocRequired" />');
        thisForm.txtDocNo.focus();
        return false;
      }  
      if(thisForm.txtDocName.disabled==false && trim(thisForm.txtDocName.value).length==0){
        alert('<bean:message key="alert.DocNameRequired" />');
        thisForm.txtDocName.focus();
        return false;
      }
      if(thisForm.chkUpload.checked==false && thisForm.chkMail.checked==false && thisForm.chkFax.checked==false){
        alert("Select Any Job.");
        return false;
      }
      if(thisForm.chkUpload.checked==true ){
        if(trim(thisForm.txtDestinationFolderPath.value).length==0){
          alert("<bean:message key="alert.DestinationFolderRequired" />");
          thisForm.txtDestinationFolderPath.focus();
          return false;
        }
      }
      if(thisForm.chkMail.checked==true ){
        if(trim(thisForm.txtMailTo.value).length==0){
          alert("<bean:message key="alert.MailToAddressRequired" />");
          thisForm.txtMailTo.focus();
          return false;
        }
      }
      if(thisForm.chkFax.checked==true ){
        if(trim(thisForm.txtFaxNumber.value).length==0){
          alert("<bean:message key="alert.FaxNumberRequired" />");
          thisForm.txtFaxNumber.focus();
          return false;
        }
      }
      thisForm.submit();
  }
  
  function onRadNameSelect(){
    thisForm=document.forms[0];
    
    if(thisForm.radNamingMethod[0].checked==true){
      thisForm.txtDocName.disabled=false;
      thisForm.txtDocName.focus();
    }else if(thisForm.radNamingMethod[1].checked==true){
      thisForm.txtDocName.disabled=true;
    }
  }
  function onRadDocNoSelect(){
    thisForm=document.forms[0];
    
    if(thisForm.radNoOfDocuments[0].checked==true){
      thisForm.txtDocNo.disabled=true;
    }else if(thisForm.radNoOfDocuments[1].checked==true){
      thisForm.txtDocNo.disabled=false;
      thisForm.txtDocNo.focus();
      thisForm.txtDocNo.value="1";
    }
    
  }
  
  function onChkSuffixSelect(){
    if(thisForm.radNamingMethod[0].checked==true){
      if(thisForm.chkDocSuffix.checked==true){
        thisForm.txtDocSuffix.disabled=false;
      }else{
        thisForm.txtDocSuffix.disabled=true;
      }
    }
  }
  
  function onChkJobSelect(){
    thisForm=document.forms[0];
    if(thisForm.chkUpload.checked==false){
      thisForm.txtDestinationFolderPath.disabled=true;
      thisForm.btnLookup.disabled=true;
    }else{
      thisForm.txtDestinationFolderPath.disabled=false;
      thisForm.btnLookup.disabled=false;
    }
    
    if(thisForm.chkMail.checked==false){
      thisForm.txtMailTo.disabled=true;
      thisForm.txtCc.disabled=true;
      thisForm.txtBcc.disabled=true;
      thisForm.txtSubject.disabled=true;
      thisForm.txaMail.disabled=true;
    }else{
      thisForm.txtMailTo.disabled=false;
      thisForm.txtCc.disabled=false;
      thisForm.txtBcc.disabled=false;
      thisForm.txtSubject.disabled=false;
      thisForm.txaMail.disabled=false;
    }
    
    if(thisForm.chkFax.checked==false){
      thisForm.txtFaxTo.disabled=true;
      thisForm.txtCompanyName.disabled=true;
      thisForm.txtFaxNumber.disabled=true;
      thisForm.txaFax.disabled=true;
    }else{
      thisForm.txtFaxTo.disabled=false;
      thisForm.txtCompanyName.disabled=false;
      thisForm.txtFaxNumber.disabled=false;
      thisForm.txaFax.disabled=false;
    }
  }
   function onFormatSelect(){
    thisForm=document.forms[0];
    if(thisForm.cboOutputFormat.value=="pdf"){
      thisForm.chkSingleFile.disabled=false;
    }else{
      thisForm.chkSingleFile.disabled=true;
    }
   }
</script>
    <script type="text/javascript">
  function window_onload(){
    thisForm=document.forms[0];
    //For Date
    MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_mail_ovr.gif','images/tab_fax_ovr.gif')
    var currentDate=new Date();
    
    //update
    
    jobExecDateAndTime.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
    jobExecDateAndTime.initialize();
    jobExecDateAndTime.click();
    
    //For Radio Button Selection
    if(!thisForm.radNoOfDocuments[0].checked && !thisForm.radNoOfDocuments[1].checked){
      thisForm.radNoOfDocuments[0].checked=true;
    }
    if(thisForm.radNoOfDocuments[0].checked){
      //thisForm.radNoOfDocuments[0].checked=true;
      thisForm.txtDocNo.disabled=true;
    }
    if(thisForm.radNoOfDocuments[1].checked){
      thisForm.txtDocNo.disabled=false;
      if(trim(thisForm.txtDocNo.value).length==0){
        thisForm.txtDocNo.value="1";
      }
      
    }
    
    if(!thisForm.radNamingMethod[0].checked && !thisForm.radNamingMethod[1].checked){
      thisForm.radNamingMethod[0].checked=true;
    }
    if(thisForm.radNamingMethod[0].checked){
      //thisForm.radNamingMethod[0].checked=true;
      thisForm.txtDocName.focus();
      thisForm.txtDocNo.value="1";
    }
    if(thisForm.radNamingMethod[1].checked){
      //thisForm.radNamingMethod[0].checked=true;
      thisForm.txtDocName.disabled=true;
      //thisForm.txtDocNo.value="1";
    }
    
    
    //For Job CheckboxSelection
    onChkJobSelect();
    //refreshFrame();
  }

  function refreshFrame(){
      frames[0].location.replace('http://192.168.0.6:8989/dbsentrySFU-sfuProj-context-root/listProcessStatusAction.do');
      setTimeout("refreshFrame()", 2000);
  }

</script>
  </head>
  <body onLoad="return window_onload();">
      <html:form action="/jobCreateAction.do">
      
      <html:hidden property="hdnCreator"/>
      <html:hidden property="hdnJobRetrialCount"/>
      <html:hidden property="hdnErrorMesg"/>
      <html:hidden property="hdnJobMaxCount"/>
      <html:hidden property="hdnJobRetrialInterval"/>
      <html:hidden property="hdnJobErrorMessage"/>
      <html:hidden property="hdnMode"/>
      <html:hidden property="hdnDpi"/>
      <html:hidden property="hdnJobFolder"/>
      <html:hidden property="hdnSenderEmailAddress"/>
      <html:hidden property="smtpHost"/>
      
      <html:hidden property="day"/>
      <html:hidden property="month"/>
      <html:hidden property="year"/>
      <html:hidden property="hours"/>
      <html:hidden property="minutes"/>
      <html:hidden property="seconds"/>
      
      <html:hidden property="hdnDevice"/>
      
      <table id="outerMost" width="900px" border="0" align="center"
             cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
            <table id="topBar" width="100%" height="50px" border="0"
                   cellpadding="0" cellspacing="0">
              <tr>
                <td width="250">
                  <img src="images/sfu_logo.jpg" alt="sfu_logo.jpg" width="250"
                       height="50"></img>
                </td>
                <td valign="bottom" align="right" class="imgTileTopbar" >
                  <!--<a href="login.jsp" onMouseOut="MM_swapImgRestore()"
                     onmouseover="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)">
                    <img src="images/bt_home.jpg" alt="Home" name="Home"
                         width="52" height="40" border="0"></img>
                  </a>-->
                  <!--
                  <a href="" onMouseOut="MM_swapImgRestore()"
                     onmouseover="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)">
                    <img src="images/bt_refresh.jpg" alt="Refresh"
                         name="Refresh" width="52" height="40" border="0"></img>
                  </a>
                  -->
                  <a href="logoutAction.do" onMouseOut="MM_swapImgRestore()"
                     onmouseover="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
                    <img src="images/bt_logout.jpg" alt="Logout" name="Logout"
                         width="52" height="40" border="0"></img>
                  </a>
                </td>
                <td width="13">
                  <img src="images/topbar_right.jpg" alt="topbar_right.jpg"
                       width="13" height="50"></img>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td height="523px" valign="top"
              class="bgColor_D8EAEC bdrColor_336666">
            <table width="850px" border="0" align="center" cellpadding="0"
                   cellspacing="0">
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>
                  <table width="850" align="center" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="100">
                        <img src="images/tab_create_job.gif" alt="Create Job"
                             name="CreateJob" width="100" height="25"
                             border="0"></img>
                        <!-- <img src="images/tab_doc_upload.gif" alt="Doc Upload"
                             name="DocUpload" width="100" height="25"
                             border="0"></img> -->
                      </td>
                      <td width="55" class="bdrBottomColor_336666">
                        <a href="jobRelayAction.do?operation=search_scheduler_user&txtSearchByUserName=<bean:write name='userProfile' property='userId' /> "
                           onmouseout="MM_swapImgRestore()"
                           onmouseover="MM_swapImage('Mail','','images/tab_mail_ovr.gif',1)">
                        <img src="images/tab_my_jobs.gif" alt="My Jobs" name="MyJobs"
                               width="100" height="25" border="0"></img>
                        <!-- <img src="images/tab_mail.gif" alt="Mail" name="Mail"
                               width="55" height="25" border="0"></img> -->
                        </a>
                      </td>
                      
                      <td class="bdrBottomColor_336666">&nbsp;</td>
                      
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="450px"
                    class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
                  <table width="800px" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td colspan="2" align="right" >
                      <div style="margin-top:5px">
                        <table border="0">
                          <tr>
                            <td align="right" height="20px">
                              <bean:message key="lbl.JobExecutesOn"/>:
                            </td>
                            <td>
                              <script type="text/javascript">jobExecDateAndTime.render();</script>
                              <html:hidden name="jobCreateForm" property="txtJobExecDateAndTime"/>
                            </td>
                          </tr>
                        </table>
                      </div>
                      </td>
                    </tr>
                    <tr>
                      <td id="Scandoclegend" colspan="2" height="10px">
                        <fieldset style="margin-top:5px">
                          <legend>Scaning Parameters[ <bean:write name="jobCreateForm" property="hdnModel" /> ]:</legend>
                          <table width="100%" border="0" cellspacing="1" cellpadding="1">
                            <tr>
                              <td width="4%" align="right">
                                <html:radio property="radNoOfDocuments" value="all"
                                onclick="onRadDocNoSelect();"/>						
                              </td>
                              <td width="22%">
                                <bean:message key="lbl.AllDocuments"/>
                              </td>
                              <td width="3%" align="right">
                                <html:radio property="radNamingMethod" value="notAuto"
                                onclick="onRadNameSelect();"/>
                              </td>
                              <td width="38%">
                                <bean:message key="lbl.DocumentsNamedAs"/>:
                                <html:text property="txtDocName" styleClass="bdrColor_336666" style="width:130px"/>
                              </td>
                              <td width="14%" align="right">
                                <bean:message key="lbl.OutputFormat"/>:
                              </td>
                              <td width="19%">
                                <html:select name="jobCreateForm" property="cboOutputFormat" styleClass="bdrColor_336666" style="width:125px; margin-left:4px" onchange="onFormatSelect();">
                                  <html:option value="jpeg">jpeg</html:option>
                                  <html:option value="pdf">pdf</html:option>
                                  <html:option value="png">png</html:option>
                                  <html:option value="tiff">tiff</html:option>
                                </html:select>
                              </td>
                            </tr>
                            <tr>
                              <td align="right">
                                <html:radio property="radNoOfDocuments" value="notAll" onclick="onRadDocNoSelect();"/>
                              </td>
                              <td>
                                <bean:message key="lbl.First"/>
                                <html:text property="txtDocNo" styleClass="bdrColor_336666" size="2" onkeypress="return integerOnly(event);" maxlength="2"/>
                                <bean:message key="lbl.Documents"/>
                              </td>
                              <td align="right">
                                <html:radio property="radNamingMethod" value="auto" onclick="onRadNameSelect();"/>
                              </td>
                              <td>
                                <bean:message key="lbl.AutoNaming"/>
                              </td>
                              <td align="right">&nbsp;</td>
                              <td>
                                <logic:equal name="jobCreateForm" property="cboOutputFormat" value="pdf">
                                  <html:checkbox property="chkSingleFile"/>
                                </logic:equal>
                                <logic:notEqual name="jobCreateForm" property="cboOutputFormat" value="pdf">
                                  <html:checkbox property="chkSingleFile" disabled="true"/>
                                </logic:notEqual><bean:message key="lbl.CreateSingleFile"/>
                              </td>
                            </tr>
                          </table>
                        </fieldset>
                      </td>
                    </tr>
                    <tr>
                      <td width="50%"></td>
                      <td width="50%"></td>
                    </tr>
                    <tr>
                      <td width="50%"></td>
                      <td width="50%"></td>
                    </tr>
                    <tr>
                      <td>
                      </td>
                      <td>
                      </td>
                    </tr>
                    <tr>
                      <td colspan="2" align="center">
                        <fieldset style="height:50px;margin-top:5px">
                          <legend>Upload:</legend>
                          <table width="100%" border="0" cellspacing="1" cellpadding="1">
                            <tr>
                              <td width="17%">
                                <html:checkbox property="chkUpload"
                                value="Upload" onchange="onChkJobSelect();"/>
                                <bean:message key="lbl.Upload"/>
                              </td>
                              <td width="28%">&nbsp;</td>
                              <td width="17%">&nbsp;</td>
                              <td width="35%">&nbsp;</td>
                              <td width="3%">&nbsp;</td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.DestinationFolder"/>:</td>
                              <td>
                                <html:text property="txtDestinationFolderPath" styleClass="bdrColor_336666" size="10" style="width:267px" disabled="true"/>
                              </td>
                              <td>
                                <html:button property="btnLookup" onclick="lookuponclick();" styleClass="buttons" value="..." style="width:20px; height:17px;" disabled="true"/>
                              </td>
                              <td align="right"> &nbsp; </td>
                              <td> &nbsp; </td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>
                          </table>
                        </fieldset>
                      </td>
                    </tr>
                    <tr>
                      <td id="mailLegend" valign="top">
                        <fieldset style="margin-top:5px">
                          <legend>Mail:</legend>
                          <table border="0" cellspacing="1" cellpadding="1">
                            <tr>
                              <td width="32%">
                                <html:checkbox property="chkMail" value="Mail" onchange="onChkJobSelect();"/>
                                <bean:message key="lbl.Mail"/>
                              </td>
                              <td width="68%">&nbsp;</td>
                            </tr>
						  
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.To"/>:
                              </td>
                              <td>
                                <html:text property="txtMailTo" styleClass="bdrColor_336666" style="width:250px;" disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.Cc"/>:
                              </td>
                              <td>
                                <html:text property="txtCc"
                                           styleClass="bdrColor_336666"
                                           style="width:250px;"
                                           disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.Bcc"/>
                                :
                              </td>
                              <td>
                                <html:text property="txtBcc"
                                           styleClass="bdrColor_336666"
                                           style="width:250px;"
                                           disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.Subject"/>
                                :
                              </td>
                              <td>
                                <html:text property="txtSubject"
                                           styleClass="bdrColor_336666"
                                           style="width:250px;"
                                           disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right" valign="top">
                                <bean:message key="lbl.MailMessage"/>
                                :
                              </td>
                              <td>
                                <textarea name="txaMail" cols="5"
                                          style="width:250px; height:88px"
                                          class="bdrColor_336666"
                                          disabled="disabled"></textarea>
                              </td>
                            </tr>
                          </table>
                        </fieldset>
                      </td>
                      <td id="FaxLegend" valign="top">
                        <fieldset style="margin-top:5px">
                          <legend>Fax:</legend>
                          <table width="100%" border="0" cellspacing="1" cellpadding="1">
                            <tr>
                              <td width="32%">
                                <html:checkbox property="chkFax" value="Fax"
                                               onchange="onChkJobSelect();"/>
                                <bean:message key="lbl.Fax"/>
                              </td>
                              <td width="68%">&nbsp;</td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.To"/>
                                :
                              </td>
                              <td>
                                <html:text property="txtFaxTo"
                                           styleClass="bdrColor_336666"
                                           style="width:250px;"
                                           disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.CompanyName"/>
                                :
                              </td>
                              <td>
                                <html:text property="txtCompanyName"
                                           styleClass="bdrColor_336666"
                                           size="10" style="width:250px"
                                           disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right">
                                <bean:message key="lbl.FaxNumber"/>:
                              </td>
                              <td>
                                <html:text property="txtFaxNumber"
                                           styleClass="bdrColor_336666"
                                           size="10" style="width:250px"
                                           disabled="true"/>
                              </td>
                            </tr>
                            <tr>
                              <td align="right" valign="top">
                                <bean:message key="lbl.FaxMessage"/>
                                :
                              </td>
                              <td valign="top">
                                <textarea name="txaFax" cols="5"
                                          style="width:250px; height:108px"
                                          class="bdrColor_336666"
                                          disabled="disabled"></textarea>
                              </td>
                            </tr>
                          </table>
                        </fieldset>
                      </td>
                    </tr>
                    <tr>
                    <!--
                      <td align="right">
                        <table>
                          <tr>
                          <td align="right">
                            <bean:message key="lbl.JobExecutesOn"/>:
                          </td>
                          <td>
                            <script type="text/javascript">jobExecDateAndTime.render();</script>
                            <html:hidden name="jobCreateForm" property="txtJobExecDateAndTime"/>
                          </td>
                          </tr>
                        </table>
                      </td>
                    -->
                      <td width="50%" height="35px">&nbsp;</td>
                      <td align="right" width="50%">
                        <html:button property="btnOk" styleClass="buttons"
                                     onclick="callSubmit(this.form)"
                                     style="width:60px">
                        <bean:message key="btn.Ok"/>
                        </html:button>
                        <html:reset property="btnClear" styleClass="buttons"
                                    style="width:60px;">
                        <bean:message key="btn.Clear"/>
                        </html:reset>
                        <html:cancel styleClass="buttons"
                                     style="width:60px; margin-right:2px">
                        <bean:message key="btn.Cancel"/>
                        </html:cancel>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td height="2px"></td>
        </tr>
        <logic:messagesPresent message="true" >
        <tr>
          <td height="23px" class="bgColor_D8EAEC bdrColor_336666 ">
            <!-- Status Bar Table Starts-->
            <table id="tblStatusBar" align="center" width="100%" height="23px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
              <tr class="bgColor_D8EAEC bdrColor_336666 ">
                <td width="30px" >
                  <div class="imgStatusMsg"/>
                </td>
                <td width="870px">
                <logic:messagesPresent  message="true">
                <div align="left" style="color:red">
                   <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
                </div>
                </logic:messagesPresent>
                </td>
              </tr>
            </table>
            <!-- Status Bar Table Ends-->
          </td>
        </tr>
        </logic:messagesPresent>
        <logic:messagesNotPresent message="true">
        <tr>
          <td height="23px" class="bgColor_D8EAEC bdrColor_336666 ">
            <table id="tblStatusBar" align="center" border="0" width="100%" height="23px"
                   border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
              <tr class="bgColor_D8EAEC bdrColor_336666 ">
                <td height="23px" >
                 <iframe src="listProcessStatus.jsp" frameborder="0" width="100%" height="23px" scrolling="no"></iframe>
                 <!--<iframe src="listProcessStatusAction.do" frameborder="0" width="100%" height="23px" scrolling="no"></iframe>-->
                </td>
              </tr>
            </table>
          </td>
        </tr>
        </logic:messagesNotPresent>
      </table>
    </html:form>
  </body>
</html>