/*
 * TransactionLineDAO.java
 *
 * Created on March 20, 2007, 12:20 PM
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
import edu.byu.isys413.cbb54.intex2kb.data.DataException;


/**
 * transactionLineDAO
 * @author Cameron
 */
public class TransactionLineDAO {
    
    private static TransactionLineDAO instance = null;
    
    
    /** Creates a new instance of TransactionLineDAO */
    private TransactionLineDAO() {
    }
    
    /**
     * getInstance of TransactionLine
     * @return transactionLineBO
     */
    public static synchronized TransactionLineDAO getInstance() {
        if (instance == null) {
            instance = new TransactionLineDAO();
        }
        return instance;
    }
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * There's no need for two creates because we don't need
     * a connection to create BOs.  We run the insert statement
     * later, when it get's saved for the first time.
     * @param transaction transaction object
     * @param sku id number of product
     * @return transactionline
     * @throws java.lang.Exception Exception
     */
    public TransactionLine create(Transaction transaction, String sku) throws Exception{
        String id = null;
        id = GUID.generate();
        TransactionLine txLn = new TransactionLine(id, transaction);
        txLn.setRevenueSource(RevenueSourceDAO.getInstance().create(sku));
        Cache c = Cache.getInstance();
        c.put(txLn.getId(), txLn);
        return txLn;
    }
   

    /////////////////////////////////////
    ///   READ
    
    /**
     * 
     * This is the public read statement.  It loads an existing record
     * from the database.
     * @param id id of txline
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException DataExceptionn
     * @return txline
     */
    public synchronized TransactionLine read(String id) throws DataException {
        TransactionLine txLn = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            txLn = (TransactionLine)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                txLn = this.read(id, conn);

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
        return txLn;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized TransactionLine read(String id, Connection conn) throws SQLException, DataException {
        TransactionLine txLn = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            txLn = (TransactionLine)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"transactionline\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
               
                txLn = readRecord(rs, conn);

                // save to the cache
                Cache.getInstance().put(id, txLn);
                
                // Close prepared statement
                read.close();
            }
        }
        
        // return the Customer
        return txLn;
    }
    
    synchronized TransactionLine readRecord(ResultSet rs, Connection conn) throws DataException, SQLException{
        TransactionLine txLn = new TransactionLine(rs.getString("id"), TransactionDAO.getInstance().read(rs.getString("transactionid")));
//        txLn.setRevenueSource(new RevenueSource(rs.getString("revenueSourceID")));
        txLn.setRsType(rs.getString("rstype"));
        txLn.setInDB(true);
        txLn.setDirty(false);
        return txLn;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     * @param txLn txln
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException DataException
     */
    public synchronized void save(TransactionLine txLn) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(txLn, conn);
            RevenueSourceDAO.getInstance().save(txLn.getRevenueSource(), conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + txLn.getId(), e);
        }
        
    } // End of first Save()

    
    /** 
     *  This is a package method that is called by the public save (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     *
     *  By having other DAOs call this save method (rather than update or
     *  insert below, each DAO in a chained save (like the MembershipDAO calling
     *  MembershipDAO calling InterestDAO save chain) can independently decide
     *  whether to udpate or insert the BO it's saving.  That's why I made
     *  update and insert private rather than package level -- if you call
     *  them directly from another DAO, this DAO can't decide whether it's
     *  object needs to be inserted or updated.
     */
     synchronized void save(TransactionLine txLn, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (txLn.isDirty()) {
            if (txLn.isInDB()) {
                // We are not supporting update
                //update(txLn, conn);
            }else{
                insert(txLn, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            txLn.setDirty(false);
            
        }
        
        // call save(bo, conn) on any subobjects (like MembershipDAO to MembershipDAO)
        // touch the cache for the object
    }// End of second Save()
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL udpate statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
//    private synchronized void update(TransactionLine txLn, Connection conn) throws SQLException, DataException {
//        // do the update statement
//        PreparedStatement update = conn.prepareStatement(
//            "UPDATE \"membership\"" +
//                "SET \"startDate\" = ?, \"endDate\" = ?, \"creditCard\" = ?, \"ccExpiration\" = ?," +
//                "\"newsletter\" = ? " +
//                "WHERE \"id\" = ?");
//        update.setString(1, mem.getStartDate());
//        update.setString(2, mem.getEndDate());
//        update.setString(3, mem.getCreditCard());
//        update.setString(4, mem.getCcExpiration());
//        update.setBoolean(5, mem.getNewsletter());
//        update.setString(6, mem.getId());
//
//
//        // execute and commit the query
//        update.executeUpdate();
//        conn.commit();
//        
//        // touch the Membership object in cache
//        Cache.getInstance().touch(mem.getId());
//        
//        // Update the InDB and Dirty 
//        mem.setInDB(true);
//        mem.setDirty(false);         
//        
//        // cascade to the Member/Interest objects if they exists for the customer
//        List<String> ilist = mem.getInterests();
//        if(ilist != null){
//            try{
//                MemberInterestDAO.getInstance().create(mem.getId(), ilist, conn);
//            }catch (Exception e){
//                throw new DataException("There was an error saving the member interests", e);
//            }
//            
//        }
//    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(TransactionLine txLn, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"transactionline\" VALUES(?,?,?,?)");
        insert.setString(1, txLn.getId());
        insert.setString(2, txLn.getRevenueSource().getId());
        insert.setString(3, txLn.getTransaction().getId());
        insert.setString(4, txLn.getRsType());
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        txLn.setInDB(true);
        txLn.setDirty(false);
        
        // put Membership object into cache
        Cache.getInstance().put(txLn.getId(),txLn);
        

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because there really isn't any reason to delete a membership. 
    // we simply set the end date.  This preserves the integrety of the database.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
   

    /**
     * Get all txlns associted with a transactions
     * @param transaction transaction object
     * @param conn connection
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException DataException
     * @return txlns
     */
    public List<TransactionLine> getByTransaction(Transaction transaction, Connection conn) throws DataException{
        List<TransactionLine> transLines = new LinkedList<TransactionLine>();
        
        try{

            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"transactionline\"" +
                    "WHERE \"transactionid\" = ? ");
            read.setString(1, transaction.getId());
            ResultSet rs = read.executeQuery();
 
            
            while(rs.next()){
                TransactionLine txLn = new TransactionLine(rs.getString("id"),transaction);
                
                // This will need to be update when RevenueSource is complete
//                txLn.setRevenueSource(new RevenueSource(rs.getString("revenueSourceID")));
                transLines.add(txLn);
            }

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
        
            
        return transLines;
    }
}
