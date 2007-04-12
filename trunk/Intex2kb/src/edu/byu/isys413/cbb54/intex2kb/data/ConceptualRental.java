/*
 * ConceptualRental.java
 *
 * Created on April 3, 2007, 4:06 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Bryan
 */
public class ConceptualRental {
    
    private String id;
    private double price;
    private double cost;
    private double late;
    private int maxrent;
    private int maxlate;
    private boolean dirty = false;
    private boolean indb = false;
    
    /** Creates a new instance of ConceptualRental */
    public ConceptualRental(String id) {
        this.id = id;
    }

    /**
     * Returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price
     */
    public void setPrice(double price) {
        this.price = price;
        this.dirty = true;
    }

    /**
     * returns the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * sets the cost
     */
    public void setCost(double cost) {
        this.cost = cost;
        this.dirty = true;
    }

    /**
     * returns the late day
     */
    public double getLate() {
        return late;
    }

    /**
     * sets the late days
     */
    public void setLate(double late) {
        this.late = late;
        this.dirty = true;
    }

    /**
     * gets the maximum days for rent
     */
    public int getMaxrent() {
        return maxrent;
    }

    /**
     * sets the maximum days for rent
     */
    public void setMaxrent(int maxrent) {
        this.maxrent = maxrent;
        this.dirty = true;
    }

    /**
     * gets the maximum days it can be late
     */
    public int getMaxlate() {
        return maxlate;
    }

    /**
     * sets the maximum days it can be late
     */
    public void setMaxlate(int maxlate) {
        this.maxlate = maxlate;
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
