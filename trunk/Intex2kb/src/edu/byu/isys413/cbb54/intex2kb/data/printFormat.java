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
 * PrintFormat refers to the paper size, paper type, and development type of a print order
 * @author Tyler
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
     * @param id GUID
     */
    public printFormat(String id) {
        this.id = id;
    }

    /**
     * get printformat ID
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * get paper size
     * @return size
     */
    public String getSize() {
        return size;
    }

    /**
     * set paper size
     * @param size size
     */
    public void setSize(String size) {
        this.size = size;
        this.dirty = true;
    }

    /**
     * getPaperType
     * @return paperType
     */
    public String getPaperType() {
        return paperType;
    }

    /**
     * setPaperType
     * @param paperType paperType
     */
    public void setPaperType(String paperType) {
        this.paperType = paperType;
        this.dirty = true;
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
        this.dirty = true;
    }

    /**
     * getIsInDB
     * @return boolean IsInDB
     */
    public boolean getIsInDB() {
        return inDB;
    }

    /**
     * setInDB
     * @param inDB inDB
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    /**
     * isDirty
     * @return isDirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * getPrice
     * @return Price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setPrice
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
        this.dirty = true;
    }
    
}
