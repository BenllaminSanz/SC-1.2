import javax.swing.*;
import javax.swing.table.*;

public class TablaConComboBox {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tabla con JComboBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear los datos de la tabla
        Object[][] datos = {
            { "Fila 1, Columna 1", "Fila 1, Columna 2", "Opción 1" },
            { "Fila 2, Columna 1", "Fila 2, Columna 2", "Opción 2" },
            { "Fila 3, Columna 1", "Fila 3, Columna 2", "Opción 3" },
            { "Fila 4, Columna 1", "Fila 4, Columna 2", "Opción 4" },
            { "Fila 5, Columna 1", "Fila 5, Columna 2", "Opción 5" }
        };

        // Crear las etiquetas de las columnas
        String[] etiquetas = { "Columna 1", "Columna 2", "Columna 3" };

        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(datos, etiquetas);

        // Crear el JComboBox
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Opción 1");
        comboBox.addItem("Opción 2");
        comboBox.addItem("Opción 3");
        comboBox.addItem("Opción 4");
        comboBox.addItem("Opción 5");

        // Crear la tabla y asignar el modelo y el JComboBox a la tercera columna
        JTable tabla = new JTable(modelo);
        TableColumn columnaComboBox = tabla.getColumnModel().getColumn(2);
        columnaComboBox.setCellEditor(new DefaultCellEditor(comboBox));

        // Crear un panel con la tabla
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(tabla));

        // Añadir el panel al frame y mostrar el frame
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
