package ContextController;

import Model.HistorialCurso;
import Subviews.IFrmAgregarInstructor;
import Subviews.IFrmEditarHistorialCurso;

public class ContextoAgregarInstructor {

    public IFrmAgregarInstructor ventanaAgregarInstructor;
    public IFrmEditarHistorialCurso ventanaEditarHistorialCurso;
    public HistorialCurso modeloHistorial;

    public ContextoAgregarInstructor(HistorialCurso mod, IFrmEditarHistorialCurso frm) {
        this.ventanaAgregarInstructor = new IFrmAgregarInstructor();
        this.modeloHistorial = mod;
        this.ventanaEditarHistorialCurso = frm;
    }
}
