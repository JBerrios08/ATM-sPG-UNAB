package com.mycompany.atm.spg.unab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioATM {

    private static final DateTimeFormatter FORMATO_DB = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Optional<Usuario> buscarUsuarioPorPin(String pin) {
        String sql = "SELECT id, nombre, numero_cuenta, pin, saldo FROM usuarios WHERE pin = ? ORDER BY id LIMIT 1";
        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, pin);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Usuario(rs.getInt("id"), rs.getString("nombre"),
                        rs.getString("numero_cuenta"), rs.getString("pin"), rs.getDouble("saldo")));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando PIN.", ex);
        }
    }

    public double obtenerSaldo(String numeroCuenta) {
        String sql = "SELECT saldo FROM usuarios WHERE numero_cuenta = ?";
        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("La cuenta no existe.");
                }
                return rs.getDouble("saldo");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al consultar saldo.", ex);
        }
    }

    public ResultadoRetiro retirar(String numeroCuenta, int monto) {
        String sqlSaldo = "SELECT saldo FROM usuarios WHERE numero_cuenta = ?";
        String sqlActualizar = "UPDATE usuarios SET saldo = ? WHERE numero_cuenta = ?";

        try (Connection cn = GestorBaseDatos.obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                double saldoActual;
                try (PreparedStatement psSaldo = cn.prepareStatement(sqlSaldo)) {
                    psSaldo.setString(1, numeroCuenta);
                    try (ResultSet rs = psSaldo.executeQuery()) {
                        if (!rs.next()) {
                            cn.rollback();
                            return new ResultadoRetiro(false, "No se encontró la cuenta.", 0);
                        }
                        saldoActual = rs.getDouble("saldo");
                    }
                }

                if (monto <= 0) {
                    cn.rollback();
                    return new ResultadoRetiro(false, "Monto inválido.", saldoActual);
                }
                if (saldoActual < monto) {
                    cn.rollback();
                    return new ResultadoRetiro(false, "Saldo insuficiente.", saldoActual);
                }

                double nuevoSaldo = saldoActual - monto;
                try (PreparedStatement psUpdate = cn.prepareStatement(sqlActualizar)) {
                    psUpdate.setDouble(1, nuevoSaldo);
                    psUpdate.setString(2, numeroCuenta);
                    psUpdate.executeUpdate();
                }

                insertarTransaccion(cn, numeroCuenta, "RETIRO", monto);
                cn.commit();
                return new ResultadoRetiro(true, "Retiro exitoso.", nuevoSaldo);
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al procesar retiro.", ex);
        }
    }

    public void registrarTransaccion(String numeroCuenta, String tipo, double monto) {
        try (Connection cn = GestorBaseDatos.obtenerConexion()) {
            insertarTransaccion(cn, numeroCuenta, tipo, monto);
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo registrar la transacción.", ex);
        }
    }

    public List<TransaccionRegistro> obtenerHistorial(String numeroCuenta) {
        String sql = """
                SELECT tipo, monto, fecha
                FROM transacciones
                WHERE numero_cuenta = ?
                ORDER BY id DESC
                LIMIT 20
                """;
        List<TransaccionRegistro> historial = new ArrayList<>();
        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial.add(new TransaccionRegistro(
                            rs.getString("tipo"),
                            rs.getDouble("monto"),
                            parsearFecha(rs.getString("fecha"))
                    ));
                }
            }
            return historial;
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo consultar el historial.", ex);
        }
    }

    private void insertarTransaccion(Connection cn, String numeroCuenta, String tipo, double monto) throws SQLException {
        String sql = "INSERT INTO transacciones (numero_cuenta, tipo, monto, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            ps.setString(2, tipo);
            ps.setDouble(3, monto);
            ps.setString(4, LocalDateTime.now().format(FORMATO_DB));
            ps.executeUpdate();
        }
    }

    private LocalDateTime parsearFecha(String texto) {
        try {
            return LocalDateTime.parse(texto, FORMATO_DB);
        } catch (DateTimeParseException ex) {
            return LocalDateTime.now();
        }
    }
}
