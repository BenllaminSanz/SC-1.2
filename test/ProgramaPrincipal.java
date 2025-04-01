
public class ProgramaPrincipal {
    public static void main(String[] args) {
        // Crear la ventana de carga y mostrarla al usuario
        VentanaCarga ventanaCarga = new VentanaCarga(null);
        ventanaCarga.setVisible(true);

        // Crear la ventana principal en segundo plano usando un SwingWorker
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        CargaVentanaWorker worker = new CargaVentanaWorker(ventanaPrincipal, ventanaCarga);
        worker.execute();
    }
}