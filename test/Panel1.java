
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Panel1 extends JPanel {

    public Panel1() {
        setLayout(new FlowLayout());
        add(new JLabel("Este es el Panel 1"));
        add(new JButton("Botón 1"));
    }
    
}
