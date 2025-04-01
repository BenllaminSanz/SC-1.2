import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaTamaño extends JFrame {

  private JButton boton;
  private int ancho = 200;
  private int alto = 200;

  public VentanaTamaño() {
    super("Ejemplo de redimensionamiento");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    boton = new JButton("Aumentar tamaño");
    boton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ancho += 20;
        alto += 20;
        setSize(new Dimension(ancho, alto));
      }
    });
    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(boton);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public static void main(String[] args) {
    new VentanaTamaño();
  }
}
