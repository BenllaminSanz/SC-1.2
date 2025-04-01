package Tables;

import Model.PersonalCurso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableCursosTrabajador extends AbstractTableModel {

    private List<PersonalCurso> TBR = new ArrayList<>();
    private final String[] columnas = {"ID", "Curso", "Estado", "Tipo", "Fecha Inicio", "Fecha Cierre"};

    public TableCursosTrabajador(List<PersonalCurso> tbr) {
        this.TBR = tbr;
    }

    @Override
    public int getRowCount() {
        return TBR.size();
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
                return TBR.get(rowIndex).getIdHistorial_Curso();
            case 1:
                return TBR.get(rowIndex).getNombre_Curso();
            case 2:
                return TBR.get(rowIndex).getEstado_curso();
            case 3:
                return TBR.get(rowIndex).getTipo_curso();
            case 4:
                return TBR.get(rowIndex).getFecha_inicio();
            case 5:
                return TBR.get(rowIndex).getFecha_cierre();
            default: return null;
        }
       
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
