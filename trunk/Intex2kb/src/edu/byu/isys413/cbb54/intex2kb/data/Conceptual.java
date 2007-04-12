/*
 * Conceptual.java
 *
 * Created on April 2, 2007, 4:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public class Conceptual extends Product{
    
    private String name;
    private String desc;
    private double avgCost;
    
    /** Creates a new instance of Conceptual */
    public Conceptual(String id) {
        this.setId(id);
    }

    /**
     * Returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     */
    public void setName(String name) {
        this.setDirty(true);
        this.name = name;
    }

    /**
     * Returns the description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the description
     */
    public void setDesc(String desc) {
        this.setDirty(true);
        this.desc = desc;
    }

    /**
     * Returns the average cost
     */
    public double getAvgCost() {
        return avgCost;
    }

    /**
     * Sets the average cost
     */
    public void setAvgCost(double avgCost) {
        this.setDirty(true);
        this.avgCost = avgCost;
    }
    
    /**
     * Returns the price
     */
    public double getPrice(){
        return super.getPrice();
    }
    
}
