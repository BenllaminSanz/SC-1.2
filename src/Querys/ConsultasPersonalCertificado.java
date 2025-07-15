package Querys;

import Functions.DateTools;
import Model.PersonalCertificado;
import java.sql.CallableStatement;
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
        CallableStatement cs = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`asistentes_certificados` "
                + "(`asistentes_curso_idAsistente`, "
                + "`certificado_idcertificado`, "
                + "`fecha_inicio`, "
                + "`fecha_certificacion`, "
                + "`tipo_certificacion`, "
                + "`Apellidos`, "
                + "`Nombres`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try {
            // Ejecutar INSERT
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getIdTrabajador_Certificado());
            ps.setInt(2, mod.getId_Certificado());
            ps.setString(3, DateTools.StringtoMySQL(mod.getFecha_inicio()));
            ps.setString(4, DateTools.StringtoMySQL(mod.getFecha_certificacion()));
            ps.setString(5, mod.getTipo_certificado());
            ps.setString(6, mod.getApellidos());
            ps.setString(7, mod.getNombres());
            ps.executeUpdate();

            // Llamar procedimiento para actualizar certificados
            cs = con.prepareCall("{CALL actualizar_certificados(?)}");
            cs.setInt(1, Integer.parseInt(mod.getIdTrabajador_Certificado())); // Asegúrate que sea int
            cs.execute();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPersonalCertificado.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean actualizar(PersonalCertificado mod) {
        PreparedStatement ps = null;
        CallableStatement cs = null;  // Para llamar al procedimiento almacenado
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`asistentes_certificados` "
                + "SET "
                + "`asistentes_curso_idAsistente` = ?, "
                + "`certificado_idcertificado` = ?, "
                + "`fecha_inicio` = ?, "
                + "`fecha_certificacion` = ?, "
                + "`tipo_certificacion` = ?, "
                + "`Apellidos` = ?, "
                + "`Nombres` = ? "
                + "WHERE `idCertificacion` = ?;";

        try {
            // 1. Ejecutar UPDATE
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getIdTrabajador_Certificado());
            ps.setInt(2, mod.getId_Certificado());
            ps.setString(3, DateTools.StringtoMySQL(mod.getFecha_inicio()));
            ps.setString(4, DateTools.StringtoMySQL(mod.getFecha_certificacion()));
            ps.setString(5, mod.getTipo_certificado());
            ps.setString(6, mod.getApellidos());
            ps.setString(7, mod.getNombres());
            ps.setInt(8, mod.getIdCertificacion());
            ps.executeUpdate();

            // 2. Llamar al procedimiento almacenado
            cs = con.prepareCall("{CALL actualizar_certificados(?)}");
            // El parámetro que espera es el id del asistente, que en tu caso parece ser mod.getIdTrabajador_Certificado()
            // pero mod.getIdTrabajador_Certificado() es String, tu procedimiento espera INT. Si es un int, adapta aquí.
            cs.setInt(1, Integer.parseInt(mod.getIdTrabajador_Certificado()));
            cs.execute();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPersonalCertificado.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean eliminar(String idCertificacion, String idTrabajador) {
        PreparedStatement ps = null;
        CallableStatement cs = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`asistentes_certificados` "
                + "WHERE idCertificacion = ?;";

        try {
            // Ejecutar DELETE
            ps = con.prepareStatement(sql);
            ps.setString(1, idCertificacion);
            ps.executeUpdate();

            // Llamar procedimiento para actualizar certificados
            cs = con.prepareCall("{CALL actualizar_certificados(?)}");
            cs.setInt(1, Integer.parseInt(idTrabajador)); // asegúrate que sea int
            cs.execute();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
