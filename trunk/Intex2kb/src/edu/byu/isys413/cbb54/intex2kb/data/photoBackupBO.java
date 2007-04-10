/*
 * Payment.java
 *
 * Created on March 20, 2007, 12:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * Payment represents a payment for a transaction
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
    
    /** Creates a new instance of Payment */
    public photoBackupBO(String id) {
        this.id = id;
    }
    
    /**
     * Returns the payment ID
     */
    public String getId() {
        return id;
    }

    
    /**
     * Payment has been changed
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the dirty value
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns
     */
    public boolean isInDB() {
        return isInDB;
    }

    public void setInDB(boolean isInDB) {
        this.isInDB = isInDB;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
        this.dirty = true;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
        this.dirty = true;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        this.dirty = true;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
        this.dirty = true;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
        this.dirty = true;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.dirty = true;
    }

    
}
