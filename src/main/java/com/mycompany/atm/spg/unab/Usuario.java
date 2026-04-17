package com.mycompany.atm.spg.unab;

public class Usuario {

    private final int id;
    private final String nombre;
    private final String numeroCuenta;
    private final String pin;
    private final double saldo;

    public Usuario(int id, String nombre, String numeroCuenta, String pin, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.pin = pin;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getPin() {
        return pin;
    }

    public double getSaldo() {
        return saldo;
    }
}
