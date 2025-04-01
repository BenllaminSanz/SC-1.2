package Tables;

import Model.PersonalCertificado;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

public class TableAsistentesCertificados extends AbstractTableModel {

    public List<PersonalCertificado> TBR = new ArrayList<>();
    private final String[] columnas = {"Certificado", "ID", "Folio", "Nombre", "Certificación", "Tipo"};

    public TableAsistentesCertificados(List<PersonalCertificado> tbr) {
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
                return TBR.get(rowIndex).getNombre_Certificado();
            case 1:
                return TBR.get(rowIndex).getIdCertificacion();
            case 2:
                return TBR.get(rowIndex).getIdTrabajador_Certificado();
            case 3:
                return TBR.get(rowIndex).getNombre_Trabajador();
            case 4:
                return TBR.get(rowIndex).getFecha_certificacion();
            case 5:
                return TBR.get(rowIndex).getTipo_certificado();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setFechaSorter(JTable table, int columnIndex) {
        TableRowSorter<TableAsistentesCertificados> sorter = new TableRowSorter<>(this);
        table.setRowSorter(sorter);

        Comparator<String> fechaComparator = new Comparator<String>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public int compare(String fecha1, String fecha2) {

                try {
                    Date date1 = dateFormat.parse(fecha1);
                    Date date2 = dateFormat.parse(fecha2);
                    return date1.compareTo(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(TableAsistentesCertificados.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        };
        sorter.setComparator(columnIndex, fechaComparator);
    }
}
