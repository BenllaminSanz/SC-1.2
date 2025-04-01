package Tables;

import Model.AsistenteCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableAsistentesCurso extends AbstractTableModel {

    private List<AsistenteCurso> TBR = new ArrayList<>();
    private final String[] columnas = {"ID", "Nombre", "Puesto", "Turno", "Entrenamiento", "Status"};

    public TableAsistentesCurso(List<AsistenteCurso> tbr) {
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
                return TBR.get(rowIndex).getId();
            case 1:
                return TBR.get(rowIndex).getNombre();
            case 2:
                return TBR.get(rowIndex).getPuesto();
            case 3:
                return TBR.get(rowIndex).getTurno();
            case 4:
                return TBR.get(rowIndex).getEntrenamiento();
            case 5:
                return TBR.get(rowIndex).getStatus();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
