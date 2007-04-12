/*
 * NumberGame.java
 *
 * Created on March 26, 2007, 7:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.*;

/**
 *
 * @author conan
 */
public class submitTX implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public submitTX() {
    }
  
    /** Processes a number guess */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        HttpSession session = request.getSession();
        
        Transaction tx = TransactionDAO.getInstance().read((String)session.getAttribute("tx"));
        
        Payment pmt = PaymentDAO.getInstance().create(tx);
        pmt.setAmount(tx.calculateTotal());
        pmt.setCcExpiration(tx.getCustomer().getMembership().getCcExpiration());
        pmt.setCcNumber(tx.getCustomer().getMembership().getCreditCard());
        pmt.setChange((double)0);
        pmt.setType("Credit Card");
        
        tx.setPayment(pmt);
        
        UpdateController.getInstance().saveTransaction(tx);
        
        List<String> email = new LinkedList<String>();
        email.add(tx.getCustomer().getEmail());
        email.add("tylerfarmer@gmail.com");
        
        formatNumber fmt = new formatNumber();
        
        String message = (tx.getCustomer().getFname() + " " + tx.getCustomer().getLname() + ":\n" +
                "\nYour order at www.2KMyStuff.com has been confirmed.  The total came to $" +
                fmt.fmt(tx.calculateTotal()) + ".\n\nThank you for your order!");
        
        
        sendMail s = new sendMail();
        s.postMail(email,message);
        
        //clear out the current transaction
        String type = tx.getType();
        if(type.matches("rental")){
            session.setAttribute("rentaltx", null);
        }
        if(type.matches("po")){
            session.setAttribute("phototx", null);
        }
        if(type.matches("sale")){
            session.setAttribute("saletx", null);
        }
        tx = null;
        
        return "confirmation.jsp";
    }//process
    
    
}//NumberGame class
