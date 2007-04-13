/*
 * Sale.java
 *
 * Created on March 29, 2007, 1:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Extends abstract class RevenueSource
 * @author Group2K
 */
public class Sale extends RevenueSource {
    
    
    private int quantity;
    private Product product;
    private String productType;
    
    /**
     * Create new instance of Sale
     * 
     * @param id String
     */
    public Sale(String id){
        this.id = id;
    }
    
    /**
     * Returns the quantity of the item sold
     * @return Integer Quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Sets the quantity sold
     * @param quantity Integer
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dirty = true;
    }

    /**
     * Returns the product sold
     * @return Product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product sold
     * @param product object
     */
    public void setProduct(Product product) {
        this.dirty = true;
        this.product = product;
    }

    /**
     * Returns the product type
     * @return String Product Type
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the product type
     * @param productType String
     */
    public void setProductType(String productType) {
        this.dirty = true;
        this.productType = productType;
    }
    
    /**
     * Sets the price of the sale
     * @param price Double
     */
    public void setPrice(double price){
        super.price = price;
    }
    
    /**
     * gets the price of the sale
     * @return price Double
     */
    public double setPrice(){
        return super.price;
    }
}
