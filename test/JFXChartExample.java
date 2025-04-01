import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class JFXChartExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear el eje de categorías (meses)
        CategoryAxis xAxis = new CategoryAxis();
        // Crear el eje de números (total de certificaciones)
        NumberAxis yAxis = new NumberAxis();
        
        // Configurar el eje Y para mostrar solo números enteros
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number value) {
                return String.format("%d", value.intValue());
            }
        });

        // Crear el gráfico de barras
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Certificaciones por Mes");
        xAxis.setLabel("Mes");
        yAxis.setLabel("Total de Certificaciones");

        // Crear una lista observable de datos para el gráfico
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        data.add(new XYChart.Data<>("Enero", 8));
        data.add(new XYChart.Data<>("Febrero", 16));
        data.add(new XYChart.Data<>("Marzo", 29));
        data.add(new XYChart.Data<>("Abril", 19));
        data.add(new XYChart.Data<>("Mayo", 13));

        // Crear una serie de datos y añadir los datos a la serie
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setData(data);

        // Añadir la serie al gráfico
        barChart.getData().add(series);

        // Crear la escena y mostrarla en la ventana
        Scene scene = new Scene(barChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gráfico de Certificaciones");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
