/*
 * PhotoSet.java
 *
 * Created on March 30, 2007, 2:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author kyle
 */
public class PhotoSet{
    
    private String id;
    private String description;
    private int numPhotos;
    private boolean dirty = false;
    private boolean inDB = false;
    
    /** Creates a new instance of PhotoSet */
    public PhotoSet(String id) {
        this.id = id;
    }

    /**
     * returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * returns the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description
     */
    public void setDescription(String description) {
        this.description = description;
        this.setDirty(true);
    }

    /**
     * returns the number of photos
     */
    public int getNumPhotos() {
        return numPhotos;
    }

    /**
     * sets the number of photos
     */
    public void setNumPhotos(int numPhotos) {
        this.numPhotos = numPhotos;
        this.setDirty(true);
    }

    /**
     * returns the dirty boolean
     */
    public boolean getIsDirty() {
        return dirty;
    }

    /**
     * returns the database boolean
     */
    public boolean getIsInDB() {
        return inDB;
    }

    /**
     * sets the database boolean
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    /**
     * sets the dirty boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
// take it away Tyler