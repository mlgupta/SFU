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
 * $Id: SessionListener.java,v 1.4 2006/08/29 08:35:43 amishra Exp $
 *****************************************************************************
 */
package sfu.listeners;


import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;

import sfu.beans.user.UserProfile;

/**
 *	Purpose: This class is used to  dispose off user specific resources whenever his/her 
 *           session is expired/invalidated.
 *           This is a session listener class whose sessionCreated() and sessionDestroyed() methods are 
 *           called corrospondingly whenever the user,s http session is created and destroyed.
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SessionListener implements HttpSessionListener {
    private UserProfile userProfile = null;

    private String userId = null;

    Logger logger = Logger.getLogger("SfuLogger");


    /* Methods for the HttpSessionListener */

    public void sessionCreated(HttpSessionEvent hse) {
        HttpSession httpSession = hse.getSession();
        logger.info("---Http Session created---");
    }

    /**
     * Purpose : To provide definition for sessionDestroyed(HttpSessionEvent) function in HttpSessionListener
     *           interface.This function is called whenever user session is destroyed.
     *           Here the CMSDK Library Session for the user is disconnected,resources/files related to his/her treeview are freed
     *           and all other user specific session data is also cleaned up.
     * @param  : hse - HttpSessionEvent 
     * 
     */
    public void sessionDestroyed(HttpSessionEvent hse) {
        logger.info("Session invalidation called.");
        logger.info("Starting Http Session cleanup.");
        HttpSession httpSession = hse.getSession();
        ServletContext context = httpSession.getServletContext();
        long start = httpSession.getCreationTime();
        long end = httpSession.getLastAccessedTime();
        userProfile = (UserProfile)httpSession.getAttribute("userProfile");
        if (userProfile != null) {
            userId = userProfile.getUserId();
            httpSession.removeAttribute("userProfile");

            logger
            .info("Time for which user '" + userId + "' stayed connected: " +
                        ((end - start) / 60) + " seconds");
            logger.info("Http session for user '" + userId + "' invalidated");
        } else {
            logger.info("No attributes found in Http Session.");
        }
    }
}


