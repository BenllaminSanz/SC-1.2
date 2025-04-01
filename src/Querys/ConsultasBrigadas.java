package Querys;

import Model.Brigadas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConsultasBrigadas extends Conexion {
    //Funcion de Registro para Area

    public boolean registrar(Brigadas tbr) {

        String sql = "INSERT INTO `brigadas`\n"
                + "(`nombre_brigada`)\n"
                + "VALUES (?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tbr.getNombre_Brigada());
            ps.execute();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean buscar(Brigadas tbr) {

        String sql = "SELECT * FROM brigadas WHERE idbrigadas = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tbr.getIdBrigada());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    tbr.setIdBrigada(rs.getInt("idbrigadas"));
                    tbr.setNombre_Brigada(rs.getString("nombre_brigada"));
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBrigadas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificar(Brigadas tbr, int folio) {

        String sql = "UPDATE `sistema_capacitacion`.`brigadas`\n"
                + "SET\n"
                + "`idbrigadas` = ?,\n"
                + "`nombre_brigada` = ?\n"
                + "WHERE `idbrigadas` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tbr.getIdBrigada());
            ps.setString(2, tbr.getNombre_Brigada());
            ps.setInt(3, folio);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar, verifica tus datos");
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);

        }
        return false;
    }

    //Funcion de Eliminar para Trabajadores
    public boolean eliminar(Brigadas tbr) {

        String sql = "DELETE FROM `brigadas`\n"
                + "WHERE `idbrigadas` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tbr.getIdBrigada());
            ps.execute();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);

        }
        return false;
    }
}
