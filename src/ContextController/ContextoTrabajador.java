//Contexto de las clases que se instancian para el controlado la ventana Trabajador
package ContextController;

import View.FrmAdministrador;
import View.IFrmTrabajador;

public class ContextoTrabajador {
    
    public FrmAdministrador ventanaAdministrador;
    public IFrmTrabajador ventanaTrabajador;

    public ContextoTrabajador(FrmAdministrador frmAdministrador) {
        this.ventanaTrabajador = new IFrmTrabajador();
        this.ventanaAdministrador =  frmAdministrador;
    }
}
