package edu.byu.isys413.web;

import javax.servlet.*;

/**
 * A simple exception to indicate web exceptions.  If you throw
 * this exception from anywhere in a servlet, action, or JSP file,
 * Tomcat will call the error.jsp page (see web.xml).
 *
 * @author Conan C. Albrecht
 */
public class WebException extends ServletException {
  
  public WebException() {
    super();
  }
  
  public WebException(String message) {
    super(message);
  }
  
  public WebException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public WebException(Throwable cause) {
    super(cause);
  }
  
}
