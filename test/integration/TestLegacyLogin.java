package integration;

import Model.Usuario;
import Querys.ConsultasLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Valida autenticación legacy (ConsultasLogin).
 */
public final class TestLegacyLogin {

    public static int run(TestReporter r) {
        Connection con = TestDb.open(r);
        if (con == null) {
            return r.getFallos();
        }
        String username = null;
        String password = null;
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT username, password FROM usuario "
                            + "WHERE status IS NULL OR status = 'Activo' LIMIT 1");
                    ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                    password = rs.getString("password");
                }
            }
            if (username == null) {
                r.fail("no hay usuario activo en tabla usuario");
                return r.getFallos();
            }

            System.out.println("1. Login válido");
            ConsultasLogin login = new ConsultasLogin();
            Usuario ok = new Usuario();
            ok.setUsername(username);
            ok.setPassword(password);
            if (login.iniciar(ok) && ok.getTipo_nivel() != null) {
                r.ok("login " + username + " (" + ok.getTipo_nivel() + ")");
            } else {
                r.fail("login válido rechazado para " + username);
            }

            System.out.println("\n2. Credenciales incorrectas");
            Usuario mal = new Usuario();
            mal.setUsername(username);
            mal.setPassword("__password_invalida__");
            if (!login.iniciar(mal)) {
                r.ok("contraseña incorrecta rechazada");
            } else {
                r.fail("contraseña incorrecta aceptada");
            }

            System.out.println("\n3. Usuario inexistente");
            Usuario ghost = new Usuario();
            ghost.setUsername("__usuario_inexistente__");
            ghost.setPassword("x");
            if (!login.iniciar(ghost)) {
                r.ok("usuario inexistente rechazado");
            } else {
                r.fail("usuario inexistente aceptado");
            }
        } catch (Exception ex) {
            r.fail("excepción login", ex);
        } finally {
            TestDb.closeQuiet(con);
        }
        return r.getFallos();
    }

    public static void main(String[] args) {
        TestReporter r = new TestReporter();
        System.out.println("=== Test Legacy — Login ===\n");
        int fallos = run(r);
        System.out.println(fallos > 0 ? "\n=== " + fallos + " fallo(s) ===" : "\n=== Login legacy OK ===");
        System.exit(fallos > 0 ? 1 : 0);
    }
}