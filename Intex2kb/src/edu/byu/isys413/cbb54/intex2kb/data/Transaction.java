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
 * Transaction holds information about each monitary interaction with customers
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
    
    /**
     * Creates a new instance of Transaction
     * @param id GUID
     */
    public Transaction(String id) {
        this.id = id;
        this.txLines = new LinkedList<TransactionLine>();
        this.orig = null;
    }
    
    /**
     * Creates a return Transaction
     * @param id New transaction GUID
     * @param orig Original Transaction
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
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns a list of transactionLines associated with the transaction
     * @return List TransactionLines
     */
    public List<TransactionLine> getTxLines() {
        return txLines;
    }

    /**
     * Sets the transaction lines for a transaction
     * @param txLines List
     */
    public void setTxLines(List<TransactionLine> txLines) {
        this.dirty = true;
        this.txLines = txLines;
    }
    
    /**
     * Adds a single TXLine to the list of TXLns on the object
     * @param txln TransactionLine
     */
    public void addTxLine(TransactionLine txln){
        this.txLines.add(txln);
        this.dirty = true;
    }

    /**
     * Returns the payment object associated with the TX
     * @return Payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Sets the Payment Object for the transaction
     * @param payment Object
     */
    public void setPayment(Payment payment) {
        this.dirty = true;
        this.payment = payment;
    }

    /**
     * Returns the TX type
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the TX Type
     * @param type String
     */
    public void setType(String type) {
        this.dirty = true;
        this.type = type;
    }

    /**
     * Returns Transaction status
     * @return Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets transaction status
     * @param status String
     */
    public void setStatus(String status) {
        this.dirty = true;
        this.status = status;
    }

    /**
     * Returns customer
     * @return Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Set customer
     * @param customer Object
     */
    public void setCustomer(Customer customer) {
        this.dirty = true;
        this.customer = customer;
    }

    /**
     * Return Employee
     * @return Employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Set Employee
     * @param employee Object
     */
    public void setEmployee(Employee employee) {
        this.dirty = true;
        this.employee = employee;
    }

    /**
     * Return Store
     * @return Store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Set Store
     * @param store Object
     */
    public void setStore(Store store) {
        this.dirty = true;
        this.store = store;
    }

    /**
     * Return boolean flag to indicate whether or not the object has changed
     * @return Boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Boolean flag to indicate the object has changed
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns boolean flag to indicate if the object has been written to the DB
     * @return Boolean
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Set boolean flag to indicate object has been written to DB
     * @param isInDB Boolean
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }
    
    /**
     * Calculate subtotal of transaction by adding the subtotal of all txlines
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is a error calculating the subtotal of the transaction
     * @return Double
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
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is an error calculating the transaction tax
     * @return Double
     */
    public double calculateTax() throws DataException{
        double tax = 0.0;
        double sub = this.calculateSubtotal();
        
        tax = (sub * 0.0625);
        return tax;
    }
    
    /**
     * Add subtotal and tax to get total
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Thrown when there is an error calculating the transaction tax and subtotal
     * @return Double
     */
    public double calculateTotal() throws DataException{
        double sub = this.calculateSubtotal();
        double tax = this.calculateTax();
        return (sub + tax);
    }

    /**
     * Return origional TX object if the tx is a return
     * @return Transaction Original
     */
    public Transaction getOrig() {
        return orig;
    }

    /**
     * Set origional TX object if the tx is a return
     * @param orig Transaction
     */
    public void setOrig(Transaction orig) {
        this.dirty = true;
        this.orig = orig;
    }
}
