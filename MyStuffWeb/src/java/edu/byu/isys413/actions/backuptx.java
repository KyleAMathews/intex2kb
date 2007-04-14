/*
 * backuptx.java
 *
 * Created on April 12, 2007, 9:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kyle
 */
public class backuptx implements edu.byu.isys413.web.Action{
    
    private Transaction tx;
    private Double GBs;
    
    /** Creates a new instance of backuptx */
    public backuptx() {
    }
    
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        backup bck = null;
        //retrieve transaction
        tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
        Store store = new Store("010001117284553c0014b20b500444");
        store.setIsInDB(true);
        store.setDirty(false);
        tx.setStore(store);
        // grab how many gbs and set on revenue source
        GBs = Double.valueOf(request.getParameter("numGBs"));
        bck = (backup)tx.getTxLines().get(0).getRevenueSource();
        bck.setSize(GBs);
        
        // calc subtotal
        bck.calcSubTotal();
        
        return "backupCheckout.jsp";
    }
    
}
