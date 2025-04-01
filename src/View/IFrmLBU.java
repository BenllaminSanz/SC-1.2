package View;

import Functions.QueryFunctions;
import java.awt.event.ItemEvent;

public class IFrmLBU extends javax.swing.JInternalFrame {

    public static String x;

    public IFrmLBU() {
        initComponents();
        x = "x";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        View_Entregable = new javax.swing.JTabbedPane();
        View_Resúmen = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        View_LBU = new javax.swing.JPanel();
        TbLBU = new javax.swing.JPanel();
        cb_area = new javax.swing.JComboBox<>();
        cb_puesto = new javax.swing.JComboBox<>();
        cb_turno = new javax.swing.JComboBox<>();
        btn_Consultar = new javax.swing.JButton();
        btn_RefreshTabla = new javax.swing.JButton();
        btn_Imprimir = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_LBU = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Panel_Resumen_LBU = new javax.swing.JPanel();
        lbName = new javax.swing.JLabel();
        lbPropuesto = new javax.swing.JLabel();
        lbDiferencia = new javax.swing.JLabel();
        lbPlantilla = new javax.swing.JLabel();
        lbA = new javax.swing.JLabel();
        lbB = new javax.swing.JLabel();
        lbC = new javax.swing.JLabel();
        lbD = new javax.swing.JLabel();
        lbLV = new javax.swing.JLabel();
        Bar_LBU = new javax.swing.JMenuBar();
        itemReporte = new javax.swing.JMenu();
        itemLBU = new javax.swing.JMenu();
        itemRptLBU_PDF = new javax.swing.JMenuItem();
        itemRptLBU_Excel = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        itemRptSup_PDF = new javax.swing.JMenuItem();
        itemRptSup_Excel = new javax.swing.JMenuItem();
        itemRptArea_Excel = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
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

        jPanel2.setBackground(new java.awt.Color(36, 109, 45));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Supervisor");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Propuesto");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Diferencia");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Plantilla");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Turno B");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Turno A");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Turno C");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Turno D");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Turno LV");

        jProgressBar.setForeground(new java.awt.Color(0, 51, 255));
        jProgressBar.setPreferredSize(new java.awt.Dimension(146, 10));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setBackground(new java.awt.Color(193, 193, 193));
        jScrollPane1.setBorder(null);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout View_ResúmenLayout = new javax.swing.GroupLayout(View_Resúmen);
        View_Resúmen.setLayout(View_ResúmenLayout);
        View_ResúmenLayout.setHorizontalGroup(
            View_ResúmenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        View_ResúmenLayout.setVerticalGroup(
            View_ResúmenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(View_ResúmenLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
        );

        View_Entregable.addTab("LBU Operativo", View_Resúmen);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        View_Entregable.addTab("Dashboard LBU", jPanel4);

        cb_area.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_areaItemStateChanged(evt);
            }
        });

        btn_Consultar.setText("Consultar");

        btn_RefreshTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        btn_Imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPrint.png"))); // NOI18N

        jLabel10.setText("Turno:");

        jLabel11.setText("Área:");

        jLabel12.setText("Puesto:");

        javax.swing.GroupLayout TbLBULayout = new javax.swing.GroupLayout(TbLBU);
        TbLBU.setLayout(TbLBULayout);
        TbLBULayout.setHorizontalGroup(
            TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TbLBULayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(5, 5, 5)
                .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(TbLBULayout.createSequentialGroup()
                        .addComponent(cb_turno, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addComponent(btn_Imprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        TbLBULayout.setVerticalGroup(
            TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TbLBULayout.createSequentialGroup()
                .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TbLBULayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10)))
                    .addGroup(TbLBULayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(TbLBULayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(TbLBULayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_Imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(8, 8, 8))
        );

        jTable_LBU.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable_LBU);

        javax.swing.GroupLayout View_LBULayout = new javax.swing.GroupLayout(View_LBU);
        View_LBU.setLayout(View_LBULayout);
        View_LBULayout.setHorizontalGroup(
            View_LBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, View_LBULayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(View_LBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(TbLBU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        View_LBULayout.setVerticalGroup(
            View_LBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(View_LBULayout.createSequentialGroup()
                .addComponent(TbLBU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
        );

        View_Entregable.addTab("Concentrado LBU", View_LBU);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );

        View_Entregable.addTab("Supervisores", jPanel3);

        Panel_Resumen_LBU.setBackground(new java.awt.Color(36, 109, 45));
        Panel_Resumen_LBU.setForeground(new java.awt.Color(255, 255, 255));
        Panel_Resumen_LBU.setPreferredSize(new java.awt.Dimension(288, 50));

        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbName.setText("Totales");

        lbPropuesto.setForeground(new java.awt.Color(255, 255, 255));
        lbPropuesto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPropuesto.setText("Num...");

        lbDiferencia.setForeground(new java.awt.Color(255, 255, 255));
        lbDiferencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDiferencia.setText("Num...");

        lbPlantilla.setForeground(new java.awt.Color(255, 255, 255));
        lbPlantilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPlantilla.setText("Num..");

        lbA.setForeground(new java.awt.Color(255, 255, 255));
        lbA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbA.setText("Num..");

        lbB.setForeground(new java.awt.Color(255, 255, 255));
        lbB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbB.setText("Num...");

        lbC.setForeground(new java.awt.Color(255, 255, 255));
        lbC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbC.setText("Num...");

        lbD.setForeground(new java.awt.Color(255, 255, 255));
        lbD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbD.setText("Num...");

        lbLV.setForeground(new java.awt.Color(255, 255, 255));
        lbLV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbLV.setText("Num...");

        javax.swing.GroupLayout Panel_Resumen_LBULayout = new javax.swing.GroupLayout(Panel_Resumen_LBU);
        Panel_Resumen_LBU.setLayout(Panel_Resumen_LBULayout);
        Panel_Resumen_LBULayout.setHorizontalGroup(
            Panel_Resumen_LBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_Resumen_LBULayout.createSequentialGroup()
                .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbPropuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbDiferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbPlantilla, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbA, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbC, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbD, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbLV, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        Panel_Resumen_LBULayout.setVerticalGroup(
            Panel_Resumen_LBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_Resumen_LBULayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(Panel_Resumen_LBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbPropuesto)
                    .addComponent(lbDiferencia)
                    .addComponent(lbPlantilla)
                    .addComponent(lbA)
                    .addComponent(lbB)
                    .addComponent(lbC)
                    .addComponent(lbD)
                    .addComponent(lbLV)
                    .addComponent(lbName))
                .addGap(12, 12, 12))
        );

        itemReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        itemReporte.setText("Reporte");

        itemLBU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCottonx16.png"))); // NOI18N
        itemLBU.setText("LBU Operativo");

        itemRptLBU_PDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        itemRptLBU_PDF.setText("Via PDF");
        itemLBU.add(itemRptLBU_PDF);

        itemRptLBU_Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        itemRptLBU_Excel.setText("Via Excel");
        itemLBU.add(itemRptLBU_Excel);

        itemReporte.add(itemLBU);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCottonx16.png"))); // NOI18N
        jMenu1.setText("Resúmen LBU");

        itemRptSup_PDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        itemRptSup_PDF.setText("Via PDF");
        jMenu1.add(itemRptSup_PDF);

        itemRptSup_Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        itemRptSup_Excel.setText("Via Excel");
        jMenu1.add(itemRptSup_Excel);

        itemRptArea_Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        itemRptArea_Excel.setText("Via Excel (Por Áreas)");
        jMenu1.add(itemRptArea_Excel);

        itemReporte.add(jMenu1);

        Bar_LBU.add(itemReporte);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        jMenu2.setText("Listas de Emergencia");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        jMenuItem1.setText("De toda la de Planta");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        jMenuItem2.setText("Especifica");
        jMenu2.add(jMenuItem2);

        Bar_LBU.add(jMenu2);

        setJMenuBar(Bar_LBU);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(View_Entregable)
            .addComponent(Panel_Resumen_LBU, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(View_Entregable)
                .addGap(0, 0, 0)
                .addComponent(Panel_Resumen_LBU, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        x = null;
    }//GEN-LAST:event_formInternalFrameClosing

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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Bar_LBU;
    private javax.swing.JPanel Panel_Resumen_LBU;
    public javax.swing.JPanel TbLBU;
    public javax.swing.JTabbedPane View_Entregable;
    public javax.swing.JPanel View_LBU;
    public javax.swing.JPanel View_Resúmen;
    public javax.swing.JButton btn_Consultar;
    public javax.swing.JButton btn_Imprimir;
    public javax.swing.JButton btn_RefreshTabla;
    public javax.swing.JComboBox<String> cb_area;
    public javax.swing.JComboBox<String> cb_puesto;
    public javax.swing.JComboBox<String> cb_turno;
    public javax.swing.JMenu itemLBU;
    private javax.swing.JMenu itemReporte;
    public javax.swing.JMenuItem itemRptArea_Excel;
    public javax.swing.JMenuItem itemRptLBU_Excel;
    public javax.swing.JMenuItem itemRptLBU_PDF;
    public javax.swing.JMenuItem itemRptSup_Excel;
    public javax.swing.JMenuItem itemRptSup_PDF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    public javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JProgressBar jProgressBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable_LBU;
    public javax.swing.JLabel lbA;
    public javax.swing.JLabel lbB;
    public javax.swing.JLabel lbC;
    public javax.swing.JLabel lbD;
    public javax.swing.JLabel lbDiferencia;
    public javax.swing.JLabel lbLV;
    public javax.swing.JLabel lbName;
    public javax.swing.JLabel lbPlantilla;
    public javax.swing.JLabel lbPropuesto;
    // End of variables declaration//GEN-END:variables
}
