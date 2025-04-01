//Creacion de modelo de las tabla trabajador
package Tables;

import Model.Trabajador;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class TableTrabajador extends AbstractTableModel {

    public List<Trabajador> trabajador = new ArrayList<>();
    private final String[] columnas = {"Nomina", "Nombre", "CURP", "RFC",
        "Ingreso", "Supevisor", "Área", "Turno", "Puesto", "Salario", "Nivel", "Sexo"};

    public TableTrabajador(List<Trabajador> tbr) {
        this.trabajador = tbr;
    }

    @Override
    public int getRowCount() {
        return trabajador.size();
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
                return trabajador.get(rowIndex).getFolio_Trabajador();
            case 1:
                return trabajador.get(rowIndex).getNombre_Trabajador();
            case 2:
                return trabajador.get(rowIndex).getCURP_Trabajador();
            case 3:
                return trabajador.get(rowIndex).getRFC_Trabajador();
            case 4:
                return trabajador.get(rowIndex).getFecha_Antiguedad();
            case 5:
                return trabajador.get(rowIndex).getNombre_Supervisor();
            case 6:
                return trabajador.get(rowIndex).getNombre_Area();
            case 7:
                return trabajador.get(rowIndex).getNombre_Turno();
            case 8:
                return trabajador.get(rowIndex).getNombre_Puesto();
            case 9:
                return trabajador.get(rowIndex).getSalarioDiario_Trabajador();
            case 10:
                return trabajador.get(rowIndex).getNivel_Salario();
            case 11:
                return trabajador.get(rowIndex).getSexo_Trabajador();
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
                int modelRow = table.convertRowIndexToModel(row);
                Trabajador t = trabajador.get(modelRow);
                String estado = t.getStatus_Trabajador();
                switch (estado) {
                    case "Activo":
                        c.setBackground(table.getBackground());
                        break;
                    case "Incapacidad":
                        c.setBackground(new Color(255, 204, 0));
                        break;
                    case "Incapacidad por Accidente":
                        c.setBackground(new Color(153, 0, 0));
                        break;
                    case "Incapacidad por Maternidad":
                        c.setBackground(new Color(255, 153, 204));
                        break;
                    case "Incapacidad por Accidente en Trayecto":
                        c.setBackground(new Color(255, 102, 0));
                        break;
                    case "Incapacidad por Enfermedad":
                        c.setBackground(new Color(0, 102, 0));
                        break;
                    case "Baja Tentativa":
                        c.setBackground(new Color(02, 102, 102));
                        break;
                    case "Lactancia":
                        c.setBackground(new Color(102, 204, 255));
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

    public void setFechaSorter(JTable table, int columnIndex) {
        TableRowSorter<TableTrabajador> sorter = new TableRowSorter<>(this);
        table.setRowSorter(sorter);

        Comparator<String> fechaComparator = new Comparator<String>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(String fecha1, String fecha2) {

                try {
                    Date date1 = dateFormat.parse(fecha1);
                    Date date2 = dateFormat.parse(fecha2);
                    return date1.compareTo(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(TableTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        };
        sorter.setComparator(columnIndex, fechaComparator);
    }
}
