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
 * conversionBO
 * @author Tyler Farmer
 */
public class conversionBO extends RevenueSource {
    
    private conversionTypeBO conversionType;
    private int quantity;
    
    /**
     * conversionType creates a conversionBO based on a known ID
     * @param id ID of conversionBO to be created
     */
    public conversionBO(String id) {
        this.id = id;
    }
    
    /**
     * Set total price on abstract RS object
     *@param q Quantity
     *@param pfPrice PrintFormat Price
     */
    public void setTotalPrice(int q, double pfPrice){
        double totalPrice = q * pfPrice;
        formatNumber f = new formatNumber();
        totalPrice = Double.valueOf(f.fmt(totalPrice));
        super.setPrice(totalPrice);
    }
    
    /**
     * getConversionType
     * @return conversionType
     */
    public conversionTypeBO getConversionType() {
        return conversionType;
    }
    
    /**
     * setConversionType
     * @param conversionType conversionType
     */
    public void setConversionType(conversionTypeBO conversionType) {
        this.conversionType = conversionType;
        this.dirty = true;
    }
    
    /**
     * getQuantity returns the number of units for a given conversionBO (units can be minutes, seconds, hours, etc.)
     * @return int quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * setQuantity
     * @param quantity int quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dirty = true;
    }
}
