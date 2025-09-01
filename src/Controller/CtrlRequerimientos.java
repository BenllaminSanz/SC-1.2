package Controller;

import ContextController.ContextoEditarRequerimientos;
import Functions.ArchivoHelper;
import Model.HabilidadEvaluada;
import Model.HabilidadesCurso;
import Model.RequerimientosCurso;
import Model.RequerimientosCursoAsistente;
import Querys.ConsultasHabilidadesCurso;
import Querys.ConsultasRequerimientosCurso;
import View.FrmAdministrador;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CtrlRequerimientos {

    private final FrmAdministrador frmA;
    private final RequerimientosCursoAsistente mod;
    private final ConsultasRequerimientosCurso modC = new ConsultasRequerimientosCurso();
    private final List<RequerimientosCurso> requerimientos;
    private final int curso;

    // Mapa único para filas ILUO
    private final Map<HabilidadesCurso, FilaILUO> mapFilasILUO = new HashMap<>();

    public CtrlRequerimientos(ContextoEditarRequerimientos contexto) {
        this.frmA = contexto.ventanaAdministrador;
        this.mod = contexto.modeloRequerimientosAsistente;
        this.requerimientos = contexto.listaRequerimientos;
        this.curso = contexto.idCurso;
    }

    public void iniciar() {
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelGeneral.add(crearPanelRequerimientos());
        panelGeneral.add(Box.createVerticalStrut(20));
        panelGeneral.add(crearPanelILUO());

        JFrame ventana = new JFrame("Gestión de Requerimientos e ILUO");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null);
        ventana.setContentPane(new JScrollPane(panelGeneral));
        ventana.setVisible(true);
    }

    // ---------------- PANEL REQUERIMIENTOS ----------------
    private JPanel crearPanelRequerimientos() {
        JPanel panelRequerimientos = new JPanel();
        panelRequerimientos.setLayout(new BoxLayout(panelRequerimientos, BoxLayout.Y_AXIS));
        panelRequerimientos.setBorder(BorderFactory.createTitledBorder("Requerimientos"));

        for (RequerimientosCurso req : requerimientos) {
            JCheckBox cb = new JCheckBox();
            JLabel lbl = new JLabel(req.getNombre_requerimiento());
            JTextField txtFecha = new JTextField(10);
            JButton btnConsulta = new JButton("Consultar");

            JPanel panelReq = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            panelReq.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            cb.addActionListener(e -> {
                if (cb.isSelected()) {
                    mostrarDialogoRegistro(req, panelReq, txtFecha, btnConsulta);
                } else {
                    mod.setIdRequerimiento(req.getIdRequerimiento());
                    if (modC.eliminarRequerimiento(mod)) {
                        panelReq.remove(txtFecha);
                        panelReq.remove(btnConsulta);
                        panelReq.revalidate();
                        panelReq.repaint();
                    }
                }
            });

            if (modC.estadoRequerimiento(req.getIdRequerimiento(), mod)) {
                cb.setSelected(true);
                txtFecha.setEditable(false);
                txtFecha.setText(mod.getFecha_entrega());
                btnConsulta.addActionListener(ev -> abrirArchivo(mod.getRuta_archivo()));

                panelReq.add(cb);
                panelReq.add(lbl);
                panelReq.add(txtFecha);
                panelReq.add(btnConsulta);
            } else {
                panelReq.add(cb);
                panelReq.add(lbl);
            }

            panelRequerimientos.add(panelReq);
        }

        return panelRequerimientos;
    }

    // ---------------- PANEL ILUO ----------------
    private JPanel crearPanelILUO() {
        JPanel panelILUO = new JPanel();
        panelILUO.setLayout(new BoxLayout(panelILUO, BoxLayout.Y_AXIS));
        panelILUO.setBorder(BorderFactory.createTitledBorder("Gestión ILUO"));

        // Cabecera
        JPanel header = new JPanel(new GridLayout(1, 5));
        header.add(new JLabel("Habilidad"));
        header.add(new JLabel("Estado ILUO"));
        header.add(new JLabel("Última Fecha de Evaluación"));
        header.add(new JLabel("Observaciones"));
        header.add(new JLabel("Historial Habilidad"));
        panelILUO.add(header);
        panelILUO.add(Box.createVerticalStrut(10));

        List<HabilidadesCurso> listaHabilidades = new ConsultasHabilidadesCurso().obtenerHabilidadesPorCurso(curso);

        for (HabilidadesCurso habilidad : listaHabilidades) {
            JPanel fila = crearFilaHabilidad(habilidad);
            panelILUO.add(fila);
        }

        cargarEvaluacionesILUO();

        panelILUO.add(Box.createVerticalStrut(10));

        JButton btnGuardarILUO = new JButton("Guardar ILUO");
        btnGuardarILUO.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGuardarILUO.addActionListener(e -> guardarILUO());

        panelILUO.add(btnGuardarILUO);

        return panelILUO;
    }

    private JPanel crearFilaHabilidad(HabilidadesCurso habilidad) {
        JPanel fila = new JPanel(new GridLayout(1, 5, 10, 0));
        fila.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel lblNombre = new JLabel(habilidad.getNombre_habilidad());
        JComboBox<String> comboNivel = new JComboBox<>(new String[]{"I", "L", "U", "O"});
        JLabel lblFecha = new JLabel("");
        JTextField txtObs = new JTextField();
        JButton btnHistorial = crearBotonHistorial(habilidad);

        fila.add(lblNombre);
        fila.add(comboNivel);
        fila.add(lblFecha);
        fila.add(txtObs);
        fila.add(btnHistorial);

        mapFilasILUO.put(habilidad, new FilaILUO(comboNivel, lblFecha, txtObs, btnHistorial));

        return fila;
    }

    private JButton crearBotonHistorial(HabilidadesCurso habilidad) {
        JButton btn = new JButton("Historial");
        btn.addActionListener(e -> mostrarHistorial(habilidad, mod.getIdAsistente(), mod.getIdHistorial()));
        return btn;
    }

    private void cargarEvaluacionesILUO() {
        List<HabilidadEvaluada> habilidadesEvaluadas = new ConsultasHabilidadesCurso()
                .obtenerHabilidadesConEvaluacion(curso, mod.getIdAsistente(), mod.getIdHistorial());

        for (HabilidadEvaluada eval : habilidadesEvaluadas) {
            for (HabilidadesCurso habilidadCurso : mapFilasILUO.keySet()) {
                if (habilidadCurso.getIdHabilidad() == eval.getIdHabilidad()) {
                    FilaILUO fila = mapFilasILUO.get(habilidadCurso);

                    if (eval.getNivelAlcanzado() != null)
                        fila.comboNivel.setSelectedItem(eval.getNivelAlcanzado());
                    else
                        fila.comboNivel.setSelectedItem(null);

                    fila.lblFecha.setText(eval.getFechaEvaluacion() != null ? eval.getFechaEvaluacion().toString() : "");
                    fila.txtObservacion.setText(eval.getObservaciones() != null ? eval.getObservaciones() : "");

                    break;
                }
            }
        }
    }

    private void guardarILUO() {
        java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
        ConsultasHabilidadesCurso consulta = new ConsultasHabilidadesCurso();

        int errores = 0;
        for (Map.Entry<HabilidadesCurso, FilaILUO> entry : mapFilasILUO.entrySet()) {
            HabilidadesCurso hab = entry.getKey();
            FilaILUO fila = entry.getValue();

            boolean ok = consulta.guardarEvaluacion(
                    mod.getIdAsistente(),
                    hab.getIdHabilidad(),
                    mod.getIdHistorial(),
                    (String) fila.comboNivel.getSelectedItem(),
                    fechaActual,
                    fila.txtObservacion.getText()
            );

            if (!ok) errores++;
        }

        JOptionPane.showMessageDialog(null,
                errores == 0 ? "Todos los niveles ILUO fueron guardados correctamente."
                        : "Hubo errores al guardar algunas evaluaciones ILUO.");
    }

    // ---------------- REQUERIMIENTOS ----------------
    private void mostrarDialogoRegistro(RequerimientosCurso requerimiento, JPanel panelRequerimiento,
                                        JTextField fechaEntregaTextField, JButton bt_consultar) {

        JPanel panelCargar = new JPanel(new GridLayout(0, 1));
        JDateChooser dateChooser = new JDateChooser();
        JTextField txt_archivo = new JTextField();
        JButton bt_Archivo = new JButton("Agregar");

        panelCargar.add(new JLabel("Seleccione la fecha de Registro:"));
        panelCargar.add(dateChooser);
        panelCargar.add(new JLabel("Seleccione el archivo:"));
        panelCargar.add(txt_archivo);
        panelCargar.add(bt_Archivo);

        bt_Archivo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                mod.setRuta_archivo(selectedFile.getAbsolutePath());
                mod.setNombre_archivo(selectedFile.getName());
                txt_archivo.setText(selectedFile.getName());
            }
        });

        int result = JOptionPane.showConfirmDialog(null, panelCargar,
                "Registro del requerimiento:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (dateChooser.getDate() == null || txt_archivo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha y un archivo",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());
            mod.setIdRequerimiento(requerimiento.getIdRequerimiento());

            if (modC.agregarRequerimiento(mod, fecha)) {
//                generarExpediente(mod);
                if (modC.estadoRequerimiento(requerimiento.getIdRequerimiento(), mod)) {
                    fechaEntregaTextField.setEditable(false);
                    fechaEntregaTextField.setText(mod.getFecha_entrega());
                    bt_consultar.addActionListener(ev -> abrirArchivo(mod.getRuta_archivo()));
                    panelRequerimiento.add(fechaEntregaTextField);
                    panelRequerimiento.add(bt_consultar);
                    panelRequerimiento.revalidate();
                    panelRequerimiento.repaint();
                }
            }
        }
    }

    private void abrirArchivo(String rutaArchivo) {
        ArchivoHelper.abrirArchivo(rutaArchivo);
    }

    private void generarExpediente(RequerimientosCursoAsistente mod) {
        ArchivoHelper.generarExpediente(mod);
    }

    // ---------------- HISTORIAL ----------------
    private void mostrarHistorial(HabilidadesCurso habilidad, int idAsistente, int idHistorial) {
        List<HabilidadEvaluada> historial = new ConsultasHabilidadesCurso()
                .obtenerHistorialEvaluaciones(idAsistente, idHistorial, habilidad.getIdHabilidad());

        String[] columnas = {"Semana", "Última Fecha de evaluación", "Nivel ILUO", "Observaciones"};
        Object[][] datos = new Object[historial.size()][4];

        for (int i = 0; i < historial.size(); i++) {
            HabilidadEvaluada eval = historial.get(i);
            datos[i][0] = eval.getSemanaCurso();
            datos[i][1] = eval.getFechaEvaluacion();
            datos[i][2] = eval.getNivelAlcanzado();
            datos[i][3] = eval.getObservaciones();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JOptionPane.showMessageDialog(null, scrollPane,
                "Historial de evaluaciones - " + habilidad.getNombre_habilidad(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- CLASE AUXILIAR PARA FILA ILUO ----------------
    private static class FilaILUO {
        JComboBox<String> comboNivel;
        JLabel lblFecha;
        JTextField txtObservacion;
        JButton btnHistorial;

        public FilaILUO(JComboBox<String> comboNivel, JLabel lblFecha, JTextField txtObservacion, JButton btnHistorial) {
            this.comboNivel = comboNivel;
            this.lblFecha = lblFecha;
            this.txtObservacion = txtObservacion;
            this.btnHistorial = btnHistorial;
        }
    }
}
