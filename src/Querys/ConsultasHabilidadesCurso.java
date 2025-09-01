package Querys;

import Model.HabilidadEvaluada;
import Model.HabilidadesCurso;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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

    public List<HabilidadEvaluada> obtenerHabilidadesConEvaluacion(int idCurso, int idAsistente, int idHistorialCurso) {
        List<HabilidadEvaluada> lista = new ArrayList<>();

        String sql = "SELECT h.id_habilidad, h.nombre, h.orden, "
                + "e.nivel_alcanzado, e.fecha_evaluacion, e.observaciones "
                + "FROM curso_habilidad ch "
                + "JOIN habilidad h ON ch.id_habilidad = h.id_habilidad "
                + "LEFT JOIN evaluacion_habilidad_asistente e ON e.id_habilidad = h.id_habilidad "
                + "AND e.id_asistente = ? AND e.id_historialCurso = ? "
                + "WHERE ch.id_curso = ? ORDER BY fecha_evaluacion";

        try (Connection con = getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idAsistente);
            pst.setInt(2, idHistorialCurso);
            pst.setInt(3, idCurso);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    HabilidadEvaluada h = new HabilidadEvaluada();
                    h.setIdHabilidad(rs.getInt("id_habilidad"));
                    h.setNombreHabilidad(rs.getString("nombre"));
                    h.setOrdenHabilidad(rs.getInt("orden"));
                    h.setNivelAlcanzado(rs.getString("nivel_alcanzado")); // puede ser null
                    h.setFechaEvaluacion(rs.getDate("fecha_evaluacion"));
                    h.setObservaciones(rs.getString("observaciones"));
                    lista.add(h);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean guardarEvaluacion(int idAsistente, int idHabilidad, int idHistorialCurso,
            String nivelAlcanzado, Date fecha, String observacion) {
        String queryInsert = "INSERT INTO evaluacion_habilidad_asistente "
                + "(id_asistente, id_habilidad, nivel_alcanzado, fecha_evaluacion, id_historialCurso, observaciones) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConnection(); PreparedStatement psInsert = con.prepareStatement(queryInsert)) {

            psInsert.setInt(1, idAsistente);
            psInsert.setInt(2, idHabilidad);
            psInsert.setString(3, nivelAlcanzado);
            psInsert.setDate(4, fecha);
            psInsert.setInt(5, idHistorialCurso);
            psInsert.setString(6, observacion);

            return psInsert.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public List<HabilidadEvaluada> obtenerHistorialEvaluaciones(int idAsistente, int idHistorialCurso, int idHabilidad) {
        List<HabilidadEvaluada> historial = new ArrayList<>();

        String sql = "SELECT FLOOR(DATEDIFF(e.fecha_evaluacion, h.fecha_inicio) / 7) + 1 AS Semana,\n" 
                + "        e.fecha_evaluacion AS fecha,\n" 
                + "        e.nivel_alcanzado AS nivel,\n" 
                + "        e.observaciones AS observaciones\n" 
                + "    FROM\n" + "        evaluacion_habilidad_asistente e\n" 
                + "    JOIN\n" + "        historial_curso h ON e.id_historialCurso = h.idHistorial_Curso\n" 
                + "    WHERE\n" + "        e.id_asistente = ? AND e.id_historialCurso = ? AND e.id_habilidad = ?\n" 
                + "    ORDER BY\n" + "        e.fecha_evaluacion ASC\n";
        
        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAsistente);
            ps.setInt(2, idHistorialCurso);
            ps.setInt(3, idHabilidad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int semana = rs.getInt("Semana");
                    Date fecha = rs.getDate("fecha");
                    String nivel = rs.getString("nivel");
                    String obs = rs.getString("observaciones");

                    historial.add(new HabilidadEvaluada(semana, fecha, nivel, obs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener historial de evaluaciones:\n" + e.getMessage());
        }

        return historial;
    }
}
