//Ventana Principal para la vista del Administrador
//Se solicitan varios usuarios: Administrador, Capturista y Tecnico(Programador)
package General;

import Controller.CtrlLogin;
import Querys.ConsultasLogin;
import Model.Usuario;
import View.FrmLogin;

public class InicioLogin {
    public static void main(String[] args) {
        //Llamamos la vista del Login
        Usuario modelo_Usuario = new Usuario();
        ConsultasLogin controlador_Usuario = new ConsultasLogin();
        FrmLogin frame_Usuario = new FrmLogin();

        //Llamaos el controlador del Login
        //Mostramos ventana
        CtrlLogin ctrl_Login = new CtrlLogin(modelo_Usuario, controlador_Usuario, frame_Usuario) {};
        ctrl_Login.iniciar();
        frame_Usuario.setVisible(true);
    }
}
