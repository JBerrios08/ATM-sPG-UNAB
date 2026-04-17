package com.mycompany.atm.spg.unab;

import java.util.List;
import java.util.Optional;

public class ServicioATM {

    private final RepositorioATM repositorio;

    public ServicioATM() {
        InicializadorBaseDatos.inicializar();
        this.repositorio = new RepositorioATM();
    }

    public Optional<ClienteATM> autenticarPorPin(String pin) {
        return repositorio.buscarClientePorPin(pin);
    }

    public double consultarSaldo(String numCuenta) {
        return repositorio.obtenerSaldo(numCuenta);
    }

    public ResultadoRetiro retirar(String numCuenta, int monto) {
        return repositorio.retirar(numCuenta, monto);
    }

    public void registrarConsultaSaldo(String numCuenta) {
        repositorio.registrarTransaccion(numCuenta, "CONSULTA_SALDO", 0);
    }

    public List<HistorialTransacciones> consultarHistorial(String numCuenta) {
        return repositorio.obtenerHistorial(numCuenta);
    }
}
