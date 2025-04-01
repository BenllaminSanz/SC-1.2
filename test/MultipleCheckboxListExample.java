import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.FlowLayout;

public class MultipleCheckboxListExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Selección múltiple con checkboxes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Crear una lista de checkboxes con áreas, puestos y turnos
        DefaultListModel<JCheckBox> model = new DefaultListModel<>();
        JList<JCheckBox> checkboxList = new JList<>(model);
        checkboxList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(checkboxList);
        frame.add(scrollPane);

        // Agregar áreas, puestos y turnos a la lista de checkboxes
        JCheckBox area1 = new JCheckBox("Área 1");
        JCheckBox area2 = new JCheckBox("Área 2");
        JCheckBox area3 = new JCheckBox("Área 3");
        JCheckBox puesto1 = new JCheckBox("Puesto 1");
        JCheckBox puesto2 = new JCheckBox("Puesto 2");
        JCheckBox puesto3 = new JCheckBox("Puesto 3");
        JCheckBox turno1 = new JCheckBox("Turno 1");
        JCheckBox turno2 = new JCheckBox("Turno 2");
        JCheckBox turno3 = new JCheckBox("Turno 3");

        model.addElement(area1);
        model.addElement(area2);
        model.addElement(area3);
        model.addElement(puesto1);
        model.addElement(puesto2);
        model.addElement(puesto3);
        model.addElement(turno1);
        model.addElement(turno2);
        model.addElement(turno3);

        // Agregar un listener para manejar los eventos de selección/deselección de checkboxes
        checkboxList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
                        JCheckBox checkbox = model.getElementAt(i);
                        checkbox.setSelected(checkboxList.isSelectedIndex(i));
                        if (checkbox.isSelected()) {
                            System.out.println("Seleccionado: " + checkbox.getText());
                        }
                    }
                }
            }
        });

        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
