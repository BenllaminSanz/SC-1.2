package View;

import Functions.QueryFunctions;
import java.awt.event.ItemEvent;

public class IFrmCapacitacion extends javax.swing.JInternalFrame {

    public static String x;
    
    public IFrmCapacitacion() {
        initComponents();
        x = "x";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopMenu_Curso = new javax.swing.JPopupMenu();
        AgregarRegistro = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        ModificarCurso = new javax.swing.JMenuItem();
        AgregarCurso = new javax.swing.JMenuItem();
        EliminarCurso = new javax.swing.JMenuItem();
        PopMenu_HistorialCurso = new javax.swing.JPopupMenu();
        item_AgregarAsistentes = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        item_ConcluirCurso = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        item_AgregarHistorialCurso = new javax.swing.JMenuItem();
        item_ModificarHistorialCurso = new javax.swing.JMenuItem();
        item_EliminarHistorialCurso = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        item_ModificarCurso = new javax.swing.JMenuItem();
        PopMenu_AsistenteCurso = new javax.swing.JPopupMenu();
        item_RequerimientosAsistente = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        item_AgregarAsistente = new javax.swing.JMenuItem();
        item_EliminarAsistente = new javax.swing.JMenuItem();
        PopMenu_RequerimientoCurso = new javax.swing.JPopupMenu();
        item_AgregarRequerimiento = new javax.swing.JMenuItem();
        item_ModificarRequerimiento = new javax.swing.JMenuItem();
        item_EliminarRequerimiento = new javax.swing.JMenuItem();
        PopMenu_Certificado = new javax.swing.JPopupMenu();
        Item_AgregarCertificado = new javax.swing.JMenuItem();
        Item_ModificarCertificado = new javax.swing.JMenuItem();
        Item_EliminarCertificado = new javax.swing.JMenuItem();
        PopMenu_Certificados = new javax.swing.JPopupMenu();
        Item_AgregarCertificados = new javax.swing.JMenuItem();
        item_ModificarCertificados = new javax.swing.JMenuItem();
        Item_EliminarCertificados = new javax.swing.JMenuItem();
        PopMenu_Cursos = new javax.swing.JPopupMenu();
        item_ModificarCursoPersonal = new javax.swing.JMenuItem();
        PanelsCapacitaciones = new javax.swing.JTabbedPane();
        PanelCursos = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        Tabla_ListadoCursos1 = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        Tabla_ListadoCursos2 = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTable_HistorialCurso = new javax.swing.JTable();
        btn_agregarCursoH = new javax.swing.JButton();
        txt_curso_titulo = new javax.swing.JLabel();
        btn_act_hc = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        btn_filtrarhistorial = new javax.swing.JButton();
        txt_total = new javax.swing.JLabel();
        txt_total1 = new javax.swing.JLabel();
        txt_total2 = new javax.swing.JLabel();
        txt_total3 = new javax.swing.JLabel();
        txt_total4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_Asistentes_Curso = new javax.swing.JTable();
        btn_AgrTrabajador = new javax.swing.JButton();
        txt_esperado = new javax.swing.JLabel();
        txt_asistencia = new javax.swing.JLabel();
        txt_asistencia1 = new javax.swing.JLabel();
        txt_esperado1 = new javax.swing.JLabel();
        btn_listaAsistentes = new javax.swing.JButton();
        txt_instructor = new javax.swing.JLabel();
        txt_instructores = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTree_Cursos = new javax.swing.JTree();
        btn_agregarCurso = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cb_area1 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cb_puesto1 = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cb_turno1 = new javax.swing.JComboBox<>();
        btn_Consultar1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cb_procesos = new javax.swing.JComboBox<>();
        bt_consulta2 = new javax.swing.JButton();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        consulta_mensual = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        grafica2 = new javax.swing.JPanel();
        grafica_certificados = new javax.swing.JPanel();
        txt_totalCertificados = new javax.swing.JLabel();
        txt_totalFlexibilidad = new javax.swing.JLabel();
        txt_TotalPrimero = new javax.swing.JLabel();
        txt_totalSegundo = new javax.swing.JLabel();
        txt_totalAnual = new javax.swing.JLabel();
        txt_totalBaja = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        grafica = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Listado = new javax.swing.JTable();
        txt_Entrenamiento = new javax.swing.JLabel();
        txt_entrenamientoPrimero = new javax.swing.JLabel();
        txt_entrenamientoSegundo = new javax.swing.JLabel();
        txt_entrenamientoCruzado = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        TbLBU = new javax.swing.JPanel();
        cb_area = new javax.swing.JComboBox<>();
        cb_puesto = new javax.swing.JComboBox<>();
        cb_turno = new javax.swing.JComboBox<>();
        btn_Consultar = new javax.swing.JButton();
        btn_RefreshTabla = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_entrenamientos = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_certificadosTrabajador = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_cursosTrabajador = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtable_certificados = new javax.swing.JTable();
        btn_agregarCertificado = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        btn_act_ct = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_AsistentesCertificados = new javax.swing.JTable();
        btn_agregar_personalcertificado = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        AgregarRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        AgregarRegistro.setText("Agregar registro al Curso");
        PopMenu_Curso.add(AgregarRegistro);
        PopMenu_Curso.add(jSeparator3);

        ModificarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        ModificarCurso.setText("Modificar Curso seleccionado");
        PopMenu_Curso.add(ModificarCurso);

        AgregarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        AgregarCurso.setText("Agregar Nuevo Curso");
        PopMenu_Curso.add(AgregarCurso);

        EliminarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        EliminarCurso.setText("Eliminar Curso seleccionado");
        PopMenu_Curso.add(EliminarCurso);

        item_AgregarAsistentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconNewWorkerx16.png"))); // NOI18N
        item_AgregarAsistentes.setText("Agregar Asistente(s)");
        item_AgregarAsistentes.setToolTipText("");
        PopMenu_HistorialCurso.add(item_AgregarAsistentes);
        PopMenu_HistorialCurso.add(jSeparator5);

        item_ConcluirCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconVotex16.png"))); // NOI18N
        item_ConcluirCurso.setText("Concluir/Restaurar Curso");
        PopMenu_HistorialCurso.add(item_ConcluirCurso);
        PopMenu_HistorialCurso.add(jSeparator1);

        item_AgregarHistorialCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarHistorialCurso.setText("Agregar Historial de un Curso");
        PopMenu_HistorialCurso.add(item_AgregarHistorialCurso);

        item_ModificarHistorialCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarHistorialCurso.setText("Modificar Historial del Curso");
        PopMenu_HistorialCurso.add(item_ModificarHistorialCurso);

        item_EliminarHistorialCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        item_EliminarHistorialCurso.setText("Eliminar Historial del Curso");
        PopMenu_HistorialCurso.add(item_EliminarHistorialCurso);
        PopMenu_HistorialCurso.add(jSeparator4);

        item_ModificarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarCurso.setText("Modificar Curso");
        PopMenu_HistorialCurso.add(item_ModificarCurso);

        item_RequerimientosAsistente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        item_RequerimientosAsistente.setText("Consultar Avance");
        PopMenu_AsistenteCurso.add(item_RequerimientosAsistente);
        PopMenu_AsistenteCurso.add(jSeparator2);

        item_AgregarAsistente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarAsistente.setText("Agregar Asistente");
        PopMenu_AsistenteCurso.add(item_AgregarAsistente);

        item_EliminarAsistente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        item_EliminarAsistente.setText("Eliminar Asistente");
        PopMenu_AsistenteCurso.add(item_EliminarAsistente);

        item_AgregarRequerimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarRequerimiento.setText("Agregar Requerimiento");
        PopMenu_RequerimientoCurso.add(item_AgregarRequerimiento);

        item_ModificarRequerimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarRequerimiento.setText("Modificar Requerimiento");
        PopMenu_RequerimientoCurso.add(item_ModificarRequerimiento);

        item_EliminarRequerimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        item_EliminarRequerimiento.setText("Eliminar Requerimiento");
        item_EliminarRequerimiento.setToolTipText("");
        PopMenu_RequerimientoCurso.add(item_EliminarRequerimiento);

        Item_AgregarCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        Item_AgregarCertificado.setText("Agregar Certificado");
        PopMenu_Certificado.add(Item_AgregarCertificado);

        Item_ModificarCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        Item_ModificarCertificado.setText("Modificar Certificado");
        PopMenu_Certificado.add(Item_ModificarCertificado);

        Item_EliminarCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconClosex16.png"))); // NOI18N
        Item_EliminarCertificado.setText("Eliminar Certificado");
        PopMenu_Certificado.add(Item_EliminarCertificado);

        Item_AgregarCertificados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        Item_AgregarCertificados.setText("Agregar Personal Certificado");
        PopMenu_Certificados.add(Item_AgregarCertificados);

        item_ModificarCertificados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarCertificados.setText("Modificar Certificado");
        PopMenu_Certificados.add(item_ModificarCertificados);

        Item_EliminarCertificados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconClosex16.png"))); // NOI18N
        Item_EliminarCertificados.setText("Eliminar Certificación");
        PopMenu_Certificados.add(Item_EliminarCertificados);

        item_ModificarCursoPersonal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        item_ModificarCursoPersonal.setText("Modificar Registro");
        PopMenu_Cursos.add(item_ModificarCursoPersonal);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Capacitaciones y Certificaciones");
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

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Trabajadores en entrenamiento:"));
        jPanel20.setPreferredSize(new java.awt.Dimension(280, 518));

        jScrollPane10.setPreferredSize(new java.awt.Dimension(250, 402));

        Tabla_ListadoCursos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane10.setViewportView(Tabla_ListadoCursos1);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Cursos Activos:"));
        jPanel21.setPreferredSize(new java.awt.Dimension(280, 518));

        jScrollPane11.setPreferredSize(new java.awt.Dimension(250, 402));

        Tabla_ListadoCursos2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane11.setViewportView(Tabla_ListadoCursos2);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        jPanel24.setPreferredSize(new java.awt.Dimension(350, 200));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel23.setPreferredSize(new java.awt.Dimension(350, 200));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
                            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jTabbedPane2.addTab("Concentrado", jPanel19);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Historial del Curso"));

        JTable_HistorialCurso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(JTable_HistorialCurso);

        btn_agregarCursoH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_agregarCursoH.setText("Agregar Registro");

        txt_curso_titulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_curso_titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btn_act_hc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jDateChooser1.setDateFormatString("dd/MM/yyyy");

        jLabel3.setText("Filtrar por Fecha:");

        btn_filtrarhistorial.setText("Filtrar");

        txt_total.setText("No. Cursos:");

        txt_total1.setText("Total de Asistentes:");

        txt_total2.setText("Texto");

        txt_total3.setText("Total de Horas del Curso:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_agregarCursoH))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_curso_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_act_hc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_filtrarhistorial))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_total)
                                    .addComponent(txt_total1))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_total3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_total2)
                                        .addGap(152, 152, 152)
                                        .addComponent(txt_total4)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_curso_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_act_hc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_total2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total)
                    .addComponent(txt_total4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_total3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_filtrarhistorial, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_agregarCursoH))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Asistentes:"));
        jPanel2.setMaximumSize(new java.awt.Dimension(600, 32767));

        jTable_Asistentes_Curso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTable_Asistentes_Curso);

        btn_AgrTrabajador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_AgrTrabajador.setText("Agregar Asistente");

        txt_esperado.setText("Asistencia Esperada:");

        txt_asistencia.setText("Asistencia Actual:");

        txt_asistencia1.setText("Horas Hombre:");

        txt_esperado1.setText("Tiempo total por persona:");

        btn_listaAsistentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        btn_listaAsistentes.setText("Enlistar Asistentes");

        txt_instructor.setText("Instructor(es):");
        txt_instructor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconAreax16.png"))); // NOI18N
        jButton4.setText("Generar Expedientes");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        jButton5.setText("Reporte de Avance");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_instructor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_instructores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_asistencia)
                            .addComponent(txt_esperado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_asistencia1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_esperado1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_listaAsistentes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_AgrTrabajador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_asistencia)
                    .addComponent(txt_esperado1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_esperado)
                    .addComponent(txt_asistencia1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_instructor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_instructores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_listaAsistentes, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_AgrTrabajador))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)))
        );

        jPanel3.setMaximumSize(new java.awt.Dimension(309, 32767));
        jPanel3.setMinimumSize(new java.awt.Dimension(309, 0));

        jScrollPane9.setBorder(null);

        jTree_Cursos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTree_Cursos.setName("Cursos"); // NOI18N
        jScrollPane9.setViewportView(jTree_Cursos);

        btn_agregarCurso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_agregarCurso.setText("Agregar Curso");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_agregarCurso))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_agregarCurso)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Cursos", jPanel4);

        javax.swing.GroupLayout PanelCursosLayout = new javax.swing.GroupLayout(PanelCursos);
        PanelCursos.setLayout(PanelCursosLayout);
        PanelCursosLayout.setHorizontalGroup(
            PanelCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        PanelCursosLayout.setVerticalGroup(
            PanelCursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        PanelsCapacitaciones.addTab("Cursos de Capacitación", new javax.swing.ImageIcon(getClass().getResource("/Images/IconCursox16.png")), PanelCursos); // NOI18N

        jPanel12.setMaximumSize(new java.awt.Dimension(32767, 60));
        jPanel12.setPreferredSize(new java.awt.Dimension(300, 518));

        jLabel13.setText("Área:");

        cb_area1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_area1ItemStateChanged(evt);
            }
        });

        jLabel14.setText("Puesto:");

        jLabel15.setText("Turno:");

        btn_Consultar1.setText("Consultar");

        jLabel8.setText("Proceso:");

        cb_procesos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los procesos...", "Procesos Operativos", "Procesos Técnicos" }));

        bt_consulta2.setText("Consultar");

        consulta_mensual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jLabel17.setText("Consulta:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(cb_procesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bt_consulta2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(cb_area1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb_puesto1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(cb_turno1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Consultar1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 353, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(consulta_mensual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel8)))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bt_consulta2)
                                .addComponent(cb_area1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cb_puesto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cb_procesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cb_turno1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_Consultar1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(consulta_mensual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setMaximumSize(new java.awt.Dimension(500, 32767));

        grafica2.setMaximumSize(new java.awt.Dimension(400, 32767));

        javax.swing.GroupLayout grafica2Layout = new javax.swing.GroupLayout(grafica2);
        grafica2.setLayout(grafica2Layout);
        grafica2Layout.setHorizontalGroup(
            grafica2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        grafica2Layout.setVerticalGroup(
            grafica2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 193, Short.MAX_VALUE)
        );

        grafica_certificados.setMaximumSize(new java.awt.Dimension(400, 32767));

        javax.swing.GroupLayout grafica_certificadosLayout = new javax.swing.GroupLayout(grafica_certificados);
        grafica_certificados.setLayout(grafica_certificadosLayout);
        grafica_certificadosLayout.setHorizontalGroup(
            grafica_certificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        grafica_certificadosLayout.setVerticalGroup(
            grafica_certificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        txt_totalCertificados.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_totalCertificados.setText("Certificados en Planta:");

        txt_totalFlexibilidad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_totalFlexibilidad.setText("Flexibilidad Planta:");

        txt_TotalPrimero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_TotalPrimero.setText("Certificaciones de Primer Puesto:");

        txt_totalSegundo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_totalSegundo.setText("Certificaciones de Segundo Puesto:");

        txt_totalAnual.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_totalAnual.setText("Certificaciones en el Año:");

        txt_totalBaja.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_totalBaja.setText("Certificaciones de Baja:");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(txt_totalCertificados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_totalFlexibilidad)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(grafica2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(grafica_certificados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(txt_totalAnual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_TotalPrimero))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(txt_totalBaja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_totalSegundo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalCertificados)
                    .addComponent(txt_totalFlexibilidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grafica2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grafica_certificados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalAnual)
                    .addComponent(txt_TotalPrimero))
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_totalSegundo)
                    .addComponent(txt_totalBaja))
                .addGap(8, 8, 8))
        );

        grafica.setPreferredSize(new java.awt.Dimension(480, 0));

        javax.swing.GroupLayout graficaLayout = new javax.swing.GroupLayout(grafica);
        grafica.setLayout(graficaLayout);
        graficaLayout.setHorizontalGroup(
            graficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graficaLayout.setVerticalGroup(
            graficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Trabajadores por status de capacitación:"));
        jPanel11.setPreferredSize(new java.awt.Dimension(280, 518));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 402));

        Tabla_Listado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabla_Listado);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        );

        txt_Entrenamiento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_Entrenamiento.setText("En Entrenamiento:");

        txt_entrenamientoPrimero.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_entrenamientoPrimero.setText("En Primer Puesto:");

        txt_entrenamientoSegundo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_entrenamientoSegundo.setText("En Segundo Puesto:");

        txt_entrenamientoCruzado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_entrenamientoCruzado.setText("En Entrenamiento dif. al LBU:");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(grafica, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(txt_Entrenamiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_entrenamientoPrimero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_entrenamientoSegundo))
                    .addComponent(txt_entrenamientoCruzado))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Entrenamiento)
                    .addComponent(txt_entrenamientoPrimero)
                    .addComponent(txt_entrenamientoSegundo))
                .addGap(0, 0, 0)
                .addComponent(txt_entrenamientoCruzado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(grafica, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 1290, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Concentrado", jPanel8);

        cb_area.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_areaItemStateChanged(evt);
            }
        });

        btn_Consultar.setText("Consultar");

        btn_RefreshTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        jLabel10.setText("Turno:");

        jLabel11.setText("Área:");

        jLabel12.setText("Puesto:");

        javax.swing.GroupLayout TbLBULayout = new javax.swing.GroupLayout(TbLBU);
        TbLBU.setLayout(TbLBULayout);
        TbLBULayout.setHorizontalGroup(
            TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TbLBULayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_turno, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        TbLBULayout.setVerticalGroup(
            TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TbLBULayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addGroup(TbLBULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cb_puesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(cb_turno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Trabajadores en Planta:"));

        jTable_entrenamientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTable_entrenamientos);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Certificados del Trabajador:"));

        jTable_certificadosTrabajador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(jTable_certificadosTrabajador);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Cursos asistidos por el Trabajador:"));

        jTable_cursosTrabajador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(jTable_cursosTrabajador);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TbLBU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(TbLBU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Por Trabajador", jPanel6);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Certificados:"));

        jtable_certificados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jtable_certificados);

        btn_agregarCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_agregarCertificado.setText("Agregar Certificado");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_agregarCertificado))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_agregarCertificado))
        );

        btn_act_ct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(941, Short.MAX_VALUE)
                .addComponent(btn_act_ct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_act_ct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Personal Certificado:"));

        jTable_AsistentesCertificados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(jTable_AsistentesCertificados);

        btn_agregar_personalcertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        btn_agregar_personalcertificado.setText("Agregar Personal Certificado");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_agregar_personalcertificado))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_agregar_personalcertificado))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Por Certificado", jPanel7);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );

        PanelsCapacitaciones.addTab("Certificados", new javax.swing.ImageIcon(getClass().getResource("/Images/IconCertifycatex16.png")), jPanel5); // NOI18N

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconList.png"))); // NOI18N
        jMenu1.setText("Reportes");

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEntrenamientox16.png"))); // NOI18N
        jMenu2.setText("Entrenamiento");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem1.setText("Reporte General PDF");
        jMenu2.add(jMenuItem1);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem13.setText("Reporte General con Salarios PDF");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem2.setText("Reporte Especifico PDF");
        jMenu2.add(jMenuItem2);

        jMenu1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCertifycatex16.png"))); // NOI18N
        jMenu3.setText("Centificaciones");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem3.setText("Reporte General PDF");
        jMenu3.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem4.setText("Reporte Supervisores PDF");
        jMenu3.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem5.setText("Reporte Especifico PDF");
        jMenu3.add(jMenuItem5);

        jMenu1.add(jMenu3);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRowx16.png"))); // NOI18N
        jMenu6.setText("Flexibilidad");

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem11.setText("Via PDF");
        jMenu6.add(jMenuItem11);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem10.setText("Via PDF Especifico");
        jMenu6.add(jMenuItem10);

        jMenu1.add(jMenu6);

        jMenuBar1.add(jMenu1);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconCertifycatex16.png"))); // NOI18N
        jMenu4.setText("Diplomas");

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        jMenuItem6.setText("Generar Formato de Diplomas");
        jMenu4.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconExcel.png"))); // NOI18N
        jMenuItem7.setText("Generar Formato Anual");
        jMenu4.add(jMenuItem7);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPDF.png"))); // NOI18N
        jMenuItem12.setText("Generar Diplomas PDF");
        jMenu4.add(jMenuItem12);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconDataBasex16.png"))); // NOI18N
        jMenu5.setText("Bases de Datos");

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconDataBasex16.png"))); // NOI18N
        jMenuItem8.setText("Cursos (xlsx)");
        jMenu5.add(jMenuItem8);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconDataBasex16.png"))); // NOI18N
        jMenuItem9.setText("Certificados (xlsx)");
        jMenu5.add(jMenuItem9);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelsCapacitaciones)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelsCapacitaciones)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        x = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void cb_area1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_area1ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (cb_area1.getSelectedIndex() > 0) {
                cb_puesto1.removeAllItems();
                cb_puesto1.addItem("Todos...");
                String idx = QueryFunctions.CapturaCondicionalSimple("area", "idArea",
                    "Nombre_Area", cb_area1.getSelectedItem().toString());
                QueryFunctions.LlenarComboBoxAnidado("puesto", "area", "area_idArea", "idArea",
                    idx, "Nombre_Puesto", cb_puesto1);
            }
        }
    }//GEN-LAST:event_cb_area1ItemStateChanged

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

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem AgregarCurso;
    public javax.swing.JMenuItem AgregarRegistro;
    public javax.swing.JMenuItem EliminarCurso;
    public javax.swing.JMenuItem Item_AgregarCertificado;
    public javax.swing.JMenuItem Item_AgregarCertificados;
    public javax.swing.JMenuItem Item_EliminarCertificado;
    public javax.swing.JMenuItem Item_EliminarCertificados;
    public javax.swing.JMenuItem Item_ModificarCertificado;
    public javax.swing.JTable JTable_HistorialCurso;
    public javax.swing.JMenuItem ModificarCurso;
    private javax.swing.JPanel PanelCursos;
    javax.swing.JTabbedPane PanelsCapacitaciones;
    public javax.swing.JPopupMenu PopMenu_AsistenteCurso;
    public javax.swing.JPopupMenu PopMenu_Certificado;
    public javax.swing.JPopupMenu PopMenu_Certificados;
    public javax.swing.JPopupMenu PopMenu_Curso;
    public javax.swing.JPopupMenu PopMenu_Cursos;
    public javax.swing.JPopupMenu PopMenu_HistorialCurso;
    public javax.swing.JPopupMenu PopMenu_RequerimientoCurso;
    public javax.swing.JTable Tabla_Listado;
    public javax.swing.JTable Tabla_ListadoCursos1;
    public javax.swing.JTable Tabla_ListadoCursos2;
    public javax.swing.JPanel TbLBU;
    public javax.swing.JButton bt_consulta2;
    public javax.swing.JButton btn_AgrTrabajador;
    public javax.swing.JButton btn_Consultar;
    public javax.swing.JButton btn_Consultar1;
    public javax.swing.JButton btn_RefreshTabla;
    public javax.swing.JButton btn_act_ct;
    public javax.swing.JButton btn_act_hc;
    public javax.swing.JButton btn_agregarCertificado;
    public javax.swing.JButton btn_agregarCurso;
    public javax.swing.JButton btn_agregarCursoH;
    public javax.swing.JButton btn_agregar_personalcertificado;
    public javax.swing.JButton btn_filtrarhistorial;
    public javax.swing.JButton btn_listaAsistentes;
    public javax.swing.JComboBox<String> cb_area;
    public javax.swing.JComboBox<String> cb_area1;
    public javax.swing.JComboBox<String> cb_procesos;
    public javax.swing.JComboBox<String> cb_puesto;
    public javax.swing.JComboBox<String> cb_puesto1;
    public javax.swing.JComboBox<String> cb_turno;
    public javax.swing.JComboBox<String> cb_turno1;
    public javax.swing.JButton consulta_mensual;
    public javax.swing.JPanel grafica;
    public javax.swing.JPanel grafica2;
    public javax.swing.JPanel grafica_certificados;
    public javax.swing.JMenuItem item_AgregarAsistente;
    public javax.swing.JMenuItem item_AgregarAsistentes;
    public javax.swing.JMenuItem item_AgregarHistorialCurso;
    public javax.swing.JMenuItem item_AgregarRequerimiento;
    public javax.swing.JMenuItem item_ConcluirCurso;
    public javax.swing.JMenuItem item_EliminarAsistente;
    public javax.swing.JMenuItem item_EliminarHistorialCurso;
    public javax.swing.JMenuItem item_EliminarRequerimiento;
    public javax.swing.JMenuItem item_ModificarCertificados;
    public javax.swing.JMenuItem item_ModificarCurso;
    public javax.swing.JMenuItem item_ModificarCursoPersonal;
    public javax.swing.JMenuItem item_ModificarHistorialCurso;
    public javax.swing.JMenuItem item_ModificarRequerimiento;
    public javax.swing.JMenuItem item_RequerimientosAsistente;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenuItem jMenuItem1;
    public javax.swing.JMenuItem jMenuItem10;
    public javax.swing.JMenuItem jMenuItem11;
    public javax.swing.JMenuItem jMenuItem12;
    public javax.swing.JMenuItem jMenuItem13;
    public javax.swing.JMenuItem jMenuItem2;
    public javax.swing.JMenuItem jMenuItem3;
    public javax.swing.JMenuItem jMenuItem4;
    public javax.swing.JMenuItem jMenuItem5;
    public javax.swing.JMenuItem jMenuItem6;
    public javax.swing.JMenuItem jMenuItem7;
    public javax.swing.JMenuItem jMenuItem8;
    public javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    public javax.swing.JPanel jPanel22;
    public javax.swing.JPanel jPanel23;
    public javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane10;
    public javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    public javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JTable jTable_AsistentesCertificados;
    public javax.swing.JTable jTable_Asistentes_Curso;
    public javax.swing.JTable jTable_certificadosTrabajador;
    public javax.swing.JTable jTable_cursosTrabajador;
    public javax.swing.JTable jTable_entrenamientos;
    public javax.swing.JTree jTree_Cursos;
    public com.toedter.calendar.JYearChooser jYearChooser1;
    public javax.swing.JTable jtable_certificados;
    public javax.swing.JLabel txt_Entrenamiento;
    public javax.swing.JLabel txt_TotalPrimero;
    public javax.swing.JLabel txt_asistencia;
    public javax.swing.JLabel txt_asistencia1;
    public javax.swing.JLabel txt_curso_titulo;
    public javax.swing.JLabel txt_entrenamientoCruzado;
    public javax.swing.JLabel txt_entrenamientoPrimero;
    public javax.swing.JLabel txt_entrenamientoSegundo;
    public javax.swing.JLabel txt_esperado;
    public javax.swing.JLabel txt_esperado1;
    public javax.swing.JLabel txt_instructor;
    public javax.swing.JLabel txt_instructores;
    public javax.swing.JLabel txt_total;
    public javax.swing.JLabel txt_total1;
    public javax.swing.JLabel txt_total2;
    public javax.swing.JLabel txt_total3;
    public javax.swing.JLabel txt_total4;
    public javax.swing.JLabel txt_totalAnual;
    public javax.swing.JLabel txt_totalBaja;
    public javax.swing.JLabel txt_totalCertificados;
    public javax.swing.JLabel txt_totalFlexibilidad;
    public javax.swing.JLabel txt_totalSegundo;
    // End of variables declaration//GEN-END:variables
}
