/*
 * AddTXLine.java
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
public class AddTXLine implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of GetRentals */
    public AddTXLine() {
    }
    
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String forrent = request.getParameter("Rental");
        System.out.println("This is the for rent guid: " + forrent);
        String rentalid = null;
        rentalid = (String) session.getAttribute("rentaltx");
        Transaction tx = TransactionDAO.getInstance().read(rentalid);
        //List<String> frlist = new LinkedList<String>();
        
        //create a rental object and the transaction line
        TransactionLine txln = TransactionLineDAO.getInstance().create(tx,"rn");
        txln.setRsType("rental");
        Rental rn = (Rental) txln.getRevenueSource();
        
        //set the rental object as the current rental in the for rent object
        ForRent fr = ForRentDAO.getInstance().read(forrent);
        fr.setCurrentrental(rn.getId());
        double price = ConceptualRentalDAO.getInstance().getRentalPrice(fr);
        
        //System.out.println("And the price is: " + price);
        rn.setPrice(price);
        rn.setDirty(true);
        
        //increment the times rented in the for rent object
        int timesrented = fr.getTimesrented();
        fr.setTimesrented(timesrented++);
        String frid = fr.getId();
        session.setAttribute("frid", frid);
        ForRentDAO.getInstance().save(fr);
        
        
        //set the revenuesource as the rental
        tx.addTxLine(txln);
        
        return "rental.jsp"; //return back to the rental page
        }  
}
