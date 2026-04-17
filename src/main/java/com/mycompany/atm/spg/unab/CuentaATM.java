package com.mycompany.atm.spg.unab;

public class CuentaATM {

    private final int idCuenta;
    private final int idCliente;
    private final String numCuenta;
    private final String tipoDeCuenta;
    private final double saldoDisponible;

    public CuentaATM(int idCuenta, int idCliente, String numCuenta, String tipoDeCuenta, double saldoDisponible) {
        this.idCuenta = idCuenta;
        this.idCliente = idCliente;
        this.numCuenta = numCuenta;
        this.tipoDeCuenta = tipoDeCuenta;
        this.saldoDisponible = saldoDisponible;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public String getTipoDeCuenta() {
        return tipoDeCuenta;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }
}
