package Tables;

import Model.Area;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableAreas extends AbstractTableModel {

    private List<Area> area = new ArrayList<>();
    private final String[] columnas = {"Nombre Área"};

    public TableAreas(List<Area> tbr) {
        this.area = tbr;
    }

    @Override
    public int getRowCount() {
        return area.size();
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
                return area.get(rowIndex).getNombre_Area();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public String getToolTipText(int rowIndex, int columnIndex) {
        Area a = area.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getNombre_Area();
            default:
                return "";
        }
    }
}
