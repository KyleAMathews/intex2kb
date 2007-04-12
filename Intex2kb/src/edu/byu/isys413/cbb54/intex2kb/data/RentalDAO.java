/*
 * RentalDAO.java
 *
 * Created on March 30, 2007, 2:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.*;

/**
 * Handles all interactions between the database and the Rental objects
 * @author kyle
 */
public class RentalDAO extends RSDAO{
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static RentalDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private RentalDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of RentalDAO
     * @return RentalDAO
     */
    public static synchronized RentalDAO getInstance() {
        if (instance == null) {
            instance = new RentalDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    /**
     * Creates a new Rental object by first creating a GUID and then calling
     * the create method in Rental
     * @throws java.lang.Exception Thrown when there is an error creating a GUID
     * @return Rental
     */
    public RevenueSource create() throws Exception{
        String id = GUID.generate("rn");
        RevenueSource rs = new Rental(id);
        System.out.println("I've created a RentalBO  :  ID: " + rs.getId());
        return rs;
    }
    
    ///////////////////////////////////////////
    /// Read
    /**
     * Reads and returns a Rental object from either the Cache or the
     * database
     * @param id GUID of Rental to be read
     * @param conn Connection
     * @throws java.sql.SQLException Thrown when there is an error in the SQL
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is an error retrieving a database connection
     * @return Rental
     */
    public RevenueSource read (String id, Connection conn) throws SQLException, DataException {
        
        Rental rn = null;
        RevenueSource rs = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            rs = (RevenueSource)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"rental\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rst = read.executeQuery();
            conn.commit();
            
        
            if (rst.next()) {
                rn = new Rental(id);
                rn.setDateDue(rst.getLong("datedue"));
                rn.setDateOut(rst.getLong("dateout"));
                
                rs = (RevenueSource)rn;
                
                // Close prepared statement
                read.close();

            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Store
        return rs;
    }
        
    
    ///////////////////////////////////////////
    /// Save
    
    /**
     * Saves a Rental object to the database
     * @param rsbo Rental
     * @param conn Connection
     * @throws java.lang.Exception Thrown when there is an error retrieving a database connection, or
     * when there is an error in the SQL
     */
    public void save(RevenueSource rsbo, Connection conn) throws Exception{
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (rsbo.isDirty()) {
            if (rsbo.isInDB()) {
                update(rsbo, conn);
            }else{
                insert(rsbo, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            rsbo.setDirty(false);
            System.out.println("Rental object saved");
            
        }
    }
    
    /**
     * Inserts a Rental object into the database
     * @param rsbo Rental
     * @param conn Connection
     * @throws java.lang.Exception Thrown when there is an error retrieving a database connection, or
     * when there is an error in the SQL
     */
    public void insert(RevenueSource rsbo, Connection conn) throws Exception{
        System.out.println("inserting rental");
        Rental rn = (Rental)rsbo;
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"rental\" VALUES (?,?,?)");
        insert.setString(1, rn.getId());
        insert.setLong(2, rn.getDateDue());
        insert.setLong(3, rn.getDateOut());
        insert.executeUpdate();
        rsbo.setInDB(true);
    }
    
    /**
     * Updates a Rental object in the database
     * @param rsbo Rental
     * @param conn Connection
     * @throws java.lang.Exception Thrown when there is an error retrieving a database connection, or
     * when there is an error in the SQL
     */
    public void update(RevenueSource rsbo, Connection conn) throws Exception{
        Rental rn = (Rental)rsbo;
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"rental\" SET \"datedue\"=?, \"dateout\" = ? WHERE \"id\" = ?");
        update.setLong(1, rn.getDateDue());
        update.setLong(2, rn.getDateOut());
        update.setString(3, rn.getId());
        update.executeUpdate();
    }
    
            
    //////////////////////////////////////////
    /// delete
    
    // for business reasons we're not supporting deleting

    
    
}
