/*
 * CategoryDAO.java
 *
 * Created on April 9, 2007, 8:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.*;
import java.util.*;
/**
 *
 * @author Bryan
 */
public class CategoryDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static CategoryDAO instance = null;
    
    
    
    /** Creates a new instance of CategoryDAO */
    public CategoryDAO() {
    }
    
    public static synchronized CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }
    
    public Category create(String name) throws Exception{
        String id = GUID.generate();
        Category cat = new Category(id,name);
        return cat;
    }
    
    public List<String> getCategoryList() throws DataException {
        List<String> list = new LinkedList<String>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"category\" ");
            ResultSet rs = read.executeQuery();
            
            // while loop to populate the list from the results
            while(rs.next()) {
                list.add(rs.getString("name"));
                //release the connection
                conn.commit();
                ConnectionPool.getInstance().release(conn);
            }
            
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
    
    public List<Category> getCategorys() throws DataException {
        List<Category> list = new LinkedList<Category>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                    "SELECT * FROM \"category\" ");
            ResultSet rs = read.executeQuery();
            
            // while loop to populate the list from the results
            while(rs.next()) {
                Category c;
                if(Cache.getInstance().containsKey(rs.getString("id"))){
                    c = (Category)Cache.getInstance().get(rs.getString("id"));
                }else{
                    c = new Category(rs.getString("id"), rs.getString("name"));
                }
                list.add(c);
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
