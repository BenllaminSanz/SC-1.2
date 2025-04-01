package Controller;

import ContextController.ContextoEditarCertificado;
import ContextController.ContextoEditarCurso;
import ContextController.ContextoEditarRequerimiento;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Model.Curso;
import Model.RequerimientosCurso;
import Querys.ConsultasCurso;
import Querys.ConsultasRequerimientosCurso;
import Subviews.IFrmEditarCurso;
import Tables.CargarTabla;
import Tables.DesignTabla;
import View.FrmAdministrador;
import View.IFrmCapacitacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;

public class CtrlEditarCurso implements ActionListener, ItemListener {

    private final Curso mod = new Curso();
    private final ConsultasCurso modC = new ConsultasCurso();
    private final RequerimientosCurso modD = new RequerimientosCurso();
    private final ConsultasRequerimientosCurso modDC = new ConsultasRequerimientosCurso();
    private final IFrmEditarCurso frm;
    private final IFrmCapacitacion frmA;
    private final FrmAdministrador frmB;
    private final String texto;
    private final String folio;

    public CtrlEditarCurso(ContextoEditarCurso contexto) {
        this.frm = contexto.ventanaEditarCurso;
        this.frmA = contexto.ventanaCertficados;
        this.frmB = contexto.ventanaAdministrador;
        this.texto = contexto.texto;
        this.folio = contexto.folio;
        this.frm.btn_agregarCurso.addActionListener(this);
        this.frm.btn_cancelar.addActionListener(this);
        this.frm.Item_AgregarRequerimiento.addActionListener(this);
        this.frm.cb_tipoCurso.addItemListener(this);
        this.frm.btn_certificado.addActionListener(this);
        this.frm.btn_AgregarRequerimiento.addActionListener(this);
        this.frm.Item_ModificarRequerimiento.addActionListener(this);
        this.frm.Item_EliminarRequerimiento.addActionListener(this);
    }

    public void iniciar() {
        frm.setSize(352, frm.getHeight());
        frm.cb_tipoCurso.addItem("Seleccione el tipo de Curso...");
        QueryFunctions.LlenarComboBox("certificado", "nombre_Certificado", frm.cb_certificado);
        ButtonFunctions.TxtBtnCurso(texto, folio, frm, mod, modC, modDC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_agregarCurso && frm.btn_agregarCurso.getText().equals("Guardar")) {
            mod.setNombre_Curso(frm.txt_nombreCurso.getText());
            int idTipo = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "tipo_curso", "idTipo_curso", "nombre_tipo",
                    frm.cb_tipoCurso.getSelectedItem().toString()));
            mod.setIdTipo_Curso(idTipo);
            mod.setSemanas(Integer.parseInt(frm.spin_semanas.getValue().toString()));
            mod.setObjetivo_curso(frm.txt_objetivo.getText());
            if (!frm.cb_tipoCurso.getSelectedItem().toString().equals("PROCESOS DE PRODUCCIÓN")) {
                if (modC.agregar(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Curso " + mod.getNombre_Curso() + " agregado con exito", "Curso agregado con exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    CargarTabla.GenerarArbolCursos(frmA);
                }
            } else {
                int idCertificado = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "certificado", "idCertificado", "nombre_Certificado",
                        frm.cb_certificado.getSelectedItem().toString()));
                mod.setIdCertificado(idCertificado);
                if (modC.agregarCertifcado(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Curso " + mod.getNombre_Curso() + " agregado con exito", "Curso agregado con exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    CargarTabla.GenerarArbolCursos(frmA);
                }
            }

        }

        if (e.getSource() == frm.btn_agregarCurso && frm.btn_agregarCurso.getText().equals("Actualizar")) {
            mod.setNombre_Curso(frm.txt_nombreCurso.getText());
            int idTipo = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "tipo_curso", "idTipo_curso", "nombre_tipo",
                    frm.cb_tipoCurso.getSelectedItem().toString()));
            mod.setIdTipo_Curso(idTipo);
            mod.setObjetivo_curso(frm.txt_objetivo.getText());
            mod.setSemanas(Integer.parseInt(frm.spin_semanas.getValue().toString()));
            if (!frm.cb_tipoCurso.getSelectedItem().toString().equals("PROCESOS DE PRODUCCIÓN")) {
                if (modC.actualizar(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Curso " + mod.getNombre_Curso() + " modificado con exito", "Curso modificado",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    CargarTabla.GenerarArbolCursos(frmA);
                }
            } else {
                int idCertificado = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "certificado", "idCertificado", "nombre_Certificado",
                        frm.cb_certificado.getSelectedItem().toString()));
                mod.setIdCertificado(idCertificado);
                if (modC.actualizarCertificado(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Curso " + mod.getNombre_Curso() + " modificado con exito", "Curso modificado",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    CargarTabla.GenerarArbolCursos(frmA);
                }
            }
        }

        if (e.getSource() == frm.btn_certificado) {
            String txtBoton = "Guardar";
            ContextoEditarCertificado contexto = new ContextoEditarCertificado(txtBoton, null, frmA, frmB);
            CtrlEditarCertificado ctrl = new CtrlEditarCertificado(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmB, contexto.ventanaEditarCertificado);
        }

        if (e.getSource() == frm.btn_AgregarRequerimiento) {
            String txtBoton = "Guardar";
            ContextoEditarRequerimiento contexto = new ContextoEditarRequerimiento(folio, txtBoton, null, frm, frmB);
            CtrlEditarRequerimiento ctrl = new CtrlEditarRequerimiento(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmB, contexto.ventanaEditarRequerimiento);
        }

        if (e.getSource() == frm.Item_ModificarRequerimiento) {
            String requerimiento = frm.jTable_Requerimientos.getValueAt(frm.jTable_Requerimientos.getSelectedRow(), 0).toString();
            String txtBoton = "Actualizar";
            ContextoEditarRequerimiento contexto = new ContextoEditarRequerimiento(folio, txtBoton, requerimiento, frm, frmB);
            CtrlEditarRequerimiento ctrl = new CtrlEditarRequerimiento(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmB, contexto.ventanaEditarRequerimiento);
        }

        if (e.getSource() == frm.Item_EliminarRequerimiento) {
            String requerimiento = frm.jTable_Requerimientos.getValueAt(frm.jTable_Requerimientos.getSelectedRow(), 0).toString();
            modD.setIdRequerimiento(Integer.parseInt(requerimiento));
            if (modDC.eliminar(modD)) {
                DesignTabla.designRequerimientosCurso(frm, String.valueOf(mod.getIdCurso()));
            }
        }

        if (e.getSource() == frm.btn_cancelar) {
            frm.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == frm.cb_tipoCurso) {
            if ((frm.cb_tipoCurso.getSelectedItem().toString()).equals(
                    "PROCESOS DE PRODUCCIÓN")) {
                frm.jLabel3.setVisible(true);
                frm.cb_certificado.setVisible(true);
                frm.btn_certificado.setVisible(true);
                if (!texto.equals("Guardar")) {
                    frm.setSize(704, 396);
                }
                ViewTools.Centrar(frmB, frm);
            } else {
                frm.jLabel3.setVisible(false);
                frm.cb_certificado.setVisible(false);
                frm.btn_certificado.setVisible(false);
                frm.setSize(352, frm.getHeight());
                ViewTools.Centrar(frmB, frm);
            }
        }
    }
}
