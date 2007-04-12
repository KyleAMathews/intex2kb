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
 *
 * @author Cameron
 */
public class RentalCheckout implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of GetRentals */
    public RentalCheckout() {
    }
    
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<TransactionLine> txlnList = new LinkedList<TransactionLine>();

        
        
        //pull out the transaction information
        String rentalid = null;
        rentalid = (String) session.getAttribute("rentaltx");
        Transaction rentaltx = TransactionDAO.getInstance().read(rentalid);
        
        txlnList = rentaltx.getTxLines();
        for(int i = 0; i<txlnList.size(); i++){
            String days = request.getParameter(txlnList.get(i).getId());
            double numdays = Double.parseDouble(days);
            Rental rn = (Rental) txlnList.get(i).getRevenueSource();
            double price = rn.getPrice();
            price = price * numdays;
            rn.setPrice(price);
        }
        

        
        return "rentalcheckout.jsp"; //return back to the rental page
        }  
}
