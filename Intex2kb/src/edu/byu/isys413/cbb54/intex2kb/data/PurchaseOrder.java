/*
 * PurchaseOrder.java
 *
 * Created on April 10, 2007, 1:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.util.List;

/**
 *
 * @author Cameron
 */
public class PurchaseOrder {
    
    private String id;
    private long date;
    private Vendor vendor;
    private List<OrderLine> OrderLines;
    private Store store;
    private boolean dirty;
    private boolean inDB;
    
    /** Creates a new instance of PurchaseOrder */
    public PurchaseOrder(String id, Vendor vendor) {
        this.id = id;
        this.vendor = vendor;
    }

    /**
     * returns the id of a purchase order
     */
    public String getId() {
        return id;
    }

    /**
     * returns the date of the purchase order
     */
    public long getDate() {
        return date;
    }

    /**
     * sets the date of the purchase order
     */
    public void setDate(long date) {
        this.date = date;
        this.dirty = true;
    }

    /**
     * return the vendor
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * sets the vendor
     */
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
        this.dirty = true;
    }

    /**
     * returns the order lines
     */
    public List<OrderLine> getOrderLines() {
        return OrderLines;
    }

    /**
     * sets the order lines
     */
    public void setOrderLines(List<OrderLine> ol){
        this.OrderLines = ol;
        this.dirty = true;
    }
    
    /**
     * add orderline to the list
     */
    public void addOrderLine(OrderLine ol) {
        OrderLines.add(ol);
        this.dirty = true;
    }

    /**
     * return the dirty variable
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
     * return the store variable
     */
    public Store getStore() {
        return store;
    }

    /**
     * set the store variable
     */
    public void setStore(Store store) {
        this.store = store;
        this.dirty = true;
    }
    
}
