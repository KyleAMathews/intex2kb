/*
 * RepairDAO.java
 *
 * Created on March 30, 2007, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author kyle
 */
public class RepairDAO extends RSDAO{
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static RepairDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private RepairDAO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of RentalDAO
     * @return RentalDAO
     */
    public static synchronized RepairDAO getInstance() {
        if (instance == null) {
            instance = new RepairDAO();
        }
        return instance;
    }
    
    ///////////////////////////////////////////
    /// Create
    
     public RevenueSource create() throws Exception{
        String id = GUID.generate("rp");
        RevenueSource rs = new repair(id);
        System.out.println("I've created a RepairBO  :  ID: " + rs.getId());
        return rs;
    }
    
       ///////////////////////////////////////////
    /// Read
    
    public RevenueSource read(String id, Connection conn) throws Exception{
        repair rp = new repair(id);
        
        PreparedStatement read = conn.prepareStatement(
                "SELECT * FROM \"serviceRepair\" WHERE \"id\" = ?");
        read.setString(1, id);
        ResultSet rs = read.executeQuery();
        
        // set variables
        rp.setDateStarted(rs.getLong("dateStarted"));
        rp.setDateCompleted(rs.getLong("dateEnded"));
        rp.setDescription(rs.getString("description"));
        rp.setLaborHours(rs.getDouble("laborHours"));
        rp.setEmployeeID(rs.getString("employeeID"));
        rp.setDatePickedUp(rs.getLong("datePickedUp"));
        rp.setPrice(rs.getDouble("price"));
        
        // return the RevenueSource
        RevenueSource rst = (RevenueSource)rp;
        return rst;
    }
    
///////////////////////////////////////////
/// Save
    
    public void save(RevenueSource rsbo, Connection conn) throws Exception{
        // check the dirty flag in the object.  if it is dirty,
        // run update or insert
        if (rsbo.isDirty()) {
            if (rsbo.isInDB()) {
                update(rsbo, conn);
            }else{
                insert(rsbo, conn);
            }
            
            // set the dirty flag to false now that we've saved it
            rsbo.setDirty(false);
            
        }
    }
    
    public void insert(RevenueSource rsbo, Connection conn) throws Exception{
        System.out.println("inserting backup");
        repair rp = (repair)rsbo;
        PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO \"serviceRepair\" VALUES (?,?,?,?,?,?,?,?)");
        insert.setString(1, rp.getId());
        insert.setDouble(2, rp.getDateStarted());
        insert.setDouble(3, rp.getDateCompleted());
        insert.setString(4, rp.getDescription());
        insert.setDouble(5, rp.getLaborHours());
        insert.setString(6, rp.getEmployeeID());
        insert.setLong(7, rp.getDatePickedUp());
        insert.setDouble(8, rp.getPrice());
        insert.executeUpdate();
        rsbo.setInDB(true);
    }
    
    public void update(RevenueSource rsbo, Connection conn) throws Exception{
        repair rp = (repair)rsbo;
        PreparedStatement update = conn.prepareStatement(
                "UPDATE \"serviceRepair\" SET \"dateStarted\"=?, \"dateEnded\" = ?, \"description\" = ?, \"laborHours\" = ?,\"employeeID\" = ?," +
                "\"datePickedUp\" = ?,\"price\" = ?  WHERE \"id\" = ?");
        update.setLong(1, rp.getDateStarted());
        update.setLong(2, rp.getDateCompleted());
        update.setString(3, rp.getDescription());
        update.setDouble(4, rp.getLaborHours());
        update.setString(5, rp.getEmployeeID());
        update.setLong(6, rp.getDatePickedUp());
        update.setDouble(7, rp.getPrice());
        update.setString(8, rp.getId());
        update.executeUpdate();
    }
    
//////////////////////////////////////////
/// delete
    
// for business reasons we're not supporting deleting
    
    
}
