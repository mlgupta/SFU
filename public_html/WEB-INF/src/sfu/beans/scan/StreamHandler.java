package sfu.beans.scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.log4j.Logger;


public class StreamHandler extends Thread {
  Logger logger = Logger.getLogger("SfuLogger");

  private static String TYPE_INFO = "INFO";

  private static String TYPE_OUTPUT = "OUTPUT";

  InputStream is;

  String type;

  public StreamHandler(InputStream is, String type) {
    logger.info("---Initializing StreamHandler constructor---");
    this.is = is;
    this.type = type;
    logger.info("---Exiting StreamHandler constructor---");
  }

  public void run() {
    try {
      logger.info("---Entering run() method of StreamHandler---");
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line = null;
      while ((line = br.readLine()) != null) {
        logger.debug(type + ">" + line);
      }
    } catch (IOException ioe) {
      logger.error(ioe);
      ioe.printStackTrace();
    } catch (Exception e) {
      logger.error(e.toString());
      //e.printStackTrace();
    } finally {
      logger.info("---Exiting run() method of StreamHandler---");
    }
  }
}
