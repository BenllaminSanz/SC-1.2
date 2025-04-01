package Tables;

import Model.LBU;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableLBUTrabajador extends AbstractTableModel {

    public List<LBU> LBU = new ArrayList<>();
    private final String[] columnas = {"Turno", "Nómina", "Nombre", "Estado"};

    public TableLBUTrabajador(List<LBU> lbu) {
        this.LBU = lbu;
    }

    @Override
    public int getRowCount() {
        return LBU.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return LBU.get(rowIndex).getNombre_Turno();
            case 1:
                return LBU.get(rowIndex).getFolio_Trabajador();
            case 2:
                return LBU.get(rowIndex).getNombre_Trabajador();
            case 3:
                return LBU.get(rowIndex).getStatus_trabajador();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public TableCellRenderer getStatusRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                LBU t = LBU.get(row);
                String estado = t.getStatus_trabajador();
                switch (estado) {
                    case "Activo":
                        c.setBackground(table.getBackground());
                        break;
                    case "Incapacidad":
                        c.setBackground(new Color(255, 153, 0));
                        break;
                    case "Baja Tentativa":
                        c.setBackground(Color.red);
                        break;
                    case "Maternidad":
                        c.setBackground(Color.cyan);
                        break;
                    case "Lactancia":
                        c.setBackground(Color.pink);
                        break;
                    case "Promoción":
                        c.setBackground(Color.blue);
                        break;
                    case "No listado":
                        c.setBackground(Color.gray);
                        break;
                    default:
                        c.setBackground(table.getBackground());
                        break;
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }

                if (c instanceof JComponent && value != null && !value.toString().isEmpty()) {
                    JComponent jc = (JComponent) c;
                    jc.setToolTipText(value.toString());
                }
                return c;
            }
        };
    }
}
