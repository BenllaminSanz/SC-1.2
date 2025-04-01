
package Tables;

import Model.Certificado;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TableCertificado extends AbstractTableModel {

    private List<Certificado> cap = new ArrayList<>();
    private final String[] columnas = {"Certificado"};

    public TableCertificado(List<Certificado> cap) {
        this.cap = cap;
    }

    @Override
    public int getRowCount() {
        return cap.size();
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
                return cap.get(rowIndex).getNombre_Certificado();
            default:  return null;
        }
       
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}


