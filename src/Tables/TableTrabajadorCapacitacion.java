package Tables;

import Model.AsistenteCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableTrabajadorCapacitacion extends AbstractTableModel {

    public List<AsistenteCurso> trabajador = new ArrayList<>();
    private final String[] columnas = {"Nomina", "Nombre", "Puesto", "Turno"};

    public TableTrabajadorCapacitacion(List<AsistenteCurso> tbr) {
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
                return trabajador.get(rowIndex).getId();
            case 1:
                return trabajador.get(rowIndex).getNombre();
            case 2:
                return trabajador.get(rowIndex).getPuesto();
            case 3:
                return trabajador.get(rowIndex).getTurno();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
