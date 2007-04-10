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
 * Validate the login information passed in by the GUI.
 * @author tylerf
 */
public class validateLogin {
    
        ///////////////////////////////////////
    ///   Singleton pattern
    
    private static validateLogin instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private validateLogin() {
    }
    
    /**
     * Return an instance of validateLogin
     * @return instance of validateLogin
     */
    public static synchronized validateLogin getInstance() {
        if (instance == null) {
            instance = new validateLogin();
        }
        return instance;
    }
    
    /**
     * validate the e-mail address and password passed in
     * @param password user password
     * @param email user e-mail
     * @return boolean valid or not
     * @throws java.lang.Exception exception
     */
    public boolean validate(String email, String password) throws Exception {
        boolean pass = false;
        
        Connection conn = ConnectionPool.getInstance().get();
        System.out.println("attempting login processing");
        
        PreparedStatement ps = conn.prepareStatement("select * from \"login\" where \"email\" = '" +
                email + "' and \"password\" = '" + password + "'");
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            pass = true;
        }
        
        ps.close();
        rs.close();
        
        ConnectionPool.getInstance().release(conn);
        System.out.println("login processed");
        return pass;
    }
    
    public String custid(String email, String password) throws Exception {
        String custid = "";
        Connection conn = ConnectionPool.getInstance().get();
        System.out.println("attempting to get custid from table login");
        
        PreparedStatement ps = conn.prepareStatement("select * from \"login\" where \"email\" = '" +
                email + "' and \"password\" = '" + password + "'");
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            custid = rs.getString("custId");
        }
        
        ps.close();
        rs.close();
        
        ConnectionPool.getInstance().release(conn);
        System.out.println("got the custid from table login");
        return custid;
    }
}
