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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneratorExcel_Cursos extends Conexion {

    // Instancia estática para la conexión (asumiendo que maneja bien el pooling/single instance)
    private static final Conexion conn = new Conexion();

    // Mapeo de niveles ILUO a valor numérico para cálculo de avance
    private static final Map<String, Integer> nivelILUO;

    static {
        Map<String, Integer> temp = new HashMap<>();
        temp.put("I", 25);
        temp.put("L", 50);
        temp.put("U", 75);
        temp.put("O", 100);
        nivelILUO = Collections.unmodifiableMap(temp);
    }

    /**
     * Objeto interno para almacenar la evaluación de una habilidad en una
     * semana.
     */
    private static class EvaluacionSemana {

        String nivel;
        java.sql.Date fecha;
        String observacion;
    }

    public static boolean AvanceILUO(int idHistorial, int totalSemanas) {
        Map<String, Map<String, Object>> datosAsistente = new LinkedHashMap<>();

        try {
            // 1. Selección de carpeta destino (Mismo que el original)
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            File carpeta = fileChooser.getSelectedFile();

            String historial = String.valueOf(idHistorial);
            String nombreCurso = QueryFunctions.CapturaCondicionalSimple(
                    "view_historialcurso", "nombre_curso", "idHistorial", historial);
            
            String idCurso = QueryFunctions.CapturaCondicionalSimple(
                    "view_historialcurso", "idcurso", "idHistorial", historial);

            // 🆕 1.1. Obtener la fecha de inicio del curso
            String fechaInicioStr = QueryFunctions.CapturaCondicionalSimple(
                    "view_historialcurso", "fecha_inicio", "idHistorial", historial);

            if (fechaInicioStr == null || fechaInicioStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontró la fecha de inicio para el historial " + historial, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Convertir la fecha de inicio a java.util.Date para el cálculo
            java.sql.Date fechaInicioCursoSQL = java.sql.Date.valueOf(fechaInicioStr);
            long fechaInicioCursoMillis = fechaInicioCursoSQL.getTime();

            // 2. Conexión y consulta de Evaluaciones ILUO (Query 1) - Mismo query
            String queryEvaluaciones = "SELECT e.id_asistente, t.Nombre_Trabajador, h.nombre AS habilidad, "
                    + "e.nivel_alcanzado, e.fecha_evaluacion, e.observaciones "
                    + "FROM evaluacion_habilidad_asistente e "
                    + "JOIN trabajador t ON t.Folio_Trabajador = e.id_asistente "
                    + "JOIN habilidad h ON h.id_habilidad = e.id_habilidad "
                    + "WHERE e.id_historialCurso = ? "
                    + "ORDER BY e.fecha_evaluacion";

            try (Connection con = conn.getConnection(); PreparedStatement ps = con.prepareStatement(queryEvaluaciones)) {

                ps.setInt(1, idHistorial);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        String idAsistente = rs.getString("id_asistente");
                        String nombreAsistente = rs.getString("Nombre_Trabajador");
                        String habilidad = rs.getString("habilidad");
                        String nivel = rs.getString("nivel_alcanzado");
                        String observacion = rs.getString("observaciones");
                        java.sql.Date fecha = rs.getDate("fecha_evaluacion");

                        // Inicializar la estructura del asistente si no existe
                        datosAsistente.putIfAbsent(idAsistente, new LinkedHashMap<>());
                        Map<String, Object> asistenteData = datosAsistente.get(idAsistente);

                        asistenteData.putIfAbsent("folio", idAsistente);
                        asistenteData.putIfAbsent("nombre", nombreAsistente);
                        asistenteData.putIfAbsent("habilidades", new LinkedHashMap<String, Map<Integer, EvaluacionSemana>>());

                        @SuppressWarnings("unchecked")
                        Map<String, Map<Integer, EvaluacionSemana>> habilidadesMap
                                = (Map<String, Map<Integer, EvaluacionSemana>>) asistenteData.get("habilidades");

                        habilidadesMap.putIfAbsent(habilidad, new HashMap<>());
                        Map<Integer, EvaluacionSemana> semanas = habilidadesMap.get(habilidad);

                        // 🔴 CORRECCIÓN CRÍTICA: Cálculo de la semana real
                        long diferenciaMillis = fecha.getTime() - fechaInicioCursoMillis;
                        long diferenciaDias = TimeUnit.DAYS.convert(diferenciaMillis, TimeUnit.MILLISECONDS);
                        // El cálculo es: (diferencia en días / 7) + 1. 
                        // El +1 asegura que la semana 1 va de día 0 a día 6.
                        int semana = (int) (diferenciaDias / 7) + 1;

                        // Asegurar que la semana no exceda el total de semanas del curso y sea al menos 1
                        semana = Math.max(1, Math.min(semana, totalSemanas));

                        // ⚠️ Cuidado con la lógica de sobrescritura:
                        // Si hay más de una evaluación por semana para la misma habilidad, 
                        // solo se guardará la última leída (debido a que usas .put(semana, eval)).
                        // Como tu consulta ya ordena por fecha, esto guardará la última evaluación de la semana.
                        EvaluacionSemana eval = new EvaluacionSemana();
                        eval.nivel = nivel;
                        eval.fecha = fecha;
                        eval.observacion = observacion;
                        semanas.put(semana, eval);
                    }
                }
            }

            // 3. Generar un archivo por asistente (Resto del código es funcional con el nuevo Map)
            for (String idAsistente : datosAsistente.keySet()) {
                // ... (El resto del código de la generación del Excel es el mismo, 
                // ya que itera sobre la estructura de datos que ya se corrigió)

                @SuppressWarnings("unchecked")
                Map<String, Object> asistenteData = datosAsistente.get(idAsistente);
                String nombreAsistente = (String) asistenteData.get("nombre");
                String folioAsistente = (String) asistenteData.get("folio");
                @SuppressWarnings("unchecked")
                Map<String, Map<Integer, EvaluacionSemana>> habilidades
                        = (Map<String, Map<Integer, EvaluacionSemana>>) asistenteData.get("habilidades");

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Avance " + nombreCurso);

                int filaIndex = 0;

                // Creación de Encabezados
                Row header = sheet.createRow(filaIndex++);
                header.createCell(0).setCellValue("Trabaajdor:");
                header.createCell(1).setCellValue("% Avance Final");

                for (int s = 1; s <= totalSemanas; s++) {
                    header.createCell(1 + (s - 1) * 2 + 1).setCellValue("Sem " + s);
                    header.createCell(1 + (s - 1) * 2 + 2).setCellValue("Observación");
                }
                header.createCell((totalSemanas * 2) + 2).setCellValue("Última Semana");

                // Fila de Avance General del Asistente
                Row filaAsistente = sheet.createRow(filaIndex++);
                filaAsistente.createCell(0).setCellValue(folioAsistente + " " + nombreAsistente);

                // Calcular avance final (Cálculo original)
                double sumaValoresFinal = 0;
                int nivelMaximo = habilidades.size() * 100; // Asumo 4 es el valor máximo para 'O'

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
                double porcentajeFinal = (sumaValoresFinal / (double) nivelMaximo) * 100.0;
                filaAsistente.createCell(1).setCellValue(redondear(porcentajeFinal));

                // Filas de Habilidades
                for (String hab : habilidades.keySet()) {
                    Row filaHab = sheet.createRow(filaIndex++);
                    filaHab.createCell(0).setCellValue(hab);

                    Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                    String ultimaLetra = "";

                    for (int s = 1; s <= totalSemanas; s++) {
                        EvaluacionSemana e = niveles.get(s);
                        int colNivel = 2 + (s - 1) * 2;
                        int colObs = colNivel + 1;

                        if (e != null && e.nivel != null) {
                            Integer valor = nivelILUO.getOrDefault(e.nivel, 0);

                            // Escribe el VALOR NUMÉRICO en la celda de la semana.
                            filaHab.createCell(colNivel).setCellValue(valor);

                            // Almacena el valor numérico para la columna "Última Semana"
                            ultimaLetra = valor.toString();

                            filaHab.createCell(colObs).setCellValue(e.observacion);
                        } else {
                            // Intenta obtener la última evaluación anterior (para rellenar si es necesario)
                            // Tu código original solo deja celdas vacías si no hay evaluación en esa semana
                            filaHab.createCell(colNivel).setCellValue("");
                            filaHab.createCell(colObs).setCellValue("-");
                        }
                    }
                    filaHab.createCell(2 + totalSemanas * 2).setCellValue(ultimaLetra);
                }

                // Fila % Avance por Semana 
                Row filaAvance = sheet.createRow(filaIndex++);
                filaAvance.createCell(0).setCellValue("% Avance");
                for (int s = 1; s <= totalSemanas; s++) {
                    double sumaValoresSemana = 0;
                    for (String hab : habilidades.keySet()) {
                        Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                        EvaluacionSemana eval = null;

                        // Busca la última evaluación registrada *hasta* esa semana (s)
                        for (int i = s; i >= 1 && eval == null; i--) {
                            eval = niveles.get(i);
                        }
                        if (eval != null) {
                            sumaValoresSemana += nivelILUO.getOrDefault(eval.nivel, 0);
                        }
                    }
                    int colNivel = 2 + (s - 1) * 2;
                    filaAvance.createCell(colNivel)
                            .setCellValue(redondear((sumaValoresSemana / (double) nivelMaximo) * 100.0));
                }

                // ... (El resto del código para Requerimientos y guardado es el mismo)
                filaIndex += 2;

                Row headerReq = sheet.createRow(filaIndex++);
                headerReq.createCell(0).setCellValue("Requerimiento");
                headerReq.createCell(1).setCellValue("Cumplido");
                headerReq.createCell(2).setCellValue("Fecha Entrega");
                headerReq.createCell(3).setCellValue("Archivo");

                // Consulta de Requerimientos Cumplidos (Query 2)
                String queryRequerimientos = "SELECT r.nombre_Requerimiento AS requerimiento, rc.fecha_entrega, rc.ruta_Archivo,\n"
                        + "CASE WHEN rc.fecha_entrega IS NOT NULL THEN 'Cumplido' ELSE 'No cumplido' END AS estado \n"
                        + "FROM asistentes_curso ac \n"
                        + "JOIN historial_curso hc ON hc.idHistorial_Curso = ac.idHistorial_Curso \n"
                        + "JOIN requerimientos r ON r.curso_idcurso = hc.idCurso \n"
                        + "LEFT JOIN requerimientos_cumplidos rc \n"
                        + "ON rc.idAsistentes_Curso = ac.idAsistentes_Curso \n"
                        + "AND rc.idHistorial_curso = ac.idHistorial_Curso \n"
                        + "AND rc.idRequerimientos = r.idRequerimientos \n"
                        + "WHERE ac.idHistorial_Curso = ? AND ac.idAsistentes_Curso = ? \n"
                        + "ORDER BY r.nombre_Requerimiento;";

                try (Connection con2 = conn.getConnection(); PreparedStatement ps2 = con2.prepareStatement(queryRequerimientos)) {

                    ps2.setInt(1, idHistorial);
                    ps2.setString(2, idAsistente);

                    try (ResultSet rs2 = ps2.executeQuery()) {
                        while (rs2.next()) {
                            Row filaReq = sheet.createRow(filaIndex++);
                            filaReq.createCell(0).setCellValue(rs2.getString("requerimiento"));

                            if (rs2.getDate("fecha_entrega") != null) {
                                filaReq.createCell(1).setCellValue("Sí");
                                filaReq.createCell(2).setCellValue(rs2.getDate("fecha_entrega").toString());
                                filaReq.createCell(3).setCellValue(
                                        rs2.getString("ruta_Archivo") != null ? rs2.getString("ruta_Archivo") : "");
                            } else {
                                filaReq.createCell(1).setCellValue("No");
                                filaReq.createCell(2).setCellValue("");
                                filaReq.createCell(3).setCellValue("");
                            }
                        }
                    }
                }

                // Ajuste de columnas
                for (int i = 0; i <= (totalSemanas * 2) + 2; i++) {
                    sheet.autoSizeColumn(i);
                }
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);

                final int ANCHO_MAX_RUTA = 3000;
                sheet.setColumnWidth(3, ANCHO_MAX_RUTA);

                // Guardar archivo individual
                String nombreArchivo = "Avance_" + nombreAsistente.replace(" ", "_")
                        + "_" + nombreCurso.replace(" ", "_")
                        + "_" + historial + ".xlsx";

                File archivo = new File(carpeta, nombreArchivo);
                try (FileOutputStream fos = new FileOutputStream(archivo)) {
                    workbook.write(fos);
                }
                workbook.close();
            }

            JOptionPane.showMessageDialog(null,
                    "Reportes generados en la carpeta: " + carpeta.getAbsolutePath());

            return true;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_Cursos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
