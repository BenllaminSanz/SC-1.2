package Controller;

import ContextController.ContextoEditarHabilidad;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Model.HabilidadesCurso;
import Querys.ConsultasHabilidadesCurso;
import Subviews.IFrmEditarCurso;
import Subviews.IFrmEditarHabilidad;
import Tables.CargarTabla;
import Tables.DesignTabla;
import View.FrmAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlEditarHabilidades implements ActionListener {

    private final HabilidadesCurso mod = new HabilidadesCurso();
    private final ConsultasHabilidadesCurso modC = new ConsultasHabilidadesCurso();
    private final IFrmEditarHabilidad frm;
    private final IFrmEditarCurso frmA;
    private final FrmAdministrador frmB;
    private final String curso;
    private final String boton;
    private final String folio;

    CtrlEditarHabilidades(ContextoEditarHabilidad contexto) {
        this.frm = contexto.ventanaEditarHabilidad;
        this.frmA = contexto.ventanaEditarCurso;
        this.frmB = contexto.ventanaAdministrador;
        this.curso = contexto.curso;
        this.boton = contexto.boton;
        this.folio = contexto.folio;
        this.frm.btn_guardar.addActionListener(this);
    }

    void iniciar() {
        QueryFunctions.LlenarComboBox("curso", "nombre_curso", frm.cb_cursos);
        if (curso == null) {
            frm.cb_cursos.setSelectedIndex(0);
        } else {
            frm.cb_cursos.setSelectedItem(curso);
        }
        ButtonFunctions.TxtBtnHabilidad(boton, folio, frm, mod, modC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("curso", "idCurso", "nombre_curso",
                    frm.cb_cursos.getSelectedItem().toString()));
            mod.setIdCurso(idCurso);
            mod.setNombre_habilidad(frm.txt_requerimiento.getText());
            int orden = Integer.parseInt(frm.txt_orden.getText());
            mod.setOrden_habilidad(orden);
            if (modC.agregar(mod)) {
                JOptionPane.showMessageDialog(frm, "Registro Exitoso de la habilidad");
                frm.dispose();
                DesignTabla.designHabilidadesCurso(frmA, String.valueOf(idCurso));
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "requerimientos", "curso_idCurso", "idRequerimientos", folio));
            mod.setIdCurso(idCurso);
            mod.setNombre_habilidad(frm.txt_requerimiento.getText());
            if (modC.actualizar(mod)) {
                JOptionPane.showMessageDialog(frm, "Actualización Exitosa del Requerimiento");
                frm.dispose();
                DesignTabla.designRequerimientosCurso(frmA, String.valueOf(idCurso));
            }
        }
    }

}
