/*
 * conversionTypeDAO.java
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
import java.sql.SQLException;

/**
 *
 * @author kyle
 */
public class conversionTypeDAO{
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static conversionTypeDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private conversionTypeDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of conversionTypeDAO
     * @return conversionTypeDAO
     */
    public static synchronized conversionTypeDAO getInstance() {
        if (instance == null) {
            instance = new conversionTypeDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
    public conversionTypeBO create() throws Exception{
        String id = GUID.generate();
        conversionTypeBO conversionType = new conversionTypeBO(id);
        
        //put RS into Cache
        Cache c = Cache.getInstance();
        c.put(conversionType.getId(),conversionType);
        
        return conversionType;
    }
    
    public conversionTypeBO create(String id) throws Exception{
        conversionTypeBO conversionType = new conversionTypeBO(id);
        
        //put RS into Cache
        Cache c = Cache.getInstance();
        c.put(conversionType.getId(),conversionType);
        
        return conversionType;
    }
    
    ///////////////////////////////////////////
    /// Read
    public conversionTypeBO read(String id) throws Exception {
        conversionTypeBO po = null;
        
        //check to see if ID is in cache.  If so, return immediately
        if(po == null && Cache.getInstance().containsKey(id) == true){
            po = (conversionTypeBO)Cache.getInstance().get(id);
        }else {
            //if not in cache, create connection and Member objects
            Connection conn = ConnectionPool.getInstance().get();
            
            try {
                //if not in cache, retrieve DB connection
                conn = ConnectionPool.getInstance().get();
                
                //get a result set from a SELECT SQL statement
                PreparedStatement ps = conn.prepareStatement("select * from \"conversiontype\" where \"id\" = '" + id + "'");
                ResultSet result = ps.executeQuery();
                
                po.setDestinationType(result.getString("destinationtype"));
                po.setSourceType(result.getString("sourcetype"));
                po.setDirty(false);
                po.setInDB(true);
                po.setPrice(result.getDouble("price"));
            } catch (Exception e){
                e.printStackTrace();
            }
            
            //put into cache
            Cache c = Cache.getInstance();
            c.put(po.getId(),po);
            
        }
        return po;
    }
    
    public conversionTypeBO read(String id, Connection conn) throws Exception {
        conversionTypeBO po = null;
        
        //check to see if ID is in cache.  If so, return immediately
        if(po == null && Cache.getInstance().containsKey(id) == true){
            po = (conversionTypeBO)Cache.getInstance().get(id);
        }else {
            //if not in cache, create connection and Member objects
            
            try {
                //if not in cache, retrieve DB connection
                conn = ConnectionPool.getInstance().get();
                
                //get a result set from a SELECT SQL statement
                PreparedStatement ps = conn.prepareStatement("select * from \"conversiontype\" where \"id\" = '" + id + "'");
                ResultSet result = ps.executeQuery();
                
                po.setDestinationType(result.getString("destinationtype"));
                po.setSourceType(result.getString("sourcetype"));
                po.setDirty(false);
                po.setInDB(true);
                po.setPrice(result.getDouble("price"));
            } catch (Exception e){
                e.printStackTrace();
            }
            
            //put into cache
            Cache c = Cache.getInstance();
            c.put(po.getId(),po);
            
            
        }
        return po;
    }
    
///////////////////////////////////////////
/// Save
    
    public synchronized void save(conversionTypeBO ps, Connection conn) throws SQLException, Exception {
        if (ps.getIsInDB() == true) {
            update(ps,conn);
        }else{
            insert(ps,conn);
        }
    }
    
    public synchronized void save(conversionTypeBO ps) throws SQLException, Exception {
        Connection conn = ConnectionPool.getInstance().get();
        if (ps.getIsInDB() == true) {
            update(ps,conn);
        }else{
            insert(ps,conn);
        }
        ConnectionPool.getInstance().release(conn);
    }
    
    private synchronized void update(conversionTypeBO conversionType, Connection conn) throws Exception{
        //do update statement
        PreparedStatement ps = conn.prepareStatement("update \"conversiontype\" set \"sourcetype\" = '" + conversionType.getSourceType()
        + "', \"destinationtype\" = '" + conversionType.getDestinationType() + "', \"price\" = " +
                conversionType.getPrice() + " where \"id\" = '" + conversionType.getId() + "'");
        ps.execute();
        conn.commit();
        ps.close();
        
        //release connection
        ConnectionPool.getInstance().release(conn);
        
        //touch the cache for the object (renew the 30 min time limit)
        Cache.getInstance().touch(conversionType.getId());
    }
    
    private synchronized void insert(conversionTypeBO conversionType, Connection conn) throws SQLException, Exception{
        conversionType.setInDB(true);
        
        PreparedStatement ps = conn.prepareStatement("insert into \"conversiontype\" values (?,?,?,?)");
        ps.setString(1,conversionType.getId());
        ps.setString(2,conversionType.getSourceType());
        ps.setString(3,conversionType.getDestinationType());
        ps.setDouble(4,conversionType.getPrice());
        
        ps.execute();
        conn.commit();
        ps.close();
        
        //release connection
        ConnectionPool.getInstance().release(conn);
        
        //touch the cache for the object (renew the 30 min time limit)
        Cache.getInstance().touch(conversionType.getId());
    }
    
    
//////////////////////////////////////////
/// delete
    
// for business reasons we're not supporting deleting
    
    public conversionTypeBO getConversionType(String sourceType, String destinationType) throws Exception {
        conversionTypeBO conv = null;
        
        Connection conn = ConnectionPool.getInstance().get();
        
        PreparedStatement ps = conn.prepareStatement("select * from \"conversiontype\" where \"sourcetype\" = '" + sourceType + "'" +
                " and \"destinationtype\" = '" + destinationType + "'");
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            conv = conversionTypeDAO.getInstance().create(rs.getString("id"));
            conv.setDestinationType(rs.getString("destinationtype"));
            conv.setSourceType(rs.getString("sourcetype"));
            conv.setDirty(false);
            conv.setInDB(true);
            conv.setPrice(rs.getDouble("price"));
        }
        
        ps.close();
        rs.close();
        ConnectionPool.getInstance().release(conn);
        
        return conv;
    }
}
//test
