package com.mycompany.atm.spg.unab;

import java.util.Collections;
import java.util.List;

public final class FlujoATM {

    private static final FlujoATM INSTANCE = new FlujoATM();
    private final ServicioATM servicioATM = new ServicioATM();

    private ClienteATM clienteAutenticado;
    private Integer montoSeleccionado;

    private FlujoATM() {
    }

    public static FlujoATM getInstance() {
        return INSTANCE;
    }

    public boolean validarPin(char[] pinChars) {
        if (pinChars == null) {
            return false;
        }
        String pin = new String(pinChars).trim();
        if (!pin.matches("\\d{4}")) {
            return false;
        }
        clienteAutenticado = servicioATM.autenticarPorPin(pin).orElse(null);
        return clienteAutenticado != null;
    }

    public boolean sesionActiva() {
        return clienteAutenticado != null;
    }

    public void registrarMonto(Integer monto) {
        montoSeleccionado = monto;
    }

    public Integer getMontoSeleccionado() {
        return montoSeleccionado;
    }

    public double obtenerSaldoActual() {
        validarSesion();
        return servicioATM.consultarSaldo(clienteAutenticado.getCuenta().getNumCuenta());
    }

    public void registrarConsultaSaldo() {
        if (sesionActiva()) {
            servicioATM.registrarConsultaSaldo(clienteAutenticado.getCuenta().getNumCuenta());
        }
    }

    public ResultadoRetiro retirarMontoSeleccionado() {
        if (!sesionActiva()) {
            return new ResultadoRetiro(false, "Sesión vencida.", 0);
        }
        if (montoSeleccionado == null) {
            return new ResultadoRetiro(false, "No hay monto seleccionado.", obtenerSaldoActual());
        }
        return servicioATM.retirar(clienteAutenticado.getCuenta().getNumCuenta(), montoSeleccionado);
    }

    public List<HistorialTransacciones> obtenerHistorial() {
        if (!sesionActiva()) {
            return Collections.emptyList();
        }
        return servicioATM.consultarHistorial(clienteAutenticado.getCuenta().getNumCuenta());
    }

    public void cerrarSesion() {
        clienteAutenticado = null;
        montoSeleccionado = null;
    }

    private void validarSesion() {
        if (!sesionActiva()) {
            throw new IllegalStateException("La sesión no está activa.");
        }
    }
}
