package sfu.beans.scheduler;

import filesync.upload.GenerateXML;
import java.io.*;

import java.util.*;

import javax.activation.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.*;

import org.quartz.*;

public class ReplicateJob  implements Job{
  Logger logger= Logger.getLogger("SfuLogger"); 
  JobDataMap data=null;
  Scheduler sched=null;
   public void execute(JobExecutionContext context)
            throws JobExecutionException {     
            String mailDesc=null;
            try {
                logger.info("Trying to execute Replicate Job.");
                sched=(Scheduler)context.getScheduler();
                SchedulerContext schedContext=sched.getContext();
                data = context.getJobDetail().getJobDataMap();
                GenerateXML generateXML = new GenerateXML((String)schedContext.get("xmlFilePrefix"),sched.getContext(),(String)schedContext.get("contextPath"));
                generateXML.getXml();
                logger.info("Replicate Job executed successfully.");
            }catch(Exception e){
              logger.error(e.toString());              
            }
            }
  public ReplicateJob() {
  }
}