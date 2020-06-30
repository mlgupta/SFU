package sfu.actions.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStreamReader;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class ListProcessStatusAction extends Action {
  Logger logger = Logger.getLogger("SfuLogger");

  FileInputStream fis;
  InputStreamReader isr;
  BufferedReader br;

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
                                                                                                ServletException {
    String line = null;
    String lastLine = null;
    ActionMessages messages = new ActionMessages();
    HttpSession httpSession=request.getSession(false);
    try {
      logger.info("------Entering ListProcessStatusAction------");
      
      String tempFileParentPathString =
        (String)servlet.getServletContext().getAttribute("contextPath");
      logger
      .debug("tempFilePath: " + tempFileParentPathString + "temp_msg" + File
                   .separator + "msg.tmp");
      File tempMsgFile =
        new File(tempFileParentPathString + "temp_msg" + File.separator +
                                  "msg.tmp");
//      long tempMsgFileLastModified=tempMsgFile.lastModified();
//      logger.debug("tempMsgFileLastModified :"+tempMsgFileLastModified);
//      logger.debug("tempMsgFileLastModified in session:"+httpSession.getAttribute("tempMsgFileLastModified"));
//      
            
//      if(httpSession.getAttribute("tempMsgFileLastModified")!=null){
//        if(Long.valueOf(tempMsgFileLastModified) == (Long)httpSession.getAttribute("tempMsgFileLastModified")){
//          this.wait();
//        }  
//      }
     
//      httpSession.setAttribute("tempMsgFileLastModified",tempMsgFileLastModified);
      
      if(tempMsgFile.exists()){
        fis = new FileInputStream(tempMsgFile);
        isr = new InputStreamReader(fis);
        br = new BufferedReader(isr);
        while ((line = br.readLine()) != null) {
          logger.debug("Reading Error Line: "+line);
          lastLine=line;
        }
        //stopExec(1000);
        ActionMessage msg=new ActionMessage("msg.ScanningStatus", lastLine);
        messages.clear();
        messages.add("procMessage",msg);
        saveMessages(request,messages);
        //tempMsgFile.delete();
      }
      
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      ActionMessage msg = new ActionMessage("msg.JavaException",e.toString());
      messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
      return mapping.getInputForward();
      } finally {
      if(!messages.isEmpty()){
        saveMessages(request,messages);  
      }
      br.close();
      isr.close();
      fis.close();
      logger.info("------Exiting ListProcessStatusAction------");
    }
    return mapping.findForward("success");
  }
  /*
  public void stopExec(long miliSec){
   Date currTime=new Date();
    long startTime=currTime.getTime();
    System.out.println(startTime);
    while(currTime.getTime()<startTime+miliSec){
      currTime=new Date();
    }
    System.out.println(currTime.getTime());
  }
  */
  
}
