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
    <title>untitled</title>
  </head>
  <body>
    <form>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <STRONG>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PatchInfo&nbsp;</STRONG></P>
      <P>No Of Newly Downloaded Patches &nbsp;&nbsp;_________________________ &nbsp;
      <input type="submit" value="View"/></P>
      <P>
        <input type="submit" value="View Patch History"/>
      </P>
      <P>&nbsp;</P>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td backgroung="images/tile_topbar.jpg">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
    </form>
  </body>
</html>
