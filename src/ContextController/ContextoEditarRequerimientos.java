package ContextController;

import Model.RequerimientosCurso;
import Model.RequerimientosCursoAsistente;
import View.FrmAdministrador;
import java.util.List;

public class ContextoEditarRequerimientos {

    public FrmAdministrador ventanaAdministrador;
    public List<RequerimientosCurso> listaRequerimientos;
    public RequerimientosCursoAsistente modeloRequerimientosAsistente;

    public ContextoEditarRequerimientos(List<RequerimientosCurso> rq, RequerimientosCursoAsistente modAc, FrmAdministrador frm) {
        this.ventanaAdministrador = frm;
        this.listaRequerimientos = rq;
        this.modeloRequerimientosAsistente = modAc; 
    }
}