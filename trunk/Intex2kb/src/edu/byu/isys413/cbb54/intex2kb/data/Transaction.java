/*
 * Transaction.java
 *
 * Created on March 7, 2007, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.util.List;

/**
 *
 * @author Cameron
 */
public class Transaction {
    
    private String id;
    private List<TransactionLine> txLines;
    private Payment payment;
    private String type;
    private String status;
    private Customer customer;
    private Employee employee;
    private Store store;
    private Transaction orig;
    private boolean dirty = false;
    private boolean isInDB = false;
    
    /** Creates a new instance of Transaction */
    public Transaction(String id) {
        this.id = id;
        this.orig = null;
    }
    
    //** Creates a return Transaction */
    public Transaction(String id, Transaction orig){
        this.id = id;
        this.customer = orig.getCustomer();
        this.employee = orig.getEmployee();
        this.store = orig.getStore();
        this.orig = orig;
        this.type = "return";
    }

    public String getId() {
        return id;
    }

    public List<TransactionLine> getTxLines() {
        return txLines;
    }

    public void setTxLines(List<TransactionLine> txLines) {
        this.dirty = true;
        this.txLines = txLines;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.dirty = true;
        this.payment = payment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.dirty = true;
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.dirty = true;
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.dirty = true;
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.dirty = true;
        this.employee = employee;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.dirty = true;
        this.store = store;
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

    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }
    
    public double calculateSubtotal() throws DataException{
        double sub = 0.0;
        
        try{
            for(int i = 0; i < this.getTxLines().size(); i++){
                sub += this.getTxLines().get(i).calculateSubtotal();
            }
        }catch (DataException e){
            throw new DataException("There was an error calculating the subtotal", e);
        }
        return sub;
    }
    
    public double calculateTax() throws DataException{
        double tax = 0.0;
        double sub = this.calculateSubtotal();
        
        tax = (sub * 0.0625);
        return tax;
    }
    
    public double calculateTotal() throws DataException{
        double sub = this.calculateSubtotal();
        double tax = this.calculateTax();
        return (sub + tax);
    }

    public Transaction getOrig() {
        return orig;
    }

    public void setOrig(Transaction orig) {
        this.dirty = true;
        this.orig = orig;
    }
}
