<% 
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Job Configuration</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
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
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="20px" background="images/tile_20px.gif" class="bdrBottomColor_336666">
          <div align="center" class="heading_1">Job Configuration</div>
        </td>
      </tr>
    </table>
    <form action="jobConfigurationAction.do">
      <bean:define name="JobConfigurationForm" type="sfu.actionforms.configuration.JobConfigurationForm" id="jobConfigurationForm"/>
      <p>&nbsp;</p>
      <table width="450" border="0" align="center" cellspacing="5">
        <!--
        <tr>
          <td width="197">
            <div align="right">
              <bean:message key="lbl.MaxUploadSize"/>
            </div>
          </td>
          <td width="225">
            <html:text name="jobConfigurationForm" property="txtMaxUploadSize" styleClass="bdrColor_336666" style="width:70px"/>
            <html:select name="jobConfigurationForm" property="cboUploadSizeUnit" style="width:50px">
              <html:option value="KB">KB</html:option>
              <html:option value="MB">MB</html:option>
            </html:select>
          </td>
        </tr>
        <tr>
          <td>
            <div align="right">
              <bean:message key="lbl.MaxMailSize"/>
            </div>
          </td>
          <td>
            <html:text name="jobConfigurationForm" property="txtMaxMailSize" styleClass="bdrColor_336666" style="width:70px"/>
            <html:select name="jobConfigurationForm" property="cboMailSizeUnit" style="width:50px">
              <html:option value="KB">KB</html:option>
              <html:option value="MB">MB</html:option>
            </html:select>
          </td>
        </tr>
        <tr>
          <td>
            <div align="right">
              <bean:message key="lbl.MaxFaxSize"/>
            </div>
          </td>
          <td>
            <html:text name="jobConfigurationForm" property="txtMaxFaxSize" styleClass="bdrColor_336666" style="width:70px"/>
            <html:select name="jobConfigurationForm" property="cboFaxSizeUnit" style="width:50px">
              <html:option value="KB">KB</html:option>
              <html:option value="MB">MB</html:option>
            </html:select>
          </td>
        </tr>
        <tr>
          <td>
            <div align="right">
              <bean:message key="lbl.MaxNoOfPagesToFax"/>
            </div>
          </td>
          <td>
            <html:text name="jobConfigurationForm" property="txtMaxPagesToFax" styleClass="bdrColor_336666" style="width:70px"/>
          </td>
        </tr>
        -->
        <tr>
          <td>
            <div align="right">
              <bean:message key="lbl.MaxNumberOfRetries"/>:
            </div>
          </td>
          <td>
            <html:text name="jobConfigurationForm" property="txtMaxNumberOfRetries" styleClass="bdrColor_336666" style="width:125px"/>
          </td>
        </tr>
        <tr/>
        <tr>
          <td>
            <div align="right">
              <bean:message key="lbl.RetrialInterval"/>:
            </div>
          </td>
          <td>
            <html:text name="jobConfigurationForm" property="txtRetrialInterval" styleClass="bdrColor_336666" style="width:125px"/>
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td height="30px">
            <input name="Submit2" type="submit" class="buttons" value="OK" style="width:60px;"/>
            <input name="Submit22" type="submit" class="buttons" value="Cancel" style="width:60px;"/>
          </td>
        </tr>
      </table>
    </form>
  </body>
</html>
