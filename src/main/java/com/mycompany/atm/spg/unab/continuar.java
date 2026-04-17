package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class continuar extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(continuar.class.getName());

    public continuar() {
        initComponents();
    }

    private void initComponents() {
        UIHelper.configurarVentanaBase(this, "Continuar");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/CONTINUAR.png", 1920, 1080);

        javax.swing.JLabel pregunta = UIHelper.createOverlayLabel("¿DESEAS CONTINUAR?", 48);

        JButton btnSi = UIHelper.createTransparentButton("SÍ", 36);
        btnSi.addActionListener(this::btnSiActionPerformed);

        JButton btnNo = UIHelper.createTransparentButton("NO", 36);
        btnNo.addActionListener(this::btnNoActionPerformed);

        panel.add(pregunta, new RestriccionesRelativas(0.333, 0.556, 0.292, 0.065));
        panel.add(btnSi, new RestriccionesRelativas(0.813, 0.593, 0.104, 0.083));
        panel.add(btnNo, new RestriccionesRelativas(0.818, 0.806, 0.104, 0.083));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void btnNoActionPerformed(java.awt.event.ActionEvent evt) {
        FlujoATM.getInstance().cerrarSesion();
        UIHelper.abrirVentana(this, inicio::new);
    }

    private void btnSiActionPerformed(java.awt.event.ActionEvent evt) {
        if (!FlujoATM.getInstance().sesionActiva()) {
            JOptionPane.showMessageDialog(this,
                    "La sesión expiró. Vuelva a ingresar su PIN.",
                    "Sesión no válida",
                    JOptionPane.WARNING_MESSAGE);
            UIHelper.abrirVentana(this, inicio::new);
            return;
        }
        UIHelper.abrirVentana(this, transaccion::new);
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
