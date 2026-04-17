package com.mycompany.atm.spg.unab;

/**
 * Estado de sesión simple para mantener datos del flujo de ATM.
 */
public final class FlujoATM {

    private static final FlujoATM INSTANCE = new FlujoATM();

    private final ATMService atmService = new ATMService();

    private Usuario usuarioAutenticado;
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

        usuarioAutenticado = atmService.autenticarPorPin(pin).orElse(null);
        return usuarioAutenticado != null;
    }

    public boolean sesionActiva() {
        return usuarioAutenticado != null;
    }

    public void registrarMonto(Integer monto) {
        this.montoSeleccionado = monto;
    }

    public Integer getMontoSeleccionado() {
        return montoSeleccionado;
    }

    public double obtenerSaldoActual() {
        if (!sesionActiva()) {
            throw new IllegalStateException("No hay una sesión activa.");
        }
        return atmService.consultarSaldo(usuarioAutenticado.getNumeroCuenta());
    }

    public void registrarConsultaSaldo() {
        if (sesionActiva()) {
            atmService.registrarConsultaSaldo(usuarioAutenticado.getNumeroCuenta());
        }
    }

    public RetiroResultado retirarMontoSeleccionado() {
        if (!sesionActiva()) {
            return new RetiroResultado(false, "La sesión expiró. Inicie sesión nuevamente.", 0);
        }
        if (montoSeleccionado == null) {
            return new RetiroResultado(false, "No se encontró un monto seleccionado.", obtenerSaldoActual());
        }
        return atmService.retirar(usuarioAutenticado.getNumeroCuenta(), montoSeleccionado);
    }

    public void cerrarSesion() {
        usuarioAutenticado = null;
        montoSeleccionado = null;
    }
}
