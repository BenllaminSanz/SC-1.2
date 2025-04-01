package Querys;

import Functions.DateTools;
import Model.Administrativos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasAdministrativo extends Conexion {

    public boolean buscar(Administrativos tbr) {
        String sql = "SELECT * FROM administrativos WHERE Folio_Administrativo = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tbr.getFolio_Trabajador());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tbr.setNombre_Trabajador(rs.getString("Nombre_Administrativo"));
                    tbr.setCURP_Trabajador(rs.getString("Curp_Administrativo"));
                    tbr.setRFC_Trabajador(rs.getString("RFC_Administrativo"));
                    tbr.setIMSS_Trabajador(rs.getString("IMSS_Administrativo"));
                    tbr.setFecha_Antiguedad(DateTools.MySQLtoString(rs.getDate("Fecha_Antiguedad")));
                    tbr.setCia(rs.getString("cia_administrativo"));
                    tbr.setArea(rs.getString("area_administrativo"));
                    tbr.setPuesto(rs.getString("puesto_administrativo"));
                    tbr.setTurno(rs.getString("turno"));
                    tbr.setBrigadista(rs.getBoolean("Brigadista"));
                    if (tbr.isBrigadista()) {
                        tbr.setBrigada(rs.getInt("brigada_idBrigada"));
                    }
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAdministrativo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean registrar(Administrativos tbr) {
        String sql = "INSERT INTO `sistema_capacitacion`.`administrativos` "
                + "(`Folio_Administrativo`, `Nombre_Administrativo`, `CURP_administrativo`, `RFC_administrativo`, "
                + "`IMSS_administrativo`, `Fecha_antiguedad`, `cia_administrativo`, `area_administrativo`, "
                + "`puesto_administrativo`, `turno`, `brigadista`, `brigada_idBrigada`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tbr.getFolio_Trabajador());
            ps.setString(2, tbr.getNombre_Trabajador());
            ps.setString(3, tbr.getCURP_Trabajador());
            ps.setString(4, tbr.getRFC_Trabajador());
            ps.setString(5, tbr.getIMSS_Trabajador());
            ps.setString(6, DateTools.StringtoMySQL(tbr.getFecha_Antiguedad()));
            ps.setString(7, tbr.getCia());
            ps.setString(8, tbr.getArea());
            ps.setString(9, tbr.getPuesto());
            ps.setString(10, tbr.getTurno());
            ps.setBoolean(11, tbr.isBrigadista());

            if (tbr.isBrigadista()) {
                ps.setInt(12, tbr.getBrigada());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }

            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAdministrativo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean modificar(Administrativos tbr, String folio) {
        String sql = "UPDATE `sistema_capacitacion`.`administrativos` "
                + "SET `Folio_Administrativo` = ?, `Nombre_Administrativo` = ?, `CURP_administrativo` = ?, "
                + "`RFC_administrativo` = ?, `IMSS_administrativo` = ?, `Fecha_antiguedad` = ?, `cia_administrativo` = ?, "
                + "`area_administrativo` = ?, `puesto_administrativo` = ?, `turno` = ?, `brigadista` = ?, "
                + "`brigada_idBrigada` = ? WHERE `Folio_Administrativo` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tbr.getFolio_Trabajador());
            ps.setString(2, tbr.getNombre_Trabajador());
            ps.setString(3, tbr.getCURP_Trabajador());
            ps.setString(4, tbr.getRFC_Trabajador());
            ps.setString(5, tbr.getIMSS_Trabajador());
            ps.setString(6, DateTools.StringtoMySQL(tbr.getFecha_Antiguedad()));
            ps.setString(7, tbr.getCia());
            ps.setString(8, tbr.getArea());
            ps.setString(9, tbr.getPuesto());
            ps.setString(10, tbr.getTurno());
            ps.setBoolean(11, tbr.isBrigadista());

            if (tbr.isBrigadista()) {
                ps.setInt(12, tbr.getBrigada());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }

            ps.setString(13, folio);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAdministrativo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean eliminar(Administrativos tbr) {
        String sql = "DELETE FROM `sistema_capacitacion`.`administrativos` WHERE `Folio_Administrativo` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tbr.getFolio_Trabajador());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSupervisor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
