package sfu.beans.scheduler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 *	Purpose: To create a DataSource from a byte array
 * 
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   23-12-2003
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class ByteArrayDataSource implements DataSource {
    private byte[] data;	// data
    private String type;	// content-type
    private String name=null;

    

    /**
     * Purpose   : To create ByteArrayDataSource
     * @param    data - A byte array consisting of all the bytes of a document/file
     * @param    type - The mime type of the document/file.
     * @param    name - The name of document/file with its extension.     
     */
    public ByteArrayDataSource(byte[] data, String type,String name) {
        this.data = data;
	    this.type = type;
        this.name=name;
    } 
    

    /**
     * Purpose   :Return an InputStream for the data.
     *            Note - a new stream must be returned each time.
     * @return    InputStream            
     */
    public InputStream getInputStream() throws IOException {
	if (data == null)
	    throw new IOException("no data");
	return new ByteArrayInputStream(data);
    }

    /**
     * Purpose   :This function is just to override as it is
     *            is present in Datasource interface.
     *            It should not be used .
     */
    public OutputStream getOutputStream() throws IOException {
	throw new IOException("cannot do this");
    }

    /**
     * Purpose   :Get the mime type associated with the byte array
     *            data.
     * @return    Mime type           
     */
    public String getContentType(){
        return type;
    }
    /**
     * Purpose   :Get the name of the file/document associated with the 
     *            byte array data.
     * @return    Name of the file.
     */
    public String getName(){
        return name;
    }
    /**
     * Purpose   :Set the name of the file/document associated with the 
     *            byte array data.
     * @param    Name of the file.
     */
    public void setName(String newName){
        name = newName;
    }
    
    
}
