package Programa;

import Programa.Controller.ControladoraMesa;
import Programa.Controller.ControladoraPedido;
import Programa.Controller.ControladoraProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VistaPedido extends javax.swing.JPanel {
    private ControladoraPedido controladoraPedido;
    private ControladoraProducto controladoraProducto;
    private ControladoraMesa controladoraMesa;
    private List<Mesa> mesasDisponibles;
    private List<Producto> productosDisponibles;
    private List<Item> itemsPedido;
    private Pedido pedido;
    private Descuento descuento;

    public VistaPedido() {
        initComponents();
        try {
            controladoraPedido = new ControladoraPedido(Conexion.Conectar());
            controladoraProducto = new ControladoraProducto();  // Inicializar controladoraProducto aquí
            controladoraMesa = new ControladoraMesa();}
        catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
        }
        controladoraMesa = new ControladoraMesa();
        mesasDisponibles = cargarMesasDisponibles();
        productosDisponibles = cargarProductosDisponibles();
        itemsPedido = new ArrayList<>();
        actualizarComboBoxMesas();
        actualizarComboBoxProductos();
    }

    private List<Mesa> cargarMesasDisponibles() {
        try {
            return controladoraMesa.listarMesas();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Producto> cargarProductosDisponibles() {
        List<Producto> productos = new ArrayList<>();
        try {
            productos = controladoraProducto.listadoDeProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
    private void actualizarComboBoxMesas() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Mesa mesa : mesasDisponibles) {
            model.addElement(mesa.getNombre());
        }
        jComboBoxMesa.setModel(model);
    }

    private void actualizarComboBoxProductos() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Producto producto : productosDisponibles) {
            model.addElement(producto.getNombre());
        }
        jComboBoxProducto.setModel(model);
    }

    private void actualizarTablaItems() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpiar la tabla
        for (Item item : itemsPedido) {
            model.addRow(new Object[]{
                item.getProducto().getNombre(),
                item.getCantidad(),
                item.getProducto().getPrecio()
            });
        }
        jLabelTotal.setText("Total: " + (pedido != null ? pedido.getTotal() : 0));
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        crearPedido = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelMesa = new javax.swing.JLabel();
        jComboBoxMesa = new javax.swing.JComboBox<>();
        jLabelProducto = new javax.swing.JLabel();
        jComboBoxProducto = new javax.swing.JComboBox<>();
        jLabelCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jButtonAgregarProducto = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 500));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Cantidad", "Precio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        crearPedido.setText("Crear Pedido");
        crearPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearPedidoActionPerformed(evt);
            }
        });

        jLabelMesa.setText("Mesas");

        jComboBoxMesa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMesaActionPerformed(evt);
            }
        });

        jLabelProducto.setText("Producto");

        jComboBoxProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxProductoActionPerformed(evt);
            }
        });

        jLabelCantidad.setText("Cantidad:");

        jLabel1.setText("Descuento:");

        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });

        jButtonAgregarProducto.setText("Agregar producto");
        jButtonAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoActionPerformed(evt);
            }
        });

        jLabelTotal.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxMesa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMesa)
                            .addComponent(jLabelProducto)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCantidad)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonAgregarProducto)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCantidad)
                                        .addComponent(txtDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMesa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotal)
                .addGap(18, 18, 18)
                .addComponent(jButtonAgregarProducto)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(crearPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crearPedido)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void jButtonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoActionPerformed
    Producto productoSeleccionado = (Producto) jComboBoxProducto.getSelectedItem();
    int cantidad = 0;
    try {
        cantidad = Integer.parseInt(txtCantidad.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Cantidad no válida.");
        return;
    }
    if (productoSeleccionado != null && cantidad > 0) {
        Item item = new Item(productoSeleccionado, cantidad);
        itemsPedido.add(item);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{
            productoSeleccionado.getNombre(),
            cantidad,
            productoSeleccionado.getPrecio()
        });
        actualizarTablaItems();
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione un producto y/o ingrese una cantidad válida.");
    }
    }//GEN-LAST:event_jButtonAgregarProductoActionPerformed

    private void crearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearPedidoActionPerformed
        try {
            if (itemsPedido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto al pedido.");
                return;
            }
            
            Mesa mesaSeleccionada = (Mesa) jComboBoxMesa.getSelectedItem();
            
            pedido = new Pedido(new Date(), itemsPedido, descuento);
            controladoraPedido.crearPedido(pedido);
            JOptionPane.showMessageDialog(this, "Pedido creado exitosamente.");
        } catch (SQLException ex) {
            Logger.getLogger(VistaPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_crearPedidoActionPerformed

    private void jComboBoxMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMesaActionPerformed

    private void jComboBoxProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton crearPedido;
    private javax.swing.JButton jButtonAgregarProducto;
    private javax.swing.JComboBox<String> jComboBoxMesa;
    private javax.swing.JComboBox<String> jComboBoxProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelCantidad;
    private javax.swing.JLabel jLabelMesa;
    private javax.swing.JLabel jLabelProducto;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescuento;
    // End of variables declaration//GEN-END:variables
}
