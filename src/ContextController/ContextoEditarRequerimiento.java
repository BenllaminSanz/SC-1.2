package ContextController;

import Subviews.IFrmEditarCurso;
import Subviews.IFrmEditarDocumento;
import View.FrmAdministrador;

public class ContextoEditarRequerimiento {

    public IFrmEditarDocumento ventanaEditarRequerimiento;
    public IFrmEditarCurso ventanaEditarCurso;
    public FrmAdministrador ventanaAdministrador;
    public String curso;
    public String boton;
    public String folio;

    public ContextoEditarRequerimiento(String curso, String txtBoton, String folio,IFrmEditarCurso frmA , FrmAdministrador frmB) {
        this.ventanaEditarRequerimiento = new IFrmEditarDocumento();
        this.ventanaEditarCurso = frmA;
        this.ventanaAdministrador = frmB;
        this.curso = curso;
        this.boton = txtBoton;
        this.folio = folio;
    }

}
