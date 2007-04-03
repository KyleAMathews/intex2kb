package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Signals that there are no more connections left in the pool.
 *
 * @author conan@warp.byu.edu
 */
public class ConnectionPoolException extends Exception {
  
  /** Creates a new instance of ConnectionPoolException */
  public ConnectionPoolException(String s) {
    super(s);
  }//constructor
  
  /** Creates a new instance of ConnectionPoolException, with an embedded exception */
  public ConnectionPoolException(String s, Throwable t) {
    super(s, t);
  }//constructor
  
}//class
