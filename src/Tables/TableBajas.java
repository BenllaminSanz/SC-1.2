package Tables;

import Model.Bajas;
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

public class TableBajas extends AbstractTableModel {

    private List<Bajas> bajas = new ArrayList<>();
    private final String[] columnas = {"Nomina", "Nombre", "Fecha Baja", "Comentario",
        "Ingreso", "Área", "Puesto", "Supervisor", "Turno", "Salario"};

    public TableBajas(List<Bajas> tbr) {
        this.bajas = tbr;
    }

    @Override
    public int getRowCount() {
        return bajas.size();
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
                return bajas.get(rowIndex).getFolio_Trabajador();
            case 1:
                return bajas.get(rowIndex).getNombre_Trabajador();
            case 2:
                return bajas.get(rowIndex).getFecha_Baja();
            case 3:
                return bajas.get(rowIndex).getComentario();
            case 4:
                return bajas.get(rowIndex).getFecha_Antiguedad();
            case 5:
                return bajas.get(rowIndex).getNombre_Area();
            case 6:
                return bajas.get(rowIndex).getNombre_Puesto();
            case 7:
                return bajas.get(rowIndex).getNombre_Supervisor();
            case 8:
                return bajas.get(rowIndex).getNombre_Turno();
            case 9:
                return bajas.get(rowIndex).getSalarioDiario_Trabajador();
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public String getToolTipText(int rowIndex, int columnIndex) {
        Bajas b = bajas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return b.getFolio_Trabajador();
            case 1:
                return b.getNombre_Trabajador();
            case 2:
                return b.getFecha_Baja().toString();
            case 3:
                return b.getComentario();
            case 4:
                return b.getFecha_Antiguedad().toString();
            case 5:
                return b.getNombre_Area();
            case 6:
                return b.getNombre_Puesto();
            case 7:
                return b.getNombre_Supervisor();
            case 8:
                return b.getNombre_Turno();
            case 9:
                return b.getSalarioDiario_Trabajador().toString();
            default:
                return "";
        }
    }

    public void setFechaSorter(JTable table, int columnIndex) {
        TableRowSorter<TableBajas> sorter = new TableRowSorter<>(this);
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
                    Logger.getLogger(TableBajas.class.getName()).log(Level.SEVERE, null, ex);
                }
                return 0;
            }
        };
        sorter.setComparator(columnIndex, fechaComparator);
    }
}
