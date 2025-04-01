//Clase con los metodos de consulta que se hacen a la tabla Usuario
package Querys;

import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasLogin extends Conexion {

    //Consulta de los datos en la tabla Usuario de la base de datos
    public boolean iniciar(Usuario usr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "select tipo_nivel, status from usuario where username= ? and password = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsername());
            System.out.println(usr.getUsername());
            ps.setString(2, usr.getPassword());
            System.out.println(usr.getPassword());
            rs = ps.executeQuery();

            if (rs.next()) {
                usr.setTipo_nivel(rs.getString("tipo_nivel"));
                usr.setStatus(rs.getString("status"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
