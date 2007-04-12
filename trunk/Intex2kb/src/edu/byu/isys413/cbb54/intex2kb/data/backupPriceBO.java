/*
 * backupPriceBO.java
 *
 * Created on April 11, 2007, 9:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author kyle
 */
public class backupPriceBO {
    
    private double price;
    
///////////////////////////////////////
    ///   Singleton pattern
    
    private static backupPriceBO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private backupPriceBO() {
    }
    
    /**
     * Singleton Pattern to allow only one instance of backupPriceBO
     * @return backupPriceBO
     */
    public static synchronized backupPriceBO getInstance() {
        if (instance == null) {
            instance = new backupPriceBO();
        }
        return instance;
    }
    
    /** Creates a new instance of backupPriceBO */
    public backupPriceBO(double price) {
        this.price = price;
    }
    
    public double getPrice(){
        return price;
    }
    
}
