package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import sfu.beans.misc.ParseXMLTag;
import sfu.beans.misc.StreamHandler;
import sfu.beans.misc.FileWriter;

public class PatchConfig {
    Logger logger = Logger.getLogger("SfuLogger");

    ParseXMLTag patchXML = null;
    //String configFilePath="/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/src/sfu/xml/config/XMLConfigFiles/PatchConfig.xml";

    String configFilePath = null;

    public PatchConfig(String xmlFilePrefix) {
        configFilePath = xmlFilePrefix + "PatchConfig.xml";
        logger.debug("configFilePath: " + configFilePath);
        patchXML = new ParseXMLTag(configFilePath);
    }

    public String getLocalPatchFolder() {
        return patchXML.getValue("LocalPatchFolder", "Configuration");
    }

    public String getRemotePatchFolder() {
        return patchXML.getValue("RemotePatchFolder", "Configuration");
    }

    public String getPatchCheckInterval() {
        return patchXML.getValue("PatchCheckInterval", "Configuration");
    }

    public String getIntervalUnit() {
        return patchXML.getValue("IntervalUnit", "Configuration");
    }

    public void writeNewSettings(String localPatchFolder,
                                 String remotePatchFolder,
                                 String patchCheckInterval,
                                 String intervalUnit) {
        String configString =
            "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
            "  <Patch>\n" + "  <LocalPatchFolder>" + localPatchFolder +
            "</LocalPatchFolder>\n" + "   <RemotePatchFolder>" +
            remotePatchFolder + "</RemotePatchFolder>\n" +
            "   <PatchCheckInterval>" + patchCheckInterval +
            "</PatchCheckInterval>\n" + "   <IntervalUnit>" + intervalUnit +
            "</IntervalUnit>\n" + " </Patch>\n" + "</Configuration>\n";
        FileWriter writeFile = new FileWriter();
        writeFile.writeFile(configFilePath, configString);
    }

    public static void main(String[] args) {
        //PatchConfig patchConfig=new PatchConfig();
        //System.out.println(patchConfig.getIntervalUnit());
    }
}
