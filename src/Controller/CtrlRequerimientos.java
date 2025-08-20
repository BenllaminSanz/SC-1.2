package Controller;

import ContextController.ContextoEditarRequerimientos;
import Functions.ArchivoHelper;
import Functions.ConsultaHelper;
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
import java.awt.Dimension;
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

// Archivo principal: CtrlRequerimientos.java
public class CtrlRequerimientos {

    private final FrmAdministrador frmA;
    private final RequerimientosCursoAsistente mod;
    private final ConsultasRequerimientosCurso modC = new ConsultasRequerimientosCurso();
    private final List<RequerimientosCurso> requerimientos;
    private final int curso;

    public CtrlRequerimientos(ContextoEditarRequerimientos contexto) {
        this.frmA = contexto.ventanaAdministrador;
        this.mod = contexto.modeloRequerimientosAsistente;
        this.requerimientos = contexto.listaRequerimientos;
        this.curso = contexto.idCurso;
    }

    public void iniciar() {
        JInternalFrame frame = new JInternalFrame("Lista de Requerimientos: " + mod.getIdAsistente());
        frame.setClosable(true);
        frame.setResizable(false);

// Panel principal con padding
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

// -------- PANEL DE REQUERIMIENTOS --------
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

        panelGeneral.add(panelRequerimientos);
        panelGeneral.add(Box.createVerticalStrut(20)); // Separación visual

// -------- PANEL ILUO --------
        JPanel panelILUO = new JPanel();
        panelILUO.setLayout(new BoxLayout(panelILUO, BoxLayout.Y_AXIS));
        panelILUO.setBorder(BorderFactory.createTitledBorder("Gestión ILUO"));

// Cabecera
        JPanel header = new JPanel(new GridLayout(1, 2));
        header.add(new JLabel("Habilidad"));
        header.add(new JLabel("Estado ILUO"));
        header.add(new JLabel("Ultima Fehca de Evaluación"));
        header.add(new JLabel("Observaciones"));

        panelILUO.add(header);

        panelILUO.add(Box.createVerticalStrut(10)); // Espacio entre cabecera y contenido

        List<HabilidadesCurso> listaHabilidades = new ConsultasHabilidadesCurso().obtenerHabilidadesPorCurso(curso);
        Map<HabilidadesCurso, JComboBox<String>> mapHabilidadILUO = new HashMap<>();

// Además, un map para guardar componentes de fecha y observación si quieres actualizar o leer luego
        Map<HabilidadesCurso, JLabel> mapFechaEvaluacion = new HashMap<>();
        Map<HabilidadesCurso, JTextField> mapObservaciones = new HashMap<>();

        for (HabilidadesCurso habilidad : listaHabilidades) {
            JPanel fila = new JPanel(new GridLayout(1, 4, 10, 0));  // 4 columnas ahora
            fila.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            fila.add(new JLabel(habilidad.getNombre_habilidad()));

            JComboBox<String> comboILUO = new JComboBox<>(new String[]{"I", "L", "U", "O"});
            comboILUO.setPreferredSize(new Dimension(50, 25));
            fila.add(comboILUO);

            JLabel labelFecha = new JLabel(""); // aquí irá la fecha
            fila.add(labelFecha);

            JTextField campoObservacion = new JTextField();  // observación editable o solo lectura
            campoObservacion.setPreferredSize(new Dimension(150, 25));
            fila.add(campoObservacion);

            panelILUO.add(fila);

            mapHabilidadILUO.put(habilidad, comboILUO);
            mapFechaEvaluacion.put(habilidad, labelFecha);
            mapObservaciones.put(habilidad, campoObservacion);
        }

// Luego, obtienes las evaluaciones y actualizas también fecha y observación
        List<HabilidadEvaluada> habilidadesEvaluadas = new ConsultasHabilidadesCurso().obtenerHabilidadesConEvaluacion(curso, mod.getIdAsistente(), mod.getIdHistorial());

        for (HabilidadEvaluada habilidadEval : habilidadesEvaluadas) {
            for (HabilidadesCurso habilidadCurso : mapHabilidadILUO.keySet()) {
                if (habilidadCurso.getIdHabilidad() == habilidadEval.getIdHabilidad()) {
                    JComboBox<String> combo = mapHabilidadILUO.get(habilidadCurso);
                    JLabel labelFecha = mapFechaEvaluacion.get(habilidadCurso);
                    JTextField campoObs = mapObservaciones.get(habilidadCurso);

                    if (habilidadEval.getNivelAlcanzado() != null) {
                        combo.setSelectedItem(habilidadEval.getNivelAlcanzado());
                    } else {
                        combo.setSelectedItem(null);
                    }

                    if (habilidadEval.getFechaEvaluacion() != null) {
                        labelFecha.setText(habilidadEval.getFechaEvaluacion().toString()); // Formatear si quieres
                    } else {
                        labelFecha.setText("");
                    }

                    if (habilidadEval.getObservaciones() != null) {
                        campoObs.setText(habilidadEval.getObservaciones());
                    } else {
                        campoObs.setText("");
                    }
                    break;
                }
            }
        }

        panelILUO.add(Box.createVerticalStrut(10)); // Espacio antes del botón

        JButton btnGuardarILUO = new JButton("Guardar ILUO");
        btnGuardarILUO.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGuardarILUO.addActionListener(e -> {
            java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
            ConsultasHabilidadesCurso consulta = new ConsultasHabilidadesCurso(); // Asegúrate de tener esta clase

            int errores = 0;
            for (Map.Entry<HabilidadesCurso, JComboBox<String>> entry : mapHabilidadILUO.entrySet()) {
                HabilidadesCurso hab = entry.getKey();
                String nivel = (String) entry.getValue().getSelectedItem();
                
                // Obtener la observación para esta habilidad desde el mapa (que debes tener disponible)
                JTextField campoObs = mapObservaciones.get(hab);
                String observacion = campoObs != null ? campoObs.getText() : null;

                boolean ok = consulta.guardarOActualizarEvaluacion(
                        mod.getIdAsistente(),
                        hab.getIdHabilidad(),
                        mod.getIdHistorial(),
                        nivel,
                        fechaActual,
                        observacion
                );

                if (!ok) {
                    errores++;
                }
            }

            if (errores == 0) {
                JOptionPane.showMessageDialog(null, "Todos los niveles ILUO fueron guardados correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Hubo errores al guardar algunas evaluaciones ILUO.");
            }
        });

        panelILUO.add(btnGuardarILUO);
        panelGeneral.add(panelILUO);

// -------- VENTANA FINAL --------
        JFrame ventana = new JFrame("Gestión de Requerimientos e ILUO");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(800, 600);
        ventana.setLocationRelativeTo(null); // Centra la ventana
        ventana.setContentPane(new JScrollPane(panelGeneral)); // Scroll si hay muchos datos
        ventana.setVisible(true);

    }

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
                return;
            }

            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(dateChooser.getDate());
            mod.setIdRequerimiento(requerimiento.getIdRequerimiento());

            if (modC.agregarRequerimiento(mod, fecha)) {
                generarExpediente(mod);
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
        File carpetaPrincipal = ArchivoHelper.seleccionarCarpeta();
        if (carpetaPrincipal == null) {
            JOptionPane.showMessageDialog(null, "No se seleccionó carpeta. Operación cancelada.");
            return;
        }

        String trabajador = ConsultaHelper.obtenerNombreTrabajador(mod.getIdAsistente());
        File carpetaTrabajador = new File(carpetaPrincipal, mod.getIdAsistente() + "_" + trabajador);
        if (!carpetaTrabajador.exists()) {
            carpetaTrabajador.mkdirs();
        }

        String curso = ConsultaHelper.obtenerCurso(mod.getIdHistorial());
        String fecha = ConsultaHelper.obtenerFechaCurso(mod.getIdHistorial());
        File carpetaCurso = new File(carpetaTrabajador, curso + " " + fecha);
        if (!carpetaCurso.exists()) {
            carpetaCurso.mkdirs();
        }

        File origen = new File(mod.getRuta_archivo());
        File destino = new File(carpetaCurso, mod.getNombre_archivo());
        ArchivoHelper.copiarArchivo(origen, destino);
    }
}
