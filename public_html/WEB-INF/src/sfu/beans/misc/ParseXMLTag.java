package sfu.beans.misc;


import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLNode;
import oracle.xml.parser.v2.XMLParseException;

import org.w3c.dom.DOMException;


public class ParseXMLTag {

    private XMLDocument xmlDoc = null;

    public ParseXMLTag() {
    }

    public ParseXMLTag(String fileName) {
        try {
            DOMParser dmParser = new DOMParser();
            URL url = createURL(fileName);
            dmParser.setErrorStream(System.err);
            dmParser.showWarnings(true);
            dmParser.parse(url);
            xmlDoc = dmParser.getDocument();
            //System.err.println("parsed MESSAGE parsetag successfully");
        } catch (DOMException DomExp) {
            System.err
            .println("Exception in constructer(DOM Exception:)" + DomExp
                               .toString());
        } catch (XMLParseException ex) {
            System.err.println("Exception in constructer" + ex.toString());
        } catch (Exception e) {
            System.err
            .println("Error while establshing connection " + e.toString());
        }
    }

    public String getValue(String sTAG, String pattern) {
        try {
            String sPattern = "";
            //sPattern="/XMLTag[child::"+sTAG+"]";
            sPattern = "/" + pattern + "/*[" + sTAG + "]";
            //sPattern="/XMLTag/*["+sTAG+"]";
            XMLNode connNode = (XMLNode)xmlDoc.selectSingleNode(sPattern);
            if (connNode != null) {
                String sValue = connNode.valueOf(sTAG);
                //System.err.println("Tag :"+sTAG+" "+"sValue :"+sValue);
                //System.out.println("Tag :"+sTAG+" "+"sValue :"+sValue);
                return sValue;
            } else {
                System.err
                .println("Value for TAG " + sTAG + " not Found  in MESSAGE");

                return null;
            }
        } catch (Exception e) {
            System.err
            .println("Exception while getting value from XML " + e.toString());

            return null;
        }
    }
    // end getNodeValue();


    private URL createURL(String fileName) {
        URL url = null;
        try {
            url = new URL(fileName);
        } catch (MalformedURLException ex) {
            File f = new File(fileName);
            try {
                String path = f.getAbsolutePath();
                String fs = System.getProperty("file.separator");
                if (fs.length() == 1) {
                    char sep = fs.charAt(0);
                    if (sep != '/')
                        path = path.replace(sep, '/');
                    if (path.charAt(0) != '/')
                        path = '/' + path;
                }
                path = "file://" + path;
                url = new URL(path);
            } catch (MalformedURLException e) {
                System.err.println("Cannot create url for: " + fileName);

            }
        }
        return url;
    }

    public static void main(String[] args) {

        ParseXMLTag pt =
            new ParseXMLTag("/home/ias/dbsentrySFU/SFUProj/public_html/WEB-INF/classes/sfu/xml/config/XMLConfigFiles/SmtpConfig.xml");
        System.out.println(pt.getValue("IPName", "Configuration"));
    }

}// class











 
