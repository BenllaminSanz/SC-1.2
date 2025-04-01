package ContextController;

import Subviews.IFrmEditarCursoPersonal;
import View.FrmAdministrador;
import View.IFrmCapacitacion;

public class ContextoEditarCursoPersonal {

    public IFrmEditarCursoPersonal ventanaEditarCurso;
    public IFrmCapacitacion ventanaCapacitaciones;
    public FrmAdministrador ventanaAdministrador;
    public String idCurso;
    public String idTrabajador;

    public ContextoEditarCursoPersonal(String idTrabajador, String idCurso, IFrmCapacitacion frm, FrmAdministrador frmA) {
        this.ventanaEditarCurso = new IFrmEditarCursoPersonal();
        this.ventanaCapacitaciones = frm;
        this.ventanaAdministrador = frmA;
        this.idCurso = idCurso;
        this.idTrabajador = idTrabajador;
    }
}
