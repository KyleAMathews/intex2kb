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

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.setDirty(true);
    }

    public int getNumPhotos() {
        return numPhotos;
    }

    public void setNumPhotos(int numPhotos) {
        this.numPhotos = numPhotos;
        this.setDirty(true);
    }

    public boolean getIsDirty() {
        return dirty;
    }

    public boolean getIsInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
// take it away Tyler