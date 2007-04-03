/*
 * CustomerDAO.java
 *
 * Created on February 21, 2007, 4:16 PM
 *
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
/**
 * This DAO handles all interaction with the database for customer objects
 * @author Cameron
 */
public class CustomerDAO {
    
    Customer cust = null;
    
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static CustomerDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private CustomerDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of CustomerDAO
     * @return CustomerDAO
     */
    public static synchronized CustomerDAO getInstance() {
        if (instance == null) {
            instance = new CustomerDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * This method will create a customer by first creating the customer ID(guid) and then calling the constructor
     * @return Customer
     * @throws java.lang.Exception General exception thrown when there is an error creating the customer
     */
    public Customer create() throws Exception{
        String id = null;
        id = GUID.generate();
        Customer cust = new Customer(id);
        Cache c = Cache.getInstance();
        c.put(cust.getId(), cust);
        return cust;
    }

    /////////////////////////////////////
    ///   READ
    
    /**
     * This is the public read statement.  It loads an existing customer from the database.
     * @param id String
     * @return Customer
     * @throws DataException Thrown when there is a problem connecting to the database, or executing the SQL, or there is no record this that id
     */
    public synchronized Customer read(String id) throws DataException {
        Customer cust = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            cust = (Customer)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                cust = this.read(id, conn);

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
        return cust;
    }
    
    /** 
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Customer read(String id, Connection conn) throws SQLException, DataException {
        Customer cust = null;
            
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            cust = (Customer)Cache.getInstance().get(id);
        }else{ 
            // if not in the cache, get a result set from 
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"customer\" WHERE \"id\" = ?");
            read.setString(1, id); 
            ResultSet rs = read.executeQuery();
            conn.commit();
            
        
            if (rs.next()) {
                cust = new Customer(id);
                cust.setFname(rs.getString("fname"));
                cust.setLname(rs.getString("lname"));
                cust.setAddress1(rs.getString("address1"));
                cust.setAddress2(rs.getString("address2"));
                cust.setCity(rs.getString("city"));
                cust.setState(rs.getString("state"));
                cust.setZip(rs.getString("zip"));
                cust.setPhone(rs.getString("phone"));
                cust.setEmail(rs.getString("email"));
                cust.setIsInDB(true);
                cust.setDirty(false);
                

                // save to the cache
                Cache.getInstance().put(id, cust);
                
                // Close prepared statement
                read.close();
                
                Membership membership = MembershipDAO.getInstance().getByCustomerID(cust.getId(), conn);
                
                if(membership != null){
                    cust.setMembership(membership);
                }
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Customer
        return cust;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update a customer
     * into the database.
     * @param cust Customer
     * @throws DataException Thrown when there is an error connection to the database or executing the SQL
     */
    public synchronized void save(Customer cust) throws DataException {
        
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(cust, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + cust.getId(), e);
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
     synchronized void save(Customer cust, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty, 
        // run update or insert
        if (cust.isDirty()) {
            if (cust.isInDB()) {
                update(cust, conn);
                
            }else{
                insert(cust, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            cust.setDirty(false);
            
            if(cust.getMembership() != null){
                MembershipDAO.getInstance().save(cust.getMembership(),conn);
            }
            
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
    private synchronized void update(Customer cust, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
            "UPDATE \"customer\"" +
                "SET \"fname\" = ?, \"lname\" = ?, \"address1\" = ?, \"address2\" = ?," +
                "\"city\" = ?, \"state\" = ?, \"zip\" = ?, \"phone\" = ?, \"email\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, cust.getFname());
        update.setString(2, cust.getLname());
        update.setString(3, cust.getAddress1());
        update.setString(4, cust.getAddress2());
        update.setString(5, cust.getCity());
        update.setString(6, cust.getState());
        update.setString(7, cust.getZip());
        update.setString(8, cust.getPhone());
        update.setString(9, cust.getEmail());
        update.setString(10, cust.getId());
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(cust.getId());
        
        // cascade to the Member object if one exists for the customer
        Membership mem = MembershipDAO.getInstance().getByCustomerID(cust.getId(),conn);
        if(mem != null){
            MembershipDAO.getInstance().save(mem, conn);
        }
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Customer cust, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "INSERT INTO \"customer\" VALUES(?,?,?,?,?,?,?,?,?,?)");
        insert.setString(1, cust.getId());
        insert.setString(2, cust.getFname());
        insert.setString(3, cust.getLname());
        insert.setString(4, cust.getAddress1());
        insert.setString(5, cust.getAddress2());
        insert.setString(6, cust.getCity());
        insert.setString(7, cust.getState());
        insert.setString(8, cust.getZip());
        insert.setString(9, cust.getPhone());
        insert.setString(10, cust.getEmail());

        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        // tell the object that it's now in the db (so we call update next time not insert)
        cust.setIsInDB(true);
        cust.setDirty(false);
        
        // put Customer object into cache
        Cache.getInstance().put(cust.getId(),cust);

    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    // we are not supporting because it would create database referential integrety issues.
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Returns the id, first name, last name, and phone number of all customers in the database
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
    
    /**
     * Returns all customer with matching first and last name
     * @param fname String
     * @param lname String
     * @return List of Customers
     * @throws DataException Thrown when there is an error creating a database connection or executing the SQL
     */
    public List<Customer> getByName(String fname, String lname) throws DataException {
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
    
    /**
     * Returns customers with matching phone number
     * @param phone String
     * @return List of Customers
     * @throws DataException Thrown when there is an error creating a database connection or executing the SQL
     */
    public List<Customer> getByPhone(String phone) throws DataException {
        List<Customer> list = new LinkedList<Customer>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"customer\" WHERE \"phone\" = ?");
            read.setString(1, phone);
            ResultSet rs = read.executeQuery();


            
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
                    cust.setMembership(MembershipDAO.getInstance().getByCustomerID(cust.getId(),conn));
                    cust.setIsInDB(true);
                    cust.setDirty(false);
                    list.add(cust);
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
       
        // return the list of customer lists
        return list;
    }
    
    /**
     * Used for holding a customer object
     * @param cust Customer
     */
    public void setHoldCustomer(Customer cust){
        this.cust = cust;
    }
    
    /**
     * Returns the held customer object
     * @return Customer
     */
    public Customer getHoldCustomer(){
        return cust;
    }
}
