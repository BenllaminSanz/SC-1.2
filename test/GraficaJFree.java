import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficaJFree {
    public static void main(String[] args) {
        // Crear un conjunto de datos de categorías
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Agregar los datos a la serie
        dataset.addValue(8, "Certificaciones", "Enero");
        dataset.addValue(16, "Certificaciones", "Febrero");
        dataset.addValue(29, "Certificaciones", "Marzo");
        dataset.addValue(19, "Certificaciones", "Abril");
        dataset.addValue(13, "Certificaciones", "Mayo");
        
        // Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
            "Certificaciones por Mes",     // Título del gráfico
            "Mes",                         // Etiqueta del eje x
            "Total",                       // Etiqueta del eje y
            dataset,                       // Datos
            PlotOrientation.VERTICAL,      // Orientación del gráfico
            true,                          // Mostrar leyenda
            true,                          // Usar tooltips
            false                          // Usar URLs
        );
        
        // Crear un marco para mostrar el gráfico
        ChartFrame frame = new ChartFrame("Gráfico de Certificaciones", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
