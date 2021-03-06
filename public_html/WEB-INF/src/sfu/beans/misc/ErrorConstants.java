package sfu.beans.misc;

/**
 *	Purpose: To generate error codes and error values to handle the exceptions that occurs in program. 
 *  @author               Amit Mishra
 *  @version              1.0
 * 	Date of creation:     27-12-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class

ErrorConstants {
    private String code;

    private String value;

    /**
   * Purpose : Contructs an ErrorConstants object and initializes the member variables.
   * @param code - A String object representing the code associated with any error.
   * @param value - A String object representing the value of any error that is generated in 
   *                the program.
   */
    private ErrorConstants(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
   * Purpose : Returns the error code.
   * @return String.
   */
    public String getErrorCode() {
        return this.code;
    }

    /**
   * Purpose : Returns the error value.
   * @return String.
   */
    public String getErrorValue() {
        return this.value;
    }

    /**
   * Purpose : Returns the error code and error value.
   * @return String.
   */
    public String toString() {
        return this.code + " " + this.value;
    }


    public static final ErrorConstants UNIQUE =
        new ErrorConstants("23505", "unique");
    // Initializes an ErrorConstants object with errorcode '001' and errorvalue 'unique'.
     public static final ErrorConstants SCANNING_UNSUCCESSFULL =
         new ErrorConstants("1", "Scanning unsuccessfull");
}
