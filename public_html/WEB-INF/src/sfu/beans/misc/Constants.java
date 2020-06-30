package sfu.beans.misc;

public final class Constants {

    //Tag Name 

    private final String constant_name;

    //Private Contructor to use in this class only

    private Constants(String constant_name) {
        this.constant_name = constant_name;
    }

    /**
	 * Purpose  : To Get the Contant , the toString() method of Object Class is overloaded 
	 * @returns : Returns an Contant Name
	 */
    public String toString() {
        return this.constant_name;
    }

    /** Enum Constants  */
    public static final
    /*public static final Constants ACTIVE = new  Constants("Active");
  public static final Constants ACTIVE_VAL = new  Constants("1");
  public static final Constants INACTIVE = new  Constants("In-Active");
  public static final Constants INACTIVE_VAL = new  Constants("0");
  public static final Constants BOTH = new  Constants("BOTH");
  public static final Constants BOTH_VAL = new  Constants("2");
  public static final Constants INS = new  Constants("I");
  public static final Constants UPD = new  Constants("U");
  public static final Constants DEL = new  Constants("D");
  public static final Constants ALL = new  Constants("All");
  */
    Constants ADMIN = new Constants("Admin");

    public static final Constants GENERAL = new Constants("General");

    public static final Constants NON_ADMIN = new Constants("Non-Admin");

    public static final Constants YES = new Constants("Yes");

    public static final Constants NO = new Constants("No");

    public static final Constants LOCKED = new Constants("Locked");

}
