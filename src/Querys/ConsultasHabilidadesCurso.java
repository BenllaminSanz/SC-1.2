package Querys;

import Model.HabilidadesCurso;
import Tables.TableHabilidadesCurso;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                mod.setIdHabilidad(rs.getInt("id_Habilidad"));
                mod.setNombre_habilidad(rs.getString("nombre"));
                mod.setOrden_habilidad(rs.getInt("orden"));
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
                + "`nombre` = ?,\n"
                + "`orden` = ?\n"
                + "WHERE `id_habilidad` = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_habilidad());
            ps.setInt(2, mod.getOrden_habilidad());
            ps.setInt(3, mod.getIdHabilidad());
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

    public boolean eliminar(HabilidadesCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`habilidad`\n"
                + "WHERE id_habilidad = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHabilidad());
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

    public List<HabilidadesCurso> obtenerHabilidadesPorCurso(int idCurso) {
        Connection con = getConnection();
        List<HabilidadesCurso> lista = new ArrayList<>();
        String sql = "SELECT h.id_habilidad, h.nombre, h.orden FROM curso_habilidad ch "
                + "JOIN habilidad h ON ch.id_habilidad = h.id_habilidad "
                + "WHERE ch.id_curso = ? ORDER BY h.orden";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCurso);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                HabilidadesCurso h = new HabilidadesCurso();
                h.setIdHabilidad(rs.getInt("id_habilidad"));
                h.setNombre_habilidad(rs.getString("nombre"));
                h.setOrden_habilidad(rs.getInt("orden"));
                lista.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean guardarOActualizarEvaluacion(int idAsistente, int idHabilidad, int idHistorialCurso, String nivelAlcanzado, Date fecha) {
        String querySelect = "SELECT COUNT(*) FROM evaluacion_habilidad_asistente WHERE id_asistente = ? AND id_habilidad = ? AND id_historialCurso = ?";
        String queryInsert = "INSERT INTO evaluacion_habilidad_asistente (id_asistente, id_habilidad, nivel_alcanzado, fecha_evaluacion, id_historialCurso, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        String queryUpdate = "UPDATE evaluacion_habilidad_asistente SET nivel_alcanzado = ?, fecha_evaluacion = ? WHERE id_asistente = ? AND id_habilidad = ? AND id_historialCurso = ?";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConnection(); PreparedStatement psSelect = con.prepareStatement(querySelect)) {

            psSelect.setInt(1, idAsistente);
            psSelect.setInt(2, idHabilidad);
            psSelect.setInt(3, idHistorialCurso);

            ResultSet rs = psSelect.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Ya existe → actualizar
                try (PreparedStatement psUpdate = con.prepareStatement(queryUpdate)) {
                    psUpdate.setString(1, nivelAlcanzado);
                    psUpdate.setDate(2, fecha);
                    psUpdate.setInt(3, idAsistente);
                    psUpdate.setInt(4, idHabilidad);
                    psUpdate.setInt(5, idHistorialCurso);
                    return psUpdate.executeUpdate() > 0;
                }
            } else {
                // No existe → insertar
                try (PreparedStatement psInsert = con.prepareStatement(queryInsert)) {
                    psInsert.setInt(1, idAsistente);
                    psInsert.setInt(2, idHabilidad);
                    psInsert.setString(3, nivelAlcanzado);
                    psInsert.setDate(4, fecha);
                    psInsert.setInt(5, idHistorialCurso);
                    psInsert.setNull(6, Types.VARCHAR); // Observaciones nulas por ahora
                    return psInsert.executeUpdate() > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerUltimoILUO(int idAsistente, int idHistorialCurso, int idHabilidad) {
        String sql = "SELECT nivel_alcanzado FROM evaluacion_habilidad_asistente "
                + "WHERE id_asistente = ? AND id_historialCurso = ? AND id_habilidad = ? "
                + "ORDER BY fecha_evaluacion DESC LIMIT 1";

        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idAsistente);
            pst.setInt(2, idHistorialCurso);
            pst.setInt(3, idHabilidad);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("nivel_alcanzado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "I";
    }

    public Map<Integer, String> obtenerUltimosEstadosILUO(int idAsistente, int idHistorialCurso) {
        Map<Integer, String> estadosILUO = new HashMap<>();
        String sql = "SELECT id_habilidad, nivel_alcanzado "
                + "FROM evaluacion_habilidad_asistente e "
                + "WHERE id_asistente = ? AND id_historialCurso = ? "
                + "ORDER BY fecha_evaluacion ASC LIMIT 1;";

        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idAsistente);
            pst.setInt(2, idHistorialCurso);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idHabilidad = rs.getInt("id_habilidad");
                String nivel = rs.getString("nivel_alcanzado");
                estadosILUO.put(idHabilidad, nivel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estadosILUO;
    }
}
