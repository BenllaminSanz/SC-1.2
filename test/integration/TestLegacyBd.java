package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Valida SPs, tablas y vistas que comparte legacy + web.
 */
public final class TestLegacyBd {

    private static final String[] SPS = {
        "concluir_curso", "actualizar_entrenamiento", "actualizar_certificados"
    };

    private static final String[] TABLAS = {
        "trabajador", "area", "puesto", "turno", "curso", "historial_curso",
        "asistentes_curso", "asistentes_certificados", "usuario"
    };

    private static final String[] VISTAS = {
        "view_trabajador", "view_lbu", "view_lbu_total", "view_lbu_resumen",
        "view_reporte_entrenamiento", "view_asistentes_cursos",
        "view_asistentes_certificados", "view_estado_capacitacion",
        "view_reporte_certificados", "view_historialcursos",
        "view_lista_asistentes_curso", "view_bajas", "view_supervisor"
    };

    public static int run(TestReporter r) {
        Connection con = TestDb.open(r);
        if (con == null) {
            return r.getFallos();
        }
        try {
            System.out.println("1. Procedimientos almacenados");
            for (String sp : SPS) {
                if (TestDb.spExists(con, sp)) {
                    r.ok("SP " + sp);
                } else {
                    r.fail("SP " + sp + " no encontrado");
                }
            }

            System.out.println("\n2. Tablas core");
            for (String tabla : TABLAS) {
                if (TestDb.tableExists(con, tabla)) {
                    r.ok("tabla " + tabla);
                } else {
                    r.fail("tabla " + tabla + " no encontrada");
                }
            }

            System.out.println("\n3. Vistas legacy");
            for (String vista : VISTAS) {
                try {
                    int n = TestDb.countView(con, vista);
                    r.ok(vista + " → " + n + " fila(s)");
                } catch (Exception ex) {
                    r.fail(vista, ex);
                }
            }

            System.out.println("\n4. Integridad mínima");
            assertMinCount(con, r, "SELECT COUNT(*) AS n FROM trabajador", 1, "trabajadores");
            assertMinCount(con, r, "SELECT COUNT(*) AS n FROM curso", 1, "cursos");
            assertMinCount(con, r, "SELECT COUNT(*) AS n FROM usuario", 1, "usuarios");
        } catch (Exception ex) {
            r.fail("excepción general BD", ex);
        } finally {
            TestDb.closeQuiet(con);
        }
        return r.getFallos();
    }

    private static void assertMinCount(Connection con, TestReporter r, String sql, int min, String label)
            throws Exception {
        try (PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            int n = rs.next() ? rs.getInt("n") : 0;
            if (n >= min) {
                r.ok(label + ": " + n);
            } else {
                r.fail(label + " vacío (" + n + ")");
            }
        }
    }

    public static void main(String[] args) {
        TestReporter r = new TestReporter();
        System.out.println("=== Test Legacy — Base de datos ===\n");
        int fallos = run(r);
        System.out.println(fallos > 0 ? "\n=== " + fallos + " fallo(s) ===" : "\n=== BD legacy OK ===");
        System.exit(fallos > 0 ? 1 : 0);
    }
}