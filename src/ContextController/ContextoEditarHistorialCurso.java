package ContextController;

import Subviews.IFrmEditarHistorialCurso;
import View.FrmAdministrador;
import View.IFrmCapacitacion;

public class ContextoEditarHistorialCurso {
    
    public IFrmEditarHistorialCurso ventanaEditarHistorialCurso;
    public IFrmCapacitacion ventanaCapacitaciones;
    public FrmAdministrador ventanaAdministrador;
    public String curso;
    public String folio;
    public String texto;

    public ContextoEditarHistorialCurso(String curso, String folio, String texto, IFrmCapacitacion frm, FrmAdministrador frmA) {
        this.ventanaEditarHistorialCurso = new IFrmEditarHistorialCurso();
        this.ventanaCapacitaciones = frm;
        this.ventanaAdministrador = frmA;
        this.curso = curso;
        this.folio = folio;
        this.texto = texto;
    }
}
