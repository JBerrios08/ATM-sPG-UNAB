package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class transaccion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(transaccion.class.getName());

    public transaccion() {
        initComponents();
    }

    private void initComponents() {
        UIHelper.configurarVentanaBase(this, "Transacción");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/TRANSACCION.png", 1920, 1080);

        javax.swing.JLabel titulo = UIHelper.createOverlayLabel("SELECCIONE SU TRANSACCIÓN", 50);

        JButton btnRetiros = UIHelper.createTransparentButton("RETIROS", 48);
        btnRetiros.addActionListener(this::btnRetirosActionPerformed);

        JButton btnDeposito = UIHelper.createTransparentButton("DEPÓSITO", 44);
        btnDeposito.addActionListener(evt -> mostrarNoDisponible("Depósito"));

        JButton btnConsultarSaldo = UIHelper.createTransparentButton("CONSULTAR SALDO", 44);
        btnConsultarSaldo.addActionListener(evt -> mostrarNoDisponible("Consulta de saldo"));

        JButton btnSalir = UIHelper.createTransparentButton("SALIR", 48);
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.292, 0.287, 0.417, 0.074));
        panel.add(btnConsultarSaldo, new RestriccionesRelativas(0.070, 0.481, 0.300, 0.102));
        panel.add(btnSalir, new RestriccionesRelativas(0.078, 0.759, 0.099, 0.102));
        panel.add(btnRetiros, new RestriccionesRelativas(0.786, 0.481, 0.135, 0.102));
        panel.add(btnDeposito, new RestriccionesRelativas(0.760, 0.778, 0.165, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void mostrarNoDisponible(String funcionalidad) {
        JOptionPane.showMessageDialog(this,
                funcionalidad + " no está implementada todavía.",
                "Funcionalidad pendiente",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnRetirosActionPerformed(java.awt.event.ActionEvent evt) {
        UIHelper.abrirVentana(this, retiro::new);
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        FlujoATM.getInstance().cerrarSesion();
        UIHelper.abrirVentana(this, inicio::new);
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
