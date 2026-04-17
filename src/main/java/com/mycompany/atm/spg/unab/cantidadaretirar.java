package com.mycompany.atm.spg.unab;

import java.awt.EventQueue;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class cantidadaretirar extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(cantidadaretirar.class.getName());

    public cantidadaretirar() {
        initComponents();
    }

    private void initComponents() {
        UIHelper.configurarVentanaBase(this, "Cantidad a retirar");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/CANTIDAD.png", 1920, 1080);

        javax.swing.JLabel titulo = UIHelper.createOverlayLabel(
                UIHelper.textoEnDosLineas("SELECCIONE UNA CANTIDAD", "A RETIRAR"),
                48);

        Map<String, Integer> montos = new LinkedHashMap<>();
        montos.put("$5", 5);
        montos.put("$15", 15);
        montos.put("$25", 25);
        montos.put("$75", 75);
        montos.put("$115", 115);

        JButton btn5 = crearBotonMonto("$5", montos.get("$5"));
        JButton btn15 = crearBotonMonto("$15", montos.get("$15"));
        JButton btn25 = crearBotonMonto("$25", montos.get("$25"));
        JButton btn75 = crearBotonMonto("$75", montos.get("$75"));
        JButton btn115 = crearBotonMonto("$115", montos.get("$115"));

        JButton btnOtra = UIHelper.createTransparentButton("OTRA CANTIDAD", 42);
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
        JButton button = UIHelper.createTransparentButton(etiqueta, 48);
        button.addActionListener(evt -> irARetiroProceder(monto));
        return button;
    }

    private void irARetiroProceder(Integer montoSeleccionado) {
        FlujoATM.getInstance().registrarMonto(montoSeleccionado);
        UIHelper.abrirVentana(this, retiroproceder::new);
    }

    private void btnOtraCantidadActionPerformed(java.awt.event.ActionEvent evt) {
        String entrada = JOptionPane.showInputDialog(this, "Ingrese el monto a retirar:", "Monto personalizado",
                JOptionPane.QUESTION_MESSAGE);
        if (entrada == null) {
            return;
        }

        String limpio = entrada.trim().replace("$", "");
        if (!limpio.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Ingrese solo números enteros.", "Monto inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int monto = Integer.parseInt(limpio);
        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.", "Monto inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        irARetiroProceder(monto);
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

        EventQueue.invokeLater(() -> new cantidadaretirar().setVisible(true));
    }
}
