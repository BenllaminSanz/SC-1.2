//Controlador de la ventana Editar Trabajador
//Recibe la estructura grafica de Frame EditarTrabajador
/*Esta pantalla debe permitir al administrador modificar el aspecto general 
del trabajador, todas sus caractersiticas y relaciones con la empresa con 
respecto a la estructura marcada en la base de datos*/
package Controller;

import ContextController.ContextoEditarTrabajador;
import Functions.ButtonFunctions;
import Functions.QueryFunctions;
import Querys.ConsultasTrabajador;
import Model.Trabajador;
import Subviews.IFrmEditarTrabajador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CtrlEditarTrabajador implements ActionListener {

    private final Trabajador mod = new Trabajador();
    private final ConsultasTrabajador modC = new ConsultasTrabajador();
    private final IFrmEditarTrabajador frm;
    private final String texto;
    private final String folio;

    //Relacionamos el controlador con la vista
    public CtrlEditarTrabajador(ContextoEditarTrabajador contexto) {
        this.frm = contexto.ventanaEditarTrabajador;
        this.texto = contexto.texto;
        this.folio = contexto.folio;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.btn_limpiar.addActionListener(this);
        this.frm.btn_cancelar.addActionListener(this);
        this.frm.cb_brigadista.addActionListener(this);
    }

    //Funcion de inicio
    public void iniciar() throws SQLException, ParseException {
        //Metodo para asignar texto al boton
        ButtonFunctions.TxtBtnTrabajador(texto, folio, frm, mod, modC);
    }

    //Acciones de los botones 
    @Override
    public void actionPerformed(ActionEvent e) {
        //Función del botón Guardar
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

                String area = frm.txt_area.getSelectedItem().toString();
                mod.setÁrea_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "Nombre_Area", area)));

                String turno = frm.txt_turno.getSelectedItem().toString();
                mod.setTurno_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "turno", "idturno", "nombre_turno", turno)));

                String puesto = frm.txt_puesto.getSelectedItem().toString();
                mod.setPuesto_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "puesto", "idPuesto", "Nombre_Puesto", puesto)));

                mod.setSalarioDiario_Trabajador(Double.valueOf(frm.txt_salario.getText()));

                String fecha1 = ((JTextField) frm.dt_Cumpleaños.getDateEditor().getUiComponent()).getText();
                if (!fecha1.isEmpty()) {
                    mod.setFecha_Cumpleaños(fecha1);
                }

                mod.setEmail_Trabajador(frm.txt_Email.getText());
                mod.setTel_Trabajador(frm.txt_tel.getText());
                mod.setComentario(frm.txtA_Com.getText());
                
                if(frm.cb_brigadista.isSelected()){
                    mod.setBrigadista(true);
                    int idBrigada = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                            "brigadas", "idBrigadas", "nombre_brigada", 
                            frm.cb_brigada.getSelectedItem().toString()));
                    mod.setBrigada(idBrigada);
                } else{
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
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al guardar");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "No deje espacios en blanco");
                System.err.println(" Error en la entrada de Datos: " + ex);
            }
        }

        //Función del botón Modificar
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            //Capturamos los datos de la ventana en la instacia del objeto Trabajador
            mod.setFolio_Trabajador(frm.txt_Folio.getText());
            mod.setNombre_Trabajador(frm.txt_Nombre.getText().toUpperCase());
            mod.setCURP_Trabajador(frm.txt_Curp.getText());
            mod.setRFC_Trabajador(frm.txt_RFC.getText());
            mod.setIMSS_Trabajador(frm.txt_IMSS.getText());
            //Convertir la entrada de tipo java.util.date a un String
            String fecha = ((JTextField) frm.dt_Ingreso.getDateEditor().getUiComponent()).getText();
            mod.setFecha_Antiguedad(fecha);
            String area = frm.txt_area.getSelectedItem().toString();
            mod.setÁrea_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "area", "idArea", "Nombre_Area", area)));
            String turno = frm.txt_turno.getSelectedItem().toString();
            mod.setTurno_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "turno", "idturno", "nombre_turno", turno)));
            String puesto = frm.txt_puesto.getSelectedItem().toString();
            mod.setPuesto_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "puesto", "idPuesto", "Nombre_Puesto", puesto)));
            mod.setSalarioDiario_Trabajador(Double.valueOf(frm.txt_salario.getText()));
            String fecha1 = ((JTextField) frm.dt_Cumpleaños.getDateEditor().getUiComponent()).getText();
            mod.setFecha_Cumpleaños(fecha1);
            mod.setEmail_Trabajador(frm.txt_Email.getText());
            mod.setTel_Trabajador(frm.txt_tel.getText());
            mod.setComentario(frm.txtA_Com.getText());
            
            if(frm.cb_brigadista.isSelected()){
                    mod.setBrigadista(true);
                    int idBrigada = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                            "brigadas", "idBrigadas", "nombre_brigada", 
                            frm.cb_brigada.getSelectedItem().toString()));
                    mod.setBrigada(idBrigada);
                } else{
                    mod.setBrigadista(false);
                }
                    System.out.println(folio);

            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            if (modC.modificar(mod, folio)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }

        //Función del botón Cancelar
        if (e.getSource() == frm.btn_cancelar) {
            frm.dispose();
        }

        //Función del botón Limpiar
        if (e.getSource() == frm.btn_limpiar) {
            limpiar();
        }

        if (e.getSource() == frm.cb_brigadista) {
            if (frm.cb_brigadista.isSelected()) {
                frm.cb_brigada.setEnabled(true);
            } else {
                frm.cb_brigada.setEnabled(false);
            }
        }
    }

//Metodo para limpiar las casillas
    public void limpiar() {
        frm.txt_Folio.setText(null);
        frm.txt_Nombre.setText(null);
        frm.txt_Curp.setText(null);
        frm.txt_RFC.setText(null);
        frm.txt_IMSS.setText(null);
        frm.dt_Ingreso.setCalendar(null);
        frm.txt_area.setSelectedIndex(-1);
        frm.txt_turno.setSelectedIndex(-1);
        frm.txt_puesto.setSelectedIndex(-1);
        frm.txt_salario.setText(null);
        frm.dt_Cumpleaños.setCalendar(null);
        frm.txt_Email.setText(null);
        frm.txt_tel.setText(null);
        frm.txtA_Com.setText(null);
    }
}
