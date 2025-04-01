//Contexto de las clases que se instancian para el controlado la ventana Areas y Puestos
package ContextController;

import View.FrmAdministrador;
import View.IFrmAreasPuestos;

public class ContextoAreasPuestos {

    public FrmAdministrador ventanaAdministrador;
    public IFrmAreasPuestos ventanaAreasPuestos;

    public ContextoAreasPuestos(FrmAdministrador frmAdministrador) {
        this.ventanaAreasPuestos = new IFrmAreasPuestos();
        this.ventanaAdministrador = frmAdministrador;
    }
}
