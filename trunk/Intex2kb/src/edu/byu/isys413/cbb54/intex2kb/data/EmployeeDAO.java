/*
 * EmployeeDAO.java
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
 * This DAO handles all interactions between the employee object and the database
 * @author Cameron
 */
public class EmployeeDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static EmployeeDAO instance = null;
    
    /** Creates a new instance of StoreDAO */
    private EmployeeDAO() {
    }
    
    /**
     * Singleton pattern to ensure only one instance of EmployeeDAO
     * @return EmployeeDAO
     */
    public static synchronized EmployeeDAO getInstance() {
        if (instance == null) {
            instance = new EmployeeDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * This methode will create a new Employee object by first creating
     * an ID(guid) and then passing it to the Employee constructor
     * @return Employee
     * @throws java.lang.Exception Thrown when there is an error creating the id or the employee object
     */
    public Employee create() throws Exception{
        String id = null;
        id = GUID.generate();
        Employee employee= new Employee(id);
        Cache c = Cache.getInstance();
        c.put(employee.getId(), employee);
        return employee;
    }
    
    /////////////////////////////////////
    ///   READ
    
    /**
     * This is the public read statement.  It loads an existing Employee object
     * from the database.
     * @param id String
     * @return Employee
     * @throws DataException Thrown when there was an error making a database connection, executing the SQL, or when there isn't an Employee with that ID
     */
    public synchronized Employee read(String id) throws DataException {
        Employee employee = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            employee = (Employee)Cache.getInstance().get(id);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // call read with a connection (the other read method in this class)
                employee = this.read(id, conn);
                
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
        return employee;
    }
    
    /**
     *  This is a package method that is called by the public read (above) or
     *  by another DAO.  Either way we already have a connection to the database
     *  to use.  The user (controller) code never calls this one directly, since
     *  it can't know about Connection objects or SQLExceptions.
     */
    synchronized Employee read(String id, Connection conn) throws SQLException, DataException {
        Employee employee = null;
        
        // check again if the id is in the cache, and if so,
        // just get it from the cache.  we need to check again
        // because this method might be called directly from
        // another DAO rather than from read above.
        if(Cache.getInstance().containsKey(id)){
            employee = (Employee)Cache.getInstance().get(id);
        }else{
            // if not in the cache, get a result set from
            // a SELECT * FROM table WHERE id=guid
            
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"employee\" WHERE \"id\" = ?");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            conn.commit();
            
            
            if (rs.next()) {
                employee = new Employee(id);
                employee.setFname(rs.getString("fname"));
                employee.setLname(rs.getString("lname"));
                employee.setAddress1(rs.getString("address1"));
                employee.setAddress2(rs.getString("address2"));
                employee.setCity(rs.getString("city"));
                employee.setState(rs.getString("state"));
                employee.setZip(rs.getString("zip"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setSsNumber(rs.getString("ssNumber"));
                employee.setHireDate(rs.getString("hireDate"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStoreID(rs.getString("storeid"));
                employee.setInDB(true);
                employee.setDirty(false);
                
                
                // save to the cache
                Cache.getInstance().put(id, employee);
                
                // Close prepared statement
                read.close();
                
            }else{
                throw new DataException("Object was not found in the database.");
            }
        }
        
        // return the Store
        return employee;
    }
    
    //////////////////////////////////
    ///   UPDATE
    
    /**
     * Saves an Employee object to the database
     * @param employee Employee
     * @throws DataException Thrown when there was an error making a database connection or executing the SQL
     */
    public synchronized void save(Employee employee) throws DataException {
        
        Connection conn = null;
        
        try {
            
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            save(employee, conn);
            
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
            
            throw new DataException("Could not retrieve record for id=" + employee.getId(), e);
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
    synchronized void save(Employee employee, Connection conn) throws SQLException, DataException {
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (employee.isDirty()) {
            if (employee.isInDB()) {
                update(employee, conn);
                
            }else{
                insert(employee, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            employee.setDirty(false);
            
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
    private synchronized void update(Employee employee, Connection conn) throws SQLException, DataException {
        // do the update statement
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"store\"" +
                "SET \"fname\" = ?, \"lname\" = ?, \"address1\" = ?, \"address2\" = ?," +
                "\"city\" = ?, \"state\" = ?, \"zip\" = ?, \"phone\" = ?" +
                "\"email\" = ?, \"ssnumber\" = ?, \"hiredate\" = ?, \"salary\" = ?, \"storeid\" = ?" +
                "WHERE \"id\" = ?");
        update.setString(1, employee.getFname());
        update.setString(2, employee.getLname());
        update.setString(3, employee.getAddress1());
        update.setString(4, employee.getAddress2());
        update.setString(5, employee.getCity());
        update.setString(6, employee.getState());
        update.setString(7, employee.getZip());
        update.setString(8, employee.getPhone());
        update.setString(9, employee.getEmail());
        update.setString(10, employee.getSsNumber());
        update.setString(11, employee.getHireDate());
        update.setDouble(12, employee.getSalary());
        update.setString(13, employee.getStoreID());
        update.setString(14, employee.getId());
        
        
        // execute and commit the query
        update.executeUpdate();
        conn.commit();
        
        // touch the Customer object in cache
        Cache.getInstance().touch(employee.getId());
        
    }
    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(Employee employee, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"employee\" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        insert.setString(1, employee.getId());
        insert.setString(2, employee.getFname());
        insert.setString(3, employee.getLname());
        insert.setString(4, employee.getAddress1());
        insert.setString(5, employee.getAddress2());
        insert.setString(6, employee.getCity());
        insert.setString(7, employee.getState());
        insert.setString(8, employee.getZip());
        insert.setString(9, employee.getPhone());
        insert.setString(10, employee.getEmail());
        insert.setString(11, employee.getSsNumber());
        insert.setString(12, employee.getHireDate());
        insert.setDouble(13, employee.getSalary());
        insert.setString(14, employee.getStoreID());
        
        
        
        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
        
        // tell the object that it's now in the db (so we call update next time not insert)
        employee.setInDB(true);
        employee.setDirty(false);
        
        // put Store object into cache
        Cache.getInstance().put(employee.getId(),employee);
        
    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    //We are not supporting the delete function of an employee, this would cause database integrity problems
    
//    private synchronized void delete(Employee employee, Connection conn) throws SQLException, DataException {
//        // do the insert SQL statement
//        PreparedStatement insert = conn.prepareStatement(
//            "DELETE FROM \"employee\" WHERE id = ?");
//        insert.setString(1, employee.getId());
//
//        // execute and commit the query
//        insert.executeUpdate();
//        conn.commit();
//
//        // remove Store object into cache
//        Cache.getInstance().remove(employee.getId());
//
//    }
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    /**
     * Returns a list of all Employees
     * @return List of Employees
     * @throws DataException Thrown when there was an error making a database connection or executing the SQL
     */
    public List<Employee> getAll() throws DataException {
        List<Employee> list = new LinkedList<Employee>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"employee\" ");
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                Employee employee= new Employee(rs.getString("id"));
                employee.setFname(rs.getString("fname"));
                employee.setLname(rs.getString("lname"));
                employee.setAddress1(rs.getString("address1"));
                employee.setAddress2(rs.getString("address2"));
                employee.setCity(rs.getString("city"));
                employee.setState(rs.getString("state"));
                employee.setZip(rs.getString("zip"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setSsNumber(rs.getString("ssnumber"));
                employee.setHireDate(rs.getString("hiredate"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStoreID(rs.getString("storeid"));
                list.add(employee);
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
            
            throw new DataException("Could not retrieve employee records form the database",  e);
        }
        
        // return the list of store lists
        return list;
    }
    
    /**
     * Returns a list of Employees with a name matching the string
     * @param name String
     * @return List of Employees
     * @throws DataException Thrown when there was an error making a database connection, executing the SQL, or when there isn't an Employee with a matching name
     */
    public List<Employee> getByName(String name) throws DataException {
        List<Employee> list = new LinkedList<Employee>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"employee\" WHERE \"lname\" = ? ");
            read.setString(1, name);
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();
            ConnectionPool.getInstance().release(conn);
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    Employee employee= (Employee)Cache.getInstance().get(rs.getString("id"));
                    list.add(employee);
                }else{
                    Employee employee= new Employee(rs.getString("id"));
                    employee.setFname(rs.getString("fname"));
                    employee.setLname(rs.getString("lname"));
                    employee.setAddress1(rs.getString("address1"));
                    employee.setAddress2(rs.getString("address2"));
                    employee.setCity(rs.getString("city"));
                    employee.setState(rs.getString("state"));
                    employee.setZip(rs.getString("zip"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setEmail(rs.getString("email"));
                    employee.setSsNumber(rs.getString("ssnumber"));
                    employee.setHireDate(rs.getString("hiredate"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setStoreID(rs.getString("storeid"));
                    list.add(employee);
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
            
            throw new DataException("Could not retrieve employee records form the database",  e);
        }
        
        // return the list of customer lists
        return list;
    }
    
    /**
     * Returns an Employee with the matching social security number
     * @param ssNumber String
     * @return Employee
     * @throws DataException Thrown when there was an error making a database connection, executing the SQL, or when there isn't an Employee with that Social Security Number
     */
    public List<Employee> getBySSNumber(String ssNumber) throws DataException {
        List<Employee> list = new LinkedList<Employee>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"employee\" WHERE \"ssnumber\" = ?");
            read.setString(1, ssNumber);
            ResultSet rs = read.executeQuery();
            
            
            
            // while loop to populate the list from the results
            while(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    Employee employee = (Employee)Cache.getInstance().get(rs.getString("id"));
                    list.add(employee);
                }else{
                    Employee employee= new Employee(rs.getString("id"));
                    employee.setFname(rs.getString("fname"));
                    employee.setLname(rs.getString("lname"));
                    employee.setAddress1(rs.getString("address1"));
                    employee.setAddress2(rs.getString("address2"));
                    employee.setCity(rs.getString("city"));
                    employee.setState(rs.getString("state"));
                    employee.setZip(rs.getString("zip"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setEmail(rs.getString("email"));
                    employee.setSsNumber(rs.getString("ssnumber"));
                    employee.setHireDate(rs.getString("hiredate"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setStoreID(rs.getString("storeid"));
                    list.add(employee);
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
            
            throw new DataException("Could not retrieve employee records form the database",  e);
        }
        
        // return the list of stores
        return list;
    }
    /**
     * Returns an Employee with the matching social security number
     * @param ssNumber String
     * @return Employee
     * @throws DataException Thrown when there was an error making a database connection, executing the SQL, or when there isn't an Employee with that Social Security Number
     */
    public Employee getEmail(String email) throws DataException {
        Employee employee = null;
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"employee\" WHERE \"email\" = ?");
            read.setString(1, email);
            ResultSet rs = read.executeQuery();
            
            
            
            // while loop to populate the list from the results
            if(rs.next()) {
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    employee = (Employee)Cache.getInstance().get(rs.getString("id"));
                }else{
                    employee = new Employee(rs.getString("id"));
                    employee.setFname(rs.getString("fname"));
                    employee.setLname(rs.getString("lname"));
                    employee.setAddress1(rs.getString("address1"));
                    employee.setAddress2(rs.getString("address2"));
                    employee.setCity(rs.getString("city"));
                    employee.setState(rs.getString("state"));
                    employee.setZip(rs.getString("zip"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setEmail(rs.getString("email"));
                    employee.setSsNumber(rs.getString("ssnumber"));
                    employee.setHireDate(rs.getString("hiredate"));
                    employee.setSalary(rs.getDouble("salary"));
                    employee.setStoreID(rs.getString("storeid"));
                }
            }
            
            //release the connection
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
            
            throw new DataException("Could not retrieve employee records form the database",  e);
        }
        
        // return the list of stores
        return employee;
    }
    
    
}
