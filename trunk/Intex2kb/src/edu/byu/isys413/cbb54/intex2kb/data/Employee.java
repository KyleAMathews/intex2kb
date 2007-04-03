/*
 * Employee.java
 *
 * Created on March 8, 2007, 1:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * The Employee object represents an employee of the business
 * @author Cameron
 */
public class Employee {
    
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
    private String ssNumber;
    private String hireDate;
    private Double salary;
    private String storeID;
    private boolean dirty = false;
    private boolean isInDB = false;
    
    /**
     * Creates a new instance of Employee
     * @param id String
     */
    public Employee(String id) {
        this.id = id;
    }

    /**
     * This method returns the first name within the Employee object.
     * @return String(fname)
     */
    public String getFname() {
        return fname;
    }

    /**
     * This method will set the first name for the Employee object
     * @param fname String
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Returns the last name of the employee
     * @return String(lname)
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets the last name of the employee
     * @param lname String
     */
    public void setLname(String lname) {
        this.dirty = true;
        this.lname = lname;
    }

    /**
     * Returns the employee's address (line 1)
     * @return String(address1)
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the employee's address (line 1)
     * @param address1 String
     */
    public void setAddress1(String address1) {
        this.dirty = true;
        this.address1 = address1;
    }

    /**
     * Returns the employee's address (line 2)
     * @return String(address2)
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the employee's address (line 2)
     * @param address2 String
     */
    public void setAddress2(String address2) {
        this.dirty = true;
        this.address2 = address2;
    }

    /**
     * Returns the Employee's city
     * @return String(city)
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the employee's city
     * @param city String
     */
    public void setCity(String city) {
        this.dirty = true;
        this.city = city;
    }

    /**
     * Returns the employee's state
     * @return String(state)
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the employee's state
     * @param state String
     */
    public void setState(String state) {
        this.dirty = true;
        this.state = state;
    }

    /**
     * Returns the employee's ZIP code
     * @return String(zip)
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the employee's ZIP code
     * @param zip String
     */
    public void setZip(String zip) {
        this.dirty = true;
        this.zip = zip;
    }

    /**
     * Returns the employee's phone number
     * @return String(phone)
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the employee's phone number
     * @param phone String
     */
    public void setPhone(String phone) {
        this.dirty = true;
        this.phone = phone;
    }

    /**
     * Returns the employee's email
     * @return String(email)
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the employee's email
     * @param email String
     */
    public void setEmail(String email) {
        this.dirty = true;
        this.email = email;
    }

    /**
     * Returns the employee's social security number
     * @return String(ssNumber)
     */
    public String getSsNumber() {
        return ssNumber;
    }

    /**
     * Sets the employee's social secutiry number
     * @param ssNumber String
     */
    public void setSsNumber(String ssNumber) {
        this.dirty = true;
        this.ssNumber = ssNumber;
    }

    /**
     * Returns the employee's date of hire
     * @return String(hireDate)
     */
    public String getHireDate() {
        return hireDate;
    }

    /**
     * Sets the employee's date of hire
     * @param hireDate String
     */
    public void setHireDate(String hireDate) {
        this.dirty = true;
        this.hireDate = hireDate;
    }

    /**
     * Returns the employee's salary
     * @return double(salary)
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Sets the employee's salary
     * @param salary Double
     */
    public void setSalary(Double salary) {
        this.dirty = true;
        this.salary = salary;
    }

    /**
     * Returns a boolean of whether the employee object has changed since the last save
     * @return Boolean(dirty)
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets a boolean of whether the employee object has changed since the last save
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns a boolean of whether the employee object has been saved to the database
     * @return Boolean(inDB)
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Sets a boolean of whether the employee object has been saved to the database
     * @param isInDB Boolean
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    /**
     * Returns the ID of the employee
     * @return String(id)
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the ID of the employees assigned store
     * @return String
     */
    public String getStoreID() {
        return storeID;
    }

    /**
     * Sets the ID of the employees assigned store
     * @param storeID String
     */
    public void setStoreID(String storeID) {
        this.dirty = true;
        this.storeID = storeID;
    }
    
}
