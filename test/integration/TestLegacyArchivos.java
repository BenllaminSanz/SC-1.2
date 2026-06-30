package integration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Archivos de configuración que usa la app Java (niveles, índices).
 */
public final class TestLegacyArchivos {

    public static int run(TestReporter r) {
        System.out.println("1. Archivos en /files");
        File niveles = resolveFile("files/niveles.properties");
        File indice = resolveFile("files/configIndiceT.properties");

        if (niveles.exists() && niveles.isFile()) {
            r.ok("niveles.properties encontrado");
        } else {
            r.fail("falta files/niveles.properties");
        }

        if (indice.exists() && indice.isFile()) {
            r.ok("configIndiceT.properties encontrado");
        } else {
            r.fail("falta files/configIndiceT.properties (importación Excel)");
        }

        System.out.println("\n2. Contenido niveles.properties");
        if (niveles.exists()) {
            try (FileInputStream in = new FileInputStream(niveles)) {
                Properties props = new Properties();
                props.load(in);
                int conValor = 0;
                for (String key : props.stringPropertyNames()) {
                    if (key.startsWith("nivel.") && props.getProperty(key) != null
                            && !props.getProperty(key).trim().isEmpty()) {
                        conValor++;
                    }
                }
                if (conValor >= 5) {
                    r.ok(conValor + " nivel(es) con salario configurado");
                } else {
                    r.fail("pocos niveles con valor (" + conValor + ")");
                }
            } catch (Exception ex) {
                r.fail("leer niveles.properties", ex);
            }
        }

        System.out.println("\n3. JAR y librerías");
        File jar = resolveFile("dist/Software_Capacitaci_n.jar");
        File lib = resolveFile("lib/mysql-connector-j-8.0.32.jar");
        if (jar.exists()) {
            r.ok("dist/Software_Capacitaci_n.jar");
        } else {
            r.fail("falta JAR en dist/ (ejecutar Build en NetBeans)");
        }
        if (lib.exists()) {
            r.ok("mysql-connector en lib/");
        } else {
            r.fail("falta mysql-connector-j en lib/");
        }
        return r.getFallos();
    }

    private static File resolveFile(String relative) {
        File cwd = new File(System.getProperty("user.dir"));
        File direct = new File(cwd, relative);
        if (direct.exists()) {
            return direct;
        }
        return new File(cwd.getParentFile() != null ? cwd : cwd, relative);
    }

    public static void main(String[] args) {
        TestReporter r = new TestReporter();
        System.out.println("=== Test Legacy — Archivos ===\n");
        int fallos = run(r);
        System.out.println(fallos > 0 ? "\n=== " + fallos + " fallo(s) ===" : "\n=== Archivos legacy OK ===");
        System.exit(fallos > 0 ? 1 : 0);
    }
}