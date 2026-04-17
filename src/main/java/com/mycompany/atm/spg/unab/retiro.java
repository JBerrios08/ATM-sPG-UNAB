package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class retiro extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(retiro.class.getName());

    public retiro() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Retiro");

        ResponsiveBackgroundPanel panel = new ResponsiveBackgroundPanel("/imagenes/RETIRO.png", 1920, 1080);

        JLabel titulo = new JLabel("SELECCIONE UNA OPCIÓN", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 50));

        JButton btnCuentaAhorros = createTransparentButton("CUENTA DE AHORROS", 48);
        btnCuentaAhorros.addActionListener(this::btnCuentadeAhorrosActionPerformed);

        JButton btnCancelar = createTransparentButton("CANCELAR", 48);
        btnCancelar.addActionListener(this::btnSalirInicioActionPerformed);

        panel.add(titulo, new RelativeConstraints(0.339, 0.287, 0.323, 0.074));
        panel.add(btnCuentaAhorros, new RelativeConstraints(0.62, 0.481, 0.313, 0.102));
        panel.add(btnCancelar, new RelativeConstraints(0.745, 0.778, 0.177, 0.102));

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

    private void btnSalirInicioActionPerformed(java.awt.event.ActionEvent evt) {
        inicio ventanaInicio = new inicio();
        ventanaInicio.setVisible(true);
        this.dispose();
    }

    private void btnCuentadeAhorrosActionPerformed(java.awt.event.ActionEvent evt) {
        cantidadaretirar ventanaRetiro = new cantidadaretirar();
        ventanaRetiro.setVisible(true);
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

        EventQueue.invokeLater(() -> new retiro().setVisible(true));
    }
}
