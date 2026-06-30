package integration;

/**
 * Utilidad compartida para suites de integración legacy.
 */
public final class TestReporter {

    private int fallos;

    public void ok(String mensaje) {
        System.out.println("  ✓ " + mensaje);
    }

    public void fail(String mensaje) {
        fallos++;
        System.err.println("  ✗ " + mensaje);
    }

    public void fail(String mensaje, Exception ex) {
        fallos++;
        String detalle = ex != null && ex.getMessage() != null ? ex.getMessage() : "";
        System.err.println("  ✗ " + mensaje + (detalle.isEmpty() ? "" : " — " + detalle));
    }

    public int getFallos() {
        return fallos;
    }
}