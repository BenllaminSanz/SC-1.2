package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * LBU: vistas y consultas usadas en Graphics_LBU y reportes PDF.
 */
public final class TestLegacyLbu {

    private static final String[] VISTAS = {
        "view_lbu", "view_lbu_total", "view_lbu_resumen",
        "view_lbu_puesto", "view_lbu_puesto_turno", "view_lbu_area_supervisor"
    };

    public static int run(TestReporter r) {
        Connection con = TestDb.open(r);
        if (con == null) {
            return r.getFallos();
        }
        try {
            System.out.println("1. Vistas LBU");
            for (String vista : VISTAS) {
                try {
                    int n = TestDb.countView(con, vista);
                    r.ok(vista + " → " + n + " fila(s)");
                } catch (Exception ex) {
                    r.fail(vista, ex);
                }
            }

            System.out.println("\n2. Totales globales (estilo reporte PDF)");
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM view_lbu_total");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r.ok("view_lbu_total consultable");
                } else {
                    r.fail("view_lbu_total sin filas");
                }
            }

            System.out.println("\n3. Resumen por supervisor");
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT Supervisor FROM view_lbu_resumen LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getString("Supervisor") != null) {
                    r.ok("supervisor: " + rs.getString("Supervisor"));
                } else {
                    r.fail("view_lbu_resumen vacío");
                }
            }

            System.out.println("\n4. Drill-down área/puesto");
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT Area, Nombre_Puesto FROM view_lbu_puesto LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r.ok("puesto " + rs.getString("Nombre_Puesto") + " (" + rs.getString("Area") + ")");
                } else {
                    r.fail("view_lbu_puesto vacío");
                }
            }
        } catch (Exception ex) {
            r.fail("excepción LBU", ex);
        } finally {
            TestDb.closeQuiet(con);
        }
        return r.getFallos();
    }

    public static void main(String[] args) {
        TestReporter r = new TestReporter();
        System.out.println("=== Test Legacy — LBU ===\n");
        int fallos = run(r);
        System.out.println(fallos > 0 ? "\n=== " + fallos + " fallo(s) ===" : "\n=== LBU legacy OK ===");
        System.exit(fallos > 0 ? 1 : 0);
    }
}