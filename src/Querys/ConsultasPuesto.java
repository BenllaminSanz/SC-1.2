package Querys;

import Model.Puesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasPuesto extends Conexion {

    //Funcion de Registro para Area
    public boolean registrar(Puesto tbr) {

        String sql = "INSERT INTO `sistema_capacitacion`.`puesto`\n"
                + "(`Nombre_Puesto`,\n"
                + "`Nombre_Puesto_Ingles`,\n"
                + "`Descripcion`,\n"
                + "`Nivel`,\n"
                + "`Centro_de_Costo`,\n"
                + "`Propuesto_Trabajadores`,\n"
                + "`Propuesto_Turno`,\n"
                + "`area_idArea`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?,?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, tbr.getNombre_Puesto());
            ps.setString(2, tbr.getNombre_Puesto_Ingles());
            ps.setString(3, tbr.getDescripcion());
            ps.setString(4, tbr.getNivel());
            ps.setInt(5, tbr.getCentro_de_Costo());
            ps.setInt(6, tbr.getPropuesto_Trabajadores());
            ps.setInt(7, tbr.getPropuesto_Turno());
            ps.setInt(8, tbr.getArea_idArea());
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificar(Puesto tbr, int folio) {

        String sql = "UPDATE `sistema_capacitacion`.`puesto`\n"
                + "SET\n"
                + "`Nombre_Puesto` = ?,\n"
                + "`Nombre_Puesto_Ingles` = ?,\n"
                + "`Descripcion` = ?,\n"
                + "`Nivel` = ?,\n"
                + "`Centro_de_Costo` = ?,\n"
                + "`Propuesto_Trabajadores` = ?,\n"
                + "`Propuesto_Turno` = ?,\n"
                + "`area_idArea` = ?\n"
                + "WHERE `idPuesto` = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, tbr.getNombre_Puesto());
            ps.setString(2, tbr.getNombre_Puesto_Ingles());
            ps.setString(3, tbr.getDescripcion());
            ps.setString(4, tbr.getNivel());
            ps.setInt(5, tbr.getCentro_de_Costo());
            ps.setInt(6, tbr.getPropuesto_Trabajadores());
            ps.setInt(7, tbr.getPropuesto_Turno());
            ps.setInt(8, tbr.getArea_idArea());
            ps.setInt(9, folio);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Funcion de Eliminar para Trabajadores
    public boolean eliminar(Puesto tbr) {
        String sql = "DELETE FROM `puesto`\n"
                + "WHERE `idPuesto` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, tbr.getIdPuesto());
            ps.execute();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, e);

        }
        return false;
    }

    //Funcion de Registro para Trabajadores
    public boolean buscar(Puesto tbr) {

        String sql = "SELECT * FROM puesto WHERE idPuesto = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, tbr.getIdPuesto());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                    tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                    tbr.setDescripcion(rs.getString("Descripcion"));
                    tbr.setNivel(rs.getString("Nivel"));
                    tbr.setCentro_de_Costo(rs.getInt("Centro_de_Costo"));
                    tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                    tbr.setPropuesto_Turno(rs.getInt("Propuesto_Turno"));
                    tbr.setArea_idArea(rs.getInt("area_idArea"));
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
