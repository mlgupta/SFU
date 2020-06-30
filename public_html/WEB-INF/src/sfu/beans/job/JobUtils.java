package sfu.beans.job;

import java.io.File;

import org.apache.log4j.Logger;

import org.quartz.SchedulerException;

import sfu.beans.scan.JobFilesManagement;

  public final class JobUtils {
  
    static Logger logger= Logger.getLogger("SfuLogger");
    
    public static void freeJobFiles(String parentFolder,String jobGroup) throws SchedulerException,Exception {
      boolean isJobFileRequired=false;
      try {
        logger.info("---Entering freeJobFiles() method---");
        File jobFile=new File(parentFolder);
        if(jobFile.exists()){
        
          if (jobGroup.equals("MAIL")) {
            isJobFileRequired =
              JobFilesManagement.alterProperties(parentFolder, "curr_dir_files_req_for_mail_job",
                                                                   "false");
          }else if (jobGroup.equals("UPLOAD")) {
            isJobFileRequired =
              JobFilesManagement.alterProperties(parentFolder, "curr_dir_files_req_for_upload_job",
                                                                   "false");
          }else if (jobGroup.equals("FAX")) {
            isJobFileRequired =
              JobFilesManagement.alterProperties(parentFolder, "curr_dir_files_req_for_fax_job",
                                                                   "false");
          }
          logger
          .debug("Are Files Required For Other Jobs : " + isJobFileRequired);
          if (!isJobFileRequired) {
            delJobFiles(parentFolder);
          }

        }else{
          logger.debug("Folder not found: "+parentFolder);
        }
      }
      catch (Exception e) {
        logger.error(e.toString());
        throw e;
      }finally{
        logger.info("---Exiting freeJobFiles() method---");
      }
  }


  public static void delJobFiles(String parentFolder) throws SchedulerException,Exception {
    //Deleting files after they are not required for any other types of job(ie: Fax/Upload)
    try {
      logger.debug("Deleting Job Files From '"+parentFolder+"'");
      File parentFolderToDelete = new File(parentFolder);
      File[] fileToDeleteArray = parentFolderToDelete.listFiles();
      for (int index = 0; index < fileToDeleteArray.length; index++) {
        fileToDeleteArray[index].delete();
      }
      logger.debug("Deleting Job Folder '"+parentFolder+"'");
      parentFolderToDelete.delete();
    } catch (Exception e) {
      logger.error(e.toString());
      throw e;
    }
  }
}
