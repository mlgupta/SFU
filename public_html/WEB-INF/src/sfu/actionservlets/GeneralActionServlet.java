package sfu.actionservlets;

import java.sql.Connection;

//import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.struts.action.ActionServlet;

import sfu.beans.database.DBConnection;
import sfu.beans.user.UserProfile;


/*import statement*/


/*Class declaration and definition*/

public class GeneralActionServlet extends ActionServlet {
  //private static ArrayList popupActionList = new ArrayList();

  Connection conn = null;

  private ServletContext context = null;

  public void init(ServletConfig config) throws ServletException {
    String xmlFilePrefix = null;
    String fs = null;
    String os = null;
    try {
      super.init(config);

      DBConnection dbConnection = new DBConnection();
      conn = dbConnection.getSfuDBConnection();


      System.out.println("Entering actionservlet");

      fs = System.getProperty("file.separator");
      os = System.getProperty("os.name");
      System.out.println("***os: " + os);
      log("Initializing Logger...");
      context = config.getServletContext();
      String prefix = context.getRealPath("/");

      if (os.equals("Linux")) {
        xmlFilePrefix =
          prefix + "WEB-INF" + fs + "xml" + fs + "config" + fs + "XMLConfigFiles" +
          fs + "linux" + fs;
        System.out.println("**xmlFilePrefix: " + xmlFilePrefix);
      }
      String logFile = getInitParameter("log4j-init-file");
      context.setAttribute("contextPath", prefix);
      context.setAttribute("xmlFilePrefix", xmlFilePrefix);
      context.setAttribute("conn", conn);
      if (logFile != null) {
        PropertyConfigurator.configure(prefix + logFile);
      } else {
        log("Unable to find log4j-initialization-file : " + logFile);
      }
      log("Logger initialized successfully");


    } catch (Exception e) {
      e.printStackTrace();
      log(" Unable to initialize logger : " + e.toString());
    }
  }

  //All the request will pass through this method

  public void process(HttpServletRequest request,
                      HttpServletResponse response) {
    Logger logger = Logger.getLogger("SfuLogger");
//    HttpSession httpSession = request.getSession(true);
//    UserProfile userProfile =
//      (UserProfile)httpSession.getAttribute("userProfile");
//
    try {
//      if (userProfile == null &&
//          !(request.getRequestURI().endsWith("loginAction.do")) &&
//          !(request.getRequestURI().endsWith("loginB4Action.do"))) {
//
//        logger.debug("Serving : login.jsp");
//        response.sendRedirect("login.jsp?sessionExpired=true");
//
//      } else {
        logger
        .info("Normal Execution of Process Method of GeneralActionServlet Before super.process");
        logger
        .info(" Request URI before super.process" + request.getRequestURI());
          super.process(request, response);
        logger
        .info(" Request URI after super.process" + request.getRequestURI());
        logger
        .info("Normal Execution of Process Method of GeneralActionServlet After super.process");
//      }
//
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.error(ex);
    }

  }
}
