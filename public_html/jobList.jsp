<% 
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page import="sfu.beans.scheduler.SchedulerConstants"%>
<%@ page import="org.quartz.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<bean:define id="pageCount" name="JobListForm" property="txtPageCount"/>
<bean:define id="pageNo" name="JobListForm" property="txtPageNo"/>
<% request.setAttribute("topic","scheduler_introduction_html");%>
<% 
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>
<html>
  <head>
    <title>Job List</title>
    <script type="text/javascript" src="general.js"></script>
    <script type="text/javascript" src="datepicker.js"></script>
    <script type="text/javascript" src="timezones.js"></script>
    <script type="text/javascript" src="navigationbar.js" ></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="main.css" rel="stylesheet" type="text/css">
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
    
    <script type="text/javascript" >
        var createDateFrom = new Calendar("createDateFrom",0,0);
        createDateFrom.onclick="selectedValues(createDateFrom,'txtCreateFromDate')";
        createDateFrom.onclear="clearValues('txtCreateFromDate')";
        //createDateFrom.timezoneDisabled=true;
        createDateFrom.noTimezone=true;
        createDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
        createDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
        createDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
        createDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';
        createDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
        createDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
        createDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
        createDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
        createDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';
        createDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
        createDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
        createDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
        createDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
        createDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
        
        var createDateTo = new Calendar("createDateTo",0,0);
        createDateTo.onclick="selectedValues(createDateTo,'txtCreateToDate')";
        createDateTo.onclear="clearValues('txtCreateToDate')";
        //createDateTo.timezoneDisabled=true;
        createDateTo.noTimezone=true;
        createDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
        createDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
        createDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
        createDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';
        createDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
        createDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
        createDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
        createDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
        createDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';
        createDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
        createDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
        createDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
        createDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
        createDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
        
        var dispatchDateFrom = new Calendar("dispatchDateFrom",0,0);
        dispatchDateFrom.onclick="selectedValues(dispatchDateFrom,'txtDispatchFromDate')";
        dispatchDateFrom.onclear="clearValues('txtDispatchFromDate')";
        //dispatchDateFrom.timezoneDisabled=true;
        dispatchDateFrom.noTimezone=true;
        dispatchDateFrom.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
        dispatchDateFrom.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
        dispatchDateFrom.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
        dispatchDateFrom.tooltipDay='<bean:message key="tooltips.cal.Day" />';
        dispatchDateFrom.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
        dispatchDateFrom.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
        dispatchDateFrom.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
        dispatchDateFrom.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
        dispatchDateFrom.tooltipNow='<bean:message key="tooltips.cal.Now" />';
        dispatchDateFrom.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
        dispatchDateFrom.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
        dispatchDateFrom.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
        dispatchDateFrom.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
        dispatchDateFrom.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
        
        var dispatchDateTo = new Calendar("dispatchDateTo",0,0);
        dispatchDateTo.onclick="selectedValues(dispatchDateTo,'txtDispatchToDate')";
        dispatchDateTo.onclear="clearValues('txtDispatchToDate')";
        //dispatchDateTo.timezoneDisabled=true;
        dispatchDateTo.noTimezone=true;
        dispatchDateTo.tooltipCalendar='<bean:message key="tooltips.cal.Calendar" />';
        dispatchDateTo.tooltipCancel='<bean:message key="tooltips.cal.Cancel" />';
        dispatchDateTo.tooltipClear='<bean:message key="tooltips.cal.Clear" />';
        dispatchDateTo.tooltipDay='<bean:message key="tooltips.cal.Day" />';
        dispatchDateTo.tooltipHour='<bean:message key="tooltips.cal.Hour" />';
        dispatchDateTo.tooltipMinute='<bean:message key="tooltips.cal.Minute" />';
        dispatchDateTo.tooltipNextMonth='<bean:message key="tooltips.cal.NextMonth" />';
        dispatchDateTo.tooltipNextYear='<bean:message key="tooltips.cal.NextYear" />';
        dispatchDateTo.tooltipNow='<bean:message key="tooltips.cal.Now" />';
        dispatchDateTo.tooltipOk='<bean:message key="tooltips.cal.Ok" />';
        dispatchDateTo.tooltipPrevMonth='<bean:message key="tooltips.cal.PrevMonth" />';
        dispatchDateTo.tooltipPrevYear='<bean:message key="tooltips.cal.PrevYear" />';
        dispatchDateTo.tooltipSecond='<bean:message key="tooltips.cal.Second" />';
        dispatchDateTo.tooltipTimezone='<bean:message key="tooltips.cal.Timezone" />';
    
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
    
    
    
    <script type="text/javascript">
      function window_onload(){
        
        MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')
        
        var currentDate=null;
        if(document.forms[0].txtCreateFromDate.value!=""){
            currentDate=new Date(document.forms[0].txtCreateFromDate.value);
        }else{
            currentDate=new Date();
        }
        createDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
        createDateFrom.setTimezone(document.forms[0].timezone.value);
    
        if(document.forms[0].txtCreateFromDate.value!=""){
            createDateFrom.click();
        }
            
        if(document.forms[0].txtCreateToDate.value!=""){
            currentDate=new Date(document.forms[0].txtCreateToDate.value);
        }else{
            currentDate=new Date();
        }
        createDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            
        createDateTo.setTimezone(document.forms[0].timezone.value);
        if(document.forms[0].txtCreateToDate.value!=""){
            createDateTo.click();
        }               
        
        if(document.forms[0].txtDispatchFromDate.value!=""){
            currentDate=new Date(document.forms[0].txtDispatchFromDate.value);
        }else{
            currentDate=new Date();
        }
        dispatchDateFrom.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                            
        dispatchDateFrom.setTimezone(document.forms[0].timezone.value);
        if(document.forms[0].txtDispatchFromDate.value!=""){
            dispatchDateFrom.click();
        }                   
    
        if(document.forms[0].txtDispatchToDate.value!=""){
            currentDate=new Date(document.forms[0].txtDispatchToDate.value);
        }else{
            currentDate=new Date();
        }  
        dispatchDateTo.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                                currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());                                                        
        dispatchDateTo.setTimezone(document.forms[0].timezone.value);
        if(document.forms[0].txtDispatchToDate.value!=""){
            dispatchDateTo.click();
        }   
      }
      
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNo%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="callPage('page_scheduler')";
    
      navBar.msgPageNotExist="<bean:message key="page.pageNumberNotExists" />";
      navBar.msgNumberOnly="<bean:message key="page.enterNumbersOnly"/>";    
      navBar.msgEnterPageNo="<bean:message key="page.enterPageNo"/>";
      navBar.msgOnFirst="<bean:message key="page.onFirst"/>";
      navBar.msgOnLast="<bean:message key="page.onLast"/>";
    
      navBar.lblPage="<bean:message key="lbl.Page"/>";
      navBar.lblOf="<bean:message key="lbl.Of"/>";
    
      navBar.tooltipPageNo="<bean:message key="tooltips.PageNo"/>";
      navBar.tooltipFirst="<bean:message key="tooltips.First"/>";
      navBar.tooltipNext="<bean:message key="tooltips.Next"/>";
      navBar.tooltipPrev="<bean:message key="tooltips.Previous"/>";
      navBar.tooltipLast="<bean:message key="tooltips.Last"/>";
      navBar.tooltipGo="<bean:message key="btn.Go"/>";
     
    
    </script>
    
    <script type="text/javascript" language="Javascript1.1" >
    
    
    function callPage(relayOperation){
      var thisForm=document.forms[0]  ;
      thisForm.txtPageNo.value=navBar.getPageNumber();
      if( (relayOperation!="search_scheduler") && (relayOperation!="scheduler_start_stop") && (relayOperation!="page_scheduler")){
        if(checkSelected(thisForm)){
          if(relayOperation=="job_delete"){
            if (confirm("<bean:message key="msg.delete.confirm" />")){
              thisForm.operation.value=relayOperation;
              thisForm.submit() ;
            }else{
              return false;
            }
          }else{
            thisForm.operation.value=relayOperation;
            thisForm.submit() ;
          }
          
        }
      }else{
        thisForm.operation.value=relayOperation;
        thisForm.submit() ;
      }
      
    }
    
    function checkSelected(thisForm){
      if (typeof thisForm.radSelect!="undefined"){
      if (typeof thisForm.radSelect.length !="undefined"){
        for(index = 0 ; index < thisForm.radSelect.length ;index++){  
        if(thisForm.radSelect[index].checked){   
          return true;
        }
        }
      }else{
        if(thisForm.radSelect.checked){   
          return true;
        }
      }
      }else{
      alert('<bean:message key="errors.radSelect.noitem"/>');         
      return false;
      }      
      alert('<bean:message key="errors.radSelect.required"/>');         
      return false;
    }
    
    function openList(thisForm){
     openWindow('userListSelectAction.do?openerControl=txtSearchByUserName','schedulerUser',500,450,0,0,true);  
    }
    
    function startStopScheduler(relayOperation,action){
        var thisForm=document.forms[0];
        thisForm.txtPageNo.value=navBar.getPageNumber();
        thisForm.operation.value=relayOperation;
        //alert(thisForm.hdnSchedulerStartStopOperation.value);
        thisForm.hdnSchedulerStartStopOperation.value=action;
        //thisForm.action="jobRelayAction.do?action="+action;
        if (action=="Stop"){
          if(confirm("<bean:message key="scheduler.stop.confirm" />")){              
            thisForm.submit() ;
          }else{
            return false;
          }
        }else{          
          thisForm.submit() ;
        }
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
        callPage('search_scheduler');
      }
    }
    document.onkeypress=document_onkeypress;

      function enter(thisField,e,relayOperation){
        var i;
        i=handleEnter(thisField,e);
        if (i==1) {
          return callPage(relayOperation);
        }
      }    
    
    </script>  
  </head>
  <body onLoad="return window_onload();">
    <html:form name="jobListForm" type="sfu.actionforms.JobListForm" action="jobRelayAction.do">
      <html:hidden name="JobListForm" property="operation"/>
      <html:hidden name="JobListForm" property="isSchedulerStopped"/>
      <html:hidden name="JobListForm" property="txtPageNo"/>
      <html:hidden name="JobListForm" property="timezone"/>
      <html:hidden name="JobListForm" property="jobType"/>
      <html:hidden name="JobListForm" property="hdnSchedulerStartStopOperation"/>
      <table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="785">&nbsp;</td>
        </tr>
        <tr>
          <td>
            <table id="topBar" width="100%"  height="50" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="250">
                  <img src="images/sfu_logo.jpg" alt="CDATA" width="250" height="50"/>
                </td>
                <td valign="bottom" background="images/tile_topbar.jpg">
                  <div align="right">
                  <!--
                    <a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)">
                      <img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0"/>
                    </a>
                  
                    <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)">
                      <img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0"/>
                    </a>
                    -->
                    <a href="logoutAction.do"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
                      <img src="images/bt_logout.jpg" alt="Logout" name="Logout" width="52" height="40" border="0">
                    </a>
                  </div>
                </td>
                <td width="13">
                  <img src="images/topbar_right.jpg" width="13" height="50"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td height="510" valign="top" class="bgColor_D8EAEC bdrBottomColor_336666 bdrColor_336666">
            <table width="745px" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <bean:define id="isAdmin" name="userProfile" property="isAdmin" scope="session" />
                <bean:define id="userId" name="userProfile" property="userId" scope="session" />
                <logic:equal name="isAdmin" value="true" >
                <td>
                  <table width="745" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="100" class="bdrBottomColor_336666">
                        <a href="configurationMainAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_configuration_ovr.gif',1)">
                          <img src="images/tab_configuration.gif" alt="Configuration" name="Configuration" width="100" height="25" border="0"/>
                        </a>
                      </td>
                      <td width="55" class="bdrBottomColor_336666">
                        <a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)">
                          <img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"/>
                        </a>
                      </td>
                      <td width="55">
                        <a href="jobListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)">
                          <img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"/>
                        </a>
                      </td>
                      <td width="55" class="bdrBottomColor_336666">
                        <a  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)">
                          <img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"/>
                        </a>
                      </td>
                      <!--
                      <td width="55" class="bdrBottomColor_336666">
                        <a href="availablePatchesAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)">
                          <img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"/>
                        </a>
                      </td>
                      -->
                      <td width="55" class="bdrBottomColor_336666">
                        <a href="viewLogAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)">
                          <img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"/>
                        </a>
                      </td>
                      <td width="55" class="bdrBottomColor_336666">
                        <a href="auditTrailListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)">
                          <img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"/>
                        </a>
                      </td>
                      <td class="bdrBottomColor_336666">&nbsp;</td>
                    </tr>
                  </table>
                </td>
                </logic:equal>
                <logic:equal name="isAdmin" value="false" >
                    <td class="bdrBottomColor_336666">&nbsp;</td>
                </logic:equal>
              </tr>
              <tr>
                <td height="40px" class="bdrLeftColor_336666 bdrRightColor_336666">
                  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="57%">
                        <logic:equal name="isAdmin" value="true" >
                            <% if(((Scheduler)application.getAttribute("scheduler")).isShutdown()){ %>
                            <input name="button" type="button" class="buttons" style="width:130px;" value="Start Scheduler" onClick="return startStopScheduler('scheduler_start_stop','Start');"/>
                            <% }else{ %>
                            <input name="button" type="button" class="buttons" style="width:130px;" value="Stop Scheduler" onClick="return startStopScheduler('scheduler_start_stop','Stop');"/>
                            <% }%>
                        </logic:equal>
                        <input name="Button" type="button" class="buttons" style="width:80px;" value="Reschedule" onClick="return callPage('job_reschedule')"/>
                        <!--&lt;input name=&quot;Button2&quot; type=&quot;button&quot; class=&quot;buttons&quot; style=&quot;width:90px;&quot; value=&quot;View Content&quot; onclick=&quot;return callPage('view_content')&quot;/&gt;-->
                        <input name="submit2" type="button" class="buttons" style="width:60px;" value="Delete" onClick="return callPage('job_delete')"/>
                        <logic:equal name="isAdmin" value="false" >
                          <a href="jobCreateB4Action.do" >back</a>
                        </logic:equal>
                      </td>
                      
                      <td width="43%">
                        <div align="right">
                          <input name="Button" type="button" class="buttons" style="width:80px;" value="Search" onClick="return callPage('search_scheduler')"/>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="40px" class="bdrLeftColor_336666 bdrRightColor_336666">
                  <table width="98%" border="0" align="center" cellpadding="1" cellspacing="0" class="bdrColor_336666">
                    <tr>
                      <td width="32%" valign="top">
                        <fieldset>
                          <legend align="left">
                            <bean:message key="lbl.JobInfo"/>
                          </legend>
                          <table width="202px" border="0" id="tblSearch" cellpadding="0" cellspacing="1">
                            <tr>
                              <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                              <td width="29%">
                                <div align="right">
                                  <bean:message key="lbl.User"/>:
                                </div>
                              </td>
                              <td width="71%" align="right">
                                <logic:equal name="isAdmin" value="true" >
                                    <html:text name="JobListForm" property="txtSearchByUserName" styleClass="bdrColor_336666" style="width:110px" maxlength="20" />
                                    <html:button property="btnSearchByUserName" styleClass="buttons" title="Select user..." style="width:20px; height:17px" value="..." onclick="openList(this.form)"/>
                                </logic:equal>
                                <logic:equal name="isAdmin" value="false" >
                                    <html:text name="JobListForm" property="txtSearchByUserName" styleClass="bdrColor_336666" style="width:110px" maxlength="20" value="<%=(String)userId%>" disabled="true" />
                                    <html:button property="btnSearchByUserName" styleClass="buttons" title="Select user..." style="width:20px; height:17px" value="..." onclick="openList(this.form)" disabled="true"  />
                                </logic:equal>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <div align="right">
                                  <bean:message key="lbl.JobType"/>:
                                </div>
                              </td>
                              
                              <bean:define id="searchJobType" name="jobListForm" property="jobType" />
                              <td align="right">
                                <html:select name="JobListForm" property="cboSearchByJobType" style="width:135px">
                                  <%
                                    if(searchJobType.equals(SchedulerConstants.ALL_JOBS)){
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
                                    if(searchJobType.equals(SchedulerConstants.MAIL_JOB)){
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
                                    if(searchJobType.equals(SchedulerConstants.FAX_JOB)){
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
                                    if(searchJobType.equals(SchedulerConstants.UPLOAD_JOB)){
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
                      <td width="34%">
                        <fieldset>
                          <legend align="left">
                            <bean:message key="lbl.CreateDate"/>
                          </legend>
                          <table width="228px" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                              <td width="2%">
                                <div align="right">
                                  <bean:message key="lbl.From"/>:
                                </div>
                              </td>
                              <td width="98%">
                                <html:hidden name="JobListForm" property="txtCreateFromDate" styleClass="bdrColor_336666" style="width:120px"/>
                                <script>
                                  createDateFrom.render();
                                </script>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <div align="right">
                                  <bean:message key="lbl.To"/>:
                                </div>
                              </td>
                              <td>
                                <html:hidden name="JobListForm" property="txtCreateToDate" styleClass="bdrColor_336666" style="width:120px"/>
                                <script>createDateTo.render()</script>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" height="3px"/>
                            </tr>
                        </table>
                        </fieldset>
                      </td>
                      <td width="34%">
                        <fieldset>
                          <legend align="left">
                            <bean:message key="lbl.DespatchDate"/>
                          </legend>
                          <table width="228px" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td colspan="2" height="8px">&nbsp;</td>
                            </tr>
                            <tr>
                              <td width="2%">
                                <div align="right">
                                  <bean:message key="lbl.From"/>:
                                </div>
                              </td>
                              <td width="98%">
                                <html:hidden name="JobListForm" property="txtDispatchFromDate" styleClass="bdrColor_336666" style="width:120px"/>
                                <script>dispatchDateFrom.render()</script>
                              </td>
                            </tr>
                            <tr>
                              <td>
                                <div align="right">
                                  <bean:message key="lbl.To"/>:
                                </div>
                              </td>
                              <td>
                                <html:hidden name="JobListForm" property="txtDispatchToDate" styleClass="bdrColor_336666" style="width:120px"/>
                                <script>dispatchDateTo.render()</script>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" height="3px"/>
                            </tr>
                        </table>
                        </fieldset>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="290px" align="center" valign="top" class="bdrLeftColor_336666 bdrRightColor_336666">
                  <div align="center" style="overflow:auto; height:300px; width:727px; margin-top:3px" frameborder="0" class="bdrColor_336666">
                    <table width="100%" align="center" cellpadding="3" cellspacing="2">
                      <tr>
                        <th width="4%">&nbsp;</th>
                        <th width="13%">
                          <div align="center">
                            <bean:message key="lbl.JobName"/>
                          </div>
                        </th>
                        <th width="10%">
                          <div align="center">
                            <bean:message key="lbl.JobType"/>
                          </div>
                        </th>
                        <th width="28%">
                          <div align="center">
                            <bean:message key="lbl.User"/>
                          </div>
                        </th>
                        <th width="13%">
                          <div align="center">
                            <bean:message key="lbl.CreateDate"/>
                          </div>
                        </th>
                        <th width="14%">
                          <div align="center">
                            <bean:message key="lbl.DespatchDate"/>
                          </div>
                        </th>
                        <th width="18%">
                          <bean:message key="lbl.Status"/>
                        </th>
                      </tr>
                      <logic:notEmpty name="jobs">
                      <bean:define id="jobs" name="jobs" type="java.util.ArrayList"/>
                      <%  if(jobs!=null && jobs.size()>0) {%>
                      <logic:iterate name="jobs" id="job">
                        <% if (firstTableRow == true){ firstTableRow = false; %>
                          <tr>
                        <% }else{ firstTableRow = true; %>
                          </tr>
                          <tr class="tr_C5E2E5">
                        <% }%>
                          <td width="4%">
                            <input type="radio" name="radSelect" value="<bean:write name="job" property="jobType"/> <bean:write name="job" property="jobName"/>"/>
                          </td>
                          <td width="13%">
                            <bean:write name="job" property="jobName"/>
                          </td>
                          <td width="10%">
                            <bean:write name="job" property="jobType"/>
                          </td>
                          <td width="28%">
                            <bean:write name="job" property="creatorName"/>
                          </td>
                          <td width="13%">
                            <bean:write name="job" property="createTime"/>
                          </td>
                          <td width="14%">
                            <bean:write name="job" property="executionTime"/>
                          </td>
                          <td width="18%">
                            <bean:write name="job" property="retrialCount"/>/
                            <bean:write name="job" property="jobErrorMessage"/>
                          </td>
                        </tr>
                      </logic:iterate>
                      <% }%>
                      </logic:notEmpty>                      
                    </table>
                    <logic:empty name="jobs" scope="request">                    
                    <div style="width:100%; position:relative; top:125px; text-align:center;" class="tabText">
                      <bean:message key="info.no_item_found.no_item_found"/>
                    </div>
                    </logic:empty>
                  </div>
                </td>
              </tr>
              <tr>
                <td height="30px" class="bdrLeftColor_336666  bdrBottomColor_336666 bdrRightColor_336666">
                  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="88%" height="20px">&nbsp;
                        
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
          </td>
        </tr>
        <tr>
          <td height="2px"></td>
        </tr>
        <tr>
          <td height="23px" class="bgColor_D8EAEC bdrColor_336666 ">
            <!-- Status Bar Table Starts-->
            <table id="tblStatusBar" align="center" width="100%" height="23px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
              <tr class="bgColor_D8EAEC bdrColor_336666 ">
                <td width="30px" >
                  <div class="imgStatusMsg"/>
                </td>
                <td width="500px">
                <logic:messagesPresent  message="true">
                <div align="left" style="color:red">
                   <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
                </div>
                </logic:messagesPresent>
                </td>
                <td width="200px" ><script>navBar.render();</script></td>
              </tr>
            </table>
            <!-- Status Bar Table Ends-->
          </td>
        </tr>
      </table>
    </html:form>
  </body>
</html>
