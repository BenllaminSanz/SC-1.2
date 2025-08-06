package Tables;

import Model.HabilidadesCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableHabilidadesCurso extends AbstractTableModel {

    private List<HabilidadesCurso> cap = new ArrayList<>();
    private final String[] columnas = {"ID", "Orden", "Habilidad"};

    public TableHabilidadesCurso(List<HabilidadesCurso> cap) {
        this.cap = cap;
    }

    @Override
    public int getRowCount() {
        return cap.size();
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
                return cap.get(rowIndex).getIdHabilidad();
            case 1:
                return cap.get(rowIndex).getOrden_habilidad();
            case 2:
                return cap.get(rowIndex).getNombre_habilidad();
            default:
                return null;

        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
