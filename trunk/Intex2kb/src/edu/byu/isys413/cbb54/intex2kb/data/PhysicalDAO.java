/*
 * PhysicalDAO.java
 *
 * Created on April 2, 2007, 4:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Cameron
 */
public class PhysicalDAO extends ProductDAO{

    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static PhysicalDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private PhysicalDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of ProductDAO
     * @return ProductDAO
     */
    public static synchronized ProductDAO getInstance() {
        if (instance == null) {
            instance = new PhysicalDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    public Product create() throws Exception{
        String id = GUID.generate();
        Product product = new Physical(id);
        System.out.println("I've created a Product  =>  ID: " + product.getId());
        return product;
    }
    
    ///////////////////////////////////////////
    /// Read
    public Product read(String id) throws Exception {
        Physical phy = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(id)){
            phy = (Physical)Cache.getInstance().get(id);
        }else{        
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();

                // call read with a connection (the other read method in this class)
                phy = (Physical)this.read(id, conn);

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

                throw new DataException("Could not retrieve record for id=" + phy, e);
            }
        }
        Product prod = (Product)phy;
        return prod;
    }
    
    
    public Product read(String id, Connection conn) throws Exception{
        Physical phy = new Physical(id);
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"id\" = ?");
        read.setString(1, id);
        ResultSet rs = read.executeQuery();
        
        // set variables
        phy.setSerialNum(rs.getString("serialNum"));
        phy.setShelfLocation(rs.getString("shelfLocation"));
        phy.setForSale(rs.getBoolean("forSale"));
        phy.setPrice(rs.getDouble("price"));
        phy.setInDB(true);
        
        // return the RevenueSource
        Product product = (Product)phy;
        return product;
    }
    
    public Product readBySku(String sku) throws Exception {
        Physical phy = null;
               
        Connection conn = null;
        try {
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();

            // call read with a connection (the other read method in this class)
            phy = (Physical)this.readSku(sku, conn);

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

            throw new DataException("Could not retrieve record for id=" + phy, e);
        }
        
        Product prod = (Product)phy;
        return prod;
    }
    
    
    public Product readSku(String sku, Connection conn) throws Exception{
        Physical phy = null;
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"serialnum\" = ?");
        read.setString(1, sku);
        ResultSet rs = read.executeQuery();
        
        // set variables
        phy = new Physical(rs.getString("id"));
        phy.setSerialNum(rs.getString("serialnum"));
        phy.setShelfLocation(rs.getString("shelfLocation"));
        phy.setForSale(rs.getBoolean("forSale"));
        phy.setPrice(rs.getDouble("price"));
        phy.setInDB(true);
        
        // return the RevenueSource
        Product product = (Product)phy;
        return product;
    }
    
    ///////////////////////////////////////////
    /// Save

    public void save(Product product) throws Exception {
        Connection conn = null;
        
        try {
   
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // call save with a connection (the other save method in this class)
            this.save(product, conn);
            
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
            
            throw new DataException("Could not save record for id=" + product.getId(), e);
        }
    }
        
    public void save(Product prod, Connection conn) throws Exception{
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (prod.isDirty()) {
            if (prod.isInDB()) {
                update(prod, conn);
            }else{
                insert(prod, conn);
            }
            
        }
    }
    
    public void insert(Product prod, Connection conn) throws Exception{
        System.out.println("inserting physical");
        Physical phy = (Physical)prod;
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"physical\" VALUES (?,?,?,?)");
        insert.setString(1, phy.getId());
        insert.setString(2, phy.getSerialNum());
        insert.setString(3, phy.getShelfLocation());
        insert.setBoolean(4, phy.isForSale());

        insert.executeUpdate();
        
        // set the dirty flag to false now that we've saved it
        prod.setDirty(false);
    }
    
    public void update(Product prod, Connection conn) throws Exception{
        Physical phy = (Physical)prod;
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"physical\" SET \"serialNum\"=?, \"shelfLocation\" = ?, \"forSale\" = ? WHERE \"id\" = ?");
        update.setString(1, phy.getSerialNum());
        update.setString(2, phy.getShelfLocation());
        update.setBoolean(3, phy.isForSale());
        update.setString(4, phy.getId());
        update.executeUpdate();
    }
    
//////////////////////////////////////////
/// delete
    
// for business reasons we're not supporting deleting
    
    
///////////////////////////////////////////
    /// SEARCH
    
       
    public boolean exists(String id) throws Exception{
        Boolean b = false;
        Connection conn = ConnectionPool.getInstance().get();
        
        PreparedStatement search = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"serialnum\" = ?");
        search.setString(1, id);
        ResultSet rs = search.executeQuery();
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        if(rs.next()){
            b = true;
        }
        
        return b;
    }   

    public List<String> getByConceptual(List productList) throws DataException {
        List<String> list = new LinkedList<String>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            for(int i = 0; i<productList.size(); i++){
                    // sql the names, phone, and ids
                    PreparedStatement read = conn.prepareStatement(
                        "SELECT \"id\" FROM \"physical\" WHERE \"conceptualproduct\" = ? ");
                    read.setString(1, (String) productList.get(i));
                    ResultSet rs = read.executeQuery();

                    // while loop to populate the list from the results
                    while(rs.next()) {
                            list.add(rs.getString("id"));         
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

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
       
        // return the list of stores
        return list;
    }



}


