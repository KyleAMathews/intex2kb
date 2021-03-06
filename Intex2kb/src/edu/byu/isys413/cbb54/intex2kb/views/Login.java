/*
 * Login.java
 *
 * Created on April 4, 2007, 7:45 PM
 */

package edu.byu.isys413.cbb54.intex2kb.views;

import edu.byu.isys413.cbb54.intex2kb.data.*;

/**
 *
 * @author  tylerf
 */
public class Login extends javax.swing.JFrame {
    
    String storeid = "000001117284553c0014b20a500442";
    String empid = null;
    
    /** Creates new form Login */
    public Login() {
        initComponents();
        emailInput.setText("c@b.com");
        passwordInput.setText("cb");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        emailInput = new javax.swing.JTextField();
        passwordInput = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        login = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(200, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 200));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel3.setText("Password");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel2.setText("Email");

        emailInput.setColumns(17);

        passwordInput.setColumns(17);

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(87, 87, 87)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel2)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, emailInput)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, passwordInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(29, 29, 29)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .add(14, 14, 14)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(passwordInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employee Login");
        jPanel4.add(jLabel1);

        jPanel2.add(jPanel4, java.awt.BorderLayout.NORTH);

        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        jPanel3.add(login);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        try {
            //System.out.println("password: " + passwordInput.getText());
            if(emailInput.getText() != null){
                empid = EmployeeDAO.getInstance().getEmail(emailInput.getText()).getId();
            }else{
                emailInput.setText("Please enter e-mail address");
            }
            boolean pass = false;
            
            try {
                pass = validateLogin.getInstance().validate(emailInput.getText(),passwordInput.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            if(pass == true){
                System.out.print("Login passed");
                this.dispose();
                getCustomer g = new getCustomer();
                Session.getInstance().setStore(StoreDAO.getInstance().read(storeid));
                Session.getInstance().setEmployee(EmployeeDAO.getInstance().read(empid));
                //Main m = new Main(empid, storeid);
                g.setVisible(true);
            }else{
                emailInput.setText("INCORRECT");
                passwordInput.setText("");
            }
        } catch (DataException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_loginActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField emailInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton login;
    private javax.swing.JPasswordField passwordInput;
    // End of variables declaration//GEN-END:variables
    
}
