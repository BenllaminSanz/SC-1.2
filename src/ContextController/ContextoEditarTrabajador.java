//Contexto de las clases que se instancian para el controlado la ventana EditarTrabajador
package ContextController;

import Subviews.IFrmEditarTrabajador;

public class ContextoEditarTrabajador {
    
    public IFrmEditarTrabajador ventanaEditarTrabajador;
    public String texto;
    public String folio;

    public ContextoEditarTrabajador(String textoBotón, String folioTrabajador) {
        this.ventanaEditarTrabajador = new IFrmEditarTrabajador();
        this.texto = textoBotón;
        this.folio = folioTrabajador;
    }
}
