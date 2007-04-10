/*
 * PhotoSet.java
 *
 * Created on March 30, 2007, 2:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * conversionTypeBO
 * @author Tyler Farmer
 */
public class conversionTypeBO{
    
    private String id;
    private String sourceType;
    private String destinationType;
    private double price;
    private boolean dirty;
    private boolean inDB;
    
    /**
     * Creates a new instance of conversionType based on a known ID
     * @param id ID
     */
    public conversionTypeBO(String id) {
        this.id = id;
    }

    /**
     * get conversionTypeID
     * @return conversionTypeID
     */
    public String getId() {
        return id;
    }

    /**
     * getSourceType
     * @return sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * setSourceType
     * @param sourceType sourceType
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
        this.setDirty(true);
    }

    /**
     * getDestinationType
     * @return destinationType
     */
    public String getDestinationType() {
        return destinationType;
    }

    /**
     * setDestinationType
     * @param destinationType destinationType
     */
    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
        this.dirty = true;
    }

    /**
     * getPrice
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setPrice
     * @param price DOUBLE price
     */
    public void setPrice(double price) {
        this.price = price;
        this.dirty = true;
    }

    /**
     * isDirty records any changes in the object
     * @return boolean isDirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * setDirty
     * @param dirty dirty
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * getIsInDB
     * @return boolean is in DB
     */
    public boolean getIsInDB() {
        return inDB;
    }

    /**
     * setInDB
     * @param inDB boolean inDB
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }
    
}

//test