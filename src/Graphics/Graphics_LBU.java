package Graphics;

import Controller.CtrlLBUGeneral;
import Querys.Conexion;
import View.IFrmLBU;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Graphics_LBU {

    public static void PieChartLBU(IFrmLBU frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement psG = null;
        ResultSet rsG = null;

        String sql = "SELECT area.idArea, area.Nombre_Area, COUNT(trabajador.Folio_Trabajador) "
                + "AS cantidad_personas\n"
                + "FROM sistema_capacitacion.area\n"
                + "JOIN trabajador ON area.idArea = trabajador.puesto_area_idArea\n"
                + "GROUP BY area.idArea, area.Nombre_Area order by area.idArea;";

        // Crear un conjunto de datos para la gráfica de pastel
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            psG = con.prepareStatement(sql);
            rsG = psG.executeQuery();

            while (rsG.next()) {
                dataset.setValue(rsG.getString("Nombre_Area"), rsG.getInt("cantidad_personas"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Crear la gráfica de pastel utilizando el conjunto de datos
        JFreeChart chart = ChartFactory.createPieChart(
                "Número de Empleados por Área",
                dataset,
                false, // Mostrar leyenda
                true, // Mostrar tooltips
                false// No permitir interacción
        );

        final PiePlot plot2 = (PiePlot) chart.getPlot();
        plot2.setInteriorGap(0.09);
        plot2.setSimpleLabels(false);
        plot2.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1},{2})"));

        // Crear un panel de gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        // Configurar el tamaño preferido del panel de gráfica
        chartPanel.setPreferredSize(new Dimension(450, 350));
        chartPanel.setMouseWheelEnabled(true);

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.jPanel5.removeAll(); // Limpiar el panel existente
        frm.jPanel5.setLayout(new BorderLayout());
        frm.jPanel5.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.jPanel5.revalidate();
        frm.jPanel5.repaint();
    }

    public static void StackedBarChartLBU(IFrmLBU frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        // Consulta: trabajadores por supervisor y turno
        String sql = "SELECT Nombre_Supervisor, Nombre_Turno, COUNT(Folio_Trabajador) AS total\n"
                + "                FROM view_lbu\n"
                + "                GROUP BY Nombre_Supervisor, Nombre_Turno\n"
                + "                ORDER BY Nombre_Turno;";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String supervisor = rs.getString("Nombre_Supervisor");
                String[] palabras = supervisor.split(" ");
                if (palabras.length > 2) {
                    supervisor = palabras[0] + " " + palabras[1]; // solo primeras dos palabras
                }

                String turno = rs.getString("Nombre_Turno"); // serie
                int total = rs.getInt("total");

                dataset.addValue(total, turno, supervisor);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Crear gráfica de barras apiladas
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Distribución de Trabajadores por Supervisor y Turno",
                "Supervisor",
                "Trabajadores",
                dataset,
                PlotOrientation.VERTICAL,
                true, // leyenda
                true, // tooltips
                false // URLs
        );

        // Personalizar colores y etiquetas
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();

        // Rotar etiquetas 45 grados
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 4) // 45°
        );
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 9));

        StackedBarRenderer renderer = new StackedBarRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        plot.setRenderer(renderer);

        // Panel con la gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 350));

        // Insertar en el panel de tu formulario
        frm.jPanel6.removeAll();
        frm.jPanel6.setLayout(new BorderLayout());
        frm.jPanel6.add(chartPanel, BorderLayout.CENTER);
        frm.jPanel6.revalidate();
        frm.jPanel6.repaint();
    }

    public static void BarChartLBUTrabajadores(IFrmLBU frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement ps1 = null;
        ResultSet rs1 = null;

        String sql1 = "SELECT h.semana, h.año, h.fecha, h.id_supervisor,  h.num_trabajadores, hc.num_certificados,\n"
                + "s.Nombre_Supervisor, round((hc.num_certificados/h.num_trabajadores)*100, 2) AS 'Flexibilidad' FROM historial_Supervisor h\n"
                + "LEFT JOIN historial_certificados hc ON h.id_supervisor = hc.id_supervisor AND h.semana=hc.semana\n"
                + "INNER JOIN supervisor s ON h.id_Supervisor=s.idSupervisor \n"
                + "WHERE h.año = YEAR(CURDATE())";

        // Crear un conjunto de datos para la gráfica de pastel
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        try {
            ps1 = con.prepareStatement(sql1);
            rs1 = ps1.executeQuery();
            while (rs1.next()) {
                dataset1.addValue(rs1.getInt("num_trabajadores"),
                        rs1.getString("Nombre_Supervisor"), rs1.getString("semana"));
//                dataset1.addValue(rs1.getInt("num_certificados"),
//                        rs1.getString("Nombre_Supervisor"), rs1.getString("semana"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        SlidingCategoryDataset sliding = new SlidingCategoryDataset(dataset1, 0, 1);
        // Crear la gráfica de pastel utilizando el conjunto de datos
        JFreeChart chart = ChartFactory.createBarChart(
                "Personal de Supervisores por Semana",
                "Semana",
                "Personal",
                sliding,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        CategoryPlot plot = chart.getCategoryPlot();

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(1);
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);
        renderer.setDefaultItemLabelGenerator(new LabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("SansSerif", 0, 10));
        final ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.5707963267948966);
        renderer.setDefaultPositiveItemLabelPosition(p);
        final ItemLabelPosition p2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, -1.5707963267948966);
        renderer.setPositiveItemLabelPositionFallback(p2);

        // Crear un panel de gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(527, 260));

        JScrollBar scroller = new JScrollBar(0, 0, 0, 0, dataset1.getRowCount());

        int rowCount = dataset1.getRowCount();
        if (rowCount > 10) {
            scroller = new JScrollBar(0, 0, 10, 0, rowCount);
        } else {
            // Si el número de filas es menor que el tamaño de la barra, ajusta la longitud
            scroller = new JScrollBar(0, 0, rowCount, 0, rowCount);
        }

        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.add(chartPanel, BorderLayout.CENTER);
        scrollPanel.add(scroller, BorderLayout.SOUTH);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
//        frm.jPanel6.removeAll(); // Limpiar el panel existente
//        frm.jPanel6.setLayout(new BorderLayout());
//        frm.jPanel6.add(scrollPanel, BorderLayout.CENTER);
        // Actualizar el JPanel para que se muestre la gráfica
//        frm.jPanel6.revalidate();
//        frm.jPanel6.repaint();
    }

    public static void BarChartLBUCertificados(IFrmLBU frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement ps1 = null;
        ResultSet rs1 = null;

        String sql1 = "SELECT h.semana, h.año, h.fecha, h.id_supervisor,  h.num_trabajadores, hc.num_certificados,\n"
                + "s.Nombre_Supervisor, round((hc.num_certificados/h.num_trabajadores)*100, 2) AS 'Flexibilidad' FROM historial_Supervisor h\n"
                + "LEFT JOIN historial_certificados hc ON h.id_supervisor = hc.id_supervisor AND h.semana=hc.semana\n"
                + "INNER JOIN supervisor s ON h.id_Supervisor=s.idSupervisor \n"
                + "WHERE h.año = YEAR(CURDATE())";

        // Crear un conjunto de datos para la gráfica de pastel
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        try {
            ps1 = con.prepareStatement(sql1);
            rs1 = ps1.executeQuery();
            while (rs1.next()) {
                dataset1.addValue(rs1.getInt("num_certificados"),
                        rs1.getString("Nombre_Supervisor"), rs1.getString("semana"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        SlidingCategoryDataset sliding = new SlidingCategoryDataset(dataset1, 0, 1);
        // Crear la gráfica de pastel utilizando el conjunto de datos
        JFreeChart chart = ChartFactory.createBarChart(
                "Certificados de Supervisores por Semana",
                "Semana",
                "Certificados",
                sliding,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        CategoryPlot plot = chart.getCategoryPlot();

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(1);
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);
        renderer.setDefaultItemLabelGenerator(new LabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("SansSerif", 0, 10));
        final ItemLabelPosition p = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.5707963267948966);
        renderer.setDefaultPositiveItemLabelPosition(p);
        final ItemLabelPosition p2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, -1.5707963267948966);
        renderer.setPositiveItemLabelPositionFallback(p2);

        // Crear un panel de gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(527, 260));
        System.out.println(dataset1.getRowCount());
        JScrollBar scroller = new JScrollBar(0, 0, 10, 0, dataset1.getRowCount());
        scroller.addAdjustmentListener(e -> {
            int firstCategoryIndex = scroller.getValue();
            ((SlidingCategoryDataset) sliding).setFirstCategoryIndex(firstCategoryIndex);
        });

        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.add(chartPanel, BorderLayout.CENTER);
        scrollPanel.add(scroller, BorderLayout.SOUTH);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
//        frm.jPanel7.removeAll(); // Limpiar el panel existente
//        frm.jPanel7.setLayout(new BorderLayout());
//        frm.jPanel7.add(scrollPanel, BorderLayout.CENTER);
        // Actualizar el JPanel para que se muestre la gráfica
//        frm.jPanel7.revalidate();
//        frm.jPanel7.repaint();
    }

    static class LabelGenerator extends StandardCategoryItemLabelGenerator {

        @Override
        public String generateLabel(final CategoryDataset dataset, final int series, final int category) {
            String supervisor = dataset.getRowKey(series).toString();
            Number value = dataset.getValue(series, category);
            return supervisor + " " + value;
        }
    }
}
