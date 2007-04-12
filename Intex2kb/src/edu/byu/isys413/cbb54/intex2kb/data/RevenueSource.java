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
 * Object representing a source of revenue for each transaction line
 * @author Cameron
 */
public abstract class RevenueSource {
    
    String id;
    Double price;
    boolean dirty = false;
    boolean isInDB = false;
    
    /**
     * Returns the price of the revenue source
     * @return Double price
     */
    public double getPrice(){
        return price;
    }
    
    /**
     * Sets the price of the revenue source
     * @param price Double
     */
    public void setPrice(double price){
        this.dirty = true;
        this.price = price;
    }

    /**
     * Returns a boolean of whether the object has been changed since
     * being saved last
     * @return Boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }
    
    /**
     * Sets the boolean of whether the object has been changed since it
     * was last saved to the database
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }

    /**
     * Returns a boolean of whether the object is in the database
     * @return Boolean inDB
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Sets the boolean of whether the object is in the database
     * @param isInDB Boolean
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }



    /**
     * Returns the id of the revenue source
     * @return GUID
     */
    public String getId() {
        return id;
    }
    
}
