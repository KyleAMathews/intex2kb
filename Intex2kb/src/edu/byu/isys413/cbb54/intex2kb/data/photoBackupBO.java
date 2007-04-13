/*
 * Payment.java
 *
 * Created on March 20, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.fileupload.FileItem;

/**
 * Photo Backup is a service of backing up photos
 * @author Cameron
 */
public class photoBackupBO {
    
    private String id;
    private String m;
    private String caption;
    private String filename;
    private String filetype;
    private String filesize;
    private String status;
    private boolean dirty = false;
    private boolean isInDB = false;
    
    /**
     * Creates a new PhotoBackup object
     * @param id Guid for PhotoBackup
     */
    public photoBackupBO(String id) {
        this.id = id;
    }
    
    /**
     * Returns the photo backup ID
     * @return ID
     */
    public String getId() {
        return id;
    }
    
    
    /**
     * Returns a boolean indicating if the photo backup object has
     * changed since being saved to the database
     * @return Boolean dirty
     */
    public boolean isDirty() {
        return dirty;
    }
    
    /**
     * Sets the dirty value
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
    /**
     * Returns a boolean indicating if the object is saved in the database
     * @return Boolean inDB
     */
    public boolean isInDB() {
        return isInDB;
    }
    
    /**
     * Sets the boolean representing the object is in the database
     * @param isInDB Boolean
     */
    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }
    
    /**
     * Returns the membership id of the owner of the backup
     * @return String member id
     */
    public String getM() {
        return m;
    }
    
    /**
     * Sets the membership id of the owner of the backup
     * @param m String Membership id
     */
    public void setM(String m) {
        this.m = m;
        this.dirty = true;
    }
    
    /**
     * Returns the caption associated with the photo
     * @return String Caption
     */
    public String getCaption() {
        return caption;
    }
    
    /**
     * Sets the caption associated with a backup photo
     * @param caption String Caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
        this.dirty = true;
    }
    
    /**
     * Returns the file name of the photo
     * @return File Name
     */
    public String getFilename() {
        return filename;
    }
    
    /**
     * Sets the file name of a backup photo
     * @param filename String
     */
    public void setFilename(String filename) {
        this.filename = filename;
        this.dirty = true;
    }
    
    /**
     * Returns the file type of a backup
     * @return String Type
     */
    public String getFiletype() {
        return filetype;
    }
    
    /**
     * Sets the type of a backup file
     * @param filetype String
     */
    public void setFiletype(String filetype) {
        this.filetype = filetype;
        this.dirty = true;
    }
    
    /**
     * Returns the file size of a backup
     * @return String
     */
    public String getFilesize() {
        return filesize;
    }
    
    /**
     * Sets the file size of a backup
     * @param filesize String
     */
    public void setFilesize(String filesize) {
        this.filesize = filesize;
        this.dirty = true;
    }
    
    /**
     * Returns the status of a backup
     * @return String Status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Sets the status of a backup
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
        this.dirty = true;
    }
    public InputStream getThumbnail(String id) throws Exception{
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("SELECT \"thumbnail\" FROM \"photoBackup\" WHERE \"id\" = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        InputStream thumb = null;
        if (rs.next()){
            thumb = rs.getBinaryStream("thumbnail");
        }
        
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        return thumb;
    }
    
    public InputStream getMediumPic(String id) throws Exception{
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("SELECT \"mediumpic\" FROM \"photoBackup\" WHERE \"id\" = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        InputStream thumb = null;
        if (rs.next()){
            thumb = rs.getBinaryStream("mediumpic");
        }
        
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        return thumb;
    }
    
    public InputStream getOriginal(String id) throws Exception{
        Connection conn = ConnectionPool.getInstance().get();
        PreparedStatement ps = conn.prepareStatement("SELECT \"originalpic\" FROM \"photoBackup\" WHERE \"id\" = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        InputStream thumb = null;
        if (rs.next()){
            thumb = rs.getBinaryStream("originalpic");
        }
        
        conn.commit();
        ConnectionPool.getInstance().release(conn);
        
        return thumb;
    }
    
}
