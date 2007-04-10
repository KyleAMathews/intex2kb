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
public class photo implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public photo() {
    }
  
    /** Processes a number guess */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        HttpSession session = request.getSession();
        
        
        
        return "photo.jsp";
    }//process
    
    
}//NumberGame class
