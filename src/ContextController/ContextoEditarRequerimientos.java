package ContextController;

import Model.RequerimientosCurso;
import Model.RequerimientosCursoAsistente;
import View.FrmAdministrador;
import java.util.List;

public class ContextoEditarRequerimientos {

    public FrmAdministrador ventanaAdministrador;
    public List<RequerimientosCurso> listaRequerimientos;
    public RequerimientosCursoAsistente modeloRequerimientosAsistente;
    public int idCurso = 0;

    public ContextoEditarRequerimientos(List<RequerimientosCurso> rq, RequerimientosCursoAsistente modAc, FrmAdministrador frm, int curso) {
        this.ventanaAdministrador = frm;
        this.listaRequerimientos = rq;
        this.modeloRequerimientosAsistente = modAc; 
        this.idCurso = curso;
    }
}