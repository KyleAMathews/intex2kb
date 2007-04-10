package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Version 2.0
 *
 * A connection pool for database connections.
 * Note that javax.sql has a standard connection
 * pool interface, but this does not implement
 * it.  This is a bare bones connection pool and
 * is not robust or efficient enough to be used
 * in the real world.  It is just for example
 * purposes, and it assumes that we need simplicity
 * over function.
 *
 * @author Conan C. Albrecht <conan@warp.byu.edu>
 */
public class ConnectionPool {

  private static String dbURL = "jdbc:sqlserver://128.187.72.113:1400;user=dbuser;password=dbuser";
  
  // right now we are forcing one connection only -- to ensure code is written correctly
  // this will up increased at deployment time
  private static final int MAX_CONNECTIONS = 10;

  
  //////////////////////////////////////////////
  ///   Singelton code
  
  /** The singelton instance of the class */
  private static ConnectionPool instance = null;
  
  /** Creates a new instance of ConnectionPool */
  private ConnectionPool() {
  }
  
  
  /** Returns the singelton instance of the ConnectionPool */
  public static synchronized ConnectionPool getInstance() {
    if (instance == null) {
      instance = new ConnectionPool();
    }//if
    return instance;
  }//getInstance
  
  
  /////////////////////////////////////////////
  ///   Connection factory
  
  private Connection createConnection() throws Exception {
    //Logger.global.info("Creating a new database connection in the pool.");
//    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
    Connection conn = DriverManager.getConnection(dbURL);
    conn.setAutoCommit(false);
    return conn;
  }//createConnection
  
  
  
  
  //////////////////////////////////////////////
  ///   Public methods
  
  List<Connection> freeConnections = new LinkedList<Connection>();
  List<Connection> usedConnections = new LinkedList<Connection>();

  
  /** Returns a connection to the database */
  public synchronized Connection get() throws ConnectionPoolException {
    // are we already at capacity right now?
    if (usedConnections.size() >= MAX_CONNECTIONS) {
      throw new ConnectionPoolException("Connection pool is at maximum capacity -- no more connections can be given out.  Please try again later.");
    }//if
      
    try {
      // do we have enough connections to assign one out?
      if (freeConnections.size() == 0) {
        freeConnections.add(createConnection());
      }//if

      // return the first free connection
      Connection conn = freeConnections.remove(0);
      usedConnections.add(conn);
      //Logger.global.info("Gave out a connection from the pool.  Free size is now: " + freeConnections.size() + "/" + (freeConnections.size() + usedConnections.size()));
      return conn; 
    }catch (Exception e) {
      throw new ConnectionPoolException("An error occurred while retrieving a database connection from the pool", e);
    }
  }//get
  
  
  /** Releases a connection that was previously in use */
  public synchronized void release(Connection conn) throws ConnectionPoolException {
    try {
      // be sure that this connection was committed (so it is at a fresh, new transaction)
      // if the user already committed, this will just no-op
      conn.commit();

      // first remove the connection from the used list
      usedConnections.remove(conn);

      // next add it back to the free connection list
      freeConnections.add(conn);
      //Logger.global.info("Released a connection back to the pool.  Free size is now: " + freeConnections.size() + "/" + (freeConnections.size() + usedConnections.size()));
    }catch (Exception e) {
      throw new ConnectionPoolException("An error occurred while releasing a database connection back to the pool", e);
    }
  }//release
  
  
  
  
  
  /** Main method for testing only. */
  public static void main(String args[]) {
    try {
      System.out.println("Getting a new connection");
      Connection c = ConnectionPool.getInstance().get();
      System.out.println("Retrieved: " + c);
      
      System.out.println("Returning the connection");
      ConnectionPool.getInstance().release(c);
      
      System.out.println("Retrieving another connection");
      Connection c2 = ConnectionPool.getInstance().get();
      System.out.println("Retrieved: " + c2);
      
      System.out.println("Retrieving a second connection");
      try {
        Connection c3 = ConnectionPool.getInstance().get();
        System.out.println("Retrieved: " + c3);
      }catch(ConnectionPoolException cpe) {
        System.out.println("Could not retrieve: " + cpe.getMessage());
      }
      
      System.out.println("Returning the connection");
      ConnectionPool.getInstance().release(c2);
      
    }catch (Exception e) {
      e.printStackTrace();
    }//try
  }// main
  
}//class
