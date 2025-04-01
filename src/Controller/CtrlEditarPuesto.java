//Controlador de la ventana Editar Trabajador
//Recibe la estructura grafica de Frame EditarTrabajador
/*Esta pantalla debe permitir al administrador modificar el aspecto general 
del trabajador, todas sus caractersiticas y relaciones con la empresa con 
respecto a la estructura marcada en la base de datos*/
package Controller;

import ContextController.ContextoEditarPuesto;
import Functions.QueryFunctions;
import Functions.ButtonFunctions;
import Querys.ConsultasPuesto;
import Model.Puesto;
import Subviews.IFrmEditarPuesto;
import Tables.DesignTabla;
import View.IFrmAreasPuestos;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CtrlEditarPuesto implements ActionListener {

    private final Puesto mod = new Puesto();
    private final ConsultasPuesto modC = new ConsultasPuesto();
    private final IFrmEditarPuesto frm;
    private final IFrmAreasPuestos frmA;
    private final String texto;
    private final int folio;

    //Relacionamos el controlador con la vista
    public CtrlEditarPuesto(ContextoEditarPuesto contexto) {
        this.frm = contexto.ventanaEditarPuesto;
        this.frmA = contexto.ventanaAreaPuesto;
        this.texto = contexto.texto;
        this.folio = contexto.folio;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.btn_cancelar.addActionListener(this);
    }

    //Funcion de inicio
    public void iniciar() throws SQLException, ParseException {
        //Metodo para asignar texto al boton
        ButtonFunctions.TxtBtnPuesto(texto, folio, frm, mod, modC);
    }

    //Acciones de los botones 
    @Override
    public void actionPerformed(ActionEvent e) {
        //Función del botón Guardar
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            mod.setNombre_Puesto(frm.txt_Puesto.getText());
            mod.setNombre_Puesto_Ingles(frm.txt_PuestoIngles.getText());
            mod.setDescripcion(frm.txt_descPuesto.getText());
            mod.setNivel(frm.txt_nivel.getText());
            mod.setCentro_de_Costo(Integer.parseInt(frm.txt_centrocosto.getText()));
            mod.setPropuesto_Trabajadores(Integer.parseInt(frm.txt_totaltbr.getText()));
            mod.setPropuesto_Turno(Integer.parseInt(frm.txt_totalturno_tbr.getText()));
            String area = frm.cb_AreaPuesto.getSelectedItem().toString();
            mod.setArea_idArea(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "area", "idArea", "Nombre_Area", area)));

            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            if (modC.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Puesto agregado");
                DesignTabla.designPuestosArea(frmA, mod.getArea_idArea());
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar puesto");
            }
        }

        //Función del botón Modificar
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            try {
                mod.setNombre_Puesto(frm.txt_Puesto.getText());
                mod.setNombre_Puesto_Ingles(frm.txt_PuestoIngles.getText());
                mod.setDescripcion(frm.txt_descPuesto.getText());
                mod.setNivel(frm.txt_nivel.getText());
                mod.setCentro_de_Costo(Integer.parseInt(frm.txt_centrocosto.getText()));
                mod.setPropuesto_Trabajadores(Integer.parseInt(frm.txt_totaltbr.getText()));
                mod.setPropuesto_Turno(Integer.parseInt(frm.txt_totalturno_tbr.getText()));
                String area = frm.cb_AreaPuesto.getSelectedItem().toString();
                mod.setArea_idArea(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "Nombre_Area", area)));

                //Llamada a la funcion de modificar enviando los datos guardados en la instacia
                if (modC.modificar(mod, folio)) {
                    JOptionPane.showMessageDialog(null, "Puesto modificado");
                    DesignTabla.designPuestosArea(frmA, mod.getArea_idArea());
                    frm.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar puesto");
                }
            } catch (HeadlessException ex) {
                Logger.getLogger(CtrlEditarPuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Función del botón Cancelar
        if (e.getSource() == frm.btn_cancelar) {
            frm.dispose();
        }
    }

//Metodo para limpiar las casillas
    public void limpiar() {
        frm.txt_Puesto.setText(null);
    }
}
