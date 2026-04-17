package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ConfirmacionRetiro extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ConfirmacionRetiro.class.getName());
    private final NumberFormat formatoMoneda = NumberFormat.getIntegerInstance(new Locale("es", "SV"));

    public ConfirmacionRetiro() {
        initComponents();
    }

    private void initComponents() {
        UtilidadesUI.configurarVentanaBase(this, "Confirmación de retiro");
        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/PROCEDERRETIRO.png", 1920, 1080);

        JLabel titulo = UtilidadesUI.crearEtiquetaSuperpuesta(crearTextoConfirmacion(FlujoATM.getInstance().getMontoSeleccionado()), 42);

        JButton btnNo = UtilidadesUI.crearBotonTransparente("NO", 48);
        JButton btnSi = UtilidadesUI.crearBotonTransparente("SÍ", 48);

        btnNo.addActionListener(this::btnCantidadNoCorrectaActionPerformed);
        btnSi.addActionListener(this::btnCantidadSiCorrectaActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.224, 0.245, 0.552, 0.180));
        panel.add(btnNo, new RestriccionesRelativas(0.083, 0.741, 0.090, 0.102));
        panel.add(btnSi, new RestriccionesRelativas(0.839, 0.769, 0.090, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private String crearTextoConfirmacion(Integer montoSeleccionado) {
        if (montoSeleccionado == null) {
            return UtilidadesUI.textoEnDosLineas("¿ES CORRECTA LA CANTIDAD", "A RETIRAR?");
        }
        String montoFormateado = "$" + formatoMoneda.format(montoSeleccionado);
        return "<html><div style='text-align:center;'>¿ES CORRECTA LA CANTIDAD<br>A RETIRAR?<br>" + montoFormateado + "</div></html>";
    }

    private void btnCantidadNoCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        UtilidadesUI.abrirVentana(this, SeleccionMontoRetiro::new);
    }

    private void btnCantidadSiCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        ResultadoRetiro resultado = FlujoATM.getInstance().retirarMontoSeleccionado();
        if (!resultado.exito()) {
            JOptionPane.showMessageDialog(this, resultado.mensaje(), "RETIRO", JOptionPane.WARNING_MESSAGE);
            UtilidadesUI.abrirVentana(this, SeleccionMontoRetiro::new);
            return;
        }

        Integer monto = FlujoATM.getInstance().getMontoSeleccionado();
        JOptionPane.showMessageDialog(this,
                String.format("Retiro exitoso. Retiró $%d.\nSaldo restante: $%.2f", monto, resultado.saldoActual()),
                "TRANSACCION OK",
                JOptionPane.INFORMATION_MESSAGE);

        FlujoATM.getInstance().cerrarSesion();
        UtilidadesUI.abrirVentana(this, Inicio::new);
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
        EventQueue.invokeLater(() -> new ConfirmacionRetiro().setVisible(true));
    }
}
