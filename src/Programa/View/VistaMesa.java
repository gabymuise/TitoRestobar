package Programa.View;

import Programa.Controller.ControladoraMesa;
import Programa.Controller.ControladoraPedido;
import Programa.DAO.DAOMesa;
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

    public VistaMesa() throws SQLException {
        initComponents();
        cargarListaMesa();
        // Inicializa la visibilidad de componentes como no visibles
        lblNombreMesa.setVisible(false);
        txtNombreMesa.setVisible(false);
        btnCrearMesa.setVisible(false);
        btnLimpiarNombre.setVisible(false);
    }
    
    private void cargarListaMesa() throws SQLException {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        ListMesa.setModel(modelo);
        DAOMesa daoMesa = new DAOMesa();

        List<Mesa> mesas = daoMesa.listarMesas(); // Maneja la excepción apropiadamente
        for (Mesa mesa : mesas) {
            modelo.addElement(mesa.getNombre()); // Añade cada mesa a la lista
        }
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
        btnCerrarPedido = new javax.swing.JButton();

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

        btnCerrarPedido.setText("CERRAR PEDIDO EN MESA");
        btnCerrarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarPedidoActionPerformed(evt);
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
                            .addComponent(btnVerPedidoActivo, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(btnCerrarPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                        .addComponent(btnVerPedidoActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCerrarPedido)))
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
                if (ex.getMessage().contains("La mesa ya existe")) {
                    JOptionPane.showMessageDialog(this, "La mesa ya existe. Por favor, elige otro nombre.", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error al crear la mesa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "El nombre de la mesa no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnCrearMesaActionPerformed

    private void btnEliminarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMesaActionPerformed
     int indiceSeleccionado = ListMesa.getSelectedIndex();
    if (indiceSeleccionado >= 0) {
        String nombreMesa = (String) ListMesa.getModel().getElementAt(indiceSeleccionado);

        try {
            Mesa mesa = controladoraMesa.obtenerMesaPorNombre(nombreMesa);
            if (mesa != null) {
                // Verificar si la mesa tiene pedidos activos
                if (controladoraMesa.tienePedidosActivos(mesa)) {
                    // Mostrar mensaje de advertencia si hay pedidos activos
                    JOptionPane.showMessageDialog(this, "La mesa tiene un pedido activo. No se puede eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Mostrar mensaje de advertencia si hay pedidos cerrados
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "La mesa tiene pedidos cerrados. ¿Estás seguro de que deseas eliminarla?",
                            "Confirmar Eliminación",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // La mesa tiene pedidos cerrados y el usuario confirma la eliminación
                        controladoraMesa.eliminarMesa(mesa.getId());
                        cargarListaMesa(); // Actualizar la lista de mesas
                        JOptionPane.showMessageDialog(this, "Mesa eliminada correctamente.");
                    } else {
                        // El usuario cancela la eliminación
                        JOptionPane.showMessageDialog(this, "La eliminación de la mesa ha sido cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar la mesa para eliminarla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al eliminar la mesa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            Mesa mesa = controladoraMesa.obtenerMesaPorNombre(nombreMesa);
           
            if (mesa != null) {
                Pedido pedidoActivo = controladoraMesa.obtenerPedidoActivo(mesa.getId());
                if (pedidoActivo != null) {
                    controladoraMesa.eliminarPedidoDeMesa(mesa, pedidoActivo);

                    JOptionPane.showMessageDialog(this, "Pedido de la mesa eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "La mesa no tiene un pedido activo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al eliminar el pedido de la mesa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de eliminar el pedido.", "Error", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarPedidoDeMesaActionPerformed

    private void btnModificarMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarMesaActionPerformed
        int indiceSeleccionado = ListMesa.getSelectedIndex();
        if (indiceSeleccionado >= 0) {
            String nuevoNombre = JOptionPane.showInputDialog(this, "Introduce el nuevo nombre para la mesa:", "Modificar Mesa", JOptionPane.QUESTION_MESSAGE);

            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                String nombreActual = (String) ListMesa.getModel().getElementAt(indiceSeleccionado);

                try {
                    // Obtener la mesa actual para asegurarse de que exista antes de modificarla
                    Mesa mesaActual = controladoraMesa.obtenerMesaPorNombre(nombreActual);

                    if (mesaActual != null) {
                        // Modificar la mesa con el nuevo nombre
                        controladoraMesa.modificarMesa(mesaActual.getId(), nuevoNombre);
                        cargarListaMesa(); // Actualiza la lista de mesas
                        JOptionPane.showMessageDialog(this, "Nombre de la mesa actualizado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "No se pudo encontrar la mesa actual para modificarla.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Error al actualizar el nombre de la mesa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            Mesa mesa = controladoraMesa.obtenerMesaPorNombre(nombreMesa);
            if (mesa != null) {
                Pedido pedidoActivo = controladoraMesa.obtenerPedidoActivo(mesa.getId());
                if (pedidoActivo != null) {
                    JOptionPane.showMessageDialog(this, "Pedido activo en la " + (mesa.getNombre()));
                } else {
                    JOptionPane.showMessageDialog(this, "La mesa no tiene un pedido activo.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al obtener el pedido activo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de ver el pedido activo.", "Error", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnVerPedidoActivoActionPerformed

    private void btnCerrarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarPedidoActionPerformed
    int indiceSeleccionado = ListMesa.getSelectedIndex();
    if (indiceSeleccionado >= 0) {
        String nombreMesa = (String) ListMesa.getModel().getElementAt(indiceSeleccionado);

        try {
            Mesa mesa = controladoraMesa.obtenerMesaPorNombre(nombreMesa);
            if (mesa != null) {
                // Verificar si la mesa tiene un pedido activo
                Pedido pedidoActivo = controladoraMesa.obtenerPedidoActivo(mesa.getId());
                if (pedidoActivo != null) {
                    ControladoraPedido controladoraPedido = new ControladoraPedido();
                    // Cerrar el pedido de la mesa
                    controladoraPedido.cerrarPedido(pedidoActivo);
                    JOptionPane.showMessageDialog(this, "Pedido cerrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "La mesa no tiene un pedido activo.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo encontrar la mesa.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaMesa.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al cerrar el pedido: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecciona una mesa de la lista antes de cerrar el pedido.", "Error", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnCerrarPedidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListMesa;
    private javax.swing.JScrollPane ListaMesas;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JButton btnAgregarMesa;
    private javax.swing.JButton btnCerrarPedido;
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
