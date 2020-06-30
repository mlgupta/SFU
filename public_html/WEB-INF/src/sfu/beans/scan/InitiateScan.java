package sfu.beans.scan;

import java.io.File;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.quartz.Scheduler;

import sfu.actionforms.job.JobCreateForm;

import sfu.beans.misc.Filter;
import sfu.beans.scan.ScannerOptionBean;


public class InitiateScan {
  Logger logger = Logger.getLogger("SfuLogger");

  String scannedDocOutputPath = null;

  HttpSession httpSession = null;

  String pattern;

  Scheduler sched = null;

  JobCreateForm jobCreateForm;

  boolean asSingleFile;

  public InitiateScan() {

  }

  /*public InitiateScan(HttpSession httpSession, String txtName, String noOfDoc,
                      boolean asSingleFile, String dpi, String mode,
                      String outputFormat, String scannedDocOutputPath) {
    this.txtName = txtName;
    this.noOfDoc = noOfDoc;
    this.asSingleFile = asSingleFile;
    this.dpi = dpi;
    this.mode = mode;
    this.outputFormat = outputFormat;
    this.scannedDocOutputPath = scannedDocOutputPath;
    this.httpSession = httpSession;
  }*/

  public InitiateScan(HttpSession httpSession, JobCreateForm jobCreateForm,
                      Scheduler sched, String pattern, boolean asSingleFile,
                      String scannedDocOutputPath) {
    logger.info("---Initializing InitiateScan constructor... --- ");

    this.jobCreateForm = jobCreateForm;
    this.sched = sched;
    this.pattern = pattern;
    this.asSingleFile = asSingleFile;
    this.scannedDocOutputPath = scannedDocOutputPath;
    this.httpSession = httpSession;

    logger.info("---Exiting InitiateScan constructor---");
  }

  public void scanPage() throws Exception {
    Process proc = null;
    String scannerSpecificOptionString = "";
    ArrayList scannerOptionFromFile = null;
    try {
      logger.info("---Entering scanPage() method---");

      //making a string of scaner specific options.
      if (httpSession.getAttribute("scannerOptionFromFile") != null) {
        logger
        .debug("size: " + ((ArrayList)httpSession.getAttribute("scannerOptionFromFile"))
                     .size());
        scannerOptionFromFile =
          (ArrayList)httpSession.getAttribute("scannerOptionFromFile");
        ScannerOptionBean scannerOptionBean = null;
        if(scannerOptionFromFile.size()>0){
          for (int i = 0; i < scannerOptionFromFile.size(); i++) {
            scannerOptionBean = (ScannerOptionBean)scannerOptionFromFile.get(i);
            String txtOptionName = scannerOptionBean.getOptionName();
            String txtOptionValue = scannerOptionBean.getOptionValue();
            scannerSpecificOptionString =
              scannerSpecificOptionString + " " + txtOptionName + "=" +
              txtOptionValue;
          }
          logger.debug("scannerSpecificOptionString: "+scannerSpecificOptionString);
        }
      }else{
        logger.debug("scannerOptionFromFile is NULL. ");
      }
      String cmd = "scanimage ";
      if (jobCreateForm.getHdnDevice() != null ||
          jobCreateForm.getHdnDevice().trim().length() != 0) {
        cmd = cmd + "--device-name=" + jobCreateForm.getHdnDevice() + " ";
      }

      if (jobCreateForm.getHdnMode() != null) {
        cmd = cmd + "--mode=" + jobCreateForm.getHdnMode() + " ";
      }

      if (jobCreateForm.getHdnDpi() != null) {
        cmd =
          cmd + "--resolution=" + jobCreateForm.getHdnDpi() + " -x 210 -y 297 ";
      }

      if (jobCreateForm.getTxtDocName() == null ||
          jobCreateForm.getTxtDocName().trim().length() == 0) {
        jobCreateForm
        .setTxtDocName(new String().valueOf(new Date().getTime()));

      }

      String fullDocPath =
        scannedDocOutputPath + File.separator + jobCreateForm.getTxtDocName();

      if (jobCreateForm.getRadNoOfDocuments().equalsIgnoreCase("notAll")) {
        if (jobCreateForm.getTxtDocNo() != null &&
            jobCreateForm.getTxtDocNo().trim().length() != 0 &&
            jobCreateForm.getTxtDocNo().equals("1")) {
          cmd = cmd + " --batch=" + fullDocPath;

        } else {
          cmd = cmd + " --batch=" + fullDocPath + "%00d";
        }
        cmd = cmd + ".pnm ";
        cmd = cmd + " --batch-count=" + jobCreateForm.getTxtDocNo();
        cmd = cmd + " --batch-start=1";

      } else if (jobCreateForm.getRadNoOfDocuments().equalsIgnoreCase("all")) {
        if(scannerSpecificOptionString.indexOf("--batch-scan=yes") > 0){
          cmd = cmd + " --batch=" + fullDocPath + "%00d";
          cmd = cmd + ".pnm ";
          cmd = cmd + " --batch-start=1 --batch-scan=yes ";
        }else{
          cmd = cmd + " --batch=" + fullDocPath + "%00d";
          cmd = cmd + ".pnm ";
          cmd = cmd + " --batch-start=1 ";
        }
      }
      
      cmd=cmd+scannerSpecificOptionString;
      
      Runtime rt = Runtime.getRuntime();

      logger.debug("Executing " + cmd);

      try {
        logger.debug("Executing: " + cmd);
        proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
        httpSession.setAttribute("scanProc", proc);
      } catch (Exception e) {
        logger.error(e.toString());
        e.printStackTrace();
      }


      ScanHandler scanHandler =
        new ScanHandler(proc, httpSession, jobCreateForm, sched, pattern,
                                                asSingleFile,
                                                scannedDocOutputPath);
      scanHandler.start();
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
      throw e;
    } finally {
      logger.info("---Exiting scanPage() method---");
    }
  }

