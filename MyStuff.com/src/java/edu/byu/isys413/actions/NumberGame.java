/*
 * NumberGame.java
 *
 * Created on March 26, 2007, 7:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import java.util.Random;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author conan
 */
public class NumberGame implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public NumberGame() {
    }
  
    /** Processes a number guess */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        int guess = Integer.parseInt(request.getParameter("guess"));
        int realnum = ((Integer)session.getAttribute("secretnumber")).intValue();
        if (guess == realnum) {
            request.setAttribute("message", "You got it!  It was " + guess + ".  Let's play again.  I've already got another number.");
            session.setAttribute("secretnumber", new Integer(new Random().nextInt(101)));
        }else if (guess > realnum) {
            request.setAttribute("message", "Try a lower number than " + guess);
        }else {
            request.setAttribute("message", "Try a higher number than " + guess);
        }//if
        
        return "NumberGame.jsp";
    }//process
    
    
}//NumberGame class
