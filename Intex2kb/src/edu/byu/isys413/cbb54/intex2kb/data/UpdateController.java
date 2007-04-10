/*
 * UpdateController.java
 *
 * Created on March 12, 2007, 3:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

/**
 * cascades through a transaction and all of its subcomponents and writes them to the DB
 * @author Cameron
 */
public class UpdateController {
    
    
    // Singelton
    private static UpdateController instance = null;
    
    
    /** Creates a new instance of TransactionLineDAO */
    private UpdateController() {
    }
    
    /**
     * returns an instance of UpdateController
     * @return instance of UpdateController
     */
    public static synchronized UpdateController getInstance() {
        if (instance == null) {
            instance = new UpdateController();
        }
        return instance;
    }        
    
           
    /**
     * saveTransaction cascades through all the pieces of a transaction and writes them to the DB
     * @param tx transaction
     * @throws java.lang.Exception Exception
     */
    public void saveTransaction(Transaction tx) throws Exception {
        Connection conn = ConnectionPool.getInstance().get();
        
        System.out.print("Saving the Transaction...");
        TransactionDAO.getInstance().save(tx, conn);
        System.out.print("success\n");
        
        conn.commit();
        ConnectionPool.getInstance().release(conn);
    }
    
    /**
     * get Product by Serial Number
     * @param serial serial number of product
     * @return product
     * @throws java.lang.Exception Exception
     */
    public Physical getbySerial(String serial) throws Exception{
        Physical phy = null;
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"physical\" WHERE \"serialnum\" = ?");
        read.setString(1, serial);
        ResultSet rs = read.executeQuery();
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        System.out.println("Read from the db");
        System.out.println(rs.getString("id"));
        
        // set variables
        while(rs.next()){
        phy = new Physical(rs.getString("id"));
        System.out.println("item created");
        phy.setSerialNum(rs.getString("serialnum"));
        phy.setShelfLocation(rs.getString("shelflocation"));
        phy.setConceptualid(rs.getString("conceptualproduct"));
        phy.setForSale(rs.getBoolean("forsale"));
        phy.setInDB(true);
        }
        
        
        // return the RevenueSource
        System.out.println("created the physical product");
        return phy;
    } 
    
    
}
