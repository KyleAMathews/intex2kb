/*
 * Interest.java
 *
 * Created on February 26, 2007, 6:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 * An object representing a particular type of interest
 * @author Cameron
 */
public class Interest {
    private String id;
    private String title;
    private String description;
    private boolean dirty = false;
    private boolean inDB = false;
    
    
    /**
     * Creates a new instance of Interest
     * @param id String
     */
    public Interest(String id) {
        this.id = id;
    }

    /**
     * Returns the interests id
     * @return String(id)
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the interests title
     * @return String(title)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the interests title
     * @param title String
     */
    public void setTitle(String title) {
        this.dirty = true;
        this.title = title;
    }

    /**
     * Returns the interests description
     * @return String(description)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the interests description
     * @param description String
     */
    public void setDescription(String description) {
        this.dirty = true;
        this.description = description;
    }

    /**
     * Returns a boolean of whether the object has changed since it was last saved
     * @return boolean(dirty)
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets a boolean of whether the object has changed since it was last saved
     * @param dirty Boolean
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Returns a boolean of whether the object has been saved to the database
     * @return Boolean(inDB)
     */
    public boolean isInDB() {
        return inDB;
    }

    /**
     * Sets a boolean of whether the object has been saved to the database
     * @param inDB Boolean
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }
    
}
