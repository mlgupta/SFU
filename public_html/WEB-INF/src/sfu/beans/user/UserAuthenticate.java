package sfu.beans.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import sfu.beans.misc.Constants;

public class UserAuthenticate {
    static Logger logger = Logger.getLogger("SfuLogger");

    public static String authenticateUser(String userId, String userPassword,
                                          Connection conn,
                                          UserProfile userProfile) throws SQLException,
                                                                                           Exception {
        ResultSet rs = null;
        String query = null;
        String password = null;
        String isLocked = null;
        Statement stmt = null;
        String isAdmin = null;
        String userType = null;
        try {
            logger.info("---Entering authenticateUser() method---");
            query = "select * from users where user_id='" + userId + "'";
            logger.debug("query: " + query);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                password = rs.getString("password");
                isLocked = rs.getString("user_is_locked");
                isAdmin = rs.getString("is_admin");
                logger.debug("password: " + password);
                logger.debug("isLocked: " + isLocked);
                logger.debug("isAdmin: " + isAdmin);
                if (password.equals(userPassword) && isLocked.equals("f")) {
                    userProfile.setUserId(rs.getString("user_id"));
                    userProfile.setUserName(rs.getString("user_name"));
                    userProfile.setNumberOfRecords("5");
                    userProfile.setEmailId(rs.getString("email_id"));
                    userProfile.setUserDirectory(rs.getString("def_user_dir"));
                    logger
                    .debug("Number Of Rec Per Page: " + userProfile.getNumberOfRecords());
                    if (isAdmin.equals("t")) {
                        userProfile.setIsAdmin("true");
                        userType = Constants.ADMIN.toString();
                    } else {
                        userProfile.setIsAdmin("false");
                        userType = Constants.GENERAL.toString();
                    }
                } else if (password.equals(userPassword) &&
                           isLocked.equals("t")) {
                    userType = Constants.LOCKED.toString();
                }
            }
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
            logger.info("---Exiting authenticateUser() method---");
        }
        return userType;
    }
}
