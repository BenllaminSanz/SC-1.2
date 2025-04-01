/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Subviews;

import Tables.DesignTabla;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SubMenuItem extends javax.swing.JPanel {

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public ArrayList<SubMenuItem> getSubMenu() {
        return subMenu;
    }

    private final ArrayList<SubMenuItem> subMenu = new ArrayList<>();
    private ActionListener act;

    public SubMenuItem(String menuName, String nameIngles, int propuesto, int diferencia, int plantilla,
            int A, int B, int C, int D, int LV, int Sup, int Pt, ActionListener act) {
        initComponents();
        lbName.setText(menuName);
        lbName1.setText(nameIngles);
        setLabel(lbPropuesto, String.valueOf(propuesto));
        setLabel(lbDiferencia, String.valueOf(diferencia));
        setDiferenciaColor(lbDiferencia, diferencia);
        setLabel(lbPlantilla, String.valueOf(plantilla));
        setNegrita(lbA, A);
        setNegrita(lbB, B);
        setNegrita(lbC, C);
        setNegrita(lbD, D);
        setNegrita(lbLV, LV);

        DesignTabla.designLBUTrabajadorArea(this, Sup, Pt);
        if (act != null) {
            this.act = act;
        }
        this.setSize(new Dimension(Integer.MAX_VALUE, 55));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 55));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PopMenu_Trabajador = new javax.swing.JPopupMenu();
        ItemAgregar = new javax.swing.JMenuItem();
        ItemModificar = new javax.swing.JMenuItem();
        ItemEliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        lbName = new javax.swing.JLabel();
        lbPropuesto = new javax.swing.JLabel();
        lbPlantilla = new javax.swing.JLabel();
        lbA = new javax.swing.JLabel();
        lbB = new javax.swing.JLabel();
        lbC = new javax.swing.JLabel();
        lbD = new javax.swing.JLabel();
        lbLV = new javax.swing.JLabel();
        lbDiferencia = new javax.swing.JLabel();
        lbName1 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Trabajadores_Supervisor = new javax.swing.JTable();

        ItemAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconPlus.png"))); // NOI18N
        ItemAgregar.setText("Agregar");
        PopMenu_Trabajador.add(ItemAgregar);

        ItemModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconEdit.png"))); // NOI18N
        ItemModificar.setText("Modificar");
        PopMenu_Trabajador.add(ItemModificar);

        ItemEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/IconMinus.png"))); // NOI18N
        ItemEliminar.setText("Eliminar");
        PopMenu_Trabajador.add(ItemEliminar);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(288, 50));

        lbName.setText("Supervisor...");

        lbPropuesto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPropuesto.setText("Num..");

        lbPlantilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPlantilla.setText("Num..");

        lbA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbA.setText("Num..");

        lbB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbB.setText("Num...");

        lbC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbC.setText("Num...");

        lbD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbD.setText("Num...");

        lbLV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbLV.setText("Num...");

        lbDiferencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDiferencia.setText("Num..");

        lbName1.setText("Supervisor...");

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator10)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbName1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbName1))
                    .addComponent(lbPropuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbDiferencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbPlantilla, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbLV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTable_Trabajadores_Supervisor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_Trabajadores_Supervisor);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public boolean showing = false;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (showing) {
            hideMenu();
            this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        } else {
            this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 
                    jTable_Trabajadores_Supervisor.getHeight() + 95));
            showMenu();
        }
        if (act != null) {
            act.actionPerformed(null);
        }
    }//GEN-LAST:event_formMousePressed

    public void showMenu() {
        new Thread(() -> {
            for (int i = 0; i < subMenu.size(); i++) {
                sleep();
                subMenu.get(i).setVisible(true);
            }
            showing = true;
            getParent().repaint();
            getParent().revalidate();
        }).start();
    }

    public void hideMenu() {
        new Thread(() -> {
            for (int i = subMenu.size() - 1; i >= 0; i--) {
                sleep();
                subMenu.get(i).setVisible(false);
                subMenu.get(i).hideMenu();
            }
            getParent().repaint();
            getParent().revalidate();
            showing = false;
        }).start();
    }

    private void sleep() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
    }

    private void setLabel(JLabel label, String text) {
        label.setText(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setDiferenciaColor(JLabel label, int diferencia) {
        if (diferencia == 0) {
            label.setFont(label.getFont().deriveFont(Font.BOLD));
        } else if (diferencia < 0) {
            label.setFont(label.getFont().deriveFont(Font.BOLD));
            label.setForeground(Color.RED);
        } else {
            label.setFont(label.getFont().deriveFont(Font.BOLD));
            label.setForeground(new Color(0, 153, 0));
        }
    }
    
    private void setNegrita(JLabel label, int turno) {
        if (turno > 0) {
            label.setText(String.valueOf(turno));
            label.setFont(label.getFont().deriveFont(Font.BOLD));
            label.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            setLabel(label, String.valueOf(turno));
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuItem ItemAgregar;
    public javax.swing.JMenuItem ItemEliminar;
    public javax.swing.JMenuItem ItemModificar;
    public javax.swing.JPopupMenu PopMenu_Trabajador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    public javax.swing.JTable jTable_Trabajadores_Supervisor;
    public javax.swing.JLabel lbA;
    public javax.swing.JLabel lbB;
    public javax.swing.JLabel lbC;
    public javax.swing.JLabel lbD;
    public javax.swing.JLabel lbDiferencia;
    public javax.swing.JLabel lbLV;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbName1;
    public javax.swing.JLabel lbPlantilla;
    public javax.swing.JLabel lbPropuesto;
    // End of variables declaration//GEN-END:variables
}
