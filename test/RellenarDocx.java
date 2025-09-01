import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RellenarDocx {

    public static void main(String[] args) throws Exception {
        // Ruta a tu plantilla .docx (puedes usar ruta absoluta o ajustar el working dir)Path plantilla = Paths.get("D:\\Escritorio\\AST OPERADOR G-33.docx"); // tu .docx con ${NOMBRE_1}
        Path plantilla = Paths.get("D:\\Escritorio\\AST OPERADOR G-33.docx"); // tu .docx con ${NOMBRE_1}
        Path salida = Paths.get("D:\\Escritorio\\AST_OPERADOR_G33_Relleno.docx");

        // Mapa de etiquetas y valores
        Map<String, String> datos = new LinkedHashMap<>();
        datos.put("${NOMBRE_1}", "Juan Pérez");
        datos.put("${NOMBRE_2}", "María López");
        datos.put("${NOMBRE_3}", "Carlos García");

        try (InputStream is = Files.newInputStream(plantilla);
             XWPFDocument doc = new XWPFDocument(is)) {

            // 1. Reemplazar en cuerpo principal
            reemplazarEnParrafos(doc.getParagraphs(), datos);
            reemplazarEnTablas(doc.getTables(), datos);

            // 2. Reemplazar en encabezados
            for (XWPFHeader header : doc.getHeaderList()) {
                reemplazarEnParrafos(header.getParagraphs(), datos);
                reemplazarEnTablas(header.getTables(), datos);
            }

            // 3. Reemplazar en pies de página
            for (XWPFFooter footer : doc.getFooterList()) {
                reemplazarEnParrafos(footer.getParagraphs(), datos);
                reemplazarEnTablas(footer.getTables(), datos);
            }

            // Guardar
            try (OutputStream os = Files.newOutputStream(salida)) {
                doc.write(os);
            }
        }

        System.out.println("Documento generado en: " + salida.toAbsolutePath());
    }

    // ==== MÉTODOS DE REEMPLAZO ====

    private static void reemplazarEnParrafos(List<XWPFParagraph> parrafos, Map<String, String> datos) {
        for (XWPFParagraph p : parrafos) {
            for (XWPFRun run : p.getRuns()) {
                String texto = run.text();
                if (texto == null) continue;
                String modificado = texto;
                for (Map.Entry<String, String> entry : datos.entrySet()) {
                    if (modificado.contains(entry.getKey())) {
                        modificado = modificado.replace(entry.getKey(), entry.getValue());
                    }
                }
                if (!texto.equals(modificado)) {
                    run.setText(modificado, 0);
//                    run.setHidden(false); // Quitar formato oculto si lo tenía
                    run.setColor("000000"); // Asegurar color negro visible
                }
            }
        }
    }

    private static void reemplazarEnTablas(List<XWPFTable> tablas, Map<String, String> datos) {
        for (XWPFTable tabla : tablas) {
            for (XWPFTableRow fila : tabla.getRows()) {
                for (XWPFTableCell celda : fila.getTableCells()) {
                    reemplazarEnParrafos(celda.getParagraphs(), datos);
                    reemplazarEnTablas(celda.getTables(), datos); // tablas anidadas
                }
            }
        }
    }
}