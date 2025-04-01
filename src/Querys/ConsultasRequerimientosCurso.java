package Querys;

import Model.RequerimientosCurso;
import Model.RequerimientosCursoAsistente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasRequerimientosCurso extends Conexion {

    public boolean buscar(RequerimientosCurso mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT "
                + "    `requerimientos`.`curso_idcurso`,\n"
                + "    `requerimientos`.`nombre_Requerimiento`,\n"
                + "    `requerimientos`.`descp_documento`,\n"
                + "    `requerimientos`.`nombre_documento`,\n"
                + "    `requerimientos`.`ruta_documento`\n"
                + "FROM `sistema_capacitacion`.`requerimientos` "
                + "WHERE `idRequerimientos` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdRequerimiento());
            rs = ps.executeQuery();
            if (rs.next()) {
                mod.setIdCurso(rs.getInt("curso_idCurso"));
                mod.setNombre_requerimiento(rs.getString("nombre_Requerimiento"));
                mod.setDescp_requerimiento(rs.getString("descp_documento"));
                mod.setNombre_archivo(rs.getString("nombre_documento"));
                mod.setRuta_Docuemento(rs.getString("ruta_documento"));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error en Query buscar: " + e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

//    public boolean agregar(RequerimientosCurso mod) {}
    public boolean actualizar(RequerimientosCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`requerimientos`\n"
                + "SET\n"
                + "`curso_idcurso` = ?,\n"
                + "`nombre_Requerimiento` = ?,\n"
                + "`descp_documento` = ?,\n"
                + "`nombre_documento` = ?,\n"
                + "`ruta_documento` = ?\n"
                + "WHERE `idRequerimientos` = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCurso());
            ps.setString(2, mod.getNombre_requerimiento());
            ps.setString(3, mod.getDescp_requerimiento());
            ps.setString(4, mod.getNombre_archivo());
            ps.setString(5, mod.getRuta_Docuemento());
            ps.setInt(6, mod.getIdRequerimiento());
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

    public boolean agregar(RequerimientosCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`requerimientos`\n"
                + "(`curso_idcurso`,\n"
                + "`nombre_Requerimiento`,\n"
                + "`descp_documento`,\n"
                + "`nombre_documento`,\n"
                + "`ruta_documento`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCurso());
            ps.setString(2, mod.getNombre_requerimiento());
            ps.setString(3, mod.getDescp_requerimiento());
            ps.setString(4, mod.getNombre_archivo());
            ps.setString(5, mod.getRuta_Docuemento());
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

    public boolean eliminar(RequerimientosCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`requerimientos`\n"
                + "WHERE idRequerimientos=?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdRequerimiento());
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

    public List<RequerimientosCurso> consultar(String idCurso) {
        List<RequerimientosCurso> lbu = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT"
                + "`requerimientos`.`idRequerimientos`, "
                + "`requerimientos`.`nombre_Requerimiento` "
                + "FROM `sistema_capacitacion`.`requerimientos` "
                + "WHERE `curso_idCurso` = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idCurso));
            rs = ps.executeQuery();

            while (rs.next()) {
                RequerimientosCurso tbr = new RequerimientosCurso();
                tbr.setIdRequerimiento(rs.getInt("idRequerimientos"));
                tbr.setNombre_requerimiento(rs.getString("nombre_Requerimiento"));
                lbu.add(tbr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return lbu;
    }

    public boolean estadoRequerimiento(int idRequerimiento, RequerimientosCursoAsistente mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`requerimientos_cumplidos`"
                + "WHERE `idAsistentes_curso` = ? "
                + "AND `idHistorial_curso` = ? AND `idRequerimientos` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdAsistente());
            ps.setInt(2, mod.getIdHistorial());
            ps.setInt(3, idRequerimiento);
            rs = ps.executeQuery();
            if (rs.next()) {
                mod.setRuta_archivo(rs.getString("ruta_archivo"));
                mod.setFecha_entrega(rs.getString("fecha_entrega"));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error en Query buscar: " + e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean agregarRequerimiento(RequerimientosCursoAsistente mod, String fecha) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`requerimientos_cumplidos`\n"
                + "(`idAsistentes_Curso`,\n"
                + "`idHistorial_curso`,\n"
                + "`idRequerimientos`,\n"
                + "`fecha_entrega`,"
                + "`nombre_Archivo`,"
                + "`ruta_Archivo`)\n"
                + "VALUES (?,?,?,?,?,?)"
                + "ON DUPLICATE KEY UPDATE fecha_entrega = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdAsistente());
            ps.setInt(2, mod.getIdHistorial());
            ps.setInt(3, mod.getIdRequerimiento());
            ps.setString(4, fecha);
            ps.setString(5, mod.getNombre_archivo());
            ps.setString(6, mod.getRuta_archivo());
            ps.setString(7, fecha);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean actualizarDocumento(RequerimientosCursoAsistente mod, String fecha) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`requerimientos_cumplidos`\n"
                + "(`idAsistentes_Curso`,\n"
                + "`idHistorial_curso`,\n"
                + "`idRequerimientos`,\n"
                + "`fecha_entrega`,"
                + "`nombre_Archivo`,"
                + "`ruta_Archivo`)\n"
                + "VALUES (?,?,?,?,?,?)"
                + "ON DUPLICATE KEY UPDATE fecha_entrega = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdAsistente());
            ps.setInt(2, mod.getIdHistorial());
            ps.setInt(3, mod.getIdRequerimiento());
            ps.setString(4, fecha);
            ps.setString(5, mod.getNombre_archivo());
            ps.setString(6, mod.getRuta_archivo());
            ps.setString(7, fecha);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean eliminarRequerimiento(RequerimientosCursoAsistente mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`requerimientos_cumplidos`\n"
                + "WHERE `idAsistentes_curso`=? "
                + "AND `idHistorial_curso`=? AND `idrequerimientos`=?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdAsistente());
            ps.setInt(2, mod.getIdHistorial());
            ps.setInt(3, mod.getIdRequerimiento());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
