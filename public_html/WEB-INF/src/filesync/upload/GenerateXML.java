package filesync.upload;

import dbsentry.filesync.client.FsFileSystemOperationsRemote;
import dbsentry.filesync.client.jxta.JxtaClient;
import dbsentry.filesync.common.CommonUtil;
import dbsentry.filesync.common.FsExceptionHolder;
import dbsentry.filesync.common.FsMessage;
import dbsentry.filesync.common.FsResponse;
import dbsentry.filesync.common.FsUser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Stack;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.SchedulerContext;
import org.w3c.dom.Document;
import sfu.beans.configuration.ReplicationConfig;
import sfu.beans.configuration.RepositoryConfig;


/**
 * This example demonstrates the use of FileSyncClient.jar and FileSyncCommon.jar by 
 * uploading folder/file to FileSync server. It starts by reading various configuration 
 * files, initializing logger, initializing jxta, starting jxta and using jxta to send 
 * request and receive response. The configuration files used are 
 *    1. jxta_config.xml  :-  Contains the parameter to create secure group. 
 *                            and specify the rdv and relay address
 *    2. PlatformConfig   :-  This is a file which is used to configure jxta silently.
 *    3. socket.adv       :-  this file contains the information to create JxtaBiDiPipe
 *    4. enc_dec_key.txt  :-  contains string to encrypt/decrypt data for various purpose.
 *                            ie. data transfer
 * 
 * The client first establishes a connection with the FileSync server by using a 
 * userid/password. Then it send the upload request with necessary parameter to upload 
 * folder/file. And after completion the upload request/response, the connection is closed.
 */
 
public class GenerateXML  {
  Logger logger= Logger.getLogger("SfuLogger"); 
  String parentFolder=null;
  String repositoryUser=null;
  String repositoryPassword=null;
   boolean isClientInitialized=false;
   SchedulerContext schedulerContext = null;
   String filePath=null;
   String jxtaConfigPath=null;
   
   String fs=null;
   
  public GenerateXML.HandleJxtaConfiguration handleJxtaConfiguration;
  
  /**
   * Handles all remote operation
   */
  public FsFileSystemOperationsRemote fsFileSystemOperationsRemote;
  private JxtaClient jxtaClient;
  
  
  /**
   * A constant to indicate which class will process the response.
   */
  private static final String FOR_THIS_CLASS = "FOR_THIS_CLASS";
  
  /**
   * this object is used to provide some synchronization between the request and response.
   */
  private final static String completeLock = "completeLock";
  //private Logger logger;
  
  /**
   * Instantiates the UploadFile class
   */
  public GenerateXML(String xmlFilePrefix,SchedulerContext schedulerContext,String filePath) {
  
    ReplicationConfig replicationConfig=new ReplicationConfig(xmlFilePrefix);
    RepositoryConfig repositoryConfig=new RepositoryConfig(xmlFilePrefix);
  
    parentFolder=replicationConfig.getParentFolder();
    repositoryUser=repositoryConfig.getUser();
    repositoryPassword=repositoryConfig.getPassword();
  
    logger.debug("***parentFolder: "+parentFolder);
    logger.debug("***repositoryUser: "+repositoryUser);
    logger.debug("***repositoryPassword: "+repositoryPassword);
    this.schedulerContext = schedulerContext ;
    this.filePath=filePath;
    this.fs=File.separator;
    this.jxtaConfigPath=filePath+"config"+fs;
    logger.debug("***jxtaConfigPath: "+jxtaConfigPath);
    
    /* initializeLogger();
    handleJxtaConfiguration = new GenerateXML.HandleJxtaConfiguration();
    String userHome = System.getProperty("user.home");

    Logger logger = Logger.getLogger("ClientLogger");

    File socketAdv = new File("/home/amit/dbsentrySFU/config/socket.adv");
    File jxtaConfig = new File("/home/amit/dbsentrySFU/config/jxta_config.xml");
    File encrDecrPassword = new File("/home/amit/dbsentrySFU/config/enc_dec_key.txt");
    File platformConfig = new File("/home/amit/dbsentrySFU/config/PlatformConfig");
    
    jxtaClient = new JxtaClient(logger,socketAdv,jxtaConfig,encrDecrPassword,platformConfig);    
    jxtaClient.addPropertyChangeSupport(handleJxtaConfiguration);
    Thread jxtaThread = new Thread(jxtaClient);
    jxtaThread.start();
    
    try{
      jxtaThread.join();
    }catch(InterruptedException ex){
      ex.printStackTrace();
    }*/
  }
  public GenerateXML() {
    initializeLogger();
    handleJxtaConfiguration = new GenerateXML.HandleJxtaConfiguration();
    String userHome = System.getProperty("user.home");

    Logger logger = Logger.getLogger("ClientLogger");

    File socketAdv = new File(jxtaConfigPath+"socket.adv");
    File jxtaConfig = new File(jxtaConfigPath+"jxta_config.xml");
    File encrDecrPassword = new File(jxtaConfigPath+"enc_dec_key.txt");
    File platformConfig = new File(jxtaConfigPath+"PlatformConfig");
    
    jxtaClient = new JxtaClient(logger,socketAdv,jxtaConfig,encrDecrPassword,platformConfig);    
    jxtaClient.addPropertyChangeSupport(handleJxtaConfiguration);
    Thread jxtaThread = new Thread(jxtaClient);
    jxtaThread.start();
    
    try{
      jxtaThread.join();
    }catch(InterruptedException ex){
      ex.printStackTrace();
    }
    
  }
  
