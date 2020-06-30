package sfu.beans.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamHandler extends Thread {
    private static String TYPE_ERROR = "ERROR";

    private static String TYPE_OUTPUT = "OUTPUT";

    public static String output = null;

    InputStream is;

    String type;

    boolean isOutPutRequired = false;

    public StreamHandler(InputStream is, String type,
                         boolean isOutPutRequired) {
        this.is = is;
        this.type = type;
        this.isOutPutRequired = isOutPutRequired;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(type + ">" + line);
                if (isOutPutRequired == true) {
                    output = line;
                    System.out.println("Output : " + output);
                }
            }

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
