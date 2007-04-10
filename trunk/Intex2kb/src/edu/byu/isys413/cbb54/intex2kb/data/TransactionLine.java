/*
 * TransactionLine.java
 *
 * Created on March 20, 2007, 12:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Transaction lines hold the information for individual revenue sources.
 * @author Cameron
 */
public class TransactionLine {
    
    private String id;
    private RevenueSource revenueSource;
    private Transaction transaction; 
    private Coupon coupon;
    private String rsType;
    private boolean dirty = false;
    private boolean isInDB = false;
    
    /**
     * Create a new TransactionLine based on a known ID
     * @param id ID
     * @param transaction Transaction object that the TXLN belongs to
     */
    public TransactionLine(String id, Transaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }

    /**
     * return TXLN id
     * @return txln
     */
    public String getId() {
        return id;
    }

    /**
     * boolean flag that indicates changes to the object
     * @return boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * change dirty flag when object changes
     * @param dirty boolean dirty
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Boolean that indicates if object has been written to the DB
     * @return boolean inDB
     */
    public boolean isInDB() {
        return isInDB;
    }

    /**
     * Change flag to indicate object has been written to the DB
     * @param isInDB boolean inDB
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    /**
     * RevenueSource refers to the type of income the TXLN is referring to
     * @return RevenueSource
     */
    public RevenueSource getRevenueSource() {
        return revenueSource;
    }

    /**
     * set RevenueSource
     * @param revenueSource revenueSource
     */
    public void setRevenueSource(RevenueSource revenueSource) {
        this.dirty = true;
        this.revenueSource = revenueSource;
    }

    /**
     * Return associated TX object
     * @return transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    
    /**
     * Return associated Coupon object
     * @return Coupon
     */
    public Coupon getCoupon() {
        return coupon;
    }

    /**
     * Add Coupon object to TXLN
     * @param coupon Coupon
     */
    public void setCoupon(Coupon coupon) {
        this.dirty = true;
        this.coupon = coupon;
    }
    
    /**
     * Calculate subtotal of TXLN
     * @return double subtotal
     * @throws edu.byu.isys413.cbb54.intex2kb.data.DataException Exception
     */
    public Double calculateSubtotal() throws DataException{
        double sub = 0.0;
        sub = sub + this.getRevenueSource().getPrice();
        if(coupon != null){
            sub -= coupon.getAmount();
        }
        return sub;
    }

    /**
     * Return revenueSourceType, a text description of the actual revenueSource
     * @return revenueSourceType
     */
    public String getRsType() {
        return rsType;
    }

    /**
     * Set RevenueSourceType
     * @param rsType RevenueSourceType
     */
    public void setRsType(String rsType) {
        this.rsType = rsType;
    }

    
}
