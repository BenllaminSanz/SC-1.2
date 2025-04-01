
package View;

import java.awt.Image;
import java.awt.Toolkit;

public class FrmLogin extends javax.swing.JFrame {

    public FrmLogin() {
        initComponents();
    }
    
    //Ponemos el icono de la empresa
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(
                "Images/IconParkdale.png"));
        return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelLogin = new javax.swing.JPanel();
        jLabel_Logo = new javax.swing.JLabel();
        txt_User = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        btn_Access = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(400, 250));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PanelLogin.add(jLabel_Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 300, 75));

        txt_User.setToolTipText("");
        txt_User.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txt_User.setName(""); // NOI18N
        txt_User.setOpaque(true);
        PanelLogin.add(txt_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 110, -1));

        txt_password.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelLogin.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 110, -1));

        btn_Access.setBackground(new java.awt.Color(0, 102, 0));
        btn_Access.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Access.setForeground(new java.awt.Color(255, 255, 255));
        btn_Access.setText("Acceder");
        btn_Access.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelLogin.add(btn_Access, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 110, -1));

        getContentPane().add(PanelLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 250));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelLogin;
    public javax.swing.JButton btn_Access;
    public javax.swing.JLabel jLabel_Logo;
    public javax.swing.JTextField txt_User;
    public javax.swing.JPasswordField txt_password;
    // End of variables declaration//GEN-END:variables
}
