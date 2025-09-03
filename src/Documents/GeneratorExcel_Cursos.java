package Documents;

import Querys.Conexion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneratorExcel_Cursos extends Conexion {

    private static final Conexion conn = new Conexion();

    private static final Map<String, Integer> nivelILUO = Map.of(
            "I", 1,
            "L", 2,
            "U", 3,
            "O", 4
    );

    public static boolean AnvanceILUO(int idHistorial, int totalSemanas) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            File carpeta = fileChooser.getSelectedFile();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Avance Curso");

            Connection con = conn.getConnection();

            // Consulta las evaluaciones
            String sql = "SELECT e.id_asistente, t.Nombre_Trabajador, h.id_habilidad, h.nombre AS habilidad, "
                    + "e.nivel_alcanzado, e.fecha_evaluacion "
                    + "FROM evaluacion_habilidad_asistente e "
                    + "JOIN trabajador t ON t.Folio_Trabajador = e.id_asistente "
                    + "JOIN habilidad h ON h.id_habilidad = e.id_habilidad "
                    + "WHERE e.id_historialCurso = ? "
                    + "ORDER BY t.Nombre_Trabajador, h.nombre, e.fecha_evaluacion";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idHistorial);
            ResultSet rs = ps.executeQuery();

            class EvaluacionSemana {

                String nivel;
                java.sql.Date fecha;
            }

            Map<String, Map<String, Map<Integer, EvaluacionSemana>>> datos = new LinkedHashMap<>();

            while (rs.next()) {
                String asistente = rs.getString("Nombre_Trabajador");
                String habilidad = rs.getString("habilidad");
                String nivel = rs.getString("nivel_alcanzado");
                java.sql.Date fecha = rs.getDate("fecha_evaluacion");

                datos.putIfAbsent(asistente, new LinkedHashMap<>());
                datos.get(asistente).putIfAbsent(habilidad, new HashMap<>());

                Map<Integer, EvaluacionSemana> semanas = datos.get(asistente).get(habilidad);
                int semana = (fecha.getDate() / 7) + 1;
                semana = Math.min(semana, totalSemanas);

                EvaluacionSemana eval = new EvaluacionSemana();
                eval.nivel = nivel;
                eval.fecha = fecha;

                semanas.put(semana, eval);
            }

            // Cabecera
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Asistente/Habilidad");

            for (int s = 1; s <= totalSemanas; s++) {
                String texto = "Semana " + s;
                java.sql.Date maxFecha = null;
                for (Map<String, Map<Integer, EvaluacionSemana>> habMap : datos.values()) {
                    for (Map<Integer, EvaluacionSemana> semanas : habMap.values()) {
                        EvaluacionSemana e = semanas.get(s);
                        if (e != null && (maxFecha == null || e.fecha.after(maxFecha))) {
                            maxFecha = e.fecha;
                        }
                    }
                }
                if (maxFecha != null) {
                    texto += " (" + maxFecha.toString() + ")";
                }
                header.createCell(s).setCellValue(texto);
            }
            header.createCell(totalSemanas + 1).setCellValue("Última Semana");
            header.createCell(totalSemanas + 2).setCellValue("% Avance");

            // Generar filas
            int filaIndex = 1;
            for (String asistente : datos.keySet()) {
                Map<String, Map<Integer, EvaluacionSemana>> habilidades = datos.get(asistente);
                double sumaValores = 0;
                int nivelMaximo = habilidades.size() * 4;

                int ultimaSemanaAsistente = 0;

                for (String hab : habilidades.keySet()) {
                    Row fila = sheet.createRow(filaIndex++);
                    fila.createCell(0).setCellValue(hab);

                    Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                    String ultimaLetra = null;
                    int maxSemana = 0;

                    for (int s = 1; s <= totalSemanas; s++) {
                        EvaluacionSemana e = niveles.get(s);
                        if (e != null && e.nivel != null) {
                            fila.createCell(s).setCellValue(e.nivel);
                            ultimaLetra = e.nivel;
                            maxSemana = s;
                        }
                    }

                    fila.createCell(totalSemanas + 1).setCellValue(ultimaLetra != null ? ultimaLetra : "");
                    if (ultimaLetra != null) {
                        sumaValores += nivelILUO.getOrDefault(ultimaLetra, 0);
                    }
                    ultimaSemanaAsistente = Math.max(ultimaSemanaAsistente, maxSemana);
                }

                // Fila resumen con nombre del asistente y porcentaje
                Row filaResumen = sheet.createRow(filaIndex++);
                filaResumen.createCell(0).setCellValue(asistente);
                double porcentaje = (sumaValores / nivelMaximo) * 100;
                filaResumen.createCell(totalSemanas + 2).setCellValue(String.format("%.2f", porcentaje));
            }

            // Ajuste de columnas
            for (int i = 0; i <= totalSemanas + 2; i++) {
                sheet.autoSizeColumn(i);
            }

            File archivo = new File(carpeta, "Reporte_Avance_Curso.xlsx");
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                workbook.write(fos);
            }
            workbook.close();

            JOptionPane.showMessageDialog(null, "Reporte generado en: " + archivo.getAbsolutePath());
            return true;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_Cursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
