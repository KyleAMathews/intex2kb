/*
 * ConversionDAO.java
 *
 * Created on March 30, 2007, 2:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import com.sun.crypto.provider.RSACipher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author kyle
 */
public class ConversionDAO extends RSDAO{
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static ConversionDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private ConversionDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of ConversionDAO
     * @return ConversionDAO
     */
    public static synchronized ConversionDAO getInstance() {
        if (instance == null) {
            instance = new ConversionDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    public RevenueSource create() throws Exception{
        String id = GUID.generate("co");
        RevenueSource rs = new conversionBO(id);
        System.out.println("I've created a ConversionOrder  :  ID: " + rs.getId());
        
        //put RS into Cache
        Cache c = Cache.getInstance();
        c.put(rs.getId(),rs);
        
        return rs;
    }
    
    ///////////////////////////////////////////
    /// Read
    
    public RevenueSource read(String id, Connection conn) throws Exception {
        conversionBO po = new conversionBO(id);
        
        //read from DB and populate new RS object
        PreparedStatement ps = conn.prepareStatement("select * from \"conversionorder\" where \"id\" = '" + id + "'");
        ResultSet result = ps.executeQuery();
        
        po.setConversionType(conversionTypeDAO.getInstance().read(result.getString("conversiontype")));
        po.setQuantity(result.getInt("quantity"));
        po.setPrice(result.getDouble("price"));
        po.setDirty(false);
        po.setInDB(true);
        
        //put into cache
        
        RevenueSource rs =(RevenueSource)po;
        return po;
    }
    
    ///////////////////////////////////////////
    /// Save
    
    public void save(RevenueSource rsbo, Connection conn) throws Exception{
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (rsbo.isDirty()) {
            if (rsbo.isInDB()) {
                update(rsbo, conn);
            }else{
                insert(rsbo, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            rsbo.setDirty(false);
        }
    }
    
    public void insert(RevenueSource rsbo, Connection conn) throws Exception{
        conversionBO po = (conversionBO)rsbo;
        
        //insert object into DB
        PreparedStatement ps = conn.prepareStatement("insert into \"conversionorder\" values (?,?,?,?)");
        ps.setString(1,po.getId());
        ps.setString(2,po.getConversionType().getId());
        ps.setInt(3,po.getQuantity());
        ps.setDouble(4,po.getPrice());
        ps.execute();
        ps.close();
    }
    
    public void update(RevenueSource rsbo, Connection conn) throws Exception{
        //We do not support the update function for a Revenue source.  All information
        //will be added and static at the time of creation.
    }
    
    
    //////////////////////////////////////////
    /// delete
    
    // for business reasons we're not supporting deleting
    
    
}

