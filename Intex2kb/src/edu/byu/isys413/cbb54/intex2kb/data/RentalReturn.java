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
 *
 * @author Bryan
 */
public class RentalReturn {
    
    private boolean dirty = false;
    private boolean isInDB = false;
    private String rentalid;
    private String id;
    private long datein;
    
    /** Creates a new instance of RentalReturn */
    public RentalReturn(String id, String rentalid) {
        this.id = id;
        this.rentalid = rentalid;
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isIsInDB() {
        return isInDB;
    }

    public void setIsInDB(boolean isInDB) {
        this.isInDB = isInDB;
        this.dirty = true;
    }

    public String getId() {
        return id;
    }

    public long getDatein() {
        return datein;
    }

    public void setDatein(long datein) {
        this.datein = datein;
        this.dirty = true;
    }

    public String getRentalid() {
        return rentalid;
    }

    public void setRentalid(String rentalid) {
        this.rentalid = rentalid;
        this.dirty = true;
    }
    
}
