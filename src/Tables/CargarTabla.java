/*Clase con los metodos que crean una lista de la consulta especial para llenar las tablas*/
package Tables;

import Functions.DateTools;
import Functions.QueryFunctions;
import Model.Administrativos;
import Model.Area;
import Model.AsistenteCurso;
import Model.Bajas;
import Model.Brigadas;
import Model.Brigadistas;
import Model.Certificado;
import Model.Curso;
import Model.HistorialCurso;
import Querys.Conexion;
import Model.LBU;
import Model.PersonalCertificado;
import Model.PersonalCurso;
import Model.Puesto;
import Model.RequerimientosCurso;
import Model.Supervisor;
import Model.Trabajador;
import static Querys.ConsultasTrabajador.getWorkbook;
import View.IFrmCapacitacion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CargarTabla {

    public static final Conexion conn = new Conexion();
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;

    public static List<Trabajador> cargarTablaTrabajador() {
        List<Trabajador> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM view_trabajador ORDER BY Folio_Trabajador DESC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Trabajador tbr = new Trabajador();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(
                        rs.getDate("Fecha_Antiguedad")));
                tbr.setNombre_Supervisor(rs.getString("Supervisor"));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setSalarioDiario_Trabajador(Double.valueOf(
                        rs.getString("SalarioDiario_Trabajador")));
                tbr.setStatus_Trabajador(rs.getString("Status_Trabajador"));
                
                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream("niveles.properties");
                properties.load(fis);

                // Obtener el salario del trabajador
                double salario = rs.getDouble("SalarioDiario_Trabajador");
                // Determinar el nivel del trabajador
                String nivel = determinarNivel(salario, properties);
                // Asignar el nivel al objeto tbr
                tbr.setNivel_Salario(nivel);

                // Obtener CURP del trabajador
                String curp = rs.getString("Curp_Trabajador");
                // Verificar si el CURP es válido y tiene la longitud correcta (18 caracteres)
                if (curp != null && curp.length() == 18) {
                    // Obtener el séptimo carácter (índice 6) que representa el sexo
                    char sexoChar = curp.charAt(10);
                    // Verificar el sexo y asignar el valor correspondiente
                    String sexo = (sexoChar == 'H') ? "Hombre" : (sexoChar == 'M') ? "Mujer" : "Desconocido";
                    // Asignar el sexo al objeto tbr (o a la variable correspondiente)
                    tbr.setSexo_Trabajador(sexo);
                }
                trabajador.add(tbr);
            }
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static String determinarNivel(double salario, Properties properties) {
        // Recorre las claves de los niveles y compara los salarios
        for (String key : properties.stringPropertyNames()) {
            String salarioStr = properties.getProperty(key);
            if (salarioStr != null && !salarioStr.isEmpty()) {
                double nivelSalario = Double.parseDouble(salarioStr);

                // Si el salario del trabajador es mayor o igual al salario del nivel, asigna el nivel
                if (salario >= nivelSalario) {
                    return key.replace("nivel.", "");  // Retorna el nivel
                }
            }
        }

        // Si no se encuentra un nivel adecuado, retornar un valor predeterminado
        return " ";
    }

    public static List<Bajas> cargarTablaBajas() {
        List<Bajas> bajas = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_bajas";
        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Bajas tbr = new Bajas();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setFecha_Baja(DateTools.MySQLtoString(rs.getDate("Fecha_Baja")));
                tbr.setComentario(rs.getString("Comentario"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(
                        rs.getDate("Fecha_Antiguedad")));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Supervisor(rs.getString("Supervisor"));
                tbr.setSalarioDiario_Trabajador(
                        rs.getString("SalarioDiario_Trabajador") != null
                        ? Double.valueOf(rs.getString("SalarioDiario_Trabajador")) : 0.0);
                bajas.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bajas;
    }

    public static List<LBU> cargarTablaLBU() {
        List<LBU> lbu = new ArrayList<>();
        Connection con = conn.getConnection();
        String sql = "Select * from view_lbu";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LBU tbr = new LBU();
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Supervisor(rs.getString("Nombre_Supervisor"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<LBU> cargarTablaLBUTrabajador() {
        List<LBU> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM view_lbu_resumen";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LBU tbr = new LBU();
                tbr.setNombre_Area(rs.getString("Supervisor"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<LBU> cargarTablaLBUResumen() {
        List<LBU> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM view_lbu_resumen";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LBU tbr = new LBU();
                tbr.setId_Supervisor(rs.getInt("id_Supervisor"));
                tbr.setNombre_Supervisor(rs.getString("Supervisor"));
                tbr.setSupervisor_Propuesto(rs.getInt("Propuesto"));
                tbr.setId_Area(rs.getInt("Area"));
                tbr.setSupervisor_Diferencia(rs.getInt("Diferencia"));
                tbr.setSupervisor_Plantilla(rs.getInt("Plantilla"));
                tbr.setTurnoA(rs.getInt("A"));
                tbr.setTurnoB(rs.getInt("B"));
                tbr.setTurnoC(rs.getInt("C"));
                tbr.setTurnoD(rs.getInt("D"));
                tbr.setTurnoLV(rs.getInt("LV"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<LBU> cargarTablaLBUResumenTrabajador(int a, int b) {
        List<LBU> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @supervisor_id:=?";
        String sql2 = "SET @supervisor_puesto:=?";
        String sql = "SELECT * FROM view_lbu_trabajador";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, a);
            ps.executeUpdate();

            ps = con.prepareStatement(sql2);
            ps.setInt(1, b);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LBU tbr = new LBU();
                tbr.setNombre_Turno(rs.getString("Turno"));
                tbr.setFolio_Trabajador(rs.getString("Nómina"));
                tbr.setNombre_Trabajador(rs.getString("Trabajador"));
                tbr.setStatus_trabajador(rs.getString("Status_Trabajador"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Supervisor> cargarTablaSupervisor() {
        List<Supervisor> supervisor = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_supervisor";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Supervisor tbr = new Supervisor();
                tbr.setNombre_supervisor(rs.getString("Nombre_Supervisor"));
                tbr.setPropuesto_trabajadores(rs.getInt("Propuesto_Trabajadores"));
                tbr.setNombre_area(rs.getString("Nombre_Area"));
                tbr.setNombre_turno(rs.getString("Nombre_Turno"));
                supervisor.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return supervisor;
    }

    public static List<Supervisor> cargarTablaSupervisorEdit() {
        List<Supervisor> supervisor = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from supervisor ORDER BY area_idArea";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Supervisor tbr = new Supervisor();
                tbr.setId_supervisor(rs.getInt("idSupervisor"));
                tbr.setNombre_supervisor(rs.getString("Nombre_Supervisor"));
                supervisor.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return supervisor;
    }

    public static List<Puesto> cargarTablaPuestoSupervisorEdit(int id) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.puesto WHERE area_idArea = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Puesto> cargarTablaPuestoSupervisor(LBU modr) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @supervisor_id_area:=?";
        String sql = "SELECT * FROM view_lbu_area_supervisor";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, modr.getId_Supervisor());
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setIdPuesto(rs.getInt("idPuesto"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setPropuesto_Turno(rs.getInt("Propuesto"));
                tbr.setDiferencia_Trabajadores(rs.getInt("Diferencia"));
                tbr.setPlantilla_Trabajadores(rs.getInt("Plantilla"));
                tbr.setTurnoA(rs.getInt("A"));
                tbr.setTurnoB(rs.getInt("B"));
                tbr.setTurnoC(rs.getInt("C"));
                tbr.setTurnoD(rs.getInt("D"));
                tbr.setTurnoLV(rs.getInt("LV"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Area> cargarTablaArea() {
        List<Area> area = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM area";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Area a = new Area();
                a.setNombre_Area(rs.getString("Nombre_Area"));
                area.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return area;
    }

    public static List<Puesto> cargarTablaPuestos() {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.puesto";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setDescripcion(rs.getString("Descripcion"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Puesto> cargarTablaPuestoArea(int idArea) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.puesto WHERE area_idArea = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idArea);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setDescripcion(rs.getString("Descripcion"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Puesto> cargarTablaPuestoCertificado(int idCertificado) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_certificado:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_puestos_certificado;";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, idCertificado);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Puesto tbr = new Puesto();
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Puesto> cargarTablaPuestoCertificadoAgregar(String idCertificado) {
        List<Puesto> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM puesto p\n"
                + "LEFT JOIN certificado_puesto cp ON p.idPuesto = cp.puesto_idPuesto\n"
                + "AND cp.puesto_idPuesto = ? "
                + "INNER JOIN area a ON p.area_idArea=a.idArea\n"
                + "WHERE cp.puesto_idPuesto IS NULL ORDER BY p.area_idArea";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, idCertificado);
            rs = ps.executeQuery();

            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Puesto> cargarTablaPuestoCertificadoAgregarFiltrar(int idcertificacion, int area) {
        List<Puesto> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM puesto p\n"
                + "LEFT JOIN certificado_puesto cp ON p.idPuesto = cp.puesto_idPuesto\n"
                + "AND cp.puesto_idPuesto = ? "
                + "INNER JOIN area a ON p.area_idArea=a.idArea\n"
                + "WHERE cp.puesto_idPuesto IS NULL AND a.idArea=? ORDER BY p.area_idArea";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idcertificacion);
            ps.setInt(2, area);
            rs = ps.executeQuery();

            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Puesto> cargarTablaPuestoAreaCertificado(String Area) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        int idArea = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                "area", "idArea", "nombre_Area", Area));
        String sql = "SELECT * FROM sistema_capacitacion.puesto WHERE area_idArea = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idArea);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Curso> cargarTablaCursos() {
        List<Curso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_curso";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Curso tbr = new Curso();
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setNombre_Tipo_Curso(rs.getString("tipo_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<HistorialCurso> cargarTablaHistorialCursos() {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historialcursos;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
//                tbr.setNombre_Taller(rs.getString("nombre_Taller"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setTiempo_estimado(rs.getDouble("duracion_curso"));
                tbr.setNum_asistentes(rs.getInt("num_asistentes"));
                tbr.setHoras_asistente(rs.getDouble("horas_asistentes"));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<HistorialCurso> cargarTablaHistorialCursosTipo(int idTipo) {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historialcursos \n"
                + "WHERE id_tipocurso = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idTipo);

            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
//                tbr.setNombre_Taller(rs.getString("nombre_Taller"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setTiempo_estimado(rs.getDouble("duracion_curso"));
                tbr.setNum_asistentes(rs.getInt("num_asistentes"));
                tbr.setHoras_asistente(rs.getDouble("horas_asistentes"));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<HistorialCurso> cargarTablaHistorialCurso(int idCurso) {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historialcursos \n"
                + "WHERE idCurso = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCurso);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
//                tbr.setNombre_Taller(rs.getString("nombre_Taller"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setTiempo_estimado(rs.getDouble("duracion_curso"));
                tbr.setNum_asistentes(rs.getInt("num_asistentes"));
                tbr.setHoras_asistente(rs.getDouble("horas_asistentes"));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<HistorialCurso> cargarTablaHistorialCursoNombres(int idCurso) {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historialcurso_nombres \n"
                + "WHERE idCurso = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCurso);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombres(rs.getString("lista_asistentes"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setTiempo_estimado(rs.getDouble("duracion_curso"));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCurso(int idCurso) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_curso`";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, idCurso);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("idAsistentes_curso"));
                tbr.setNombre(rs.getString("nombre_asistente"));
                tbr.setPuesto(rs.getString("asistente_type"));
                tbr.setTurno(rs.getString("nombre_turno"));
                tbr.setEntrenamiento(rs.getString("tipo_entrenamiento"));
                tbr.setStatus(rs.getString("status_entrenamiento"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCursoTrabajadores(String idHistorial) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_trabajador`";

        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio_Trabajador"));
                tbr.setNombre(rs.getString("nombre_trabajador"));
                tbr.setPuesto(rs.getString("nombre_puesto"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCursoSupervisores(String idHistorial) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_supervisor`";

        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("idSupervisor"));
                tbr.setNombre(rs.getString("nombre_supervisor"));
                tbr.setPuesto(rs.getString("nombre_area"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCursoAdministrativos(String idHistorial) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_asistentes_administrativos;";

        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio_Administrativo"));
                tbr.setNombre(rs.getString("nombre_administrativo"));
                tbr.setArea(rs.getString("cia_Administrativo"));
                tbr.setPuesto(rs.getString("puesto_Administrativo"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<RequerimientosCurso> cargarTablaRequerimientosCurso(String idCurso) {
        List<RequerimientosCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_curso:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_requerimientos_curso;";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, Integer.parseInt(idCurso));
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                RequerimientosCurso tbr = new RequerimientosCurso();
                tbr.setIdRequerimiento(rs.getInt("IdRequerimientos"));
                tbr.setNombre_requerimiento(rs.getString("nombre_Requerimiento"));
                tbr.setDescp_requerimiento(rs.getString("descp_requerimiento"));
                tbr.setRuta_Docuemento(rs.getString("ruta_documento"));
                tbr.setIdCurso(rs.getInt("idcurso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Certificado> cargarTablaCertificados() {
        List<Certificado> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.certificado";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Certificado tbr = new Certificado();
                tbr.setIdCertificado(rs.getInt("idcertificado"));
                tbr.setNombre_Certificado(rs.getString("nombre_certificado"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Puesto> cargarTablaPuestosCurso(String idCurso) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_curso:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_puestos_curso;";

        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idCurso);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setIdPuesto(rs.getInt("idPuesto"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Puesto> cargarTablaPuestosFromCurso(String idCurso) {
        List<Puesto> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_curso:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_puestos_from_curso;";

        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idCurso);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setIdPuesto(rs.getInt("idPuesto"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Trabajador> cargarTablaInstructoresCurso() {
        List<Trabajador> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.trabajador t\n"
                + "INNER JOIN puesto p ON t.puesto_idPuesto=p.idPuesto\n"
                + "INNER JOIN area a ON t.puesto_area_idArea=a.idArea\n"
                + "INNER JOIN turno tn ON t.turno_idTurno=tn.idTurno\n"
                + "WHERE puesto_area_idArea = 10 AND puesto_idPuesto != 71\n"
                + "ORDER BY Nombre_Turno ASC;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Trabajador tbr = new Trabajador();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<PersonalCertificado> cargarTablaAsistentesCertificados(int idCertificado) {
        List<PersonalCertificado> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_certificado:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_asistentes_certificado;";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, idCertificado);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdCertificacion(rs.getInt("idCertificacion"));
                tbr.setIdTrabajador_Certificado(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setFecha_certificacion(DateTools.MySQLtoString(
                        rs.getDate("fecha_certificacion")));
                tbr.setTipo_certificado(rs.getString("tipo_certificacion"));

                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<PersonalCertificado> cargarTablaAllAsistentesCertificados() {
        List<PersonalCertificado> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_asistentes_certificados;";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdCertificacion(rs.getInt("idCertificacion"));
                tbr.setNombre_Certificado(rs.getString("nombre_certificado"));
                tbr.setIdTrabajador_Certificado(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setFecha_certificacion(DateTools.MySQLtoString(
                        rs.getDate("fecha_certificacion")));
                tbr.setTipo_certificado(rs.getString("tipo_certificacion"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Trabajador> buscarTablaTrabajadorCapacitacionAgregar(int id_Capacitacion, String texto) {
        List<Trabajador> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_capacitacion:=?";
        String sql = "SELECT * FROM trabajador t\n"
                + "LEFT JOIN capacitacion_trabajador ct ON t.Folio_Trabajador = ct.trabajador_Folio_Trabajador\n"
                + "WHERE Folio_Trabajador LIKE '%" + texto + "%'"
                + "OR Nombre_Trabajador LIKE '%" + texto + "%'"
                + "AND ct.trabajador_Folio_Trabajador IS NULL ORDER BY Folio_Trabajador DESC";

        try {
            ps = con.prepareStatement(sql1);
            ps.setInt(1, id_Capacitacion);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Trabajador tbr = new Trabajador();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Trabajador> buscarTablaTrabajador(String texto) {
        List<Trabajador> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_trabajador "
                + "WHERE Folio_Trabajador LIKE '%" + texto + "%'"
                + "OR Nombre_Trabajador LIKE '%" + texto + "%'"
                + "ORDER BY Folio_Trabajador ASC";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Trabajador tbr = new Trabajador();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(
                        rs.getDate("Fecha_Antiguedad")));
                tbr.setNombre_Supervisor(rs.getString("Supervisor"));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setSalarioDiario_Trabajador(Double.valueOf(
                        rs.getString("SalarioDiario_Trabajador")));
                tbr.setStatus_Trabajador(rs.getString("Status_Trabajador"));
                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream("niveles.properties");
                properties.load(fis);

                // Obtener el salario del trabajador
                double salario = rs.getDouble("SalarioDiario_Trabajador");
                // Determinar el nivel del trabajador
                String nivel = determinarNivel(salario, properties);
                // Asignar el nivel al objeto tbr
                tbr.setNivel_Salario(nivel);

                // Obtener CURP del trabajador
                String curp = rs.getString("Curp_Trabajador");
                // Verificar si el CURP es válido y tiene la longitud correcta (18 caracteres)
                if (curp != null && curp.length() == 18) {
                    // Obtener el séptimo carácter (índice 6) que representa el sexo
                    char sexoChar = curp.charAt(10);
                    // Verificar el sexo y asignar el valor correspondiente
                    String sexo = (sexoChar == 'H') ? "Hombre" : (sexoChar == 'M') ? "Mujer" : "Desconocido";
                    // Asignar el sexo al objeto tbr (o a la variable correspondiente)
                    tbr.setSexo_Trabajador(sexo);
                }
                trabajador.add(tbr);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Trabajador> buscarTablaTrabajadorStatus(String texto) {
        List<Trabajador> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_trabajador "
                + "WHERE Status_Trabajador = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, texto);
            rs = ps.executeQuery();

            while (rs.next()) {
                Trabajador tbr = new Trabajador();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(
                        rs.getDate("Fecha_Antiguedad")));
                tbr.setNombre_Supervisor(rs.getString("Supervisor"));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setSalarioDiario_Trabajador(Double.valueOf(
                        rs.getString("SalarioDiario_Trabajador")));
                tbr.setStatus_Trabajador(rs.getString("Status_Trabajador"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Bajas> buscarTablaBajas(String texto) {
        List<Bajas> bajas = new ArrayList<>();
        Connection con = conn.getConnection();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        String sql = "Select * from view_bajas "
                + "WHERE Folio_Trabajador LIKE '%" + texto + "%'"
                + "OR Nombre_Trabajador LIKE '%" + texto + "%'"
                + "ORDER BY Folio_Trabajador ASC";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Bajas tbr = new Bajas();
                tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setFecha_Baja(DateTools.MySQLtoString(rs.getDate("Fecha_Baja")));
                tbr.setComentario(rs.getString("Comentario"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(
                        rs.getDate("Fecha_Antiguedad")));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                Double salario = rs.getString("SalarioDiario_Trabajador") != null ? Double.valueOf(rs.getString("SalarioDiario_Trabajador")) : 0.0;
                tbr.setSalarioDiario_Trabajador(salario);
                bajas.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bajas;
    }

    public static List<LBU> buscarTablaLBU(LBU mod, int i) {
        List<LBU> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        switch (i) {
            case 1: {
                String sql = "SELECT * FROM view_lbu "
                        + "where idArea=? order by idPuesto, idTurno";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getId_Area());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        LBU tbr = new LBU();
                        tbr.setNombre_Area(rs.getString("Nombre_Area"));
                        tbr.setNombre_Supervisor(rs.getString("Nombre_Supervisor"));
                        tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                        tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                        tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                        tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                        tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                        tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 2: {
                String sql = "SELECT * FROM view_lbu "
                        + "where idArea=? and idTurno=? order by idPuesto";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getId_Area());
                    ps.setInt(2, mod.getId_Turno());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        LBU tbr = new LBU();
                        tbr.setNombre_Area(rs.getString("Nombre_Area"));
                        tbr.setNombre_Supervisor(rs.getString("Nombre_Supervisor"));
                        tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                        tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                        tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                        tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                        tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                        tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 3: {
                String sql = "SELECT * FROM view_lbu "
                        + "where idArea=? and idPuesto=? order by idTurno";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getId_Area());
                    ps.setInt(2, mod.getId_Puesto());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        LBU tbr = new LBU();
                        tbr.setNombre_Area(rs.getString("Nombre_Area"));
                        tbr.setNombre_Supervisor(rs.getString("Nombre_Supervisor"));
                        tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                        tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                        tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                        tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                        tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                        tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 4: {
                String sql = "SELECT * FROM view_lbu "
                        + "where idArea=? and idPuesto=? and idTurno=?";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getId_Area());
                    ps.setInt(2, mod.getId_Puesto());
                    ps.setInt(3, mod.getId_Turno());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        LBU tbr = new LBU();
                        tbr.setNombre_Area(rs.getString("Nombre_Area"));
                        tbr.setNombre_Supervisor(rs.getString("Nombre_Supervisor"));
                        tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                        tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                        tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                        tbr.setNombre_Turno(rs.getString("Nombre_Turno"));
                        tbr.setFolio_Trabajador(rs.getString("Folio_Trabajador"));
                        tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
        }
        return null;
    }

    public static List<Curso> buscarTablaCursos(String texto) {
        List<Curso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_curso "
                + "WHERE nombre_curso LIKE '%" + texto + "%'"
                + "ORDER BY nombre_curso ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Curso tbr = new Curso();
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setNombre_Tipo_Curso(rs.getString("tipo_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Curso> buscarTablaCursosTipo(String texto) {
        List<Curso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_curso "
                + "WHERE idtipo_curso = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(texto));
            rs = ps.executeQuery();
            while (rs.next()) {
                Curso tbr = new Curso();
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setNombre_Tipo_Curso(rs.getString("tipo_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<PersonalCertificado> cargarTablaEstadoCertificacion() {
        List<PersonalCertificado> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = "Select * from view_reporte_entrenamiento";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdTrabajador_Certificado(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("Fecha_antiguedad")));
                tbr.setArea(rs.getString("nombre_Area"));
                tbr.setPuesto(rs.getString("nombre_Puesto"));
                tbr.setTurno(rs.getString("nombre_turno"));
                tbr.setEstado_certificado(rs.getString("estado"));
                tbr.setEstado_entrenamiento(rs.getString("entrenamiento"));
                if (tbr.getEstado_entrenamiento() == null) {
                    tbr.setEstado_entrenamiento("");
                }
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<HistorialCurso> buscarTablaHistorialCursos(String fecha_inicio) {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historialcursos "
                + "WHERE fecha_inicio = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, DateTools.StringtoMySQL(fecha_inicio));
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
//                tbr.setNombre_Taller(rs.getString("nombre_Taller"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setTiempo_estimado(rs.getDouble("duracion_curso"));
                tbr.setNum_asistentes(rs.getInt("num_asistentes"));
                tbr.setHoras_asistente(rs.getDouble("horas_asistentes"));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> buscarTablaAsistentesCursoTrabajadores(String idHistorial, String texto) {
        List<AsistenteCurso> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_asistentes_trabajador "
                + "WHERE Folio_Trabajador LIKE '%" + texto + "%'"
                + "OR Nombre_Trabajador LIKE '%" + texto + "%'"
                + "ORDER BY Folio_Trabajador ASC";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio_Trabajador"));
                tbr.setNombre(rs.getString("nombre_trabajador"));
                tbr.setPuesto(rs.getString("nombre_puesto"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<AsistenteCurso> buscarTablaAsistentesCursoSupervisores(String idHistorial, String texto) {

        List<AsistenteCurso> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_asistentes_supervisor "
                + "WHERE nombre_supervisor LIKE '%" + texto + "%'"
                + "ORDER BY nombre_supervisor ASC";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("idSupervisor"));
                tbr.setNombre(rs.getString("nombre_supervisor"));
                tbr.setPuesto(rs.getString("nombre_area"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Puesto> buscarTablaPuestosFromCurso(String idCurso, String texto) {
        List<Puesto> puesto = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_curso:=?";
        String sql = "SELECT * FROM sistema_capacitacion.view_puestos_from_curso"
                + " WHERE Nombre_Puesto LIKE '%" + texto + "%'"
                + " OR Nombre_Puesto_Ingles LIKE '%" + texto + "%';";
        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idCurso);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setIdPuesto(rs.getInt("idPuesto"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                puesto.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return puesto;
    }

    public static List<PersonalCertificado> buscarTablaAsistentesCertificados(PersonalCertificado mod, int i) {
        List<PersonalCertificado> lbu = new ArrayList<>();
        Connection con = conn.getConnection();
        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        String sql = null;
        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();
            switch (i) {
                case 1:
                    sql = "SELECT * FROM view_reporte_entrenamiento "
                            + "where idArea=? order by idPuesto, idTurno";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getArea_idArea());
                    rs = ps.executeQuery();
                    break;
                case 2:
                    sql = "SELECT * FROM view_reporte_entrenamiento "
                            + "where idArea=? and idTurno=? order by idPuesto";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getArea_idArea());
                    ps.setInt(2, mod.getTurno_idturno());
                    rs = ps.executeQuery();
                    break;
                case 3:
                    sql = "SELECT * FROM view_reporte_entrenamiento "
                            + "where idArea=? and idPuesto=? order by idTurno";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getArea_idArea());
                    ps.setInt(2, mod.getPuesto_idpuesto());
                    rs = ps.executeQuery();
                    break;
                case 4:
                    sql = "SELECT * FROM view_reporte_entrenamiento "
                            + "where idArea=? and idPuesto=? and idTurno=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, mod.getArea_idArea());
                    ps.setInt(2, mod.getPuesto_idpuesto());
                    ps.setInt(3, mod.getTurno_idturno());
                    rs = ps.executeQuery();
                    break;
            }
            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdTrabajador_Certificado(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("Fecha_antiguedad")));
                tbr.setArea(rs.getString("nombre_Area"));
                tbr.setPuesto(rs.getString("nombre_Puesto"));
                tbr.setTurno(rs.getString("nombre_turno"));
                tbr.setEstado_certificado(rs.getString("estado"));
                tbr.setEstado_entrenamiento(rs.getString("entrenamiento"));
                lbu.add(tbr);
            }
            return lbu;
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static List<AsistenteCurso> filtrarTablaAsistentesCursoTrabajadores(String idHistorial, AsistenteCurso mod, int i) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (i) {
            case 1: {

                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_trabajador`"
                        + " WHERE nombre_Area = ?";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getArea());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio_Trabajador"));
                        tbr.setNombre(rs.getString("nombre_trabajador"));
                        tbr.setPuesto(rs.getString("nombre_puesto"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 2: {
                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_trabajador`"
                        + " WHERE nombre_Area = ? AND nombre_turno = ?";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getArea());
                    ps.setString(2, mod.getTurno());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio_Trabajador"));
                        tbr.setNombre(rs.getString("nombre_trabajador"));
                        tbr.setPuesto(rs.getString("nombre_puesto"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 3: {
                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_trabajador`"
                        + " WHERE nombre_Area = ? AND nombre_puesto = ?";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getArea());
                    ps.setString(2, mod.getPuesto());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio_Trabajador"));
                        tbr.setNombre(rs.getString("nombre_trabajador"));
                        tbr.setPuesto(rs.getString("nombre_puesto"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 4: {
                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_trabajador`"
                        + " WHERE nombre_Area = ? AND nombre_puesto = ? AND nombre_turno = ?";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getArea());
                    ps.setString(2, mod.getPuesto());
                    ps.setString(3, mod.getTurno());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio_Trabajador"));
                        tbr.setNombre(rs.getString("nombre_trabajador"));
                        tbr.setPuesto(rs.getString("nombre_puesto"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
        }
        return null;
    }

    public static List<PersonalCertificado> cargarTablaCertificadosTrabajador(String idAsistente) {
        List<PersonalCertificado> certificado = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_asistentes_certificados "
                + "WHERE Folio_Trabajador = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, idAsistente);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdCertificacion(rs.getInt("idCertificacion"));
                tbr.setNombre_Certificado(rs.getString("nombre_certificado"));
                tbr.setEstado_certificado(rs.getString("estado_certificacion"));
                tbr.setTipo_certificado(rs.getString("tipo_certificacion"));
                tbr.setFecha_certificacion(rs.getString("fecha_certificacion"));
                certificado.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return certificado;
    }

    public static List<PersonalCurso> cargarTablaCursosTrabajador(String idAsistente) {
        List<PersonalCurso> curso = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_asistentes_cursos "
                + "WHERE idAsistentes_Curso = ? ORDER BY fecha_cierre ASC";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, idAsistente);
            rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCurso tbr = new PersonalCurso();
                tbr.setIdHistorial_Curso(rs.getInt("idHistorial_Curso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setEstado_curso(rs.getString("status_entrenamiento"));
                tbr.setTipo_curso(rs.getString("tipo_entrenamiento"));
                tbr.setFecha_inicio(rs.getString("fecha_inicio"));
                tbr.setFecha_cierre(rs.getString("fecha_cierre"));
                curso.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return curso;
    }

    public static List<Administrativos> cargarTablaAdministrativos() {
        List<Administrativos> admin = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.administrativos ORDER BY Folio_Administrativo";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Administrativos tbr = new Administrativos();
                tbr.setFolio_Trabajador(rs.getString("Folio_Administrativo"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Administrativo"));
                tbr.setCURP_Trabajador(rs.getString("Curp_Administrativo"));
                tbr.setRFC_Trabajador(rs.getString("RFC_Administrativo"));
                tbr.setIMSS_Trabajador(rs.getString("IMSS_Administrativo"));
                tbr.setFecha_Antiguedad(DateTools.MySQLtoString(
                        rs.getDate("Fecha_Antiguedad")));
                tbr.setCia(rs.getString("cia_Administrativo"));
                tbr.setArea(rs.getString("area_administrativo"));
                tbr.setPuesto(rs.getString("Puesto_Administrativo"));
                tbr.setTurno(rs.getString("turno"));
                admin.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return admin;
    }

    public static List<AsistenteCurso> buscarTablaAsistentesCursoAdministrativos(String idHistorial, String texto) {
        List<AsistenteCurso> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_asistentes_administrativos "
                + "WHERE Folio_Administrativo LIKE '%" + texto + "%'"
                + "OR Nombre_Administrativo LIKE '%" + texto + "%'"
                + "ORDER BY Folio_Administrativo ASC";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio_Administrativo"));
                tbr.setNombre(rs.getString("Nombre_Administrativo"));
                tbr.setArea(rs.getString("cia_Administrativo"));
                tbr.setPuesto(rs.getString("puesto_administrativo"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Curso> obtenerTiposDeCursos() {
        List<Curso> tipoCurso = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from tipo_curso";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Curso tbr = new Curso();
                tbr.setIdTipo_Curso(rs.getInt("idtipo_curso"));
                tbr.setNombre_Tipo_Curso(rs.getString("nombre_tipo"));
                tipoCurso.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tipoCurso;
    }

    public static List<Curso> obtenerCursosAsociados(int idTipo_Curso) {
        List<Curso> curso = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from curso where id_tipocurso=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, idTipo_Curso);
            rs = ps.executeQuery();

            while (rs.next()) {
                Curso tbr = new Curso();
                tbr.setIdCurso(rs.getInt("idcurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                curso.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return curso;
    }

    public static void GenerarArbolCursos(IFrmCapacitacion frm) {
        // Obtener los datos de las tablas de la base de datos
        List<Curso> tiposDeCursos = CargarTabla.obtenerTiposDeCursos(); // Obtener los tipos de cursos desde la base de datos
        List<Area> AreasCurso = CargarTabla.obtenerAreasProduccion();

        // Crear el nodo raíz del árbol
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("CURSOS");

        // Construir la estructura del árbol
        for (Curso tipoCurso : tiposDeCursos) {
            DefaultMutableTreeNode tipoCursoNode = new DefaultMutableTreeNode(tipoCurso.getNombre_Tipo_Curso());

            if (tipoCurso.getIdTipo_Curso() == 2) {
                // Agregar los cursos como hijos del tipo de curso
                for (Area area : AreasCurso) {
                    DefaultMutableTreeNode cursoNode = new DefaultMutableTreeNode(area.getNombre_Area());
                    tipoCursoNode.add(cursoNode);

                    List<Curso> cursoNodeProduccion = CargarTabla.obtenerCursosAsociadosProduccion(area.getIdArea());

                    // Agregar los cursos como hijos del tipo de curso
                    for (Curso curso : cursoNodeProduccion) {
                        DefaultMutableTreeNode cursoNodeProd = new DefaultMutableTreeNode(curso.getNombre_Curso());
                        cursoNode.add(cursoNodeProd);
                    }
                    // Agregar el tipo de curso como hijo del nodo raíz
                    root.add(tipoCursoNode);
                }
                root.add(tipoCursoNode);

            } else {
                // Obtener los cursos asociados al tipo de curso
                List<Curso> cursosAsociados = CargarTabla.obtenerCursosAsociados(tipoCurso.getIdTipo_Curso()); // Obtener los cursos asociados desde la base de datos

                // Agregar los cursos como hijos del tipo de curso
                for (Curso curso : cursosAsociados) {
                    DefaultMutableTreeNode cursoNode = new DefaultMutableTreeNode(curso.getNombre_Curso());
                    tipoCursoNode.add(cursoNode);
                }
                // Agregar el tipo de curso como hijo del nodo raíz
                root.add(tipoCursoNode);
            }
        }

        // Crear el nuevo modelo del árbol
        DefaultTreeModel newTreeModel = new DefaultTreeModel(root);

        // Crear los iconos personalizados para los nodos
        Icon iconTipoCurso = new ImageIcon("Images/IconPlus.png");
        Icon iconCurso = new ImageIcon("Images/IconCheckx16.png");

        // Crear el renderizador personalizado y asignarlo al JTree
        TableArbolCursos cellRenderer = new TableArbolCursos(iconTipoCurso, iconCurso);
        frm.jTree_Cursos.setCellRenderer(cellRenderer);

        // Asignar el nuevo modelo al JTree
        frm.jTree_Cursos.setModel(newTreeModel);

        // Asignar un MouseListener al JTree
        frm.jTree_Cursos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Obtener el nodo seleccionado
                    TreePath path = frm.jTree_Cursos.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                        // Verificar si el nodo seleccionado es un nodo de Curso
                        if (selectedNode.getParent() != null && selectedNode.getParent().getParent() != null) {
                            // Mostrar el JPopupMenu solo para los nodos de Curso
                            frm.jTree_Cursos.setComponentPopupMenu(frm.PopMenu_Curso);
                        }
                    }
                }
            }
        });
        frm.jTree_Cursos.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCursoBrigadistas(String idHistorial) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_brigadistas`";

        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio"));
                tbr.setNombre(rs.getString("Nombre"));
                tbr.setArea(rs.getString("Brigada"));
                tbr.setPuesto(rs.getString("Tipo"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCursoBrigadistasF(String idHistorial, AsistenteCurso mod, int i) {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql1 = "SET @id_historial_curso:=?";
        try {
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHistorial);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (i) {
            case 1: {

                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_brigadistas`"
                        + " WHERE Brigada = ?";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getArea());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio"));
                        tbr.setNombre(rs.getString("Nombre"));
                        tbr.setArea(rs.getString("Brigada"));
                        tbr.setPuesto(rs.getString("Tipo"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 2: {
                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_brigadistas`"
                        + " WHERE Turno = ? OR Turno IS NULL";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getTurno());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio"));
                        tbr.setNombre(rs.getString("Nombre"));
                        tbr.setArea(rs.getString("Brigada"));
                        tbr.setPuesto(rs.getString("Tipo"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                return lbu;
            }
            case 3: {
                String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_brigadistas`"
                        + " WHERE Brigada = ? AND (Turno = ? OR Turno IS NULL)";

                try {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, mod.getArea());
                    ps.setString(2, mod.getTurno());
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        AsistenteCurso tbr = new AsistenteCurso();
                        tbr.setId(rs.getString("Folio"));
                        tbr.setNombre(rs.getString("Nombre"));
                        tbr.setArea(rs.getString("Brigada"));
                        tbr.setPuesto(rs.getString("Tipo"));
                        lbu.add(tbr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            return lbu;
        }
        return null;
    }

    public static List<AsistenteCurso> buscarTablaAsistentesCursoBrigadistas(String idHistorial, String texto) {
        List<AsistenteCurso> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "Select * from view_asistentes_brigadistas "
                + "WHERE Folio LIKE '%" + texto + "%'"
                + "OR Nombre LIKE '%" + texto + "%'"
                + "ORDER BY Folio ASC";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio"));
                tbr.setNombre(rs.getString("Nombre"));
                tbr.setArea(rs.getString("Brigada"));
                tbr.setPuesto(rs.getString("Tipo"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<Puesto> cargarTablaPuestoCertificadoAgregarBuscar(String idCertificado, String texto) {
        List<Puesto> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM puesto p\n"
                + "LEFT JOIN certificado_puesto cp ON p.idPuesto = cp.puesto_idPuesto\n"
                + "AND cp.puesto_idPuesto = ? "
                + "INNER JOIN area a ON p.area_idArea=a.idArea\n"
                + "WHERE cp.puesto_idPuesto IS NULL  "
                + "AND Nombre_Puesto LIKE '%" + texto + "%'"
                + " OR Nombre_Puesto_Ingles LIKE '%" + texto + "%' \n"
                + "ORDER BY p.area_idArea ;";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, idCertificado);
            rs = ps.executeQuery();

            while (rs.next()) {
                Puesto tbr = new Puesto();
                tbr.setArea_NombreArea(rs.getString("Nombre_Area"));
                tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<AsistenteCurso> cargarTablaTrabajadorCapacitacion(String Area, String Puesto, String Turno, String Estado, int par) {
        List<AsistenteCurso> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        System.out.println("Area: " + Area + ", Estado: " + Estado);

        String sql1 = "SELECT * FROM sistema_capacitacion.view_lista_entrenamiento \n"
                + "WHERE Nombre_Area = '" + Area + "' \n"
                + "AND Entrenamiento = '" + Estado + "';";

        String sql2 = "SELECT * FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Nombre_Area = '" + Area + "' AND (Estado = '" + Estado + "' "
                + "AND (Entrenamiento != 'En Entrenamiento' AND Entrenamiento != 'En Entrenamiento en otro Puesto'));";

        String sql3 = "SELECT * FROM view_lista_entrenamiento \n"
                + "WHERE Nombre_Puesto = '" + Puesto + "' AND Nombre_Turno = '" + Turno + "'\n"
                + "AND Entrenamiento = '" + Estado + "';";

        String sql4 = "SELECT * FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Nombre_Puesto = '" + Puesto + "' AND Nombre_Turno = '" + Turno + "'\n"
                + "AND (Estado = '" + Estado + "' "
                + "AND (Entrenamiento != 'En Entrenamiento' AND Entrenamiento != 'En Entrenamiento en otro Puesto'));";

        String sql5 = "SELECT * FROM view_lista_entrenamiento \n"
                + "WHERE Nombre_Puesto = '" + Puesto + "' AND Nombre_Turno = '" + Turno + "'\n"
                + "AND Entrenamiento = '" + Estado + "';";

        String sql6 = "SELECT * FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Nombre_Puesto = '" + Puesto + "' AND Nombre_Turno = '" + Turno + "'\n"
                + "AND (Estado = '" + Estado + "' "
                + "AND (Entrenamiento != 'En Entrenamiento' AND Entrenamiento != 'En Entrenamiento en otro Puesto'));";

        String sql7 = "SELECT * FROM view_lista_entrenamiento \n"
                + "WHERE Nombre_Area = '" + Area + "' AND Nombre_Puesto = '" + Puesto + "' AND Nombre_Turno = '" + Turno + "'\n"
                + "AND Entrenamiento = '" + Estado + "';";

        String sql8 = "SELECT * FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Nombre_Area = '" + Area + "' AND Nombre_Puesto = '" + Puesto + "' AND Nombre_Turno = '" + Turno + "'\n"
                + "AND (Estado = '" + Estado + "' "
                + "AND (Entrenamiento != 'En Entrenamiento' AND Entrenamiento != 'En Entrenamiento en otro Puesto'));";

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            switch (par) {
                case 1:
                    ps = con.prepareStatement(sql1);
                    break;
                case 2:
                    ps = con.prepareStatement(sql2);
                    break;
                case 3:
                    ps = con.prepareStatement(sql3);
                    break;
                case 4:
                    ps = con.prepareCall(sql4);
                    break;
                case 5:
                    ps = con.prepareStatement(sql5);
                    break;
                case 6:
                    ps = con.prepareCall(sql6);
                    break;
                case 7:
                    ps = con.prepareStatement(sql7);
                    break;
                case 8:
                    ps = con.prepareCall(sql8);
                    break;
                default:
                    ps = con.prepareCall(null);
                    break;
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio_Trabajador"));
                tbr.setNombre(rs.getString("Nombre_Trabajador"));
                tbr.setPuesto(rs.getString("Nombre_Puesto"));
                tbr.setTurno(rs.getString("Nombre_Turno"));
                if (par == 1) {
                    tbr.setEntrenamiento(rs.getString("Cursos"));
                }
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<AsistenteCurso> cargarTablaTrabajadorCertificados(String Area, String Puesto, String Turno, String Estado, int par) {
        List<AsistenteCurso> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_reporte_certificados \n"
                + "JOIN view_trabajador on view_reporte_certificados.folio_trabajador = view_trabajador.folio_trabajador \n"
                + "WHERE obsoleto > 0 ";
        String sql0 = "SELECT * FROM sistema_capacitacion.view_reporte_certificados \n"
                + "JOIN view_trabajador on view_reporte_certificados.folio_trabajador = view_trabajador.folio_trabajador \n"
                + "WHERE certificados = ? ";
        String sql1 = "SELECT * FROM sistema_capacitacion.view_reporte_certificados \n"
                + "JOIN view_trabajador on view_reporte_certificados.folio_trabajador = view_trabajador.folio_trabajador \n"
                + "WHERE certificados = ? AND view_reporte_certificados.Nombre_Area = '" + Area + "'";
        String sql2 = "SELECT * FROM sistema_capacitacion.view_reporte_certificados \n"
                + "JOIN view_trabajador on view_reporte_certificados.folio_trabajador = view_trabajador.folio_trabajador \n"
                + "WHERE certificados = ? AND view_reporte_certificados.Nombre_Area = '" + Area + "' AND view_reporte_certificados.Nombre_Turno = '" + Turno + "'";
        String sql3 = "SELECT * FROM sistema_capacitacion.view_reporte_certificados \n"
                + "JOIN view_trabajador on view_reporte_certificados.folio_trabajador = view_trabajador.folio_trabajador \n"
                + "WHERE certificados = ? AND view_reporte_certificados.Nombre_Area = '" + Area + "' AND view_reporte_certificados.Nombre_Puesto = '" + Puesto + "'";
        String sql4 = "SELECT * FROM sistema_capacitacion.view_reporte_certificados \n"
                + "JOIN view_trabajador on view_reporte_certificados.folio_trabajador = view_trabajador.folio_trabajador \n"
                + "WHERE certificados = ? AND view_reporte_certificados.Nombre_Area = '" + Area + "' AND view_reporte_certificados.Nombre_Puesto = '" + Puesto + "' AND view_reporte_certificados.Nombre_Turno = '" + Turno + "'";

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            switch (par) {
                case -1:
                    ps = con.prepareStatement(sql);
                    break;
                case 0:
                    ps = con.prepareStatement(sql0);
                    break;
                case 1:
                    ps = con.prepareStatement(sql1);
                    break;
                case 2:
                    ps = con.prepareStatement(sql2);
                    break;
                case 3:
                    ps = con.prepareStatement(sql3);
                    break;
                case 4:
                    ps = con.prepareStatement(sql4);
                    break;
            }

            switch (Estado) {
                case "Obsoleto":
                    break;
                case "Ninguna":
                    ps.setInt(1, 0);
                    break;
                case "Primera":
                    ps.setInt(1, 1);
                    break;
                case "Segunda":
                    ps.setInt(1, 2);
                    break;
                case "Tercera":
                    ps.setInt(1, 3);
                    break;
                case "Cuarta":
                    ps.setInt(1, 4);
                    break;
                case "Quinta":
                    ps.setInt(1, 5);
                    break;
                case "Sexta":
                    ps.setInt(1, 6);
                    break;
            }

            System.out.println(ps);
            rs = ps.executeQuery();

            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("Folio_Trabajador"));
                tbr.setNombre(rs.getString("Nombre_Trabajador"));
                tbr.setPuesto(rs.getString("Nombre_Puesto"));
                tbr.setTurno(rs.getString("Nombre_Turno"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    public static List<PersonalCertificado> cargarTablaPersonalCertificado(int año, int mes, String Area, String Puesto, String Turno, int par) {
        List<PersonalCertificado> personal = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql0 = "SELECT * FROM sistema_capacitacion.asistentes_certificados ac\n"
                + "JOIN certificado c ON ac.certificado_idcertificado=c.idcertificado\n"
                + "LEFT JOIN view_trabajador vt ON ac.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                + "WHERE YEAR(fecha_certificacion) = ? AND MONTH(fecha_certificacion) = ?\n"
                + "ORDER BY fecha_certificacion";
        String sql1 = "SELECT * FROM sistema_capacitacion.asistentes_certificados ac\n"
                + "JOIN certificado c ON ac.certificado_idcertificado=c.idcertificado\n"
                + "LEFT JOIN view_trabajador vt ON ac.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                + "WHERE YEAR(fecha_certificacion) = ? AND MONTH(fecha_certificacion) = ?\n"
                + "AND nombre_area = ?"
                + "ORDER BY fecha_certificacion;";
        String sql2 = "SELECT * FROM sistema_capacitacion.asistentes_certificados ac\n"
                + "JOIN certificado c ON ac.certificado_idcertificado=c.idcertificado\n"
                + "LEFT JOIN view_trabajador vt ON ac.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                + "WHERE YEAR(fecha_certificacion) = ? AND MONTH(fecha_certificacion) = ?\n"
                + "AND nombre_area = ? AND nombre_turno = ?"
                + "ORDER BY fecha_certificacion;";
        String sql3 = "SELECT * FROM sistema_capacitacion.asistentes_certificados ac\n"
                + "JOIN certificado c ON ac.certificado_idcertificado=c.idcertificado\n"
                + "LEFT JOIN view_trabajador vt ON ac.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                + "WHERE YEAR(fecha_certificacion) = ? AND MONTH(fecha_certificacion) = ?\n"
                + "AND nombre_area = ? AND nombre_puesto = ?"
                + "ORDER BY fecha_certificacion;";
        String sql4 = "SELECT * FROM sistema_capacitacion.asistentes_certificados ac\n"
                + "JOIN certificado c ON ac.certificado_idcertificado=c.idcertificado\n"
                + "LEFT JOIN view_trabajador vt ON ac.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                + "WHERE YEAR(fecha_certificacion) = ? AND MONTH(fecha_certificacion) = ?\n"
                + "AND nombre_area = ? AND nombre_puesto = ? AND nombre_turno = ?"
                + "ORDER BY fecha_certificacion;";

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            switch (par) {
                case 0:
                    ps = con.prepareStatement(sql0);
                    ps.setInt(1, año);
                    ps.setInt(2, mes);
                    break;
                case 1:
                    ps = con.prepareStatement(sql1);
                    ps.setInt(1, año);
                    ps.setInt(2, mes);
                    ps.setString(3, Area);
                    break;
                case 2:
                    ps = con.prepareStatement(sql2);
                    ps.setInt(1, año);
                    ps.setInt(2, mes);
                    ps.setString(3, Area);
                    ps.setString(4, Turno);
                    break;
                case 3:
                    ps = con.prepareStatement(sql3);
                    ps.setInt(1, año);
                    ps.setInt(2, mes);
                    ps.setString(3, Area);
                    ps.setString(4, Puesto);
                    break;
                case 4:
                    ps = con.prepareStatement(sql4);
                    ps.setInt(1, año);
                    ps.setInt(2, mes);
                    ps.setString(3, Area);
                    ps.setString(4, Puesto);
                    ps.setString(5, Turno);
                    break;
            }
            rs = ps.executeQuery();
            System.out.println(ps);

            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdTrabajador_Certificado(rs.getString("asistentes_curso_idAsistente"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setNombre_Certificado(rs.getString("Nombre_Certificado"));
                tbr.setTipo_certificado(rs.getString("estado_certificacion"));
                tbr.setFecha_certificacion(rs.getString("fecha_certificacion"));
                personal.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return personal;
    }

    public static List<PersonalCertificado> cargarTablaPersonalCertificadoProceso(int año, int mes, String proceso) {

        List<PersonalCertificado> personal = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT *\n"
                + "FROM view_asistentes_certificados\n"
                + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_Trabajador = view_lbu.folio_trabajador\n"
                + "WHERE YEAR(fecha_termino) = ? AND MONTH(fecha_termino) = ? AND tipo_proceso = ?\n"
                + "GROUP BY view_asistentes_certificados.Folio_Trabajador ORDER BY fecha_termino DESC;";

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement(sql);
            ps.setInt(1, año);
            ps.setInt(2, mes);
            ps.setInt(3, Integer.parseInt(proceso));
            rs = ps.executeQuery();

            while (rs.next()) {
                PersonalCertificado tbr = new PersonalCertificado();
                tbr.setIdTrabajador_Certificado(rs.getString("Folio_Trabajador"));
                tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                tbr.setNombre_Certificado(rs.getString("Nombre_Certificado"));
                tbr.setTipo_certificado(rs.getString("estado_certificacion"));
                tbr.setFecha_certificacion(rs.getString("fecha_termino"));
                personal.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return personal;
    }

    public static List<HistorialCurso> cargarTablaHistorialTipoCurso(int idTipoCurso) {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historial_tipocurso WHERE id_tipocurso = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idTipoCurso);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
                tbr.setArea(rs.getString("nombre_Area"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    private static List<Area> obtenerAreasProduccion() {
        List<Area> trabajador = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM area \n"
                + "ORDER BY idArea ;";

        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Area tbr = new Area();
                tbr.setIdArea(rs.getInt("idArea"));
                tbr.setNombre_Area(rs.getString("Nombre_Area"));
                trabajador.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trabajador;
    }

    private static List<Curso> obtenerCursosAsociadosProduccion(int idArea) {
        List<Curso> curso = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM curso\n"
                + "JOIN certificado ON curso.id_certificado = certificado.idcertificado\n"
                + "LEFT JOIN certificado_puesto ON  certificado.idcertificado= certificado_puesto.certificado_idCertificado\n"
                + "LEFT JOIN puesto ON certificado_puesto.puesto_idPuesto=puesto.idPuesto\n"
                + "LEFT JOIN area on puesto.area_idArea=area.idArea\n"
                + "WHERE id_tipocurso = 2\n"
                + "AND idArea = ?\n"
                + "GROUP BY idCurso;";

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";

        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement(sql);
            ps.setInt(1, idArea);
            rs = ps.executeQuery();

            while (rs.next()) {
                Curso tbr = new Curso();
                tbr.setIdCurso(rs.getInt("idcurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                curso.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return curso;
    }

    public static void cargar() {
        Connection con = conn.getConnection();
        String sql = "INSERT INTO `sistema_capacitacion`.`asistentes_certificados`\n"
                + "(`asistentes_curso_idAsistente`,\n"
                + "`certificado_idcertificado`,\n"
                + "`fecha_inicio`,\n"
                + "`fecha_termino`,\n"
                + "`fecha_certificacion`,\n"
                + "`tipo_certificacion`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?)";
        try {
            FileInputStream file = new FileInputStream(
                    new File("Y:\\Capacitacion\\Benjamin\\Nuevo Estado de los Certificados 10 de Mayo 2023.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(file);
            //Hoja donde estan los datos
            XSSFSheet hoja = wb.getSheetAt(0);
            int numFilas = 0;

            for (Row fila : hoja) {
                if (fila.getPhysicalNumberOfCells() > 0) {
                    numFilas++;
                }
            }

            // a inicia en 0 por que no hay encabezados
            // Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 686; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);
                //Fila con el nombre certificado
                Cell cell = fila.getCell(10);
                String nomCer = cell.getStringCellValue();
                if (nomCer != null && !nomCer.isEmpty()) {
                    ps = con.prepareStatement(sql);
                    //Folio del trabajador
                    ps.setInt(1, (int) fila.getCell(0).getNumericCellValue());
                    int idCer = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                            "certificado", "idcertificado", "nombre_certificado", nomCer));
                    ps.setInt(2, (int) idCer);
                    //Fecha de certificacion
                    Cell cell2 = fila.getCell(6);
                    ps.setString(3, DateTools.ExceltoMySQLfromCell(cell2));
                    //Fecha de certificacion
                    Cell cell3 = fila.getCell(7);
                    ps.setString(4, DateTools.ExceltoMySQLfromCell(cell3));
                    //Fecha de certificacion
                    Cell cell4 = fila.getCell(9);
                    ps.setString(5, DateTools.ExceltoMySQLfromCell(cell4));
                    //Tipo de la certificacion
                    ps.setString(6, fila.getCell(8).getStringCellValue());
                    System.out.println(ps);
                    ps.execute();
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void cargar2() {
        Connection con = conn.getConnection();
        //Funcion de Registro para Trabajadores a travez de archivo Excel;
        String sql = "INSERT INTO `sistema_capacitacion`.`administrativos`\n"
                + "(`Folio_Administrativo`,\n"
                + "`Nombre_Administrativo`,\n"
                + "`CURP_administrativo`,\n"
                + "`RFC_administrativo`,\n"
                + "`IMSS_administrativo`,\n"
                + "`Fecha_antiguedad`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?);";

        String sql1 = "UPDATE `sistema_capacitacion`.`administrativos`\n"
                + "SET\n"
                + "`Nombre_Administrativo` = ?,\n"
                + "`CURP_administrativo` = ?,\n"
                + "`RFC_administrativo` = ?,\n"
                + "`IMSS_administrativo` = ?,\n"
                + "`Fecha_antiguedad` = ?\n"
                + "WHERE `Folio_Administrativo` = ?;";

        String consulta = "SELECT *\n"
                + "FROM `administrativos`\n"
                + "WHERE `administrativos`.`Folio_Administrativo` = ?;";
        try {
            File file = new File("Y:\\Capacitacion\\Benjamin\\información para capacitacion.xlsx");

            Sheet hoja = getWorkbook(file.getAbsolutePath());
            int numFilas = hoja.getLastRowNum();

            // a inicia en 0 por que no hay encabezados
            // Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 1; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);
                Cell cell = fila.getCell(0);
                int folio = (int) cell.getNumericCellValue();

                ps = con.prepareStatement(consulta);
                ps.setInt(1, folio);
                rs = ps.executeQuery();

                if (rs.next()) {
                    ps = con.prepareStatement(sql1);
                    ps.setString(1, fila.getCell(1).getStringCellValue());
                    ps.setString(2, fila.getCell(2).getStringCellValue());
                    ps.setString(3, fila.getCell(3).getStringCellValue());
                    Cell cell2 = fila.getCell(4);
                    long imss = (long) cell2.getNumericCellValue();
                    ps.setString(4, String.valueOf(imss));
                    Cell cell1 = fila.getCell(5);
                    ps.setString(5, DateTools.ExceltoMySQLfromCell(cell1));
                    ps.setInt(6, (int) fila.getCell(0).getNumericCellValue());
                    System.out.println(ps);
                    ps.execute();
                } else {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, (int) fila.getCell(0).getNumericCellValue());
                    ps.setString(2, fila.getCell(1).getStringCellValue());
                    ps.setString(3, fila.getCell(2).getStringCellValue());
                    ps.setString(4, fila.getCell(3).getStringCellValue());
                    Cell cell2 = fila.getCell(4);
                    long imss = (long) cell2.getNumericCellValue();
                    ps.setString(5, String.valueOf(imss));
                    Cell cell1 = fila.getCell(5);
                    ps.setString(6, DateTools.ExceltoMySQLfromCell(cell1));
                    System.out.println(ps);
                    System.out.println(ps);
                    ps.execute();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void cargar3() {
        Connection con = conn.getConnection();
        //Funcion de Registro para Trabajadores a travez de archivo Excel;
        String sql = "INSERT INTO `sistema_capacitacion`.`baja`\n"
                + "(`Folio_Trabajador`,\n"
                + "`Nombre_Trabajador`,\n"
                + "`Fecha_Baja`,\n"
                + "`Comentario`,\n"
                + "`Fecha_Antiguedad`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?);";

        String sql1 = "UPDATE `sistema_capacitacion`.`baja`\n"
                + "SET\n"
                + "`Nombre_Trabajador` = ?,\n"
                + "`Fecha_Baja` = ?,\n"
                + "`Comentario` = ?,\n"
                + "`Fecha_Antiguedad` = ?\n"
                + "WHERE `Folio_Trabajador` = ?;";

        String consulta = "SELECT *\n"
                + "FROM `baja`\n"
                + "WHERE `baja`.`Folio_Trabajador` = ?;";

        try {
            File file = new File("Y:\\Capacitacion\\Benjamin\\BAJAS BETSI (16).xls");

            Sheet hoja = getWorkbook(file.getAbsolutePath());
            int numFilas = hoja.getLastRowNum();

            // a inicia en 0 por que no hay encabezados
            // Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 4; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);
                //Fila con el nombre certificado
                Cell cell = fila.getCell(2);
                int folio = (int) cell.getNumericCellValue();

                ps = con.prepareStatement(consulta);
                ps.setInt(1, folio);
                rs = ps.executeQuery();

                if (rs.next()) {
                    ps = con.prepareStatement(sql1);
                    ps.setString(1, fila.getCell(3).getStringCellValue());
                    Cell cell2 = fila.getCell(4);
                    ps.setString(2, DateTools.ExceltoMySQLfromCell(cell2));
                    ps.setString(3, fila.getCell(5).getStringCellValue() + " " + fila.getCell(6).getStringCellValue());
                    Cell cell3 = fila.getCell(8);
                    ps.setString(4, DateTools.ExceltoMySQLfromCell(cell3));
                    ps.setInt(5, (int) fila.getCell(2).getNumericCellValue());
                    System.out.println(ps);
                    ps.execute();
                } else {
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, (int) fila.getCell(2).getNumericCellValue());
                    ps.setString(2, fila.getCell(3).getStringCellValue());
                    Cell cell2 = fila.getCell(4);
                    ps.setString(3, DateTools.ExceltoMySQLfromCell(cell2));
                    ps.setString(4, fila.getCell(5).getStringCellValue() + " " + fila.getCell(6).getStringCellValue());
                    Cell cell4 = fila.getCell(8);
                    ps.setString(5, DateTools.ExceltoMySQLfromCell(cell4));
                    System.out.println(ps);
                    ps.execute();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void cargar4() {
        Connection con = conn.getConnection();
        //Funcion de Registro para Trabajadores a travez de archivo Excel;
        String sql1 = "UPDATE `sistema_capacitacion`.`administrativos`\n"
                + "SET\n"
                + "`cia_administrativo` = ?,\n"
                + "`area_administrativo` = ?,\n"
                + "`puesto_administrativo` = ?\n"
                + "WHERE `Folio_Administrativo` = ?;";

        String consulta = "SELECT *\n"
                + "FROM `administrativos`\n"
                + "WHERE `administrativos`.`Folio_Administrativo` = ?;";
        try {
            File file = new File("Y:\\Capacitacion\\Benjamin\\Copia de Listado Administrativos.xlsx");

            Sheet hoja = getWorkbook(file.getAbsolutePath());
            int numFilas = hoja.getLastRowNum();

            // a inicia en 0 por que no hay encabezados
            // Nota: si se agregan encabezados el valor de a seria 1
            for (int a = 1; a <= numFilas; a++) {
                Row fila = hoja.getRow(a);
                Cell cell = fila.getCell(0);
                int folio = (int) cell.getNumericCellValue();

                ps = con.prepareStatement(consulta);
                ps.setInt(1, folio);
                rs = ps.executeQuery();

                if (rs.next()) {
                    ps = con.prepareStatement(sql1);
                    ps.setString(1, fila.getCell(3).getStringCellValue());
                    ps.setString(2, fila.getCell(4).getStringCellValue());
                    Cell cell1 = fila.getCell(5);
                    if (cell1 != null) {
                        ps.setString(3, fila.getCell(5).getStringCellValue());
                    } else {
                        ps.setString(3, "");
                    }
                    ps.setInt(4, (int) fila.getCell(0).getNumericCellValue());
                    System.out.println(ps);
                    ps.execute();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static List<AsistenteCurso> cargarTablaAsistentesCursos() {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_cursos`\n"
                + "LEFT JOIN view_trabajador ON view_asistentes_cursos.idAsistentes_curso=view_trabajador.Folio_Trabajador\n"
                + "WHERE status_entrenamiento = 'En Entrenamiento'";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("idAsistentes_curso"));
                tbr.setNombre(rs.getString("nombre_asistente"));
                tbr.setPuesto(rs.getString("asistente_type"));
                tbr.setTurno(rs.getString("nombre_turno"));
                tbr.setEntrenamiento(rs.getString("tipo_entrenamiento"));
                tbr.setStatus(rs.getString("status_entrenamiento"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<HistorialCurso> cargarTablaHistorialCursosActivos() {
        List<HistorialCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM sistema_capacitacion.view_historialcursos\n"
                + "WHERE estado_curso = 'Activo' ORDER BY id_tipocurso";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialCurso tbr = new HistorialCurso();
                tbr.setIdHistorialCurso(rs.getInt("idHistorial"));
//                tbr.setNombre_Taller(rs.getString("nombre_Taller"));
                tbr.setIdCurso(rs.getInt("idCurso"));
                tbr.setNombre_Curso(rs.getString("nombre_curso"));
                tbr.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                tbr.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                tbr.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                tbr.setTiempo_estimado(rs.getDouble("duracion_curso"));
                tbr.setNum_asistentes(rs.getInt("num_asistentes"));
                tbr.setHoras_asistente(rs.getDouble("horas_asistentes"));
                tbr.setStatus_curso(rs.getString("estado_curso"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<AsistenteCurso> cargarTablaAsistentesProceso() {
        List<AsistenteCurso> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`view_asistentes_cursos`\n"
                + "LEFT JOIN view_trabajador ON view_asistentes_cursos.idAsistentes_curso=view_trabajador.Folio_Trabajador\n"
                + "WHERE status_entrenamiento = 'En Proceso de Certificación'";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AsistenteCurso tbr = new AsistenteCurso();
                tbr.setId(rs.getString("idAsistentes_curso"));
                tbr.setNombre(rs.getString("nombre_asistente"));
                tbr.setPuesto(rs.getString("asistente_type"));
                tbr.setTurno(rs.getString("nombre_turno"));
                tbr.setEntrenamiento(rs.getString("tipo_entrenamiento"));
                tbr.setStatus(rs.getString("status_entrenamiento"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Brigadas> cargarTablaBrigadas() {
        List<Brigadas> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`brigadas`";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Brigadas tbr = new Brigadas();
                tbr.setIdBrigada(rs.getInt("idbrigadas"));
                tbr.setNombre_Brigada(rs.getString("nombre_brigada"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Brigadistas> cargarTablaBrigadistas() {
        List<Brigadistas> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`view_brigadistas`";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Brigadistas tbr = new Brigadistas();
                tbr.setNombre_Brigada(rs.getString("nombre_Brigada"));
                tbr.setIdBrigadista(rs.getInt("Nomina"));
                tbr.setNombre_Brigadista(rs.getString("Nombre"));
                tbr.setTipo_Brigadista(rs.getString("Trabajador"));
                tbr.setArea_Brigadista(rs.getString("AREA"));
                tbr.setPuesto_Brigadista(rs.getString("PUESTO"));
                tbr.setTurno_Brigadista(rs.getString("TURNO"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }

    public static List<Brigadistas> cargarTablaBrigadistasBrigada(int idBrigada) {
        List<Brigadistas> lbu = new ArrayList<>();
        Connection con = conn.getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`view_brigadistas`\n"
                + "WHERE idbrigadas = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, idBrigada);
            rs = ps.executeQuery();
            while (rs.next()) {
                Brigadistas tbr = new Brigadistas();
                tbr.setIdBrigadista(rs.getInt("Nomina"));
                tbr.setNombre_Brigadista(rs.getString("Nombre"));
                tbr.setTipo_Brigadista(rs.getString("Trabajador"));
                tbr.setArea_Brigadista(rs.getString("AREA"));
                tbr.setPuesto_Brigadista(rs.getString("PUESTO"));
                tbr.setTurno_Brigadista(rs.getString("TURNO"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargarTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lbu;
    }
}
