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
 *
 * @author kyle
 */
public class repair extends RevenueSource{
    
    private long dateStarted;
    private long dateCompleted;
    private String description;
    private double laborHours;
    private String employeeID;
    private long datePickedUp;
    
    /** Creates a new instance of repair */
    public repair(String id) {
        this.id = id;
    }
    
    public long getDateStarted() {
        return dateStarted;
    }
    
    public void setDateStarted(long dateStarted) {
        this.dirty = true;
        this.dateStarted = dateStarted;
    }
    
    public long getDateCompleted() {
        return dateCompleted;
    }
    
    public void setDateCompleted(long dateCompleted) {
        this.dirty = true;
        this.dateCompleted = dateCompleted;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.dirty = true;
        this.description = description;
    }
    
    public double getLaborHours() {
        return laborHours;
    }
    
    public void setLaborHours(double laborHours) {
        this.dirty = true;
        this.laborHours = laborHours;
    }
    
    public String getEmployeeID() {
        return employeeID;
    }
    
    public void setEmployeeID(String employeeID) {
        this.dirty = true;
        this.employeeID = employeeID;
    }
    
    public long getDatePickedUp() {
        return datePickedUp;
    }
    
    public void setDatePickedUp(long datePickedUp) {
        this.dirty = true;
        this.datePickedUp = datePickedUp;
    }
    
}
