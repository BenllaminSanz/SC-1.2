package Tables;

import Model.Administrativos;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableAdministrativos extends AbstractTableModel {

    public List<Administrativos> trabajador = new ArrayList<>();
    private final String[] columnas = {"Nomina", "Nombre", "CURP", "RFC", "IMSS", "Ingreso", "Compañia", "Area", "Puesto", "Turno"};

    public TableAdministrativos(List<Administrativos> tbr) {
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
                return trabajador.get(rowIndex).getFolio_Trabajador();
            case 1:
                return trabajador.get(rowIndex).getNombre_Trabajador();
            case 2:
                return trabajador.get(rowIndex).getCURP_Trabajador();
            case 3:
                return trabajador.get(rowIndex).getRFC_Trabajador();
            case 4:
                return trabajador.get(rowIndex).getIMSS_Trabajador();
            case 5:
                return trabajador.get(rowIndex).getFecha_Antiguedad();
            case 6:
                return trabajador.get(rowIndex).getCia();
            case 7:
                return trabajador.get(rowIndex).getArea();
            case 8:
                return trabajador.get(rowIndex).getPuesto();
            case 9:
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
