<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="pageCount" name="userListForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="userListForm" property="hdnSearchPageNo" />        



<%
//Variable declaration
boolean firstTableRow;
firstTableRow = true;
%>
<html:html>
<head>
<title><bean:message key="title.UserList" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="general.js" ></script>
<script language="JavaScript" type="text/JavaScript" src="navigationbar.js" ></script>
    <script>
      var pageCount=parseInt(<%=pageCount%>);
      var navBar=new NavigationBar("navBar");
      navBar.setPageNumber(<%=pageNo%>);
      navBar.setPageCount(<%=pageCount%>);
      navBar.onclick="callPage('userList')";

      navBar.msgPageNotExist="<bean:message  key="page.pageNumberNotExists" />";
      navBar.msgNumberOnly="<bean:message   key="page.enterNumbersOnly"/>";    
      navBar.msgEnterPageNo="<bean:message   key="page.enterPageNo"/>";
      navBar.msgOnFirst="<bean:message    key="page.onFirst"/>";
      navBar.msgOnLast="<bean:message    key="page.onLast"/>";

      navBar.lblPage="<bean:message    key="lbl.Page"/>";
      navBar.lblOf="<bean:message    key="lbl.Of"/>";

      navBar.tooltipPageNo="<bean:message    key="tooltips.PageNo"/>";
      navBar.tooltipFirst="<bean:message    key="tooltips.First"/>";
      navBar.tooltipNext="<bean:message    key="tooltips.Next"/>";
      navBar.tooltipPrev="<bean:message    key="tooltips.Previous"/>";
      navBar.tooltipLast="<bean:message    key="tooltips.Last"/>";
      navBar.tooltipGo="<bean:message    key="btn.Go"/>";
    </script>
<script language="JavaScript" type="text/JavaScript"  >
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
  var thisForm=document.forms[0]; 
  var index=0;
  if(pageName =="userCreate"){
    thisForm.action="userCreateB4Action.do";
      
  }else if(pageName =="userModify" || pageName =="userDelete"){
    index=getRadioSelectedIndex(thisForm.radSelect);
    if (index>-2){
      if(pageName =="userModify"){
        thisForm.action="userModifyB4Action.do";
       
      }else if(pageName =="userDelete"){
        if (confirm('<bean:message   key="alert.delete.confirm" />')){
          thisForm.action="userDeleteAction.do";      
        }else{
          return false;
        }
        
      }
    }else{
      alert('<bean:message key="alert.UserSelectRequired" />');
      return false;
    }
    
  }else if(pageName =="userList"){
    thisForm.action="userListAction.do";      
    
  }
  thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
  
  thisForm.submit();
}

function document_onkeypress(e){
  var key;
  if (window.event){
    key = window.event.keyCode;
  }else if (e){
    key = e.which;
  }else{
    return false;
  }      
  if(key==13 && MM_findObj('tblSearch').style.display!='none'){
    callPage('userList');
  }
}
document.onkeypress=document_onkeypress;
//-->
</script>
</head>

<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/tab_configuration_ovr.gif','images/tab_user_ovr.gif','images/tab_jobs_ovr.gif','images/tab_patches_ovr.gif','images/tab_log_ovr.gif','images/tab_audit_ovr.gif')">

