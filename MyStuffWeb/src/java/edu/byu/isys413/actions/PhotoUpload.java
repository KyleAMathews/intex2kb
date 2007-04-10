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
public class PhotoUpload implements edu.byu.isys413.web.Action {
    
    /** Creates a new instance of NumberGame */
    public PhotoUpload() {
    }
    
    /** Processes a number guess */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        
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
        
        // get a file from the request
        List<FileItem> f = new LinkedList();
        
        if(((FileItem)params.get("datafile1")).getName() != ""){
            f.add((FileItem)params.get("datafile1"));
        }
        if(((FileItem)params.get("datafile2")).getName() != ""){
            f.add((FileItem)params.get("datafile2"));
        }
        if(((FileItem)params.get("datafile3")).getName() != ""){
            f.add((FileItem)params.get("datafile3"));
        }
        if(((FileItem)params.get("datafile4")).getName() != ""){
            f.add((FileItem)params.get("datafile4"));
        }
        if(((FileItem)params.get("datafile5")).getName() != ""){
            f.add((FileItem)params.get("datafile5"));
        }
        
        //create PhotoBackupBO & write to DB
        for(int i = 0;i < f.size(); i++){
            photoBackupBO photo = PhotoDAO.getInstance().create();
            photo.setCaption(null);
            photo.setFilename(f.get(i).getName());
            photo.setFilesize(Long.toString(f.get(i).getSize()));
            photo.setFiletype(f.get(i).getContentType());
            photo.setM((String)session.getAttribute("membid"));
            photo.setStatus("1");
            
            //save photo to DB
            PhotoDAO.getInstance().save(photo,null,null,f.get(i));
            JOptionPane.showMessageDialog(null,Integer.toString(i));
            session.setAttribute(Integer.toString(i + 1),photo.getFilename());
        }
        
        // the fileitem also has the bytes of the file.  you could easily call
        // setBinaryStream in your PreparedStatement since fileitem.getInputStream
        // gives you an input stream, which is what setBinaryStream wants.
        
        
        return "photoDisplay.jsp";
    }//process
    
    
}//PhotoUpload.java
