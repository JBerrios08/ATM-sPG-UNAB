package com.mycompany.atm.spg.unab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ATMRepository {

    public Optional<Usuario> buscarUsuarioPorPin(String pin) {
        String sql = """
                SELECT id, nombre, numero_cuenta, pin, saldo
                FROM usuarios
                WHERE pin = ?
                ORDER BY id
                LIMIT 1
                """;

        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapearUsuario(resultSet));
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando usuario por PIN.", ex);
        }
    }

    public double obtenerSaldo(String numeroCuenta) {
        String sql = "SELECT saldo FROM usuarios WHERE numero_cuenta = ?";
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, numeroCuenta);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalStateException("No existe la cuenta especificada.");
                }
                return resultSet.getDouble("saldo");
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando saldo en la base de datos.", ex);
        }
    }

    public RetiroResultado retirar(String numeroCuenta, int monto) {
        String sqlSaldo = "SELECT saldo FROM usuarios WHERE numero_cuenta = ?";
        String sqlActualizarSaldo = "UPDATE usuarios SET saldo = ? WHERE numero_cuenta = ?";

        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                double saldoActual;
                try (PreparedStatement saldoStatement = connection.prepareStatement(sqlSaldo)) {
                    saldoStatement.setString(1, numeroCuenta);
                    try (ResultSet resultSet = saldoStatement.executeQuery()) {
                        if (!resultSet.next()) {
                            connection.rollback();
                            return new RetiroResultado(false, "No se encontró la cuenta del usuario.", 0);
                        }
                        saldoActual = resultSet.getDouble("saldo");
                    }
                }

                if (monto <= 0) {
                    connection.rollback();
                    return new RetiroResultado(false, "El monto debe ser mayor a cero.", saldoActual);
                }

                if (saldoActual < monto) {
                    connection.rollback();
                    return new RetiroResultado(false, "Saldo insuficiente para completar el retiro.", saldoActual);
                }

                double nuevoSaldo = saldoActual - monto;
                try (PreparedStatement actualizarStatement = connection.prepareStatement(sqlActualizarSaldo)) {
                    actualizarStatement.setDouble(1, nuevoSaldo);
                    actualizarStatement.setString(2, numeroCuenta);
                    actualizarStatement.executeUpdate();
                }

                insertarTransaccion(connection, numeroCuenta, "RETIRO", monto);
                connection.commit();
                return new RetiroResultado(true, "Retiro exitoso.", nuevoSaldo);
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error procesando retiro en la base de datos.", ex);
        }
    }

    public void registrarTransaccion(String numeroCuenta, String tipo, double monto) {
        try (Connection connection = DatabaseManager.getConnection()) {
            insertarTransaccion(connection, numeroCuenta, tipo, monto);
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo registrar la transacción.", ex);
        }
    }

    private void insertarTransaccion(Connection connection, String numeroCuenta, String tipo, double monto) throws SQLException {
        String sqlInsert = """
                INSERT INTO transacciones (numero_cuenta, tipo, monto, fecha)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, numeroCuenta);
            preparedStatement.setString(2, tipo);
            preparedStatement.setDouble(3, monto);
            preparedStatement.setString(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            preparedStatement.executeUpdate();
        }
    }

    private Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        return new Usuario(
                resultSet.getInt("id"),
                resultSet.getString("nombre"),
                resultSet.getString("numero_cuenta"),
                resultSet.getString("pin"),
                resultSet.getDouble("saldo")
        );
    }
}
