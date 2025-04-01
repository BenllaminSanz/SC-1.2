import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartExample {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        // Crear un conjunto de datos de categoría
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Agregar valores al conjunto de datos
        dataset.addValue(10, "Entrenamiento 1", "Área 1");
        dataset.addValue(5, "Entrenamiento 2", "Área 1");
        dataset.addValue(3, "Entrenamiento 3", "Área 1");
        
        dataset.addValue(15, "Entrenamiento 1", "Área 2");
        dataset.addValue(8, "Entrenamiento 2", "Área 2");
        dataset.addValue(6, "Entrenamiento 3", "Área 2");
        
        dataset.addValue(8, "Entrenamiento 1", "Área 3");
        dataset.addValue(4, "Entrenamiento 2", "Área 3");
        dataset.addValue(2, "Entrenamiento 3", "Área 3");
        
        // Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfico de Barras de Entrenamiento",
                "Áreas",
                "Cantidad de personas",
                dataset
        );
        
        // Personalizar la apariencia del gráfico
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
//        renderer.setItemLabelsVisible(true);
        
        // Configurar el eje y para mostrar valores enteros
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        // Crear el marco del gráfico
        ChartFrame frame = new ChartFrame("Gráfico de Barras", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
