package edu.byu.isys413.actions;

//import edu.byu.isys413.cbb54.intex2kb.data.validateLogin;
import edu.byu.isys413.cbb54.intex2kb.data.Transaction;
import edu.byu.isys413.cbb54.intex2kb.data.TransactionDAO;
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
    String membid;
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
        membid = validateLogin.getInstance().membid(email, password);
        System.out.println("I'm in loginweb.java " + password + " " + email);
        System.out.println("membid: " + membid);
        
        pass = validateLogin.getInstance().validate(email, password);
        System.out.println("validation value = " + pass);
        
        if(pass == true){
            // set login guid (not yet implemented)
            
            // set customerid in cookie
            session.setAttribute("membid", membid);
            return "account.jsp"; // returns account page
        }else{
            // send back text saying please try again
            request.setAttribute("message", "Login Failed <br />Please try again");
            return "login.jsp"; // returns login page with error message
        }
        
        
    }
    
}
