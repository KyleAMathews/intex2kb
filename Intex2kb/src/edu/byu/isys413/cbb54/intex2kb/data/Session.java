/*
 * Session.java
 *
 * Created on March 20, 2007, 8:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Session object stores the employee object and store object to be
 * used in completing transaction objects
 * @author Cameron
 */
public class Session {
    
    private Store store;
    private Employee employee;
    
    /**
     * Creates a new instance of Session
     * @param store GUID
     * @param employee GUID
     */
    public Session(Store store, Employee employee) {
        this.setStore(store);
        this.setEmployee(employee);
    }

    /**
     * Returns the store object in the session
     * @return Store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Sets the store object in the session
     * @param store Object
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Returns the employee object in the session
     * @return Employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee object in the session
     * @param employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
}