<html:form action="userListAction.do" >
<html:hidden property="hdnSearchPageNo" />
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
                <td width="55" ><a href="userListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('User','','images/tab_user_ovr.gif',1)"><img src="images/tab_user.gif" alt="User" name="User" width="55" height="25" border="0"></a></td>
                <td width="55" class="bdrBottomColor_336666"><a href="jobListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Jobs','','images/tab_jobs_ovr.gif',1)"><img src="images/tab_jobs.gif" alt="Jobs" name="Jobs" width="55" height="25" border="0"></a></td>
                <td width="55"class="bdrBottomColor_336666"><a  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>
                <!--<td width="55"class="bdrBottomColor_336666"><a href="availablePatchesAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Patches','','images/tab_patches_ovr.gif',1)"><img src="images/tab_patches.gif" alt="Patches" name="Patches" width="55" height="25" border="0"></a></td>-->
                <td width="55" class="bdrBottomColor_336666"><a href="viewLogAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Log','','images/tab_log_ovr.gif',1)"><img src="images/tab_log.gif" alt="Log" name="Log" width="55" height="25" border="0"></a></td>
                <td width="55"class="bdrBottomColor_336666"><a href="auditTrailListAction.do" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Audit','','images/tab_audit_ovr.gif',1)"><img src="images/tab_audit.gif" alt="Audit" name="Audit" width="55" height="25" border="0"></a></td>
                <td class="bdrBottomColor_336666">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td height="40px" class="bdrLeftColor_336666 bdrRightColor_336666">
            <table width="98%"  border="0" id="tblSearch" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="50%">
                    <html:button property="btnCreate" styleClass="buttons" style="width:60px;" onclick="return callPage('userCreate');" ><bean:message key="btn.Create" /></html:button>
                    <html:button property="btnModify" styleClass="buttons" style="width:60px;" onclick="return callPage('userModify');" ><bean:message key="btn.Modify" /></html:button>
                    <html:button property="btnDelete" styleClass="buttons" style="width:60px;" onclick="return callPage('userDelete');" ><bean:message key="btn.Delete" /></html:button>
                </td>
                <td width="50%">
                  <div align="right">
                      <html:text property="txtSearchUserName" styleClass="bdrColor_336666" style="width:125px" />
                      <html:button property="btnSearch" styleClass="buttons" style="width:60px;" onclick="return callPage('userList');"><bean:message key="btn.Search" /></html:button>
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
                  <th width="5%">&nbsp;</th>
                  <th width="19%"><div align="center"><bean:message key="tbl.UserId" /></div></th>
                  <th width="19%"><div align="center"><bean:message key="tbl.UserName" /></div></th>
                  <th width="19%"><div align="center"><bean:message key="tbl.EmailId" /></div></th>
                  <th width="19%"><div align="center"><bean:message key="tbl.IsLocked" /> ?</div></th>
                  <th width="19%"><div align="center"><bean:message key="tbl.UserStatus" /></div></th>
                </tr>
                <logic:iterate name="users" id="user" >
                  <bean:define id="userId" name="user" property="userId" scope="page" /> 
                  <% if (firstTableRow == true){ firstTableRow = false; %>
                    <tr>
                  <% }else{ firstTableRow = true; %>
                    </tr>
                    <tr class="tr_C5E2E5">
                  <% }%>
                    <td width="5%">
                      <html:radio property="radSelect" value="<%=(String)userId%>"  tabindex="6"  />                
                    </td>
                    <td align="center" width="19%"><bean:write name="user" property="userId"/></td>
                    <td align="center" width="19%"><bean:write name="user" property="userName"/></td>
                    <td align="center" width="19%"><bean:write name="user" property="emailId"/></td>
                    <td align="center" width="19%"><bean:write name="user" property="isLocked"/></td>
                    <td align="center" width="19%"><bean:write name="user" property="isAdmin"/></td>
                  </tr>    
                </logic:iterate>
              </table>
              <logic:empty name="users">
              <div style="width:100%; position:relative; top:175px; text-align:center;" class="tabText">
                <bean:message key="info.no_item_found"/>
              </div>
              </logic:empty>
            </div>	
          </td>
        </tr>
        <tr>
          <td height="30px" class="bdrLeftColor_336666  bdrBottomColor_336666 bdrRightColor_336666">
            <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="88%">
                  <div align="right">&nbsp</div>
                </td>
                <td width="12%">
                  <div align="right">
                    
                  </div>
                </td>
              </tr>
            </table>
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
          <td>&nbsp;<html:messages id="msg1" message="false" ><bean:write name="msg1" /></html:messages></td>
          <td width="200px" ><script>navBar.render();</script></td>
        </tr>
      </table>
      <!-- Status Bar Table Ends-->
    </td>
  </tr>
</table>
</html:form>
</body>
</html:html>
