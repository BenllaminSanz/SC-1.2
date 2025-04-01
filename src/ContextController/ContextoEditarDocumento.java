package ContextController;

import Subviews.IFrmEditarRequerimiento;
import View.IFrmCapacitacion;

public class ContextoEditarDocumento {

    public IFrmEditarRequerimiento ventanaEditarRequerimiento;
    public IFrmCapacitacion ventanaCapacitaciones;
    public String curso;
    public String boton;
    public String folio;

    public ContextoEditarDocumento(String curso, String txtBoton, String folio, IFrmCapacitacion frm) {
        this.ventanaEditarRequerimiento = new IFrmEditarRequerimiento();
        this.ventanaCapacitaciones = frm;
        this.curso = curso;
        this.boton = txtBoton;
        this.folio = folio;
    }
}
