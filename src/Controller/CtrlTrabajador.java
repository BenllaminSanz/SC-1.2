//Controlador de la vista Trabajador
//Recibe la estructura grafica de la vista Trabajador
/*Esta pantalla da una muestra general de los trabajares y sus datos,
permite acceder a los mismos y modificarlos, asi como hacer consultas la base
de datos de los trabajadores, asi como facilitar herramientas para adminitar de
manera eficiente estos datos*/
package Controller;

import ContextController.ContextoEditarAdministrativo;
import ContextController.ContextoEditarBaja;
import ContextController.ContextoEditarTrabajador;
import ContextController.ContextoTrabajador;
import Documents.GeneratorExcel_BDs;
import Documents.GeneratorPDF_LBU;
import Functions.DateTools;
import Functions.QueryFunctions;
import Functions.ViewTools;
import Functions.TextPrompt;
import Model.Administrativos;
import Model.Bajas;
import Model.Brigadas;
import Model.Brigadistas;
import Querys.ConsultasBaja;
import Querys.ConsultasSupervisor;
import Model.Supervisor;
import Model.Trabajador;
import Model.Turno;
import Querys.ConsultasAdministrativo;
import Querys.ConsultasBrigadas;
import Querys.ConsultasBrigadistas;
import Querys.ConsultasTrabajador;
import Subviews.ConfiguracionColumnasExcelTrabajadores;
import Subviews.ConfiguracionNivelesSalarios;
import View.FrmAdministrador;
import Tables.DesignTabla;
import Tables.TableBrigadistas;
import Tables.TableBrigadistasBrigada;
import View.IFrmTrabajador;
import com.toedter.calendar.JDateChooser;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

public class CtrlTrabajador implements ActionListener, KeyListener, MouseListener, ListSelectionListener {

    private final Trabajador modTrabajador = new Trabajador();
    private final ConsultasTrabajador conTrabajador = new ConsultasTrabajador();
    private final Bajas modBajas = new Bajas();
    private final ConsultasBaja conBajas = new ConsultasBaja();
    private final Supervisor modSupervisor = new Supervisor();
    private final ConsultasSupervisor conSupervisor = new ConsultasSupervisor();
    private final Administrativos modAdministrativo = new Administrativos();
    private final ConsultasAdministrativo conAdministrativo = new ConsultasAdministrativo();
    private final Brigadas modBrigada = new Brigadas();
    private final ConsultasBrigadas conBrigadas = new ConsultasBrigadas();
    private final Brigadistas modBrigadista = new Brigadistas();
    private final ConsultasBrigadistas conBrigadistas = new ConsultasBrigadistas();
    private final IFrmTrabajador frmTrabajador;
    private final FrmAdministrador frmAdministrador;

    //Variables para escoger un archivi Excel para cargar trabajadores
    private final JFileChooser fileChooser = new JFileChooser();
    private File archivo;
    private final FileNameExtensionFilter filtro = new FileNameExtensionFilter(
            "Archivo Excel", "xlsx", "xls");

    //Relacionamos el controlador con la vista
    public CtrlTrabajador(ContextoTrabajador contexto) {
        this.frmTrabajador = contexto.ventanaTrabajador;
        this.frmAdministrador = contexto.ventanaAdministrador;
        this.frmTrabajador.btn_AgregarTrabajador.addActionListener(this);
        this.frmTrabajador.btn_RefreshTabla.addActionListener(this);
        this.frmTrabajador.btn_RefreshTabla2.addActionListener(this);
        this.frmTrabajador.Item_NuevoTrabajador.addActionListener(this);
        this.frmTrabajador.Item_TrabajadoresExcel.addActionListener(this);
        this.frmTrabajador.Item_TrabajadoresExcel_NO.addActionListener(this);
        this.frmTrabajador.Item_EditarLecturaExcel.addActionListener(this);
        this.frmTrabajador.ItemAgregar.addActionListener(this);
        this.frmTrabajador.ItemModificar.addActionListener(this);
        this.frmTrabajador.ItemEliminar.addActionListener(this);
        this.frmTrabajador.Item_Lista_Nuevos_Tbr.addActionListener(this);
        this.frmTrabajador.Item_Lista_Bajas_Tbr.addActionListener(this);
        this.frmTrabajador.ItemEliminar_Baja.addActionListener(this);
        this.frmTrabajador.ItemRestaurar.addActionListener(this);
        this.frmTrabajador.ItemModificar_Baja.addActionListener(this);
        this.frmTrabajador.txt_buscar_Tbr.addKeyListener(this);
        this.frmTrabajador.txt_buscar_bajas.addKeyListener(this);
        this.frmTrabajador.ItemAgregarS.addActionListener(this);
        this.frmTrabajador.ItemModificarS.addActionListener(this);
        this.frmTrabajador.ItemEliminarS.addActionListener(this);
        this.frmTrabajador.btn_guardar_sup.addActionListener(this);
        this.frmTrabajador.btn_limpiar.addActionListener(this);
        this.frmTrabajador.MenuEstado.addMouseListener(this);
        this.frmTrabajador.CheckActivo.addActionListener(this);
        this.frmTrabajador.CheckAccidente.addActionListener(this);
        this.frmTrabajador.CheckIncapacidad.addActionListener(this);
        this.frmTrabajador.CheckMaternidad.addActionListener(this);
        this.frmTrabajador.CheckLactancia.addActionListener(this);
        this.frmTrabajador.CheckEnfermedad.addActionListener(this);
        this.frmTrabajador.CheckTrayecto.addActionListener(this);
        this.frmTrabajador.CheckBaja.addActionListener(this);
        this.frmTrabajador.btn1.addActionListener(this);
        this.frmTrabajador.btn2.addActionListener(this);
        this.frmTrabajador.btn3.addActionListener(this);
        this.frmTrabajador.btn4.addActionListener(this);
        this.frmTrabajador.btn5.addActionListener(this);
        this.frmTrabajador.btn6.addActionListener(this);
        this.frmTrabajador.btn7.addActionListener(this);
        this.frmTrabajador.item_AgregarAdministrativo.addActionListener(this);
        this.frmTrabajador.item_ModificarAdministrativo.addActionListener(this);
        this.frmTrabajador.item_EliminarAdministrativo.addActionListener(this);
        this.frmTrabajador.item_AgregarBrigada.addActionListener(this);
        this.frmTrabajador.btn_agregarBrigada.addActionListener(this);
        this.frmTrabajador.item_ModificarBrigada.addActionListener(this);
        this.frmTrabajador.item_EliminarBrigada.addActionListener(this);
        this.frmTrabajador.jTable_brigadas.getSelectionModel().addListSelectionListener(this);
        this.frmTrabajador.btn_RefreshTabla3.addActionListener(this);
        this.frmTrabajador.item_EliminarBrigadista.addActionListener(this);
        this.frmTrabajador.item_nivelesSalarios.addActionListener(this);
        this.frmTrabajador.btn_ListT.addActionListener(this);
    }

