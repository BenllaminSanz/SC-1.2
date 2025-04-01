//Clase con los metodos para cambiar el formato de fechas
package Functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class DateTools {
    public static String ExceltoMySQLfromCell(Cell cell) {
        if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
            return null;
        }

        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                // Si la celda es numérica, se asume que es un valor de fecha
                Date fecha = cell.getDateCellValue();
                return formatoSalida.format(fecha);
            } else if (cell.getCellTypeEnum() == CellType.STRING) {
                String cellValue = cell.getStringCellValue().trim();
                SimpleDateFormat formatoEntrada1 = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoEntrada2 = new SimpleDateFormat("dd-MMM-yyyy");
                Date fecha;
                
                try {
                    fecha = formatoEntrada1.parse(cellValue);
                } catch (Exception e1) {
                    try {
                        fecha = formatoEntrada2.parse(cellValue);
                    } catch (Exception e2) {
                        Logger.getLogger(DateTools.class.getName()).log(Level.SEVERE, "Formato de fecha no soportado: " + cellValue, e2);
                        return null;
                    }
                }
                return formatoSalida.format(fecha);
            }
        } catch (Exception e) {
            Logger.getLogger(DateTools.class.getName()).log(Level.SEVERE, "Error al procesar la celda: " + cell.toString(), e);
        }
        
        return null;
    }

    //Metodo para adaptar las fechas en el formato de Excel a MySQL
    public static String StringtoMySQL(String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            try {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaD = formatoEntrada.parse(fecha);
                return formatoSalida.format(fechaD);
            } catch (ParseException ex) {
                Logger.getLogger(DateTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //Metodo para adaptar las fechas en el formato de Excel a MySQL
    public static String StringtoDoc(String fecha) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
            Date fechaD = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaD);
        } catch (ParseException ex) {
            Logger.getLogger(DateTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Metodo para adaptar las fechas en el formato de Excel a MySQL
    public static Date StringtoDatePDF(String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            try {
                SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
                return formatoSalida.parse(fecha);
            } catch (ParseException ex) {
                Logger.getLogger(DateTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //Metodo para adaptar las fechas en el formato de MySQL a de uso en la empresa
    public static String DateLocaltoString(String fecha) {
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd-MM-yyyy");
            Date fechaD = formatoEntrada.parse(fecha);
            return formatoSalida.format(fechaD);
        } catch (ParseException ex) {
            Logger.getLogger(DateTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Metodo para adaptar las fechas en el formato de MySQL a de uso en la empresa
    public static String MySQLtoString(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
            String fechaStr = formatoSalida.format(fecha);
            return fechaStr;
        } else {
            return null;
        }
    }

    public static String FechaActualTexto() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatFecha.format(fecha);
    }

    public static String FechaActualDoc() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        return formatFecha.format(fecha);
    }

    public static Date FechaActualDocD() {
        Date fecha = new Date();
        return fecha;
    }

    public static String FechaActualSQL() {
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy-MM-dd");
        return formatFecha.format(fecha);
    }

    public static String calcularFechas(int num, String Fecha) throws ParseException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = formatoFecha.parse(Fecha);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaDate);
        int[] dias = {6, 0, 1, 2, 3, 4, 5};
        int dia = dias[cal.get(Calendar.DAY_OF_WEEK) - 1];
        int desplazamiento = num - 1 - dia;
        cal.add(Calendar.DATE, desplazamiento);
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaD = formatFecha.format(cal.getTime());
        return fechaD;
    }

    public static String obtenerNombreMes(int mes) {
        switch (mes) {
            case 1:
                return "enero";
            case 2:
                return "febrero";
            case 3:
                return "marzo";
            case 4:
                return "abril";
            case 5:
                return "mayo";
            case 6:
                return "junio";
            case 7:
                return "julio";
            case 8:
                return "agosto";
            case 9:
                return "septiembre";
            case 10:
                return "octubre";
            case 11:
                return "noviembre";
            case 12:
                return "diciembre";
            default:
                return "";
        }
    }

    public static int obtenerNumeroMes(String mes) {
        switch (mes) {
            case "enero":
                return 1;
            case "febrero":
                return 2;
            case "marzo":
                return 3;
            case "abril":
                return 4;
            case "mayo":
                return 5;
            case "junio":
                return 6;
            case "julio":
                return 7;
            case "agosto":
                return 8;
            case "septiembre":
                return 9;
            case "octubre":
                return 10;
            case "noviembre":
                return 11;
            case "diciembre":
                return 12;
            default:
                return 0;
        }
    }
}
