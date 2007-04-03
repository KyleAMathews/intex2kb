/*
 * Store.java
 *
 * Created on March 8, 2007, 1:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public class Store {
    
    private boolean dirty = false;
    private boolean isInDB = false;
    private String id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String fax;
    private String managerID;
    
    /** Creates a new instance of Store */
    public Store(String id) {
        this.id = id;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isInDB() {
        return isInDB;
    }

    public void setIsInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.dirty = true;
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.dirty = true;
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.dirty = true;
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.dirty = true;
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.dirty = true;
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.dirty = true;
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.dirty = true;
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.dirty = true;
        this.fax = fax;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.dirty = true;
        this.managerID = managerID;
    }
    
}
