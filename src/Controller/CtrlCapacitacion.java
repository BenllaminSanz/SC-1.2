package Controller;

import ContextController.ContextoAgregarAsistentes;
import ContextController.ContextoCapacitacion;
import ContextController.ContextoEditarCertificacion;
import ContextController.ContextoEditarCertificado;
import ContextController.ContextoEditarCurso;
import ContextController.ContextoEditarCursoPersonal;
import ContextController.ContextoEditarHistorialCurso;
import ContextController.ContextoEditarRequerimientos;
import Documents.DesingPDF_Diplomas;
import Documents.GeneratorExcel_BDs;
import Documents.GeneratorExcel_Certificados;
import Documents.GeneratorExcel_Cursos;
import Documents.GeneratorPDF_Certificados;
import Documents.GeneratorPDF_Cursos;
import Functions.DateTools;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Graphics.Graphics_Capacitacion;
import Graphics.Graphics_Cursos;
import Model.Certificado;
import Model.Curso;
import Model.HistorialCurso;
import Model.PersonalCertificado;
import Model.RequerimientosCurso;
import Model.RequerimientosCursoAsistente;
import Querys.ConsultasAsistentesCurso;
import Querys.ConsultasCertificado;
import Querys.ConsultasCurso;
import Querys.ConsultasHistorialCurso;
import Querys.ConsultasPersonalCertificado;
import Querys.ConsultasRequerimientosCurso;
import Tables.CargarTabla;
import static Tables.CargarTabla.conn;
import Tables.DesignTabla;
import Tables.TableAsistentesCertificado;
import Tables.TableAsistentesCertificados;
import Tables.TableCertificadosTrabajador;
import View.FrmAdministrador;
import View.IFrmCapacitacion;
import com.itextpdf.text.DocumentException;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class CtrlCapacitacion implements ActionListener, MouseListener, ListSelectionListener, TreeSelectionListener, ChangeListener {

    String cantidadCursos = "No. de Cursos: %s";
    String cantidadAsistentes = "No. de Asistentes: %s";
    String cantidadMinutos = "Minutos Curso: %s";
    String cantidadHoras = "Horas Curso: %s";
    String cantidadSemanas = "Semanas del Curso: %s";

    String certificacionesTotal = "Certificados: %s";
    String porcentajeFlexibilidad = "Porcentaje de Flexibilidad: %s";

    String certificadosAnual = "Certificaciones en el Año:  %s";
    String certificadosNuevoIngreso = "Certificaciones de Nuevo Ingreso: %s";
    String certificadosSegundoPuesto = "Certificaciones de Segundo Puesto: %s";
    String certificadosBaja = "Certificaciones dados de Baja: %s";

    String personalEntrenamiento = "En entrenamiento: %s";
    String primerEntrenamiento = "En primer puesto: %s";
    String segundoEntrenamiento = "En segundo puesto: %s";
    String entreamientoCruzado = "En entrenamiento en otro Puesto: %s";

    String cursosActivos = "Cursos Activos: %s";
    String asistentesEntrenamiento = "Personal en Entrenamiento: %s";
    String asistentesProceso = "Personal en proceso de Certificación: %s";

    private final Curso modCr = new Curso();
    private final ConsultasCurso modCCr = new ConsultasCurso();
    private final HistorialCurso modH = new HistorialCurso();
    private final ConsultasHistorialCurso modCH = new ConsultasHistorialCurso();
    private final ConsultasAsistentesCurso modCAc = new ConsultasAsistentesCurso();
    private final RequerimientosCursoAsistente modRa = new RequerimientosCursoAsistente();
    private final ConsultasRequerimientosCurso modCRa = new ConsultasRequerimientosCurso();
    private final Certificado modCt = new Certificado();
    private final ConsultasCertificado modCCt = new ConsultasCertificado();
    private final PersonalCertificado modT = new PersonalCertificado();
    private final ConsultasPersonalCertificado modTCt = new ConsultasPersonalCertificado();
    private final IFrmCapacitacion frm;
    private final FrmAdministrador frmA;
    int year = Calendar.getInstance().get(Calendar.YEAR);

    //Relacionamos el controlador con la vista
    public CtrlCapacitacion(ContextoCapacitacion contexto) {
        this.frm = contexto.ventanaCapacitacion;
        this.frmA = contexto.ventanaAdministrador;
        this.frm.btn_agregarCurso.addActionListener(this);
        this.frm.btn_agregarCursoH.addActionListener(this);
        this.frm.AgregarCurso.addActionListener(this);
        this.frm.item_AgregarHistorialCurso.addActionListener(this);
        this.frm.ModificarCurso.addActionListener(this);
        this.frm.EliminarCurso.addActionListener(this);
        this.frm.item_ModificarHistorialCurso.addActionListener(this);
        this.frm.item_EliminarHistorialCurso.addActionListener(this);
        this.frm.btn_act_hc.addActionListener(this);
        this.frm.item_AgregarAsistente.addActionListener(this);
        this.frm.item_EliminarAsistente.addActionListener(this);
        this.frm.btn_AgrTrabajador.addActionListener(this);
        this.frm.JTable_HistorialCurso.getSelectionModel().addListSelectionListener(this);
        this.frm.jtable_certificados.getSelectionModel().addListSelectionListener(this);
        this.frm.item_ConcluirCurso.addActionListener(this);
        this.frm.item_ModificarRequerimiento.addActionListener(this);
        this.frm.item_EliminarRequerimiento.addActionListener(this);
        this.frm.item_RequerimientosAsistente.addActionListener(this);
        this.frm.btn_filtrarhistorial.addActionListener(this);
        this.frm.AgregarRegistro.addActionListener(this);
        this.frm.btn_listaAsistentes.addActionListener(this);
        this.frm.item_ModificarCurso.addActionListener(this);
        this.frm.btn_agregarCertificado.addActionListener(this);
        this.frm.Item_ModificarCertificado.addActionListener(this);
        this.frm.Item_EliminarCertificado.addActionListener(this);
        this.frm.btn_act_ct.addActionListener(this);
        this.frm.btn_agregar_personalcertificado.addActionListener(this);
        this.frm.Item_AgregarCertificados.addActionListener(this);
        this.frm.item_ModificarCertificados.addActionListener(this);
        this.frm.Item_EliminarCertificados.addActionListener(this);
        this.frm.btn_Consultar.addActionListener(this);
        this.frm.btn_RefreshTabla.addActionListener(this);
        this.frm.jTable_entrenamientos.getSelectionModel().addListSelectionListener(this);
        this.frm.btn_Consultar1.addActionListener(this);
        this.frm.jTree_Cursos.addTreeSelectionListener(this);
        this.frm.item_AgregarAsistentes.addActionListener(this);
        this.frm.jTabbedPane1.addChangeListener(this);
        this.frm.bt_consulta2.addActionListener(this);
        this.frm.consulta_mensual.addActionListener(this);
        this.frm.item_ModificarCursoPersonal.addActionListener(this);
        this.frm.jMenuItem1.addActionListener(this);
        this.frm.jMenuItem2.addActionListener(this);
        this.frm.jMenuItem3.addActionListener(this);
        this.frm.jMenuItem4.addActionListener(this);
        this.frm.jMenuItem5.addActionListener(this);
        this.frm.jMenuItem6.addActionListener(this);
        this.frm.jMenuItem7.addActionListener(this);
        this.frm.jMenuItem8.addActionListener(this);
        this.frm.jMenuItem9.addActionListener(this);
        this.frm.jMenuItem10.addActionListener(this);
        this.frm.jMenuItem11.addActionListener(this);
        this.frm.jMenuItem12.addActionListener(this);
        this.frm.jMenuItem13.addActionListener(this);
        this.frm.jMenuItem14.addActionListener(this);
        this.frm.jButton1.addActionListener(this);
        this.frm.jButton2.addActionListener(this);
        this.frm.jButton3.addActionListener(this);
        this.frm.jButton4.addActionListener(this);
        this.frm.jButton5.addActionListener(this);
    }

    public void iniciar() {
//        frm.jYearChooser1.setYear(year);
        CargarTabla.GenerarArbolCursos(frm);
        CargarComboBoxCapacitacion(frm);
        DesignTabla.designAllCursosActivos(frm);
        DesignTabla.designAsistentesCursos(frm);
        DesignTabla.designAllHistorialCursos(frm);
        DesignTabla.designCertificados(frm);
        DesignTabla.designAllAsistentesCertificados(frm);
        DesignTabla.designAllEstadoCertificacion(frm);

        // Crear una instancia de la clase SwingWorker
        SwingWorker<Void, Void> workerInfo = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                ConcentradoCursos(frm);
                ConcentradoCertificados(frm);
                ConcentradoCertificadosGeneral(frm, year);
                return null;
            }
        };

