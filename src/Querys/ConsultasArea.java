package Querys;

import Model.Area;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasArea extends Conexion {

    //Funcion de Registro para Area
    public boolean registrar(Area tbr) {

        String sql = "INSERT INTO `area`\n"
                + "(`Nombre_Area`,`Descripcion`,`tipo_proceso`)\n"
                + "VALUES (?,?,?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tbr.getNombre_Area());
            ps.setString(2, tbr.getDescripcion());
            ps.setInt(3, tbr.getTipo_proceso());
            ps.execute();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(ConsultasArea.class.getName()).log(Level.SEVERE, "Error en Query registro: ", e);
        }
        return false;
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificar(Area tbr, int folio) {

        String sql = "UPDATE `area`\n"
                + "SET\n"
                + "`Nombre_area` = ?,\n"
                + "`Descripcion` = ?,\n"
                + "`Tipo_Proceso` = ?\n"
                + "WHERE `idArea` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tbr.getNombre_Area());
            ps.setString(2, tbr.getDescripcion());
            ps.setInt(3, tbr.getTipo_proceso());
            ps.setInt(4, folio);
            ps.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ConsultasArea.class.getName()).log(Level.SEVERE, "Error en Query modificar: ", e);
        }
        return false;
    }

    //Funcion de Eliminar para Trabajadores
    public boolean eliminar(Area tbr) {

        String sql = "DELETE FROM `area`\n"
                + "WHERE `idArea` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tbr.getIdArea());
            ps.execute();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(ConsultasArea.class.getName()).log(Level.SEVERE, "Error en Query eliminar: ", e);
        }
        return false;
    }

    //Funcion de Registro para Trabajadores
    public boolean buscar(Area tbr) {

        String sql = "SELECT * FROM area WHERE idArea = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tbr.getIdArea());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    tbr.setNombre_Area(rs.getString("Nombre_Area"));
                    tbr.setDescripcion(rs.getString("Descripcion"));
                    tbr.setTipo_proceso(rs.getInt("Tipo_Proceso"));
                    return true;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ConsultasArea.class.getName()).log(Level.SEVERE, "Error en Query buscar: ", e);
        }
        return false;
    }
}
