/*
 * CouponDAO.java
 *
 * Created on March 20, 2007, 12:24 PM
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.omg.CORBA.TRANSACTION_MODE;

/**
 * This DAO handles all interaction between the database and the coupon objects.
 * @author Cameron
 */
public class CouponDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static CouponDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private CouponDAO() {
    }
    
    /**
     * This Singleton pattern allows only one instance of CouponDAO to exist
     * @return CouponDAO
     */
    public static synchronized CouponDAO getInstance() {
        if (instance == null) {
            instance = new CouponDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * Creates a new Coupon by creating a id(guid) and then calling the Coupon constructor
     * @return Coupon
     * @throws java.lang.Exception Throws a generic Exception
     */
    public Coupon create() throws Exception{
        String id = null;
        id = GUID.generate();
        Coupon coupon = new Coupon(id);
        Cache c = Cache.getInstance();
        c.put(coupon.getId(), coupon);
        return coupon;
    }

    /////////////////////////////////////
    ///   READ
    
    /**
     * This is the public read statement.  It loads an existing coupon from the database.
     * @param id String
     * @return Coupon
     * @throws DataException Occurs when there is an error getting a database connection, running the SQL statement, or when there is no record with that id.
     */
    public synchronized Coupon read(String id) throws DataException {
        Coupon coupon = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            coupon = (Coupon)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                coupon = this.read(id, conn);

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
        return coupon;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Coupon read(String id, Connection conn) throws SQLException, DataException {
        Coupon coupon= null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            coupon = (Coupon)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"coupon\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
                coupon = new Coupon(id);
                coupon.setAmount(rs.getDouble("amount"));
                coupon.setInDB(true);
                coupon.setDirty(false);
                

                // save to the cache
                Cache.getInstance().put(id, coupon);
                
                // Close prepared statement
                read.close();

            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return coupon;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * This is the public save method.  It is what is called when the user (controller) code wants to save or update an object into the database.
     * @param coupon Coupon
     * @throws DataException Thrown when there is a connection error or an error in executing the SQL.
     */
    public synchronized void save(Coupon coupon) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(coupon, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + coupon.getId(), e);
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
     synchronized void save(Coupon coupon, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (coupon.isDirty()) {
            if (coupon.isInDB()) {
                // Here we do nothing because we do not need to update a coupon
                //update(coupon, conn);
            }else{
                insert(coupon, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            coupon.setDirty(false);
            
        }
        
        // call save(bo, conn) on any subobjects (like CustomerDAO to MembershipDAO)
        // touch the cache for the object
    }// End of second Save()
    
//    /**
//     * This method is really a part of save(bo, conn) above.  It could be
//     * embedded directly in it, but I've separated it into it's own method
//     * to isolate the SQL udpate statement and make it more readable.  But
//     * logically, it's really just a part of save.
//     */
//    private synchronized void update(Coupon coupon, Connection conn) throws SQLException, DataException {
//        // do the update statement
//        PreparedStatement update = conn.prepareStatement(
//            "UPDATE \"coupon\"" +
//                "SET \"transactionid\" = ?, \"amount\" = ?, \"ccexpiration\" = ?, \"ccnumber\" = ?, \"change\" = ?," +
//                "\"type\" = ?" +
//                "WHERE \"id\" = ?");
//        update.setString(1, pmt.getTransaction().getId());
//        update.setDouble(2, pmt.getAmount());
//        update.setString(3, pmt.getCcExpiration());
//        update.setString(4, pmt.getCcNumber());
//        update.setDouble(5, pmt.getChange());
//        update.setString(6, pmt.getType());
//        update.setString(7, pmt.getId());
//        
//        // execute and commit the query
//        update.executeUpdate();
//        conn.commit();
//        
//        // touch the Customer object in cache
//        Cache.getInstance().touch(pmt.getId());
//        
//    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Coupon coupon, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"coupon\" VALUES(?,?)");
        insert.setString(1, coupon.getId());
        insert.setDouble(2, coupon.getAmount());

        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        // tell the object that it's now in the db (so we call update next time not insert)
        coupon.setInDB(true);
        coupon.setDirty(false);
        
        // put Customer object into cache
        Cache.getInstance().put(coupon.getId(),coupon);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create database referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
//    public Coupon getByTransactionLine(TransactionLine txLn) throws DataException {
//        Coupon coupon = null;
//        
//        // get the connection
//        Connection conn = null;
//        try{
//            // retrieve a database connection from the pool
//            conn = ConnectionPool.getInstance().get();
//            
//            // sql the names, phone, and ids
//            PreparedStatement read = conn.prepareStatement(
//                "SELECT * FROM \"coupon\" WHERE \"transactionlineid\" = ?");
//            read.setString(1, txLn.getId());
//            ResultSet rs = read.executeQuery();
//
//            // release the connection
//            conn.commit();
//            ConnectionPool.getInstance().release(conn);
//            
//            // while loop to populate the list from the results
//            if(rs.next()) {
//                if(Cache.getInstance().containsKey(rs.getString("id"))){
//                   coupon = (Coupon)Cache.getInstance().get(rs.getString("id")); 
//                }else{
//                    coupon = new Coupon(rs.getString("id"), txLn);
//                    coupon.setAmount(rs.getDouble("amount"));
//                    coupon.setInDB(true);
//                    coupon.setDirty(false);
//
//                
//
//                // save to the cache
//                Cache.getInstance().put(coupon.getId(), coupon);
//                
//                // Close prepared statement
//                read.close();
//                }
//            }    
//
//        }catch (ConnectionPoolException e){
//            throw new DataException("Could not get a connection to the database.");
//
//        }catch (SQLException e) {
//            // rollback
//            try {
//                conn.rollback();
//                ConnectionPool.getInstance().release(conn);
//            }catch (ConnectionPoolException ce){
//                throw new DataException("There was an error with the connection to the database", ce);
//            }catch (SQLException e2) {
//                throw new DataException("Big error: could not even release the connection", e2);
//            }
//
//            throw new DataException("Could not retrieve payment records form the database",  e);
//        }
//       
//        // return the payment object
//        return coupon;
//    }
    
}
