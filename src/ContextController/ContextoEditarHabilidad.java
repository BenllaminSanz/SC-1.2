package ContextController;

import Subviews.IFrmEditarCurso;
import Subviews.IFrmEditarHabilidad;
import View.FrmAdministrador;

public class ContextoEditarHabilidad {

    public IFrmEditarHabilidad ventanaEditarHabilidad;
    public IFrmEditarCurso ventanaEditarCurso;
    public FrmAdministrador ventanaAdministrador;
    public String curso;
    public String boton;
    public String folio;

    public ContextoEditarHabilidad(String curso, String txtBoton, String folio, IFrmEditarCurso frmA, FrmAdministrador frmB) {
        this.ventanaEditarHabilidad = new IFrmEditarHabilidad();
        this.ventanaEditarCurso = frmA;
        this.ventanaAdministrador = frmB;
        this.curso = curso;
        this.boton = txtBoton;
        this.folio = folio;
    }
}
