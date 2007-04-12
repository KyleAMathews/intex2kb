/*
 * AddTransaction.java
 *
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.Session;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * See http://www.developershome.com/wap/wapUpload/wap_upload.asp?page=jsp 
 * for a really good tutorial on JSP file upload using the Jakarta libarary.
 *
 * @author Kyle Mathews
 */
public class AddTransaction implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public AddTransaction() {
    }
  
    /**
     * Process addTransaction
     */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("Myname is (regular request object):" + request.getParameter("myname"));
        
        HttpSession session = request.getSession();
        //session.getAttribute();
        return "rental.jsp";
    }//process
    
    
}//NumberGame class
