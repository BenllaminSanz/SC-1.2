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
    static String host = "localhost"; /* "10.102.128.80" */

    static int port = 3306;
    String url = "jdbc:mysql://" + host + ":" + port + "/" + bd + "?zeroDateTimeBehavior=CONVERT_TO_NULL";
    
    //DATOS DE USUARIO
    static String user = "sc.admin.sup";
    static String password = "root";
    
    @SuppressWarnings("deprecation")
    public Connection getConnection() {
        Connection c = connectInternal();
        if (c == null && !java.awt.GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar con la base de Datos \n Consulte con el programador",
                    "Conexion Fallida", JOptionPane.ERROR_MESSAGE);
        }
        return c;
    }

    /** Conexión sin diálogos — para scripts de prueba y entornos headless. */
    public Connection getConnectionSilent() {
        return connectInternal();
    }

    @SuppressWarnings("deprecation")
    private Connection connectInternal() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String dbHost = System.getenv("DB_HOST");
            String dbUser = System.getenv("DB_USER");
            String dbPass = System.getenv("DB_PASSWORD");
            String dbName = System.getenv("DB_NAME");
            String dbPort = System.getenv("DB_PORT");
            String effectiveHost = dbHost != null && !dbHost.isEmpty() ? dbHost : host;
            int effectivePort = dbPort != null && !dbPort.isEmpty() ? Integer.parseInt(dbPort) : port;
            String effectiveDb = dbName != null && !dbName.isEmpty() ? dbName : bd;
            String effectiveUrl = "jdbc:mysql://" + effectiveHost + ":" + effectivePort + "/"
                    + effectiveDb + "?zeroDateTimeBehavior=CONVERT_TO_NULL";
            String effectiveUser = dbUser != null && !dbUser.isEmpty() ? dbUser : user;
            String effectivePass = dbPass != null ? dbPass : password;
            conn = DriverManager.getConnection(effectiveUrl, effectiveUser, effectivePass);
            return conn;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }
}
