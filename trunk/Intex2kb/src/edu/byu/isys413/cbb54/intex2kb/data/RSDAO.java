/*
 * RSDAO.java
 *
 * Created on March 30, 2007, 3:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;

/**
 *
 * @author kyle
 */
public abstract class RSDAO {
    
    /** Creates a new instance of RSDAO */
    public RSDAO() {
    }
    
    ///////////////////////////////////////////
    /// Create
    
    abstract RevenueSource create() throws Exception;
    
    ///////////////////////////////////////////
    /// Read
    
    abstract RevenueSource read (String sku, Connection conn) throws Exception;
            
    ///////////////////////////////////////////
    /// Save
    
    abstract void save(RevenueSource rsbo, Connection conn) throws Exception;
            
    abstract void insert(RevenueSource rsbo, Connection conn) throws Exception;
    
    abstract void update(RevenueSource rsbo, Connection conn) throws Exception;
    
    
}
