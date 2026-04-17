package com.mycompany.atm.spg.unab;

import java.util.Optional;

public class ATMService {

    private final ATMRepository repository;

    public ATMService() {
        DatabaseInitializer.initialize();
        this.repository = new ATMRepository();
    }

    public Optional<Usuario> autenticarPorPin(String pin) {
        return repository.buscarUsuarioPorPin(pin);
    }

    public double consultarSaldo(String numeroCuenta) {
        return repository.obtenerSaldo(numeroCuenta);
    }

    public RetiroResultado retirar(String numeroCuenta, int monto) {
        return repository.retirar(numeroCuenta, monto);
    }

    public void registrarConsultaSaldo(String numeroCuenta) {
        repository.registrarTransaccion(numeroCuenta, "CONSULTA_SALDO", 0);
    }
}
