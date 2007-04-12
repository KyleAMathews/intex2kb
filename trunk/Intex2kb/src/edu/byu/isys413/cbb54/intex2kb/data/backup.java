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
 * Backup objects represent a backup service of a member
 * @author kyle
 */
public class backup extends RevenueSource {
    
    private double size;
    private double lengthOfBackup;
    
    /**
     * Creates a new instance of backup
     * @param id String
     */
    public backup(String id) {
        this.id = id;
    }
    
    /**
     * Returns the size of the backup space
     * @return Double
     */
    public double getSize() {
        return size;
    }

    /**
     * Sets the size of the backup
     * @param size Double
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Returns the length of the backup service
     * @return Double
     */
    public double getLengthOfBackup() {
        return lengthOfBackup;
    }

    /**
     * Sets the length of the backup service
     * @param lengthOfBackup Double
     */
    public void setLengthOfBackup(double lengthOfBackup) {
        this.lengthOfBackup = lengthOfBackup;
    }
    
}
