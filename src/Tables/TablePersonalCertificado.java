package Tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablePersonalCertificado extends AbstractTableModel {

    private List<Model.PersonalCertificado> TBR = new ArrayList<>();
    private final String[] columnas = {"Folio", "Nombre", "Certificación", "Tipo","Fecha de Termino"};

    public TablePersonalCertificado(List<Model.PersonalCertificado> tbr) {
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
                return TBR.get(rowIndex).getIdTrabajador_Certificado();
            case 1:
                return TBR.get(rowIndex).getNombre_Trabajador();
            case 2:
                return TBR.get(rowIndex).getNombre_Certificado();
            case 3:
                return TBR.get(rowIndex).getTipo_certificado();
            case 4:
                return TBR.get(rowIndex).getFecha_certificacion();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}

