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
        
        System.out.println("PrintFormat 1: " + request.getParameter("PrintFormat1"));
        System.out.println("Qty 1: " + (String)request.getParameter("qty1"));
        
        return "checkout.jsp";
    }//process
    
    
}//PhotoUpload.java
