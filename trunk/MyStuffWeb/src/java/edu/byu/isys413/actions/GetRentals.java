/*
 * GetRentals.java
 *
 * Created on April 10, 2007, 1:20 PM
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
 *
 * @author Bryan
 */
public class GetRentals implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of GetRentals */
    public GetRentals() {
    }
    
    /**
     * process GetRentals
     */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<String> storeList = new LinkedList<String>();
        List<String> conceptualProductList = new LinkedList<String>();
        List<String> productList = new LinkedList<String>();
        List<String> forRentList = new LinkedList<String>();
        List<ForRent> forRentBOList = new LinkedList<ForRent>();
        
        
        //check the different check boxes where to search for the products
        if(request.getParameter("StoreOpt1") != null){
            storeList.add("000001117284553c0014b20a500442");
        }
        if(request.getParameter("StoreOpt2") != null){
            storeList.add("000001117284553c0014b20a500443");
        }
        if(request.getParameter("StoreOpt3") != null){
            storeList.add("000001117284553c0014b20a500444");
        }
        
        //get list of conceptual product id's from store id's
        conceptualProductList = StoreProductDAO.getInstance().getProductList(storeList);
        
        //get list of product id with conceptual product id
        PhysicalDAO pdao = (PhysicalDAO)PhysicalDAO.getInstance();
        productList = pdao.getByConceptual(conceptualProductList);
        
        //get only for rent items from product list
        forRentList = ForRentDAO.getInstance().getByID(productList);
        System.out.println(forRentList);
        //create a list of for rent items
        forRentBOList = ForRentDAO.getInstance().getAvailableRentals(forRentList);
        request.setAttribute("forrent", forRentBOList);
        
        return "rental.jsp"; //return back to the rental page
        }  
}
