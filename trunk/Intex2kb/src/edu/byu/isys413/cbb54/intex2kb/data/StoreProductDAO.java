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
 * Handles all database queries to the store/product table
 * @author Bryan
 */
public class StoreProductDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static StoreProductDAO instance = null;
    
    
    
    /** Creates a new instance of CategoryDAO */
    public StoreProductDAO() {
    }
 
    /**
     * Returns an instance of the StoreProductDAO
     * @return StoreProductDAO
     */
    public static synchronized StoreProductDAO getInstance() {
        if (instance == null) {
            instance = new StoreProductDAO();
        }
        return instance;
    }
    
    /**
     * Returns the product for specified stores
     * @param storeList List of store IDs
     * @return List of product IDs
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is an error getting a database connection or 
     * executing SQL
     */
    public List<String> getProductList(List storeList) throws DataException {
        List<String> list = new LinkedList<String>();
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            for(int i = 0; i<storeList.size(); i++){
                    // sql the names, phone, and ids
                    PreparedStatement read = conn.prepareStatement(
                        "SELECT \"productid\" FROM \"storeproduct\" WHERE \"storeid\" = ? ");
                    read.setString(1, (String) storeList.get(i));
                    ResultSet rs = read.executeQuery();

                    // while loop to populate the list from the results
                    while(rs.next()) {
                            list.add(rs.getString("productid"));
                            
                    }           
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
