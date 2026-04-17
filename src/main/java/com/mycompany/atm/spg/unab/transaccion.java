package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class transaccion extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(transaccion.class.getName());

    public transaccion() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transacción");

        ResponsiveBackgroundPanel panel = new ResponsiveBackgroundPanel("/imagenes/TRANSACCION.png", 1920, 1080);

        JLabel titulo = new JLabel("SELECCIONE SU TRANSACCIÓN", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 50));

        JButton btnRetiros = createTransparentButton("RETIROS", 48);
        btnRetiros.addActionListener(this::btnRetirosActionPerformed);

        JButton btnDeposito = createTransparentButton("DEPOSITO", 48);
        JButton btnConsultarSaldo = createTransparentButton("CONSULTAR SALDO", 48);
        JButton btnSalir = createTransparentButton("SALIR", 48);

        panel.add(titulo, new RelativeConstraints(0.292, 0.287, 0.417, 0.074));
        panel.add(btnConsultarSaldo, new RelativeConstraints(0.083, 0.481, 0.281, 0.102));
        panel.add(btnSalir, new RelativeConstraints(0.078, 0.759, 0.099, 0.102));
        panel.add(btnRetiros, new RelativeConstraints(0.786, 0.481, 0.135, 0.102));
        panel.add(btnDeposito, new RelativeConstraints(0.766, 0.778, 0.156, 0.102));

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

    private void btnRetirosActionPerformed(java.awt.event.ActionEvent evt) {
        retiro ventanaRetiro = new retiro();
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

        EventQueue.invokeLater(() -> new transaccion().setVisible(true));
    }
}
