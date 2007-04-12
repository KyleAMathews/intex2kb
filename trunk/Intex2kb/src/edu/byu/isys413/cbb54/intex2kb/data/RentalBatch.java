/*
 * RentalBatch.java
 *
 * Created on April 3, 2007, 3:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;
import java.sql.*;
import java.util.*;

/**
 * Rental Batch handles the charging of customers for overdue rentals,
 * moving items from being for rent to for sale after they have been rented
 * a certain number of times, and charging customer for rentals that have never
 * been returned
 * @author Bryan
 */
public class RentalBatch {
    
    
        ///////////////////////////////////////
    ///   Singleton pattern
    
    private static RentalBatch instance = null;
    

       private RentalBatch() {
        }
       
        /**
     * Creates a new instance of RentalBatch
     * @return RentalBatch
     */
       public static synchronized RentalBatch getInstance() {
        if (instance == null) {
            instance = new RentalBatch();
        }
        return instance;
    }
    
    
    
    /**
     * Method responsible for changing physical for rent product to for sale
     * after they have been rented a certain number of times
     * @throws java.sql.SQLException Thrown when there is an error in the SQL
     * @throws edu.byu.isys413.cbb54.intex2kb.data.ConnectionPoolException Thrown when there is an error retrieving a database connection
     */
    public void movetosale() throws SQLException, ConnectionPoolException{
        //sql query all items that are not for rent and store for rent object in a list
        List<ForRent> list = new LinkedList<ForRent>();
        List<ForRent> list2 = new LinkedList<ForRent>();
        
        // retrieve a database connection from the pool
            Connection conn = ConnectionPool.getInstance().get();
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"forrent\" ");
        ResultSet rs = read.executeQuery();
        
        
        
         // while loop to populate the list from the results
            while(rs.next()) {
                    ForRent fr = new ForRent(rs.getString("id"));
                    fr.setTimesrented(rs.getInt("timesrented"));
                    list.add(fr);
                }
        
        
        
        //query the conceptual rental table for max rental on that rental
        for(int i = 0; i < list.size(); i++){
            PreparedStatement read1 = conn.prepareStatement(
                "SELECT \"conceptualproduct\" FROM \"physical\" WHERE \"id\" = ? ");
            read1.setString(1, list.get(i).getId());
            ResultSet rs1 = read1.executeQuery();
                    while(rs1.next()) {
                        String prodid = rs1.getString("conceptualproduct");
                                PreparedStatement read2 = conn.prepareStatement(
                                "SELECT \"maxrent\" FROM \"conceptualrental\" WHERE \"id\" = ? ");
                                read2.setString(1, prodid);
                                ResultSet rs2 = read2.executeQuery();
                                    while(rs2.next()){
                                    int maxrent = rs2.getInt("maxrent");
                                            if (list.get(i).getTimesrented() == maxrent){
                                            list2.add(list.get(i));
                                            }//end if
                                    }//end while loop
                    }//end while loop
            
        }//end for loop
        
        
        //set the for sale boolean on items that are at the max rental
        for(int i = 0; i < list2.size(); i++){
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"physical\" SET \"forsale\" = ? WHERE \"id\" = ?");
        update.setInt(1, 1);
        update.setString(2, list2.get(i).getId());
        update.executeUpdate();
        }
        
        
        
        // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
        
        System.out.println("Move to sale batch complete");
    }
    
    
    
    /**
     * Method for charging members for rental that have never been returned
     */
    public void chargeReplacement(){
        //find all the rentals that are late
        //calculate the days late that the overdue rentals are
        //compare the max late days with days late of the specific rental
        //create replacement charge on items that have reached max late days
    }
    
}
