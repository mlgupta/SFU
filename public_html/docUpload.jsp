<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>Doc Upload</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<html:javascript formName="docUploadForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
<script src="datepicker.js"></script>
<script src="useragent.js"></script>
<script src="treeview.js"></script>
<script src="/temp/ReplicateTree.js"></script>
<script src="general.js"></script>
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
  openWindow("folderDocSelectB4Action.do?heading=<bean:message key="lbl.ChoosePath" />&foldersOnly=true&openerControl=txtDestinationFolderPath&rootFolder=<bean:write name="docUploadForm" property="txtDestinationFolderPath" />","searchLookUp",495,390,0,0,true);
  document.forms[0].target='searchLookUp';
}

//-->
</script>
<script>
    var dateAndTime = new Calendar("dateAndTime",0,0);
    dateAndTime.onclick="setDateValues()";
    dateAndTime.onclear="clearDateValues()";
    dateAndTime.clearDisabled="true";
    //dateAndTime.onclick="selectedValues(dateAndTime,'txtDateAndTime')";
    //dateAndTime.onclear="clearValues('txtDateAndTime')";
    //dateAndTime.timezoneDisabled=true;
    dateAndTime.noTimezone=true;
    //dateAndTime.noTime=true;
    dateAndTime.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    dateAndTime.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    dateAndTime.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    dateAndTime.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    dateAndTime.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    dateAndTime.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    dateAndTime.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    dateAndTime.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    dateAndTime.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    dateAndTime.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    dateAndTime.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    dateAndTime.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    dateAndTime.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    //dateAndTime.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
    function setDateValues(){
      document.forms[0].day.value=dateAndTime.getDay();
      document.forms[0].month.value=dateAndTime.getMonth();
      document.forms[0].year.value=dateAndTime.getYear();
      document.forms[0].hours.value=dateAndTime.getHours();
      document.forms[0].minutes.value=dateAndTime.getMinutes();
      document.forms[0].timezone.value=dateAndTime.getTimezone();
      document.forms[0].seconds.value=dateAndTime.getSeconds();
      
    }
    function  selectedValues(dtPickerObj, txtDateTimeName){
      var dateString=dtPickerObj.getMonth();
      dateString+="/" +dtPickerObj.getDay();
      dateString+="/" +dtPickerObj.getYear();
      dateString+=" " +dtPickerObj.getHours();
      dateString+=":" +dtPickerObj.getMinutes();
      dateString+=":" +dtPickerObj.getSeconds();
      eval("document.forms[0]."+txtDateTimeName).value=dateString;
    }  

    function  clearValues(txtDateTimeName){
        eval("document.forms[0]."+txtDateTimeName).value="";
    }
        
