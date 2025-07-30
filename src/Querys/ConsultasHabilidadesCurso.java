package Querys;

import Model.HabilidadesCurso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasHabilidadesCurso extends Conexion {

    public boolean buscar(HabilidadesCurso mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM habilidad WHERE id_Habilidad = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHabilidad());
            rs = ps.executeQuery();

            if (rs.next()) {
                mod.setNombre_habilidad(rs.getString("nombre"));

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

    public boolean agregar(HabilidadesCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        int idHabilidad = -1;

        String sql = "INSERT INTO `sistema_capacitacion`.`habilidad`\n"
                + "(`nombre`,`orden`)\n"
                + "VALUES\n"
                + "(?,?);";

        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, mod.getNombre_habilidad());
            ps.setInt(2, mod.getOrden_habilidad());
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    idHabilidad = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la nueva habilidad.");
                }
            }

            String sqlRelacionCurso = "INSERT INTO curso_habilidad (id_curso, id_habilidad) VALUES (?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(sqlRelacionCurso);
            ps1.setInt(1, mod.getIdCurso());
            ps1.setInt(2, idHabilidad);
            ps1.executeUpdate();

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

    public boolean actualizar(HabilidadesCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`habilidad`\n"
                + "SET\n"
                + "`id_habilidad` = ?,\n"
                + "`nombre` = ?,\n"
                + "`orden` = ?\n"
                + "WHERE `id_habilidad` = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCurso());
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

}
