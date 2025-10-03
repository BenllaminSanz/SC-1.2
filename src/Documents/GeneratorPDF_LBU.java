package Documents;

import Functions.QueryFunctions;
import Functions.DateTools;
import Querys.Conexion;
import Model.LBU;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GeneratorPDF_LBU extends Conexion {

    private static int cont = 0;
    private static final Conexion conn = new Conexion();

    public static boolean Nuevos_Trabajadores(String fecha) throws SQLException {
        Document doc = new Document();
        LocalDate fechaActual = LocalDate.now();

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de Nuevos Trabajadores");
        fileChooser.setSelectedFile(new File("Lista de Personal de Nuevo Ingreso " + fecha + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter.getInstance(doc,
                    new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(30, 30, 50, 50);
            doc.open();

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(1);
            doc.add(logo);

            Font font1 = new Font();
            font1.setSize(12);
            Paragraph titulo = new Paragraph("Hilos de Yecapixtla S.A. de C.V.", font1);
            titulo.setAlignment(1);
            doc.add(titulo);

            float[] relativeWidths = {0.5F, 2F, 1.8F, 0.7F, 1.1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
            PdfPTable tabla = new PdfPTable(relativeWidths);
            tabla.setWidthPercentage(100);
            Font font = new Font();
            font.setSize(8);

            doc.add(new Paragraph("PERSONAL DE NUEVO INGRESO"));
            doc.add(new Paragraph("Fecha: " + fecha));
            doc.add(new Paragraph("\n"));

            tabla.addCell(new Phrase("NÚM. NOM", font));
            tabla.addCell(new Phrase("NOMBRE COMPLETO", font));
            tabla.addCell(new Phrase("PUESTO", font));
            tabla.addCell(new Phrase("TURNO", font));
            tabla.addCell(new Phrase("NÚM. TELÉFONO", font));
            tabla.addCell(new Phrase("LUNES \n" + DateTools.calcularFechas(1, fecha), font));
            tabla.addCell(new Phrase("MARTES \n" + DateTools.calcularFechas(2, fecha), font));
            tabla.addCell(new Phrase("MIERCOLES \n" + DateTools.calcularFechas(3, fecha), font));
            tabla.addCell(new Phrase("JUEVES \n" + DateTools.calcularFechas(4, fecha), font));
            tabla.addCell(new Phrase("VIERNES \n" + DateTools.calcularFechas(5, fecha), font));
            tabla.addCell(new Phrase("SABADO \n" + DateTools.calcularFechas(6, fecha), font));
            tabla.addCell(new Phrase("DOMINGO \n" + DateTools.calcularFechas(7, fecha), font));

            Connection con = conn.getConnection();
            PreparedStatement ps
                    = con.prepareStatement("Select Folio_Trabajador, Nombre_Trabajador, turno.Nombre_Turno, "
                            + "Teléfono_Trabajador  from trabajador inner join turno "
                            + "on trabajador.turno_idTurno=turno.idTurno where Fecha_Antiguedad = ?");
            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    tabla.addCell(new Phrase(rs.getString(1), font));
                    tabla.addCell(new Phrase(rs.getString(2), font));
                    tabla.addCell("");
                    tabla.addCell(new Phrase(rs.getString(3), font));
                    tabla.addCell(new Phrase(rs.getString(4), font));
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    cont++;
                } while (rs.next());
                doc.add(tabla);
                doc.add(new Phrase("\n"));
                doc.add(new Phrase("Firma de enterado de asignación de turno."));

                ps = con.prepareStatement("Select Nombre_Trabajador from trabajador "
                        + "inner join turno on trabajador.turno_idTurno=turno.idTurno where Fecha_Antiguedad = ?");
                ps.setDate(1, java.sql.Date.valueOf(fecha));
                rs = ps.executeQuery();

                float[] relativeWidths1 = {1F, 1F};
                PdfPTable tabla1 = new PdfPTable(relativeWidths1);
                tabla1.setWidthPercentage(50);
                tabla1.getDefaultCell().setFixedHeight(20);
                if (rs.next()) {
                    do {
                        tabla1.addCell(new Phrase(rs.getString(1), font));
                        tabla1.addCell("");
                    } while (rs.next());
                    tabla1.setHorizontalAlignment(0);
                    doc.add(tabla1);
                }
            }
            ps.close();
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en Capacitacion/NUEVO INGRESO/LISTAS DE NUEVO INGRESO " + fechaActual.getYear()
                    + " con " + cont + " trabajadores nuevos.");
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | ParseException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Bajas_Trabajadores(String fechaInicio, String fechaFin) {
        Document doc = new Document();
        LocalDate fechaActual = LocalDate.now();

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Listado de Bajas");
        fileChooser.setSelectedFile(new File("Listado de Bajas del " + fechaInicio + " al " + fechaFin + ".pdf"));
        // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter.getInstance(doc,
                    new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(50, 50, 30, 30);
            doc.open();

            float[] relativeWidths = {1F, 2F, 0.8F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
            PdfPTable tabla = new PdfPTable(relativeWidths);
            tabla.setWidthPercentage(100);
            Font font = new Font();
            font.setSize(9);

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(1);
            doc.add(logo);

            Font font1 = new Font();
            font1.setSize(12);
            Paragraph titulo = new Paragraph("Hilos de Yecapixtla S.A. de C.V.", font1);
            titulo.setAlignment(1);
            doc.add(titulo);

            doc.add(new Paragraph("LISTA DE BAJAS DEL PERIODO " + fechaInicio + " AL " + fechaFin));
            doc.add(new Paragraph("\n"));

            Connection con = conn.getConnection();
            PreparedStatement ps = null;
            String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("SELECT * FROM view_bajas "
                    + "WHERE Fecha_Baja >= ? AND Fecha_Baja <= ?");
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            PdfPTable TeBajas = DesingPDF_LBU.encabezadoBajas(font);
            doc.add(TeBajas);

            while (rs.next()) {
                PdfPTable tablaBajas = DesingPDF_LBU.tablaBajas(font, rs);
                doc.add(tablaBajas);
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en Capacitacion/BAJAS/LISTAS DE BAJAS " + fechaActual.getYear());
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    

    public static boolean LBUGeneral() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar LBU Operativo");
        fileChooser.setSelectedFile(new File("LBU Operativo " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("LBU Operativo, Área: " + rs.getString("Nombre_Area")));
                doc.add(new Paragraph("Fecha: " + FechaS));
                doc.add(new Paragraph("\n"));

                PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));");
                psql.execute();

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_lbu_puesto where Area = '" + rs.getString("Nombre_Area") + "'");
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    PdfPTable tablaPuesto = DesingPDF_LBU.tablaPuesto(font, rs1);
                    doc.add(tablaPuesto);
                    if (rs1.getInt("Plantilla") > 0) {
                        doc.add(DesingPDF_LBU.encabezadoTrabajador());

                        for (String workerType : Arrays.asList("A", "B", "C", "D", "LV")) {
                            PdfPTable tablaTrabajador = DesingPDF_LBU.tablaTrabajador(workerType);
                            ResultSet datosTrabajadorRs = DesingPDF_LBU.datosTrabajador(workerType, rs1.getString("Nombre_Puesto"), con).executeQuery();

                            while (datosTrabajadorRs.next()) {
                                tablaTrabajador.addCell(new Phrase(datosTrabajadorRs.getString("Folio_Trabajador"), font));
                                tablaTrabajador.addCell(new Phrase(datosTrabajadorRs.getString("Nombre_Trabajador"), font));
                                tablaTrabajador.addCell(new Phrase(workerType, font));
                            }
                            doc.add(tablaTrabajador);
                        }
                    }
                    PageNumber(writer, doc);
                }
                if (!(rs.getString("Nombre_Area")).equals("CAPACITACIÓN")) {
                    doc.newPage();
                } else {
                    doc.add(new Paragraph("\n"));
                    doc.add(new Phrase("Resúmen de todas las Áreas:"));
                    PdfPTable tablaSt = DesingPDF_LBU.encabezadoSupervisorTotal();
                    doc.add(tablaSt);
                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_total");
                    ResultSet rs2 = ps2.executeQuery();

                    if (rs2.next()) {
                        PdfPTable tablaEt = DesingPDF_LBU.tablaSupervisoresTotal(font, rs2);
                        doc.add(tablaEt);
                    }
                }
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUOperativoArea(LBU mod) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar LBU Operativo");
        fileChooser.setSelectedFile(new File("LBU Operativo " + mod.getNombre_Area() + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area where Nombre_Area = ?");
            ps.setString(1, mod.getNombre_Area());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("LBU Operativo, Área: " + rs.getString("Nombre_Area")));
                doc.add(new Paragraph("Fecha: " + FechaS));
                doc.add(new Paragraph("\n"));

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_lbu_puesto where Area = ?");
                ps1.setString(1, rs.getString("Nombre_Area"));
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    PdfPTable tablaPuesto = DesingPDF_LBU.tablaPuesto(font, rs1);
                    doc.add(tablaPuesto);
                    if (rs1.getInt("Plantilla") > 0) {
                        doc.add(DesingPDF_LBU.encabezadoTrabajador());

                        for (String workerType : Arrays.asList("A", "B", "C", "D", "LV")) {
                            PdfPTable tablaTrabajador = DesingPDF_LBU.tablaTrabajador(workerType);
                            ResultSet datosTrabajadorRs = DesingPDF_LBU.datosTrabajador(workerType, rs1.getString("Nombre_Puesto"), con).executeQuery();

                            while (datosTrabajadorRs.next()) {
                                tablaTrabajador.addCell(new Phrase(datosTrabajadorRs.getString("Folio_Trabajador"), font));
                                tablaTrabajador.addCell(new Phrase(datosTrabajadorRs.getString("Nombre_Trabajador"), font));
                                tablaTrabajador.addCell(new Phrase(workerType, font));
                            }
                            doc.add(tablaTrabajador);
                        }
                    }
                    PageNumber(writer, doc);
                }
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUOperativoPuesto(LBU mod) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar LBU Operativo");
        fileChooser.setSelectedFile(new File("LBU Operativo " + mod.getNombre_Area() + " "
                + mod.getNombre_Puesto() + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area where Nombre_Area = ?");
            ps.setString(1, mod.getNombre_Area());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("LBU Operativo, Área: " + rs.getString("Nombre_Area")));
                doc.add(new Paragraph("Fecha: " + FechaS));
                doc.add(new Paragraph("\n"));

                PdfPTable tablaT = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
                tablaT.setWidthPercentage(100);

                tablaT.addCell(new Phrase("A", font));
                tablaT.addCell(new Phrase("B", font));
                tablaT.addCell(new Phrase("C", font));
                tablaT.addCell(new Phrase("D", font));
                tablaT.addCell(new Phrase("LV", font));

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_lbu_puesto where Area = ? and Nombre_Puesto = ?");
                ps1.setString(1, mod.getNombre_Area());
                ps1.setString(2, mod.getNombre_Puesto());
                ResultSet rs1 = ps1.executeQuery();

                if (rs1.next()) {
                    PdfPTable tablaPuesto = DesingPDF_LBU.tablaPuesto(font, rs1);
                    doc.add(tablaPuesto);
                    if (rs1.getInt("Plantilla") > 0) {
                        doc.add(DesingPDF_LBU.encabezadoTrabajador());

                        for (String workerType : Arrays.asList("A", "B", "C", "D", "LV")) {
                            PdfPTable tablaTrabajador = DesingPDF_LBU.tablaTrabajador(workerType);
                            ResultSet datosTrabajadorRs = DesingPDF_LBU.datosTrabajador(workerType, rs1.getString("Nombre_Puesto"), con).executeQuery();

                            while (datosTrabajadorRs.next()) {
                                tablaTrabajador.addCell(new Phrase(datosTrabajadorRs.getString("Folio_Trabajador"), font));
                                tablaTrabajador.addCell(new Phrase(datosTrabajadorRs.getString("Nombre_Trabajador"), font));
                                tablaTrabajador.addCell(new Phrase(workerType, font));
                            }
                            doc.add(tablaTrabajador);
                        }
                    }
                    PageNumber(writer, doc);
                }
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUOperativoTurno(LBU mod) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("LBU Operativo");
        fileChooser.setSelectedFile(new File("LBU Operativo " + mod.getNombre_Area()
                + " " + mod.getNombre_Turno() + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area where Nombre_Area = ?");
            ps.setString(1, mod.getNombre_Area());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("LBU Operativo, Área: " + rs.getString("Nombre_Area") + " Turno: " + mod.getNombre_Turno()));
                doc.add(new Paragraph("Fecha: " + FechaS));
                doc.add(new Paragraph("\n"));

                int idTurno = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("turno", "idTurno", "Nombre_Turno", mod.getNombre_Turno()));
                PreparedStatement psV = con.prepareStatement("SET @id_turno:=?");
                psV.setInt(1, idTurno);
                psV.executeUpdate();

                int idArea = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("area", "idArea", "Nombre_Area", mod.getNombre_Area()));
                PreparedStatement psA = con.prepareStatement("SET @id_area:=?");
                psA.setInt(1, idArea);
                psA.executeUpdate();

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_puesto_turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    PdfPTable tablaPuestoTurno = DesingPDF_LBU.tablaPuestoTurno(font, rs1);
                    PdfPTable tablaE = DesingPDF_LBU.encabezadoTrabajadorTurno();
                    String turno = mod.getNombre_Turno();
                    PdfPTable tablaA = DesingPDF_LBU.tablaTrabajadorTurno();
                    ResultSet rsA = DesingPDF_LBU.datosTrabajador(turno, rs1.getString("Puesto"), con).executeQuery();
                    while (rsA.next()) {
                        tablaA.addCell(new Phrase(rsA.getString("Folio_Trabajador"), font1));
                        tablaA.addCell(new Phrase(rsA.getString("Nombre_Trabajador"), font1));
                    }
                    doc.add(tablaPuestoTurno);
                    if (rs1.getInt("Plantilla") > 0) {
                        doc.add(tablaE);
                        doc.add(tablaA);
                    }
                    PageNumber(writer, doc);
                }
                doc.newPage();
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUOperativoSelecto(LBU mod) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar LBU Operativo");
        fileChooser.setSelectedFile(new File("LBU Operativo " + mod.getNombre_Area()
                + " " + mod.getNombre_Puesto() + " Turno " + mod.getNombre_Turno() + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM area where Nombre_Area = ?");
            ps.setString(1, mod.getNombre_Area());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("LBU Operativo, Área: " + rs.getString("Nombre_Area")));
                doc.add(new Paragraph("Fecha: " + FechaS));
                doc.add(new Paragraph("\n"));

                PdfPTable tablaT = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
                tablaT.setWidthPercentage(100);

                tablaT.addCell(new Phrase("A", font));
                tablaT.addCell(new Phrase("B", font));
                tablaT.addCell(new Phrase("C", font));
                tablaT.addCell(new Phrase("D", font));
                tablaT.addCell(new Phrase("LV", font));

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM view_lbu_puesto where Area = ? AND Nombre_Puesto = ?");
                ps1.setString(1, mod.getNombre_Area());
                ps1.setString(2, mod.getNombre_Puesto());
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    PdfPTable tablaPuesto = DesingPDF_LBU.tablaPuesto(font, rs1);
                    PdfPTable tablaE = DesingPDF_LBU.encabezadoTrabajador();
                    String turno = mod.getNombre_Turno();
                    switch (turno) {
                        case "A":
                            PdfPTable tablaA = DesingPDF_LBU.tablaTrabajador("A");
                            ResultSet rsA = DesingPDF_LBU.datosTrabajador("A", rs1.getString("Nombre_Puesto"), con).executeQuery();
                            while (rsA.next()) {
                                tablaA.addCell(new Phrase(rsA.getString("Folio_Trabajador"), font1));
                                tablaA.addCell(new Phrase(rsA.getString("Nombre_Trabajador"), font1));
                                tablaA.addCell(new Phrase("A", font1));
                            }
                            doc.add(tablaPuesto);
                            doc.add(tablaE);
                            doc.add(tablaA);
                            PageNumber(writer, doc);
                            break;

                        case "B":
                            PdfPTable tablaB = DesingPDF_LBU.tablaTrabajador("B");
                            ResultSet rsB = DesingPDF_LBU.datosTrabajador("B", rs1.getString("Nombre_Puesto"), con).executeQuery();
                            while (rsB.next()) {
                                tablaB.addCell(new Phrase(rsB.getString("Folio_Trabajador"), font1));
                                tablaB.addCell(new Phrase(rsB.getString("Nombre_Trabajador"), font1));
                                tablaB.addCell(new Phrase("B", font1));
                            }
                            doc.add(tablaPuesto);
                            doc.add(tablaE);
                            doc.add(tablaB);
                            PageNumber(writer, doc);
                            break;

                        case "C":
                            PdfPTable tablaC = DesingPDF_LBU.tablaTrabajador("C");
                            ResultSet rsC = DesingPDF_LBU.datosTrabajador("C", rs1.getString("Nombre_Puesto"), con).executeQuery();
                            while (rsC.next()) {
                                tablaC.addCell(new Phrase(rsC.getString("Folio_Trabajador"), font1));
                                tablaC.addCell(new Phrase(rsC.getString("Nombre_Trabajador"), font1));
                                tablaC.addCell(new Phrase("C", font1));
                            }
                            doc.add(tablaPuesto);
                            doc.add(tablaE);
                            doc.add(tablaC);
                            PageNumber(writer, doc);
                            break;
                        case "D":
                            PdfPTable tablaD = DesingPDF_LBU.tablaTrabajador("D");
                            ResultSet rsD = DesingPDF_LBU.datosTrabajador("D", rs1.getString("Nombre_Puesto"), con).executeQuery();
                            while (rsD.next()) {
                                tablaD.addCell(new Phrase(rsD.getString("Folio_Trabajador"), font1));
                                tablaD.addCell(new Phrase(rsD.getString("Nombre_Trabajador"), font1));
                                tablaD.addCell(new Phrase("D", font1));
                            }
                            doc.add(tablaPuesto);
                            doc.add(tablaE);
                            doc.add(tablaD);
                            PageNumber(writer, doc);
                            break;
                        case "LV":
                            PdfPTable tablaLV = DesingPDF_LBU.tablaTrabajador("LV");
                            ResultSet rsLV = DesingPDF_LBU.datosTrabajador("LV", rs1.getString("Nombre_Puesto"), con).executeQuery();
                            while (rsLV.next()) {
                                tablaLV.addCell(new Phrase(rsLV.getString("Folio_Trabajador"), font1));
                                tablaLV.addCell(new Phrase(rsLV.getString("Nombre_Trabajador"), font1));
                                tablaLV.addCell(new Phrase("LV", font1));
                            }
                            doc.add(tablaPuesto);
                            doc.add(tablaE);
                            doc.add(tablaLV);
                            PageNumber(writer, doc);
                            break;
                        default:
                            break;
                    }
                }
                doc.newPage();
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUOperativoSupervisor(LBU mod) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar LBU Operativo por Supervisor");
        fileChooser.setSelectedFile(new File("LBU Operativo " + mod.getNombre_Supervisor() + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(10);
            Font font1 = new Font();
            font1.setSize(8);

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("LBU Operativo Supervisor: " + mod.getNombre_Supervisor()));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_supervisor where Nombre_Supervisor = ?");
            ps.setString(1, mod.getNombre_Supervisor());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                doc.add(new Phrase("Área: " + rs.getString("Nombre_Area") + ", Turno: " + rs.getString("Nombre_Turno"), font));

                PreparedStatement psV = con.prepareStatement("SET @id_turno:=?");
                psV.setInt(1, rs.getInt("idTurno"));
                psV.executeUpdate();

                PreparedStatement psA = con.prepareStatement("SET @id_area:=?");
                psA.setInt(1, rs.getInt("idArea"));
                psA.executeUpdate();

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_puesto_turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    PdfPTable tablaPuestoTurno = DesingPDF_LBU.tablaPuestoTurnoS(font, rs1);
                    PdfPTable tablaE = DesingPDF_LBU.encabezadoTrabajadorTurno();
                    String turno = rs.getString("Nombre_Turno");
                    PdfPTable tablaA = DesingPDF_LBU.tablaTrabajadorTurno();
                    ResultSet rsA = DesingPDF_LBU.datosTrabajador(turno, rs1.getString("Puesto"), con).executeQuery();
                    while (rsA.next()) {
                        tablaA.addCell(new Phrase(rsA.getString("Folio_Trabajador"), font));
                        tablaA.addCell(new Phrase(rsA.getString("Nombre_Trabajador"), font));
                    }

                    if (rs1.getInt("Plantilla") > 0) {
                        doc.add(tablaPuestoTurno);
                        doc.add(tablaE);
                        doc.add(tablaA);
                    }
                    PageNumber(writer, doc);
                }
            }
            doc.add(new Phrase("Resúmen de todas los Puestos del Supervisor:"));

            PdfPTable tablaSt = DesingPDF_LBU.encabezadoSupervisorTotal();
            doc.add(tablaSt);

            PreparedStatement ps2 = con.prepareStatement(
                    "SELECT * FROM sistema_capacitacion.view_lbu_resumen WHERE Supervisor = ?");
            ps2.setString(1, mod.getNombre_Supervisor());
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                PdfPTable tablaEt = DesingPDF_LBU.tablaSupervisores(font, rs2);
                doc.add(tablaEt);
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean LBUResumenSupervisor() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar LBU Resumen de Supervisores");
        fileChooser.setSelectedFile(new File("LBU Resumen de Supervisores " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_resumen");
            ResultSet rs = ps.executeQuery();

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("LBU Operativo Resúmen de Supervisores"));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            PdfPTable tablaE = DesingPDF_LBU.encabezadoSupervisor();
            doc.add(tablaE);
            while (rs.next()) {
                PdfPTable tableS = DesingPDF_LBU.tablaSupervisoresResumen(font, rs);
                doc.add(tableS);
            }
            doc.add(new Phrase("Resúmen de todas las Áreas:"));

            PdfPTable tablaSt = DesingPDF_LBU.encabezadoSupervisorTotal();
            doc.add(tablaSt);

            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_lbu_total");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                PdfPTable tablaEt = DesingPDF_LBU.tablaSupervisoresTotal(font, rs2);
                doc.add(tablaEt);
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado Exitosamente");
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void PageNumber(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Phrase pageNumber = new Phrase("Página " + writer.getPageNumber());
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, pageNumber,
                (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
    }

}
