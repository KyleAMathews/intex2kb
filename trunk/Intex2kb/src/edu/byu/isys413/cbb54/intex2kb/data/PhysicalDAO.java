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
    
}
