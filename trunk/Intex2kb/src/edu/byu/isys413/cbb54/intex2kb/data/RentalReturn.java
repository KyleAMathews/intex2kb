/*
 * RentalReturn.java
 *
 * Created on April 3, 2007, 12:16 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Object representing a transaction line for the return of a rental
 * @author Bryan
 */
public class RentalReturn {
    
    private boolean dirty = false;
    private boolean isInDB = false;
    private String rentalid;
    private String id;
    private long datein;
    
    /**
     * Creates a new instance of RentalReturn
     * @param id GUID of Rental Return
     * @param rentalid GUID of Rental
     */
    public RentalReturn(String id, String rentalid) {
        this.id = id;
        this.rentalid = rentalid;
        this.dirty = true;
    }

    /**
     * Boolean representing if the object has changed since being saved
     * to the database
     * @return Boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the boolean representing if the object has been changed since
     * being saved to the database
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Boolean representing if the object is in the database
     * @return Boolean isInDB
     */
    public boolean isIsInDB() {
        return isInDB;
    }

    /**
     * Sets the boolean representing if the object is in the database
     * @param isInDB Boolean
     */
    public void setIsInDB(boolean isInDB) {
        this.isInDB = isInDB;
        this.dirty = true;
    }

    /**
     * Returns the ID of the Rental Return object
     * @return String GUID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the Date the rental was returned
     * @return Date datein
     */
    public long getDatein() {
        return datein;
    }

    /**
     * Sets the date the rental was returned
     * @param datein Date
     */
    public void setDatein(long datein) {
        this.datein = datein;
        this.dirty = true;
    }

    /**
     * Returns the ID of the rental the return is for
     * @return GUID
     */
    public String getRentalid() {
        return rentalid;
    }

    /**
     * Sets the ID of the rental the return is for
     * @param rentalid GUID
     */
    public void setRentalid(String rentalid) {
        this.rentalid = rentalid;
        this.dirty = true;
    }
    
}
