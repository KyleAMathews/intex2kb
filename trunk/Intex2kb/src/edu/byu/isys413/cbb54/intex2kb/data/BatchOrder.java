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
            readProducts.close();

            // loop through all products that need to be ordered and select the cheapest vendor
            while(rsP.next()) {
                String ProductId = rsP.getString("id");
                
                // Select the cheapest vendor for the item
                PreparedStatement readVendorItem = conn.prepareStatement(
                        "SELECT min(\"cost\"), \"vendorid\" "+
                        "FROM \"vendoritem\" " +
                        "WHERE \"productid = ? " +
                        "GROUP BY \"productid\', \"vendorid\" ");
                readVendorItem.setString(1, ProductId);
                ResultSet rsVI = readVendorItem.executeQuery();
                conn.commit();
                readVendorItem.close();
                
                Vendor vendor;
                
                // Get Vendor for item
                if(Cache.getInstance().containsKey(rsVI.getString("vendorid"))){
                    vendor = VendorDAO.getInstance().read(rsVI.getString("vendorid"), conn);
                }else{
                    vendor = new Vendor(rsVI.getString("vendorid"));
                    vendor.setName(rsVI.getString("name"));
                    vendor.setAddress(rsVI.getString("address"));
                    vendor.setPhone(rsVI.getString("phone"));
                    vendor.setContact(rsVI.getString("contact"));
                    vendor.setDirty(false);
                    vendor.setInDB(true);
                }
                
                // add vendor to vendor list
                vendorList.add(vendor);
                
                // create an purchase order for the vendor
                PurchaseOrder po;
                if(vendor.getOrder() == null){
                    po = PurchaseOrderDAO.getInstance().create(vendor);
                }else{
                    po = vendor.getOrder();
                }
                
                // create and order line and add it to the vendor's order
                OrderLine ol = OrderLineDAO.getInstance().create(po);
                ol.setProduct(ConceptualDAO.getInstance().read(ProductId));
                ol.setQuantity(rsP.getInt("quantitytoorder"));
                
                po.addOrderLine(ol);
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
