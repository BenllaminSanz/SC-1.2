package Querys;

import Model.Brigadistas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConsultasBrigadistas extends Conexion {

    public boolean eliminar(Brigadistas tbr) {

        //Convertir en vista
        String sql = "SELECT * FROM ("
                + "SELECT `t`.`Folio_Trabajador` AS `Nomina`, 'Operativo' AS `Trabajador` FROM `view_trabajador` `t` "
                + "UNION ALL "
                + "SELECT `a`.`Folio_Administrativo` AS `Nomina`, 'Administrativo' AS `Trabajador` FROM `administrativos` `a`"
                + ") AS subconsulta WHERE Nomina = ?";

        String sqlA = "UPDATE `administrativos` SET `brigadista` = NULL, `brigada_idBrigada` = NULL "
                + "WHERE `Folio_Administrativo` = ?";
        String sqlT = "UPDATE `trabajador` SET `brigadista` = NULL, `brigada_idBrigada` = NULL "
                + "WHERE `Folio_Trabajador` = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, tbr.getIdBrigadista());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipoTrabajador = rs.getString("Trabajador");
                    String sqlUpdate = tipoTrabajador.equals("Operativo") ? sqlT : sqlA;

                    try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                        psUpdate.setInt(1, rs.getInt("Nomina"));
                        psUpdate.execute();
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al Brigadista");
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

}
