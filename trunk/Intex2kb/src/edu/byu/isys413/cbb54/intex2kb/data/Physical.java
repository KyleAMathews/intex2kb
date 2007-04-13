/*
 * Physical.java
 *
 * Created on April 2, 2007, 3:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public class Physical extends Product{
    
    private String serialNum;
    private String shelfLocation;
    private String conceptualid;
    private boolean forSale;
    
    /** Creates a new instance of Physical */
    public Physical(String id) {
        this.setId(id);
    }

    /**
     * returns serial number
     */
    public String getSerialNum() {
        return serialNum;
    }
    
    /**
     * set serial number
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
        this.setDirty(true);
    }

    /**
     * return shelf location
     */
    public String getShelfLocation() {
        return shelfLocation;
    }

    /**
     * set shelf location
     */
    public void setShelfLocation(String shelfLocation) {
        this.setDirty(true);
        this.shelfLocation = shelfLocation;
    }

    /**
     * return for sale variable
     */
    public boolean isForSale() {
        return forSale;
    }

    /**
     * set for sale variable
     */
    public void setForSale(boolean forSale) {
        this.setDirty(true);
        this.forSale = forSale;
    }

    /**
     * return conceptualid
     */
    public String getConceptualid() {
        return conceptualid;
    }

    /**
     * set conceptualid
     */
    public void setConceptualid(String conceptualid) {
        this.conceptualid = conceptualid;
    }
    
    
    
}
