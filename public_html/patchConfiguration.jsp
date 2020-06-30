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
<link href="main.css" rel="stylesheet" type="text/css">
<title>Patch Configuration</title>
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
<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td height="20px" background="images/tile_20px.gif" class="bdrBottomColor_336666">
	<div align="center" class="heading_1">
	  Patch Configuration
	</div>	</td>
	</tr>
</table>
<form action="patchConfigurationAction.do">
<bean:define name="PatchConfigurationForm" type="sfu.actionforms.configuration.PatchConfigurationForm" id="patchConfigurationForm" />
<p>&nbsp;</p>
<table width="450" border="0" align="center" cellspacing="8">
	<tr>
	  <td width="197"><div align="right">Local Patch Folder:</div></td>
	  <td width="225">
		  <html:text name="patchConfigurationForm" property="txtLocalPatchFolder"  styleClass="bdrColor_336666" style="width:125px"/>
    </td>
	</tr>
	<tr>
	  <td><div align="right">Remote Patch Folder:</div></td>
	  <td>
		   <html:text name="patchConfigurationForm" property="txtRemotePatchFolder"  styleClass="bdrColor_336666" style="width:125px"/>
		</td>
	</tr>
	<tr>
	  <td><div align="right">Patch Check Interval: </div></td>
	  <td>
	    <html:text name="patchConfigurationForm" property="txtPatchCheckInterval"  styleClass="bdrColor_336666" style="width:70px"/>
	    <html:select name="patchConfigurationForm" property="cboPatchCheckInterval"  style="width:100px" >
              <html:option value="minute" >minute(s)</html:option>
              <html:option value="hour" >hour(s)</html:option>
              <html:option value="day" >day(s)</html:option>
      </html:select>
	    </td>
    </tr>
	<tr>
	  <td>&nbsp;</td>
	  <td><input name="Submit2" type="submit" class="buttons" value="OK" style="width:60px;">
		<input name="Submit22" type="submit" class="buttons" value="Cancel" style="width:60px;"></td>
	</tr>
</table>
</form>
</body>
</html>
