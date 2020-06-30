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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title> <bean:message key="lbl.SMTPServerConfiguration"/></title>
<link href="main.css" rel="stylesheet" type="text/css">
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
</head>
<script language="Javascript1.1">

function callSubmit(thisForm){    
    thisform.submit();
}
</script>
<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td height="20px" background="images/tile_20px.gif" class="bdrBottomColor_336666">
	<div align="center" class="heading_1">
	  <bean:message key="lbl.SMTPServerConfiguration"/>
	</div>	</td>
	</tr>
</table>
<form action="smtpConfigurationAction.do">
<bean:define name="SmtpConfigurationForm" type="sfu.actionforms.configuration.SmtpConfigurationForm" id="smtpConfigurationForm" />
<p>&nbsp;</p>
<table width="450" border="0" align="center" cellspacing="8">
	<tr>
	  <td width="221"><div align="right"><bean:message key="lbl.SMTPPort"/></div></td>
	  <td width="219">
		<html:text name="smtpConfigurationForm" property="port" styleClass="bdrColor_336666" style="width:125px"/>    
	  </td>
	</tr>
	<tr>
	  <td><div align="right"><bean:message key="lbl.SMTPServerName/IP"/></div></td>
	  <td>
		<html:text name="smtpConfigurationForm" property="ipName" styleClass="bdrColor_336666" style="width:125px"/>    
	  </td>
	</tr>
	<tr>
	  <td>&nbsp;</td>
	  <td><input name="Submit2" type="submit" class="buttons" value="OK" style="width:60px;" onclick="callSubmit(this.form)" />
		<input name="Submit22" type="submit" class="buttons" value="Cancel" style="width:60px;" /></td>
	</tr>
</table>
</form>
</body>
</html>
