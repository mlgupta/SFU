<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>
<html:html>
<head>
<title>User List</title>
<link href="main.css" rel="stylesheet" type="text/css">
<script >
<!--
function MM_swapImgRestore() { 
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { 
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { 
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { 
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function callPage(pageName){
 document.form.submit();
  var thisForm=document.forms[0]; 
  if(pageName =="userCreate"){
    thisForm.action="userCreateB4Action.do";
      
  }else if(pageName =="userModify"){
    thisForm.action="userModifyB4Action.do";
   
  }else if(pageName =="userDelete"){
    thisForm.action="userDeleteAction.do";      
  }  
  thisForm.submit();
}
//-->
</script>
</head>

<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')">
<html:form name="UserListForm" type="sfu.actionforms.user.UserListForm" action="userCreateAction.do" >

<table id="outerMost" width="765" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="785">&nbsp;</td>
  </tr>
  <tr>
    <td>
	<table id="topBar" width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="250"><img src="images/sfu_logo.jpg" width="250" height="50"></td>
        <td valign="bottom" background="images/tile_topbar.jpg"><div align="right"><a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)"><img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0"></a> <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)"><img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0"></a></div></td>
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
            <td width="55"><a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)"><img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"></a></td>
            <td width="55" class="bdrBottomColor_336666"><a href="jobList.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
            <td width="55"class="bdrBottomColor_336666"><a href="availablePatches.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
            <td width="55" class="bdrBottomColor_336666"><a href="viewLog.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
            <td width="55"class="bdrBottomColor_336666"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
            <td class="bdrBottomColor_336666">&nbsp;</td>
          </tr>
        </table>
		</td>
      </tr>
      <tr>
        <td height="40px" class="bdrLeftColor_336666 bdrRightColor_336666">
		  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="50%">
                <input name="btnCreate" type="button" class="buttons" style="width:60px;" value="Create" onclick="return callPage('userCreate');" />
                <input name="btnModify" type="button" class="buttons" style="width:60px;" value="Modify" onclick="return callPage('userModify');" />
                <input name="btnDelete" type="button" class="buttons" style="width:60px;" value="Delete" onclick="return callPage('userDelete');" />
            </td>
            <td width="50%"><div align="right">
                <input name="text2" type="text" class="bdrColor_336666" style="width:125px">
                <input name="btnSearch" type="button" class="buttons" style="width:60px;" value="Search">
            </div>
			</td>
          </tr>
        </table>
		</td>
      </tr>
      <tr>
        <td height="380px" align="center" valign="top" class="bdrLeftColor_336666 bdrRightColor_336666">
			
			<div  align="center" style="overflow:auto; height:380px; width:727px;" frameborder="0" class="bdrColor_336666">
			  <table width="100%" align="center" cellpadding="3" cellspacing="2">
                <tr>
                  <th width="4%">&nbsp;</th>
                  <th width="25%"><div align="center">Name</div></th>
                  <th width="29%"><div align="center">Full Name</div></th>
                  <th width="18%"><div align="center">Expiration</div></th>
                  <th width="11%"><div align="center">Is Locked ?</div></th>
                  <th width="13%"><div align="center">Home Folder</div></th>
                </tr>
                <logic:iterate name="users" id="user" >
                <bean:define id="name" name="user" property="userName" scope="page" /> 
                <%if (firstTableRow == true){ firstTableRow = false; %>
                       <tr  class="tr_C5E2E5">
                  <%}else{ firstTableRow = true; %>
                       <tr>                  
                  <%}%>
                
                  <td width="4%">
                    <html:radio property="radSelect" value="<%=name%>"  tabindex="6"  />                
                  </td>
                  <td width="25%"><bean:write name="user" property="userName"/></td>
                  <td width="29%"><bean:write name="user" property="fullName"/></td>
                  <td width="18%"><bean:write name="user" property="expiration"/></td>
                  <td width="11%"><bean:write name="user" property="isLocked"/></td>
                  <td width="13%"><bean:write name="user" property="homeFolder"/></td>
                </tr>    
                </logic:iterate>
              </table>
			</div>	
		</td>
      </tr>
	  <tr>
        <td height="30px" class="bdrLeftColor_336666  bdrBottomColor_336666 bdrRightColor_336666">
		  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="88%">
                <div align="right">Page <strong>xx</strong> of <strong>yy</strong> </div></td>
              <td width="12%"><div align="right">
                  <input name="submit" type="button" class="buttons" style="width:60px;" value="Cancel">
              </div></td>
            </tr>
          </table></td>
      </tr>
    </table>
	</td>
  </tr>
</table>
</html:form>
</body>
</html:html>
