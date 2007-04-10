/*
 * Transaction.java
 *
 * Created on March 7, 2007, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.util.LinkedList;
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
        this.txLines = new LinkedList<TransactionLine>();
        this.orig = null;
    }
    
    //** Creates a return Transaction */
    /**
     * Creates a new instance of Transaction
     */
    public Transaction(String id, Transaction orig){
        this.id = id;
        this.customer = orig.getCustomer();
        this.employee = orig.getEmployee();
        this.store = orig.getStore();
        this.orig = orig;
        this.type = "return";
    }

    /**
     * Returns transactionID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns a list of transactionLines associated with the transaction
     */
    public List<TransactionLine> getTxLines() {
        return txLines;
    }

    /**
     * Reads TX lines from DB and sets them to a list on the TX object
     */
    public void setTxLines(List<TransactionLine> txLines) {
        this.dirty = true;
        this.txLines = txLines;
    }
    
    /**
     * Adds a single TXLine to the list of TXLns on the object
     */
    public void addTxLine(TransactionLine txln){
        this.txLines.add(txln);
        this.dirty = true;
    }

    /**
     * Returns the payment object associated with the TX
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the Payment Object for the transaction
     */
    public void setPayment(Payment payment) {
        this.dirty = true;
        this.payment = payment;
    }

    /**
     * Returns the TX type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the TX Type
     */
    public void setType(String type) {
        this.dirty = true;
        this.type = type;
    }

    /**
     * Returns Transaction status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets transaction status
     */
    public void setStatus(String status) {
        this.dirty = true;
        this.status = status;
    }

    /**
     * Returns customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Set customer
     */
    public void setCustomer(Customer customer) {
        this.dirty = true;
        this.customer = customer;
    }

    /**
     * Return Employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Set Employee
     */
    public void setEmployee(Employee employee) {
        this.dirty = true;
        this.employee = employee;
    }

    /**
     * Return Store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Set Store
     */
    public void setStore(Store store) {
        this.dirty = true;
        this.store = store;
    }

    /**
     * Return boolean flag to indicate whether or not the object has changed
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Boolean flag to indicate the object has changed
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns boolean flag to indicate if the object has been written to the DB
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Set boolean flag to indicate object has been written to DB
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }
    
    /**
     * Calculate subtotal of transaction by adding the subtotal of all txlines
     */
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
    
    /**
     * Calculate tax for tx
     */
    public double calculateTax() throws DataException{
        double tax = 0.0;
        double sub = this.calculateSubtotal();
        
        tax = (sub * 0.0625);
        return tax;
    }
    
    /**
     * Add subtotal and tax to get total
     */
    public double calculateTotal() throws DataException{
        double sub = this.calculateSubtotal();
        double tax = this.calculateTax();
        return (sub + tax);
    }

    /**
     * Return origional TX object if the tx is a return
     */
    public Transaction getOrig() {
        return orig;
    }

    /**
     * Set origional TX object if the tx is a return
     */
    public void setOrig(Transaction orig) {
        this.dirty = true;
        this.orig = orig;
    }
}
