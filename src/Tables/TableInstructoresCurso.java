package Tables;

import Model.Trabajador;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableInstructoresCurso extends AbstractTableModel {

    private List<Trabajador> tbr = new ArrayList<>();
    private final String[] columnas = {"Folio", "Nombre", "Puesto", "Turno"};

    public TableInstructoresCurso(List<Trabajador> tbr) {
        this.tbr = tbr;
    }

    @Override
    public int getRowCount() {
        return tbr.size();
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
                return tbr.get(rowIndex).getFolio_Trabajador();
            case 1:
                return tbr.get(rowIndex).getNombre_Trabajador();
            case 2:
                return tbr.get(rowIndex).getNombre_Puesto();
            case 3:
                return tbr.get(rowIndex).getNombre_Turno();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
