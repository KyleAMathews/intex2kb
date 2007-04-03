/*
 * Membership.java
 *
 * Created on February 23, 2007, 1:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.util.List;

/**
 * Membership represents a customer with greater privilages
 * @author Cameron
 */
public class Membership {
        private boolean dirty = false;
        private boolean inDB = false;
        private String id;
        private String custId;
        private String startDate;
        private String endDate;
        private String creditCard;
        private String ccExpiration;
        private double backupSize;
        private long backupExpDate;
        private Boolean newsletter = false;
        private List interests;
        private Customer customer;
        
    /**
     * Creates a new instance of Membership
     * @param id String
     * @param custId String
     */
    public Membership(String id, String custId) {
        this.id = id;
        this.custId = custId;
    }

    /**
     * Returns whether the membership has been changes since the last save
     * @return Boolean(dirty)
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets whether the membership has been changes since the last save
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns whether the membership has been saved to the database
     * @return Boolean(inDB)
     */
    public boolean isInDB() {
        return inDB;
    }

    /**
     * Sets whether the membership has been saved to the database
     * @param isInDB Boolean
     */
    public void setInDB(boolean isInDB) {
        this.dirty = false;
        this.inDB = true;
    }

    /**
     * Returns the membership id
     * @return String(id)
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the memberships customer id
     * @return String(custID)
     */
    public String getCustId() {
        return custId;
    }

    /**
     * Returns the membership start date
     * @return String(startDate)
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the membership start date
     * @param startDate String
     */
    public void setStartDate(String startDate) {
        this.dirty = true;
        this.startDate = startDate;
    }

    /**
     * Returns the membership end date
     * @return String(endDate)
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the membership end date
     * @param endDate String
     */
    public void setEndDate(String endDate) {
        this.dirty = true;
        this.endDate = endDate;
    }

    /**
     * Returns the membership credit card number
     * @return String(creditCard)
     */
    public String getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the membership credit card number
     * @param creditCard String
     */
    public void setCreditCard(String creditCard) {
        this.dirty = true;
        this.creditCard = creditCard;
    }

    /**
     * Returns the membership credit card expiration date
     * @return String(ccExpriation)
     */
    public String getCcExpiration() {
        return ccExpiration;
    }

    /**
     * Sets the membership credit card expiration date
     * @param ccExpiration String
     */
    public void setCcExpiration(String ccExpiration) {
        this.dirty = true;
        this.ccExpiration = ccExpiration;
    }

    /**
     * Returns the membership preference to the newsletter
     * @return Boolean
     */
    public Boolean getNewsletter() {
        return newsletter;
    }

    /**
     * Sets the membership preference to the newsletter
     * @param newsletter Boolean
     */
    public void setNewsletter(Boolean newsletter) {
        this.dirty = true;
        this.newsletter = newsletter;
    }

    /**
     * Returns a list of the members interests
     * @return List of interests
     */
    public List getInterests() {
        return interests;
    }

    /**
     * Sets a list of the members interests
     * @param interests List
     */
    public void setInterests(List interests) {
        this.dirty = true;
        this.interests = interests;
    }

    /**
     * Returns the customer of the membership
     * @return Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer of the membership
     * @param customer Customer
     */
    public void setCustomer(Customer customer) {
        this.dirty = true;
        this.customer = customer;
    }

    public double getBackupSize() {
        return backupSize;
    }

    public void setBackupSize(double backUpsize) {
        this.backupSize = backupSize;
    }

    public long getBackupExpDate() {
        return backupExpDate;
    }

    public void setBackupExpDate(long expDate) {
        this.backupExpDate = backupExpDate;
    }
    
}
