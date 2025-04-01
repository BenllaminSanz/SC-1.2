package Documents;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DesingPDF_Certificados {

    public static PdfPTable encabezadotablaPuestos(Font font, ResultSet rs) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{1F});
        tablaPuesto.setWidthPercentage(25);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);

        // Agregar celda de encabezado
        tablaPuesto.addCell(createHeaderCell(rs.getString("nombre_turno"), font1, color, 1));

        return tablaPuesto;
    }

    public static PdfPTable encabezadotablaCertificados(Font font) {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.5F, 1F});
        tablaPuesto.setWidthPercentage(25);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);

        // Agregar celda de encabezado
        tablaPuesto.addCell(createHeaderCell("Nomina", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Nombre", font1, color, 1));

        return tablaPuesto;
    }

    public static PdfPTable tablaCertificados(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.5F, 1F});
        tablaPuesto.setWidthPercentage(100);

        // Agregar celdas de datos
        String[] columnas = {"asistentes_curso_idAsistente", "Nombre_Trabajador"};
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell();
            celda.setPhrase(new Phrase(rs1.getString(columna), font));
            tablaPuesto.addCell(celda);
        }
        return tablaPuesto;
    }

    public static PdfPCell createHeaderCell(String text, Font font, BaseColor color, int rowspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(rowspan);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}
