import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CursorEspera {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cargando Archivo...");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton botonCargar = new JButton("Cargar Archivo");
        frame.add(botonCargar);

        botonCargar.addActionListener(e -> {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // Activa cursor de espera

            // Simula carga de archivo en un hilo separado
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                cargarArchivo(); // Método de carga simulada
                frame.setCursor(Cursor.getDefaultCursor()); // Vuelve al cursor normal
            });
            executor.shutdown();
        });

        frame.setVisible(true);
    }

    private static void cargarArchivo() {
        try {
            Thread.sleep(3000); // Simula carga de archivo con pausa de 3 segundos
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}