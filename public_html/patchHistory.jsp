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
<title>Patch History</title>
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
</head>

<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif','images/tab_avl_patches_ovr.gif','images/tab_patch_history_ovr.gif')">
<form>

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
                    <a href="login.jsp" onmouseout="MM_swapImgRestore()"
                       onmouseover="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)">
                      <img src="images/bt_home.jpg" alt="Home" name="Home"
                           width="52" height="40" border="0"></img>
                    </a>
                     
                    <a href="#" onmouseout="MM_swapImgRestore()"
                       onmouseover="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)">
                      <img src="images/bt_refresh.jpg" alt="Refresh"
                           name="Refresh" width="52" height="40" border="0"></img>
                    </a>
                    -->
                    <a href="logoutAction.do" onMouseOut="MM_swapImgRestore()"
                       onmouseover="MM_swapImage('Logout','','images/bt_logout_ovr.jpg',1)">
                      <img src="images/bt_logout.jpg" alt="Logout" name="Logout"
                           width="52" height="40" border="0"></img>
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
            <td width="55" class="bdrBottomColor_336666"><a href="jobList.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
            <td width="55"><a href="availablePatches.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
            <td width="55" class="bdrBottomColor_336666"><a href="viewLog.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
            <td width="55"class="bdrBottomColor_336666"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
            <td class="bdrBottomColor_336666">&nbsp;</td>
          </tr>
        </table>
		</td>
      </tr>
      <tr>
        <td height="460px" class="bdrLeftColor_336666 bdrBottomColor_336666 bdrRightColor_336666">
		<table id="2ndLevelTags" width="725" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="40px" colspan="3" valign="bottom" >			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="bdrBottomColor_336666">&nbsp;</td>
                <td width="100" class="bdrBottomColor_336666"><a href="availablePatches.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Available Patches','','images/tab_avl_patches_ovr.gif',1)"><img src="images/tab_avl_patches.gif" alt="Available Patches" name="Available Patches" width="100" height="25" border="0"></a></td>
                <td width="100"><a href="patchHistory.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patch History','','images/tab_patch_history_ovr.gif',1)"><img src="images/tab_patch_history.gif" alt="Patch History" name="Patch History" width="100" height="25" border="0"></a></td>
              </tr>
            </table></td>
            </tr>
          
          <tr>
            <td height="105px" colspan="3" class="bdrLeftColor_336666 bdrRightColor_336666 tr_C5E2E5"><table width="98%"  border="0" align="center" cellpadding="1" cellspacing="0" class="borderClrLvl_2">
              <tr>
                <td width="33%" valign="top">
				<fieldset>
                  <legend align="left">
                    <bean:message key="lbl.AppliedOn"/>
                  </legend>
                  <table width="100%"  border="0" cellpadding="0">
                    <tr>
                      <td colspan="2" height="8px"></td>
                    </tr>
                    <tr>
                      <td width="22%"><div align="right">
                        <bean:message key="lbl.From"/>
                      </div></td>
                      <td width="78%"><input type="text" name="txtSearchByUserName22" maxlength="20" tabindex="5" value="" style="width:120px" class="bdrColor_336666">
                          <input type="submit" name="Submit2" value="..." class="buttons" style="width:20px"></td>
                    </tr>
                    <tr>
                      <td><div align="right">
                        <bean:message key="lbl.To"/>
                      </div></td>
                      <td><input type="text" name="txtSearchByUserName23" maxlength="20" tabindex="5" value="" style="width:120px" class="bdrColor_336666">
                          <input type="submit" name="Submit3" value="..." class="buttons" style="width:20px"></td>
                    </tr>
                    <tr>
                      <td colspan="2" height="3px"></td>
                    </tr>
                  </table>
                </fieldset>
				</td>
                <td width="33%">
                  <fieldset>
                  <legend align="left">
                    <bean:message key="lbl.ReleaseDate"/>
                  </legend>
                  <table width="100%"  border="0" cellpadding="0">
                    <tr>
                      <td colspan="2" height="8px"></td>
                    </tr>
                    <tr>
                      <td width="22%"><div align="right">
                        <bean:message key="lbl.From"/>
                      </div></td>
                      <td width="78%"><input type="text" name="txtSearchByUserName22" maxlength="20" tabindex="5" value="" style="width:120px" class="bdrColor_336666">
                          <input type="submit" name="Submit2" value="..." class="buttons" style="width:20px"></td>
                    </tr>
                    <tr>
                      <td><div align="right">
                        <bean:message key="lbl.To"/>
                      </div></td>
                      <td><input type="text" name="txtSearchByUserName23" maxlength="20" tabindex="5" value="" style="width:120px" class="bdrColor_336666">
                          <input type="submit" name="Submit3" value="..." class="buttons" style="width:20px"></td>
                    </tr>
                    <tr>
                      <td colspan="2" height="3px"></td>
                    </tr>
                  </table>
                </fieldset></td>
                <td width="33%">
                  <fieldset>
                  <legend align="left">
                    <bean:message key="lbl.DownloadDate"/>
                  </legend>
                  <table width="100%"  border="0" cellpadding="0">
                    <tr>
                      <td colspan="2" height="8px"></td>
                    </tr>
                    <tr>
                      <td width="22%"><div align="right">
                        <bean:message key="lbl.From"/>
                      </div></td>
                      <td width="78%"><input type="text" name="txtSearchByUserName222" maxlength="20" tabindex="5" value="" style="width:120px" class="bdrColor_336666">
                          <input type="submit" name="Submit4" value="..." class="buttons" style="width:20px"></td>
                    </tr>
                    <tr>
                      <td><div align="right">
                        <bean:message key="lbl.To"/>
                      </div></td>
                      <td><input type="text" name="txtSearchByUserName232" maxlength="20" tabindex="5" value="" style="width:120px" class="bdrColor_336666">
                          <input type="submit" name="Submit5" value="..." class="buttons" style="width:20px"></td>
                    </tr>
                    <tr>
                      <td colspan="2" height="3px"></td>
                    </tr>
                  </table>
                </fieldset></td>
              </tr>
            </table></td>
            </tr>
          <tr>
        <td height="260px" align="center" valign="top" class="bdrLeftColor_336666 bdrRightColor_336666 tr_C5E2E5">
			
			<div  align="center" style="overflow:auto; height:260px; width:705px;" frameborder="0" class="bdrColor_336666">
			  <table width="100%" align="center" cellpadding="3" cellspacing="2">
                <tr>
                  <th width="23%"><div align="center">
                    <bean:message key="lbl.PatchName"/>
                  </div></th>
                  <th width="17%"><div align="center">
                    <bean:message key="lbl.PatchNumber"/>
                  </div></th>
                  <th width="11%">
                    <bean:message key="lbl.AppliedOn"/>
                  </th>
                  <th width="13%"><div align="center">
                    <bean:message key="lbl.ReleaseDate"/>
                  </div></th>
                  <th width="14%"><div align="center">
                    <bean:message key="lbl.DownloadDate"/>
                  </div></th>
                  <th width="22%"><div align="center">
                    <bean:message key="lbl.Description"/>
                  </div></th>
                </tr>
                <tr>
                  <td width="23%">wwwwwwwwwwwwwww</td>
                  <td width="17%">No_32746372 tera </td>
                  <td width="11%">88/88/8888</td>
                  <td width="13%">88/88/8888</td>
                  <td width="14%">88/88/8888</td>
                  <td width="22%">Description about Patch </td>
                </tr>
                <tr class="tr_C5E2E5">
                  <td width="23%">qqqqqqqqqqqqqqqqqqqqqq</td>
                  <td width="17%">&nbsp;</td>
                  <td width="11%">&nbsp;</td>
                  <td width="13%">&nbsp;</td>
                  <td width="14%">&nbsp;</td>
                  <td width="22%">&nbsp;</td>
                </tr>
                <tr>
                  <td width="23%">eeeeeeeeeeeeeeeeeee</td>
                  <td width="17%">&nbsp;</td>
                  <td width="11%">&nbsp;</td>
                  <td width="13%">&nbsp;</td>
                  <td width="14%">&nbsp;</td>
                  <td width="22%">&nbsp;</td>
                </tr>
                <tr class="tr_C5E2E5">
                  <td width="23%">qqqqqqqqqqqqqqqqqqqqqq</td>
                  <td width="17%">&nbsp;</td>
                  <td width="11%">&nbsp;</td>
                  <td width="13%">&nbsp;</td>
                  <td width="14%">&nbsp;</td>
                  <td width="22%">&nbsp;</td>
                </tr>
                <tr>
                  <td width="23%">eeeeeeeeeeeeeeeeeee</td>
                  <td width="17%">&nbsp;</td>
                  <td width="11%">&nbsp;</td>
                  <td width="13%">&nbsp;</td>
                  <td width="14%">&nbsp;</td>
                  <td width="22%">&nbsp;</td>
                </tr>
                <tr class="tr_C5E2E5">
                  <td width="23%">qqqqqqqqqqqqqqqqqqqqqq</td>
                  <td width="17%">&nbsp;</td>
                  <td width="11%">&nbsp;</td>
                  <td width="13%">&nbsp;</td>
                  <td width="14%">&nbsp;</td>
                  <td width="22%">&nbsp;</td>
                </tr>
                <tr>
                  <td width="23%">eeeeeeeeeeeeeeeeeee</td>
                  <td width="17%">&nbsp;</td>
                  <td width="11%">&nbsp;</td>
                  <td width="13%">&nbsp;</td>
                  <td width="14%">&nbsp;</td>
                  <td width="22%">&nbsp;</td>
                </tr>
              </table>
			</div>	
		</td>
      </tr>
	  <tr>
        <td height="30px" class="bdrLeftColor_336666  bdrBottomColor_336666 bdrRightColor_336666 tr_C5E2E5">
		  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="89%">
                <div align="right">Page <strong>xx</strong> of <strong>yy</strong> </div>                <div align="right">
                </div></td>
              <td width="11%"><div align="right">
                <input name="submit2" type="submit" class="buttons" style="width:60px;" value="<bean:message key="btn.Ok"/>">
              </div></td>
            </tr>
          </table></td>
      </tr>
        </table>		</td>
	</tr>
	  
    </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>
