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
<title>View Log</title>
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

  function callSubmit(thisForm){
    var myOption = -1;
    for (var i=thisForm.radLogType.length-1; i > -1; i--) {
      if (thisForm.radLogType[i].checked) {
        myOption = i;
      }
    }
    //alert(myOption + " test" + thisForm.txtViewLinesFromStartTill.value);
    
    if ((myOption==1) && ((thisForm.txtViewLinesFromStartTill.value==null) || (thisForm.txtViewLinesFromStartTill.value==""))){
      alert('<bean:message key="errors.enterNumberOfLinesToBeViewed" />');
      thisForm.displayLog.value="";
      thisForm.txtViewLinesFromStartTill.setFocus;
      return false;
      
    }
    if ((myOption==2) && ((thisForm.txtViewLinesTillEndStartingFrom.value==null) || (thisForm.txtViewLinesTillEndStartingFrom.value==""))){
      alert('<bean:message key="errors.enterNumberOfLinesToBeViewed" />');
      thisForm.txtViewLinesFromStartTill.setFocus;
      thisForm.displayLog.value="";
      return false;
      
    }
    thisForm.submit();   
  }

//-->
</script>
</head>

<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')">
<form action="viewLogAction.do" method="POST" >

<table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="785">&nbsp;</td>
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
      </table>
    </td>
  </tr>
  <tr>
    <td height="510" valign="top" class="bgColor_D8EAEC bdrColor_336666">
	<table width="745" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>
          <table width="745" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="100" class="bdrBottomColor_336666"><a href="configurationMainAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_configuration_ovr.gif',1)"><img src="images/tab_configuration.gif" alt="Configuration" name="Configuration" width="100" height="25" border="0"></a></td>
              <td width="55" class="bdrBottomColor_336666"><a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)"><img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"></a></td>
              <td width="55" class="bdrBottomColor_336666"><a href="jobListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
              <td width="55"class="bdrBottomColor_336666"><a onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
              <!--<td width="55"class="bdrBottomColor_336666"><a href="availablePatchesAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>-->
              <td width="55"><a href="viewLogAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
              <td width="55"class="bdrBottomColor_336666"><a href="auditTrailListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
              <td class="bdrBottomColor_336666">&nbsp;</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td height="460px" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
		<table width="700" border="0" align="center" cellspacing="8" class="bdrColor_336666"
         cellpadding="3">
			<tr>
			  <td width="84">
			    <bean:message key="lbl.Default"/>
			  </td>
			  <td width="195">&nbsp;</td>
			  <td width="101">&nbsp;</td>
			</tr>
			<tr>
			  <td>
          <div align="right">
            <html:radio name="viewLogForm" property="radLogType" value="1" />
          </div>
        </td>
			  <td>
			    <bean:message key="lbl.FullLog"/>
			  </td>
			  <td>&nbsp;</td>
			</tr>
			<tr>
			  <td>
          <div align="right">
            <html:radio name="viewLogForm" property="radLogType" value="2" />
          </div></td>
			  <td>
			    <bean:message key="lbl.ViewLinesFromStartTill"/>
			  </td>
			  <td>
          <html:text name="viewLogForm" property="txtViewLinesFromStartTill" style="width:100px; align:right" styleClass="bdrColor_336666" />
        </td>
			</tr>
			<tr>
			  <td>
          <div align="right">
            <html:radio name="viewLogForm" property="radLogType" value="3" />
          </div>
        </td>
			  <td>
			    <bean:message key="lbl.ViewLinesTillEndStartingFrom"/>
			  </td>
			  <td>
          <html:text name="viewLogForm" property="txtViewLinesTillEndStartingFrom" style="width:100px; align:right" styleClass="bdrColor_336666" />
        </td>
			</tr>
			<tr>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
			  <td>&nbsp;</td>
			</tr>
			<tr>
			  <td colspan="3">
          <html:button property="btnViewLog" styleClass="buttons" style="width:80px; margin-left:" onclick="callSubmit(this.form)"><bean:message key="btn.ViewLog" /></html:button> 
        </td>
			  </tr>
			<tr>
			  <td colspan="3">
          <html:textarea name="viewLogForm" property="displayLog"
                         style="width:600px; height:150px"/>
        </td>
			  </tr>
</table>		</td>
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
                <td width="723px">
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
</form>
</body>
</html>