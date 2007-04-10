package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.validateLogin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * LoginWeb.java
 *
 * Created on April 7, 2007, 8:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author kyle
 */
public class LoginWeb implements edu.byu.isys413.web.Action{
    
    boolean pass = false;
    String email;
    String password;
    /**
     * Creates a new instance of LoginWeb
     */
    public LoginWeb() {
        
    }
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        // get password / email
        email = (String)request.getParameter("email");
        password = (String)request.getParameter("password");
        System.out.println("I'm in loginweb.java " + password + " " + email);
        
        
        pass = validateLogin.getInstance().validate(email, password);
        System.out.println("validation value=" + pass);
        
        if(pass == true){
            request.setAttribute("login", "1"); // makes login not null sending message that login worked
            return "account.jsp"; // returns account page
        }else{
            // send back text saying please try again
            request.setAttribute("message", "Login Failed <br />Please try again");
            return "login.jsp"; // returns login page with error message
        }
        
        
    }
    
}
