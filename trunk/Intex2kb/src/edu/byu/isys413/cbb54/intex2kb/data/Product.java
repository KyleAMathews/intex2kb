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

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.setDirty(false);
        this.price = price;
    }

    public boolean isInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
