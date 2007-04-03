/*
 * BatchOrder.java
 *
 * Created on April 3, 2007, 11:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author tylerf
 */
public class BatchOrder {
    
    /** Creates a new instance of BatchOrder */
    public BatchOrder() {
        
        /**
         *  SQL Statement to get products that need to be ordered
         *
         *                      SELECT * 
         *                      FROM StoreProduct 
         *                      WHERE (QtyOnHand + QtyOnOrder) <= ReorderPoint
         *                      ORDER BY StoreID
         */
        
        /**
         *  Format the result set into a list (Store, List<Product>)
         */
        
        /**
         *  s
         *
         */
    }
    
}
