/*
 * FileUpload.java
 *
 */

package edu.byu.isys413.actions;

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
public class PhotoUpload implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public PhotoUpload() {
    }
  
    /** Processes a number guess */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("Myname is (regular request object):" + request.getParameter("datafile1"));
        
        // parse the request using the Jakarta file upload library
        // convert the requestParams into a map.  this assumes that each form field on
        // the page has a unique name.  if two fields have the same name, the second
        // one will blast the first one
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List paramList = servletFileUpload.parseRequest(request);
        Map params = new TreeMap();
        for (int i = 0; i < paramList.size(); i++) {
            FileItem item = (FileItem)paramList.get(i);
            params.put(item.getFieldName(), item);
        }//for
            
        // get a regular parameter from the request
        String datafile1 = ((FileItem)params.get("datafile1")).getString();
        System.out.println(datafile1);
        
        // get a file from the request
        FileItem fileinfo = (FileItem)params.get("datafile1");
        System.out.println("File name: " + fileinfo.getName());
        System.out.println("File size: " + fileinfo.getSize());
        System.out.println("File type: " + fileinfo.getContentType());
                
        // the fileitem also has the bytes of the file.  you could easily call
        // setBinaryStream in your PreparedStatement since fileitem.getInputStream
        // gives you an input stream, which is what setBinaryStream wants.
        
        
        return "FileUpload2.jsp";
    }//process
    
    
}//NumberGame class
