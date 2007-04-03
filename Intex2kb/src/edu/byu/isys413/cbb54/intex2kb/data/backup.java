/*
 * backup.java
 *
 * Created on March 30, 2007, 2:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author kyle
 */
public class backup extends RevenueSource {
    
    private double size;
    private double lengthOfBackup;
    
    /** Creates a new instance of backup */
    public backup(String id) {
        this.id = id;
    }
    
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getLengthOfBackup() {
        return lengthOfBackup;
    }

    public void setLengthOfBackup(double lengthOfBackup) {
        this.lengthOfBackup = lengthOfBackup;
    }
    
}
