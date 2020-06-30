<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" autoFlush="true" buffer="none" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="main.css" rel="stylesheet" type="text/css"></link>
    <%
      Boolean scanningDone = (Boolean)session.getAttribute("scanningDone");
      if(scanningDone!=null && !scanningDone.booleanValue()){
    %>
    <meta http-equiv="Refresh" content="2;URL=listProcessStatusAction.do" />
    <%
      }
    %>
    
    <title>listProcessStatus</title>
  </head>
  <body>
    <!-- Status Bar Table Starts-->
    <table id="tblStatusBar" align="center" width="100%" height="23px"
                   border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
      <tr class="bgColor_D8EAEC bdrColor_336666 ">
        <td width="30px">
          <div class="imgStatusMsg"/>
        </td>
        <td style="color:red" >&nbsp;
          <logic:messagesPresent name="procMessage" message="true" >
              <html:messages id="procMessage" message="true">
                <bean:write name="procMessage"/>
              </html:messages>
          </logic:messagesPresent>
        </td>
      </tr>
      
    </table>
    
  </body> 
</html>