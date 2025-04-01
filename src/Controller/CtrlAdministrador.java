//Controlador de la ventana Admistrador
//Recibe la estructura grafica de Frame Administrador
/*Esta vista en la pantalla principal del sistema donde se requiere tener todas
las funciones del programa sin mas restricciones que las necesarias para que el 
programa funcione correctamente, esta ventana será la que use el administrador 
del sistema*/
package Controller;

import ContextController.ContextoAreasPuestos;
import ContextController.ContextoCapacitacion;
import ContextController.ContextoLBU;
import ContextController.ContextoTrabajador;
import Functions.DateTools;
import Functions.ViewTools;
import Querys.Conexion;
import View.FrmAdministrador;
import View.IFrmAreasPuestos;
import View.IFrmCapacitacion;
import View.IFrmLBU;
import View.IFrmTrabajador;
import java.awt.Cursor;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CtrlAdministrador implements ActionListener {

    private final FrmAdministrador frame;

    //Relación Controlador-Vista
    public CtrlAdministrador(FrmAdministrador frame) {
        this.frame = frame;
        this.frame.btn_Trabajadores.addActionListener(this);
        this.frame.btn_LBU.addActionListener(this);
        this.frame.btn_Capacitaciones.addActionListener(this);
        this.frame.btn_AreasPuestos.addActionListener(this);
        this.frame.itm_Trabajdores.addActionListener(this);
        this.frame.itm_LBU.addActionListener(this);
        this.frame.itm_CmbTurnos.addActionListener(this);
        this.frame.itm_AreasPuestos.addActionListener(this);
        this.frame.itm_Capacitacion.addActionListener(this);
    }

    //Metodo de Arranque
    public void iniciar() {
        //Metodos para gestionar la UI (Interfaz de Usuario)
        setLookAndFeel();
        setLogo();
        setWindowProperties();
        //Funciones que se ejecutan al inicar la ventana
        //cambioTurnoFecha();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Accion para los botones que abren la ventana Areas y Puestos
        List<Object> validSourcesAreas = Arrays.asList(frame.btn_AreasPuestos, frame.itm_AreasPuestos);
        if (validSourcesAreas.contains(e.getSource())) {
            //Variable del IFrm para verificar si esta abierto o no en el Desktop
            String x = IFrmAreasPuestos.x;
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (x == null) {
                ContextoAreasPuestos contextoAreasPuestos = new ContextoAreasPuestos(frame);
                CtrlAreasPuestos ctrl = new CtrlAreasPuestos(contextoAreasPuestos);
                ctrl.iniciar();
                ViewTools.Centrar(frame, contextoAreasPuestos.ventanaAreasPuestos);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        //Accion para los botones que abren la ventana Trabajador
        List<Object> validSourcesTrabajador = Arrays.asList(frame.btn_Trabajadores, frame.itm_Trabajdores);
        if (validSourcesTrabajador.contains(e.getSource())) {
            //Variable del IFrm para verificar si esta abierto o no en el Desktop
            String x = IFrmTrabajador.x;
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (x == null) {
                //Llamamos las clases necesarias para trabajador
                ContextoTrabajador contextoTrabajador = new ContextoTrabajador(frame);
                //Llamada al Controlador de trabajador
                CtrlTrabajador ctrl = new CtrlTrabajador(contextoTrabajador);
                ctrl.iniciar();
                ViewTools.Centrar(frame, contextoTrabajador.ventanaTrabajador);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        //Accion para los botones que abren la ventana LBU Operativo
        List<Object> validSourcesLBU = Arrays.asList(frame.btn_LBU, frame.itm_LBU);
        if (validSourcesLBU.contains(e.getSource())) {
            //Variable del IFrm para verificar si esta abierto o no en el Desktop
            String x = IFrmLBU.x;
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (x == null) {
                ContextoLBU contextoLBU = new ContextoLBU(frame);
                CtrlLBUGeneral ctrl = new CtrlLBUGeneral(contextoLBU);
                ctrl.iniciar();
                ViewTools.Centrar(frame, contextoLBU.ventanaLBU);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        //Accion para los botones que abren la ventana Capacitaciones
        List<Object> validSourcesCapacitaciones = Arrays.asList(frame.btn_Capacitaciones, frame.itm_Capacitacion);
        if (validSourcesCapacitaciones.contains(e.getSource())) {
            String x = IFrmCapacitacion.x;
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (x == null) {
                ContextoCapacitacion contextoCapacitaciones = new ContextoCapacitacion(frame);
                CtrlCapacitacion ctrl = new CtrlCapacitacion(contextoCapacitaciones);
                ctrl.iniciar();
                ViewTools.Centrar(frame, contextoCapacitaciones.ventanaCapacitacion);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            } else {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }

        //Botón para inicar funcion del cambio de turno
        if (e.getSource() == frame.itm_CmbTurnos) {
            //Metodo para ejecutar el cambio de Turno en la BD
            cambioTurnoManual();
        }
    }

    //Metodo para el cambio de turno de los trabajadores por fecha
    private void cambioTurnoFecha() {
        LocalDateTime fechaActual = LocalDateTime.now();
        int mesActual = fechaActual.getMonthValue();
        int diaActual = fechaActual.getDayOfMonth();

        if (diaActual == 6 && (mesActual % 3) == 0) {
            String message = "Hoy es día " + diaActual + " de " + DateTools.obtenerNombreMes(mesActual)
                    + ". ¿Desea autorizar el cambio de turno?";
            int opcion = JOptionPane.showConfirmDialog(frame, message,
                    "Cambio de turno", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                cambiarTurnos();
            }
        }
    }

    //Metodo par el cambio de turno de forma manual
    private void cambioTurnoManual() {
        LocalDateTime fechaActual = LocalDateTime.now();
        int mesActual = fechaActual.getMonthValue();
        int diaActual = fechaActual.getDayOfMonth();

        String message = "Hoy es día " + diaActual + " de " + DateTools.obtenerNombreMes(mesActual)
                + ". ¿Desea autorizar el cambio de turno manualmente?";
        int opcion = JOptionPane.showConfirmDialog(frame, message,
                "Cambio de turno", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            cambiarTurnos();
        }
    }

    //Metodo para cambiar los turnos de los trabajadores en la base de datos
    //Este llama a la funcion cambiar_turnos creada en la base de datos
    private void cambiarTurnos() {
        try {
            Connection conn = new Conexion().getConnection();
            String metodoCambioarTurnos = "{CALL sistema_capacitacion.cambiar_turnos()}";
            PreparedStatement ps = conn.prepareStatement(metodoCambioarTurnos);
            ps.execute();

            JOptionPane.showMessageDialog(frame, "Cambio exitoso de turno", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Cambio Exitoso de Turno");
        } catch (SQLException ex) {
            Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frame, "Cambio fallido de turno. Contacte con el programador.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo para definir el diseño de los componentes segun el equipo
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CtrlAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodo para proporcionarle el logo al label
    private void setLogo() {
        ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
        Icon icon = new ImageIcon(logo.getImage().getScaledInstance(
                frame.jLabel_Logo.getWidth(), frame.jLabel_Logo.getHeight(), Image.SCALE_SMOOTH));
        frame.jLabel_Logo.setIcon(icon);

        ImageIcon icono = new ImageIcon(ClassLoader.getSystemResource("Images/IconParkdale.png"));
        frame.setIconImage(icono.getImage());
    }

    //Metodo para definir las propiedades generales de la vista
    private void setWindowProperties() {
        frame.setTitle("Vista Administrador");
        frame.setSize(1300, 750);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
    }
}
