package integration;

/**
 * Orquestador del pool de pruebas legacy.
 * Uso: run-test-pool.bat
 *      java integration.RunTestPool --only bd,login
 */
public final class RunTestPool {

    private static final class Suite {
        final String id;
        final String nombre;
        final SuiteRunner runner;

        Suite(String id, String nombre, SuiteRunner runner) {
            this.id = id;
            this.nombre = nombre;
            this.runner = runner;
        }
    }

    @FunctionalInterface
    private interface SuiteRunner {
        int run(TestReporter reporter);
    }

    private static final Suite[] SUITES = {
        new Suite("bd", "Base de datos y vistas core", TestLegacyBd::run),
        new Suite("login", "Autenticación (ConsultasLogin)", TestLegacyLogin::run),
        new Suite("personal", "Personal (trabajadores, áreas)", TestLegacyPersonal::run),
        new Suite("lbu", "LBU operativo", TestLegacyLbu::run),
        new Suite("capacitacion", "Capacitación y certificados", TestLegacyCapacitacion::run),
        new Suite("archivos", "Archivos de configuración", TestLegacyArchivos::run),
    };

    public static void main(String[] args) {
        String only = null;
        for (int i = 0; i < args.length; i++) {
            if ("--only".equals(args[i]) && i + 1 < args.length) {
                only = args[i + 1];
            }
        }

        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║   POOL DE PRUEBAS — Software Capacitación Legacy (Java) ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        if (only != null) {
            System.out.println("Filtro --only: " + only);
        }
        System.out.println();

        int totalFallos = 0;
        int ejecutadas = 0;
        long t0 = System.currentTimeMillis();

        for (Suite suite : SUITES) {
            if (only != null && !matchesFilter(suite.id, only)) {
                continue;
            }
            ejecutadas++;
            System.out.println(line('─', 60));
            System.out.println("▶ " + suite.nombre);
            System.out.println(line('─', 60));

            TestReporter reporter = new TestReporter();
            long ts = System.currentTimeMillis();
            int fallos = suite.runner.run(reporter);
            long ms = System.currentTimeMillis() - ts;
            totalFallos += fallos;

            String icon = fallos == 0 ? "✓" : "✗";
            System.out.printf("%n  %s %s (%.1fs)%n", icon,
                    fallos == 0 ? "OK" : fallos + " fallo(s)", ms / 1000.0);
        }

        if (ejecutadas == 0) {
            System.err.println("Ninguna suite coincide con el filtro.");
            System.exit(1);
        }

        double totalSec = (System.currentTimeMillis() - t0) / 1000.0;
        System.out.println("\n" + line('═', 60));
        System.out.println("RESUMEN");
        System.out.println(line('═', 60));
        System.out.printf("%d suite(s) — %.1fs total%n", ejecutadas, totalSec);

        if (totalFallos > 0) {
            System.out.println("⚠ " + totalFallos + " fallo(s) en el pool");
            System.exit(1);
        }
        System.out.println("\n✔ Pool legacy completado sin fallos");
    }

    private static String line(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static boolean matchesFilter(String id, String only) {
        String[] parts = only.split(",");
        for (String part : parts) {
            if (id.equalsIgnoreCase(part.trim())) {
                return true;
            }
        }
        return false;
    }
}