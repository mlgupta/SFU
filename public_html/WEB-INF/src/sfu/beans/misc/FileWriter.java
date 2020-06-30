package sfu.beans.misc;

import java.io.*;

public class FileWriter {

    public void writeFile(String filePath, String content) {
        FileOutputStream out;
        // declare a file output object
        PrintStream p;
        // declare a print stream object

        try {
            // Create a new file output stream
            // connected to "myfile.txt"
            out = new FileOutputStream(filePath);

            // Connect print stream to the output stream
            p = new PrintStream(out);

            p.println(content);

            p.close();
        } catch (Exception e) {
            System.err.println("Error writing to file");
        }
    }
}

