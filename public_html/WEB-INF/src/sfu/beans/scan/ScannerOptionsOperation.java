package sfu.beans.scan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import sfu.beans.misc.General;

public final class ScannerOptionsOperation {
  static final String PROFILE = "ScannerOptionsProps.properties";

  static Logger logger = Logger.getLogger("SfuLogger");

  /*public static Properties createScannerOptionsProperties(String propFilePath) {
    Properties scannerOptionsProps = new Properties();
    String fileName = null;
    try {
      File fileToCreate=new File(propFilePath+File.separator+PROFILE);
      System.out.println(fileToCreate.exists());
      if(!fileToCreate.exists()){
        fileName = propFilePath + File.separator + PROFILE;
        saveProperties(scannerOptionsProps, fileName);
        logger.debug(PROFILE+" created ...");
        //System.out.println(PROFILE+" created ...");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return scannerOptionsProps;

  }*/

  public static Properties createScannerOptionsProperties(String propFilePath,
                                                          ArrayList optionsToAdd) {
    Properties scannerOptionsProps = new Properties();
    ScannerOptionBean scannerOptionBean = null;
    String fileName = null;
    String key = null;
    String value = null;

    try {
      fileName = propFilePath + File.separator + PROFILE;
      for (int index = 0; index < optionsToAdd.size(); index++) {
        scannerOptionBean = (ScannerOptionBean)optionsToAdd.get(index);
        key = scannerOptionBean.getOptionName();
        value = scannerOptionBean.getOptionValue();
        scannerOptionsProps.setProperty(key, value);
      }
      saveProperties(scannerOptionsProps, fileName);
      logger.debug(PROFILE + " created ...");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return scannerOptionsProps;

  }

  private static void saveProperties(Properties p, String fileName) {

    OutputStream propsFile;

    try {
      propsFile = new FileOutputStream(fileName);
      //      p
      //      .storeToXML(propsFile, "This properties file to stores the value of specific scanner model related options.");
      p
      .store(propsFile, "This properties file to stores the value of specific scanner model related options.");
      propsFile.close();
    } catch (IOException ioe) {
      System.out.println("I/O Exception.");
      ioe.printStackTrace();
    }

  }

  public static void addProperty(String propFilePath, String key,
                                 String value) {

    try {
      String filename = propFilePath + File.separator + PROFILE;
      Properties scannerOptionsProps = loadProperties(filename);
      //scannerOptionsProps.remove(key);
      scannerOptionsProps.setProperty(key, value);

      logger.debug(key + " : " + scannerOptionsProps.get(key));

      saveProperties(scannerOptionsProps, filename);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.toString());
    }
  }

  public static void removeProperty(String propFilePath, String key) {
    String filename = propFilePath + File.separator + PROFILE;
    Properties scannerOptionsProps = loadProperties(filename);
    scannerOptionsProps.remove(key);
    saveProperties(scannerOptionsProps, filename);
  }

  public static void alterProperty(String propFilePath, String key,
                                   String value) {

    String filename = propFilePath + File.separator + PROFILE;
    Properties scannerOptionsProps = loadProperties(filename);
    scannerOptionsProps.setProperty(key, value);
    scannerOptionsProps.put(key, value);
    saveProperties(scannerOptionsProps, filename);
  }

  public static String getKeyValue(String propFilePath, String key) {

    String keyValue = null;
    try {
      String filename = propFilePath + File.separator + PROFILE;
      Properties scannerOptionsProps = loadProperties(filename);
      keyValue = scannerOptionsProps.getProperty(key);
    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
    return keyValue;

  }


  private static Properties loadProperties(String fileName) {

    InputStream propsFile;
    Properties scannerOptionsProps = new Properties();

    try {
      File fileToCreate = new File(fileName);
      if (fileToCreate.exists()) {
        propsFile = new FileInputStream(fileName);
        //scannerOptionsProps.loadFromXML(propsFile);
        scannerOptionsProps.load(propsFile);
        propsFile.close();
      }
    } catch (IOException ioe) {
      System.out.println("I/O Exception.");
      ioe.printStackTrace();
      //System.exit(0);
    }

    return scannerOptionsProps;

  }

  public static ArrayList getScannerOptions(String propFilePath) {
    ArrayList scannerOptions = null;
    ScannerOptionBean scannerOptionBean = null;
    String key = null;
    scannerOptions = new ArrayList();
    String filename = propFilePath + File.separator + PROFILE;
    Properties scannerOptionsProps = loadProperties(filename);
    Enumeration enProps = scannerOptionsProps.propertyNames();
    while (enProps.hasMoreElements()) {
      scannerOptionBean = new ScannerOptionBean();
      key = (String)enProps.nextElement();
      scannerOptionBean.setOptionName(key);
      scannerOptionBean.setOptionValue(scannerOptionsProps.getProperty(key));
      scannerOptions.add(scannerOptionBean);

    }

    return General.reverseOrder(scannerOptions);

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
  /*  private static Properties alterProperty(Properties p) {

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

    //final String PROPFILE = "ScannerOptionsProps.properties";
    //alterProperty("/home/jdev", "fax_job_finished",
    //                "true");

    Properties myProp;
    //    Properties myNewProp;
    //
    //myProp = createScannerOptionsProperties("/home/jdev");
    //    printProperties(myProp, "Newly Created (Default) Properties");
    //saveProperties(myProp, PROPFILE);
    //    myNewProp = loadProperties(PROPFILE);
    //    printProperties(myNewProp, "Loaded Properties");
    //    myNewProp = alterProperty(myProp);
    //    printProperties(myNewProp, "After Altering Properties");
    //    saveProperties(myNewProp, PROPFILE);
    //addProperty("/home/jdev", "--resolution", "value3");
    //alterProperty("/home/jdev","asd1","value2");
    //removeProperty("/home/jdev","asd1");

  }

}
