package Tables;

import Model.Brigadas;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableBrigadas extends AbstractTableModel {

    public List<Brigadas> trabajador = new ArrayList<>();
    private final String[] columnas = {"ID", "Nombre Brigada"};

    public TableBrigadas(List<Brigadas> tbr) {
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
                return trabajador.get(rowIndex).getIdBrigada();
            case 1:
                return trabajador.get(rowIndex).getNombre_Brigada();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
