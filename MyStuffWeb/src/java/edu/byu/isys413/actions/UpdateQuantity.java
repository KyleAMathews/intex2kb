/*
 * UpdateQuantity.java
 *
 * Created on April 13, 2007, 1:11 AM
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
public class UpdateQuantity implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of AddItems */
    public UpdateQuantity() {
    }
    
    /**
     * Updates the quantity for a TransactionLine unless the quantity is zero, in which
     * case it removes the TransactionLine from the transaction.
     */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        // Retrieve variables
        HttpSession session = request.getSession();
        String category = request.getParameter("category");
        Transaction saletx = (Transaction)TransactionDAO.getInstance().read((String)session.getAttribute("saletx"));
        int q = Integer.parseInt(request.getParameter("quantity"));
        int line = Integer.parseInt(request.getParameter("update"));
        List<Conceptual> productList = new LinkedList<Conceptual>();
        List<TransactionLine> txLines = new LinkedList<TransactionLine>();
        
        //get list of conceptual products in specified category
        ConceptualDAO cDAO = (ConceptualDAO) ConceptualDAO.getInstance();
        productList = cDAO.getProductsByCategory(category);
        
        txLines = saletx.getTxLines();
        
        if(q == 0){
            txLines.remove(line);
            saletx.setTxLines(txLines);
        }else{
            TransactionLine txln = txLines.get(line);
            Sale s = (Sale)txln.getRevenueSource();
            s.setQuantity(q);
            s.setPrice(s.getProduct().getPrice() * q);
        }

        
        // Return category ID to next page
        request.setAttribute("category", category);
        
        // Return productlist to next page
        request.setAttribute("products", productList);
        
        // Direct information to the Sale.jsp page
        return "sale.jsp";
        }  
    
}