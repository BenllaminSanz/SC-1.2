package ContextController;

import Subviews.IFrmEditarAdministrativo;
import View.IFrmTrabajador;

public class ContextoEditarAdministrativo {

    public IFrmEditarAdministrativo ventanaEditarAdministrativo;
    public IFrmTrabajador ventanaTrabajador;
    public String texto;
    public String folio;

    public ContextoEditarAdministrativo(String txtBoton, String folio, IFrmTrabajador frmA) {
        this.ventanaEditarAdministrativo = new IFrmEditarAdministrativo();
        this.ventanaTrabajador = frmA;
        this.texto = txtBoton;
        this.folio = folio;
    }
}
