import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartExample {
    public static void main(String[] args) {
        // Crear un conjunto de datos para la gráfica de pastel
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Manzanas", 45);
        dataset.setValue("Naranjas", 30);
        dataset.setValue("Plátanos", 25);

        // Crear la gráfica de pastel utilizando el conjunto de datos
        JFreeChart chart = ChartFactory.createPieChart(
                "Ejemplo de Gráfica de Pastel",
                dataset,
                true, // Mostrar leyenda
                true, // Mostrar tooltips
                false // No permitir interacción
        );

        // Crear un marco de la gráfica para mostrarla en una ventana
        ChartFrame frame = new ChartFrame("Gráfica de Pastel", chart);
        frame.pack();
        frame.setVisible(true);
    }
}

