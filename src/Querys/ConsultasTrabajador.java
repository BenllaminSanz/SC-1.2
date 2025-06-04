//Consultas SQL para la clase Trabajador
package Querys;

import Functions.DateTools;
import Model.Trabajador;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConsultasTrabajador extends Conexion {

    public int registros;
    // Obtener los índices de columna desde las propiedades

    //Funcion de Registro para Trabajadores
    public boolean registrar(Trabajador tbr) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `trabajador`\n"
                + "(`Folio_Trabajador`,\n"
                + "`Nombre_Trabajador`,\n"
                + "`CURP_Trabajador`,\n"
                + "`RFC_Trabajador`,\n"
                + "`IMSS_Trabajador`,\n"
                + "`Fecha_Antiguedad`,\n"
                + "`puesto_area_idArea`,\n"
                + "`puesto_idPuesto`,\n"
                + "`turno_idTurno`,\n"
                + "`SalarioDiario_Trabajador`,\n"
                + "`Fecha_Cumpleaños`,\n"
                + "`Email_Trabajador`,\n"
                + "`Teléfono_Trabajador`,\n"
                + "`Comentario`,\n"
                + "`Brigadista`,\n"
                + "`brigada_idBrigada`)\n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, tbr.getFolio_Trabajador());
            ps.setString(2, tbr.getNombre_Trabajador());
            ps.setString(3, tbr.getCURP_Trabajador());
            ps.setString(4, tbr.getRFC_Trabajador());
            ps.setString(5, tbr.getIMSS_Trabajador());
            ps.setString(6, DateTools.StringtoMySQL(tbr.getFecha_Antiguedad()));
            ps.setInt(7, tbr.getÁrea_Trabajador());
            ps.setInt(8, tbr.getPuesto_Trabajador());
            ps.setInt(9, tbr.getTurno_Trabajador());
            ps.setDouble(10, tbr.getSalarioDiario_Trabajador());
            ps.setString(11, DateTools.StringtoMySQL(tbr.getFecha_Cumpleaños()));
            ps.setString(12, tbr.getEmail_Trabajador());
            ps.setString(13, tbr.getTel_Trabajador());
            ps.setString(14, tbr.getComentario());
            ps.setBoolean(15, tbr.isBrigadista());
            if (tbr.isBrigadista()) {
                ps.setInt(16, tbr.getBrigada());
            } else {
                ps.setNull(16, tbr.getBrigada());
            }
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificar(Trabajador tbr, String folio) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`trabajador`\n"
                + "SET\n"
                + "`Folio_Trabajador` = ?,\n"
                + "`Nombre_Trabajador` = ?,\n"
                + "`CURP_Trabajador` = ?,\n"
                + "`RFC_Trabajador` = ?,\n"
                + "`IMSS_Trabajador` = ?,\n"
                + "`Fecha_Antiguedad` = ?,\n"
                + "`puesto_area_idArea` = ?,\n"
                + "`puesto_idPuesto` = ?,\n"
                + "`turno_idTurno` = ?,\n"
                + "`SalarioDiario_Trabajador` = ?,\n"
                + "`Fecha_Cumpleaños` = ?,\n"
                + "`Email_Trabajador` = ?,\n"
                + "`Teléfono_Trabajador` = ?,\n"
                + "`Comentario` = ?,\n"
                + "`Brigadista` = ?,\n"
                + "`brigada_idBrigada` = ?\n"
                + "WHERE `Folio_Trabajador` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, tbr.getFolio_Trabajador());
            ps.setString(2, tbr.getNombre_Trabajador());
            ps.setString(3, tbr.getCURP_Trabajador());
            ps.setString(4, tbr.getRFC_Trabajador());
            ps.setString(5, tbr.getIMSS_Trabajador());
            ps.setString(6, DateTools.StringtoMySQL(tbr.getFecha_Antiguedad()));
            ps.setInt(7, tbr.getÁrea_Trabajador());
            ps.setInt(8, tbr.getPuesto_Trabajador());
            ps.setInt(9, tbr.getTurno_Trabajador());
            ps.setDouble(10, tbr.getSalarioDiario_Trabajador());
            ps.setString(11, DateTools.StringtoMySQL(tbr.getFecha_Cumpleaños()));
            ps.setString(12, tbr.getEmail_Trabajador());
            ps.setString(13, tbr.getTel_Trabajador());
            ps.setString(14, tbr.getComentario());
            ps.setBoolean(15, tbr.isBrigadista());
            if (tbr.isBrigadista()) {
                ps.setInt(16, tbr.getBrigada());
            } else {
                ps.setNull(16, tbr.getBrigada());
            }
            ps.setString(17, folio);
            System.out.println(ps);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Funcion de Eliminar para Trabajadores
    public boolean eliminar(Trabajador tbr) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`trabajador`\n"
                + "WHERE `Folio_Trabajador` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, tbr.getFolio_Trabajador());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Funcion de Registro para Trabajadores
    public boolean buscar(Trabajador tbr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM view_trabajador WHERE Folio_Trabajador = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, tbr.getFolio_Trabajador());
            rs = ps.executeQuery();

            if (rs.next()) {
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                tbr.setIMSS_Trabajador(rs.getString("IMSS_Trabajador"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(rs.getDate("Fecha_Antiguedad")));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setSalarioDiario_Trabajador(Double.valueOf(rs.getString("SalarioDiario_Trabajador")));
                tbr.setFecha_Cumpleaños(DateTools.MySQLtoString(rs.getDate("Fecha_Cumpleaños")));
                tbr.setEmail_Trabajador(rs.getString("Email_Trabajador"));
                tbr.setTel_Trabajador(rs.getString("Teléfono_Trabajador"));
                tbr.setComentario(rs.getString("Comentario"));
                tbr.setBrigadista(rs.getBoolean("Brigadista"));
                if (tbr.isBrigadista()) {
                    tbr.setBrigada(rs.getInt("brigada_idBrigada"));
                }
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificarStatus(Trabajador tbr, String status) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        try {

            String sql = "UPDATE `sistema_capacitacion`.`trabajador`\n"
                    + "SET Status_Trabajador = ? "
                    + "WHERE `Folio_Trabajador` = ?;";

            ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, tbr.getFolio_Trabajador());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Funcion de Registro para Trabajadores a travez de archivo Excel
    public boolean cargarExcel(String file, int option) {
        Properties propiedades = new Properties();
        File archivoConfig = new File("configIndiceT.properties");
        if (!archivoConfig.exists()) {
            try (FileOutputStream fos = new FileOutputStream(archivoConfig)) {
                // Puedes escribir propiedades por defecto aquí si deseas:
                Properties defaultProps = new Properties();
                // Ejemplo: defaultProps.setProperty("nivel1", "Básico");
                defaultProps.store(fos, "Archivo de configuración creado automáticamente");
            } catch (IOException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, "No se pudo crear el archivo de configuración", ex);
                return false;
            }
        }

        try (FileInputStream fis = new FileInputStream(archivoConfig)) {
            propiedades.load(fis);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, "No se pudo cargar el archivo de configuración", ex);
            return false;
        }

        int indiceFolio = Integer.parseInt(propiedades.getProperty("indice.folio"));
        int indiceNombre = Integer.parseInt(propiedades.getProperty("indice.nombre"));
        int indiceSalario = Integer.parseInt(propiedades.getProperty("indice.salario"));
        int indiceFechaAntiguedad = Integer.parseInt(propiedades.getProperty("indice.fecha_antiguedad"));
        int indiceFechaCumpleanos = Integer.parseInt(propiedades.getProperty("indice.fecha_cumpleanos"));
        int indiceCurp = Integer.parseInt(propiedades.getProperty("indice.curp"));
        int indiceRfc = Integer.parseInt(propiedades.getProperty("indice.rfc"));
        int indiceImss = Integer.parseInt(propiedades.getProperty("indice.imss"));
        int indiceEmail = Integer.parseInt(propiedades.getProperty("indice.email"));
        int indiceTelefono = Integer.parseInt(propiedades.getProperty("indice.telefono"));
        try (FileInputStream fis = new FileInputStream("files/configIndiceT.properties")) {
            propiedades.load(fis);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, "No se pudo cargar el archivo de configuración", ex);
            return false;
        }

        PreparedStatement ps;
        Connection con = getConnection();
        ResultSet rs = null;

        String sql = "INSERT INTO `trabajador`\n"
                + "(`Folio_Trabajador`,\n"
                + "`Nombre_Trabajador`,\n"
                + "`SalarioDiario_Trabajador`,\n"
                + "`Fecha_Antiguedad`,\n"
                + "`Fecha_Cumpleaños`,\n"
                + "`CURP_Trabajador`,\n"
                + "`RFC_Trabajador`,\n"
                + "`IMSS_Trabajador`,\n"
                + "`Email_Trabajador`,\n"
                + "`Teléfono_Trabajador`)\n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";

        String consulta = "SELECT `trabajador`.`Folio_Trabajador`"
                + "FROM `trabajador` "
                + "WHERE `trabajador`.`Folio_Trabajador` = ?;";

        try {
            Sheet hoja = getWorkbook(file);
            int numFilas = hoja.getLastRowNum() - 1;

            for (int a = 3; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);
                int folio = (int) fila.getCell(0).getNumericCellValue();
                System.out.println(folio);

                if (folio == 0) {
                    break;
                } else {
                    ps = con.prepareStatement(consulta);
                    ps.setInt(1, (int) fila.getCell(0).getNumericCellValue());
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        if (option == JOptionPane.OK_OPTION) {
                            modificarExcel(folio, fila);
                        }
                    } else {
                        ps = con.prepareStatement(sql);

                        Cell celdaFolio = fila.getCell(indiceFolio);
                        if (celdaFolio != null && celdaFolio.getCellTypeEnum() == CellType.NUMERIC) {
                            ps.setInt(1, (int) celdaFolio.getNumericCellValue());
                        } else {
                            // Maneja el caso donde la celda está vacía o el tipo no es el esperado
                            ps.setNull(1, java.sql.Types.INTEGER);
                        }

                        ps.setString(2, fila.getCell(indiceNombre) != null ? fila.getCell(indiceNombre).getStringCellValue() : "");

                        ps.setString(3, cmbDec(fila.getCell(indiceSalario)));

                        Cell celda1 = fila.getCell(indiceFechaAntiguedad);
                        if (celda1 != null) {
                            ps.setString(4, DateTools.ExceltoMySQLfromCell(celda1));
                        } else {
                            ps.setNull(4, java.sql.Types.VARCHAR); // Si la celda es null, establece el valor como NULL
                        }

                        Cell celda2 = fila.getCell(indiceFechaCumpleanos);
                        if (celda2 != null) {
                            ps.setString(5, DateTools.ExceltoMySQLfromCell(celda2));
                        } else {
                            ps.setNull(5, java.sql.Types.VARCHAR); // Si la celda es null, establece el valor como NULL
                        }

                        ps.setString(6, fila.getCell(indiceCurp) != null ? fila.getCell(indiceCurp).getStringCellValue() : "");
                        ps.setString(7, fila.getCell(indiceRfc) != null ? fila.getCell(indiceRfc).getStringCellValue() : "");

                        int celdaIMSS = 0;
                        Cell celdaIMSSValue = fila.getCell(indiceImss);
                        if (celdaIMSSValue != null && celdaIMSSValue.getCellTypeEnum() == CellType.NUMERIC) {
                            celdaIMSS = (int) celdaIMSSValue.getNumericCellValue();
                        }
                        ps.setString(8, String.valueOf(celdaIMSS));

                        ps.setString(9, fila.getCell(indiceEmail) != null ? fila.getCell(indiceEmail).getStringCellValue() : "");

                        Cell celdaTel = fila.getCell(indiceTelefono);
                        if (celdaTel != null) {
                            if (celdaTel.getCellTypeEnum() == CellType.NUMERIC) {
                                long longTel = (long) celdaTel.getNumericCellValue();
                                String tel = String.valueOf(longTel);
                                ps.setString(10, tel);
                            } else if (celdaTel.getCellTypeEnum() == CellType.STRING) {
                                String tel = celdaTel.getStringCellValue();
                                tel = tel.replaceAll("\\s+", "");
                                ps.setString(10, tel);
                            }
                        } else {
                            ps.setNull(10, java.sql.Types.VARCHAR); // Si la celda de teléfono es null, establece el valor como NULL
                        }

                        ps.execute();

                        registros++;
                    }
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean modificarExcel(int folio, Row fila) {
        Properties propiedades = new Properties();
        try (FileInputStream fis = new FileInputStream("files/configIndiceT.properties")) {
            propiedades.load(fis);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, "No se pudo cargar el archivo de configuración", ex);
            return false;
        }

        int indiceFolio = Integer.parseInt(propiedades.getProperty("indice.folio"));
        int indiceNombre = Integer.parseInt(propiedades.getProperty("indice.nombre"));
        int indiceSalario = Integer.parseInt(propiedades.getProperty("indice.salario"));
        int indiceFechaAntiguedad = Integer.parseInt(propiedades.getProperty("indice.fecha_antiguedad"));
        int indiceFechaCumpleanos = Integer.parseInt(propiedades.getProperty("indice.fecha_cumpleanos"));
        int indiceCurp = Integer.parseInt(propiedades.getProperty("indice.curp"));
        int indiceRfc = Integer.parseInt(propiedades.getProperty("indice.rfc"));
        int indiceImss = Integer.parseInt(propiedades.getProperty("indice.imss"));
        int indiceEmail = Integer.parseInt(propiedades.getProperty("indice.email"));
        int indiceTelefono = Integer.parseInt(propiedades.getProperty("indice.telefono"));
        try (FileInputStream fis = new FileInputStream("files/configIndiceT.properties")) {
            propiedades.load(fis);
        } catch (IOException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, "No se pudo cargar el archivo de configuración", ex);
            return false;
        }

        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`trabajador`\n"
                + "SET\n"
                + "`Nombre_Trabajador` = ?,\n"
                + "`SalarioDiario_Trabajador` = ?,\n"
                + "`Fecha_Antiguedad` = ?,\n"
                + "`Fecha_Cumpleaños` = ?,\n"
                + "`CURP_Trabajador` = ?,\n"
                + "`RFC_Trabajador` = ?,\n"
                + "`IMSS_Trabajador` = ?,\n"
                + "`Email_Trabajador` = ?,\n"
                + "`Teléfono_Trabajador` = ?\n"
                + "WHERE `Folio_Trabajador` = ?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, fila.getCell(indiceNombre) != null ? fila.getCell(indiceNombre).getStringCellValue() : "");

            ps.setString(2, cmbDec(fila.getCell(indiceSalario)));

            Cell celda1 = fila.getCell(indiceFechaAntiguedad);
            if (celda1 != null) {
                ps.setString(3, DateTools.ExceltoMySQLfromCell(celda1));
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR); // Si la celda es null, establece el valor como NULL
            }

            Cell celda2 = fila.getCell(indiceFechaCumpleanos);
            if (celda2 != null) {
                ps.setString(4, DateTools.ExceltoMySQLfromCell(celda2));
            } else {
                ps.setNull(4, java.sql.Types.VARCHAR); // Si la celda es null, establece el valor como NULL
            }

            ps.setString(5, fila.getCell(indiceCurp) != null ? fila.getCell(indiceCurp).getStringCellValue() : "");
            ps.setString(6, fila.getCell(indiceRfc) != null ? fila.getCell(indiceRfc).getStringCellValue() : "");

            int celdaIMSS = 0;
            Cell celdaIMSSValue = fila.getCell(indiceImss);
            if (celdaIMSSValue != null && celdaIMSSValue.getCellTypeEnum() == CellType.NUMERIC) {
                celdaIMSS = (int) celdaIMSSValue.getNumericCellValue();
            }
            ps.setString(7, String.valueOf(celdaIMSS));

            ps.setString(8, fila.getCell(indiceEmail) != null ? fila.getCell(indiceEmail).getStringCellValue() : "");

            Cell celdaTel = fila.getCell(indiceTelefono);
            if (celdaTel != null) {
                if (celdaTel.getCellTypeEnum() == CellType.NUMERIC) {
                    long longTel = (long) celdaTel.getNumericCellValue();
                    String tel = String.valueOf(longTel);
                    ps.setString(9, tel);
                } else if (celdaTel.getCellTypeEnum() == CellType.STRING) {
                    String tel = celdaTel.getStringCellValue();
                    tel = tel.replaceAll("\\s+", "");
                    ps.setString(9, tel);
                }
            } else {
                ps.setNull(9, java.sql.Types.VARCHAR); // Si la celda de teléfono es null, establece el valor como NULL
            }
            
            Cell celdaFolio = fila.getCell(indiceFolio);
            if (celdaFolio != null && celdaFolio.getCellTypeEnum() == CellType.NUMERIC) {
                ps.setInt(10, (int) celdaFolio.getNumericCellValue());
            } else {
                // Maneja el caso donde la celda está vacía o el tipo no es el esperado
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Metodo de coversión para cambiar los decimales
    private static String cmbDec(Cell cell) {
        if (cell.getCellTypeEnum() != CellType.NUMERIC) {
            return null;
        }
        DecimalFormat formato = new DecimalFormat("#.00");
        String a = formato.format(cell.getNumericCellValue());
        return a;
    }

    public static Sheet getWorkbook(String fileName) {
        try {
            File file = new File(fileName);
            String name = file.toString();
            Workbook workbook = WorkbookFactory.create(file);

            Sheet hoja = null;
            try (FileInputStream inputStream = new FileInputStream(name)) {
                if (fileName.toLowerCase().endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                    hoja = workbook.getSheetAt(0);
                } else if (fileName.toLowerCase().endsWith(".xls")) {
                    workbook = new HSSFWorkbook(inputStream);
                    hoja = workbook.getSheetAt(0);
                }
            }
            return hoja;
        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String consultarStatus(String folio) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();
        String sql = "SELECT * FROM view_trabajador WHERE Folio_Trabajador = ?";
        String status;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, folio);
            System.out.println(ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                status = rs.getString("Status_Trabajador");
                System.out.println("x" + status);
                return status;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //Funcion de Eliminar para Trabajadores
    public int contar() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();
        int trabajadores = 0;

        String sql = "SELECT COUNT(*) as Trabajadores\n"
                + "FROM trabajador;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                trabajadores = rs.getInt("Trabajadores");
                return trabajadores;
            }
            return trabajadores;

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasTrabajador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
}
