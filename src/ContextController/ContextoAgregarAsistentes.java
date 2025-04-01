package ContextController;

import Subviews.IFrmAgregarAsistente;
import View.IFrmCapacitacion;

public class ContextoAgregarAsistentes {
    
    public IFrmAgregarAsistente ventanaAgregarAsistente;
    public IFrmCapacitacion ventanaCapacitaciones;
    public String idCurso;
    public String idHistorial;

    public ContextoAgregarAsistentes(String IDCurso, String IDHistorial, IFrmCapacitacion frm) {
        this.ventanaAgregarAsistente = new IFrmAgregarAsistente();
        this.ventanaCapacitaciones = frm;
        this.idCurso = IDCurso;
        this.idHistorial = IDHistorial;
    }
}
