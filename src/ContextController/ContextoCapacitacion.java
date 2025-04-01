package ContextController;

import View.FrmAdministrador;
import View.IFrmCapacitacion;

public class ContextoCapacitacion {
    public FrmAdministrador ventanaAdministrador;
    public IFrmCapacitacion ventanaCapacitacion;

    public ContextoCapacitacion(FrmAdministrador frame) {
        this.ventanaCapacitacion = new IFrmCapacitacion();
        this.ventanaAdministrador = frame;
    }
}
