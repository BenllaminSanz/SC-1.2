//Controlador de la vista Login
//Recibe la estructura grafica del LogIn
/*Esta pantalla en la barrera de accesso del sistema, para que esta brinde 
seguridad de acceso y la conservacion de los datos integros del sistema*/
 /*Por ahora solo hay una vista de usuario por lo que solo se tiene acceso a 
la vista del administrador, aun falta definir si habra mas usuarios y sus funcines
dentro del sistema asi como sus limitaciones*/
package Controller;

import Functions.TextPrompt;
import General.InicioAdministrador;
import Querys.ConsultasLogin;
import Model.Usuario;
import View.FrmLogin;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CtrlLogin implements ActionListener {

    private final Usuario mod;
    private final ConsultasLogin modC;
    private final FrmLogin frm;

    private String user = "";
    private String password = "";

    public CtrlLogin(Usuario mod, ConsultasLogin modC, FrmLogin frm) {
        this.mod = mod;
        this.modC = modC;
        this.frm = frm;
        this.frm.btn_Access.addActionListener(this);
        this.frm.txt_User.addActionListener(this);
        this.frm.txt_password.addActionListener(this);
    }

    public void iniciar() {
        setLookAndFeel();
        setWindowProperties();
        setLogo();
        setPlaceholderText();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | ClassNotFoundException ex) {
            Logger.getLogger(CtrlLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setWindowProperties() {
        frm.setSize(400, 250);
        frm.setResizable(false);
        frm.setTitle("Acceso al sistema");
        frm.setLocationRelativeTo(null);
    }

    private void setLogo() {
        ImageIcon logo = new ImageIcon("src/Images/LogoParkdale.png");
        Icon icon = new ImageIcon(logo.getImage().getScaledInstance(
                frm.jLabel_Logo.getWidth(), frm.jLabel_Logo.getHeight(), Image.SCALE_SMOOTH));
        frm.jLabel_Logo.setIcon(icon);
    }

    private void setPlaceholderText() {
        TextPrompt placeholder1 = new TextPrompt("Usuario", frm.txt_User);
        TextPrompt placeholder2 = new TextPrompt("Contraseña", frm.txt_password);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btn_Access) {
            LogIn();
        }

        if (e.getSource() == frm.txt_User) {
            frm.btn_Access.doClick(); // Llamar al método LogIn() al presionar "Enter"
        }

        if (e.getSource() == frm.txt_password) {
            frm.btn_Access.doClick(); // Llamar al método LogIn() al presionar "Enter"
        }
    }

    public void LogIn() {
        //Guardamos los datos escritos en los textField
        user = frm.txt_User.getText().trim();
        //Sentencia modificada
        //frm.txt_password.getText().trim()
        char[] passwordChars = frm.txt_password.getPassword();
        password = new String(passwordChars);
        
        // aplicamos trim() aquí para eliminar espacios en blanco al inicio y final de la cadena

        //Comprobamos que los campos no estén vacíos
        if (!user.equals("") && !password.equals("")) {
            // usamos el operador && en lugar de ||, para comprobar que ambos campos están llenos
            mod.setUsername(user);
            mod.setPassword(password);

            //Llamamos al metodo de consultas con la BD
            if (modC.iniciar(mod)) {
                //Condiciones de acceso a los diferentes tipos de usuario
                if (mod.getTipo_nivel().equalsIgnoreCase("Administrador")
                        && mod.getStatus().equalsIgnoreCase("Activo")) {
                    frm.dispose();
                    InicioAdministrador.main(null);
                } else if (mod.getTipo_nivel().equalsIgnoreCase("Capturista")
                        && mod.getStatus().equalsIgnoreCase("Activo")) {
                    frm.dispose();
                    //new FrmAdministrador().setVisible(true);
                } else if (mod.getTipo_nivel().equalsIgnoreCase("Tecnico")
                        && mod.getStatus().equalsIgnoreCase("Activo")) {
                    frm.dispose();
                    //new FrmAdministrador().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Datos de acceso incorrectos");
                frm.txt_User.setText("");
                frm.txt_password.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos");
        }
    }
}
