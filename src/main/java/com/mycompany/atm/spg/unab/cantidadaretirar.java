/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.atm.spg.unab;

/**
 *
 * @author Alex
 */
public class cantidadaretirar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(cantidadaretirar.class.getName());

    /**
     * Creates new form cantidadaretirar
     */
    public cantidadaretirar() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn5dolares = new javax.swing.JButton();
        btn15dolares = new javax.swing.JButton();
        btn25dolares = new javax.swing.JButton();
        btn75dolares = new javax.swing.JButton();
        btn115dolares = new javax.swing.JButton();
        btnOtraCantidad = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn5dolares.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        btn5dolares.setForeground(new java.awt.Color(255, 255, 255));
        btn5dolares.setText("$5");
        btn5dolares.setBorderPainted(false);
        btn5dolares.setContentAreaFilled(false);
        btn5dolares.setFocusPainted(false);
        btn5dolares.addActionListener(this::btn5dolaresActionPerformed);
        jPanel1.add(btn5dolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, 150, 110));

        btn15dolares.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        btn15dolares.setForeground(new java.awt.Color(255, 255, 255));
        btn15dolares.setText("$15");
        btn15dolares.setBorderPainted(false);
        btn15dolares.setContentAreaFilled(false);
        btn15dolares.setFocusPainted(false);
        btn15dolares.addActionListener(this::btn15dolaresActionPerformed);
        jPanel1.add(btn15dolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 670, 150, 110));

        btn25dolares.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        btn25dolares.setForeground(new java.awt.Color(255, 255, 255));
        btn25dolares.setText("$25");
        btn25dolares.setBorderPainted(false);
        btn25dolares.setContentAreaFilled(false);
        btn25dolares.setFocusPainted(false);
        btn25dolares.addActionListener(this::btn25dolaresActionPerformed);
        jPanel1.add(btn25dolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 900, 150, 110));

        btn75dolares.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        btn75dolares.setForeground(new java.awt.Color(255, 255, 255));
        btn75dolares.setText("$75");
        btn75dolares.setBorderPainted(false);
        btn75dolares.setContentAreaFilled(false);
        btn75dolares.setFocusPainted(false);
        btn75dolares.addActionListener(this::btn75dolaresActionPerformed);
        jPanel1.add(btn75dolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(1610, 440, 150, 110));

        btn115dolares.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        btn115dolares.setForeground(new java.awt.Color(255, 255, 255));
        btn115dolares.setText("$115");
        btn115dolares.setBorderPainted(false);
        btn115dolares.setContentAreaFilled(false);
        btn115dolares.setFocusPainted(false);
        btn115dolares.addActionListener(this::btn115dolaresActionPerformed);
        jPanel1.add(btn115dolares, new org.netbeans.lib.awtextra.AbsoluteConstraints(1620, 690, 150, 110));

        btnOtraCantidad.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        btnOtraCantidad.setForeground(new java.awt.Color(255, 255, 255));
        btnOtraCantidad.setText("OTRA CANTIDAD");
        btnOtraCantidad.setBorderPainted(false);
        btnOtraCantidad.setContentAreaFilled(false);
        btnOtraCantidad.setFocusPainted(false);
        btnOtraCantidad.addActionListener(this::btnOtraCantidadActionPerformed);
        jPanel1.add(btnOtraCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 920, 490, 110));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 50)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SELECCIONE UNA CANTIDAD A RETIRAR");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 310, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/CANTIDAD.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 0, 2680, 1080));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void irARetiroProceder() {
        retiroproceder ventana = new retiroproceder();
        ventana.setVisible(true);
        this.dispose();
    }
    
    private void btn5dolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5dolaresActionPerformed
      irARetiroProceder();
    }//GEN-LAST:event_btn5dolaresActionPerformed

    private void btn15dolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn15dolaresActionPerformed
        irARetiroProceder();
    }//GEN-LAST:event_btn15dolaresActionPerformed

    private void btn25dolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn25dolaresActionPerformed
        irARetiroProceder();
    }//GEN-LAST:event_btn25dolaresActionPerformed

    private void btn75dolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn75dolaresActionPerformed
       irARetiroProceder();
    }//GEN-LAST:event_btn75dolaresActionPerformed

    private void btn115dolaresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn115dolaresActionPerformed
        irARetiroProceder();
    }//GEN-LAST:event_btn115dolaresActionPerformed

    private void btnOtraCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtraCantidadActionPerformed
       irARetiroProceder();
    }//GEN-LAST:event_btnOtraCantidadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new cantidadaretirar().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn115dolares;
    private javax.swing.JButton btn15dolares;
    private javax.swing.JButton btn25dolares;
    private javax.swing.JButton btn5dolares;
    private javax.swing.JButton btn75dolares;
    private javax.swing.JButton btnOtraCantidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
