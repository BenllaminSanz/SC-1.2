package View;

import javax.swing.ButtonGroup;

public class IFrmTrabajador extends javax.swing.JInternalFrame {

    public static String x;
    public ButtonGroup grupoStatus = new ButtonGroup();

    public IFrmTrabajador() {
        initComponents();
        x = "x";

        grupoStatus.add(CheckActivo);
        CheckActivo.setActionCommand("Activo");
        grupoStatus.add(CheckIncapacidad);
        CheckIncapacidad.setActionCommand("Incapacidad");
        grupoStatus.add(CheckAccidente);
        CheckAccidente.setActionCommand("Incapacidad por Accidente");
        grupoStatus.add(CheckMaternidad);
        CheckMaternidad.setActionCommand("Incapacidad por Maternidad");
        grupoStatus.add(CheckTrayecto);
        CheckTrayecto.setActionCommand("Incapacidad por Accidente en Trayecto");
        grupoStatus.add(CheckEnfermedad);
        CheckEnfermedad.setActionCommand("Incapacidad por Enfermedad");
        grupoStatus.add(CheckLactancia);
        CheckLactancia.setActionCommand("Lactancia");
        grupoStatus.add(CheckBaja);
        CheckBaja.setActionCommand("Baja Tentativa");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopMenu_Trabajador = new javax.swing.JPopupMenu();
        MenuEstado = new javax.swing.JMenu();
        CheckActivo = new javax.swing.JRadioButtonMenuItem();
        CheckIncapacidad = new javax.swing.JRadioButtonMenuItem();
        CheckAccidente = new javax.swing.JRadioButtonMenuItem();
        CheckMaternidad = new javax.swing.JRadioButtonMenuItem();
        CheckTrayecto = new javax.swing.JRadioButtonMenuItem();
        CheckEnfermedad = new javax.swing.JRadioButtonMenuItem();
        CheckLactancia = new javax.swing.JRadioButtonMenuItem();
        CheckBaja = new javax.swing.JRadioButtonMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        ItemAgregar = new javax.swing.JMenuItem();
        ItemModificar = new javax.swing.JMenuItem();
        ItemEliminar = new javax.swing.JMenuItem();
        PopMenu_Baja = new javax.swing.JPopupMenu();
        ItemModificar_Baja = new javax.swing.JMenuItem();
        ItemRestaurar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ItemEliminar_Baja = new javax.swing.JMenuItem();
        PopMenu_Supervisor = new javax.swing.JPopupMenu();
        ItemAgregarS = new javax.swing.JMenuItem();
        ItemModificarS = new javax.swing.JMenuItem();
        ItemEliminarS = new javax.swing.JMenuItem();
        PopMenu_Administrativo = new javax.swing.JPopupMenu();
        item_AgregarAdministrativo = new javax.swing.JMenuItem();
        item_ModificarAdministrativo = new javax.swing.JMenuItem();
        item_EliminarAdministrativo = new javax.swing.JMenuItem();
        PopMenu_Brigadas = new javax.swing.JPopupMenu();
        item_AgregarBrigada = new javax.swing.JMenuItem();
        item_ModificarBrigada = new javax.swing.JMenuItem();
        item_EliminarBrigada = new javax.swing.JMenuItem();
        PopMenu_Brigadistas = new javax.swing.JPopupMenu();
        item_EliminarBrigadista = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Panel_Trabajador = new javax.swing.JPanel();
        label_buscar = new javax.swing.JLabel();
        txt_buscar_Tbr = new javax.swing.JTextField();
        btn_AgregarTrabajador = new javax.swing.JButton();
        btn_RefreshTabla = new javax.swing.JButton();
        Panel_Atajos = new javax.swing.JPanel();
        txt_panel1 = new javax.swing.JLabel();
        txt_total = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        jTable_Trabajadores = new javax.swing.JTable();
        Panel_Bajas = new javax.swing.JPanel();
        txt_buscar_bajas = new javax.swing.JTextField();
        btn_RefreshTabla2 = new javax.swing.JButton();
        ScrollPane1 = new javax.swing.JScrollPane();
        jTable_Bajas = new javax.swing.JTable();
        label_buscar3 = new javax.swing.JLabel();
        Panel_Supervisores = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Supervisores = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txt_NombreSupervisor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cb_Area = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txt_Propuesto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_LV = new javax.swing.JRadioButton();
        btn_LS = new javax.swing.JRadioButton();
        btn_A = new javax.swing.JRadioButton();
        btn_B = new javax.swing.JRadioButton();
        btn_C = new javax.swing.JRadioButton();
        btn_D = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txt_nomina_sup = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btn_guardar_sup = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_Administrativos = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_brigadas = new javax.swing.JTable();
        btn_agregarBrigada = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_brigadistas = new javax.swing.JTable();
        btn_agregarBrigadista = new javax.swing.JButton();
        btn_RefreshTabla3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btn_eliminarBrigadista = new javax.swing.JButton();
        Bar_Trabajador = new javax.swing.JMenuBar();
        Menu_NuevoTrabajador = new javax.swing.JMenu();
        Item_NuevoTrabajador = new javax.swing.JMenuItem();
        Menu_NuevosTrabajadores = new javax.swing.JMenu();
        Item_TrabajadoresExcel = new javax.swing.JMenuItem();
        Item_TrabajadoresExcel_NO = new javax.swing.JMenuItem();
        Item_EditarLecturaExcel = new javax.swing.JMenuItem();
        Item_Lista_Nuevos_Tbr = new javax.swing.JMenuItem();
        Menu_Bajas = new javax.swing.JMenu();
        Item_Lista_Bajas_Tbr = new javax.swing.JMenuItem();
        Menu_Salarios = new javax.swing.JMenu();
        item_nivelesSalarios = new javax.swing.JMenuItem();

        MenuEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCheckx16.png"))); // NOI18N
        MenuEstado.setText("Status");

        CheckActivo.setText("Activo");
        MenuEstado.add(CheckActivo);

        CheckIncapacidad.setText("Incapacidad");
        MenuEstado.add(CheckIncapacidad);

        CheckAccidente.setText("Incapacidad por Accidente");
        MenuEstado.add(CheckAccidente);

        CheckMaternidad.setText("Incapacidad por Maternidad");
        MenuEstado.add(CheckMaternidad);

        CheckTrayecto.setText("Incapacidad por Accidente en Trayecto");
        CheckTrayecto.setToolTipText("");
        MenuEstado.add(CheckTrayecto);

        CheckEnfermedad.setText("Incapacidad por Enfermedad");
        CheckEnfermedad.setActionCommand("Promoción");
        MenuEstado.add(CheckEnfermedad);

        CheckLactancia.setText("Lactancia");
        MenuEstado.add(CheckLactancia);

        CheckBaja.setSelected(true);
        CheckBaja.setText("Baja Tentativa");
        MenuEstado.add(CheckBaja);

        PopMenu_Trabajador.add(MenuEstado);
        PopMenu_Trabajador.add(jSeparator1);

        ItemAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        ItemAgregar.setText("Agregar");
        PopMenu_Trabajador.add(ItemAgregar);

        ItemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        ItemModificar.setText("Modificar");
        PopMenu_Trabajador.add(ItemModificar);

        ItemEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        ItemEliminar.setText("Dar de Baja");
        PopMenu_Trabajador.add(ItemEliminar);

        ItemModificar_Baja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        ItemModificar_Baja.setText("Modificar");
        PopMenu_Baja.add(ItemModificar_Baja);

        ItemRestaurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        ItemRestaurar.setText("Restaurar");
        PopMenu_Baja.add(ItemRestaurar);
        PopMenu_Baja.add(jSeparator2);

        ItemEliminar_Baja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        ItemEliminar_Baja.setText("Eliminar");
        PopMenu_Baja.add(ItemEliminar_Baja);

        ItemAgregarS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        ItemAgregarS.setText("Agregar Supervisor");
        PopMenu_Supervisor.add(ItemAgregarS);

        ItemModificarS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        ItemModificarS.setText("Modificar");
        PopMenu_Supervisor.add(ItemModificarS);

        ItemEliminarS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        ItemEliminarS.setText("Eliminar");
        PopMenu_Supervisor.add(ItemEliminarS);

        item_AgregarAdministrativo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarAdministrativo.setText("Agregar Administrativo");
        PopMenu_Administrativo.add(item_AgregarAdministrativo);

        item_ModificarAdministrativo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarAdministrativo.setText("Modificar Administrativo");
        item_ModificarAdministrativo.setToolTipText("");
        PopMenu_Administrativo.add(item_ModificarAdministrativo);

        item_EliminarAdministrativo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        item_EliminarAdministrativo.setText("Eliminar Administrativo");
        PopMenu_Administrativo.add(item_EliminarAdministrativo);

        item_AgregarBrigada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarBrigada.setText("Agregar Brigada");
        PopMenu_Brigadas.add(item_AgregarBrigada);

        item_ModificarBrigada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarBrigada.setText("Modificar Brigada");
        PopMenu_Brigadas.add(item_ModificarBrigada);

        item_EliminarBrigada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        item_EliminarBrigada.setText("Eliminar Brigada");
        PopMenu_Brigadas.add(item_EliminarBrigada);

        item_EliminarBrigadista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        item_EliminarBrigadista.setText("Eliminar Brigadista");
        PopMenu_Brigadistas.add(item_EliminarBrigadista);

        jMenuItem1.setText("jMenuItem1");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Trabajadores");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(977, 617));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        Panel_Trabajador.setName(""); // NOI18N

        label_buscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label_buscar.setText("Buscar Trabajador:");

        txt_buscar_Tbr.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_AgregarTrabajador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconNewWorker.png"))); // NOI18N
        btn_AgregarTrabajador.setToolTipText("Agregar Trabajador");
        btn_AgregarTrabajador.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_RefreshTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N
        btn_RefreshTabla.setToolTipText("Refrescar Tabla");
        btn_RefreshTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Panel_Atajos.setBackground(new java.awt.Color(36, 109, 45));

        txt_panel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_panel1.setForeground(new java.awt.Color(255, 255, 255));
        txt_panel1.setText("Trabajadores en Planta:");

        txt_total.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_total.setForeground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(36, 109, 45));
        jPanel5.setAutoscrolls(true);

