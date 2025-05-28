package Documents;

import Functions.DateTools;
import Functions.QueryFunctions;
import Querys.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GeneratorPDF_Cursos extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean Lista_Induccion(String idHistorial) {
        Document doc = new Document();
        Connection con = conn.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        PreparedStatement ps1;
        ResultSet rs1;

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista de Inducción");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try {
            ps = con.prepareStatement("Select * FROM historial_curso WHERE idHistorial_curso = ?");
            ps.setString(1, idHistorial);
            rs = ps.executeQuery();
            if (rs.next()) {
                String fecha = DateTools.MySQLtoString(rs.getDate("fecha_inicio"));
                String fechaSQL = DateTools.StringtoMySQL(fecha);
                fileChooser.setSelectedFile(new File("LBU Resumen de Supervisores " + fechaSQL + ".xlsx")); // Nombre predeterminado del archivo
                String rutaDoc = fileToSave.getAbsolutePath();
                PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
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
                tabla.addCell(new Phrase("LUNES \n" + DateTools.calcularFechas(1, fechaSQL), font));
                tabla.addCell(new Phrase("MARTES \n" + DateTools.calcularFechas(2, fechaSQL), font));
                tabla.addCell(new Phrase("MIERCOLES \n" + DateTools.calcularFechas(3, fechaSQL), font));
                tabla.addCell(new Phrase("JUEVES \n" + DateTools.calcularFechas(4, fechaSQL), font));
                tabla.addCell(new Phrase("VIERNES \n" + DateTools.calcularFechas(5, fechaSQL), font));
                tabla.addCell(new Phrase("SABADO \n" + DateTools.calcularFechas(6, fechaSQL), font));
                tabla.addCell(new Phrase("DOMINGO \n" + DateTools.calcularFechas(7, fechaSQL), font));

                ps1 = con.prepareStatement("SELECT ac.idAsistentes_Curso, ac.nombre_asistente, vt.nombre_puesto, vt.nombre_turno\n"
                        + "FROM view_trabajador vt RIGHT JOIN asistentes_curso ac ON vt.Folio_Trabajador = idAsistentes_Curso\n"
                        + "WHERE idHistorial_Curso = ?");
                ps1.setString(1, idHistorial);
                rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    tabla.addCell(new Phrase(rs1.getString(1), font));
                    tabla.addCell(new Phrase(rs1.getString(2), font));
                    tabla.addCell("");
                    tabla.addCell(new Phrase(rs1.getString(3), font));
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                    tabla.addCell("");
                }
                doc.add(tabla);
                doc.add(new Phrase("\n"));
                doc.add(new Phrase("Firma de enterado de asignación de turno."));

                ps1 = con.prepareStatement("SELECT ac.nombre_asistente\n"
                        + "FROM view_trabajador vt RIGHT JOIN asistentes_curso ac ON vt.Folio_Trabajador = idAsistentes_Curso\n"
                        + "WHERE idHistorial_Curso = ?");
                ps1.setString(1, idHistorial);
                rs1 = ps1.executeQuery();

                float[] relativeWidths1 = {1F, 1F};
                PdfPTable tabla1 = new PdfPTable(relativeWidths1);
                tabla1.setWidthPercentage(50);
                tabla1.getDefaultCell().setFixedHeight(20);

                while (rs1.next()) {
                    tabla1.addCell(new Phrase(rs1.getString(1), font));
                    tabla1.addCell("");
                }

                tabla1.setHorizontalAlignment(0);
                doc.add(tabla1);
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
            }
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(GeneratorPDF_Cursos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static boolean Lista_Asistentes(String idHistorial) {
        String Curso = QueryFunctions.CapturaCondicionalAnidado(
                "historial_curso", "curso", "idCurso", "idcurso",
                "nombre_curso", "idHistorial_curso", idHistorial);
        String Objetivo = QueryFunctions.CapturaCondicionalAnidado(
                "historial_curso", "curso", "idCurso", "idcurso",
                "objetivo_curso", "idHistorial_curso", idHistorial);
        String fechaInicio = DateTools.DateLocaltoString(QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "fecha_inicio", "idHistorial_Curso", idHistorial));
        Double MinutosCursos = Double.valueOf(
                QueryFunctions.CapturaCondicionalSimple("historial_curso", "duracion_curso",
                        "idHistorial_curso", idHistorial));
        Double HorasCursos = (MinutosCursos / 60);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String Duracion = decimalFormat.format(HorasCursos);

        Document doc = new Document();

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista de Asistencia");
        fileChooser.setSelectedFile(new File("Lista Asistentes " + Curso + " " + fechaInicio + ".pdf")); // Nombre predeterminado del archivo

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

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorPDF_Cursos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        doc.setPageSize(PageSize.LETTER);
        doc.setMargins(20, 20, 20, 20);
        doc.open();

        try {
            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 8);

            // Crear el encabezado
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);

            // Agregar la imagen
            Image logo = Image.getInstance(ClassLoader.getSystemResource(
                    "Images/LogoParkdale.png"));
            logo.scalePercent(50);
            logo.setAlignment(1);
            doc.add(logo);
            PdfPCell nameCell = new PdfPCell(new Phrase("DEPARTAMENTO DE CAPACITACIÓN Y ADIESTRAMIENTO"));
            nameCell.setBorder(Rectangle.NO_BORDER);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(nameCell);
            PdfPCell nameCell1 = new PdfPCell(new Phrase("FORMATO DE PLATICAS"));
            nameCell1.setBorder(Rectangle.NO_BORDER);
            nameCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(nameCell1);

            doc.add(headerTable);
            doc.add(new Paragraph("\n"));

            PdfPTable dataTable = new PdfPTable(2);
            dataTable.setWidthPercentage(100);

            PdfPCell nameCell2 = new PdfPCell(new Phrase("NOMBRE DE LA PLATICA: " + Curso));
            nameCell2.setColspan(2);
            nameCell2.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell2);

            PdfPCell nameCell3 = new PdfPCell(new Phrase("OBJETIVO: " + Objetivo));
            nameCell3.setColspan(2);
            nameCell3.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell3);

            PdfPCell nameCell5 = new PdfPCell(new Phrase("FECHA: " + fechaInicio));
            nameCell5.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell5);

            PdfPCell nameCell6 = new PdfPCell(new Phrase("DURACIÓN: " + Duracion + "hrs"));
            nameCell6.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell6);

            doc.add(dataTable);

            float[] relativeWidths = {0.7F, 2F, 1F, 2F, 0.6F};
            PdfPTable tabla = new PdfPTable(relativeWidths);
            tabla.setWidthPercentage(100);

            tabla.addCell(new Phrase("NÚM. NOM", font));
            tabla.addCell(new Phrase("NOMBRE COMPLETO", font));
            tabla.addCell(new Phrase("FIRMA", font));
            tabla.addCell(new Phrase("ÁREA", font));
            tabla.addCell(new Phrase("TURNO", font));
            tabla.getDefaultCell().setFixedHeight(25);

            Connection con = conn.getConnection();
            String sql1 = "SET @id_historial_curso:=?";
            String sql = "SELECT * "
                    + "FROM `sistema_capacitacion`.`view_lista_asistentes_curso`;";

            PreparedStatement ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    tabla.addCell(new Phrase(rs.getString("Folio_Trabajador"), font));
                    tabla.addCell(new Phrase(rs.getString("Nombre_Trabajador"), font));
                    tabla.addCell(new Phrase(""));
                    tabla.addCell(new Phrase(rs.getString("Nombre_Area"), font));
                    tabla.addCell(new Phrase(rs.getString("Nombre_Turno"), font));
                } while (rs.next());
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                tabla.addCell(new Phrase(""));
                doc.add(tabla);
            }
            doc.add(new Phrase("\n"));
            doc.add(new Phrase("\n"));
            PdfPTable pieTable = new PdfPTable(1);
            pieTable.setWidthPercentage(50);
            PdfPCell nameCell7 = new PdfPCell(new Phrase("NOMBRE Y FIRMA DEL EXPOSITOR"));
            nameCell7.setBorder(Rectangle.NO_BORDER);
            nameCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameCell7.setBorderWidthTop(1);
            pieTable.addCell(nameCell7);
            doc.add(pieTable);
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
        } catch (DocumentException | SQLException | IOException ex) {
            Logger.getLogger(GeneratorPDF_Cursos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Lista_AsistentesLUP(String idHistorial) {
        String Curso = QueryFunctions.CapturaCondicionalAnidado(
                "historial_curso", "curso", "idCurso", "idcurso",
                "nombre_curso", "idHistorial_curso", idHistorial);
        String Objetivo = QueryFunctions.CapturaCondicionalAnidado(
                "historial_curso", "curso", "idCurso", "idcurso",
                "objetivo_curso", "idHistorial_curso", idHistorial);
        String fechaInicio = DateTools.DateLocaltoString(QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "fecha_inicio", "idHistorial_Curso", idHistorial));
        Double MinutosCursos = Double.valueOf(
                QueryFunctions.CapturaCondicionalSimple("historial_curso", "duracion_curso",
                        "idHistorial_curso", idHistorial));
        Double HorasCursos = (MinutosCursos / 60);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String Duracion = decimalFormat.format(HorasCursos);

        Document doc = new Document();
        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Lista de Asistencia");
        fileChooser.setSelectedFile(new File("Lista Asistentes " + Curso + " " + fechaInicio + ".pdf")); // Nombre predeterminado del archivo

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

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(GeneratorPDF_Cursos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        doc.setPageSize(PageSize.LETTER.rotate());
        doc.setMargins(20, 20, 20, 20);
        doc.open();

        try {
            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA, 8);

            // Crear el encabezado
            PdfPTable headerTable = new PdfPTable(1);
            headerTable.setWidthPercentage(100);

            // Agregar la imagen
            Image logo = Image.getInstance(ClassLoader.getSystemResource(
                    "Images/LogoParkdale.png"));
            logo.scalePercent(50);
            logo.setAlignment(1);
            doc.add(logo);

            PdfPCell nameCell = new PdfPCell(new Phrase("DEPARTAMENTO DE CAPACITACION Y ADIESTRAMIENTO"));
            nameCell.setBorder(Rectangle.NO_BORDER);
            nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(nameCell);
            PdfPCell nameCell1 = new PdfPCell(new Phrase("FORMATO DE PLATICAS"));
            nameCell1.setBorder(Rectangle.NO_BORDER);
            nameCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(nameCell1);

            doc.add(headerTable);
            doc.add(new Paragraph("\n"));

            PdfPTable dataTable = new PdfPTable(2);
            dataTable.setWidthPercentage(100);

            PdfPCell nameCell2 = new PdfPCell(new Phrase("NOMBRE DE LA PLATICA: " + Curso));
            nameCell2.setColspan(2);
            nameCell2.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell2);

            PdfPCell nameCell3 = new PdfPCell(new Phrase("OBJETIVO: " + Objetivo));
            nameCell3.setColspan(2);
            nameCell3.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell3);

            PdfPCell nameCell5 = new PdfPCell(new Phrase("FECHA: " + fechaInicio));
            nameCell5.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell5);

            PdfPCell nameCell6 = new PdfPCell(new Phrase("DURACIÓN: " + Duracion + "hrs"));
            nameCell6.setBorder(Rectangle.NO_BORDER);
            dataTable.addCell(nameCell6);

            doc.add(dataTable);

            float[] relativeWidths = {0.5F, 2F, 1F, 1F, 1F, 1F, 0.7F, 1F, 0.5F};
            PdfPTable tabla = new PdfPTable(relativeWidths);
            tabla.setWidthPercentage(100);

            tabla.addCell(new Phrase("NÚM. NOM", font));
            tabla.addCell(new Phrase("NOMBRE COMPLETO", font));
            tabla.addCell(new Phrase("FIRMA DE ENTRENAMIENTO TEORICO", font));
            tabla.addCell(new Phrase("FIRMA DE ENTRENAMIENTO PRACTICO", font));
            tabla.addCell(new Phrase("FIRMA DE VERIFICACIÓN A LA PRACTIA DEL OPERARIO", font));
            tabla.addCell(new Phrase("FIRMA DEL SUPERVISOR DE LA VERIFICACIÓN DE LA PRACTICA", font));
            tabla.addCell(new Phrase("FECHA", font));
            tabla.addCell(new Phrase("ÁREA", font));
            tabla.addCell(new Phrase("TURNO", font));
            tabla.getDefaultCell().setFixedHeight(25);

            Connection con = conn.getConnection();
            String sql1 = "SET @id_historial_curso:=?";
            String sql = "SELECT * "
                    + "FROM `sistema_capacitacion`.`view_lista_asistentes_curso`;";

            PreparedStatement ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    tabla.addCell(new Phrase(rs.getString("Folio_Trabajador"), font));
                    tabla.addCell(new Phrase(rs.getString("Nombre_Trabajador"), font));
                    tabla.addCell(new Phrase(""));
                    tabla.addCell(new Phrase(""));
                    tabla.addCell(new Phrase(""));
                    tabla.addCell(new Phrase(""));
                    tabla.addCell(new Phrase(""));
                    tabla.addCell(new Phrase(rs.getString("Nombre_Area"), font));
                    tabla.addCell(new Phrase(rs.getString("Nombre_Turno"), font));
                } while (rs.next());
                doc.add(tabla);
            }
            doc.add(new Phrase("\n"));
            doc.add(new Phrase("\n"));
            PdfPTable pieTable = new PdfPTable(1);
            pieTable.setWidthPercentage(50);
            PdfPCell nameCell7 = new PdfPCell(new Phrase("NOMBRE Y FIRMA DEL EXPOSITOR"));
            nameCell7.setBorder(Rectangle.NO_BORDER);
            nameCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameCell7.setBorderWidthTop(1);
            pieTable.addCell(nameCell7);
            doc.add(pieTable);
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

        } catch (DocumentException | SQLException | IOException ex) {
            Logger.getLogger(GeneratorPDF_Cursos.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Entrenamiento_General() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        LocalDate fechaActual = LocalDate.now();

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Entrenamiento");
        fileChooser.setSelectedFile(new File("Reporte de Entrenamiento " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {

                    String Area = rs.getString("nombre_area");
                    String Turno = rs1.getString("nombre_turno");

                    PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
                    psql.executeUpdate();

                    PreparedStatement ps2 = con.prepareStatement("SELECT nombre_Area, " + Turno + " FROM view_reporte_entrenamientos where nombre_Area = ?");
                    ps2.setString(1, Area);
                    ResultSet rs2 = ps2.executeQuery();
                    int contadorFilas = 0;

                    while (rs2.next()) {
                        if (rs2.getInt(Turno) > 0) {
                            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                            logo.scalePercent(40F);
                            logo.setAlignment(0);
                            doc.add(logo);

                            doc.add(new Paragraph("Reporte de Entrenamiento", font1));
                            doc.add(new Paragraph("Área: " + rs.getString("Nombre_Area")));
                            doc.add(new Paragraph("Fecha: " + FechaS));
                            doc.add(new Paragraph("Turno " + rs1.getString("Nombre_Turno")));
                            doc.add(new Paragraph("\n"));

                            PreparedStatement ps3 = con.prepareStatement("SELECT *,\n"
                                    + "FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)  AS programadas,\n"
                                    + "FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7)  AS actual,\n"
                                    + "(FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7))-(FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)) AS diferencia\n"
                                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                                    + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                                    + "JOIN view_reporte_entrenamiento ON view_lbu.Folio_Trabajador = view_reporte_entrenamiento.Folio_Trabajador\n"
                                    + "JOIN trabajador ON view_asistentes_cursos.idAsistentes_Curso = trabajador.Folio_Trabajador\n"
                                    + "WHERE view_lbu.nombre_area = ? and view_lbu.nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                                    + "AND id_tipocurso = 2");

                            ps3.setString(1, Area);
                            ps3.setString(2, Turno);
                            ResultSet rs3 = ps3.executeQuery();

                            PdfPTable encabezadoEntrenamiento = DesingPDF_Cursos.encabezadotablaEntrenamientoGeneral(font);
                            doc.add(encabezadoEntrenamiento);

                            while (rs3.next()) {
                                PdfPTable tablaEntrenamiento = DesingPDF_Cursos.tablaEntrenamientoGeneral(font, rs3);
                                doc.add(tablaEntrenamiento);
                            }

                            doc.add(new Paragraph("\n"));

                            PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f});
                            tablaConcentrado.setWidthPercentage(35);
                            tablaConcentrado.setHorizontalAlignment(Element.ALIGN_RIGHT);

                            PreparedStatement ps4 = con.prepareStatement("SELECT SUM((CASE\n"
                                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                                    + "            ELSE 0\n"
                                    + "        END)) AS `PRIMERO`,\n"
                                    + "        SUM((CASE\n"
                                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                                    + "            ELSE 0\n"
                                    + "        END)) AS `SEGUNDO`,\n"
                                    + "        COUNT(*) AS 'TOTAL'\n"
                                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                                    + "WHERE nombre_area = ? and nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                                    + "AND id_tipocurso = 2;");

                            ps4.setString(1, Area);
                            ps4.setString(2, Turno);
                            ResultSet rs4 = ps4.executeQuery();

                            PdfPTable encabezadoConcentrado = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentrado(font);
                            doc.add(encabezadoConcentrado);

                            if (rs4.next()) {
                                PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentrado(font, rs4);
                                doc.add(tablaConcentradoTotal);
                            }

                            doc.newPage();
                        }
                        contadorFilas++;
                    }
                }
                doc.newPage();
            }
            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("Concentrado de Reporte de Entrenamiento", font1));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            PreparedStatement ps5 = con.prepareStatement("SELECT nombre_area,nombre_turno,Nombre_Supervisor,SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `PRIMERO`,\n"
                    + "        SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `SEGUNDO`,\n"
                    + "        COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2 GROUP BY nombre_area, nombre_turno ORDER BY idArea,idTurno");

            ResultSet rs5 = ps5.executeQuery();

            PdfPTable encabezadoConcentradoTurnos = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentradoTurnoGeneral(font);
            doc.add(encabezadoConcentradoTurnos);

            while (rs5.next()) {
                PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentradoTurnoGeneral(font, rs5);
                doc.add(tablaConcentradoTotal);
            }

            PreparedStatement ps6 = con.prepareStatement("SELECT SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `PRIMERO`,\n"
                    + "        SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `SEGUNDO`,\n"
                    + "        COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2;");

            ResultSet rs6 = ps6.executeQuery();

            PdfPTable tablaPuesto = new PdfPTable(new float[]{1F, 1F, 1F, 1F, 1F});
            tablaPuesto.setWidthPercentage(75);
            PdfPCell celda = new PdfPCell();
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setColspan(2);
            celda.setPhrase(new Phrase("Total", font));
            tablaPuesto.addCell(celda);

            if (rs6.next()) {
                // Agregar celdas de datos
                String[] columnas = {"PRIMERO", "SEGUNDO", "TOTAL"};
                for (String columna : columnas) {
                    celda.setColspan(1);
                    celda.setPhrase(new Phrase(rs6.getString(columna), font));
                    tablaPuesto.addCell(celda);
                }
                doc.add(tablaPuesto);
            }

            doc.add(new Paragraph("\n"));

            PdfPTable encabezadoConcentradoTurnosTotal = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentradoTurnoGeneralSumatoria(font);
            doc.add(encabezadoConcentradoTurnosTotal);

            PreparedStatement ps7 = con.prepareStatement("SELECT nombre_area,nombre_turno, COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2 GROUP BY nombre_area, nombre_turno ORDER BY idArea,idTurno;");

            ResultSet rs7 = ps7.executeQuery();

            PdfPTable tablaSumatorio = new PdfPTable(new float[]{1F, 1F, 1F, 1F, 1F});
            tablaSumatorio.setWidthPercentage(75);
            PdfPCell celda1 = new PdfPCell();
            celda1.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda1.setColspan(2);
            tablaSumatorio.addCell(celda1);

            while (rs7.next()) {
                celda1.setPhrase(new Phrase(rs7.getString("Nombre_Area"), font));
                switch (rs7.getString("nombre_turno")) {
                    case "A":
                        celda1.setPhrase(new Phrase(rs7.getString("TOTAL"), font));
                        break;
                    case "B":
                        celda1.setPhrase(new Phrase(rs7.getString("TOTAL"), font));
                        break;
                    case "C":
                        celda1.setPhrase(new Phrase(rs7.getString("TOTAL"), font));
                        break;
                    case "D":
                        celda1.setPhrase(new Phrase(rs7.getString("TOTAL"), font));
                        break;
                    default:
                        break;
                }
                tablaSumatorio.addCell(celda1);
            }
            doc.add(tablaSumatorio);
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
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Entrenamiento_EspecificoArea(String area, String observaciones) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        LocalDate fechaActual = LocalDate.now();
        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Entrenamiento");
        fileChooser.setSelectedFile(new File("Reporte de Entrenamiento " + area + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                String Turno = rs1.getString("nombre_turno");

                PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
                psql.executeUpdate();

                PreparedStatement ps2 = con.prepareStatement("SELECT nombre_Area, " + Turno + " FROM view_reporte_entrenamientos where nombre_Area = ?");
                ps2.setString(1, area);
                ResultSet rs2 = ps2.executeQuery();
                int contadorFilas = 0;

                while (rs2.next()) {
                    if (rs2.getInt(Turno) > 0) {
                        Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                        logo.scalePercent(40F);
                        logo.setAlignment(0);
                        doc.add(logo);

                        doc.add(new Paragraph("Reporte de Entrenamiento", font1));
                        doc.add(new Paragraph("Área: " + area));
                        doc.add(new Paragraph("Fecha: " + FechaS));
                        doc.add(new Paragraph("Turno " + rs1.getString("Nombre_Turno")));
                        doc.add(new Paragraph("\n"));

                        PreparedStatement ps3 = con.prepareStatement("SELECT *,\n"
                                + "FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)  AS programadas,\n"
                                + "FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7)  AS actual,\n"
                                + "(FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7))-(FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)) AS diferencia\n"
                                + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                                + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                                + "JOIN trabajador ON view_asistentes_cursos.idAsistentes_Curso = trabajador.Folio_Trabajador\n"
                                + "WHERE nombre_area = ? and nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                                + "AND id_tipocurso = 2");

                        ps3.setString(1, area);
                        ps3.setString(2, Turno);
                        ResultSet rs3 = ps3.executeQuery();

                        PdfPTable encabezadoEntrenamiento = DesingPDF_Cursos.encabezadotablaEntrenamientoGeneral(font);
                        doc.add(encabezadoEntrenamiento);

                        while (rs3.next()) {
                            PdfPTable tablaEntrenamiento = DesingPDF_Cursos.tablaEntrenamientoGeneral(font, rs3);
                            doc.add(tablaEntrenamiento);
                        }

                        PdfPTable tablaObservaciones = new PdfPTable(new float[]{1f});
                        tablaObservaciones.setWidthPercentage(35);
                        tablaObservaciones.setHorizontalAlignment(Element.ALIGN_LEFT);

                        PdfPCell texto = new PdfPCell(new Phrase("Observaciones: " + observaciones, font));
                        tablaObservaciones.addCell(texto);

                        doc.add(new Paragraph("\n"));
                        doc.add(tablaObservaciones);

                        PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f});
                        tablaConcentrado.setWidthPercentage(35);
                        tablaConcentrado.setHorizontalAlignment(Element.ALIGN_RIGHT);

                        PreparedStatement ps4 = con.prepareStatement("SELECT SUM((CASE\n"
                                + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                                + "            ELSE 0\n"
                                + "        END)) AS `PRIMERO`,\n"
                                + "        SUM((CASE\n"
                                + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                                + "            ELSE 0\n"
                                + "        END)) AS `SEGUNDO`,\n"
                                + "        COUNT(*) AS 'TOTAL'\n"
                                + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                                + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                                + "WHERE nombre_area = ? and nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                                + "AND id_tipocurso = 2;");

                        ps4.setString(1, area);
                        ps4.setString(2, Turno);
                        ResultSet rs4 = ps4.executeQuery();

                        PdfPTable encabezadoConcentrado = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentrado(font);
                        doc.add(encabezadoConcentrado);

                        if (rs4.next()) {
                            PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentrado(font, rs4);
                            doc.add(tablaConcentradoTotal);
                        }
                    }
                    contadorFilas++;
                }
                doc.newPage();
            }
            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("Concentrado de Reporte de Entrenamiento", font1));
            doc.add(new Paragraph("Área: " + area));
            doc.add(new Paragraph("Fecha: " + FechaS));

            PreparedStatement ps5 = con.prepareStatement("SELECT nombre_turno,SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `PRIMERO`,\n"
                    + "        SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `SEGUNDO`,\n"
                    + "        COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE nombre_area = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2 GROUP BY nombre_turno ORDER BY idTurno");

            ps5.setString(1, area);
            ResultSet rs5 = ps5.executeQuery();

            PdfPTable encabezadoConcentradoTurnos = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentradoTurno(font);
            doc.add(encabezadoConcentradoTurnos);

            while (rs5.next()) {
                PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentradoTurno(font, rs5);
                doc.add(tablaConcentradoTotal);
            }

            PreparedStatement ps6 = con.prepareStatement("SELECT SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `PRIMERO`,\n"
                    + "        SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `SEGUNDO`,\n"
                    + "        COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE nombre_area = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2;");

            ps6.setString(1, area);
            ResultSet rs6 = ps6.executeQuery();

            PdfPTable tablaPuesto = new PdfPTable(new float[]{1F, 1F, 1F, 1F});
            tablaPuesto.setWidthPercentage(50);
            PdfPCell celda = new PdfPCell();
            celda.setPhrase(new Phrase("Total", font));
            tablaPuesto.addCell(celda);

            if (rs6.next()) {
                // Agregar celdas de datos
                String[] columnas = {"PRIMERO", "SEGUNDO", "TOTAL"};
                for (String columna : columnas) {

                    celda.setPhrase(new Phrase(rs6.getString(columna), font));
                    tablaPuesto.addCell(celda);
                }
                doc.add(tablaPuesto);
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
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Entrenamiento_Especifico(String area, String turno, String observaciones) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        LocalDate fechaActual = LocalDate.now();
        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Entrenamiento");
        fileChooser.setSelectedFile(new File("Reporte de Entrenamiento " + area + " Turno " + turno + " " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
            psql.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("SELECT nombre_Area, " + turno + " FROM view_reporte_entrenamientos where nombre_Area = ?");
            ps2.setString(1, area);
            ResultSet rs2 = ps2.executeQuery();
            int contadorFilas = 0;

            while (rs2.next()) {
                if (rs2.getInt(turno) > 0) {
                    Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                    logo.scalePercent(40F);
                    logo.setAlignment(0);
                    doc.add(logo);

                    doc.add(new Paragraph("Reporte de Entrenamiento", font1));
                    doc.add(new Paragraph("Área: " + area));
                    doc.add(new Paragraph("Fecha: " + FechaS));
                    doc.add(new Paragraph("Turno " + turno));
                    doc.add(new Paragraph("\n"));

                    PreparedStatement ps3 = con.prepareStatement("SELECT *,\n"
                            + "FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)  AS programadas,\n"
                            + "FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7)  AS actual,\n"
                            + "(FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7))-(FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)) AS diferencia\n"
                            + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                            + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                            + "JOIN trabajador ON view_asistentes_cursos.idAsistentes_Curso = trabajador.Folio_Trabajador\n"
                            + "WHERE nombre_area = ? and nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                            + "AND id_tipocurso = 2");

                    ps3.setString(1, area);
                    ps3.setString(2, turno);
                    ResultSet rs3 = ps3.executeQuery();

                    PdfPTable encabezadoEntrenamiento = DesingPDF_Cursos.encabezadotablaEntrenamientoGeneral(font);
                    doc.add(encabezadoEntrenamiento);

                    while (rs3.next()) {
                        PdfPTable tablaEntrenamiento = DesingPDF_Cursos.tablaEntrenamientoGeneral(font, rs3);
                        doc.add(tablaEntrenamiento);
                    }

                    PdfPTable tablaObservaciones = new PdfPTable(new float[]{1f});
                    tablaObservaciones.setWidthPercentage(35);
                    tablaObservaciones.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell texto = new PdfPCell(new Phrase("Observaciones: " + observaciones, font));
                    tablaObservaciones.addCell(texto);

                    doc.add(new Paragraph("\n"));
                    doc.add(tablaObservaciones);

                    PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f});
                    tablaConcentrado.setWidthPercentage(35);
                    tablaConcentrado.setHorizontalAlignment(Element.ALIGN_RIGHT);

                    PreparedStatement ps4 = con.prepareStatement("SELECT SUM((CASE\n"
                            + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                            + "            ELSE 0\n"
                            + "        END)) AS `PRIMERO`,\n"
                            + "        SUM((CASE\n"
                            + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                            + "            ELSE 0\n"
                            + "        END)) AS `SEGUNDO`,\n"
                            + "        COUNT(*) AS 'TOTAL'\n"
                            + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                            + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                            + "WHERE nombre_area = ? and nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                            + "AND id_tipocurso = 2;");

                    ps4.setString(1, area);
                    ps4.setString(2, turno);
                    ResultSet rs4 = ps4.executeQuery();

                    PdfPTable encabezadoConcentrado = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentrado(font);
                    doc.add(encabezadoConcentrado);

                    if (rs4.next()) {
                        PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentrado(font, rs4);
                        doc.add(tablaConcentradoTotal);
                    }
                }
                contadorFilas++;
            }
            doc.newPage();
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
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Entrenamiento_GeneralSalarios() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        LocalDate fechaActual = LocalDate.now();

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte de Entrenamiento");
        fileChooser.setSelectedFile(new File("Reporte de Entrenamiento con Salarios" + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {

                    String Area = rs.getString("nombre_area");
                    String Turno = rs1.getString("nombre_turno");

                    PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))");
                    psql.executeUpdate();

                    PreparedStatement ps2 = con.prepareStatement("SELECT nombre_Area, " + Turno + " FROM view_reporte_entrenamientos where nombre_Area = ?");
                    ps2.setString(1, Area);
                    ResultSet rs2 = ps2.executeQuery();
                    int contadorFilas = 0;

                    while (rs2.next()) {
                        if (rs2.getInt(Turno) > 0) {
                            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                            logo.scalePercent(40F);
                            logo.setAlignment(0);
                            doc.add(logo);

                            doc.add(new Paragraph("Reporte de Entrenamiento", font1));
                            doc.add(new Paragraph("Área: " + rs.getString("Nombre_Area")));
                            doc.add(new Paragraph("Fecha: " + FechaS));
                            doc.add(new Paragraph("Turno " + rs1.getString("Nombre_Turno")));
                            doc.add(new Paragraph("\n"));

                            PreparedStatement ps3 = con.prepareStatement("SELECT *,\n"
                                    + "FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)  AS programadas,\n"
                                    + "FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7)  AS actual,\n"
                                    + "(FLOOR(DATEDIFF(curdate(),fecha_inicio ) / 7))-(FLOOR(DATEDIFF(fecha_estimada,fecha_inicio ) / 7)) AS diferencia\n"
                                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                                    + "LEFT JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                                    + "JOIN view_reporte_entrenamiento ON view_lbu.Folio_Trabajador = view_reporte_entrenamiento.Folio_Trabajador\n"
                                    + "JOIN trabajador ON view_asistentes_cursos.idAsistentes_Curso = trabajador.Folio_Trabajador\n"
                                    + "WHERE view_lbu.nombre_area = ? and view_lbu.nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                                    + "AND id_tipocurso = 2");

                            ps3.setString(1, Area);
                            ps3.setString(2, Turno);
                            ResultSet rs3 = ps3.executeQuery();

                            PdfPTable encabezadoEntrenamiento = DesingPDF_Cursos.encabezadotablaEntrenamientoGeneralSalarios(font);
                            doc.add(encabezadoEntrenamiento);

                            while (rs3.next()) {
                                PdfPTable tablaEntrenamiento = DesingPDF_Cursos.tablaEntrenamientoGeneralSalarios(font, rs3);
                                doc.add(tablaEntrenamiento);
                            }

                            doc.add(new Paragraph("\n"));

                            PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f});
                            tablaConcentrado.setWidthPercentage(35);
                            tablaConcentrado.setHorizontalAlignment(Element.ALIGN_RIGHT);

                            PreparedStatement ps4 = con.prepareStatement("SELECT SUM((CASE\n"
                                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                                    + "            ELSE 0\n"
                                    + "        END)) AS `PRIMERO`,\n"
                                    + "        SUM((CASE\n"
                                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                                    + "            ELSE 0\n"
                                    + "        END)) AS `SEGUNDO`,\n"
                                    + "        COUNT(*) AS 'TOTAL'\n"
                                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                                    + "WHERE nombre_area = ? and nombre_turno = ? AND status_entrenamiento = 'En Entrenamiento'\n"
                                    + "AND id_tipocurso = 2;");

                            ps4.setString(1, Area);
                            ps4.setString(2, Turno);
                            ResultSet rs4 = ps4.executeQuery();

                            PdfPTable encabezadoConcentrado = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentrado(font);
                            doc.add(encabezadoConcentrado);

                            if (rs4.next()) {
                                PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentrado(font, rs4);
                                doc.add(tablaConcentradoTotal);
                            }

                            doc.newPage();
                        }
                        contadorFilas++;
                    }
                }
                doc.newPage();
            }
            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("Concentrado de Reporte de Entrenamiento", font1));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            PreparedStatement ps5 = con.prepareStatement("SELECT nombre_area,nombre_turno,SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `PRIMERO`,\n"
                    + "        SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `SEGUNDO`,\n"
                    + "        COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2 GROUP BY nombre_area, nombre_turno ORDER BY idArea,idTurno");

            ResultSet rs5 = ps5.executeQuery();

            PdfPTable encabezadoConcentradoTurnos = DesingPDF_Cursos.encabezadotablaEntrenamientoConcentradoTurnoGeneral(font);
            doc.add(encabezadoConcentradoTurnos);

            while (rs5.next()) {
                PdfPTable tablaConcentradoTotal = DesingPDF_Cursos.tablaConcentradoTurnoGeneral(font, rs5);
                doc.add(tablaConcentradoTotal);
            }

            PreparedStatement ps6 = con.prepareStatement("SELECT SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento = 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `PRIMERO`,\n"
                    + "        SUM((CASE\n"
                    + "            WHEN (tipo_entrenamiento != 'PRIMERO') THEN 1\n"
                    + "            ELSE 0\n"
                    + "        END)) AS `SEGUNDO`,\n"
                    + "        COUNT(*) AS 'TOTAL'\n"
                    + "FROM sistema_capacitacion.view_asistentes_cursos\n"
                    + "JOIN view_lbu ON view_asistentes_cursos.idAsistentes_Curso = view_lbu.Folio_Trabajador\n"
                    + "WHERE status_entrenamiento = 'En Entrenamiento'\n"
                    + "AND id_tipocurso = 2;");

            ResultSet rs6 = ps6.executeQuery();

            PdfPTable tablaPuesto = new PdfPTable(new float[]{1F, 1F, 1F, 1F, 1F});
            tablaPuesto.setWidthPercentage(75);
            PdfPCell celda = new PdfPCell();
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setColspan(2);
            celda.setPhrase(new Phrase("Total", font));
            tablaPuesto.addCell(celda);

            if (rs6.next()) {
                // Agregar celdas de datos
                String[] columnas = {"PRIMERO", "SEGUNDO", "TOTAL"};
                for (String columna : columnas) {
                    celda.setColspan(1);
                    celda.setPhrase(new Phrase(rs6.getString(columna), font));
                    tablaPuesto.addCell(celda);
                }
                doc.add(tablaPuesto);
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
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
