package Controller;

import ContextController.ContextoAgregarPuesto;
import Functions.QueryFunctions;
import Model.Certificado;
import Model.Puesto;
import Querys.ConsultasCertificadoPuesto;
import Subviews.IFrmEditarCertificado;
import Subviews.IFrmAgregarPuestoCertificado;
import Tables.DesignTabla;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CtrlAgregarPuestoCertificado implements ActionListener, ItemListener, KeyListener {

    private final Certificado mod;
    private final ConsultasCertificadoPuesto modC = new ConsultasCertificadoPuesto();
    private final IFrmAgregarPuestoCertificado frm;
    private final IFrmEditarCertificado frmA;
    private String texto = null;

    public CtrlAgregarPuestoCertificado(ContextoAgregarPuesto contexto) {
        this.mod = contexto.modelo;
        this.frm = contexto.ventanaPuestoCertificado;
        this.frmA = contexto.ventanaCertificado;
        this.texto = contexto.texto;
        this.frm.cb_area.addItemListener(this);
        this.frm.item_AgregarTrabajadorCapacitacion.addActionListener(this);
        this.frm.jTextField1.addKeyListener(this);
    }

    public void iniciar() {
        if (!texto.equals("Guardar")) {
            DesignTabla.designPuestosCertificado(frm, String.valueOf(mod.getIdCertificado()));
        }
        frm.cb_area.addItem("Todas las áreas...");
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_area);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.item_AgregarTrabajadorCapacitacion && !texto.equals("Guardar")) {
            int[] selectedRows = frm.jTable_Puestos.getSelectedRows();
            List<Puesto> rows = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                Puesto ac = new Puesto();
                String nombrePuesto = frm.jTable_Puestos.getValueAt(selectedRows[i], 0).toString();
                ac.setNombre_Puesto(nombrePuesto);
                ac.setIdPuesto(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "puesto", "idPuesto", "nombre_Puesto", nombrePuesto)));
                rows.add(ac);
            }

            if (modC.registarPuesto(String.valueOf(mod.getIdCertificado()), rows)) {
                JOptionPane.showMessageDialog(null, "Puesto(s) asignados al certificado");
                DesignTabla.designCertificadoPuesto(frmA, String.valueOf(mod.getIdCertificado()));
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }

        if (e.getSource() == frm.item_AgregarTrabajadorCapacitacion && texto.equals("Guardar")) {
            int[] selectedRows = frm.jTable_Puestos.getSelectedRows();
            List<Puesto> rows = new ArrayList<>();
            
            for (int i = 0; i < selectedRows.length; i++) {
                Puesto ac = new Puesto();
                String nombrePuesto = frm.jTable_Puestos.getValueAt(selectedRows[i], 0).toString();
                ac.setNombre_Puesto(nombrePuesto);
                ac.setIdPuesto(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "puesto", "idPuesto", "nombre_Puesto", nombrePuesto)));
                rows.add(ac);
            }

           DesignTabla.designNuevoCertificadoPuesto(frmA, modC, rows);
           frm.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == frm.cb_area) {
            if ((frm.cb_area.getSelectedItem().toString()).equals(
                    "Todas las áreas...")) {
                DesignTabla.designPuestosCertificado(frm, String.valueOf(mod.getIdCertificado()));
            } else {
                int idArea = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "nombre_Area",
                        frm.cb_area.getSelectedItem().toString()));
                DesignTabla.designPuestosCertificadoF(frm, mod.getIdCertificado(), idArea);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == frm.jTextField1) {
            String busqueda = frm.jTextField1.getText();
            if (busqueda.equals("")) {
                DesignTabla.designPuestosCertificado(frm, String.valueOf(mod.getIdCertificado()));
            } else {
                DesignTabla.designPuestosCertificadoB(frm, String.valueOf(mod.getIdCertificado()), busqueda);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

}
