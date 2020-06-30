package sfu.beans.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import sfu.actionforms.user.UserForm;

public class UserModify {
    static Logger logger = Logger.getLogger("SfuLogger");

    public static UserForm viewUser(String userId,
                                    Connection conn) throws Exception {
        ResultSet rs = null;
        String sqlString = null;
        UserForm userForm = null;
        Statement stmt = null;
        try {
            logger.info("Entering viewOutlet");
            sqlString =
                "select user_id,user_name,password,user_is_locked,is_admin,email_id from users ";
            sqlString += "where user_id='" + userId + "'";
            logger.debug("query is: " + sqlString);
            stmt = conn.createStatement();

            logger.debug("String value of user id is:" + userId);
            rs = stmt.executeQuery(sqlString);
            if (rs.next()) {
                userForm = new UserForm();
                userForm.setTxtUserId(rs.getString("user_id"));
                userForm.setTxtEmailId(rs.getString("email_id"));
                userForm.setTxtUserName(rs.getString("user_name"));
                userForm.setTxtPassword(rs.getString("password"));
                userForm.setTxtConfirmPassword(rs.getString("password"));
                userForm
                .setChkIsLocked(rs.getString("user_is_locked").equals("t") ?
                                        "true" : "false");
                userForm
                .setChkIsAdmin(rs.getString("is_admin").equals("t") ? "true" :
                                       "false");
            } else {
                throw new Exception("user_id not found");
            }
        } catch (SQLException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            logger.info("Exiting viewUser");
        }
        return userForm;
    }

    public static void modifyUser(Connection conn,
                                  UserForm userForm) throws Exception {

        Statement stmt = null;
        String sqlString = null;

        try {
            logger.info("Entering modifyUser()");

            sqlString = "update App.users set ";
            sqlString +=
                "user_name='" + userForm.getTxtUserName().trim() + "' ";
          sqlString +=
              ",email_id='" + userForm.getTxtEmailId().trim() + "' ";
            sqlString +=
                ",password='" + userForm.getTxtPassword().trim() + "' ";
            sqlString +=
                ",user_is_locked='" + (userForm.getChkIsLocked().equals("on") ?
                                                "t" : "f") + "' ";
            sqlString +=
                ",is_admin='" + (userForm.getChkIsAdmin().equals("on") ? "t" :
                                          "f") + "' ";
            sqlString +=
                "where user_id='" + userForm.getTxtUserId().trim() + "' ";
            logger
            .debug("userForm.getChkIsLocked(): " + userForm.getChkIsLocked());
            logger
            .debug("userForm.getChkIsAdmin(): " + userForm.getChkIsAdmin());
            logger.debug("sqlString: " + sqlString);

            stmt = conn.createStatement();
            stmt.executeUpdate(sqlString);

        } catch (SQLException e) {
            logger.error("Error Code:" + e.getErrorCode());
            logger.error("exception message:" + e.getMessage());
            logger.error("sql state : " + e.getSQLState());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            logger.error("Exception: " + e);
            throw e;
        } finally {
            logger.info("Exiting modifyUser()");
        }

    }
    /*
  public static String encrPass=null;
  ParseXMLTag userXML=new ParseXMLTag("/home/ias/jdevhome/mywork/SFUTestApp/SfuGUI/classes/sfu/xml/config/XMLConfigFiles/UserCommands.xml");
  public UserModify()
  {
     
  }
  

  public void convertPassword(String password){
            try{
               // String cmd="openssl passwd "+ password;
               
                String cmd=userXML.getValue("CryptCommand","Commands")+" "+userXML.getValue("CryptParam","Commands")+" "+password;
                Runtime rt = Runtime.getRuntime();
                System.out.println("Executing "+cmd );
                //Process proc = rt.exec(cmd);
                Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
                int exitVal = proc.waitFor();
                // any error message?
                StreamHandler errorHandler = new
                StreamHandler(proc.getErrorStream(), "ERROR",true);            
            
                // any output?
                StreamHandler outputHandler = new
                StreamHandler(proc.getInputStream(), "OUTPUT",true);
                
                // start them 
                errorHandler.start();
                outputHandler.start();
                                    
                // any error???
                
                
                if(exitVal==0){
                    System.out.println("Convert Password Successfull");
                }else{
                    System.out.println("Error Converting User");
                    
                }  
            }catch(Exception e) {
              System.out.println(e);
            }
            
    
  }
  
  public void modifyUser(String loginName,String isLocked,String fullName,String expirationEnabled,String expirationDate,String loginShell,String homeFolder){
            try{
            
               String lockParam=" ";
               String fullNameParam=" ";
               String expirationParam=" ";
               String loginParam=" ";
               String homeParam=" ";
               if(isLocked!=null && !(isLocked.equals(""))){
                 lockParam=" -L ";
               }else{
                 lockParam=" -U ";
               }
               if(fullName!=null && !(fullName.equals(""))){
                 fullNameParam=" -c '"+fullName+"' ";                 
               }
               if(expirationEnabled!=null && !(expirationEnabled.equals(""))){
                 expirationParam=" -e "+expirationDate+" ";                 
               }else{
                 expirationParam=" -e '' ";
               }
               if(loginShell!=null && !(loginShell.equals(""))){
                 loginParam=" -s "+loginShell+" ";                 
               }
               if(homeFolder!=null && !(homeFolder.equals(""))){
                 homeParam=" -d "+homeFolder+" ";                 
               }
               
               
                String cmd="usermod "+expirationParam+fullNameParam+loginParam+homeParam+lockParam+loginName;
                
                Runtime rt = Runtime.getRuntime();
                System.out.println("Executing "+cmd );
                //Process proc = rt.exec(cmd);
                Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
                int exitVal = proc.waitFor();
                // any error message?
                StreamHandler errorHandler = new
                StreamHandler(proc.getErrorStream(), "ERROR",false);            
            
                // any output?
                StreamHandler outputHandler = new
                StreamHandler(proc.getInputStream(), "OUTPUT",false);
                
                // start them 
                errorHandler.start();
                outputHandler.start();
                                    
                // any error???
                
                
                if(exitVal==0){
                    System.out.println("Password Creation Successfull");
                }else{
                    System.out.println("Error Creating Password");
                    
                }  
            }catch(Exception e) {
              System.out.println(e);
            }
            
    
  }
  public static void main(String args[]){
    UserModify um=  new UserModify();
    // um.convertPassword("aaaaa");
   
     
    System.out.println("---------------------------------------------");
    um.modifyUser("aa","v","as","d","2004-12-12","/bin/sh","/home/as");
    
  }*/
}

