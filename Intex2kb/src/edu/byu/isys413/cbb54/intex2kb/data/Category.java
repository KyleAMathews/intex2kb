/*
 * Category.java
 *
 * Created on April 11, 2007, 3:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author Cameron
 */
public class Category {
    
    private String id;
    private String name;
    private boolean inDB;
    private boolean dirty;
    
    /** Creates a new instance of Category */
    public Category(String id,String name) {
        this.id = id;
        this.name = name;
        this.dirty = true;
    }

    /**
     * Returns the ID of the string
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category
     */
    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    /**
     * Returns the database boolean
     */
    public boolean isInDB() {
        return inDB;
    }

    /**
     * Sets the database boolean
     */
    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    /**
     * Returns the dirty variable
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets the dirty variable
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
