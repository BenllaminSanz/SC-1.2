package Functions;

import Model.LBU;
import Model.Puesto;
import Subviews.MenuItem;
import Subviews.SubMenuItem;
import Tables.CargarTabla;
import View.IFrmLBU;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

public class CargarDatosTask extends SwingWorker<Void, MenuItem> {

    private final IFrmLBU frm;
    private final LBU mod;

    public CargarDatosTask(IFrmLBU frm, LBU mod) {
        this.frm = frm;
        this.mod = mod;
    }

    @Override
    protected Void doInBackground() throws Exception {
        List<LBU> lbu = CargarTabla.cargarTablaLBUResumen();
        int progress = 0;
        int total = lbu.size();
        for (LBU l : lbu) {
            mod.setId_Supervisor(l.getId_Supervisor());
            List<Puesto> lbuP = CargarTabla.cargarTablaPuestoSupervisor(mod);
            List<SubMenuItem> submenus = new ArrayList<>();
            for (Puesto p : lbuP) {
                submenus.add(createSubMenuItem(p, l.getId_Supervisor()));
            }
            
            SubMenuItem[] submenuArray = submenus.toArray(new SubMenuItem[0]);

            MenuItem menu = new MenuItem(l.getNombre_Supervisor(), 
                    l.getSupervisor_Propuesto(), 
                    l.getSupervisor_Diferencia(),
                    l.getSupervisor_Plantilla(), 
                    l.getTurnoA(), l.getTurnoB(), l.getTurnoC(),
                    l.getTurnoD(), l.getTurnoLV(), null, submenuArray);
            publish(menu);
            int percentComplete = (int) ((double) progress / total * 100);
            setProgress(percentComplete);
            progress++;
        }
        return null;
    }

    protected void addMenu(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            frm.jPanel1.add(menu[i]);
            ArrayList<SubMenuItem> subMenu = menu[i].getSubMenu();
            for (SubMenuItem m : subMenu) {
                addSubMenu(m);
            }
        }
        frm.jPanel1.revalidate();
    }

    protected void addSubMenu(SubMenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            frm.jPanel1.add(menu[i]);
        }
        frm.jPanel1.revalidate();
    }

    protected SubMenuItem createSubMenuItem(Puesto puesto, int supervisorId) {
        return new SubMenuItem(puesto.getNombre_Puesto(),
                puesto.getNombre_Puesto_Ingles(),
                puesto.getPropuesto_Turno(),
                puesto.getDiferencia_Trabajadores(),
                puesto.getPlantilla_Trabajadores(),
                puesto.getTurnoA(), puesto.getTurnoB(),
                puesto.getTurnoC(), puesto.getTurnoD(),
                puesto.getTurnoLV(), supervisorId,
                puesto.getIdPuesto(), null);
    }

    @Override
    protected void process(List<MenuItem> chunks) {
        for (MenuItem menu : chunks) {
            addMenu(menu);
        }
    }

    @Override
    protected void done() {
        frm.jProgressBar.setVisible(false);
        frm.repaint();
    }
}
