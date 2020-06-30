<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title>Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script language="Javascript1.1" type="text/JavaScript">
function callPage(pageName){
  var thisForm=document.forms[0]; 
  if(pageName=='smtpConfiguration'){
    document.forms[0].pageToCall.value='smtpConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='faxServerConfiguration'){
    document.forms[0].pageToCall.value='faxServerConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='scannerConfiguration'){
    document.forms[0].pageToCall.value='scannerConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='loggerConfiguration'){
    document.forms[0].pageToCall.value='loggerConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='patchConfiguration'){
    document.forms[0].pageToCall.value='patchConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='repositoryConfiguration'){
    document.forms[0].pageToCall.value='repositoryConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='replicationConfiguration'){
    document.forms[0].pageToCall.value='replicationConfigurationB4Action.do';
    document.forms[0].submit();    
  }else if(pageName=='jobConfiguration'){
    document.forms[0].pageToCall.value='jobConfigurationB4Action.do';
    document.forms[0].submit();    
  }
}
</script>
</head>

<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')">


<table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>
	<table id="topBar" width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="250"><img src="images/sfu_logo.jpg" width="250" height="50"></td>
        <td valign="bottom" background="images/tile_topbar.jpg">
          <div align="right">
          <!--
            <a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)">
              <img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0">
            </a>
            <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)">
              <img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0">
            </a>
            -->
            <a href="logoutAction.do"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
              <img src="images/bt_logout.jpg" alt="Logout" name="Logout" width="52" height="40" border="0">
            </a>
          </div>
        </td>
        <td width="13"><img src="images/topbar_right.jpg" width="13" height="50"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="510" valign="top" class="bgColor_D8EAEC bdrColor_336666">
    <table width="725" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><table width="745" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100"><a href="configurationMainAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_configuration_ovr.gif',1)"><img src="images/tab_configuration.gif" alt="Configuration" name="Configuration" width="100" height="25" border="0"></a></td>
            <td width="55" class="bdrBottomColor_336666"><a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)"><img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"></a></td>
            <td width="55" class="bdrBottomColor_336666"><a href="jobListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
            <td width="55"class="bdrBottomColor_336666"><a  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
            <!-- To be uncommented-->
            <!--<td width="55"class="bdrBottomColor_336666"><a href="availablePatchesAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>-->
            <td width="55" class="bdrBottomColor_336666"><a href="viewLogAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
            <td width="55"class="bdrBottomColor_336666"><a href="auditTrailListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
            <td class="bdrBottomColor_336666">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="450px" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
		<form action="configurationMainAction.do">		
    <bean:define id="pageToCall" name="ConfigurationMainForm" property="pageToCall" />
    <input type="hidden" name="pageToCall" />
        <table width="27%"  border="0" cellpadding="0" cellspacing="10"style="float:left">
          <tr>
            <td><div align="center">
              <input name="Submit2" type="button" class="buttons" value="SMTP Configuration"  style="width:160px;" onclick="callPage('smtpConfiguration')">
			  </div></td>
            </tr>
          <tr>
            <td><div align="center">
              <input name="Submit22" type="submit" class="buttons" value="Fax Server Configuration" style="width:160px;" onclick="callPage('faxServerConfiguration')">
            </div></td>
          </tr>
          <tr>
            <td><div align="center">
              <input name="Submit23" type="submit" class="buttons" value="Scanner Configuration" style="width:160px;" onclick="callPage('scannerConfiguration')">
            </div></td>
          </tr>
          <!--
          ****To be removed later****
          <tr>
            <td><div align="center">
              <input name="Submit24" type="submit" class="buttons" value="Logger Configuration" style="width:160px;" onclick="callPage('loggerConfiguration')">
            </div></td>
          </tr>
          -->
          <!--
          ****To be enabled later****
          <tr>
            <td><div align="center">
              <input name="Submit25" type="submit" class="buttons" value="LDAP Configuration ???" style="width:160px;">
              </div></td>
          </tr>
          -->
          <tr>
            <td><div align="center">
              <input name="Submit26" type="submit" class="buttons" value="Repository Configuration" style="width:160px;" onclick="callPage('repositoryConfiguration')">
            </div></td>
          </tr>
          <tr>
            <td><div align="center">
              <input name="Submit27" type="submit" class="buttons" value="Replication Configuration" style="width:160px;" onclick="callPage('replicationConfiguration')">
            </div></td>
          </tr>
          <!--
          ****To be enabled later****
          <tr>
            <td><div align="center">
              <input name="Submit28" type="submit" class="buttons" value="Patch Configuration" style="width:160px;" onclick="callPage('patchConfiguration')">
            </div></td>
          </tr>
          -->
          <tr>
            <td><div align="center">
              <input name="Submit282" type="submit" class="buttons" value="Job Configuration" style="width:160px;" onclick="callPage('jobConfiguration')">
            </div></td>
          </tr>
        </table>
        <table width="70%" height="400"  border="0" style="float:left">
          <tr>
            <td>
              <iframe name="configuration" src="<%=pageToCall%>" scrolling="no" height="410px" width="500px"  
              style="position:relative; left:0px; top:0px" frameborder="0" class="bdrColor_336666">
              </iframe>
            </td>
          </tr>
          
        </table>
      </form>
    </td>
  </tr>
  
      </table>
    </td>
  </tr>
  <tr>
    <td height="2px"></td>
  </tr>
  <tr>
    <td height="23px" class="bgColor_D8EAEC bdrColor_336666 ">
      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="100%" height="23px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
        <tr class="bgColor_D8EAEC bdrColor_336666 ">
          <td width="30px" >
            <div class="imgStatusMsg"/>
          </td>
          <td width="733px">
          <logic:messagesPresent  message="true">
          <div align="left" style="color:red">
             <html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages>
          </div>
          </logic:messagesPresent>
          </td>
        </tr>
        
      </table>
      <!-- Status Bar Table Ends-->
    </td>
  </tr>
</table>
</body>
</html>
