package Subviews;

import Functions.QueryFunctions;
import java.awt.event.ItemEvent;

public class IFrmAgregarAsistente extends javax.swing.JInternalFrame {

    public IFrmAgregarAsistente() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopMenu_TrabajadorCapacitacion = new javax.swing.JPopupMenu();
        item_AgregarTrabajadorCapacitacion = new javax.swing.JMenuItem();
        PopMenu_SupervisorCapacitacion = new javax.swing.JPopupMenu();
        item_AgregarTrabajadorCapacitacion1 = new javax.swing.JMenuItem();
        PopMenu_AdministrativoCapacitacion = new javax.swing.JPopupMenu();
        item_AgregarAdministrativo = new javax.swing.JMenuItem();
        PopMenu_BrigadistaCapacitacion = new javax.swing.JPopupMenu();
        item_AgregarBrigadista = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txt_buscar_Tbr = new javax.swing.JTextField();
        btn_RefreshTabla = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        jTable_Trabajadores = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        cb_area = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cb_puesto = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cb_turno = new javax.swing.JComboBox<>();
        btn_Consultar = new javax.swing.JButton();
        label_buscar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        label_buscar1 = new javax.swing.JLabel();
        txt_buscar_Tbr1 = new javax.swing.JTextField();
        btn_RefreshTabla1 = new javax.swing.JButton();
        ScrollPane1 = new javax.swing.JScrollPane();
        jTable_Trabajadores1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        label_buscar2 = new javax.swing.JLabel();
        txt_buscar_Tbr2 = new javax.swing.JTextField();
        btn_RefreshTabla2 = new javax.swing.JButton();
        ScrollPane2 = new javax.swing.JScrollPane();
        jTable_Administrativos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        label_buscar4 = new javax.swing.JLabel();
        txt_buscar_Tbr4 = new javax.swing.JTextField();
        btn_RefreshTabla4 = new javax.swing.JButton();
        ScrollPane4 = new javax.swing.JScrollPane();
        jTable_Administrativos1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_puesto = new javax.swing.JTextField();
        btn_agregar_otros = new javax.swing.JButton();
        btn_limpiar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();

        item_AgregarTrabajadorCapacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarTrabajadorCapacitacion.setText("Agregar a Capacitación");
        PopMenu_TrabajadorCapacitacion.add(item_AgregarTrabajadorCapacitacion);

        item_AgregarTrabajadorCapacitacion1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarTrabajadorCapacitacion1.setText("Agregar a Capacitación");
        PopMenu_SupervisorCapacitacion.add(item_AgregarTrabajadorCapacitacion1);

        item_AgregarAdministrativo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarAdministrativo.setText("Agregar Administrativo");
        PopMenu_AdministrativoCapacitacion.add(item_AgregarAdministrativo);

        item_AgregarBrigadista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarBrigadista.setText("Agregar Brigadistas");
        PopMenu_BrigadistaCapacitacion.add(item_AgregarBrigadista);

        setClosable(true);
        setTitle("Agregrar asistentes al curso");

        txt_buscar_Tbr.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_RefreshTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jTable_Trabajadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollPane.setViewportView(jTable_Trabajadores);

        jLabel11.setText("Área:");

