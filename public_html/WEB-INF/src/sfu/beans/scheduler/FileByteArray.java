package sfu.beans.scheduler;
import java.io.Serializable;
/**
 *	Purpose: To store all the bytes of a file as a byte array,when they are required
 *           to be scheduled from mailing or faxing.
 *           
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public class FileByteArray implements Serializable
{
    private byte fileBytes[];
    private String fileName;
    private String mimeType;

    public FileByteArray()
    {
    }
    /**
     * Purpose   : Get the file byte array.     
     * @return   All the bytes associated with the file in form of byte array.
     * 
     */
    public byte[] getFileBytes()
    {
        return fileBytes;
    }   

    /**
     * Purpose   : Set the file byte array.   
     * @param    newfileBytes - The bytes associated with the file in form of byte array     
     * 
     */
    public void setFileBytes(byte[] newFileBytes)
    {
        fileBytes = newFileBytes;
    }

    /**
     * Purpose   : Get the file name.     
     * @return   File name associated with the byte array.
     * 
     */
    public String getFileName()
    {
        return fileName;
    }

    /**
     * Purpose   : Set the file name associated with the byte array.   
     * @param    newfileName - file name to set.    
     * 
     */
    public void setFileName(String newFileName)
    {
        fileName = newFileName;
    }

    /**
     * Purpose   : Get the mime type.     
     * @return   Mime type of file associated with the byte array.
     * 
     */
    public String getMimeType()
    {
        return mimeType;
    }

    /**
     * Purpose   : Set the file mime type.   
     * @param    newMimeType - Mime type to set.     
     * 
     */
    public void setMimeType(String newMimeType)
    {
        mimeType = newMimeType;
    }
}