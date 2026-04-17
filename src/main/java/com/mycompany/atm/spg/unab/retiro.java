package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.UIManager;

public class retiro extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(retiro.class.getName());

    public retiro() {
        initComponents();
    }

    private void initComponents() {
        UIHelper.configurarVentanaBase(this, "Retiro");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/RETIRO.png", 1920, 1080);

        javax.swing.JLabel titulo = UIHelper.createOverlayLabel("SELECCIONE UNA OPCIÓN", 50);

        JButton btnCuentaAhorros = UIHelper.createTransparentButton("CUENTA DE AHORROS", 43);
        btnCuentaAhorros.addActionListener(this::btnCuentadeAhorrosActionPerformed);

        JButton btnCancelar = UIHelper.createTransparentButton("CANCELAR", 48);
        btnCancelar.addActionListener(this::btnSalirInicioActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.339, 0.287, 0.323, 0.074));
        panel.add(btnCuentaAhorros, new RestriccionesRelativas(0.60, 0.481, 0.333, 0.102));
        panel.add(btnCancelar, new RestriccionesRelativas(0.745, 0.778, 0.177, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void btnSalirInicioActionPerformed(java.awt.event.ActionEvent evt) {
        FlujoATM.getInstance().cerrarSesion();
        UIHelper.abrirVentana(this, inicio::new);
    }

    private void btnCuentadeAhorrosActionPerformed(java.awt.event.ActionEvent evt) {
        UIHelper.abrirVentana(this, cantidadaretirar::new);
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
