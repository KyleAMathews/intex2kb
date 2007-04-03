/*
 * RentalReturnDAO.java
 *
 * Created on April 3, 2007, 12:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Bryan
 */
public class RentalReturnDAO {


///////////////////////////////////////
    ///   Singleton pattern
    
    private static RentalReturnDAO instance = null;
    
    /** Creates a new instance of StoreDAO */
    private RentalReturnDAO() {
    }
    
    public static synchronized RentalReturnDAO getInstance() {
        if (instance == null) {
            instance = new RentalReturnDAO();
        }
        return instance;
    }
     
    /////////////////////////////////
    ///   CREATE
    
    /** 
     * There's no need for two creates because we don't need
     * a connection to create BOs.  We run the insert statement
     * later, when it get's saved for the first time.
     */
    public RentalReturn create(String rentalid) throws Exception{
        String id = null;
        id = GUID.generate();
        RentalReturn rr = new RentalReturn(id, rentalid);
        Cache c = Cache.getInstance();
        c.put(rr.getId(), rr);
        return rr;
    }
    
     //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(RentalReturn rr) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(rr, conn);
            
            // release the connection
            ConnectionPool.getInstance().release(conn);
            
        }catch (ConnectionPoolException e){
            throw new DataException("Could not get a connection to the database.");
            
        }catch (SQLException e) {
            
            // rollback
            try {
                conn.rollback();
                ConnectionPool.getInstance().release(conn);
            }catch (ConnectionPoolException ce){
                throw new DataException("There was an error with the connection to the database", ce);
            }catch (SQLException e2) {
                throw new DataException("Big error: could not even release the connection", e2);
            }
            
            throw new DataException("Could not retrieve record for id=" + rr.getId(), e);
        }
        
    } // End of first Save()

    
    /** 
     *  This is a package method that is called by the public save (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     *
     *  By having other DAOs call this save method (rather than update or
     *  insert below, each DAO in a chained save (like the CustomerDAO calling
     *  MembershipDAO calling InterestDAO save chain) can independently decide
     *  whether to udpate or insert the BO it's saving.  That's why I made
     *  update and insert private rather than package level -- if you call
     *  them directly from another DAO, this DAO can't decide whether it's
     *  object needs to be inserted or updated.
     */
     synchronized void save(RentalReturn rr, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (rr.isDirty()) {
            if (rr.isIsInDB()) {
                update(rr, conn);
                
            }else{
                insert(rr, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            rr.setDirty(false);
            
        }
        
        // call save(bo, conn) on any subobjects (like CustomerDAO to MembershipDAO)
        // touch the cache for the object
    }// End of second Save()
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL udpate statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void update(RentalReturn rr, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"rentalreturn\"" +
                "SET \"datein\" = ?, WHERE \"id\" = ?");
        update.setLong(1, rr.getDatein());
        update.setString(2, rr.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(rr.getId());
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(RentalReturn rr, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"rentalreturn\" VALUES(?,?,?)");
        insert.setString(1, rr.getId());
        insert.setString(2, rr.getRentalid());
        insert.setLong(3, rr.getDatein());

        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        rr.setIsInDB(true);
        rr.setDirty(false);
        
        // put Store object into cache
        Cache.getInstance().put(rr.getId(),rr);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // We are not supporting the delete method in the class becuase it would create database integrity issues
    
    //////////////////
    ///// SEARCH METHODS
    
    
      
}
