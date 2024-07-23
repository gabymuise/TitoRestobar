package Programa.View;

import Programa.Controller.ControladoraMesa;
import Programa.Controller.ControladoraPedido;
import Programa.Controller.ControladoraProducto;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Timestamp;

public class VistaPedido extends javax.swing.JPanel {
    private final ControladoraPedido controladoraPedido;
    private final ControladoraProducto controladoraProducto;
    private final ControladoraMesa controladoraMesa;
    private final List<Mesa> mesasDisponibles;
    private final List<Producto> productosDisponibles;
    private final List<Pedido> pedidos;
    private Pedido nuevoPedido;
    private Item item;

    public VistaPedido() throws SQLException {
        initComponents();
        controladoraPedido = new ControladoraPedido();
        controladoraProducto = new ControladoraProducto();
        controladoraMesa = new ControladoraMesa();
        mesasDisponibles = cargarMesasDisponibles();
        productosDisponibles = cargarProductosDisponibles();
        pedidos = new ArrayList<>();
        actualizarComboBoxMesas();
        actualizarComboBoxProductos();
        limpiarFormulario();
        cargarPedidosEnTabla();
    }

    private void inicializarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); // Limpia la tabla de productos
    }

    private List<Mesa> cargarMesasDisponibles() {
        try {
            return controladoraMesa.listarMesas();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }    
    private Mesa obtenerMesaSeleccionada() {
        Mesa mesa = (Mesa) jComboBoxMesa.getSelectedItem();
        if (mesa == null) {
            throw new RuntimeException("Debe seleccionar una mesa.");
        }
        return mesa;
    }

    private List<Producto> cargarProductosDisponibles() throws SQLException {
        return controladoraProducto.listadoDeProductos();
    }

    private void actualizarComboBoxMesas() {
        DefaultComboBoxModel<Mesa> mesaModel = new DefaultComboBoxModel<>();
        for (Mesa mesa : mesasDisponibles) {
            mesaModel.addElement(mesa); // Agregar objetos Mesa directamente
        }
        jComboBoxMesa.setModel(mesaModel);
    }

    private void actualizarComboBoxProductos() {
        DefaultComboBoxModel<String> productoModel = new DefaultComboBoxModel<>();
        for (Producto producto : productosDisponibles) {
            productoModel.addElement(producto.getNombre());
        }
        jComboBoxProducto.setModel(productoModel);
    }

    private void actualizarTotal() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        double total = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += (double) modelo.getValueAt(i, 2); // Asumiendo que el total del producto está en la tercera columna
        }
        jLabelTotal.setText("Total: $" + total);
    }

    private void limpiarFormulario() {
        inicializarTabla(); // Limpia la tabla de productos

        if (jComboBoxMesa.getItemCount() > 0) {
            jComboBoxMesa.setSelectedIndex(0); // Solo resetea selección si hay elementos
        }

        if (jComboBoxProducto.getItemCount() > 0) {
            jComboBoxProducto.setSelectedIndex(0); // Solo resetea selección si hay elementos
        }

        txtCantidad.setText(""); // Limpia campo de cantidad
        jLabelTotal.setText("Total: $0.00"); // Resetea total
    }


   private double obtenerPrecioProducto(String productoNombre) {
    for (Producto producto : productosDisponibles) {
        if (producto.getNombre().equals(productoNombre)) {
            return producto.getPrecio();
        }
    }
    return 0.0;
}

    private void cargarPedidosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.setRowCount(0); // Limpia la tabla de pedidos

        for (Pedido pedido : pedidos) {
            double subtotal = pedido.getItems().stream().mapToDouble(item -> item.getCantidad() * item.getProducto().getPrecio()).sum();
            double total = subtotal - pedido.getDescuento().getPorcentaje();
            modelo.addRow(new Object[]{
                pedido.getId(),
                pedido.getMesa().getNombre(),
                pedido.getFechaHoraApertura(),
                subtotal,
                total,
                pedido.getDescuento().getPorcentaje()
            });
        }
    }

    public List<Item> getItemsFromTable() {
        List<Item> items = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                String productoNombre = (String) model.getValueAt(i, 0); // Nombre del producto
                int cantidad = Integer.parseInt(model.getValueAt(i, 1).toString()); // Cantidad
                double precio = Double.parseDouble(model.getValueAt(i, 2).toString()); // Precio

                Producto producto = obtenerProductoPorNombre(productoNombre); // Método para obtener Producto por nombre
                if (producto == null) {
                    // Manejo de error si el producto no se encuentra
                    System.err.println("Producto no encontrado: " + productoNombre);
                    continue;
                }
                items.add(new Item(producto, cantidad));
            } catch (NumberFormatException e) {
                // Manejo de excepción si el formato del número es incorrecto
                e.printStackTrace();
            }
        }
        return items;
    }

    private Producto obtenerProductoPorNombre(String nombre) {
        for (Producto producto : productosDisponibles) {
            if (producto.getNombre().equals(nombre)) {
                return producto;
            }
        }
        return null; // Retorna null si no se encuentra el producto
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonCrearPedido = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelMesa = new javax.swing.JLabel();
        jComboBoxMesa = new javax.swing.JComboBox<>();
        jLabelProducto = new javax.swing.JLabel();
        jComboBoxProducto = new javax.swing.JComboBox<>();
        jLabelCantidad = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jButtonAgregarProducto = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

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

        jButtonCrearPedido.setText("Crear Pedido");
        jButtonCrearPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearPedidoActionPerformed(evt);
            }
        });

        jLabelMesa.setText("Mesas");

        jComboBoxMesa.setModel(new javax.swing.DefaultComboBoxModel<>(new Mesa[0]));
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

        jButtonAgregarProducto.setText("Agregar producto");
        jButtonAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxMesa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelMesa)
                                    .addComponent(jLabelProducto))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabelCantidad)
                        .addGap(20, 20, 20)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jButtonAgregarProducto)
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jButtonAgregarProducto)
                .addContainerGap(249, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "idPedido", "Mesa", "HoraApertura", "Subtotal", "Total", "descuento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonCrearPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonCrearPedido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoActionPerformed
       String productoSeleccionado = (String) jComboBoxProducto.getSelectedItem();
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio = obtenerPrecioProducto(productoSeleccionado);
        double total = precio * cantidad;
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.addRow(new Object[]{productoSeleccionado, cantidad, total});
        actualizarTotal();
        txtCantidad.setText(""); // Limpia el campo de cantidad después de agregar 
    }//GEN-LAST:event_jButtonAgregarProductoActionPerformed

    private void jButtonCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearPedidoActionPerformed
        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos en la tabla. Agregue productos antes de crear el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp fechaHoraApertura = new Timestamp(System.currentTimeMillis());
        float porcentajeDescuento = 0.0f;

        int applyDiscount = JOptionPane.showConfirmDialog(this, "¿Desea aplicar un descuento?", "Descuento", JOptionPane.YES_NO_OPTION);
        if (applyDiscount == JOptionPane.YES_OPTION) {
            String descuentoInput = JOptionPane.showInputDialog(this, "Ingrese el porcentaje de descuento (0-100):");
            try {
                porcentajeDescuento = Float.parseFloat(descuentoInput);
                if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
                    JOptionPane.showMessageDialog(this, "Descuento debe estar entre 0 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    porcentajeDescuento = 0.0f;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Descuento inválido. Se aplicará 0% de descuento.", "Error", JOptionPane.ERROR_MESSAGE);
                porcentajeDescuento = 0.0f;
            }
        }

        Mesa mesaSeleccionada = obtenerMesaSeleccionada();
        List<Item> items = getItemsFromTable();
        Descuento descuento = new Descuento(porcentajeDescuento);

        if (nuevoPedido == null) {
            nuevoPedido = new Pedido();
        }
        nuevoPedido.setMesa(mesaSeleccionada);
        nuevoPedido.setFechaHoraApertura(fechaHoraApertura);
        nuevoPedido.setDescuento(descuento);
        nuevoPedido.setItems(items);

        try {
            boolean exito = controladoraPedido.crearPedido(nuevoPedido, mesaSeleccionada.getId(), items);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Pedido creado con éxito.");
                limpiarFormulario();
                cargarPedidosEnTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonCrearPedidoActionPerformed

    private void jComboBoxMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMesaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxMesaActionPerformed

    private void jComboBoxProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarProducto;
    private javax.swing.JButton jButtonCrearPedido;
    private javax.swing.JComboBox<Mesa> jComboBoxMesa;
    private javax.swing.JComboBox<String> jComboBoxProducto;
    private javax.swing.JLabel jLabelCantidad;
    private javax.swing.JLabel jLabelMesa;
    private javax.swing.JLabel jLabelProducto;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables

}
