import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;

public class ExcelLikeTableSorterDemo {
    public static void main(String[] args) {
        // Crear la tabla
        MyTableModel model = new MyTableModel();
        JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(400, 200));
        table.setModel(model);
        // Crear el TableRowSorter
        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(model);
        table.setRowSorter(sorter);

        // Personalizar el comparador del sorter
        sorter.setComparator(0, new AlphanumericComparator());

        // Mostrar la tabla en una ventana
        JFrame frame = new JFrame("Excel-Like Table Sorter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }

    static class MyTableModel extends AbstractTableModel {
        private final Object[][] data = {
                {"B", "John"},
                {"C", "Jane"},
                {"A", "David"},
                {"A", "Alice"},
                {"B", "Bob"},
                {"C", "Carol"}
        };

        private final String[] columnNames = {"Column 1", "Column 2"};

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

    static class AlphanumericComparator implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = o1.toString();
            String s2 = o2.toString();
            return s1.compareToIgnoreCase(s2);
        }
    }
}
