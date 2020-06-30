package sfu.beans.scheduler;

import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *	Purpose: This class schedules mail and fax jobs.All the jobs come to this scheduler
 *           which then decides when to despatch them according to the date and time given.
 *           
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class JobScheduler 
{
     private Scheduler dbsScheduler=null;
     private Logger logger=Logger.getLogger("SfuLogger");

    /**
     * Purpose   : To create JobScheduler instance by initializing and starting a new 
     *             scheduler instance.
     */  
    public JobScheduler(){
        try{
            logger.info("Initialing Scheduler");            
            dbsScheduler=StdSchedulerFactory.getDefaultScheduler();
            dbsScheduler.start();
            logger.info("Scheduler Initialized Is Paused="+dbsScheduler.isPaused());                      
        }catch(SchedulerException se){
            logger.error(se);
        }
    }

    /**
     * Purpose   : To create JobScheduler instance by passing the already running scheduler
     *              instance.
     * @param    scheduler - Already running scheduler instance.             
     */ 
     public JobScheduler(Scheduler scheduler){
        
            logger.info("Aquired already running scheduler");            
            dbsScheduler=scheduler; 
    }

    /**
     * Purpose   : To add a mail job to the job scheduler, it gets all the required 
     *             data from mailJobData (eg. creator's name,execution time etc)
     *             ,creates new job and associated trigger with unique name and then 
     *             adds them to the scheduler.
     * @param    mailJobData - Data associated with the mail job.
     * @return   true if mail job is successfully added to the scheduler otherwise false.
     */
    private boolean addMailJob(JobDataMap mailJobData) {
        logger.info("Adding Mail Job");
        boolean isSuccess=false;
        Date dateOfSubmission=new Date();
        String namePrefix=mailJobData.getString(SchedulerConstants.JOB_CREATOR)+DateHelper.format(dateOfSubmission,"HH:mm:ss--yyyy-MM-dd-z");
        String jobName=SchedulerConstants.JOB_NAME_SUFFIX+namePrefix;
        String triggerName=SchedulerConstants.TRIGGER_NAME_SUFFIX+namePrefix;
        Date timeOfExecution=(Date)mailJobData.get(SchedulerConstants.EXECUTION_TIME);
        JobDetail mailJobDetail= new JobDetail(jobName,SchedulerConstants.MAIL_JOB,SendMailJob.class,false,true,true);
        logger.info("Going to set mailJobData");
        mailJobDetail.setJobDataMap(mailJobData);
        mailJobData=null;
        logger.info("mailJobData set");
        SimpleTrigger mailTrigger=null;
        if(timeOfExecution==null){
               mailTrigger=new SimpleTrigger(triggerName,SchedulerConstants.MAIL_JOB);
               
        }else{
               mailTrigger=new SimpleTrigger(triggerName,SchedulerConstants.MAIL_JOB,timeOfExecution);
               
        }
        try{
           //mailJobDetail.addJobListener("mailListener");
           dbsScheduler.scheduleJob(mailJobDetail,mailTrigger);
           
           //dbsScheduler.addJobListener(new MailJobListener("mailListener"));
           isSuccess=true;
           logger.info("Mail Job Addition Complete");
           logger.info("Mail Job Scheduled To Run At "+timeOfExecution);
        }catch(SchedulerException se){
        se.printStackTrace();
            logger.error("An exception occured while scheduling job: "+se);
        }        
        
        return isSuccess;        
    }
    
    private boolean addDocUploadJob(JobDataMap docUploadJobData) {
        logger.info("Adding DocUpload Job");
        boolean isSuccess=false;
        Date dateOfSubmission=new Date();
        String namePrefix=docUploadJobData.getString(SchedulerConstants.JOB_CREATOR)+DateHelper.format(dateOfSubmission,"HH:mm:ss--yyyy-MM-dd-z");
        String jobName=SchedulerConstants.JOB_NAME_SUFFIX+namePrefix;
        String triggerName=SchedulerConstants.TRIGGER_NAME_SUFFIX+namePrefix;
        Date timeOfExecution=(Date)docUploadJobData.get(SchedulerConstants.EXECUTION_TIME);
        JobDetail docUploadJobDetail= new JobDetail(jobName,SchedulerConstants.UPLOAD_JOB,UploadJob.class,false,true,true);
        logger.debug("Going to set docUploadJobDataJobData");
        docUploadJobDetail.setJobDataMap(docUploadJobData);
        docUploadJobData=null;
        logger.debug("docUploadJobData set");
        SimpleTrigger docUploadTrigger=null;
        if(timeOfExecution==null){
               docUploadTrigger=new SimpleTrigger(triggerName,SchedulerConstants.UPLOAD_JOB);
               
        }else{
               docUploadTrigger=new SimpleTrigger(triggerName,SchedulerConstants.UPLOAD_JOB,timeOfExecution);
               
        }
        try{
           //mailJobDetail.addJobListener("mailListener");
           dbsScheduler.scheduleJob(docUploadJobDetail,docUploadTrigger);
           
           //dbsScheduler.addJobListener(new MailJobListener("mailListener"));
           isSuccess=true;
           logger.info("DocUpload Job Addition Complete");
           logger.info("DocUpload Job Scheduled To Run At "+timeOfExecution);
        }catch(SchedulerException se){
        se.printStackTrace();
            logger.error("An exception occured while scheduling job: "+se);
        }        
        
        return isSuccess;
    }

    private boolean addPrintJob(JobDataMap printJobData)
    {
        return false;
    }
    
    /**
     * Purpose   : To add a fax job to the job scheduler, it gets all the required 
     *             data from faxJobData (eg. creator's name,execution time etc)
     *             ,creates new job and associated trigger with unique name and then 
     *             adds them to the scheduler.
     * @param    faxJobData - Data associated with the mail job.
     * @return   true if fax job is successfully added to the scheduler otherwise false.
     */
    private boolean addFaxJob(JobDataMap faxJobData)
    {
        logger.debug("From job scheduler:Adding Fax Job");
        boolean isSuccess=false;
        Date dateOfSubmission=new Date();
        String namePrefix=faxJobData.getString(SchedulerConstants.JOB_CREATOR)+DateHelper.format(dateOfSubmission,"HH:mm:ss--yyyy-MM-dd-z");
        String jobName=SchedulerConstants.JOB_NAME_SUFFIX+namePrefix;
        String triggerName=SchedulerConstants.TRIGGER_NAME_SUFFIX+namePrefix;
        Date timeOfExecution=(Date)faxJobData.get(SchedulerConstants.EXECUTION_TIME);
        JobDetail faxJobDetail= new JobDetail(jobName,SchedulerConstants.FAX_JOB,SendFaxJob.class,false,true,true);
        faxJobDetail.setJobDataMap(faxJobData);
        SimpleTrigger faxTrigger=null;
        if(timeOfExecution==null){
               faxTrigger=new SimpleTrigger(triggerName,SchedulerConstants.FAX_JOB);
        }else{
               faxTrigger=new SimpleTrigger(triggerName,SchedulerConstants.FAX_JOB,timeOfExecution);
        }
        try{
           //faxJobDetail.addJobListener("faxListener");
           dbsScheduler.scheduleJob(faxJobDetail,faxTrigger);
           //dbsScheduler.addJobListener(new FaxJobListener("faxListener"));
           isSuccess=true;
           logger.info("From job scheduler:Fax Job Addition Complete");
           logger.info("From job scheduler:Fax Job Scheduled To Run At "+timeOfExecution);
        
           
        }catch(SchedulerException se){
            logger.error("An exception occured while scheduling job: "+se);
        }
        
        return isSuccess;
    }

    /**
     * Purpose   : To add a job to the job scheduler
     * @param    jobData - Data associated with the job,based on which it checks the 
     *             job type and calls the appropriate function(addMailJob ,addFaxJob etc.)
     * @throws   SchedulerException if the job is not supported.            
     */ 
    public void addJob(JobDataMap jobData) throws SchedulerException
    {
        String jobType=jobData.getString(SchedulerConstants.JOB_TYPE);        
        boolean isSuccess=false;
        if(jobType.equals(SchedulerConstants.MAIL_JOB)){
           isSuccess= addMailJob(jobData);
        }else if(jobType.equals(SchedulerConstants.PRINT_JOB)){
           isSuccess= addPrintJob(jobData);
        }else if(jobType.equals(SchedulerConstants.FAX_JOB)){
           isSuccess= addFaxJob(jobData);
        }else if(jobType.equals(SchedulerConstants.UPLOAD_JOB)){
           isSuccess= addDocUploadJob(jobData);
        }else{
            throw new SchedulerException("Unsupported JOB");
        }
    }
}