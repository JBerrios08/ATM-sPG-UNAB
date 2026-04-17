package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class continuar extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(continuar.class.getName());

    public continuar() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Continuar");

        ResponsiveBackgroundPanel panel = new ResponsiveBackgroundPanel("/imagenes/CONTINUAR.png", 1920, 1080);

        JLabel pregunta = new JLabel("¿DESEAS CONTINUAR?");
        pregunta.setForeground(Color.WHITE);
        pregunta.setFont(new Font("Segoe UI Black", Font.BOLD, 48));
        pregunta.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnSi = createTransparentButton("SÍ", 36);
        btnSi.addActionListener(this::btnSiActionPerformed);

        JButton btnNo = createTransparentButton("NO", 36);
        btnNo.addActionListener(this::btnNoActionPerformed);

        panel.add(pregunta, new RelativeConstraints(0.333, 0.556, 0.292, 0.065));
        panel.add(btnSi, new RelativeConstraints(0.813, 0.593, 0.104, 0.083));
        panel.add(btnNo, new RelativeConstraints(0.818, 0.806, 0.104, 0.083));

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

    private void btnNoActionPerformed(java.awt.event.ActionEvent evt) {
        inicio ventanaInicio = new inicio();
        ventanaInicio.setVisible(true);
        this.dispose();
    }

    private void btnSiActionPerformed(java.awt.event.ActionEvent evt) {
        transaccion ventanaTransaccion = new transaccion();
        ventanaTransaccion.setVisible(true);
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

        EventQueue.invokeLater(() -> new continuar().setVisible(true));
    }
}
