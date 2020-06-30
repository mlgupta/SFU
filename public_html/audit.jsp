<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page import="sfu.beans.scheduler.SchedulerConstants"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="pageCount" name="auditTrailListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="auditTrailListForm" property="hdnSearchPageNo" />
<bean:define id="auditTrails" name="auditTrails" type="java.util.ArrayList"/>

<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>
<html:html>
<head>
<title><bean:message key="title.AuditTrailList" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<script src="datepicker.js"></script>
<script src="timezones.js"></script>
<script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
<script language="JavaScript" type="text/JavaScript" src="navigationbar.js" ></script>
    <script>
      var pageCount=parseInt(<%=pageCount%>);
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNo%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="callPage('auditTrailList')";

      navBar.msgPageNotExist="<bean:message  key="page.pageNumberNotExists" />";
      navBar.msgNumberOnly="<bean:message   key="page.enterNumbersOnly"/>";    
      navBar.msgEnterPageNo="<bean:message   key="page.enterPageNo"/>";
      navBar.msgOnFirst="<bean:message    key="page.onFirst"/>";
      navBar.msgOnLast="<bean:message    key="page.onLast"/>";

      navBar.lblPage="<bean:message    key="lbl.Page"/>";
      navBar.lblOf="<bean:message    key="lbl.Of"/>";

      navBar.tooltipPageNo="<bean:message    key="tooltips.PageNo"/>";
      navBar.tooltipFirst="<bean:message    key="tooltips.First"/>";
      navBar.tooltipNext="<bean:message    key="tooltips.Next"/>";
      navBar.tooltipPrev="<bean:message    key="tooltips.Previous"/>";
      navBar.tooltipLast="<bean:message    key="tooltips.Last"/>";
      navBar.tooltipGo="<bean:message    key="btn.Go"/>";
    </script>
<script>
    var performedDateFrom = new Calendar("performedDateFrom",0,0);
    performedDateFrom.onclick="selectedValues(performedDateFrom,'txtPerformedFromDate')";
    performedDateFrom.onclear="clearValues('txtPerformedFromDate')";
    //performedDateFrom.timezoneDisabled=true;
    performedDateFrom.noTimezone=true;
    //performedDateFrom.noTime=true;
    performedDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    performedDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    performedDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    performedDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    performedDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    performedDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    performedDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    performedDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    performedDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    performedDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    performedDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    performedDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    performedDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    //performedDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
    var performedDateTo = new Calendar("performedDateTo",0,0);
    performedDateTo.onclick="selectedValues(performedDateTo,'txtPerformedToDate')";
    performedDateTo.onclear="clearValues('txtPerformedToDate')";
    performedDateTo.noTimezone=true;
    //performedDateTo.timezoneDisabled=true;
    performedDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    performedDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    performedDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    performedDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    performedDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    performedDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    performedDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    performedDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    performedDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    performedDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    performedDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    performedDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    performedDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    //performedDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
    var scheduledDateFrom = new Calendar("scheduledDateFrom",0,0);
    scheduledDateFrom.onclick="selectedValues(scheduledDateFrom,'txtScheduledFromDate')";
    scheduledDateFrom.onclear="clearValues('txtScheduledFromDate')";
    scheduledDateFrom.noTimezone=true;
    //scheduledDateFrom.timezoneDisabled=true;
    scheduledDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    scheduledDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    scheduledDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    scheduledDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    scheduledDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    scheduledDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    scheduledDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    scheduledDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    scheduledDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    scheduledDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    scheduledDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    scheduledDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    scheduledDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    //scheduledDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
    var scheduledDateTo = new Calendar("scheduledDateTo",0,0);
    scheduledDateTo.onclick="selectedValues(scheduledDateTo,'txtScheduledToDate')";
    scheduledDateTo.onclear="clearValues('txtScheduledToDate')";
    scheduledDateTo.noTimezone=true;
    //scheduledDateTo.timezoneDisabled=true;
    scheduledDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
    scheduledDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
    scheduledDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
    scheduledDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';
    scheduledDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
    scheduledDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
    scheduledDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
    scheduledDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
    scheduledDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';
    scheduledDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
    scheduledDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
    scheduledDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
    scheduledDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
    //scheduledDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';

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
  function window_onload(){
    
    MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')
    
    var currentDate=null;
    if(document.forms[0].txtPerformedFromDate.value!=""){
        currentDate=new Date(document.forms[0].txtPerformedFromDate.value);
    }else{
        currentDate=new Date();
    }
    performedDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
    //performedDateFrom.setTimezone(document.forms[0].timezone.value);
    
    if(document.forms[0].txtPerformedFromDate.value!=""){
        performedDateFrom.click();
    }
        
    if(document.forms[0].txtPerformedToDate.value!=""){
        currentDate=new Date(document.forms[0].txtPerformedToDate.value);
    }else{
        currentDate=new Date();
    }
    performedDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            
    //performedDateTo.setTimezone(document.forms[0].timezone.value);
    
    if(document.forms[0].txtPerformedToDate.value!=""){
        performedDateTo.click();
    }               
    
    if(document.forms[0].txtScheduledFromDate.value!=""){
        currentDate=new Date(document.forms[0].txtScheduledFromDate.value);
    }else{
        currentDate=new Date();
    }
    scheduledDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            
    //scheduledDateFrom.setTimezone(document.forms[0].timezone.value);
    if(document.forms[0].txtScheduledFromDate.value!=""){
        scheduledDateFrom.click();
    }                   

    if(document.forms[0].txtScheduledToDate.value!=""){
        currentDate=new Date(document.forms[0].txtScheduledToDate.value);
    }else{
        currentDate=new Date();
    }  
    scheduledDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                                                        
    //scheduledDateTo.setTimezone(document.forms[0].timezone.value);
    if(document.forms[0].txtScheduledToDate.value!=""){
        scheduledDateTo.click();
    }   
  }
  
