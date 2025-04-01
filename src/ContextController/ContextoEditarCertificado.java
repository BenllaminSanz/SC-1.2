package ContextController;

import Subviews.IFrmEditarCertificado;
import View.FrmAdministrador;
import View.IFrmCapacitacion;

public class ContextoEditarCertificado {
    
    public IFrmEditarCertificado ventanaEditarCertificado;
    public IFrmCapacitacion ventanaCapacitaciones;
    public FrmAdministrador ventanaAdministrador;
    public String textoBoton;
    public String idCertificado;
    
    public ContextoEditarCertificado(String txtBoton, String idCertificado, IFrmCapacitacion frm, FrmAdministrador frmA) {
        this.ventanaEditarCertificado = new IFrmEditarCertificado();
        this.ventanaCapacitaciones = frm;
        this.ventanaAdministrador = frmA;
        this.textoBoton = txtBoton;
        this.idCertificado = idCertificado;
    }
}