  public void initializeJxta(SchedulerContext schedulerContextContext ) {
    
    initializeLogger();
    fsFileSystemOperationsRemote=(FsFileSystemOperationsRemote)schedulerContextContext.get("fsFileSystemOperationsRemote");
    
    
   if(fsFileSystemOperationsRemote!=null){
       logger.debug("Getting fsFileSystemOperationsRemote from schedulerContext context");
    }else{
        String userHome = System.getProperty("user.home");
        Logger logger = Logger.getLogger("ClientLogger");
        File socketAdv = new File(jxtaConfigPath+"socket.adv");
        System.out.println(socketAdv.getAbsolutePath());
        File jxtaConfig = new File(jxtaConfigPath+"jxta_config.xml");
        File encrDecrPassword = new File(jxtaConfigPath+"enc_dec_key.txt");
        File platformConfig = new File(jxtaConfigPath+"PlatformConfig");
        jxtaClient = new JxtaClient(logger,socketAdv,jxtaConfig,encrDecrPassword,platformConfig);    
        handleJxtaConfiguration = new GenerateXML.HandleJxtaConfiguration();
        jxtaClient.addPropertyChangeSupport(handleJxtaConfiguration);
        //schedulerContextContext.put("jxtaClient",jxtaClient );
        Thread jxtaThread = new Thread(jxtaClient);
        jxtaThread.start();
        try{
          jxtaThread.join();
        }catch(InterruptedException ex){
          ex.printStackTrace();
        }
    }
    
    
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    int length = args.length;
    
    /*if(length >= 3){
      UploadFile uploadFile = new UploadFile();
      uploadFile.upload(args);
    }else{
      System.out.println("Invalid number of arguments or format");
      System.out.println("\nUsage Syntax");
      System.out.println("\tjava -jar UploadExample.jar \"file/folder complete path\" src dest");
    }*/
    GenerateXML generateXML = new GenerateXML();
    generateXML.getXml();
  }
  
  public void getXml(){
    initializeJxta(schedulerContext);
    FsUser fsUser = new FsUser();
    fsUser.setUserId(repositoryUser);
    fsUser.setUserPassword(repositoryPassword);
    fsFileSystemOperationsRemote.connectUser(fsUser,FOR_THIS_CLASS);
    waitUntilCompleted();
    fsFileSystemOperationsRemote.getFolderContentRecursive(parentFolder,FOR_THIS_CLASS);
    waitUntilCompleted();
    System.out.println("Got XML File ");    
    fsFileSystemOperationsRemote.disconnectUser(FOR_THIS_CLASS);
    waitUntilCompleted();
    //System.exit(0);
  }
  
  /**
   * Initialize logger
   */
  private void initializeLogger(){
    try {
      System.out.println("******2Initializing Logger...");
      String userHome = System.getProperty("user.home");

      File logFolder = new File(userHome + "/.dbsfs/log");
      if(!logFolder.exists()){
        logFolder.mkdirs();
      }
      
      File file = new File(jxtaConfigPath+"log4j.properties");
      if(file.exists()) {
        PropertyConfigurator.configureAndWatch(file.getAbsolutePath(),2000);
      }else{
        System.out.println("The application was unable to initialize logger properly.");
        System.out.println("log4j-initialization-file : '" + file.getAbsolutePath() + "'");
        System.out.println("The application will exit now!");
        //System.exit(1);
      }
      
      logger = Logger.getLogger("ClientLogger");
      logger.info("Logger initialized successfully");

    }catch(Exception ex){
      ex.printStackTrace();
      //System.exit(1);
    }
  }  

  /**
   * Used to handle jxta configuration notification. and initialize the app for 
   * communication with the FileSync server
   */
  private class HandleJxtaConfiguration implements PropertyChangeListener {
      
