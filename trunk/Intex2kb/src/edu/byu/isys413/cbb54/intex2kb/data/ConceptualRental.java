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

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.dirty = true;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
        this.dirty = true;
    }

    public double getLate() {
        return late;
    }

    public void setLate(double late) {
        this.late = late;
        this.dirty = true;
    }

    public int getMaxrent() {
        return maxrent;
    }

    public void setMaxrent(int maxrent) {
        this.maxrent = maxrent;
        this.dirty = true;
    }

    public int getMaxlate() {
        return maxlate;
    }

    public void setMaxlate(int maxlate) {
        this.maxlate = maxlate;
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isIndb() {
        return indb;
    }

    public void setIndb(boolean indb) {
        this.indb = indb;
    }
    
}
