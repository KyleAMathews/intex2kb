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
 *
 * @author Cameron
 */
public class TransactionDAO {
       
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static TransactionDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private TransactionDAO() {
    }
    
    public static synchronized TransactionDAO getInstance() {
        if (instance == null) {
            instance = new TransactionDAO();
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
    public Transaction create() throws Exception{
        String id = null;
        id = GUID.generate();
        Transaction trans = new Transaction(id);
        Cache c = Cache.getInstance();
        c.put(trans.getId(), trans);
        return trans;
    }
    
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
     * This is the public read statement.  It loads an existing record
     * from the database.
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
                trans.setPayment(PaymentDAO.getInstance().read(rs.getString("paymentid"),trans, conn));
                if(rs.getString("origtx") == null){
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
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
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
                }
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
            insert.setString(7, "");
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
    
    
    public List<List> getAll() throws DataException {
        List<List> list = new LinkedList<List>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT \"id\", \"fname\", \"lname\", \"phone\" FROM \"customer\" ");
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                List<String> clist = new LinkedList();
                clist.add(rs.getString("id"));
                clist.add(rs.getString("fname"));
                clist.add(rs.getString("lname"));
                clist.add(rs.getString("phone"));
                list.add(clist);
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of customer lists
        return list;
    }
    
    public List<Customer> getByCustmerID(String fname, String lname) throws DataException {
        List<Customer> list = new LinkedList<Customer>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"customer\" WHERE \"fname\" = ? AND \"lname\" = ?");
            read.setString(1, fname);
            read.setString(2, lname);
            ResultSet rs = read.executeQuery();

            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                   Customer cust = (Customer)Cache.getInstance().get(rs.getString("id")); 
                   list.add(cust);
                }else{
                    Customer cust = new Customer(rs.getString("id"));
                    cust.setFname(rs.getString("fname"));
                    cust.setLname(rs.getString("lname"));
                    cust.setAddress1(rs.getString("address1"));
                    cust.setAddress2(rs.getString("address2"));
                    cust.setCity(rs.getString("city"));
                    cust.setState(rs.getString("state"));
                    cust.setZip(rs.getString("zip"));
                    cust.setPhone(rs.getString("phone"));
                    cust.setIsInDB(true);
                    cust.setDirty(false);
                    list.add(cust);
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of customer lists
        return list;
    }
    
    public double getTransactionTotal(Transaction tx){
        List<TransactionLine> txList = tx.getTxLines();
        double total = 0;
        
        for(int i = 0;i < txList.size();i++){
            total += txList.get(i).getRevenueSource().getPrice();
        }
        
        return total;
    }
    
}
