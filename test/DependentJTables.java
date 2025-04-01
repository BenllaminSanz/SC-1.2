
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class DependentJTables extends JFrame {

    private JTable table1;
    private JTable table2;
    private JTable table3;

    public DependentJTables() {
        // Configuración de la ventana
        setTitle("JTables Dependientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        // Creación de los modelos de tabla
        final Table1Model table1Model = new Table1Model();
        final Table2Model table2Model = new Table2Model();
        final Table3Model table3Model = new Table3Model();

        // Creación de las tablas
        table1 = new JTable(table1Model);
        table2 = new JTable(table2Model);
        table3 = new JTable(table3Model);

        // Configuración de las tablas
        table1.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table2.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table3.setPreferredScrollableViewportSize(new Dimension(200, 200));

        // Agregar listener de selección a la tabla 1
        table1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedValue = (String) table1.getValueAt(selectedRow, 0);
                    table2Model.updateData(selectedValue);
                    table3Model.updateData(selectedValue);
                }
            }
        });

        // Agregar listener de selección a la tabla 2
        table2.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = table2.getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedValue = (String) table2.getValueAt(selectedRow, 0);
                    table3Model.updateData(selectedValue);
                }
            }
        });

        // Agregar las tablas a la ventana
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.add(new JScrollPane(table1));
        panel.add(new JScrollPane(table2));
        panel.add(new JScrollPane(table3));
        add(panel);

        // Mostrar la ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Modelo de tabla 1
    private class Table1Model extends AbstractTableModel {

        private final String[] columnNames = {"Col1"};

        private final Object[][] data = {
            {"A"},
            {"B"},
            {"C"}
        };

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
    }

    // Modelo de tabla 2
    private class Table2Model extends AbstractTableModel {

        private final String[] columnNames = {"Col2"};

        private final Object[][] data = {
            {"1"},
            {"2"},
            {"3"}
        };

        private Object[][] filteredData = data;

        @Override
        public int getRowCount() {
            return filteredData.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return filteredData[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void updateData(String selectedValue) {
            // Filtrar los datos según el valor seleccionado en la tabla 1
            filteredData = new Object[][]{{selectedValue + "1"}, {selectedValue + "2"}, {selectedValue + "3"}};
            fireTableDataChanged();
        }
    }

// Modelo de tabla 3
    private class Table3Model extends AbstractTableModel {

        private final String[] columnNames = {"Col3"};

        private Object[][] data = {};

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void updateData(String selectedValue) {
            // Actualizar los datos según el valor seleccionado en la tabla 1 y 2
            if (selectedValue.equals("A") || selectedValue.equals("B")) {
                data = new Object[][]{{selectedValue + "1"}, {selectedValue + "2"}};
            } else {
                data = new Object[][]{{selectedValue + "3"}};
            }
            fireTableDataChanged();
        }
    }

// Método principal para ejecutar el programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DependentJTables::new);
    }
}