    //Funcion de inicio
    public void iniciar() {
        //Llamado a los metodos de diseño de los paneles
        DesignTabla.designTrabajador(frmTrabajador);
        DesignTabla.designBajas(frmTrabajador);
        DesingSupervisor();
        DesignTabla.designAdministrativos(frmTrabajador);
        DesignTabla.designBrigadas(frmTrabajador);
        DesignTabla.designBrigadistas(frmTrabajador);
        setPlaceholderText();
        //Caracterisitcas de la Ventana y componentes
        setWindowProperties();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Accion para los botones que abren la ventana Areas y Puestos
        List<Object> validSourcesAgregarTrabajador = Arrays.asList(frmTrabajador.btn_AgregarTrabajador, frmTrabajador.ItemAgregar, frmTrabajador.Item_NuevoTrabajador);
        if (validSourcesAgregarTrabajador.contains(e.getSource())) {
            try {
                String txtBoton = "Guardar";
                ContextoEditarTrabajador contexto = new ContextoEditarTrabajador(txtBoton, null);
                CtrlEditarTrabajador ctrl = new CtrlEditarTrabajador(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmAdministrador, contexto.ventanaEditarTrabajador);
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE,
                        "Error al llamar el metodo EditarTrabajador: ", ex);
            }
        }

        //Accion para Cargar Trabajadores desde archivo Excel
        List<Object> validSourcesAgregarTrabajadorExcel = Arrays.asList(frmTrabajador.Item_TrabajadoresExcel, frmTrabajador.Item_TrabajadoresExcel_NO);
        if (validSourcesAgregarTrabajadorExcel.contains(e.getSource())) {
            fileChooser.setFileFilter(filtro);
            if (fileChooser.showOpenDialog(frmTrabajador) == JFileChooser.APPROVE_OPTION) {
                int option = JOptionPane.showConfirmDialog(frmTrabajador,
                        "Recuerde guardar y cerrar el Excel de consulta\n"
                        + "¿Desea actualizar los trabajadores ya existentes en la base de datos?\n"
                        + "Esto modificara los trabajadores con la información de archivo Excel seleccionado.",
                        "Actualizar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                archivo = fileChooser.getSelectedFile();
                String ruta = archivo.getAbsolutePath();

                frmTrabajador.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if (conTrabajador.cargarExcel(ruta, option)) {
                    frmTrabajador.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    int registros = conTrabajador.registros;
                    JOptionPane.showMessageDialog(null,
                            "Carga de exitosa, " + registros + " trabajadores nuevos.");
                    actualizarVistaTrabajador();
                    DesignTabla.designTrabajador(frmTrabajador);
                }
            }
        }

        //Accion para Cargar Trabajadores desde archivo Excel
        if (e.getSource() == frmTrabajador.Item_EditarLecturaExcel) {
            ConfiguracionColumnasExcelTrabajadores configuracion = new ConfiguracionColumnasExcelTrabajadores();
            configuracion.setVisible(true);
        }

        //Accion para Cargar Trabajadores desde archivo Excel
        if (e.getSource() == frmTrabajador.item_nivelesSalarios) {
            ConfiguracionNivelesSalarios configuracion = new ConfiguracionNivelesSalarios();
            configuracion.setVisible(true);
        }

        //Abre la ventana para modificar el trabajador seleccionado
        if (e.getSource() == frmTrabajador.ItemModificar) {
            try {
                String folio = frmTrabajador.jTable_Trabajadores.getValueAt(
                        frmTrabajador.jTable_Trabajadores.getSelectedRow(), 0).toString();
                String txtBoton = "Actualizar";
                ContextoEditarTrabajador contexto = new ContextoEditarTrabajador(txtBoton, folio);
                CtrlEditarTrabajador ctrl = new CtrlEditarTrabajador(contexto);
                ctrl.iniciar();
                ViewTools.Centrar(frmAdministrador, contexto.ventanaEditarTrabajador);
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE,
                        "Error al llamar el metodo EditarTrabajador: ", ex);
            }
        }

