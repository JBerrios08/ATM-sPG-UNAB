package com.mycompany.atm.spg.unab;

/**
 * Estado de sesión simple para mantener datos del flujo de ATM.
 */
public final class FlujoATM {

    private static final FlujoATM INSTANCE = new FlujoATM();

    private static final String PIN_VALIDO = "1234";

    private String pinIngresado;
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
        boolean esValido = PIN_VALIDO.equals(pin);
        if (esValido) {
            this.pinIngresado = pin;
        }
        return esValido;
    }

    public boolean sesionActiva() {
        return pinIngresado != null;
    }

    public void registrarMonto(Integer monto) {
        this.montoSeleccionado = monto;
    }

    public Integer getMontoSeleccionado() {
        return montoSeleccionado;
    }

    public void cerrarSesion() {
        pinIngresado = null;
        montoSeleccionado = null;
    }
}
