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
public class ForRentDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static ForRentDAO instance = null;
    
    /** Creates a new instance of ForRentDAO */
    private ForRentDAO() {
    }
    
    public static synchronized ForRentDAO getInstance() {
        if (instance == null) {
            instance = new ForRentDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /** 
     * This method is called when the physical product that is going to be available for rent is initially set aside as
     * a for rent object
     */
    public synchronized void create(String id) throws DataException{
        Connection conn = null;
        
        try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

               //insert the data into the database
                insert(id, conn);

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
    
    

    /////////////////////////////////////
    ///   READ
    
    /** 
     * This is the public read statement.  It loads an existing record
     * from the database.
     */
    public synchronized ForRent read(String id) throws DataException {
        ForRent fr = null;
         // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            fr = (ForRent)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;

            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                fr = this.read(id, conn);

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
        return fr;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized ForRent read(String id, Connection conn) throws SQLException, DataException {
            ForRent fr = null;
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
           try{ 
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"forrent\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            while(rs.next()){
                fr = new ForRent(rs.getString("id"));
                fr.setTimesrented(rs.getInt("timesrented"));
                fr.setCurrentrental(rs.getString("currentrental"));
            }
                
                // Close prepared statement
                read.close();
                // save to the cache
                Cache.getInstance().put(id, fr);

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
        return fr;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(ForRent fr) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(fr, conn);
            
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
     synchronized void save(ForRent fr, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        
                update(fr, conn);
        }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL udpate statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void update(ForRent fr, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"forrent\"" +
                "SET \"timesrented\" = ?, \"currentrental\" = ? WHERE \"id\" = ?");
        update.setInt(1, fr.getTimesrented());
        update.setString(2, fr.getCurrentrental());
        update.setString(3, fr.getId());
        
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
    private synchronized void insert(String id, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"forrent\" VALUES(?,?,?)");
        insert.setString(1, id);
        insert.setInt(2, 0);
        insert.setString(3, "");

        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // We are not supporting the delete method in the class becuase it would create database integrity issues
 
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    public String getBySerial(String serial) throws DataException {
         ForRent fr = null;
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"serialnum\" = ? ");
            read.setString(1, serial);
            ResultSet rs = read.executeQuery();

            // while loop to populate the list from the results
            while(rs.next()) {
               fr = ForRentDAO.getInstance().read(rs.getString("id"), conn);
                }  
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of customer lists
        String id = fr.getCurrentrental();
        return id;
    }
    
    public List getByID(List physical) throws DataException {
        List<String> list = new LinkedList<String>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            for(int i = 0; i<physical.size(); i++){
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"id\" = ? AND \"forsale\" = ? ");
            read.setString(1, (String) physical.get(i));
            read.setBoolean(2, false);
            ResultSet rs = read.executeQuery();

            // while loop to populate the list from the results
            while(rs.next()) {
               list.add(rs.getString("id"));
                }  
            }
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of customer lists
        return list;
    }
    
     public List getAvailableRentals(List rentals) throws DataException {
        List<ForRent> list = new LinkedList<ForRent>();
        ForRent fr = null;
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            for(int i = 0; i<rentals.size(); i++){
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"forrent\" WHERE \"currentrental\" = ? AND \"id\" = ? ");
            read.setString(1, "");
            read.setString(2, (String) rentals.get(i));
            ResultSet rs = read.executeQuery();

            // while loop to populate the list from the results
            while(rs.next()) {
                fr = new ForRent(rs.getString("id"));
                list.add(fr);
                }  
            }
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of customer lists
        return list;
    }
     
     public ForRent getByRentalID(String id) throws DataException {
        ForRent fr = null;
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"forrent\" WHERE \"currentrental\" = ? ");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();

            // while loop to populate the list from the results
            while(rs.next()) {
                fr = new ForRent(rs.getString("id"));
                }  
            
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of customer lists
        return fr;
    }
     
}
