/*
 * InterestDAO.java
 *
 * Created on February 26, 2007, 6:16 PM
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
 * This DAO handles all interactions between the interest objects and that database
 * @author Cameron
 */
public class InterestDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static InterestDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private InterestDAO() {
    }
    
    /**
     * Singleton pattern to ensure only one InterestDAO
     * @return InterestDAO
     */
    public static synchronized InterestDAO getInstance() {
        if (instance == null) {
            instance = new InterestDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * Creates a new Interest by first generating a guid and then passing it
     * to the Interest constructor
     * @return Interest
     * @throws java.lang.Exception Thrown when there is an error generating the interest
     */
    public Interest create() throws Exception{
        String id = null;
        id = GUID.generate();
        Interest interest = new Interest(id);
        Cache c = Cache.getInstance();
        c.put(interest.getId(), interest);
        return interest;
    }

    /////////////////////////////////////
    ///   READ
    
    /**
     * 
     * This is the public read statement.  It loads an existing Interest
     * from the database.
     * @param id String
     * @return Interest
     * @throws DataException Thrown when there is an error getting a database connection, executing the SQL, or there is no interest with the associated ID
     */
    public synchronized Interest read(String id) throws DataException {
        Interest interest = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            interest = (Interest)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                interest = this.read(id, conn);

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
        return interest;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Interest read(String id, Connection conn) throws SQLException, DataException {
        Interest interest = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            interest = (Interest)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"interest\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
               
                interest = readRecord(rs);
                
                // if this were customer, this is what we would do
//                Membership m = MembershipDAO.getInstance().getByCustomerID(cust.getId(), conn);
                // can't do the next one because I don't have the member ID here!'
                // MembershipBO m = MembershipDAO.getInstance().read(memberid, conn);
//                cust.setMembership(m);
                
                // continue to get any subobjects (for example, if this was the MembershipDAO,
                // we'd need to call the MembershipDAO to load the membership record.


                // save to the cache
                Cache.getInstance().put(id, interest);
                
                // Close prepared statement
                read.close();
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return interest;
    }
    
    synchronized Interest readRecord(ResultSet rs) throws SQLException{
        Interest interest = new Interest(rs.getString("id"));
        interest.setTitle(rs.getString("title"));
        interest.setDescription(rs.getString("description"));
        interest.setInDB(true);
        interest.setDirty(false);
        
        return interest;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * Saves an Interest to the database
     * @param interest Interest
     * @throws DataException Thrown when there is an error getting a database connection or executing the SQL
     */
    public synchronized void save(Interest interest) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(interest, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + interest.getId(), e);
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
     synchronized void save(Interest interest, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (interest.isDirty()) {
            if (interest.isInDB()) {
                update(interest, conn);
                
            }else{
                insert(interest, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            interest.setDirty(false);
            
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
    private synchronized void update(Interest interest, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"interest\"" +
                "SET \"title\" = ?, \"description\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, interest.getTitle());
        update.setString(2, interest.getTitle());
        update.setString(3, interest.getId());

        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Interest object in cache
        Cache.getInstance().touch(interest.getId());
        
        // Update the InDB and Dirty 
        interest.setInDB(true);
        interest.setDirty(false);         
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Interest interest, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"interest\" VALUES(?,?,?)");
        insert.setString(1, interest.getId());
        insert.setString(2, interest.getTitle());
        insert.setString(3, interest.getDescription());

        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        interest.setInDB(true);
        interest.setDirty(false);
        
        // put Membership object into cache
        Cache.getInstance().put(interest.getId(),interest);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create a referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Returns all interest in the database
     * @return List of Interests
     * @throws DataException Thrown when there is an error getting a database connection or executing the SQL
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
                "SELECT \"id\", \"title\", \"description\" FROM \"interest\" ");
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                List<String> clist = new LinkedList();
                clist.add(rs.getString("id"));
                clist.add(rs.getString("title"));
                clist.add(rs.getString("description"));
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
    
}
