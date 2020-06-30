package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import sfu.beans.misc.ParseXMLTag;
import sfu.beans.misc.StreamHandler;
import sfu.beans.misc.FileWriter;

public class JobConfig {
    ParseXMLTag jobXML = null;

    Logger logger = Logger.getLogger("SfuLogger");
    //String configFilePath="/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/src/sfu/xml/config/XMLConfigFiles/JobConfig.xml";

    String configFilePath = null;

    public JobConfig(String xmlFilePrefix) {
        configFilePath = xmlFilePrefix + "JobConfig.xml";
        logger.debug("configFilePath: " + configFilePath);
        jobXML = new ParseXMLTag(configFilePath);
    }

    public String getMaxUploadSize() {
        return jobXML.getValue("MaxUploadSize", "Configuration");
    }

    public String getUploadSizeUnit() {
        return jobXML.getValue("UploadSizeUnit", "Configuration");
    }

    public String getMaxMailSize() {
        return jobXML.getValue("MaxMailSize", "Configuration");
    }

    public String getMailSizeUnit() {
        return jobXML.getValue("MailSizeUnit", "Configuration");
    }

    public String getMaxFaxSize() {
        return jobXML.getValue("MaxFaxSize", "Configuration");
    }

    public String getFaxSizeUnit() {
        return jobXML.getValue("FaxSizeUnit", "Configuration");
    }

    public String getMaxPagesToFax() {
        return jobXML.getValue("MaxPagesToFax", "Configuration");
    }

    public String getMaxNumberOfRetries() {
        return jobXML.getValue("MaxNumberOfRetries", "Configuration");
    }

    public String getRetrialInterval() {
        return jobXML.getValue("RetrialInterval", "Configuration");
    }

    public void writeNewSettings(String maxUploadSize, String uploadSizeUnit,
                                 String maxMailSize, String mailSizeUnit,
                                 String maxFaxSize, String maxSizeUnit,
                                 String maxPagesToFaxport,
                                 String maxNumberOfRetries,
                                 String retrialInterval) {
        String configString =
            "<?xml version='1.0' encoding='UTF-8'?>\n" + "<Configuration>\n" +
            "    <Job>\n" + "     <MaxUploadSize>" + maxUploadSize +
            "</MaxUploadSize>\n" + "     <UploadSizeUnit>" + uploadSizeUnit +
            "</UploadSizeUnit>\n" + "     <MaxMailSize>" + maxMailSize +
            "</MaxMailSize>\n" + "     <MailSizeUnit>" + mailSizeUnit +
            "</MailSizeUnit>\n" + "     <MaxFaxSize>" + maxFaxSize +
            "</MaxFaxSize>\n" + "     <FaxSizeUnit>" + maxSizeUnit +
            "</FaxSizeUnit>\n" + "     <MaxPagesToFax>" + maxPagesToFaxport +
            "</MaxPagesToFax>\n" + "     <MaxNumberOfRetries>" +
            maxNumberOfRetries + "</MaxNumberOfRetries>\n" +
            "     <RetrialInterval>" + retrialInterval +
            "</RetrialInterval>\n" + "   </Job>\n" + "</Configuration>\n";
        FileWriter writeFile = new FileWriter();
        writeFile.writeFile(configFilePath, configString);
    }

    public static void main(String[] args) {
        //JobConfig jobConfig=new JobConfig();
        //System.out.println(jobConfig.getMailSizeUnit());
    }
}
