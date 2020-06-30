package sfu.beans.misc;

import java.io.*;

public class FileReader {

    public static void main(String[] args) {
        FileReader f = new FileReader();
        f.readFromFile("/etc/passwd");
    }

    public static String readFromFile(String fileName) {

        DataInputStream dis = null;
        String fileData = null;
        try {

            File f = new File(fileName);
            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            byte[] fileBytes = new byte[dis.available()];
            dis.readFully(fileBytes);
            fileData = new String(fileBytes);

        } catch (IOException e) {
            // catch io errors from FileInputStream or readLine() 
            System.out
            .println("Uh oh, got an IOException error!" + e.getMessage());

        } finally {
            // if the file opened okay, make sure we close it 
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException ioe) {
                }
            }
        }
        return fileData;
    }
}
