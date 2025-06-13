package Documents;

import Querys.Conexion;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeneratorExcel_LBU extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean LBUGeneral() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);
        LocalDate fechaActual = LocalDate.now();

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de LBU");
        fileChooser.setSelectedFile(new File("LBU " + fechaActual.getYear() + ".xlsx")); // Nombre predeterminado del archivo

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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sheet sheet = workbook.createSheet(rs.getString("nombre_Area"));
                int rowIndex = 0;
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_lbu_puesto where Area = ?");
                ps1.setString(1, rs.getString("nombre_Area"));
                ResultSet rs1 = ps1.executeQuery();

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue("Área: " + rs.getString("Nombre_Area"));

                while (rs1.next()) {
                    // Escribir los encabezados de cada área en la hoja de cálculo
                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(rs1.getString("Nombre_Puesto"));
                    row.createCell(4).setCellValue("Propuesta");
                    row.createCell(5).setCellValue("Diferencia");
                    row.createCell(6).setCellValue("Plantilla");
                    row.createCell(7).setCellValue("A");
                    row.createCell(8).setCellValue("B");
                    row.createCell(9).setCellValue("C");
                    row.createCell(10).setCellValue("D");
                    row.createCell(11).setCellValue("LV");
                    row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(rs1.getString("Nombre_Puesto_Ingles"));
                    row.createCell(4).setCellValue(rs1.getString("Propuesto"));
                    row.createCell(5).setCellValue(rs1.getString("Diferencia"));
                    row.createCell(6).setCellValue(rs1.getString("Plantilla"));
                    row.createCell(7).setCellValue(rs1.getString("A"));
                    row.createCell(8).setCellValue(rs1.getString("B"));
                    row.createCell(9).setCellValue(rs1.getString("C"));
                    row.createCell(10).setCellValue(rs1.getString("D"));
                    row.createCell(11).setCellValue(rs1.getString("LV"));
                    if (rs1.getInt("Plantilla") > 0) {
                        // Escribir los encabezados de los trabajadores en la hoja de cálculo
                        row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue("Folio Trabajador");
                        row.createCell(1).setCellValue("Nombre Trabajador");
                        row.createCell(6).setCellValue("Turno");

                        for (String workerType : Arrays.asList("A", "B", "C", "D", "LV")) {
                            ResultSet datosTrabajadorRs = DesingPDF_LBU.datosTrabajador(workerType, rs1.getString("Nombre_Puesto"), con).executeQuery();

                            while (datosTrabajadorRs.next()) {
                                // Escribir los datos de cada trabajador en la hoja de cálculo
                                row = sheet.createRow(rowIndex++);
                                row.createCell(0).setCellValue(datosTrabajadorRs.getString("Folio_Trabajador"));
                                row.createCell(1).setCellValue(datosTrabajadorRs.getString("Nombre_Trabajador"));
                                row.createCell(6).setCellValue(workerType);
                            }
                        }
                        rowIndex++;
                    }
                }
            }
            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
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

    public static boolean LBUResumenSupervisor() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Resumen de Supervisores");
        fileChooser.setSelectedFile(new File("LBU Resumen de Supervisores " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

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

            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_resumen");
            ResultSet rs = ps.executeQuery();

            Sheet sheet = workbook.createSheet("Resúmen Supervisores");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("LBU Operativo Resúmen de Supervisores");
            row = sheet.createRow(1);
            row.createCell(0).setCellValue("Fecha: " + FechaS);

            row = sheet.createRow(2);
            row.createCell(0).setCellValue("Supervisor");
            row.createCell(1).setCellValue("Propuesto");
            row.createCell(2).setCellValue("Diferencia");
            row.createCell(3).setCellValue("Plantilla");
            row.createCell(4).setCellValue("A");
            row.createCell(5).setCellValue("B");
            row.createCell(6).setCellValue("C");
            row.createCell(7).setCellValue("D");
            row.createCell(8).setCellValue("LV");
            int rowIndex = 3;
            while (rs.next()) {
                row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getString("Supervisor"));
                row.createCell(1).setCellValue(rs.getString("Propuesto"));
                if (rs.getInt("Diferencia") > 0) {

                    row.createCell(2).setCellValue(rs.getString("Diferencia"));
                } else if (rs.getInt("Diferencia") < 0) {
                    row.createCell(2).setCellValue(rs.getString("Diferencia"));
                } else {
                    row.createCell(2).setCellValue(rs.getString("Diferencia"));
                }
                row.createCell(3).setCellValue(rs.getString("Plantilla"));

                if (rs.getInt("A") > 0) {
                    row.createCell(4).setCellValue(rs.getString("A"));
                } else {
                    row.createCell(4).setCellValue(rs.getString("A"));
                }
                if (rs.getInt("B") > 0) {
                    row.createCell(5).setCellValue(rs.getString("B"));
                } else {
                    row.createCell(5).setCellValue(rs.getString("B"));
                }
                if (rs.getInt("C") > 0) {
                    row.createCell(6).setCellValue(rs.getString("C"));
                } else {
                    row.createCell(6).setCellValue(rs.getString("C"));
                }
                if (rs.getInt("D") > 0) {
                    row.createCell(7).setCellValue(rs.getString("D"));
                } else {
                    row.createCell(7).setCellValue(rs.getString("D"));
                }
                if (rs.getInt("LV") > 0) {
                    row.createCell(8).setCellValue(rs.getString("LV"));
                } else {
                    row.createCell(8).setCellValue(rs.getString("LV"));
                }
            }
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue("Resúmen de todas las Áreas:");
            row.createCell(1).setCellValue("Propuesto");
            row.createCell(2).setCellValue("Diferencia");
            row.createCell(3).setCellValue("Plantilla");
            row.createCell(4).setCellValue("A");
            row.createCell(5).setCellValue("B");
            row.createCell(6).setCellValue("C");
            row.createCell(7).setCellValue("D");
            row.createCell(8).setCellValue("LV");
            row = sheet.createRow(rowIndex + 1);

            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_total");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                row.createCell(1).setCellValue(rs2.getString("Total_P"));
                if (rs2.getInt("Total_D") > 0) {
                    row.createCell(2).setCellValue(rs2.getString("Total_D"));
                } else if (rs2.getInt("Total_D") < 0) {
                    row.createCell(2).setCellValue(rs2.getString("Total_D"));
                } else {
                    row.createCell(2).setCellValue(rs2.getString("Total_D"));
                }
                row.createCell(3).setCellValue(rs2.getString("Total_Pl"));

                if (rs2.getInt("A") > 0) {
                    row.createCell(4).setCellValue(rs2.getString("A"));
                } else {
                    row.createCell(4).setCellValue(rs2.getString("A"));
                }
                if (rs2.getInt("B") > 0) {
                    row.createCell(5).setCellValue(rs2.getString("B"));
                } else {
                    row.createCell(5).setCellValue(rs2.getString("B"));
                }
                if (rs2.getInt("C") > 0) {
                    row.createCell(6).setCellValue(rs2.getString("C"));
                } else {
                    row.createCell(6).setCellValue(rs2.getString("C"));
                }
                if (rs2.getInt("D") > 0) {
                    row.createCell(7).setCellValue(rs2.getString("D"));
                } else {
                    row.createCell(7).setCellValue(rs2.getString("D"));
                }
                if (rs2.getInt("LV") > 0) {
                    row.createCell(8).setCellValue(rs2.getString("LV"));
                } else {
                    row.createCell(8).setCellValue(rs2.getString("LV"));
                }
            }
            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUResumenArea() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Resumen de Áreas");
        fileChooser.setSelectedFile(new File("LBU Resumen de Área " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection(); // Crear un archivo de Excel
                 Workbook workbook = new XSSFWorkbook()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_resumen");
            ResultSet rs = ps.executeQuery();

            Sheet sheet = workbook.createSheet("Resúmen Áreas");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("LBU Operativo Resúmen de Áreas");
            row = sheet.createRow(1);
            row.createCell(0).setCellValue("Fecha: " + FechaS);

            row = sheet.createRow(2);
            row.createCell(0).setCellValue("Área");
            row.createCell(1).setCellValue("Autorizado");
            row.createCell(2).setCellValue("Diferencia");
            row.createCell(3).setCellValue("Plantilla Actual");
            row.createCell(4).setCellValue("A");
            row.createCell(5).setCellValue("B");
            row.createCell(6).setCellValue("C");
            row.createCell(7).setCellValue("D");
            row.createCell(8).setCellValue("LV");
            int rowIndex = 3;
            while (rs.next()) {
                row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(rs.getString("Nombre_Area"));
                row.createCell(1).setCellValue(rs.getString("Propuesto"));
                if (rs.getInt("Diferencia") > 0) {

                    row.createCell(2).setCellValue(rs.getString("Diferencia"));
                } else if (rs.getInt("Diferencia") < 0) {
                    row.createCell(2).setCellValue(rs.getString("Diferencia"));
                } else {
                    row.createCell(2).setCellValue(rs.getString("Diferencia"));
                }
                row.createCell(3).setCellValue(rs.getString("Plantilla"));

                if (rs.getInt("A") > 0) {
                    row.createCell(4).setCellValue(rs.getString("A"));
                } else {
                    row.createCell(4).setCellValue(rs.getString("A"));
                }
                if (rs.getInt("B") > 0) {
                    row.createCell(5).setCellValue(rs.getString("B"));
                } else {
                    row.createCell(5).setCellValue(rs.getString("B"));
                }
                if (rs.getInt("C") > 0) {
                    row.createCell(6).setCellValue(rs.getString("C"));
                } else {
                    row.createCell(6).setCellValue(rs.getString("C"));
                }
                if (rs.getInt("D") > 0) {
                    row.createCell(7).setCellValue(rs.getString("D"));
                } else {
                    row.createCell(7).setCellValue(rs.getString("D"));
                }
                if (rs.getInt("LV") > 0) {
                    row.createCell(8).setCellValue(rs.getString("LV"));
                } else {
                    row.createCell(8).setCellValue(rs.getString("LV"));
                }
            }
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue("Resúmen de todas las Áreas:");
            row.createCell(1).setCellValue("Propuesto");
            row.createCell(2).setCellValue("Diferencia");
            row.createCell(3).setCellValue("Plantilla");
            row.createCell(4).setCellValue("A");
            row.createCell(5).setCellValue("B");
            row.createCell(6).setCellValue("C");
            row.createCell(7).setCellValue("D");
            row.createCell(8).setCellValue("LV");
            row = sheet.createRow(rowIndex + 1);

            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_total");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                row.createCell(1).setCellValue(rs2.getString("Total_P"));
                if (rs2.getInt("Total_D") > 0) {
                    row.createCell(2).setCellValue(rs2.getString("Total_D"));
                } else if (rs2.getInt("Total_D") < 0) {
                    row.createCell(2).setCellValue(rs2.getString("Total_D"));
                } else {
                    row.createCell(2).setCellValue(rs2.getString("Total_D"));
                }
                row.createCell(3).setCellValue(rs2.getString("Total_Pl"));

                if (rs2.getInt("A") > 0) {
                    row.createCell(4).setCellValue(rs2.getString("A"));
                } else {
                    row.createCell(4).setCellValue(rs2.getString("A"));
                }
                if (rs2.getInt("B") > 0) {
                    row.createCell(5).setCellValue(rs2.getString("B"));
                } else {
                    row.createCell(5).setCellValue(rs2.getString("B"));
                }
                if (rs2.getInt("C") > 0) {
                    row.createCell(6).setCellValue(rs2.getString("C"));
                } else {
                    row.createCell(6).setCellValue(rs2.getString("C"));
                }
                if (rs2.getInt("D") > 0) {
                    row.createCell(7).setCellValue(rs2.getString("D"));
                } else {
                    row.createCell(7).setCellValue(rs2.getString("D"));
                }
                if (rs2.getInt("LV") > 0) {
                    row.createCell(8).setCellValue(rs2.getString("LV"));
                } else {
                    row.createCell(8).setCellValue(rs2.getString("LV"));
                }
            }
            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUResumenAreas() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Resumen de Áreas y Puestos");
        fileChooser.setSelectedFile(new File("Resumen de Áreas a " + FechaS + ".xlsx")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection(); // Crear un archivo de Excel
                 Workbook workbook = new XSSFWorkbook()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            Sheet sheet = workbook.createSheet("Áreas y Puestos");
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("Área");
            headerRow.createCell(1).setCellValue("Puesto");
            headerRow.createCell(2).setCellValue("Total");
            headerRow.createCell(3).setCellValue("Nivel");
            

            int rowNum = 1;
            while (rs.next()) {
                String idArea = rs.getString("idArea");
                String nombreArea = rs.getString("Nombre_Area");

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps1.setString(1, idArea);
                ResultSet rs1 = ps1.executeQuery();

                int startRow = rowNum; // Guardamos la primera fila del área

                while (rs1.next()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(1).setCellValue(rs1.getString("Nombre_Puesto"));
                    row.createCell(2).setCellValue(rs1.getInt("Propuesto_Trabajadores"));
                    row.createCell(3).setCellValue(rs1.getString("Nivel"));
                }

                // Fusionar celdas de la columna "Área"
                if (startRow < rowNum - 1) {
                    sheet.addMergedRegion(new CellRangeAddress(startRow, rowNum - 1, 0, 0));
                }

                // Escribir el área solo en la primera fila del grupo fusionado
                sheet.getRow(startRow).createCell(0).setCellValue(nombreArea);
            }
            
            // Autoajustar columnas
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            FileOutputStream outputStream = new FileOutputStream(rutaDoc);
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
