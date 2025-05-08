package Documents;

import Functions.DateTools;
import Querys.Conexion;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DesingPDF_Diplomas extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean FormatoDiplomas(int añoSeleccionado, int mesSeleccionado) throws DocumentException {

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Formato de Diplomas");
        String mes = DateTools.obtenerNombreMes(mesSeleccionado + 1);
        fileChooser.setSelectedFile(new File("Diplomas " + mes + " " + añoSeleccionado + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_certificados "
                    + "WHERE MONTH(fecha_certificacion) = ? AND YEAR(fecha_certificacion) = ?;");
            ps.setInt(1, mesSeleccionado + 1);
            ps.setInt(2, añoSeleccionado);
            ResultSet rs = ps.executeQuery();

            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(rutaDoc));
            document.open();

            while (rs.next()) {
                document.newPage(); // Agregar una nueva página por cada diploma

                String nombreCompleto = rs.getString("Nombres") + " " + rs.getString("Apellidos");
                String tipoCertificado = rs.getString("tipo_certificacion");
                String nombreCertificado = rs.getString("nombre_certificado");
                SimpleDateFormat formatoFecha = new SimpleDateFormat("'Yecapixtla, Mor a' d 'de' MMMM 'del' yyyy.", new Locale("es", "MX"));
                String fechaCertificacion = formatoFecha.format(rs.getDate("fecha_certificacion"));

                Font tituloFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);
                Font textoFont = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.ITALIC);
                Font tipoFont = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.BOLD);
                Font fechaFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
                Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

                Paragraph espacio = new Paragraph(" ");
                espacio.setLeading(225f); // 11.5 cm en puntos
                document.add(espacio);

                String nombreFormateado = capitalizarCadaPalabra(nombreCompleto);
                Paragraph nombre = new Paragraph("a: " + nombreFormateado, tituloFont);
                nombre.setAlignment(Element.ALIGN_CENTER);
                document.add(nombre);

                Paragraph texto1 = new Paragraph("Por haber concluido su certificación como: ", textoFont);
                texto1.setAlignment(Element.ALIGN_CENTER);
                document.add(texto1);

//                Paragraph curso = new Paragraph(tipoCertificado + " " + nombreCertificado, tipoFont);
                Paragraph curso = new Paragraph(nombreCertificado, tipoFont);
                curso.setAlignment(Element.ALIGN_CENTER);
                document.add(curso);

                espacio.setLeading(30f); // 11.5 cm en puntos
                document.add(espacio);

                Paragraph fecha = new Paragraph(fechaCertificacion, fechaFont);
                fecha.setAlignment(Element.ALIGN_RIGHT);
                document.add(fecha);

                espacio.setLeading(50f); // 11.5 cm en puntos
                document.add(espacio);

                PdfPTable table = new PdfPTable(3); // Número de columnas
                table.setWidthPercentage(100); // Ajusta el ancho de la tabla

                Chunk gerente1 = new Chunk(rs.getString("gerente1") + "\n", subFont);
                gerente1.setUnderline(0.3f, -2f); // grosor, posición vertical

                Chunk puesto1 = new Chunk(rs.getString("puesto1"), subFont); // Sin subrayado, o agrégalo si quieres

                Phrase phrase = new Phrase();
                phrase.add(gerente1);
                phrase.add(puesto1);

                PdfPCell cell = new PdfPCell(phrase);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell);

                Chunk gerente2 = new Chunk(rs.getString("gerente2") + "\n", subFont);
                gerente2.setUnderline(0.3f, -2f); // grosor, posición vertical

                Chunk puesto2 = new Chunk(rs.getString("puesto2"), subFont); // Sin subrayado, o agrégalo si quieres

                Phrase phrase2 = new Phrase();
                phrase2.add(gerente2);
                phrase2.add(puesto2);

                PdfPCell cell2 = new PdfPCell(phrase2);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell2.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell2);

                Chunk gerente3 = new Chunk(rs.getString("gerente3") + "\n", subFont);
                gerente3.setUnderline(0.3f, -2f); // grosor, posición vertical

                Chunk puesto3 = new Chunk(rs.getString("puesto3"), subFont); // Sin subrayado, o agrégalo si quieres

                Phrase phrase3 = new Phrase();
                phrase3.add(gerente3);
                phrase3.add(puesto3);

                PdfPCell cell3 = new PdfPCell(phrase3);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell3.setBorder(Rectangle.NO_BORDER);
                table.addCell(cell3);

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);
                document.add(new Paragraph("\n\n"));
            }
            document.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (SQLException | DocumentException | java.io.IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String capitalizarCadaPalabra(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        String[] palabras = texto.toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }

        return resultado.toString().trim();
    }
}