</script>
<script>
  
  function callSubmit(thisForm){
    if(validateDocUploadForm(thisForm)){
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
      thisForm.submit();
    }
  }
  
  function onRadSelect(){
    thisForm=document.forms[0];
    
    if(thisForm.radNoOfDocuments[0].checked==true){
      thisForm.txtDocNo.disabled=true;
    }else if(thisForm.radNoOfDocuments[1].checked==true){
      thisForm.txtDocNo.disabled=false;
    }
    
    if(thisForm.radNamingMethod[0].checked==true){
      thisForm.txtDocName.disabled=false;
      //thisForm.txtDocSuffix.disabled=false;  -----to be uncommented-----
      //thisForm.chkDocSuffix.disabled=false;  -----to be uncommented-----
      //onChkSuffixSelect();   -----to be uncommented-----
    }else if(thisForm.radNamingMethod[1].checked==true){
      thisForm.txtDocName.disabled=true;
      //thisForm.txtDocSuffix.disabled=true;    -----to be uncommented-----
      //thisForm.chkDocSuffix.disabled=true;      -----to be uncommented-----
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
  
</script>
<script>
  function window_onload(){
    thisForm=document.forms[0];
    //For Date
    MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_mail_ovr.gif','images/tab_fax_ovr.gif')
    var currentDate=new Date();
    dateAndTime.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
    dateAndTime.initialize();
    dateAndTime.click();
    //For Radio Button Selection
    thisForm.radNoOfDocuments[0].checked=true;
    thisForm.txtDocNo.disabled=true;
    thisForm.radNamingMethod[0].checked=true;
    //For Check Box Selection
    //thisForm.chkDocSuffix.checked=true; to be uncommented
  }
  
</script>
</head>
<body onLoad="return window_onload();">
            <html:form action="uploadAction.do">
            <html:hidden  property="day"  />
            <html:hidden  property="month"  />
            <html:hidden  property="year"  />
            <html:hidden  property="hours"  />
            <html:hidden  property="minutes"  />
            <html:hidden  property="timezone"  />
            <html:hidden  property="seconds"  />
<table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>
      <table id="topBar" width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="250"><img src="images/sfu_logo.jpg" width="250" height="50"></td>
        <td valign="bottom" align="right" background="images/tile_topbar.jpg">
            <a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)"><img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0"></a>
			<a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)"><img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0"></a>
			            <a href="logoutAction.do"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
			<img src="images/bt_logout.jpg" alt="Logout" name="Logout" width="52" height="40" border="0"></a>
        </td>
          <td width="13"><img src="images/topbar_right.jpg" width="13" height="50"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="510px" valign="top" class="bgColor_D8EAEC bdrColor_336666">
      <table width="725px" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
            <table width="725" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="100"><img src="images/tab_doc_upload.gif" alt="Doc Upload" name="DocUpload" width="100" height="25" border="0"></td>
                <td width="55" class="bdrBottomColor_336666"><a href="mailB4Action.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Mail','','images/tab_mail_ovr.gif',1)"><img src="images/tab_mail.gif" alt="Mail" name="Mail" width="55" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a href="faxB4Action.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Fax','','images/tab_fax_ovr.gif',1)"><img src="images/tab_fax.gif" alt="Fax" name="Fax" width="55" height="25" border="0"></a></td>
                <td class="bdrBottomColor_336666">&nbsp;</td>
                </tr>
            </table>
          </td>
        </tr>
		
        <tr>
          <td height="440px" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
              <table width="69%"  border="0" align="center" class="bdrColor_336666">
                <tr>
                  <td colspan="2" height="10px"></td>
                </tr>  
                <tr>
                  <td width="28%">&nbsp;</td>
                  <td width="69%">
                    <html:radio property="radNoOfDocuments" value="all" onclick="onRadSelect();" />
                    <bean:message key="lbl.AllDocuments" />
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <html:radio  property="radNoOfDocuments"  value="notAll" onclick="onRadSelect();" />
                    <bean:message key="lbl.First"/>
                    <html:text  property="txtDocNo" styleClass="bdrColor_336666" size="2" onkeypress="return integerOnly(event);" maxlength="2" />
                    <bean:message key="lbl.Documents"/>					</td>
                </tr>
                <tr>
                  <td valign="top">&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td valign="top">&nbsp;                  </td>
                  <td>
                    <table width="100%"  border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="165">
                          <html:radio  property="radNamingMethod"  value="notAuto" onclick="onRadSelect();"/>
                          <bean:message key="lbl.DocumentsNamedAs"/>						
                        </td>
                        <td width="147">
                          <html:text property="txtDocName" styleClass="bdrColor_336666" style="width:130px" />
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <html:radio  property="radNamingMethod" value="auto" onclick="onRadSelect();"/>
                    <bean:message key="lbl.AutoNaming"/>				  
                  </td>
                </tr>
                <logic:equal name="docUploadForm" property="hdnOutputFormat" value="pdf">
                 <tr>
                  <td>&nbsp;</td>
                  <td>
                    <html:checkbox property="chkSingleFile" />
                    <bean:message key="lbl.CreateSingleFile" />				  </td>
                </tr>
                </logic:equal>
                <tr>
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td align="right">&nbsp;
                  </td>
                  <td>
                    <span style="float:left; margin-right:10px"><bean:message key="lbl.UploadDate&Time" />:</span>
                    <script>dateAndTime.render();</script>
                    <html:hidden name="docUploadForm" property="txtDateAndTime" />
                  </td>
                  <td width="3%">&nbsp;</td>
                </tr>
                <tr>
                  <td align="right">
                      <bean:message key="lbl.DestinationFolder"/>:
                  </td>
                  <td>
                    <html:text property="txtDestinationFolderPath" styleClass="bdrColor_336666" size="10" style="width:287px" />
                    <html:button  property="btnLookup" onclick="lookuponclick();" styleClass="buttons"  value="..." style="width:20px; height:17px;" ></html:button>
        				  </td>
                </tr>
                <tr>
                  <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td align="right">
                    <html:button  property="btnOk" styleClass="buttons" onclick="callSubmit(this.form)" style="width:60px"><bean:message key="btn.Ok" /></html:button>
                    <html:reset property="btnClear" styleClass="buttons"  style="width:60px; "><bean:message key="btn.Clear" /></html:reset>
                    <html:cancel  styleClass="buttons"  style="width:60px; margin-right:25px"><bean:message key="btn.Cancel" /></html:cancel>
                  </td>
                </tr>
                <tr>
                  <td colspan="2" height="10px"></td>
                </tr>
              </table>
            
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr><td height="2px"></td></tr>
  <tr>
	<td>
	  <!-- Status Bar Table Starts-->
	  <table id="tblStatusBar" align="center" width="100%" height="2  3px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
		<tr class="bgColor_D8EAEC bdrColor_336666 ">
		  <td width="30px" >
			<div class="imgStatusMsg"/>
		  </td>
		  <td>&nbsp;<html:messages id="msg1" message="false" ><bean:write name="msg1" /></html:messages></td>
		</tr>
	  </table>
	  <!-- Status Bar Table Ends-->
	</td>
  </tr>
</table>
</html:form>
</body>
</html>
