package Tables;

import Model.Brigadistas;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableBrigadistasBrigada extends AbstractTableModel {

    public List<Brigadistas> trabajador = new ArrayList<>();
    private final String[] columnas = {"Nomina", "Nombre", "Trabajador", "Área", "Puesto", "Turno"};

    public TableBrigadistasBrigada(List<Brigadistas> tbr) {
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
                return trabajador.get(rowIndex).getIdBrigadista();
            case 1:
                return trabajador.get(rowIndex).getNombre_Brigadista();
            case 2:
                return trabajador.get(rowIndex).getTipo_Brigadista();
            case 3:
                return trabajador.get(rowIndex).getArea_Brigadista();
            case 4:
                return trabajador.get(rowIndex).getPuesto_Brigadista();
            case 5:
                return trabajador.get(rowIndex).getTurno_Brigadista();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
