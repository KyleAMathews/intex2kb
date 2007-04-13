package edu.byu.isys413.web;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;
/**
 * The primary controller for all actions in the system.  This
 * controller looks at the URL and calls the appropriate Action
 * given in the URL.  This object must implement the Action.java
 * interface.
 *
 * To enable this controller, do the following:
 *
 *  1. Modify the package statement above to match your packages.
 *     Be sure this file is in the appropriate directory that matches
 *     the package statement.
 *
 *  2. Modify your web.xml file with the following code (note
 *     you'll need to modify the package to match #1):
            <servlet>
                <servlet-name>Controller</servlet-name>
                <servlet-class>edu.byu.isys413.web.Controller</servlet-class>
            </servlet>
            <servlet-mapping>
                <servlet-name>Controller</servlet-name>
                <url-pattern>*.action</url-pattern>
            </servlet-mapping>
 *     The above XML commands specify that any URL ending with
 *     the string ".action" should call the Controller.java
 *     controller.
 *
 * This system is similar to Struts, only simpler.  It is not
 * entirely secure.  In the real world, you'd probably want 
 * to ues Jave Server Faces or Struts or Tapestry or some other 
 * framework.
 * 
 * @author Conan C. Albrecht
 * @version 1.0
 */
public class Controller extends HttpServlet {
  
   
  /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException 
     * @throws java.io.IOException 
     */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher dispatcher = null;
    try {
      // get the action class to be called
      // to do this, we grab the URL and take the ".action" off of it
      String servletpath = request.getServletPath().substring(1);
      int dot = servletpath.lastIndexOf(".action");
      if (dot <= 0) {
        throw new ServletException("Invalid action.  No method specified");
      }
      String className = servletpath.substring(0, dot);
      System.out.println(className);
      // get an instance of the action
      Class klass = Class.forName(className);
      Action action = (Action)klass.newInstance();
      
      // call the action
      String viewPath = action.process(request, response);
      
      // get a dispatcher to the view
      dispatcher = request.getRequestDispatcher(viewPath);
      
    }catch (Exception e) {
      // this is wierd.  from a servlet, tomcat seems to inspect the embedded
      // exception, not the main exception.  so i'm embedding a WebException
      // inside a regular ServletException.  this makes tomcat (per web.xml)
      // call the /error.jsp page.
      throw new ServletException(new WebException("Error in controller: " + e.getMessage(), e));
    }
    
    // forward to the JSP page
    dispatcher.forward(request, response);
    
  }
  
  /** Handles the HTTP <code>GET</code> method.
   * @param request servlet request
   * @param response servlet response
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    processRequest(request, response);
  }
  
  /** Handles the HTTP <code>POST</code> method.
   * @param request servlet request
   * @param response servlet response
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    processRequest(request, response);
  }
  
  /** Returns a short description of the servlet.
   */
  public String getServletInfo() {
    return "Short description";
  }
}
