package Querys;

import Model.Curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasCurso extends Conexion {

    public boolean buscar(Curso mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM curso WHERE idCurso = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCurso());
            rs = ps.executeQuery();

            if (rs.next()) {
                mod.setNombre_Curso(rs.getString("nombre_curso"));
                mod.setIdTipo_Curso(rs.getInt("id_tipocurso"));
                mod.setSemanas(rs.getInt("duracion_curso"));
                mod.setObjetivo_curso(rs.getString("objetivo_curso"));
                mod.setIdCertificado(rs.getInt("id_certificado"));
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

    public boolean agregar(Curso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        String sql = "INSERT INTO `sistema_capacitacion`.`curso`\n"
                + "(`nombre_curso`,\n"
                + "`id_tipocurso`,\n"
                + "`duracion_curso`,\n"
                + "`objetivo_curso`)\n"
                + "VALUES\n"
                + "(?,?,?,?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Curso());
            ps.setInt(2, mod.getIdTipo_Curso());
            ps.setInt(3, mod.getSemanas());
            ps.setString(4, mod.getObjetivo_curso());
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

    public boolean agregarCertifcado(Curso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        String sql = "INSERT INTO `sistema_capacitacion`.`curso`\n"
                + "(`nombre_curso`,\n"
                + "`id_tipocurso`,\n"
                + "`duracion_curso`,\n"
                + "`objetivo_curso`,"
                + "`id_certificado`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Curso());
            ps.setInt(2, mod.getIdTipo_Curso());
            ps.setInt(3, mod.getSemanas());
            ps.setString(4, mod.getObjetivo_curso());
            ps.setInt(5, mod.getIdCertificado());
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

    public boolean eliminar(Curso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`curso` WHERE nombre_curso=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Curso());
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

    public boolean actualizar(Curso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`curso`\n"
                + "SET\n"
                + "`nombre_curso` = ?,\n"
                + "`id_tipocurso` = ?, \n"
                + "`duracion_curso`=?, \n"
                + "`objetivo_curso` = ?\n"
                + "WHERE `idcurso` =?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Curso());
            ps.setInt(2, mod.getIdTipo_Curso());
            ps.setInt(3, mod.getSemanas());
            ps.setString(4, mod.getObjetivo_curso());
            ps.setInt(5, mod.getIdCurso());
            System.out.println(ps);
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

    public boolean actualizarCertificado(Curso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`curso`\n"
                + "SET\n"
                + "`nombre_curso` = ?,\n"
                + "`id_tipocurso` = ?,\n"
                + "`duracion_curso`=?, \n"
                + "`objetivo_curso` = ?,\n"
                + "`id_certificado` = ?\n"
                + "WHERE `idcurso` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Curso());
            ps.setInt(2, mod.getIdTipo_Curso());
            ps.setInt(3, mod.getSemanas());
            ps.setString(4, mod.getObjetivo_curso());
            ps.setInt(5, mod.getIdCertificado());
            ps.setInt(6, mod.getIdCurso());
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
