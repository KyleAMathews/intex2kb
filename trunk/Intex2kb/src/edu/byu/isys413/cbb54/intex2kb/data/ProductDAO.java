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
    
    abstract Product create() throws Exception;
    
    ///////////////////////////////////////////
    /// Read
    
    abstract Product read (String sku, Connection conn) throws Exception;
            
    ///////////////////////////////////////////
    /// Save
    
    abstract void save(Product product, Connection conn) throws Exception;
            
    abstract void insert(Product product, Connection conn) throws Exception;
    
    abstract void update(Product product, Connection conn) throws Exception;
    
    public ProductDAO getProductDAO(String id) throws Exception{
        ProductDAO p;
        PhysicalDAO pDAO = (PhysicalDAO)PhysicalDAO.getInstance();
        if(pDAO.exists(id)){
            p = pDAO;
        }else{
            p = (ConceptualDAO)ConceptualDAO.getInstance();
        }
        return p;
    }

}
