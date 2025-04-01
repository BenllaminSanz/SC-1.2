import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public class TablaSerializable extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public TablaSerializable() {
        super("Tabla Serializable");

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

        // Agregar tabla al panel principal
        JScrollPane scrollPane = new JScrollPane(tabla);
        getContentPane().add(scrollPane);

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
        TablaSerializable frame = new TablaSerializable();
        frame.guardarTabla();
        frame.cargarTabla();
    }
}