        btn1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn1.setForeground(new java.awt.Color(255, 204, 0));
        btn1.setToolTipText("Click para seleccionar");
        btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn2.setForeground(new java.awt.Color(153, 0, 0));
        btn2.setToolTipText("Click para seleccionar");
        btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn3.setForeground(new java.awt.Color(255, 102, 0));
        btn3.setToolTipText("Click para seleccionar");
        btn3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn4.setForeground(new java.awt.Color(0, 102, 0));
        btn4.setToolTipText("Click para seleccionar");
        btn4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn5.setForeground(new java.awt.Color(255, 153, 204));
        btn5.setToolTipText("Click para seleccionar");
        btn5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn6.setForeground(new java.awt.Color(102, 204, 255));
        btn6.setToolTipText("Click para seleccionar");
        btn6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn7.setForeground(new java.awt.Color(2, 102, 102));
        btn7.setToolTipText("Click para seleccionar");
        btn7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Panel_AtajosLayout = new javax.swing.GroupLayout(Panel_Atajos);
        Panel_Atajos.setLayout(Panel_AtajosLayout);
        Panel_AtajosLayout.setHorizontalGroup(
            Panel_AtajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AtajosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_panel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel_AtajosLayout.setVerticalGroup(
            Panel_AtajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AtajosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_AtajosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AtajosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTable_Trabajadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollPane.setViewportView(jTable_Trabajadores);

        javax.swing.GroupLayout Panel_TrabajadorLayout = new javax.swing.GroupLayout(Panel_Trabajador);
        Panel_Trabajador.setLayout(Panel_TrabajadorLayout);
        Panel_TrabajadorLayout.setHorizontalGroup(
            Panel_TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel_Atajos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Panel_TrabajadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane)
                    .addGroup(Panel_TrabajadorLayout.createSequentialGroup()
                        .addComponent(label_buscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_Tbr, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_AgregarTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        Panel_TrabajadorLayout.setVerticalGroup(
            Panel_TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_TrabajadorLayout.createSequentialGroup()
                .addGroup(Panel_TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Panel_TrabajadorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Panel_TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_TrabajadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(label_buscar)
                                .addComponent(txt_buscar_Tbr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_AgregarTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel_Atajos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ScrollPane.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("Trabajadores", Panel_Trabajador);

        txt_buscar_bajas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_RefreshTabla2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jTable_Bajas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollPane1.setViewportView(jTable_Bajas);

        label_buscar3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label_buscar3.setText("Buscar Trabajador:");

        javax.swing.GroupLayout Panel_BajasLayout = new javax.swing.GroupLayout(Panel_Bajas);
        Panel_Bajas.setLayout(Panel_BajasLayout);
        Panel_BajasLayout.setHorizontalGroup(
            Panel_BajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_BajasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_BajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane1)
                    .addGroup(Panel_BajasLayout.createSequentialGroup()
                        .addComponent(label_buscar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_bajas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        Panel_BajasLayout.setVerticalGroup(
            Panel_BajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_BajasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_BajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_BajasLayout.createSequentialGroup()
                        .addGroup(Panel_BajasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_buscar3)
                            .addComponent(txt_buscar_bajas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 504, Short.MAX_VALUE))
                    .addGroup(Panel_BajasLayout.createSequentialGroup()
                        .addComponent(btn_RefreshTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ScrollPane1)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bajas", Panel_Bajas);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Supervisores:"));

        jTable_Supervisores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_Supervisores);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Supervisor:"));

        jLabel1.setText("Nombre del Supervisor:");

        jLabel4.setText("Área asignada:");

        jLabel2.setText("Propuesto de Trabajadores:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Turnos a su cargo:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        btn_LV.setText("Turno LV");

        btn_LS.setText("Turno LS");

        btn_A.setText("Turno A");

        btn_B.setText("Turno B");

        btn_C.setText("Turno C");

        btn_D.setText("Turno D");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_A)
                        .addGap(15, 15, 15)
                        .addComponent(btn_LV))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_B)
                        .addGap(15, 15, 15)
                        .addComponent(btn_LS))
                    .addComponent(btn_C)
                    .addComponent(btn_D))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_A)
                    .addComponent(btn_LV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_B)
                    .addComponent(btn_LS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_C)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_D)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Orden del Supervisor en LBU:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txt_Propuesto))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(txt_NombreSupervisor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_nomina_sup, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cb_Area, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nomina_sup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_NombreSupervisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_Area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Propuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Operaciónes"));

        btn_guardar_sup.setText("Guardar");

        btn_limpiar.setText("Limpiar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_guardar_sup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_guardar_sup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_limpiar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Panel_SupervisoresLayout = new javax.swing.GroupLayout(Panel_Supervisores);
        Panel_Supervisores.setLayout(Panel_SupervisoresLayout);
        Panel_SupervisoresLayout.setHorizontalGroup(
            Panel_SupervisoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_SupervisoresLayout.createSequentialGroup()
                .addContainerGap(180, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_SupervisoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(181, Short.MAX_VALUE))
        );
        Panel_SupervisoresLayout.setVerticalGroup(
            Panel_SupervisoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_SupervisoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_SupervisoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(Panel_SupervisoresLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Supervisores", Panel_Supervisores);

        jTable_Administrativos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable_Administrativos);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Administrativos", jPanel6);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Brigadas"));
        jPanel8.setMaximumSize(new java.awt.Dimension(500, 32767));

        jTable_brigadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable_brigadas);

        btn_agregarBrigada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_agregarBrigada.setText("Agregar Brigada");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_agregarBrigada)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_agregarBrigada))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Brigadistas"));

        jTable_brigadistas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable_brigadistas);

        btn_agregarBrigadista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_agregarBrigadista.setText("Agregar Brigadista");

        btn_RefreshTabla3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N
        btn_RefreshTabla3.setToolTipText("Refrescar Tabla");
        btn_RefreshTabla3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("jLabel5");

        btn_eliminarBrigadista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        btn_eliminarBrigadista.setText("Eliminar Brigadista");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_agregarBrigadista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminarBrigadista))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_RefreshTabla3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_agregarBrigadista)
                    .addComponent(btn_eliminarBrigadista)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jTabbedPane1.addTab("Brigadas", jPanel7);

        Menu_NuevoTrabajador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        Menu_NuevoTrabajador.setText("Nuevo(s)");

        Item_NuevoTrabajador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        Item_NuevoTrabajador.setText("Trabajador");
        Menu_NuevoTrabajador.add(Item_NuevoTrabajador);

        Menu_NuevosTrabajadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        Menu_NuevosTrabajadores.setText("Trabajadores");

        Item_TrabajadoresExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        Item_TrabajadoresExcel.setText("Via Excel (Actualizar)");
        Menu_NuevosTrabajadores.add(Item_TrabajadoresExcel);

        Item_TrabajadoresExcel_NO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        Item_TrabajadoresExcel_NO.setText("Via Excel (No actualizar)");
        Menu_NuevosTrabajadores.add(Item_TrabajadoresExcel_NO);

        Item_EditarLecturaExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        Item_EditarLecturaExcel.setText("Editar Columnas");
        Menu_NuevosTrabajadores.add(Item_EditarLecturaExcel);

        Menu_NuevoTrabajador.add(Menu_NuevosTrabajadores);

        Item_Lista_Nuevos_Tbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        Item_Lista_Nuevos_Tbr.setText("Listar Nuevos");
        Menu_NuevoTrabajador.add(Item_Lista_Nuevos_Tbr);

        Bar_Trabajador.add(Menu_NuevoTrabajador);

        Menu_Bajas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        Menu_Bajas.setText("Bajas");

        Item_Lista_Bajas_Tbr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        Item_Lista_Bajas_Tbr.setText("Listar Bajas");
        Menu_Bajas.add(Item_Lista_Bajas_Tbr);

        Bar_Trabajador.add(Menu_Bajas);

        Menu_Salarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconArrowDown.png"))); // NOI18N
        Menu_Salarios.setText("Salarios");

        item_nivelesSalarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEntrenamientox16.png"))); // NOI18N
        item_nivelesSalarios.setText("Modificar Niveles");
        Menu_Salarios.add(item_nivelesSalarios);

        Bar_Trabajador.add(Menu_Salarios);

        setJMenuBar(Bar_Trabajador);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        x = null;
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Bar_Trabajador;
    public javax.swing.JRadioButtonMenuItem CheckAccidente;
    public javax.swing.JRadioButtonMenuItem CheckActivo;
    public javax.swing.JRadioButtonMenuItem CheckBaja;
    public javax.swing.JRadioButtonMenuItem CheckEnfermedad;
    public javax.swing.JRadioButtonMenuItem CheckIncapacidad;
    public javax.swing.JRadioButtonMenuItem CheckLactancia;
    public javax.swing.JRadioButtonMenuItem CheckMaternidad;
    public javax.swing.JRadioButtonMenuItem CheckTrayecto;
    public javax.swing.JMenuItem ItemAgregar;
    public javax.swing.JMenuItem ItemAgregarS;
    public javax.swing.JMenuItem ItemEliminar;
    public javax.swing.JMenuItem ItemEliminarS;
    public javax.swing.JMenuItem ItemEliminar_Baja;
    public javax.swing.JMenuItem ItemModificar;
    public javax.swing.JMenuItem ItemModificarS;
    public javax.swing.JMenuItem ItemModificar_Baja;
    public javax.swing.JMenuItem ItemRestaurar;
    public javax.swing.JMenuItem Item_EditarLecturaExcel;
    public javax.swing.JMenuItem Item_Lista_Bajas_Tbr;
    public javax.swing.JMenuItem Item_Lista_Nuevos_Tbr;
    public javax.swing.JMenuItem Item_NuevoTrabajador;
    public javax.swing.JMenuItem Item_TrabajadoresExcel;
    public javax.swing.JMenuItem Item_TrabajadoresExcel_NO;
    public javax.swing.JMenu MenuEstado;
    private javax.swing.JMenu Menu_Bajas;
    public javax.swing.JMenu Menu_NuevoTrabajador;
    private javax.swing.JMenu Menu_NuevosTrabajadores;
    private javax.swing.JMenu Menu_Salarios;
    private javax.swing.JPanel Panel_Atajos;
    public javax.swing.JPanel Panel_Bajas;
    private javax.swing.JPanel Panel_Supervisores;
    private javax.swing.JPanel Panel_Trabajador;
    public javax.swing.JPopupMenu PopMenu_Administrativo;
    public javax.swing.JPopupMenu PopMenu_Baja;
    public javax.swing.JPopupMenu PopMenu_Brigadas;
    public javax.swing.JPopupMenu PopMenu_Brigadistas;
    public javax.swing.JPopupMenu PopMenu_Supervisor;
    public javax.swing.JPopupMenu PopMenu_Trabajador;
    public javax.swing.JScrollPane ScrollPane;
    public javax.swing.JScrollPane ScrollPane1;
    public javax.swing.JButton btn1;
    public javax.swing.JButton btn2;
    public javax.swing.JButton btn3;
    public javax.swing.JButton btn4;
    public javax.swing.JButton btn5;
    public javax.swing.JButton btn6;
    public javax.swing.JButton btn7;
    public javax.swing.JRadioButton btn_A;
    public javax.swing.JButton btn_AgregarTrabajador;
    public javax.swing.JRadioButton btn_B;
    public javax.swing.JRadioButton btn_C;
    public javax.swing.JRadioButton btn_D;
    public javax.swing.JRadioButton btn_LS;
    public javax.swing.JRadioButton btn_LV;
    public javax.swing.JButton btn_RefreshTabla;
    public javax.swing.JButton btn_RefreshTabla2;
    public javax.swing.JButton btn_RefreshTabla3;
    public javax.swing.JButton btn_agregarBrigada;
    public javax.swing.JButton btn_agregarBrigadista;
    public javax.swing.JButton btn_eliminarBrigadista;
    public javax.swing.JButton btn_guardar_sup;
    public javax.swing.JButton btn_limpiar;
    public javax.swing.JComboBox<String> cb_Area;
    public javax.swing.JMenuItem item_AgregarAdministrativo;
    public javax.swing.JMenuItem item_AgregarBrigada;
    public javax.swing.JMenuItem item_EliminarAdministrativo;
    public javax.swing.JMenuItem item_EliminarBrigada;
    public javax.swing.JMenuItem item_EliminarBrigadista;
    public javax.swing.JMenuItem item_ModificarAdministrativo;
    public javax.swing.JMenuItem item_ModificarBrigada;
    public javax.swing.JMenuItem item_nivelesSalarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable_Administrativos;
    public javax.swing.JTable jTable_Bajas;
    public javax.swing.JTable jTable_Supervisores;
    public javax.swing.JTable jTable_Trabajadores;
    public javax.swing.JTable jTable_brigadas;
    public javax.swing.JTable jTable_brigadistas;
    private javax.swing.JLabel label_buscar;
    private javax.swing.JLabel label_buscar3;
    public javax.swing.JTextField txt_NombreSupervisor;
    public javax.swing.JTextField txt_Propuesto;
    public javax.swing.JTextField txt_buscar_Tbr;
    public javax.swing.JTextField txt_buscar_bajas;
    public javax.swing.JTextField txt_nomina_sup;
    private javax.swing.JLabel txt_panel1;
    public javax.swing.JLabel txt_total;
    // End of variables declaration//GEN-END:variables
}
