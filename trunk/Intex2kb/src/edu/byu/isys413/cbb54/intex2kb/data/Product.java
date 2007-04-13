/*
 * Product.java
 *
 * Created on April 2, 2007, 3:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public abstract class Product {
    
    private String id;
    private double price;
    private boolean inDB;
    private boolean dirty;
    
    /** Creates a new instance of Product */
    public Product() {
    }

    /**
     * returns the id of the product
     */
    public String getId() {
        return id;
    }

    /**
     * returns the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price of the product
     */
    public void setPrice(double price) {
        this.setDirty(false);
        this.price = price;
    }

    /**
     * returns the database variable
     */
    public boolean isInDB() {
        return inDB;
    }

    /**
     * sets the database variable
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    /**
     * returns the dirty variable
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * sets the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * sets the dirty variable
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
