package Controller;

import ContextController.ContextoEditarCertificacion;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Model.PersonalCertificado;
import Querys.ConsultasPersonalCertificado;
import Subviews.IFrmEditarCertificacion;
import Tables.DesignTabla;
import View.FrmAdministrador;
import View.IFrmCapacitacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CtrlEditarCertificacion implements ActionListener {

    private final PersonalCertificado mod = new PersonalCertificado();
    private final ConsultasPersonalCertificado modC = new ConsultasPersonalCertificado();
    private final IFrmEditarCertificacion frm;
    private final IFrmCapacitacion frmA;
    private final FrmAdministrador frmB;
    private final String texto;
    private final String folio;

    public CtrlEditarCertificacion(ContextoEditarCertificacion contexto) {
        this.frm = contexto.ventanaEditarCertificacion;
        this.frmA = contexto.ventanaCapacitaciones;
        this.frmB = contexto.ventanaAdmnistrador;
        this.texto = contexto.texto;
        this.folio = contexto.folio;
        this.frm.btn_buscar.addActionListener(this);
        this.frm.btn_guardar.addActionListener(this);
    }

    public void iniciar() {
        frm.txt_nombre.setEditable(false);
        ButtonFunctions.TxtBtnCertificacion(texto, folio, frm, mod, modC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_buscar) {
            String nomina = frm.txt_nomina.getText();
            String nombres = QueryFunctions.CapturaCondicionalSimple(
                    "asistentes_certificados", "Nombres", "asistentes_curso_idAsistente", nomina);
            String apellidos = QueryFunctions.CapturaCondicionalSimple(
                    "asistentes_certificados", "Apellidos", "asistentes_curso_idAsistente", nomina);
            frm.txt_apellidos.setText(convertirPrimerasLetrasMayusculas(apellidos));
            frm.txt_nombres.setText(convertirPrimerasLetrasMayusculas(nombres));
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            mod.setIdTrabajador_Certificado(frm.txt_nomina.getText());
            mod.setApellidos(frm.txt_apellidos.getText());
            mod.setNombres(frm.txt_nombres.getText());
            int idCertificado = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("certificado", "idcertificado",
                    "nombre_certificado", frm.cb_certificado.getSelectedItem().toString()));
            mod.setId_Certificado(idCertificado);
            mod.setTipo_certificado(frm.cb_tipo.getSelectedItem().toString());
            String fecha_inicio = ((JTextField) frm.dc_inicio.getDateEditor().getUiComponent()).getText();
            mod.setFecha_inicio(fecha_inicio);
            String fecha_certificacion = ((JTextField) frm.dc_certificacion.getDateEditor().getUiComponent()).getText();
            mod.setFecha_certificacion(fecha_certificacion);
            if (modC.agregar(mod)) {
                JOptionPane.showMessageDialog(null, "Certificación Guardada");
                DesignTabla.designAsistentesCertificados(frmA, idCertificado);
                frm.dispose();
            }
        }

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            mod.setIdCertificacion(Integer.parseInt(folio));
            mod.setIdTrabajador_Certificado(frm.txt_nomina.getText());
            mod.setApellidos(frm.txt_apellidos.getText());
            mod.setNombres(frm.txt_nombres.getText());
            int idCertificado = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("certificado", "idcertificado",
                    "nombre_certificado", frm.cb_certificado.getSelectedItem().toString()));
            mod.setId_Certificado(idCertificado);
            mod.setTipo_certificado(frm.cb_tipo.getSelectedItem().toString());
            String fecha_inicio = ((JTextField) frm.dc_inicio.getDateEditor().getUiComponent()).getText();
            mod.setFecha_inicio(fecha_inicio);
            String fecha_certificacion = ((JTextField) frm.dc_certificacion.getDateEditor().getUiComponent()).getText();
            mod.setFecha_certificacion(fecha_certificacion);
            if (modC.actualizar(mod)) {
                JOptionPane.showMessageDialog(null, "Certificación Actualizada");
                DesignTabla.designAsistentesCertificados(frmA, idCertificado);
                frm.dispose();
            }
        }
    }

    public static String convertirPrimerasLetrasMayusculas(String texto) {
        String toLowerCase = texto.toLowerCase();
        StringBuilder resultado = new StringBuilder();
        boolean capitalizar = true; // Indica si la siguiente letra debe ser mayúscula

        for (int i = 0; i < toLowerCase.length(); i++) {
            char caracter = toLowerCase.charAt(i);

            if (Character.isWhitespace(caracter)) {
                capitalizar = true; // Si es un espacio en blanco, la siguiente letra debe ser mayúscula
            } else if (capitalizar) {
                caracter = Character.toUpperCase(caracter);
                capitalizar = false; // Ya se capitalizó la letra, la siguiente debe ser minúscula
            }
            resultado.append(caracter);
        }

        return resultado.toString();
    }
}
