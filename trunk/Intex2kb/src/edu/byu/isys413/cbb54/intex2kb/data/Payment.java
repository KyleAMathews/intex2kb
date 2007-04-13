/*
 * Payment.java
 *
 * Created on March 20, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Payment represents a payment for a transaction
 * @author Cameron
 */
public class Payment {
    
    private String id;
    private Double amount;
    private Double change;
    private String type;
    private String ccNumber;
    private String ccExpiration;
    private Transaction transaction;
    private boolean dirty = false;
    private boolean isInDB = false;
    
    /** Creates a new instance of Payment */
    public Payment(String id, Transaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }
    
    //** Creates a new instance of Payment */
    /**
     * Creates a new instance of Payment
     */
    public Payment(String id, Transaction transaction, double amount, String type) throws DataException{
        this.id = id;
        this.transaction = transaction;
        this.amount = amount;
        this.type = type;
        try {
            
            if(this.transaction.calculateTotal() != amount){
                this.change = (amount - this.transaction.calculateTotal());
            }else{
                this.change = 0.0;
            }
        } catch (DataException ex) {
            throw new DataException("There was an error posting the payment", ex);
        }
    }

    /**
     * Returns the payment ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the payment transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * returns the change on a payment
     */
    public Double getChange() {
        return change;
    }

    /**
     * sets the change of the payment
     */
    public void setChange(Double change) {
        this.setDirty(true);
        this.change = change;
    }

    /**
     * returns the type of payment
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of payment
     */
    public void setType(String type) {
        this.setDirty(true);
        this.type = type;
    }

    /**
     * Payment has been changed
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the dirty value
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * sets the database boolean
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    /**
     * returns the amount of the payment
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * sets the amount of the payment
     */
    public void setAmount(Double amount) {
        this.dirty = true;
        this.amount = amount;
    }

    /**
     * return the creditcard number
     */
    public String getCcNumber() {
        return ccNumber;
    }

    /**
     * sets the creditcard number
     */
    public void setCcNumber(String ccNumber) {
        this.dirty = false;
        this.ccNumber = ccNumber;
    }

    /**
     * return the creditcard expiration date
     */
    public String getCcExpiration() {
        return ccExpiration;
    }

    /**
     * sets the creditcard expiration date
     */
    public void setCcExpiration(String ccExpiration) {
        this.dirty = true;
        this.ccExpiration = ccExpiration;
    }
    
}
