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

    public String getId() {
        return id;
    }

    public int getTimesrented() {
        return timesrented;
    }

    public void setTimesrented(int timesrented) {
        this.timesrented = timesrented;
        this.dirty = true;
    }

    public String getCurrentrental() {
        return currentrental;
    }

    public void setCurrentrental(String currentrental) {
        this.currentrental = currentrental;
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isIndb() {
        return indb;
    }

    public void setIndb(boolean indb) {
        this.indb = indb;
    }
    
}
