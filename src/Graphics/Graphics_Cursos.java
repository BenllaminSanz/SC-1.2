//
package Graphics;

import Querys.Conexion;
import View.IFrmCapacitacion;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graphics_Cursos {

    public static void BarCharCursosGeneral(IFrmCapacitacion frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
        ResultSet rs = null;

        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = "SELECT \n"
                + "    nombre_tipo,COUNT(idHistorial) AS cursos,\n"
                + "    SUM(asistentes_esperados) as esperado,\n"
                + "    SUM(num_asistentes) AS asistentes,\n"
                + "    SUM(num_asistentes)/SUM(asistentes_esperados) AS porcentaje\n"
                + "FROM sistema_capacitacion.view_historialcursos\n"
                + "WHERE estado_curso = 'Concluido'\n"
                + "GROUP BY id_tipocurso\n"
                + "ORDER BY id_tipocurso;";

        try {
            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                dataset1.addValue(rs.getInt("cursos"), "Total de Cursos", rs.getString("nombre_tipo"));
                dataset2.addValue(rs.getDouble("porcentaje"), "% Asistencia", rs.getString("nombre_tipo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart chart1 = ChartFactory.createBarChart(
                "Total de Cursos y % Asistencia",
                "Cursos",
                "Cantidad",
                dataset1,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot1 = chart1.getCategoryPlot();

        final NumberAxis rangeAxis = (NumberAxis) plot1.getRangeAxis();
        rangeAxis.setUpperMargin(0.10);

        final BarRenderer renderer = new BarRenderer();
        plot1.setRenderer(renderer);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelPaint(Color.WHITE);
        renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE6, TextAnchor.BOTTOM_CENTER));

        LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setDefaultStroke(new BasicStroke(2.0f));
        renderer2.setDefaultShapesVisible(true);
        renderer2.setDrawOutlines(true);
        renderer2.setUseFillPaint(true);
        renderer2.setDefaultFillPaint(Color.WHITE);
        renderer2.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("##%")));
        renderer2.setDefaultItemLabelsVisible(true);

        plot1.setDataset(1, dataset2);
        plot1.setRenderer(1, renderer2);
        plot1.mapDatasetToRangeAxis(1, 1);

        NumberAxis axis2 = new NumberAxis("% Asistencia");
        axis2.setNumberFormatOverride(NumberFormat.getPercentInstance());
        axis2.setUpperMargin(0.10);
        plot1.setRangeAxis(1, axis2);

        CombinedDomainCategoryPlot combinedPlot = new CombinedDomainCategoryPlot(new CategoryAxis("Cursos"));
        combinedPlot.add(plot1, 2);
        CategoryAxis categoryAxis;
        categoryAxis = combinedPlot.getDomainAxis();
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        JFreeChart combinedChart = new JFreeChart("Total de Cursos y % de Asistencia", JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, true);
        ChartPanel chartPanel = new ChartPanel(combinedChart);
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel24)
        frm.jPanel24.removeAll(); // Limpiar el panel existente
        frm.jPanel24.setLayout(new BorderLayout());
        frm.jPanel24.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.jPanel24.revalidate();
        frm.jPanel24.repaint();
    }

    public static void BarCharCursosTiempo(IFrmCapacitacion frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
        ResultSet rs = null;

        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = "SELECT \n"
                + "nombre_tipo,\n"
                + "SUM(duracion_curso)/60 AS horas_curso,\n"
                + "SUM(horas_asistentes)/60 AS horas_capacitacion\n"
                + "FROM sistema_capacitacion.view_historialcursos\n"
                + "WHERE estado_curso = 'Concluido'\n"
                + "GROUP BY id_tipocurso\n"
                + "ORDER BY id_tipocurso;";

        try {
            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                dataset1.addValue(rs.getInt("horas_curso"), "Horas Curso", rs.getString("nombre_tipo"));
                dataset1.addValue(rs.getInt("horas_capacitacion"), "Horas Capacitación", rs.getString("nombre_tipo"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart chart1 = ChartFactory.createBarChart(
                null,
                "Cursos",
                null,
                dataset1,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart1.getCategoryPlot();
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemLabelAnchorOffset(9.0);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        ChartPanel chartPanel = new ChartPanel(chart1);
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(670, 230));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel24)
        frm.jPanel23.removeAll(); // Limpiar el panel existente
        frm.jPanel23.setLayout(new BorderLayout());
        frm.jPanel23.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.jPanel23.revalidate();
        frm.jPanel23.repaint();
    }

    public static void BarCharCursosTiempoTotal(IFrmCapacitacion frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
        ResultSet rs = null;

        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql1 = "SELECT \n"
                + "SUM(duracion_curso)/60 AS horas_curso,\n"
                + "SUM(horas_asistentes)/60 AS horas_capacitacion\n"
                + "FROM sistema_capacitacion.view_historialcursos\n"
                + "WHERE estado_curso = 'Concluido';";

        try {
            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            ps = con.prepareStatement(sql1);
            rs = ps.executeQuery();

            while (rs.next()) {
                dataset2.addValue(rs.getInt("horas_curso"), "Horas Curso", "Total");
                dataset2.addValue(rs.getInt("horas_capacitacion"), "Horas Capacitación", "Total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart chart1 = ChartFactory.createBarChart(
                "Total de Tiempo en Horas de los Cursos",
                "Cursos",
                "Cantidad",
                dataset2,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false
        );

        CategoryPlot plot = chart1.getCategoryPlot();

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setUpperMargin(0.15);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemLabelAnchorOffset(9.0);
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        ChartPanel chartPanel = new ChartPanel(chart1);
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(670, 100));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel24)
        frm.jPanel22.removeAll(); // Limpiar el panel existente
        frm.jPanel22.setLayout(new BorderLayout());
        frm.jPanel22.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.jPanel22.revalidate();
        frm.jPanel22.repaint();
    }

}
