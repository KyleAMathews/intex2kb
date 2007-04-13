/*
 * PrintOrderDAO.java
 *
 * Created on March 30, 2007, 2:26 PM
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
 * @author kyle
 */
public class PrintOrderDAO extends RSDAO{
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static PrintOrderDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private PrintOrderDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of PrintOrderDAO
     * @return PrintOrderDAO
     */
    public static synchronized PrintOrderDAO getInstance() {
        if (instance == null) {
            instance = new PrintOrderDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    /**
     * creates a print order
     */
    public RevenueSource create() throws Exception{
        String id = GUID.generate("po");
        String poid = GUID.generate();
        RevenueSource rs = new printOrder(id);
        printOrder po = (printOrder)rs;
        po.setPOID(poid);
        System.out.println("I've created a PrintOrder  :  ID: " + rs.getId());
        
        //put RS into Cache
        Cache c = Cache.getInstance();
        c.put(rs.getId(),rs);
        
        return rs;
    }
    
    ///////////////////////////////////////////
    /// Read
    
    /**
     * read a print order
     */
    public RevenueSource read(String id, Connection conn) throws Exception {
        printOrder po = new printOrder(id);
        
        //read from DB and populate new RS object
        PreparedStatement ps = conn.prepareStatement("select * from \"printorder\" where \"id\" = '" + id + "'");
        ResultSet result = ps.executeQuery();
        
        po.setPOID(result.getString("poid"));
        po.setQuantity(result.getInt("quantity"));
        po.setPhotoSet(PhotoSetDAO.getInstance().read(result.getString("photoset")));
        po.setInDB(true);
        po.setDirty(false);
        po.setPrice(result.getDouble("price"));
        
        //put into cache
        
        RevenueSource rs =(RevenueSource)po;
        return po;
    }
    
    ///////////////////////////////////////////
    /// Save
    
    /**
     * save a print order
     */
    public void save(RevenueSource rsbo, Connection conn) throws Exception{
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (rsbo.isDirty()) {
            if (rsbo.isInDB()) {
                update(rsbo, conn);
            }else{
                insert(rsbo, conn);
            }
            
            //update PhotoSet associated with PrintOrder
            printOrder po = (printOrder)rsbo;
            PhotoSetDAO.getInstance().save(po.getPhotoSet(),conn);
            
            // set the dirty flag to false now that we've saved it
            rsbo.setDirty(false);
        }
    }
    
    /**
     * insert a print order object into the database
     */
    public void insert(RevenueSource rsbo, Connection conn) throws Exception{
        printOrder po = (printOrder)rsbo;
        
        //insert object into DB
        PreparedStatement ps = conn.prepareStatement("insert into \"printorder\" values (?,?,?,?,?,?)");
        ps.setString(1,po.getId());
        ps.setString(2,po.getPoID());
        ps.setInt(3,po.getQuantity());
        ps.setString(4,po.getPhotoSet().getId());
        ps.setString(5,po.getPrintFormat().getId());
        ps.setDouble(6,po.getPrice());
        ps.execute();
        ps.close();
    }
    
    /**
     * update a print order object in the database
     */
    public void update(RevenueSource rsbo, Connection conn) throws Exception{
        //We do not support the update function for a Revenue source.  All information
        //will be added and static at the time of creation.
    }
    
    
    //////////////////////////////////////////
    /// delete
    
    // for business reasons we're not supporting deleting
    
    
}

