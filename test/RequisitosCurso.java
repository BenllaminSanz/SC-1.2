import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RequisitosCurso extends JFrame {

    private List<Requisito> requisitos;
    private JPanel pnlRequisitos;
    private JButton btnImprimir;

    public RequisitosCurso(List<Requisito> requisitos) {
        this.requisitos = requisitos;
        initComponents();
    }

    private void initComponents() {
        setTitle("Requisitos del curso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel que contendrá los requisitos
        pnlRequisitos = new JPanel();
        pnlRequisitos.setLayout(new BoxLayout(pnlRequisitos, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pnlRequisitos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Crear los paneles de los requisitos
        for (Requisito r : requisitos) {
            JPanel pnlRequisito = new JPanel();
            pnlRequisito.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pnlRequisito.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel lblNombre = new JLabel(r.getNombre());
            lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
            pnlRequisito.add(lblNombre);
            JCheckBox chkCumplido = new JCheckBox("Cumplido");
            pnlRequisito.add(chkCumplido);
            pnlRequisitos.add(pnlRequisito);
        }

        // Crear el botón para imprimir
        btnImprimir = new JButton("Imprimir");
        btnImprimir.setPreferredSize(new Dimension(100, 30));
        JPanel pnlBotones = new JPanel();
        pnlBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlBotones.add(btnImprimir);
        add(pnlBotones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Crear una lista de requisitos ficticios
        List<Requisito> requisitos = new ArrayList<>();
        requisitos.add(new Requisito("Certificado de estudios"));
        requisitos.add(new Requisito("Constancia de experiencia"));
        requisitos.add(new Requisito("Carta de recomendación"));

        // Crear la ventana y mostrarla
        RequisitosCurso ventana = new RequisitosCurso(requisitos);
        ventana.setVisible(true);
    }

}

class Requisito {
    private String nombre;

    public Requisito(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
