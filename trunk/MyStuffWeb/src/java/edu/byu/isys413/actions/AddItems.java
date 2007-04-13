/*
 * AddItems.java
 *
 * Created on April 12, 2007, 11:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Cameron
 */
public class AddItems implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of AddItems */
    public AddItems() {
    }
    
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        // Retrieve variables
        HttpSession session = request.getSession();
        String category = request.getParameter("category");
        Transaction saletx = (Transaction)session.getAttribute("saletx");
        String productID = request.getParameter("item");
        List<Conceptual> productList = new LinkedList<Conceptual>();
        
        //get list of conceptual products in specified category
        ConceptualDAO cDAO = (ConceptualDAO) ConceptualDAO.getInstance();
        productList = cDAO.getProductsByCategory(category);
        
        // Retrieve Product
        Conceptual product = (Conceptual)ConceptualDAO.getInstance().read(productID);
        
        Sale s = (Sale)SaleDAO.getInstance().create();
        s.setProduct(product);
        s.setPrice(product.getPrice());
        s.setProductType("Bulk");
        s.setQuantity(1);
        
        
        TransactionLine txln = TransactionLineDAO.getInstance().create(saletx, product.getId());
        txln.setRevenueSource(s);
        txln.setRsType("Online Sale");
        saletx.addTxLine(txln);
        
        
        // Return category ID to next page
        request.setAttribute("category", category);
        
        // Return productlist to next page
        request.setAttribute("products", productList);
        
        // Direct information to the Sale.jsp page
        return "sale.jsp";
        }  
    
}
