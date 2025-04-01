//Controlador de la ventana Areas y Puestos
//Recibe la estructura grafica de Frame AreasPuestos
/*Esta vista permite gestionar las Areas que existen en la empresa, asi 
tambien adminitrar los puestos asignados a cada uno de ellas*/
package Controller;

import ContextController.ContextoAreasPuestos;
import ContextController.ContextoEditarArea;
import ContextController.ContextoEditarPuesto;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Model.Area;
import Querys.ConsultasArea;
import Querys.ConsultasPuesto;
import Model.Puesto;
import Tables.DesignTabla;
import View.FrmAdministrador;
import View.IFrmAreasPuestos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CtrlAreasPuestos implements ActionListener, ListSelectionListener {

    private final Area modA = new Area();
    private final ConsultasArea conA = new ConsultasArea();
    private final Puesto modP = new Puesto();
    private final ConsultasPuesto conP = new ConsultasPuesto();
    private final IFrmAreasPuestos frm;
    private final FrmAdministrador frmA;

    //Relación Controlador-Vista
    public CtrlAreasPuestos(ContextoAreasPuestos contexto) {
        this.frm = contexto.ventanaAreasPuestos;
        this.frmA = contexto.ventanaAdministrador;
        this.frm.ItemModificarA.addActionListener(this);
        this.frm.ItemModificarP.addActionListener(this);
        this.frm.ItemAgregarA.addActionListener(this);
        this.frm.ItemAgregarP.addActionListener(this);
        this.frm.ItemEliminarA.addActionListener(this);
        this.frm.ItemEliminarP.addActionListener(this);
        this.frm.btn_act2.addActionListener(this);
        this.frm.btn_agregarArea.addActionListener(this);
        this.frm.btn_agregarPuesto.addActionListener(this);
        this.frm.jTable_Areas.getSelectionModel().addListSelectionListener(this);
    }

    //Metodo de Arranque
    public void iniciar() {
        //Metodos para gestionar la UI (Interfaz de Usuario)
        setWindowProperties();
        //Modelos para las Tablas
        DesignTabla.designAreas(frm);
        DesignTabla.designPuestos(frm);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Accion para los botones que abren la ventana de agregar Area
        List<Object> validSourcesAgregarArea = Arrays.asList(frm.btn_agregarArea, frm.ItemAgregarA);
        if (validSourcesAgregarArea.contains(e.getSource())) {
            String txtBoton = "Guardar";
            ContextoEditarArea contexto = new ContextoEditarArea(txtBoton, 0, frm);
            CtrlEditarArea ctrl = new CtrlEditarArea(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarArea);
        }
        //Accion para los botones que abren la ventana de modificar Area
        if (e.getSource() == frm.ItemModificarA) {
            String Area = frm.jTable_Areas.getValueAt(
                    frm.jTable_Areas.getSelectedRow(), 0).toString();
            int folio = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("Area", "idArea", "Nombre_Area", Area));
            String txtBoton = "Actualizar";
            ContextoEditarArea contexto = new ContextoEditarArea(txtBoton, folio, frm);
            CtrlEditarArea ctrl = new CtrlEditarArea(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarArea);
        }

        //Accion para los botones que eliminan un Area
        if (e.getSource() == frm.ItemEliminarA) {
            String Area = frm.jTable_Areas.getValueAt(
                    frm.jTable_Areas.getSelectedRow(), 0).toString();
            modA.setIdArea(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("area", "idArea", "Nombre_Area", Area)));
            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja esta Área?");
            if (option == JOptionPane.OK_OPTION) {
                if (conA.eliminar(modA)) {
                    JOptionPane.showMessageDialog(null, "Área eliminada");
                    DesignTabla.designAreas(frm);
                    DesignTabla.designAreas(frm);
                    frm.txt_descArea.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar área");
                }
            }
        }

        //Accion para los botones que abren la ventana agregar Puesto
        List<Object> validSourcesAgregarPuesto = Arrays.asList(frm.btn_agregarPuesto, frm.ItemAgregarP);
        if (validSourcesAgregarPuesto.contains(e.getSource())) {
            try {
                String txtBoton = "Guardar";
                ContextoEditarPuesto contexto = new ContextoEditarPuesto(txtBoton, 0, frm);
                CtrlEditarPuesto ctrl = new CtrlEditarPuesto(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarPuesto);
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE,
                        "Error al llamar el metodo EditarArea: ", ex);
            }
        }

        //Accion para los botones que abren la ventana modificar Puesto
        if (e.getSource() == frm.ItemModificarP) {
            try {
                String Puesto = frm.jTable_Puestos.getValueAt(
                        frm.jTable_Puestos.getSelectedRow(), 0).toString();
                int folio = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("Puesto", "idPuesto", "Nombre_Puesto", Puesto));
                String txtBoton = "Actualizar";
                ContextoEditarPuesto contexto = new ContextoEditarPuesto(txtBoton, folio, frm);
                CtrlEditarPuesto ctrl = new CtrlEditarPuesto(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarPuesto);
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE,
                        "Error al llamar el metodo EditarPuesto: ", ex);
            }
        }

        //Accion para los botones que eliminan un Puesto
        if (e.getSource() == frm.ItemEliminarP) {
            String Puesto = frm.jTable_Puestos.getValueAt(
                    frm.jTable_Puestos.getSelectedRow(), 0).toString();
            modP.setIdPuesto(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("puesto", "idPuesto", "Nombre_Puesto", Puesto)));
            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja esta Área?");
            if (option == JOptionPane.OK_OPTION) {
                if (conP.eliminar(modP)) {
                    JOptionPane.showMessageDialog(null, "Puesto eliminado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar puesto");
                }
            }
        }

        //Accion para los botones que actualizan la tabla Puestos
        List<Object> validSourcesActualizarPuesto = Arrays.asList(frm.btn_act2);
        if (validSourcesActualizarPuesto.contains(e.getSource())) {
            DesignTabla.designAreas(frm);
            DesignTabla.designPuestos(frm);
            frm.txt_descArea.setText(null);
        }
    }

    private void setWindowProperties() {
        frm.setTitle("Área y Puestos");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == frm.jTable_Areas.getSelectionModel()) {
            if (!e.getValueIsAdjusting()) {
                if (frm.jTable_Areas.getSelectedRow() >= 0
                        && frm.jTable_Areas.getSelectedRow() < frm.jTable_Areas.getRowCount()) {
                    String Area = frm.jTable_Areas.getValueAt(
                            frm.jTable_Areas.getSelectedRow(), 0).toString();
                    String idArea = QueryFunctions.CapturaCondicionalSimple("area", "idArea", "nombre_Area", Area);
                    frm.txt_descArea.setText(QueryFunctions.CapturaCondicionalSimple(
                            "area", "descripcion", "idArea", idArea));
                    DesignTabla.designPuestosArea(frm, Integer.parseInt(idArea));
                }
            }
        }
    }
}
