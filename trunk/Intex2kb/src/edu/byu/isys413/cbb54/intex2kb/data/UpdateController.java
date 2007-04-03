/*
 * UpdateController.java
 *
 * Created on March 12, 2007, 3:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Cameron
 */
public class UpdateController {
    
    
    // Singelton
    private static UpdateController instance = null;
    
    
    /** Creates a new instance of TransactionLineDAO */
    private UpdateController() {
    }
    
    public static synchronized UpdateController getInstance() {
        if (instance == null) {
            instance = new UpdateController();
        }
        return instance;
    }        
    
            
    public void saveTransaction(Transaction tx) throws Exception {
        Connection conn = ConnectionPool.getInstance().get();
        
        System.out.print("Saving the Transaction...");
        TransactionDAO.getInstance().save(tx, conn);
        System.out.print("success\n");
        
        conn.commit();
        ConnectionPool.getInstance().release(conn);
    }
}
