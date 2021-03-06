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
    
    /**
     * Used to find the member id of the person attempting to log in to the online store
     * @param email email address of the person
     * @param password password entered by the person
     * @return String - Member ID, if one exists
     * @throws java.lang.Exception Throws and Exception if there is a problem connection with the 
     * database of there is an error in the SQL
     */
    public String membid(String email, String password) throws Exception {
        String membid = "";
        Connection conn = ConnectionPool.getInstance().get();
        System.out.println("attempting to get membid from table login");
        
        PreparedStatement ps = conn.prepareStatement("select * from \"login\" where \"email\" = '" +
                email + "' and \"password\" = '" + password + "'");
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            membid = rs.getString("membid");
        }
        
        ps.close();
        rs.close();
        
        ConnectionPool.getInstance().release(conn);
        System.out.println("got the membid from table login");
        return membid;
    }
}
