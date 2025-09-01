package Functions;

import Model.RequerimientosCursoAsistente;
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

    public static void generarExpediente(RequerimientosCursoAsistente mod) {
        File carpetaPrincipal = ArchivoHelper.seleccionarCarpeta();
        if (carpetaPrincipal == null) {
            JOptionPane.showMessageDialog(null, "No se seleccionó carpeta. Operación cancelada.");
            return;
        }

        String trabajador = ConsultaHelper.obtenerNombreTrabajador(mod.getIdAsistente());
        File carpetaTrabajador = new File(carpetaPrincipal, mod.getIdAsistente() + "_" + trabajador);
        if (!carpetaTrabajador.exists()) {
            carpetaTrabajador.mkdirs();
        }

        String curso = ConsultaHelper.obtenerCurso(mod.getIdHistorial());
        String fecha = ConsultaHelper.obtenerFechaCurso(mod.getIdHistorial());
        File carpetaCurso = new File(carpetaTrabajador, curso + " " + fecha);
        if (!carpetaCurso.exists()) {
            carpetaCurso.mkdirs();
        }

        File origen = new File(mod.getRuta_archivo());
        File destino = new File(carpetaCurso, mod.getNombre_archivo());
        ArchivoHelper.copiarArchivo(origen, destino);
    }
}
