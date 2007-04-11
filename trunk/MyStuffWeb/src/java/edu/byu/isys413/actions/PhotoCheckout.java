/*
 * PhotoUpload.java
 * Write to photobackup table
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;
import javax.swing.JOptionPane;

/**
 *
 * See http://www.developershome.com/wap/wapUpload/wap_upload.asp?page=jsp
 * for a really good tutorial on JSP file upload using the Jakarta libarary.
 *
 * @author Tyler Farmer
 */
public class PhotoCheckout implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public PhotoCheckout() {
    }
    
    /** Processes a number guess */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
        String membid = (String)session.getAttribute("membid");
        Membership m = MembershipDAO.getInstance().read(membid);
        Customer cust = m.getCustomer();
        
        Employee emp = new Employee("120001117284553c0014b60a500442");
        emp.setInDB(true);
        emp.setDirty(false);
        
        String storeid = (String)request.getParameter("group1");
        List<Store> storeList = StoreDAO.getInstance().getByName((String)request.getParameter("group1"));
        Store store = storeList.get(0);
        JOptionPane.showMessageDialog(null,"Store: " + store.getName());
        
        Transaction tx = (Transaction)session.getAttribute("tx");
        tx.setEmployee(emp);
        tx.setCustomer(cust);
        tx.setStore(store);
        tx.setType("po");
        tx.setStatus("Complete");
        
        String printFormat1 = request.getParameter("PrintFormat1");
        String printFormat2 = request.getParameter("PrintFormat2");
        String printFormat3 = request.getParameter("PrintFormat3");
        String printFormat4 = request.getParameter("PrintFormat4");
        String printFormat5 = request.getParameter("PrintFormat5");
        
        String sourceType = "Digital";
        
        String pfSize1 = "";
        String pfPaper1 = "";
        String pfSize2 = "";
        String pfPaper2 = "";
        String pfSize3 = "";
        String pfPaper3 = "";
        String pfSize4 = "";
        String pfPaper4 = "";
        String pfSize5 = "";
        String pfPaper5 = "";
        
        int qty1 = 0;
        int qty2 = 0;
        int qty3 = 0;
        int qty4 = 0;
        int qty5 = 0;
        
        if((request.getParameter("qty1") != null)){
            qty1 = Integer.parseInt(request.getParameter("qty1"));
        }
        if((request.getParameter("qty2") != null)){
            qty1 = Integer.parseInt(request.getParameter("qty2"));
        }
        if((request.getParameter("qty3") != null)){
            qty1 = Integer.parseInt(request.getParameter("qty3"));
        }
        if((request.getParameter("qty4") != null)){
            qty1 = Integer.parseInt(request.getParameter("qty4"));
        }
        if((request.getParameter("qty5") != null)){
            qty1 = Integer.parseInt(request.getParameter("qty5"));
        }
        
        if(printFormat1 != null){
            pfSize1 = getSize(Integer.valueOf(printFormat1));
            pfPaper1 = getPaper(Integer.valueOf(printFormat1));
            
            printOrder po1 = (printOrder)PrintOrderDAO.getInstance().create();
            PhotoSet ps1 = PhotoSetDAO.getInstance().create();
            
            //read printFormat
            printFormat pf1 = printFormatDAO.getInstance().getPrintFormat(pfSize1,pfPaper1,sourceType);
            
            //set PrintOrder values
            po1.setPOID(GUID.generate());
            po1.setPrintFormat(pf1);
            po1.setQuantity(qty1);
            
            //set PhotoSet values
            ps1.setDescription((String)request.getAttribute("FileName1"));
            ps1.setNumPhotos(1);
            po1.setPhotoSet(ps1);
            
            //set total price in RSBO
            po1.setTotalPrice(po1.getQuantity(),pf1.getPrice(),ps1.getNumPhotos());
            
            //Create TX-Line
            TransactionLine txln1 = TransactionLineDAO.getInstance().create(tx,po1.getId());
            txln1.setRevenueSource(po1);
            txln1.setRsType("Print Order - " + po1.getPhotoSet().getDescription());
            tx.addTxLine(txln1);
            
            UpdateController.getInstance().saveTransaction(tx);
        }
        if(printFormat2 != null){
            pfSize2 = getSize(Integer.valueOf(printFormat2));
            pfPaper2 = getPaper(Integer.valueOf(printFormat2));
        }
        if(printFormat3 != null){
            pfSize3 = getSize(Integer.valueOf(printFormat3));
            pfPaper3 = getPaper(Integer.valueOf(printFormat3));
        }
        if(printFormat4 != null){
            pfSize4 = getSize(Integer.valueOf(printFormat4));
            pfPaper4 = getPaper(Integer.valueOf(printFormat4));
        }
        if(printFormat5 != null){
            pfSize5 = getSize(Integer.valueOf(printFormat5));
            pfPaper5 = getPaper(Integer.valueOf(printFormat5));
        }
        
        System.out.println((String)session.getAttribute("FileName1"));
        System.out.println(pfSize1);
        System.out.println(pfPaper1);
        System.out.println(pfSize2);
        System.out.println(pfPaper2);
        System.out.println(pfSize3);
        System.out.println(pfPaper3);
        System.out.println(pfSize4);
        System.out.println(pfPaper4);
        System.out.println(pfSize5);
        System.out.println(pfPaper5);
        
        
        
        return "photo.jsp";
    }//process
    
    private String getSize(int pf){
        String pfSize1 = "";
        
        switch(pf){
            case 0:
                pfSize1 = "4x6";
                break;
            case 1:
                pfSize1 = "4x6";
                break;
            case 2:
                pfSize1 = "5x7";
                break;
            case 3:
                pfSize1 = "5x7";
                break;
        }
        
        return pfSize1;
    }//end getSize
    
    private String getPaper(int pf){
        String pfPaper1 = "";
        
        switch(pf){
            case 0:
                pfPaper1 = "Matte";
                break;
            case 1:
                pfPaper1 = "Glossy";
                break;
            case 2:
                pfPaper1 = "Matte";
                break;
            case 3:
                pfPaper1 = "Glossy";
                break;
        }
        
        return pfPaper1;
    }//end getPaper
    
}//PhotoUpload.java

