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
<title>Mail</title>
<link href="main.css" rel="stylesheet" type="text/css">
<html:javascript formName="mailForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
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
//-->
</script>

<script src="general.js"></script>
<script src="datepicker.js"></script>
<script src="timezones.js"></script> 

<script>
  var datePicker = new Calendar("datePicker",0,0);
  datePicker.onclick="setDateValues()";
  datePicker.onclear="clearDateValues()";
  datePicker.clearDisabled="true";
  datePicker.noTimezone=true;
  datePicker.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
  datePicker.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
  datePicker.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
  datePicker.tooltipDay='<bean:message key="tooltips.cal.Day" />';
  datePicker.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
  datePicker.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
  datePicker.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
  datePicker.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
  datePicker.tooltipNow='<bean:message key="tooltips.cal.Now" />';
  datePicker.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
  datePicker.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
  datePicker.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
  datePicker.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
  datePicker.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
  function setDateValues(){
      document.forms[0].day.value=datePicker.getDay();
      document.forms[0].month.value=datePicker.getMonth();
      document.forms[0].year.value=datePicker.getYear();
      document.forms[0].hours.value=datePicker.getHours();
      document.forms[0].minutes.value=datePicker.getMinutes();
      document.forms[0].timezone.value=datePicker.getTimezone();
      document.forms[0].seconds.value=datePicker.getSeconds();
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

  function clearDateValues(){
      document.forms[0].day.value="";
      document.forms[0].month.value="";
      document.forms[0].year.value="";
      document.forms[0].hours.value="";
      document.forms[0].minutes.value="";
      document.forms[0].seconds.value="";
      document.forms[0].timezone.value="";
  }
        
</script>
<script>
  
  function callSubmit(thisForm){
    if(validateMailForm(thisForm)){
      if(thisForm.txtDocNo.disabled==false && trim(thisForm.txtDocNo.value).length==0){
        alert('<bean:message key="alert.NoOfDocRequired" />');
        thisForm.txtDocNo.focus();
        return false;
      }
      if(thisForm.txtName.disabled==false && trim(thisForm.txtName.value).length==0){
        alert('<bean:message key="alert.DocNameRequired" />');
        thisForm.txtName.focus();
        return false;
      }
      thisForm.submit();
    }
  }
  
  function onRadSelect(){
    thisForm=document.forms[0];
    
    if(thisForm.radDocSelection[0].checked==true){
      thisForm.txtDocNo.disabled=true;
    }else if(thisForm.radDocSelection[1].checked==true){
      thisForm.txtDocNo.disabled=false;
    }
    
    if(thisForm.radNaming[0].checked==true){
      thisForm.txtName.disabled=false;
      //thisForm.txtSuffix.disabled=false;  -----to be uncommented-----
      //thisForm.chkSuffix.disabled=false;  -----to be uncommented-----
      //onChkSuffixSelect();   -----to be uncommented-----
    }else if(thisForm.radNaming[1].checked==true){
      thisForm.txtName.disabled=true;
      //thisForm.txtSuffix.disabled=true;    -----to be uncommented-----
      //thisForm.chkSuffix.disabled=true;      -----to be uncommented-----
    }
  }
  
  function onChkSuffixSelect(){
    if(thisForm.radNaming[0].checked==true){
      if(thisForm.chkSuffix.checked==true){
        thisForm.txtSuffix.disabled=false;
      }else{
        thisForm.txtSuffix.disabled=true;
      }
    }
  }
  
</script>
<script>
  function window_onload(){
    thisForm=document.forms[0];
    MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_doc_upload_ovr.gif','images/tab_fax_ovr.gif')
    var currentDate=new Date();
    datePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
    datePicker.initialize();
    datePicker.click();
    
    //For Radio Button Selection
    thisForm.radDocSelection[0].checked=true;
    thisForm.txtDocNo.disabled=true;
    thisForm.radNaming[0].checked=true;
    //For Check Box Selection
    //thisForm.chkSuffix.checked=true; to be uncommented
  }

</script>

<script>
<!--

// Email Validation. Written by PerlScriptsJavaScripts.com

function check_email(e) {
    ok = "1234567890qwertyuiop[]asdfghjklzxcvbnm.@-_QWERTYUIOPASDFGHJKLZXCVBNM";

    for(i=0; i < e.length ;i++){
        if(ok.indexOf(e.charAt(i))<0){ 
            return (false);
        }       
    } 

    if (document.images) {        
        re = /(@.*@)|(\.\.)|(^\.)|(^@)|(@$)|(\.$)|(@\.)/;
        re_two = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        if (!e.match(re) && e.match(re_two)) {
            return (true);   
            
        }else{
            return false;
        }

    }

}

// -->
</script>
</head>

<body onLoad="return window_onload();">

    <table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>
                <table id="topBar" width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="250"><img src="images/sfu_logo.jpg" width="250px" height="50"></td>
                        <td valign="bottom" align="right" background="images/tile_topbar.jpg">
                            <a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)">
                                <img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0">
                            </a>
                            <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)">
                                <img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0">
                            </a>
                            <a href="logoutAction.do"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
                                <img src="images/bt_logout.jpg" alt="Logout" name="Logout" width="52" height="40" border="0">
                            </a>
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
                                    <td width="100" class="bdrBottomColor_336666"><a href="uploadB4Action.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('DocUpload','','images/tab_doc_upload_ovr.gif',1)"><img src="images/tab_doc_upload.gif" alt="Doc Upload" name="DocUpload" width="100" height="25" border="0"></a></td>
                                    <td width="55"><img src="images/tab_mail.gif" alt="Mail" name="Mail" width="55" height="25" border="0"></td>
                                    <td width="55" class="bdrBottomColor_336666"><a href="faxB4Action.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Fax','','images/tab_fax_ovr.gif',1)"><img src="images/tab_fax.gif" alt="Fax" name="Fax" width="55" height="25" border="0"></a></td>
                                    <td class="bdrBottomColor_336666">&nbsp;</td>
                                    <td class="bdrBottomColor_336666">
                                        <a href="jobRelayAction.do?operation=search_scheduler&txtSearchByUserName=<bean:write name='userProfile' property='userId' /> ">
                                            My Jobs
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="450px" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
                            <html:form name="mailForm" method="post" type="sfu.actionforms.job.mail.MailForm" action="mailAction.do" >
                            <html:hidden name="mailForm" property="day"  />
                            <html:hidden name="mailForm" property="month"  />
                            <html:hidden name="mailForm" property="year"  />
                            <html:hidden name="mailForm" property="hours"  />
                            <html:hidden name="mailForm" property="minutes"  />
                            <html:hidden name="mailForm" property="timezone"  />
                            <html:hidden name="mailForm" property="seconds"  />
                            <html:hidden name="mailForm" property="smtpHost"  />
                            
                            <br>
                                <table width="65%"  border="0" align="center" class="bdrColor_336666">
                                    <tr>
                                        <td colspan="2" height="5px"></td>
                                    </tr>
                                    <tr>
                                        <td width="23%">&nbsp;</td>
                                        <td width="77%">
                                            <html:radio  property="radDocSelection"  value="all" onclick="onRadSelect();" />
                                            <bean:message key="lbl.AllDocuments"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>
                                            <html:radio  property="radDocSelection"  value="notAll" onclick="onRadSelect();" />
                                            <bean:message key="lbl.First"/> 
                                            <html:text  property="txtDocNo" styleClass="bdrColor_336666" size="2" onkeypress="return integerOnly(event);" maxlength="2" /><bean:message key="lbl.Documents"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td valign="top">&nbsp;</td>
                                        <td>
                                            <table width="99%"  border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td width="154px">
                                                        <html:radio  property="radNaming"  value="notAuto" onclick="onRadSelect();"/>
                                                        <bean:message key="lbl.DocumentsNamedAs"/>
                                                    </td>
                                                    <td width="191px">
                                                        <input name="txtName" type="TEXT" class="bdrColor_336666" style="width:142px">
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>
                                            <html:radio  property="radNaming"  value="auto" onclick="onRadSelect();"/>
                                            <bean:message key="lbl.AutoNaming"/>
                                        </td>
                                    </tr>
                                    <logic:equal name="mailForm" property="hdnOutputFormat" value="pdf">
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td><html:checkbox property="chkSingleFile" />
                                                <bean:message key="lbl.CreateSingleFile" />
                                            </td>
                                        </tr>
                                    </logic:equal>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td align="right"> 
                                        </td>
                                        <td>
                                            <span style="float:left; margin-right:10px"><bean:message key="lbl.MailDate&Time" />:</span>
                                            <script>datePicker.render();</script>
                                            <html:hidden name="mailForm" property="txtSendTime" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                         <bean:message key="lbl.To"/>:
                                        </td>
                                        <td>
                                            <html:text name="mailForm" property="txtTo" styleClass="bdrColor_336666" style="width:300px;"  />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                            <bean:message key="lbl.Cc"/>:
                                        </td>
                                        <td>
                                            <html:text name="mailForm" property="txtCc" styleClass="bdrColor_336666" style="width:300px;"  />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">
                                            <bean:message key="lbl.Bcc"/>:
                                        </td>
                                        <td>
                                            <html:text name="mailForm" property="txtBcc" styleClass="bdrColor_336666" style="width:300px;"  />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td valign="top"align="right">
                                        <bean:message key="lbl.Subject"/>:
                                        </td>
                                        <td>
                                        <html:text name="mailForm" property="txtSubject" styleClass="bdrColor_336666" style="width:300px;"  /></td>
                                    </tr>
                                    <tr>
                                        <td valign="top" align="right">
                                        <bean:message key="lbl.MailMessage"/>:
                                        </td>
                                        <td>
                                        <textarea name="txaMail" rows="5"  style="width:300px" ></textarea>			</td></tr>
                                    <tr>
                                        <td colspan="2" align="right">
                                            <html:button  property="btnOk" styleClass="buttons" onclick="callSubmit(this.form)" style="width:60px">			<bean:message key="btn.Ok" /></html:button>
                                            <html:reset property="btnClear" styleClass="buttons"  style="width:60px; ">			<bean:message key="btn.Clear" /></html:reset>
                                            <html:cancel  styleClass="buttons"  style="width:60px; margin-right:54px"><bean:message key="btn.Cancel" /></html:cancel>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" height="5px"></td>
                                    </tr>
                                </table>
                            </html:form>
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
</body>
</html>
