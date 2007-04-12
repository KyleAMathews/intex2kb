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
 * Object that keeps track of all stores and their associated information
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
    
    /**
     * Creates a new instance of Store
     * @param id GUID
     */
    public Store(String id) {
        this.id = id;
    }

    /**
     * Returns boolean flag to notify of changes to object
     * @return Boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets boolean flag to indicate changes to object
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns boolean flag to indicate object has been written to DB
     * @return Boolean inDB
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Sets boolean flat to indicate object has been written to DB
     * @param isInDB Boolean
     */
    public void setIsInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    /**
     * Returns ID of Store
     * @return GUID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the store
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the store
     * @param name String
     */
    public void setName(String name) {
        this.dirty = true;
        this.name = name;
    }

    /**
     * Returns the first line of the store's address
     * @return address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the first line of the store's address
     * @param address1 String
     */
    public void setAddress1(String address1) {
        this.dirty = true;
        this.address1 = address1;
    }

    /**
     * Returns the second line of the store's address
     * @return address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the second line of the store's address
     * @param address2 String
     */
    public void setAddress2(String address2) {
        this.dirty = true;
        this.address2 = address2;
    }

    /**
     * Returns the store's city
     * @return City
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets store's city
     * @param city String
     */
    public void setCity(String city) {
        this.dirty = true;
        this.city = city;
    }

    /**
     * Returns store's state
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets store's state
     * @param state String
     */
    public void setState(String state) {
        this.dirty = true;
        this.state = state;
    }

    /**
     * Return store's zipcode
     * @return zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Set store's zipcode
     * @param zip String
     */
    public void setZip(String zip) {
        this.dirty = true;
        this.zip = zip;
    }

    /**
     * Returns store's phone number
     * @return Phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set store's phone number
     * @param phone String
     */
    public void setPhone(String phone) {
        this.dirty = true;
        this.phone = phone;
    }

    /**
     * Returns store's fax number
     * @return fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets store's fax number
     * @param fax String
     */
    public void setFax(String fax) {
        this.dirty = true;
        this.fax = fax;
    }

    /**
     * Returns EmployeeID of store manager
     * @return employee GUID
     */
    public String getManagerID() {
        return managerID;
    }

    /**
     * Sets EmployeeID of store manager
     * @param managerID String GUID
     */
    public void setManagerID(String managerID) {
        this.dirty = true;
        this.managerID = managerID;
    }
    
}
