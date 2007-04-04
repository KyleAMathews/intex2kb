/*
 * printOrder.java
 *
 * Created on March 30, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * PrintOrder is a one of several possible revenue sources for the MyStuff system.  It is the printing of pictures either digital or film.
 * @author tyler
 */
public class printOrder extends RevenueSource {
    
    private PhotoSet photoSet;
    private printFormat printFormat;
    private int quantity;
    private String poID;
    
    /**
     * Creates a new instance of printOrder
     * @param id GUID
     */
    public printOrder(String id) {
        this.id = id;
    }

    /**
     * Set total price on abstract RS object
     *@param q Quantity
     *@param pfPrice PrintFormat Price
     */
    public void setTotalPrice(int q, double pfPrice){
        double totalPrice = q * pfPrice;
        super.setPrice(totalPrice);
    }
    
    /**
     * Return photoset
     * @return photoset
     */
    public PhotoSet getPhotoSet() {
        return photoSet;
    }

    /**
     * Set PhotoSet object
     * @param photoSet Photoset associated with printorder
     */
    public void setPhotoSet(PhotoSet photoSet) {
        this.photoSet = photoSet;
        this.dirty = true;
    }

    /**
     * Get Quantity
     * @return Number of orders of a particular type & photoset
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set Quantity
     * @param quantity Number of a particular photoset and printformat
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dirty = true;
    }

    /**
     * Get PrintFormat
     * @return Papertype, size, sourcetype
     */
    public printFormat getPrintFormat() {
        return printFormat;
    }

    /**
     * setPrintFormat
     * @param printFormat Papertype, size, sourcetype
     */
    public void setPrintFormat(printFormat printFormat) {
        this.printFormat = printFormat;
        this.dirty = true;
    }

    /**
     * get PrintOrderID
     * @return return PrintOrderID
     */
    public String getPoID() {
        return poID;
    }

    /**
     * set PrintOrderID
     * @param poID PrintOrderID
     */
    public void setPOID(String poID) {
        this.poID = poID;
        this.dirty = true;
    }
    
}
