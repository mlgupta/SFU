<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page language="java" %>
<%-- set document type to Javascript (addresses a bug in Netscape according to a web resource --%>
<%@ page contentType="application/x-javascript" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
 
<html:javascript dynamicJavascript="false" staticJavascript="true"/>
