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

public final class UtilidadesUI {

    public static final Dimension VENTANA_MINIMA = new Dimension(960, 540);
    public static final Dimension VENTANA_PREFERIDA = new Dimension(1280, 720);

    private UtilidadesUI() {
    }

    public static void configurarVentanaBase(JFrame frame, String titulo) {
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(titulo);
        frame.setMinimumSize(VENTANA_MINIMA);
        frame.setPreferredSize(VENTANA_PREFERIDA);
    }

    public static JButton crearBotonTransparente(String texto, int tamanoFuente) {
        JButton button = new JButton(texto);
        aplicarEstiloSuperpuesto(button, tamanoFuente);
        return button;
    }

    public static JLabel crearEtiquetaSuperpuesta(String texto, int tamanoFuente) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        aplicarEstiloSuperpuesto(label, tamanoFuente);
        return label;
    }

    private static void aplicarEstiloSuperpuesto(javax.swing.JComponent component, int tamanoFuente) {
        component.setForeground(Color.WHITE);
        component.setFont(new Font("Segoe UI Black", Font.BOLD, tamanoFuente));
        if (component instanceof AbstractButton button) {
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
        }
    }

    public static String textoEnDosLineas(String lineaUno, String lineaDos) {
        return "<html><div style='text-align:center;'>" + lineaUno + "<br>" + lineaDos + "</div></html>";
    }

    public static void abrirVentana(JFrame actual, Supplier<JFrame> fabricaDestino) {
        JFrame siguiente = fabricaDestino.get();
        siguiente.setVisible(true);
        actual.dispose();
    }
}
