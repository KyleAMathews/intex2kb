/*
 * validateLogin.java
 *
 * Created on April 4, 2007, 8:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author tylerf
 */
public class validateLogin {
    
        ///////////////////////////////////////
    ///   Singleton pattern
    
    private static validateLogin instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private validateLogin() {
    }
    
    public static synchronized validateLogin getInstance() {
        if (instance == null) {
            instance = new validateLogin();
        }
        return instance;
    }
    
    public boolean validate(String email, String password) throws Exception {
        boolean pass = false;
        
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("select * from \"login\" where \"email\" = '" +
                email + "' and \"password\" = '" + password + "'");
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            pass = true;
        }
        
        ps.close();
        rs.close();
        
        ConnectionPool.getInstance().release(conn);
        
        return pass;
    }
}
