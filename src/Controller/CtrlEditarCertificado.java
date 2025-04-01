package Controller;

import ContextController.ContextoAgregarPuesto;
import ContextController.ContextoEditarCertificado;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Model.Certificado;
import Querys.ConsultasCertificado;
import Querys.ConsultasCertificadoPuesto;
import Subviews.IFrmEditarCertificado;
import Tables.DesignTabla;
import View.FrmAdministrador;
import View.IFrmCapacitacion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlEditarCertificado implements ActionListener {

    private final Certificado mod =  new Certificado();
    private final ConsultasCertificado modC =  new ConsultasCertificado();
    private final ConsultasCertificadoPuesto modCC =  new ConsultasCertificadoPuesto();
    private final IFrmEditarCertificado frm;
    private final IFrmCapacitacion frmA;
    private final FrmAdministrador frmB;
    private final String texto;
    private final String folio;

    public CtrlEditarCertificado(ContextoEditarCertificado contexto) {
        this.frm = contexto.ventanaEditarCertificado;
        this.frmA = contexto.ventanaCapacitaciones;
        this.frmB = contexto.ventanaAdministrador;
        this.texto = contexto.textoBoton;
        this.folio = contexto.idCertificado;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.cb_nocurso.addActionListener(this);
        this.frm.btn_AgregarPuesto.addActionListener(this);
        this.frm.Item_EliminarPuesto.addActionListener(this);
    }

    public void iniciar() {
        ButtonFunctions.TxtBtnCertificado(texto, folio, frm, mod, modC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.cb_nocurso) {
            if (frm.cb_nocurso.isSelected()) {
                frm.jTable_Puestos.setEnabled(false);
            } else if (!frm.cb_nocurso.isSelected()) {
                frm.jTable_Puestos.setEnabled(true);
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            mod.setNombre_Certificado(frm.txt_certificado.getText());
            mod.setGerente1(frm.txt_gerente1.getText());
            mod.setGerente2(frm.txt_gerente2.getText());
            mod.setGerente3(frm.txt_gerente3.getText());
            if (!frm.cb_nocurso.isSelected()) {
                if (modC.agregarCertificado(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Certificado " + mod.getNombre_Certificado() + " agregado con exito", "Certificado agregado con exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    DesignTabla.designCertificados(frmA);
                }else{
                    JOptionPane.showMessageDialog(frm,
                            "Si el certificado no esta relacionado a ningun puesto, seleccionar la casilla 'Sin puestos asignados'", "Ajustes Certificado",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.cb_nocurso.setForeground(Color.red);
                }
            } else {
                if (modC.agregarSinCurso(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Certificado " + mod.getNombre_Certificado() + " agregado con exito", "Certificado agregado con exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    DesignTabla.designCertificados(frmA);
                }
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            mod.setNombre_Certificado(frm.txt_certificado.getText());
            mod.setGerente1(frm.txt_gerente1.getText());
            mod.setGerente2(frm.txt_gerente2.getText());
            mod.setGerente3(frm.txt_gerente3.getText());
            if (!frm.cb_nocurso.isSelected()) {
                if (modC.actualizarCurso(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Curso " + mod.getNombre_Certificado() + " agregado con exito", "Curso agregado con exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    DesignTabla.designCertificados(frmA);
                }
            } else {
                if (modC.actualizarSinCurso(mod)) {
                    JOptionPane.showMessageDialog(frm,
                            "Curso " + mod.getNombre_Certificado() + " agregado con exito", "Curso agregado con exito",
                            JOptionPane.INFORMATION_MESSAGE);
                    frm.dispose();
                    DesignTabla.designCertificados(frmA);
                }
            }
        }
        
        if (e.getSource() == frm.btn_AgregarPuesto && frm.btn_guardar.getText().equals("Guardar")) {
            ContextoAgregarPuesto contexto = new ContextoAgregarPuesto(mod, frm, texto);
            CtrlAgregarPuestoCertificado ctrl = new CtrlAgregarPuestoCertificado(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmB, contexto.ventanaPuestoCertificado);
        }

        if (e.getSource() == frm.btn_AgregarPuesto && frm.btn_guardar.getText().equals("Actualizar")) {
            mod.setIdCertificado(Integer.parseInt(folio));
            ContextoAgregarPuesto contexto = new ContextoAgregarPuesto(mod, frm, texto);
            CtrlAgregarPuestoCertificado ctrl = new CtrlAgregarPuestoCertificado(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmB, contexto.ventanaPuestoCertificado);
        }

        if (e.getSource() == frm.Item_EliminarPuesto) {
            String Puesto = frm.jTable_Puestos.getValueAt(
                    frm.jTable_Puestos.getSelectedRow(), 0).toString();
            int idPuesto = Integer.parseInt(
                    QueryFunctions.CapturaCondicionalSimple("puesto", "idPuesto", "nombre_puesto", Puesto));
            if (modCC.eliminar(mod.getIdCertificado(), idPuesto)) {
                  DesignTabla.designCertificadoPuesto(frm, String.valueOf(mod.getIdCertificado()));
            }
        }
    }
}
