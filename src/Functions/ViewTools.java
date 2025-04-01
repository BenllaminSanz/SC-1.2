//Clase con el metodo para centrar un IFrame dentro del DesktopPane
package Functions;

import View.FrmAdministrador;
import javax.swing.JInternalFrame;

public class ViewTools {

    //Metodo para centrar una ventana interna dentro del Desktop
    public static void Centrar(FrmAdministrador ctrl, JInternalFrame frame) {
        //Variables que calculan el centro del Desktop
        int x = (ctrl.Desktop_Administrador.getWidth() / 2) - frame.getWidth() / 2;
        int y = (ctrl.Desktop_Administrador.getHeight() / 2) - frame.getHeight() / 2;

        //Condicion de muestra de la ventana
        if (frame.isShowing()) {
            frame.setLocation(x, y);
        } else {
            ctrl.Desktop_Administrador.add(frame);
            frame.setLocation(x, y);
            frame.show();
        }
    }
}
