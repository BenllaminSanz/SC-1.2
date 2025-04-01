import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AgregarCursoUI extends JFrame implements ActionListener {
    private JTextField nombreCampo;
    private JComboBox<String> tipoComboBox;
    private JTextField certificadoCampo;
    private JButton agregarBoton;

    public AgregarCursoUI() {
        super("Agregar Curso");

        // Configurar el layout del formulario
        setLayout(new BorderLayout());

        // Crear los componentes del formulario
        JPanel formularioPanel = new JPanel(new GridLayout(0, 2));
        formularioPanel.add(new JLabel("Nombre del curso:"));
        nombreCampo = new JTextField();
        formularioPanel.add(nombreCampo);

        formularioPanel.add(new JLabel("Tipo de curso:"));
        String[] tipos = {"Tipo 1", "Tipo 2", "Operativo"};
        tipoComboBox = new JComboBox<>(tipos);
        tipoComboBox.addActionListener(this);
        formularioPanel.add(tipoComboBox);

        formularioPanel.add(new JLabel("Certificado:"));
        certificadoCampo = new JTextField();
        formularioPanel.add(certificadoCampo);

        // Ocultar el campo de certificado por defecto
        certificadoCampo.setVisible(false);

        // Agregar el panel del formulario al centro de la ventana
        add(formularioPanel, BorderLayout.CENTER);

        // Agregar el botón "Agregar" al sur de la ventana
        agregarBoton = new JButton("Agregar");
        agregarBoton.addActionListener(this);
        add(agregarBoton, BorderLayout.SOUTH);

        // Configurar las propiedades de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Mostrar u ocultar el campo de certificado según el tipo de curso seleccionado
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tipoComboBox) {
            String tipo = (String) tipoComboBox.getSelectedItem();
            if (tipo.equals("Operativo")) {
                certificadoCampo.setVisible(true);
            } else {
                certificadoCampo.setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        new AgregarCursoUI();
    }
}
