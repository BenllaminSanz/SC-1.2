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
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFValueAxis;

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
            // Selección de carpeta destino
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

            // Obtener fecha de inicio del curso
            String fechaInicioStr = QueryFunctions.CapturaCondicionalSimple(
                    "view_historialcurso", "fecha_inicio", "idHistorial", historial);
            if (fechaInicioStr == null || fechaInicioStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontró la fecha de inicio para el historial " + historial, "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            java.sql.Date fechaInicioCursoSQL = java.sql.Date.valueOf(fechaInicioStr);
            long fechaInicioCursoMillis = fechaInicioCursoSQL.getTime();

            // Consulta de evaluaciones
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

                        // Calcular número de semana
                        long diferenciaMillis = fecha.getTime() - fechaInicioCursoMillis;
                        long diferenciaDias = TimeUnit.DAYS.convert(diferenciaMillis, TimeUnit.MILLISECONDS);
                        int semana = (int) (diferenciaDias / 7) + 1;
                        semana = Math.max(1, Math.min(semana, totalSemanas));

                        EvaluacionSemana eval = new EvaluacionSemana();
                        eval.nivel = nivel;
                        eval.fecha = fecha;
                        eval.observacion = observacion;
                        semanas.put(semana, eval);
                    }
                }
            }

            // Generar archivo por asistente
            for (String idAsistente : datosAsistente.keySet()) {
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

                // Encabezados
                Row header = sheet.createRow(filaIndex++);
                header.createCell(0).setCellValue("Trabajador:");
                header.createCell(1).setCellValue("% Avance Final");

                int col = 2;
                for (int s = 1; s <= totalSemanas; s++) {
                    header.createCell(col++).setCellValue("Sem " + s);
                }
                header.createCell(col++).setCellValue("Última Semana");
                for (int s = 1; s <= totalSemanas; s++) {
                    header.createCell(col++).setCellValue("Observación " + s);
                }

                // Fila de avance general
                Row filaAsistente = sheet.createRow(filaIndex++);
                filaAsistente.createCell(0).setCellValue(folioAsistente + " " + nombreAsistente);

                double sumaValoresFinal = 0;
                int nivelMaximo = habilidades.size() * 100;

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

                // Filas de habilidades
                for (String hab : habilidades.keySet()) {
                    Row filaHab = sheet.createRow(filaIndex++);
                    filaHab.createCell(0).setCellValue(hab);

                    Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                    String ultimaLetra = "";
                    int colHab = 2;

                    // Semanas
                    for (int s = 1; s <= totalSemanas; s++) {
                        EvaluacionSemana e = niveles.get(s);
                        if (e != null && e.nivel != null) {
                            int valor = nivelILUO.getOrDefault(e.nivel, 0);
                            filaHab.createCell(colHab++).setCellValue(valor);
                            ultimaLetra = String.valueOf(valor);
                        } else {
                            filaHab.createCell(colHab++).setCellValue("");
                        }
                    }

                    // Última semana
                    filaHab.createCell(colHab++).setCellValue(ultimaLetra);

                    // Observaciones
                    for (int s = 1; s <= totalSemanas; s++) {
                        EvaluacionSemana e = niveles.get(s);
                        if (e != null && e.observacion != null && !e.observacion.isEmpty()) {
                            filaHab.createCell(colHab++).setCellValue(e.observacion);
                        } else {
                            filaHab.createCell(colHab++).setCellValue("-");
                        }
                    }
                }

                // Fila % Avance por semana
                Row filaAvance = sheet.createRow(filaIndex++);
                filaAvance.createCell(0).setCellValue("% Avance");

                int colAvance = 2;
                for (int s = 1; s <= totalSemanas; s++) {
                    double sumaValoresSemana = 0;
                    for (String hab : habilidades.keySet()) {
                        Map<Integer, EvaluacionSemana> niveles = habilidades.get(hab);
                        EvaluacionSemana eval = null;
                        for (int i = s; i >= 1 && eval == null; i--) {
                            eval = niveles.get(i);
                        }
                        if (eval != null) {
                            sumaValoresSemana += nivelILUO.getOrDefault(eval.nivel, 0);
                        }
                    }
                    filaAvance.createCell(colAvance++).setCellValue(redondear((sumaValoresSemana / (double) nivelMaximo) * 100.0));
                }

                // Crear gráfico solo para semanas
                int filaIndexAvance = filaAvance.getRowNum();
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, filaIndexAvance + 2, totalSemanas + 1, filaIndexAvance + 20);

                XSSFChart chart = drawing.createChart(anchor);
                chart.setTitleText("Avance de Crecimiento por Semana");
                ChartLegend legend = chart.getOrCreateLegend();
                legend.setPosition(LegendPosition.BOTTOM);

                LineChartData data = chart.getChartDataFactory().createLineChartData();
                ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
                XSSFValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
                leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

// Datos de categorías y valores
                ChartDataSource<String> semanas = DataSources.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 1, totalSemanas));
                ChartDataSource<Number> porcentajes = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(filaIndexAvance, filaIndexAvance, 1, totalSemanas));

// Serie de línea
                LineChartSeries series = data.addSeries(semanas, porcentajes);
                series.setTitle("% Avance");
//                series.setMarkerStyle(MarkerStyle.CIRCLE); // marcadores en cada punto
//                series.setShowDataLabels(true);             // mostrar valores en cada punto

// Dibujar gráfico
                chart.plot(data, bottomAxis, leftAxis);

                Row headerReq = sheet.createRow(filaIndex++);
                headerReq.createCell(0).setCellValue("Requerimiento");
                headerReq.createCell(1).setCellValue("Cumplido");
                headerReq.createCell(2).setCellValue("Fecha Entrega");
                headerReq.createCell(3).setCellValue("Archivo");

                // Consulta de Requerimientos Cumplidos (Query 2) 
                String queryRequerimientos = "SELECT r.nombre_Requerimiento AS requerimiento, rc.fecha_entrega, rc.ruta_Archivo,\n"
                        + "CASE WHEN rc.fecha_entrega IS NOT NULL THEN 'Cumplido' ELSE 'No cumplido' END AS estado \n"
                        + "FROM asistentes_curso ac \n" + "JOIN historial_curso hc ON hc.idHistorial_Curso = ac.idHistorial_Curso \n"
                        + "JOIN requerimientos r ON r.curso_idcurso = hc.idCurso \n" + "LEFT JOIN requerimientos_cumplidos rc \n"
                        + "ON rc.idAsistentes_Curso = ac.idAsistentes_Curso \n" + "AND rc.idHistorial_curso = ac.idHistorial_Curso \n"
                        + "AND rc.idRequerimientos = r.idRequerimientos \n" + "WHERE ac.idHistorial_Curso = ? AND ac.idAsistentes_Curso = ? \n"
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
                                filaReq.createCell(3).setCellValue(rs2.getString("ruta_Archivo") != null ? rs2.getString("ruta_Archivo") : "");
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
                sheet.setColumnWidth(3, 3000);

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

            JOptionPane.showMessageDialog(null, "Reportes generados en la carpeta: " + carpeta.getAbsolutePath());
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
