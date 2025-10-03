package Graphics;

import Controller.CtrlLBUGeneral;
import Functions.DateTools;
import Querys.Conexion;
import Tables.DesignTabla;
import View.IFrmCapacitacion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Graphics_Capacitacion {

    public static void PieCharCertificadosProcesos(IFrmCapacitacion frm, String proceso) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
        ResultSet rs = null;
        DefaultPieDataset<String> datasetPie = new DefaultPieDataset<>();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = "SELECT certificaciones,(CASE WHEN certificaciones = 0\n"
                + "THEN (SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE CERTIFICACIONES = 0 AND OBSOLETO = 0)\n"
                + "ELSE COUNT(*)END) AS cantidad\n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE tipo_proceso = ?\n"
                + "GROUP BY certificaciones ORDER BY certificaciones ASC";
        String sql2 = "SELECT COUNT(*) AS cantidad \n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE tipo_proceso = ?\n"
                + "AND obsoleto > 0 AND certificaciones = 0";
        String sql3 = "SELECT certificaciones,(CASE WHEN certificaciones = 0\n"
                + "THEN (SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE CERTIFICACIONES = 0 AND OBSOLETO = 0)\n"
                + "ELSE COUNT(*)END) AS cantidad\n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n"
                + "GROUP BY certificaciones ORDER BY certificaciones ASC";
        String sql4 = "SELECT COUNT(*) AS cantidad \n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE obsoleto > 0 AND certificaciones = 0";

        try {
            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            switch (proceso) {
                case "1":
                    ps = con.prepareStatement(sql2);
                    ps.setInt(1, 1);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, 1);
                    rs = ps.executeQuery();
                    break;
                case "2":
                    ps = con.prepareStatement(sql2);
                    ps.setInt(1, 2);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, 2);
                    rs = ps.executeQuery();
                    break;
                default:
                    ps = con.prepareStatement(sql4);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }
                    ps = con.prepareStatement(sql3);
                    rs = ps.executeQuery();
                    break;
            }

            while (rs.next()) {
                int certificacion = rs.getInt("certificaciones");
                int cantidad = rs.getInt("cantidad");
                String nombreCertificacion;

                switch (certificacion) {
                    case 0:
                        nombreCertificacion = "Ninguna";
                        break;
                    case 1:
                        nombreCertificacion = "Primera";
                        break;
                    case 2:
                        nombreCertificacion = "Segunda";
                        break;
                    case 3:
                        nombreCertificacion = "Tercera";
                        break;
                    case 4:
                        nombreCertificacion = "Cuarta";
                        break;
                    case 5:
                        nombreCertificacion = "Quinta";
                        break;
                    case 6:
                        nombreCertificacion = "Sexta";
                        break;
                    default:
                        nombreCertificacion = "Otra";
                        break;
                }
                datasetPie.setValue(nombreCertificacion, cantidad);
            }

            // Crear el gráfico de pie
            JFreeChart chart = ChartFactory.createPieChart(
                    "Certificaciones % Flexibilidad",
                    datasetPie,
                    true,
                    true,
                    false
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.addChartMouseListener(new ChartMouseListener() {
                @Override
                public void chartMouseClicked(ChartMouseEvent event) {
                    ChartEntity entity = event.getEntity();
                    if (entity instanceof PieSectionEntity) {
                        PieSectionEntity pieEntity = (PieSectionEntity) entity;
                        Comparable<?> seriesKey = pieEntity.getSectionKey();
                        String estado = seriesKey.toString();

                        // Realiza la acción deseada según la serie y la categoría seleccionada
                        System.out.println("Clic en serie: " + seriesKey + " Estado: " + estado);

                    }
                }

                @Override
                public void chartMouseMoved(ChartMouseEvent event) {
                    // No es necesario implementar este método en este caso
                }
            });

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionPaint("Obsoleto", Color.DARK_GRAY);
            plot.setSectionPaint("Ninguna", Color.WHITE);
            plot.setSectionPaint("Primera", new Color(0, 100, 20));
            plot.setSectionPaint("Segunda", new Color(90, 181, 7));
            plot.setSectionPaint("Tercera", new Color(102, 192, 11));
            plot.setSectionPaint("Cuarta", new Color(114, 203, 16));
            plot.setSectionPaint("Quinta", new Color(137, 214, 49));
            plot.setSectionPaint("Sexta", new Color(169, 225, 89));

            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1}-({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
            plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 11));

            // Crear un nuevo JPanel para contener la gráfica
            JPanel chartContainer = new JPanel(new BorderLayout());
            chartContainer.setPreferredSize(new Dimension(200, 200));
            chartContainer.add(chartPanel, BorderLayout.CENTER);

            // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
            frm.grafica2.removeAll(); // Limpiar el panel existente
            frm.grafica2.setLayout(new BorderLayout());
            frm.grafica2.add(chartContainer, BorderLayout.CENTER);

            // Actualizar el JPanel para que se muestre la gráfica
            frm.grafica2.revalidate();
            frm.grafica2.repaint();
        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void PieCharCertificados(IFrmCapacitacion frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
        ResultSet rs = null;
        DefaultPieDataset datasetPie = new DefaultPieDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = "SELECT certificaciones,(CASE WHEN certificaciones = 0\n"
                + "THEN (SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE CERTIFICACIONES = 0 AND OBSOLETO = 0)\n"
                + "ELSE COUNT(*)END) AS cantidad\n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n"
                + "GROUP BY certificaciones ORDER BY certificaciones ASC";
        String sql2 = "SELECT COUNT(*) AS cantidad \n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n"
                + "WHERE obsoleto > 0 AND certificaciones = 0";

        try {

            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            ps = con.prepareStatement(sql2);
            rs = ps.executeQuery();

            if (rs.next()) {
                int obsoleto = rs.getInt("cantidad");
                datasetPie.setValue("Obsoleto", obsoleto);
            }

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int certificacion = rs.getInt("certificaciones");
                int cantidad = rs.getInt("cantidad");
                String nombreCertificacion;
                switch (certificacion) {
                    case 0:
                        nombreCertificacion = "Ninguna";
                        break;
                    case 1:
                        nombreCertificacion = "Primera";
                        break;
                    case 2:
                        nombreCertificacion = "Segunda";
                        break;
                    case 3:
                        nombreCertificacion = "Tercera";
                        break;
                    case 4:
                        nombreCertificacion = "Cuarta";
                        break;
                    case 5:
                        nombreCertificacion = "Quinta";
                        break;
                    case 6:
                        nombreCertificacion = "Sexta";
                        break;
                    default:
                        nombreCertificacion = "Otra";
                        break;
                }
                datasetPie.setValue(nombreCertificacion, cantidad);

            }

            // Crear el gráfico de pie
            JFreeChart chart = ChartFactory.createPieChart(
                    "Certificaciones % Flexibilidad",
                    datasetPie,
                    true,
                    true,
                    false
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.addChartMouseListener(new ChartMouseListener() {
                @Override
                public void chartMouseClicked(ChartMouseEvent event) {
                    ChartEntity entity = event.getEntity();
                    if (entity instanceof PieSectionEntity) {
                        PieSectionEntity pieEntity = (PieSectionEntity) entity;
                        Comparable<?> seriesKey = pieEntity.getSectionKey();
                        String estado = seriesKey.toString();

                        // Realiza la acción deseada según la serie y la categoría seleccionada
                        System.out.println("Clic en serie: " + seriesKey + " Estado: " + estado);
                        if (!estado.equals("Obsoleto")) {
                            DesignTabla.designTrabajadorCertificados(frm, null, null, null, estado, 0);
                        } else {
                            DesignTabla.designTrabajadorCertificados(frm, null, null, null, estado, -1);
                        }
                    }
                }

                @Override
                public void chartMouseMoved(ChartMouseEvent event) {
                    // No es necesario implementar este método en este caso
                }
            });

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionPaint("Obsoleto", Color.DARK_GRAY);
            plot.setSectionPaint("Ninguna", Color.WHITE);
            plot.setSectionPaint("Primera", new Color(0, 100, 20));
            plot.setSectionPaint("Segunda", new Color(90, 181, 7));
            plot.setSectionPaint("Tercera", new Color(102, 192, 11));
            plot.setSectionPaint("Cuarta", new Color(114, 203, 16));
            plot.setSectionPaint("Quinta", new Color(137, 214, 49));
            plot.setSectionPaint("Sexta", new Color(169, 225, 89));

            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1}-({2})",
                    NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
            plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 11));

            // Crear un nuevo JPanel para contener la gráfica
            JPanel chartContainer = new JPanel(new BorderLayout());
            chartContainer.setPreferredSize(new Dimension(180, 180));
            chartContainer.add(chartPanel, BorderLayout.CENTER);

            // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
            frm.grafica2.removeAll(); // Limpiar el panel existente
            frm.grafica2.setLayout(new BorderLayout());
            frm.grafica2.add(chartContainer, BorderLayout.CENTER);

            // Actualizar el JPanel para que se muestre la gráfica
            frm.grafica2.revalidate();
            frm.grafica2.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void PieCharCertificadosB(IFrmCapacitacion frm, String area, String puesto, String turno, int i) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = null;
        String sql1 = null;

        JFreeChart chart = null;
        ChartPanel chartPanel = null;

        DefaultPieDataset datasetPie = new DefaultPieDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            switch (i) {
                case 1:
                    sql = "SELECT certificados, count(*) as cantidad FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "WHERE nombre_area = ?\n"
                            + "GROUP BY certificados ORDER BY certificados ASC;";
                    sql1 = "SELECT sum(obsoleto) AS cantidad\n"
                            + "FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "  WHERE obsoleto != 0 AND nombre_Area=?";

                    ps = con.prepareStatement(sql1);
                    ps.setString(1, area);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }

                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        int certificacion = rs.getInt("certificados");
                        int cantidad = rs.getInt("cantidad");
                        String nombreCertificacion;
                        switch (certificacion) {
                            case 0:
                                nombreCertificacion = "Ninguna";
                                break;
                            case 1:
                                nombreCertificacion = "Primera";
                                break;
                            case 2:
                                nombreCertificacion = "Segunda";
                                break;
                            case 3:
                                nombreCertificacion = "Tercera";
                                break;
                            case 4:
                                nombreCertificacion = "Cuarta";
                                break;
                            case 5:
                                nombreCertificacion = "Quinta";
                                break;
                            case 6:
                                nombreCertificacion = "Sexta";
                                break;
                            default:
                                nombreCertificacion = "Otra";
                                break;
                        }
                        datasetPie.setValue(nombreCertificacion, cantidad);

                    }
                    break;
                case 2:
                    sql = "SELECT certificados, count(*) as cantidad FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "WHERE nombre_area = ? AND nombre_turno=?\n"
                            + "GROUP BY certificados ORDER BY certificados ASC;";
                    sql1 = "SELECT sum(obsoleto) AS cantidad\n"
                            + "FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "  WHERE obsoleto != 0 AND nombre_Area=? AND nombre_turno=?";

                    ps = con.prepareStatement(sql1);
                    ps.setString(1, area);
                    ps.setString(2, turno);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    ps.setString(2, turno);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        int certificacion = rs.getInt("certificados");
                        int cantidad = rs.getInt("cantidad");
                        String nombreCertificacion;
                        switch (certificacion) {
                            case 0:
                                nombreCertificacion = "Ninguna";
                                break;
                            case 1:
                                nombreCertificacion = "Primera";
                                break;
                            case 2:
                                nombreCertificacion = "Segunda";
                                break;
                            case 3:
                                nombreCertificacion = "Tercera";
                                break;
                            case 4:
                                nombreCertificacion = "Cuarta";
                                break;
                            case 5:
                                nombreCertificacion = "Quinta";
                                break;
                            case 6:
                                nombreCertificacion = "Sexta";
                                break;
                            default:
                                nombreCertificacion = "Otra";
                                break;
                        }
                        datasetPie.setValue(nombreCertificacion, cantidad);

                    }
                    break;
                case 3:
                    sql = "SELECT certificados, count(*) as cantidad FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "WHERE nombre_area = ? AND nombre_puesto=?\n"
                            + "GROUP BY certificados ORDER BY certificados ASC;";
                    sql1 = "SELECT sum(obsoleto) AS cantidad\n"
                            + "FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "  WHERE obsoleto != 0 AND nombre_Area=? AND nombre_puesto=?";

                    ps = con.prepareStatement(sql1);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        int certificacion = rs.getInt("certificados");
                        int cantidad = rs.getInt("cantidad");
                        String nombreCertificacion;
                        switch (certificacion) {
                            case 0:
                                nombreCertificacion = "Ninguna";
                                break;
                            case 1:
                                nombreCertificacion = "Primera";
                                break;
                            case 2:
                                nombreCertificacion = "Segunda";
                                break;
                            case 3:
                                nombreCertificacion = "Tercera";
                                break;
                            case 4:
                                nombreCertificacion = "Cuarta";
                                break;
                            case 5:
                                nombreCertificacion = "Quinta";
                                break;
                            case 6:
                                nombreCertificacion = "Sexta";
                                break;
                            default:
                                nombreCertificacion = "Otra";
                                break;
                        }
                        datasetPie.setValue(nombreCertificacion, cantidad);

                    }
                    break;
                case 4:
                    sql = "SELECT certificados, count(*) as cantidad FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "WHERE nombre_area = ? AND nombre_puesto=? AND nombre_turno=?\n"
                            + "GROUP BY certificados ORDER BY certificados ASC;";
                    sql1 = "SELECT sum(obsoleto) AS cantidad\n"
                            + "FROM sistema_capacitacion.view_reporte_certificados\n"
                            + "  WHERE obsoleto != 0 AND nombre_Area=? AND nombre_puesto=? AND nombre_turno=?";

                    ps = con.prepareStatement(sql1);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    ps.setString(3, turno);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        int obsoleto = rs.getInt("cantidad");
                        datasetPie.setValue("Obsoleto", obsoleto);
                    }
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    ps.setString(3, turno);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        int certificacion = rs.getInt("certificados");
                        int cantidad = rs.getInt("cantidad");
                        String nombreCertificacion;
                        switch (certificacion) {
                            case 0:
                                nombreCertificacion = "Ninguna";
                                break;
                            case 1:
                                nombreCertificacion = "Primera";
                                break;
                            case 2:
                                nombreCertificacion = "Segunda";
                                break;
                            case 3:
                                nombreCertificacion = "Tercera";
                                break;
                            case 4:
                                nombreCertificacion = "Cuarta";
                                break;
                            case 5:
                                nombreCertificacion = "Quinta";
                                break;
                            case 6:
                                nombreCertificacion = "Sexta";
                                break;
                            default:
                                nombreCertificacion = "Otra";
                                break;
                        }
                        datasetPie.setValue(nombreCertificacion, cantidad);
                    }
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // Crear el gráfico de barras
        chart = ChartFactory.createPieChart(
                "Certificaciones % Flexibilidad",
                datasetPie,
                true,
                true,
                false
        );

        chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                ChartEntity entity = event.getEntity();
                if (entity instanceof PieSectionEntity) {
                    PieSectionEntity pieEntity = (PieSectionEntity) entity;
                    Comparable<?> seriesKey = pieEntity.getSectionKey();
                    String Estado = seriesKey.toString();

                    // Realiza la acción deseada según la serie y la categoría seleccionada
                    System.out.println("Clic en serie: " + seriesKey + " Estado: " + Estado);
                    DesignTabla.designTrabajadorCertificados(frm, area, puesto, turno, Estado, i);
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                // No es necesario implementar este método en este caso
            }
        });

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Obsoleto", Color.DARK_GRAY);
        plot.setSectionPaint("Ninguna", Color.WHITE);
        plot.setSectionPaint("Primera", new Color(0, 100, 20));
        plot.setSectionPaint("Segunda", new Color(90, 181, 7));
        plot.setSectionPaint("Tercera", new Color(102, 192, 11));
        plot.setSectionPaint("Cuarta", new Color(114, 203, 16));
        plot.setSectionPaint("Quinta", new Color(137, 214, 49));
        plot.setSectionPaint("Sexta", new Color(169, 225, 89));

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1}-({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 11));

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(200, 200));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica2.removeAll(); // Limpiar el panel existente
        frm.grafica2.setLayout(new BorderLayout());
        frm.grafica2.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica2.revalidate();
        frm.grafica2.repaint();
    }

    public static void BarCharCertificadosProcesos(IFrmCapacitacion frm, String proceso) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql1 = "SELECT * \n"
                + "FROM `sistema_capacitacion`.`view_estado_capacitacion`\n"
                + "WHERE tipo_proceso = ?;";

        String sql = "SELECT * \n"
                + "FROM `sistema_capacitacion`.`view_estado_capacitacion`;";

        // Crear un conjunto de datos para la gráfica de Barra
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            switch (proceso) {
                case "1":
                    ps = con.prepareStatement(sql1);
                    ps.setInt(1, 1);
                    rs = ps.executeQuery();
                    break;
                case "2":
                    ps = con.prepareStatement(sql1);
                    ps.setInt(1, 2);
                    rs = ps.executeQuery();
                    break;
                default:
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    break;
            }

            while (rs.next()) {
                dataset.addValue(rs.getInt("Obsoleto"), "Obsoleto", rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("Certificados"), "Certificado", rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("No certificados"), "No certificado", rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("En Entrenamiento"), "En Entrenamiento", rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("En Entrenamiento en otro Puesto"), "En Entrenamiento en otro Puesto", rs.getString("nombre_area"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Estatus de Capacitación",
                "Áreas",
                "Cantidad de personas",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, Color.DARK_GRAY); // Color para la cuarta serie
        renderer.setSeriesPaint(1, Color.BLUE); // Color para la primera serie
        renderer.setSeriesPaint(2, Color.RED); // Color para la segunda serie
        renderer.setSeriesPaint(3, Color.GREEN); // Color para la tercera serie
        renderer.setSeriesPaint(4, Color.MAGENTA); // Color para la cuarta serie

        renderer.setSeriesToolTipGenerator(0, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("0.00")));
        renderer.setSeriesToolTipGenerator(1, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(2, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(3, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(4, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));

        CategoryAxis categoryAxis = plot.getDomainAxis();
        categoryAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // Crear un panel de gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                ChartEntity entity = event.getEntity();
                if (entity instanceof CategoryItemEntity) {
                    CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                    Comparable<?> seriesKey = itemEntity.getRowKey();
                    String estado = seriesKey.toString();
                    Object category = itemEntity.getColumnKey();
                    String area = category.toString();
                    // Realiza la acción deseada según la serie y la categoría seleccionada
                    System.out.println("Clic en serie: " + seriesKey + ", categoría: " + category);
                    if (estado.equals("En Entrenamiento") || estado.equals("En Entrenamiento en otro Puesto")) {
                        DesignTabla.designTrabajadorCapacitacion(frm, area, null, null, estado, 1);
                    } else {
                        DesignTabla.designTrabajadorCapacitacion(frm, area, null, null, estado, 2);
                    }
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                // No es necesario implementar este método en este caso
            }
        });

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica.removeAll(); // Limpiar el panel existente
        frm.grafica.setLayout(new BorderLayout());
        frm.grafica.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica.revalidate();
        frm.grafica.repaint();
    }

    public static void BarCharCertificados(IFrmCapacitacion frm) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * \n"
                + "FROM `sistema_capacitacion`.`view_estado_capacitacion`;";

        // Crear un conjunto de datos para la gráfica de Barra
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                dataset.addValue(rs.getInt("Obsoleto"),
                        "Obsoleto",
                        rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("Certificados"),
                        "Certificado",
                        rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("No certificados"),
                        "No certificado",
                        rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("En Entrenamiento"),
                        "En Entrenamiento",
                        rs.getString("nombre_area"));
                dataset.addValue(rs.getInt("En Entrenamiento en otro Puesto"),
                        "En Entrenamiento en otro Puesto",
                        rs.getString("nombre_area"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Estatus de Capacitación",
                "Áreas",
                "Cantidad de personas",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, Color.DARK_GRAY); // Color para la tercera serie
        renderer.setSeriesPaint(1, Color.BLUE); // Color para la primera serie
        renderer.setSeriesPaint(2, Color.RED); // Color para la segunda serie
        renderer.setSeriesPaint(3, Color.GREEN); // Color para la tercera serie
        renderer.setSeriesPaint(4, Color.MAGENTA); // Color para la tercera serie

        renderer.setSeriesToolTipGenerator(0, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(1, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(2, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(3, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));
        renderer.setSeriesToolTipGenerator(4, new StandardCategoryToolTipGenerator(
                "<html>Personal: {2} un {3}</html>", new DecimalFormat("#.00")));

        CategoryAxis categoryAxis;
        categoryAxis = plot.getDomainAxis();
        categoryAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // Crear un panel de gráfica
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                ChartEntity entity = event.getEntity();
                if (entity instanceof CategoryItemEntity) {
                    CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                    Comparable<?> seriesKey = itemEntity.getRowKey();
                    String Estado = seriesKey.toString();
                    Object category = itemEntity.getColumnKey();
                    String Area = category.toString();
                    // Realiza la acción deseada según la serie y la categoría seleccionada
                    System.out.println("Clic en serie: " + seriesKey + ", categoría: " + category);
                    if (Estado.equals("En Entrenamiento") || Estado.equals("En Entrenamiento en otro Puesto")) {
                        DesignTabla.designTrabajadorCapacitacion(frm, Area, null, null, Estado, 1);
                    } else {
                        DesignTabla.designTrabajadorCapacitacion(frm, Area, null, null, Estado, 2);
                    }
//                    frm.jLabel7.setText("Área:" + Area);
//                    frm.jLabel2.setText("Estado: " + Estado);
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                // No es necesario implementar este método en este caso
            }
        });

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica.removeAll(); // Limpiar el panel existente
        frm.grafica.setLayout(new BorderLayout());
        frm.grafica.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica.revalidate();
        frm.grafica.repaint();
    }

    public static void BarCharCertificadosB(IFrmCapacitacion frm, String area, String puesto, String turno, int i) {
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = null;

        // Crear un conjunto de datos para la gráfica de Barra
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = null;
        CategoryPlot plot = null;
        BarRenderer renderer = null;
        CategoryAxis categoryAxis;
        ChartPanel chartPanel = null;

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();
            switch (i) {
                case 1:
                    sql = "SELECT * \n"
                            + "FROM `sistema_capacitacion`.`view_estado_capacitacion_general` "
                            + "WHERE nombre_area = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dataset.addValue(rs.getInt("Obsoleto"),
                                "Obsoleto",
                                rs.getString("nombre_puesto") + "-"
                                + rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("Certificados"),
                                "Certificado",
                                rs.getString("nombre_puesto") + "-"
                                + rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("No certificados"),
                                "No certificado",
                                rs.getString("nombre_puesto") + "-"
                                + rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("En Entrenamiento"),
                                "En Entrenamiento",
                                rs.getString("nombre_puesto") + "-"
                                + rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("En Entrenamiento en otro Puesto"),
                                "En Entrenamiento en otro Puesto",
                                rs.getString("nombre_puesto") + "-"
                                + rs.getString("nombre_turno"));
                    }
                    // Crear el gráfico de barras
                    chart = ChartFactory.createStackedBarChart(
                            "Estatus de Capacitación " + area,
                            "Puestos",
                            "Cantidad de personas",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
                    );

                    // Obtener la referencia al plot de la gráfica
                    plot = (CategoryPlot) chart.getPlot();
                    // Obtener la referencia al eje X (CategoryAxis)
                    categoryAxis = plot.getDomainAxis();
                    renderer = (BarRenderer) plot.getRenderer();

                    renderer.setSeriesPaint(0, Color.DARK_GRAY); // Color para la primera serie
                    renderer.setSeriesPaint(1, Color.BLUE); // Color para la primera serie
                    renderer.setSeriesPaint(2, Color.RED); // Color para la segunda serie
                    renderer.setSeriesPaint(3, Color.GREEN); // Color para la tercera serie
                    renderer.setSeriesPaint(4, Color.MAGENTA); // Color para la tercera serie
                    renderer.setSeriesPaint(5, Color.BLACK); // Color para la tercera serie

                    categoryAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
                    categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

                    // Crear un panel de gráfica
                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            ChartEntity entity = event.getEntity();
                            if (entity instanceof CategoryItemEntity) {
                                CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                                Comparable<?> seriesKey = itemEntity.getRowKey();
                                String Estado = seriesKey.toString();
                                Object category = itemEntity.getColumnKey();
                                String AreaTurno = category.toString();
                                String[] palabras = AreaTurno.split("-");

                                String Puesto = palabras[0];
                                String Turno = palabras[1];

                                // Realiza la acción deseada según la serie y la categoría seleccionada
                                System.out.println("Clic en serie: " + seriesKey + ", categoría: " + category);
                                if (Estado.equals("En Entrenamiento") || Estado.equals("En Entrenamiento en otro Puesto")) {
                                    DesignTabla.designTrabajadorCapacitacion(frm, null, Puesto, Turno, Estado, 3);
                                } else {
                                    DesignTabla.designTrabajadorCapacitacion(frm, null, Puesto, Turno, Estado, 4);
                                }
//                                frm.jLabel7.setText("Puesto: " + Puesto);
//                                frm.jLabel2.setText("Estado:" + Estado);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {
                            // No es necesario implementar este método en este caso
                        }
                    });
                    break;
                case 2:
                    sql = "SELECT * FROM `sistema_capacitacion`.`view_estado_capacitacion_general` "
                            + "WHERE nombre_area=? AND nombre_turno=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    ps.setString(2, turno);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dataset.addValue(rs.getInt("Obsoleto"),
                                "Obsoleto",
                                rs.getString("nombre_puesto"));
                        dataset.addValue(rs.getInt("Certificados"),
                                "Certificado",
                                rs.getString("nombre_puesto"));
                        dataset.addValue(rs.getInt("No certificados"),
                                "No certificado",
                                rs.getString("nombre_puesto"));
                        dataset.addValue(rs.getInt("En Entrenamiento"),
                                "En Entrenamiento",
                                rs.getString("nombre_puesto"));
                        dataset.addValue(rs.getInt("En Entrenamiento en otro Puesto"),
                                "En Entrenamiento en otro Puesto",
                                rs.getString("nombre_puesto"));
                    }
                    // Crear el gráfico de barras
                    chart = ChartFactory.createStackedBarChart(
                            "Gráfico de Entrenamiento de " + area + " Turno " + turno,
                            "Puestos",
                            "Cantidad de personas",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
                    );
                    // Obtener la referencia al plot de la gráfica
                    plot = (CategoryPlot) chart.getPlot();
                    // Obtener la referencia al eje X (CategoryAxis)
                    categoryAxis = plot.getDomainAxis();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.DARK_GRAY); // Color para la primera serie
                    renderer.setSeriesPaint(1, Color.BLUE); // Color para la primera serie
                    renderer.setSeriesPaint(2, Color.RED); // Color para la segunda serie
                    renderer.setSeriesPaint(3, Color.GREEN); // Color para la tercera serie
                    renderer.setSeriesPaint(4, Color.MAGENTA); // Color para la tercera serie

                    categoryAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
                    categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

                    // Crear un panel de gráfica
                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            ChartEntity entity = event.getEntity();
                            if (entity instanceof CategoryItemEntity) {
                                CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                                Comparable<?> seriesKey = itemEntity.getRowKey();
                                String Estado = seriesKey.toString();
                                Object category = itemEntity.getColumnKey();
                                String Puesto = category.toString();

                                // Realiza la acción deseada según la serie y la categoría seleccionada
                                System.out.println("Clic en serie: " + seriesKey + ", categoría: " + category);
                                if (Estado.equals("En Entrenamiento") || Estado.equals("En Entrenamiento en otro Puesto")) {
                                    DesignTabla.designTrabajadorCapacitacion(frm, null, Puesto, turno, Estado, 3);
                                } else {
                                    DesignTabla.designTrabajadorCapacitacion(frm, null, Puesto, turno, Estado, 4);
                                }
//                                frm.jLabel7.setText("Puesto: " + Puesto);
//                                frm.jLabel2.setText("Estado: " + Estado);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {
                            // No es necesario implementar este método en este caso
                        }
                    });
                    break;
                case 3:
                    sql = "SELECT * FROM `sistema_capacitacion`.`view_estado_capacitacion_general` "
                            + "WHERE nombre_area=? AND nombre_puesto=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dataset.addValue(rs.getInt("Obsoleto"),
                                "Obsoleto",
                                rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("Certificados"),
                                "Certificado",
                                rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("No certificados"),
                                "No certificado",
                                rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("En Entrenamiento"),
                                "En Entrenamiento",
                                rs.getString("nombre_turno"));
                        dataset.addValue(rs.getInt("En Entrenamiento en otro Puesto"),
                                "En Entrenamiento en otro Puesto",
                                rs.getString("nombre_turno"));
                    }
                    // Crear el gráfico de barras
                    chart = ChartFactory.createStackedBarChart(
                            "Gráfico de Entrenamiento de " + area + " Puesto " + puesto,
                            "Turnos",
                            "Cantidad de personas",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
                    );
                    // Obtener la referencia al plot de la gráfica
                    plot = (CategoryPlot) chart.getPlot();
                    // Obtener la referencia al eje X (CategoryAxis)
                    categoryAxis = plot.getDomainAxis();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.DARK_GRAY); // Color para la primera serie
                    renderer.setSeriesPaint(1, Color.BLUE); // Color para la primera serie
                    renderer.setSeriesPaint(2, Color.RED); // Color para la segunda serie
                    renderer.setSeriesPaint(3, Color.GREEN); // Color para la tercera serie
                    renderer.setSeriesPaint(4, Color.MAGENTA); // Color para la tercera serie

                    categoryAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
                    categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

                    // Crear un panel de gráfica
                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            ChartEntity entity = event.getEntity();
                            if (entity instanceof CategoryItemEntity) {
                                CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                                Comparable<?> seriesKey = itemEntity.getRowKey();
                                String Estado = seriesKey.toString();
                                Object category = itemEntity.getColumnKey();
                                String Turno = category.toString();

                                // Realiza la acción deseada según la serie y la categoría seleccionada
                                System.out.println("Clic en serie: " + seriesKey + ", categoría: " + category);
                                if (Estado.equals("En Entrenamiento") || Estado.equals("En Entrenamiento en otro Puesto")) {
                                    DesignTabla.designTrabajadorCapacitacion(frm, null, puesto, Turno, Estado, 5);
                                } else {
                                    DesignTabla.designTrabajadorCapacitacion(frm, null, puesto, Turno, Estado, 6);
                                }
//                                frm.jLabel7.setText("Turno: " + Turno);
//                                frm.jLabel2.setText("Estado: " + Estado);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {
                            // No es necesario implementar este método en este caso
                        }
                    });
                    break;
                case 4:
                    DefaultPieDataset datasetPie = new DefaultPieDataset();
                    sql = "SELECT * FROM `sistema_capacitacion`.`view_estado_capacitacion_general` "
                            + "WHERE nombre_area=? AND nombre_puesto=? AND nombre_turno=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    ps.setString(3, turno);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int Obsoleto = (rs.getInt("Obsoleto"));
                        if (Obsoleto > 0) {
                            datasetPie.setValue("Obsoleto", Obsoleto);
                        }
                        int Certificado = (rs.getInt("Certificados"));
                        if (Certificado > 0) {
                            datasetPie.setValue("Certificado", Certificado);
                        }
                        int NoCertificado = (rs.getInt("No Certificados"));
                        if (NoCertificado > 0) {
                            datasetPie.setValue("No certificado", NoCertificado);
                        }
                        int Entrenamiento = (rs.getInt("En Entrenamiento"));
                        if (Entrenamiento > 0) {
                            datasetPie.setValue("En Entrenamiento", Entrenamiento);
                        }
                        int EntrenamientoB = (rs.getInt("En Entrenamiento en otro Puesto"));
                        if (EntrenamientoB > 0) {
                            datasetPie.setValue("En Entrenamiento en otro Puesto", EntrenamientoB);
                        }

                        System.out.println(Certificado + "\n" + NoCertificado + "\n" + Entrenamiento);
                    }

                    // Crear el gráfico de barras
                    chart = ChartFactory.createPieChart(
                            "Gráfico de Entrenamiento de " + area + "\n Puesto: " + puesto + "\n Turno: " + turno,
                            datasetPie,
                            true,
                            true,
                            false
                    );

                    PiePlot plot1 = (PiePlot) chart.getPlot();
                    plot1.setSectionPaint("Certificado", Color.BLUE);
                    plot1.setSectionPaint("No certificado", Color.RED);
                    plot1.setSectionPaint("En Entrenamiento", Color.GREEN);
                    plot1.setSectionPaint("En Entrenamiento en otro Puesto", Color.MAGENTA);
                    plot1.setSectionPaint("Obsoleto", Color.BLACK);

                    // Crear un panel de gráfica
                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            ChartEntity entity = event.getEntity();
                            if (entity instanceof PieSectionEntity) {
                                PieSectionEntity pieEntity = (PieSectionEntity) entity;
                                Comparable<?> seriesKey = pieEntity.getSectionKey();
                                String Estado = seriesKey.toString();

                                // Realiza la acción deseada según la serie y la categoría seleccionada
                                System.out.println("Clic en serie: " + seriesKey);
                                // Resto del código...

                                if (Estado.equals("En Entrenamiento") || Estado.equals("En Entrenamiento en otro Puesto")) {
                                    DesignTabla.designTrabajadorCapacitacion(frm, area, puesto, turno, Estado, 7);
                                } else {
                                    DesignTabla.designTrabajadorCapacitacion(frm, area, puesto, turno, Estado, 8);
                                }
//                                frm.jLabel2.setText("Estado: " + Estado);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {
                            // No es necesario implementar este método en este caso
                        }
                    });
                    break;

            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica.removeAll(); // Limpiar el panel existente
        frm.grafica.setLayout(new BorderLayout());
        frm.grafica.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica.revalidate();
        frm.grafica.repaint();
    }

    public static void ChartCertificadosProcesos(IFrmCapacitacion frm, int year, String proceso) {
        Conexion conn = new Conexion();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = "SELECT YEAR(fecha_termino) AS Año, MONTH(fecha_termino) AS Mes, COUNT(fecha_termino) AS Total\n"
                + "FROM view_asistentes_certificados\n"
                + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_Trabajador = view_lbu.folio_trabajador\n"
                + "WHERE YEAR(fecha_termino) = ? AND tipo_proceso = ?\n"
                + "GROUP BY MONTH(fecha_termino) ORDER BY MONTH(fecha_termino);";

        try (Connection con = conn.getConnection()) {
            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            ps.setInt(1, year);
            ps.setString(2, proceso);
            System.out.println(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dataset.addValue(rs.getInt("Total"), "Certificados", DateTools.obtenerNombreMes(rs.getInt("Mes")));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Total de Certificaciones Mensuales " + year, // Título del gráfico
                "Mes", // Etiqueta del eje X
                "Total", // Etiqueta del eje Y
                dataset, // Conjunto de datos
                PlotOrientation.HORIZONTAL, // Orientación del gráfico
                true, // Incluir leyenda
                true, // Incluir tooltips
                false // Incluir URLs
        );

        // Crear un marco para mostrar el gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                // Obtener la entidad seleccionada (en este caso, la barra)
                if (event.getEntity() instanceof CategoryItemEntity) {
                    CategoryItemEntity entity = (CategoryItemEntity) event.getEntity();
                    Comparable colum = entity.getColumnKey();
                    String mes = colum.toString();
                    DesignTabla.designPersonalCertificadoProceso(frm, year, DateTools.obtenerNumeroMes(mes), proceso);
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {

            }

        });

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(200, 200));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica_certificados.removeAll(); // Limpiar el panel existente
        frm.grafica_certificados.setLayout(new BorderLayout());
        frm.grafica_certificados.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica_certificados.revalidate();
        frm.grafica_certificados.repaint();
    }

    public static void ChartCertificados(IFrmCapacitacion frm, int year) {
        Conexion conn = new Conexion();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection con = conn.getConnection()) {
            String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            String sql = "SELECT year(fecha_certificacion), month(fecha_certificacion)as Mes,count(*) as Total\n"
                    + "FROM sistema_capacitacion.asistentes_certificados\n"
                    + "WHERE year(fecha_certificacion) = ?\n"
                    + "group by year(fecha_certificacion), month(fecha_certificacion) \n"
                    + "order by year(fecha_certificacion), month(fecha_certificacion) asc;";

            String sql1 = "SELECT year(fecha_certificacion), month(fecha_certificacion) as Mes,count(*) as Total\n"
                    + "FROM sistema_capacitacion.asistentes_certificados\n"
                    + "WHERE year(fecha_certificacion) = year(curdate())\n"
                    + "group by year(fecha_certificacion), month(fecha_certificacion) \n"
                    + "order by year(fecha_certificacion), month(fecha_certificacion) asc;";

            if (year > 0) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, year);
            } else {
                ps = con.prepareStatement(sql1);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dataset.addValue(rs.getInt("Total"), "Certificados", DateTools.obtenerNombreMes(rs.getInt("Mes")));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Total de Certificaciones Mensuales " + year, // Título del gráfico
                "Mes", // Etiqueta del eje X
                "Total", // Etiqueta del eje Y
                dataset, // Conjunto de datos
                PlotOrientation.HORIZONTAL, // Orientación del gráfico
                true, // Incluir leyenda
                true, // Incluir tooltips
                false // Incluir URLs
        );

        // Crear un marco para mostrar el gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                // Obtener la entidad seleccionada (en este caso, la barra)
                if (event.getEntity() instanceof CategoryItemEntity) {
                    CategoryItemEntity entity = (CategoryItemEntity) event.getEntity();
                    Comparable colum = entity.getColumnKey();
                    String mes = colum.toString();
                    DesignTabla.designPersonalCertificado(frm, year, DateTools.obtenerNumeroMes(mes), null, null, null, 0);
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {

            }

        });

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(200, 200));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica_certificados.removeAll(); // Limpiar el panel existente
        frm.grafica_certificados.setLayout(new BorderLayout());
        frm.grafica_certificados.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica_certificados.revalidate();
        frm.grafica_certificados.repaint();
    }

    public static void ChartCertificadosB(IFrmCapacitacion frm, int year, String area, String puesto, String turno, int i) {
        Conexion conn = new Conexion();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        JFreeChart chart = null;
        CategoryPlot plot = null;
        BarRenderer renderer = null;

        // Crear un marco para mostrar el gráfico
        ChartPanel chartPanel = null;

        try (Connection con = conn.getConnection()) {
            String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

            PreparedStatement ps = con.prepareStatement(mode);
            ps.executeUpdate();

            switch (i) {
                case 1:
                    String sql = "SELECT YEAR(fecha_termino) AS Año, MONTH(fecha_termino) AS Mes, COUNT(fecha_termino) AS Total\n"
                            + "FROM view_asistentes_certificados\n"
                            + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_Trabajador=view_lbu.folio_trabajador\n"
                            + "WHERE YEAR(fecha_termino) = YEAR(CURDATE()) AND view_asistentes_certificados.nombre_Area = ?\n"
                            + "GROUP BY MONTH(fecha_termino) ORDER BY MONTH(fecha_termino);";

                    ps = con.prepareStatement(sql);
                    ps.setString(1, area);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            dataset.addValue(rs.getInt("Total"), "Certificados",
                                    DateTools.obtenerNombreMes(rs.getInt("Mes")));
                        }

                    }
                    chart = ChartFactory.createBarChart(
                            "Total de Certificaciones Mensuales " + year, // Título del gráfico
                            "Mes", // Etiqueta del eje X
                            "Total", // Etiqueta del eje Y
                            dataset, // Conjunto de datos
                            PlotOrientation.HORIZONTAL, // Orientación del gráfico
                            true, // Incluir leyenda
                            true, // Incluir tooltips
                            false // Incluir URLs
                    );

                    plot = (CategoryPlot) chart.getPlot();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.BLUE);

                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            // Obtener la entidad seleccionada (en este caso, la barra)
                            if (event.getEntity() instanceof CategoryItemEntity) {
                                CategoryItemEntity entity = (CategoryItemEntity) event.getEntity();
                                Comparable colum = entity.getColumnKey();
                                String mes = colum.toString();
                                DesignTabla.designPersonalCertificado(frm, year, DateTools.obtenerNumeroMes(mes), area, null, null, 1);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {

                        }

                    });

                    break;
                case 2:
                    String sql1 = "SELECT YEAR(fecha_termino) AS Año, MONTH(fecha_termino) AS Mes, COUNT(fecha_termino) AS Total\n"
                            + "FROM view_asistentes_certificados\n"
                            + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_Trabajador=view_lbu.folio_trabajador\n"
                            + "WHERE YEAR(fecha_termino) = YEAR(CURDATE()) AND  view_asistentes_certificados.nombre_area = ? AND view_asistentes_certificados.nombre_turno=?\n"
                            + "GROUP BY MONTH(fecha_termino) ORDER BY MONTH(fecha_termino);";

                    ps = con.prepareStatement(sql1);
                    ps.setString(1, area);
                    ps.setString(2, turno);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            dataset.addValue(rs.getInt("Total"), "Certificados",
                                    DateTools.obtenerNombreMes(rs.getInt("Mes")));
                        }
                    }
                    chart = ChartFactory.createBarChart(
                            "Certificaciones", // Título del gráfico
                            "Mes", // Etiqueta del eje X
                            "Total", // Etiqueta del eje Y
                            dataset, // Conjunto de datos
                            PlotOrientation.HORIZONTAL, // Orientación del gráfico
                            true, // Incluir leyenda
                            true, // Incluir tooltips
                            false // Incluir URLs
                    );

                    plot = (CategoryPlot) chart.getPlot();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.BLUE);

                    plot = (CategoryPlot) chart.getPlot();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.BLUE);

                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            // Obtener la entidad seleccionada (en este caso, la barra)
                            if (event.getEntity() instanceof CategoryItemEntity) {
                                CategoryItemEntity entity = (CategoryItemEntity) event.getEntity();
                                Comparable colum = entity.getColumnKey();
                                String mes = colum.toString();
                                DesignTabla.designPersonalCertificado(frm, year, DateTools.obtenerNumeroMes(mes), area, null, turno, 2);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {

                        }

                    });

                    break;
                case 3:
                    String sql3 = "SELECT YEAR(fecha_termino) AS Año, MONTH(fecha_termino) AS Mes, COUNT(fecha_termino) AS Total\n"
                            + "FROM view_asistentes_certificados\n"
                            + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_Trabajador=view_lbu.folio_trabajador\n"
                            + "WHERE YEAR(fecha_termino) = YEAR(CURDATE()) AND  view_asistentes_certificados.nombre_area = ? AND view_asistentes_certificados.nombre_Puesto=?\n"
                            + "GROUP BY MONTH(fecha_termino) ORDER BY MONTH(fecha_termino);";

                    ps = con.prepareStatement(sql3);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            dataset.addValue(rs.getInt("Total"), "Certificados",
                                    DateTools.obtenerNombreMes(rs.getInt("Mes")));
                        }
                    }
                    chart = ChartFactory.createBarChart(
                            "Certificaciones", // Título del gráfico
                            "Mes", // Etiqueta del eje X
                            "Total", // Etiqueta del eje Y
                            dataset, // Conjunto de datos
                            PlotOrientation.HORIZONTAL, // Orientación del gráfico
                            true, // Incluir leyenda
                            true, // Incluir tooltips
                            false // Incluir URLs
                    );

                    plot = (CategoryPlot) chart.getPlot();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.BLUE);

                    chartPanel = new ChartPanel(chart);

                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            // Obtener la entidad seleccionada (en este caso, la barra)
                            if (event.getEntity() instanceof CategoryItemEntity) {
                                CategoryItemEntity entity = (CategoryItemEntity) event.getEntity();
                                Comparable colum = entity.getColumnKey();
                                String mes = colum.toString();
                                DesignTabla.designPersonalCertificado(frm, year, DateTools.obtenerNumeroMes(mes), area, puesto, null, 3);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {

                        }

                    });

                    break;
                case 4:
                    String sql4 = "SELECT YEAR(fecha_termino) AS Año, MONTH(fecha_termino) AS Mes, COUNT(fecha_termino) AS Total\n"
                            + "FROM view_asistentes_certificados\n"
                            + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_Trabajador=view_lbu.folio_trabajador\n"
                            + "WHERE YEAR(fecha_termino) = YEAR(CURDATE()) AND  view_asistentes_certificados.nombre_area = ? "
                            + "AND view_asistentes_certificados.nombre_puesto=? AND view_asistentes_certificados.nombre_turno=?\n"
                            + "GROUP BY MONTH(fecha_termino) ORDER BY MONTH(fecha_termino);";

                    ps = con.prepareStatement(sql4);
                    ps.setString(1, area);
                    ps.setString(2, puesto);
                    ps.setString(3, turno);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            dataset.addValue(rs.getInt("Total"), "Certificados",
                                    DateTools.obtenerNombreMes(rs.getInt("Mes")));
                        }
                    }
                    chart = ChartFactory.createBarChart(
                            "Certificaciones", // Título del gráfico
                            "Mes", // Etiqueta del eje X
                            "Total", // Etiqueta del eje Y
                            dataset, // Conjunto de datos
                            PlotOrientation.HORIZONTAL, // Orientación del gráfico
                            true, // Incluir leyenda
                            true, // Incluir tooltips
                            false // Incluir URLs
                    );

                    plot = (CategoryPlot) chart.getPlot();
                    renderer = (BarRenderer) plot.getRenderer();
                    renderer.setSeriesPaint(0, Color.BLUE);

                    chartPanel = new ChartPanel(chart);
                    chartPanel.addChartMouseListener(new ChartMouseListener() {
                        @Override
                        public void chartMouseClicked(ChartMouseEvent event) {
                            // Obtener la entidad seleccionada (en este caso, la barra)
                            if (event.getEntity() instanceof CategoryItemEntity) {
                                CategoryItemEntity entity = (CategoryItemEntity) event.getEntity();
                                Comparable colum = entity.getColumnKey();
                                String mes = colum.toString();
                                DesignTabla.designPersonalCertificado(frm, year, DateTools.obtenerNumeroMes(mes), area, puesto, turno, 4);
                            }
                        }

                        @Override
                        public void chartMouseMoved(ChartMouseEvent event) {

                        }

                    });
                    break;
            }
            System.out.println(ps + "\n" + i);

        } catch (SQLException ex) {
            Logger.getLogger(Graphics_Capacitacion.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        // Crear un nuevo JPanel para contener la gráfica
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(200, 200));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Agregar el panel de gráfica al JPanel existente en la interfaz (frm.jPanel4)
        frm.grafica_certificados.removeAll(); // Limpiar el panel existente
        frm.grafica_certificados.setLayout(new BorderLayout());
        frm.grafica_certificados.add(chartContainer, BorderLayout.CENTER);

        // Actualizar el JPanel para que se muestre la gráfica
        frm.grafica_certificados.revalidate();
        frm.grafica_certificados.repaint();
    }
}