      public void propertyChange(PropertyChangeEvent evt){
      Boolean jxtaConfigured = null;
        Logger logger = Logger.getLogger("ClientLogger");
        if(!isClientInitialized){
          jxtaClient = (JxtaClient)evt.getSource();
          jxtaConfigured = (Boolean)evt.getNewValue();
        }else{
          jxtaConfigured=new Boolean(true);
        }
        
        jxtaConfigured = (Boolean)evt.getNewValue();
        if(jxtaConfigured.booleanValue()){
          try{
            File encrDecrPassword = new File(jxtaConfigPath+"enc_dec_key.txt");
            fsFileSystemOperationsRemote = new FsFileSystemOperationsRemote(logger,jxtaClient,encrDecrPassword);
            fsFileSystemOperationsRemote.addPropertyChangeSupport(new PropertyChangeListener(){
              public void propertyChange(PropertyChangeEvent evt) {
              // TODO:  Implement this java.beans.PropertyChangeListener abstract method
              propertyChangeFileSystemOperationRemote(evt);
              }            
            });
            schedulerContext.put("fsFileSystemOperationsRemote",fsFileSystemOperationsRemote );
          }catch(Exception e){
           e.printStackTrace();
         }
        }else{
          System.out.println("Jxta server not found");
        }
      }
  }

  /**
   * Handles notification after each request/response completion
   * @param evt
   */
  public void propertyChangeFileSystemOperationRemote(PropertyChangeEvent evt){
    logger.debug("evt.getSource() : " + evt.getSource());
    FsResponse fsResponse;
    FsExceptionHolder fsExceptionHolder;
    if(evt.getSource().equals(FOR_THIS_CLASS)){
      if(evt.getPropertyName().equals("fsResponse")){
        fsResponse = (FsResponse)evt.getNewValue();
        logger.debug("fsResponse.getResponseCode() : " + fsResponse.getResponseCode());
        handleResponse(fsResponse);
        processCompleted();
      }
    }
  }

  /**
   * The class which actually handles the notification
   * @param fsResponse FsResponse object as parameter
   */
  private void handleResponse(FsResponse fsResponse) {
    FsExceptionHolder fsExceptionHolder;
    String homeFolder;
    
    try{
      int responseCode = fsResponse.getResponseCode();
      switch (responseCode){
      case FsResponse.CONNECT:
        Boolean connectionSuccessFul = (Boolean)fsResponse.getData();
        if(connectionSuccessFul.booleanValue()){
          logger.info("Connected to the server");
        }else{
          fsExceptionHolder = fsResponse.getFsExceptionHolder();
          logger.error(fsExceptionHolder.getErrorMessage());
          logger.info("Invalid userid/password");
        }
        break;
      case FsMessage.DISCONNECT:
        logger.info("User Disconnected");
        break;
      case FsResponse.DOWNLOAD_COMPLETED:
        //JOptionPane.showMessageDialog(this,"File(s) Downloaded successfully");
        logger.info("Download Completed");
        break;
      case FsResponse.DOWNLOAD_FAILURE:
        logger.info("Download failure");
        break;
      case FsResponse.DOWNLOAD_CANCELED:
        logger.info("Download canceled");
        break;
      case FsResponse.UPLOAD_COMPLETED:
        logger.info("Upload Complete");
        break;
      case FsResponse.UPLOAD_FAILURE:
        logger.info("Upload Failure");
        if(fsResponse.getFsExceptionHolder().getErrorCode() == 30002){
          logger.info("Failed to upload, access denied for the specified destination folder.");
        }
        break;
      case FsMessage.UPLOAD_CANCELED:
        logger.info("Upload canceled");
        break;
      case FsMessage.GET_FOLDER_CONTENT_RECURSIVE:
        String xmlString = (String)fsResponse.getData();
        logger.error("xmlString : " + xmlString);
        CommonUtil commonUtil = new CommonUtil(logger);
        Document document = commonUtil.getDocumentFromXMLString(xmlString);        
        File fileLocal = new File(filePath+File.separator+"TreeStructure.xml");
        commonUtil.saveXMLDocumentToFile(document,fileLocal);
        break;          
      case FsMessage.FETAL_ERROR:
        logger.error("Fetal Error");
        break;
      case FsMessage.ERROR_MESSAGE:
        fsExceptionHolder = fsResponse.getFsExceptionHolder();
        logger.error(fsExceptionHolder);
        break;
      }
    }catch(Exception ex){
      logger.error(ex.getMessage());
    }
  }
  
  /**
   * Makes the thread to wait until the completion of the request
   */
    private void waitUntilCompleted() {
        try {
            synchronized(completeLock) {
                completeLock.wait();
            }
            System.out.println("Done.");
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }

  
  /**
   * Notifies the waiting thread after completion of the request.
   */
  private void processCompleted(){
    synchronized(completeLock) {
      completeLock.notify();
    }
  }
  
}
