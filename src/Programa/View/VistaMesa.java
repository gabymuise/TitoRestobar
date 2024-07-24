package Programa.View;

import Programa.Controller.ControladoraMesa;
import Programa.Controller.ControladoraPedido;
import Programa.DAO.DAOMesa;
import Programa.DAO.DAOPedido;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class VistaMesa extends javax.swing.JPanel {

    private final ControladoraMesa controladoraMesa = new ControladoraMesa();
    private final ControladoraPedido controladoraPedido = new ControladoraPedido();
    private Mesa mesa;

    public VistaMesa() throws SQLException {
        initComponents();
        cargarListaMesa();
        // Inicializa la visibilidad de componentes como no visibles
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
        btnModificarMesa = new javax.swing.JButton();
        btnEliminarMesa = new javax.swing.JButton();
        txtNombreMesa = new javax.swing.JTextField();
        ListaMesas = new javax.swing.JScrollPane();
        ListMesa = new javax.swing.JList<>();
        Separador1 = new javax.swing.JSeparator();
        btnEliminarPedidoDeMesa = new javax.swing.JButton();
        btnVerPedidoActivo = new javax.swing.JButton();

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

        btnModificarMesa.setText("MODIFICAR MESA");
        btnModificarMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarMesaActionPerformed(evt);
            }
        });

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

        btnEliminarPedidoDeMesa.setText("ELIMINAR PEDIDO");
        btnEliminarPedidoDeMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPedidoDeMesaActionPerformed(evt);
            }
        });

        btnVerPedidoActivo.setText("VER PEDIDO EN MESA");
        btnVerPedidoActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPedidoActivoActionPerformed(evt);
            }
        });

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
                        .addComponent(ListaMesas, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jpMesaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAgregarMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarPedidoDeMesa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerPedidoActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))))
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
                        .addComponent(btnModificarMesa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarMesa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarPedidoDeMesa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerPedidoActivo)))
                .addGap(87, 87, 87))
        );

        add(jpMesa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 330));
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreMesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreMesaActionPerformed

    private void btnLimpiarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarNombreActionPerformed
         if (btnLimpiarNombre.isEnabled()) {
            txtNombreMesa.setText(""); // Limpia el campo de texto
        }
        
    }//GEN-LAST:event_btnLimpiarNombreActionPerformed

    private void btnAgregarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMesaActionPerformed
        if (btnAgregarMesa.isEnabled()) {
            lblNombreMesa.setVisible(true);
            txtNombreMesa.setVisible(true);
            btnCrearMesa.setVisible(true);
            btnLimpiarNombre.setVisible(true);
        }
    }//GEN-LAST:event_btnAgregarMesaActionPerformed
    private void cargarListaMesa() throws SQLException {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        ListMesa.setModel(modelo);
        DAOMesa daoMesa = new DAOMesa();

        List<Mesa> mesas = daoMesa.listarMesas(); // Maneja la excepción apropiadamente
        for (Mesa mesa : mesas) {
            modelo.addElement(mesa.getNombre()); // Añade cada mesa a la lista
        }
    }

    private void btnCrearMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearMesaActionPerformed
          String nombre = txtNombreMesa.getText();

        if (!nombre.isEmpty()) {
            try {
                Mesa nuevaMesa = new Mesa(nombre);
                controladoraMesa.crearMesa(nuevaMesa);
                txtNombreMesa.setText(""); // Limpia el campo de texto
                cargarListaMesa(); // Actualiza la lista de mesas
                JOptionPane.showMessageDialog(this, "Mesa creada correctamente.");
            } catch (SQLException ex) {
                Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error al crear la mesa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "El nombre de la mesa no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCrearMesaActionPerformed

    private void btnEliminarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMesaActionPerformed
       int indiceSeleccionado = ListMesa.getSelectedIndex();

        if (indiceSeleccionado >= 0) {
            String nombreMesa = (String) ListMesa.getModel().getElementAt(indiceSeleccionado); // Nombre de la mesa

            try {
                controladoraMesa.eliminarMesa(nombreMesa);
                DefaultListModel<String> modelo = (DefaultListModel<String>) ListMesa.getModel();
                modelo.remove(indiceSeleccionado); // Elimina la mesa de la lista
                JOptionPane.showMessageDialog(this, "Mesa eliminada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar la mesa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de eliminarla.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarMesaActionPerformed

    private void btnEliminarPedidoDeMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPedidoDeMesaActionPerformed
        int indiceSeleccionado = ListMesa.getSelectedIndex();

        if (indiceSeleccionado >= 0) {
            String nombreMesa = (String) ListMesa.getModel().getElementAt(indiceSeleccionado);

            try {
                // Obtener la mesa seleccionada
                Mesa mesa = controladoraMesa.obtenerMesaPorNombre(nombreMesa);

                DAOPedido daoPedido = new DAOPedido();
                Pedido pedidoActivo = daoPedido.obtenerPedidoActivoEnMesa(mesa);

                if (pedidoActivo != null) {
                    // Eliminar el pedido asociado
                    controladoraMesa.eliminarPedidoDeMesa(mesa, pedidoActivo);
                }

                // Ahora eliminar la mesa
                controladoraMesa.eliminarMesa(nombreMesa);

                // Actualizar la vista
                DefaultListModel<String> modelo = (DefaultListModel<String>) ListMesa.getModel();
                modelo.remove(indiceSeleccionado);
                JOptionPane.showMessageDialog(this, "Mesa y pedido eliminados correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar la mesa o el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de eliminarla.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarPedidoDeMesaActionPerformed

    private void btnModificarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarMesaActionPerformed
        int indiceSeleccionado = ListMesa.getSelectedIndex();

        if (indiceSeleccionado >= 0) {
            String nuevoNombre = JOptionPane.showInputDialog(this, "Introduce el nuevo nombre para la mesa:", "Modificar Mesa", JOptionPane.QUESTION_MESSAGE);

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                String nombreActual = (String) ListMesa.getModel().getElementAt(indiceSeleccionado);

                try {
                    controladoraMesa.modificarMesa(nombreActual, nuevoNombre);
                    cargarListaMesa(); // Actualiza la lista de mesas
                    JOptionPane.showMessageDialog(this, "Nombre de la mesa actualizado correctamente.");
                } catch (SQLException ex) {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error al actualizar el nombre de la mesa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nuevo nombre no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de modificarla.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarMesaActionPerformed

    private void btnVerPedidoActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerPedidoActivoActionPerformed
      int indiceSeleccionado = ListMesa.getSelectedIndex();

        if (indiceSeleccionado >= 0) {
            String nombreMesa = (String) ListMesa.getModel().getElementAt(indiceSeleccionado);

            try {
                // Obtener la mesa seleccionada
                Mesa mesaSeleccionada = controladoraMesa.obtenerMesaPorNombre(nombreMesa);

                // Verificar si la mesa tiene un pedido activo
                controladoraPedido.verificarPedidoActivoEnMesa(mesaSeleccionada);

                JOptionPane.showMessageDialog(this, "La mesa seleccionada tiene un pedido activo.", "Pedido Activo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista para verificar el pedido.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnVerPedidoActivoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListMesa;
    private javax.swing.JScrollPane ListaMesas;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JButton btnAgregarMesa;
    private javax.swing.JButton btnCrearMesa;
    private javax.swing.JButton btnEliminarMesa;
    private javax.swing.JButton btnEliminarPedidoDeMesa;
    private javax.swing.JButton btnLimpiarNombre;
    private javax.swing.JButton btnModificarMesa;
    private javax.swing.JButton btnVerPedidoActivo;
    private javax.swing.JPanel jpMesa;
    private javax.swing.JLabel lblNombreMesa;
    private javax.swing.JTextField txtNombreMesa;
    // End of variables declaration//GEN-END:variables


}
