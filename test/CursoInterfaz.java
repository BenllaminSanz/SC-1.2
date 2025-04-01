import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CursoInterfaz extends JFrame {
    private JTextField campoNombre;
    private JList<String> listaAreas;
    private JList<String> listaPuestos;
    private JCheckBox checkBoxTurnoManana;
    private JCheckBox checkBoxTurnoTarde;
    private JCheckBox checkBoxTurnoNoche;
    private final JButton botonModificar;
    
    public CursoInterfaz() {
        // Configurar la ventana principal
        setTitle("Modificar Curso");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Crear los componentes
        campoNombre = new JTextField();
        listaAreas = new JList<>(new String[]{"Área 1", "Área 2", "Área 3", "Área 4"});
        listaPuestos = new JList<>();
        checkBoxTurnoManana = new JCheckBox("Mañana");
        checkBoxTurnoTarde = new JCheckBox("Tarde");
        checkBoxTurnoNoche = new JCheckBox("Noche");
        botonModificar = new JButton("Modificar");
        
        // Agregar los componentes a la ventana
        JPanel panelIzquierdo = new JPanel(new GridLayout(2, 1));
        panelIzquierdo.add(new JLabel("Nombre del Curso:"));
        panelIzquierdo.add(new JScrollPane(listaAreas));
        
        JPanel panelDerecho = new JPanel(new GridLayout(3, 1));
        panelDerecho.add(new JScrollPane(listaPuestos));
        panelDerecho.add(checkBoxTurnoManana);
        panelDerecho.add(checkBoxTurnoTarde);
        panelDerecho.add(checkBoxTurnoNoche);
        
        add(panelIzquierdo, BorderLayout.WEST);
        add(campoNombre, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
        add(botonModificar, BorderLayout.SOUTH);
        
        // Configurar el ActionListener del botón Modificar
        botonModificar.addActionListener((ActionEvent e) -> {
            // Obtener el nombre del curso modificado
            String nuevoNombre = campoNombre.getText();
            
            // Obtener las áreas seleccionadas
            String[] areasSeleccionadas = listaAreas.getSelectedValuesList().toArray(new String[0]);
            
            // Obtener los puestos seleccionados
            String[] puestosSeleccionados = listaPuestos.getSelectedValuesList().toArray(new String[0]);
            
            // Obtener los turnos seleccionados
            String turnosSeleccionados = "";
            if (checkBoxTurnoManana.isSelected()) {
                turnosSeleccionados += "Mañana ";
            }
            if (checkBoxTurnoTarde.isSelected()) {
                turnosSeleccionados += "Tarde ";
            }
            if (checkBoxTurnoNoche.isSelected()) {
                turnosSeleccionados += "Noche ";
            }
            
            // Realizar la modificación del curso en la base de datos
            modificarCurso(nuevoNombre, areasSeleccionadas, puestosSeleccionados, turnosSeleccionados);
            
            // Actualizar la tabla de puestos asociados al curso
            actualizarTablaPuestos();
        });
        
        // Mostrar la ventana
        setVisible(true);
    }
    
    private void modificarCurso(String nuevoNombre, String[] areasSeleccionadas, String[] puestosSeleccionados, String turnosSeleccionados) {
        // Lógica para modificar el curso en la base de datos
        // Utiliza el nuevo nombre, áreas seleccionadas, puestos seleccionados y turnos seleccionados proporcionados
    }
    
    private void actualizarTablaPuestos() {
        // Lógica para obtener los puestos asociados al curso seleccionado
        // y actualizar la tabla de puestos
    }
    
    public static void main(String[] args) {
        CursoInterfaz interfaz = new CursoInterfaz();
    }
}
