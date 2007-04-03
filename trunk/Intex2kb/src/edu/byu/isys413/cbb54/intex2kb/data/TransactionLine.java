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
 *
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
    
    /** Creates a new instance of TransactionLine */
    public TransactionLine(String id, Transaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }

    public String getId() {
        return id;
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

    public RevenueSource getRevenueSource() {
        return revenueSource;
    }

    public void setRevenueSource(RevenueSource revenueSource) {
        this.dirty = true;
        this.revenueSource = revenueSource;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    
    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.dirty = true;
        this.coupon = coupon;
    }
    
    public Double calculateSubtotal() throws DataException{
        double sub = 0.0;
        sub = sub + this.getRevenueSource().getPrice();
        if(coupon != null){
            sub -= coupon.getAmount();
        }
        return sub;
    }

    public String getRsType() {
        return rsType;
    }

    public void setRsType(String rsType) {
        this.rsType = rsType;
    }

    
}
