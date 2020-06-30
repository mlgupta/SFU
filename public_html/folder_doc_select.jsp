<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%
String currentFolderDocId4Select  = null;
boolean isDocument=false;
String heading="";

if (session.getAttribute("currentFolderDocId4Select")!=null){
  currentFolderDocId4Select =((Long) session.getAttribute("currentFolderDocId4Select")).toString();
} 
if (request.getAttribute("isDocument")!=null){
  isDocument =((Boolean)request.getAttribute("isDocument")).booleanValue();
}
if (session.getAttribute("heading")!=null){
  heading =(String)session.getAttribute("heading");
}

%>
<html:html>
<head>

<title><bean:message key="title.FoldersDocuments" /></title>
<link href="main.css" rel="stylesheet" type="text/css">
<!-- Begin (Tree View Related js Files) -->
<script src="useragent.js" type="text/javascript"></script>
<script src="treeview_select.js" type="text/javascript"></script>
<script src="general.js" type="text/javascript"></script>
<script src="temp/ReplicateTree.js" type="text/javascript"></script>



<!-- End (Tree View Related js Files)  -->
<script type="text/javascript">
function window_onload(){
 document.forms[0].btnSelect.disabled=!((eval(document.forms[0].hdnFoldersOnly.value))  && !eval("<%=isDocument%>"))
 
 var controlName=document.forms[0].hdnOpenerControl.value;
 control=eval("opener.document.forms[0]." +controlName);
 document.forms[0].folderDocument.value =control.value;
 document.forms[0].hdnFolderDocument.value=control.value;
}
function goTo(){
    document.forms[0].action="folderDocSelectGoAction.do";
    document.forms[0].submit();
}

function selectOnclick(){
  var controlName=document.forms[0].hdnOpenerControl.value;
  var control=null;
  if(controlName!=""){
    control=eval("opener.document.forms[0]." +controlName);
    if (typeof control!="undefined"){
      if((control.type=="select-multiple")||(control.type=="select")){
        for(var openerIndex=0;openerIndex<control.options.length; openerIndex++){
          if(control.options[openerIndex].value==document.forms[0].hdnFolderDocument.value){                  
            window.close();
            return false;
          }       
        }
        var opt=opener.document.createElement("OPTION");
        opt.text=document.forms[0].hdnFolderDocument.value;
        opt.value=document.forms[0].hdnFolderDocument.value;
        var newIndex=control.options.length ;
        control.options[newIndex]=opt;
      }else{
        control.value=document.forms[0].hdnFolderDocument.value;
      }
      window.close();
      return true;
    }
  }
}
</script>
</head>
<body style="margin:15px" onLoad="window_onload();">
<html:form action="/folderDocSelectB4Action" >

<html:hidden name="folderDocSelectForm" property="hdnFoldersOnly"/>
<html:hidden name="folderDocSelectForm" property="hdnOpenerControl"/>
<html:hidden name="folderDocSelectForm" property="hdnFolderName"/>
<html:hidden name="folderDocSelectForm" property="hdnFolderDocument"/>
<html:hidden name="folderDocSelectForm" property="hdnRootFolder"/>

<!-- This page contains 2 outermost tables, named 'errorContainer' and 'tabContainer' -->
<table id="tabContainer" width="100%"  border="0" cellspacing="0" cellpadding="0">
<!-- This table contains 1 tr with 1 td containing tables, 'tabParent' and 'blueBorder' -->
<tr>
  <td align="center">
    <table id="tabParent" width="350px"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>
          <table id="tab" width="140px" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="5px" class="imgTabLeft"></td>
              <td width="130px" class="imgTabTile"><div align="center" class="tabText"><bean:message key="lbl.FoldersDocuments" /></div></td>
              <td width="5px" class="imgTabRight"></td>            
            </tr>
          </table>
        </td>
  		</tr>
    </table>
    <table id="borderClrLvl_2" width="350px" border="0" cellpadding="0" cellspacing="0" class="imgData bgColor_D8EAEC bdrColor_336666"  >
        <tr>
          <td >
          <table width="90%"  border="0" cellpadding="0" cellspacing="0" align="center">
            <tr>
              <td height="10px"></td>
            </tr>
            <tr>
              <td class="tabText"><%=heading%></td>
            </tr>
            <tr>
              <td height="10px"></td>
            </tr>
            <tr>
              <td>
                <span style="float:left">
                  <bean:message key="lbl.Path" />:
                  <html:text property="folderDocument"  name="folderDocSelectForm" styleClass="bdrColor_336666" style="width:286px;"  />
                </span>
                <!--<a onclick="goTo();" class="imgGo" title="<bean:message key="btn.Go" />" ></a>-->
              </td>
            </tr>
            <tr>
              <td height="10px"></td>
            </tr>

            <tr>
              <td >
                <div id="treeview" align="center" class="bdrColor_336666" style="overflow:auto;height:325px;width:320px;">
                  <!-- Begin Tree View Generation-->    
                  <div style="width:95%;" >
                  <script type="text/javascript">                      
                      openerControl=document.forms[0].hdnOpenerControl.value;
                      foldersOnly=document.forms[0].hdnFoldersOnly.value ;
                      SetCookie("highlightedTreeviewLink","<%=currentFolderDocId4Select %>");
                      initializeDocument();
                      <%if(currentFolderDocId4Select !=null){ %>
                        getElById("treeview").scrollTop=findObj("<%=currentFolderDocId4Select %>").navObj.offsetTop;
                      <%}%> 
                      
                  </script>
                  </div>
                  <!-- End Tree View Generation-->
                </div>
              </td>
            </tr>
            <tr>
              <td height="10px"></td>
            </tr>
            <tr>
              <td align="right">
                <html:button property="btnSelect"  onclick="selectOnclick();" styleClass="buttons" style="width:70px" ><bean:message key="btn.Select" /></html:button>
                <html:button property="btnCancel" onclick="window.close();" styleClass="buttons" style="width:70px" ><bean:message key="btn.Cancel" /></html:button>
                <html:button property="btnHelp" styleClass="buttons" style="width:70px" onclick="openWindow('help?topic=folder_doc_copy_move_html','Help',650,800,0,0,true);" tabindex="5"><bean:message key="btn.Help" /></html:button>
              </td>
            </tr>
            <tr>
              <td height="10px"></td>
            </tr>
          </table>
        </td>
    </tr>
  </table>
  <!-- borderClrLvl_2 table ends above-->
</td>
</tr>
</table>
<!-- tabContainer table ends above-->
<table cellpadding="0" cellspacing="0" border="0">
<tr>
<td height="3px">
</td>
</tr>
</table>
<table align="center"  class="bdrColor_336666  bgColor_D8EAEC" width="350px" border="0" cellpadding="0" cellspacing="0" id="statusBar">
    <tr>
    <td><div class="imgStatusMsg"></div></td>
    <td>
    <logic:messagesPresent>
      <html:messages id="actionMessage" message="true">
          <bean:write  name="actionMessage"/>
      </html:messages>
          
      <html:messages id="actionError">
          <font color="red"> <bean:write  name="actionError"/></font>
      </html:messages>
    </logic:messagesPresent>
    </td>
    </tr>
</table>
<!-- statusBar table ends above-->
</html:form>
</body>
</html:html>