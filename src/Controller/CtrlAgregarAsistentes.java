package Controller;

import ContextController.ContextoAgregarAsistentes;
import Functions.QueryFunctions;
import Functions.TextPrompt;
import Model.AsistenteCurso;
import Querys.ConsultasAsistentesCurso;
import Subviews.IFrmAgregarAsistente;
import Tables.DesignTabla;
import View.IFrmCapacitacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CtrlAgregarAsistentes implements ActionListener, KeyListener {

    private final AsistenteCurso mod = new AsistenteCurso();
    private final ConsultasAsistentesCurso modC = new ConsultasAsistentesCurso();
    private final IFrmAgregarAsistente frm;
    private final IFrmCapacitacion frmA;
    private final String idCurso;
    private final String idHistorial;

    public CtrlAgregarAsistentes(ContextoAgregarAsistentes contexto) {
        this.frm = contexto.ventanaAgregarAsistente;
        this.frmA = contexto.ventanaCapacitaciones;
        this.idCurso = contexto.idCurso;
        this.idHistorial = contexto.idHistorial;
        this.frm.txt_buscar_Tbr.addKeyListener(this);
        this.frm.txt_buscar_Tbr1.addKeyListener(this);
        this.frm.txt_buscar_Tbr2.addKeyListener(this);
        this.frm.txt_buscar_Tbr4.addKeyListener(this);
        this.frm.item_AgregarTrabajadorCapacitacion.addActionListener(this);
        this.frm.item_AgregarTrabajadorCapacitacion1.addActionListener(this);
        this.frm.item_AgregarAdministrativo.addActionListener(this);
        this.frm.btn_agregar_otros.addActionListener(this);
        this.frm.btn_RefreshTabla.addActionListener(this);
        this.frm.btn_RefreshTabla1.addActionListener(this);
        this.frm.btn_Consultar.addActionListener(this);
        this.frm.jButton1.addActionListener(this);
        this.frm.item_AgregarBrigadista.addActionListener(this);
        this.frm.btn_RefreshTabla4.addActionListener(this);

    }

    void iniciar() {
        DesignTabla.designAsistentesTrabajadores(frm, idHistorial);
        DesignTabla.designAsistentesSupervisores(frm, idHistorial);
        DesignTabla.designAsistentesAdministrativos(frm, idHistorial);
        DesignTabla.designAsistentesBrigadistas(frm, idHistorial);
        frm.cb_area.addItem("Todas las áreas...");
        frm.cb_puesto.addItem("Todos...");
        frm.cb_turno.addItem("Todos...");
        frm.jComboBox1.addItem("Todas las Brigadas...");
        frm.jComboBox2.addItem("Todos...");
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_area);
        QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto);
        QueryFunctions.LlenarComboBox("turno", "Nombre_Turno", frm.cb_turno);
        QueryFunctions.LlenarComboBox("brigadas", "nombre_Brigada", frm.jComboBox1);
        QueryFunctions.LlenarComboBox("turno", "Nombre_Turno", frm.jComboBox2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.item_AgregarTrabajadorCapacitacion) {
            int[] selectedRows = frm.jTable_Trabajadores.getSelectedRows();
            List<AsistenteCurso> rows = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                AsistenteCurso ac = new AsistenteCurso();
                ac.setId(frm.jTable_Trabajadores.getValueAt(selectedRows[i], 0).toString());
                ac.setNombre(frm.jTable_Trabajadores.getValueAt(selectedRows[i], 1).toString());
                ac.setPuesto("Trabajador");
                rows.add(ac);
            }

            if (modC.registrarTrabajador(idCurso, idHistorial, rows)) {
                JOptionPane.showMessageDialog(null, "Trabajador(es) agregados al curso");
                int Curso = Integer.parseInt(frmA.JTable_HistorialCurso.getValueAt(
                        frmA.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                DesignTabla.designAsistentesCurso(frmA, Curso);
                DesignTabla.designAsistentesTrabajadores(frm, idHistorial);
                ConcentradoCurso(frmA, Integer.parseInt(idHistorial));
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }

        if (e.getSource() == frm.item_AgregarTrabajadorCapacitacion1) {
            int[] selectedRows = frm.jTable_Trabajadores1.getSelectedRows();
            List<AsistenteCurso> rows = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                AsistenteCurso ac = new AsistenteCurso();
                String nombreSupervisor = frm.jTable_Trabajadores1.getValueAt(selectedRows[i], 0).toString();
                String idSupervisor = QueryFunctions.CapturaCondicionalSimple(
                        "supervisor", "idSupervisor", "nombre_supervisor", nombreSupervisor);
                ac.setId(idSupervisor);
                ac.setNombre(frm.jTable_Trabajadores1.getValueAt(selectedRows[i], 0).toString());
                ac.setPuesto("Supervisor");
                rows.add(ac);
            }

            if (modC.registrarSupervisor(idHistorial, rows)) {
                JOptionPane.showMessageDialog(null, "Supervisor(es) agregados al curso");
                int Curso = Integer.parseInt(frmA.JTable_HistorialCurso.getValueAt(
                        frmA.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                DesignTabla.designAsistentesCurso(frmA, Curso);
                DesignTabla.designAsistentesSupervisores(frm, idHistorial);
                ConcentradoCurso(frmA, Integer.parseInt(idHistorial));
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }

        if (e.getSource() == frm.item_AgregarAdministrativo) {
            int[] selectedRows = frm.jTable_Administrativos.getSelectedRows();
            List<AsistenteCurso> rows = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                AsistenteCurso ac = new AsistenteCurso();
                ac.setId(frm.jTable_Administrativos.getValueAt(selectedRows[i], 0).toString());
                ac.setNombre(frm.jTable_Administrativos.getValueAt(selectedRows[i], 1).toString());
                ac.setPuesto("Administrativo");
                rows.add(ac);
            }

            if (modC.registrarAdministrativo(idCurso, idHistorial, rows)) {
                JOptionPane.showMessageDialog(null, "Administrativo(s) agregados al curso");
                int Curso = Integer.parseInt(frmA.JTable_HistorialCurso.getValueAt(
                        frmA.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                DesignTabla.designAsistentesCurso(frmA, Curso);
                DesignTabla.designAsistentesAdministrativos(frm, idHistorial);
                ConcentradoCurso(frmA, Integer.parseInt(idHistorial));
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }

        if (e.getSource() == frm.item_AgregarBrigadista) {
            int[] selectedRows = frm.jTable_Administrativos1.getSelectedRows();
            List<AsistenteCurso> rows = new ArrayList<>();

            for (int i = 0; i < selectedRows.length; i++) {
                AsistenteCurso ac = new AsistenteCurso();
                ac.setId(frm.jTable_Administrativos1.getValueAt(selectedRows[i], 0).toString());
                ac.setNombre(frm.jTable_Administrativos1.getValueAt(selectedRows[i], 1).toString());
                ac.setPuesto(frm.jTable_Administrativos1.getValueAt(selectedRows[i], 2).toString());
                rows.add(ac);
            }

            if (modC.registrarBrigadista(idCurso, idHistorial, rows)) {
                JOptionPane.showMessageDialog(null, "Brigadista(s) agregados al curso");
                int Curso = Integer.parseInt(frmA.JTable_HistorialCurso.getValueAt(
                        frmA.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                DesignTabla.designAsistentesCurso(frmA, Curso);
                DesignTabla.designAsistentesBrigadistas(frm, idHistorial);
                ConcentradoCurso(frmA, Integer.parseInt(idHistorial));
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
        }

        if (e.getSource() == frm.btn_agregar_otros) {
            mod.setId(frm.txt_id.getText());
            mod.setNombre(frm.txt_nombre.getText().toUpperCase());
            mod.setPuesto(frm.txt_puesto.getText());
            if (mod.getId().isEmpty()) {
                if (modC.registrarOtros(idHistorial, mod)) {
                    JOptionPane.showMessageDialog(null, "Asistentes agregado al curso");
                    int Curso = Integer.parseInt(frmA.JTable_HistorialCurso.getValueAt(
                            frmA.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                    DesignTabla.designAsistentesCurso(frmA, Curso);
                    ConcentradoCurso(frmA, Integer.parseInt(idHistorial));
                }
            } else {
                if (modC.registrarOtrosNomina(idHistorial, mod)) {
                    JOptionPane.showMessageDialog(null, "Asistentes agregado al curso");
                    int Curso = Integer.parseInt(frmA.JTable_HistorialCurso.getValueAt(
                            frmA.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                    DesignTabla.designAsistentesCurso(frmA, Curso);
                    ConcentradoCurso(frmA, Integer.parseInt(idHistorial));
                }
            }
        }

        if (e.getSource() == frm.btn_RefreshTabla) {
            frm.cb_area.setSelectedItem("Todas las áreas...");
            frm.cb_puesto.removeAllItems();
            frm.cb_puesto.addItem("Todos...");
            QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto);
            frm.cb_turno.setSelectedItem("Todos...");
            DesignTabla.designAsistentesTrabajadores(frm, idHistorial);
        }

        if (e.getSource() == frm.btn_RefreshTabla1) {
            DesignTabla.designAsistentesSupervisores(frm, idHistorial);
        }

        if (e.getSource() == frm.btn_Consultar) {
            String area = frm.cb_area.getSelectedItem().toString();
            String puesto = frm.cb_puesto.getSelectedItem().toString();
            String turno = frm.cb_turno.getSelectedItem().toString();

            if (puesto.equals("Todos...") && turno.equals("Todos...")) {
                mod.setArea(area);
                DesignTabla.designAsistentesTrabajadoresF(frm, idHistorial, mod, 1);
            } else if (puesto.equals("Todos...")) {
                mod.setArea(area);
                mod.setTurno(turno);
                DesignTabla.designAsistentesTrabajadoresF(frm, idHistorial, mod, 2);
            } else if (turno.equals("Todos...")) {
                mod.setArea(area);
                mod.setPuesto(puesto);
                DesignTabla.designAsistentesTrabajadoresF(frm, idHistorial, mod, 3);
            } else {
                mod.setArea(area);
                mod.setPuesto(puesto);
                mod.setTurno(turno);
                DesignTabla.designAsistentesTrabajadoresF(frm, idHistorial, mod, 4);
            }
        }

        if (e.getSource() == frm.jButton1) {
            String brigada = frm.jComboBox1.getSelectedItem().toString();
            String turno = frm.jComboBox2.getSelectedItem().toString();

            if (brigada.equals("Todas las Brigadas...") && turno.equals("Todos...")) {
                DesignTabla.designAsistentesBrigadistas(frm, idHistorial);
            } else if (!brigada.equals("Todas las Brigadas...") && turno.equals("Todos...")) {
                mod.setArea(brigada);
                DesignTabla.designAsistentesBrigadistasF(frm, idHistorial, mod, 1);
            } else if (!turno.equals("Todos...") && brigada.equals("Todas las Brigadas...")) {
                mod.setTurno(turno);
                DesignTabla.designAsistentesBrigadistasF(frm, idHistorial, mod, 2);
            } else {
                mod.setArea(brigada);
                mod.setTurno(turno);
                DesignTabla.designAsistentesBrigadistasF(frm, idHistorial, mod, 3);
            }

        }

        if (e.getSource() == frm.btn_RefreshTabla4) {
            DesignTabla.designAsistentesBrigadistas(frm, idHistorial);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == frm.txt_buscar_Tbr) {
            String texto = frm.txt_buscar_Tbr.getText();
            if (texto.equals("")) {
                DesignTabla.designAsistentesTrabajadores(frm, idHistorial);
            } else {
                DesignTabla.designAsistentesTrabajadoresB(frm, idHistorial, texto);
            }
        }

        if (e.getSource() == frm.txt_buscar_Tbr1) {
            String texto = frm.txt_buscar_Tbr1.getText();
            if (texto.equals("")) {
                DesignTabla.designAsistentesSupervisores(frm, idHistorial);
            } else {
                DesignTabla.designAsistentesSupervisoresB(frm, idHistorial, texto);
            }
        }

        if (e.getSource() == frm.txt_buscar_Tbr2) {
            String texto = frm.txt_buscar_Tbr2.getText();
            if (texto.equals("")) {
                DesignTabla.designAsistentesAdministrativos(frm, idHistorial);
            } else {
                DesignTabla.designAsistentesAdministrativosB(frm, idHistorial, texto);
            }
        }

        if (e.getSource() == frm.txt_buscar_Tbr4) {
            String texto = frm.txt_buscar_Tbr4.getText();
            if (texto.equals("")) {
                DesignTabla.designAsistentesBrigadistas(frm, idHistorial);
            } else {
                DesignTabla.designAsistentesBrigadistasB(frm, idHistorial, texto);
            }
        }
    }

    private void setPlaceholderText() {
        TextPrompt placeholder1 = new TextPrompt("Folio/Nombre", frm.txt_buscar_Tbr);
        TextPrompt placeholder2 = new TextPrompt("Nombre", frm.txt_buscar_Tbr1);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    public void ConcentradoCurso(IFrmCapacitacion frm, int Curso) {
        String cantidadAsistentesEsperados = "Asistencia Esperada: %s";
        String cantidadAsistentesReales = "Asistencia Actual: %s";
        String cantidadTiempoCurso = "Duración del Curso: %s";
        String cantidadTiempoHombre = "Horas Hombre: %s";

        String consultaAsitentesEsperados = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "asistentes_esperados",
                "idHistorial_Curso", String.valueOf(Curso));
        String consultaAsitentesReales = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "num_asistentes",
                "idHistorial_Curso", String.valueOf(Curso));
        String consultaTiempoCursoHoras = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "TIME_FORMAT(SEC_TO_TIME(SUM(duracion_curso) * 60), '%H:%i')",
                "idHistorial_Curso", String.valueOf(Curso));
        String consultaTiempoHombreHoras = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "TIME_FORMAT(SEC_TO_TIME(SUM(horas_asistentes) * 60), '%H:%i')",
                "idHistorial_Curso", String.valueOf(Curso));

        frm.txt_esperado.setText(String.format(cantidadAsistentesEsperados, consultaAsitentesEsperados));
        frm.txt_asistencia.setText(String.format(cantidadAsistentesReales, consultaAsitentesReales));
        frm.txt_esperado1.setText(String.format(cantidadTiempoCurso, consultaTiempoCursoHoras));
        frm.txt_asistencia1.setText(String.format(cantidadTiempoHombre, consultaTiempoHombreHoras));
    }
}
