<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script src="general.js"></script>
<script src="datepicker.js"></script>
<script src="timezones.js"></script> 

<script>
  var datePicker = new Calendar("datePicker",0,0);
  datePicker.onclick="setDateValues()";
  datePicker.onclear="clearDateValues()";
  datePicker.clearDisabled="true";
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
  function window_onload(){
    var currentDate=new Date();
    datePicker.setDateTime(currentDate.getYear(),currentDate.getMonth()+1,currentDate.getDate(),
                            currentDate.getHours(),currentDate.getMinutes(),currentDate.getSeconds());
    datePicker.initialize();
    datePicker.click();
  }

</script>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>untitled</title>
  </head>
  <body>
  </body>
  <html:form name="MailForm" method="post" type="sfu.actionforms.job.mail.MailForm" action="mailAction.do" >
    <html:hidden name="MailForm" property="day"  />
    <html:hidden name="MailForm" property="month"  />
    <html:hidden name="MailForm" property="year"  />
    <html:hidden name="MailForm" property="hours"  />
    <html:hidden name="MailForm" property="minutes"  />
    <html:hidden name="MailForm" property="timezone"  />
    <html:hidden name="MailForm" property="seconds"  />
    
    
    <script>datePicker.render();</script>
</html:form>
</html>
