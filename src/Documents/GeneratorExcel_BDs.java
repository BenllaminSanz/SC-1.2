package Documents;

import Functions.DateTools;
import Querys.Conexion;
import static Tables.CargarTabla.conn;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneratorExcel_BDs extends Conexion {

    public static boolean BD_CURSOS() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);
        
        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Cursos");
        fileChooser.setSelectedFile(new File("Respaldo de Cursos " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();
        
        try {
            Workbook workbook = new XSSFWorkbook();
            Connection con = conn.getConnection(); // Crear un archivo de Excel
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tipo_curso");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sheet sheet = workbook.createSheet(rs.getString("nombre_Tipo"));
                int rowIndex = 0;

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_curso where tipo_curso = ?");
                ps1.setString(1, rs.getString("nombre_tipo"));
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue("Curso " + rs1.getString("nombre_curso"));

                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM view_historialcursos where nombre_curso = ? \n "
                            + "ORDER BY estado_curso ASC , fecha_inicio DESC");
                    ps2.setString(1, rs1.getString("nombre_curso"));
                    ResultSet rs2 = ps2.executeQuery();

                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue("Fecha de Inicio");
                    row.createCell(1).setCellValue("Fecha de Estimada");
                    row.createCell(2).setCellValue("Fecha de Cierre");
                    row.createCell(3).setCellValue("Instructores:");
                    row.createCell(4).setCellValue("Duración (Minutos)");
                    row.createCell(5).setCellValue("Asistencia Esperada");
                    row.createCell(6).setCellValue("Asistencia Real");
                    row.createCell(7).setCellValue("Minutos Capacitación");
                    row.createCell(8).setCellValue("Costo del Curso");
                    row.createCell(9).setCellValue("Estado del Curso");

                    while (rs2.next()) {
                        row = sheet.createRow(rowIndex++);
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

                        PreparedStatement ps3 = con.prepareStatement("SELECT * FROM view_asistentes_cursos WHERE idHistorial_Curso = ?");
                        ps3.setString(1, rs2.getString("idHistorial"));
                        ResultSet rs3 = ps3.executeQuery();

                        row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue("Nomina");
                        row.createCell(1).setCellValue("Nombre");
                        row.createCell(8).setCellValue("Tipo");
                        row.createCell(9).setCellValue("Estado");

                        while (rs3.next()) {
                            row = sheet.createRow(rowIndex++);
                            row.createCell(0).setCellValue(rs3.getString("idAsistentes_Curso"));
                            row.createCell(1).setCellValue(rs3.getString("nombre_asistente"));
                            row.createCell(8).setCellValue(rs3.getString("asistente_type"));
                            row.createCell(9).setCellValue(rs3.getString("status_entrenamiento"));
                        }
                    }
                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue("");
                }
            }
            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Respaldo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean BD_CERTIFICADOS() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Respaldo de Certificados");
        fileChooser.setSelectedFile(new File("Respaldo Historial de Certificados " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();
        try {
            Workbook workbook = new XSSFWorkbook();
            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sheet sheet = workbook.createSheet(rs.getString("nombre_Area"));
                int rowIndex = 0;

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getString("Nombre_Area"));

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue("Turno " + rs1.getString("nombre_Turno"));

                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM view_asistentes_certificados \n"
                            + "WHERE nombre_area = ? AND nombre_turno = ?");
                    ps2.setString(1, rs.getString("nombre_Area"));
                    ps2.setString(2, rs1.getString("nombre_Turno"));
                    ResultSet rs2 = ps2.executeQuery();

                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue("Nomina");
                    row.createCell(1).setCellValue("Nombre");
                    row.createCell(3).setCellValue("Certificado");
                    row.createCell(5).setCellValue("Estado");
                    row.createCell(6).setCellValue("Tipo");
                    row.createCell(7).setCellValue("Inicio");
                    row.createCell(8).setCellValue("Cierre");
                    row.createCell(9).setCellValue("Certificación");

                    while (rs2.next()) {
                        row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(rs2.getInt("Folio_Trabajador"));
                        row.createCell(1).setCellValue(rs2.getString("nombre_trabajador"));
                        row.createCell(3).setCellValue(rs2.getString("nombre_certificado"));
                        row.createCell(5).setCellValue(rs2.getString("tipo_certificacion"));
                        row.createCell(6).setCellValue(rs2.getString("estado_certificacion"));
                        row.createCell(7).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_inicio")));
                        row.createCell(8).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_termino")));
                        row.createCell(9).setCellValue(DateTools.MySQLtoString(rs2.getDate("fecha_certificacion")));
                    }
                }
            }
            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Respaldo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
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
}
