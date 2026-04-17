package com.mycompany.atm.spg.unab;

public class AplicacionATM {

    public static void main(String[] args) {
        InicializadorBaseDatos.inicializar();
        java.awt.EventQueue.invokeLater(() -> new Inicio().setVisible(true));
    }
}
