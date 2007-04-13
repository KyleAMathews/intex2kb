/*
 * TransactionDAO.java
 *
 * Created on March 8, 2007, 1:55 PM
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
 * Perform CRUD functions for transaction objects
 * @author Cameron
 */
public class TransactionDAO {
       
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static TransactionDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private TransactionDAO() {
    }
    
    /**
     * Return an instance of TransactionDAO
     * @return TransactionDAO
     */
    public static synchronized TransactionDAO getInstance() {
        if (instance == null) {
            instance = new TransactionDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * Creates a new Transaction by first creating a GUID and then passing it to
     * the Transaction create method
     * @throws java.lang.Exception Thrown when there is an error creating the GUID
     * @return Transaction
     */
    public Transaction create() throws Exception{
        String id = null;
        id = GUID.generate();
        Transaction trans = new Transaction(id);
        Cache c = Cache.getInstance();
        c.put(trans.getId(), trans);
        return trans;
    }
    
    /**
     * Create a transaction based on a previous transaction (type return)
     * @param orig Transaction
     * @throws java.lang.Exception Thrown when there is an error creating a GUID
     * @return Transaction
     */
    public Transaction create(Transaction orig) throws Exception{
        String id = null;
        id = GUID.generate();
        Transaction trans = new Transaction(id, orig);
        Cache c = Cache.getInstance();
        c.put(trans.getId(), trans);
        return trans;
    }

    /////////////////////////////////////
    ///   READ
    
    /**
     * Returns the Transaction with the matching id
     * @param id String
     * @return Transaction
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is an error getting a database connection, 
     * executing the SQL, or when there is no entry in the database
     * with that id
     */
    public synchronized Transaction read(String id) throws DataException {
        Transaction trans = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            trans = (Transaction)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                trans = this.read(id, conn);

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
        return trans;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Transaction read(String id, Connection conn) throws SQLException, DataException {
        Transaction trans = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            trans = (Transaction)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"transaction\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
                trans = new Transaction(id);
                trans.setType(rs.getString("type"));
                trans.setStatus(rs.getString("status"));    
                trans.setInDB(true);
                trans.setDirty(false);
                
                
                // save to the cache
                Cache.getInstance().put(id, trans);
                
                
                trans.setCustomer(CustomerDAO.getInstance().read(rs.getString("custid"), conn));
                trans.setEmployee(EmployeeDAO.getInstance().read(rs.getString("empid"), conn));
                trans.setStore(StoreDAO.getInstance().read(rs.getString("storeid"), conn));
                //trans.setPayment(PaymentDAO.getInstance().read(rs.getString("paymentid"),trans, conn));
                if(rs.getString("origtx").matches("na") == true){
                    trans.setOrig(null);
                }else{
                    trans.setOrig(TransactionDAO.getInstance().read(rs.getString("origtx"), conn));
                }
                trans.setTxLines(TransactionLineDAO.getInstance().getByTransaction(trans, conn));


                // Close prepared statement
                read.close();

                
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return trans;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * Saves the Transaction be determining if its in the database, inserting it or
     * updating it accordingly.
     * @param tx Transaction to save
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is an error retrieving the database connection,
     * executing the SQL
     */
    public synchronized void save(Transaction tx) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(tx, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + tx.getId(), e);
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
     synchronized void save(Transaction tx, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (tx.isDirty()) {
            if (tx.isInDB()) {
                update(tx, conn);
                
            }else{
                insert(tx, conn);
                
                List txLines = tx.getTxLines();
                for(int i = 0; txLines.size() > i; i++){
                    TransactionLineDAO.getInstance().save((TransactionLine)txLines.get(i), conn);
                    TransactionLine txln = (TransactionLine)txLines.get(i);
                    RevenueSourceDAO.getInstance().save(txln.getRevenueSource(),conn);
                }
                
                PaymentDAO.getInstance().save(tx.getPayment(),conn);
                
            }
            
            // set the dirty flag to false now that we've saved it
            tx.setDirty(false);

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
    private synchronized void update(Transaction tx, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"transaction\"" +
                "SET \"status\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, tx.getStatus());
        update.setString(2, tx.getId());

        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(tx.getId());
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Transaction tx, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"transaction\" VALUES(?,?,?,?,?,?,?)");
        insert.setString(1, tx.getId());
        insert.setString(2, tx.getType());
        insert.setString(3, tx.getStatus());
        insert.setString(4, tx.getCustomer().getId());
        insert.setString(5, tx.getEmployee().getId());
        insert.setString(6, tx.getStore().getId());
        if(tx.getOrig() == null){
            insert.setString(7, "na");
        }else{
            insert.setString(7, tx.getOrig().getId());
        }
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        // tell the object that it's now in the db (so we call update next time not insert)
        tx.setInDB(true);
        tx.setDirty(false);
        
        // put Transaction object into cache
        Cache.getInstance().put(tx.getId(), tx);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create database referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Return the total of the Transaction
     * @param tx Transaction
     * @return Double Transaction total
     */
    public double getTransactionTotal(Transaction tx){
        List<TransactionLine> txList = tx.getTxLines();
        double total = 0;
        
        for(int i = 0;i < txList.size();i++){
            total += txList.get(i).getRevenueSource().getPrice();
        }
        
        return total;
    }
    
}
