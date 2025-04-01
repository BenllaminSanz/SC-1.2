//Contexto de las clases que se instancian para el controlado la ventana Editar Bajas
package ContextController;

import Subviews.IFrmEditarBaja;
import View.IFrmTrabajador;

public class ContextoEditarBaja {
    public IFrmEditarBaja ventanaEditarBaja;
    public IFrmTrabajador ventanaTrabajador;
    public String folio;
    
    public ContextoEditarBaja(String folio, IFrmTrabajador frmA){
        this.ventanaEditarBaja = new IFrmEditarBaja();
        this.ventanaTrabajador = frmA;
        this.folio = folio;
    }
}
