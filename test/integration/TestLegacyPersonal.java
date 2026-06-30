package integration;

import Model.Area;
import Model.Trabajador;
import Querys.ConsultasArea;
import Querys.ConsultasTrabajador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Personal: trabajadores, áreas, bajas, supervisores.
 */
public final class TestLegacyPersonal {

    public static int run(TestReporter r) {
        Connection con = TestDb.open(r);
        if (con == null) {
            return r.getFallos();
        }
        try {
            System.out.println("1. Consulta trabajador (view_trabajador)");
            int folio = 0;
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT Folio_Trabajador FROM view_trabajador LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    folio = rs.getInt("Folio_Trabajador");
                }
            }
            if (folio > 0) {
                ConsultasTrabajador ct = new ConsultasTrabajador();
                Trabajador t = new Trabajador();
                t.setFolio_Trabajador(String.valueOf(folio));
                if (ct.buscar(t)) {
                    r.ok("buscar trabajador folio " + folio);
                } else {
                    r.fail("buscar trabajador folio " + folio);
                }
                String status = ct.consultarStatus(String.valueOf(folio));
                if (status != null && !status.isEmpty()) {
                    r.ok("status trabajador: " + status);
                } else {
                    r.fail("consultarStatus vacío");
                }
            } else {
                r.fail("sin trabajadores en view_trabajador");
            }

            System.out.println("\n2. Áreas");
            int idArea = 0;
            try (PreparedStatement ps = con.prepareStatement("SELECT idArea FROM area LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idArea = rs.getInt("idArea");
                }
            }
            if (idArea > 0) {
                ConsultasArea ca = new ConsultasArea();
                Area area = new Area();
                area.setIdArea(idArea);
                if (ca.buscar(area) && area.getNombre_Area() != null) {
                    r.ok("buscar área " + area.getNombre_Area());
                } else {
                    r.fail("buscar área id=" + idArea);
                }
            } else {
                r.fail("sin áreas");
            }

            System.out.println("\n3. Vistas personal");
            int bajas = TestDb.countView(con, "view_bajas");
            r.ok("view_bajas → " + bajas + " fila(s)");
            int sup = TestDb.countView(con, "view_supervisor");
            r.ok("view_supervisor → " + sup + " fila(s)");

            System.out.println("\n4. Conteo trabajadores activos");
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) AS n FROM view_trabajador WHERE Status_Trabajador <> 'Baja' "
                            + "OR Status_Trabajador IS NULL");
                    ResultSet rs = ps.executeQuery()) {
                int n = rs.next() ? rs.getInt("n") : 0;
                if (n > 0) {
                    r.ok("activos en vista: " + n);
                } else {
                    r.fail("sin trabajadores activos en vista");
                }
            }
        } catch (Exception ex) {
            r.fail("excepción personal", ex);
        } finally {
            TestDb.closeQuiet(con);
        }
        return r.getFallos();
    }

    public static void main(String[] args) {
        TestReporter r = new TestReporter();
        System.out.println("=== Test Legacy — Personal ===\n");
        int fallos = run(r);
        System.out.println(fallos > 0 ? "\n=== " + fallos + " fallo(s) ===" : "\n=== Personal legacy OK ===");
        System.exit(fallos > 0 ? 1 : 0);
    }
}