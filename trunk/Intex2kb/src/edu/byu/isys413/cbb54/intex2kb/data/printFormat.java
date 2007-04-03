/*
 * printFormat.java
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
public class printFormat {
    
    private String id;
    private String size;
    private String paperType;
    private String sourceType;
    private double price;
    private boolean inDB;
    private boolean dirty;
    
    /**
     * Creates a new instance of printFormat
     */
    public printFormat(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
        this.dirty = true;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
        this.dirty = true;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
        this.dirty = true;
    }

    public boolean getIsInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    public boolean isDirty() {
        return dirty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.dirty = true;
    }
    
}
