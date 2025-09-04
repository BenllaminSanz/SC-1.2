package Subviews;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ConfiguracionNivelesSalarios extends JFrame {

    private Properties propiedades;
    private Map<String, JTextField> campos = new LinkedHashMap<>();
    private JPanel panel;
    private JButton btnGuardar, btnCancelar, btnAgregar, btnEliminar;

    public ConfiguracionNivelesSalarios() {
        setTitle("Configurar Niveles de Salarios");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        propiedades = new Properties();
        cargarPropiedades();

        panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        actualizarPanel();

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        btnAgregar = new JButton("Agregar Nivel");
        btnEliminar = new JButton("Eliminar Nivel");

        btnGuardar.addActionListener(e -> {
            guardarPropiedades();
            JOptionPane.showMessageDialog(this, "Niveles guardados correctamente.");
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        btnAgregar.addActionListener(e -> agregarNivel());

        btnEliminar.addActionListener(e -> eliminarNivel());

        JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnCancelar);
        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnEliminar);

        // Contenedor principal
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(300, 450));

        add(scrollPane, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
    }

    private void cargarPropiedades() {
        try (InputStream input = new FileInputStream("files/niveles.properties")) {
            propiedades.load(input);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo de propiedades.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void guardarPropiedades() {
        for (Map.Entry<String, JTextField> entry : campos.entrySet()) {
            propiedades.setProperty(entry.getKey(), entry.getValue().getText());
        }

        try (OutputStream output = new FileOutputStream("files/niveles.properties")) {
            propiedades.store(output, null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo de propiedades.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarPanel() {
        panel.removeAll();
        campos.clear();

        List<String> clavesOrdenadas = propiedades.stringPropertyNames().stream()
                .filter(k -> k.startsWith("nivel."))
                .sorted(Comparator.comparingInt((String k) -> {
                    String nivel = k.substring("nivel.".length());
                    return Integer.parseInt(nivel.replaceAll("[^0-9]", ""));
                }).thenComparing(k -> {
                    String nivel = k.substring("nivel.".length());
                    return nivel.replaceAll("[0-9]", "");
                }))
                .collect(Collectors.toList());

        for (String key : clavesOrdenadas) {
            String nivel = key.substring("nivel.".length());
            JLabel label = new JLabel("Nivel " + nivel + ":");
            JTextField campo = new JTextField(propiedades.getProperty(key));
            campos.put(key, campo);
            panel.add(label);
            panel.add(campo);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void agregarNivel() {
        String nuevoNivel = JOptionPane.showInputDialog(this, "Ingresa el nuevo nivel (ej: 9A):");
        if (nuevoNivel == null || nuevoNivel.trim().isEmpty()) {
            return;
        }

        String clave = "nivel." + nuevoNivel.toUpperCase();

        if (propiedades.containsKey(clave)) {
            JOptionPane.showMessageDialog(this, "Ese nivel ya existe.");
            return;
        }

        propiedades.setProperty(clave, ""); // valor vacío por default
        actualizarPanel();
    }

    private void eliminarNivel() {
        Object[] opciones = propiedades.stringPropertyNames().stream()
                .filter(k -> k.startsWith("nivel."))
                .sorted()
                .map(k -> k.substring("nivel.".length()))
                .toArray();

        if (opciones.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay niveles para eliminar.");
            return;
        }

        String seleccion = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona el nivel a eliminar:",
                "Eliminar Nivel",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion != null) {
            propiedades.remove("nivel." + seleccion);
            actualizarPanel();
        }
    }
}
