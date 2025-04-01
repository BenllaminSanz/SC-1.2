//Controlador de la ventana Editar Trabajador
//Recibe la estructura grafica de Frame EditarTrabajador
/*Esta pantalla debe permitir al administrador modificar el aspecto general 
del trabajador, todas sus caractersiticas y relaciones con la empresa con 
respecto a la estructura marcada en la base de datos*/
package Controller;

import ContextController.ContextoEditarArea;
import Functions.ButtonFunctions;
import Model.Area;
import Querys.ConsultasArea;
import Subviews.IFrmEditarArea;
import Tables.DesignTabla;
import View.IFrmAreasPuestos;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class CtrlEditarArea implements ActionListener {

    private final Area mod = new Area();
    private final ConsultasArea modC = new ConsultasArea();
    private final IFrmEditarArea frm;
    private final IFrmAreasPuestos frmA;
    private final String texto;
    private final int folio;

    //Relacionamos el controlador con la vista
    public CtrlEditarArea(ContextoEditarArea contexto) {
        this.frm = contexto.ventanaEditarArea;
        this.frmA = contexto.ventanaAreasPuestos;
        this.texto = contexto.texto;
        this.folio = contexto.folio;
        this.frm.btn_guardar.addActionListener(this);
        this.frm.btn_cancelar.addActionListener(this);
    }

    //Funcion de inicio
    public void iniciar() {
        //Metodo para asignar texto al boton
        ButtonFunctions.TxtBtnArea(texto, folio, frm, mod, modC);
    }

    //Acciones de los botones 
    @Override
    public void actionPerformed(ActionEvent e) {
        //Función del botón Guardar
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Guardar")) {
            mod.setNombre_Area(frm.txt_Folio.getText());
            mod.setDescripcion(frm.txt_descripcion.getText());
            mod.setTipo_proceso(frm.cb_proceso.getSelectedIndex()+1);
            try {
                //Llamada a la funcion de modificar enviando los datos guardados en la instacia
                if (modC.registrar(mod)) {
                    JOptionPane.showMessageDialog(null, "Área agregada");
                    DesignTabla.designAreas(frmA);
                    DesignTabla.designPuestos(frmA);
                    frm.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar área");
                }
            } catch (HeadlessException ex) {
                System.err.println("Error al Guardar: " + ex);
            }
        }

        //Función del botón Modificar
        if (e.getSource() == frm.btn_guardar && frm.btn_guardar.getText().equals("Actualizar")) {
            //Capturamos los datos de la ventana en la instacia del objeto Trabajador
            mod.setNombre_Area(frm.txt_Folio.getText());
            mod.setDescripcion(frm.txt_descripcion.getText());
            mod.setTipo_proceso(frm.cb_proceso.getSelectedIndex()+1);
            //Llamada a la funcion de modificar enviando los datos guardados en la instacia
            if (modC.modificar(mod, folio)) {
                JOptionPane.showMessageDialog(null, "Área Modificada");
                DesignTabla.designAreas(frmA);
                DesignTabla.designPuestos(frmA);
                frm.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }

        //Función del botón Cancelar
        if (e.getSource() == frm.btn_cancelar) {
            frm.dispose();
        }
    }

//Metodo para limpiar las casillas
    public void limpiar() {
        frm.txt_Folio.setText(null);
    }
}
