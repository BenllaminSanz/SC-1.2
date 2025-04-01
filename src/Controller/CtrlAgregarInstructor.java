package Controller;

import ContextController.ContextoAgregarInstructor;
import Model.HistorialCurso;
import Querys.ConsultasHistorialCurso;
import Subviews.IFrmAgregarInstructor;
import Subviews.IFrmEditarHistorialCurso;
import Tables.DesignTabla;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CtrlAgregarInstructor implements ActionListener {

    private final HistorialCurso mod = new HistorialCurso();
    private final ConsultasHistorialCurso modC = new ConsultasHistorialCurso();
    private final IFrmAgregarInstructor frm;
    private final IFrmEditarHistorialCurso frmA;

    CtrlAgregarInstructor(ContextoAgregarInstructor contexto) {
        this.frm = contexto.ventanaAgregarInstructor;
        this.frmA = contexto.ventanaEditarHistorialCurso;
        this.frm.btn_agregarInterno.addActionListener(this);
        this.frm.btn_agregarExterno.addActionListener(this);
    }

    public void iniciar() {
        DesignTabla.designInstructores(frm);
    }

    StringBuilder concatenacion = new StringBuilder();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_agregarInterno) {
            int[] filasSeleccionadas = frm.jTable_InstructuresLBU.getSelectedRows();

            for (int fila : filasSeleccionadas) {
                String valor = frm.jTable_InstructuresLBU.getValueAt(fila, 1).toString();
                concatenacion.append(valor).append(",");
            }
        }

        if (e.getSource() == frm.btn_agregarExterno) {
            String nombre = frm.txt_externo.getText().toUpperCase();
            concatenacion.append(nombre).append(",");
            frmA.txt_instructor.setText(concatenacion.toString());
        }

        String resultado = concatenacion.toString();
        System.out.println(resultado);
        if (resultado.endsWith(",")) {
            resultado = resultado.substring(0, resultado.length() - 1);
        }

        frmA.txt_instructor.setText(resultado);
    }
}
