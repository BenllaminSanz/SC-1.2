package Querys;

import Model.AsistenteCurso;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasAsistentesCurso extends Conexion {

    String sqlIngreso = "INSERT INTO `sistema_capacitacion`.`asistentes_curso` "
            + "(`idHistorial_Curso`, `idAsistentes_curso`, `nombre_asistente`, `asistente_type`) "
            + "VALUES (?, ?, ?, ?);";

    public boolean registrarTrabajador(String idCurso, String idHistorial, List<AsistenteCurso> tbr) {

        String sql1 = "SET @id_historial_curso:=?";
        String sql2 = "SET @id_asistente:=?";
        String sql3 = "SET @id_curso:=?";
        String call = "{CALL `sistema_capacitacion`.`actualizar_entrenamiento`()}";

        try (Connection con = getConnection(); 
                PreparedStatement psInsert = con.prepareStatement(sqlIngreso); 
                PreparedStatement psHistorial = con.prepareStatement(sql1); 
                PreparedStatement psAsistente = con.prepareStatement(sql2); 
                PreparedStatement psCurso = con.prepareStatement(sql3); 
                CallableStatement cs = con.prepareCall(call)) {

            for (AsistenteCurso value : tbr) {
                // Ejecutar la inserción
                psInsert.setInt(1, Integer.parseInt(idHistorial));
                psInsert.setString(2, value.getId());
                psInsert.setString(3, value.getNombre());
                psInsert.setString(4, value.getPuesto());
                psInsert.execute();

                // Establecer variables
                psHistorial.setInt(1, Integer.parseInt(idHistorial));
                psHistorial.execute();

                psAsistente.setString(1, value.getId());
                psAsistente.execute();

                psCurso.setInt(1, Integer.parseInt(idCurso));
                psCurso.execute();

                // Llamar al procedimiento almacenado
                cs.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean registrarSupervisor(String idHistorial, List<AsistenteCurso> tbr) {

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sqlIngreso)) {
            for (AsistenteCurso value : tbr) {
                ps.setInt(1, Integer.parseInt(idHistorial));
                ps.setString(2, value.getId());
                ps.setString(3, value.getNombre());
                ps.setString(4, value.getPuesto());
                ps.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean registrarAdministrativo(String idCurso, String idHistorial, List<AsistenteCurso> rows) {

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sqlIngreso)) {
            for (AsistenteCurso value : rows) {
                ps.setInt(1, Integer.parseInt(idHistorial));
                ps.setString(2, value.getId());
                ps.setString(3, value.getNombre());
                ps.setString(4, value.getPuesto());
                ps.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public boolean registrarOtrosNomina(String idHistorial, AsistenteCurso mod) {

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sqlIngreso)) {
            ps.setInt(1, Integer.parseInt(idHistorial));
            ps.setInt(2, Integer.parseInt(mod.getId()));
            ps.setString(3, mod.getNombre());
            ps.setString(4, mod.getPuesto());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public boolean registrarOtros(String idHistorial, AsistenteCurso mod) {

        String sql = "INSERT INTO `sistema_capacitacion`.`asistentes_curso`\n"
                + "(`idHistorial_Curso`,\n"
                + "`nombre_asistente`,\n"
                + "`asistente_type`)\n"
                + "VALUES\n"
                + "(?,?,?);";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(idHistorial));
            ps.setString(2, mod.getNombre());
            ps.setString(3, mod.getPuesto());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public boolean eliminar(String idHistorial, String idAsistente) {
        String sql = "DELETE FROM `sistema_capacitacion`.`asistentes_curso`\n"
                + "WHERE idHistorial_Curso=? AND idAsistentes_curso=?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(idHistorial));
            ps.setString(2, idAsistente);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public boolean registrarBrigadista(String idCurso, String idHistorial, List<AsistenteCurso> rows) {

        String sql = "INSERT INTO `sistema_capacitacion`.`asistentes_curso`\n"
                + "(`idHistorial_Curso`,\n"
                + "`idAsistentes_curso`,\n"
                + "`nombre_asistente`,\n"
                + "`asistente_type`)\n"
                + "VALUES\n"
                + "(?,?,?,?);";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (AsistenteCurso value : rows) {
                ps.setInt(1, Integer.parseInt(idHistorial));
                ps.setString(2, value.getId());
                ps.setString(3, value.getNombre());
                ps.setString(4, value.getPuesto());
                ps.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }
}
