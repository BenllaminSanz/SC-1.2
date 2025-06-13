package Controller;

import ContextController.ContextoLBU;
import Documents.GeneratorExcel_LBU;
import Documents.GeneratorPDF_Brigadas;
import Documents.GeneratorPDF_LBU;
import Functions.CargarDatosTask;
import Functions.QueryFunctions;
import Graphics.Graphics_LBU;
import Querys.Conexion;
import Model.LBU;
import Tables.DesignTabla;
import View.IFrmLBU;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CtrlLBUGeneral implements ActionListener {

    private final LBU mod = new LBU();
    private final IFrmLBU frm;
    public static PreparedStatement ps = null;
    public static ResultSet rs = null;
    private static final String Todos = "Todos...";

    //Relacionamos el controlador con la vista
    public CtrlLBUGeneral(ContextoLBU contexto) {
        this.frm = contexto.ventanaLBU;
        this.frm.btn_Consultar.addActionListener(this);
        this.frm.btn_RefreshTabla.addActionListener(this);
        this.frm.btn_Imprimir.addActionListener(this);
        this.frm.itemRptLBU_PDF.addActionListener(this);
        this.frm.itemRptLBU_Excel.addActionListener(this);
        this.frm.itemRptSup_PDF.addActionListener(this);
        this.frm.itemRptSup_Excel.addActionListener(this);
        this.frm.itemRptArea_Excel.addActionListener(this);
        this.frm.jMenuItem1.addActionListener(this);
        this.frm.jMenuItem2.addActionListener(this);
        this.frm.jMenuItem3.addActionListener(this);
        this.frm.jMenuItem4.addActionListener(this);
    }

    //Funcion de inicio
    public void iniciar() throws SQLException {

        //Llamado a los metodos de diseño de los paneles
        DesingTablas();
        //Funciones de Arranque
        setWindowProperties();
        Graphics_LBU.PieChartLBU(frm);
        Graphics_LBU.BarChartLBUTrabajadores(frm);
        Graphics_LBU.BarChartLBUCertificados(frm);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_Imprimir) {
            String area = frm.cb_area.getSelectedItem().toString();
            String puesto = frm.cb_puesto.getSelectedItem().toString();
            String turno = frm.cb_turno.getSelectedItem().toString();
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            try {
                if (area.equals("Todas las áreas...") && puesto.equals(Todos) && turno.equals("Todos...")) {
                    if (GeneratorPDF_LBU.LBUGeneral()) {
                        frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                } else if (puesto.equals("Todos...") && turno.equals("Todos...")) {
                    mod.setNombre_Area(area);
                    if (GeneratorPDF_LBU.LBUOperativoArea(mod)) {
                        frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                } else if (turno.equals("Todos...")) {
                    mod.setNombre_Area(area);
                    mod.setNombre_Puesto(puesto);
                    if (GeneratorPDF_LBU.LBUOperativoPuesto(mod)) {
                        frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                } else if (puesto.equals("Todos...")) {
                    mod.setNombre_Area(area);
                    mod.setNombre_Turno(turno);
                    if (GeneratorPDF_LBU.LBUOperativoTurno(mod)) {
                        frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                } else {
                    mod.setNombre_Area(area);
                    mod.setNombre_Puesto(puesto);
                    mod.setNombre_Turno(turno);
                    if (GeneratorPDF_LBU.LBUOperativoSelecto(mod)) {
                        frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE,
                        "Error para las funciones Generator_PDF: ", ex);
            }
        }

        if (e.getSource() == frm.btn_Consultar) {
            String area = frm.cb_area.getSelectedItem().toString();
            String puesto = frm.cb_puesto.getSelectedItem().toString();
            String turno = frm.cb_turno.getSelectedItem().toString();

            if (puesto.equals("Todos...") && turno.equals("Todos...")) {
                mod.setId_Area(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "Nombre_Area", area)));
                DesignTabla.designLBUB(frm, mod, 1);
            } else if (puesto.equals("Todos...")) {
                mod.setId_Area(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "Nombre_Area", area)));
                mod.setId_Turno(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "turno", "idturno", "nombre_turno", turno)));
                DesignTabla.designLBUB(frm, mod, 2);
            } else if (turno.equals("Todos...")) {
                mod.setId_Area(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "Nombre_Area", area)));
                mod.setId_Puesto(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "puesto", "idPuesto", "Nombre_Puesto", puesto)));
                DesignTabla.designLBUB(frm, mod, 3);
            } else {
                mod.setId_Area(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "Nombre_Area", area)));
                mod.setId_Turno(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "turno", "idturno", "nombre_turno", turno)));
                mod.setId_Puesto(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "puesto", "idPuesto", "Nombre_Puesto", puesto)));
                DesignTabla.designLBUB(frm, mod, 4);
            }
        }

        List<Object> validSourcesActualizarTabla = Arrays.asList(frm.btn_RefreshTabla);
        if (validSourcesActualizarTabla.contains(e.getSource())) {
            actualizar();
        }

        if (e.getSource() == frm.itemRptLBU_PDF) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_LBU.LBUGeneral();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.itemRptLBU_Excel) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorExcel_LBU.LBUGeneral();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.itemRptSup_PDF) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_LBU.LBUResumenSupervisor();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.itemRptSup_Excel) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorExcel_LBU.LBUResumenSupervisor();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.itemRptArea_Excel) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorExcel_LBU.LBUResumenArea();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem1) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_Brigadas.listaEmergencia();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem2) {
            JComboBox<String> comboBox1 = new JComboBox<>();
            JComboBox<String> comboBox2 = new JComboBox<>();

            JLabel label1 = new JLabel("Area:");
            JLabel label2 = new JLabel("Turno:");

            QueryFunctions.LlenarComboBox("area", "nombre_Area", comboBox1);
            comboBox1.addItem("ADMINISTRACIÓN");
            comboBox2.addItem("Todos...");
            QueryFunctions.LlenarComboBox("turno", "nombre_Turno", comboBox2);

            Object[] components = {label1, comboBox1, label2, comboBox2};

            int option = JOptionPane.showConfirmDialog(null, components, "Reporte Certificados", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                String areaSeleccionada = (String) comboBox1.getSelectedItem();
                String turnoSeleccionado = (String) comboBox2.getSelectedItem();
                if (turnoSeleccionado.equals("Todos...")) {
                    GeneratorPDF_Brigadas.Lista_EmergenciaArea(areaSeleccionada);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    GeneratorPDF_Brigadas.Lista_EmergenciaEspecifico(areaSeleccionada, turnoSeleccionado);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }
        }

        if (e.getSource() == frm.jMenuItem3) {
            JComboBox<String> comboBox1 = new JComboBox<>();

            JLabel label1 = new JLabel("Turno:");

            QueryFunctions.LlenarComboBox("turno", "nombre_Turno", comboBox1);

            Object[] components = {label1, comboBox1};

            int option = JOptionPane.showConfirmDialog(null, components, "Lista de Emergencia por Turnos", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                String turnoSeleccionado = (String) comboBox1.getSelectedItem();
                GeneratorPDF_Brigadas.Lista_EmergenciaPorTurno(turnoSeleccionado);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }
        }
        
        if (e.getSource() == frm.jMenuItem4) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorExcel_LBU.LBUResumenAreas();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    private void CargarLBU() {
        CargarDatosTask task = new CargarDatosTask(frm, mod);
        task.addPropertyChangeListener((evt) -> {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                frm.jProgressBar.setValue(progress);
            }
        });
        // Establecer el valor máximo de la barra de progreso
        frm.jProgressBar.setMaximum(100);
        task.execute();
        frm.jProgressBar.setVisible(true);
    }

    public void actualizar() {
        frm.cb_area.setSelectedItem("Todas las áreas...");
        frm.cb_puesto.removeAllItems();
        frm.cb_puesto.addItem("Todos...");
        QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto);
        frm.cb_turno.setSelectedItem("Todos...");
        DesignTabla.designLBU(frm, mod);
    }

    public void CargarLBU_Total() {
        Conexion con = new Conexion();
        Connection conn = con.getConnection();

        String sql = "SELECT * FROM view_lbu_total";

        try {
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {
                frm.lbPropuesto.setText(String.valueOf(rs.getInt("Total_P")));
                if (rs.getInt("Total_D") == 0) {
                    frm.lbDiferencia.setText(String.valueOf(rs.getInt("Total_D")));
                    frm.lbDiferencia.setFont(new Font(null, Font.BOLD, 12));
                } else if (rs.getInt("Total_D") < 0) {
                    frm.lbDiferencia.setText(String.valueOf(rs.getInt("Total_D")));
                    frm.lbDiferencia.setFont(new Font(null, Font.BOLD, 12));
                    frm.lbDiferencia.setForeground(Color.red);
                } else {
                    frm.lbDiferencia.setText(String.valueOf(rs.getInt("Total_D")));
                    frm.lbDiferencia.setFont(new Font(null, Font.BOLD, 12));
                    frm.lbDiferencia.setForeground(new Color(0, 153, 0));
                }
                frm.lbPlantilla.setText(String.valueOf(rs.getInt("Total_Pl")));
                frm.lbA.setText(String.valueOf(rs.getInt("A")));
                frm.lbB.setText(String.valueOf(rs.getInt("B")));
                frm.lbC.setText(String.valueOf(rs.getInt("C")));
                frm.lbD.setText(String.valueOf(rs.getInt("D")));
                frm.lbLV.setText(String.valueOf(rs.getInt("LV")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CtrlLBUGeneral.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void DesingTablas() {
        DesignTabla.designLBU(frm, mod);
        DesignTabla.designSupervisor(frm);
        CargarLBU();
        CargarLBU_Total();
    }

    private void setWindowProperties() {
        frm.setTitle("LBU Operativo");
        frm.cb_area.addItem("Todas las áreas...");
        frm.cb_puesto.addItem("Todos...");
        frm.cb_turno.addItem("Todos...");
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_area);
        QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto);
        QueryFunctions.LlenarComboBox("turno", "Nombre_Turno", frm.cb_turno);
        frm.lbName.setHorizontalAlignment(0);
        frm.lbPropuesto.setHorizontalAlignment(0);
        frm.lbDiferencia.setHorizontalAlignment(0);
        frm.lbPlantilla.setHorizontalAlignment(0);
        frm.lbA.setHorizontalAlignment(0);
        frm.lbB.setHorizontalAlignment(0);
        frm.lbC.setHorizontalAlignment(0);
        frm.lbD.setHorizontalAlignment(0);
        frm.lbLV.setHorizontalAlignment(0);
    }
}
