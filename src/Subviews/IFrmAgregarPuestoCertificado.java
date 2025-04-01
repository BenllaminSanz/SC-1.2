package Subviews;

import java.awt.event.ItemEvent;

public class IFrmAgregarPuestoCertificado extends javax.swing.JInternalFrame {

    public IFrmAgregarPuestoCertificado() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopMenu_TrabajadorCapacitacion = new javax.swing.JPopupMenu();
        item_AgregarTrabajadorCapacitacion = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        btn_RefreshTabla = new javax.swing.JButton();
        ScrollPane = new javax.swing.JScrollPane();
        jTable_Puestos = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        cb_area = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        item_AgregarTrabajadorCapacitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        item_AgregarTrabajadorCapacitacion.setText("Agregar a Capacitación");
        PopMenu_TrabajadorCapacitacion.add(item_AgregarTrabajadorCapacitacion);

        setClosable(true);
        setTitle("Agregrar asistentes al curso");

        btn_RefreshTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconRefresh.png"))); // NOI18N

        ScrollPane.setViewportView(jTable_Puestos);

        jLabel11.setText("Área:");

        cb_area.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_areaItemStateChanged(evt);
            }
        });

        jLabel1.setText("Nombre del Puesto:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                        .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_RefreshTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cb_areaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_areaItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (cb_area.getSelectedIndex() > 0) {
                
            }
        }
    }//GEN-LAST:event_cb_areaItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPopupMenu PopMenu_TrabajadorCapacitacion;
    public javax.swing.JScrollPane ScrollPane;
    public javax.swing.JButton btn_RefreshTabla;
    public javax.swing.JComboBox<String> cb_area;
    public javax.swing.JMenuItem item_AgregarTrabajadorCapacitacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JTable jTable_Puestos;
    public javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
