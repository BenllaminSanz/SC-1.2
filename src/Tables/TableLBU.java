package Tables;

import Model.LBU;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableLBU extends AbstractTableModel {

    private List<LBU> LBU = new ArrayList<>();
    private final String[] columnas = {"Area", "Supervisor", "Puesto", "Position", "Propuesto",
        "Turno", "Folio", "Nombre"};

    public TableLBU(List<LBU> lbu) {
        this.LBU = lbu;
    }

    @Override
    public int getRowCount() {
        return LBU.size();
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
                return LBU.get(rowIndex).getNombre_Area();
            case 1:
                return LBU.get(rowIndex).getNombre_Supervisor();
            case 2:
                return LBU.get(rowIndex).getNombre_Puesto();
            case 3:
                return LBU.get(rowIndex).getNombre_Puesto_Ingles();
            case 4:
                return LBU.get(rowIndex).getPropuesto_Trabajadores();
            case 5:
                return LBU.get(rowIndex).getNombre_Turno();
            case 6:
                return LBU.get(rowIndex).getFolio_Trabajador();
            case 7:
                return LBU.get(rowIndex).getNombre_Trabajador();
            default:
                return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
