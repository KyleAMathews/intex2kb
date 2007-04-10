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

    public String getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
        this.dirty = true;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
        this.dirty = true;
    }

    public List<OrderLine> getOrderLines() {
        return OrderLines;
    }

    public void setOrderLines(List<OrderLine> ol){
        this.OrderLines = ol;
        this.dirty = true;
    }
    
    public void addOrderLine(OrderLine ol) {
        OrderLines.add(ol);
        this.dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
        this.dirty = true;
    }
    
}
