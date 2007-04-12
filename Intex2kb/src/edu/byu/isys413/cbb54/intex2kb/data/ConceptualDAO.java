/*
 * ConceptualDAO.java
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
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Cameron
 */
public class ConceptualDAO extends ProductDAO{
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static ConceptualDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private ConceptualDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of ProductDAO
     * @return ProductDAO
     */
    public static synchronized ProductDAO getInstance() {
        if (instance == null) {
            instance = new ConceptualDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    /**
     * Create a new instance of a conceptual object
     */
    public Product create() throws Exception{
        String id = GUID.generate();
        Product product = new Conceptual(id);
        System.out.println("I've created a Conceptaul Product  =>  ID: " + product.getId());
        return product;
    }
    
    ///////////////////////////////////////////
    /// Read
    /**
     * public read statement
     */
    public Product read(String sku) throws Exception {
        Conceptual con = null;
        
        
        // check to see if id in the cache
        // if so, return it immediately
        if(Cache.getInstance().containsKey(sku)){
            con = (Conceptual)Cache.getInstance().get(sku);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // call read with a connection (the other read method in this class)
                con = (Conceptual)this.read(sku, conn);
                
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
                
                throw new DataException("Could not retrieve record for id=" + con, e);
            }
        }
        Product prod = (Product)con;
        return prod;
    }
    
    /**
     * Private read statement called from the public read statement
     */
    public Product read(String id, Connection conn) throws Exception{
        Conceptual concept = new Conceptual(id);
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"conceptual\" \"co\", \"product\" \"pr\" WHERE \"co\".\"id\" = ? AND \"co\".\"id\" = \"pr\".\"id\" ");
        read.setString(1, id);
        ResultSet rs = read.executeQuery();
        
        if(rs.next()){
            // set variables
            concept.setName(rs.getString("name"));
            concept.setDesc(rs.getString("description"));
            concept.setAvgCost(rs.getDouble("avgCost"));
            concept.setPrice(rs.getDouble("price"));
            concept.setInDB(true);
        }else{
            throw new DataException("Object was not found in the database.");
        }
        
        // return the RevenueSource
        Product product = (Product)concept;
        return product;
    }
    
    ///////////////////////////////////////////
    /// Save
    
    /**
     * public save method
     */
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
    
    /**
     * private save method called from the public save method
     */
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
    
    /**
     * Insert the conceptual object into the database
     */
    public void insert(Product prod, Connection conn) throws Exception{
        System.out.println("inserting conceptual");
        Conceptual conc = (Conceptual)prod;
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"conceptual\" VALUES (?,?,?,?)");
        insert.setString(1, conc.getId());
        insert.setString(2, conc.getName());
        insert.setString(3, conc.getDesc());
        insert.setDouble(4, conc.getAvgCost());
        
        insert.executeUpdate();
        
        // set the dirty flag to false now that we've saved it
        prod.setDirty(false);
    }
    
    /**
     * Update the conceptual object information in the database
     */
    public void update(Product prod, Connection conn) throws Exception{
        Conceptual conc = (Conceptual)prod;
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"conceptual\" SET \"name\"=?, \"description\" = ?, \"avgCost\" = ? WHERE \"id\" = ?");
        update.setString(1, conc.getName());
        update.setString(2, conc.getDesc());
        update.setDouble(3, conc.getAvgCost());
        update.setString(4, conc.getId());
        update.executeUpdate();
    }
    
    //////////////////////////////////////////
    /// delete
    
    // for business reasons we're not supporting deleting
    
    //////////////////////////////////////////
    /// search
    
    /**
     * Returns a boolean if the object exists
     */
    public boolean exists(String id) throws Exception{
        Boolean b = false;
        Connection conn = ConnectionPool.getInstance().get();
        
        PreparedStatement search = conn.prepareStatement(
                "SELECT * FROM \"conceptual\" WHERE \"id\" = ?");
        search.setString(1, id);
        ResultSet rs = search.executeQuery();
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        if(rs.next()){
            b = true;
        }
        
        return b;
    }

    /**
     * Returns a list of products by category
     */
    public List<Conceptual> getProductsByCategory(String categoryID) throws Exception{
        List<Conceptual> list = new LinkedList<Conceptual>();
        
        Connection conn = ConnectionPool.getInstance().get();
        
        PreparedStatement search = conn.prepareStatement(
                "SELECT * FROM \"conceptual\" \"co\", \"product\" \"pr\" WHERE \"co\".\"id\" = \"pr\".\"id\" AND \"co\".\"categoryID\" = ? ORDER BY \"co\".\"name\" ");
        search.setString(1, categoryID);
        ResultSet rs = search.executeQuery();
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        while(rs.next()){
            Conceptual concept = new Conceptual(rs.getString("id"));
            concept.setName(rs.getString("name"));
            concept.setDesc(rs.getString("description"));
            concept.setAvgCost(rs.getDouble("avgCost"));
            concept.setPrice(rs.getDouble("price"));
            concept.setInDB(true);
            list.add(concept);
        }
        
        
        
        return list;
    }
}
