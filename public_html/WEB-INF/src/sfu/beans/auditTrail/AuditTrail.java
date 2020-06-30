package sfu.beans.auditTrail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import sfu.actionforms.auditTrail.AuditTrailListForm;

import sfu.beans.misc.General;
import sfu.beans.scheduler.DateHelper;

public class AuditTrail {
    private static String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    //"MM/dd/yyyy HH:mm:ss z"; //also present in JobListBean

    static Logger logger = Logger.getLogger("SfuLogger");

    public static void doAuditTrail(Connection conn,
                                    AuditTrailBean auditTrailBean) throws SQLException,
                                                                                           Exception {
        String query = null;
        Statement stmt = null;
        Timestamp ts = null;

        try {
            logger.info("---Entering doAuditTrail() method---");

            query = "insert into audit_trail ";
            query += "(USER_ID, ";
            query += "ACTION_TYPE, ";
            query += "PERFORMED_DATE, ";
            query += "SCHEDULED_DATE, ";
            query += "PAGES_SCANNED, ";
            query += "NAME_OF_FILE, ";
            query += "TO_ADDRESS) ";
            query += "values ";
            query += "( ";
            query += "'" + auditTrailBean.getUserId() + "', ";
            query += "'" + auditTrailBean.getActionType() + "', ";
            query +=
                "'" + ts.valueOf(auditTrailBean.getPerformedDate()) + "', ";
            query +=
                "'" + ts.valueOf(auditTrailBean.getScheduledDate()) + "', ";
            query += "" + auditTrailBean.getPagesScanned() + ", ";
            query += "'" + auditTrailBean.getFileName() + "', ";
            query += "'" + auditTrailBean.getToAddress() + "' ";
            query += ")";

            logger.debug("query: " + query);
            System.out.println("query: " + query);
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            logger.error("Error Code:" + e.getErrorCode());
            logger.error("exception message:" + e.getMessage());
            logger.error("sql state : " + e.getSQLState());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            logger.info("---Exiting doAuditTrail() method---");
        }
    }

