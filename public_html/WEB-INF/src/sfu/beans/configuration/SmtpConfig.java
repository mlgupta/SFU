package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import sfu.beans.misc.ParseXMLTag;
import sfu.beans.misc.FileWriter;

public class SmtpConfig {
    Logger logger = Logger.getLogger("SfuLogger");

    ParseXMLTag smtpXML = null;
    //String configFilePath="/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/src/sfu/xml/config/XMLConfigFiles/SmtpConfig.xml";

    String configFilePath = null;

    public SmtpConfig(String xmlFilePrefix) {
        configFilePath = xmlFilePrefix + "SmtpConfig.xml";
        logger.debug("configFilePath: " + configFilePath);
        smtpXML = new ParseXMLTag(configFilePath);
    }

    public String getIPName() {
        return smtpXML.getValue("IPName", "Configuration");
    }

    public String getPort() {
        return smtpXML.getValue("Port", "Configuration");
    }

    public void writeNewSettings(String ipName, String port) {
        String configString =
            "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
            "  <SMTP>\n" + "    <Port>" + port + "</Port>\n" + "    <IPName>" +
            ipName + "</IPName>\n" + "  </SMTP>\n" + "</Configuration>\n";
        FileWriter writeFile = new FileWriter();
        writeFile.writeFile(configFilePath, configString);
    }

    public static void main(String[] args) {
        //SmtpConfig smtpConfig=new SmtpConfig();
        //System.out.println(smtpConfig.getPort());
    }
}
