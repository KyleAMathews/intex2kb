/*
 * MembershipDAO.java
 *
 * Created on February 21, 2007, 4:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.*;
import java.util.List;


/**
 * This DAO controlls all interactions between the Membership objects and the database
 * @author Cameron
 */
public class MembershipDAO {
    
    Membership mem = null;
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static MembershipDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private MembershipDAO() {
    }
    
    /**
     * Singleton pattern to allow only one MembershipDAO
     * @return MembershipDAO
     */
    public static synchronized MembershipDAO getInstance() {
        if (instance == null) {
            instance = new MembershipDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * Creates a new membership by first creating an ID and then passing
     * it to the membership constructor
     * @param custId String
     * @return Membership
     * @throws java.lang.Exception Thrown when there is an error creating the membership
     */
    public Membership create(String custId) throws Exception{
        String id = null;
        id = GUID.generate();
        Membership mem = new Membership(id, custId);
        Cache c = Cache.getInstance();
        c.put(mem.getId(), mem);
        mem.setCustomer(CustomerDAO.getInstance().read(custId));
        return mem;
    }
    
    /////////////////////////////////////
    ///   READ
    
    /**
     *
     * This is the public read statement.  It loads an existing membership
     * from the database.
     * @param id String
     * @return Membership
     * @throws DataException Thrown when there is an error getting a database connection, executing the SQL, or there isn't a record with the associated ID
     */
    public synchronized Membership read(String id) throws DataException {
        Membership mem = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            mem = (Membership)Cache.getInstance().get(id);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // call read with a connection (the other read method in this class)
                mem = this.read(id, conn);
                
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
        return mem;
    }
    
    /**
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Membership read(String id, Connection conn) throws SQLException, DataException {
        Membership mem = null;
        
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            mem = (Membership)Cache.getInstance().get(id);
        }else{
            // if not in the cache, get a result set from
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"membership\" WHERE \"id\" = ?");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            conn.commit();
            
            
            if (rs.next()) {
                
                mem = readRecord(rs,conn);
                
                // save to the cache
                Cache.getInstance().put(id, mem);
                
                // Close prepared statement
                read.close();
            }
        }
        
        // return the Member
        return mem;
    }
    
    synchronized Membership readRecord(ResultSet rs, Connection conn) throws DataException, SQLException{
        Membership mem = new Membership(rs.getString("id"), rs.getString("custId"));
        mem.setStartDate(rs.getString("startDate"));
        mem.setEndDate(rs.getString("endDate"));
        mem.setCreditCard(rs.getString("creditCard"));
        mem.setCcExpiration(rs.getString("ccExpiration"));
        mem.setNewsletter(rs.getBoolean("newsletter"));
        mem.setBackupSize(rs.getDouble("backupSize"));
        mem.setBackupExpDate(rs.getLong("backupExpDate"));
        mem.setInterests(MemberInterestDAO.getInstance().read(mem.getId(),conn));
        mem.setInDB(true);
        mem.setDirty(false);
        mem.setCustomer(CustomerDAO.getInstance().read(mem.getCustId(),conn));
        return mem;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * Saves a membership to the database
     * @param mem Membership
     * @throws DataException Thrown when there is an error getting a database connection or executing the SQL
     */
    public synchronized void save(Membership mem) throws DataException {
        
        Connection conn = null;
        
        try {
            
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(mem, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + mem.getId(), e);
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
    synchronized void save(Membership mem, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (mem.isDirty()) {
            if (mem.isInDB()) {
                update(mem, conn);
                
            }else{
                insert(mem, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            mem.setDirty(false);
            
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
    private synchronized void update(Membership mem, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"membership\"" +
                "SET \"startDate\" = ?, \"endDate\" = ?, \"creditCard\" = ?, \"ccExpiration\" = ?," +
                "\"newsletter\" = ?, \"backupSize\" = ?, \"backupExpDate\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, mem.getStartDate());
        update.setString(2, mem.getEndDate());
        update.setString(3, mem.getCreditCard());
        update.setString(4, mem.getCcExpiration());
        update.setBoolean(5, mem.getNewsletter());
        update.setString(6, Double.toString(mem.getBackupSize()));
        update.setString(7, Long.toString(mem.getBackupExpDate()));
        update.setString(8, mem.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Membership object in cache
        Cache.getInstance().touch(mem.getId());
        
        // Update the InDB and Dirty
        mem.setInDB(true);
        mem.setDirty(false);
        
        // cascade to the Member/Interest objects if they exists for the customer
        List<String> ilist = mem.getInterests();
        if(ilist != null){
            try{
                MemberInterestDAO.getInstance().create(mem.getId(), ilist, conn);
            }catch (Exception e){
                throw new DataException("There was an error saving the member interests", e);
            }
            
        }
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Membership mem, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"membership\" VALUES(?,?,?,?,?,?,?,?,?)");
        insert.setString(1, mem.getId());
        insert.setString(2, mem.getCustId());
        insert.setString(3, mem.getStartDate());
        insert.setString(4, mem.getEndDate());
        insert.setString(5, mem.getCreditCard());
        insert.setString(6, mem.getCcExpiration());
        insert.setBoolean(7, mem.getNewsletter());
        insert.setString(8, Double.toString(mem.getBackupSize()));
        insert.setString(9, Long.toString(mem.getBackupExpDate()));
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        mem.setInDB(true);
        mem.setDirty(false);
        
        // put Membership object into cache
        Cache.getInstance().put(mem.getId(),mem);
        
        mem.setCustomer(CustomerDAO.getInstance().read(mem.getCustId()));
        
    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because there really isn't any reason to delete a membership.
    // we simply set the end date.  This preserves the integrety of the database.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Returns the membership associated with a certain customer
     * @param id String
     * @param conn Database Connection
     * @return Customer
     * @throws edu.byu.isys413.cbb54.intex2.data.DataException Thrown when there is an error getting a database connection, executing the SQL, or there is not a membership associated with the customer
     */
    public Membership getByCustomerID(String id, Connection conn) throws DataException {
        Membership mem = null;
        
        
        try{
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"membership\" WHERE \"custID\" = ?");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    mem = (Membership)Cache.getInstance().get(rs.getString("id"));
                }else{
                    mem = readRecord(rs, conn);
                }
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
            
            throw new DataException("Could not retrieve membership records form the database",  e);
        }
        
        // return the list of customer lists
        return mem;
    }
    
    /**
     * Used to hold a membership temporarily
     * @param mem Membership
     */
    public void setHoldMembership(Membership mem){
        this.mem = mem;
    }
    
    /**
     * Returns the held membership
     * @return Membership
     */
    public Membership getHoldMembership(){
        return mem;
    }
}
