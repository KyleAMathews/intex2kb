/*
 * MemberInterestDAO.java
 *
 * Created on February 26, 2007, 6:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * This DAO handels all interactions to the database for membership interests
 * @author Cameron
 */
public class MemberInterestDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static MemberInterestDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private MemberInterestDAO() {
    }
    
    /**
     * Singleton pattern to ensure only one MembershipInterestDAO
     * @return MembershipInterestDAO
     */
    public static synchronized MemberInterestDAO getInstance() {
        if (instance == null) {
            instance = new MemberInterestDAO();
        }
        return instance;
    }
    
    
    /////////////////////////////////
    ///   CREATE
    
    /**
     * Creates membership - interest associations by saving them to the database
     * @param memID String
     * @param list List of Interests
     * @throws java.lang.Exception Thrown when there is an error getting a database connection or executing the SQL
     */
    public void create(String memID, List list) throws Exception{
        
        // get the connection
        Connection conn = null;
        try{
            // retrieve a database connection from the pool
            conn = ConnectionPool.getInstance().get();
            
            save(memID, list, conn);
            
            conn.commit();
            ConnectionPool.getInstance().release(conn);

        }catch (ConnectionPoolException e){
            throw new DataException("Could not get a connection to the database.");

        }catch (SQLException e) {
            // rollback
            try {
                conn.rollback();
                ConnectionPool.getInstance().release(conn);
            }catch (ConnectionPoolException ce){
                throw new DataException("There was an error with the connection to the database", ce);
            }catch (SQLException e2) {
                throw new DataException("Big error: could not even release the connection", e2);
            }

            throw new DataException("Could not retrieve customer records form the database",  e);
        }
        
    }
    /**
     * Creates membership - interest associations by saving them to the database
     * @param memID String
     * @param list List of Interests
     * @param conn Database Connection
     * @throws java.lang.Exception Thrown when there is an error getting a database connection or executing the SQL
     */
        public void create(String memID, List list, Connection conn) throws Exception{
        

        try{

            
            save(memID, list, conn);
            
            conn.commit();

        }catch (SQLException e) {
            // rollback
            try {
                conn.rollback();
                ConnectionPool.getInstance().release(conn);
            }catch (ConnectionPoolException ce){
                throw new DataException("There was an error with the connection to the database", ce);
            }catch (SQLException e2) {
                throw new DataException("Big error: could not even release the connection", e2);
            }

            throw new DataException("Could not retrieve customer records form the database",  e);
        }

    }

    /////////////////////////////////////
    ///   READ
    
    /**
     * Returns a List of interest GUIDs for a specified member
     * @param id String
     * @param conn Database Connection
     * @return List of Interest GUIDs
     * @throws edu.byu.isys413.cbb54.intex2.data.DataException Thrown when there is an error getting a database connection or executing the SQL
     */
    public synchronized List<String> read(String id, Connection conn) throws DataException {
        List<String> list = new LinkedList<String>();
        
       
        try{

            
            // sql the names, phone, and ids
            PreparedStatement read = conn.prepareStatement(
                "SELECT \"memberID\", \"interestID\" FROM \"memberinterests\"" +
                    "WHERE \"memberID\" = ? ");
            read.setString(1, id);
            ResultSet rs = read.executeQuery();
            
            // release the connection
            conn.commit();

            
            
//            // while loop to populate the list from the results
//            while(rs.next()) {
//                List<String> clist = new LinkedList();
//                clist.add(rs.getString("memberID"));
//                clist.add(rs.getString("interestID"));
//                list.add(clist);
//            }    
            
            while(rs.next()){
                list.add(rs.getString("interestID"));
            }

        }catch (SQLException e) {
            // rollback
            try {
                conn.rollback();
                ConnectionPool.getInstance().release(conn);
            }catch (ConnectionPoolException ce){
                throw new DataException("There was an error with the connection to the database", ce);
            }catch (SQLException e2) {
                throw new DataException("Big error: could not even release the connection", e2);
            }

            throw new DataException("Could not retrieve customer records form the database",  e);
        }

        return list;
    }
    
    
    //////////////////////////////////
    ///   UPDATE
    
    /** 
     * This is the public save method.  It is what is called when
     * the user (controller) code wants to save or update an object
     * into the database.
     */
    private synchronized void save(String id, List list, Connection conn) throws DataException {
        
        try {

            // call delete() to clear the database of member/interests for one member
            delete(id, conn);
            
            // call create with a connection
            for(int i = 0; i < list.size(); i++){
                insert(id, list.get(i).toString(), conn);
            }
            
        }catch (SQLException e) {
            
            // rollback
            try {
                conn.rollback();
                ConnectionPool.getInstance().release(conn);
            }catch (ConnectionPoolException ce){
                throw new DataException("There was an error with the connection to the database", ce);
            }catch (SQLException e2) {
                throw new DataException("Big error: could not even release the connection", e2);
            }
            
            throw new DataException("Could not create a Member/Interest entry", e);
        }catch (Exception e3) {
            throw new DataException("There was a error in the formating of the Member/Interest List", e3);
        }
        
    } // End of Save()

    
    /**
     * This method is really a part of save(bo, conn) above.  It could be
     * embedded directly in it, but I've separated it into it's own method
     * to isolate the SQL insert statement and make it more readable.  But
     * logically, it's really just a part of save.
     */
    private synchronized void insert(String memID, String intID, Connection conn) throws SQLException, DataException {
        try {
            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"memberinterests\" VALUES(?,?)");
            insert.setString(1, memID);
            insert.setString(2, intID);
            insert.execute();

            // release the connection
            conn.commit();



        }catch (SQLException e) {
            // rollback
            try {
                conn.rollback();
                ConnectionPool.getInstance().release(conn);
            }catch (ConnectionPoolException ce){
                throw new DataException("There was an error with the connection to the database", ce);
            }catch (SQLException e2) {
                throw new DataException("Big error: could not even release the connection", e2);
            }

            throw new DataException("Could not create a Membership/Interest entry" + e);
        }
    
    }
    
    
    ////////////////////////////////////
    ///   DELETE
    
    private synchronized void delete(String memID, Connection conn) throws SQLException, DataException {
        // do the insert SQL statement
        PreparedStatement insert = conn.prepareStatement(
            "DELETE FROM \"memberinterests\" WHERE \"memberID\" = ?");
        insert.setString(1, memID);

        // execute and commit the query
        insert.executeUpdate();
        conn.commit();
    }
    
    
    
    //////////////////////////////
    ///  SEARCH methods
    
    
    
    
}
