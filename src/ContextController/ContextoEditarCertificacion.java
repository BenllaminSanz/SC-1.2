package ContextController;

import Subviews.IFrmEditarCertificacion;
import View.FrmAdministrador;
import View.IFrmCapacitacion;

public class ContextoEditarCertificacion {
    
    public IFrmEditarCertificacion ventanaEditarCertificacion;
    public IFrmCapacitacion ventanaCapacitaciones;
    public FrmAdministrador ventanaAdmnistrador;
    public String texto;
    public String folio;

    public ContextoEditarCertificacion(String txtBoton, String folio, IFrmCapacitacion frm, FrmAdministrador frmA) {
        this.ventanaEditarCertificacion = new IFrmEditarCertificacion();
        this.ventanaCapacitaciones = frm;
        this.ventanaAdmnistrador = frmA;
        this.texto = txtBoton;
        this.folio = folio;
    }
}
