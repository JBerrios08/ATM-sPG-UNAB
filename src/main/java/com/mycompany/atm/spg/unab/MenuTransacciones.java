package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class MenuTransacciones extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuTransacciones.class.getName());
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public MenuTransacciones() {
        initComponents();
    }

    private void initComponents() {
        UtilidadesUI.configurarVentanaBase(this, "Transacción");
        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/TRANSACCION.png", 1920, 1080);

        javax.swing.JLabel titulo = UtilidadesUI.crearEtiquetaSuperpuesta("SELECCIONE SU TRANSACCION", 50);

        JButton btnRetiros = UtilidadesUI.crearBotonTransparente("RETIROS", 48);
        JButton btnHistorial = UtilidadesUI.crearBotonTransparente("HISTORIAL", 44);
        JButton btnConsultarSaldo = UtilidadesUI.crearBotonTransparente("CONSULTAR SALDO", 44);
        JButton btnSalir = UtilidadesUI.crearBotonTransparente("SALIR", 48);

        btnRetiros.addActionListener(this::btnRetirosActionPerformed);
        btnHistorial.addActionListener(evt -> mostrarHistorial());
        btnConsultarSaldo.addActionListener(evt -> consultarSaldoActual());
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.292, 0.287, 0.417, 0.074));
        panel.add(btnConsultarSaldo, new RestriccionesRelativas(0.070, 0.481, 0.300, 0.102));
        panel.add(btnSalir, new RestriccionesRelativas(0.078, 0.759, 0.099, 0.102));
        panel.add(btnRetiros, new RestriccionesRelativas(0.786, 0.481, 0.135, 0.102));
        panel.add(btnHistorial, new RestriccionesRelativas(0.760, 0.778, 0.165, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void consultarSaldoActual() {
        try {
            double saldo = FlujoATM.getInstance().obtenerSaldoActual();
            FlujoATM.getInstance().registrarConsultaSaldo();
            JOptionPane.showMessageDialog(this,
                    String.format("Su saldo actual es: $%.2f", saldo),
                    "Consulta de saldo",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Sesión", JOptionPane.WARNING_MESSAGE);
            UtilidadesUI.abrirVentana(this, Inicio::new);
        }
    }

    private void mostrarHistorial() {
        List<TransaccionRegistro> historial = FlujoATM.getInstance().obtenerHistorial();
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay transacciones registradas.", "Historial", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder texto = new StringBuilder();
        for (TransaccionRegistro registro : historial) {
            texto.append(registro.fecha().format(FORMATO_FECHA))
                    .append(" | ")
                    .append(registro.tipo())
                    .append(" | $")
                    .append(String.format("%.2f", registro.monto()))
                    .append("\n");
        }

        JTextArea area = new JTextArea(texto.toString(), 14, 42);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Historial de TRANSACCION", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnRetirosActionPerformed(java.awt.event.ActionEvent evt) {
        UtilidadesUI.abrirVentana(this, SeleccionRetiro::new);
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
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
        EventQueue.invokeLater(() -> new MenuTransacciones().setVisible(true));
    }
}
