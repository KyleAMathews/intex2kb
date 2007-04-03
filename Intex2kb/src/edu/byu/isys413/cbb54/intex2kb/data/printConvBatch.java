/*
 * printConvBatch.java
 *
 * Created on April 3, 2007, 8:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tylerf
 */
public class printConvBatch {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static printConvBatch instance = null;
    
    
    public printConvBatch() {
    }
    
    /** Creates a new instance of RentalBatch */
    public static synchronized printConvBatch getInstance() {
        if (instance == null) {
            instance = new printConvBatch();
        }
        return instance;
    }
    
    //Find all tx with print or conversion order and status complete & return customer e-mail
    public List<Customer> getReady() throws Exception {
        List l = new LinkedList<Customer>();
        
        //query db for txs
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("select * from \"transactionline\" where \"rstype\" = '" +
                "conv' or \"rstype\" = 'po'");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Transaction tx = TransactionDAO.getInstance().read(rs.getString("transactionid"));
            l.add(tx.getCustomer());
        }
        
        return l;
    }
}
