package Querys;

import Model.PersonalCurso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasPersonalCurso extends Conexion {

    public boolean buscar(PersonalCurso mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`asistentes_curso`"
                + " WHERE idHistorial_Curso = ? AND idAsistentes_Curso = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHistorial_Curso());
            ps.setInt(2, mod.getIdTrabajador());
            rs = ps.executeQuery();

            if (rs.next()) {
                mod.setNombre_Trabajador(rs.getString("nombre_asistente"));
                mod.setTipo_curso(rs.getString("tipo_entrenamiento"));
                mod.setEstado_curso(rs.getString("status_entrenamiento"));
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

    public boolean actualizar(PersonalCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`asistentes_curso`\n"
                + "SET\n"
                + "`tipo_entrenamiento` = ?,\n"
                + "`status_entrenamiento` = ?\n"
                + "WHERE `idHistorial_Curso` = ? AND `idAsistentes_Curso` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getTipo_curso());
            ps.setString(2, mod.getEstado_curso());
            ps.setInt(3, mod.getIdHistorial_Curso());
            ps.setInt(4, mod.getIdTrabajador());

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
