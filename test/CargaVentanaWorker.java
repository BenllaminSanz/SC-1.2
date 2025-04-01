
import javax.swing.SwingWorker;
public class CargaVentanaWorker extends SwingWorker<Void, Void> {
    private VentanaPrincipal ventanaPrincipal;
    private VentanaCarga ventanaCarga;

    // Constructor del SwingWorker
    public CargaVentanaWorker(VentanaPrincipal ventanaPrincipal, VentanaCarga ventanaCarga) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.ventanaCarga = ventanaCarga;
    }

    // Método que se ejecuta en segundo plano
    @Override
    protected Void doInBackground() throws Exception {
        // Cargar la ventana principal en segundo plano
        Thread.sleep(100); // Simulando una carga de 5 segundos
        return null;
    }

    // Método que se ejecuta en el hilo de eventos de Swing una vez que se completa la tarea en segundo plano
    @Override
    protected void done() {
        ventanaCarga.setVisible(false);
        ventanaPrincipal.setVisible(true);
    }
}