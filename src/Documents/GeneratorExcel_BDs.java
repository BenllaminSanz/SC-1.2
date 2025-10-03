package Documents;

import Functions.DateTools;
import Functions.Niveles_Salarios;
import Querys.Conexion;
import static Tables.CargarTabla.conn;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneratorExcel_BDs extends Conexion {

    public static boolean BD_CURSOS() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Cursos");
        fileChooser.setSelectedFile(new File("Respaldo de Cursos " + FechaS + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook(); Connection con = conn.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM tipo_curso"); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sheet sheet = workbook.createSheet(rs.getString("nombre_Tipo"));
                int rowIndex = 0;

                try (PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_curso WHERE tipo_curso = ?"); PreparedStatement ps2 = con.prepareStatement("SELECT * FROM view_historialcursos WHERE nombre_curso = ? ORDER BY estado_curso ASC, fecha_inicio DESC"); PreparedStatement ps3 = con.prepareStatement("SELECT * FROM view_asistentes_cursos WHERE idHistorial_Curso = ?")) {

                    ps1.setString(1, rs.getString("nombre_tipo"));
                    try (ResultSet rs1 = ps1.executeQuery()) {
                        while (rs1.next()) {
                            Row headerRow = sheet.createRow(rowIndex++);
                            headerRow.createCell(0).setCellValue("Curso " + rs1.getString("nombre_curso"));

                            // Crear estilos de encabezado
                            CellStyle headerStyle = workbook.createCellStyle();
                            Font font = workbook.createFont();
                            font.setBold(true);
                            headerStyle.setFont(font);

                            Row titles = sheet.createRow(rowIndex++);
                            String[] headers = {
                                "Fecha de Inicio", "Fecha Estimada", "Fecha de Cierre", "Instructores",
                                "Duración (Minutos)", "Asistencia Esperada", "Asistencia Real",
                                "Minutos Capacitación", "Costo del Curso", "Estado del Curso"
                            };

                            for (int i = 0; i < headers.length; i++) {
                                Cell cell = titles.createCell(i);
                                cell.setCellValue(headers[i]);
                                cell.setCellStyle(headerStyle);
                            }

                            ps2.setString(1, rs1.getString("nombre_curso"));
                            try (ResultSet rs2 = ps2.executeQuery()) {
                                while (rs2.next()) {
                                    Row row = sheet.createRow(rowIndex++);
                                    row.createCell(0).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_inicio")));
                                    row.createCell(1).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_estimada")));
                                    row.createCell(2).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_cierre")));
                                    row.createCell(3).setCellValue(rs2.getString("nombre_instructor"));
                                    row.createCell(4).setCellValue(rs2.getInt("duracion_curso"));
                                    row.createCell(5).setCellValue(rs2.getInt("asistentes_esperados"));
                                    row.createCell(6).setCellValue(rs2.getInt("num_asistentes"));
                                    row.createCell(7).setCellValue(rs2.getInt("horas_asistentes"));
                                    row.createCell(8).setCellValue(rs2.getInt("costo_curso"));
                                    row.createCell(9).setCellValue(rs2.getString("estado_curso"));

                                    ps3.setString(1, rs2.getString("idHistorial"));
                                    try (ResultSet rs3 = ps3.executeQuery()) {
                                        Row subHeader = sheet.createRow(rowIndex++);
                                        subHeader.createCell(0).setCellValue("Nómina");
                                        subHeader.createCell(1).setCellValue("Nombre");
                                        subHeader.createCell(8).setCellValue("Tipo");
                                        subHeader.createCell(9).setCellValue("Estado");

                                        while (rs3.next()) {
                                            Row r = sheet.createRow(rowIndex++);
                                            r.createCell(0).setCellValue(rs3.getString("idAsistentes_Curso"));
                                            r.createCell(1).setCellValue(rs3.getString("nombre_asistente"));
                                            r.createCell(8).setCellValue(rs3.getString("asistente_type"));
                                            r.createCell(9).setCellValue(rs3.getString("status_entrenamiento"));
                                        }
                                    }
                                    rowIndex++; // Espacio entre cursos
                                }
                            }
                        }
                    }

                    // Ajustar el ancho de columnas
                    if (sheet.getRow(0) != null) {
                        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                            sheet.autoSizeColumn(i);
                        }
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(rutaDoc)) {
                workbook.write(outputStream);
            }

            JOptionPane.showMessageDialog(null, "Respaldo creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;

        } catch (Exception ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public static boolean BD_CERTIFICADOS() throws IOException {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        Properties properties = new Properties();
        properties.load(new FileInputStream("files/niveles.properties"));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Certificados");
        fileChooser.setSelectedFile(new File("Respaldo Historial de Certificados " + FechaS + ".xlsx"));

        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        String rutaDoc = fileChooser.getSelectedFile().getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook()) {
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sheet sheet = workbook.createSheet(rs.getString("nombre_Area"));
                int rowIndex = 0;

                Row header = sheet.createRow(rowIndex++);
                String[] columnas = {
                    "Nómina", "Nombre", "Certificado", "Estado", "Tipo", "Inicio",
                    "Cierre", "Certificación", "Turno", "Salario", "Nivel"
                };

                for (int i = 0; i < columnas.length; i++) {
                    Cell cell = header.createCell(i);
                    cell.setCellValue(columnas[i]);
                }

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    PreparedStatement ps2 = con.prepareStatement(
                            "SELECT * FROM view_asistentes_certificados WHERE nombre_area = ? AND nombre_turno = ?");
                    ps2.setString(1, rs.getString("nombre_Area"));
                    ps2.setString(2, rs1.getString("nombre_Turno"));
                    ResultSet rs2 = ps2.executeQuery();

                    while (rs2.next()) {
                        Row row = sheet.createRow(rowIndex++);
                        int col = 0;

                        row.createCell(col++).setCellValue(rs2.getInt("Folio_Trabajador"));
                        row.createCell(col++).setCellValue(rs2.getString("nombre_trabajador"));
                        row.createCell(col++).setCellValue(rs2.getString("nombre_certificado"));
                        row.createCell(col++).setCellValue(rs2.getString("estado_certificacion"));
                        row.createCell(col++).setCellValue(rs2.getString("tipo_certificacion"));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_inicio")));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_termino")));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_certificacion")));
                        row.createCell(col++).setCellValue(rs2.getString("nombre_turno"));

                        double salario = rs2.getDouble("SalarioDiario_Trabajador");
                        row.createCell(col++).setCellValue(salario);

                        String nivel = Niveles_Salarios.determinarNivel(salario, properties);
                        row.createCell(col).setCellValue(nivel);
                    }
                }

                // Ajustar el ancho de columnas
                for (int i = 0; i < columnas.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(rutaDoc)) {
                workbook.write(outputStream);
            }

            JOptionPane.showMessageDialog(null, "Respaldo creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(rutaDoc));
            }

            ps.close();
            return true;

        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean BD_CERTIFICADOS_BAJAS() throws IOException {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        Properties properties = new Properties();
        properties.load(new FileInputStream("files/niveles.properties"));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Certificados de Bajas");
        fileChooser.setSelectedFile(new File("Respaldo Historial de Certificados de Bajas " + FechaS + ".xlsx"));

        if (fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        String rutaDoc = fileChooser.getSelectedFile().getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook()) {
            Connection con = conn.getConnection();
            PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));");
            psql.execute();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sheet sheet = workbook.createSheet(rs.getString("nombre_Area"));
                int rowIndex = 0;

                Row header = sheet.createRow(rowIndex++);
                String[] columnas = {
                    "Nómina", "Nombre", "Certificado", "Estado", "Tipo", "Inicio",
                    "Cierre", "Certificación", "Fecha de Baja",
                    "Turno", "Salario", "Nivel"
                };

                for (int i = 0; i < columnas.length; i++) {
                    Cell cell = header.createCell(i);
                    cell.setCellValue(columnas[i]);
                }

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    PreparedStatement ps2 = con.prepareStatement(
                            "SELECT * FROM view_asistentes_certificados_bajas WHERE nombre_area = ? AND nombre_turno = ?");
                    ps2.setString(1, rs.getString("nombre_Area"));
                    ps2.setString(2, rs1.getString("nombre_Turno"));
                    ResultSet rs2 = ps2.executeQuery();

                    while (rs2.next()) {
                        Row row = sheet.createRow(rowIndex++);
                        int col = 0;

                        row.createCell(col++).setCellValue(rs2.getInt("Folio_Trabajador"));
                        row.createCell(col++).setCellValue(rs2.getString("nombre_trabajador"));
                        row.createCell(col++).setCellValue(rs2.getString("nombre_certificado"));
                        row.createCell(col++).setCellValue(rs2.getString("estado_certificacion"));
                        row.createCell(col++).setCellValue(rs2.getString("tipo_certificacion"));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_inicio")));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_termino")));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_certificacion")));
                        row.createCell(col++).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_baja")));
                        row.createCell(col++).setCellValue(rs2.getString("nombre_turno"));

                        double salario = rs2.getDouble("SalarioDiario_Trabajador");
                        row.createCell(col++).setCellValue(salario);

                        String nivel = Niveles_Salarios.determinarNivel(salario, properties);
                        row.createCell(col).setCellValue(nivel);
                    }
                }

                // Ajustar el ancho de columnas
                for (int i = 0; i < columnas.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(rutaDoc)) {
                workbook.write(outputStream);
            }

            JOptionPane.showMessageDialog(null, "Respaldo creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(rutaDoc));
            }

            ps.close();
            return true;

        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean BD_TRABAJADORES(Map<String, String> camposDisponibles, List<String> camposSeleccionados) {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Trabajadores");
        fileChooser.setSelectedFile(new File("Respaldo Lista de Trabajadores " + FechaS + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Lista de Trabajadores");
            int rowIndex = 0;

            Row row = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (String campo : camposSeleccionados) {
                row.createCell(colIndex++).setCellValue(camposDisponibles.get(campo));
            }

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM view_trabajador");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                row = sheet.createRow(rowIndex++);
                colIndex = 0;
                for (String campo : camposSeleccionados) {
                    switch (campo) {
                        case "Nivel":
                            String nivel = calcularNivel(rs.getDouble("SalarioDiario_Trabajador"));
                            row.createCell(colIndex++).setCellValue(nivel);
                            break;
                        case "Sexo":
                            String curp = rs.getString("CURP_Trabajador");
                            String sexo = obtenerSexoDesdeCURP(curp);
                            row.createCell(colIndex++).setCellValue(sexo);
                            break;
                        default:
                            if (campo.startsWith("fecha")) {
                                row.createCell(colIndex++).setCellValue(DateTools.MySQLtoString(rs.getDate(campo)));
                            } else if (campo.equals("Folio_Trabajador")) {
                                row.createCell(colIndex++).setCellValue(rs.getInt(campo));
                            } else {
                                row.createCell(colIndex++).setCellValue(rs.getString(campo));
                            }
                    }
                }
            }

            for (int i = 0; i < camposSeleccionados.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Respaldo Creado en " + rutaDoc);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }

            ps.close();
            return true;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean BD_BAJAS(String fechaInicio, String fechaFin, Map<String, String> camposDisponibles, List<String> camposSeleccionados) throws IOException {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Bajas");
        fileChooser.setSelectedFile(new File("Respaldo Lista de Bajas " + FechaS + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Lista de Bajas");
            int rowIndex = 0;

            Row row = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (String campo : camposSeleccionados) {
                row.createCell(colIndex++).setCellValue(camposDisponibles.get(campo));
            }

            Connection con = conn.getConnection();
            PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));");
            psql.execute();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM view_bajas WHERE Fecha_Baja >= ? AND Fecha_Baja <= ?");
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                row = sheet.createRow(rowIndex++);
                colIndex = 0;
                for (String campo : camposSeleccionados) {
                    switch (campo) {
                        case "Nivel":
                            String nivel = calcularNivel(rs.getDouble("SalarioDiario_Trabajador"));
                            row.createCell(colIndex++).setCellValue(nivel);
                            break;
                        case "Sexo":
                            String curp = rs.getString("CURP_Trabajador");
                            String sexo = obtenerSexoDesdeCURP(curp);
                            row.createCell(colIndex++).setCellValue(sexo);
                            break;
                        default:
                            if (campo.startsWith("fecha")) {
                                row.createCell(colIndex++).setCellValue(DateTools.MySQLtoString(rs.getDate(campo)));
                            } else if (campo.equals("Folio_Trabajador")) {
                                row.createCell(colIndex++).setCellValue(rs.getInt(campo));
                            } else {
                                row.createCell(colIndex++).setCellValue(rs.getString(campo));
                            }
                    }
                }
            }

            for (int i = 0; i < camposSeleccionados.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Respaldo Creado en " + rutaDoc);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }

            ps.close();
            return true;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean BD_ADMINISTRATIVOS(Map<String, String> camposDisponibles, List<String> camposSeleccionados) {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Administrativos");
        fileChooser.setSelectedFile(new File("Respaldo Lista de Administrativos " + FechaS + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Lista de Administrativos");
            int rowIndex = 0;

            Row row = sheet.createRow(rowIndex++);
            int colIndex = 0;
            for (String campo : camposSeleccionados) {
                row.createCell(colIndex++).setCellValue(camposDisponibles.get(campo));
            }

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM administrativos");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                row = sheet.createRow(rowIndex++);
                colIndex = 0;
                for (String campo : camposSeleccionados) {
                    switch (campo) {
                        case "Sexo":
                            String curp = rs.getString("CURP_administrativo");
                            String sexo = obtenerSexoDesdeCURP(curp);
                            row.createCell(colIndex++).setCellValue(sexo);
                            break;
                        default:
                            if (campo.startsWith("fecha")) {
                                row.createCell(colIndex++).setCellValue(DateTools.MySQLtoString(rs.getDate(campo)));
                            } else if (campo.equals("Folio_Administrativo")) {
                                row.createCell(colIndex++).setCellValue(rs.getInt(campo));
                            } else {
                                row.createCell(colIndex++).setCellValue(rs.getString(campo));
                            }
                    }
                }
            }

            for (int i = 0; i < camposSeleccionados.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Respaldo Creado en " + rutaDoc);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }

            ps.close();
            return true;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean BD_BRIGADAS() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Brigadas");
        fileChooser.setSelectedFile(new File("Respaldo Lista de Brigadas " + FechaS + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Workbook workbook = new XSSFWorkbook()) {

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM view_brigadistas ORDER BY nombre_brigada, idbrigadas"
            );
            ResultSet rs = ps.executeQuery();

            String brigadaActual = null;
            Sheet sheet = null;
            int rowIndex = 0;

            while (rs.next()) {
                String brigada = rs.getString("nombre_brigada");

                // Si es una brigada nueva, crear hoja
                if (brigadaActual == null || !brigada.equals(brigadaActual)) {
                    brigadaActual = brigada;
                    String nombreHoja = brigadaActual.replaceAll("[:\\\\/?*\\[\\]]", "_");
                    sheet = workbook.createSheet(nombreHoja);
                    rowIndex = 0;

                    // Crear encabezados
                    Row header = sheet.createRow(rowIndex++);
//                    header.createCell(0).setCellValue("Brigada");
                    header.createCell(0).setCellValue("Nomina");
                    header.createCell(1).setCellValue("Nombre");
                    header.createCell(2).setCellValue("Area");
                    header.createCell(3).setCellValue("Puesto");
                    header.createCell(4).setCellValue("Turno");
                    header.createCell(5).setCellValue("Tipo");
                }

                // Insertar datos
                Row row = sheet.createRow(rowIndex++);
//                row.createCell(0).setCellValue(brigada);
                row.createCell(0).setCellValue(rs.getString("Nomina"));
                row.createCell(1).setCellValue(rs.getString("Nombre"));
                row.createCell(2).setCellValue(rs.getString("Area"));
                row.createCell(3).setCellValue(rs.getString("Puesto"));
                row.createCell(4).setCellValue(rs.getString("Turno"));
                row.createCell(5).setCellValue(rs.getString("trabajador"));
            }

            // Autoajustar columnas de cada hoja
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet s = workbook.getSheetAt(i);
                if (s.getPhysicalNumberOfRows() > 0) {
                    Row header = s.getRow(0);
                    int totalColumnas = header.getLastCellNum();
                    for (int j = 0; j < totalColumnas; j++) {
                        s.autoSizeColumn(j);
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(rutaDoc)) {
                workbook.write(outputStream);
            }

            JOptionPane.showMessageDialog(null, "Respaldo Creado en " + rutaDoc);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }

            ps.close();
            return true;

        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    private static String calcularNivel(double aDouble) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("files/niveles.properties");
        properties.load(fis);

        // Obtener el salario del trabajador
        double salario = aDouble;
        // Determinar el nivel del trabajador
        String nivel = determinarNivel(salario, properties);
        // Asignar el nivel al objeto tbr
        return nivel;
    }

    public static String determinarNivel(double salario, Properties properties) {
        // Recorre las claves de los niveles y compara los salarios
        for (String key : properties.stringPropertyNames()) {
            String salarioStr = properties.getProperty(key);
            if (salarioStr != null && !salarioStr.isEmpty()) {
                double nivelSalario = Double.parseDouble(salarioStr);

                // Si el salario del trabajador es mayor o igual al salario del nivel, asigna el nivel
                if (salario == nivelSalario) {
                    return key.replace("nivel.", "");  // Retorna el nivel
                }
            }
        }

        // Si no se encuentra un nivel adecuado, retornar un valor predeterminado
        return " ";
    }

    private static String obtenerSexoDesdeCURP(String curp) {
        // Verificar si el CURP es válido y tiene la longitud correcta (18 caracteres)
        if (curp != null && curp.length() == 18) {
            // Obtener el séptimo carácter (índice 6) que representa el sexo
            char sexoChar = curp.charAt(10);
            // Verificar el sexo y asignar el valor correspondiente
            String sexo = (sexoChar == 'H') ? "Hombre" : (sexoChar == 'M') ? "Mujer" : "Desconocido";
            // Asignar el sexo al objeto tbr (o a la variable correspondiente)
            return sexo;
        }
        return null;
    }
}
