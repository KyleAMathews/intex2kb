/*
 * Main.java
 *
 * Created on April 3, 2007, 9:35 AM
 */

package edu.byu.isys413.cbb54.intex2kb.views;
import com.sun.tools.javac.tree.Tree.Return;
import edu.byu.isys413.cbb54.intex2kb.data.*;
import edu.byu.isys413.cbb54.intex2kb.data.Payment;

import java.awt.Color;
import javax.swing.UIManager;

/**
 *
 * @author  Cameron
 */
public class PmtGUI extends javax.swing.JFrame {
    
    private TableModel model;
    private Transaction tx;
    private Main m;
    
    /** Creates new form Main */
    public PmtGUI(Transaction tx1, Main m1, TableModel model1) {
        //        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Set the selection background to yellow
        UIManager.put("Table.selectionBackground",Color.yellow );
        tx = tx1;
        m = m1;
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
        jLabel2 = new javax.swing.JLabel();
        paymentTypeInput = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        paymentAmountInput = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ccNumInput = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ccExpInput = new javax.swing.JTextField();
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
        cancel = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.X_AXIS));

        jPanel2.setLayout(new java.awt.CardLayout());

        Default.setBackground(new java.awt.Color(255, 255, 255));
        Default.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel2.setText("Payment Type:");

        paymentTypeInput.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cash", "Credit Card" }));

        jLabel3.setText("Payment Amount:");

        jLabel4.setText("Credit Card #:");

        jLabel5.setText("Credit Card Exp:");

        org.jdesktop.layout.GroupLayout DefaultLayout = new org.jdesktop.layout.GroupLayout(Default);
        Default.setLayout(DefaultLayout);
        DefaultLayout.setHorizontalGroup(
            DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(DefaultLayout.createSequentialGroup()
                .addContainerGap()
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel5)
                    .add(jLabel4)
                    .add(jLabel3)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(paymentTypeInput, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(paymentAmountInput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                    .add(ccNumInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ccExpInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 107, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        DefaultLayout.setVerticalGroup(
            DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(DefaultLayout.createSequentialGroup()
                .add(47, 47, 47)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(paymentTypeInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(paymentAmountInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(ccNumInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(DefaultLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(ccExpInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(278, Short.MAX_VALUE))
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

        cancel.setFont(new java.awt.Font("Lucida Grande", 1, 18));
        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(598, Short.MAX_VALUE)
                .add(cancel)
                .add(16, 16, 16)
                .add(finish)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(finish)
                    .add(cancel))
                .addContainerGap())
        );
        getContentPane().add(jPanel4, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.dispose();
        m.setVisible(true);
    }//GEN-LAST:event_cancelActionPerformed
    
    private void finishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishActionPerformed
        try {
            Payment p = PaymentDAO.getInstance().create(tx);
            p.setAmount(Double.valueOf(paymentAmountInput.getText()));
            p.setCcExpiration(ccExpInput.getText());
            p.setCcNumber(ccNumInput.getText());
            p.setType(paymentTypeInput.getSelectedItem().toString());
            double change = p.getAmount() - TransactionDAO.getInstance().getTransactionTotal(tx);
            p.setChange(change);
            UpdateController.getInstance().saveTransaction(tx);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    private javax.swing.JButton cancel;
    private javax.swing.JTextField ccExpInput;
    private javax.swing.JTextField ccNumInput;
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
    private javax.swing.JTextField paymentAmountInput;
    private javax.swing.JComboBox paymentTypeInput;
    private javax.swing.JScrollPane table;
    // End of variables declaration//GEN-END:variables
    
    
}
