package Controller;

import ContextController.ContextoEditarRequerimientos;
import Functions.DateTools;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Model.RequerimientosCurso;
import Model.RequerimientosCursoAsistente;
import Querys.ConsultasRequerimientosCurso;
import View.FrmAdministrador;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CtrlRequerimientos {

    private final FrmAdministrador frmA;
    private final RequerimientosCursoAsistente mod;
    private final ConsultasRequerimientosCurso modC = new ConsultasRequerimientosCurso();
    private final List<RequerimientosCurso> requerimientos;

    public CtrlRequerimientos(ContextoEditarRequerimientos contexto) {
        this.frmA = contexto.ventanaAdministrador;
        this.mod = contexto.modeloRequerimientosAsistente;
        this.requerimientos = contexto.listaRequerimientos;
    }

    public void iniciar() {
        System.out.println(mod.getIdAsistente());
        System.out.println(mod.getIdHistorial());

        JInternalFrame frame = new JInternalFrame("Lista de Requerimientos: " + mod.getIdAsistente());
        frame.setClosable(true);
        frame.setResizable(false);

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaciado externo

        for (RequerimientosCurso requerimiento : requerimientos) {
            JPanel panelRequerimiento = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panelRequerimiento.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding interno
            ));

            JCheckBox cb_buton = new JCheckBox();
            JLabel lblRequerimiento = new JLabel(requerimiento.getNombre_requerimiento());
            JTextField fechaEntregaTextField = new JTextField(10);
            JButton bt_consultar = new JButton("Consultar");

            cb_buton.addActionListener((ActionEvent e) -> {
                if (cb_buton.isSelected()) {
                    requerimentoSelected(requerimiento, panelRequerimiento, fechaEntregaTextField, bt_consultar);
                } else {
                    mod.setIdRequerimiento(requerimiento.getIdRequerimiento());
                    if (modC.eliminarRequerimiento(mod)) {
                        panelRequerimiento.remove(fechaEntregaTextField);
                        panelRequerimiento.remove(bt_consultar);
                        panelRequerimiento.revalidate();
                        panelRequerimiento.repaint();
                        System.out.println("Registro Eliminado");
                    }
                }
            });

            // Si ya fue marcado como cumplido
            if (modC.estadoRequerimiento(requerimiento.getIdRequerimiento(), mod)) {
                cb_buton.setSelected(true);
                fechaEntregaTextField.setEditable(false);
                fechaEntregaTextField.setText(mod.getFecha_entrega());

                bt_consultar.addActionListener((ActionEvent e) -> {
                    File archivo = new File(mod.getRuta_archivo());
                    if (archivo.exists()) {
                        try {
                            Desktop.getDesktop().open(archivo);
                        } catch (IOException ex) {
                            Logger.getLogger(CtrlRequerimientos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        System.out.println("El archivo no existe.");
                    }
                });

                panelRequerimiento.add(cb_buton);
                panelRequerimiento.add(lblRequerimiento);
                panelRequerimiento.add(fechaEntregaTextField);
                panelRequerimiento.add(bt_consultar);
            } else {
                // Si no está marcado
                panelRequerimiento.add(cb_buton);
                panelRequerimiento.add(lblRequerimiento);
            }

            panelGeneral.add(panelRequerimiento);
        }

        frame.getContentPane().add(new JScrollPane(panelGeneral)); // Soporte para scroll si hay muchos
        frame.pack();
        ViewTools.Centrar(frmA, frame);

//        int x = (frmA.Desktop_Administrador.getWidth() / 2) - frame.getWidth() / 2;
//        int y = (frmA.Desktop_Administrador.getHeight() / 2) - frame.getHeight() / 2;
//
//        frmA.Desktop_Administrador.add(frame);
//
//        frame.setVisible(true);
//        if (frame.isShowing()) {
//            frame.setLocation(x, y);
//        } else {
//            frmA.Desktop_Administrador.add(frame);
//            frame.setLocation(x, y);
//            frame.show();
//        }
    }

    private void requerimentoSelected(RequerimientosCurso requerimiento, JPanel panelRequerimiento, JTextField fechaEntregaTextField, JButton bt_consultar) {
        JPanel panelCargar = new JPanel(new GridLayout(0, 1));
        JDateChooser dateChooser = new JDateChooser();
        JTextField txt_archivo = new JTextField();
        JButton bt_Archivo = new JButton();

        bt_Archivo.setText("Agregar");
        panelCargar.add(new JLabel("Seleccione la fecha de Registro:"));
        panelCargar.add(dateChooser);
        panelCargar.add(new JLabel("Seleccione el archivo:"));
        panelCargar.add(txt_archivo);
        panelCargar.add(bt_Archivo);

        bt_Archivo.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int fileResult = fileChooser.showOpenDialog(null);

            if (fileResult == JFileChooser.APPROVE_OPTION) {
                try {
                    FileInputStream fis = null;
                    File selectedFile = fileChooser.getSelectedFile();

                    String rutaArchivo = selectedFile.getAbsolutePath();
                    String nombreArchivo = selectedFile.getName();

                    fis = new FileInputStream(selectedFile);
                    byte[] contenido = new byte[(int) selectedFile.length()];
                    fis.read(contenido);
                    fis.close();

                    mod.setRuta_archivo(rutaArchivo);
                    mod.setNombre_archivo(nombreArchivo);

                    txt_archivo.setText(nombreArchivo);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CtrlRequerimientos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(CtrlRequerimientos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        int result = JOptionPane.showConfirmDialog(null, panelCargar,
                "Registro del requerimiento:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (dateChooser.getDate() == null || txt_archivo.getText() == null) {
                JOptionPane.showMessageDialog(
                        null, "Debe seleccionar una fecha de aceptación y un archivo",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaSeleccionada = dateChooser.getDate();
                String fecha = formato.format(fechaSeleccionada);

                mod.setIdRequerimiento(requerimiento.getIdRequerimiento());

                if (modC.agregarRequerimiento(mod, fecha)) {
                    GenerarExpendiente(mod);
                    System.out.println("Registro Exitoso");

                    if (modC.estadoRequerimiento(requerimiento.getIdRequerimiento(), mod)) {
                        fechaEntregaTextField.setEditable(false);
                        String fechaEntrega = mod.getFecha_entrega();
                        fechaEntregaTextField.setText(fechaEntrega);

                        bt_consultar.addActionListener((ActionEvent e) -> {
                            File archivo = new File(mod.getRuta_archivo());
                            Desktop desktop = Desktop.getDesktop();
                            if (archivo.exists()) {
                                try {
                                    desktop.open(archivo);
                                } catch (IOException ex) {
                                    Logger.getLogger(CtrlRequerimientos.class.getName()).log(
                                            Level.SEVERE, null, ex);
                                }
                            } else {
                                System.out.println("El archivo no existe.");
                            }
                        });

                        panelRequerimiento.add(fechaEntregaTextField);
                        panelRequerimiento.add(bt_consultar);
                        panelRequerimiento.revalidate();
                        panelRequerimiento.repaint();
                    }
                }
            }
        }
    }

    private void GenerarExpendiente(RequerimientosCursoAsistente mod) {
        String trabajador = QueryFunctions.CapturaCondicionalSimple("trabajador", "nombre_Trabajador",
                "Folio_Trabajador", String.valueOf(mod.getIdAsistente()));
        // Construir la ruta completa de la carpeta del trabajador
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo permite seleccionar carpetas
        selector.setDialogTitle("Selecciona la carpeta del expediente del Curso");

        int resultado = selector.showOpenDialog(null);
        String RUTA_CARPETA_PRINCIPAL = null;

        if (resultado == JFileChooser.APPROVE_OPTION) {
            RUTA_CARPETA_PRINCIPAL = selector.getSelectedFile().toString();
            System.out.println("Carpeta seleccionada: " + RUTA_CARPETA_PRINCIPAL);
        }

        String rutaCarpetaTrabajador = RUTA_CARPETA_PRINCIPAL + "/" + mod.getIdAsistente() + "_" + trabajador;

        // Verificar si la carpeta del trabajador ya existe
        File carpetaTrabajador = new File(rutaCarpetaTrabajador);
        if (!carpetaTrabajador.exists()) {
            // La carpeta del trabajador no existe, así que la creamos
            if (carpetaTrabajador.mkdirs()) {
                System.out.println("Carpeta del trabajador creada: " + rutaCarpetaTrabajador);
            } else {
                System.out.println("No se pudo crear la carpeta del trabajador: " + rutaCarpetaTrabajador);
            }
        }
        String Curso = QueryFunctions.CapturaCondicionalAnidado("historial_curso",
                "curso", "idCurso", "idCurso", "nombre_Curso", "idHistorial_Curso",
                String.valueOf(mod.getIdHistorial()));

        String FechaSQL = QueryFunctions.CapturaCondicionalSimple("historial_Curso", "fecha_inicio",
                "idHistorial_Curso", String.valueOf(mod.getIdHistorial()));

        String Fecha = DateTools.DateLocaltoString(FechaSQL);

        // Construir la ruta completa de la carpeta del trabajador
        String rutaCarpetaCurso = rutaCarpetaTrabajador + "\\" + Curso + " " + Fecha;

        // Verificar si la carpeta del trabajador ya existe
        File carpetaCurso = new File(rutaCarpetaCurso);
        if (!carpetaCurso.exists()) {
            // La carpeta del trabajador no existe, así que la creamos
            if (carpetaCurso.mkdirs()) {
                System.out.println("Carpeta del trabajador creada: " + carpetaCurso);
            } else {
                System.out.println("No se pudo crear la carpeta del trabajador: " + carpetaCurso);
            }
        }

        // Copiar el archivo de referencia en la carpeta del trabajador
        try {

            Files.copy(new File(mod.getRuta_archivo()).toPath(),
                    new File(carpetaCurso + "\\" + mod.getNombre_archivo()).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo de referencia copiado en la carpeta del trabajador.");
        } catch (IOException e) {
            System.out.println("Error al copiar el archivo de referencia: " + e.getMessage());
        }
    }
}
