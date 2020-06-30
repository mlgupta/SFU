package sfu.beans.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;


public class Log {
  public Log() {
  }

  public String getLog(String startPos, int numLines, String logFileLoc) {
    String record = null;
    int recCount = 0;
    StringBuffer fileContents = new StringBuffer();
    if (startPos.equals("full")) {
      try {
        FileReader fr = new FileReader(logFileLoc);
        BufferedReader br = new BufferedReader(fr);

        record = new String();
        while ((record = br.readLine()) != null) {
          recCount++;
          fileContents = fileContents.append(record + "\n");
          System.out.println(recCount);
        }

      } catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
      }
    } else if (startPos.equalsIgnoreCase("start")) {
      try {
        FileReader fr = new FileReader(logFileLoc);
        BufferedReader br = new BufferedReader(fr);

        record = new String();
        while ((record = br.readLine()) != null) {
          recCount++;
          fileContents = fileContents.append(record + "\n");
          if (recCount >= numLines) {
            break;
          }
        }

      } catch (IOException e) {
        // catch possible io errors from readLine()
        System.out.println("Uh oh, got an IOException error!");
        e.printStackTrace();
      }
    } else if (startPos.equalsIgnoreCase("end")) {
      try {
        File logFile = new File(logFileLoc);
        RandomAccessFile f = new RandomAccessFile(logFile, "r");
        long len = f.length();
        f.seek(len);
        while (len > 0) {
          f.seek(--len);
          byte b = f.readByte();
          byte[] bArr = new byte[1];
          bArr[0] = b;
          if ((new String(bArr)).equals("\n")) {
            recCount++;
          }
          if (recCount >= numLines) {
            break;
          }
        }
        System.out.println("CurrentPos=" + f.getFilePointer());
        System.out.println("Length=" + f.length());
        while (f.getFilePointer() < f.length()) {

          fileContents = fileContents.append(f.readLine() + "\n");
        }
        f.close();
        System.out.flush();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


    return fileContents.toString();
  }
  //public static void main(String args[]){
  //  Log log = new Log();
  //  System.out.print(log.getLog("start",50,"/home/ias/JPAM Documentation.txt"));
  // }
}
