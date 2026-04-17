package com.mycompany.atm.spg.unab;

import com.mycompany.atm.spg.unab.otros.PanelFondoResponsivo;
import com.mycompany.atm.spg.unab.otros.RestriccionesRelativas;
import com.mycompany.atm.spg.unab.otros.UtilidadesUI;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.UIManager;

public class SeleccionRetiro extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SeleccionRetiro.class.getName());

    public SeleccionRetiro() {
        initComponents();
    }

    private void initComponents() {
        UtilidadesUI.configurarVentanaBase(this, "Retiro");
        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/RETIRO.png", 1920, 1080);

        javax.swing.JLabel titulo = UtilidadesUI.crearEtiquetaSuperpuesta("SELECCIONE UNA OPCIÓN", 50);
        JButton btnCuentaAhorros = UtilidadesUI.crearBotonTransparente("CUENTA DE AHORROS", 43);
        JButton btnCancelar = UtilidadesUI.crearBotonTransparente("CANCELAR", 48);

        btnCuentaAhorros.addActionListener(this::btnCuentaAhorrosActionPerformed);
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.339, 0.287, 0.323, 0.074));
        panel.add(btnCuentaAhorros, new RestriccionesRelativas(0.60, 0.481, 0.333, 0.102));
        panel.add(btnCancelar, new RestriccionesRelativas(0.745, 0.778, 0.177, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        FlujoATM.getInstance().cerrarSesion();
        UtilidadesUI.abrirVentana(this, Inicio::new);
    }

    private void btnCuentaAhorrosActionPerformed(java.awt.event.ActionEvent evt) {
        UtilidadesUI.abrirVentana(this, SeleccionMontoRetiro::new);
    }

    public static void main(String[] args) {
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
        EventQueue.invokeLater(() -> new SeleccionRetiro().setVisible(true));
    }
}
