package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.validateLogin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * loginWeb.java
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
public class loginWeb implements edu.byu.isys413.web.Action{
    
    boolean pass = false;
    String email;
    String password;
    /**
     * Creates a new instance of loginWeb
     */
    public loginWeb() {
        
    }
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        // get password / email
        email = (String)request.getAttribute("email");
        password = (String)request.getAttribute("password");
        System.out.println(password + " " + email);
        
        try {
            pass = validateLogin.getInstance().validate(email, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if(pass == true){
            request.setAttribute("message", "Login passed");
            request.setAttribute("login", "1");
            // send back javascript or whatever that switches parent frame to main screen
        }else{
            // send back text saying please try again
            request.setAttribute("message", "Login Failed <br />Please try again");
            request.setAttribute("login", "0");
        }
        
        return "login.jsp";
    }
    
}
