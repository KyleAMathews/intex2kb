/*
 * backupFile.java
 *
 * Created on April 12, 2007, 11:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.actions;

import edu.byu.isys413.cbb54.intex2kb.data.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class is called before going to the backup.jsp page. 
 * Uses PhotoDAO.getInstance().readByMembId() method to retrive list of photo objects. 
 * The list of photo objects is set as a session variable.  The backup.jsp page retrieves
 * the photos and displays them.
 * @author kyle
 */
public class backupFile implements edu.byu.isys413.web.Action{
    
    /**
     * Creates a new instance of backupFile
     */
    public backupFile() {
    }
    
    /**
     * Uses PhotoDAO.getInstance().readByMembId() method to retrive list of photo objects. 
     * The list of photo objects is set as a session variable.
     */
    public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("inside backupFile");
        String membid = null;
        List<photoBackupBO> pb = new LinkedList<photoBackupBO>();
        
        // get membid
        membid = (String)session.getAttribute("membid");
        System.out.println("membid in backupFile: " + membid);
        // pull of pictures
        pb = PhotoDAO.getInstance().readByMembId(membid);
        System.out.println("got photo objects");
        // set list of pictures in session object
        session.setAttribute("files", pb);
        
        return("backup.jsp");
    }
}
