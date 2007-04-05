package edu.byu.isys413.web;

import javax.servlet.http.*;

/**
 * An interface representing an action called from the Controller.java
 * controller.  All actions run in the web system must implement this
 * interface.  Actions must also have a no-arg (empty) constructor.
 *
 * @author Conan C. Albrecht
 */
public interface Action {
  
  // Note: be sure to have a no-arg constructor in your class
  
  /**
   *  Responds to an action call from the Controller.java file.
   *  This method should perform any work required, then return
   *  a new JSP page to call.
   *
   *  @param request  The HttpServletRequest object that represents
   *                  information sent from the browser.  The getParameter()
   *                  method is particularly useful.
   *
   *  @param response The HttpServletResponse object that represents
   *                  information sent back to the browser.  Normally
   *                  you won't use this object as the JSP page that
   *                  gets forwarded to sends information back to the 
   *                  browser.
   *
   *  @returns        A string giving the path of the JSP file to call
   *                  after this action is performed.  If you need this
   *                  to be dynamic, use hidden form field to send
   *                  a parameter giving the page to go to after the
   *                  action.  
   *
   *                  If the path starts with "/", the path is 
   *                  absolute to the application context.  If the 
   *                  path doesn't start with "/", it is relative
   *                  to the current page (dangerous!).
   */
  public String process(HttpServletRequest request, HttpServletResponse response)
    throws Exception;
  
}
