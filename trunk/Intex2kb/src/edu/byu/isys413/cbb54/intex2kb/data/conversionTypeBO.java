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
 *
 * @author kyle
 */
public class conversionTypeBO{
    
    private String id;
    private String sourceType;
    private String destinationType;
    private double price;
    private boolean dirty;
    private boolean inDB;
    
    /** Creates a new instance of PhotoSet */
    public conversionTypeBO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
        this.setDirty(true);
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
        this.dirty = true;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean getIsInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }
    
}

//test