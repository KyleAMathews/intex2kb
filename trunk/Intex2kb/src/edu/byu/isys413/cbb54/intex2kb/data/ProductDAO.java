/*
 * ProductDAO.java
 *
 * Created on April 2, 2007, 4:16 PM
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
 * @author Cameron
 */
public abstract class ProductDAO {

    ///////////////////////////////////////////
    /// Create
    
    public abstract Product create() throws Exception;
    
    ///////////////////////////////////////////
    /// Read
    
    public abstract Product read(String sku, Connection conn) throws Exception;
    public abstract Product read(String sku) throws Exception;
            
    ///////////////////////////////////////////
    /// Save
    
    public abstract void save(Product product, Connection conn) throws Exception;
    public abstract void save(Product product) throws Exception;
            
    public abstract void insert(Product product, Connection conn) throws Exception;
    
    public abstract void update(Product product, Connection conn) throws Exception;
    
    //////////////////////////////////////////
    /// Search
    
    public abstract boolean exists(String id) throws Exception;

}
