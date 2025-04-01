import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public class TablaConBotonGuardar extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonGuardar;
    private JButton botonAgregar;
    private JButton botonEliminar;

    public TablaConBotonGuardar() {
        super("Tabla con botón guardar, agregar y eliminar");

        // Crear modelo de tabla
        modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Edad");
        modelo.addColumn("Ciudad");
        modelo.addRow(new Object[]{"Juan", 25, "Madrid"});
        modelo.addRow(new Object[]{"María", 30, "Barcelona"});
        modelo.addRow(new Object[]{"Pedro", 35, "Sevilla"});

        // Crear tabla con el modelo
        tabla = new JTable(modelo);

        // Crear botón guardar
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarTabla();
            }
        });

        // Crear botón agregar
        botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelo.addRow(new Object[]{"", null, ""});
            }
        });

        // Crear botón eliminar
        botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    modelo.removeRow(filaSeleccionada);
                }
            }
        });

        // Agregar tabla y botones al panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonGuardar);
        panelBotones.add(botonAgregar);
        panelBotones.add(botonEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);
        getContentPane().add(panel);

        // Mostrar ventana
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void guardarTabla() {
        try {
            FileOutputStream fileOut = new FileOutputStream("tabla.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(modelo);
            out.close();
            fileOut.close();
            System.out.println("Tabla guardada en tabla.ser");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void cargarTabla() {
        try {
            FileInputStream fileIn = new FileInputStream("tabla.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            DefaultTableModel modelo = (DefaultTableModel) in.readObject();
            in.close();
            fileIn.close();
            tabla.setModel(modelo);
            System.out.println("Tabla cargada desde tabla.ser");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TablaConBotonGuardar frame = new TablaConBotonGuardar();
        frame.cargarTabla();
    }
}