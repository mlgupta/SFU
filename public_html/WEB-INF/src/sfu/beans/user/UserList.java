package sfu.beans.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import sfu.actionforms.user.UserListForm;

import sfu.beans.misc.Constants;
import sfu.beans.misc.General;


public final class UserList {
  static Logger logger = Logger.getLogger("SfuLogger");

  public static ArrayList getUsers(UserListForm userListForm, Connection conn,
                                   int numberOfRecords) {
    ArrayList users = null;
    UserListBean user = null;
    Statement stmt = null;
    String query = null;
    ResultSet rs = null;
    String searchText = null;

    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;

    try {
      logger.info("Entering getUsers() method");
      if ((userListForm.getHdnSearchPageNo() != null) &&
          (userListForm.getHdnSearchPageNo().trim().length() > 0)) {
        pageNumber = Integer.parseInt(userListForm.getHdnSearchPageNo());
      }
      users = new ArrayList();
      stmt =
        conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      //ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY
      query = "select * from app.users where 1=1";
      searchText = userListForm.getTxtSearchUserName();
      if (searchText != null && searchText.trim().length() != 0) {
        logger.debug("searchText: " + searchText);
        logger.debug("replaced searchText: " + searchText.replace('*', '%'));
        logger.debug("* position in search text: " + searchText.indexOf("*"));
        if (searchText.indexOf("*") > -1) {
          searchText = searchText.replace('*', '%');
          query += "and user_id like '" + searchText + "' ";
        } else {
          query += "and user_id like '" + searchText + "%' ";
        }
      }
      query += " order by user_id";

      logger.debug("List Query: " + query);

      rs = stmt.executeQuery(query);
      if (rs != null) {
        pageCount = General.getPageCount(rs, numberOfRecords);
        if (pageNumber > pageCount) {
          pageNumber = pageCount;
        }
        startIndex = (pageNumber * numberOfRecords) - numberOfRecords;
        endIndex = (startIndex + numberOfRecords) - 1;
        if (startIndex > 0) {
          rs.relative(startIndex);
        }
        while (rs.next()) {
          user = new UserListBean();
          user.setUserId(rs.getString("user_id"));
          user.setEmailId(rs.getString("email_id"));
          user.setUserName(rs.getString("user_name"));
          user
          .setIsLocked(rs.getString("user_is_locked").equals("t") ? Constants
                           .YES.toString() : Constants.NO.toString());
          user
          .setIsAdmin(rs.getString("is_admin").equals("t") ? Constants.ADMIN
                          .toString() : Constants.NON_ADMIN.toString());
          logger.debug("***userId***: " + rs.getString("user_id"));
          logger.debug("***userId***: " + user.getUserId());
          logger.debug("***userName***: " + rs.getString("user_name"));
          logger
          .debug("***userIsLocked***: " + rs.getString("user_is_locked"));
          logger.debug("***isAdmin***: " + rs.getString("is_admin"));
          users.add(user);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
      }
    } catch (SQLException e) {
      logger.error(e.getMessage());
    } finally {
      userListForm.setHdnSearchPageNo(Integer.toString(pageNumber));
      userListForm.setHdnSearchPageCount(Integer.toString(pageCount));
      logger
      .debug("***page number in getUsers()*** " + userListForm.getHdnSearchPageNo());

      logger.info("Exiting getUsers() method");
    }


    return users;
  }
}