  public int compressPage() {
    String fileList[] = null;
    try {
      logger.info("---Entering compressPage() method---");
      String cmd = "";
      fileList = Filter.getFileList(scannedDocOutputPath, null);

      cmd =
        "mogrify -format jpeg " + scannedDocOutputPath + File.separator + "*.pnm";


      Runtime rt = Runtime.getRuntime();

      logger.debug("Executing " + cmd);

      Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      int exitVal = proc.waitFor();
      // any error message?

      StreamHandler errorHandler =
        new StreamHandler(proc.getErrorStream(), "TYPE_INFO");

      // any output?
      StreamHandler outputHandler =
        new StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");

      // start them 
      errorHandler.start();
      outputHandler.start();

      // any error???

      if (exitVal == 0) {
        logger.debug("From ScanPage:  Compression Successfull");
      } else {
        logger.debug("From ScanPage:  Compression UnSuccessfull");

      }
      for (int index = 0; index < fileList.length; index++) {
        File fileToDelete =
          new File(scannedDocOutputPath + File.separator + fileList[index]);
        if (fileToDelete.exists()) {
          fileToDelete.delete();
        }
      }
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
    } finally {
      logger.info("---Exiting compressPage() method---");
    }

    return fileList.length;
  }

  public int convertPage() {
    String fileList[] = null;
    int exitVal;
    final double SCALE = 0.176;
    try {
      logger.info("---Entering convertPage() method---");
      String cmd = "convert ";
      fileList = Filter.getFileList(scannedDocOutputPath, null);

      String fullDocPath =
        scannedDocOutputPath + File.separator + jobCreateForm.getTxtDocName();

      if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("pdf") &&
          asSingleFile) {
        cmd =
          cmd + " -scale " + SCALE + " " + fullDocPath + "*.pnm " + fullDocPath +
          ".ps";
        exitVal = executeCmd(cmd);
        fileList = Filter.getFileList(scannedDocOutputPath, null);
        if (exitVal == 0) {
          logger.debug("Converted to post script(ps) format successfully ...");
          cmd =
            "convert " + scannedDocOutputPath + File.separator + "*.ps " + fullDocPath +
            ".pdf";
          exitVal = executeCmd(cmd);
          if (exitVal == 0) {
            logger.debug("Converted to single PDF successfully ...");
          } else {
            logger.debug("Conversion to 'PDF' failed ...");
          }
        } else {
          logger.debug("Conversion to 'PS' failed ...");
        }

      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("pdf") &&
                 !asSingleFile) {
        cmd =
          "mogrify -scale " + SCALE + " -format ps " + scannedDocOutputPath +
          File.separator + "*.pnm";
        exitVal = executeCmd(cmd);
        fileList = Filter.getFileList(scannedDocOutputPath, null);
        if (exitVal == 0) {
          cmd =
            "mogrify -format pdf " + scannedDocOutputPath + File.separator +
            "*.ps";
          exitVal = executeCmd(cmd);
          if (exitVal == 0) {
            logger.debug("Converted to 'PDF' format successfully ...");
          } else {
            logger.debug("Conversion to 'PDF' failed ...");
          }
        }
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("jpeg")) {
        cmd =
          "mogrify -format jpeg " + scannedDocOutputPath + File.separator + "*.pnm";
        exitVal = executeCmd(cmd);
        if (exitVal == 0) {
          logger.debug("Converted to 'JPEG' format successfully ...");
        } else {
          logger.debug("Conversion to 'JPEG' failed ...");
        }
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("png")) {
        cmd =
          "mogrify -format png " + scannedDocOutputPath + File.separator + "*.pnm";
        exitVal = executeCmd(cmd);
        if (exitVal == 0) {
          logger.debug("Converted to 'PNG' format successfully ...");
        } else {
          logger.debug("Conversion to 'PNG' failed ...");
        }
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("tiff")) {
        cmd =
          "mogrify -format tiff " + scannedDocOutputPath + File.separator + "*.pnm";
        exitVal = executeCmd(cmd);
        if (exitVal == 0) {
          logger.debug("Converted to 'TIFF' format successfully ...");
        } else {
          logger.debug("Conversion to 'TIFF' failed ...");
        }
      }
      /*//
       logger.info("---Entering convertPage() method---");
       String cmd = "convert -page A4";
       fileList = Filter.getFileList(scannedDocOutputPath, null);

       String fullDocPath =
         scannedDocOutputPath + File.separator + jobCreateForm.getTxtDocName();

       if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("pdf") &&
           asSingleFile) {
         cmd = cmd + " -compress zip "+ fullDocPath + "*.pnm " + fullDocPath + ".pdf";
       } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("pdf") &&
                  !asSingleFile) {
         cmd =
           "mogrify -compress zip -format pdf " + scannedDocOutputPath + File.separator + "*.pnm";
       } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("jpeg")) {
         cmd =
           "mogrify -format jpeg " + scannedDocOutputPath + File.separator + "*.pnm";
       } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("png")) {
         cmd =
           "mogrify -format png " + scannedDocOutputPath + File.separator + "*.pnm";
       } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("tiff")) {
         cmd =
           "mogrify -format tiff " + scannedDocOutputPath + File.separator + "*.pnm";
       }
       */

      for (int index = 0; index < fileList.length; index++) {
        File fileToDelete =
          new File(scannedDocOutputPath + File.separator + fileList[index]);
        if (fileToDelete.exists()) {
          fileToDelete.delete();
          logger.debug("File " + fileToDelete + " deleted after conversion");
        }
      }


    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
    } finally {
      logger.info("---Exiting convertPage() method---");
    }

