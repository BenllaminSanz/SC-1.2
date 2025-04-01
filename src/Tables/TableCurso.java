
package Tables;

import Model.Curso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableCurso extends AbstractTableModel {

    private List<Curso> cap = new ArrayList<>();
    private final String[] columnas = {"Curso", "Tipo"};

    public TableCurso(List<Curso> cap) {
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
                return cap.get(rowIndex).getNombre_Curso();
            case 1:
                return cap.get(rowIndex).getNombre_Tipo_Curso();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}

