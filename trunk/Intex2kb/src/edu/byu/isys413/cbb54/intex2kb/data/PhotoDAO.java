/*
 * PhotoDAO.java
 *
 * Created on April 10, 2007, 1:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author tylerf
 */
public class PhotoDAO {
    
    ///////////////////////////////////////
    ///   Singleton pattern
    
    private static PhotoDAO instance = null;
    
    /** Creates a new instance of RSSFeedDAO */
    private PhotoDAO() {
    }
    
    public static synchronized PhotoDAO getInstance() {
        if (instance == null) {
            instance = new PhotoDAO();
        }
        return instance;
    }
    
    /////////////////////////////////
    ///   CREATE
    
    /** 
     * There's no need for two creates because we don't need
     * a connection to create BOs.  We run the insert statement
     * later, when it get's saved for the first time.
     */
    public photoBackupBO create() throws Exception{
        String id = null;
        id = GUID.generate();
        photoBackupBO p = new photoBackupBO(id);
        Cache c = Cache.getInstance();
        c.put(p.getId(), p);
        return p;
    }
    
    public void save(photoBackupBO p, FileItem thumb, FileItem med, FileItem file) throws Exception {
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO photobackup (id,membid,caption,thumbnail,mediumpic,originalpic,filename,filetype,filesize,status) values (?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,p.getId());
        ps.setString(2,p.getM());
        ps.setString(3,p.getCaption());
        if(thumb != null){
            System.out.println("trying to write thumb");
            ps.setBinaryStream(4,thumb.getInputStream(),(int)thumb.getSize());
        }else{
            ps.setBinaryStream(4,null,0);
        }
        if(med != null){
            ps.setBinaryStream(5,med.getInputStream(),(int)thumb.getSize());
        }else{
            ps.setBinaryStream(5,null,0);
        }
        ps.setBinaryStream(6,file.getInputStream(),(int)file.getSize());
        ps.setString(7,p.getFilename());
        ps.setString(8,p.getFiletype());
        ps.setString(9,p.getFilesize());
        ps.setString(10,p.getStatus());
        
        ps.execute();
        ps.close();
        ConnectionPool.getInstance().release(conn);
    }
    
    
}