        //Elimina un trabajador seleccionado en la tabla Trabajador
        if (e.getSource() == frmTrabajador.ItemEliminar) {
            String folio = frmTrabajador.jTable_Trabajadores.getValueAt(
                    frmTrabajador.jTable_Trabajadores.getSelectedRow(), 0).toString();
            modTrabajador.setFolio_Trabajador(folio);

            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja este Trabajador?");
            if (option == JOptionPane.OK_OPTION && conTrabajador.eliminar(modTrabajador)) {
                JOptionPane.showMessageDialog(null, "Baja de trabajador realizada");
                actualizarVistaTrabajador();
                DesignTabla.designBajas(frmTrabajador);
            }
        }

        //Restaura un trabajador seleccionado en la tabla Bajas
        if (e.getSource() == frmTrabajador.ItemRestaurar) {
            String folio = frmTrabajador.jTable_Bajas.getValueAt(
                    frmTrabajador.jTable_Bajas.getSelectedRow(), 0).toString();
            modBajas.setFolio_Trabajador(folio);
            if (JOptionPane.showConfirmDialog(null, "¿Seguro de restaurar este Trabajador?") == JOptionPane.OK_OPTION 
                    && conBajas.buscar(modBajas)) {
                modTrabajador.setFolio_Trabajador(modBajas.getFolio_Trabajador());
                modTrabajador.setNombre_Trabajador(modBajas.getNombre_Trabajador());
                modTrabajador.setCURP_Trabajador(modBajas.getCURP_Trabajador());
                modTrabajador.setRFC_Trabajador(modBajas.getRFC_Trabajador());
                modTrabajador.setIMSS_Trabajador(modBajas.getIMSS_Trabajador());
                modTrabajador.setFecha_Antiguedad(modBajas.getFecha_Antiguedad());
                modTrabajador.setÁrea_Trabajador(modBajas.getÁrea_Trabajador());
                modTrabajador.setPuesto_Trabajador(modBajas.getPuesto_Trabajador());
                modTrabajador.setTurno_Trabajador(modBajas.getTurno_Trabajador());
                modTrabajador.setSalarioDiario_Trabajador(modBajas.getSalarioDiario_Trabajador());
                modTrabajador.setFecha_Cumpleaños(modBajas.getFecha_Cumpleaños());
                modTrabajador.setEmail_Trabajador(modBajas.getEmail_Trabajador());
                modTrabajador.setTel_Trabajador(modBajas.getTeléfono_Trabajador());
                modTrabajador.setComentario(modBajas.getComentario());

                if (conTrabajador.registrar(modTrabajador) && conBajas.eliminar(modBajas)) {
                    JOptionPane.showMessageDialog(null, "Trabajador: "
                            + modBajas.getFolio_Trabajador() + " restaurado");
                    actualizarVistaTrabajador();
                    DesignTabla.designTrabajador(frmTrabajador);
                    DesignTabla.designBajas(frmTrabajador);
                }
            }
        }

        //Elimina un trabajador seleccionado en la tabla baja permanentemente
        if (e.getSource() == frmTrabajador.ItemEliminar_Baja) {
            String folio = frmTrabajador.jTable_Bajas.getValueAt(
                    frmTrabajador.jTable_Bajas.getSelectedRow(), 0).toString();
            modBajas.setFolio_Trabajador(folio);

            int option = JOptionPane.showConfirmDialog(null,
                    "¿Seguro de eliminar este trabajador permanentemente?");
            if (option == JOptionPane.OK_OPTION && conBajas.eliminar(modBajas)) {
                    JOptionPane.showMessageDialog(null, "Trabajador eliminado permanentemente");
                    actualizarVistaTrabajador();
                    DesignTabla.designBajas(frmTrabajador);
            }
        }

        //Modifica una baja seleccionado en la tabla Baja
        if (e.getSource() == frmTrabajador.ItemModificar_Baja) {
            String folio = frmTrabajador.jTable_Bajas.getValueAt(
                    frmTrabajador.jTable_Bajas.getSelectedRow(), 0).toString();
            ContextoEditarBaja contexto = new ContextoEditarBaja(folio, frmTrabajador);
            CtrlEditarBaja ctrl = new CtrlEditarBaja(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmAdministrador, contexto.ventanaEditarBaja);
        }

