package sfu.beans.scheduler;
 
import java.util.*;

/**
 *	Purpose: This class implements Comparator interface to help in sorting of columns
 *           in job listing.         
 *           
 * @author              Mishra Maneesh
 * @version             1.0
 * 	Date of creation:   10-03-2004
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class JobComparator  implements Comparator{
        private String[] orderColumns;

        /**
         *	Purpose: To create JobComparator for sorting.
         *  @param : orderColumns - A String array containing
         *           names of columns against which sorting has
         *           to be performed.The sorting is done in the 
         *           same order as the position of the column names. 
         * 
         */
        public  JobComparator(String[] orderColumns){
                this.orderColumns=orderColumns;
        }

        /**
         *	Purpose: To override compare function present in 
         *           Comparator interface such that it provides
         *           custom sorting needs.
         *  @param o1 - First object to be compared. 
         *  @param o2 - Second object to be compared.
         * 
         */
        public int compare(Object o1, Object o2){
                JobListBean c1 = (JobListBean)o1;
                JobListBean c2 = (JobListBean)o2;
                for(int i=0;i<orderColumns.length;i++){
                        if(orderColumns[i].equals("jobName")){
                            int res=c1.jobName.compareTo(c2.jobName);
                            if(res!=0) return(res);
                        }
                        if(orderColumns[i].equals("creatorName")){
                            int res=c1.creatorName.compareTo(c2.creatorName);
                            if(res!=0) return(res);
                        }
                        if(orderColumns[i].equals("createTime")){
                            int res=c1.createTime.compareTo(c2.createTime);
                            if(res!=0) return(res);
                        }
                        if(orderColumns[i].equals("executionTime")){
                            int res=c1.executionTime.compareTo(c2.executionTime);
                            if(res!=0) return(res);
                        }
                        if(orderColumns[i].equals("jobType")){
                            int res=c1.jobType.compareTo(c2.jobType);
                            if(res!=0) return(res);
                        }
                }
                return(0);
                }
}