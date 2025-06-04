package Functions;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class optenerNivel {

    private optenerNivel() {

    }

    public static String recibirNivel(ResultSet rs) {
        FileInputStream fis = null;
        try {
            Properties properties = new Properties();
            fis = new FileInputStream("files/niveles.properties");
            properties.load(fis);
            
            // Obtener el salario del trabajador
            double salario = rs.getDouble("SalarioDiario_Trabajador");
            // Determinar el nivel del trabajador
            String nivel = Niveles_Salarios.determinarNivel(salario, properties);
            // Asignar el nivel al objeto tbr
            return nivel;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(optenerNivel.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(optenerNivel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
