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
<title>Logger Configuration</title>
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
	  Logger Configuration
	</div>	</td>
	</tr>
</table>
<form>
<p>&nbsp;</p>
<table width="450" border="0" align="center" cellspacing="8">
	<tr>
	  <td width="197"><div align="right">
	    <bean:message key="lbl.LogFolder"/>
	  </div></td>
	  <td width="225">
		<input type="text" class="bdrColor_336666"/ style="width:125px">
	  </td>
	</tr>
	<tr>
	  <td><div align="right">
	    <bean:message key="lbl.MaxLogSize"/>
	  </div></td>
	  <td>
		<input type="text" class="bdrColor_336666"/ style="width:125px">
		<select name="select2" style="width:50px">
		  <option selected>?????</option>
                                        </select></td>
	</tr>
	<tr>
	  <td><div align="right">
	    <bean:message key="lbl.LogFileName"/>
	  </div></td>
	  <td>
	    <input name="text" type="text" class="bdrColor_336666"/ style="width:125px"></td>
    </tr>
	<tr>
	  <td><div align="right">
	    <bean:message key="lbl.LogFilesToMaintain"/>
	  </div></td>
	  <td><input name="text2" type="text" class="bdrColor_336666"/ style="width:125px"></td>
    </tr>
	<tr>
	  <td><div align="right">
	    <bean:message key="lbl.LogLevel"/>
	  </div></td>
	  <td><select name="select" style="width:50px">
        <option selected>?????</option>
      </select></td>
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
