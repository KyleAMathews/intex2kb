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
    public void getReady() throws Exception {
        List l = new LinkedList<String>();
        
        //query db for txs
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("select distinct custid from \"transaction\" where exists" +
                " (select * from transactionline where \"rstype\" like '" +
                "Conversion%' or \"rstype\" like 'Print Order%')");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Customer cust = CustomerDAO.getInstance().read(rs.getString("custid"),conn);
            l.add(cust.getEmail());
        }
        
        sendMail sm = new sendMail();
        sm.postMail(l,"The order that was received by MyStuff Digital Photography " +
                "is now ready to be picked up.  Please come as soon as possible to " +
                "claim your order.");
    }
}
