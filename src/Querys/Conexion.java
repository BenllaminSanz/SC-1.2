//Clase Conexion con la base de Datos en MySQL
package Querys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {

    //Variables para iniciar la conexion 
    //Variables locales
    Connection conn = null;
    static String bd = "sistema_capacitacion";
    static String host = "10.102.128.80"; /* "10.102.128.80" */

    static int port = 3306;
    String url = "jdbc:mysql://" + host + ":" + port + "/" + bd + "?zeroDateTimeBehavior=CONVERT_TO_NULL";
    
    //DATOS DE USUARIO
    static String user = "sc.admin.sup";
    static String password = "root";
    
    @SuppressWarnings("deprecation")
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar con la base de Datos \n Consulte con el programador",
                    "Conexion Fallida", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
