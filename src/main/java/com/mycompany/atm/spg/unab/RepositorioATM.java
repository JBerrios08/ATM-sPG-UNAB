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

    public Optional<ClienteATM> buscarClientePorPin(String pin) {
        String sql = """
                SELECT c.id_cliente, c.nombre, c.apellido, c.num_tarjeta, c.pin,
                       cu.id_cuenta, cu.num_cuenta, cu.tipo_cuenta, cu.saldo_disponible
                FROM cliente c
                JOIN cuenta cu ON cu.id_cliente = c.id_cliente
                WHERE c.pin = ?
                ORDER BY c.id_cliente
                LIMIT 1
                """;

        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, pin);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapearCliente(rs));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando PIN.", ex);
        }
    }

    public double obtenerSaldo(String numCuenta) {
        String sql = "SELECT saldo_disponible FROM cuenta WHERE num_cuenta = ?";
        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("La cuenta no existe.");
                }
                return rs.getDouble("saldo_disponible");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error al consultar saldo.", ex);
        }
    }

    public ResultadoRetiro retirar(String numCuenta, int monto) {
        String sqlCuenta = "SELECT id_cuenta, saldo_disponible FROM cuenta WHERE num_cuenta = ?";
        String sqlActualizar = "UPDATE cuenta SET saldo_disponible = ? WHERE num_cuenta = ?";

        try (Connection cn = GestorBaseDatos.obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                int idCuenta;
                double saldoActual;
                try (PreparedStatement psSaldo = cn.prepareStatement(sqlCuenta)) {
                    psSaldo.setString(1, numCuenta);
                    try (ResultSet rs = psSaldo.executeQuery()) {
                        if (!rs.next()) {
                            cn.rollback();
                            return new ResultadoRetiro(false, "No se encontró la cuenta.", 0);
                        }
                        idCuenta = rs.getInt("id_cuenta");
                        saldoActual = rs.getDouble("saldo_disponible");
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
                    psUpdate.setString(2, numCuenta);
                    psUpdate.executeUpdate();
                }

                insertarTransaccion(cn, idCuenta, "RETIRO", monto);
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

    public void registrarTransaccion(String numCuenta, String tipo, double monto) {
        String sql = "SELECT id_cuenta FROM cuenta WHERE num_cuenta = ?";
        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("No se encontró la cuenta para transacción.");
                }
                insertarTransaccion(cn, rs.getInt("id_cuenta"), tipo, monto);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo registrar la transacción.", ex);
        }
    }

    public List<HistorialTransacciones> obtenerHistorial(String numCuenta) {
        String sql = """
                SELECT t.tipo, t.monto, t.fecha
                FROM transaccion t
                JOIN cuenta cu ON cu.id_cuenta = t.id_cuenta
                WHERE cu.num_cuenta = ?
                ORDER BY t.id_transaccion DESC
                LIMIT 20
                """;

        List<HistorialTransacciones> historial = new ArrayList<>();
        try (Connection cn = GestorBaseDatos.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, numCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historial.add(new HistorialTransacciones(
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

    private ClienteATM mapearCliente(ResultSet rs) throws SQLException {
        CuentaATM cuenta = new CuentaATM(
                rs.getInt("id_cuenta"),
                rs.getInt("id_cliente"),
                rs.getString("num_cuenta"),
                rs.getString("tipo_cuenta"),
                rs.getDouble("saldo_disponible")
        );

        return new ClienteATM(
                rs.getInt("id_cliente"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("num_tarjeta"),
                rs.getString("pin"),
                cuenta
        );
    }

    private void insertarTransaccion(Connection cn, int idCuenta, String tipo, double monto) throws SQLException {
        String sql = "INSERT INTO transaccion (id_cuenta, tipo, monto, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
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
