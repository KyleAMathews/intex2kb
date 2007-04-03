/*
 * MainGUI.java
 *
 * Created on March 30, 2007, 3:03 PM
 */

package edu.byu.isys413.cbb54.intex2kb.views;

/**
 *
 * @author  tylerf
 */
public class MainGUI extends javax.swing.JPanel {
    
    private String storeid = "111";
    private String empid = "111";
    
    /** Creates new form MainGUI */
    public MainGUI() {
        initComponents();
        storeID.setText(storeid);
        empID.setText(empid);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        storeLable = new javax.swing.JLabel();
        storeID = new javax.swing.JLabel();
        empLable = new javax.swing.JLabel();
        empID = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        title.setFont(new java.awt.Font("Lucida Grande", 1, 24));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("MyStuff Digital Photography");

        storeLable.setText("Store:");

        storeID.setText("jLabel1");

        empLable.setText("Employee:");

        empID.setText("jLabel1");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, storeLable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, empLable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(empID))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(storeID))))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(32, 32, 32)
                        .add(title, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 414, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(title, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(22, 22, 22)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(empLable)
                    .add(empID))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(storeID)
                    .add(storeLable))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(jPanel1);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel empID;
    private javax.swing.JLabel empLable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel storeID;
    private javax.swing.JLabel storeLable;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
    
}
