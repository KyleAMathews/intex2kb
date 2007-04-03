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
    public synchronized void create(String id){
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
    public synchronized List<String> read(String id) throws DataException {
        List<String> forRentList = new LinkedList<String>();
        Connection conn = null;

            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                forRentList = this.read(id, conn);

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
        return forRentList;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized List<String> read(String id, Connection conn) throws SQLException, DataException {
        List<String> forRentList = new LinkedList<String>();
        
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
           try{ 
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"forrent\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            while(rs.next()){
                forRentList.add(rs.getString("timesrented"));
                forRentList.add(rs.getString("currentrental"));
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
        return forRentList;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(List list) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(list, conn);
            
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
     synchronized void save(List list, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        
                update(list, conn);
        }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL udpate statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void update(List list, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"forrent\"" +
                "SET \"timesrented\" = ?, \"currentrental\" = ? WHERE \"id\" = ?");
        update.setInt(1, list.get(0));
        update.setString(2, list.get(1).toString());
        update.setString(3, list.get(2).toString());
        
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
    
    
}
