package Controller;

import ContextController.ContextoEditarRequerimiento;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Model.RequerimientosCurso;
import Querys.ConsultasRequerimientosCurso;
import Subviews.IFrmEditarCurso;
import Subviews.IFrmEditarRequerimiento;
import Tables.DesignTabla;
import View.FrmAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CtrlEditarRequerimiento implements ActionListener {

    private final RequerimientosCurso mod = new RequerimientosCurso();
    private final ConsultasRequerimientosCurso modC = new ConsultasRequerimientosCurso();
    private final IFrmEditarRequerimiento frm;
    private final IFrmEditarCurso frmA;
    private final FrmAdministrador frmB;
    private final String curso;
    private final String boton;
    private final String folio;

    CtrlEditarRequerimiento(ContextoEditarRequerimiento contexto) {
        this.frm = contexto.ventanaEditarRequerimiento;
        this.frmA = contexto.ventanaEditarCurso;
        this.frmB = contexto.ventanaAdministrador;
        this.curso = contexto.curso;
        this.boton = contexto.boton;
        this.folio = contexto.folio;
        this.frm.btn_filechooser.addActionListener(this);
        this.frm.btn_guardar.addActionListener(this);
    }

    void iniciar() {
        QueryFunctions.LlenarComboBox("curso", "nombre_curso", frm.cb_cursos);
        if (curso == null) {
            frm.cb_cursos.setSelectedIndex(0);
        } else {
            frm.cb_cursos.setSelectedItem(curso);
        }
        ButtonFunctions.TxtBtnRequerimiento(boton, folio, frm, mod, modC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_filechooser) {
            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de Word", "docx"));
            int seleccion = fileChooser.showSaveDialog(null);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                FileInputStream fis = null;
                try {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    String nombreArchivo = archivoSeleccionado.getName();
                    String rutaArchivo = archivoSeleccionado.getAbsolutePath();
//                    String extensionArchivo = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
                    fis = new FileInputStream(archivoSeleccionado);
                    byte[] contenido = new byte[(int) archivoSeleccionado.length()];
                    fis.read(contenido);
                    fis.close();
                    mod.setNombre_archivo(nombreArchivo);
                    mod.setRuta_Docuemento(rutaArchivo);
                    frm.txt_documento.setText(nombreArchivo);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CtrlEditarRequerimiento.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CtrlEditarRequerimiento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("curso", "idCurso", "nombre_curso",
                    frm.cb_cursos.getSelectedItem().toString()));
            mod.setIdCurso(idCurso);
            mod.setNombre_requerimiento(frm.txt_requerimiento.getText());
            mod.setDescp_requerimiento(frm.txt_descripcion.getText());
            if (modC.agregar(mod)) {
                JOptionPane.showMessageDialog(frm, "Registro Exitoso del Requerimiento");
                frm.dispose();
                DesignTabla.designRequerimientosCurso(frmA, String.valueOf(idCurso));
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "requerimientos", "curso_idCurso", "idRequerimientos", folio));
            mod.setIdCurso(idCurso);
            mod.setNombre_requerimiento(frm.txt_requerimiento.getText());
            mod.setDescp_requerimiento(frm.txt_descripcion.getText());
            if (modC.actualizar(mod)) {
                JOptionPane.showMessageDialog(frm, "Actualización Exitosa del Requerimiento");
                frm.dispose();
                DesignTabla.designRequerimientosCurso(frmA, String.valueOf(idCurso));
            }
        }
    }
}
