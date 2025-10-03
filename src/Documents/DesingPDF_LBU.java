package Documents;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DesingPDF_LBU {

    public static PdfPTable encabezadoTrabajador() {
        PdfPTable tabla = new PdfPTable(new float[]{0.5F, 1.1F, 0.5F});
        tabla.setWidthPercentage(100);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);
        tabla.addCell(createHeaderCell("Folio", font1, BaseColor.LIGHT_GRAY, 1));
        tabla.addCell(createHeaderCell("Nombre", font1, BaseColor.LIGHT_GRAY, 1));
        tabla.addCell(createHeaderCell("Turno", font1, BaseColor.LIGHT_GRAY, 1));
        return tabla;
    }

    public static PdfPTable encabezadoTrabajadorTurno() {
        PdfPTable tabla = new PdfPTable(new float[]{0.5F, 1F});
        tabla.setWidthPercentage(100);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(9);
        tabla.addCell(createHeaderCell("Folio", font1, BaseColor.LIGHT_GRAY, 1));
        tabla.addCell(createHeaderCell("Nombre", font1, BaseColor.LIGHT_GRAY, 1));
        return tabla;
    }

    public static PdfPTable encabezadoSupervisor() {
        PdfPTable encabezadoSupervisor = new PdfPTable(new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        encabezadoSupervisor.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        encabezadoSupervisor.addCell(createHeaderCell("Supervisor", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Propuesto", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Diferencia", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Plantilla", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("A", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("B", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("C", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("D", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("LV", font1, color, 1));

        return encabezadoSupervisor;
    }

    public static PdfPTable encabezadoSupervisorFlexi() {
        PdfPTable encabezadoSupervisor = new PdfPTable(new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        encabezadoSupervisor.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        encabezadoSupervisor.addCell(createHeaderCell("Supervisor", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Plantilla", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Certificaciones", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Flexibilidad", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Pers. Primer Puesto", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Pers. Segundo Puesto o más", font1, color, 1));
        return encabezadoSupervisor;
    }

    public static PdfPTable encabezadoSupervisorTotal() {
        PdfPTable encabezadoSupervisor = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        encabezadoSupervisor.setWidthPercentage(67);
        encabezadoSupervisor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        encabezadoSupervisor.addCell(createHeaderCell("Puestos Totales", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Diferencia", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Plantilla", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("A", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("B", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("C", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("D", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("LV", font1, color, 1));

        return encabezadoSupervisor;
    }

    public static PdfPTable encabezadoSupervisorTotalFlexi() {
        PdfPTable encabezadoSupervisor = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        encabezadoSupervisor.setWidthPercentage(67);
//        encabezadoSupervisor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        encabezadoSupervisor.addCell(createHeaderCell("Plantilla Total", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Certificados", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Flexibilidad", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Pers. Primer Puesto", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Pers. Segundo Puesto o más", font1, color, 1));

        return encabezadoSupervisor;
    }
    
    public static PdfPTable encabezadoSupervisorTotalAnual() {
        PdfPTable encabezadoSupervisor = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        encabezadoSupervisor.setWidthPercentage(67);
//        encabezadoSupervisor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        encabezadoSupervisor.addCell(createHeaderCell("Total Certificados", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Primer Puesto", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Segundo o más", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Bajas", font1, color, 1));
        encabezadoSupervisor.addCell(createHeaderCell("Total Activo", font1, color, 1));

        return encabezadoSupervisor;
    }

    public static PdfPTable encabezadoBajas(Font font) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.3F, 1.7F, 0.5F, 0.3F, 0.5F, 0.5F, 1F});
        tablaPuesto.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        // Agregar celda de encabezado
        tablaPuesto.addCell(createHeaderCell("Folio", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Nombre", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Fecha de Baja", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Turno", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Puesto", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Supervisor", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Causa", font1, color, 1));
        return tablaPuesto;
    }

    public static PdfPTable tablaTrabajador(String turno) {
        PdfPTable tabla = new PdfPTable(new float[]{0.5F, 1.1F, 0.5F});
        tabla.setWidthPercentage(100);
        return tabla;
    }

    public static PdfPTable tablaTrabajadorTurno() {
        PdfPTable tabla = new PdfPTable(new float[]{0.5F, 1F});
        tabla.setWidthPercentage(100);
        return tabla;
    }

    public static PreparedStatement datosTrabajador(String turno, String puesto, Connection con) throws SQLException {
        String query = "SELECT Folio_Trabajador, Nombre_Trabajador "
                + "FROM view_Trabajador "
                + "WHERE nombre_turno = ? AND Nombre_Puesto = ? "
                + "AND Folio_Trabajador IS NOT NULL AND Nombre_Trabajador IS NOT NULL";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, turno);
        ps.setString(2, puesto);
        return ps;
    }

    public static PdfPTable tablaPuesto(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        tablaPuesto.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        // Agregar celda de encabezado
        PdfPCell headerCell = createHeaderCell(rs1.getString("Nombre_Puesto") + "\n" + rs1.getString("Nombre_Puesto_Ingles"), font1, color, 2);
        tablaPuesto.addCell(headerCell);
        tablaPuesto.addCell(createHeaderCell("Propuesto", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Diferencia", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Plantilla", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("A", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("B", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("C", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("D", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("LV", font1, color, 1));

        // Agregar celdas de datos
        String[] columnas = {"Propuesto", "Diferencia", "Plantilla", "A", "B", "C", "D", "LV"};
        for (String columna : columnas) {
            tablaPuesto.addCell(new Phrase(rs1.getString(columna), font));
        }

        return tablaPuesto;
    }

    public static PdfPTable tablaLBUResumen(Font font) throws SQLException {
        PdfPTable tablaResumen = new PdfPTable(new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        tablaResumen.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        // Agregar celda de encabezado
        tablaResumen.addCell(createHeaderCell("Supervisor", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("Propuesto", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("Diferencia", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("Plantilla", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("A", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("B", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("C", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("D", font1, color, 1));
        tablaResumen.addCell(createHeaderCell("LV", font1, color, 1));

        return tablaResumen;
    }

    public static PdfPTable tablaPuestoTurno(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{5F, 0.5F, 0.5F, 0.5F});
        tablaPuesto.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        // Agregar celda de encabezado
        PdfPCell headerCell = createHeaderCell(rs1.getString("Puesto"), font1, color, 2);
        tablaPuesto.addCell(headerCell);
        tablaPuesto.addCell(createHeaderCell("Propuesto", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Diferencia", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Plantilla", font1, color, 1));

        // Agregar celdas de datos
        String[] columnas = {"Propuesto", "Diferencia", "Plantilla", "Turno"};
        for (String columna : columnas) {
            tablaPuesto.addCell(new Phrase(rs1.getString(columna), font));
        }

        return tablaPuesto;
    }

    public static PdfPTable tablaPuestoTurnoS(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{1F});
        tablaPuesto.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        // Agregar celda de encabezado
        PdfPCell headerCell = createHeaderCell(rs1.getString("Puesto") + " / " + rs1.getString("Puesto_Ingles"), font1, color, 2);
        tablaPuesto.addCell(headerCell);
        return tablaPuesto;
    }

    public static PdfPTable tablaPuestoTurnoTotal(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F});
        tablaPuesto.setWidthPercentage(100);
        BaseColor color = new BaseColor(175, 196, 174);
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);

        tablaPuesto.addCell(createHeaderCell("Propuesto", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Diferencia", font1, color, 1));
        tablaPuesto.addCell(createHeaderCell("Plantilla", font1, color, 1));

        // Agregar celdas de datos
        String[] columnas = {"Total_P", "Total_D", "Total_Pl"};
        for (String columna : columnas) {
            tablaPuesto.addCell(new Phrase(rs1.getString(columna), font));
        }

        return tablaPuesto;
    }

    public static PdfPTable tablaSupervisoresResumen(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        tablaPuesto.setWidthPercentage(100);
        // Agregar celdas de datos
        tablaPuesto.addCell((new Phrase(rs1.getString("Supervisor"), font)));
        tablaPuesto.addCell((createRowsCell(rs1.getString("Propuesto"), font)));
        if (rs1.getInt("Diferencia") > 0) {
            Font vd = new Font();
            vd.setSize(9);
            vd.setStyle(Font.BOLD);
            vd.setColor(0, 153, 0);
            tablaPuesto.addCell(createRowsCell(rs1.getString("Diferencia"), vd));
        } else if (rs1.getInt("Diferencia") < 0) {
            Font rj = new Font();
            rj.setSize(9);
            rj.setStyle(Font.BOLD);
            rj.setColor(BaseColor.RED);
            tablaPuesto.addCell(createRowsCell(rs1.getString("Diferencia"), rj));
        } else {
            tablaPuesto.addCell(createRowsCell(rs1.getString("Diferencia"), font));
        }
        tablaPuesto.addCell((createRowsCell(rs1.getString("Plantilla"), font)));

        String[] columnas = {"A", "B", "C", "D", "LV"};
        for (String columna : columnas) {
            if (rs1.getInt(columna) > 0) {
                Font ng = new Font();
                ng.setSize(9);
                ng.setStyle(Font.BOLD);
                tablaPuesto.addCell(createRowsCell(rs1.getString(columna), ng));
            } else {
                tablaPuesto.addCell((createRowsCell(rs1.getString(columna), font)));
            }
        }
        return tablaPuesto;
    }

    public static PdfPTable tablaSupervisoresTotal(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        tablaPuesto.setWidthPercentage(67);
        tablaPuesto.setHorizontalAlignment(Element.ALIGN_RIGHT);
        // Agregar celdas de datos
        tablaPuesto.addCell(createRowsCell(rs1.getString("Total_P"), font));
        if (rs1.getInt("Total_D") > 0) {
            Font vd = new Font();
            vd.setSize(9);
            vd.setStyle(Font.BOLD);
            vd.setColor(0, 153, 0);
            tablaPuesto.addCell(createRowsCell(rs1.getString("Total_D"), vd));
        } else if (rs1.getInt("Total_D") < 0) {
            Font rj = new Font();
            rj.setSize(9);
            rj.setStyle(Font.BOLD);
            rj.setColor(BaseColor.RED);
            tablaPuesto.addCell(createRowsCell(rs1.getString("Total_D"), rj));
        } else {
            tablaPuesto.addCell(createRowsCell(rs1.getString("Total_D"), font));
        }
        tablaPuesto.addCell(createRowsCell(rs1.getString("Total_Pl"), font));
        String[] columnas = {"A", "B", "C", "D", "LV"};
        for (String columna : columnas) {
            tablaPuesto.addCell(createRowsCell(rs1.getString(columna), font));
        }
        return tablaPuesto;
    }

    public static PdfPTable tablaSupervisores(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
        tablaPuesto.setWidthPercentage(67);
        tablaPuesto.setHorizontalAlignment(Element.ALIGN_RIGHT);
        // Agregar celdas de datos
        tablaPuesto.addCell(createRowsCell(rs1.getString("Propuesto"), font));
        if (rs1.getInt("Diferencia") > 0) {
            Font vd = new Font();
            vd.setSize(9);
            vd.setStyle(Font.BOLD);
            vd.setColor(0, 153, 0);
            tablaPuesto.addCell(createRowsCell(rs1.getString("Diferencia"), vd));
        } else if (rs1.getInt("Diferencia") < 0) {
            Font rj = new Font();
            rj.setSize(9);
            rj.setStyle(Font.BOLD);
            rj.setColor(BaseColor.RED);
            tablaPuesto.addCell(createRowsCell(rs1.getString("Diferencia"), rj));
        } else {
            tablaPuesto.addCell(createRowsCell(rs1.getString("Diferencia"), font));
        }
        tablaPuesto.addCell(createRowsCell(rs1.getString("Plantilla"), font));
        String[] columnas = {"A", "B", "C", "D", "LV"};
        for (String columna : columnas) {
            tablaPuesto.addCell(createRowsCell(rs1.getString(columna), font));
        }
        return tablaPuesto;
    }

    public static PdfPTable tablaBajas(Font font, ResultSet rs1) throws SQLException {
        PdfPTable tablaPuesto = new PdfPTable(new float[]{0.3F, 1.7F, 0.5F, 0.3F, 0.5F, 0.5F, 1F});
        tablaPuesto.setWidthPercentage(100);

        // Agregar celdas de datos
        String[] columnas = {"Folio_Trabajador", "Nombre_Trabajador", "Fecha_Baja", "Nombre_Turno",
            "Nombre_Puesto", "Supervisor", "Comentario"};
        for (String columna : columnas) {
            tablaPuesto.addCell(new Phrase(rs1.getString(columna), font));
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

    private static PdfPCell createRowsCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}
