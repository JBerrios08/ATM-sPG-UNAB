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
            activarClavesForaneas(conexion);
            crearTablas(conexion);
            insertarClientesBase(conexion);
        } catch (SQLException ex) {
            throw new IllegalStateException("No se pudo inicializar SQLite.", ex);
        }
    }

    private static void activarClavesForaneas(Connection conexion) throws SQLException {
        try (Statement st = conexion.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
    }

    private static void crearTablas(Connection conexion) throws SQLException {
        String sqlCliente = """
                CREATE TABLE IF NOT EXISTS cliente (
                    id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    apellido TEXT NOT NULL,
                    num_tarjeta TEXT NOT NULL UNIQUE,
                    pin TEXT NOT NULL
                )
                """;

        String sqlCuenta = """
                CREATE TABLE IF NOT EXISTS cuenta (
                    id_cuenta INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_cliente INTEGER NOT NULL,
                    num_cuenta TEXT NOT NULL UNIQUE,
                    tipo_cuenta TEXT NOT NULL,
                    saldo_disponible REAL NOT NULL DEFAULT 0,
                    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
                )
                """;

        String sqlTransaccion = """
                CREATE TABLE IF NOT EXISTS transaccion (
                    id_transaccion INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_cuenta INTEGER NOT NULL,
                    tipo TEXT NOT NULL,
                    monto REAL NOT NULL,
                    fecha TEXT NOT NULL,
                    FOREIGN KEY (id_cuenta) REFERENCES cuenta(id_cuenta)
                )
                """;

        try (Statement st = conexion.createStatement()) {
            st.execute(sqlCliente);
            st.execute(sqlCuenta);
            st.execute(sqlTransaccion);
        }
    }

    private static void insertarClientesBase(Connection conexion) throws SQLException {
        insertarClienteConCuentaSiNoExiste(
                conexion,
                "Alexis",
                "Castro",
                "5432198765432101",
                "2410",
                "458712349001",
                "AHORRO",
                1650.75
        );

        insertarClienteConCuentaSiNoExiste(
                conexion,
                "Jaime",
                "Berrios",
                "6011987654321098",
                "3704",
                "458712349002",
                "CORRIENTE",
                980.40
        );
    }

    private static void insertarClienteConCuentaSiNoExiste(
            Connection conexion,
            String nombre,
            String apellido,
            String numTarjeta,
            String pin,
            String numCuenta,
            String tipoCuenta,
            double saldoDisponible
    ) throws SQLException {
        Integer idCliente = buscarIdClientePorTarjeta(conexion, numTarjeta);

        if (idCliente == null) {
            String sqlInsertCliente = "INSERT INTO cliente (nombre, apellido, num_tarjeta, pin) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(sqlInsertCliente, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nombre);
                ps.setString(2, apellido);
                ps.setString(3, numTarjeta);
                ps.setString(4, pin);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idCliente = rs.getInt(1);
                    }
                }
            }
        }

        if (idCliente == null) {
            throw new IllegalStateException("No se pudo crear cliente inicial: " + nombre + " " + apellido);
        }

        if (!existeCuenta(conexion, numCuenta)) {
            String sqlInsertCuenta = "INSERT INTO cuenta (id_cliente, num_cuenta, tipo_cuenta, saldo_disponible) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(sqlInsertCuenta)) {
                ps.setInt(1, idCliente);
                ps.setString(2, numCuenta);
                ps.setString(3, tipoCuenta);
                ps.setDouble(4, saldoDisponible);
                ps.executeUpdate();
            }
        }
    }

    private static Integer buscarIdClientePorTarjeta(Connection conexion, String numTarjeta) throws SQLException {
        String sql = "SELECT id_cliente FROM cliente WHERE num_tarjeta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, numTarjeta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_cliente");
                }
                return null;
            }
        }
    }

    private static boolean existeCuenta(Connection conexion, String numCuenta) throws SQLException {
        String sql = "SELECT 1 FROM cuenta WHERE num_cuenta = ? LIMIT 1";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, numCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