</script>
<script language="JavaScript" type="text/JavaScript"  >
<!--
    function MM_swapImgRestore() { 
      var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
    }
    
    function MM_preloadImages() { 
      var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    }
    
    function MM_findObj(n, d) { 
      var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
        d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
      if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
      for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
      if(!x && d.getElementById) x=d.getElementById(n); return x;
    }
    
    function MM_swapImage() { 
      var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
       if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
    }
    
    function callPage(pageName){
      var thisForm=document.forms[0]; 
      var index=0;
      if(pageName =="auditTrailDelete"){
        index=getRadioSelectedIndex(thisForm.chkSelect);
        if (index>-2){
          if(pageName =="auditTrailDelete"){
            if (confirm('<bean:message   key="alert.delete.confirm" />')){
              thisForm.action="auditTrailDeleteAction.do";      
            }else{
              return false;
            }
          }
        }else{
          alert('<bean:message key="alert.RecordSelectRequired" />');
          return false;
        }
      }else if(pageName =="auditTrailList"){
        thisForm.action="auditTrailListAction.do";      
        
      }
      thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
      
      thisForm.submit();
    }
    
    
    function openList(thisForm){
      openWindow('userListSelectAction.do?openerControl=txtSearchByUserName','schedulerUser',500,450,0,0,true,'status=no,resizable=no,dependent=yes,alwaysRaised=yes');  
    }
    
    function chkSelect_onclick(me){
      var thisForm=me.form;
      var isAllChkSelectChecked=false;
      var noOfCheckedRows=0;
      if(typeof thisForm.chkSelect.length !='undefined'){
        for(var chkIndex=0;  chkIndex<thisForm.chkSelect.length; chkIndex++){
          if(thisForm.chkSelect[chkIndex].checked==true){
            noOfCheckedRows=noOfCheckedRows+1;
          }
        }
        if (noOfCheckedRows==thisForm.chkSelect.length){
          isAllChkSelectChecked=true;
        }
      }else{
        if(thisForm.chkSelect.checked==true){
          isAllChkSelectChecked=true;
        }
      }
      if(isAllChkSelectChecked){
        thisForm.chkSelectAll.checked=true;
      }else{
        thisForm.chkSelectAll.checked=false;
      }
    }
    
    function chkSelectAll_onclick(me){
      var thisForm=me.form;
      
      if(typeof thisForm.chkSelect.length !='undefined'){
        for(var chkIndex=0;  chkIndex<thisForm.chkSelect.length; chkIndex++){
          thisForm.chkSelect[chkIndex].checked=me.checked;
          //isAnyPrintableAreaSelected=true;
        }
      }else{
        thisForm.chkSelect.checked=me.checked;
      }
      //if(me.checked==true){
      //  isAnyPrintableAreaSelected=true;
      //}else{
      //  isAnyPrintableAreaSelected=false;
      //}
    }
    
    function document_onkeypress(e){
      var key;
      if (window.event){
        key = window.event.keyCode;
      }else if (e){
        key = e.which;
      }else{
        return false;
      }      
      if(key==13 && MM_findObj('tblSearch').style.display!='none'){
        callPage('auditTrailList');
      }
    }
    document.onkeypress=document_onkeypress;
