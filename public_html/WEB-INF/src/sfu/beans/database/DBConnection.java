package sfu.beans.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

import org.apache.log4j.Logger;


public class DBConnection {
  Logger logger = Logger.getLogger("SfuLogger");

  public Connection getSfuDBConnection() throws SQLException, Exception {
    String framework = "embedded";
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String protocol = "jdbc:derby:";
    System.out.println("SFU starting in " + framework + " mode.");
    Connection conn = null;

    try {
      logger.info("Entering getSfuDBConnection() method");
      /*
           The driver is installed by loading its class.
           In an embedded environment, this will start up Derby, since it is not already running.
         */
      Class.forName(driver).newInstance();
      System.out.println("Loaded the appropriate driver.");

      //Connection conn = null;
      Properties props = new Properties();
      props.put("user", "");
      props.put("password", "");

      /*
           The connection specifies create=true to cause
           the database to be created. To remove the database,
           remove the directory derbyDB and its contents.
           The directory derbyDB will be created under
           the directory that the system property
           derby.system.home points to, or the current
           directory if derby.system.home is not set.
         */
      conn = DriverManager.getConnection(protocol + "sfuDB", props);

      System.out.println("Connected to database sfuDB");
    } catch (SQLException e) {
      while (e != null) {
        System.out.println("Severity(ErrorCode) :" + e.getErrorCode());
        System.out.println("Message :" + e.getMessage());
        System.out.println("SqlState :" + e.getSQLState());
        e.printStackTrace();
        e = e.getNextException();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      logger.info("Exiting getSfuDBConnection() method");
    }
    return (conn);
  }
}
