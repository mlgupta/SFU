package sfu.beans.misc;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;

public class Filter implements FilenameFilter {
  static Logger logger=Logger.getLogger("SfuLogger");
  protected String pattern;

  public Filter(String str) {
    pattern = str;
  }

  public boolean accept(File dir, String name) {
    //return name.toLowerCase().endsWith(pattern.toLowerCase());
    return true;
  }

  public static String[] getFileList(String folder, String pattern) {
    Filter nf = new Filter(pattern);
    // current directory
    File dir = new File(folder);
    String[] strs = dir.list(nf);
    logger.debug("Filter pattern: "+pattern);
    for (int i = 0; i < strs.length; i++) {
      logger.debug(strs[i]);
    }
    return strs;
  }

  public static void main(String[] args) {
    System.out.println(Filter.getFileList("/home/amit/scan", "jpg"));
  }
}
