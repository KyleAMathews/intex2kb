/*
 * OrderLineDAO.java
 *
 * Created on April 10, 2007, 2:20 PM
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
public class OrderLineDAO {
    
    private static OrderLineDAO instance = null;
    
    
    /** Creates a new instance of TransactionLineDAO */
    private OrderLineDAO() {
    }
    
    /**
     * getInstance of TransactionLine
     * @return transactionLineBO
     */
    public static synchronized OrderLineDAO getInstance() {
        if (instance == null) {
            instance = new OrderLineDAO();
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
    public OrderLine create(PurchaseOrder po) throws Exception{
        String id = null;
        id = GUID.generate();
        OrderLine ol = new OrderLine(id, po);
        Cache c = Cache.getInstance();
        c.put(ol.getId(), ol);
        return ol;
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
    public synchronized OrderLine read(String id) throws DataException, Exception {
        OrderLine ol = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            ol = (OrderLine)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                ol = this.read(id, conn);

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
        return ol;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized OrderLine read(String id, Connection conn) throws SQLException, DataException, Exception {
        OrderLine ol = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            ol = (OrderLine)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"orderline\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
               
                ol = readRecord(rs, conn);

                // save to the cache
                Cache.getInstance().put(id, ol);
                
                // Close prepared statement
                read.close();
            }
        }
        
        // return the Customer
        return ol;
    }
    
    synchronized OrderLine readRecord(ResultSet rs, Connection conn) throws DataException, SQLException, Exception{
        OrderLine ol = new OrderLine(rs.getString("id"), PurchaseOrderDAO.getInstance().read(rs.getString("purchaseorderid")));
//        txLn.setRevenueSource(new RevenueSource(rs.getString("revenueSourceID")));
        ol.setProduct(ConceptualDAO.getInstance().read(rs.getString("productid")));
        ol.setQuantity(rs.getInt("quantity"));
        ol.setInDB(true);
        ol.setDirty(false);
        return ol;
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
    public synchronized void save(OrderLine ol) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(ol, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + ol.getId(), e);
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
     synchronized void save(OrderLine ol, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (ol.isDirty()) {
            if (ol.isInDB()) {
                update(ol, conn);
            }else{
                insert(ol, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            ol.setDirty(false);
            
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
    private synchronized void update(OrderLine ol, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"orderline\"" +
                "SET \"purchaseorderid\" = ?, \"productid\" = ?, \"quantity\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, ol.getPurchaseOrder().getId());
        update.setString(2, ol.getProduct().getId());
        update.setInt(3, ol.getQuantity());
        update.setString(4, ol.getId());



        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Membership object in cache
        Cache.getInstance().touch(ol.getId());
        
        // Update the InDB and Dirty 
        ol.setInDB(true);
        ol.setDirty(false);         
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(OrderLine ol, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"orderline\" VALUES(?,?,?,?)");
        insert.setString(1, ol.getId());
        insert.setString(2, ol.getPurchaseOrder().getId());
        insert.setString(3, ol.getProduct().getId());
        insert.setInt(4, ol.getQuantity());
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        ol.setInDB(true);
        ol.setDirty(false);
        
        // put Membership object into cache
        Cache.getInstance().put(ol.getId(),ol);
        

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
    public List<OrderLine> getByPurchaseOrder(PurchaseOrder po, Connection conn) throws DataException{
        List<OrderLine> orderLines = new LinkedList<OrderLine>();
        
        try{

            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"orderline\"" +
                    "WHERE \"purchaseorderid\" = ? ");
            read.setString(1, po.getId());
            ResultSet rs = read.executeQuery();
 
            
            while(rs.next()){
                OrderLine ol = new OrderLine(rs.getString("id"), po);
                orderLines.add(ol);
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
        
            
        return orderLines;
    }
    
}
