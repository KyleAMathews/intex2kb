/*
 * OrderLine.java
 *
 * Created on April 10, 2007, 2:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public class OrderLine {
    
    private String id;
    private PurchaseOrder purchaseOrder;
    private Product product;
    private int quantity;
    private boolean inDB;
    private boolean dirty;
    
    /** Creates a new instance of OrderLine */
    public OrderLine(String id, PurchaseOrder po) {
        this.id = id;
        this.purchaseOrder = po;
    }

    /**
     * Returns the id of the orderline
     */
    public String getId() {
        return id;
    }

    /**
     * returns the purchase order
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * sets the purchase order
     */
    public void setPurchaseOrder(PurchaseOrder PurchaseOrder) {
        this.purchaseOrder = PurchaseOrder;
        this.dirty = true;
    }

    /**
     * returns the produce
     */
    public Product getProduct() {
        return product;
    }

    /**
     * sets the product
     */
    public void setProduct(Product product) {
        this.product = product;
        this.dirty = true;
    }

    /**
     * returns the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * sets the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dirty = true;
    }

    /**
     * returns the database boolean
     */
    public boolean isInDB() {
        return inDB;
    }

    /**
     * sets the database boolean
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
     * sets the dirty boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
