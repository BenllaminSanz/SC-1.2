package Controller;

import ContextController.ContextoAgregarInstructor;
import ContextController.ContextoEditarHistorialCurso;
import Functions.ButtonFunctions;
import Functions.DateTools;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Model.HistorialCurso;
import Querys.ConsultasHistorialCurso;
import Subviews.IFrmEditarHistorialCurso;
import View.FrmAdministrador;
import View.IFrmCapacitacion;
import com.toedter.calendar.JDateChooser;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CtrlEditarHistorialCurso implements ActionListener {

    private final HistorialCurso mod =  new HistorialCurso();
    private final ConsultasHistorialCurso modC = new ConsultasHistorialCurso();
    private final IFrmEditarHistorialCurso frm;
    private final IFrmCapacitacion frmA;
    private final FrmAdministrador frmB;
    private final String curso;
    private final String folio;
    private final String texto;

    public CtrlEditarHistorialCurso(ContextoEditarHistorialCurso contexto) {
        this.frm = contexto.ventanaEditarHistorialCurso;
        this.frmA = contexto.ventanaCapacitaciones;
        this.frmB = contexto.ventanaAdministrador;
        this.curso = contexto.curso;
        this.folio = contexto.folio;
        this.texto = contexto.texto;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.btn_concluir.addActionListener(this);
        this.frm.btn_select_instructor.addActionListener(this);
        this.frm.btn_cancelar.addActionListener(this);
    }

    public void iniciar() {
        QueryFunctions.LlenarComboBox("curso", "nombre_curso", frm.cb_curso);
        if (curso == null) {
            frm.cb_curso.setSelectedIndex(0);
        } else {
            frm.cb_curso.setSelectedItem(curso);
        }
        ButtonFunctions.TxtBtnHistorialCurso(texto, folio, frm, mod, modC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "curso", "idCurso", "nombre_curso",
                    frm.cb_curso.getSelectedItem().toString()));
            mod.setIdCurso(idCurso);
            mod.setNombre_instructor(frm.txt_instructor.getText());
            String fecha_inicio = ((JTextField) frm.dc_inicio.getDateEditor().getUiComponent()).getText();
            mod.setFecha_inicio(fecha_inicio);
            String fecha_estiamda = ((JTextField) frm.dc_estimado.getDateEditor().getUiComponent()).getText();
            mod.setFecha_estimada(fecha_estiamda);
            String str = frm.txt_duracion.getText();
            if (!str.isEmpty()) {
                mod.setTiempo_estimado(Double.parseDouble(frm.txt_duracion.getText()));
            }
            String str1 = frm.txt_asistentes.getText();
            if (!str1.isEmpty()) {
                mod.setAsistentes_estimados(Integer.parseInt(frm.txt_asistentes.getText()));
            }
            String costo = frm.txt_costo.getText();
            if (!costo.isEmpty()) {
                mod.setCosto_curso(Double.parseDouble(frm.txt_costo.getText()));
            }
            if (modC.agregar(mod, frm)) {
                JOptionPane.showMessageDialog(frm, "Registro exitoso del Curso");
                frm.dispose();
                frmA.txt_total.setText("No.Cursos: "
                        + QueryFunctions.CapturaSimple("historial_curso", "COUNT(*)"));
//                DesignTabla.designHistorialCurso(frmA, idCurso);
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            mod.setIdHistorialCurso(Integer.parseInt(folio));
            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "curso", "idCurso", "nombre_curso",
                    frm.cb_curso.getSelectedItem().toString()));
            mod.setIdCurso(idCurso);
            mod.setNombre_instructor(frm.txt_instructor.getText());
            String fecha_inicio = ((JTextField) frm.dc_inicio.getDateEditor().getUiComponent()).getText();
            mod.setFecha_inicio(fecha_inicio);
            String fecha_estimada = ((JTextField) frm.dc_estimado.getDateEditor().getUiComponent()).getText();
            mod.setFecha_estimada(fecha_estimada);
            String str = frm.txt_duracion.getText();
            if (!str.isEmpty()) {
                mod.setTiempo_estimado(Double.parseDouble(frm.txt_duracion.getText()));
            }
            String str1 = frm.txt_asistentes.getText();
            if (!str1.isEmpty()) {
                mod.setAsistentes_estimados(Integer.parseInt(frm.txt_asistentes.getText()));
            }
            String costo = frm.txt_costo.getText();
            if (!costo.isEmpty()) {
                mod.setCosto_curso(Double.parseDouble(frm.txt_costo.getText()));
            }
//            System.out.println(mod.getTiempo_estimado() + " " + mod.getAsistentes_estimados());
            if (modC.actualizar(mod, frm)) {
                JOptionPane.showMessageDialog(frm, "Curso modificado con exito");
                frm.dispose();
                frmA.txt_total.setText("No.Cursos: "
                        + QueryFunctions.CapturaSimple("historial_curso", "COUNT(*)"));
//                CtrlCapacitacionCertificados.DatosCurso(frmA, idCurso);
            }
        }

        if (e.getSource() == frm.btn_concluir) {
            // Crear el DateChooser
            JDateChooser dateChooser = new JDateChooser();

            // Crear un panel para el DateChooser y etiqueta descriptiva
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Seleccione la fecha de conclusión del curso:"));
            panel.add(dateChooser);

            // Mostrar el JOptionPane con el panel y botones personalizados
            int option = JOptionPane.showOptionDialog(frm, panel,
                    "Concluir Curso", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Obtener la fecha seleccionada si se presionó el botón "OK"
            if (option == JOptionPane.OK_OPTION) {
                mod.setIdHistorialCurso(Integer.parseInt(folio));
                Date selectedDate = dateChooser.getDate();
                String fecha_cierre = DateTools.MySQLtoString(selectedDate);
                mod.setFecha_cierre(fecha_cierre);
                if (modC.concluir(mod)) {
                    JOptionPane.showMessageDialog(frm, "Curso concluido");
                }
            }

            if (e.getSource() == frm.btn_concluir) {
                frm.dispose();
            }
        }

        if (e.getSource() == frm.btn_select_instructor) {
            ContextoAgregarInstructor contexto = new ContextoAgregarInstructor(mod, frm);
            CtrlAgregarInstructor ctrl = new CtrlAgregarInstructor(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmB, contexto.ventanaAgregarInstructor);
        }
    }
}
