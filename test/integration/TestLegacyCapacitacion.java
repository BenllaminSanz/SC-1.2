package integration;

import Functions.ConsultaHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Capacitación: vistas y consultas de Graphics_Capacitacion / CtrlCapacitacion.
 */
public final class TestLegacyCapacitacion {

    public static int run(TestReporter r) {
        Connection con = TestDb.open(r);
        if (con == null) {
            return r.getFallos();
        }
        try {
            System.out.println("1. Vistas capacitación");
            String[] vistas = {
                "view_historialcursos", "view_estado_capacitacion",
                "view_estado_capacitacion_general", "view_reporte_certificados",
                "view_asistentes_cursos", "view_lista_asistentes_curso"
            };
            for (String vista : vistas) {
                try {
                    int n = TestDb.countView(con, vista);
                    r.ok(vista + " → " + n + " fila(s)");
                } catch (Exception ex) {
                    r.fail(vista, ex);
                }
            }

            System.out.println("\n2. PieChar certificados (Graphics_Capacitacion)");
            try (Statement st = con.createStatement()) {
                st.executeUpdate("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
                try (ResultSet rs = st.executeQuery(
                        "SELECT certificaciones, "
                                + "(CASE WHEN certificaciones = 0 "
                                + "THEN (SELECT COUNT(*) FROM view_reporte_certificados "
                                + "WHERE certificaciones = 0 AND obsoleto = 0) "
                                + "ELSE COUNT(*) END) AS cantidad "
                                + "FROM view_reporte_certificados "
                                + "GROUP BY certificaciones ORDER BY certificaciones ASC")) {
                    int grupos = 0;
                    while (rs.next()) {
                        grupos++;
                    }
                    if (grupos > 0) {
                        r.ok("PieChar: " + grupos + " grupo(s)");
                    } else {
                        r.fail("PieChar sin datos");
                    }
                }
            }

            System.out.println("\n3. Variables @nombre_Area (reportes Java)");
            try (PreparedStatement ps = con.prepareStatement("SELECT Nombre_Area FROM area LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombreArea = rs.getString("Nombre_Area");
                    try (PreparedStatement set = con.prepareStatement("SET @nombre_Area = ?")) {
                        set.setString(1, nombreArea);
                        set.execute();
                    }
                    try (ResultSet tot = con.createStatement().executeQuery(
                            "SELECT * FROM view_certificados_area_total")) {
                        if (tot.next() && tot.getObject("Plantilla") != null) {
                            r.ok("@nombre_Area (" + nombreArea + ") → plantilla OK");
                        } else {
                            r.fail("view_certificados_area_total sin datos");
                        }
                    }
                } else {
                    r.fail("sin áreas para variables de sesión");
                }
            }

            System.out.println("\n4. ConsultaHelper");
            int historialId = 0;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT idHistorial_Curso FROM historial_curso LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    historialId = rs.getInt("idHistorial_Curso");
                }
            }
            if (historialId > 0) {
                String curso = ConsultaHelper.obtenerCurso(historialId);
                if (curso != null && !"0".equals(curso) && !curso.isEmpty()) {
                    r.ok("obtenerCurso historial " + historialId + ": " + curso);
                } else {
                    r.fail("ConsultaHelper.obtenerCurso vacío");
                }
            } else {
                r.fail("sin historial de curso para ConsultaHelper");
            }
        } catch (Exception ex) {
            r.fail("excepción capacitación", ex);
        } finally {
            TestDb.closeQuiet(con);
        }
        return r.getFallos();
    }

    public static void main(String[] args) {
        TestReporter r = new TestReporter();
        System.out.println("=== Test Legacy — Capacitación ===\n");
        int fallos = run(r);
        System.out.println(fallos > 0 ? "\n=== " + fallos + " fallo(s) ===" : "\n=== Capacitación legacy OK ===");
        System.exit(fallos > 0 ? 1 : 0);
    }
}