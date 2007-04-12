/*
 * VendorDAO.java
 *
 * Created on April 10, 2007, 10:51 AM
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
 * Handles all the database interactions for the Vendor objects
 * @author Cameron
 */
public class VendorDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static VendorDAO instance = null;
    
    /** Creates a new instance of VendorDAO */
    private VendorDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of VendorDAO
     * @return CustomerDAO
     */
    public static synchronized VendorDAO getInstance() {
        if (instance == null) {
            instance = new VendorDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * This method will create a vendor by first creating the vendor ID(guid) and then calling the constructor
     * @return Vendor
     * @throws java.lang.Exception General exception thrown when there is an error creating the vendor
     */
    public Vendor create() throws Exception{
        String id = null;
        id = GUID.generate();
        Vendor vend = new Vendor(id);
        Cache c = Cache.getInstance();
        c.put(vend.getId(), vend);
        return vend;
    }
    
    /////////////////////////////////////
    ///   READ
    
    /**
     * This is the public read statement.  It loads an existing vendor from the database.
     * @param id String
     * @return Vendor
     * @throws DataException Thrown when there is a problem connecting to the database, or executing the SQL, or there is no record this that id
     */
    public synchronized Vendor read(String id) throws DataException {
        Vendor vendor= null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            vendor = (Vendor)Cache.getInstance().get(id);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // call read with a connection (the other read method in this class)
                vendor = this.read(id, conn);
                
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
        return vendor;
    }
    
    /**
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Vendor read(String id, Connection conn) throws SQLException, DataException {
        Vendor vendor = null;
        
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            vendor = (Vendor)Cache.getInstance().get(id);
        }else{
            
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"vendor\" WHERE \"id\" = ?");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            conn.commit();
            
            
            if (rs.next()) {
                vendor = new Vendor(id);
                vendor.setName(rs.getString("name"));
                vendor.setAddress(rs.getString("address"));
                vendor.setPhone(rs.getString("phone"));
                vendor.setContact(rs.getString("contact"));
                vendor.setInDB(true);
                vendor.setDirty(false);
                
                
                // save to the cache
                Cache.getInstance().put(id, vendor);
                
                // Close prepared statement
                read.close();
                
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Vendor
        return vendor;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update a customer
     * into the database.
     * @throws DataException Thrown when there is an error connection to the database or executing the SQL
     * @param vendor Object to be saved
     */
    public synchronized void save(Vendor vendor) throws DataException {
        
        Connection conn = null;
        
        try {
            
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(vendor, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + vendor.getId(), e);
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
    synchronized void save(Vendor vendor, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (vendor.isDirty()) {
            if (vendor.isInDB()) {
                update(vendor, conn);
                
            }else{
                insert(vendor, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            vendor.setDirty(false);
            
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
    private synchronized void update(Vendor vendor, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"vendor\"" +
                "SET \"name\" = ?, \"address\" = ?, \"phone\" = ?, \"contact\" = ?," +
                "WHERE \"id\" = ?");
        update.setString(1, vendor.getName());
        update.setString(2, vendor.getAddress());
        update.setString(3, vendor.getPhone());
        update.setString(4, vendor.getContact());
        update.setString(5, vendor.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(vendor.getId());

    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Vendor vendor, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"vendor\" VALUES(?,?,?,?,?,?,?,?,?,?)");
        insert.setString(1, vendor.getId());
        insert.setString(2, vendor.getName());
        insert.setString(3, vendor.getAddress());
        insert.setString(4, vendor.getPhone());
        insert.setString(5, vendor.getContact());

        
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        // tell the object that it's now in the db (so we call update next time not insert)
        vendor.setInDB(true);
        vendor.setDirty(false);
        
        // put Customer object into cache
        Cache.getInstance().put(vendor.getId(),vendor);
        
    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create database referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Returns the id, name, address, phone, and contact of all vendors in the database
     * @return List
     * @throws DataException Thrown when there is an error connecting to the database or executing the SQL
     */
    public List<List> getAll() throws DataException {
        List<List> list = new LinkedList<List>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"vendor\" ");
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                List<String> vlist = new LinkedList();
                vlist.add(rs.getString("id"));
                vlist.add(rs.getString("name"));
                vlist.add(rs.getString("address"));
                vlist.add(rs.getString("phone"));
                vlist.add(rs.getString("contact"));
                list.add(vlist);
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
    
    /**
     * Returns all vendors with matching first and last name
     * @throws DataException Thrown when there is an error creating a database connection or executing the SQL
     * @param name String to be searched
     * @return List Vendors
     */
    public List<Vendor> getByName(String name) throws DataException {
        List<Vendor> list = new LinkedList<Vendor>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"vendor\" WHERE \"name\" = ?");
            read.setString(1, name);
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    Vendor vendor= (Vendor)Cache.getInstance().get(rs.getString("id"));
                    list.add(vendor);
                }else{
                    Vendor vendor = new Vendor(rs.getString("id"));
                    vendor.setName(rs.getString("name"));
                    vendor.setAddress(rs.getString("address"));
                    vendor.setPhone(rs.getString("phone"));
                    vendor.setContact(rs.getString("contact"));
                    vendor.setInDB(true);
                    vendor.setDirty(false);
                    list.add(vendor);
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