    public static ArrayList getAuditTrails(AuditTrailListForm auditTrailListForm,
                                           Connection conn,
                                           int numberOfRecords) {
        ArrayList auditTrails = null;
        AuditTrailBean auditTrail = null;
        Statement stmt = null;
        String query = null;
        ResultSet rs = null;
        String searchText = null;

        Date dPerformedFromDate = null;
        Date dPerformedToDate = null;
        Date dScheduledFromDate = null;
        Date dScheduledToDate = null;

        Timestamp tsPerformedFromDate = null;
        Timestamp tsPerformedToDate = null;
        Timestamp tsScheduledFromDate = null;
        Timestamp tsScheduledToDate = null;

        int pageNumber = 1;
        int pageCount = 1;
        int startIndex = 1;
        int endIndex = 1;

        try {
            logger.info("Entering getAuditTrails() method");

            SimpleDateFormat formatter =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            logger
            .debug("performedFromDate: " + auditTrailListForm.getTxtPerformedFromDate());

            if ((auditTrailListForm.getHdnSearchPageNo() != null) &&
                (auditTrailListForm.getHdnSearchPageNo().trim().length() >
                                                                      0)) {
                pageNumber =
                    Integer.parseInt(auditTrailListForm.getHdnSearchPageNo());
            }
            auditTrails = new ArrayList();
            stmt =
                conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet
                                        .CONCUR_READ_ONLY);
            //ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY
            query = "select * from audit_trail where 1=1";
            searchText = auditTrailListForm.getTxtSearchByUserName();
            if (searchText != null && searchText.trim().length() != 0) {
                logger.debug("searchText: " + searchText);
                logger
                .debug("replaced searchText: " + searchText.replace('*', '%'));
                logger
                .debug("* position in search text: " + searchText.indexOf("*"));
                if (searchText.indexOf("*") > -1) {
                    searchText = searchText.replace('*', '%');
                    query += "and user_id like '" + searchText + "' ";
                } else {
                    query += "and user_id like '" + searchText + "%' ";
                }
            }
            if (auditTrailListForm.getActionType() != null &&
                auditTrailListForm.getActionType().trim().length() != 0
                && !auditTrailListForm.getActionType().equalsIgnoreCase("all")) {
                query +=
                    "and action_type ='" + auditTrailListForm.getActionType() +
                    "' ";
            }
            if (auditTrailListForm.getTxtPerformedFromDate() != null &&
                auditTrailListForm.getTxtPerformedFromDate().trim().length() !=
                0) {
                dPerformedFromDate =
                    DateHelper.parse(auditTrailListForm.getTxtPerformedFromDate(),
                                                      DATE_FORMAT);
                tsPerformedFromDate =
                    tsPerformedFromDate.valueOf(formatter.format(dPerformedFromDate));
                logger.debug("tsPerformedFromDate: " + tsPerformedFromDate);
                query += "and performed_date >='" + tsPerformedFromDate + "' ";
            }
            if (auditTrailListForm.getTxtPerformedToDate() != null &&
                auditTrailListForm.getTxtPerformedToDate().trim().length() !=
                0) {
                dPerformedToDate =
                    DateHelper.parse(auditTrailListForm.getTxtPerformedToDate(),
                                                    DATE_FORMAT);
                tsPerformedToDate =
                    tsPerformedToDate.valueOf(formatter.format(dPerformedToDate));
                logger.debug("tsPerformedToDate: " + tsPerformedToDate);
                query += "and performed_date <='" + tsPerformedToDate + "' ";
            }
            if (auditTrailListForm.getTxtScheduledFromDate() != null &&
                auditTrailListForm.getTxtScheduledFromDate().trim().length() !=
                0) {
                dScheduledFromDate =
                    DateHelper.parse(auditTrailListForm.getTxtScheduledFromDate(),
                                                      DATE_FORMAT);
                tsScheduledFromDate =
                    tsScheduledFromDate.valueOf(formatter.format(dScheduledFromDate));
                logger.debug("tsScheduledFromDate: " + tsScheduledFromDate);
                query += "and scheduled_date >='" + tsScheduledFromDate + "' ";
            }
            if (auditTrailListForm.getTxtScheduledToDate() != null &&
                auditTrailListForm.getTxtScheduledToDate().trim().length() !=
                0) {
                dScheduledToDate =
                    DateHelper.parse(auditTrailListForm.getTxtScheduledToDate(),
                                                    DATE_FORMAT);
                tsScheduledToDate =
                    tsScheduledToDate.valueOf(formatter.format(dScheduledToDate));
                logger.debug("tsScheduledToDate: " + tsScheduledToDate);
                query += "and scheduled_date <='" + tsScheduledToDate + "' ";
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
                    auditTrail = new AuditTrailBean();
                    auditTrail.setUserId(rs.getString("user_id"));
                    auditTrail.setActionType(rs.getString("action_type"));
                    auditTrail
                    .setPerformedDate(rs.getString("performed_date"));
                    auditTrail
                    .setScheduledDate(rs.getString("scheduled_date"));
                    auditTrail.setPagesScanned(rs.getString("pages_scanned"));
                    auditTrail.setFileName(rs.getString("name_of_file"));
                    auditTrail.setToAddress(rs.getString("to_address"));

                    auditTrails.add(auditTrail);
                    startIndex++;
                    if (startIndex > endIndex) {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            auditTrailListForm
            .setHdnSearchPageNo(Integer.toString(pageNumber));
            auditTrailListForm
            .setHdnSearchPageCount(Integer.toString(pageCount));
            logger
            .debug("***page number in getAuditTrails()*** " + auditTrailListForm
                         .getHdnSearchPageNo());

            logger.info("Exiting getAuditTrails() method");
        }


        return auditTrails;
    }

    public static void deleteAuditTrail(String performedDateString,
                                        Connection conn) throws Exception {
        String query = null;
        Statement stmt = null;

        try {
            logger.info("---Entering deleteAuditTrail() method---");

            query =
                "delete from audit_trail where performed_date in (" + performedDateString +
                ")";
            logger.debug("delete query: " + query);
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            logger.info("---Exiting deleteAuditTrail() method---");
        }

    }
}
