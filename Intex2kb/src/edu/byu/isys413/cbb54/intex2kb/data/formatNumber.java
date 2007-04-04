/*
 * formatNumber.java
 *
 * Created on April 4, 2007, 5:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import java.text.DecimalFormat;

/**
 *
 * @author tylerf
 */
public class formatNumber {
    
    /** Creates a new instance of formatNumber */
    public formatNumber() {
        
    }
    
    private String fmt(double number) {
        DecimalFormat formatter = new DecimalFormat("###,##0.00");
        return formatter.format(number);
        
    }
}
