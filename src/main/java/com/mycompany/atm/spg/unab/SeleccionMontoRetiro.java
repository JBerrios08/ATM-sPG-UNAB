package com.mycompany.atm.spg.unab;

import com.mycompany.atm.spg.unab.otros.PanelFondoResponsivo;
import com.mycompany.atm.spg.unab.otros.RestriccionesRelativas;
import com.mycompany.atm.spg.unab.otros.UtilidadesUI;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class SeleccionMontoRetiro extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SeleccionMontoRetiro.class.getName());

    public SeleccionMontoRetiro() {
        initComponents();
    }

    private void initComponents() {
        UtilidadesUI.configurarVentanaBase(this, "Cantidad a retirar");
        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/CANTIDAD.png", 1920, 1080);

        javax.swing.JLabel titulo = UtilidadesUI.crearEtiquetaSuperpuesta(
                UtilidadesUI.textoEnDosLineas("SELECCIONE UNA CANTDAD", "A RETIRAR"),
                48);

        JButton btn5 = crearBotonMonto("$5", 5);
        JButton btn15 = crearBotonMonto("$15", 15);
        JButton btn25 = crearBotonMonto("$25", 25);
        JButton btn75 = crearBotonMonto("$75", 75);
        JButton btn115 = crearBotonMonto("$115", 115);

        JButton btnOtra = UtilidadesUI.crearBotonTransparente("OTRA CANTIDAD", 42);
        btnOtra.addActionListener(this::btnOtraCantidadActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.224, 0.270, 0.552, 0.105));
        panel.add(btn5, new RestriccionesRelativas(0.094, 0.407, 0.090, 0.102));
        panel.add(btn15, new RestriccionesRelativas(0.094, 0.62, 0.090, 0.102));
        panel.add(btn25, new RestriccionesRelativas(0.099, 0.833, 0.090, 0.102));
        panel.add(btn75, new RestriccionesRelativas(0.830, 0.407, 0.090, 0.102));
        panel.add(btn115, new RestriccionesRelativas(0.833, 0.639, 0.100, 0.102));
        panel.add(btnOtra, new RestriccionesRelativas(0.645, 0.852, 0.285, 0.102));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton crearBotonMonto(String etiqueta, int monto) {
        JButton button = UtilidadesUI.crearBotonTransparente(etiqueta, 48);
        button.addActionListener(evt -> irAConfirmacion(monto));
        return button;
    }

    private void irAConfirmacion(int monto) {
        FlujoATM.getInstance().registrarMonto(monto);
        UtilidadesUI.abrirVentana(this, ConfirmacionRetiro::new);
    }

    private void btnOtraCantidadActionPerformed(java.awt.event.ActionEvent evt) {
        String entrada = JOptionPane.showInputDialog(this, "Ingrese el monto a retirar:", "Monto", JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) {
            return;
        }

        String limpio = entrada.trim().replace("$", "");
        if (!limpio.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Ingrese solo números.", "Monto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int monto = Integer.parseInt(limpio);
        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "Monto inválido.", "Monto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        irAConfirmacion(monto);
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
        EventQueue.invokeLater(() -> new SeleccionMontoRetiro().setVisible(true));
    }
}
