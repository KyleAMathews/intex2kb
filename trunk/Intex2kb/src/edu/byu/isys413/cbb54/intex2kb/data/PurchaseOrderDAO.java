/*
 * PurchaseOrderDAO.java
 *
 * Created on April 10, 2007, 1:43 PM
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
public class PurchaseOrderDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static PurchaseOrderDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private PurchaseOrderDAO() {
    }
    
    /**
     * Return an instance of Transaction
     */
    public static synchronized PurchaseOrderDAO getInstance() {
        if (instance == null) {
            instance = new PurchaseOrderDAO();
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
    public PurchaseOrder create(Vendor vendor) throws Exception{
        String id = null;
        id = GUID.generate();
        PurchaseOrder po = new PurchaseOrder(id, vendor);
        Cache c = Cache.getInstance();
        c.put(po.getId(), po);
        return po;
    }
    
    
    /////////////////////////////////////
    ///   READ
    
    /**
     * This is the public read statement.  It loads an existing record
     * from the database.
     */
    public synchronized PurchaseOrder read(String id) throws DataException {
        PurchaseOrder po = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            po = (PurchaseOrder)Cache.getInstance().get(id);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // call read with a connection (the other read method in this class)
                po = this.read(id, conn);
                
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
        return po;
    }
    
    /**
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized PurchaseOrder read(String id, Connection conn) throws SQLException, DataException {
        PurchaseOrder po = null;
        
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            po = (PurchaseOrder)Cache.getInstance().get(id);
        }else{
            // if not in the cache, get a result set from
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"purchaseorder\" WHERE \"id\" = ?");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            conn.commit();
            
            
            if (rs.next()) {
                po = new PurchaseOrder(id, VendorDAO.getInstance().read(rs.getString("vendorid")));
                po.setDate(rs.getLong("date"));
                po.setInDB(true);
                po.setDirty(false);
                
                
                // save to the cache
                Cache.getInstance().put(id, po);
                
                po.setOrderLines(OrderLineDAO.getInstance().getByPurchaseOrder(po, conn));
                
                po.setStore(StoreDAO.getInstance().read(rs.getString("storeid")));
                
                
                // Close prepared statement
                read.close();
                
                
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return po;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(PurchaseOrder po) throws DataException {
        
        Connection conn = null;
        
        try {
            
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(po, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + po.getId(), e);
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
    synchronized void save(PurchaseOrder po, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (po.isDirty()) {
            if (po.isInDB()) {
                update(po, conn);
            }else{
                insert(po, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            po.setDirty(false);
            
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
    private synchronized void update(PurchaseOrder po, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"purchaseorder\"" +
                "SET \"date\" = ? AND \"vendorid\" = ? AND \"storeid\" = ? " +
                "WHERE \"id\" = ?");
        update.setLong(1, po.getDate());
        update.setString(2, po.getVendor().getId());
        update.setString(3, po.getStore().getId());
        update.setString(4, po.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(po.getId());
        
        List poLines = po.getOrderLines();
        for(int i = 0; poLines.size() > i; i++){
            OrderLineDAO.getInstance().save((OrderLine)poLines.get(i), conn);
        }
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(PurchaseOrder po, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"purchaseorder\" VALUES(?,?,?,?)");
        insert.setString(1, po.getId());
        insert.setLong(2, po.getDate());
        insert.setString(3, po.getVendor().getId());
        insert.setString(4, po.getStore().getId());
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        // tell the object that it's now in the db (so we call update next time not insert)
        po.setInDB(true);
        po.setDirty(false);
        
        // put Transaction object into cache
        Cache.getInstance().put(po.getId(), po);
        
        List poLines = po.getOrderLines();
        for(int i = 0; poLines.size() > i; i++){
            OrderLineDAO.getInstance().save((OrderLine)poLines.get(i), conn);
        }
        
    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create database referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    /**
     * Get all transactions for a given customer
     */
    public List<PurchaseOrder> getByVendorID(String id) throws DataException {
        List<PurchaseOrder> list = new LinkedList<PurchaseOrder>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"purchaseorder\" WHERE \"vendorid\" ");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    PurchaseOrder po = (PurchaseOrder)Cache.getInstance().get(rs.getString("id"));
                    list.add(po);
                }else{
                    PurchaseOrder po = new PurchaseOrder(id, VendorDAO.getInstance().read(rs.getString("vendorid")));
                    po.setDate(rs.getLong("date"));
                    po.setInDB(true);
                    po.setDirty(false);
                    po.setInDB(true);
                    po.setDirty(false);
                    list.add(po);
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
    
}
