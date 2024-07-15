
package Programa;

import Programa.DAO.DAOMesa;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import Programa.Controller.ControladoraMesa;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class VistaMesa extends javax.swing.JPanel {

    private final ControladoraMesa controladoraMesa = new ControladoraMesa();

    public VistaMesa() {
        initComponents();
        CargarListaMesa();
        lblNombreMesa.setVisible(false);
        txtNombreMesa.setVisible(false);
        btnCrearMesa.setVisible(false);
        btnLimpiarNombre.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpMesa = new javax.swing.JPanel();
        lblNombreMesa = new javax.swing.JLabel();
        btnCrearMesa = new javax.swing.JButton();
        btnLimpiarNombre = new javax.swing.JButton();
        btnAgregarMesa = new javax.swing.JButton();
        btnVerMesa = new javax.swing.JButton();
        btnEliminarMesa = new javax.swing.JButton();
        txtNombreMesa = new javax.swing.JTextField();
        ListaMesas = new javax.swing.JScrollPane();
        ListMesa = new javax.swing.JList<>();
        Separador1 = new javax.swing.JSeparator();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombreMesa.setText("Nombre:");

        btnCrearMesa.setText("CREAR");
        btnCrearMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearMesaActionPerformed(evt);
            }
        });

        btnLimpiarNombre.setText("LIMPIAR");
        btnLimpiarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarNombreActionPerformed(evt);
            }
        });

        btnAgregarMesa.setText("AGREGAR MESA");
        btnAgregarMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMesaActionPerformed(evt);
            }
        });

        btnVerMesa.setText("VER MESA");

        btnEliminarMesa.setText("ELIMINAR MESA");
        btnEliminarMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMesaActionPerformed(evt);
            }
        });

        txtNombreMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreMesaActionPerformed(evt);
            }
        });

        ListMesa.setModel(new DefaultListModel<String>());
        ListaMesas.setViewportView(ListMesa);

        javax.swing.GroupLayout jpMesaLayout = new javax.swing.GroupLayout(jpMesa);
        jpMesa.setLayout(jpMesaLayout);
        jpMesaLayout.setHorizontalGroup(
            jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Separador1)
            .addGroup(jpMesaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpMesaLayout.createSequentialGroup()
                        .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpMesaLayout.createSequentialGroup()
                                .addComponent(btnCrearMesa)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiarNombre))
                            .addGroup(jpMesaLayout.createSequentialGroup()
                                .addComponent(lblNombreMesa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 409, Short.MAX_VALUE))
                    .addGroup(jpMesaLayout.createSequentialGroup()
                        .addComponent(ListaMesas, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarMesa, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jpMesaLayout.setVerticalGroup(
            jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMesaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreMesa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearMesa)
                    .addComponent(btnLimpiarNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ListaMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpMesaLayout.createSequentialGroup()
                        .addComponent(btnAgregarMesa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerMesa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarMesa)))
                .addGap(87, 87, 87))
        );

        add(jpMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 330));
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreMesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMesaActionPerformed

    private void btnLimpiarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarNombreActionPerformed
        if(btnLimpiarNombre.isEnabled()){txtNombreMesa.setText("");}
        
    }//GEN-LAST:event_btnLimpiarNombreActionPerformed

    private void btnAgregarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMesaActionPerformed
        if(btnAgregarMesa.isEnabled()){
            lblNombreMesa.setVisible(true);
            txtNombreMesa.setVisible(true);
            btnCrearMesa.setVisible(true);
            btnLimpiarNombre.setVisible(true);
        }
    }//GEN-LAST:event_btnAgregarMesaActionPerformed
    private void CargarListaMesa() {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        ListMesa.setModel(modelo);
        DAOMesa daoMesa = new DAOMesa();

        try {
            List<Mesa> mesas = daoMesa.listarMesas();
            for (Mesa mesa : mesas) {
                modelo.addElement(mesa.getNombre());
            }
        } catch (SQLException e) {
            // Maneja la excepción apropiadamente
            e.printStackTrace();
        }
    }

    private void btnCrearMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearMesaActionPerformed
        String nombre = txtNombreMesa.getText();

        if (!nombre.isEmpty()) {
            controladoraMesa.CrearMesa(nombre);
            txtNombreMesa.setText("");
            CargarListaMesa();
        }
    }//GEN-LAST:event_btnCrearMesaActionPerformed

    private void btnEliminarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMesaActionPerformed
        int indiceSeleccionado = ListMesa.getSelectedIndex();

    if (indiceSeleccionado >= 0) {
        String nombreMesa = (String) ListMesa.getModel().getElementAt(indiceSeleccionado); // Nombre de la mesa

        ControladoraMesa controladoraMesa = new ControladoraMesa();

        controladoraMesa.EliminarMesa(nombreMesa);
        DefaultListModel<String> modelo = (DefaultListModel<String>) ListMesa.getModel();
        modelo.remove(indiceSeleccionado);
        JOptionPane.showMessageDialog(this, "Mesa eliminada correctamente.");
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de eliminarla.", "Error", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarMesaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListMesa;
    private javax.swing.JScrollPane ListaMesas;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JButton btnAgregarMesa;
    private javax.swing.JButton btnCrearMesa;
    private javax.swing.JButton btnEliminarMesa;
    private javax.swing.JButton btnLimpiarNombre;
    private javax.swing.JButton btnVerMesa;
    private javax.swing.JPanel jpMesa;
    private javax.swing.JLabel lblNombreMesa;
    private javax.swing.JTextField txtNombreMesa;
    // End of variables declaration//GEN-END:variables


}
