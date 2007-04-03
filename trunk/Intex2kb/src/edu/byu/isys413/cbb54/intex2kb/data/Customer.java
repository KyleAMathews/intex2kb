/*
 * Customer.java
 *
 * Created on February 21, 2007, 4:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.util.List;

/**
 * Customer object representing customer and their contact information
 * @author Cameron
 */
public class Customer {
    
    private boolean dirty = false;
    private boolean isInDB = false;
    private String id;
    private String fname;
    private String lname;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;
    private long lastUpdated;
    private Membership membership;

    /** Creates a new instance of Customer */
    Customer(String id) {
        this.id = id;
    }
    
    /**
     * Returns whether the object has changed since the last save.
     * @return Boolean
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the Boolean value for Dirty
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns whether the object data is stored in the database
     * @return Boolean
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Sets the Boolean value of if the object is stored in the database
     * @param isInDB Boolean
     */
    public void setIsInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    /**
     * Returns the ID of the customer
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the first name of the customer
     * @return String(Fname)
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets the value of the customer's first name
     * @param fname String
     */
    public void setFname(String fname) {
        this.dirty = true;
        this.fname = fname;
    }

    /**
     * Returns the customer's last name
     * @return String(Lname)
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets the customer's last name value
     * @param lname String
     */
    public void setLname(String lname) {
        this.dirty = true;
        this.lname = lname;
    }

    /**
     * Returns the customers address (line 1)
     * @return String(address1)
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the customer's address (line 1)
     * @param address1 String
     */
    public void setAddress1(String address1) {
        this.dirty = true;
        this.address1 = address1;
    }

    /**
     * Returns the customer's address (line 2)
     * @return String(address2)
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the customer's address (line 2)
     * @param address2 String
     */
    public void setAddress2(String address2) {
        this.dirty = true;
        this.address2 = address2;
    }

    /**
     * Returns the customer's city
     * @return String(city)
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the customer's city
     * @param city String
     */
    public void setCity(String city) {
        this.dirty = true;
        this.city = city;
    }

    /**
     * Returns the customer's state
     * @return String(state)
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the customer's state
     * @param state String
     */
    public void setState(String state) {
        this.dirty = true;
        this.state = state;
    }

    /**
     * Returns the customer's ZIP code
     * @return String(zip)
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the customer's ZIP code
     * @param zip String
     */
    public void setZip(String zip) {
        this.dirty = true;
        this.zip = zip;
    }

    /**
     * Returns the customer's phone number
     * @return String
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number
     * @param phone String
     */
    public void setPhone(String phone) {
        this.dirty = true;
        this.phone = phone;
    }

    /**
     * Returns the date of the last update
     * @return String
     */
    public long getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the date of the last update
     * @param lastUpdated Long
     */
    public void setLastUpdated(long lastUpdated) {
        this.dirty = true;
        this.lastUpdated = lastUpdated;
    }

    /**
     * Returns the membership (if any) associated with the customer
     * @return Membership
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets the membership associated with the customer
     * @param membership Membership
     */
    public void setMembership(Membership membership) {
        this.dirty = true;
        this.membership = membership;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
