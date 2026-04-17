package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class retiroproceder extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(retiroproceder.class.getName());

    public retiroproceder() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Confirmación de retiro");

        ResponsiveBackgroundPanel panel = new ResponsiveBackgroundPanel("/imagenes/PROCEDERRETIRO.png", 1920, 1080);

        JLabel titulo = new JLabel("¿ES CORRECTA LA CANTIDAD A RETIRAR?", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 50));

        JButton btnNo = createTransparentButton("NO", 48);
        btnNo.addActionListener(this::btnCantidadNoCorrectaActionPerformed);

        JButton btnSi = createTransparentButton("SÍ", 48);
        btnSi.addActionListener(this::btnCantidadSiCorrectaActionPerformed);

        panel.add(titulo, new RelativeConstraints(0.224, 0.287, 0.552, 0.074));
        panel.add(btnNo, new RelativeConstraints(0.083, 0.741, 0.078, 0.102));
        panel.add(btnSi, new RelativeConstraints(0.849, 0.769, 0.078, 0.102));

        setContentPane(panel);
        setMinimumSize(new java.awt.Dimension(960, 540));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        pack();
        setLocationRelativeTo(null);
    }

    private JButton createTransparentButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Black", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    private void btnCantidadNoCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        cantidadaretirar ventanaCantidad = new cantidadaretirar();
        ventanaCantidad.setVisible(true);
        this.dispose();
    }

    private void btnCantidadSiCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        // Flujo futuro
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new retiroproceder().setVisible(true));
    }
}
