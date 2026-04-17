package com.mycompany.atm.spg.unab;

import com.mycompany.atm.spg.unab.otros.PanelFondoResponsivo;
import com.mycompany.atm.spg.unab.otros.RestriccionesRelativas;
import com.mycompany.atm.spg.unab.otros.UtilidadesUI;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Inicio extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Inicio.class.getName());
    private JPasswordField password;

    public Inicio() {
        initComponents();
    }

    private void initComponents() {
        UtilidadesUI.configurarVentanaBase(this, "Inicio");

        PanelFondoResponsivo panel = new PanelFondoResponsivo("/imagenes/INICIO.png", 1920, 1080);

        javax.swing.JLabel titulo = UtilidadesUI.crearEtiquetaSuperpuesta(
                UtilidadesUI.textoEnDosLineas("ESTIMADO USUARIO, INGRESE SU PIN", "Y PRESIONE CONTNUAR"),
                24);

        javax.swing.JLabel integrantes = UtilidadesUI.crearEtiquetaSuperpuesta(
                UtilidadesUI.textoEnDosLineas("INTEGRANTES", "Alexis Castro · Jaime Berrios"),
                20);

        password = new JPasswordField();
        password.setBackground(Color.WHITE);
        password.setForeground(new Color(60, 60, 60));

        JButton btnContinuar = UtilidadesUI.crearBotonTransparente("CONTINUAR", 22);
        btnContinuar.setBackground(Color.WHITE);
        btnContinuar.setForeground(Color.BLACK);
        btnContinuar.setContentAreaFilled(true);
        btnContinuar.setBorderPainted(true);
        btnContinuar.addActionListener(this::btnContinuarActionPerformed);

        panel.add(titulo, new RestriccionesRelativas(0.20, 0.23, 0.60, 0.16));
        panel.add(integrantes, new RestriccionesRelativas(0.31, 0.875, 0.38, 0.09));
        panel.add(password, new RestriccionesRelativas(0.396, 0.519, 0.203, 0.056));
        panel.add(btnContinuar, new RestriccionesRelativas(0.423, 0.778, 0.155, 0.083));

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {
        UtilidadesUI.ejecutarConToqueOpcion(() -> {
            boolean pinValido = FlujoATM.getInstance().validarPin(password.getPassword());
            if (!pinValido) {
                JOptionPane.showMessageDialog(this,
                        "PIN inválido. Ingrese 4 dígitos.",
                        "Validación PIN",
                        JOptionPane.WARNING_MESSAGE);
                password.setText("");
                return;
            }
            UtilidadesUI.abrirVentana(this, ContinuarOperacion::new);
        });
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
        EventQueue.invokeLater(() -> new Inicio().setVisible(true));
    }
}
