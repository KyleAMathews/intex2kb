/*
 * printOrder.java
 *
 * Created on March 30, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

/**
 *
 * @author kyle
 */
public class printOrder extends RevenueSource {
    
    private PhotoSet photoSet;
    private printFormat printFormat;
    private int quantity;
    private String poID;
    
    /** Creates a new instance of printOrder */
    public printOrder(String id) {
        this.id = id;
    }

    public PhotoSet getPhotoSet() {
        return photoSet;
    }

    public void setPhotoSet(PhotoSet photoSet) {
        this.photoSet = photoSet;
        this.dirty = true;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.dirty = true;
    }

    public printFormat getPrintFormat() {
        return printFormat;
    }

    public void setPrintFormat(printFormat printFormat) {
        this.printFormat = printFormat;
        this.dirty = true;
    }

    public String getPoID() {
        return poID;
    }

    public void setPOID(String poID) {
        this.poID = poID;
        this.dirty = true;
    }
    
}
