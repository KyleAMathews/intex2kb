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
 *
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
    
    /** Creates a new instance of Vendor */
    public Vendor(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.dirty = true;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.dirty = true;
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
    
    public void setDirty(boolean dirty){
        this.dirty = dirty;
    }

    public String getId() {
        return id;
    }
    
}
