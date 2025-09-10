package Documents;

import Functions.QueryFunctions;
import Querys.Conexion;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
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
            // Selección de carpeta destino
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            File carpeta = fileChooser.getSelectedFile();

            Workbook workbook = new XSSFWorkbook();
            String historial = String.valueOf(idHistorial);
            String nombreCurso = QueryFunctions.CapturaCondicionalSimple("view_historialcurso", "nombre_curso", "idHistorial", historial);
            Sheet sheet = workbook.createSheet("Avance Curso "+nombreCurso);

            // Conexión y consulta
            try (Connection con = conn.getConnection(); PreparedStatement ps = con.prepareStatement(
                    "SELECT e.id_asistente, t.Nombre_Trabajador, h.id_habilidad, h.nombre AS habilidad, "
                    + "e.nivel_alcanzado, e.fecha_evaluacion "
                    + "FROM evaluacion_habilidad_asistente e "
                    + "JOIN trabajador t ON t.Folio_Trabajador = e.id_asistente "
                    + "JOIN habilidad h ON h.id_habilidad = e.id_habilidad "
                    + "WHERE e.id_historialCurso = ? "
                    + "ORDER BY t.Nombre_Trabajador, h.nombre, e.fecha_evaluacion")) {

                ps.setInt(1, idHistorial);

                try (ResultSet rs = ps.executeQuery()) {

                    class EvaluacionSemana {

                        String nivel;
                        java.sql.Date fecha;
                    }

                    // Estructura: asistente -> habilidad -> semana -> evaluacion
                    Map<String, Map<String, Map<Integer, EvaluacionSemana>>> datos = new LinkedHashMap<>();

                    while (rs.next()) {
                        String asistente = rs.getString("Nombre_Trabajador");
                        String habilidad = rs.getString("habilidad");
                        String nivel = rs.getString("nivel_alcanzado");
                        java.sql.Date fecha = rs.getDate("fecha_evaluacion");

                        datos.putIfAbsent(asistente, new LinkedHashMap<>());
                        datos.get(asistente).putIfAbsent(habilidad, new HashMap<>());

                        Map<Integer, EvaluacionSemana> semanas = datos.get(asistente).get(habilidad);

                        // ⚠️ Aquí usas getDate() (día del mes). Podría ajustarse según fecha de inicio del curso.
                        int semana = (fecha.getDate() / 7) + 1;
                        semana = Math.min(semana, totalSemanas);

                        EvaluacionSemana eval = new EvaluacionSemana();
                        eval.nivel = nivel;
                        eval.fecha = fecha;

                        semanas.put(semana, eval);
                    }

                    // Cabecera principal
                    // Cabecera principal
                    Row header = sheet.createRow(0);
                    header.createCell(0).setCellValue("Asistente");
                    header.createCell(1).setCellValue("% Avance Final");

                    for (int s = 1; s <= totalSemanas; s++) {
                        String texto = "Semana " + s;
                        java.sql.Date maxFecha = null;

                        // Buscar la última fecha de evaluación de esa semana en todas las habilidades
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
                        header.createCell(s + 1).setCellValue(texto);
                    }

                    header.createCell(totalSemanas + 2).setCellValue("Última Semana");

                    // Generación de filas
                    int filaIndex = 1;
                    for (String asistente : datos.keySet()) {
                        Map<String, Map<Integer, EvaluacionSemana>> habilidades = datos.get(asistente);

                        double sumaValoresFinal = 0;
                        int nivelMaximo = habilidades.size() * 4;

                        // Fila del Asistente
                        Row filaAsistente = sheet.createRow(filaIndex++);
                        filaAsistente.createCell(0).setCellValue(asistente);

                        // Calcular avance final
                        for (String hab : habilidades.keySet()) {
                            Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                            EvaluacionSemana ultimaEval = niveles.values()
                                    .stream()
                                    .max(Comparator.comparing(e -> e.fecha))
                                    .orElse(null);
                            if (ultimaEval != null) {
                                sumaValoresFinal += nivelILUO.getOrDefault(ultimaEval.nivel, 0);
                            }
                        }
                        double porcentajeFinal = (sumaValoresFinal / nivelMaximo) * 100.0;
                        filaAsistente.createCell(1).setCellValue(redondear(porcentajeFinal));

                        // Filas de Habilidades
                        for (String hab : habilidades.keySet()) {
                            Row filaHab = sheet.createRow(filaIndex++);
                            filaHab.createCell(0).setCellValue(hab);

                            Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                            String ultimaLetra = "";
                            for (int s = 1; s <= totalSemanas; s++) {
                                EvaluacionSemana e = niveles.get(s);
                                if (e != null && e.nivel != null) {
                                    filaHab.createCell(s + 1).setCellValue(e.nivel);
                                    ultimaLetra = e.nivel;
                                }
                            }
                            filaHab.createCell(totalSemanas + 2).setCellValue(ultimaLetra);
                        }

                        // Fila % Avance por Semana
                        Row filaAvance = sheet.createRow(filaIndex++);
                        filaAvance.createCell(0).setCellValue("% Avance");

                        for (int s = 1; s <= totalSemanas; s++) {
                            double sumaValoresSemana = 0;
                            for (String hab : habilidades.keySet()) {
                                Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                                EvaluacionSemana eval = null;
                                // Buscar la última evaluación hasta la semana s
                                for (int i = s; i >= 1 && eval == null; i--) {
                                    eval = niveles.get(i);
                                }
                                if (eval != null) {
                                    sumaValoresSemana += nivelILUO.getOrDefault(eval.nivel, 0);
                                }
                            }
                            double porcentajeSemana = (sumaValoresSemana / nivelMaximo) * 100.0;
                            filaAvance.createCell(s + 1).setCellValue(redondear(porcentajeSemana));
                        }
                    }

                    // Ajuste de columnas
                    for (int i = 0; i <= totalSemanas + 2; i++) {
                        sheet.autoSizeColumn(i);
                    }

                    // Guardar archivo
                    File archivo = new File(carpeta, "Reporte_Avance_Curso_"+nombreCurso+"_"+historial+".xlsx");
                    try (FileOutputStream fos = new FileOutputStream(archivo)) {
                        workbook.write(fos);
                    }
                    workbook.close();

                    JOptionPane.showMessageDialog(null, "Reporte generado en: " + archivo.getAbsolutePath());
                    return true;
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_Cursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
