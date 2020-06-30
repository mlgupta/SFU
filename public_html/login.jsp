
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<head>
<title><bean:message key="title.Login" /></title>
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
  function callSubmit(){

      thisForm=document.forms[0];
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
    if(key==13){
      callSubmit();
    }
  }
  document.onkeypress=document_onkeypress;
//-->
</script>

</head>
<html:html>
<body onLoad="MM_preloadImages('images/bt_home_ovr.jpg','images/bt_refresh_ovr.jpg','images/bt_ok_ovr.gif','images/bt_cancel_ovr.gif')">


<table width="765" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>
	<table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="250"><img src="images/sfu_logo.jpg" width="250" height="50"></td>
        <td valign="bottom" background="images/tile_topbar.jpg">
        <div align="right">
        <!--<a href="login.jsp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Home','','images/bt_home_ovr.jpg',1)"><img src="images/bt_home.jpg" alt="Home" name="Home" width="52" height="40" border="0"></a>
        <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Refresh','','images/bt_refresh_ovr.jpg',1)"><img src="images/bt_refresh.jpg" alt="Refresh" name="Refresh" width="52" height="40" border="0"></a>
        -->
        &nbsp;
        </div>
        </td>
        <td width="13"><img src="images/topbar_right.jpg" width="13" height="50"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="510" valign="bottom" class="bgColor_D8EAEC bdrColor_336666" style="background-image:url(images/scnner.jpg)">
	<html:form action="loginAction.do" focus="txtLoginId"  >
	  <table width="100%"  border="0">
      <tr>
        <td width="25%">&nbsp;</td>
        <td width="51%"><div align="right"><bean:message key="lbl.UserId" />:</div></td>
        <td width="20%">
          <html:text property="txtLoginId" styleClass="bdrColor_336666" style="width:140px" value="amishra" />
        </td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><div align="right"><bean:message key="lbl.Password" />:</div></td>
        <td>
          <html:password property="txtLoginPassword" styleClass="bdrColor_336666" style="width:140px" value="aaaaa" />
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          &nbsp;
          <%-- 
          <html:button property="btnOk" styleClass="buttons"  style="width:60px; margin-left:"  onclick="callSubmit(this.form)"><bean:message key="btn.Ok" /></html:button> 
          <html:cancel styleClass="buttons" style="width:60px;" ><bean:message key="btn.Cancel" /></html:cancel>
          --%>
        </td>
        <td>
          <a  onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('OK','','images/bt_ok_ovr.gif',1)" onclick="callSubmit();"><img src="images/bt_ok.gif" name="OK" width="69" height="20" border="0" onclick="return callSubmit(this.form);" /></a> <a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Cancel','','images/bt_cancel_ovr.gif',1)"><img src="images/bt_cancel.gif" name="Cancel" width="69" height="20" border="0" onclick="history.go;" ></a>
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="4">&nbsp;</td>
        </tr>
    </table>
    
	</html:form>
  </td>
  
  </tr>
  <tr><td height="2px"></td></tr>
  <tr>
    <td height="23px" class="bgColor_D8EAEC bdrColor_336666 ">
      <!-- Status Bar Table Starts-->
      <table id="tblStatusBar" align="center" width="100%" height="23px" border="0" cellpadding="0" cellspacing="1" bgcolor="#80A0B2">
        <tr class="bgColor_D8EAEC bdrColor_336666 ">
          <td width="30px" >
            <div class="imgStatusMsg"/>
          </td>
          <td width="735px">
          <logic:present parameter="sessionExpired" >
            <div style="color:#ff0000"><b><bean:message key="info.SessionExpired" /></b></div>
          </logic:present>
          <logic:messagesPresent message="true">
            <div style="color:#ff0000">
              <html:messages id="msg1" message="true">
                <b><bean:write name="msg1" /></b>
              </html:messages>
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
</html:html>
</html>
