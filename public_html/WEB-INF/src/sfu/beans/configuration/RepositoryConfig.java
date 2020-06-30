package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import sfu.beans.misc.ParseXMLTag;
import sfu.beans.misc.StreamHandler;
import sfu.beans.misc.FileWriter;

public class RepositoryConfig {
    Logger logger = Logger.getLogger("SfuLogger");

    ParseXMLTag repositoryXML = null;
    //String configFilePath="/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/src/sfu/xml/config/XMLConfigFiles/RepositoryConfig.xml";

    String configFilePath = null;

    public RepositoryConfig(String xmlFilePrefix) {
        configFilePath = xmlFilePrefix + "RepositoryConfig.xml";
        logger.debug("configFilePath: " + configFilePath);
        repositoryXML = new ParseXMLTag(configFilePath);
    }

    public String getIPName() {
        return repositoryXML.getValue("IPName", "Configuration");
    }

    public String getConnectString() {
        return repositoryXML.getValue("ConnectString", "Configuration");
    }

    public String getUser() {
        return repositoryXML.getValue("User", "Configuration");
    }

    public String getPassword() {
        return repositoryXML.getValue("Password", "Configuration");
    }

    public void writeNewSettings(String ipName, String connectString,
                                 String user, String password) {
        String configString =
            "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
            "    <Repository>\n" + "     <IPName>" + ipName + "</IPName>\n" +
            "     <ConnectString>" + connectString + "</ConnectString>\n" +
            "     <User>" + user + "</User>\n" + "     <Password>" + password +
            "</Password>\n" + "   </Repository>\n" + "</Configuration>\n";
        FileWriter writeFile = new FileWriter();
        writeFile.writeFile(configFilePath, configString);
    }

    public static void main(String[] args) {
        //RepositoryConfig repositoryConfig=new RepositoryConfig();
        //System.out.println(repositoryConfig.getPassword());
    }
}
