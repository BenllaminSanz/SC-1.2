package Subviews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionNivelesSalarios extends JFrame {
    private JTextField A1, B1, A2, B2, A3, B3, A4, B4, 
            A5, B5, A6, B6, A7, B7, A8, B8;
    private JButton btnGuardar, btnCancelar;
    private Properties propiedades;

    public ConfiguracionNivelesSalarios() {
        // Configuración de la ventana
        setTitle("Configurar Niveles de Salarios");
        setSize(300, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar propiedades desde el archivo
        propiedades = new Properties();
        cargarPropiedades();

        // Crear componentes
        JPanel panel = new JPanel(new GridLayout(18, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nivel 1A:"));
        A1 = new JTextField(propiedades.getProperty("nivel.1A"));
        panel.add(A1);
        panel.add(new JLabel("Nivel 1B:"));
        B1 = new JTextField(propiedades.getProperty("nivel.1B"));
        panel.add(B1);
        
        panel.add(new JLabel("Nivel 2A:"));
        A2 = new JTextField(propiedades.getProperty("nivel.2A"));
        panel.add(A2);
        panel.add(new JLabel("Nivel 2B:"));
        B2 = new JTextField(propiedades.getProperty("nivel.2B"));
        panel.add(B2);
        
        panel.add(new JLabel("Nivel 3A:"));
        A3 = new JTextField(propiedades.getProperty("nivel.3A"));
        panel.add(A3);
        panel.add(new JLabel("Nivel 3B:"));
        B3 = new JTextField(propiedades.getProperty("nivel.3B"));
        panel.add(B3);
        
        panel.add(new JLabel("Nivel 4A:"));
        A4 = new JTextField(propiedades.getProperty("nivel.4A"));
        panel.add(A4);
        panel.add(new JLabel("Nivel 4B:"));
        B4 = new JTextField(propiedades.getProperty("nivel.4B"));
        panel.add(B4);
        
        panel.add(new JLabel("Nivel 5A:"));
        A5 = new JTextField(propiedades.getProperty("nivel.5A"));
        panel.add(A5);
        panel.add(new JLabel("Nivel 5B:"));
        B5 = new JTextField(propiedades.getProperty("nivel.5B"));
        panel.add(B5);
        
        panel.add(new JLabel("Nivel 6A:"));
        A6 = new JTextField(propiedades.getProperty("nivel.6A"));
        panel.add(A6);
        panel.add(new JLabel("Nivel 6B:"));
        B6 = new JTextField(propiedades.getProperty("nivel.6B"));
        panel.add(B6);
        
        panel.add(new JLabel("Nivel 7A:"));
        A7 = new JTextField(propiedades.getProperty("nivel.7A"));
        panel.add(A7);
        panel.add(new JLabel("Nivel 7B:"));
        B7 = new JTextField(propiedades.getProperty("nivel.7B"));
        panel.add(B7);
        
        panel.add(new JLabel("Nivel 8A:"));
        A8 = new JTextField(propiedades.getProperty("nivel.8A"));
        panel.add(A8);
        panel.add(new JLabel("Nivel 8B:"));
        B8 = new JTextField(propiedades.getProperty("nivel.8B"));
        panel.add(B8);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        // Agregar acción al botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPropiedades();
                JOptionPane.showMessageDialog(ConfiguracionNivelesSalarios.this, "Niveles guardados correctamente.");
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
        try (FileInputStream fis = new FileInputStream("niveles.properties")) {
            propiedades.load(fis);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo de configuración.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarPropiedades() {
        propiedades.setProperty("nivel.A1", A1.getText());
        propiedades.setProperty("nivel.B1", B1.getText());
        propiedades.setProperty("nivel.A2", A2.getText());
        propiedades.setProperty("nivel.B2", B2.getText());
        propiedades.setProperty("nivel.A3", A3.getText());
        propiedades.setProperty("nivel.B3", B3.getText());
        propiedades.setProperty("nivel.A4", A4.getText());
        propiedades.setProperty("nivel.B4", B4.getText());
        propiedades.setProperty("nivel.A5", A5.getText());
        propiedades.setProperty("nivel.B5", B5.getText());
        propiedades.setProperty("nivel.A6", A6.getText());
        propiedades.setProperty("nivel.B6", B6.getText());
        propiedades.setProperty("nivel.A7", A7.getText());
        propiedades.setProperty("nivel.B7", B7.getText());
        propiedades.setProperty("nivel.A8", A8.getText());
        propiedades.setProperty("nivel.B8", B8.getText());

        try (FileOutputStream fos = new FileOutputStream("niveles.properties")) {
            propiedades.store(fos, "Configurar Niveles de Salarios");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo de configuración.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}