        //Accion para abrir la ventana que genera la lista de trabajadores nuevos
        if (e.getSource() == frmTrabajador.Item_Lista_Nuevos_Tbr) {
            // Crear el DateChooser
            JDateChooser dateChooserIn = new JDateChooser();// Crear el DateChooser
            dateChooserIn.setDateFormatString("dd/MM/yyyy");//Formato de fecha

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Seleccione la fecha de Ingreso de los Trabajadores:"));
            panel.add(dateChooserIn);

            // Mostrar el JOptionPane con el panel y botones personalizados
            int option = JOptionPane.showOptionDialog(frmTrabajador, panel,
                    "Enlistar Nuevos Trabajadores", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Obtener la fecha seleccionada si se presionó el botón "OK"
            if (option == JOptionPane.OK_OPTION) {
                String fechaInicio = DateTools.StringtoMySQL(((JTextField) dateChooserIn.getDateEditor().getUiComponent()).getText());
                try {
                    GeneratorPDF_LBU.Nuevos_Trabajadores(fechaInicio);
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //Accion para abrir la ventana que genera la lista de bajas
        if (e.getSource() == frmTrabajador.Item_Lista_Bajas_Tbr) {
            // Crear el DateChooser
            JDateChooser dateChooserIn = new JDateChooser();// Crear el DateChooser
            dateChooserIn.setDateFormatString("dd/MM/yyyy");//Formato de fecha
            JDateChooser dateChooserFn = new JDateChooser();
            dateChooserFn.setDateFormatString("dd/MM/yyyy");//Formato de fecha

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Seleccione el periodo de consulta de las Bajas:"));
            panel.add(new JLabel("Inicio:"));
            panel.add(dateChooserIn);
            panel.add(new JLabel("Final:"));
            panel.add(dateChooserFn);

            // Mostrar el JOptionPane con el panel y botones personalizados
            int option = JOptionPane.showOptionDialog(frmTrabajador, panel,
                    "Consultar Bajas", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Obtener la fecha seleccionada si se presionó el botón "OK"
            if (option == JOptionPane.OK_OPTION) {
                String fechaInicio = DateTools.StringtoMySQL(((JTextField) dateChooserIn.getDateEditor().getUiComponent()).getText());
                String fechaFin = DateTools.StringtoMySQL(((JTextField) dateChooserFn.getDateEditor().getUiComponent()).getText());
                GeneratorPDF_LBU.Bajas_Trabajadores(fechaInicio, fechaFin);
            }
        }

        //Boton que llama al metodo actualizar tabla trabajador
        if (e.getSource() == frmTrabajador.btn_RefreshTabla) {
            frmTrabajador.txt_buscar_Tbr.setText(null);
            DesignTabla.designTrabajador(frmTrabajador);
        }

        //Boton que llama al metodo actualizar tabla bajas
        if (e.getSource() == frmTrabajador.btn_RefreshTabla2) {
            DesignTabla.designBajas(frmTrabajador);
        }

        //
        if (e.getSource() == frmTrabajador.ItemModificarS) {
            limpiarRBSupervisor();
            String nombreS = frmTrabajador.jTable_Supervisores.getValueAt(
                    frmTrabajador.jTable_Supervisores.getSelectedRow(), 1).toString();
            modSupervisor.setNombre_supervisor(nombreS);
            List<Supervisor> supervisoresEncontrados = conSupervisor.buscarSupervisor(modSupervisor);
            if (conSupervisor.buscar(modSupervisor)) {
                frmTrabajador.txt_nomina_sup.setText(String.valueOf(modSupervisor.getId_supervisor()));
                frmTrabajador.txt_NombreSupervisor.setText(modSupervisor.getNombre_supervisor());
                frmTrabajador.txt_Propuesto.setText(String.valueOf(modSupervisor.getPropuesto_trabajadores()));
                for (Supervisor supervisor : supervisoresEncontrados) {
                    int index = supervisor.getArea_idarea();
                    frmTrabajador.cb_Area.setSelectedIndex(index - 1);
                    switch (supervisor.getNombre_turno()) {
                        case "A":
                            frmTrabajador.btn_A.setSelected(true);
                            break;
                        case "B":
                            frmTrabajador.btn_B.setSelected(true);
                            break;
                        case "C":
                            frmTrabajador.btn_C.setSelected(true);
                            break;
                        case "D":
                            frmTrabajador.btn_D.setSelected(true);
                            break;
                        case "LV":
                            frmTrabajador.btn_LV.setSelected(true);
                            break;
                        case "LS":
                            frmTrabajador.btn_LS.setSelected(true);
                            break;
                    }
                }
            }
            frmTrabajador.btn_guardar_sup.setText("Actualizar");
        }

        if (e.getSource() == frmTrabajador.btn_guardar_sup) {
            if (frmTrabajador.btn_guardar_sup.getText().equals("Guardar")) {
                modSupervisor.setId_supervisor(Integer.parseInt(frmTrabajador.txt_nomina_sup.getText()));
                modSupervisor.setNombre_supervisor(frmTrabajador.txt_NombreSupervisor.getText());
                String idArea = QueryFunctions.CapturaCondicionalSimple("area", "idArea", "nombre_Area",
                        frmTrabajador.cb_Area.getSelectedItem().toString());
                modSupervisor.setArea_idarea(Integer.parseInt(idArea));
                modSupervisor.setPropuesto_trabajadores(Integer.parseInt(frmTrabajador.txt_Propuesto.getText()));
                obtenerTurnosSeleccionados();
                if (conSupervisor.insertar(modSupervisor, obtenerTurnosSeleccionados())) {
                    JOptionPane.showMessageDialog(null, "Registro de Supervisor Existoso");
                    limpiarSupervisor();
                }
            } else if (frmTrabajador.btn_guardar_sup.getText().equals("Actualizar")) {
                modSupervisor.setId_supervisor(Integer.parseInt(frmTrabajador.txt_nomina_sup.getText()));
                modSupervisor.setNombre_supervisor(frmTrabajador.txt_NombreSupervisor.getText());
                String folio = frmTrabajador.jTable_Supervisores.getValueAt(
                        frmTrabajador.jTable_Supervisores.getSelectedRow(), 0).toString();
                String idArea = QueryFunctions.CapturaCondicionalSimple("area", "idArea", "nombre_Area",
                        frmTrabajador.cb_Area.getSelectedItem().toString());
                modSupervisor.setArea_idarea(Integer.parseInt(idArea));
                modSupervisor.setPropuesto_trabajadores(Integer.parseInt(frmTrabajador.txt_Propuesto.getText()));
                obtenerTurnosSeleccionados();
                if (conSupervisor.modificar(modSupervisor, folio, obtenerTurnosSeleccionados())) {
                    JOptionPane.showMessageDialog(null, "Actualización de Supervisor Existosa");
                    DesignTabla.designSupervisorEdit(frmTrabajador);
                    limpiarSupervisor();
                }
            }
        }

        if (e.getSource() == frmTrabajador.ItemEliminarS) {
            String folio = frmTrabajador.jTable_Supervisores.getValueAt(
                    frmTrabajador.jTable_Supervisores.getSelectedRow(), 1).toString();
            modSupervisor.setNombre_supervisor(folio);
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja este Supervisor?");
            if (option == JOptionPane.OK_OPTION) {
                if (conSupervisor.eliminar(modSupervisor)) {
                    JOptionPane.showMessageDialog(frmTrabajador, "Supervisor Eliminado");
                    DesignTabla.designSupervisorEdit(frmTrabajador);
                }
            }
        }

        List<Object> validSourcesAgregarSupervisor = Arrays.asList(frmTrabajador.btn_limpiar, frmTrabajador.ItemAgregarS);
        if (validSourcesAgregarSupervisor.contains(e.getSource())) {
            limpiarSupervisor();
        }

        List<Object> validSourcesStatus = Arrays.asList(frmTrabajador.CheckActivo, frmTrabajador.CheckIncapacidad, frmTrabajador.CheckAccidente,
                frmTrabajador.CheckMaternidad, frmTrabajador.CheckLactancia, frmTrabajador.CheckEnfermedad, frmTrabajador.CheckBaja, frmTrabajador.CheckTrayecto);
        if (validSourcesStatus.contains(e.getSource())) {
            String folio = frmTrabajador.jTable_Trabajadores.getValueAt(
                    frmTrabajador.jTable_Trabajadores.getSelectedRow(), 0).toString();
            String status = frmTrabajador.grupoStatus.getSelection().getActionCommand();
            modTrabajador.setFolio_Trabajador(folio);
            conTrabajador.modificarStatus(modTrabajador, status);
            DesignTabla.designTrabajador(frmTrabajador);
            actualizarVistaTrabajador();

        }

        if (e.getSource() == frmTrabajador.btn1) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Incapacidad"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Incapacidad");
            }
        }

        if (e.getSource() == frmTrabajador.btn2) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Incapacidad por Accidente"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Incapacidad por Accidente");
            }
        }

        if (e.getSource() == frmTrabajador.btn5) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Incapacidad por Maternidad"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Incapacidad por Maternidad");
            }
        }

        if (e.getSource() == frmTrabajador.btn3) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Incapacidad por Accidente en Trayecto"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Incapacidad por Accidente en Trayecto");
            }
        }
        if (e.getSource() == frmTrabajador.btn4) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Incapacidad por Enfermedad"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Incapacidad por Enfermedad");
            }
        }

        if (e.getSource() == frmTrabajador.btn6) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Lactancia"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Lactancia");
            }
        }

        if (e.getSource() == frmTrabajador.btn7) {
            int cont = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                    "trabajador", "count(*)", "status_trabajador", "Baja Tentativa"));
            if (cont > 0) {
                DesignTabla.designTrabajadorBStatus(frmTrabajador, "Baja Tentativa");
            }
        }

        if (e.getSource() == frmTrabajador.item_AgregarAdministrativo) {
            String txtBoton = "Guardar";
            ContextoEditarAdministrativo contexto = new ContextoEditarAdministrativo(txtBoton, null, frmTrabajador);
            CtrlEditarAdministrativo ctrl = new CtrlEditarAdministrativo(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmAdministrador, contexto.ventanaEditarAdministrativo);
        }

        if (e.getSource() == frmTrabajador.item_ModificarAdministrativo) {
            String folio = frmTrabajador.jTable_Administrativos.getValueAt(
                    frmTrabajador.jTable_Administrativos.getSelectedRow(), 0).toString();
            String txtBoton = "Actualizar";
            ContextoEditarAdministrativo contexto = new ContextoEditarAdministrativo(txtBoton, folio, frmTrabajador);
            CtrlEditarAdministrativo ctrl = new CtrlEditarAdministrativo(contexto);
            ctrl.iniciar();
            ViewTools.Centrar(frmAdministrador, contexto.ventanaEditarAdministrativo);
        }

        if (e.getSource() == frmTrabajador.item_EliminarAdministrativo) {
            String folio = frmTrabajador.jTable_Administrativos.getValueAt(
                    frmTrabajador.jTable_Administrativos.getSelectedRow(), 0).toString();
            modAdministrativo.setFolio_Trabajador(folio);
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja este Administrativo?");
            if (option == JOptionPane.OK_OPTION) {
                if (conAdministrativo.eliminar(modAdministrativo)) {
                    JOptionPane.showMessageDialog(frmTrabajador, "Administrativo Eliminado");
                    DesignTabla.designAdministrativos(frmTrabajador);
                }
            }
        }

        List<Object> validSourcesAgregarBrigada = Arrays.asList(frmTrabajador.item_AgregarBrigada, frmTrabajador.btn_agregarBrigada);
        if (validSourcesAgregarBrigada.contains(e.getSource())) {
            JLabel text = new JLabel("Nombre de la Brigada:");
            JTextField textField = new JTextField(10);
            JPanel panel = new JPanel();
            panel.add(text);
            panel.add(textField);

            JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);

            JDialog dialog = optionPane.createDialog("Agregar Brigada");
            dialog.setVisible(true);

            int result = (Integer) optionPane.getValue();
            if (result == JOptionPane.OK_OPTION) {
                modBrigada.setNombre_Brigada(textField.getText());
                if (conBrigadas.registrar(modBrigada)) {
                    JOptionPane.showMessageDialog(null, "Brigada Agregada con exito");
                    DesignTabla.designBrigadas(frmTrabajador);
                }
            }
        }

        if (e.getSource() == frmTrabajador.item_ModificarBrigada) {
            int idBrigada = Integer.parseInt(
                    frmTrabajador.jTable_brigadas.getValueAt(frmTrabajador.jTable_brigadas.getSelectedRow(), 0).toString());
            modBrigada.setIdBrigada(idBrigada);
            if (conBrigadas.buscar(modBrigada)) {
                int folio = modBrigada.getIdBrigada();

                JLabel text1 = new JLabel("ID de la Brigada:");
                JTextField textField1 = new JTextField(5);
                textField1.setText(String.valueOf(modBrigada.getIdBrigada()));
                JLabel text = new JLabel("Nombre de la Brigada:");
                JTextField textField = new JTextField(10);
                textField.setText(modBrigada.getNombre_Brigada());
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(text1);
                panel.add(textField1);
                panel.add(text);
                panel.add(textField);

                JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);

                JDialog dialog = optionPane.createDialog("Modificar Brigada");
                dialog.setVisible(true);

                int result = (Integer) optionPane.getValue();
                if (result == JOptionPane.OK_OPTION) {
                    modBrigada.setIdBrigada(Integer.parseInt(textField1.getText()));
                    modBrigada.setNombre_Brigada(textField.getText());
                    if (conBrigadas.modificar(modBrigada, folio)) {
                        JOptionPane.showMessageDialog(null, "Brigada Modificada con exito");
                        DesignTabla.designBrigadas(frmTrabajador);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el ID de la Brigada");
            }
        }

        if (e.getSource() == frmTrabajador.item_EliminarBrigada) {
            String folio = frmTrabajador.jTable_brigadas.getValueAt(
                    frmTrabajador.jTable_brigadas.getSelectedRow(), 0).toString();
            modBrigada.setIdBrigada(Integer.parseInt(folio));
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja esta Brigada?");
            if (option == JOptionPane.OK_OPTION) {
                if (conBrigadas.eliminar(modBrigada)) {
                    JOptionPane.showMessageDialog(frmTrabajador, "Brigada Eliminada");
                    DesignTabla.designBrigadas(frmTrabajador);
                }
            }
        }

        if (e.getSource() == frmTrabajador.btn_RefreshTabla3) {
            frmTrabajador.jLabel5.setText("Listado de Todos los Brigadistas");
            DesignTabla.designBrigadistas(frmTrabajador);
        }

        List<Object> validSourcesEliminarBrigadistas = Arrays.asList(frmTrabajador.btn_eliminarBrigadista, frmTrabajador.item_EliminarBrigadista);
        if (validSourcesEliminarBrigadistas.contains(e.getSource())) {
            TableModel modelo = frmTrabajador.jTable_brigadistas.getModel();
            String folio = null;
            if (modelo instanceof TableBrigadistas) {
                folio = frmTrabajador.jTable_brigadistas.getValueAt(
                        frmTrabajador.jTable_brigadistas.getSelectedRow(), 1).toString();
            } else if (modelo instanceof TableBrigadistasBrigada) {
                folio = frmTrabajador.jTable_brigadistas.getValueAt(
                        frmTrabajador.jTable_brigadistas.getSelectedRow(), 0).toString();
            }
            modBrigadista.setIdBrigadista(Integer.parseInt(folio));
            int option = JOptionPane.showConfirmDialog(null, "¿Seguro de dar de baja a este Brigadista?");
            if (option == JOptionPane.OK_OPTION) {
                if (conBrigadistas.eliminar(modBrigadista)) {
                    JOptionPane.showMessageDialog(frmTrabajador, "Brigadista Eliminado");
                    String idBrigada = frmTrabajador.jTable_brigadas.getValueAt(
                            frmTrabajador.jTable_brigadas.getSelectedRow(), 0).toString();
                    DesignTabla.designBrigadistasBrigada(frmTrabajador, Integer.parseInt(idBrigada));
                }
            }
        }

        if (e.getSource() == frmTrabajador.btn_agregarBrigadista) {
            JOptionPane.showMessageDialog(null, "Para agregar o modificar un Brigadista,\n"
                    + "busque su número de Nomina en su respectiva tabla. \n"
                    + "Dele click a la opción de modificar y busque el apartodo de Brigadas.");
        }

        if (e.getSource() == frmTrabajador.btn_ListT) {
            // Lista de campos disponibles
            Map<String, String> camposDisponibles = new LinkedHashMap<>();
            camposDisponibles.put("Folio_Trabajador", "Nomina");
            camposDisponibles.put("nombre_trabajador", "Nombre");
            camposDisponibles.put("CURP_Trabajador", "CURP");
            camposDisponibles.put("RFC_Trabajador", "RFC");
            camposDisponibles.put("IMSS_Trabajador", "IMSS");
            camposDisponibles.put("Fecha_Antiguedad", "Ingreso");
            camposDisponibles.put("Nombre_Area", "Area");
            camposDisponibles.put("Nombre_Puesto", "Puesto");
            camposDisponibles.put("nombre_turno", "Turno");
            camposDisponibles.put("Supervisor", "Supervisor");
            camposDisponibles.put("SalarioDiario_Trabajador", "Salario");
            camposDisponibles.put("Nivel", "Nivel");
            camposDisponibles.put("Fecha_Cumpleaños", "Cumpleaños");
            camposDisponibles.put("Email_Trabajador", "Email");
            camposDisponibles.put("Teléfono_Trabajador", "Teléfono");
            camposDisponibles.put("Sexo", "Sexo");

            JPanel panel = new JPanel(new GridLayout(0, 1));
            Map<String, JCheckBox> checkBoxes = new HashMap<>();

            for (Map.Entry<String, String> entry : camposDisponibles.entrySet()) {
                JCheckBox checkBox = new JCheckBox(entry.getValue(), true); // true para seleccionado por defecto
                checkBoxes.put(entry.getKey(), checkBox);
                panel.add(checkBox);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Selecciona los campos a exportar",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            List<String> camposSeleccionados = new ArrayList<>();
            for (Map.Entry<String, JCheckBox> entry : checkBoxes.entrySet()) {
                if (entry.getValue().isSelected()) {
                    camposSeleccionados.add(entry.getKey());
                }
            }

            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            } else {
                frmTrabajador.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                if (GeneratorExcel_BDs.BD_TRABAJADORES(camposDisponibles, camposSeleccionados)) {
                    frmTrabajador.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                } else {
                    frmTrabajador.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == frmTrabajador.txt_buscar_Tbr) {
            String texto = frmTrabajador.txt_buscar_Tbr.getText();
            if (texto.equals("")) {
                DesignTabla.designTrabajador(frmTrabajador);
            } else {
                DesignTabla.designTrabajadorB(frmTrabajador, texto);
            }
        }

        if (e.getSource() == frmTrabajador.txt_buscar_bajas) {
            String texto = frmTrabajador.txt_buscar_bajas.getText();
            if (texto.equals("")) {
                DesignTabla.designBajas(frmTrabajador);
            } else {
                DesignTabla.designBajasB(frmTrabajador, texto);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == frmTrabajador.MenuEstado) {
            String folio = frmTrabajador.jTable_Trabajadores.getValueAt(
                    frmTrabajador.jTable_Trabajadores.getSelectedRow(), 0).toString();
            String status = conTrabajador.consultarStatus(folio);
            switch (status) {
                case "Activo":
                    frmTrabajador.CheckActivo.setSelected(true);
                    break;
                case "Incapacidad":
                    frmTrabajador.CheckIncapacidad.setSelected(true);
                    break;
                case "Incapacidad por Accidente":
                    frmTrabajador.CheckAccidente.setSelected(true);
                    break;
                case "Incapacidad por Maternidad":
                    frmTrabajador.CheckMaternidad.setSelected(true);
                    break;
                case "Incapacidad por Accidente en Trayecto":
                    frmTrabajador.CheckTrayecto.setSelected(true);
                    break;
                case "Incapacidad por Enfermedad":
                    frmTrabajador.CheckEnfermedad.setSelected(true);
                    break;
                case "Baja Tentativa":
                    frmTrabajador.CheckBaja.setSelected(true);
                    break;
                case "Lactancia":
                    frmTrabajador.CheckLactancia.setSelected(true);
                    break;
                default:
                    frmTrabajador.CheckActivo.setSelected(true);
                    break;
            }
        }
    }

    // Método que devuelve una lista con los turnos seleccionados
    public List<Turno> obtenerTurnosSeleccionados() {
        List<Turno> turnos = new ArrayList<>();
        if (frmTrabajador.btn_A.isSelected()) {
            Turno tbr = new Turno();
            tbr.setIdTurno(1);
            tbr.setNombre_Turno("A");
            turnos.add(tbr);
        }
        if (frmTrabajador.btn_B.isSelected()) {
            Turno tbr = new Turno();
            tbr.setIdTurno(2);
            tbr.setNombre_Turno("B");
            turnos.add(tbr);
        }
        if (frmTrabajador.btn_C.isSelected()) {
            Turno tbr = new Turno();
            tbr.setIdTurno(3);
            tbr.setNombre_Turno("C");
            turnos.add(tbr);
        }
        if (frmTrabajador.btn_D.isSelected()) {
            Turno tbr = new Turno();
            tbr.setIdTurno(4);
            tbr.setNombre_Turno("D");
            turnos.add(tbr);
        }
        if (frmTrabajador.btn_LV.isSelected()) {
            Turno tbr = new Turno();
            tbr.setIdTurno(5);
            tbr.setNombre_Turno("LV");
            turnos.add(tbr);
        }
        if (frmTrabajador.btn_LS.isSelected()) {
            Turno tbr = new Turno();
            tbr.setIdTurno(6);
            tbr.setNombre_Turno("LS");
            turnos.add(tbr);
        }
        return turnos;
    }

    public void limpiar() {
        frmTrabajador.txt_buscar_Tbr.setText(null);
    }

    public void limpiarSupervisor() {
        frmTrabajador.txt_nomina_sup.setText(null);
        frmTrabajador.txt_NombreSupervisor.setText(null);
        frmTrabajador.cb_Area.setSelectedIndex(0);
        frmTrabajador.txt_Propuesto.setText(null);
        frmTrabajador.btn_guardar_sup.setText("Guardar");
        DesingSupervisor();
        limpiarRBSupervisor();
    }

    public void limpiarRBSupervisor() {
        frmTrabajador.btn_A.setSelected(false);
        frmTrabajador.btn_B.setSelected(false);
        frmTrabajador.btn_C.setSelected(false);
        frmTrabajador.btn_D.setSelected(false);
        frmTrabajador.btn_LV.setSelected(false);
        frmTrabajador.btn_LS.setSelected(false);
    }

    public void actualizarVistaTrabajador() {
        frmTrabajador.txt_total.setText(String.valueOf(conTrabajador.contar()));
        frmTrabajador.btn1.setText("En Incapacidad: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad"));
        frmTrabajador.btn2.setText("En Accidente: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Accidente"));
        frmTrabajador.btn3.setText("En Acc. de Trayecto: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Accidente en Trayecto"));
        frmTrabajador.btn4.setText("En Enfermedad: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Enfermedad"));
        frmTrabajador.btn5.setText("En Maternidad: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Maternidad"));
        frmTrabajador.btn6.setText("En Lactancia: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Lactancia"));
        frmTrabajador.btn7.setText("Baja Tentaiva: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Baja Tentativa"));
        limpiar();
    }

    public void DesingSupervisor() {
        DesignTabla.designSupervisorEdit(frmTrabajador);
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frmTrabajador.cb_Area);
    }

    private void setPlaceholderText() {
        TextPrompt placeholder1 = new TextPrompt("Folio/Nombre", frmTrabajador.txt_buscar_Tbr);
    }

    private void setWindowProperties() {
        frmTrabajador.setResizable(false);
        frmTrabajador.setTitle("Trabajadores y Supervisores en Nomina");
        frmTrabajador.txt_total.setText(String.valueOf(conTrabajador.contar()));
        frmTrabajador.btn1.setText("En Incapacidad: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad"));
        frmTrabajador.btn2.setText("En Accidente: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Accidente"));
        frmTrabajador.btn3.setText("En Acc. de Trayecto: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Accidente en Trayecto"));
        frmTrabajador.btn4.setText("En Enfermedad: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Enfermedad"));
        frmTrabajador.btn5.setText("En Maternidad: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Incapacidad por Maternidad"));
        frmTrabajador.btn6.setText("En Lactancia: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Lactancia"));
        frmTrabajador.btn7.setText("Baja Tentativa: "
                + QueryFunctions.CapturaCondicionalSimple("trabajador", "count(*)", "status_trabajador", "Baja Tentativa"));
        frmTrabajador.jLabel5.setText("Listado de Todos los Brigadistas");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == frmTrabajador.jTable_brigadas.getSelectionModel()) {
            if (!e.getValueIsAdjusting()) {
                if (frmTrabajador.jTable_brigadas.getSelectedRow() >= 0
                        && frmTrabajador.jTable_brigadas.getSelectedRow() < frmTrabajador.jTable_brigadas.getRowCount()) {
                    String idBrigada = frmTrabajador.jTable_brigadas.getValueAt(
                            frmTrabajador.jTable_brigadas.getSelectedRow(), 0).toString();
                    String Brigada = frmTrabajador.jTable_brigadas.getValueAt(
                            frmTrabajador.jTable_brigadas.getSelectedRow(), 1).toString();
                    frmTrabajador.jLabel5.setText(Brigada);
                    DesignTabla.designBrigadistasBrigada(frmTrabajador, Integer.parseInt(idBrigada));
                }
            }
        }
    }
}
