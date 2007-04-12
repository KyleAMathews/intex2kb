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
         
        Transaction tx = TransactionDAO.getInstance().read((String)session.getAttribute("phototx"));
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
            qty2 = Integer.parseInt(request.getParameter("qty2"));
        }
        if((request.getParameter("qty3") != null)){
            qty3 = Integer.parseInt(request.getParameter("qty3"));
        }
        if((request.getParameter("qty4") != null)){
            qty4 = Integer.parseInt(request.getParameter("qty4"));
        }
        if((request.getParameter("qty5") != null)){
            qty5 = Integer.parseInt(request.getParameter("qty5"));
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
            session.setAttribute("pf1",(pfSize1 + " - " + pfPaper1));
            po1.setQuantity(qty1);
            session.setAttribute("qty1",Integer.toString(qty1));
            
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
            
            //UpdateController.getInstance().saveTransaction(tx);
        }
        if(printFormat2 != null){
            pfSize2 = getSize(Integer.valueOf(printFormat2));
            pfPaper2 = getPaper(Integer.valueOf(printFormat2));
            
            printOrder po2 = (printOrder)PrintOrderDAO.getInstance().create();
            PhotoSet ps2 = PhotoSetDAO.getInstance().create();
            
            //read printFormat
            printFormat pf2 = printFormatDAO.getInstance().getPrintFormat(pfSize2,pfPaper2,sourceType);
            
            //set PrintOrder values
            po2.setPOID(GUID.generate());
            po2.setPrintFormat(pf2);
            session.setAttribute("pf2",(pfSize2 + " - " + pfPaper2));
            po2.setQuantity(qty2);
            session.setAttribute("qty2",Integer.toString(qty2));
            
            //set PhotoSet values
            ps2.setDescription((String)request.getAttribute("FileName2"));
            ps2.setNumPhotos(1);
            po2.setPhotoSet(ps2);
            
            //set total price in RSBO
            po2.setTotalPrice(po2.getQuantity(),pf2.getPrice(),ps2.getNumPhotos());
            
            //Create TX-Line
            TransactionLine txln2 = TransactionLineDAO.getInstance().create(tx,po2.getId());
            txln2.setRevenueSource(po2);
            txln2.setRsType("Print Order - " + po2.getPhotoSet().getDescription());
            tx.addTxLine(txln2);
        }
        if(printFormat3 != null){
            pfSize3 = getSize(Integer.valueOf(printFormat3));
            pfPaper3 = getPaper(Integer.valueOf(printFormat3));
            
            printOrder po3 = (printOrder)PrintOrderDAO.getInstance().create();
            PhotoSet ps3 = PhotoSetDAO.getInstance().create();
            
            //read printFormat
            printFormat pf3 = printFormatDAO.getInstance().getPrintFormat(pfSize3,pfPaper3,sourceType);
            
            //set PrintOrder values
            po3.setPOID(GUID.generate());
            po3.setPrintFormat(pf3);
            session.setAttribute("pf3",(pfSize3 + " - " + pfPaper3));
            po3.setQuantity(qty3);
            session.setAttribute("qty3",Integer.toString(qty3));
            
            //set PhotoSet values
            ps3.setDescription((String)request.getAttribute("FileName3"));
            ps3.setNumPhotos(1);
            po3.setPhotoSet(ps3);
            
            //set total price in RSBO
            po3.setTotalPrice(po3.getQuantity(),pf3.getPrice(),ps3.getNumPhotos());
            
            //Create TX-Line
            TransactionLine txln3 = TransactionLineDAO.getInstance().create(tx,po3.getId());
            txln3.setRevenueSource(po3);
            txln3.setRsType("Print Order - " + po3.getPhotoSet().getDescription());
            tx.addTxLine(txln3);
        }
        if(printFormat4 != null){
            pfSize4 = getSize(Integer.valueOf(printFormat4));
            pfPaper4 = getPaper(Integer.valueOf(printFormat4));
            
            printOrder po4 = (printOrder)PrintOrderDAO.getInstance().create();
            PhotoSet ps4 = PhotoSetDAO.getInstance().create();
            
            //read printFormat
            printFormat pf4 = printFormatDAO.getInstance().getPrintFormat(pfSize4,pfPaper4,sourceType);
            
            //set PrintOrder values
            po4.setPOID(GUID.generate());
            po4.setPrintFormat(pf4);
            session.setAttribute("pf4",(pfSize4 + " - " + pfPaper4));
            po4.setQuantity(qty4);
            session.setAttribute("qty4",Integer.toString(qty4));
            
            //set PhotoSet values
            ps4.setDescription((String)request.getAttribute("FileName4"));
            ps4.setNumPhotos(1);
            po4.setPhotoSet(ps4);
            
            //set total price in RSBO
            po4.setTotalPrice(po4.getQuantity(),pf4.getPrice(),ps4.getNumPhotos());
            
            //Create TX-Line
            TransactionLine txln4 = TransactionLineDAO.getInstance().create(tx,po4.getId());
            txln4.setRevenueSource(po4);
            txln4.setRsType("Print Order - " + po4.getPhotoSet().getDescription());
            tx.addTxLine(txln4);
        }
        if(printFormat5 != null){
            pfSize5 = getSize(Integer.valueOf(printFormat5));
            pfPaper5 = getPaper(Integer.valueOf(printFormat5));
            
            printOrder po5 = (printOrder)PrintOrderDAO.getInstance().create();
            PhotoSet ps5 = PhotoSetDAO.getInstance().create();
            
            //read printFormat
            printFormat pf5 = printFormatDAO.getInstance().getPrintFormat(pfSize5,pfPaper5,sourceType);
            
            //set PrintOrder values
            po5.setPOID(GUID.generate());
            po5.setPrintFormat(pf5);
            session.setAttribute("pf5",(pfSize5 + " - " + pfPaper5));
            po5.setQuantity(qty5);
            session.setAttribute("qty5",Integer.toString(qty5));
            
            //set PhotoSet values
            ps5.setDescription((String)request.getAttribute("FileName5"));
            ps5.setNumPhotos(1);
            po5.setPhotoSet(ps5);
            
            //set total price in RSBO
            po5.setTotalPrice(po5.getQuantity(),pf5.getPrice(),ps5.getNumPhotos());
            
            //Create TX-Line
            TransactionLine txln5 = TransactionLineDAO.getInstance().create(tx,po5.getId());
            txln5.setRevenueSource(po5);
            txln5.setRsType("Print Order - " + po5.getPhotoSet().getDescription());
            tx.addTxLine(txln5);
        }
        session.setAttribute("checkoutTxType","po");
        return "photoCheckout.jsp";
    }//process
    
    private String getSize(int pf){
        String pfSize = "";
        
        switch(pf){
            case 0:
                pfSize = "4x6";
                break;
            case 1:
                pfSize = "4x6";
                break;
            case 2:
                pfSize = "5x7";
                break;
            case 3:
                pfSize = "5x7";
                break;
        }
        
        return pfSize;
    }//end getSize
    
    private String getPaper(int pf){
        String pfPaper = "";
        
        switch(pf){
            case 0:
                pfPaper = "Matte";
                break;
            case 1:
                pfPaper = "Glossy";
                break;
            case 2:
                pfPaper = "Matte";
                break;
            case 3:
                pfPaper = "Glossy";
                break;
        }
        
        return pfPaper;
    }//end getPaper
    
}//PhotoUpload.java

