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

    public String getId() {
        return id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder PurchaseOrder) {
        this.purchaseOrder = PurchaseOrder;
        this.dirty = true;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.dirty = true;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dirty = true;
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

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
