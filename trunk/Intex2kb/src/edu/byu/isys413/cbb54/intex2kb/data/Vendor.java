/*
 * Vendor.java
 *
 * Created on April 10, 2007, 10:45 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Vendor objects represent the vendors of merchandise purchased by MyStuff
 * @author Cameron
 */
public class Vendor {
    
    private String id;
    private String name;
    private String address;
    private String phone;
    private String contact;
    private boolean inDB;
    private boolean dirty;
    private PurchaseOrder purchaseOrder;
    
    /**
     * Creates a new instance of Vendor
     * @param id Guid of new Vendor
     */
    public Vendor(String id) {
        this.id = id;
    }

    /**
     * Returns the name of the vendor
     * @return String Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Name of the vendor
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    /**
     * Returns the address of the vendor
     * @return Address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the vendor
     * @param address String
     */
    public void setAddress(String address) {
        this.dirty = true;
        this.address = address;
    }

    /**
     * Returns the phone number of the vendor
     * @return Phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the vendor
     * @param phone String
     */
    public void setPhone(String phone) {
        this.dirty = true;
        this.phone = phone;
    }

    /**
     * Returns the name of the contact at the vendor
     * @return Contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact name of the vendor
     * @param contact String
     */
    public void setContact(String contact) {
        this.contact = contact;
        this.dirty = true;
    }

    /**
     * Returns boolean value of if the vendor is recorded in the database
     * @return Boolean inDB
     */
    public boolean isInDB() {
        return inDB;
    }

    /**
     * Sets the boolean of if the vendor is stored in the database
     * @param inDB Boolean
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    /**
     * Returns the boolean value of if the vendor object has changed since being saved 
     * to the database
     * @return Boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }
    
    /**
     * Sets the boolean of if the vendor object has been changed since the last
     * save to the database
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }

    /**
     * Returns the vendor's id
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the temporary purchase order for the vendor
     * @return Order
     */
    public PurchaseOrder getOrder() {
        return getPurchaseOrder();
    }

    /**
     * Sets the temporary purchase order for the vendor
     * @param po PurchaseOrder
     */
    public void setOrder(PurchaseOrder po) {
        this.setPurchaseOrder(po);
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
    
}
