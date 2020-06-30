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
    <P align="center">&nbsp;</P>
    <P/>
    <form>
      <P>
        <STRONG>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New</STRONG>&nbsp;
      <STRONG>Patch</STRONG>
        <STRONG> List</STRONG>
      </P>
      <P>
        <input type="submit" value="Apply Patch"/>
        <input type="submit" value="Delete"/>
      </P>
      <table cellspacing="0" cellpadding="0" border="0" width="100%">
        <tr>
          <td width="35%">Release Date&nbsp;</td>
          <td width="38%">&nbsp;Download Date</td>
        </tr>
        <tr>
          <td width="35%" height="39">&nbsp;From
            <input type="text" size="10"/>
            <input type="submit" value="Cal"/>
          </td>
          <td width="38%" height="39">From
            <input type="text" size="10"/>
            <input type="submit" value="Cal"/>
          </td>
        </tr>
        <tr>
          <td width="35%">&nbsp;&nbsp;&nbsp;To &nbsp;&nbsp;
            <input type="text" size="10"/>
            <input type="submit" value="Cal"/>
          </td>
          <td width="38%">&nbsp;To &nbsp;&nbsp;&nbsp;
            <input type="text" size="10"/>
            <input type="submit" value="Cal"/>
          </td>
        </tr>
      </table>
      <P>&nbsp;</P>
      <table cellspacing="0" cellpadding="0" border="0" width="100%">
        <tr>
          <td width="8%">select</td>
          <td width="14%">Patch Name</td>
          <td width="15%">Patch Number</td>
          <td width="14%">Release Date</td>
          <td width="19%">Download Date</td>
          <td width="19%">Description</td>
        </tr>
        <tr>
          <td width="8%">
            <input type="radio"/>
          </td>
          <td width="14%">&nbsp;</td>
          <td width="15%">&nbsp;</td>
          <td width="14%">&nbsp;</td>
          <td width="19%">&nbsp;</td>
          <td width="19%">&nbsp;</td>
        </tr>
        <tr>
          <td width="8%">
            <input type="radio"/>
          </td>
          <td width="14%">&nbsp;</td>
          <td width="15%">&nbsp;</td>
          <td width="14%">&nbsp;</td>
          <td width="19%">&nbsp;</td>
          <td width="19%">&nbsp;</td>
        </tr>
        <tr>
          <td width="8%">
            <input type="radio"/>
          </td>
          <td width="14%">&nbsp;</td>
          <td width="15%">&nbsp;</td>
          <td width="14%">&nbsp;</td>
          <td width="19%">&nbsp;</td>
          <td width="19%">&nbsp;</td>
        </tr>
      </table>
      <P>&nbsp;</P>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Page&nbsp;---&nbsp;Of&nbsp;----</P>
      <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="submit" value="Ok"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </P>
    </form>
  </body>
</html>
