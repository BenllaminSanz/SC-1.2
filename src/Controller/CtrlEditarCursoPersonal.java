package Controller;

import ContextController.ContextoEditarCursoPersonal;
import Model.PersonalCurso;
import Querys.ConsultasPersonalCurso;
import Subviews.IFrmEditarCursoPersonal;
import View.FrmAdministrador;
import View.IFrmCapacitacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlEditarCursoPersonal implements ActionListener {

    private final PersonalCurso mod = new PersonalCurso();
    private final ConsultasPersonalCurso modC = new ConsultasPersonalCurso();
    private final IFrmEditarCursoPersonal frm;
    private final IFrmCapacitacion frmA;
    private final FrmAdministrador frmB;
    private String idCurso = null;
    private String idTrabajador = null;

    public CtrlEditarCursoPersonal(ContextoEditarCursoPersonal contexto) {
        this.frm = contexto.ventanaEditarCurso;
        this.frmA = contexto.ventanaCapacitaciones;
        this.frmB = contexto.ventanaAdministrador;
        this.idCurso = contexto.idCurso;
        this.idTrabajador = contexto.idTrabajador;
        this.frm.btn_guardar.addActionListener(this);
    }

    public void iniciar() {
        mod.setIdHistorial_Curso(Integer.parseInt(idCurso));
        mod.setIdTrabajador(Integer.parseInt(idTrabajador));
        if (modC.buscar(mod)) {
            frm.txt_nomina.setText(String.valueOf(mod.getIdTrabajador()));
            frm.txt_nombre.setText(mod.getNombre_Trabajador());
            if (mod.getTipo_curso() != null) {
                frm.cb_tipo.setSelectedItem(mod.getTipo_curso());
            } else {
                frm.cb_tipo.setSelectedIndex(0);
            }
            frm.cb_estado.setSelectedItem(mod.getEstado_curso());
        }
        frm.txt_nomina.setEditable(false);
        frm.txt_nombre.setEditable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_guardar) {
            mod.setIdHistorial_Curso(Integer.parseInt(idCurso));
            mod.setIdTrabajador(Integer.parseInt(idTrabajador));
            if (frm.cb_tipo.getSelectedIndex() == 0) {
                mod.setTipo_curso("");
            } else {
                mod.setTipo_curso(frm.cb_tipo.getSelectedItem().toString());
            }
            mod.setEstado_curso(frm.cb_estado.getSelectedItem().toString());
            if (modC.actualizar(mod)) {
                JOptionPane.showMessageDialog(null, "Actulización correcta");
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error en la Actulización");
                frm.dispose();
            }
        }
    }
}
