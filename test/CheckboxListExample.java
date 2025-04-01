import javax.swing.*;
import java.awt.event.*;

public class CheckboxListExample {
    private final JFrame frame;
    private final JPanel panel;
    
    public CheckboxListExample() {
        frame = new JFrame("Checkbox List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Crea los checkboxes
        JCheckBox checkBox1 = new JCheckBox("Opción 1");
        JCheckBox checkBox2 = new JCheckBox("Opción 2");
        JCheckBox checkBox3 = new JCheckBox("Opción 3");
        
        // Agrega los checkboxes al panel
        panel.add(checkBox1);
        panel.add(checkBox2);
        panel.add(checkBox3);
        
        // Agrega un listener de eventos al botón
        checkBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    System.out.println("Checkbox 1 seleccionado");
                } else {
                    System.out.println("Checkbox 1 deseleccionado");
                }
            }
        });
        
        // Agrega el panel al frame
        frame.add(panel);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CheckboxListExample();
            }
        });
    }
}
