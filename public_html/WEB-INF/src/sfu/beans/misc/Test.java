package sfu.beans.misc;


import javax.activation.MimetypesFileTypeMap;

import org.quartz.impl.StdSchedulerFactory;

import sfu.beans.scheduler.DateHelper;
import sfu.beans.scheduler.FileByteArray;
import sfu.beans.scheduler.JobCreator;
import sfu.beans.scheduler.JobScheduler;
import sfu.beans.scheduler.*;


//Java API
import java.io.*;

import java.util.*;

//Servlet API
import javax.servlet.*;
import javax.servlet.http.*;

//Struts API
import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.apache.struts.validator.*;

import org.quartz.*;


public class Test {
    private static String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    public Test() {
    }


    //-------------------------------------------

    public static void main(String[] arg) {


        Logger logger = Logger.getLogger("SfuLogger");
        String creator = null;
        String senderEmailAddress = null;
        String txtTo = null;
        String txtCc = null;
        String txtBCc = null;
        String txtSubject = null;
        String txaMail = null;
        String txtSendTime = null;
        String[] lstAttachment;
        String jobRetrialCount = "0";
        String errorMesg = "";
        String jobMaxCount = null;
        String jobRetrialInterval = null;
        String jobErrorMessage = "NA";
        int day;
        int month;
        int year;
        int hours;
        int minutes;
        int seconds;
        String timezone;
        ArrayList attachments = new ArrayList();
        try {
            logger.debug("Initializing Variable ...");

            jobMaxCount = "1";
            jobRetrialInterval = "10";

            creator = "kalicharan";


            senderEmailAddress = "maneesh@kk.com";

            String smtpHost = "192.168.0.1";
            StdSchedulerFactory stFact =
                new StdSchedulerFactory("/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/quartz.properties");
            System.out.println("Initializing Scheduler ........");
            Scheduler sched = stFact.getScheduler();
            System.out.println("Scheduler Initialized ");
            System.out.println("Starting Scheduler ......... ");
            sched.start();
            System.out.println("Scheduler Started ");
            JobScheduler jobSched = new JobScheduler(sched);

            txtTo = "maneeshm@gmail.com";
            txtCc = "maneeshmishra2000@yahoo.com";
            txtBCc = "maneeshsmishra2000@gmail";
            txtSubject = "Hello Maneesh";
            txaMail = "This is just a test message";
            day = 18;
            month = 11;
            year = 2004;
            hours = 16;
            minutes = 40;
            seconds = 00;
            String parentFolder = "/home/ias";
            lstAttachment = Filter.getFileList(parentFolder, "html");
            //lstAttachment=new String[] {"/home/ias/wp_t610_r2a.pdf"};
            FileByteArray[] fileArr = null;
            if (lstAttachment != null) {
                fileArr = new FileByteArray[lstAttachment.length];
                for (int index = 0; index < lstAttachment.length; index++) {
                    fileArr[index] = new FileByteArray();
                    System.out
                    .println(parentFolder + "/" + lstAttachment[index]);
                    File attachedfile =
                        new File(parentFolder + "/" + lstAttachment[index]);
                    InputStream fileInputStream =
                        new FileInputStream(attachedfile);
                    int fileSize = fileInputStream.available();
                    byte[] fileBytes = new byte[fileSize];
                    fileInputStream.read(fileBytes, 0, fileSize);
                    String fileName = lstAttachment[index];
                    String mimeType =
                        new MimetypesFileTypeMap().getContentType(attachedfile);
                    fileArr[index].setFileBytes(fileBytes);
                    fileArr[index].setFileName(fileName);
                    fileArr[index].setMimeType(mimeType);
                    attachments.add(fileArr[index]);
                    fileInputStream.close();
                }
            }
            JobCreator jc = new JobCreator();
            //Date startTime=DateHelper.parse(txtSendTime,DATE_FORMAT);
            //TimeZone userTimeZone=TimeZone.getDefault();
            Date startTime =
                DateHelper.getDate(year, month, day, hours, minutes, seconds);
            /*if(startTime.compareTo(new Date())<=0){
                            startTime=null;
                        }*/
            JobDataMap jobData =
                jc.createMailData(txtTo, senderEmailAddress, txtCc, txtBCc,
                                                   txtSubject, txaMail,
                                                   creator, smtpHost,
                                                   startTime, jobRetrialCount,
                                                   jobRetrialInterval,
                                                   jobMaxCount,
                                                   jobErrorMessage,
                                                   parentFolder,
                                                   attachments);
            jobSched.addJob(jobData);
            System.out.println("Job added successfully");
            sched.shutdown();
            System.exit(0);

        } catch (Exception exception) {
            exception.printStackTrace();
            logger.error(exception);

        }


    }


    //-------------------------------------------
}
