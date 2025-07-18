package ContextController;

import Subviews.IFrmEditarDocumento;
import View.IFrmCapacitacion;

public class ContextoEditarDocumento {

    public IFrmEditarDocumento ventanaEditarDocumento;
    public IFrmCapacitacion ventanaCapacitaciones;
    public String curso;
    public String boton;
    public String folio;

    public ContextoEditarDocumento(String curso, String txtBoton, String folio, IFrmCapacitacion frm) {
        this.ventanaEditarDocumento = new IFrmEditarDocumento();
        this.ventanaCapacitaciones = frm;
        this.curso = curso;
        this.boton = txtBoton;
        this.folio = folio;
    }
}
