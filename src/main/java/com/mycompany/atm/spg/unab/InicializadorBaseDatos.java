package com.mycompany.atm.spg.unab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class InicializadorBaseDatos {

    private InicializadorBaseDatos() {
    }

    public static void inicializar() {
        try (Connection conexion = GestorBaseDatos.obtenerConexion()) {
            crearTablas(conexion);
            insertarUsuarioDemoSiNoExiste(conexion);
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo inicializar SQLite.", ex);
        }
    }

    private static void crearTablas(Connection conexion) throws SQLException {
        String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    numero_cuenta TEXT NOT NULL UNIQUE,
                    pin TEXT NOT NULL,
                    saldo REAL NOT NULL DEFAULT 0
                )
                """;

        String sqlTransacciones = """
                CREATE TABLE IF NOT EXISTS transacciones (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    numero_cuenta TEXT NOT NULL,
                    tipo TEXT NOT NULL,
                    monto REAL NOT NULL,
                    fecha TEXT NOT NULL
                )
                """;

        try (Statement st = conexion.createStatement()) {
            st.execute(sqlUsuarios);
            st.execute(sqlTransacciones);
        }
    }

    private static void insertarUsuarioDemoSiNoExiste(Connection conexion) throws SQLException {
        try (Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM usuarios")) {
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }
        }

        String sql = "INSERT INTO usuarios (nombre, numero_cuenta, pin, saldo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "Usuario Demo");
            ps.setString(2, "000123456789");
            ps.setString(3, "1234");
            ps.setDouble(4, 500.00);
            ps.executeUpdate();
        }
    }
}
