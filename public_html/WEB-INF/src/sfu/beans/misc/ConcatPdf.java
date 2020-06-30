package sfu.beans.misc;


/**
 * This class demonstrates copying a PDF file using iText.
 * @author Mark Thompson
 */

import java.io.*;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.*;

import java.util.List;
import java.util.ArrayList;

/**
 * This class demonstrates copying a PDF file using iText.
 * @author Mark Thompson
 */
public class

ConcatPdf {

    /**
     * This class can be used to concatenate existing PDF files.
     * (This was an example known as PdfCopy.java)
     * @param args the command line arguments
     */
    public static void mergePdf(String[] args) {
        if (args.length < 2) {
            System.err.println("arguments: destfile file1 [file2 ...]");
        } else {
            try {
                int pageOffset = 0;
                ArrayList master = new ArrayList();
                int f = 1;
                String outFile = args[0];
                Document document = null;
                PdfCopy writer = null;
                while (f < args.length) {
                    // we create a reader for a certain document
                    PdfReader reader = new PdfReader(args[f]);
                    reader.consolidateNamedDestinations();
                    // we retrieve the total number of pages
                    int n = reader.getNumberOfPages();
                    List bookmarks = SimpleBookmark.getBookmark(reader);
                    if (bookmarks != null) {
                        if (pageOffset != 0)
                            SimpleBookmark
                            .shiftPageNumbers(bookmarks, pageOffset, null);
                        master.addAll(bookmarks);
                    }
                    pageOffset += n;
                    System.out
                    .println("There are " + n + " pages in " + args[f]);

                    if (f == 1) {
                        // step 1: creation of a document-object
                        document =
                            new Document(reader.getPageSizeWithRotation(1));
                        // step 2: we create a writer that listens to the document
                        writer =
                            new PdfCopy(document, new FileOutputStream(outFile));
                        // step 3: we open the document
                        document.open();
                    }
                    // step 4: we add content
                    PdfImportedPage page;
                    for (int i = 0; i < n; ) {
                        ++i;
                        page = writer.getImportedPage(reader, i);
                        writer.addPage(page);
                        System.out.println("Processed page " + i);
                    }
                    PRAcroForm form = reader.getAcroForm();
                    if (form != null)
                        writer.copyAcroForm(reader);
                    f++;
                }
                if (master.size() > 0)
                    writer.setOutlines(master);
                // step 5: we close the document
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String ag[] =
        { "/home/ias/finalFile.pdf", "/OracleProductsSetup/iasold/ias/Desktop/DNSandSendmail.pdf",
                        "/OracleProductsSetup/iasold/ias/Desktop/JavaMail-1.2.pdf",
                        "/OracleProductsSetup/iasold/ias/Desktop/Sendmail.pdf" };
        ConcatPdf.mergePdf(ag);
    }
}
