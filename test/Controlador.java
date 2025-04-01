
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

public class Controlador implements ItemListener {
    private Vista vista;
    
    public Controlador(Vista vista) {
        this.vista = vista;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            int index = comboBox.getSelectedIndex();
            vista.cambiarPanel(index);
        }
    }
}
