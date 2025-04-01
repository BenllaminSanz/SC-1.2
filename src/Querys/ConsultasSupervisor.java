package Querys;

import Functions.QueryFunctions;
import Model.Supervisor;
import Model.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasSupervisor extends Conexion {

    public List<Supervisor> buscarSupervisor(Supervisor tbr) {
        List<Supervisor> supervisor = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        tbr.setId_supervisor(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                "supervisor", "idSupervisor", "Nombre_Supervisor", tbr.getNombre_supervisor())));

        String sql = "SELECT * FROM sistema_capacitacion.view_supervisor WHERE idSupervisor = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, tbr.getId_supervisor());
            rs = ps.executeQuery();

            while (rs.next()) {
                Supervisor sup = new Supervisor();
                sup.setId_supervisor(rs.getInt("idSupervisor"));
                sup.setNombre_supervisor(rs.getString("Nombre_Supervisor"));
                sup.setPropuesto_trabajadores(rs.getInt("Propuesto_Trabajadores"));
                sup.setArea_idarea(rs.getInt("idArea"));
                sup.setNombre_area(rs.getString("Nombre_area"));
                sup.setTurno_idturno(rs.getInt("idTurno"));
                sup.setNombre_turno(rs.getString("Nombre_turno"));
                supervisor.add(sup);
            }
            return supervisor;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean insertar(Supervisor tbr, List<Turno> turnos) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`supervisor`\n"
                + "(`idSupervisor`,"
                + "`Nombre_Supervisor`,\n"
                + "`area_idArea`)\n"
                + "VALUES\n"
                + "(?,?,?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, tbr.getId_supervisor());
            ps.setString(2, tbr.getNombre_supervisor());
            ps.setInt(3, tbr.getArea_idarea());
            System.out.println(ps);
            ps.execute();

            if (buscar(tbr)) {
                if (insertarTurnos(tbr, turnos)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public boolean modificar(Supervisor tbr, String folio, List<Turno> turnos) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`supervisor`\n"
                + "SET\n"
                + "`idSupervisor` = ?,\n"
                + "`Nombre_Supervisor` = ?,\n"
                + "`Propuesto_Trabajadores` = ?,\n"
                + "`area_idArea` = ?\n"
                + "WHERE `idSupervisor` = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, tbr.getId_supervisor());
            ps.setString(2, tbr.getNombre_supervisor());
            ps.setInt(3, tbr.getPropuesto_trabajadores());
            ps.setInt(4, tbr.getArea_idarea());
            ps.setInt(5, Integer.parseInt(folio));
            ps.execute();

            if (buscar(tbr)) {
                if (eliminarTurnos(tbr)) {
                    insertarTurnos(tbr, turnos);
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean eliminar(Supervisor tbr) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`supervisor`\n"
                + "WHERE `Nombre_Supervisor` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, tbr.getNombre_supervisor());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean buscar(Supervisor tbr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM supervisor WHERE Nombre_Supervisor = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, tbr.getNombre_supervisor());
            rs = ps.executeQuery();

            if (rs.next()) {
                tbr.setId_supervisor(rs.getInt("idSupervisor"));
                tbr.setNombre_supervisor(rs.getString("nombre_supervisor"));
                tbr.setPropuesto_trabajadores(rs.getInt("propuesto_trabajadores"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean insertarTurnos(Supervisor tbr, List<Turno> turnos) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`supervisor_turno`\n"
                + "(`turno_idTurno`,\n"
                + "`supervisor_idSupervisor`,\n"
                + "`supervisor_area_idArea`)\n"
                + "VALUES\n"
                + "(?,?,?);";

        List<Turno> turnosEncontrados = turnos;
        try {
            for (Turno turno : turnosEncontrados) {
                switch (turno.getNombre_Turno()) {
                    case "A":
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, turno.getIdTurno());
                        ps.setInt(2, tbr.getId_supervisor());
                        ps.setInt(3, tbr.getArea_idarea());
                        ps.execute();
                        break;
                    case "B":
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, turno.getIdTurno());
                        ps.setInt(2, tbr.getId_supervisor());
                        ps.setInt(3, tbr.getArea_idarea());
                        ps.execute();
                        break;
                    case "C":
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, turno.getIdTurno());
                        ps.setInt(2, tbr.getId_supervisor());
                        ps.setInt(3, tbr.getArea_idarea());
                        ps.execute();
                        break;
                    case "D":
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, turno.getIdTurno());
                        ps.setInt(2, tbr.getId_supervisor());
                        ps.setInt(3, tbr.getArea_idarea());
                        ps.execute();
                        break;
                    case "LV":
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, turno.getIdTurno());
                        ps.setInt(2, tbr.getId_supervisor());
                        ps.setInt(3, tbr.getArea_idarea());
                        ps.execute();
                        break;
                    case "LS":
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, turno.getIdTurno());
                        ps.setInt(2, tbr.getId_supervisor());
                        ps.setInt(3, tbr.getArea_idarea());
                        ps.execute();
                        break;
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean eliminarTurnos(Supervisor tbr) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`supervisor_turno`\n"
                + "WHERE supervisor_idSupervisor = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, tbr.getId_supervisor());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
