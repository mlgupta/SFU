package sfu.beans.configuration;

import org.apache.log4j.Logger;

import sfu.beans.misc.FileWriter;

import sfu.beans.misc.ParseXMLTag;

public class FaxServerConfig {
    Logger logger = Logger.getLogger("SfuLogger");

    ParseXMLTag faxXML = null;
    //String configFilePath="/home/amit/dbsentrySFU/public_html/WEB-INF/xml/config/XMLConfigFiles/linux/FaxServerConfig.xml";

    String configFilePath = null;

    public FaxServerConfig(String xmlFilePrefix) {
        configFilePath = xmlFilePrefix + "FaxServerConfig.xml";
        logger.debug("configFilePath: " + configFilePath);
        faxXML = new ParseXMLTag(configFilePath);
    }

    public String getIPName() {
        return faxXML.getValue("IPName", "Configuration");
    }

    public String getPort() {
        return faxXML.getValue("Port", "Configuration");
    }

    public void writeNewSettings(String ipName, String port) {
        String configString =
            "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
            "  <FaxServer>\n" + "    <Port>" + port + "</Port>\n" +
            "    <IPName>" + ipName + "</IPName>\n" + "  </FaxServer>\n" +
            "</Configuration>\n";
        FileWriter writeFile = new FileWriter();
        writeFile.writeFile(configFilePath, configString);
    }

    public static void main(String[] args) {
        //SmtpConfig smtpConfig=new SmtpConfig();
        //System.out.println(smtpConfig.getPort());
    }
}
