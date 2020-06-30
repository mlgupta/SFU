package sfu.beans.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import java.util.regex.Pattern;

import sfu.beans.scan.ScannerOptionBean;

public final class ScannerOptionParser {
  
  static Logger logger = Logger.getLogger("SfuLogger");
  
  public static ArrayList getOptions(String deviceName ) throws Exception{
    Runtime rt=null;
    Process proc=null;
    String cmd =null;
    ArrayList options=null;
    String line = null;
    InputStreamReader isr=null;
    BufferedReader br=null;
    String prefix="--";
    String optionName=null;
    String optionHelp=null;
    ScannerOptionBean scannerOptionBean=null;
    final String PARSE_AFTER="Options specific to device";
    try{
      options=new ArrayList();
      cmd="scanimage -d "+deviceName+" --help";
      rt = Runtime.getRuntime();  
      logger.debug("Executing: "+cmd);
      proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      proc.waitFor();
      isr = new InputStreamReader(proc.getInputStream());
      br = new BufferedReader(isr);
      
      while ((line = br.readLine())!=null) {
        if(line.trim().indexOf(PARSE_AFTER) >= 0){
          break;
        }
      }
      
      while ((line = br.readLine()) != null) {
        //System.out.println(line);
        line=line.trim();
        //System.out.println(line);
        if(line.startsWith(prefix)){
          String[] lineArray=null;
          lineArray=line.split("[^-^a-zA-Z]");
          
          optionName=lineArray[0];
          optionHelp=line.substring(optionName.length());
          
          System.out.println(optionName+" "+optionHelp);
          
          scannerOptionBean=new ScannerOptionBean();
          scannerOptionBean.setOptionName(optionName);
          scannerOptionBean.setOptionHelp(optionHelp);
          
          options .add(scannerOptionBean);
        }
      }
      
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.toString());
    }finally{
      if(br!=null){
        br.close();
      }
      if(isr!=null){
        isr.close();
      }
    }
    
    return options;
  }
  /*public static ArrayList getOptions(String deviceName ) throws Exception{
    Runtime rt=null;
    Process proc=null;
    String cmd =null;
    ArrayList options=null;
    String line = null;
    InputStreamReader isr=null;
    BufferedReader br=null;
    String prefix="--";
    final String PARSE_AFTER="Options specific to device";
    try{
      options=new ArrayList();
      cmd="scanimage -d "+deviceName+" --help";
      rt = Runtime.getRuntime();  
      logger.debug("Executing: "+cmd);
      proc = rt.exec(new String[] { "/bin/sh", "-c", cmd });
      proc.waitFor();
      isr = new InputStreamReader(proc.getInputStream());
      br = new BufferedReader(isr);
      
      while ((line = br.readLine())!=null) {
        if(line.trim().indexOf(PARSE_AFTER) >= 0){
          break;
        }
      }
      
      while ((line = br.readLine()) != null) {
        //System.out.println(line);
        line=line.trim();
        //System.out.println(line);
        if(line.startsWith(prefix)){
          String[] lineArray=null;
          lineArray=line.split("[^-^a-zA-Z]");
          line=lineArray[0];
          logger.debug("line:"+line);
          System.out.println(""+line);
          options .add(line);
        }
      }
      
    }catch(Exception e){
      e.printStackTrace();
      logger.error(e.toString());
    }finally{
      if(br!=null){
        br.close();
      }
      if(isr!=null){
        isr.close();
      }
    }
    
    return options;
  }
  */
  public static void main (String args[]) throws Exception{
   
    getOptions("hpoj:mlc:usb:OfficeJet_6100_Series");
    
  }

}