        cb_area.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_areaItemStateChanged(evt);
            }
        });

        jLabel12.setText("Puesto:");

        jLabel10.setText("Turno:");

        btn_Consultar.setText("Consultar");

        label_buscar.setText("Buscar Trabajador:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label_buscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_buscar_Tbr, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(cb_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(cb_turno, 0, 1, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Consultar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_buscar)
                            .addComponent(txt_buscar_Tbr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Trabajadores", jPanel1);

        label_buscar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label_buscar1.setText("Buscar Supervisor:");

        txt_buscar_Tbr1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_RefreshTabla1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jTable_Trabajadores1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollPane1.setViewportView(jTable_Trabajadores1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(label_buscar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_Tbr1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_buscar_Tbr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_buscar1))
                    .addComponent(btn_RefreshTabla1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Supervisores", jPanel2);

        label_buscar2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label_buscar2.setText("Buscar Administrativo:");

        txt_buscar_Tbr2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_RefreshTabla2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jTable_Administrativos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollPane2.setViewportView(jTable_Administrativos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(label_buscar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_Tbr2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_buscar_Tbr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_buscar2))
                    .addComponent(btn_RefreshTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Administrativos", jPanel4);

        label_buscar4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        label_buscar4.setText("Buscar Brigadista:");

        txt_buscar_Tbr4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_RefreshTabla4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jTable_Administrativos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        ScrollPane4.setViewportView(jTable_Administrativos1);

        jLabel4.setText("Brigadas:");

        jLabel5.setText("Turno:");

        jButton1.setText("Consultar");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla4))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(label_buscar4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_buscar_Tbr4, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 156, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_RefreshTabla4)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_buscar4)
                    .addComponent(txt_buscar_Tbr4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Brigadas", jPanel5);

        jLabel1.setText("Nombre Completo (Apellido Paterno, Apellido Materno, Nombre):");

        jLabel2.setText("Puesto o Rol en la compañia:");

        btn_agregar_otros.setText("Agregar");

        btn_limpiar.setText("Limpiar");

        jLabel3.setText("Número de Nomina (Si es que cuenta con uno)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_puesto)
                    .addComponent(txt_nombre)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_limpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_agregar_otros, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 266, Short.MAX_VALUE))
                    .addComponent(txt_id))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(3, 3, 3)
                .addComponent(txt_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_agregar_otros)
                    .addComponent(btn_limpiar))
                .addContainerGap(167, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Otros", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cb_areaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_areaItemStateChanged
       if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (cb_area.getSelectedIndex() > 0) {
                cb_puesto.removeAllItems();
                cb_puesto.addItem("Todos...");
                String idx = QueryFunctions.CapturaCondicionalSimple("area", "idArea",
                        "Nombre_Area", cb_area.getSelectedItem().toString());
                QueryFunctions.LlenarComboBoxAnidado("puesto", "area", "area_idArea", "idArea",
                        idx, "Nombre_Puesto", cb_puesto);
            }
        } 
    }//GEN-LAST:event_cb_areaItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPopupMenu PopMenu_AdministrativoCapacitacion;
    public javax.swing.JPopupMenu PopMenu_BrigadistaCapacitacion;
    public javax.swing.JPopupMenu PopMenu_SupervisorCapacitacion;
    public javax.swing.JPopupMenu PopMenu_TrabajadorCapacitacion;
    public javax.swing.JScrollPane ScrollPane;
    public javax.swing.JScrollPane ScrollPane1;
    public javax.swing.JScrollPane ScrollPane2;
    public javax.swing.JScrollPane ScrollPane4;
    public javax.swing.JButton btn_Consultar;
    public javax.swing.JButton btn_RefreshTabla;
    public javax.swing.JButton btn_RefreshTabla1;
    public javax.swing.JButton btn_RefreshTabla2;
    public javax.swing.JButton btn_RefreshTabla4;
    public javax.swing.JButton btn_agregar_otros;
    public javax.swing.JButton btn_limpiar;
    public javax.swing.JComboBox<String> cb_area;
    public javax.swing.JComboBox<String> cb_puesto;
    public javax.swing.JComboBox<String> cb_turno;
    public javax.swing.JMenuItem item_AgregarAdministrativo;
    public javax.swing.JMenuItem item_AgregarBrigadista;
    public javax.swing.JMenuItem item_AgregarTrabajadorCapacitacion;
    public javax.swing.JMenuItem item_AgregarTrabajadorCapacitacion1;
    public javax.swing.JButton jButton1;
    public javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable_Administrativos;
    public javax.swing.JTable jTable_Administrativos1;
    public javax.swing.JTable jTable_Trabajadores;
    public javax.swing.JTable jTable_Trabajadores1;
    private javax.swing.JLabel label_buscar;
    private javax.swing.JLabel label_buscar1;
    private javax.swing.JLabel label_buscar2;
    private javax.swing.JLabel label_buscar4;
    public javax.swing.JTextField txt_buscar_Tbr;
    public javax.swing.JTextField txt_buscar_Tbr1;
    public javax.swing.JTextField txt_buscar_Tbr2;
    public javax.swing.JTextField txt_buscar_Tbr4;
    public javax.swing.JTextField txt_id;
    public javax.swing.JTextField txt_nombre;
    public javax.swing.JTextField txt_puesto;
    // End of variables declaration//GEN-END:variables
}
