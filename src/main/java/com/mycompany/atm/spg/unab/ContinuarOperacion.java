package com.mycompany.atm.spg.unab;

import com.mycompany.atm.spg.unab.otros.PanelFondoResponsivo;
import com.mycompany.atm.spg.unab.otros.RestriccionesRelativas;
import com.mycompany.atm.spg.unab.otros.UtilidadesUI;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ContinuarOperacion extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ContinuarOperacion.class.getName());

    public ContinuarOperacion() {
        initComponents();
    }

    private void initComponents() {
        UtilidadesUI.configurarVentanaBase(this, "Continuar");
        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/CONTINUAR.png", 1920, 1080);

        javax.swing.JLabel pregunta = UtilidadesUI.crearEtiquetaSuperpuesta("¿DESEAS CONTINUAR?", 48);
        JButton btnSi = UtilidadesUI.crearBotonTransparente("SÍ", 36);
        JButton btnNo = UtilidadesUI.crearBotonTransparente("NO", 36);

        btnSi.addActionListener(this::btnSiActionPerformed);
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
        UtilidadesUI.abrirVentana(this, Inicio::new);
    }

    private void btnSiActionPerformed(java.awt.event.ActionEvent evt) {
        if (!FlujoATM.getInstance().sesionActiva()) {
            JOptionPane.showMessageDialog(this, "Sesión no válida. Inicie de nuevo.", "Sesión", JOptionPane.WARNING_MESSAGE);
            UtilidadesUI.abrirVentana(this, Inicio::new);
            return;
        }
        UtilidadesUI.abrirVentana(this, MenuTransacciones::new);
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
        EventQueue.invokeLater(() -> new ContinuarOperacion().setVisible(true));
    }
}
