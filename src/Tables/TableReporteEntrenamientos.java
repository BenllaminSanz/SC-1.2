package Tables;

import Model.PersonalCertificado;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TableReporteEntrenamientos extends AbstractTableModel {

    public List<PersonalCertificado> TBR = new ArrayList<>();
    private final String[] columnas = {"Nomina", "Nombre", "Ingreso", "Area", "Puesto", "Turno", "Entrenamiento", "Estado"};

    public TableReporteEntrenamientos(List<PersonalCertificado> tbr) {
        this.TBR = tbr;
    }

    @Override
    public int getRowCount() {
        return TBR.size();
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
                return TBR.get(rowIndex).getIdTrabajador_Certificado();
            case 1:
                return TBR.get(rowIndex).getNombre_Trabajador();
            case 2:
                return TBR.get(rowIndex).getFecha_inicio();
            case 3:
                return TBR.get(rowIndex).getArea();
            case 4:
                return TBR.get(rowIndex).getPuesto();
            case 5:
                return TBR.get(rowIndex).getTurno();
            case 6:
                return TBR.get(rowIndex).getEstado_entrenamiento();
            case 7:
                return TBR.get(rowIndex).getEstado_certificado();
            default:return null;
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
                PersonalCertificado t = TBR.get(modelRow);
                String entrenamiento = t.getEstado_entrenamiento();
                String certificado = t.getEstado_certificado();

                switch (entrenamiento) {
                    case "En Entrenamiento":
                        c.setBackground(new Color(255, 255, 153));
                        break;
                    case "En Entrenamiento en otro Puesto":
                        c.setBackground(new Color(204, 153, 255));
                        break;
                    default:
                        switch (certificado) {
                            case "Certificado":
                                c.setBackground(new Color(140, 219, 140));
                                break;
                            case "Recertificado":
                                c.setBackground(new Color(140, 219, 140));
                                break;
                            case "No certificado":
                                c.setBackground(new Color(255, 128, 128));
                                break;
                            case "Obsoleto":
                                c.setBackground(new Color(153, 153, 255));
                                break;
                            default:
                                // Si no coincide con ninguno de los casos anteriores, se utiliza el color de fondo predeterminado de la tabla
                                c.setBackground(table.getBackground());
                                break;
                        }
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
