package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import sfu.beans.misc.ParseXMLTag;
import sfu.beans.misc.StreamHandler;
import sfu.beans.misc.FileWriter;

public class ScannerConfig {
  Logger logger = Logger.getLogger("SfuLogger");

  ParseXMLTag scannerXML = null;
  //String configFilePath="/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/src/sfu/xml/config/XMLConfigFiles/ScannerConfig.xml";

  String configFilePath = null;

  public ScannerConfig(String xmlFilePrefix) {
    configFilePath = xmlFilePrefix + "ScannerConfig.xml";
    logger.debug("configFilePath: " + configFilePath);
    scannerXML = new ParseXMLTag(configFilePath);
  }

  public String getScanningDevice() {
    return scannerXML.getValue("ScanningDevice", "Configuration");
  }
  
  public String getScannerModel() {
    return scannerXML.getValue("ScannerModel", "Configuration");
  }

  public String getDPI() {
    return scannerXML.getValue("DPI", "Configuration");
  }

  public String getMode() {
    return scannerXML.getValue("Mode", "Configuration");
  }

  public String getOutputFormat() {
    return scannerXML.getValue("OutputFormat", "Configuration");
  }

  public String getColorEnable() {
    return scannerXML.getValue("ColorEnable", "Configuration");
  }

  public String getJobFolder() {
    return scannerXML.getValue("JobFolder", "Configuration");
  }

  public void writeNewSettings(String scanningDevice,String scannerModel,String dpi, String mode, String outputFormat,
                               String colorEnable, String fleJobFolder) {
    String configString="<?xml version='1.0' encoding='UTF-8'?>\n"
                             +"<Configuration>\n"
                             +"    <Scanner>\n"
                             +"     <ScanningDevice>"+scanningDevice+"</ScanningDevice>\n"
                             +"     <ScannerModel>"+scannerModel+"</ScannerModel>\n"
                             +"     <DPI>"+dpi+"</DPI>\n"
                             +"     <Mode>"+mode+"</Mode>\n"
                             +"     <OutputFormat>"+outputFormat+"</OutputFormat>\n"
                             +"     <ColorEnable>"+colorEnable+"</ColorEnable>\n"
                             +"     <JobFolder>"+fleJobFolder+"</JobFolder>\n"
                             +"   </Scanner>\n"
                             +"</Configuration>\n";                               
    /*String configString =
      "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
      "    <Scanner>\n" + "     <DPI>" + dpi + "</DPI>\n" + "     <Mode>" +
      mode + "</Mode>\n" + "     <OutputFormat>" + outputFormat +
      "</OutputFormat>\n" + "     <ColorEnable>" + colorEnable +
      "</ColorEnable>\n" + "     <JobFolder>" + fleJobFolder +
      "</JobFolder>\n" + "   </Scanner>\n" + "</Configuration>\n";
    */
    FileWriter writeFile = new FileWriter();
    writeFile.writeFile(configFilePath, configString);
  }

  public static void main(String[] args) {
    //ScannerConfig scannerConfig=new ScannerConfig();
    //System.out.println(scannerConfig.getDPI());
  }
}