    return fileList.length;
  }

  private int executeCmd(String cmd) throws IOException, InterruptedException {
    int exitVal = -1;
    try {
      Runtime rt = Runtime.getRuntime();
      logger.debug("Executing " + cmd);
      //Process proc = rt.exec(cmd);
      Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      exitVal = proc.waitFor();
      // any error message?
      StreamHandler errorHandler =
        new StreamHandler(proc.getErrorStream(), "TYPE_INFO");

      // any output?
      StreamHandler outputHandler =
        new StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");

      // start them 
      errorHandler.start();
      outputHandler.start();

      // any error???


      if (exitVal == 0) {
        logger.debug("From ScanPage:  Conversion Successfull");
      } else {
        logger.debug("From ScanPage:  Conversion UnSuccessfull");

      }
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
    return exitVal;
  }
  /*
  public int compressPage() {
    String fileList[] = null;
    try {
      logger.info("---Entering compressPage() method---");
      String cmd = "";
      fileList = Filter.getFileList(scannedDocOutputPath, null);

      cmd =
        "mogrify -format jpeg " + scannedDocOutputPath + File.separator + "*.pnm";


      Runtime rt = Runtime.getRuntime();

      logger.debug("Executing " + cmd);

      Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      int exitVal = proc.waitFor();
      // any error message?

      StreamHandler errorHandler =
        new StreamHandler(proc.getErrorStream(), "TYPE_INFO");

      // any output?
      StreamHandler outputHandler =
        new StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");

      // start them 
      errorHandler.start();
      outputHandler.start();

      // any error???

      if (exitVal == 0) {
        logger.debug("From ScanPage:  Compression Successfull");
      } else {
        logger.debug("From ScanPage:  Compression UnSuccessfull");

      }
      for (int index = 0; index < fileList.length; index++) {
        File fileToDelete =
          new File(scannedDocOutputPath + File.separator + fileList[index]);
        if (fileToDelete.exists()) {
          fileToDelete.delete();
        }
      }
    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
    } finally {
      logger.info("---Exiting compressPage() method---");
    }

    return fileList.length;
  }

  public int convertPage() {
    String fileList[] = null;
    try {
      logger.info("---Entering convertPage() method---");
      String cmd = "convert ";
      fileList = Filter.getFileList(scannedDocOutputPath, null);

      String fullDocPath =
        scannedDocOutputPath + File.separator + jobCreateForm.getTxtDocName();

      if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("pdf") &&
          asSingleFile) {
        cmd = cmd + fullDocPath + "*.jpeg " + fullDocPath + ".pdf";
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("pdf") &&
                 !asSingleFile) {
        cmd =
          "mogrify -format pdf " + scannedDocOutputPath + File.separator + "*.jpeg";
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("jpeg")) {
        cmd =
          "mogrify -format jpeg " + scannedDocOutputPath + File.separator + "*.jpeg";
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("png")) {
        cmd =
          "mogrify -format png " + scannedDocOutputPath + File.separator + "*.jpeg";
      } else if (jobCreateForm.getCboOutputFormat().equalsIgnoreCase("tiff")) {
        cmd =
          "mogrify -format tiff " + scannedDocOutputPath + File.separator + "*.jpeg";
      }


      Runtime rt = Runtime.getRuntime();
      logger.debug("Executing " + cmd);
      //Process proc = rt.exec(cmd);
      Process proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      int exitVal = proc.waitFor();
      // any error message?
      StreamHandler errorHandler =
        new StreamHandler(proc.getErrorStream(), "TYPE_INFO");

      // any output?
      StreamHandler outputHandler =
        new StreamHandler(proc.getInputStream(), "TYPE_OUTPUT");

      // start them 
      errorHandler.start();
      outputHandler.start();

      // any error???


      if (exitVal == 0) {
        logger.debug("From ScanPage:  Conversion Successfull");
      } else {
        logger.debug("From ScanPage:  Conversion UnSuccessfull");

      }
      if (!jobCreateForm.getCboOutputFormat().equalsIgnoreCase("jpeg")) {
        for (int index = 0; index < fileList.length; index++) {
          File fileToDelete =
            new File(scannedDocOutputPath + File.separator + fileList[index]);
          if (fileToDelete.exists()) {
            fileToDelete.delete();
            logger.debug("File " + fileToDelete + " deleted after conversion");
          }
        }
      }

    } catch (Exception e) {
      logger.error(e);
      e.printStackTrace();
    } finally {
      logger.info("---Exiting convertPage() method---");
    }

    return fileList.length;
  }
   */

}
//class ends


