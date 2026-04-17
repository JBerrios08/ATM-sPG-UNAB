package com.mycompany.atm.spg.unab;

public class ClienteATM {

    private final int idCliente;
    private final String nombre;
    private final String apellido;
    private final String numTarjeta;
    private final String pin;
    private final CuentaATM cuenta;

    public ClienteATM(int idCliente, String nombre, String apellido, String numTarjeta, String pin, CuentaATM cuenta) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numTarjeta = numTarjeta;
        this.pin = pin;
        this.cuenta = cuenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getNumTarjeta() {
        return numTarjeta;
    }

    public String getPin() {
        return pin;
    }

    public CuentaATM getCuenta() {
        return cuenta;
    }
}
