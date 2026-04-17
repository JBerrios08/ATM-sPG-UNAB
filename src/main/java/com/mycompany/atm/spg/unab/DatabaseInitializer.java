package com.mycompany.atm.spg.unab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {
        try (Connection connection = DatabaseManager.getConnection()) {
            crearTablas(connection);
            insertarUsuarioInicialSiCorresponde(connection);
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo inicializar la base de datos SQLite.", ex);
        }
    }

    private static void crearTablas(Connection connection) throws SQLException {
        String crearUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    numero_cuenta TEXT NOT NULL UNIQUE,
                    pin TEXT NOT NULL,
                    saldo REAL NOT NULL DEFAULT 0
                )
                """;

        String crearTransacciones = """
                CREATE TABLE IF NOT EXISTS transacciones (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    numero_cuenta TEXT NOT NULL,
                    tipo TEXT NOT NULL,
                    monto REAL NOT NULL,
                    fecha TEXT NOT NULL
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(crearUsuarios);
            statement.execute(crearTransacciones);
        }
    }

    private static void insertarUsuarioInicialSiCorresponde(Connection connection) throws SQLException {
        String contarUsuarios = "SELECT COUNT(*) FROM usuarios";
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(contarUsuarios)) {
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return;
            }
        }

        String insertarUsuario = """
                INSERT INTO usuarios (nombre, numero_cuenta, pin, saldo)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertarUsuario)) {
            preparedStatement.setString(1, "Usuario Demo");
            preparedStatement.setString(2, "000123456789");
            preparedStatement.setString(3, "1234");
            preparedStatement.setDouble(4, 500.0);
            preparedStatement.executeUpdate();
        }
    }
}
