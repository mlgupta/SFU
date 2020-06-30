package sfu.beans.misc;

/**
 *	Purpose: To calculate the number of pages that will be formed depending upon the number
 *           of records per page.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
import sfu.beans.misc.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 *	Purpose: To calculate the number of pages that will be formed depending upon the number
 *           of records per page.
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public final class

General {

    private static Logger logger = Logger.getLogger("SfuLogger");

    /**
 *	Purpose: Returns the value of pageCount of type integer.
 *  @param rs - A ResultSet Object.
 *  @param numberOfRecords - An Integer Object.
 *  @return integer
 */
    public static int getPageCount(ResultSet rs,
                                   int numberOfRecords) throws SQLException {

        int recordCount;
        int pageCount = 1;
        try {
            rs.last();
            recordCount = rs.getRow();
            if (recordCount != 0) {
                pageCount =
                    ((recordCount % numberOfRecords) == 0) ? (recordCount /
                                                                      numberOfRecords) :
                            ((recordCount / numberOfRecords) + 1);
            }
            rs.beforeFirst();
        } catch (SQLException se) {
            logger
            .error("***Exception in getPageCount() method" + se.getMessage());
            throw se;
        }
        return pageCount;
    }
    
  public static ArrayList reverseOrder(ArrayList a){
    ArrayList retArrayList=null;
    
    try {
      retArrayList=new ArrayList();
      if(a.size()>0){
        for(int i=a.size()-1;i>=0;i--){
          retArrayList.add(a.get(i));
        }
      }
    }
    catch (Exception e) {
      logger.error(e.toString());
    }
    return retArrayList;
  }
}

