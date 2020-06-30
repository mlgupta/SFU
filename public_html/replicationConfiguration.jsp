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
<title>Replication Configuration</title>
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
	  Replication Configuration
	</div>	</td>
	</tr>
</table>
<form action="replicationConfigurationAction.do">
<bean:define name="ReplicationConfigurationForm" type="sfu.actionforms.configuration.ReplicationConfigurationForm" id="replicationConfigurationForm" />
<p>&nbsp;</p>
<table width="450" border="0" align="center" cellspacing="8">
	<tr>
	  <td width="197px">
      <div align="right">
        <bean:message key="lbl.ReplicationPeriod" />
      </div>
    </td>
	  <td width="225px">
		   <html:text name="replicationConfigurationForm" property="txtReplicationInterval"  styleClass="bdrColor_336666" style="width:47px"/>
	    <html:select name="replicationConfigurationForm" property="cboReplicationInterval" styleClass="bdrColor_336666" style="width:75px" >
              <html:option value="minute" >minute(s)</html:option>
              <html:option value="hour" >hour(s)</html:option>              
      </html:select>  </td>
	</tr>
	<tr>
	  <td><div align="right">
	    <bean:message key="lbl.ParentFolder"/>
	  </div></td>
	  <td>
		   <html:text name="replicationConfigurationForm" property="txtParentFolder"  styleClass="bdrColor_336666" style="width:125px"/>
	  </td>
	</tr>
  <!--
	<tr>
	  <td><div align="right">
	    <bean:message key="lbl.Timeout"/>
	  </div></td>
	  <td>
	    <html:text name="replicationConfigurationForm" property="txtTimeout"  styleClass="bdrColor_336666" style="width:125px"/>
	    mins</td>
  </tr>
  -->
	<tr>
	  <td>&nbsp;</td>
	  <td><input name="Submit2" type="submit" class="buttons" value="OK" style="width:60px;">
		<input name="Submit22" type="submit" class="buttons" value="Cancel" style="width:60px;"></td>
	</tr>
</table>
</form>
</body>
</html>
