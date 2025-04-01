
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TwoLevelPieChartExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Datos para las categorías
        ObservableList<PieChart.Data> categoryData = FXCollections.observableArrayList(
                new PieChart.Data("Categoría 1", 30),
                new PieChart.Data("Categoría 2", 20),
                new PieChart.Data("Categoría 3", 50)
        );

        // Datos para las subcategorías
        ObservableList<PieChart.Data> subcategoryData = FXCollections.observableArrayList(
                new PieChart.Data("Subcategoría 1", 15),
                new PieChart.Data("Subcategoría 2", 10),
                new PieChart.Data("Subcategoría 3", 5)
        );

        // Crear la gráfica de pastel para las categorías
        PieChart categoryChart = new PieChart(categoryData);
        categoryChart.setPrefSize(300, 300);
        categoryChart.setTitle("Categorías");

        // Crear la gráfica de pastel para las subcategorías
        PieChart subcategoryChart = new PieChart(subcategoryData);
        subcategoryChart.setPrefSize(500, 500);
        subcategoryChart.setTitle("Subcategorías");

        // Ajustar los colores de las subcategorías
        for (PieChart.Data data : subcategoryData) {
            data.getNode().setStyle("-fx-pie-color: #999999;");
        }

        // Contenedor para las gráficas de pastel
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.getChildren().addAll(categoryChart, subcategoryChart);

        // Apilar las gráficas en un contenedor HBox
        HBox hbox = new HBox(root);
        hbox.setAlignment(Pos.CENTER);

        // Mostrar la escena que contiene las gráficas
        Scene scene = new Scene(hbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
