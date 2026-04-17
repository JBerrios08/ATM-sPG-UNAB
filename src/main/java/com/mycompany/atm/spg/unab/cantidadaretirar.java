package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class cantidadaretirar extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(cantidadaretirar.class.getName());

    public cantidadaretirar() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cantidad a retirar");

        ResponsiveBackgroundPanel panel = new ResponsiveBackgroundPanel("/imagenes/CANTIDAD.png", 1920, 1080);

        JLabel titulo = new JLabel("SELECCIONE UNA CANTIDAD A RETIRAR", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Black", Font.BOLD, 50));

        JButton btn5 = createTransparentButton("$5", 48);
        btn5.addActionListener(this::btn5dolaresActionPerformed);
        JButton btn15 = createTransparentButton("$15", 48);
        btn15.addActionListener(this::btn15dolaresActionPerformed);
        JButton btn25 = createTransparentButton("$25", 48);
        btn25.addActionListener(this::btn25dolaresActionPerformed);
        JButton btn75 = createTransparentButton("$75", 48);
        btn75.addActionListener(this::btn75dolaresActionPerformed);
        JButton btn115 = createTransparentButton("$115", 48);
        btn115.addActionListener(this::btn115dolaresActionPerformed);
        JButton btnOtra = createTransparentButton("OTRA CANTIDAD", 48);
        btnOtra.addActionListener(this::btnOtraCantidadActionPerformed);

        panel.add(titulo, new RelativeConstraints(0.224, 0.287, 0.552, 0.074));
        panel.add(btn5, new RelativeConstraints(0.094, 0.407, 0.078, 0.102));
        panel.add(btn15, new RelativeConstraints(0.094, 0.62, 0.078, 0.102));
        panel.add(btn25, new RelativeConstraints(0.099, 0.833, 0.078, 0.102));
        panel.add(btn75, new RelativeConstraints(0.839, 0.407, 0.078, 0.102));
        panel.add(btn115, new RelativeConstraints(0.844, 0.639, 0.078, 0.102));
        panel.add(btnOtra, new RelativeConstraints(0.672, 0.852, 0.255, 0.102));

        setContentPane(panel);
        setMinimumSize(new java.awt.Dimension(960, 540));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        pack();
        setLocationRelativeTo(null);
    }

    private JButton createTransparentButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Black", Font.BOLD, fontSize));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    private void irARetiroProceder() {
        retiroproceder ventana = new retiroproceder();
        ventana.setVisible(true);
        this.dispose();
    }

    private void btn5dolaresActionPerformed(java.awt.event.ActionEvent evt) {
        irARetiroProceder();
    }

    private void btn15dolaresActionPerformed(java.awt.event.ActionEvent evt) {
        irARetiroProceder();
    }

    private void btn25dolaresActionPerformed(java.awt.event.ActionEvent evt) {
        irARetiroProceder();
    }

    private void btn75dolaresActionPerformed(java.awt.event.ActionEvent evt) {
        irARetiroProceder();
    }

    private void btn115dolaresActionPerformed(java.awt.event.ActionEvent evt) {
        irARetiroProceder();
    }

    private void btnOtraCantidadActionPerformed(java.awt.event.ActionEvent evt) {
        irARetiroProceder();
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
