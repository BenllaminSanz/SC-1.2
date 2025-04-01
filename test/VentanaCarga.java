
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaCarga extends JDialog {
    // Constructor de la ventana de carga
    public VentanaCarga(JFrame parent) {
        super(parent, "Cargando", true);
        setPreferredSize(new Dimension(300, 100));
        // Añadir un mensaje de "cargando"
        JLabel mensaje = new JLabel("Cargando...");
        mensaje.setHorizontalAlignment(JLabel.CENTER);
        add(mensaje, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
    }
}