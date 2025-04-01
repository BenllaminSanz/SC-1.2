package Tables;

import Model.HistorialCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public final class TableHistorialCursosTipo extends AbstractTableModel {

    private List<HistorialCurso> curso = new ArrayList<>();
    private final String[] columnas
            = {"ID", "Curso", "Fecha Inicio", "Fecha Estimada", "Fecha Cierre", "Estado"};

    public TableHistorialCursosTipo(List<HistorialCurso> tbr) {
        this.curso = tbr;

    }

    @Override
    public int getRowCount() {
        return curso.size();
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
                return curso.get(rowIndex).getIdHistorialCurso();
            case 1:
                return curso.get(rowIndex).getNombre_Curso();
            case 2:
                return curso.get(rowIndex).getFecha_inicio();
            case 3:
                return curso.get(rowIndex).getFecha_estimada();
            case 4:
                return curso.get(rowIndex).getFecha_cierre();
            case 5:
                return curso.get(rowIndex).getStatus_curso();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void clearData() {
        curso.clear();
    }
}
