package Tables;

import Model.Supervisor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableSupervisor extends AbstractTableModel{

    private List<Supervisor> supervisor = new ArrayList<>();
    private final String[] columnas = {"Supervisor", "Propuesto", "Area", "Turno"};

    public TableSupervisor(List<Supervisor> tbr) {
        this.supervisor = tbr;
    }

    @Override
    public int getRowCount() {
        return supervisor.size();
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
                return supervisor.get(rowIndex).getNombre_supervisor();
            case 1:
                return supervisor.get(rowIndex).getPropuesto_trabajadores();
            case 2:
                return supervisor.get(rowIndex).getNombre_area();
            case 3:
                return supervisor.get(rowIndex).getNombre_turno();
            default:
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
