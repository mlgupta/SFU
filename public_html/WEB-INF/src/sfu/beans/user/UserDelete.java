package sfu.beans.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class UserDelete {
    static Logger logger = Logger.getLogger("SfuLogger");

    public static void deleteUser(String userId,
                                  Connection conn) throws Exception {
        String query = null;
        Statement stmt = null;

        try {
            logger.info("---Entering deleteUser() method---");

            query = "delete from users where user_id='" + userId + "'";
            logger.debug("delete query: " + query);
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            logger.info("---Exiting deleteUser() method---");
        }

    }
    /*
  ParseXMLTag userXML=new ParseXMLTag("/home/ias/jdevhome/mywork/SFUTestApp/SfuGUI/classes/sfu/xml/config/XMLConfigFiles/UserCommands.xml");
  public UserDelete()
  {
     
  }
  
  
 
  
  
  public static void main(String args[]){
    UserDelete dc=  new UserDelete();
    
     dc.deleteUser("user2");
   
    
  }
  */
}

