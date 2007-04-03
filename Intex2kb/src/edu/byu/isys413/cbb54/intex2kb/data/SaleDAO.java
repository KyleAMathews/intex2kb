/*
 * SaleDAO.java
 *
 * Created on March 29, 2007, 2:07 PM
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
public class SaleDAO extends RSDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static SaleDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private SaleDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of SaleDAO
     * @return SaleDAO
     */
    public static synchronized SaleDAO getInstance() {
        if (instance == null) {
            instance = new SaleDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    public RevenueSource create() throws Exception{
        String id = GUID.generate("sa");
        RevenueSource rs = new Sale(id);
        System.out.println("I've created a SaleBO  :  ID: " + rs.getId());
        return rs;
    }
    
    ///////////////////////////////////////////
    /// Read
    
    public RevenueSource read(String id, Connection conn) throws Exception{
        Sale sale = new Sale(id);
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"sale\" WHERE \"id\" = ?");
        read.setString(1, id);
        ResultSet rs = read.executeQuery();
        
        // set variables 
        // check first digit if sku/sn
        String type = rs.getString("producttype");
        
        ProductDAO dao = null;
        if (type == "p"){
            dao = PhysicalDAO.getInstance();
        }else{
            dao = ConceptualDAO.getInstance();
        }
        
        Product product = dao.read(id, conn);
        sale.setProduct(product);
        sale.setQuantity(rs.getInt("quantity"));
        
        // return the RevenueSource
        RevenueSource rst = (RevenueSource)sale;
        return rst;
    }
    
    ///////////////////////////////////////////
    /// Save
    
     public void save(RevenueSource rsbo, Connection conn) throws Exception{
         // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (rsbo.isDirty()) {
            if (rsbo.isInDB()) {
                // We are not supporting update
                //update(rsbo, conn);
            }else{
                insert(rsbo, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            rsbo.setDirty(false);
            
        }
     }
            
     public void insert(RevenueSource rsbo, Connection conn) throws Exception{
        System.out.println("inserting sale");
        Sale sale = (Sale)rsbo;
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"sale\" VALUES (?,?,?,?)");
        insert.setString(1, sale.getId());
        insert.setDouble(2, sale.getQuantity());
        insert.setString(3, sale.getProduct().getId());
        insert.setString(4, sale.getProductType());
        insert.executeUpdate();
        sale.setInDB(true);
     }
    
     public void update(RevenueSource rsbo, Connection conn) throws Exception{
        System.out.println("Updating sale");
        Sale sale = (Sale)rsbo;
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"sale\" SET \"quantity\"=?, \"productID\" = ?, \"producttype\" = ? WHERE \"id\" = ?");
        update.setDouble(1, sale.getQuantity());
        update.setString(2, sale.getProduct().getId());
        update.setString(3, sale.getProductType());
        update.setString(4, sale.getId());
        update.executeUpdate();
     }
    
    
    //////////////////////////////////////////
    /// delete
    
    // for business reasons we're not supporting deleting
    
    
}

