package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class retiroproceder extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(retiroproceder.class.getName());
    private final NumberFormat formatoMoneda = NumberFormat.getIntegerInstance(new Locale("es", "SV"));

    public retiroproceder() {
        initComponents();
    }

    private void initComponents() {
        UIHelper.configurarVentanaBase(this, "Confirmación de retiro");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/PROCEDERRETIRO.png", 1920, 1080);

        JLabel titulo = UIHelper.createOverlayLabel(crearTextoConfirmacion(FlujoATM.getInstance().getMontoSeleccionado()), 42);

        JButton btnNo = UIHelper.createTransparentButton("NO", 48);
        btnNo.addActionListener(this::btnCantidadNoCorrectaActionPerformed);

        JButton btnSi = UIHelper.createTransparentButton("SÍ", 48);
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
            return UIHelper.textoEnDosLineas("¿ES CORRECTA LA CANTIDAD", "A RETIRAR?");
        }

        String montoFormateado = "$" + formatoMoneda.format(montoSeleccionado);
        return "<html><div style='text-align:center;'>¿ES CORRECTA LA CANTIDAD<br>A RETIRAR?<br>" + montoFormateado + "</div></html>";
    }

    private void btnCantidadNoCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        UIHelper.abrirVentana(this, cantidadaretirar::new);
    }

    private void btnCantidadSiCorrectaActionPerformed(java.awt.event.ActionEvent evt) {
        RetiroResultado resultado = FlujoATM.getInstance().retirarMontoSeleccionado();
        if (!resultado.exito()) {
            JOptionPane.showMessageDialog(this,
                    resultado.mensaje(),
                    "No fue posible completar el retiro",
                    JOptionPane.WARNING_MESSAGE);
            UIHelper.abrirVentana(this, cantidadaretirar::new);
            return;
        }

        Integer monto = FlujoATM.getInstance().getMontoSeleccionado();
        JOptionPane.showMessageDialog(this,
                String.format("Retiro exitoso. Ha retirado $%d.\nSaldo restante: $%.2f\nGracias por usar DIGIBANCK.",
                        monto, resultado.saldoActual()),
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
