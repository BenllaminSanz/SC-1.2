import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Cursor_Espera extends JFrame {

  private JButton boton;

  public Cursor_Espera() {
    super("Ejemplo de cambio de cursor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    boton = new JButton("Mostrar ventana");
    boton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // Simulamos la carga de la ventana con un retraso de 3 segundos
        try {
          Thread.sleep(3000);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        }
        setCursor(Cursor.getDefaultCursor());
        // Mostramos la ventana
        // ...
      }
    });
    getContentPane().add(boton);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public static void main(String[] args) {
    new Cursor_Espera();
  }
}

