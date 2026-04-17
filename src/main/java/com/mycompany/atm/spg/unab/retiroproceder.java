package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class retiroproceder extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(retiroproceder.class.getName());

    public retiroproceder() {
        initComponents();
    }

    private void initComponents() {
        UIHelper.configurarVentanaBase(this, "Confirmación de retiro");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/PROCEDERRETIRO.png", 1920, 1080);

        javax.swing.JLabel titulo = UIHelper.createOverlayLabel(
                UIHelper.textoEnDosLineas("¿ES CORRECTA LA CANTIDAD", "A RETIRAR?"),
                48);

        JButton btnNo = UIHelper.createTransparentButton("NO", 48);
        btnNo.addActionListener(this::btnCantidadNoCorrectaActionPerformed);

        JButton btnSi = UIHelper.createTransparentButton("SÍ", 48);
        btnSi.addActionListener(this::btnCantidadSiCorrectaActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.224, 0.270, 0.552, 0.105));
        panel.add(btnNo, new RestriccionesRelativas(0.083, 0.741, 0.090, 0.102));
        panel.add(btnSi, new RestriccionesRelativas(0.839, 0.769, 0.090, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void btnCantidadNoCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        UIHelper.abrirVentana(this, cantidadaretirar::new);
    }

    private void btnCantidadSiCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        Integer monto = FlujoATM.getInstance().getMontoSeleccionado();
        if (monto == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un monto seleccionado. Vuelva a elegir una cantidad.",
                    "Monto no definido",
                    JOptionPane.WARNING_MESSAGE);
            UIHelper.abrirVentana(this, cantidadaretirar::new);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Retiro exitoso. Ha retirado $" + monto + ".\nGracias por usar DIGIBANCK.",
                "Transacción finalizada",
                JOptionPane.INFORMATION_MESSAGE);

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

        EventQueue.invokeLater(() -> new retiroproceder().setVisible(true));
    }
}
