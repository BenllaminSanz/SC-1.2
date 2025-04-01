//Ventana Principal para la vista del Administrador
package General;

import Controller.CtrlAdministrador;
import View.FrmAdministrador;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class InicioAdministrador {

    public static void main(String[] args) {
        //Llamamos la vista del Administrador
        FrmAdministrador frm_Administrador = new FrmAdministrador();
        //Llamamos el controlador del Login
        //Mostramos ventana
        // Establecer el icono predeterminado para todos los JInternalFrame
        UIManager.put("InternalFrame.icon", new ImageIcon(ClassLoader.getSystemResource("Images/IconParkdale.png")));
        CtrlAdministrador ctrl_Administrador = new CtrlAdministrador(frm_Administrador);
        ctrl_Administrador.iniciar();
        frm_Administrador.setVisible(true);
    }
}
