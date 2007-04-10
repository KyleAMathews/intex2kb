/*
 * backupView.java
 *
 * Created on April 10, 2007, 11:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.views;

/**
 *
 * @author kyle
 */
public class backupView {
    
    /** Creates a new instance of backupView */
    public backupView() {
    }
    
    public static String readPhotos() {
        String photos="";
        // sql
        
        // let into loop 4
        // for each 4
        photos += "<table width=\"500px\" cellpadding=\"5px\" cellspacing=\"10px\">";
        photos += "<tr><th>" + getTitle() + "</th>";
        
        // for each 4
        photos += "<tr><td>";
        photos += "a href=\"" + getidtobig() + "\" class=\"thickbox\" rel=\"gallery\"><img src=\"" + getthumbnailid() + "title=\"" + getTitle() + "/></a><br /><a href=\"downloadfile.jsp?id=" + getId() + ">Download</a><a href =\"deletefile.jsp?id=" + getId() + ">Delete</a></td>";
        
        //finish off
        photos += "</td></table>";
        
                return photos;
    }
}
