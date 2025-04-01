import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;

public class CoverageChart {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowChart());
    }

    private static void createAndShowChart() {
        // Datos de ejemplo (puedes reemplazarlos con tus propios datos)
        int personalRequerido = 100;
        int personalActual = 80;
        int personalFaltante = personalRequerido - personalActual;

        // Crear un conjunto de datos para el gráfico de barras
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(personalActual, "Personal Actual", "");
        dataset.setValue(personalFaltante, "Personal Faltante", "");

        // Crear el gráfico de barras utilizando el conjunto de datos
        JFreeChart chart = ChartFactory.createBarChart(
                "Cobertura de Personal",
                "", // Etiqueta del eje x
                "Cantidad", // Etiqueta del eje y
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false
        );

        // Personalizar el gráfico de barras
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 153, 0));  // Personal Actual (verde)
        renderer.setSeriesPaint(1, Color.RED);  // Personal Faltante (rojo)

        // Configurar el eje y (vertical) para que solo muestre números enteros
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // Crear el panel de gráfico
        ChartPanel chartPanel = new ChartPanel(chart);

        // Crear la ventana
        JFrame frame = new JFrame("Gráfico de Cobertura de Personal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(chartPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
