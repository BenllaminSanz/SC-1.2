package Functions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


// Archivo auxiliar: ArchivoHelper.java

public class ArchivoHelper {
    public static void abrirArchivo(String ruta) {
        File archivo = new File(ruta);
        if (archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
            } catch (IOException ex) {
                Logger.getLogger(ArchivoHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El archivo no existe.");
        }
    }

    public static boolean copiarArchivo(File origen, File destino) {
        try {
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("Error al copiar: " + e.getMessage());
            return false;
        }
    }

    public static File seleccionarCarpeta() {
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setDialogTitle("Selecciona la carpeta del expediente del Curso");

        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return selector.getSelectedFile();
        }
        return null;
    }
}