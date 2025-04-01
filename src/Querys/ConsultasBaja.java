package Querys;

import Functions.DateTools;
import Model.Bajas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasBaja extends Conexion {

    //Funcion de Eliminar para Trabajadores
    public boolean buscar(Bajas tbr) {
        String sqlMode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''))";
        String sql = "SELECT * FROM view_bajas WHERE Folio_Trabajador = ?";

        try (Connection con = getConnection(); 
                PreparedStatement psMode = con.prepareStatement(sqlMode); 
                PreparedStatement ps = con.prepareStatement(sql)) {

            // Ejecutar el cambio de modo SQL
            psMode.executeUpdate();

            // Configurar y ejecutar la consulta principal
            ps.setString(1, tbr.getFolio_Trabajador());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tbr.setNombre_Trabajador(rs.getString("Nombre_Trabajador"));
                    tbr.setFecha_Baja(DateTools.MySQLtoString(rs.getDate("Fecha_Baja")));
                    tbr.setCURP_Trabajador(rs.getString("Curp_Trabajador"));
                    tbr.setRFC_Trabajador(rs.getString("RFC_Trabajador"));
                    tbr.setIMSS_Trabajador(rs.getString("IMSS_Trabajador"));
                    tbr.setFecha_Antiguedad(DateTools.MySQLtoString(rs.getDate("Fecha_Antiguedad")));
                    tbr.setÁrea_Trabajador(rs.getInt("puesto_area_idArea"));
                    tbr.setTurno_Trabajador(rs.getInt("turno_idTurno"));
                    tbr.setNombre_Supervisor(rs.getString("supervisor"));
                    tbr.setPuesto_Trabajador(rs.getInt("puesto_idPuesto"));
                    Double salario = rs.getString("SalarioDiario_Trabajador") != null
                            ? Double.valueOf(rs.getString("SalarioDiario_Trabajador"))
                            : 0.0;
                    tbr.setSalarioDiario_Trabajador(salario);
                    tbr.setEmail_Trabajador(rs.getString("Email_Trabajador"));
                    tbr.setTeléfono_Trabajador(rs.getString("Teléfono_Trabajador"));
                    tbr.setFecha_Cumpleaños(DateTools.MySQLtoString(rs.getDate("Fecha_Cumpleaños")));
                    tbr.setComentario(rs.getString("Comentario"));
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Funcion de Eliminar para Trabajadores
    public boolean eliminar(Bajas tbr) {

        String sql = "DELETE FROM `baja` WHERE (`Folio_Trabajador` = ?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tbr.getFolio_Trabajador());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean modificar(Bajas tbr, String folio) {

        String sql = "UPDATE `sistema_capacitacion`.`baja`\n"
                + "SET\n"
                + "`Folio_Trabajador` = ?,\n"
                + "`Nombre_Trabajador` = ?,\n"
                + "`Fecha_Baja` = ?,\n"
                + "`Comentario` = ?,\n"
                + "`CURP_Trabajador` = ?,\n"
                + "`RFC_Trabajador` = ?,\n"
                + "`IMSS_Trabajador` = ?,\n"
                + "`Fecha_Antiguedad` = ?,\n"
                + "`puesto_area_idArea` = ?,\n"
                + "`turno_idTurno` = ?,\n"
                + "`puesto_idPuesto` = ?,\n"
                + "`supervisor` = ?,\n"
                + "`SalarioDiario_Trabajador` = ?,\n"
                + "`Email_Trabajador` = ?,\n"
                + "`Teléfono_Trabajador` = ?,\n"
                + "`Fecha_Cumpleaños` = ?\n"
                + "WHERE `Folio_Trabajador` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tbr.getFolio_Trabajador());
            ps.setString(2, tbr.getNombre_Trabajador());
            ps.setString(3, DateTools.StringtoMySQL(tbr.getFecha_Baja()));
            ps.setString(4, tbr.getComentario());
            ps.setString(5, tbr.getCURP_Trabajador());
            ps.setString(6, tbr.getRFC_Trabajador());
            ps.setString(7, tbr.getIMSS_Trabajador());
            ps.setString(8, DateTools.StringtoMySQL(tbr.getFecha_Antiguedad()));
            ps.setInt(9, tbr.getÁrea_Trabajador());
            ps.setInt(10, tbr.getTurno_Trabajador());
            ps.setInt(11, tbr.getPuesto_Trabajador());
            ps.setString(12, tbr.getNombre_Supervisor());
            ps.setDouble(13, tbr.getSalarioDiario_Trabajador());
            ps.setString(14, tbr.getEmail_Trabajador());
            ps.setString(15, tbr.getTeléfono_Trabajador());
            ps.setString(16, DateTools.StringtoMySQL(tbr.getFecha_Cumpleaños()));
            ps.setString(17, folio);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
