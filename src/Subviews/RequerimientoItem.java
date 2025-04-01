package Subviews;

import Model.RequerimientosCursoAsistente;
import Querys.ConsultasRequerimientosCurso;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class RequerimientoItem extends javax.swing.JPanel {

    private final RequerimientosCursoAsistente modAc = new RequerimientosCursoAsistente();
    private final ConsultasRequerimientosCurso modC = new ConsultasRequerimientosCurso();

    public RequerimientoItem(RequerimientosCursoAsistente mod) {
        modAc.setIdAsistente(mod.getIdAsistente());
        modAc.setIdHistorial(mod.getIdHistorial());
        modAc.setIdRequerimiento(mod.getIdRequerimiento());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Nombre del Requerimiento");

        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir Formato");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jButton1)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        if (jRadioButton1.isSelected()) {
            mostrarVentanaFecha();
        } else {
            eliminarRegistro();
        }
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void mostrarVentanaFecha() {
        System.out.println(modAc.getIdRequerimiento());
        JDateChooser dateChooser = new JDateChooser();
        int result = JOptionPane.showConfirmDialog(
                null,
                dateChooser,
                "Seleccione la fecha de aceptación del requerimiento:",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaSeleccionada = dateChooser.getDate();
            String fecha = formato.format(fechaSeleccionada);
            if (modC.agregarRequerimiento(modAc, fecha)) {
                System.out.println("Registro Exitoso");
            }
        }
    }

    private void eliminarRegistro() {
        if(modC.eliminarRequerimiento(modAc)){
            System.out.println("Registro Eliminado");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JRadioButton jRadioButton1;
    // End of variables declaration//GEN-END:variables

}
