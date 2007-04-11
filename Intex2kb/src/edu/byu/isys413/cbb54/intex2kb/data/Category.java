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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    public boolean isInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
    
}
