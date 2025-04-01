package Controller;

import ContextController.ContextoEditarAdministrativo;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Model.Administrativos;
import Querys.ConsultasAdministrativo;
import Subviews.IFrmEditarAdministrativo;
import Tables.DesignTabla;
import View.IFrmTrabajador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CtrlEditarAdministrativo implements ActionListener {

    private final Administrativos mod = new Administrativos();
    private final ConsultasAdministrativo modC = new ConsultasAdministrativo();
    private final IFrmEditarAdministrativo frm;
    private final IFrmTrabajador frmA;
    private final String texto;
    private final String folio;

    CtrlEditarAdministrativo(ContextoEditarAdministrativo contexto) {
        this.frm = contexto.ventanaEditarAdministrativo;
        this.frmA = contexto.ventanaTrabajador;
        this.texto = contexto.texto;
        this.folio = contexto.folio;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.cb_brigadista.addActionListener(this);
    }

    void iniciar() {
        ButtonFunctions.TxtBtnAdministrativo(texto, folio, frm, mod, modC);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            try {
                //Capturamos los datos de la ventana en la instacia del objeto Trabajador
                mod.setFolio_Trabajador(frm.txt_Folio.getText());
                mod.setNombre_Trabajador(frm.txt_Nombre.getText().toUpperCase());
                mod.setCURP_Trabajador(frm.txt_Curp.getText());
                mod.setRFC_Trabajador(frm.txt_RFC.getText());
                mod.setIMSS_Trabajador(frm.txt_IMSS.getText());

                String fecha = ((JTextField) frm.dt_Ingreso.getDateEditor().getUiComponent()).getText();
                mod.setFecha_Antiguedad(fecha);

                mod.setCia(frm.jTextField1.getText());
                mod.setArea(frm.jTextField3.getText());
                mod.setPuesto(frm.jTextField2.getText());
                mod.setTurno(frm.cb_turnos.getSelectedItem().toString());

                if (frm.cb_brigadista.isSelected()) {
                    mod.setBrigadista(true);
                    int idBrigada = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                            "brigadas", "idBrigadas", "nombre_brigada",
                            frm.cb_brigada.getSelectedItem().toString()));
                    mod.setBrigada(idBrigada);
                } else {
                    mod.setBrigadista(false);
                }

                //Variable para determinar confrmación del usuario
                int i = JOptionPane.showConfirmDialog(null,
                        "¿Seguro de agregar este Trabajador?");

                //Condicion de confirmacion
                if (i == 0) {
                    //Llamada a la funcion de registrar enviando los datos guardados en la instacia
                    if (modC.registrar(mod)) {
                        JOptionPane.showMessageDialog(null, "Registro Existoso");
                        DesignTabla.designAdministrativos(frmA);
                        frm.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "No deje espacios en blanco");
                System.err.println(" Error en la entrada de Datos: " + ex);
            }
        }

        if (e.getSource()
                == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            //Capturamos los datos de la ventana en la instacia del objeto Trabajador
            mod.setFolio_Trabajador(frm.txt_Folio.getText());
            mod.setNombre_Trabajador(frm.txt_Nombre.getText().toUpperCase());
            mod.setCURP_Trabajador(frm.txt_Curp.getText());
            mod.setRFC_Trabajador(frm.txt_RFC.getText());
            mod.setIMSS_Trabajador(frm.txt_IMSS.getText());

            String fecha = ((JTextField) frm.dt_Ingreso.getDateEditor().getUiComponent()).getText();
            mod.setFecha_Antiguedad(fecha);

            mod.setCia(frm.jTextField1.getText());
            mod.setArea(frm.jTextField3.getText());
            mod.setPuesto(frm.jTextField2.getText());
            mod.setTurno(frm.cb_turnos.getSelectedItem().toString());

            if (frm.cb_brigadista.isSelected()) {
                mod.setBrigadista(true);
                int idBrigada = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "brigadas", "idBrigadas", "nombre_brigada",
                        frm.cb_brigada.getSelectedItem().toString()));
                mod.setBrigada(idBrigada);
            } else {
                mod.setBrigadista(false);
            }

            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            if (modC.modificar(mod, folio)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                DesignTabla.designAdministrativos(frmA);
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }

        if (e.getSource()
                == frm.cb_brigadista) {
            if (frm.cb_brigadista.isSelected()) {
                frm.cb_brigada.setEnabled(true);
            } else {
                frm.cb_brigada.setEnabled(false);
            }
        }
    }
}
