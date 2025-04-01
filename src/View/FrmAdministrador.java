package View;

public class FrmAdministrador extends javax.swing.JFrame {

    public FrmAdministrador() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Desktop_Administrador = new javax.swing.JDesktopPane();
        Panel_Funciones_Administrador = new javax.swing.JPanel();
        Panel_Botones = new javax.swing.JPanel();
        btn_AreasPuestos = new javax.swing.JButton();
        btn_Trabajadores = new javax.swing.JButton();
        btn_LBU = new javax.swing.JButton();
        btn_Capacitaciones = new javax.swing.JButton();
        jLabel_Logo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        itm_AreasPuestos = new javax.swing.JMenuItem();
        itm_Trabajdores = new javax.swing.JMenuItem();
        itm_LBU = new javax.swing.JMenuItem();
        itm_Capacitacion = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        itm_CmbTurnos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        Desktop_Administrador.setBackground(new java.awt.Color(175, 196, 174));

        Panel_Funciones_Administrador.setBackground(new java.awt.Color(255, 255, 255));

        Panel_Botones.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Botones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_AreasPuestos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_AreasPuestos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconArea.png"))); // NOI18N
        btn_AreasPuestos.setText("Áreas y Puestos");
        btn_AreasPuestos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        btn_AreasPuestos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_Botones.add(btn_AreasPuestos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 150, 45));

        btn_Trabajadores.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Trabajadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconWorker.png"))); // NOI18N
        btn_Trabajadores.setText("Trabajadores\n"); // NOI18N
        btn_Trabajadores.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        btn_Trabajadores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Trabajadores.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Panel_Botones.add(btn_Trabajadores, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 15, 150, 45));

        btn_LBU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_LBU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCotton.png"))); // NOI18N
        btn_LBU.setText("LBU Operativo");
        btn_LBU.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        btn_LBU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_Botones.add(btn_LBU, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 15, 150, 45));

        btn_Capacitaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Capacitaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCertifycate.png"))); // NOI18N
        btn_Capacitaciones.setText("Capacitación");
        btn_Capacitaciones.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        btn_Capacitaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_Botones.add(btn_Capacitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 15, 150, 45));

        jLabel_Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/LogoParkdale.png"))); // NOI18N

        javax.swing.GroupLayout Panel_Funciones_AdministradorLayout = new javax.swing.GroupLayout(Panel_Funciones_Administrador);
        Panel_Funciones_Administrador.setLayout(Panel_Funciones_AdministradorLayout);
        Panel_Funciones_AdministradorLayout.setHorizontalGroup(
            Panel_Funciones_AdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_Funciones_AdministradorLayout.createSequentialGroup()
                .addComponent(Panel_Botones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(jLabel_Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        Panel_Funciones_AdministradorLayout.setVerticalGroup(
            Panel_Funciones_AdministradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Botones, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_Funciones_AdministradorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jMenu1.setText("Navegar");

        itm_AreasPuestos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itm_AreasPuestos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconAreax16.png"))); // NOI18N
        itm_AreasPuestos.setText("Áreas y Puestos");
        jMenu1.add(itm_AreasPuestos);

        itm_Trabajdores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itm_Trabajdores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconWorkerx16.png"))); // NOI18N
        itm_Trabajdores.setText("Trabajadores");
        jMenu1.add(itm_Trabajdores);

        itm_LBU.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itm_LBU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCottonx16.png"))); // NOI18N
        itm_LBU.setText("LBU Operativo");
        jMenu1.add(itm_LBU);

        itm_Capacitacion.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itm_Capacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCertifycatex16.png"))); // NOI18N
        itm_Capacitacion.setText("Capacitación");
        jMenu1.add(itm_Capacitacion);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Funciones");

        itm_CmbTurnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconChangex16.png"))); // NOI18N
        itm_CmbTurnos.setText("Cambiar Turnos");
        jMenu2.add(itm_CmbTurnos);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Funciones_Administrador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Desktop_Administrador)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel_Funciones_Administrador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Desktop_Administrador, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane Desktop_Administrador;
    public javax.swing.JPanel Panel_Botones;
    private javax.swing.JPanel Panel_Funciones_Administrador;
    public javax.swing.JButton btn_AreasPuestos;
    public javax.swing.JButton btn_Capacitaciones;
    public javax.swing.JButton btn_LBU;
    public javax.swing.JButton btn_Trabajadores;
    public javax.swing.JMenuItem itm_AreasPuestos;
    public javax.swing.JMenuItem itm_Capacitacion;
    public javax.swing.JMenuItem itm_CmbTurnos;
    public javax.swing.JMenuItem itm_LBU;
    public javax.swing.JMenuItem itm_Trabajdores;
    public javax.swing.JLabel jLabel_Logo;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
