/*
 * StoreDAO.java
 *
 * Created on March 8, 2007, 1:56 PM
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
 * Performs CRUD functions on Store business object
 * @author Cameron
 */
public class StoreDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static StoreDAO instance = null;
    
    /** Creates a new instance of StoreDAO */
    private StoreDAO() {
    }
    
    /**
     * Returns singleton pattern instance of StoreDAO
     */
    public static synchronized StoreDAO getInstance() {
        if (instance == null) {
            instance = new StoreDAO();
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
    public Store create() throws Exception{
        String id = null;
        id = GUID.generate();
        Store store= new Store(id);
        Cache c = Cache.getInstance();
        c.put(store.getId(), store);
        return store;
    }

    /////////////////////////////////////
    ///   READ
    
    /** 
     * This is the public read statement.  It loads an existing record
     * from the database.
     */
    public synchronized Store read(String id) throws DataException {
        Store store = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            store = (Store)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                store = this.read(id, conn);

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
        return store;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Store read(String id, Connection conn) throws SQLException, DataException {
        Store store = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            store = (Store)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"store\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
                store = new Store(id);
                store.setName(rs.getString("name"));
                store.setAddress1(rs.getString("address1"));
                store.setAddress2(rs.getString("address2"));
                store.setCity(rs.getString("city"));
                store.setState(rs.getString("state"));
                store.setZip(rs.getString("zip"));
                store.setPhone(rs.getString("phone"));
                store.setFax(rs.getString("fax"));
                store.setManagerID(rs.getString("managerid"));
                store.setIsInDB(true);
                store.setDirty(false);
                

                // save to the cache
                Cache.getInstance().put(id, store);
                
                // Close prepared statement
                read.close();

            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Store
        return store;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    public synchronized void save(Store store) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(store, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + store.getId(), e);
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
     synchronized void save(Store store, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (store.isDirty()) {
            if (store.isInDB()) {
                update(store, conn);
                
            }else{
                insert(store, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            store.setDirty(false);
            
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
    private synchronized void update(Store store, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"store\"" +
                "SET \"name\" = ?, \"address1\" = ?, \"address2\" = ?," +
                "\"city\" = ?, \"state\" = ?, \"zip\" = ?, \"phone\" = ?," +
                "\"fax\" = ?, \"managerid\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, store.getName());
        update.setString(2, store.getAddress1());
        update.setString(3, store.getAddress2());
        update.setString(4, store.getCity());
        update.setString(5, store.getState());
        update.setString(6, store.getZip());
        update.setString(7, store.getPhone());
        update.setString(8, store.getFax());
        update.setString(9, store.getManagerID());
        update.setString(10, store.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(store.getId());
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Store store, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"store\" VALUES(?,?,?,?,?,?,?,?,?,?)");
        insert.setString(1, store.getId());
        insert.setString(2, store.getName());
        insert.setString(3, store.getAddress1());
        insert.setString(4, store.getAddress2());
        insert.setString(5, store.getCity());
        insert.setString(6, store.getState());
        insert.setString(7, store.getZip());
        insert.setString(8, store.getPhone());
        insert.setString(9, store.getFax());
        insert.setString(10, store.getManagerID());

        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        store.setIsInDB(true);
        store.setDirty(false);
        
        // put Store object into cache
        Cache.getInstance().put(store.getId(),store);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // We are not supporting the delete method in the class becuase it would create database integrity issues
    
//    private synchronized void delete(Store store, Connection conn) throws SQLException, DataException {
//        // do the insert SQL statement
//        PreparedStatement insert = conn.prepareStatement(
//            "DELETE FROM \"store\" WHERE id = ?");
//        insert.setString(1, store.getId());
//        
//        // execute and commit the query
//        insert.executeUpdate();
//        conn.commit();
//        
//        // remove Store object into cache
//        Cache.getInstance().remove(store.getId());
//
//    }
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Returns all of the Stores in the DB
     */
    public List<Store> getAll() throws DataException {
        List<Store> list = new LinkedList<Store>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"store\" ");
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                Store store = new Store(rs.getString("id"));
                store.setName(rs.getString("name"));
                store.setAddress1(rs.getString("address1"));
                store.setAddress2(rs.getString("address2"));
                store.setCity(rs.getString("city"));
                store.setState(rs.getString("state"));
                store.setZip(rs.getString("zip"));
                store.setPhone(rs.getString("phone"));
                store.setFax(rs.getString("fax"));
                store.setManagerID(rs.getString("managerid"));
                list.add(store);
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
       
        // return the list of store lists
        return list;
    }
    
    /**
     * Queries the DB for a store based on store name
     */
    public List<Store> getByName(String name) throws DataException {
        List<Store> list = new LinkedList<Store>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"store\" WHERE \"name\" = ? ");
            read.setString(1, name);
            ResultSet rs = read.executeQuery();

            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                   Store store = (Store)Cache.getInstance().get(rs.getString("id")); 
                   list.add(store);
                }else{
                    Store store = new Store(rs.getString("id"));
                    store.setName(rs.getString("name"));
                    store.setAddress1(rs.getString("address1"));
                    store.setAddress2(rs.getString("address2"));
                    store.setCity(rs.getString("city"));
                    store.setState(rs.getString("state"));
                    store.setZip(rs.getString("zip"));
                    store.setPhone(rs.getString("phone"));
                    store.setFax(rs.getString("fax"));
                    store.setManagerID(rs.getString("managerid"));
                    list.add(store);
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
    
    /**
     * Queries the DB for a store based on store phone number
     */
    public List<Store> getByPhone(String phone) throws DataException {
        List<Store> list = new LinkedList<Store>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"store\" WHERE \"phone\" = ?");
            read.setString(1, phone);
            ResultSet rs = read.executeQuery();


            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                   Store store = (Store)Cache.getInstance().get(rs.getString("id")); 
                   list.add(store);
                }else{
                    Store store = new Store(rs.getString("id"));
                    store.setName(rs.getString("name"));
                    store.setAddress1(rs.getString("address1"));
                    store.setAddress2(rs.getString("address2"));
                    store.setCity(rs.getString("city"));
                    store.setState(rs.getString("state"));
                    store.setZip(rs.getString("zip"));
                    store.setPhone(rs.getString("phone"));
                    store.setFax(rs.getString("fax"));
                    store.setManagerID(rs.getString("managerid"));
                    list.add(store);
                }
                
                //release the connection
                conn.commit();
                ConnectionPool.getInstance().release(conn);
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
       
        // return the list of stores
        return list;
    }
    
}
