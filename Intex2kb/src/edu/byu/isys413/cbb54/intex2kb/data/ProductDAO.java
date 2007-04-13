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
    
    /**
     * create a new product object
     */
    public abstract Product create() throws Exception;
    
    ///////////////////////////////////////////
    /// Read
    
    /**
     * private read statemtn
     */
    public abstract Product read(String sku, Connection conn) throws Exception;
    /**
     * public read statement
     */
    public abstract Product read(String sku) throws Exception;
            
    ///////////////////////////////////////////
    /// Save
    
    /**
     * private save statement
     */
    public abstract void save(Product product, Connection conn) throws Exception;
    /**
     * public save statement
     */
    public abstract void save(Product product) throws Exception;
            
    /**
     * insert the product into the database
     */
    public abstract void insert(Product product, Connection conn) throws Exception;
    
    /**
     * update the prodcut info in the database
     */
    public abstract void update(Product product, Connection conn) throws Exception;
    
    //////////////////////////////////////////
    /// Search
    
    /**
     * check to see if the object exists in the database
     */
    public abstract boolean exists(String id) throws Exception;

}
