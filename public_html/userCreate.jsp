<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html >
<head>
<title><bean:message key="title.CreateUser"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="main.css" rel="stylesheet" type="text/css">
<html:javascript formName="userForm" dynamicJavascript="true" staticJavascript="false" />
<script language="javascript1.1" src="staticJavascript.jsp"></script>
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
<script language="Javascript1.1">

  function callSubmit(thisForm){
    if(validateUserForm(thisForm)){
      if(thisForm.txtUserId.value.indexOf(" ")>-1){
        alert('<bean:message key="alert.UserIdNoSpace" />');
        return false;
      }
      if (thisForm.txtPassword.value!=thisForm.txtConfirmPassword.value){
       alert("<bean:message key="alert.BothPasswordsNotEqual" />");
       return false;
      }
      
      thisForm.submit();
    }
  }
</script>
</head>

<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')">
<html:form action="userCreateAction.do"  >
<html:hidden property="hdnSearchPageNo" />
  <table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="785">&nbsp;</td>
    </tr>
    <tr>
      <td>
        <!--Sfu Logo table starts-->
        <table id="topBar" width="100%" height="50px"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="250"><img src="images/sfu_logo.jpg" width="250" height="50"></td>
            <td valign="bottom" background="images/tile_topbar.jpg"><div align="right">
            <!--<a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)"><img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0"></a>
            <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)"><img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0"></a>-->
            <a href="logoutAction.do"  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
              <img src="images/bt_logout.jpg" alt="Logout" name="Logout" width="52" height="40" border="0">
            </a>
            </div></td>
            <td width="13"><img src="images/topbar_right.jpg" width="13" height="50"></td>
          </tr>
        </table>
        <!--Sfu Logo table ends-->
      </td>
    </tr>
    <tr>
      <td height="510" valign="top" class="bgColor_D8EAEC bdrColor_336666">
        <!--Border table starts-->
        <table width="745" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>
              <!--Tab table starts-->
              <table width="745" border="0" cellspacing="0" cellpadding="0">
                <tr>
                <td width="100" class="bdrBottomColor_336666"><a href="configurationMainAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Configuration','','images/tab_configuration_ovr.gif',1)"><img src="images/tab_configuration.gif" alt="Configuration" name="Configuration" width="100" height="25" border="0"></a></td>
                <td width="55" ><a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)"><img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a href="jobListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
                <td width="55"class="bdrBottomColor_336666"><a href="availablePatchesAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a href="viewLogAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
                <td width="55"class="bdrBottomColor_336666"><a href="auditTrailListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
                <td class="bdrBottomColor_336666">&nbsp;</td>
                </tr>
              </table>
              <!--Tab table ends-->
            </td>
          </tr>
          <tr>
            <td height="460px" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
              <!--user create table Starts-->
              <table width="508" border="0" align="center" cellspacing="8" class="bdrColor_336666">
                <tr>
                  <td colspan="2">
                    <DIV align="center">
                      <STRONG><bean:message key="page.CreateUser" /></STRONG>
                    </DIV>
                  </td>
                </tr>
                <tr>
                  <td><div align="right">
                    <bean:message key="lbl.UserId" />
                  </div></td>
                  <td>
                    <html:text name ="userForm" property="txtUserId"  styleClass="bdrColor_336666" style="width:125px" maxlength="20" />
                  </td>
                </tr>
                <tr>
                  <td width="231"><div align="right">
                    <bean:message key="lbl.UserName"/>
                  </div></td>
                  <td width="247">
                  <html:text property="txtUserName"  styleClass="bdrColor_336666" style="width:125px" maxlength="20"/>
                  </td>
                </tr>
                <tr>
                  <td><div align="right">
                    <bean:message key="tbl.EmailId"/>
                  </div></td>
                  <td>
                    <html:text property="txtEmailId" styleClass="bdrColor_336666" style="width:125px" />
                  </td>
                </tr>                
                <tr>
                  <td><div align="right">
                    <bean:message key="lbl.Password"/>
                  </div></td>
                  <td>
                  <html:password  property="txtPassword" styleClass="bdrColor_336666" style="width:125px" maxlength="20" /></td>
                </tr>
                <tr>
                  <td><div align="right">
                    <bean:message key="lbl.ConfirmPassword"/>
                  </div></td>
                  <td>
                    <html:password property="txtConfirmPassword" styleClass="bdrColor_336666" style="width:125px" maxlength="20" />
                  </td>
                </tr>
                <tr>
                  <td><div align="right">
                    <bean:message key="lbl.UserIsLocked" />
                  </div></td>
                  <td>
                    <html:checkbox  property="chkIsLocked" />
                  </td>
                </tr>
                
                <tr>
                  <td><div align="right">
                    <bean:message key="lbl.UserIsAdmin" />
                  </div></td>
                  <td>
                    <html:checkbox  property="chkIsAdmin"  />
                  </td>
                </tr>
                
                <tr>
                  <td>&nbsp;</td>
                  <td>
                    <html:button property="btnOk" styleClass="buttons" style="width:60px; margin-left:" onclick="callSubmit(this.form)"><bean:message key="btn.Ok" /></html:button> 
                    <html:cancel styleClass="buttons" style="width:60px;" ><bean:message key="btn.Cancel" /></html:cancel>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <!--Border table ends-->
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
          <td>&nbsp;<html:messages id="msg1" message="true" ><bean:write name="msg1" /></html:messages></td>
        </tr>

      </table>
      <!-- Status Bar Table Ends-->
    </td>
  </tr>    
  </table>
</html:form>
</body>
</html:html>
