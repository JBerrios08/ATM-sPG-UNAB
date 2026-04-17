package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class inicio extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(inicio.class.getName());

    public inicio() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio");

        ResponsiveBackgroundPanel panel = new ResponsiveBackgroundPanel("/imagenes/INICIO.png", 1920, 1080);

        JLabel titulo = new JLabel("ESTIMADO USUARIO, PARA CONTINUAR INGRESE SU PIN Y PRESIONE: \"CONTINUAR\"");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPasswordField password = new JPasswordField();
        password.setBackground(Color.WHITE);
        password.setForeground(new Color(60, 60, 60));

        JButton btnContinuar = new JButton("CONTINUAR");
        btnContinuar.setBackground(Color.WHITE);
        btnContinuar.setForeground(Color.BLACK);
        btnContinuar.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        btnContinuar.addActionListener(this::btncontinuarActionPerformed);

        panel.add(titulo, new RelativeConstraints(0.23, 0.25, 0.56, 0.13));
        panel.add(password, new RelativeConstraints(0.396, 0.519, 0.203, 0.056));
        panel.add(btnContinuar, new RelativeConstraints(0.438, 0.778, 0.125, 0.083));

        setContentPane(panel);
        setMinimumSize(new java.awt.Dimension(960, 540));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        pack();
        setLocationRelativeTo(null);
    }

    private void btncontinuarActionPerformed(java.awt.event.ActionEvent evt) {
        continuar ventanaContinuar = new continuar();
        ventanaContinuar.setVisible(true);
        this.dispose();
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

        EventQueue.invokeLater(() -> new inicio().setVisible(true));
    }
}
