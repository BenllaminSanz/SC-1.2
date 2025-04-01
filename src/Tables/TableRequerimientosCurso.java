package Tables;

import Model.RequerimientosCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableRequerimientosCurso extends AbstractTableModel {

    private List<RequerimientosCurso> cap = new ArrayList<>();
    private final String[] columnas = {"ID", "Requerimiento", "Dirección del Documento"};

    public TableRequerimientosCurso(List<RequerimientosCurso> cap) {
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
                return cap.get(rowIndex).getIdRequerimiento();
            case 1:
                return cap.get(rowIndex).getNombre_requerimiento();
            case 2:
                return cap.get(rowIndex).getRuta_Docuemento();
            default:return null;

        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
