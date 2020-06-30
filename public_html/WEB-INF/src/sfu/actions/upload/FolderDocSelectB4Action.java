
package sfu.actions.upload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sfu.actionforms.upload.FolderDocSelectForm;

import sfu.beans.exception.ExceptionBean;
import sfu.beans.uploadEngine.Treeview;
import sfu.beans.user.UserProfile;

/**
 *	Purpose: To Poulate Treeview for Selection
 *  @author              Sudheer Pujar
 *  @version             1.0
 * 	Date of creation:   02-04-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */


/**
 *	Purpose: To Poulate Treeview for Selection
 *  @author              Sudheer Pujar
 *  @version             1.0
 * 	Date of creation:   02-04-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

FolderDocSelectB4Action extends Action {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException,
                                                                                                ServletException {
    Logger logger = Logger.getLogger("SfuLogger");
    logger.info("Performing Folder Doc Tree Append Action ...");

    //variable declaration
    ExceptionBean exceptionBean;
    String forward = "success";
    HttpSession httpSession = null;
    Treeview treeview4Select = null;
    UserProfile userProfile = null;
    String physicalPath = null;
    boolean foldersOnly = true;
    Long currentFolderDocId4Select = null;
    boolean treeAppend = false;
    String openerControl = null;
    String currentFolderPath = null;
    FolderDocSelectForm folderDocSelectForm = null;
    boolean isDocument = false;
    boolean recreateTree = false;
    String heading = "";
    String userID = null;
    String sessionID = null;
    String iconPath = null;
    try {
      httpSession = request.getSession(false);
      physicalPath = request.getSession().getServletContext().getRealPath("/");
      logger.debug("physicalPath: " + physicalPath);
      iconPath = "images/";
      userProfile = (UserProfile)httpSession.getAttribute("userProfile");
      sessionID = httpSession.getId();
      userID = userProfile.getUserId();
      if (request.getParameter("recreate") != null) {
        recreateTree =
          (new Boolean(request.getParameter("recreate"))).booleanValue();
      }


      if (request.getParameter("currentFolderDocId4Select") != null) {
        currentFolderDocId4Select =
          new Long(request.getParameter("currentFolderDocId4Select"));
        logger
        .debug("******currentFolderDocId4Select" + request.getParameter("currentFolderDocId4Select"));
      } else if (httpSession.getAttribute("currentFolderDocId4Select") !=
                 null) {
        currentFolderDocId4Select =
          (Long)httpSession.getAttribute("currentFolderDocId4Select");
      } else {
        currentFolderDocId4Select = Long.valueOf("345430");
        //HARDCODED-------------------------------------------------
        logger
        .debug("currentFolderDocId4Select***: " + currentFolderDocId4Select);
      }

      if (request.getParameter("heading") != null) {
        heading = request.getParameter("heading");
      } else if (httpSession.getAttribute("heading") != null) {
        heading = (String)httpSession.getAttribute("heading");
      }

      if (request.getParameter("foldersOnly") != null) {
        foldersOnly =
          (new Boolean(request.getParameter("foldersOnly"))).booleanValue();
      } else if (httpSession.getAttribute("foldersOnly") != null) {
        foldersOnly =
          (new Boolean(httpSession.getAttribute("foldersOnly").toString()))
          .booleanValue();
      }

      if (request.getParameter("openerControl") != null) {
        openerControl = request.getParameter("openerControl");
      } else if (httpSession.getAttribute("foldersOnly") != null) {
        openerControl = httpSession.getAttribute("openerControl").toString();
      }
      logger.debug("openerControl***: " + openerControl);
      if (request.getParameter("treeAppend") != null) {
        treeAppend = true;
      }

      treeview4Select =
        new Treeview("FolderDocSelect", physicalPath, iconPath, sessionID,
                                     userID);
      logger
      .debug("***currentFolderDocId4Select: " + currentFolderDocId4Select);
      if (currentFolderDocId4Select != null) {

        if (treeview4Select == null || recreateTree) {
          if (treeview4Select != null) {
            treeview4Select.free();
          }
          treeview4Select =
            new Treeview("FolderDocSelect", physicalPath, iconPath, sessionID,
                                         userID);
        }


        currentFolderPath = request.getParameter("rootFolder");

        folderDocSelectForm = (FolderDocSelectForm)form;
        folderDocSelectForm.setFolderDocument(currentFolderPath);
        folderDocSelectForm.setHdnFolderDocument(currentFolderPath);
        folderDocSelectForm.setHdnRootFolder(currentFolderPath);
        folderDocSelectForm.setHdnOpenerControl(openerControl);
        logger
        .debug("***OpenerControl: " + folderDocSelectForm.getHdnOpenerControl());
        folderDocSelectForm.setHdnFoldersOnly(foldersOnly);

        httpSession.setAttribute("Treeview4Select", treeview4Select);
        httpSession
        .setAttribute("currentFolderDocId4Select", currentFolderDocId4Select);
        request.setAttribute("isDocument", Boolean.valueOf(isDocument));

        request.setAttribute(mapping.getAttribute(), folderDocSelectForm);
        httpSession.setAttribute("heading", heading);
      }

      if (httpSession.getAttribute("actionerrors") != null) {
        ActionErrors actionErrors =
          (ActionErrors)httpSession.getAttribute("actionerrors");
        saveErrors(request, actionErrors);
        httpSession.removeAttribute("actionerrors");
      }
    } catch (Exception e) {
      exceptionBean = new ExceptionBean(e);
      logger.error(exceptionBean.getMessage());
      saveErrors(request, exceptionBean.getActionErrors());
      return mapping.findForward(forward);
    }

    return mapping.findForward(forward);

  }
}
