package sfu.beans.uploadEngine;

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
 * $Id: Treeview.java,v 20040220.13 2005/04/14 06:38:26 suved Exp $
 *****************************************************************************
 */

/**
 *	Purpose: To generate Tree Stucture for Folder for a given Librarysession
 *  @author
 *  @version             1.0
 * 	Date of creation:
 * 	Last Modfied by :
 * 	Last Modfied Date:
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Treeview {
  private String user=null;
  private String jsFileName="";                       //A String object that defines the dynamically genreated jsFileName by User object Id
  private String jsFilePath="";                       //A String object that defines the physicalpath for jsFileName
  private String treeId="";                           //A String object that defines the Id for the TreeView
  private String iconPath="";                         //A String object that defines the relative path for icons and images in the tree
  private String tempFolderPath ="temp/";             //A temporary folder name
  private String tempFolderPath4User="";              //A temporary folder name
  private Logger logger=null;
  private Node rootFolder=null;                  //Root Folder of the File System


/**
 * Purpose : Contructs a Treeview Object for Given Librarysession
 * @param jsFilePath - A String object defines the Physical path for jsFile
 * @param iconPath - A String object defines the relative path for icons and images in the tree
 * @param sessionId - A String object defines the relative path jsp Session Id
 */
  public Treeview(String treeId,String jsFilePath,String iconPath, String sessionId,String userId)throws Exception{
    try{
      javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
      javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
      Document d = db.parse("file://" + jsFilePath + "TreeStructure.xml");
      
      this.user=userId;

      this.jsFilePath=jsFilePath;
      this.treeId=treeId;
      this.iconPath=iconPath;

      this.logger = Logger.getLogger("SfuLogger");

      //Temp Folder Path generation to store js and .jsp files

      //this.tempFolderPath4User=this.tempFolderPath + user + "-" + sessionId + "-" + treeId +"/"  ;
      //this.logger.info("Temp Folder Path :" + tempFolderPath4User);


      //Filename generation for .js file which inclues tree Data
      //this.jsFileName= tempFolderPath4User+ user + ".js";
      this.jsFileName=this.tempFolderPath + "ReplicateTree.js";
      this.logger.info("Js File :" + jsFileName);

      //Fetches Root Folder
      this.rootFolder =d.getDocumentElement();
      logger.debug("Root Folder  :" + this.rootFolder.getNodeName());
      logger.debug("Named item  :" + this.rootFolder.getAttributes().getNamedItem("ID").getNodeValue());

      //Initializes Tree
      this.logger.info("Calling initTree()");
      initTree();

      this.logger.info("Instance of the Treeview is Constructed");

    }catch(Exception e){
      e.printStackTrace();
      //this.logger.info("Exception Caught In Contructor of Treeview.java");
      //this.logger.fatal(e.getMessage());
      throw e;
    }

  }

 /**
 * Purpose : Initialize the Folders Tree .
 * @return void
 */
  private void initTree()throws IOException,Exception {
    try{

      this.logger.info("Treeview.initTree() Starts");

      StringBuffer initTreeString = new StringBuffer();
      initTreeString.append("USETEXTLINKS = 1 \n");
      initTreeString.append("STARTALLOPEN = 0" + "\n");
      initTreeString.append("USEFRAMES = 0" + "\n");
      initTreeString.append("HIGHLIGHT = 1" + "\n");
      initTreeString.append("PERSERVESTATE = 1" + "\n");
      initTreeString.append("ICONPATH = '" + this.iconPath + "'" + "\n");
      
      initTreeString.append("foldersTree = gFld(\"/\");" + "\n");
      initTreeString.append("foldersTree.treeID = \""+ this.treeId + "\";" + "\n");
      
      initTreeString.append("foldersTree.xID = \"" + rootFolder.getAttributes().getNamedItem("ID").getNodeValue() + "\";"  + "\n");
      
      initTreeString.append("foldersTree.isChildExist = true;"  + "\n");
      initTreeString.append("foldersTree.isNextLevel = true;"  + "\n");

      this.logger.info("Calling getTreeOfFoldersName");
      //initTreeString.append(getTreeOfFoldersName(this.rootFolder,"foldersTree",1,dbsDirectoryUser.getId()));
      initTreeString.append(getTreeOfFoldersName(this.rootFolder ,"foldersTree",this.user));

      //Creates the temp dir
      if (!(new File(this.jsFilePath + this.tempFolderPath).exists())){
        if (!(new File(this.jsFilePath + this.tempFolderPath).mkdir())){
          this.logger.error("Unable to Create "+ tempFolderPath + " Directory");
        }else{
          this.logger.info("Temp Directory " + tempFolderPath + " Created");
        }
      }

      //Creates the <user> dir
      if (!(new File(this.jsFilePath + this.tempFolderPath4User).exists())){
        if (!(new File(this.jsFilePath + this.tempFolderPath4User).mkdir())){
          this.logger.error("Unable to Create "+ tempFolderPath4User + " Directory");
        }else{
          this.logger.info("User On Session Temp Directory "+ tempFolderPath4User + " Created");
        }
      }

      //Creates .js File
      if (!(writeToFile(initTreeString,this.jsFilePath+this.jsFileName,false))){
        this.logger.error("Unable to Create " + this.jsFilePath+this.jsFileName + " File");
      }else{
        this.logger.info("Master Js File " + this.jsFilePath+this.jsFileName + " Created");
      }

    }catch(Exception e){
      this.logger.info("Exception Caught In initTree() of Treeview.java");
      this.logger.fatal(e.getMessage());
      throw e;
    }

    this.logger.info("Treeview.initTree() Ends");
  }

