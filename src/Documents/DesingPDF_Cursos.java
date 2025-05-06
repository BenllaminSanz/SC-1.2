package Documents;

import static Functions.optenerNivel.recibirNivel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DesingPDF_Cursos {

    public static PdfPTable encabezadotablaEntrenamientoGeneral(Font font) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(
                new float[]{0.6F, 1.0F, 0.9F, 0.4F, 0.4F, 0.4F, 0.9F, 1.0F, 0.8F, 0.7F, 0.7F, 0.6F, 0.6F, 0.6F, 0.8F});
        tablaPuesto.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);

        // Agregar celda de encabezado
        tablaPuesto.addCell(createHeaderCell("Nomina", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Nombre", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Área", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Turno", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Nivel", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Salario", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Estado", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Curso", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Entrenamiento", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Fecha Inicio", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Fecha Cierre Programada", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Sem. Progamadas", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Sem. Entrenamiento Actual", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Diferencia", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Instructor", font1, color, 1));

        return tablaPuesto;
    }

    public static PdfPTable tablaEntrenamientoGeneral(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(
                new float[]{0.6F, 1.0F, 0.9F, 0.4F, 0.4F, 0.4F, 0.9F, 1.0F, 0.8F, 0.7F, 0.7F, 0.6F, 0.6F, 0.6F, 0.8F});
        tablaPuesto.setWidthPercentage(100);

        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(8);
        font1.setColor(BaseColor.RED);

        // Agregar celdas de datos
        String[] columnas = {"idAsistentes_Curso", "Nombre_Asistente", "nombre_Area", "nombre_Turno", "Nivel", "Salario", "Estado",
            "nombre_curso", "tipo_entrenamiento", "fecha_inicio", "fecha_estimada", "programadas", "actual", "diferencia", "nombre_instructor"};
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell();
            switch (columna) {
                case "nombre_Turno":
                case "actual":
                case "programadas":
                    celda.setPhrase(new Phrase(rs1.getString(columna), font));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tablaPuesto.addCell(celda);
                    break;
                case "diferencia":
                    if (rs1.getInt(columna) > 0) {
                        celda.setPhrase(new Phrase("+" + rs1.getString(columna), font1));
                        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaPuesto.addCell(celda);
                    } else if (rs1.getInt(columna) < 0) {
                        celda.setPhrase(new Phrase(rs1.getString(columna), font));
                        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaPuesto.addCell(celda);
                    } else {
                        celda.setPhrase(new Phrase(rs1.getString(columna), font));
                        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tablaPuesto.addCell(celda);
                    }
                    break;
                case "Nivel":
                    celda.setPhrase(new Phrase(recibirNivel(rs1), font));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tablaPuesto.addCell(celda);
                    break;
                default:
                    celda.setPhrase(new Phrase(rs1.getString(columna), font));
                    tablaPuesto.addCell(celda);
                    break;
            }
        }
        return tablaPuesto;
    }

    private static PdfPCell createHeaderCell(String text, Font font, BaseColor color, int rowspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(rowspan);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    static PdfPTable encabezadotablaEntrenamientoConcentrado(Font font) {
        PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f, 1f, 1f});
        tablaConcentrado.setWidthPercentage(35);
        tablaConcentrado.setHorizontalAlignment(Element.ALIGN_RIGHT);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);

        // Agregar celda de encabezado
        tablaConcentrado.addCell(createHeaderCell("Primero", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Segundo o más", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Total", font1, color, 1));

        return tablaConcentrado;
    }

    static PdfPTable tablaConcentrado(Font font, ResultSet rs4) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{1F, 1F, 1F});
        tablaPuesto.setWidthPercentage(35);
        tablaPuesto.setHorizontalAlignment(Element.ALIGN_RIGHT);

        // Agregar celdas de datos
        String[] columnas = {"PRIMERO", "SEGUNDO", "TOTAL"};
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell();
            celda.setPhrase(new Phrase(rs4.getString(columna), font));
            tablaPuesto.addCell(celda);
        }
        return tablaPuesto;
    }

    static PdfPTable encabezadotablaEntrenamientoConcentradoTurno(Font font) {
        PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f, 1f, 1f, 1f});
        tablaConcentrado.setWidthPercentage(50);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);

        // Agregar celda de encabezado
        tablaConcentrado.addCell(createHeaderCell("Turno", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Primero", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Segundo o más", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Total", font1, color, 1));

        return tablaConcentrado;
    }

    static PdfPTable tablaConcentradoTurno(Font font, ResultSet rs5) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{1F, 1F, 1F, 1F});
        tablaPuesto.setWidthPercentage(50);

        // Agregar celdas de datos
        String[] columnas = {"Nombre_turno", "PRIMERO", "SEGUNDO", "TOTAL"};
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell();
            celda.setPhrase(new Phrase(rs5.getString(columna), font));
            tablaPuesto.addCell(celda);
        }
        return tablaPuesto;
    }

    static PdfPTable encabezadotablaEntrenamientoConcentradoTurnoGeneral(Font font) {
        PdfPTable tablaConcentrado = new PdfPTable(new float[]{1f, 1f, 1f, 1f, 1f});
        tablaConcentrado.setWidthPercentage(75);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);

        // Agregar celda de encabezado
        tablaConcentrado.addCell(createHeaderCell("Área", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Turno", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Primero", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Segundo o más", font1, color, 1));
        tablaConcentrado.addCell(createHeaderCell("Total", font1, color, 1));

        return tablaConcentrado;
    }

    static PdfPTable tablaConcentradoTurnoGeneral(Font font, ResultSet rs5) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{1F, 1F, 1F, 1F, 1F});
        tablaPuesto.setWidthPercentage(75);

        // Agregar celdas de datos
        String[] columnas = {"Nombre_Area", "Nombre_turno", "PRIMERO", "SEGUNDO", "TOTAL"};
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell();
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celda.setPhrase(new Phrase(rs5.getString(columna), font));
            tablaPuesto.addCell(celda);
        }
        return tablaPuesto;
    }
}
