
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel2 extends JPanel {

    public Panel2() {
        setLayout(new FlowLayout());
        add(new JLabel("Este es el Panel 2"));
        add(new JButton("Botón 2"));
    }
    
}
