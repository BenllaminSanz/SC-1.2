package Documents;

import Functions.DateTools;
import Querys.Conexion;
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

public class GeneratorExcel_Certificados extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean FormatoDiplomas(int añoSeleccionado, int mesSeleccionado) {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);
        try (Connection con = conn.getConnection(); // Crear un archivo de Excel
                 Workbook workbook = new XSSFWorkbook()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_certificados\n"
                    + "WHERE MONTH(fecha_certificacion) = ? AND YEAR(fecha_certificacion) = ?;");
            ps.setInt(1, mesSeleccionado);
            ps.setInt(2, añoSeleccionado);
            ResultSet rs = ps.executeQuery();

            String mes = DateTools.obtenerNombreMes(mesSeleccionado + 1);
            Sheet sheet = workbook.createSheet(mes);
            int rowIndex = 0;
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("NUM");
            row.createCell(1).setCellValue("Nombre Diploma");
            row.createCell(2).setCellValue("FECH CERT");
            row.createCell(3).setCellValue("puesto- correcto");
            row.createCell(4).setCellValue("Turno");
            row.createCell(5).setCellValue("Supervisor");
            row.createCell(6).setCellValue("Nombre 1");
            row.createCell(7).setCellValue("Gerente 1");
            row.createCell(8).setCellValue("Nombre2");
            row.createCell(9).setCellValue("Gerente 2");
            row.createCell(10).setCellValue("Nombre3");
            row.createCell(11).setCellValue("Gerente3");
            row.createCell(12).setCellValue("T.Pantalón");
            row.createCell(13).setCellValue("T.Playera");

            while (rs.next()) {
                row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getString("Folio_Trabajador"));
                row.createCell(1).setCellValue(rs.getString("Nombres") + " " + rs.getString("Apellidos"));
                row.createCell(2).setCellValue(DateTools.MySQLtoString(rs.getDate("fecha_certificacion")));
                row.createCell(3).setCellValue(rs.getString("nombre_certificado"));
                row.createCell(4).setCellValue(rs.getString("nombre_turno"));
                row.createCell(5).setCellValue(rs.getString("supervisor"));
                row.createCell(6).setCellValue(rs.getString("gerente1"));
                row.createCell(7).setCellValue(rs.getString("puesto1"));
                row.createCell(8).setCellValue(rs.getString("gerente2"));
                row.createCell(9).setCellValue(rs.getString("puesto2"));
                row.createCell(10).setCellValue(rs.getString("gerente3"));
                row.createCell(11).setCellValue(rs.getString("puesto3"));
            }

            // Autoajustar el ancho de las columnas
            for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }
            
            // Crear un JFileChooser para que el usuario elija la ubicación del archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Formato de Diplomas");
            fileChooser.setSelectedFile(new File("DIPLOMAS " + añoSeleccionado + "/Concentrado " + mes + " " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                // El usuario canceló la operación
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
                return false;
            }

            File fileToSave = fileChooser.getSelectedFile();
            String rutaDoc = fileToSave.getAbsolutePath();            

            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Archivo en "+rutaDoc);
            return true;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean FormatoDiplomasAnual(int añoSeleccionado) {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);
        try (Connection con = conn.getConnection(); // Crear un archivo de Excel
                 Workbook workbook = new XSSFWorkbook()) {

            for (int mesSeleccionado = 1; mesSeleccionado <= 12; mesSeleccionado++) {
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_certificados\n"
                        + "WHERE MONTH(fecha_certificacion) = ? AND YEAR(fecha_certificacion) = ?;")) {
                    ps.setInt(1, mesSeleccionado);
                    ps.setInt(2, añoSeleccionado);
                    try (ResultSet rs = ps.executeQuery()) {
                        Sheet sheet = workbook.createSheet(DateTools.obtenerNombreMes(mesSeleccionado));
                        int rowIndex = 0;
                        Row row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue("NUM");
                        row.createCell(1).setCellValue("Nombre Diploma");
                        row.createCell(2).setCellValue("FECH CERT");
                        row.createCell(3).setCellValue("puesto- correcto");
                        row.createCell(4).setCellValue("Nombre 1");
                        row.createCell(5).setCellValue("Gerente 1");
                        row.createCell(6).setCellValue("Nombre2");
                        row.createCell(7).setCellValue("Gerente 2");
                        row.createCell(8).setCellValue("Nombre3");
                        row.createCell(9).setCellValue("Gerente3");

                        while (rs.next()) {
                            row = sheet.createRow(rowIndex++);
                            row.createCell(0).setCellValue(rs.getString("Folio_Trabajador"));
                            row.createCell(1).setCellValue(rs.getString("Nombres") + " " + rs.getString("Apellidos"));
                            row.createCell(2).setCellValue(DateTools.MySQLtoString(rs.getDate("fecha_certificacion")));
                            row.createCell(3).setCellValue(rs.getString("nombre_certificado"));
                            row.createCell(4).setCellValue(rs.getString("gerente1"));
                            row.createCell(5).setCellValue(rs.getString("puesto1"));
                            row.createCell(6).setCellValue(rs.getString("gerente2"));
                            row.createCell(7).setCellValue(rs.getString("puesto2"));
                            row.createCell(8).setCellValue(rs.getString("gerente3"));
                            row.createCell(9).setCellValue(rs.getString("puesto3"));
                        }

                        // Autoajustar el ancho de las columnas
                        for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                            sheet.autoSizeColumn(columnIndex);
                        }
                    }
                }
            }

            // Crear un JFileChooser para que el usuario elija la ubicación del archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Formato de Diplomas");
            fileChooser.setSelectedFile(new File("DIPLOMAS " + añoSeleccionado + "/Concentrado " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                // El usuario canceló la operación
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
                return false;
            }

            File fileToSave = fileChooser.getSelectedFile();
            String rutaDoc = fileToSave.getAbsolutePath();            

            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Archivo en "+rutaDoc);
            return true;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
