
import Querys.Conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CargarExcelTrabajador extends Conexion{

    public static PreparedStatement ps = null;

    public static ResultSet rs = null;
    public static Conexion conn = new Conexion();

    public static void main(String[] args) {
        cargar();
    }

    public static void cargar() {
        String sql = "INSERT INTO `sistema_capacitacion`.`curso` (`nombre_curso`) VALUES(?)";
        try(Connection con = conn.getConnection();){
            
            FileInputStream file = new FileInputStream(new File("Y:\\Capacitacion\\Benjamin\\certification.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet hoja = wb.getSheetAt(1);

            int numFilas = hoja.getLastRowNum();

            // a inicia en 0 por que no hay encabezados
            // Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 0; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);

                ps = con.prepareStatement(sql);
                ps.setString(1, fila.getCell(0).getStringCellValue());
                ps.execute();
            }
        } catch (IOException | SQLException ex) {
            System.err.println(ex);
            Logger.getLogger(CargarExcelTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargar1() throws IOException, SQLException, ParseException {

        Conexion con = new Conexion();
        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "INSERT INTO `sistema capacitación`.`area`(`Nombre_Area`)"
                + "VALUES (?);";

        try {
            Connection conn = con.getConnection();
            FileInputStream file = new FileInputStream(
                    new File("C:\\Users\\jesanchez\\Documents\\Data.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet hoja = wb.getSheetAt(2);

            int numFilas = hoja.getLastRowNum();

            //a inicia en 0 por que no hay encabezados
            //Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 0; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);

                ps = conn.prepareStatement(sql);
                ps.setString(1, fila.getCell(3).getStringCellValue());

                ps.execute();
            }

            conn.close();

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            Logger.getLogger(CargarExcelTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargar2() throws IOException, SQLException, ParseException {

        Conexion con = new Conexion();
        PreparedStatement ps;

        String sql = "INSERT INTO `sistema capacitación`.`puesto`(`Nombre_Puesto`,"
                + "`Nombre_Puesto_Ingles`,`Centro_de_Costo`,`area_idArea`)\n"
                + "VALUES (?,?,?,?);";

        try {
            Connection conn = con.getConnection();
            FileInputStream file = new FileInputStream(
                    new File("C:\\Users\\jesanchez\\Documents\\Data.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet hoja = wb.getSheetAt(0);

            int numFilas = hoja.getLastRowNum();

            //a inicia en 0 por que no hay encabezados
            //Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 0; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);

                ps = conn.prepareStatement(sql);
                ps.setString(1, fila.getCell(4).getStringCellValue());
                ps.setString(2, fila.getCell(3).getStringCellValue());
//                ps.setString(3, fila.getCell(2).getStringCellValue());
//                ps.setInt(3, (int) fila.getCell(2).getNumericCellValue());
                ps.setInt(3, (int) fila.getCell(1).getNumericCellValue());
                ps.setInt(4, (int) fila.getCell(6).getNumericCellValue());

                ps.execute();
            }

            conn.close();

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            Logger.getLogger(CargarExcelTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargar3() throws IOException, SQLException, ParseException {

        Conexion con = new Conexion();
        PreparedStatement ps;

        String sql = "INSERT INTO `sistema capacitación`.`trabajador`(`Folio_Trabajador`,`Nombre_Trabajador`,`Turno`)"
                + "VALUES (?,?,?);";

        try {
            Connection conn = con.getConnection();
            FileInputStream file = new FileInputStream(
                    new File("C:\\Users\\jesanchez\\Documents\\Data.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet hoja = wb.getSheetAt(2);

            int numFilas = hoja.getLastRowNum();

            //a inicia en 0 por que no hay encabezados
            //Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 0; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);

                ps = conn.prepareStatement(sql);
                ps.setString(1, fila.getCell(0).getStringCellValue());
                ps.setString(2, fila.getCell(1).getStringCellValue());
                ps.setInt(3, 1);

                ps.execute();
            }

            conn.close();

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            Logger.getLogger(CargarExcelTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargar4() throws IOException, SQLException, ParseException {

        Conexion con = new Conexion();
        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "INSERT INTO `sistema_capacitacion`.`capacitacion`(`Nombre_Capacitacion`)"
                + "VALUES (?);";

        try {
            Connection conn = con.getConnection();
            FileInputStream file = new FileInputStream(
                    new File("\\\\sum-yec-san\\Publico\\Capacitacion\\Benjamin\\Treinning.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet hoja = wb.getSheetAt(1);

            int numFilas = hoja.getLastRowNum();

            //a inicia en 0 por que no hay encabezados
            //Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 0; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);

                ps = conn.prepareStatement(sql);
                ps.setString(1, fila.getCell(2).getStringCellValue());

                ps.execute();
            }

            conn.close();

        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            Logger.getLogger(CargarExcelTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargar5() throws IOException, SQLException, ParseException {

        Conexion con = new Conexion();
        PreparedStatement ps;
        ResultSet rs = null;

        String sql = "INSERT INTO `certificacion`(`nombre_certificacion`)"
                + "VALUES (?);";

        try {
            Connection conn = con.getConnection();
            FileInputStream file = new FileInputStream(
                    new File("\\sum-yec-san\\Publico\\Capacitacion\\Benjamin\\certification.xlsx"));

            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet hoja = wb.getSheetAt(2);

            int numFilas = hoja.getLastRowNum();

            //a inicia en 0 por que no hay encabezados
            //Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 0; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);

                ps = conn.prepareStatement(sql);
                ps.setString(1, fila.getCell(2).getStringCellValue());

                ps.execute();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
            Logger.getLogger(CargarExcelTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    private static String cmbFecha(Cell cell) throws ParseException {
        SimpleDateFormat prs = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd");
        Date date = prs.parse(cell.getStringCellValue());
        String fecha = frm.format(date);
//        System.out.println(frm.format(date));
        return fecha;
    }
}
