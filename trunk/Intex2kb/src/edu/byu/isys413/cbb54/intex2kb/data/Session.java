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
 *
 * @author Cameron
 */
public class Session {
    
    private Store store;
    private Employee employee;
    
    /** Creates a new instance of Session */
    public Session(Store store, Employee employee) {
        this.setStore(store);
        this.setEmployee(employee);
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
}
