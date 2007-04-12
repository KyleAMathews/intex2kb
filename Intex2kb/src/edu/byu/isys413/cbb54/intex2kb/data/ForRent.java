/*
 * ForRent.java
 *
 * Created on April 3, 2007, 12:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Bryan
 */
public class ForRent {
    
    private String id;
    private int timesrented;
    private String currentrental;
    private boolean dirty = false;
    private boolean indb = false;
    
    /** Creates a new instance of ForRent */
    public ForRent(String id) {
        this.id = id;
    }

    /**
     * returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * returns the times rented of the object
     */
    public int getTimesrented() {
        return timesrented;
    }

    /**
     * sets the times rented of the object
     */
    public void setTimesrented(int timesrented) {
        this.timesrented = timesrented;
        this.dirty = true;
    }

    /**
     * returns the current rental id
     */
    public String getCurrentrental() {
        return currentrental;
    }

    /**
     * sets the current rental id
     */
    public void setCurrentrental(String currentrental) {
        this.currentrental = currentrental;
        this.dirty = true;
    }

    /**
     * returns the dirty variable
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * sets the dirty variable
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * returns the database variable
     */
    public boolean isIndb() {
        return indb;
    }

    /**
     * sets the database variable
     */
    public void setIndb(boolean indb) {
        this.indb = indb;
    }
    
}
