/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: ContextListener.java,v 1.5 2006/08/29 08:35:43 amishra Exp $
 *****************************************************************************
 */
package sfu.listeners;

/* dms package references */

/* Java API */
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/* Struts API */

import org.apache.log4j.*;
import org.apache.struts.action.*;

import java.sql.*;


/**
 *	Purpose: This class is used to  dispose CMSDK Library service in correct manner.
 *           This is a listener class whose contextInitialized() and contextDestroyed() methods are 
 *           called corrospondingly whenever the servlet context is loaded into the memory and destroyed.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class ContextListener implements ServletContextListener {
    ServletContext servletContext;

    Logger logger = Logger.getLogger("SfuLogger");

    /**
     * Purpose : To provide definition for contextInitialized(ServletContextEvent) function in ServletContextListener
     *           interface.This function is called whenever the servlet context is loaded.
     * @param  : sce - ServletContextEvent 
     * 
     */
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing Servlet Context");
        servletContext = sce.getServletContext();
        logger.info("Initializing Servlet Context Complete");
    }

    /**
     * Purpose : To provide definition for contextDestroyed(ServletContextEvent) function in ServletContextListener
     *           interface.This function is called whenever the servlet context is destroyed.
     *           Here the CMSDK Library Service is disposed off as soon as servlet context is destroyed.
     * @param  : sce - ServletContextEvent 
     * 
     */
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        Connection conn = (Connection)servletContext.getAttribute("conn");
        try {
            conn.close();
            /*
           In embedded mode, an application should shut down Derby.
           If the application fails to shut down Derby explicitly,
           the Derby does not perform a checkpoint when the JVM shuts down, which means
           that the next connection will be slower.
           Explicitly shutting down Derby with the URL is preferred.
           This style of shutdown will always throw an "exception".
         */
            boolean gotSQLExc = false;

            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                gotSQLExc = true; 
            }
            if (!gotSQLExc) {
                logger.info("Database did not shut down normally");
            } else {
                logger.info("Database shut down normally");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("Destroying Servlet Context...");
        logger.info("Trying to stop Document Log Agent");

        logger.info("Destroying Servlet Context complete");
    }


}
