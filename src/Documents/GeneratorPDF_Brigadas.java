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

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista de Emergencias");
        fileChooser.setSelectedFile(new File("Lista General de Emergencia " + FechaS + ".pdf")); // Nombre predeterminado del archivo

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

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
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

                    PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_trabajador vt \n"
                            + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas WHERE nombre_Area = '" + rs.getString("Nombre_Area") + "'\n"
                            + "AND nombre_turno = '" + rs1.getString("Nombre_Turno") + "'");
                    ResultSet rs2 = ps2.executeQuery();
                    int cont = 0;
                    while (rs2.next()) {
                        tabla.addCell(new Phrase(rs2.getString(1), font));
                        tabla.addCell(new Phrase(rs2.getString(2), font));
                        tabla.addCell(new Phrase(rs2.getString("nombre_brigada"), font));
                        tabla.addCell("");
                        cont++;
                    }

                    if (cont > 0) {
                        doc.add(logo);
                        doc.add(new Paragraph("Lista de Emergencia, Área: " + rs.getString("Nombre_Area")
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

            doc.add(logo);

            PreparedStatement ps2 = con.prepareStatement("SELECT area_administrativo FROM sistema_capacitacion.administrativos group by area_administrativo;");
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                PreparedStatement ps3 = con.prepareStatement("SELECT turno FROM sistema_capacitacion.administrativos group by turno;");
                ResultSet rs3 = ps3.executeQuery();

                while (rs3.next()) {
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
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
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
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorExcel_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class.getName()).log(Level.SEVERE, null, ex);
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

            // Consulta general por turno
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM sistema_capacitacion.view_trabajador vt "
                    + "LEFT JOIN brigadas b ON vt.brigada_idBrigada = b.idbrigadas "
                    + "WHERE nombre_turno = ?");
            ps.setString(1, turno);
            ResultSet rs = ps.executeQuery();

            int cont = 0;
            while (rs.next()) {
                tabla.addCell(new Phrase(rs.getString("Folio_Trabajador"), font));
                tabla.addCell(new Phrase(rs.getString("Nombre_Trabajador"), font));
                tabla.addCell(new Phrase(rs.getString("nombre_brigada"), font));
                tabla.addCell(""); // Asistencia vacía
                cont++;
            }

            if (cont > 0) {
                doc.add(logo);
                doc.add(new Paragraph("Lista de Emergencia por Turno: " + turno));
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

            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(rutaDoc));
            }

            return true;

        } catch (Exception ex) {
            Logger.getLogger(GeneratorPDF_Brigadas.class.getName()).log(Level.SEVERE, null, ex);
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
