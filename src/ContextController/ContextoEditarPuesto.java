//Contexto de las clases que se instancian para el controlado la ventana EditarPuesto
package ContextController;

import Subviews.IFrmEditarPuesto;
import View.IFrmAreasPuestos;

public class ContextoEditarPuesto {

    public IFrmEditarPuesto ventanaEditarPuesto;
    public IFrmAreasPuestos ventanaAreaPuesto;
    public String texto;
    public int folio;

    public ContextoEditarPuesto(String textoBoton, int folioTrabajador, IFrmAreasPuestos frm) {
        this.ventanaEditarPuesto = new IFrmEditarPuesto();
        this.ventanaAreaPuesto = frm;
        this.texto = textoBoton;
        this.folio = folioTrabajador;
    }
}
