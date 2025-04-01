package ContextController;

import Subviews.IFrmEditarCurso;
import View.FrmAdministrador;
import View.IFrmCapacitacion;

public class ContextoEditarCurso {
    
    public IFrmEditarCurso ventanaEditarCurso;
    public IFrmCapacitacion ventanaCertficados;
    public FrmAdministrador ventanaAdministrador;
    public String texto;
    public String folio;

    public ContextoEditarCurso(String texto, String folioTrabajador, IFrmCapacitacion frmA, FrmAdministrador frmB) {
        this.ventanaEditarCurso = new IFrmEditarCurso();
        this.ventanaCertficados = frmA;
        this.ventanaAdministrador = frmB;
        this.texto = texto;
        this.folio = folioTrabajador;
    }
}
