package Tables;

import Model.AsistenteCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableAsistentesSupervisor extends AbstractTableModel {

    private List<AsistenteCurso> supervisor = new ArrayList<>();
    private final String[] columnas = {"Supervisor", "Área"};

    public TableAsistentesSupervisor(List<AsistenteCurso> tbr) {
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
                return supervisor.get(rowIndex).getNombre();
            case 1:
                return supervisor.get(rowIndex).getPuesto();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
