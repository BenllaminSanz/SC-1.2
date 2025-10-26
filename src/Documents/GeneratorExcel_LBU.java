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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
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
            headerRow.createCell(1).setCellValue("Centro de Costo");
            headerRow.createCell(2).setCellValue("Puesto");
            headerRow.createCell(3).setCellValue("Position");
            headerRow.createCell(4).setCellValue("Nivel");
            headerRow.createCell(5).setCellValue("Propuesto");
            headerRow.createCell(6).setCellValue("Diferencia");
            headerRow.createCell(7).setCellValue("Actual");
            headerRow.createCell(8).setCellValue("Propuesto A");
            headerRow.createCell(9).setCellValue("Diferencia A");
            headerRow.createCell(10).setCellValue("Actual A");
            headerRow.createCell(11).setCellValue("Propuesto B");
            headerRow.createCell(12).setCellValue("Diferencia B");
            headerRow.createCell(13).setCellValue("Actual B");
            headerRow.createCell(14).setCellValue("Propuesto C");
            headerRow.createCell(15).setCellValue("Diferencia C");
            headerRow.createCell(16).setCellValue("Actual C");
            headerRow.createCell(17).setCellValue("Propuesto D");
            headerRow.createCell(18).setCellValue("Diferencia D");
            headerRow.createCell(19).setCellValue("Actual D");
            headerRow.createCell(20).setCellValue("Propuesto LV");
            headerRow.createCell(21).setCellValue("Diferencia LV");
            headerRow.createCell(22).setCellValue("Actual LV");
            headerRow.createCell(23).setCellValue("Total Area");
            headerRow.createCell(24).setCellValue("Diferencia Area");
            headerRow.createCell(25).setCellValue("Plantilla Area");

            int totalGeneral = 0;
            int totalResta = 0;
            int totalPlantilla = 0;

            int rowNum = 1;
            while (rs.next()) {
                String idArea = rs.getString("idArea");
                String nombreArea = rs.getString("Nombre_Area");

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps1.setString(1, idArea);
                ResultSet rs1 = ps1.executeQuery();

                int totalArea = 0;
                int startRow = rowNum; // Guardamos la primera fila del área

                String idPuesto = null;

                while (rs1.next()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(nombreArea);
                    row.createCell(1).setCellValue(rs1.getString("Centro_de_Costo"));
                    row.createCell(2).setCellValue(rs1.getString("Nombre_Puesto"));
                    row.createCell(3).setCellValue(rs1.getString("Nombre_Puesto_Ingles"));
                    row.createCell(4).setCellValue(rs1.getString("Nivel"));
                    row.createCell(5).setCellValue(rs1.getInt("Propuesto_Trabajadores"));
                    totalArea = totalArea + rs1.getInt("Propuesto_Trabajadores");

                    idPuesto = rs1.getString("idPuesto");
                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM view_lbu_puesto WHERE idPuesto = ?");
                    ps2.setString(1, idPuesto);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        row.createCell(6).setCellValue(rs2.getInt("Diferencia"));
                        row.createCell(7).setCellValue(rs2.getInt("Plantilla"));
                        row.createCell(10).setCellValue(rs2.getInt("A"));
                        row.createCell(13).setCellValue(rs2.getInt("B"));
                        row.createCell(16).setCellValue(rs2.getInt("C"));
                        row.createCell(19).setCellValue(rs2.getInt("D"));
                        row.createCell(22).setCellValue(rs2.getInt("LV"));
                    }
                    PreparedStatement ps4 = con.prepareStatement("SELECT * FROM turno_puesto WHERE puesto_idPuesto = ?");
                    ps4.setString(1, idPuesto);
                    ResultSet rs4 = ps4.executeQuery();
                    while (rs4.next()) {
                        Cell diferenciaCell = null;
                        switch (rs4.getInt("turno_idTurno")) {
                            case 1:
                                row.createCell(8).setCellValue(rs4.getInt("Propuesto"));
                                diferenciaCell = row.createCell(9);
                                diferenciaCell.setCellFormula("I" + (rowNum) + "-K" + (rowNum));

                                break;
                            case 2:
                                row.createCell(11).setCellValue(rs4.getInt("Propuesto"));
                                diferenciaCell = row.createCell(12);
                                diferenciaCell.setCellFormula("L" + (rowNum) + "-N" + (rowNum));
                                break;
                            case 3:
                                row.createCell(14).setCellValue(rs4.getInt("Propuesto"));
                                diferenciaCell = row.createCell(15);
                                diferenciaCell.setCellFormula("O" + (rowNum) + "-Q" + (rowNum));
                                break;
                            case 4:
                                row.createCell(17).setCellValue(rs4.getInt("Propuesto"));
                                diferenciaCell = row.createCell(18);
                                diferenciaCell.setCellFormula("R" + (rowNum) + "-T" + (rowNum));
                                break;
                            case 5:
                                row.createCell(20).setCellValue(rs4.getInt("Propuesto"));
                                diferenciaCell = row.createCell(21);
                                diferenciaCell.setCellFormula("U" + (rowNum) + "-W" + (rowNum));
                                break;
                            default:
                                row.createCell(20).setCellValue("0");
                                break;
                        }
                    }

                }

                PreparedStatement ps3 = con.prepareStatement("SELECT * FROM view_lbu_puesto WHERE Area= ?");
                ps3.setString(1, nombreArea);
                ResultSet rs3 = ps3.executeQuery();

                int totalReal = 0;
                int totalDiferencia = 0;
                while (rs3.next()) {
                    totalReal = totalReal + rs3.getInt("Diferencia");
                    totalDiferencia = totalDiferencia + rs3.getInt("Plantilla");
                }

                sheet.getRow(startRow).createCell(23).setCellValue(totalArea);
                sheet.getRow(startRow).createCell(24).setCellValue(totalReal);
                sheet.getRow(startRow).createCell(25).setCellValue(totalDiferencia);

                totalGeneral = totalGeneral + totalArea;
                totalResta = totalResta + totalReal;
                totalPlantilla = totalPlantilla + totalDiferencia;
            }

            // Crear una fila al final para mostrar el total general
            Row totalRow = sheet.createRow(rowNum);
            totalRow.createCell(23).setCellValue(totalGeneral);
            totalRow.createCell(24).setCellValue(totalResta);
            totalRow.createCell(25).setCellValue(totalPlantilla);

            // Autoajustar columnas
            for (int i = 0; i <= 25; i++) {
                sheet.autoSizeColumn(i);
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

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUResumenAreasTecnologias() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Resumen Tecnologias");
        fileChooser.setSelectedFile(new File("Resumen de Tecnologias " + FechaS + ".xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }
        String rutaDoc = fileChooser.getSelectedFile().getAbsolutePath();

        try (Connection con = conn.getConnection(); Workbook workbook = new XSSFWorkbook()) {

            // ============================================================
            // ESTILOS
            // ============================================================
            CellStyle centeredStyle = workbook.createCellStyle();
            centeredStyle.setAlignment(HorizontalAlignment.CENTER);
            centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            DataFormat format = workbook.createDataFormat();
            centeredStyle.setDataFormat(format.getFormat("0")); // formato numérico sin decimales

            // --- Estilo para porcentajes centrados ---
            CellStyle percentStyle = workbook.createCellStyle();
            percentStyle.setAlignment(HorizontalAlignment.CENTER);
            percentStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            DataFormat format2 = workbook.createDataFormat();
            percentStyle.setDataFormat(format2.getFormat("0.00%"));

            // ============================================================
            // HOJA 1: Resumen Completo
            // ============================================================
            Sheet sheet = workbook.createSheet("Resumen Completo");
            int rowNum = 0;

            Row header = sheet.createRow(rowNum++);
            header.createCell(0).setCellValue("Área");
            header.createCell(1).setCellValue("Puesto");
            header.createCell(2).setCellValue("Position");
            header.createCell(3).setCellValue("Nivel");
            header.createCell(4).setCellValue("Propuesto");
            header.createCell(5).setCellValue("Diferencia");
            header.createCell(6).setCellValue("Actual");
            header.createCell(7).setCellValue("OE");
            header.createCell(8).setCellValue("RS");
            header.createCell(9).setCellValue("POLYCOTTON");

            Map<String, Map<String, Integer>> tabla1 = new LinkedHashMap<>();
            Map<Integer, Set<Integer>> tecnologiasPorPuesto = new HashMap<>();

            PreparedStatement psAreas = con.prepareStatement("SELECT * FROM area ORDER BY idArea");
            ResultSet rsAreas = psAreas.executeQuery();

            List<String> ordenAreas = new ArrayList<>();

            while (rsAreas.next()) {
                String idArea = rsAreas.getString("idArea");
                String nombreArea = rsAreas.getString("Nombre_Area");
                ordenAreas.add(nombreArea);

                PreparedStatement psPuestos = con.prepareStatement(
                        "SELECT * FROM puesto WHERE area_idArea = ? ORDER BY idPuesto");
                psPuestos.setString(1, idArea);
                ResultSet rsPuestos = psPuestos.executeQuery();

                while (rsPuestos.next()) {
                    int idPuesto = rsPuestos.getInt("idPuesto");
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(nombreArea);
                    row.createCell(1).setCellValue(rsPuestos.getString("Nombre_Puesto"));
                    row.createCell(2).setCellValue(rsPuestos.getString("Nombre_Puesto_Ingles"));
                    row.createCell(3).setCellValue(rsPuestos.getString("Nivel"));

                    int propuesto = rsPuestos.getInt("Propuesto_Trabajadores");
                    Cell cellPropuesto = row.createCell(4);
                    cellPropuesto.setCellValue(propuesto);
                    cellPropuesto.setCellStyle(centeredStyle);

                    PreparedStatement psView = con.prepareStatement(
                            "SELECT Diferencia, Plantilla FROM view_lbu_puesto WHERE idPuesto = ?");
                    psView.setInt(1, idPuesto);
                    ResultSet rsView = psView.executeQuery();
                    int diferencia = 0;
                    int actual = 0;
                    if (rsView.next()) {
                        diferencia = rsView.getInt("Diferencia");
                        actual = rsView.getInt("Plantilla");
                    }

                    Cell cellDif = row.createCell(5);
                    cellDif.setCellValue(diferencia);
                    cellDif.setCellStyle(centeredStyle);

                    Cell cellAct = row.createCell(6);
                    cellAct.setCellValue(actual);
                    cellAct.setCellStyle(centeredStyle);

                    PreparedStatement psTecno = con.prepareStatement(
                            "SELECT idTecnologia FROM puesto_tecnologia WHERE idPuesto = ?");
                    psTecno.setInt(1, idPuesto);
                    ResultSet rsTecno = psTecno.executeQuery();
                    Set<Integer> tecSet = new HashSet<>();
                    while (rsTecno.next()) {
                        tecSet.add(rsTecno.getInt("idTecnologia"));
                    }
                    tecnologiasPorPuesto.put(idPuesto, tecSet);

                    Cell cellOE = row.createCell(7);
                    cellOE.setCellValue(tecSet.contains(1) ? 1 : 0);
                    cellOE.setCellStyle(centeredStyle);

                    Cell cellRS = row.createCell(8);
                    cellRS.setCellValue(tecSet.contains(2) ? 1 : 0);
                    cellRS.setCellStyle(centeredStyle);

                    Cell cellPOLY = row.createCell(9);
                    cellPOLY.setCellValue(tecSet.contains(3) ? 1 : 0);
                    cellPOLY.setCellStyle(centeredStyle);

                    String comboTec;
                    if (tecSet.contains(1) && !tecSet.contains(2) && !tecSet.contains(3)) {
                        comboTec = "OE";
                    } else if (!tecSet.contains(1) && tecSet.contains(2) && !tecSet.contains(3)) {
                        comboTec = "RS";
                    } else if (!tecSet.contains(1) && !tecSet.contains(2) && tecSet.contains(3)) {
                        comboTec = "POLY";
                    } else if (tecSet.contains(1) && tecSet.contains(2) && !tecSet.contains(3)) {
                        comboTec = "OE-RS";
                    } else if (tecSet.contains(1) && !tecSet.contains(2) && tecSet.contains(3)) {
                        comboTec = "OE-POLY";
                    } else if (!tecSet.contains(1) && tecSet.contains(2) && tecSet.contains(3)) {
                        comboTec = "RS-POLY";
                    } else if (tecSet.contains(1) && tecSet.contains(2) && tecSet.contains(3)) {
                        comboTec = "OE-RS-POLY";
                    } else {
                        comboTec = "Capacitación";
                    }

                    Map<String, Integer> inner = tabla1.getOrDefault(comboTec, new LinkedHashMap<>());
                    inner.put(nombreArea, inner.getOrDefault(nombreArea, 0) + actual);
                    tabla1.put(comboTec, inner);
                }
            }

            rowNum += 2;

            Row header2 = sheet.createRow(rowNum++);
            header2.createCell(0).setCellValue("Combinación/Área");

            for (int i = 0; i < ordenAreas.size(); i++) {
                header2.createCell(i + 1).setCellValue(ordenAreas.get(i));
            }
            header2.createCell(ordenAreas.size() + 1).setCellValue("TOTAL");

            List<String> combosOrden = Arrays.asList(
                    "OE", "RS", "POLY", "OE-RS", "OE-POLY", "RS-POLY", "OE-RS-POLY", "Capacitación"
            );

            for (String combo : combosOrden) {
                Map<String, Integer> inner = tabla1.getOrDefault(combo, new LinkedHashMap<>());
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(combo);
                int totalFila = 0;
                for (int i = 0; i < ordenAreas.size(); i++) {
                    int val = inner.getOrDefault(ordenAreas.get(i), 0);
                    Cell cell = row.createCell(i + 1);
                    cell.setCellValue(val);
                    cell.setCellStyle(centeredStyle);
                    totalFila += val;
                }
                Cell totalCell = row.createCell(ordenAreas.size() + 1);
                totalCell.setCellValue(totalFila);
                totalCell.setCellStyle(centeredStyle);
            }

            Row totalGen = sheet.createRow(rowNum++);
            totalGen.createCell(0).setCellValue("TOTAL GENERAL");
            int sumaTotal = 0;
            for (int i = 0; i < ordenAreas.size(); i++) {
                int colSum = 0;
                for (String combo : combosOrden) {
                    colSum += tabla1.getOrDefault(combo, new LinkedHashMap<>())
                            .getOrDefault(ordenAreas.get(i), 0);
                }
                Cell cell = totalGen.createCell(i + 1);
                cell.setCellValue(colSum);
                cell.setCellStyle(centeredStyle);
                sumaTotal += colSum;
            }
            Cell totalCell = totalGen.createCell(ordenAreas.size() + 1);
            totalCell.setCellValue(sumaTotal);
            totalCell.setCellStyle(centeredStyle);

            for (int i = 0; i <= 30; i++) {
                sheet.autoSizeColumn(i);
            }

            // ============================================================
            // HOJA 2: Resumen Agrupado
            // ============================================================
            Sheet resumen2 = workbook.createSheet("Summit LBU Summary");
            workbook.setSheetOrder("Summit LBU Summary", 0);
            workbook.setActiveSheet(0);

            int rowNum2 = 0;

            Row h2 = resumen2.createRow(rowNum2++);
            h2.createCell(0).setCellValue("Grupo / Área");
            for (int i = 0; i < ordenAreas.size(); i++) {
                h2.createCell(i + 1).setCellValue(ordenAreas.get(i));
            }
            h2.createCell(ordenAreas.size() + 1).setCellValue("TOTAL");
            h2.createCell(ordenAreas.size() + 2).setCellValue("TOTAL GRUPO");

            List<String> grupo1 = Arrays.asList("OE", "RS", "POLY");
            int totalGrupo1 = 0;
            for (String combo : grupo1) {
                Map<String, Integer> inner = tabla1.getOrDefault(combo, new LinkedHashMap<>());
                Row row = resumen2.createRow(rowNum2++);
                row.createCell(0).setCellValue(combo);
                int subtotal = 0;
                for (int i = 0; i < ordenAreas.size(); i++) {
                    int val = inner.getOrDefault(ordenAreas.get(i), 0);
                    Cell cell = row.createCell(i + 1);
                    cell.setCellValue(val);
                    cell.setCellStyle(centeredStyle);
                    subtotal += val;
                }
                Cell cellTotal = row.createCell(ordenAreas.size() + 1);
                cellTotal.setCellValue(subtotal);
                cellTotal.setCellStyle(centeredStyle);
                totalGrupo1 += subtotal;
            }

            Map<String, Integer> otros = tabla1.getOrDefault("Capacitación", new LinkedHashMap<>());
            Row rowOtros = resumen2.createRow(rowNum2++);
            rowOtros.createCell(0).setCellValue("Capacitación");
            int subtotal = 0;
            for (int i = 0; i < ordenAreas.size(); i++) {
                int val = otros.getOrDefault(ordenAreas.get(i), 0);
                Cell cell = rowOtros.createCell(i + 1);
                cell.setCellValue(val);
                cell.setCellStyle(centeredStyle);
                subtotal += val;
            }

            Row row4 = resumen2.createRow(rowNum2++);
            Cell grupoCell = row4.createCell(ordenAreas.size() + 2);
            grupoCell.setCellValue(totalGrupo1);
            grupoCell.setCellStyle(centeredStyle);

            double porcentajeOEPOLY = ((tabla1.getOrDefault("OE", Collections.emptyMap()).values().stream().mapToInt(Integer::intValue).sum()
                    + tabla1.getOrDefault("POLY", Collections.emptyMap()).values().stream().mapToInt(Integer::intValue).sum())
                    / (double) totalGrupo1) * 100;

            double porcentajeRS = (tabla1.getOrDefault("RS", Collections.emptyMap()).values().stream().mapToInt(Integer::intValue).sum()
                    / (double) totalGrupo1) * 100;

            Cell subtotalCell = rowOtros.createCell(ordenAreas.size() + 1);
            subtotalCell.setCellValue(subtotal);
            subtotalCell.setCellStyle(centeredStyle);

            Cell cellOE = resumen2.createRow(rowNum2++).createCell(ordenAreas.size() + 1);
            cellOE.setCellValue("OE-POLY:");
            Cell valOE = resumen2.getRow(rowNum2 - 1).createCell(ordenAreas.size() + 2);
            valOE.setCellValue(porcentajeOEPOLY / 100.0);
            valOE.setCellStyle(percentStyle);

            Cell cellRS = resumen2.createRow(rowNum2++).createCell(ordenAreas.size() + 1);
            cellRS.setCellValue("RS:");
            Cell valRS = resumen2.getRow(rowNum2 - 1).createCell(ordenAreas.size() + 2);
            valRS.setCellValue(porcentajeRS / 100.0);
            valRS.setCellStyle(percentStyle);

            List<String> grupo2 = Arrays.asList("OE-RS", "OE-RS-POLY");
            int totalGrupo2 = 0;
            for (String combo : grupo2) {
                Map<String, Integer> inner = tabla1.getOrDefault(combo, new LinkedHashMap<>());
                Row row = resumen2.createRow(rowNum2++);
                row.createCell(0).setCellValue(combo);
                int subtotal2 = 0;
                for (int i = 0; i < ordenAreas.size(); i++) {
                    int val = inner.getOrDefault(ordenAreas.get(i), 0);
                    Cell cell = row.createCell(i + 1);
                    cell.setCellValue(val);
                    cell.setCellStyle(centeredStyle);
                    subtotal2 += val;
                }
                Cell cellTotal2 = row.createCell(ordenAreas.size() + 1);
                cellTotal2.setCellValue(subtotal2);
                cellTotal2.setCellStyle(centeredStyle);
                totalGrupo2 += subtotal2;
            }

            Map<String, Integer> otros2 = tabla1.getOrDefault("Capacitación", new LinkedHashMap<>());
            Row rowOtros2 = resumen2.createRow(rowNum2++);
            rowOtros2.createCell(0).setCellValue("Capacitación");
            int subtotal2 = 0;
            for (int i = 0; i < ordenAreas.size(); i++) {
                int val = otros2.getOrDefault(ordenAreas.get(i), 0);
                Cell cell = rowOtros2.createCell(i + 1);
                cell.setCellValue(val);
                cell.setCellStyle(centeredStyle);
                subtotal2 += val;
            }

            Cell cellTotal2 = rowOtros2.createCell(ordenAreas.size() + 1);
            cellTotal2.setCellValue(subtotal2);
            cellTotal2.setCellStyle(centeredStyle);
            totalGrupo2 += subtotal2;

            Row row5 = resumen2.createRow(rowNum2++);
            Cell grupoCell2 = row5.createCell(ordenAreas.size() + 2);
            grupoCell2.setCellValue(totalGrupo2);
            grupoCell2.setCellStyle(centeredStyle);

            Row totalFinal = resumen2.createRow(rowNum2++);
            totalFinal.createCell(ordenAreas.size() + 1).setCellValue("TOTAL GENERAL");
            Cell totalGeneralCell = totalFinal.createCell(ordenAreas.size() + 2);
            totalGeneralCell.setCellValue(totalGrupo1 + totalGrupo2);
            totalGeneralCell.setCellStyle(centeredStyle);

            for (int i = 0; i <= 30; i++) {
                resumen2.autoSizeColumn(i);
            }

            // ============================================================
            // GUARDAR ARCHIVO
            // ============================================================
            try (FileOutputStream out = new FileOutputStream(rutaDoc)) {
                workbook.write(out);
            }

            JOptionPane.showMessageDialog(null, "Archivo creado en: " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(rutaDoc));
            }

            return true;

        } catch (SQLException | IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar el archivo: " + ex.getMessage());
            return false;
        }
    }
}
