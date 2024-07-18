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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class VistaPedido extends javax.swing.JPanel {
    private ControladoraPedido controladoraPedido;
    private ControladoraProducto controladoraProducto;
    private ControladoraMesa controladoraMesa;
    private List<Mesa> mesasDisponibles;
    private List<Producto> productosDisponibles;
    private List<Pedido> pedidos;
    private Descuento descuento;

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
        limpiarFormulario(); // Asegura que la tabla esté vacía al iniciar
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
        jComboBoxMesa.setSelectedIndex(0); // Resetea selección de mesa
        jComboBoxProducto.setSelectedIndex(0); // Resetea selección de producto
        txtCantidad.setText(""); // Limpia campo de cantidad
        jLabelTotal.setText("Total: $0.00"); // Resetea total
    }

    private double calcularSubtotal() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        double subtotal = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            subtotal += (double) modelo.getValueAt(i, 2); // Asumiendo que el total del producto está en la tercera columna
        }
        return subtotal;
    }

    private double obtenerPrecioProducto(String producto) {
        for (Producto prod : productosDisponibles) {
            if (prod.getNombre().equals(producto)) {
                return prod.getPrecio();
            }
        }
        return 0.0;
    }

   private void cargarPedidosEnTabla() {
    DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
    modelo.setRowCount(0); // Limpia la tabla de pedidos

    for (Pedido pedido : pedidos) {
        double subtotal = pedido.getItems().stream().mapToDouble(item -> item.getSubTotal()).sum(); // Usa lambda en lugar de referencia de método

        modelo.addRow(new Object[]{
            pedido.getId(),
            pedido.getMesa().getNombre(),
            pedido.getFechaHoraApertura(),
            subtotal,
            subtotal - pedido.getDescuento().getPorcentaje(), // Ajusta si es necesario
            pedido.getDescuento().getPorcentaje()
        });
    }
}
private double pedirDescuento() {
    int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea aplicar un descuento?", "Aplicar Descuento", JOptionPane.YES_NO_OPTION);
    
    if (respuesta == JOptionPane.YES_OPTION) {
        String descuentoStr = JOptionPane.showInputDialog(this, "Ingrese el monto del descuento:");
        
        try {
            return Double.parseDouble(descuentoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El descuento debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    return 0.0;
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
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarProductoActionPerformed
    String productoSeleccionado = (String) jComboBoxProducto.getSelectedItem();
    
    // Obtén el texto de la cantidad y verifica si no está vacío
    String cantidadText = txtCantidad.getText();
    int cantidad = 0;
    boolean cantidadValida = true;
    
    try {
        // Intenta convertir la cantidad a un número entero
        cantidad = Integer.parseInt(cantidadText);
        
        // Verifica que la cantidad sea positiva
        if (cantidad <= 0) {
            cantidadValida = false;
            JOptionPane.showMessageDialog(null, "La cantidad debe ser un número positivo.");
        }
    } catch (NumberFormatException e) {
        cantidadValida = false;
        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido para la cantidad.");
    }
    
    // Si la cantidad es válida, procede a agregar el producto a la tabla
    if (cantidadValida) {
        double precio = obtenerPrecioProducto(productoSeleccionado);
        double totalProducto = precio * cantidad;

        // Agrega la fila a la tabla
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.addRow(new Object[]{productoSeleccionado, cantidad, totalProducto});
        
        // Actualiza el total
        actualizarTotal();
    }
    }//GEN-LAST:event_jButtonAgregarProductoActionPerformed

    private void jButtonCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearPedidoActionPerformed
      String mesaSeleccionada = (String) jComboBoxMesa.getSelectedItem();
    DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    int filas = modelo.getRowCount();
    
    if (filas == 0) {
        JOptionPane.showMessageDialog(this, "Por favor, agregue productos al pedido.");
        return;
    }

    double subtotal = calcularSubtotal();
    double descuento = pedirDescuento(); // Usar el nuevo método para obtener el descuento
    double total = subtotal - descuento;

    Mesa mesa = mesasDisponibles.stream()
                                .filter(m -> m.getNombre().equals(mesaSeleccionada))
                                .findFirst()
                                .orElse(null);
    if (mesa == null) {
        JOptionPane.showMessageDialog(this, "Mesa seleccionada no válida.");
        return;
    }

    // Create a list of items for the order
    List<Item> items = new ArrayList<>();
    for (int i = 0; i < filas; i++) {
        String productoNombre = (String) modelo.getValueAt(i, 0);
        int cantidad = (int) modelo.getValueAt(i, 1);
        double precio = (double) modelo.getValueAt(i, 2);

        Producto producto = productosDisponibles.stream()
                                                .filter(p -> p.getNombre().equals(productoNombre))
                                                .findFirst()
                                                .orElse(null);
        if (producto != null) {
            // Create an Item using Producto and quantity
            Item item = new Item(producto, cantidad); // Assuming this is the correct constructor
            items.add(item);
        }
    }

    // Create a new order
    Pedido nuevoPedido = new Pedido(mesa, new Date(), items, new Descuento((float) descuento));
    pedidos.add(nuevoPedido);

    JOptionPane.showMessageDialog(this, "Pedido creado exitosamente!");
    limpiarFormulario();
    cargarPedidosEnTabla(); // Update the order table
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
    private javax.swing.JComboBox<String> jComboBoxMesa;
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
