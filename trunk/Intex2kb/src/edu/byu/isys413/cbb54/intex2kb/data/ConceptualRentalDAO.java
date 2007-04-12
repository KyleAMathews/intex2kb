/*
 * StoreDAO.java
 *
 * Created on March 8, 2007, 1:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Cameron
 */
public class ConceptualRentalDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static ConceptualRentalDAO instance = null;
    
    /** Creates a new instance of ForRentDAO */
    private ConceptualRentalDAO() {
    }
    
    /**
     * Returns an instance of the DAO
     */
    public static synchronized ConceptualRentalDAO getInstance() {
        if (instance == null) {
            instance = new ConceptualRentalDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /** 
     * This method is called when the physical product that is going to be available for rent is initially set aside as
     * a for rent object
     */
    /** 
     * There's no need for two creates because we don't need
     * a connection to create BOs.  We run the insert statement
     * later, when it get's saved for the first time.
     */
    public ConceptualRental create() throws Exception{
        String id = null;
        id = GUID.generate();
        ConceptualRental cr= new ConceptualRental(id);
        Cache c = Cache.getInstance();
        c.put(cr.getId(), cr);
        return cr;
    }
    
    

    /////////////////////////////////////
    ///   READ
    
    /** 
     * This is the public read statement.  It loads an existing record
     * from the database.
     */
    public synchronized ConceptualRental read(String id) throws DataException {
        ConceptualRental cr = null;
         // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            cr = (ConceptualRental)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;

            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                cr = this.read(id, conn);

                // release the connection
                conn.commit();
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

                throw new DataException("Could not retrieve record for id=" + id, e);
            }
        }
        return cr;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized ConceptualRental read(String id, Connection conn) throws SQLException, DataException {
            ConceptualRental cr = null;
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
           try{ 
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"conceptualrental\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            while(rs.next()){
                cr = new ConceptualRental(rs.getString("id"));
                cr.setPrice(rs.getDouble("price"));
                cr.setCost(rs.getDouble("cost"));
                cr.setLate(rs.getDouble("late"));
                cr.setMaxlate(rs.getInt("maxlate"));
                cr.setMaxrent(rs.getInt("maxrent"));
            }
                
                // Close prepared statement
                read.close();

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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
        
        // return the Store
        return cr;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(ConceptualRental cr) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(cr, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" , e);
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
     synchronized void save(ConceptualRental cr, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (cr.isDirty()) {
            if (cr.isIndb()) {
                update(cr, conn);
                
            }else{
                insert(cr, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            cr.setDirty(false);
            
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
    private synchronized void update(ConceptualRental cr, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"conceptualrental\"" +
                "SET \"price\" = ?, \"cost\" = ?, \"late\" = ?, \"maxrent\" = ?, \"maxlate\" = ? WHERE \"id\" = ?");
        update.setDouble(1, cr.getPrice());
        update.setDouble(2, cr.getCost());
        update.setDouble(3, cr.getLate());
        update.setInt(4, cr.getMaxrent());
        update.setInt(5, cr.getMaxlate());
        update.setString(6, cr.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(ConceptualRental cr, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"conceptualrental\" VALUES(?,?,?,?,?,?)");
        insert.setString(1, cr.getId());
        insert.setDouble(2, cr.getPrice());
        insert.setDouble(3, cr.getCost());
        insert.setDouble(4, cr.getLate());
        insert.setInt(5, cr.getMaxrent());
        insert.setInt(6, cr.getMaxlate());
        

        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // We are not supporting the delete method in the class becuase it would create database integrity issues
 
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    /**
     * Returns the rental price
     */
    public double getRentalPrice(ForRent fr) throws SQLException, ConnectionPoolException{
        double price = 0.0;
        Connection conn = null;
        try {
            
            conn = ConnectionPool.getInstance().get();
        
        
        
            PreparedStatement read1 = conn.prepareStatement(
                "SELECT \"conceptualproduct\" FROM \"physical\" WHERE \"id\" = ? ");
            read1.setString(1, fr.getId());
            ResultSet rs1 = read1.executeQuery();
                    while(rs1.next()) {
                        String prodid = rs1.getString("conceptualproduct");
                                PreparedStatement read2 = conn.prepareStatement(
                                "SELECT \"price\" FROM \"conceptualrental\" WHERE \"id\" = ? ");
                                read2.setString(1, prodid);
                                ResultSet rs2 = read2.executeQuery();
                                    while(rs2.next()){
                                    price = rs2.getDouble("price");
                                            
                                    }//end while loop
                    }//end while loop
              //release the connection      
              ConnectionPool.getInstance().release(conn);
           } catch (ConnectionPoolException ex) {
            ex.printStackTrace();
        } 
        
      
    return price;
    }//end method
    
    /**
     * Returns the rental name
     */
    public String getRentalName(ForRent fr) throws SQLException, ConnectionPoolException{
        String name = "";
        Connection conn = null;
        try {
            
            conn = ConnectionPool.getInstance().get();
       
            PreparedStatement read1 = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"id\" = ? ");
            read1.setString(1, fr.getId());
            ResultSet rs1 = read1.executeQuery();
                    while(rs1.next()) {
                        String prodid = rs1.getString("conceptualproduct");
                                PreparedStatement read2 = conn.prepareStatement(
                                "SELECT * FROM \"conceptual\" WHERE \"id\" = ? ");
                                read2.setString(1, prodid);
                                ResultSet rs2 = read2.executeQuery();
                                    while(rs2.next()){
                                    name = rs2.getString("name");
                                            
                                    }//end while loop
                    }//end while loop
           } catch (ConnectionPoolException ex) {
            ex.printStackTrace();
        } 
        
        //release the connection
        ConnectionPool.getInstance().release(conn);
    return name;
    }//end method
    
    
    
}