/*
 * repair.java
 *
 * Created on March 31, 2007, 12:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * This class will create Repair Revenue Sources.
 * It also sets and gets all the values associated with a repair object
 * @author kyle
 */
public class repair extends RevenueSource{
    
    private long dateStarted;
    private long dateCompleted;
    private String description;
    private double laborHours;
    private String employeeID;
    private long datePickedUp;
    
    /**
     * Creates a new instance of repair
     * @param id GUID for the new Repair
     */
    public repair(String id) {
        this.id = id;
    }
    
    /**
     * Returns the data the Repair order was created
     * @return Date started
     */
    public long getDateStarted() {
        return dateStarted;
    }
    
    /**
     * Sets the date the Repair was started
     * @param dateStarted Date the Repair was started
     */
    public void setDateStarted(long dateStarted) {
        this.dirty = true;
        this.dateStarted = dateStarted;
    }
    
    /**
     * Returns the date the Repair was completed
     * @return Date Completed
     */
    public long getDateCompleted() {
        return dateCompleted;
    }
    
    /**
     * Sets the date the Repair was completed
     * @param dateCompleted Date Completed
     */
    public void setDateCompleted(long dateCompleted) {
        this.dirty = true;
        this.dateCompleted = dateCompleted;
    }
    
    /**
     * Returns the description for the Repair
     * @return Description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description for the Repair
     * @param description String
     */
    public void setDescription(String description) {
        this.dirty = true;
        this.description = description;
    }
    
    /**
     * Returns the hours of labor for the Repair
     * @return Hours of Labor
     */
    public double getLaborHours() {
        return laborHours;
    }
    
    /**
     * Sets the hours of labor for the Repair
     * @param laborHours Number of hours
     */
    public void setLaborHours(double laborHours) {
        this.dirty = true;
        this.laborHours = laborHours;
    }
    
    /**
     * Returns the employee id of the employee who created the repair ticket
     * @return Employee ID
     */
    public String getEmployeeID() {
        return employeeID;
    }
    
    /**
     * Sets the employee id to the ID of the employee who created the repair ticket
     * @param employeeID String
     */
    public void setEmployeeID(String employeeID) {
        this.dirty = true;
        this.employeeID = employeeID;
    }
    
    /**
     * Returns the date the Repair was picked up by the customer
     * @return Date picked up
     */
    public long getDatePickedUp() {
        return datePickedUp;
    }
    
    /**
     * Sets the date the customer picked up the repair
     * @param datePickedUp Date
     */
    public void setDatePickedUp(long datePickedUp) {
        this.dirty = true;
        this.datePickedUp = datePickedUp;
    }
    
}
