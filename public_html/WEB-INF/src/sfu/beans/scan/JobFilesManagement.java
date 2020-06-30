package sfu.beans.scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class JobFilesManagement {
  static final String PROFILE = "JobFileProps.properties";

  static Logger logger = Logger.getLogger("SfuLogger");

  public static Properties createJobFileProperties(String scannedDocOutputPath,
                                                   boolean isMailChecked,
                                                   boolean isUploadChecked,
                                                   boolean isFaxChecked) {
    Properties jobFileProps = new Properties();
    String fileName = null;
    try {
      if (isMailChecked) {
        jobFileProps.setProperty("curr_dir_files_req_for_mail_job", "true");
      } else {
        jobFileProps.setProperty("curr_dir_files_req_for_mail_job", "false");
      }
      if (isUploadChecked) {
        jobFileProps.setProperty("curr_dir_files_req_for_upload_job", "true");
      } else {
        jobFileProps.setProperty("curr_dir_files_req_for_upload_job", "false");
      }
      if (isFaxChecked) {
        jobFileProps.setProperty("curr_dir_files_req_for_fax_job", "true");
      } else {
        jobFileProps.setProperty("curr_dir_files_req_for_fax_job", "false");
      }

      fileName = scannedDocOutputPath + File.separator + PROFILE;
      saveProperties(jobFileProps, fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jobFileProps;

  }
  private static void saveProperties(Properties p, String fileName) {

    OutputStream propsFile;

    try {
      propsFile = new FileOutputStream(fileName);
      p
      .store(propsFile, "Properties File to find which Job (Mail/Fax/Upload) require files in the current directory.");
      propsFile.close();
    } catch (IOException ioe) {
      System.out.println("I/O Exception.");
      ioe.printStackTrace();
      //System.exit(0);
    }

  }

  //returns weathre all the keys have the value false or not

  public static boolean alterProperties(String scannedDocOutputPath,
                                        String key, String value) {

    boolean isJobFileRequired = false;
    String filename = scannedDocOutputPath + File.separator + PROFILE;
    Properties jobFileProps = loadProperties(filename);
    jobFileProps.remove(key);
    jobFileProps.put(key, value);
    Enumeration enProps = jobFileProps.propertyNames();
    while (enProps.hasMoreElements()) {
      key = (String)enProps.nextElement();
      if (jobFileProps.get(key).equals("true")) {
        isJobFileRequired = true;
        //break;
      }
      logger.debug(key+" : "+jobFileProps.get(key));
    }
    saveProperties(jobFileProps, filename);

    return isJobFileRequired;

  }


  private static Properties loadProperties(String fileName) {

    InputStream propsFile;
    Properties jobFileProps = new Properties();

    try {
      propsFile = new FileInputStream(fileName);
      jobFileProps.load(propsFile);
      propsFile.close();
    } catch (IOException ioe) {
      System.out.println("I/O Exception.");
      ioe.printStackTrace();
      //System.exit(0);
    }

    return jobFileProps;

  }

/*
   private static void printProperties(Properties p, String s) {

    System.out.println();
    System.out.println("========================================");
    System.out.println(s);
    System.out.println("========================================");
    System.out.println("+---------------------------------------+");
    System.out.println("| Print Job File Properties          |");
    System.out.println("+---------------------------------------+");
    p.list(System.out);
    System.out.println();
  }

*/
  /*  private static Properties alterProperties(Properties p) {

    Properties newProps = new Properties();
    Enumeration enProps = p.propertyNames();
    String key = "";

    while (enProps.hasMoreElements()) {

      key = (String)enProps.nextElement();

      // If we were going to use the "log_level" or "database_oid" key in 
      // our application, we would want to cast the value to an Integer or
      // Long.
      //   log_level    = Integer.parseInt(props.getProperty("log_level"));
      //   database_oid = Long.parseLong(props.getProperty("database_oid"));

      if (!key.equals("fake_entry")) {
        if (key.equals("log_level")) {
          newProps.setProperty(key, "3");
        } else {
          newProps.setProperty(key, p.getProperty(key));
        }
      }

    }

    return newProps;

  }
*/

  /**
       * Sole entry point to the class and application.
       * @param args Array of String arguments.
       */
  public static void main(String[] args) {

    //final String PROPFILE = "JobFileProps.properties";
    alterProperties("/home/jdev/Scan/Jobs/amishra", "fax_job_finished",
                    "true");

    //    Properties myProp;
    //    Properties myNewProp;
    //
    //    myProp = createjobFileProperties();
    //    printProperties(myProp, "Newly Created (Default) Properties");
    //    saveProperties(myProp, PROPFILE);
    //    myNewProp = loadProperties(PROPFILE);
    //    printProperties(myNewProp, "Loaded Properties");
    //    myNewProp = alterProperties(myProp);
    //    printProperties(myNewProp, "After Altering Properties");
    //    saveProperties(myNewProp, PROPFILE);

  }

}
