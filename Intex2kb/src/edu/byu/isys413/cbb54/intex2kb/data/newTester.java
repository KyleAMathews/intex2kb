/*
 * tester.java
 *
 * Created on February 21, 2007, 4:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.byu.isys413.cbb54.intex2kb.data;


import java.math.RoundingMode;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A simple tester class for my data layer.
 *
 * @author Conan C. Albrecht
 */
public class newTester {
    
    /**
     * main method of new tester
     */
    public void main(String args[]) {
        try {
            
            System.out.println(CategoryDAO.getInstance().getCategoryList());
        } catch (DataException ex) {
            ex.printStackTrace();
        }
    
    } 
}//class
