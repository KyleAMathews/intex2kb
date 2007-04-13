/*
 * PaymentDAO.java
 *
 * Created on March 20, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Cameron
 */
public class PaymentDAO {

    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static PaymentDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private PaymentDAO() {
    }
    
    /**
     * return an instance of the dao
     */
    public static synchronized PaymentDAO getInstance() {
        if (instance == null) {
            instance = new PaymentDAO();
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
    public Payment create(Transaction transaction) throws Exception{
        String id = null;
        id = GUID.generate();
        Payment pmt = new Payment(id, transaction);
        Cache c = Cache.getInstance();
        c.put(pmt.getId(), pmt);
        return pmt;
    }
    
    /**
     * create a payment object
     */
     public Payment create(Transaction transaction, double amount, String type) throws Exception{
        String id = null;
        id = GUID.generate();
        Payment pmt = new Payment(id, transaction, amount, type);
        Cache c = Cache.getInstance();
        c.put(pmt.getId(), pmt);
        return pmt;
    }

    /////////////////////////////////////
    ///   READ
    
    /** 
     * This is the public read statement.  It loads an existing record
     * from the database.
     */
    public synchronized Payment read(String id, Transaction transaction) throws DataException {
        Payment pmt = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            pmt = (Payment)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                pmt = this.read(id, transaction, conn);

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
        return pmt;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Payment read(String id, Transaction transaction, Connection conn) throws SQLException, DataException {
        Payment pmt = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            pmt = (Payment)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"payment\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
                pmt = new Payment(id, transaction);
                pmt.setAmount(rs.getDouble("amount"));
                pmt.setCcExpiration(rs.getString("ccexpiration"));
                pmt.setCcNumber(rs.getString("ccnumber"));
                pmt.setChange(rs.getDouble("change"));
                pmt.setType(rs.getString("type"));
                pmt.setInDB(true);
                pmt.setDirty(false);
                

                // save to the cache
                Cache.getInstance().put(id, pmt);
                
                // Close prepared statement
                read.close();

            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return pmt;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(Payment pmt) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(pmt, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + pmt.getId(), e);
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
     synchronized void save(Payment pmt, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (pmt.isDirty()) {
            if (pmt.isInDB()) {
                update(pmt, conn);
                
            }else{
                insert(pmt, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            pmt.setDirty(false);
            
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
    private synchronized void update(Payment pmt, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"payment\"" +
                "SET \"transactionid\" = ?, \"amount\" = ?, \"ccexpiration\" = ?, \"ccnumber\" = ?, \"change\" = ?," +
                "\"type\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, pmt.getTransaction().getId());
        update.setDouble(2, pmt.getAmount());
        update.setString(3, pmt.getCcExpiration());
        update.setString(4, pmt.getCcNumber());
        update.setDouble(5, pmt.getChange());
        update.setString(6, pmt.getType());
        update.setString(7, pmt.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(pmt.getId());
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Payment pmt, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"payment\" VALUES(?,?,?,?,?,?,?)");
        insert.setString(1, pmt.getId());
        insert.setDouble(2, pmt.getAmount());
        insert.setString(3, pmt.getCcNumber());
        insert.setString(4, pmt.getCcExpiration());
        insert.setString(5, pmt.getTransaction().getId());
        insert.setDouble(6, pmt.getChange());
        insert.setString(7, pmt.getType());
        

        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        // tell the object that it's now in the db (so we call update next time not insert)
        pmt.setInDB(true);
        pmt.setDirty(false);
        
        // put Customer object into cache
        Cache.getInstance().put(pmt.getId(),pmt);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create database referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    /**
     * returns payment object by transaction
     */
    public Payment getByTransaction(Transaction tx) throws DataException {
        Payment pmt = null;
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"payment\" WHERE \"transactionid\" = ?");
            read.setString(1, tx.getId());
            ResultSet rs = read.executeQuery();

            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            // while loop to populate the list from the results
            if(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                   pmt = (Payment)Cache.getInstance().get(rs.getString("id")); 
                }else{
                    pmt = new Payment(rs.getString("id"), tx);
                    pmt.setAmount(rs.getDouble("amount"));
                    pmt.setCcExpiration(rs.getString("ccexpiration"));
                    pmt.setCcNumber(rs.getString("ccnumber"));
                    pmt.setChange(rs.getDouble("change"));
                    pmt.setType(rs.getString("type"));
                    pmt.setInDB(true);
                    pmt.setDirty(false);
                

                // save to the cache
                Cache.getInstance().put(pmt.getId(), pmt);
                
                // Close prepared statement
                read.close();
                }
            }    

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

            throw new DataException("Could not retrieve payment records form the database",  e);
        }
       
        // return the payment object
        return pmt;
    }
    
}
