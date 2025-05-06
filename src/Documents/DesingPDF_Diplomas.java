package Documents;

import Functions.DateTools;
import Querys.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class DesingPDF_Diplomas extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean FormatoDiplomas(int añoSeleccionado, int mesSeleccionado) throws DocumentException {

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Formato de Diplomas");
        String mes = DateTools.obtenerNombreMes(mesSeleccionado+1);
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
            ps.setInt(1, mesSeleccionado+1);
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
                SimpleDateFormat formatoFecha = new SimpleDateFormat("'Yecapixtla, Mor a' d 'de' MMMM 'del' yyyy", new Locale("es", "MX"));
                String fechaCertificacion = formatoFecha.format(rs.getDate("fecha_certificacion"));

                Font tituloFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
                Font textoFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);

                // Cargar la imagen de fondo
                InputStream inputStreamL = ClassLoader.getSystemResourceAsStream("Images/fondoDiploma.png");
                Image fondo = Image.getInstance(ImageIO.read(inputStreamL), null);
                fondo.setAbsolutePosition(0, 0);
                fondo.setAlignment(Element.ALIGN_CENTER);
                fondo.scaleAbsolute(PageSize.A4.getHeight(), PageSize.A4.getWidth());
                document.add(fondo);

//                InputStream inputStream = ClassLoader.getSystemResourceAsStream("Images/IconParkdale.png");
//                Image logo = Image.getInstance(ImageIO.read(inputStream), null);
//                logo.scaleToFit(200, 100);
//                logo.setAlignment(Element.ALIGN_CENTER);
//                document.add(logo);
                Paragraph otorga = new Paragraph("Otorga el presente", textoFont);
                otorga.setAlignment(Element.ALIGN_CENTER);
                document.add(otorga);

                Paragraph titulo = new Paragraph("DIPLOMA", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));

                Paragraph nombre = new Paragraph("a: " + nombreCompleto, textoFont);
                nombre.setAlignment(Element.ALIGN_CENTER);
                document.add(nombre);

                Paragraph texto1 = new Paragraph("Por haber concluido su certificación como: ", textoFont);
                texto1.setAlignment(Element.ALIGN_CENTER);
                document.add(texto1);

                Paragraph curso = new Paragraph(tipoCertificado + " " + nombreCertificado, textoFont);
                curso.setAlignment(Element.ALIGN_CENTER);
                document.add(curso);
                
                document.add(new Paragraph("\n"));

                Paragraph fecha = new Paragraph(fechaCertificacion, textoFont);
                fecha.setAlignment(Element.ALIGN_RIGHT);
                document.add(fecha);

                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));

                PdfPTable table = new PdfPTable(3); // Número de columnas
                table.setWidthPercentage(100); // Ajusta el ancho de la tabla

                PdfPCell cell = new PdfPCell(new Phrase(rs.getString("gerente1") + "\n" + rs.getString("puesto1"), textoFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Centrado horizontal
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Centrado vertical
                cell.setBorder(Rectangle.NO_BORDER); // Elimina los bordes

                table.addCell(cell);

                PdfPCell cell1 = new PdfPCell(new Phrase(rs.getString("gerente2") + "\n" + rs.getString("puesto2"), textoFont));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER); // Centrado horizontal
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); // Centrado vertical
                cell1.setBorder(Rectangle.NO_BORDER); // Elimina los bordes
                table.addCell(cell1);

                PdfPCell cell2 = new PdfPCell(new Phrase(rs.getString("gerente3") + "\n" + rs.getString("puesto3"), textoFont));
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER); // Centrado horizontal
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE); // Centrado vertical
                cell2.setBorder(Rectangle.NO_BORDER); // Elimina los bordes

                table.addCell(cell2);

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
}
