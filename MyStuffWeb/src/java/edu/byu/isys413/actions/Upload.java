/*
 * Upload.java
 *
 * Created on April 9, 2007, 10:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kyle
 */
public class Upload implements edu.byu.isys413.web.Action{
    
    /**
     * Creates a new instance of Upload
     */
    public Upload() {
    }
    
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        // uses Albrechts file upload code
        
        return "something.jsp";
    }
}
