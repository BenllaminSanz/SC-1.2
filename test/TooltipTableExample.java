import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TooltipTableExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tooltip Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable(new DefaultTableModel(
                new Object[][]{
                        {"Item 1", 10},
                        {"Item 2", 5},
                        {"Item 3", 8}
                },
                new Object[]{"Nombre", "Cantidad"}
        ));

        table.setPreferredScrollableViewportSize(new Dimension(200, 80));

        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int column = table.columnAtPoint(point);
                table.setToolTipText((String) table.getValueAt(row, column));
            }
        });

        frame.getContentPane().add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}
