package Tables;

import Model.Puesto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablePuestoSupervisor extends AbstractTableModel {

    private List<Puesto> puesto = new ArrayList<>();
    private final String[] columnas = {"Puesto", "Position"};

    public TablePuestoSupervisor(List<Puesto> tbr) {
        this.puesto = tbr;
    }

    @Override
    public int getRowCount() {
        return puesto.size();
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
                return puesto.get(rowIndex).getNombre_Puesto();
            case 1:
                return puesto.get(rowIndex).getNombre_Puesto_Ingles();
            default: return null;
        }
       
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
