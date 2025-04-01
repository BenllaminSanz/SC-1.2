//Controlador de la ventana Editar Trabajador
//Recibe la estructura grafica de Frame EditarTrabajador
/*Esta pantalla debe permitir al administrador modificar el aspecto general 
del trabajador, todas sus caractersiticas y relaciones con la empresa con 
respecto a la estructura marcada en la base de datos*/
package Controller;

import ContextController.ContextoEditarBaja;
import Functions.QueryFunctions;
import Functions.DateTools;
import Model.Bajas;
import Querys.ConsultasBaja;
import Subviews.IFrmEditarBaja;
import Tables.DesignTabla;
import View.IFrmTrabajador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CtrlEditarBaja implements ActionListener {

    private final Bajas mod = new Bajas();
    private final ConsultasBaja modC = new ConsultasBaja();
    private final IFrmEditarBaja frm;
    private final IFrmTrabajador frmA;
    private final String folio;

    //Relacionamos el controlador con la vista
    public CtrlEditarBaja(ContextoEditarBaja contexto) {
        this.frm = contexto.ventanaEditarBaja;
        this.frmA = contexto.ventanaTrabajador;
        this.folio = contexto.folio;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.btn_limpiar.addActionListener(this);
        this.frm.btn_cancelar.addActionListener(this);
    }

    //Funcion de inicio
    public void iniciar() {
        //Caracteristicas de la ventana
        frm.setTitle("Bajas");

        frm.btn_guardar.setText("Actualizar");
        mod.setFolio_Trabajador(folio);
        llenar(mod);
    }

    //Acciones de los botones 
    @Override
    public void actionPerformed(ActionEvent e) {
        //Función del botón Modificar
        if (e.getSource() == frm.btn_guardar) {
            //Capturamos los datos de la ventana en la instacia del objeto Trabajador
            mod.setFolio_Trabajador(frm.txt_Folio.getText());
            mod.setNombre_Trabajador(frm.txt_Nombre.getText());
            mod.setCURP_Trabajador(frm.txt_Curp.getText());
            mod.setRFC_Trabajador(frm.txt_RFC.getText());
            mod.setIMSS_Trabajador(frm.txt_IMSS.getText());
            //Convertir la entrada de tipo java.util.date a un String
            String fecha = ((JTextField) frm.dt_Ingreso.getDateEditor().getUiComponent()).getText();
            mod.setFecha_Antiguedad(fecha);

            String area = null;
            if (frm.txt_area.getSelectedItem() != null) {
                area = frm.txt_area.getSelectedItem().toString();
            }
            mod.setÁrea_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "area", "idArea", "Nombre_Area", area)));

            String puesto = null;
            if (frm.txt_puesto.getSelectedItem() != null) {
                puesto = frm.txt_puesto.getSelectedItem().toString();
            }
            mod.setPuesto_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "puesto", "idPuesto", "Nombre_Puesto", puesto)));

            String turno = null;
            if (frm.txt_turno.getSelectedItem() != null) {
                turno = frm.txt_turno.getSelectedItem().toString();
            }
            mod.setTurno_Trabajador(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "turno", "idturno", "nombre_turno", turno)));

            String supervisor = null;
            if (frm.txt_supervisor.getSelectedItem() != null) {
                supervisor = frm.txt_supervisor.getSelectedItem().toString();
            }
            mod.setNombre_Supervisor(supervisor);

            mod.setSalarioDiario_Trabajador(Double.valueOf(frm.txt_salario.getText()));
            String fecha1 = ((JTextField) frm.dt_Cumpleaños1.getDateEditor().getUiComponent()).getText();
            mod.setFecha_Cumpleaños(fecha1);
            mod.setEmail_Trabajador(frm.txt_Email.getText());
            mod.setTeléfono_Trabajador(frm.txt_tel.getText());
            mod.setComentario(frm.txtA_Com.getText());
            String fecha2 = ((JTextField) frm.dt_Baja.getDateEditor().getUiComponent()).getText();
            mod.setFecha_Baja(fecha2);
            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            if (modC.modificar(mod, folio)) {
                JOptionPane.showMessageDialog(null, "Registro Modificado");
                DesignTabla.designBajas(frmA);
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
    }

    public void llenar(Bajas mod) {
        if (modC.buscar(mod)) {
            frm.txt_Folio.setText(mod.getFolio_Trabajador());
            frm.txt_Nombre.setText(mod.getNombre_Trabajador());
            frm.dt_Baja.setDate(DateTools.StringtoDatePDF(mod.getFecha_Baja()));
            frm.txt_Curp.setText(mod.getCURP_Trabajador());
            frm.txt_RFC.setText(mod.getRFC_Trabajador());
            frm.txt_IMSS.setText(mod.getIMSS_Trabajador());
            frm.dt_Ingreso.setDate(DateTools.StringtoDatePDF(mod.getFecha_Antiguedad()));
            QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.txt_area);
            frm.txt_area.setSelectedIndex(mod.getÁrea_Trabajador() - 1);
            QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.txt_puesto);
            String idx1 = QueryFunctions.CapturaCondicionalAnidado(
                    "puesto", "area", "area_idArea", "idArea", "Nombre_Puesto",
                    "idPuesto", String.valueOf(mod.getPuesto_Trabajador()));
            frm.txt_puesto.setSelectedItem(idx1);
            QueryFunctions.LlenarComboBox("turno", "nombre_turno", frm.txt_turno);
            frm.txt_turno.setSelectedIndex(mod.getTurno_Trabajador() - 1);
            QueryFunctions.LlenarComboBox("supervisor", "Nombre_Supervisor", frm.txt_supervisor);
            frm.txt_supervisor.setSelectedItem(mod.getNombre_Supervisor());
            frm.txt_salario.setText(String.valueOf(mod.getSalarioDiario_Trabajador()));
            frm.dt_Cumpleaños1.setDate(DateTools.StringtoDatePDF(mod.getFecha_Cumpleaños()));
            frm.txt_Email.setText(mod.getEmail_Trabajador());
            frm.txt_tel.setText(mod.getTeléfono_Trabajador());
            frm.txtA_Com.setText(mod.getComentario());
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
        frm.txt_area.setSelectedIndex(0);
        frm.txt_turno.setSelectedIndex(0);
        frm.txt_puesto.setSelectedIndex(0);
        frm.txt_salario.setText(null);
        frm.dt_Baja.setCalendar(null);
        frm.txt_Email.setText(null);
        frm.txt_tel.setText(null);
        frm.txtA_Com.setText(null);
    }
}
