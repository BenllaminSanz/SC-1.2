package Querys;

import Functions.DateTools;
import Model.PersonalCertificado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasPersonalCertificado extends Conexion {

    public boolean buscar(PersonalCertificado mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`asistentes_certificados`"
                + " WHERE idCertificacion = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCertificacion());
            rs = ps.executeQuery();

            if (rs.next()) {
                mod.setIdTrabajador_Certificado(
                        rs.getString("asistentes_curso_idAsistente"));
                mod.setApellidos(rs.getString("Apellidos"));
                mod.setNombres(rs.getString("Nombres"));
                mod.setId_Certificado(rs.getInt("certificado_idCertificado"));
                mod.setTipo_certificado(rs.getString("tipo_certificacion"));
                mod.setFecha_inicio(
                        DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                mod.setFecha_certificacion(
                        DateTools.MySQLtoString(rs.getDate("fecha_certificacion")));
                return true;
            }
            return false;
        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean agregar(PersonalCertificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`asistentes_certificados`\n"
                + "(`asistentes_curso_idAsistente`,\n"
                + "`certificado_idcertificado`,\n"
                + "`fecha_inicio`,\n"
                + "`fecha_certificacion`,\n"
                + "`tipo_certificacion`,\n"
                + "`Apellidos`,\n"
                + "`Nombres`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";

        String sql1 = "CALL actualizar_certificados(?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getIdTrabajador_Certificado());
            ps.setInt(2, mod.getId_Certificado());
            ps.setString(3, DateTools.StringtoMySQL(mod.getFecha_inicio()));
            ps.setString(4, DateTools.StringtoMySQL(mod.getFecha_certificacion()));
            ps.setString(5, mod.getTipo_certificado());
            ps.setString(6, mod.getApellidos());
            ps.setString(7, mod.getNombres());
            ps.execute();

            ps = con.prepareStatement(sql1);
            ps.setString(1, mod.getIdTrabajador_Certificado());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean actualizar(PersonalCertificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`asistentes_certificados`\n"
                + "SET\n"
                + "`asistentes_curso_idAsistente` = ?,\n"
                + "`certificado_idcertificado` = ?,\n"
                + "`fecha_inicio` = ?,\n"
                + "`fecha_certificacion` = ?,\n"
                + "`tipo_certificacion` = ?,\n"
                + "`Apellidos` = ?,\n"
                + "`Nombres` = ?\n"
                + "WHERE `idCertificacion` = ?;";
        
        String sql1 = "CALL actualizar_certificados(?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getIdTrabajador_Certificado());
            ps.setInt(2, mod.getId_Certificado());
            ps.setString(3, DateTools.StringtoMySQL(mod.getFecha_inicio()));
            ps.setString(4, DateTools.StringtoMySQL(mod.getFecha_certificacion()));
            ps.setString(5, mod.getTipo_certificado());
            ps.setString(6, mod.getApellidos());
            ps.setString(7, mod.getNombres());
            ps.setInt(8, mod.getIdCertificacion());
            ps.execute();

            System.out.println(ps);
            ps = con.prepareStatement(sql1);
            ps.setString(1, mod.getIdTrabajador_Certificado());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean eliminar(String idCertificacion, String idTrabajador) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`asistentes_certificados`\n"
                + "WHERE idCertificacion = ?;";

        String sql1 = "CALL actualizar_certificados(?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, idCertificacion);
            ps.execute();

            ps = con.prepareCall(sql1);
            ps.setString(1, idTrabajador);
            ps.execute();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
