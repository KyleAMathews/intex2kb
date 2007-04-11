/*
 * checkoutView.java
 *
 * Created on April 11, 2007, 10:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.DataException;
import edu.byu.isys413.cbb54.intex2kb.data.TransactionDAO;
import edu.byu.isys413.cbb54.intex2kb.data.Transaction;
import edu.byu.isys413.cbb54.intex2kb.data.TransactionLine;
import edu.byu.isys413.cbb54.intex2kb.data.MembershipDAO;
import edu.byu.isys413.cbb54.intex2kb.data.Membership;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author kyle
 */
public class checkoutView implements edu.byu.isys413.web.Action {
    
    static Transaction tx = null;
    static Membership memb = null;
    static TransactionLine txLine1 = null;
    static HttpSession session = null;
    
    /** Creates a new instance of checkoutView */
    public checkoutView() {
    }
    
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        session = request.getSession();
        session.setAttribute("checkoutTxType", "ba");
        
        return "checkout.jsp";
    }
    
    public static String getTx(String txType) throws DataException{
        String txHtml = "dummy text";
        System.out.println("value of txType: " + txType);
        tx = TransactionDAO.getInstance().read((String)session.getAttribute("backuptx"));
        memb = MembershipDAO.getInstance().read((String)session.getAttribute("membid"));
        txLine1 = tx.getTxLines().get(0);
        System.out.println("customer's name " + memb.getCustomer().getFname() + " " + memb.getBackupExpDate() + " " + memb.getBackupSize());
        if (txType.equals("sa")){
            txHtml = saleHtml();
        }else if (txType.equals("re")){
            txHtml = rentalHtml();
        }else if (txType.equals("po")){
            txHtml = photoHtml();
        }else if (txType.equals("ba")){
            txHtml = backupHtml();
            
        }
        return txHtml;
    }
    
    public static String getMembInfo(){
        String membHtml = "";
        membHtml += "<table>" +
                "  <thead>" +
                "    <tr>" +
                "      <th>Billing + Shipping Info</th>" +
                "    </tr>" +
                "  </thead>" +
                "  <tbody>" +
                "    <tr>" +
                "      <td>Name</td>" +
                "      <td>" +
                "</td>  " +
                "  </tr>   " +
                " <tr>" +
                "      <td>Address</td> " +
                "     <td></td> " +
                "   </tr>" +
                "    <tr> " +
                "     <td>Email</td>" +
                "      <td></td>" +
                "    </tr>" +
                "    <tr> " +
                "     <td>Payment</td> " +
                "     <td>" +
                "</td>   " +
                " </tr> " +
                " </tbody>" +
                "</table>";
        return membHtml;
    }
    
    
    private static String saleHtml(){
        return "sale transaction";
    }
    
    private static String rentalHtml(){
        return "rental transaction";
    }
    
    private static String photoHtml(){
        return "photo transaction";
    }
    
    private static String backupHtml(){
        return "backup transaction";
    }
}
