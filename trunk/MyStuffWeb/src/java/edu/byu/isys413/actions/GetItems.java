/*
 * GetItems.java
 *
 * Created on April 11, 2007, 3:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;
import edu.byu.isys413.cbb54.intex2kb.data.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Creates a new instance of GetItems
 * @author Cameron
 */
public class GetItems implements edu.byu.isys413.web.Action {
    
    /**
     * Creates a new instance of GetItems
     */
    public GetItems() {
    }
    
    /**
     * process GetItems
     */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Create list to hold Conceptual product of a category
        List<Conceptual> productList = new LinkedList<Conceptual>();
        
        // Retieve category ID
        String category = request.getParameter("category");
        
        //get list of conceptual products in specified category
        ConceptualDAO cDAO = (ConceptualDAO) ConceptualDAO.getInstance();
        productList = cDAO.getProductsByCategory(category);
        
        // Return category ID to next page
        request.setAttribute("category", category);
        
        // Return productlist to next page
        request.setAttribute("products", productList);
        
        // Direct information to the Sale.jsp page
        return "sale.jsp";
        }  
}
