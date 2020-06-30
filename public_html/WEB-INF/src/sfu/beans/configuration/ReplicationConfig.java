package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import sfu.beans.misc.ParseXMLTag;
import sfu.beans.misc.StreamHandler;
import sfu.beans.misc.FileWriter;

public class ReplicationConfig {
    Logger logger = Logger.getLogger("SfuLogger");

    ParseXMLTag replicationXML = null;
    //String configFilePath="/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/src/sfu/xml/config/XMLConfigFiles/ReplicationConfig.xml";

    String configFilePath = null;

    public ReplicationConfig(String xmlFilePrefix) {
        configFilePath = xmlFilePrefix + "ReplicationConfig.xml";
        logger.debug("configFilePath: " + configFilePath);
        replicationXML = new ParseXMLTag(configFilePath);
    }

    public String getParentFolder() {
        return replicationXML.getValue("ParentFolder", "Configuration");
    }

    public String getTimeout() {
        return replicationXML.getValue("Timeout", "Configuration");
    }

    public String getReplicationInterval() {
        return replicationXML.getValue("ReplicationInterval", "Configuration");
    }

    public String getIntervalUnit() {
        return replicationXML.getValue("IntervalUnit", "Configuration");
    }

    public void writeNewSettings(String replicationInterval,
                                 String intervalUnit, String parentFolder,
                                 String timeout) {
        String configString =
            "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
            "  <Replication>\n" + "   <ReplicationInterval>" +
            replicationInterval + "</ReplicationInterval>\n" +
            "   <IntervalUnit>" + intervalUnit + "</IntervalUnit>\n" +
            "   <ParentFolder>" + parentFolder + "</ParentFolder>\n" +
            "   <Timeout>" + timeout + "</Timeout>\n" + " </Replication>\n" +
            "</Configuration>\n";
        FileWriter writeFile = new FileWriter();
        writeFile.writeFile(configFilePath, configString);
    }

    public static void main(String[] args) {
        //ReplicationConfig replicationConfig=new ReplicationConfig();
        //System.out.println(replicationConfig.getTimeout());
    }
}
