package com.mycompany.atm.spg.unab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class GestorBaseDatos {

    private static final String URL_DB = "jdbc:sqlite:atm.db";

    private GestorBaseDatos() {
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL_DB);
    }
}
