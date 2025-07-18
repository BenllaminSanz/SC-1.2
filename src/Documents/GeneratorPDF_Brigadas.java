package Documents;

import Querys.Conexion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GeneratorPDF_Brigadas extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean listaEmergencia() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Elegir ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista de Emergencias");
        fileChooser.setSelectedFile(new File("Lista General de Emergencia " + FechaS + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        String rutaDoc = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            Connection con = conn.getConnection();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);

            // === Generar tablas por área y turno ===
            PreparedStatement psArea = con.prepareStatement("SELECT * FROM area");
            ResultSet rsArea = psArea.executeQuery();

            while (rsArea.next()) {
                int tipoProceso = rsArea.getInt("tipo_proceso");

                if (tipoProceso == 2) {
                    PreparedStatement psTrab = con.prepareStatement(
                            "SELECT * FROM sistema_capacitacion.view_trabajador vt "
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                            + "WHERE nombre_Area = ?"
                    );
                    psTrab.setString(1, rsArea.getString("Nombre_Area"));

                    ResultSet rsTrab = psTrab.executeQuery();
                    int cont = 0;

                    float[] relativeWidths = {0.5F, 2F, 2F, 0.7F};
                    PdfPTable tabla = new PdfPTable(relativeWidths);
                    tabla.setWidthPercentage(100);

                    BaseColor color = new BaseColor(175, 196, 174);
                    Font fontBold = new Font();
                    fontBold.setStyle(Font.BOLD);
                    fontBold.setSize(10);

                    tabla.addCell(createHeaderCell("NÚM. NOM", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("NOMBRE COMPLETO", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("BRIGADISTA", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("ASISTENCIA", fontBold, color, 1));

                    while (rsTrab.next()) {
                        tabla.addCell(new Phrase(rsTrab.getString(1), font));
                        tabla.addCell(new Phrase(rsTrab.getString(2), font));
                        tabla.addCell(new Phrase(rsTrab.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }

                    if (cont > 0) {
                        doc.add(logo);
                        doc.add(new Paragraph("Lista de Emergencia, Área: " + rsArea.getString("Nombre_Area")));
                        doc.add(new Paragraph("Fecha: " + FechaS));
                        doc.add(new Paragraph("\n"));
                        doc.add(tabla);
                        doc.add(new Paragraph("\n"));

                        PdfPTable total = new PdfPTable(1);
                        total.setWidthPercentage(30);
                        PdfPCell cell = new PdfPCell();
                        Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                        cell.addElement(paragraph);
                        total.addCell(cell);
                        total.setHorizontalAlignment(0);
                        doc.add(total);
                        doc.newPage();
                    }

                } else {
                    PreparedStatement psTurno = con.prepareStatement("SELECT * FROM turno");
                    ResultSet rsTurno = psTurno.executeQuery();
                    while (rsTurno.next()) {
                        PreparedStatement psTrab = con.prepareStatement(
                                "SELECT * FROM sistema_capacitacion.view_trabajador vt "
                                + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                                + "WHERE nombre_Area = ? AND nombre_turno = ?"
                        );
                        psTrab.setString(1, rsArea.getString("Nombre_Area"));
                        psTrab.setString(2, rsTurno.getString("Nombre_Turno"));

                        ResultSet rsTrab = psTrab.executeQuery();
                        int cont = 0;

                        float[] relativeWidths = {0.5F, 2F, 2F, 0.7F};
                        PdfPTable tabla = new PdfPTable(relativeWidths);
                        tabla.setWidthPercentage(100);

                        BaseColor color = new BaseColor(175, 196, 174);
                        Font fontBold = new Font();
                        fontBold.setStyle(Font.BOLD);
                        fontBold.setSize(10);

                        tabla.addCell(createHeaderCell("NÚM. NOM", fontBold, color, 1));
                        tabla.addCell(createHeaderCell("NOMBRE COMPLETO", fontBold, color, 1));
                        tabla.addCell(createHeaderCell("BRIGADISTA", fontBold, color, 1));
                        tabla.addCell(createHeaderCell("ASISTENCIA", fontBold, color, 1));

                        while (rsTrab.next()) {
                            tabla.addCell(new Phrase(rsTrab.getString(1), font));
                            tabla.addCell(new Phrase(rsTrab.getString(2), font));
                            tabla.addCell(new Phrase(rsTrab.getString("nombre_brigada"), font));
                            tabla.addCell("");
                            cont++;
                        }

                        if (cont > 0) {
                            doc.add(logo);
                            doc.add(new Paragraph("Lista de Emergencia, Área: " + rsArea.getString("Nombre_Area")
                                    + ", Turno: " + rsTurno.getString("Nombre_Turno")));
                            doc.add(new Paragraph("Fecha: " + FechaS));
                            doc.add(new Paragraph("\n"));
                            doc.add(tabla);
                            doc.add(new Paragraph("\n"));

                            PdfPTable total = new PdfPTable(1);
                            total.setWidthPercentage(30);
                            PdfPCell cell = new PdfPCell();
                            Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                            paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                            cell.addElement(paragraph);
                            total.addCell(cell);
                            total.setHorizontalAlignment(0);
                            doc.add(total);
                            doc.newPage();
                        }
                    }
                }
            }

            // === Generar tablas por administrativos ===
            PreparedStatement psAdminArea = con.prepareStatement(
                    "SELECT area_administrativo FROM sistema_capacitacion.administrativos GROUP BY area_administrativo");
            ResultSet rsAdminArea = psAdminArea.executeQuery();

            while (rsAdminArea.next()) {
                float[] relativeWidths = {0.5F, 2F, 2F, 0.7F};
                PdfPTable tabla = new PdfPTable(relativeWidths);
                tabla.setWidthPercentage(100);

                BaseColor color = new BaseColor(175, 196, 174);
                Font fontBold = new Font();
                fontBold.setStyle(Font.BOLD);
                fontBold.setSize(10);

                tabla.addCell(createHeaderCell("NÚM. NOM", fontBold, color, 1));
                tabla.addCell(createHeaderCell("NOMBRE COMPLETO", fontBold, color, 1));
                tabla.addCell(createHeaderCell("BRIGADISTA", fontBold, color, 1));
                tabla.addCell(createHeaderCell("ASISTENCIA", fontBold, color, 1));

                doc.add(logo);
                doc.add(new Paragraph("Lista de Emergencia, " + rsAdminArea.getString("area_administrativo")));
                doc.add(new Paragraph("Fecha: " + FechaS));
                doc.add(new Paragraph("\n"));

                PreparedStatement psTurnoAdmin = con.prepareStatement(
                        "SELECT turno FROM sistema_capacitacion.administrativos GROUP BY turno ORDER BY turno");
                ResultSet rsTurnoAdmin = psTurnoAdmin.executeQuery();
                int cont = 0;

                while (rsTurnoAdmin.next()) {

                    PreparedStatement psTrabAdmin = con.prepareStatement(
                            "SELECT * FROM sistema_capacitacion.administrativos vt "
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                            + "WHERE area_administrativo = ? AND turno = ?"
                    );
                    psTrabAdmin.setString(1, rsAdminArea.getString("area_administrativo"));
                    psTrabAdmin.setString(2, rsTurnoAdmin.getString("turno"));
                    ResultSet rsTrabAdmin = psTrabAdmin.executeQuery();

                    while (rsTrabAdmin.next()) {
                        tabla.addCell(new Phrase(rsTrabAdmin.getString(1), font));
                        tabla.addCell(new Phrase(rsTrabAdmin.getString(2), font));
                        tabla.addCell(new Phrase(rsTrabAdmin.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }
                }

                if (cont > 0) {
                    doc.add(tabla);
                    doc.add(new Paragraph("\n"));

                    PdfPTable total = new PdfPTable(1);
                    total.setWidthPercentage(30);
                    PdfPCell cell = new PdfPCell();
                    Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                    paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                    cell.addElement(paragraph);
                    total.addCell(cell);
                    total.setHorizontalAlignment(0);
                    doc.add(total);
                    doc.newPage();
                }
            }

            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);

            // Abrir el archivo
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }

            return true;

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean listaEmergenciaTurno() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Elegir ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista de Emergencias");
        fileChooser.setSelectedFile(new File("Lista General de Emergencia " + FechaS + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        String rutaDoc = fileChooser.getSelectedFile().getAbsolutePath();

        try {
            Connection con = conn.getConnection();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);

            // === Generar tablas por área y turno ===
            PreparedStatement psArea = con.prepareStatement("SELECT * FROM area");
            ResultSet rsArea = psArea.executeQuery();

            while (rsArea.next()) {
                int tipoProceso = rsArea.getInt("tipo_proceso");

                PreparedStatement psTurno = con.prepareStatement("SELECT * FROM turno");
                ResultSet rsTurno = psTurno.executeQuery();
                while (rsTurno.next()) {
                    PreparedStatement psTrab = con.prepareStatement(
                            "SELECT * FROM sistema_capacitacion.view_trabajador vt "
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                            + "WHERE nombre_Area = ? AND nombre_turno = ?"
                    );
                    psTrab.setString(1, rsArea.getString("Nombre_Area"));
                    psTrab.setString(2, rsTurno.getString("Nombre_Turno"));

                    ResultSet rsTrab = psTrab.executeQuery();
                    int cont = 0;

                    float[] relativeWidths = {0.5F, 2F, 2F, 0.7F};
                    PdfPTable tabla = new PdfPTable(relativeWidths);
                    tabla.setWidthPercentage(100);

                    BaseColor color = new BaseColor(175, 196, 174);
                    Font fontBold = new Font();
                    fontBold.setStyle(Font.BOLD);
                    fontBold.setSize(10);

                    tabla.addCell(createHeaderCell("NÚM. NOM", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("NOMBRE COMPLETO", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("BRIGADISTA", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("ASISTENCIA", fontBold, color, 1));

                    while (rsTrab.next()) {
                        tabla.addCell(new Phrase(rsTrab.getString(1), font));
                        tabla.addCell(new Phrase(rsTrab.getString(2), font));
                        tabla.addCell(new Phrase(rsTrab.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }

                    if (cont > 0) {
                        doc.add(logo);
                        doc.add(new Paragraph("Lista de Emergencia, Área: " + rsArea.getString("Nombre_Area")
                                + ", Turno: " + rsTurno.getString("Nombre_Turno")));
                        doc.add(new Paragraph("Fecha: " + FechaS));
                        doc.add(new Paragraph("\n"));
                        doc.add(tabla);
                        doc.add(new Paragraph("\n"));

                        PdfPTable total = new PdfPTable(1);
                        total.setWidthPercentage(30);
                        PdfPCell cell = new PdfPCell();
                        Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                        cell.addElement(paragraph);
                        total.addCell(cell);
                        total.setHorizontalAlignment(0);
                        doc.add(total);
                        doc.newPage();
                    }
                }

            }

            // === Generar tablas por administrativos ===
            PreparedStatement psAdminArea = con.prepareStatement(
                    "SELECT area_administrativo FROM sistema_capacitacion.administrativos GROUP BY area_administrativo");
            ResultSet rsAdminArea = psAdminArea.executeQuery();

            while (rsAdminArea.next()) {
                PreparedStatement psTurnoAdmin = con.prepareStatement(
                        "SELECT turno FROM sistema_capacitacion.administrativos WHERE area_administrativo = ? GROUP BY turno ORDER BY turno");
                psTurnoAdmin.setString(1, rsAdminArea.getString("area_administrativo"));
                ResultSet rsTurnoTurnos = psTurnoAdmin.executeQuery();

                while (rsTurnoTurnos.next()) {
                    float[] relativeWidths = {0.5F, 2F, 2F, 0.7F};
                    PdfPTable tabla = new PdfPTable(relativeWidths);
                    tabla.setWidthPercentage(100);

                    BaseColor color = new BaseColor(175, 196, 174);
                    Font fontBold = new Font();
                    fontBold.setStyle(Font.BOLD);
                    fontBold.setSize(10);

                    tabla.addCell(createHeaderCell("NÚM. NOM", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("NOMBRE COMPLETO", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("BRIGADISTA", fontBold, color, 1));
                    tabla.addCell(createHeaderCell("ASISTENCIA", fontBold, color, 1));

                    doc.add(logo);
                    doc.add(new Paragraph("Lista de Emergencia, Área Administrativa: " + rsAdminArea.getString("area_administrativo")
                            + ", Turno: " + rsTurnoTurnos.getString("turno")));
                    doc.add(new Paragraph("Fecha: " + FechaS));
                    doc.add(new Paragraph("\n"));

                    PreparedStatement psTrabAdmin = con.prepareStatement(
                            "SELECT * FROM sistema_capacitacion.administrativos vt "
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                            + "WHERE area_administrativo = ? AND turno = ?"
                    );
                    psTrabAdmin.setString(1, rsAdminArea.getString("area_administrativo"));
                    psTrabAdmin.setString(2, rsTurnoTurnos.getString("turno"));
                    ResultSet rsTrabAdmin = psTrabAdmin.executeQuery();

                    int cont = 0;
                    while (rsTrabAdmin.next()) {
                        tabla.addCell(new Phrase(rsTrabAdmin.getString(1), font));
                        tabla.addCell(new Phrase(rsTrabAdmin.getString(2), font));
                        tabla.addCell(new Phrase(rsTrabAdmin.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }

                    if (cont > 0) {
                        doc.add(tabla);
                        doc.add(new Paragraph("\n"));

                        PdfPTable total = new PdfPTable(1);
                        total.setWidthPercentage(30);
                        PdfPCell cell = new PdfPCell();
                        Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                        cell.addElement(paragraph);
                        total.addCell(cell);
                        total.setHorizontalAlignment(0);
                        doc.add(total);
                        doc.newPage();
                    }
                }
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);

            // Abrir el archivo
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(rutaDoc));
                }
            }

            return true;

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean Lista_EmergenciaArea(String area) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista General de Emergencia");
        fileChooser.setSelectedFile(new File("Lista General de Emergencia " + area + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            Connection con = conn.getConnection();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);

            if (area.equals("ADMINISTRACIÓN")) {
                PreparedStatement ps2 = con.prepareStatement("SELECT area_administrativo FROM sistema_capacitacion.administrativos group by area_administrativo;");
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    PreparedStatement ps3 = con.prepareStatement("SELECT turno FROM sistema_capacitacion.administrativos group by turno;");
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                        logo.scalePercent(40F);
                        logo.setAlignment(0);

                        float[] relativeWidths = {0.5F, 2F, 2, 0.7F};
                        PdfPTable tabla = new PdfPTable(relativeWidths);
                        tabla.setWidthPercentage(100);

                        BaseColor color = new BaseColor(175, 196, 174);
                        Font font1 = new Font();
                        font1.setStyle(Font.BOLD);
                        font1.setSize(10);

                        tabla.addCell(createHeaderCell("NÚM. NOM", font1, color, 1));
                        tabla.addCell(createHeaderCell("NOMBRE COMPLETO", font1, color, 1));
                        tabla.addCell(createHeaderCell("BRIGADISTA", font1, color, 1));
                        tabla.addCell(createHeaderCell("ASISTENCIA", font1, color, 1));

                        PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.administrativos vt \n"
                                + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas WHERE area_administrativo = '" + rs2.getString("area_administrativo") + "'\n"
                                + "AND turno = '" + rs3.getString("turno") + "'");
                        ResultSet rs4 = ps4.executeQuery();
                        int cont = 0;
                        while (rs4.next()) {
                            tabla.addCell(new Phrase(rs4.getString(1), font));
                            tabla.addCell(new Phrase(rs4.getString(2), font));
                            tabla.addCell(new Phrase(rs4.getString("nombre_brigada"), font));
                            tabla.addCell("");
                            cont++;
                        }

                        if (cont > 0) {
                            doc.add(logo);
                            doc.add(new Paragraph("Lista de Emergencia, Área: " + rs2.getString("area_administrativo")
                                    + ", Turno: " + rs3.getString("turno")));
                            doc.add(new Paragraph("Fecha: " + FechaS));
                            doc.add(new Paragraph("\n"));

                            doc.add(tabla);
                            doc.add(new Paragraph("\n"));

                            PdfPTable total = new PdfPTable(1);
                            total.setWidthPercentage(30);
                            PdfPCell cell = new PdfPCell();
                            Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                            paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                            cell.addElement(paragraph);
                            total.addCell(cell);
                            total.setHorizontalAlignment(0);
                            doc.add(total);
                        }
                        doc.newPage();
                    }
                }
            } else {
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                    logo.scalePercent(40F);
                    logo.setAlignment(0);

                    float[] relativeWidths = {0.5F, 2F, 2, 0.7F};
                    PdfPTable tabla = new PdfPTable(relativeWidths);
                    tabla.setWidthPercentage(100);

                    BaseColor color = new BaseColor(175, 196, 174);
                    Font font1 = new Font();
                    font1.setStyle(Font.BOLD);
                    font1.setSize(10);

                    tabla.addCell(createHeaderCell("NÚM. NOM", font1, color, 1));
                    tabla.addCell(createHeaderCell("NOMBRE COMPLETO", font1, color, 1));
                    tabla.addCell(createHeaderCell("BRIGADISTA", font1, color, 1));
                    tabla.addCell(createHeaderCell("ASISTENCIA", font1, color, 1));

                    PreparedStatement ps3 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_trabajador vt \n"
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas WHERE nombre_Area = '" + area + "'\n"
                            + "AND nombre_turno = '" + rs1.getString("Nombre_Turno") + "'");
                    ResultSet rs3 = ps3.executeQuery();
                    int cont = 0;
                    while (rs3.next()) {
                        tabla.addCell(new Phrase(rs3.getString(1), font));
                        tabla.addCell(new Phrase(rs3.getString(2), font));
                        tabla.addCell(new Phrase(rs3.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }

                    if (cont > 0) {
                        doc.add(logo);
                        doc.add(new Paragraph("Lista de Emergencia, Área: " + area
                                + ", Turno: " + rs1.getString("Nombre_Turno")));
                        doc.add(new Paragraph("Fecha: " + FechaS));
                        doc.add(new Paragraph("\n"));
                        doc.add(tabla);
                        doc.add(new Paragraph("\n"));

                        PdfPTable total = new PdfPTable(1);
                        total.setWidthPercentage(30);
                        PdfPCell cell = new PdfPCell();
                        Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                        cell.addElement(paragraph);
                        total.addCell(cell);
                        total.setHorizontalAlignment(0);
                        doc.add(total);
                    }
                    doc.newPage();
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

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Lista_EmergenciaEspecifico(String area, String turno) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lista General de Emergencia");
        fileChooser.setSelectedFile(new File("Lista General de Emergencia " + area + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            Connection con = conn.getConnection();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);

            if (area.equals("ADMINISTRACIÓN")) {
                PreparedStatement ps2 = con.prepareStatement("SELECT area_administrativo FROM sistema_capacitacion.administrativos group by area_administrativo;");
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                    logo.scalePercent(40F);
                    logo.setAlignment(0);

                    float[] relativeWidths = {0.5F, 2F, 2, 0.7F};
                    PdfPTable tabla = new PdfPTable(relativeWidths);
                    tabla.setWidthPercentage(100);

                    BaseColor color = new BaseColor(175, 196, 174);
                    Font font1 = new Font();
                    font1.setStyle(Font.BOLD);
                    font1.setSize(10);

                    tabla.addCell(createHeaderCell("NÚM. NOM", font1, color, 1));
                    tabla.addCell(createHeaderCell("NOMBRE COMPLETO", font1, color, 1));
                    tabla.addCell(createHeaderCell("BRIGADISTA", font1, color, 1));
                    tabla.addCell(createHeaderCell("ASISTENCIA", font1, color, 1));

                    PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.administrativos vt \n"
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas WHERE area_administrativo = '" + rs2.getString("area_administrativo") + "'\n"
                            + "AND turno = '" + turno + "'");
                    ResultSet rs4 = ps4.executeQuery();
                    int cont = 0;
                    while (rs4.next()) {
                        tabla.addCell(new Phrase(rs4.getString(1), font));
                        tabla.addCell(new Phrase(rs4.getString(2), font));
                        tabla.addCell(new Phrase(rs4.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }

                    if (cont > 0) {
                        doc.add(logo);
                        doc.add(new Paragraph("Lista de Emergencia, Área: " + rs2.getString("area_administrativo")
                                + ", Turno: " + turno));
                        doc.add(new Paragraph("Fecha: " + FechaS));
                        doc.add(new Paragraph("\n"));

                        doc.add(tabla);
                        doc.add(new Paragraph("\n"));

                        PdfPTable total = new PdfPTable(1);
                        total.setWidthPercentage(30);
                        PdfPCell cell = new PdfPCell();
                        Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                        cell.addElement(paragraph);
                        total.addCell(cell);
                        total.setHorizontalAlignment(0);
                        doc.add(total);
                    }
                    doc.newPage();

                }
            } else {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);

                float[] relativeWidths = {0.5F, 2F, 2, 0.7F};
                PdfPTable tabla = new PdfPTable(relativeWidths);
                tabla.setWidthPercentage(100);

                BaseColor color = new BaseColor(175, 196, 174);
                Font font1 = new Font();
                font1.setStyle(Font.BOLD);
                font1.setSize(10);

                tabla.addCell(createHeaderCell("NÚM. NOM", font1, color, 1));
                tabla.addCell(createHeaderCell("NOMBRE COMPLETO", font1, color, 1));
                tabla.addCell(createHeaderCell("BRIGADISTA", font1, color, 1));
                tabla.addCell(createHeaderCell("ASISTENCIA", font1, color, 1));

                PreparedStatement ps3 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_trabajador vt \n"
                        + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas WHERE nombre_Area = '" + area + "'\n"
                        + "AND nombre_turno = '" + turno + "'");
                ResultSet rs3 = ps3.executeQuery();
                int cont = 0;
                while (rs3.next()) {
                    tabla.addCell(new Phrase(rs3.getString(1), font));
                    tabla.addCell(new Phrase(rs3.getString(2), font));
                    tabla.addCell(new Phrase(rs3.getString("nombre_brigada"), font));
                    tabla.addCell("");
                    cont++;
                }

                if (cont > 0) {
                    doc.add(logo);
                    doc.add(new Paragraph("Lista de Emergencia, Área: " + area
                            + ", Turno: " + turno));
                    doc.add(new Paragraph("Fecha: " + FechaS));
                    doc.add(new Paragraph("\n"));
                    doc.add(tabla);
                    doc.add(new Paragraph("\n"));

                    PdfPTable total = new PdfPTable(1);
                    total.setWidthPercentage(30);
                    PdfPCell cell = new PdfPCell();
                    Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + cont);
                    paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                    cell.addElement(paragraph);
                    total.addCell(cell);
                    total.setHorizontalAlignment(0);
                    doc.add(total);
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

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Lista_EmergenciaPorTurno(String turno) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lista General de Emergencia por Turno");
        fileChooser.setSelectedFile(new File("Lista Emergencia Turno " + turno + " " + FechaS + ".pdf"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            Connection con = conn.getConnection();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);

            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font font1 = new Font();
            font1.setStyle(Font.BOLD);
            font1.setSize(10);

            BaseColor color = new BaseColor(175, 196, 174);

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);

            doc.add(logo);
            doc.add(new Paragraph("Lista de Emergencia por Turno: " + turno, fontTitle));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            PreparedStatement ps = con.prepareStatement(
                    "SELECT vt.*, b.nombre_brigada FROM sistema_capacitacion.view_trabajador vt "
                    + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                    + "WHERE nombre_turno = ? ORDER BY nombre_area, Nombre_Trabajador"
            );
            ps.setString(1, turno);
            ResultSet rs = ps.executeQuery();

            String areaActual = "";
            int totalArea = 0;
            int totalGeneral = 0;

            PdfPTable tabla = null;

            while (rs.next()) {
                String area = rs.getString("nombre_area");

                // Si cambia el área, creamos un nuevo título y tabla
                if (!area.equals(areaActual)) {
                    if (tabla != null) {
                        doc.add(tabla);
                        doc.add(new Paragraph("Total del Área: ____/" + totalArea));
                        doc.newPage();
                    }

                    totalArea = 0;
                    areaActual = area;

                    doc.add(new Paragraph("Área: " + areaActual, font1));
                    doc.add(new Paragraph("\n"));

                    float[] relativeWidths = {0.5F, 2F, 2, 0.7F};
                    tabla = new PdfPTable(relativeWidths);
                    tabla.setWidthPercentage(100);

                    tabla.addCell(createHeaderCell("NÚM. NOM", font1, color, 1));
                    tabla.addCell(createHeaderCell("NOMBRE COMPLETO", font1, color, 1));
                    tabla.addCell(createHeaderCell("BRIGADISTA", font1, color, 1));
                    tabla.addCell(createHeaderCell("ASISTENCIA", font1, color, 1));
                }

                tabla.addCell(new Phrase(rs.getString("Folio_Trabajador"), font));
                tabla.addCell(new Phrase(rs.getString("Nombre_Trabajador"), font));
                tabla.addCell(new Phrase(rs.getString("nombre_brigada"), font));
                tabla.addCell(""); // asistencia vacía

                totalArea++;
                totalGeneral++;
            }

            // Añadir última tabla
            if (tabla != null && totalArea > 0) {
                doc.add(tabla);
                doc.add(new Paragraph("Total del Área: ____/" + totalArea));
            }

            // Total general
            doc.add(new Paragraph("\n"));
            PdfPTable total = new PdfPTable(1);
            total.setWidthPercentage(30);
            PdfPCell cell = new PdfPCell();
            Paragraph paragraph = new Paragraph("Total de Asistentes: ____/" + totalGeneral);
            paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
            cell.addElement(paragraph);
            total.addCell(cell);
            total.setHorizontalAlignment(0);
            doc.add(total);

            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(rutaDoc));
            }

            return true;

        } catch (Exception ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    private static PdfPCell createHeaderCell(String text, Font font, BaseColor color, int rowspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(rowspan);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}
