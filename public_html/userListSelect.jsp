<%
  if((sfu.beans.user.UserProfile)session.getAttribute("userProfile")==null){
      response.sendRedirect("login.jsp?sessionExpired=true");  
  }
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="pageCount" name="userListSelectForm" property="hdnSearchPageCount" />        
<bean:define id="pageNo" name="userListSelectForm" property="hdnSearchPageNo" />        



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

function callPage(pageName){
  var thisForm=document.forms[0]; 
  var index=0;
  if(pageName =="userListSelect"){
    index=getRadioSelectedIndex(thisForm.radSelect);
    if (index>-2){
        thisForm.action="userListSelectAction.do";
    }else{
      alert('<bean:message key="alert.UserSelectRequired" />');
      return false;
    }
    
  }else if(pageName =="userList"){
    thisForm.action="userListSelectAction.do";      
    
  }
  thisForm.hdnSearchPageNo.value=navBar.getPageNumber();
  
  thisForm.submit();
}

function selectOnclick(){
  thisForm=document.forms[0];
  var index=0;
  index=getRadioSelectedIndex(thisForm.radSelect);
  var controlName=document.forms[0].hdnOpenerControl.value;
  var control=null;
  if (index>-2){
    if(controlName!=""){
      control=eval("opener.document.forms[0]." +controlName);
      if (typeof control!="undefined"){
        if((control.type=="select-multiple")||(control.type=="select")){
          for(var openerIndex=0;openerIndex<control.options.length; openerIndex++){
            if(control.options[openerIndex].value==document.forms[0].radSelect[index].value){                  
              window.close();
              return false;
            }       
          }
          var opt=opener.document.createElement("OPTION");
          opt.text=document.forms[0].radSelect[index].value;
          opt.value=document.forms[0].radSelect[index].value;
          var newIndex=control.options.length ;
          control.options[newIndex]=opt;
        }else{
          control.value=document.forms[0].radSelect[index].value;
        }
        window.close();
        return true;
      }
    }
  }else{
    alert("<bean:message key="alert.UserSelectRequired" />");
  }
}
function cancelOnClick(){
  window.close();
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

<html:form action="userListSelectAction.do" focus="txtSearchUserName" >
<html:hidden property="hdnSearchPageNo" />
<html:hidden property="hdnOpenerControl" />

<table id="outerMost" width="350px" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="350px">&nbsp;</td>
  </tr>
  

  <tr>
    <td height="410px" width="350px" valign="top" class="bgColor_D8EAEC bdrColor_336666">
    <table width="350px" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="center">
          <div  align="center" style="height:30px; width:320px;" class="bdrColor_336666" >
            <table width="100%" border="0" cellpadding="0" id="tblSearch" cellspacing="1">
              <tr>
                <td height="5px"></td>
              </tr>
              <tr >
                <td align="right">
                  <bean:message key="lbl.UserId" />: 
                </td>
                <td align="right" width="124px">
                  <html:text property="txtSearchUserName" styleClass="bdrColor_336666" style="width:125px" />
                </td>
                <td align="right" width="60px">
                  <html:button property="btnSearch" styleClass="buttons" style="width:60px;" onclick="return callPage('userList');"><bean:message key="btn.Search" /></html:button>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
      <tr><td colspan="3" height="2px"></td></tr>
      <tr>
        <td height="350px" align="center" valign="top">
          <div  align="center" style="overflow:auto; height:380px; width:320px;" frameborder="0" class="bdrColor_336666">
            <table width="100%" align="center" border="0" cellpadding="3" cellspacing="2">
              <tr>
                <th width="2%">&nbsp;</th>
                <th width="49%"><div align="center">
                  <bean:message key="tbl.UserId" />
                </div></th>
                <th width="49%"><div align="center">
                  <bean:message key="tbl.UserStatus" />
                </div></th>
              </tr>
              <logic:iterate name="users" id="user" >
                <bean:define id="userId" name="user" property="userId" scope="page" />
                <% if (firstTableRow == true){ firstTableRow = false; %>
                  <tr>
                <% }else{ firstTableRow = true; %>
                  </tr>
                  <tr class="tr_C5E2E5">
                <% }%>
                  <td width="2%"><html:radio property="radSelect" value="<%=(String)userId%>"  tabindex="6"  />                  </td>
                  <td align="center" width="32%"><bean:write name="user" property="userId"/>                  </td>
                  <td align="center" width="32%"><bean:write name="user" property="isAdmin"/>                  </td>
                </tr>
              </logic:iterate>
            </table>
        </div></td>
      </tr>
      <tr>
        <td height="30px">
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td >&nbsp;</td>
              <td width="48%"div align="right">
			  <html:button property="btnSelect" styleClass="buttons" style="width:60px;" onclick="selectOnclick()" >
                  <bean:message key="btn.Select" />
              </html:button>
                  <html:button property="btnCancel" styleClass="buttons" style="width:60px; margin-right:10px" onclick="return cancelOnClick();" >
                    <bean:message key="btn.Cancel" />
              </html:button>
			</td>
            </tr>
        </table>
		</td>
      </tr>
    </table>
    </td>
  </tr>
  <tr><td height="2px"></td></tr>
  <tr>
    <td>
      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="354px" height="23px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
        <tr class="bgColor_D8EAEC bdrColor_336666 ">
          <td width="30px" >
            <div class="imgStatusMsg"/>
          </td>
          <td width="324px" align="right" ><script type="text/javascript">navBar.render();</script></td>
        </tr>
      </table>
      <!-- Status Bar Table Ends-->
    </td>
  
</table>
  
</html:form>
</body>
</html:html>
