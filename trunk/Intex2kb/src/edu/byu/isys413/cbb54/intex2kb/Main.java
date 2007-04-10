/*
 * Main.java
 *
 * Created on April 2, 2007, 9:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 *This is a change
 */

package edu.byu.isys413.cbb54.intex2kb;
import edu.byu.isys413.cbb54.intex2kb.data.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Bryan
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            //new tester().main(args);
//        try{
//        batchBackupRepair.backup();
//        }catch(DataException e){
//              System.out.println(e);
//        }
            //Login lo = new Login();
            //lo.setVisible(true);
            //new Login().setVisible(true);//creates and loads the gui
       // new NewJFrame().setVisible(true);
            
            printConvBatch.getInstance().getReady();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
