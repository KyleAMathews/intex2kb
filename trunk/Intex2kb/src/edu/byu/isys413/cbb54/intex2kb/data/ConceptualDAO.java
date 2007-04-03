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
    
    public Product create() throws Exception{
        String id = GUID.generate();
        Product product = new Conceptual(id);
        System.out.println("I've created a Conceptaul Product  =>  ID: " + product.getId());
        return product;
    }
    
    ///////////////////////////////////////////
    /// Read
    
    public Product read(String id, Connection conn) throws Exception{
        Conceptual concept = new Conceptual(id);
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"conceptual\" WHERE \"id\" = ?");
        read.setString(1, id);
        ResultSet rs = read.executeQuery();
        
        // set variables
        concept.setName(rs.getString("name"));
        concept.setDesc(rs.getString("description"));
        concept.setAvgCost(rs.getDouble("avgCost"));
        concept.setInDB(true);
        
        // return the RevenueSource
        Product product = (Product)concept;
        return product;
    }
    
    ///////////////////////////////////////////
    /// Save

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
}
