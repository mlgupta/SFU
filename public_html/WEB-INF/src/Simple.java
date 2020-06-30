import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Timer;
import javax.servlet.ServletContext;
import org.apache.struts.action.Action;
import sfu.beans.auditTrail.AuditTrail;
import sfu.beans.auditTrail.AuditTrailBean;
import sfu.beans.database.DBConnection;


public class Simple extends Action{
  public void insAudit(AuditTrailBean auditTrailBean){
    //ServletContext context=servlet.getServletContext();
    //Connection conn= (Connection)context.getAttribute("conn");
    
    try {
      DBConnection dbConnection=new DBConnection();
      Connection conn= dbConnection.getSfuDBConnection();
      AuditTrail.doAuditTrail(conn,auditTrailBean);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void stopExec(long miliSec){
   Date currTime=new Date();
    long startTime=currTime.getTime();
    System.out.println(startTime);
    while(currTime.getTime()<startTime+miliSec){
      currTime=new Date();
    }
    System.out.println(currTime.getTime());
   
   
  }
  public static void main(String args[]){
      stopExec(1000);
//    AuditTrailBean auditTrailBean=new AuditTrailBean();
//    Timestamp ts=null;
//    String tsString="2005-12-12 12:38:59";
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Date date = new Date();
    
//    Timer timer=new Timer();
//    timer.equals(1000);
//    Calendar cal=Calendar.getInstance();
//    System.out.println("date: "+formatter.format(date));
//    System.out.println("ts: "+ts.valueOf(formatter.format(date)));
//    System.out.println("cal: "+cal.getTime().getTime());
//    Timestamp tstmp=new Timestamp(cal.getTime().getTime());
//          
//    System.out.println("current timestamp: "+tstmp);
//    auditTrailBean.setUserId("cmishra");
//    auditTrailBean.setActionType("mail");
//    auditTrailBean.setPerformedDate(tsString);
//    auditTrailBean.setScheduledDate(tsString);
//    auditTrailBean.setPagesScanned("1");
//    auditTrailBean.setFileName("file01");
//    auditTrailBean.setToAddress("amishra@dbsentry.com");
//    Simple smp=new Simple();
//    smp.insAudit(auditTrailBean);
  }
}
