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
        HttpSession session = request.getSession();
        List<String> storeList = new LinkedList<String>();
        List<Conceptual> productList = new LinkedList<Conceptual>();
        List<String> forRentList = new LinkedList<String>();
        List<ForRent> forRentBOList = new LinkedList<ForRent>();
        
        String category = request.getParameter("category");
        
//        //check the different check boxes where to search for the products
//        if(request.getParameter("StoreOpt1") != null){
//            storeList.add("000001117284553c0014b20a500442");
//        }
//        if(request.getParameter("StoreOpt2") != null){
//            storeList.add("000001117284553c0014b20a500443");
//        }
//        if(request.getParameter("StoreOpt3") != null){
//            storeList.add("000001117284553c0014b20a500444");
//        }
        
        //get list of conceptual products in specified category
        
        ConceptualDAO cDAO = (ConceptualDAO) ConceptualDAO.getInstance();
        productList = cDAO.getProductsByCategory(category);
        
        request.setAttribute("category", category);
        request.setAttribute("products", productList);
        
        return "sale.jsp"; //return back to the rental page
        }  
}
