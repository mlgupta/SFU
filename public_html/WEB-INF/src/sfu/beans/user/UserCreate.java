package sfu.beans.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class UserCreate {
  Logger logger = Logger.getLogger("SfuLogger");


  public void createUser(Connection conn, String userId, String userName,
                         String emailId, String password, String isLocked,
                         String isAdmin) throws Exception {
    String framework = "embedded";
    System.out.println("SimpleApp starting in " + framework + " mode.");
    Statement stmt = null;

    String query = null;

    try {
      logger.info("Entering createUser() method");

      stmt = conn.createStatement();
      query =
        "insert into app.users (user_id,user_name,password,user_is_locked,is_admin,email_id) values('" + userId + "','" + userName + "','" +
        password + "','" + isLocked + "','" + isAdmin + "','" + emailId + "')";
      logger.debug("User insert Query: " + query);
      System.out.println("User insert Query: " + query);
      /*
           We create a table, add a few rows, and update one.
         */
      stmt.execute(query);

    } catch (SQLException e) {
      logger.error("Error Code:" + e.getErrorCode());
      logger.error("exception message:" + e.getMessage());
      logger.error("sql state : " + e.getSQLState());
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      logger.error("exception thrown:" + e.getMessage());
      e.printStackTrace();
      throw e;
    } finally {
      logger.info("Exiting createUser() method");
    }

  }


}

