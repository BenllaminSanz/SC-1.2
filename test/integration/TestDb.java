package integration;

import Querys.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

final class TestDb {

    private TestDb() {
    }

    static Connection open(TestReporter r) {
        Conexion cx = new Conexion();
        Connection con = cx.getConnectionSilent();
        if (con == null) {
            r.fail("conexión a MySQL (revisar host, usuario y contraseña en Conexion.java o variables DB_*)");
        } else {
            r.ok("conexión MySQL");
        }
        return con;
    }

    static void closeQuiet(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ignored) {
            }
        }
    }

    static boolean spExists(Connection con, String nombre) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) AS n FROM information_schema.ROUTINES "
                        + "WHERE ROUTINE_SCHEMA = DATABASE() AND ROUTINE_NAME = ? AND ROUTINE_TYPE = 'PROCEDURE'")) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("n") == 1;
            }
        }
    }

    static int countView(Connection con, String vista) throws SQLException {
        try (Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) AS n FROM `" + vista + "`")) {
            return rs.next() ? rs.getInt("n") : 0;
        }
    }

    static boolean tableExists(Connection con, String tabla) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) AS n FROM information_schema.TABLES "
                        + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?")) {
            ps.setString(1, tabla);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("n") == 1;
            }
        }
    }
}