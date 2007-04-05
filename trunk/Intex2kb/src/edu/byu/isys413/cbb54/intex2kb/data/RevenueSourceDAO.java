/*
 * RevenueSourceDAO.java
 *
 * Created on March 30, 2007, 2:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author kyle
 */
public class RevenueSourceDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    Map<String, RSDAO> DAOmap = new TreeMap<String, RSDAO>();
    
    private static RevenueSourceDAO instance = null;
    
    /** Creates a new instance of RevenueSourceDAO */
    private RevenueSourceDAO() {
        //DAOmap.put("sa", SaleDAO.getInstance());
        DAOmap.put("ba", BackupDAO.getInstance());
        DAOmap.put("po", PrintOrderDAO.getInstance());
        DAOmap.put("rn", RentalDAO.getInstance());
        DAOmap.put("rp", RepairDAO.getInstance());
        DAOmap.put("co", ConversionDAO.getInstance());
    }
    
    /**
     * Singleton Pattern to allow only one instance of RevenueSourceDAO
     * @return RevenueSourceDAO
     */
    public static synchronized RevenueSourceDAO getInstance() {
        if (instance == null) {
            instance = new RevenueSourceDAO();
        }
        return instance;
    }
    // KEY (ba = backup | rn = rental | us = used | rp = repair | po = printOrder | sale = Sale | co = conversion)
    ///////////////////////////////////////////
    //// Create
    public RevenueSource create(String sku) throws DataException{
        try{
            // check first digit if backup/rental/used/repair/printOrder
            String type = String.valueOf(sku.charAt(0)) + String.valueOf(sku.charAt(1));
            
            // grab DAO from map
            RSDAO dao = DAOmap.get(type);
            if (dao == null){
                dao = SaleDAO.getInstance();
            }
            
            // create BO
            RevenueSource rs = dao.create();
            // save to cache
            Cache c = Cache.getInstance();
            c.put(rs.getId(), rs);
            
            // return
            return rs;
        }catch (DataException de){
            throw new DataException("Could not create new Revenue Source", de);
        }catch (Exception e){
            throw new DataException("Could not create new Revenue Source.", e);
        }
    }
    
    
    ///////////////////////////////////////////
    /// Read
    
    public RevenueSource read(String id) throws DataException{
        RevenueSource rs = null;
        
        // read from  RevenueSource database with id
        if(Cache.getInstance().containsKey(id)){
            rs = (RevenueSource)Cache.getInstance().get(id);
        }else{
            Connection conn = null;
            try {
                // retrieve a database connection from the pool
                conn = ConnectionPool.getInstance().get();
                
                // check first digit if backup/rental/used/repair/printOrder
                String type = String.valueOf(id.charAt(0)) + String.valueOf(id.charAt(1));
                
                // grab DAO from map
                RSDAO dao = DAOmap.get(type);
                
                // call read with a connection on selected DAO
                rs = dao.read(id, conn);
                rs.setDirty(false);
                rs.setInDB(true);
                
                // save to cache
                Cache c = Cache.getInstance();
                c.put(rs.getId(), rs);
                
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
            }catch (Exception e){
                throw new DataException("Caught yet another exception in the RevenueSourceDAO " + e);
            }
            
        }
        
        // return
        return rs;
    }
    
    
    ///////////////////////////////////////////
    /// Save
    
    public void save(RevenueSource rs) throws DataException{
        
        String id = rs.getId();
        Connection conn = null;
        try {
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // check first digit if backup/rental/used/repair/printOrder/sale
            String type = String.valueOf(id.charAt(0)) + String.valueOf(id.charAt(1));
            
            // grab DAO from map
            RSDAO dao = DAOmap.get(type);
            // save rs using a connection on selected DAO
            dao.save(rs, conn);
            System.out.println("saved rs");
            // clean up rs
            rs.setDirty(false);
            
            // touch cache
            Cache c = Cache.getInstance();
            c.touch(rs.getId());
            
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
        }catch (Exception e){
            throw new DataException("Caught yet another exception in the RevenueSourceDAO " + e);
        }
        
        return;
    }
    
    public void save(RevenueSource rs, Connection conn1) throws DataException{
        String id = rs.getId();
        Connection conn = conn1;
        try {
            
            // check first digit if backup/rental/used/repair/printOrder/sale
            String type = String.valueOf(id.charAt(0)) + String.valueOf(id.charAt(1));
            
            // grab DAO from map
            RSDAO dao = DAOmap.get(type);
//            System.out.println("saving rs");
//            System.out.println(id + " " + type + " " + rs.isDirty() + " " + rs.isInDB);
            // save rs using a connection on selected DAO
            dao.save(rs, conn);
            System.out.println("saved rs");
            // clean up rs
            rs.setDirty(false);
            
            // touch cache
            Cache c = Cache.getInstance();
            c.touch(rs.getId());
            
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
        }catch (Exception e){
            throw new DataException("Caught yet another exception in the RevenueSourceDAO " + e);
        }
        
        return;
    }
    
    ///////////////////////////////////////////
    /// Delete
    
    // for business reasons we are not deleteing any revenue sources
    
}
