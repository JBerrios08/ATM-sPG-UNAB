package com.mycompany.atm.spg.unab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Supplier;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public final class UIHelper {

    public static final Dimension MIN_WINDOW = new Dimension(960, 540);
    public static final Dimension PREF_WINDOW = new Dimension(1280, 720);

    private UIHelper() {
    }

    public static void configurarVentanaBase(JFrame frame, String title) {
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(title);
        frame.setMinimumSize(MIN_WINDOW);
        frame.setPreferredSize(PREF_WINDOW);
    }

    public static JButton createTransparentButton(String text, int fontSize) {
        JButton button = new JButton(text);
        prepararTextoSuperpuesto(button, fontSize);
        return button;
    }

    public static JLabel createOverlayLabel(String text, int fontSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        prepararTextoSuperpuesto(label, fontSize);
        return label;
    }

    private static void prepararTextoSuperpuesto(javax.swing.JComponent component, int fontSize) {
        component.setForeground(Color.WHITE);
        component.setFont(new Font("Segoe UI Black", Font.BOLD, fontSize));
        if (component instanceof AbstractButton button) {
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
        }
    }

    public static String textoEnDosLineas(String lineaUno, String lineaDos) {
        return "<html><div style='text-align:center;'>" + lineaUno + "<br>" + lineaDos + "</div></html>";
    }

    public static void abrirVentana(JFrame current, Supplier<JFrame> targetFactory) {
        JFrame next = targetFactory.get();
        next.setVisible(true);
        current.dispose();
    }
}