/**
* Purpose : Creates given file with a given String
* @param singleBuffer - A StringBuffer object defines the content for the file
* @param fileName - A String object defines fullpath with name of file
* @param append - A boolean data defines whether to append the file or overwrite
* @return A booean data results file created or not
*/

  private boolean writeToFile(StringBuffer singleBuffer, String fileName, boolean append)throws IOException,Exception{
    FileOutputStream out;
    PrintStream p;
    String toFile = singleBuffer.toString();
    try{
      out = new FileOutputStream(fileName,append);
      p = new PrintStream( out );
      if(toFile.endsWith("\n"))
        p.println (toFile.substring(0,toFile.lastIndexOf("\n")));
      else
        p.println (toFile);
      p.close();
      return true;
    }catch(Exception e){
      this.logger.info("Exception Caught In writeToFile() of Treeview.java");
      this.logger.fatal(e.getMessage());
      throw e;
    }

  }
  
/**
* Purpose : Returns dynamically generated js file Name.
* @return String object defines js File Name
*/
  public String getJsFileName(){
    return this.jsFileName;
  }


/**
* Purpose : Returns returns dynamically generated jsp file Name.
* @return String object defines jsp File Name
*/
  public String getJsFileLinks(){
    return "<script src=\"" + this.tempFolderPath4User + this.jsFileName + ".js" + "\"></script>\n";
  }


/**
 * Purpose : Creates Tree String for Folders .
 * @param folder - A Folder object
 * @param parent - A String object defined by concatation of "obj" and folder id
 * @param jsFileName4Folder - A Long object defined by the jsFile which contains the Folder object Definition
 * @return A StringBuffer object  containing Tree String of Folders
 */
  private StringBuffer getTreeOfFoldersName(Node folder, String parent, String jsFileName4Folder) throws IOException,Exception{
    StringBuffer treeOfFoldersName = new StringBuffer();
    Node folderItem;
    String child;
    try{
      NodeList folderList = folder.getChildNodes();
      //System.out.println(folderList.item(0).getClass());
      
      for(int i=0; i<folderList.getLength();i++){
        folderItem=(folderList.item(i));
        
        if (folderItem.getAttributes()!=null){
          System.out.println("Item Name  : " + folderItem.getNodeName() );
          System.out.println("Att : " + folderItem.getAttributes());
          child="obj" + folderItem.getAttributes().getNamedItem("ID").getNodeValue();
          
          treeOfFoldersName.append("\n");
          treeOfFoldersName.append(child + "= gFld(\"" + folderItem.getAttributes().getNamedItem("NAME").getNodeValue() + "\");");
          treeOfFoldersName.append("\n");
          treeOfFoldersName.append(child + ".xID=\""+ folderItem.getAttributes().getNamedItem("ID").getNodeValue() + "\";");
          treeOfFoldersName.append("\n");
          treeOfFoldersName.append(child + ".isChildExist="+ folderItem.hasChildNodes() + ";");
          treeOfFoldersName.append("\n");
          treeOfFoldersName.append(child + ".path=\""+  folderItem.getAttributes().getNamedItem("PATH").getNodeValue() + "\";");
          treeOfFoldersName.append("\n");
          treeOfFoldersName.append("insFld(" + parent + "," + child + ");"  + "\n" );
        }  
        
      }

      for(int i=0; i<folderList.getLength();i++){
        folderItem=(folderList.item(i));
        if (folderItem.getAttributes()!=null){        
          treeOfFoldersName.append(getTreeOfFoldersName(folderItem,"obj" + folderItem.getAttributes().getNamedItem("ID").getNodeValue(),jsFileName4Folder));
        }
      }
    
      return treeOfFoldersName;
    }catch(Exception e){
      this.logger.info("Exception Caught In getTreeOfFoldersName() of Treeview.java");
      this.logger.fatal(e.getMessage());
      e.printStackTrace();
      throw e;
    }
  }


  /**
   * Purpose : To free up the objects created in the constructor and
   * delete the temp folder pertaining to the user
   * @return void
   */
  public void free()throws IOException,Exception{
    try{
      this.freeTempFolder();
    }catch(Exception e){
      this.logger.info("Exception Caught In free() of Treeview.java");
      this.logger.fatal(e.getMessage());
      throw e;
    }

    this.logger.info("Instance of the Treeview is Freed");
    this.logger=null;
  }

  /**
   * Purpose : To  delete the temp folder pertaining to the user
   * @return void
   */
  private void freeTempFolder()throws IOException,Exception{
    try{

      File tempFolder2BDeleted=new File(this.jsFilePath + this.tempFolderPath4User);

      if ((tempFolder2BDeleted.exists())){

        // Deleting the files from the temp Folder
        File[] filesInTempFolder=tempFolder2BDeleted.listFiles();
        for(int index=0;index<filesInTempFolder.length;index++){
          if (!(filesInTempFolder[index].delete())){
            this.logger.error("Unable to Delete "+ filesInTempFolder[index].getName() + " File");
          }
        }

        // Deleting the Temp Folder
        if (!(tempFolder2BDeleted.delete())){
          this.logger.error("Unable to Delete "+ this.tempFolderPath4User + " Directory");
        }else{
          this.logger.info("User On Session Temp Directory "+ this.tempFolderPath4User + " Deleted");
        }

      }else{
        this.logger.error("Unable to Find "+ this.jsFilePath + this.tempFolderPath4User + " Directory");
      }

    }catch(Exception e){
      this.logger.info("Exception Caught In freeTempFolder() of Treeview.java");
      this.logger.fatal(e.getMessage());
      throw e;
    }
  }

 public static void main(String[] args ){
    try{
      Treeview treeview = new Treeview("DocUploadTree","/home/amit/dbsentrySFU/public_html/","/home/amit/dbsentrySFU/public_html/images","aaaaa","amishra");
    } catch (Exception e){
      e.printStackTrace();
    }
 }
}
