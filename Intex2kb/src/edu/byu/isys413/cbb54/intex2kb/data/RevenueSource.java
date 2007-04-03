/*
 * RevenueSource.java
 *
 * Created on March 12, 2007, 2:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public abstract class RevenueSource {
    
    String id;
    Double price;
    boolean dirty = false;
    boolean isInDB = false;
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double price){
        this.dirty = true;
        this.price = price;
    }

    public boolean isDirty() {
        return dirty;
    }
    
    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }

    public boolean isInDB() {
        return isInDB;
    }

    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }



    public String getId() {
        return id;
    }
    
}
