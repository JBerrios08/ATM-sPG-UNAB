package com.mycompany.atm.spg.unab;

import java.util.List;
import java.util.Optional;

public class ServicioATM {

    private final RepositorioATM repositorio;

    public ServicioATM() {
        InicializadorBaseDatos.inicializar();
        this.repositorio = new RepositorioATM();
    }

    public Optional<Usuario> autenticarPorPin(String pin) {
        return repositorio.buscarUsuarioPorPin(pin);
    }

    public double consultarSaldo(String numeroCuenta) {
        return repositorio.obtenerSaldo(numeroCuenta);
    }

    public ResultadoRetiro retirar(String numeroCuenta, int monto) {
        return repositorio.retirar(numeroCuenta, monto);
    }

    public void registrarConsultaSaldo(String numeroCuenta) {
        repositorio.registrarTransaccion(numeroCuenta, "CONSULTA_SALDO", 0);
    }

    public List<TransaccionRegistro> consultarHistorial(String numeroCuenta) {
        return repositorio.obtenerHistorial(numeroCuenta);
    }
}
