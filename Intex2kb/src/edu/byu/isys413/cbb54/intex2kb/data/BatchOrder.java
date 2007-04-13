/*
 * BatchOrder.java
 *
 * Created on April 3, 2007, 11:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tylerf
 */
public class BatchOrder {
    
    List<Vendor> vendorList = new LinkedList<Vendor>();
    
    
    /** Creates a new instance of BatchOrder */
    public BatchOrder() {
        Connection conn;
        try {
            conn = ConnectionPool.getInstance().get();
            
            
            /**
             *  SQL Statement to get products that need to be ordered
             */
            
            PreparedStatement readProducts = conn.prepareStatement(
                    "SELECT * " +
                    "FROM \"storeproduct\" " +
                    "WHERE (\"quantityonhand\" + \"quantityonorder\") <= \"reorderpoint\" ");
            ResultSet rsP = readProducts.executeQuery();
            conn.commit();
            
            
            // loop through all products that need to be ordered and select the cheapest vendor
            while(rsP.next()) {
                String ProductId = rsP.getString("productid");
                String StoreID = rsP.getString("storeid");
                Conceptual p = (Conceptual)ConceptualDAO.getInstance().read(ProductId,conn);
                Cache.getInstance().put(ProductId, p);
                
                // Select the cheapest vendor for the item
                PreparedStatement readVendorItem = conn.prepareStatement(
                        "SELECT min(\"cost\"), \"vendorid\" "+
                        "FROM \"vendoritem\" " +
                        "WHERE \"productid\" = ? "+
                        "GROUP BY \"productid\", \"vendorid\" ");
                readVendorItem.setString(1, ProductId);
                ResultSet rsVI = readVendorItem.executeQuery();
                conn.commit();
                
                
                Vendor vendor = null;
                
                if(rsVI.next()){
                    // Get Vendor for item
                    if(Cache.getInstance().containsKey(rsVI.getString("vendorid"))){
                        vendor = (Vendor)Cache.getInstance().get(rsVI.getString("vendorid"));
                    }else{
                        vendor = VendorDAO.getInstance().read(rsVI.getString("vendorid"), conn);
                    }
                    
                    // add vendor to vendor list - if vendor is not already in the list
                    if(!vendorList.contains(vendor)){
                        vendorList.add(vendor);
                    }
                }
                
                
                // create an purchase order for the vendor
                PurchaseOrder po;
                if(vendor.getOrder() == null){
                    po = PurchaseOrderDAO.getInstance().create(vendor);
                    po.setStore(StoreDAO.getInstance().read(StoreID,conn));
                    vendor.setOrder(po);
                    Cache.getInstance().put(po.getId(),po);
                }else{
                    po = vendor.getOrder();
                }
                
                // create and order line and add it to the vendor's order
                OrderLine ol = OrderLineDAO.getInstance().create(po);
                ol.setProduct(ConceptualDAO.getInstance().read(ProductId, conn));
                ol.setQuantity(rsP.getInt("quantitytoorder"));
                Cache.getInstance().put(ol.getId(), ol);
                
                po.addOrderLine(ol);
                readVendorItem.close();
            }
            
            readProducts.close();
            
            for(int i=0; i<vendorList.size(); i++){
                Vendor v = vendorList.get(i);
                
                System.out.println("Purchase Order for : " + v.getName());
                PurchaseOrder vPO = v.getOrder();
                List<OrderLine> POL = vPO.getOrderLines();
                for(int l=0; l<POL.size(); l++){
                    OrderLine ol = POL.get(l);
                    Conceptual lp = (Conceptual)ConceptualDAO.getInstance().read(ol.getProduct().getId(), conn);
                    System.out.println("Line " + (l+1) + ": " + lp.getName() + "\t    Quantity: " + ol.getQuantity());
                }
                System.out.println(" \n");
                
                PurchaseOrderDAO.getInstance().save(vPO, conn);
            }
            
        } catch (ConnectionPoolException ex) {
            ex.printStackTrace();
        } catch (SQLException sql){
            sql.printStackTrace();
        } catch (DataException de){
            de.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        /**
         *  Format the result set into a list (Store, List<Product>)
         */
        
        /**
         *  s
         *
         */
    }
    
}
