/*
 * Main.java
 *
 * Created on April 3, 2007, 9:35 AM
 */

package edu.byu.isys413.cbb54.intex2kb.views;
//import com.sun.tools.javac.tree.Tree.Return;
import edu.byu.isys413.cbb54.intex2kb.data.*;
import edu.byu.isys413.cbb54.intex2kb.data.Payment;
import edu.byu.isys413.cbb54.intex2kb.views.*;

import java.awt.Color;
import javax.swing.UIManager;

/**
 *
 * @author  Cameron
 */
public class Confirmation extends javax.swing.JFrame {
    
    private TableModel model;
    private Transaction tx;
    private formatNumber fmtNum = new formatNumber();
    
    /** Creates new form Main */
    public Confirmation(Transaction tx1,TableModel model1) throws DataException {
        //        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Set the selection background to yellow
        UIManager.put("Table.selectionBackground",Color.yellow );
        tx = tx1;
        model = model1;
        
        
        
        // Set the look and feel to the system's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
        
        // Insert the TableModel
        initComponents();
        jTable1.setModel(model);
        
        //set summary fields
        custName.setText(tx.getCustomer().getFname() + " " + tx.getCustomer().getLname());
        double txTotalDbl = tx.calculateTotal();
        txTotal.setText(formatNumber.fmt(txTotalDbl));
        pmtAmount.setText(formatNumber.fmt(tx.getPayment().getAmount()));
        change.setText(formatNumber.fmt(tx1.getPayment().getChange()));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Default = new javax.swing.JPanel();
        custName = new javax.swing.JLabel();
        txTotal = new javax.swing.JLabel();
        pmtAmount = new javax.swing.JLabel();
        change = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        table = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        finish = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.X_AXIS));

        jPanel2.setLayout(new java.awt.CardLayout());

        Default.setBackground(new java.awt.Color(255, 255, 255));
        Default.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        Default.setPreferredSize(new java.awt.Dimension(264, 285));
        custName.setText("jLabel2");

        txTotal.setText("jLabel2");

        pmtAmount.setText("jLabel2");

        change.setText("jLabel2");

        jLabel2.setText("Customer Name:");

        jLabel3.setText("Total Amount:");

        jLabel4.setText("Amount Paid:");

        jLabel5.setText("Change:");

        org.jdesktop.layout.GroupLayout DefaultLayout = new org.jdesktop.layout.GroupLayout(Default);
        Default.setLayout(DefaultLayout);
        DefaultLayout.setHorizontalGroup(
            DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(DefaultLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel4)
                    .add(jLabel3)
                    .add(jLabel2)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txTotal)
                    .add(custName)
                    .add(pmtAmount)
                    .add(change))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        DefaultLayout.setVerticalGroup(
            DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(DefaultLayout.createSequentialGroup()
                .add(34, 34, 34)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(custName)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(txTotal))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(pmtAmount))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(change))
                .addContainerGap(334, Short.MAX_VALUE))
        );
        jPanel2.add(Default, "card9");

        jPanel5.add(jPanel2);

        jPanel6.setPreferredSize(new java.awt.Dimension(10, 100));
        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 10, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 446, Short.MAX_VALUE)
        );
        jPanel5.add(jPanel6);

        getContentPane().add(jPanel5, java.awt.BorderLayout.EAST);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setText("My Stuff");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.setViewportView(jTable1);

        jPanel3.add(table, java.awt.BorderLayout.CENTER);

        jPanel7.setMinimumSize(new java.awt.Dimension(1, 1));
        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 10, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 446, Short.MAX_VALUE)
        );
        jPanel3.add(jPanel7, java.awt.BorderLayout.EAST);

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 10, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 446, Short.MAX_VALUE)
        );
        jPanel3.add(jPanel8, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setMinimumSize(new java.awt.Dimension(100, 30));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 50));
        finish.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        finish.setText("FINISH");
        finish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(709, Short.MAX_VALUE)
                .add(finish)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(finish)
                .addContainerGap())
        );
        getContentPane().add(jPanel4, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void finishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishActionPerformed
        this.dispose();
        getCustomer g = new getCustomer();
        g.setVisible(true);
    }//GEN-LAST:event_finishActionPerformed
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Default;
    private javax.swing.JLabel change;
    private javax.swing.JLabel custName;
    private javax.swing.JButton finish;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel pmtAmount;
    private javax.swing.JScrollPane table;
    private javax.swing.JLabel txTotal;
    // End of variables declaration//GEN-END:variables
    
    
}
