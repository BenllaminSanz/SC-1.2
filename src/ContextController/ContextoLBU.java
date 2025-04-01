//Contexto de las clases que se instancian para el controlado la ventana LBU Operativo
package ContextController;

import View.FrmAdministrador;
import View.IFrmLBU;

public class ContextoLBU {

    public FrmAdministrador ventanaAdministrador;
    public IFrmLBU ventanaLBU;

    public ContextoLBU(FrmAdministrador frm) {
        this.ventanaLBU = new IFrmLBU();
        this.ventanaAdministrador = frm;
    }
}
