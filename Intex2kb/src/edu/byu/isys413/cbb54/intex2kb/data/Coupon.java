/*
 * Coupon.java
 *
 * Created on March 20, 2007, 12:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Coupon refers to a discount to be applied to a TransactionLine in the form of a dollar amount.
 * @author Cameron Burgon
 */
public class Coupon {
    
    private String id;
    private Double amount;
    private boolean dirty = false;
    private boolean isInDB = false;
    
    /**
     * Creates a new instance of Coupon
     * @param id ID refers to the GUID of the object.
     */
    public Coupon(String id) {
        this.id = id;
    }

    /**
     * This method will return the ID of the Coupon
     * @return String(ID)
     */
    public String getId() {
        return id;
    }

    /**
     * This method will return the Amount of the coupon(discount)
     * @return Double(amount)
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method will set the Amount - or dollar discount - for the Coupon
     * @param amount (double) - Dollar discount
     */
    public void setAmount(Double amount) {
        this.setDirty(true);
        this.amount = amount;
    }

    /**
     * This method indicates whether the object has been changed since it was saved last
     * @return Boolean
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the boolean value Dirty for the Coupon
     * @param dirty (boolean)
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * This method indicates whether the Coupon has been saved to the database
     * @return Boolean
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Sets the InDB value for Coupone
     * @param isInDB Boolean
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }
    
}