// Crear una instancia de la clase SwingWorker
        SwingWorker<Void, Void> workerGraphics = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Graphics_Cursos.BarCharCursosGeneral(frm);
                Graphics_Cursos.BarCharCursosTiempoTotal(frm);
                Graphics_Cursos.BarCharCursosTiempo(frm);
                Graphics_Capacitacion.PieCharCertificados(frm);
                Graphics_Capacitacion.BarCharCertificados(frm);
                Graphics_Capacitacion.ChartCertificados(frm, year);
                return null;
            }
        };

// Ejecutar ambas tareas en segundo plano
        workerInfo.execute();
        workerGraphics.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Object> validSourcesAgregarTrabajador = Arrays.asList(frm.AgregarCurso, frm.btn_agregarCurso);
        if (validSourcesAgregarTrabajador.contains(e.getSource())) {
            String txtBoton = "Guardar";
            ContextoEditarCurso contexto = new ContextoEditarCurso(txtBoton, null, frm, frmA);
            CtrlEditarCurso ctrl = new CtrlEditarCurso(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarCurso);
        }

        //Abre la ventana para modificar el trabajador seleccionado
        if (e.getSource() == frm.ModificarCurso) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) frm.jTree_Cursos.getLastSelectedPathComponent();
            if (selectedNode.isLeaf()) {
                String curso = (String) selectedNode.getUserObject().toString();
                String txtBoton = "Actualizar";
                ContextoEditarCurso contexto = new ContextoEditarCurso(txtBoton, curso, frm, frmA);
                CtrlEditarCurso ctrl = new CtrlEditarCurso(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarCurso);
            }
        }

        //Abre la ventana para modificar el trabajador seleccionado
        if (e.getSource() == frm.EliminarCurso) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) frm.jTree_Cursos.getLastSelectedPathComponent();
            if (selectedNode.isLeaf()) {
                String curso = (String) selectedNode.getUserObject().toString();
                modCr.setNombre_Curso(curso);
                int option = JOptionPane.showConfirmDialog(null,
                        "¿Seguro de dar de baja este Curso?"
                        + "\n Esto eliminara permanentemente los registros asociados al curso",
                        "Eliminar Curso", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (modCCr.eliminar(modCr)) {
                        JOptionPane.showMessageDialog(null, "Baja de Curso realizada con exito");
                        CargarTabla.GenerarArbolCursos(frm);
                    }
                }
            }
        }

        List<Object> validSourcesAgregarHistorial = Arrays.asList(frm.AgregarRegistro, frm.btn_agregarCursoH, frm.item_AgregarHistorialCurso);
        if (validSourcesAgregarHistorial.contains(e.getSource())) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) frm.jTree_Cursos.getLastSelectedPathComponent();
            if (selectedNode == null) {
                String txtBoton = "Guardar";
                ContextoEditarHistorialCurso contexto = new ContextoEditarHistorialCurso(null, null, txtBoton, frm, frmA);
                CtrlEditarHistorialCurso ctrl = new CtrlEditarHistorialCurso(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarHistorialCurso);
            } else {
                String curso = selectedNode.getUserObject().toString();
                String txtBoton = "Guardar";
                ContextoEditarHistorialCurso contexto = new ContextoEditarHistorialCurso(curso, null, txtBoton, frm, frmA);
                CtrlEditarHistorialCurso ctrl = new CtrlEditarHistorialCurso(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarHistorialCurso);
            }
        }

        if (e.getSource() == frm.item_ModificarHistorialCurso) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) frm.jTree_Cursos.getLastSelectedPathComponent();
            String historial = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            if (selectedNode == null) {
                String txtBoton = "Actualizar";
                ContextoEditarHistorialCurso contexto = new ContextoEditarHistorialCurso(null, historial, txtBoton, frm, frmA);
                CtrlEditarHistorialCurso ctrl = new CtrlEditarHistorialCurso(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarHistorialCurso);
            } else {
                String curso = selectedNode.getUserObject().toString();
                String txtBoton = "Actualizar";
                ContextoEditarHistorialCurso contexto = new ContextoEditarHistorialCurso(curso, historial, txtBoton, frm, frmA);
                CtrlEditarHistorialCurso ctrl = new CtrlEditarHistorialCurso(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarHistorialCurso);
            }
        }

        if (e.getSource() == frm.item_EliminarHistorialCurso) {
            String historial = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja este registro del Curso?");
            if (option == JOptionPane.OK_OPTION) {
                if (modCH.eliminar(historial)) {
                    DesignTabla.designAllHistorialCursos(frm);
                }
            }
        }

        if (e.getSource() == frm.btn_act_hc) {
            frm.jTree_Cursos.clearSelection();
            DesignTabla.designAllHistorialCursos(frm);
            DesignTabla.designAsistentesCurso(frm, 0);
            ConcentradoCursos(frm);
        }

        List<Object> validSourcesAgregarAsistentes = Arrays.asList(frm.btn_AgrTrabajador, frm.item_AgregarAsistente, frm.item_AgregarAsistentes);
        if (validSourcesAgregarAsistentes.contains(e.getSource())) {
            String idHistorial = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            String idCurso = QueryFunctions.CapturaCondicionalSimple("historial_curso", "idCurso", "idHistorial_curso", idHistorial);
            ContextoAgregarAsistentes contexto = new ContextoAgregarAsistentes(idCurso, idHistorial, frm);
            CtrlAgregarAsistentes ctrl = new CtrlAgregarAsistentes(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaAgregarAsistente);
        }

        if (e.getSource() == frm.item_EliminarAsistente) {
            String idHistorial = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            String asistente = frm.jTable_Asistentes_Curso.getValueAt(frm.jTable_Asistentes_Curso.getSelectedRow(), 0).toString();
            if (modCAc.eliminar(idHistorial, asistente)) {
                int Curso = Integer.parseInt(frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                DesignTabla.designAsistentesCurso(frm, Curso);
                ConcentradoAsistentesCurso(frm, Curso);
            }
        }

        if (e.getSource() == frm.item_ConcluirCurso) {
            String idHistorial = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            String estado = QueryFunctions.CapturaCondicionalSimple("historial_curso", "estado_curso", "idHistorial_Curso", idHistorial);
            String idCurso = QueryFunctions.CapturaCondicionalSimple("historial_curso", "idCurso", "idHistorial_Curso", idHistorial);

            int tipo = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple("curso", "id_tipocurso", "idCurso", idCurso));

            if (estado.equals("Concluido")) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Se restuarara como Activo el Curso seleccionado", "¿Restaurar Curso?", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    modH.setIdTipo_Curso(tipo);
                    modH.setIdHistorialCurso(Integer.parseInt(idHistorial));
                    if (modCH.restaurar(modH)) {
                        JOptionPane.showMessageDialog(frm, "Curso Restaurado");
                        DesignTabla.designHistorialCurso(frm, Integer.parseInt(idCurso));
                    }
                }
            } else {
                JDateChooser dateChooser = new JDateChooser();
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Seleccione la fecha de conclusión del curso:"));
                panel.add(dateChooser);
                int option = JOptionPane.showOptionDialog(frm, panel,
                        "Concluir Curso", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (option == JOptionPane.OK_OPTION) {
                    modH.setIdHistorialCurso(Integer.parseInt(idHistorial));
                    Date selectedDate = dateChooser.getDate();
                    String fecha_cierre = DateTools.MySQLtoString(selectedDate);
                    modH.setIdTipo_Curso(tipo);
                    modH.setFecha_cierre(fecha_cierre);
                    modH.setStatus_curso(estado);
                    if (modCH.concluir(modH)) {
                        JOptionPane.showMessageDialog(frm, "Curso concluido");
                    }
                }
            }
            DesignTabla.designHistorialCurso(frm, Integer.parseInt(idCurso));
        }

        if (e.getSource() == frm.item_RequerimientosAsistente) {
            String idHistorial = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            modRa.setIdHistorial(Integer.parseInt(idHistorial));
            String asistente = frm.jTable_Asistentes_Curso.getValueAt(frm.jTable_Asistentes_Curso.getSelectedRow(), 1).toString();
            String idasistente = QueryFunctions.CapturaCondicionalSimple(
                    "asistentes_curso", "idAsistentes_curso", "nombre_asistente", asistente);
            modRa.setIdAsistente(Integer.parseInt(idasistente));
            String idCurso = QueryFunctions.CapturaCondicionalSimple(
                    "historial_curso", "idcurso", "idHistorial_curso", idHistorial);
            int curso = Integer.parseInt(idCurso);
            List<RequerimientosCurso> requisitos = modCRa.consultar(idCurso);
            if (!requisitos.isEmpty()) {
                ContextoEditarRequerimientos contexto = new ContextoEditarRequerimientos(requisitos, modRa, frmA, curso);
                CtrlRequerimientos ctrl = new CtrlRequerimientos(contexto);
                ctrl.iniciar();
            } else {
                JOptionPane.showMessageDialog(null,
                        "El avance solo aplica para los cursos de Proceso de Producción \n"
                        + "Si es el caso, verifique que se han cargado los requerimientos y habilidades en el curso");

            }
        }

        if (e.getSource() == frm.btn_filtrarhistorial) {
            String fecha_inicio = ((JTextField) frm.jDateChooser1.getDateEditor().getUiComponent()).getText();
            if (!fecha_inicio.isEmpty()) {
                DesignTabla.designAllHistorialCursosB(fecha_inicio, frm);
            }
        }

        if (e.getSource() == frm.btn_listaAsistentes) {
            int filaSeleccionada = frm.JTable_HistorialCurso.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un curso en la tabla.");
                return;
            }

            String idHistorial = frm.JTable_HistorialCurso.getValueAt(
                    frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();

            int tipo = Integer.parseInt(QueryFunctions.CapturaCondicionalAnidado(
                    "historial_curso", "curso", "idCurso",
                    "idCurso", "id_tipocurso", "idHistorial_curso", String.valueOf(idHistorial)));
            switch (tipo) {
                case 1:
                    if (GeneratorPDF_Cursos.Lista_Induccion(idHistorial)) {
                        JOptionPane.showMessageDialog(frm, "Archivo generado correctamente en Capacitacion/Listas Cursos");
                    }
                    break;
                case 6:
                    if (GeneratorPDF_Cursos.Lista_AsistentesLUP(idHistorial)) {
                        JOptionPane.showMessageDialog(frm, "Archivo generado correctamente en Capacitacion/Listas Cursos");
                    }
                    break;
                default:
                    if (GeneratorPDF_Cursos.Lista_Asistentes(idHistorial)) {
                        JOptionPane.showMessageDialog(frm, "Archivo generado correctamente en Capacitacion/Listas Cursos");
                    }
                    break;
            }
        }

        if (e.getSource() == frm.item_ModificarCurso) {
            String curso = frm.JTable_HistorialCurso.getValueAt(frm.JTable_HistorialCurso.getSelectedRow(), 1).toString();
            String txtBoton = "Actualizar";
            ContextoEditarCurso contexto = new ContextoEditarCurso(txtBoton, curso, frm, frmA);
            CtrlEditarCurso ctrl = new CtrlEditarCurso(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarCurso);
        }

        if (e.getSource() == frm.btn_agregarCertificado) {
            String txtBoton = "Guardar";
            ContextoEditarCertificado contexto = new ContextoEditarCertificado(txtBoton, null, frm, frmA);
            CtrlEditarCertificado ctrl = new CtrlEditarCertificado(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarCertificado);
        }

        if (e.getSource() == frm.Item_ModificarCertificado) {
            String txtBoton = "Actualizar";
            String certificado = frm.jtable_certificados.getValueAt(
                    frm.jtable_certificados.getSelectedRow(), 0).toString();

            String idCertificado = QueryFunctions.CapturaCondicionalSimple(
                    "certificado", "idCertificado", "nombre_certificado", certificado);
            ContextoEditarCertificado contexto = new ContextoEditarCertificado(txtBoton, idCertificado, frm, frmA);
            CtrlEditarCertificado ctrl = new CtrlEditarCertificado(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarCertificado);
        }

        if (e.getSource() == frm.Item_EliminarCertificado) {
            String certificado = frm.jtable_certificados.getValueAt(
                    frm.jtable_certificados.getSelectedRow(), 0).toString();
            modCt.setNombre_Certificado(certificado);
            int option = JOptionPane.showConfirmDialog(null,
                    "¿Seguro de dar de baja este Certificado?"
                    + "\n Esto eliminara permanentemente los registros asociados al certificado",
                    "Eliminar Certificado", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                if (modCCt.eliminar(modCt)) {
                    JOptionPane.showMessageDialog(null, "Baja de Certificado realizada con exito");
                    DesignTabla.designCertificados(frm);
                }
            }
        }

        if (e.getSource() == frm.btn_act_ct) {
            DesignTabla.designCertificados(frm);
            DesignTabla.designAllAsistentesCertificados(frm);
        }

        List<Object> validSourcesAgregarHCertificacion = Arrays.asList(frm.btn_agregar_personalcertificado, frm.Item_AgregarCertificados);
        if (validSourcesAgregarHCertificacion.contains(e.getSource())) {
            String txtBoton = "Guardar";
            ContextoEditarCertificacion contexto = new ContextoEditarCertificacion(txtBoton, null, frm, frmA);
            CtrlEditarCertificacion ctrl = new CtrlEditarCertificacion(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarCertificacion);
        }

        if (e.getSource() == frm.item_ModificarCertificados) {
            String txtBoton = "Actualizar";
            int seleccion1 = frm.jTable_AsistentesCertificados.getSelectedRow();
            int seleccion2 = frm.jTable_certificadosTrabajador.getSelectedRow();
            TableModel modelo = null;

            if (seleccion1 != -1) {
                modelo = frm.jTable_AsistentesCertificados.getModel();
            } else if (seleccion2 != -1) {
                modelo = frm.jTable_certificadosTrabajador.getModel();
            }

            if (modelo instanceof TableAsistentesCertificados) {
                String certificado = frm.jTable_AsistentesCertificados.getValueAt(
                        frm.jTable_AsistentesCertificados.getSelectedRow(), 1).toString();
                ContextoEditarCertificacion contexto = new ContextoEditarCertificacion(txtBoton, certificado, frm, frmA);
                CtrlEditarCertificacion ctrl = new CtrlEditarCertificacion(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarCertificacion);
            } else if (modelo instanceof TableCertificadosTrabajador) {
                String certificado = frm.jTable_certificadosTrabajador.getValueAt(
                        frm.jTable_certificadosTrabajador.getSelectedRow(), 0).toString();
                ContextoEditarCertificacion contexto = new ContextoEditarCertificacion(txtBoton, certificado, frm, frmA);
                CtrlEditarCertificacion ctrl = new CtrlEditarCertificacion(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarCertificacion);
            } else if (modelo instanceof TableAsistentesCertificado) {
                String certificado = frm.jTable_AsistentesCertificados.getValueAt(
                        frm.jTable_AsistentesCertificados.getSelectedRow(), 0).toString();
                ContextoEditarCertificacion contexto = new ContextoEditarCertificacion(txtBoton, certificado, frm, frmA);
                CtrlEditarCertificacion ctrl = new CtrlEditarCertificacion(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmA, contexto.ventanaEditarCertificacion);
            }
        }

        if (e.getSource() == frm.Item_EliminarCertificados) {
            int seleccion1 = frm.jTable_AsistentesCertificados.getSelectedRow();
            int seleccion2 = frm.jTable_certificadosTrabajador.getSelectedRow();
            TableModel modelo = null;

            if (seleccion1 != -1) {
                modelo = frm.jTable_AsistentesCertificados.getModel();
            } else if (seleccion2 != -1) {
                modelo = frm.jTable_certificadosTrabajador.getModel();
            }

            if (modelo instanceof TableAsistentesCertificados) {
                String certificado = frm.jTable_AsistentesCertificados.getValueAt(
                        frm.jTable_AsistentesCertificados.getSelectedRow(), 1).toString();
                String trabajador = frm.jTable_AsistentesCertificados.getValueAt(
                        frm.jTable_AsistentesCertificados.getSelectedRow(), 2).toString();
                int option = JOptionPane.showConfirmDialog(null,
                        "¿Seguro de dar de eliminar este Certificado?",
                        "Eliminar Certificado", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (modTCt.eliminar(certificado, trabajador)) {
                        JOptionPane.showMessageDialog(null, "Baja de Certificación realizada con exito");
                        DesignTabla.designCertificados(frm);
                    }
                }
            } else if (modelo instanceof TableCertificadosTrabajador) {
                String certificado = frm.jTable_certificadosTrabajador.getValueAt(
                        frm.jTable_certificadosTrabajador.getSelectedRow(), 0).toString();
                String trabajador = frm.jTable_entrenamientos.getValueAt(
                        frm.jTable_entrenamientos.getSelectedRow(), 0).toString();
                int option = JOptionPane.showConfirmDialog(null,
                        "¿Seguro de dar de eliminar este Certificado?",
                        "Eliminar Certificado", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (modTCt.eliminar(certificado, trabajador)) {
                        JOptionPane.showMessageDialog(null, "Baja de Certificación realizada con exito");
                        DesignTabla.designCertificados(frm);
                    }
                }
            } else if (modelo instanceof TableAsistentesCertificado) {
                String certificado = frm.jTable_AsistentesCertificados.getValueAt(
                        frm.jTable_AsistentesCertificados.getSelectedRow(), 0).toString();
                String trabajador = frm.jTable_AsistentesCertificados.getValueAt(
                        frm.jTable_AsistentesCertificados.getSelectedRow(), 1).toString();
                int option = JOptionPane.showConfirmDialog(null,
                        "¿Seguro de dar de eliminar este Certificado?",
                        "Eliminar Certificado", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (modTCt.eliminar(certificado, trabajador)) {
                        JOptionPane.showMessageDialog(null, "Baja de Certificación realizada con exito");
                        DesignTabla.designCertificados(frm);
                    }
                }
            }
        }

        if (e.getSource() == frm.item_ModificarCursoPersonal) {
            String idCurso = frm.jTable_cursosTrabajador.getValueAt(
                    frm.jTable_cursosTrabajador.getSelectedRow(), 0).toString();
            String idTrabajador = frm.jTable_entrenamientos.getValueAt(
                    frm.jTable_entrenamientos.getSelectedRow(), 0).toString();
            ContextoEditarCursoPersonal contexto = new ContextoEditarCursoPersonal(idTrabajador, idCurso, frm, frmA);
            CtrlEditarCursoPersonal ctrl = new CtrlEditarCursoPersonal(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmA, contexto.ventanaEditarCurso);
        }

        if (e.getSource() == frm.btn_Consultar) {
            String area = frm.cb_area.getSelectedItem().toString();
            String puesto = frm.cb_puesto.getSelectedItem().toString();
            String turno = frm.cb_turno.getSelectedItem().toString();

            modT.setArea_idArea(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "area", "idArea", "Nombre_Area", area)));
            modT.setTurno_idturno(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "turno", "idturno", "nombre_turno", turno)));
            modT.setPuesto_idpuesto(Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "puesto", "idPuesto", "Nombre_Puesto", puesto)));

            if (modT.getArea_idArea() != 0 && modT.getTurno_idturno() == 0 && modT.getPuesto_idpuesto() == 0) {
                DesignTabla.designEstadoCertificacionB(frm, modT, 1);
            } else if (modT.getArea_idArea() != 0 && modT.getTurno_idturno() != 0 && modT.getPuesto_idpuesto() == 0) {
                DesignTabla.designEstadoCertificacionB(frm, modT, 2);
            } else if (modT.getArea_idArea() != 0 && modT.getTurno_idturno() == 0 && modT.getPuesto_idpuesto() != 0) {
                DesignTabla.designEstadoCertificacionB(frm, modT, 3);
            } else if (modT.getArea_idArea() != 0 && modT.getTurno_idturno() != 0 && modT.getPuesto_idpuesto() != 0) {
                DesignTabla.designEstadoCertificacionB(frm, modT, 4);
            }
        }

        if (e.getSource() == frm.btn_Consultar1) {
            String area = frm.cb_area1.getSelectedItem().toString();
            String puesto = frm.cb_puesto1.getSelectedItem().toString();
            String turno = frm.cb_turno1.getSelectedItem().toString();
            if (!area.equals("Todas las áreas...") && puesto.equals("Todos...") && turno.equals("Todos...")) {
                ConcentradoCertificadosBusqueda(frm, year, area, puesto, turno, 1);
            } else if (!area.equals("Todas las áreas...") && puesto.equals("Todos...") && !turno.equals("Todos...")) {
                ConcentradoCertificadosBusqueda(frm, year, area, puesto, turno, 2);
            } else if (!area.equals("Todas las áreas...") && !puesto.equals("Todos...") && turno.equals("Todos...")) {
                ConcentradoCertificadosBusqueda(frm, year, area, puesto, turno, 3);
            } else if (!area.equals("Todas las áreas...") && !puesto.equals("Todos...") && !turno.equals("Todos...")) {
                ConcentradoCertificadosBusqueda(frm, year, area, puesto, turno, 4);
            } else {
                Graphics_Capacitacion.BarCharCertificados(frm);
                Graphics_Capacitacion.PieCharCertificados(frm);
                Graphics_Capacitacion.ChartCertificados(frm, year);
            }
        }

        if (e.getSource() == frm.bt_consulta2) {
            String proceso = String.valueOf(frm.cb_procesos.getSelectedIndex());

            if (!proceso.equals("0")) {
                ConcentradoCertificadosProcesos(frm, proceso, year);
            } else {
                Graphics_Capacitacion.BarCharCertificadosProcesos(frm, "");
                Graphics_Capacitacion.PieCharCertificadosProcesos(frm, "");
                Graphics_Capacitacion.ChartCertificados(frm, year);
                ConcentradoCertificadosGeneral(frm, year);
            }
        }

        if (e.getSource() == frm.btn_RefreshTabla) {
            frm.cb_area.setSelectedItem("Todas las áreas...");
            frm.cb_puesto.removeAllItems();
            frm.cb_puesto.addItem("Todos...");
            QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto);
            frm.cb_turno.setSelectedItem("Todos...");
            DesignTabla.designAllEstadoCertificacion(frm);
        }

        if (e.getSource() == frm.consulta_mensual) {
            year = Calendar.getInstance().get(Calendar.YEAR);
            Graphics_Capacitacion.BarCharCertificados(frm);
            Graphics_Capacitacion.PieCharCertificados(frm);
            Graphics_Capacitacion.ChartCertificados(frm, year);
        }

        if (e.getSource() == frm.jMenuItem1) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_Cursos.Entrenamiento_General();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem13) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_Cursos.Entrenamiento_GeneralSalarios();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem14) {
            try {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if (GeneratorExcel_BDs.BD_CERTIFICADOS_BAJAS()) {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            } catch (IOException ex) {
                Logger.getLogger(CtrlCapacitacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == frm.jMenuItem2) {
            JComboBox<String> comboBox1 = new JComboBox<>();
            JComboBox<String> comboBox2 = new JComboBox<>();
            JTextArea textArea1 = new JTextArea();
            JScrollPane scrollPane1 = new JScrollPane(textArea1);
            scrollPane1.setPreferredSize(new Dimension(100, 50));

            JLabel label1 = new JLabel("Area:");
            JLabel label2 = new JLabel("Turno:");
            JLabel label3 = new JLabel("Observaciones:");

            QueryFunctions.LlenarComboBox("area", "nombre_Area", comboBox1);
            comboBox2.addItem("Todos...");
            QueryFunctions.LlenarComboBox("turno", "nombre_Turno", comboBox2);

            Object[] components = {label1, comboBox1, label2, comboBox2, label3, scrollPane1};

            int option = JOptionPane.showConfirmDialog(null, components, "Reporte Entrenamiento", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                String areaSeleccionada = (String) comboBox1.getSelectedItem();
                String turnoSeleccionado = (String) comboBox2.getSelectedItem();
                String observaciones = textArea1.getText().toUpperCase();
                if (turnoSeleccionado.equals("Todos...")) {
                    GeneratorPDF_Cursos.Entrenamiento_EspecificoArea(areaSeleccionada, observaciones);
                } else {
                    GeneratorPDF_Cursos.Entrenamiento_Especifico(areaSeleccionada, turnoSeleccionado, observaciones);
                }

            }
        }

        if (e.getSource() == frm.jMenuItem3) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_Certificados.Certificados_General();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem4) {
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                GeneratorPDF_Certificados.Certificados_Supervisores();
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } catch (Exception ex) {
                Logger.getLogger(CtrlEditarTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem5) {
            JComboBox<String> comboBox1 = new JComboBox<>();
            JComboBox<String> comboBox2 = new JComboBox<>();

            JLabel label1 = new JLabel("Area:");
            JLabel label2 = new JLabel("Turno:");

            QueryFunctions.LlenarComboBox("area", "nombre_Area", comboBox1);
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
                    GeneratorPDF_Certificados.Certificados_EspecificoArea(areaSeleccionada);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    GeneratorPDF_Certificados.Certificados_Especifico(areaSeleccionada, turnoSeleccionado);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }

            }
        }

        if (e.getSource() == frm.jMenuItem6) {
            JYearChooser chooser1 = new JYearChooser();
            JMonthChooser choorser2 = new JMonthChooser();

            JLabel label1 = new JLabel("Año:");
            JLabel label2 = new JLabel("Mes:");

            Object[] components = {label1, chooser1, label2, choorser2};

            int option = JOptionPane.showConfirmDialog(null, components, "Reporte Certificados", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                int añoSeleccionado = chooser1.getYear();
                int mesSeleccionado = choorser2.getMonth();
                if (GeneratorExcel_Certificados.FormatoDiplomas(añoSeleccionado, mesSeleccionado)) {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }

        if (e.getSource() == frm.jMenuItem7) {
            JYearChooser chooser1 = new JYearChooser();
            JLabel label1 = new JLabel("Año:");
            Object[] components = {label1, chooser1};

            int option = JOptionPane.showConfirmDialog(null, components, "Reporte Certificados", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                int añoSeleccionado = chooser1.getYear();
                if (GeneratorExcel_Certificados.FormatoDiplomasAnual(añoSeleccionado)) {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }

        if (e.getSource() == frm.jMenuItem8) {
            // Acción a realizar al hacer clic en "Aceptar"
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (GeneratorExcel_BDs.BD_CURSOS()) {
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem9) {
            try {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if (GeneratorExcel_BDs.BD_CERTIFICADOS()) {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            } catch (IOException ex) {
                Logger.getLogger(CtrlCapacitacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == frm.jMenuItem11) {
            // Acción a realizar al hacer clic en "Aceptar"
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (GeneratorPDF_Certificados.CertificadosResumenSupervisor()) {
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jMenuItem10) {
            JComboBox<String> comboBox1 = new JComboBox<>();
            JComboBox<String> comboBox2 = new JComboBox<>();

            JLabel label1 = new JLabel("Area:");
            JLabel label2 = new JLabel("Turno:");

            QueryFunctions.LlenarComboBox("area", "nombre_Area", comboBox1);
            comboBox2.addItem("Todos...");
            QueryFunctions.LlenarComboBox("turno", "nombre_Turno", comboBox2);

            Object[] components = {label1, comboBox1, label2, comboBox2};

            int option = JOptionPane.showConfirmDialog(null, components, "Reporte Flexibilidad", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                String areaSeleccionada = (String) comboBox1.getSelectedItem();
                String turnoSeleccionado = (String) comboBox2.getSelectedItem();
                if (turnoSeleccionado.equals("Todos...")) {
                    GeneratorPDF_Certificados.CertificadosResumenEspecifico(areaSeleccionada, turnoSeleccionado);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    GeneratorPDF_Certificados.CertificadosResumenEspecifico(areaSeleccionada, turnoSeleccionado);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }

        if (e.getSource() == frm.jMenuItem12) {
            JYearChooser chooser1 = new JYearChooser();
            JMonthChooser choorser2 = new JMonthChooser();

            JLabel label1 = new JLabel("Año:");
            JLabel label2 = new JLabel("Mes:");

            Object[] components = {label1, chooser1, label2, choorser2};

            int option = JOptionPane.showConfirmDialog(null, components, "Diplomas del Mes", JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Acción a realizar al hacer clic en "Aceptar"
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                int añoSeleccionado = chooser1.getYear();
                int mesSeleccionado = choorser2.getMonth();
                try {
                    DesingPDF_Diplomas.FormatoDiplomas(añoSeleccionado, mesSeleccionado);
                    frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } catch (DocumentException ex) {
                    Logger.getLogger(CtrlCapacitacion.class.getName()).log(Level.SEVERE, null, ex);
                }
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        if (e.getSource() == frm.jButton2) {
            DesignTabla.designAsistentesCursos(frm);
        }

        if (e.getSource() == frm.jButton3) {
            DesignTabla.designAsistentesCursosProceso(frm);
        }

        if (e.getSource() == frm.jButton1) {
            DesignTabla.designAllCursosActivos(frm);
        }

        if (e.getSource() == frm.jButton4) {
            int filaSeleccionada = frm.JTable_HistorialCurso.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un curso en la tabla.");
                return;
            }

            int idHCurso = Integer.parseInt(frm.JTable_HistorialCurso.getValueAt(filaSeleccionada, 0).toString());
            String idCursoS = String.valueOf(idHCurso);
            String nombreCurso = frm.txt_curso_titulo.getText();

            int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicional("historial_curso", "idCurso", "idHistorial_Curso", idCursoS));

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona dónde crear la carpeta del curso");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int resultado = chooser.showDialog(null, "Seleccionar");
            if (resultado != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
                return;
            }

            File carpetaBase = chooser.getSelectedFile();
            
            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            try (Connection con = conn.getConnection(); PreparedStatement psAsistentes = con.prepareStatement(
                    "SELECT idAsistentes_Curso, nombre_asistente FROM asistentes_curso WHERE idHistorial_Curso = ?"); PreparedStatement psDocs = con.prepareStatement(
                            "SELECT nombre_requerimiento, ruta_documento FROM requerimientos WHERE curso_idcurso = ?")) {

                psAsistentes.setInt(1, idHCurso);
                ResultSet rsAsistentes = psAsistentes.executeQuery();

                psDocs.setInt(1, idCurso);
                ResultSet rsDocs = psDocs.executeQuery();

                // Leer rutas de archivos desde la base
                List<File> archivosCurso = new ArrayList<>();
                boolean todosExisten = true; // bandera para verificar si todos existen

                while (rsDocs.next()) {
                    String rutaArchivo = rsDocs.getString("ruta_documento");

                    if (rutaArchivo != null) {
                        File archivo = new File(rutaArchivo);

                        if (archivo.exists()) {
                            archivosCurso.add(archivo);
                        } else {
                            todosExisten = false; // al menos uno no existe
                        }
                    } else {
                        System.err.println("Advertencia: Se encontró una ruta de documento NULL en la base de datos.");
                    }
                }

                // Mostrar un solo mensaje al final
                if (archivosCurso.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No se encontraron archivos en el expediente del curso.");
                } else if (todosExisten) {
                    JOptionPane.showMessageDialog(null, "Archivos creados en los expedientes del curso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Uno o más archivos del expediente no fueron encontrados.");
                }

                while (rsAsistentes.next()) {
                    String idAsistente = rsAsistentes.getString("idAsistentes_Curso").trim();
//                    String nombreAsistente = rsAsistentes.getString("nombre_asistente").trim()
//                            .replaceAll("[\\\\/:*?\"<>|]", "_");

                    File carpetaAsistente = new File(carpetaBase, idAsistente + "_" + nombreCurso.trim().replaceAll("[\\\\/:*?\"<>|]", "_"));
                    carpetaAsistente.mkdirs();

                    for (File archivoOriginal : archivosCurso) {
                        File destino = new File(carpetaAsistente, archivoOriginal.getName());
                        try (InputStream in = new FileInputStream(archivoOriginal); OutputStream out = new FileOutputStream(destino)) {

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = in.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(CtrlCapacitacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                JOptionPane.showMessageDialog(null, "Carpetas y archivos generados en:\n" + carpetaBase.getAbsolutePath());

            } catch (SQLException ex) {
                Logger.getLogger(CtrlCapacitacion.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage());
            }
        }

        if (e.getSource() == frm.jButton5) {
            int filaSeleccionada = frm.JTable_HistorialCurso.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona un curso en la tabla.");
                return;
            }
            String historialCurso = frm.JTable_HistorialCurso.getValueAt(
                    frm.JTable_HistorialCurso.getSelectedRow(), 0).toString();
            int idHistorialCurso = Integer.parseInt(historialCurso);

            frm.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) frm.jTree_Cursos.getLastSelectedPathComponent();
            String Node = selectedNode.getUserObject().toString();
            String consultaSemanas = QueryFunctions.CapturaCondicionalSimple("curso", "duracion_curso", "nombre_curso", Node);
            int semanas = Integer.parseInt(consultaSemanas);
            if (GeneratorExcel_Cursos.AvanceILUO(idHistorialCurso, semanas)) {
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frm.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == frm.JTable_HistorialCurso.getSelectionModel()) {
            if (!e.getValueIsAdjusting()) {
                if (frm.JTable_HistorialCurso.getSelectedRow() >= 0
                        && frm.JTable_HistorialCurso.getSelectedRow() < frm.JTable_HistorialCurso.getRowCount()) {
                    int Curso = Integer.parseInt(frm.JTable_HistorialCurso.getValueAt(
                            frm.JTable_HistorialCurso.getSelectedRow(), 0).toString());
                    ConcentradoAsistentesCurso(frm, Curso);
                    DesignTabla.designAsistentesCurso(frm, Curso);
                    int induccion = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                            "historial_curso", "idCurso",
                            "idHistorial_Curso", String.valueOf(Curso)));
                }
            }
        }

        if (e.getSource() == frm.jtable_certificados.getSelectionModel()) {
            if (!e.getValueIsAdjusting()) {
                if (frm.jtable_certificados.getSelectedRow() >= 0
                        && frm.jtable_certificados.getSelectedRow() < frm.jtable_certificados.getRowCount()) {
                    String Curso = frm.jtable_certificados.getValueAt(
                            frm.jtable_certificados.getSelectedRow(), 0).toString();
                    int idCertificado = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                            "certificado", "idCertificado", "nombre_certificado", Curso));
                    DesignTabla.designAsistentesCertificados(frm, idCertificado);
                }
            }
        }

        if (e.getSource() == frm.jTable_entrenamientos.getSelectionModel()) {
            if (!e.getValueIsAdjusting()) {
                if (frm.jTable_entrenamientos.getSelectedRow() >= 0
                        && frm.jTable_entrenamientos.getSelectedRow() < frm.jTable_entrenamientos.getRowCount()) {
                    String idAsistente = frm.jTable_entrenamientos.getValueAt(
                            frm.jTable_entrenamientos.getSelectedRow(), 0).toString();
                    DesignTabla.designCertificadosTrabajador(frm, idAsistente);
                    DesignTabla.designCursosTrabajador(frm, idAsistente);
                }
            }
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        if (e.getSource() == frm.jTree_Cursos) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) frm.jTree_Cursos.getLastSelectedPathComponent();
            if (selectedNode != null) {
                String Node = selectedNode.getUserObject().toString();
                int idTipoCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "tipo_curso", "idtipo_curso", "nombre_tipo", Node));
                int idArea = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "area", "idArea", "nombre_Area", Node));
                int idCurso = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "curso", "idCurso", "nombre_Curso", Node));

                if (idTipoCurso > idArea && idTipoCurso > idCurso) {
                    ConcentradoTipoCurso(frm, idTipoCurso, Node);
                    DesignTabla.designHistorialCursosTipo(frm, idTipoCurso);
                } else if (idCurso > idArea && idCurso > idTipoCurso) {
                    int idTipo = Integer.parseInt(QueryFunctions.CapturaCondicional(
                            "curso", "id_tipocurso", "idCurso", String.valueOf(idCurso)));
                    ConcentradoCurso(frm, idCurso, Node);
                    DesignTabla.designHistorialCurso(frm, idCurso);
                    if (idTipo == 2) {
                        DesignTabla.designHistorialCursoNombres(frm, idCurso);
                    } else {
                        DesignTabla.designHistorialCurso(frm, idCurso);
                    }
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == frm.jTabbedPane1) {
            int selectedIndex = frm.jTabbedPane1.getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    frm.jTable_certificadosTrabajador.clearSelection();
                    break;
                case 1:
                    frm.jTable_AsistentesCertificados.clearSelection();
                    break;
            }
        }
    }

    public void CargarComboBoxCapacitacion(IFrmCapacitacion frm) {
        frm.cb_area.addItem("Todas las áreas...");
        frm.cb_puesto.addItem("Todos...");
        frm.cb_turno.addItem("Todos...");
        frm.cb_area1.addItem("Todas las áreas...");
        frm.cb_puesto1.addItem("Todos...");
        frm.cb_turno1.addItem("Todos...");
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_area);
        QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto);
        QueryFunctions.LlenarComboBox("turno", "Nombre_Turno", frm.cb_turno);
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_area1);
        QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.cb_puesto1);
        QueryFunctions.LlenarComboBox("turno", "Nombre_Turno", frm.cb_turno1);
    }

    public void ConcentradoCursos(IFrmCapacitacion frm) {
        String consultaTotalActivos = QueryFunctions.CapturaCondicionalSimple("historial_curso", "COUNT(*)", "estado_curso", "Activo");
        String consultaTotalEntrenamiento = QueryFunctions.CapturaCondicionalSimple("view_asistentes_cursos", "COUNT(*)", "status_entrenamiento", "En Entrenamiento");
        String consultaTotalProceso = QueryFunctions.CapturaCondicionalSimple("view_asistentes_cursos", "COUNT(*)", "status_entrenamiento", "En Proceso de Certificación");

        String consultaTotalCursos = QueryFunctions.CapturaSimple("historial_curso", "COUNT(*)");
        String consultaTotalAsistentes = QueryFunctions.CapturaSimple("view_asistentes_cursos", "COUNT(*)");
        String consultaTotalMinutos = QueryFunctions.CapturaSimple("historial_curso", "FORMAT(SUM(horas_asistentes),0)");
        String formulahoras = "CONCAT(FLOOR(SUM(horas_asistentes) / 60),':', LPAD(SUM(horas_asistentes) % 60, 2, '0'))";
        String consultaTotalHoras = QueryFunctions.CapturaSimple("historial_curso", formulahoras);

        frm.jButton1.setText(String.format(cursosActivos, consultaTotalActivos));
        frm.jButton2.setText(String.format(asistentesEntrenamiento, consultaTotalEntrenamiento));
        frm.jButton3.setText(String.format(asistentesProceso, consultaTotalProceso));

        frm.txt_curso_titulo.setText("Historial de todos los cursos impartidos");
        frm.txt_total.setText(String.format(cantidadCursos, consultaTotalCursos));
        frm.txt_total1.setText(String.format(cantidadAsistentes, consultaTotalAsistentes));
        frm.txt_total2.setText(String.format(cantidadMinutos, consultaTotalMinutos));
        frm.txt_total3.setText(String.format(cantidadHoras, consultaTotalHoras));
        frm.txt_total4.setText("");
    }

    public void ConcentradoCurso(IFrmCapacitacion frm, int idCurso, String Curso) {
        String consultaTotalCursos = QueryFunctions.CapturaSimple("view_historialcursos WHERE idCurso = " + idCurso, "COUNT(*)");
        String consultaTotalAsistentes = QueryFunctions.CapturaSimple("view_asistentes_cursos WHERE idCurso = " + idCurso, "COUNT(*)");
        String consultaTotalMinutos = QueryFunctions.CapturaSimple("view_historialcursos WHERE idCurso = " + idCurso, "FORMAT(SUM(horas_asistentes),0)");
        String formulaHombrehoras = "CONCAT(FLOOR(SUM(horas_asistentes) / 60),':', LPAD(SUM(horas_asistentes) % 60, 2, '0'))";
        String consultaTotalHoras = QueryFunctions.CapturaSimple("view_historialcursos WHERE idCurso = " + idCurso, formulaHombrehoras);
        String consultaSemanas = QueryFunctions.CapturaCondicionalSimple("curso", "duracion_curso", "idCurso", String.valueOf(idCurso));

        frm.txt_curso_titulo.setText(Curso);
        frm.txt_total.setText(String.format(cantidadCursos, consultaTotalCursos));
        frm.txt_total1.setText(String.format(cantidadAsistentes, consultaTotalAsistentes));
        frm.txt_total2.setText(String.format(cantidadMinutos, consultaTotalMinutos));
        frm.txt_total3.setText(String.format(cantidadHoras, consultaTotalHoras));
        frm.txt_total4.setText(String.format(cantidadSemanas, consultaSemanas));
    }

    public void ConcentradoTipoCurso(IFrmCapacitacion frm, int idTipoCurso, String TipoCurso) {
        String consultaTotalCursos = QueryFunctions.CapturaSimple("view_historialcursos WHERE id_tipocurso = " + idTipoCurso, "COUNT(*)");
        String consultaTotalAsistentes = QueryFunctions.CapturaSimple("view_asistentes_cursos WHERE id_tipocurso = " + idTipoCurso, "COUNT(*)");
        String consultaTotalMinutos = QueryFunctions.CapturaSimple("view_historialcursos WHERE id_tipocurso = " + idTipoCurso, "FORMAT(SUM(horas_asistentes),0)");
        String formulaHombrehoras = "CONCAT(FLOOR(SUM(horas_asistentes) / 60),':', LPAD(SUM(horas_asistentes) % 60, 2, '0'))";
        String consultaTotalHoras = QueryFunctions.CapturaSimple("view_historialcursos WHERE id_tipocurso = " + idTipoCurso, formulaHombrehoras);

        frm.txt_curso_titulo.setText(TipoCurso);
        frm.txt_total.setText(String.format(cantidadCursos, consultaTotalCursos));
        frm.txt_total1.setText(String.format(cantidadAsistentes, consultaTotalAsistentes));
        frm.txt_total2.setText(String.format(cantidadMinutos, consultaTotalMinutos));
        frm.txt_total3.setText(String.format(cantidadHoras, consultaTotalHoras));
        frm.txt_total4.setText(String.format(""));
    }

    public void ConcentradoAsistentesCurso(IFrmCapacitacion frm, int idCurso) {
        String cantidadAsistentesEsperados = "Asistencia Esperada: %s";
        String cantidadAsistentesReales = "Asistencia Actual: %s";
        String cantidadTiempoCurso = "Duración del Curso: %s";
        String cantidadTiempoHombre = "Horas Hombre: %s";

        String consultaAsitentesEsperados = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "asistentes_esperados",
                "idHistorial_Curso", String.valueOf(idCurso));
        String consultaAsitentesReales = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", "num_asistentes",
                "idHistorial_Curso", String.valueOf(idCurso));
        String formulaCursohoras = "CONCAT(FLOOR(SUM(duracion_curso) / 60),':', LPAD(SUM(duracion_curso) % 60, 2, '0'))";
        String formulaHombrehoras = "CONCAT(FLOOR(SUM(horas_asistentes) / 60),':', LPAD(SUM(horas_asistentes) % 60, 2, '0'))";
        String consultaTiempoCursoHoras = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", formulaCursohoras,
                "idHistorial_Curso", String.valueOf(idCurso));
        String consultaTiempoHombreHoras = QueryFunctions.CapturaCondicionalSimple(
                "historial_curso", formulaHombrehoras, "idHistorial_Curso", String.valueOf(idCurso));

        frm.txt_esperado.setText(String.format(cantidadAsistentesEsperados, consultaAsitentesEsperados));
        frm.txt_asistencia.setText(String.format(cantidadAsistentesReales, consultaAsitentesReales));
        frm.txt_esperado1.setText(String.format(cantidadTiempoCurso, consultaTiempoCursoHoras));
        frm.txt_asistencia1.setText(String.format(cantidadTiempoHombre, consultaTiempoHombreHoras));
    }

    public void ConcentradoCertificados(IFrmCapacitacion frm) {
        String consultaCertificados = "SELECT SUM(certificaciones) FROM sistema_capacitacion.view_reporte_certificados";
        String consultaFelixbilidad = "SELECT ROUND(AVG(Flexibilidad), 2) AS FlexibilidadPromedio FROM (SELECT nombre_area AS Area,\n"
                + "nombre_turno AS Turno,COUNT(folio_trabajador) AS Plantilla,\n"
                + "SUM(certificados) AS Certificados,ROUND((SUM(certificados) / COUNT(folio_trabajador)) * 100, 2) AS Flexibilidad\n"
                + "FROM sistema_capacitacion.view_reporte_certificados GROUP BY nombre_area , nombre_turno) AS subconsulta";

        String consultaEntrenamiento = "SELECT COUNT(*)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '';";
        String porcentajeEntrenamiento = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '';";
        String consultaprimer = "SELECT  COUNT(*)\n "
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento = 'PRIMERO';";
        String porcentajeprimer = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM  view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento = 'PRIMERO';";
        String consultasegundo = "SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento != 'PRIMERO';";
        String porcentajesegundo = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM\n view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento != 'PRIMERO';";
        String consultacruzado = "SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento = 'En Entrenamiento en otro Puesto';";
        String porcentajecruzado = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM\n view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento = 'En Entrenamiento en otro Puesto';";

        frm.txt_Entrenamiento.setText(String.format(personalEntrenamiento,
                QueryFunctions.CapturaDirecta(consultaEntrenamiento) + "-"
                + QueryFunctions.CapturaDirecta(porcentajeEntrenamiento) + "%"));
        frm.txt_entrenamientoPrimero.setText(String.format(primerEntrenamiento,
                QueryFunctions.CapturaDirecta(consultaprimer) + "-"
                + QueryFunctions.CapturaDirecta(porcentajeprimer) + "%"));
        frm.txt_entrenamientoSegundo.setText(String.format(segundoEntrenamiento,
                QueryFunctions.CapturaDirecta(consultasegundo) + "-"
                + QueryFunctions.CapturaDirecta(porcentajesegundo) + "%"));
        frm.txt_entrenamientoCruzado.setText(String.format(entreamientoCruzado,
                QueryFunctions.CapturaDirecta(consultacruzado) + "-"
                + QueryFunctions.CapturaDirecta(porcentajecruzado) + "%"));

        frm.txt_totalCertificados.setText(String.format(certificacionesTotal,
                QueryFunctions.CapturaDirecta(consultaCertificados)));
        frm.txt_totalFlexibilidad.setText(String.format(porcentajeFlexibilidad,
                QueryFunctions.CapturaDirecta(consultaFelixbilidad) + "%"));
    }

    public void ConcentradoCertificadosGeneral(IFrmCapacitacion frm, int year) {

        frm.txt_totalAnual.setText(String.format(certificacionesTotal,
                QueryFunctions.CapturaDirecta("SELECT COUNT(*) FROM view_asistentes_certificados\n"
                        + "WHERE YEAR(fecha_termino) =" + year)));

        frm.txt_TotalPrimero.setText(String.format(certificadosNuevoIngreso,
                QueryFunctions.CapturaDirecta("SELECT COUNT(*) FROM view_asistentes_certificados\n"
                        + "LEFT JOIN view_lbu ON view_asistentes_certificados.folio_trabajador=view_lbu.folio_trabajador\n"
                        + "WHERE YEAR(fecha_termino) = " + year + " AND estado_certificacion = 'Primero'")));

        frm.txt_totalSegundo.setText(String.format(certificadosSegundoPuesto,
                QueryFunctions.CapturaDirecta("SELECT COUNT(*) FROM view_asistentes_certificados\n"
                        + "LEFT JOIN view_lbu ON view_asistentes_certificados.folio_trabajador=view_lbu.folio_trabajador\n"
                        + "WHERE YEAR(fecha_termino) = " + year + " AND estado_certificacion != 'Primero'")));

        frm.txt_totalBaja.setText(String.format(certificadosBaja,
                QueryFunctions.CapturaDirecta("SELECT COUNT(*) FROM view_asistentes_certificados\n"
                        + "JOIN view_lbu ON view_asistentes_certificados.folio_trabajador = view_lbu.folio_trabajador\n"
                        + "WHERE YEAR(fecha_termino) = " + year + " AND view_lbu.folio_trabajador IS NULL")));
    }

    public void ConcentradoCertificadosProcesos(IFrmCapacitacion frm, String proceso, int year) {
        Graphics_Capacitacion.BarCharCertificadosProcesos(frm, proceso);
        Graphics_Capacitacion.PieCharCertificadosProcesos(frm, proceso);
        Graphics_Capacitacion.ChartCertificadosProcesos(frm, year, proceso);

        String consultaCertificados = "SELECT SUM(certificaciones) FROM sistema_capacitacion.view_reporte_certificados WHERE tipo_proceso = " + proceso;
        String consultaFelixbilidad = "SELECT ROUND(AVG(Flexibilidad), 2) AS FlexibilidadPromedio FROM (SELECT nombre_area AS Area,\n"
                + "nombre_turno AS Turno,COUNT(folio_trabajador) AS Plantilla,\n"
                + "SUM(certificados) AS Certificados,ROUND((SUM(certificados) / COUNT(folio_trabajador)) * 100, 2) AS Flexibilidad\n"
                + "FROM sistema_capacitacion.view_reporte_certificados WHERE tipo_proceso = " + proceso + "\n"
                + "GROUP BY nombre_area , nombre_turno) AS subconsulta";
        String consultaCertificadosAnual = "SELECT COUNT(*) FROM view_asistentes_certificados\n"
                + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_trabajador = view_lbu.folio_trabajador WHERE YEAR(fecha_termino) = "
                + String.valueOf(year) + " AND tipo_proceso =" + proceso;
        String consultaCertificadosNuevo = "SELECT count(*) FROM view_asistentes_certificados \n"
                + "JOIN view_lbu ON view_asistentes_certificados.Folio_trabajador=view_lbu.folio_trabajador \n"
                + "WHERE YEAR(fecha_termino) = " + String.valueOf(year) + " AND estado_certificacion = 'Primero' AND tipo_proceso = " + proceso;
        String consultaCertificadosSegundo = "SELECT count(*) FROM view_asistentes_certificados \n"
                + "JOIN view_lbu ON view_asistentes_certificados.Folio_trabajador=view_lbu.folio_trabajador \n"
                + "WHERE YEAR(fecha_termino) = " + String.valueOf(year) + " AND estado_certificacion != 'Primero' AND tipo_proceso = " + proceso;
        String consultaCertificadosBaja = "SELECT count(*) FROM view_asistentes_certificados \n"
                + "JOIN view_lbu ON view_asistentes_certificados.Folio_trabajador=view_lbu.folio_trabajador \n"
                + "WHERE YEAR(fecha_termino) = " + String.valueOf(year) + " AND view_lbu.folio_trabajador IS NULL AND tipo_proceso = " + proceso;

        frm.txt_totalCertificados.setText(String.format(certificacionesTotal,
                QueryFunctions.CapturaDirecta(consultaCertificados)));
        frm.txt_totalFlexibilidad.setText(String.format(porcentajeFlexibilidad,
                QueryFunctions.CapturaDirecta(consultaFelixbilidad) + "%"));

        frm.txt_totalAnual.setText(String.format(certificadosAnual,
                QueryFunctions.CapturaDirecta(consultaCertificadosAnual)));
        frm.txt_TotalPrimero.setText(String.format(certificadosNuevoIngreso,
                QueryFunctions.CapturaDirecta(consultaCertificadosNuevo)));
        frm.txt_totalSegundo.setText(String.format(certificadosSegundoPuesto,
                QueryFunctions.CapturaDirecta(consultaCertificadosSegundo)));
        frm.txt_totalBaja.setText(String.format(certificadosBaja,
                QueryFunctions.CapturaDirecta(consultaCertificadosBaja)));
    }

    public void ConcentradoCertificadosBusqueda(IFrmCapacitacion frm, int year, String area, String puesto, String turno, int i) {
        SwingWorker<Void, Void> workerGraficas = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Graphics_Capacitacion.BarCharCertificadosB(frm, area, puesto, turno, i);
                Graphics_Capacitacion.PieCharCertificadosB(frm, area, puesto, turno, i);
                Graphics_Capacitacion.ChartCertificadosB(frm, year, area, puesto, turno, i);
                return null;
            }
        };

        String consultaCertificados = "SELECT SUM(certificados) FROM sistema_capacitacion.view_reporte_certificados\n";
        String consultaFelixbilidad = "SELECT ROUND(AVG(Flexibilidad), 2) AS FlexibilidadPromedio FROM (SELECT nombre_area AS Area,\n"
                + "nombre_Puesto AS Puesto, nombre_turno AS Turno,COUNT(folio_trabajador) AS Plantilla,\n"
                + "SUM(certificados) AS Certificados,ROUND(SUM(certificados) / COUNT(folio_trabajador) * 100, 2) AS Flexibilidad\n"
                + "FROM sistema_capacitacion.view_reporte_certificados\n";
        String consulta = "SELECT COUNT(*) FROM view_asistentes_certificados\n"
                + "LEFT JOIN view_lbu ON view_asistentes_certificados.Folio_trabajador = view_lbu.folio_trabajador\n";

        String consultaEntrenamiento = "SELECT COUNT(*)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' \n";
        String porcentajeEntrenamiento = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' \n";
        String consultaprimer = "SELECT  COUNT(*)\n "
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento = 'PRIMERO' \n";
        String porcentajeprimer = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM  view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento = 'PRIMERO' \n";
        String consultasegundo = "SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento != 'PRIMERO' \n";
        String porcentajesegundo = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM\n view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento != '' AND tipo_entrenamiento != 'PRIMERO' \n";
        String consultacruzado = "SELECT COUNT(*) FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento = 'En Entrenamiento en otro Puesto' \n";
        String porcentajecruzado = "SELECT ROUND((COUNT(*) * 100 / (SELECT COUNT(*) FROM\n view_reporte_entrenamiento)),2)\n"
                + "FROM sistema_capacitacion.view_reporte_entrenamiento\n"
                + "WHERE Entrenamiento = 'En Entrenamiento en otro Puesto' \n";

        SwingWorker<Void, Void> workerEstado = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                switch (i) {
                    case 1:
                        frm.txt_totalCertificados.setText(String.format(certificacionesTotal,
                                QueryFunctions.CapturaDirecta(consultaCertificados + " WHERE nombre_area = '" + area + "'")));
                        frm.txt_totalFlexibilidad.setText(String.format(porcentajeFlexibilidad,
                                QueryFunctions.CapturaDirecta(consultaFelixbilidad + " WHERE nombre_area = '" + area + "' "
                                        + "GROUP BY nombre_area) AS subconsulta") + "%"));

                        frm.txt_totalAnual.setText(String.format(certificadosAnual,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'")));
                        frm.txt_TotalPrimero.setText(String.format(certificadosNuevoIngreso,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "' "
                                        + " AND estado_certificacion = 'Primero'")));
                        frm.txt_totalSegundo.setText(String.format(certificadosSegundoPuesto,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "' "
                                        + " AND estado_certificacion != 'Primero'")));
                        frm.txt_totalBaja.setText(String.format(certificadosBaja,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "' "
                                        + "AND view_lbu.folio_trabajador IS NULL")));

                        frm.txt_Entrenamiento.setText(String.format(personalEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaEntrenamiento
                                        + " AND nombre_area = '" + area + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeEntrenamiento
                                        + " AND nombre_area = '" + area + "'") + "%"));
                        frm.txt_entrenamientoPrimero.setText(String.format(primerEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaprimer
                                        + " AND nombre_area = '" + area + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeprimer
                                        + " AND nombre_area = '" + area + "'") + "%"));
                        frm.txt_entrenamientoSegundo.setText(String.format(segundoEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultasegundo
                                        + " AND nombre_area = '" + area + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajesegundo
                                        + " AND nombre_area = '" + area + "'") + "%"));
                        frm.txt_entrenamientoCruzado.setText(String.format(entreamientoCruzado,
                                QueryFunctions.CapturaDirecta(consultacruzado
                                        + " AND nombre_area = '" + area + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajecruzado
                                        + " AND nombre_area = '" + area + "'") + "%"));
                        break;
                    case 2:
                        frm.txt_totalCertificados.setText(String.format(certificacionesTotal,
                                QueryFunctions.CapturaDirecta(consultaCertificados + " WHERE nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'")));
                        frm.txt_totalFlexibilidad.setText(String.format(porcentajeFlexibilidad,
                                QueryFunctions.CapturaDirecta(consultaFelixbilidad + " WHERE nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "' "
                                        + "GROUP BY nombre_area , nombre_turno) AS subconsulta") + "%"));
                        frm.txt_totalAnual.setText(String.format(certificadosAnual,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'")));
                        frm.txt_TotalPrimero.setText(String.format(certificadosNuevoIngreso,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'"
                                        + " AND estado_certificacion = 'Primero'")));
                        frm.txt_totalSegundo.setText(String.format(certificadosSegundoPuesto,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'"
                                        + " AND estado_certificacion != 'Primero'")));
                        frm.txt_totalBaja.setText(String.format(certificadosBaja,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'"
                                        + " AND view_lbu.folio_trabajador IS NULL")));

                        frm.txt_Entrenamiento.setText(String.format(personalEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaEntrenamiento
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeEntrenamiento
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        frm.txt_entrenamientoPrimero.setText(String.format(primerEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaprimer
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeprimer
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        frm.txt_entrenamientoSegundo.setText(String.format(segundoEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultasegundo
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajesegundo
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        frm.txt_entrenamientoCruzado.setText(String.format(entreamientoCruzado,
                                QueryFunctions.CapturaDirecta(consultacruzado
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajecruzado
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        break;
                    case 3:
                        frm.txt_totalCertificados.setText(String.format(certificacionesTotal,
                                QueryFunctions.CapturaDirecta(consultaCertificados + " WHERE nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'")));
                        frm.txt_totalFlexibilidad.setText(String.format(porcentajeFlexibilidad,
                                QueryFunctions.CapturaDirecta(consultaFelixbilidad + " WHERE nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "' GROUP BY nombre_area , nombre_puesto) AS subconsulta") + "%"));
                        frm.txt_totalAnual.setText(String.format(certificadosAnual,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'")));
                        frm.txt_TotalPrimero.setText(String.format(certificadosNuevoIngreso,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "' "
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'"
                                        + " AND estado_certificacion = 'Primero'")));
                        frm.txt_totalSegundo.setText(String.format(certificadosSegundoPuesto,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'"
                                        + " AND estado_certificacion != 'Primero' \n")));
                        frm.txt_totalBaja.setText(String.format(certificadosBaja,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'"
                                        + " AND view_lbu.folio_trabajador IS NULL")));

                        frm.txt_Entrenamiento.setText(String.format(personalEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaEntrenamiento
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeEntrenamiento
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "%"));
                        frm.txt_entrenamientoPrimero.setText(String.format(primerEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaprimer
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeprimer
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "%"));
                        frm.txt_entrenamientoSegundo.setText(String.format(segundoEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultasegundo
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajesegundo
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "%"));
                        frm.txt_entrenamientoCruzado.setText(String.format(entreamientoCruzado,
                                QueryFunctions.CapturaDirecta(consultacruzado
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajecruzado
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'") + "%"));
                        break;
                    case 4:
                        frm.txt_totalCertificados.setText(String.format(certificacionesTotal,
                                QueryFunctions.CapturaDirecta(consultaCertificados + " WHERE nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'")));
                        frm.txt_totalFlexibilidad.setText(String.format(porcentajeFlexibilidad,
                                QueryFunctions.CapturaDirecta(consultaFelixbilidad + " WHERE nombre_area = '" + area + "'\n"
                                        + " AND nombre_puesto = '" + puesto + "' "
                                        + " AND nombre_turno = '" + turno + "') AS subconsulta") + "%"));
                        frm.txt_totalAnual.setText(String.format(certificadosAnual,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'")));
                        frm.txt_TotalPrimero.setText(String.format(certificadosNuevoIngreso,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'"
                                        + " AND estado_certificacion = 'Primero'")));
                        frm.txt_totalSegundo.setText(String.format(certificadosSegundoPuesto,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "' "
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "' "
                                        + " AND estado_certificacion != 'Primero' \n")));
                        frm.txt_totalBaja.setText(String.format(certificadosBaja,
                                QueryFunctions.CapturaDirecta(consulta + " WHERE YEAR(fecha_termino) = " + year
                                        + " AND view_asistentes_certificados.nombre_area = '" + area + "'"
                                        + " AND view_asistentes_certificados.nombre_puesto = '" + puesto + "'"
                                        + " AND view_asistentes_certificados.nombre_turno = '" + turno + "'"
                                        + " AND view_lbu.folio_trabajador IS NULL")));

                        frm.txt_Entrenamiento.setText(String.format(personalEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaEntrenamiento
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeEntrenamiento
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        frm.txt_entrenamientoPrimero.setText(String.format(primerEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultaprimer
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajeprimer
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        frm.txt_entrenamientoSegundo.setText(String.format(segundoEntrenamiento,
                                QueryFunctions.CapturaDirecta(consultasegundo
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajesegundo
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        frm.txt_entrenamientoCruzado.setText(String.format(entreamientoCruzado,
                                QueryFunctions.CapturaDirecta(consultacruzado
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "-"
                                + QueryFunctions.CapturaDirecta(porcentajecruzado
                                        + " AND nombre_area = '" + area + "'"
                                        + " AND nombre_puesto = '" + puesto + "'"
                                        + " AND nombre_turno = '" + turno + "'") + "%"));
                        break;
                }
                return null;
            }
        };
        workerGraficas.execute();
        workerEstado.execute();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
