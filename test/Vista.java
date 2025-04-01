
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Vista extends JFrame {

    private JComboBox<String> comboBox;
    private JPanel[] paneles = {new Panel1(), new Panel2()};
    private JPanel panelActual;

    public Vista() {
        comboBox = new JComboBox<>(new Modelo().getOpciones());
        comboBox.addItemListener(new Controlador(this));
        panelActual = paneles[0];
        add(comboBox, BorderLayout.NORTH);
        add(panelActual, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
    }

    public void cambiarPanel(int index) {
        remove(panelActual);
        panelActual = paneles[index];
        add(panelActual, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        new Vista();
    }
}
