import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionColumnasGUI extends JFrame {
    private JTextField txtFolio, txtNombre, txtSalario, txtFechaAntiguedad, txtFechaCumpleanos, txtCurp, txtRfc, txtImss, txtEmail, txtTelefono;
    private JButton btnGuardar, btnCancelar;
    private Properties propiedades;

    public ConfiguracionColumnasGUI() {
        // Configuración de la ventana
        setTitle("Configurar Índices de Columnas");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar propiedades desde el archivo
        propiedades = new Properties();
        cargarPropiedades();

        // Crear componentes
        JPanel panel = new JPanel(new GridLayout(11, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Índice Folio:"));
        txtFolio = new JTextField(propiedades.getProperty("indice.folio"));
        panel.add(txtFolio);

        panel.add(new JLabel("Índice Nombre:"));
        txtNombre = new JTextField(propiedades.getProperty("indice.nombre"));
        panel.add(txtNombre);

        panel.add(new JLabel("Índice Salario:"));
        txtSalario = new JTextField(propiedades.getProperty("indice.salario"));
        panel.add(txtSalario);

        panel.add(new JLabel("Índice Fecha Antigüedad:"));
        txtFechaAntiguedad = new JTextField(propiedades.getProperty("indice.fecha_antiguedad"));
        panel.add(txtFechaAntiguedad);

        panel.add(new JLabel("Índice Fecha Cumpleaños:"));
        txtFechaCumpleanos = new JTextField(propiedades.getProperty("indice.fecha_cumpleanos"));
        panel.add(txtFechaCumpleanos);

        panel.add(new JLabel("Índice CURP:"));
        txtCurp = new JTextField(propiedades.getProperty("indice.curp"));
        panel.add(txtCurp);

        panel.add(new JLabel("Índice RFC:"));
        txtRfc = new JTextField(propiedades.getProperty("indice.rfc"));
        panel.add(txtRfc);

        panel.add(new JLabel("Índice IMSS:"));
        txtImss = new JTextField(propiedades.getProperty("indice.imss"));
        panel.add(txtImss);

        panel.add(new JLabel("Índice Email:"));
        txtEmail = new JTextField(propiedades.getProperty("indice.email"));
        panel.add(txtEmail);

        panel.add(new JLabel("Índice Teléfono:"));
        txtTelefono = new JTextField(propiedades.getProperty("indice.telefono"));
        panel.add(txtTelefono);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        // Agregar acción al botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPropiedades();
                JOptionPane.showMessageDialog(ConfiguracionColumnasGUI.this, "Configuración guardada correctamente.");
                dispose(); // Cerrar la ventana después de guardar
            }
        });

        // Agregar acción al botón Cancelar
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana sin guardar
            }
        });

        // Agregar botones al panel
        panel.add(btnGuardar);
        panel.add(btnCancelar);

        // Agregar panel a la ventana
        add(panel);
    }

    private void cargarPropiedades() {
        try (FileInputStream fis = new FileInputStream("files/config.properties")) {
            propiedades.load(fis);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo de configuración.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarPropiedades() {
        propiedades.setProperty("indice.folio", txtFolio.getText());
        propiedades.setProperty("indice.nombre", txtNombre.getText());
        propiedades.setProperty("indice.salario", txtSalario.getText());
        propiedades.setProperty("indice.fecha_antiguedad", txtFechaAntiguedad.getText());
        propiedades.setProperty("indice.fecha_cumpleanos", txtFechaCumpleanos.getText());
        propiedades.setProperty("indice.curp", txtCurp.getText());
        propiedades.setProperty("indice.rfc", txtRfc.getText());
        propiedades.setProperty("indice.imss", txtImss.getText());
        propiedades.setProperty("indice.email", txtEmail.getText());
        propiedades.setProperty("indice.telefono", txtTelefono.getText());

        try (FileOutputStream fos = new FileOutputStream("files/config.properties")) {
            propiedades.store(fos, "Configuración de índices de columnas");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo de configuración.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConfiguracionColumnasGUI().setVisible(true);
            }
        });
    }
}