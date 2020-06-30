package sfu.beans.exception;

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
 * $Id: ExceptionBean.java,v 20040220.16 2005/03/29 04:41:36 manish Exp $
 *****************************************************************************
 */


/**
 *	Purpose: To handle common Exceptions and prepare Action Errors collection to 
 *           be displayed to User.
 *  @author              
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
import org.apache.struts.action.*;
import org.apache.log4j.*;

/**
 *	Purpose: To handle common Exceptions and prepare Action Errors collection to 
 *           be displayed to User.
 *  @author              
 *  @version             1.0
 * 	Date of creation:   
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

ExceptionBean extends Throwable {
    private int errorCode;

    private String errorTrace;

    private String message;

    private String messageKey;

    private String[] replacementValues;

    /**
     * Purpose   : Constructor to create ExceptionBean Object to handle our own Exception.
     */
    public ExceptionBean() {
        Logger logger = Logger.getLogger("SfuLogger");
        errorCode = 0;
        logger.debug("errorCode : " + errorCode);
        errorTrace = "";
        logger.debug("errorTrace : " + errorTrace);
        message = "";
        logger.debug("message : " + message);
        messageKey = "errors.catchall";
        logger.debug("messageKey : " + messageKey);
        replacementValues = null;
        logger.debug("replacementValues : " + replacementValues);
    }


    /**
     * Purpose   : Constructor to create ExceptionBean Object to handle Exception.
     * @param    : ex - All type of Exception
     */
    public ExceptionBean(Exception ex) {
        Logger logger = Logger.getLogger("SfuLogger");
        message = ex.getMessage();
        logger.debug("message : " + message);
        if (message == null) {
            message = ex.toString();
            logger.debug("message : " + message);
        }
        messageKey = "errors.catchall";
        logger.debug("messageKey : " + messageKey);
        replacementValues = new String[1];
        replacementValues[0] = message;
        logger.debug("replacementValues[0] : " + replacementValues[0]);

        StackTraceElement[] st = ex.getStackTrace();
        errorTrace = message;
        for (int index = 0; index < st.length; index++) {
            errorTrace += "\n\t" + st[index].toString();
        }
        logger.debug("errorTrace : " + errorTrace);
    }

    /**
     * Purpose   : To prepare Action Errors
     * @returns  : actionErrors - ActionErrors
     */
    public ActionErrors getActionErrors() {
        ActionErrors actionErrors = new ActionErrors();
        ActionError actionError =
            new ActionError(messageKey, replacementValues);
        actionErrors.add(ActionMessages.GLOBAL_MESSAGE, actionError);
        return actionErrors;
    }

    /**
     * Purpose   : Sets the value of messageKey.
     * @param    : newMessageKey Value of messageKey from the form.
     */
    public void setMessageKey(String newMessageKey) {
        messageKey = newMessageKey;
    }

    /**
     * Purpose   : Sets the value of messageKey.
     * @param    : newMessageKey Value of messageKey.
     * @param    : newReplacementValues Value of replacementValues. 
     */
    public void setMessageKey(String newMessageKey,
                              String[] newReplacementValues) {
        messageKey = newMessageKey;
        replacementValues = newReplacementValues;
    }

    /**
     * Purpose   : Sets the value of messageKey.
     * @param    : newReplacementValues Value of replacementValues from the form.
     */
    public void setReplacementValues(String[] newReplacementValues) {
        replacementValues = newReplacementValues;
    }

    /**
     * Purpose   : Sets the value of message.
     * @param    : newMessage Value of message from the form.
     */
    public void setMessage(String newMessage) {
        message = newMessage;
    }

    /**
     * Purpose   : Returns message.
     * @return   : String 
     */
    public String getMessage() {
        return message;
    }

    /**
     * Purpose   : Returns errorTrace.
     * @return   : String 
     */
    public String getErrorTrace() {
        return errorTrace;
    }
}
