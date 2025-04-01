import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ModernPieChartExample extends Application {

    @Override
    public void start(Stage primaryStage) {
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

        // Crear un componente SwingNode para integrar la gráfica en JavaFX
        SwingNode chartNode = new SwingNode();
        chartNode.setContent(new ChartPanel(chart));

        // Crear el layout de la interfaz utilizando JavaFX
        StackPane root = new StackPane();
        root.getChildren().add(chartNode);

        // Crear la escena y mostrar la ventana
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Gráfica de Pastel Moderna");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
