//Contexto de las clases que se instancian para el controlado la ventana EditarArea
package ContextController;

import Subviews.IFrmEditarArea;
import View.IFrmAreasPuestos;

public class ContextoEditarArea {

    public IFrmEditarArea ventanaEditarArea;
    public IFrmAreasPuestos ventanaAreasPuestos;
    public String texto;
    public int folio;

    public ContextoEditarArea(String textoBotón, int folioTrabajador, IFrmAreasPuestos frm) {
        this.ventanaEditarArea = new IFrmEditarArea();
        this.ventanaAreasPuestos = frm;
        this.texto = textoBotón;
        this.folio = folioTrabajador;
    }
}