//-->
</script>
</head>

<body onLoad="return window_onload();MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif');">
<html:form action="auditTrailListAction.do" >
<html:hidden property="hdnSearchPageNo" />
<html:hidden  property="actionType"/>
<html:hidden property="timezone" />
<table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="785">&nbsp;</td>
  </tr>
  <tr>
    <td>
      <table id="topBar" width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="250"><img src="images/sfu_logo.jpg" width="250" height="50"></td>
                <td valign="bottom" background="images/tile_topbar.jpg">
                  <div align="right">
                    <!--
                    <a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)"><img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0"></a> 
                     
                    <a href="#" onmouseout="MM_swapImgRestore()"
                       onmouseover="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)">
                      <img src="images/bt_refresh.jpg" alt="Refresh"
                           name="Refresh" width="52" height="40" border="0"></img>
                    </a>
                    -->
                     
                    <a href="logoutAction.do" onmouseout="MM_swapImgRestore()"
                       onmouseover="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
                      <img src="images/bt_logout.jpg" alt="Logout" name="Logout"
                           width="52" height="40" border="0"></img>
                    </a>
                  </div>
                </td>
                <td width="13"><img src="images/topbar_right.jpg" width="13" height="50"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="510px" valign="top" class="bgColor_D8EAEC bdrColor_336666">
      <table width="745" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>
            <table width="745" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="100" class="bdrBottomColor_336666"><a href="configurationMainAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_configuration_ovr.gif',1)"><img src="images/tab_configuration.gif" alt="Configuration" name="Configuration" width="100" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)"><img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a href="jobListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
                <!--<td width="55" class="bdrBottomColor_336666"><a href="availablePatchesAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>-->
                <td width="55" class="bdrBottomColor_336666"><a href="viewLogAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
                <td width="55"><a href="auditTrailListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
                <td class="bdrBottomColor_336666">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td height="40px" class="bdrLeftColor_336666 bdrRightColor_336666">
            <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="57%">
                   &nbsp;&nbsp;&nbsp;<html:button property="btnDelete" styleClass="buttons" style="width:60px;" onclick="return callPage('auditTrailDelete');" ><bean:message key="btn.Delete" /></html:button>
                </td>
                <td width="43%" align="right">
                  <html:button property="btnSearch" styleClass="buttons" style="width:60px;" onclick="return callPage('auditTrailList');"><bean:message key="btn.Search" /></html:button>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="bdrLeftColor_336666 bdrRightColor_336666">
            <div align="right">
              <table width="98%"  id="tblSearch" border="0" align="center" cellpadding="1" cellspacing="0" class="bdrColor_336666">
                <tr>
                  <td width="20%">
                    <fieldset>
                      <legend align="left" >
                            <bean:message key="lbl.ActionInfo" />
                      </legend>
                      <table width="202px" border="0" cellpadding="1" cellspacing="2">
                        <tr>
                          <td width="40%">
                            <div align="right">
                              <bean:message key="lbl.User"/>:
                            </div>
                          </td>
                          <td width="60%" align="right">
                            <html:text name="auditTrailListForm" property="txtSearchByUserName" styleClass="bdrColor_336666" style="width:90px" maxlength="20" onkeypress="return enter(this,event,'search_scheduler');"/>
                            <html:button property="btnSearchByUserName" styleClass="buttons" title="Select user..." style="width:20px; height:17px" value="..." onclick="openList(this.form)"/>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div align="right">
                              <bean:message key="lbl.ActionType"/>:
                            </div>
                          </td>
                          <bean:define id="searchActionType" name="auditTrailListForm" property="actionType" />
                          <td align="right">
                            <html:select name="auditTrailListForm" property="cboSearchByActionType" style="width:115px">
                              <%
                                if(searchActionType.equals(SchedulerConstants.ALL_JOBS)){
                              %>
                                <option selected value="<%=SchedulerConstants.ALL_JOBS%>">
                                  <bean:message key="lbl.All"/>
                                </option>
                              <%
                                }else{
                              %>
                                <option value="<%=SchedulerConstants.ALL_JOBS%>">
                                  <bean:message key="lbl.All"/>
                                </option>
                              <%
                                }
                              %>
                              
                              <%
                                if(searchActionType.equals(SchedulerConstants.MAIL_JOB)){
                              %>
                                <option selected value="<%=SchedulerConstants.MAIL_JOB%>">
                                  <bean:message key="lbl.Mail"/>
                                </option>
                              <%
                                }else{
                              %>
                                <option value="<%=SchedulerConstants.MAIL_JOB%>">
                                  <bean:message key="lbl.Mail"/>
                                </option>
                              <%
                                }
                              %>
                              
                              <%
                                if(searchActionType.equals(SchedulerConstants.FAX_JOB)){
                              %>
                                <option selected value="<%=SchedulerConstants.FAX_JOB%>">
                                  <bean:message key="lbl.Fax"/>
                                </option>
                              <%
                                }else{
                              %>
                                <option value="<%=SchedulerConstants.FAX_JOB%>">
                                  <bean:message key="lbl.Fax"/>
                                </option>
                              <%
                                }
                              %>
                              <%
                                if(searchActionType.equals(SchedulerConstants.UPLOAD_JOB)){
                              %>
                                <option selected value="<%=SchedulerConstants.UPLOAD_JOB%>">
                                  <bean:message key="lbl.Upload"/>
                                </option>
                              <%
                                }else{
                              %>
                                <option value="<%=SchedulerConstants.UPLOAD_JOB%>">
                                  <bean:message key="lbl.Upload"/>
                                </option>
                              <%
                                }
                              %>
                            </html:select>
                          </td>
                        </tr>
                      </table>	
                    </fieldset>
                  </td>
                  
                  <td width="40%">
                    <fieldset>
                      <legend align="left">
                        <bean:message key="lbl.PerformedDate"/>
                      </legend>
                      <table width="228px"  border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td colspan="2" height="8px"></td>
                        </tr>
                        <tr>
                          <td width="2%">
                            <div align="right">
                              <bean:message key="lbl.From"/>:
                            </div>
                          </td>
                          <td width="98%">
                            <html:hidden property="txtPerformedFromDate" styleClass="bdrColor_336666" style="width:120px"  />
                            <script>
                                performedDateFrom.render();
                            </script>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <div align="right">
                              <bean:message key="lbl.To"/>:
                            </div></td>
                          <td>
                            <html:hidden  property="txtPerformedToDate" styleClass="bdrColor_336666" style="width:120px"  />                    
                            <script>performedDateTo.render()</script>                    
                          </td>
                        </tr>
                        <tr>
                          <td colspan="2" height="3px"></td>
                        </tr>
                      </table>
                    </fieldset>
                  </td>
                  <td width="40%">
                    <fieldset>
                      <legend align="left">
                        <bean:message key="lbl.ScheduledDate"/>
                      </legend>
                      <table width="228px"  border="0" cellpadding="0" cellspacing="0">
                        <tr>
                          <td colspan="2" height="8px"></td>
                        </tr>
                        <tr>
                          <td width="2%">
                            <div align="right">
                              <bean:message key="lbl.From"/>:
                            </div>
                          </td>
                          <td width="98%">
                            <html:hidden property="txtScheduledFromDate" styleClass="bdrColor_336666" style="width:120px"  />
                            <script>scheduledDateFrom.render()</script>
                          </td>
                        </tr>
                        <tr>
                          <td><div align="right">
                            <bean:message key="lbl.To"/>:
                          </div></td>
                          <td>
                            <html:hidden property="txtScheduledToDate" styleClass="bdrColor_336666" style="width:120px"  />
                            <script>scheduledDateTo.render()</script>        
                          </td>
                        </tr>
                        <tr>
                          <td colspan="2" height="3px"></td>
                        </tr>
                      </table>
                    </fieldset>
                  </td>
                </tr>
              </table>
            </div>
          </td>
        </tr>
        <tr>
          <td height="287px" align="center" valign="top" class="bdrLeftColor_336666 bdrRightColor_336666">
            <div  align="center" style="overflow:auto; height:308px; width:727px; margin-top:3px" frameborder="0" class="bdrColor_336666">
              <table width="100%" border="0" align="center" cellpadding="3" cellspacing="2">
                  <tr>
                    <th width="2%"><input type="checkbox" id="chkSelectAll" name="chkSelectAll" onclick="return chkSelectAll_onclick(this);" /></th>
                    <th width="14%"><div align="center"><bean:message key="tbl.UserId" /></div></th>
                    <th width="14%"><div align="center"><bean:message key="tbl.ActionType" /></div></th>
                    <th width="20%"><div align="center"><bean:message key="tbl.PerformedDate" /> </div></th>
                    <th width="20%"><div align="center"><bean:message key="tbl.SchuduledDate" /></div></th>
                    <th width="10%"><div align="center"><bean:message key="tbl.PagesScanned" /></div></th>
                    <th width="10%"><div align="center"><bean:message key="tbl.FileName" /></div></th>
                    <th width="10%"><div align="center"><bean:message key="tbl.To" /></div></th>
                  </tr>
                  <logic:iterate name="auditTrails" id="auditTrail" >
                    <bean:define id="performedDate" name="auditTrail" property="performedDate" scope="page" /> 
                    <% if (firstTableRow == true){ firstTableRow = false; %>
                      <tr>
                    <% }else{ firstTableRow = true; %>
                      </tr>
                      <tr class="tr_C5E2E5">
                    <% }%>
                      <td width="2%">
                        <html:multibox property="chkSelect" onclick="return chkSelect_onclick(this);" value="<%=(String)performedDate%>"  />                
                      </td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="userId"/></td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="actionType"/></td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="performedDate"/></td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="scheduledDate"/></td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="pagesScanned"/></td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="fileName"/></td>
                      <td align="center" width="1%"><bean:write name="auditTrail" property="toAddress"/></td>
                    </tr>    
                  </logic:iterate>
                </table>
                <%  if(auditTrails.size()==0) {%>
                <div style="width:100%; position:relative; top:125px; text-align:center;" class="tabText">
                  <bean:message key="info.no_item_found.no_item_found"/>
                </div>
                <% }%>
            </div>	
          </td>
        </tr>
        <tr>
          <td height="30px" class="bdrLeftColor_336666  bdrBottomColor_336666 bdrRightColor_336666">
            <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="88%">
                  <div align="right"><script>&nbsp;</script> </div>
                </td>
                <td width="12%">
                  <div align="right">
                    
                  </div>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <tr>
        <td height="23px" class="bgColor_D8EAEC bdrColor_336666 ">
          <!-- Status Bar Table Starts-->
          <table id="tblStatusBar" align="center" width="100%" height="2  3px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
            <tr class="bgColor_D8EAEC bdrColor_336666 ">
              <td width="30px" >
                <div class="imgStatusMsg"/>
              </td>
              <td>&nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
              <td width="200px" ><script>navBar.render();</script></td>
            </tr>
          </table>
          <!-- Status Bar Table Ends-->
        </td>
      </tr>
    </td>
  </tr>
</table>
</html:form>
</body>
</html:html>
