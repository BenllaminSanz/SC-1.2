package Tables;

import Model.Puesto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablePuestosCertificado extends AbstractTableModel {

    private List<Puesto> pst = new ArrayList<>();
    private final String[] columnas = {"Puesto", "Position", "Area"};

    public TablePuestosCertificado(List<Puesto> tbr) {
        this.pst = tbr;
    }

    @Override
    public int getRowCount() {
        return pst.size();
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
                return pst.get(rowIndex).getNombre_Puesto();
            case 1:
                return pst.get(rowIndex).getNombre_Puesto_Ingles();
            case 2:
                return pst.get(rowIndex).getArea_NombreArea();
            default: return null;
        }
       
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
