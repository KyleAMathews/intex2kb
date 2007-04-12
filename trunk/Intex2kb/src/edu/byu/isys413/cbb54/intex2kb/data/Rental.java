/*
 * rental.java
 *
 * Created on March 30, 2007, 2:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Object representing the rental of a physical item
 * @author kyle
 */
public class Rental extends RevenueSource {
    
    private long dateOut;
    private long dateDue;
    
    
    /**
     * Creates a new instance of rental
     * @param id GUID
     */
    public Rental(String id) {
        this.id = id;
    }

    /**
     * Returns the date the rental was picked up
     * @return Date dateOut
     */
    public long getDateOut() {
        return dateOut;
    }

    /**
     * Sets the date the rental was picked up
     * @param dateOut Date
     */
    public void setDateOut(long dateOut) {
        this.dirty = true;
        this.dateOut = dateOut;
    }

    /**
     * Returns the date the rental is due
     * @return Date dateDue
     */
    public long getDateDue() {
        return dateDue;
    }

    /**
     * Sets the date the rental is due
     * @param dateDue Date 
     */
    public void setDateDue(long dateDue) {
        this.dirty = true;
        this.dateDue = dateDue;
    }
    
}
