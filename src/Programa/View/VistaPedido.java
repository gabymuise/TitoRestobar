package Programa.View;

import Programa.Controller.ControladoraMesa;
import Programa.Controller.ControladoraPedido;
import Programa.Controller.ControladoraProducto;
import Programa.DAO.DAOPedido;
import Programa.DAO.DAOStock;
import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Producto;
import Programa.Model.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VistaPedido extends javax.swing.JPanel {
    private final ControladoraPedido controladoraPedido;
    private final ControladoraProducto controladoraProducto;
    private final ControladoraMesa controladoraMesa;
    private final List<Mesa> mesasDisponibles;
    private final List<Producto> productosDisponibles;
    private final List<Pedido> pedidos;
    private Pedido nuevoPedido;
    private Connection conexion;

    public VistaPedido() throws SQLException {
        initComponents();
        try {
            conexion = Conexion.Conectar();
            controladoraPedido = new ControladoraPedido();
            controladoraProducto = new ControladoraProducto();
            controladoraMesa = new ControladoraMesa();
            mesasDisponibles = cargarMesasDisponibles();
            productosDisponibles = cargarProductosDisponibles();
            pedidos = new ArrayList<>();
            actualizarComboBoxMesas();
            actualizarComboBoxProductos();
            limpiarFormulario();
            cargarDatosTabla();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } finally {
            // Asegúrate de cerrar la conexión si es necesario
        }
    }
    private void inicializarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
    }

    private List<Mesa> cargarMesasDisponibles() {
        try {
            return controladoraMesa.listarMesas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar mesas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    private List<Producto> cargarProductosDisponibles() throws SQLException {
        return controladoraProducto.listadoDeProductos();
    }

    private void actualizarComboBoxMesas() {
        DefaultComboBoxModel<Mesa> mesaModel = new DefaultComboBoxModel<>();
        for (Mesa mesa : mesasDisponibles) {
            mesaModel.addElement(mesa);
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
            Object value = modelo.getValueAt(i, 2);
            if (value instanceof Number) {
                total += ((Number) value).doubleValue();
            }
        }
        jLabelTotal.setText("Total: $" + total);
    }

    private void limpiarFormulario() {
        inicializarTabla();

        if (jComboBoxMesa.getItemCount() > 0) {
            jComboBoxMesa.setSelectedIndex(0);
        }

        if (jComboBoxProducto.getItemCount() > 0) {
            jComboBoxProducto.setSelectedIndex(0);
        }

        txtCantidad.setText("");
        jLabelTotal.setText("Total: $0.00");
    }

    private void cargarDatosTabla() {
     try {
         DefaultTableModel modelo = (DefaultTableModel) jTablePedidos.getModel();
         modelo.setRowCount(0);

         List<Pedido> pedidos = controladoraPedido.obtenerTodosLosPedidosActivos();

         for (Pedido pedido : pedidos) {
             List<Item> items = controladoraPedido.obtenerItemsPorPedido(pedido.getId());
             pedido.setItems(items);

             pedido.calcularSubtotalYTotal(); 

             double descuento = pedido.getDescuento() != null ? pedido.getDescuento().getPorcentaje() : 0;

             Object[] fila = {
                 pedido.getId(),
                 pedido.getMesa().getNombre(),
                 pedido.getFechaHoraApertura(),
                 pedido.getSubtotal(), 
                 pedido.getTotal(),    
                 descuento
             };
             modelo.addRow(fila);
         }

     } catch (SQLException e) {
         JOptionPane.showMessageDialog(this, "Error al obtener los pedidos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
     }
 }
    
    private Producto obtenerProductoPorNombre(String nombreProducto) {
    for (Producto producto : productosDisponibles) {
        if (producto.getNombre().equals(nombreProducto)) {
            return producto;
        }
    }
    return null;
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
        btnModificarItem = new javax.swing.JButton();
        btnEliminarItem = new javax.swing.JButton();
        jLabelTotal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePedidos = new javax.swing.JTable();

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

        jButtonAgregarProducto.setText("Agregar Producto");
        jButtonAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarProductoActionPerformed(evt);
            }
        });

        btnModificarItem.setText("Modificar Item");
        btnModificarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarItemActionPerformed(evt);
            }
        });

        btnEliminarItem.setText("Eliminar Item");
        btnEliminarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarItemActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(btnModificarItem, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(btnEliminarItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jButtonAgregarProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarItem)
                .addContainerGap(179, Short.MAX_VALUE))
        );

        jTablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "idPedido", "Mesa", "HoraApertura", "Subtotal", "Total", "Descuento"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTablePedidos);

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
        String nombreProducto = (String) jComboBoxProducto.getSelectedItem();
        if (nombreProducto == null || nombreProducto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto producto = obtenerProductoPorNombre(nombreProducto);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "El producto seleccionado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (producto.isElaboracion()) {
            double precio = producto.getPrecio(); 
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.addRow(new Object[]{nombreProducto, cantidad, precio * cantidad});

            txtCantidad.setText(""); 
            actualizarTotal();
            return; 
        }

        DAOStock daoStock = new DAOStock();
        Stock stock = daoStock.obtenerStockPorProducto(producto.getId());
        if (stock == null) {
            JOptionPane.showMessageDialog(this, "No se encontró stock para el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cantidad > stock.getCantidad()) {
            JOptionPane.showMessageDialog(this, "No hay suficiente stock disponible. Solo hay " + stock.getCantidad() + " unidades en stock.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio = producto.getPrecio(); 
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.addRow(new Object[]{nombreProducto, cantidad, precio * cantidad});

        txtCantidad.setText("");
        actualizarTotal();
    }//GEN-LAST:event_jButtonAgregarProductoActionPerformed
   
    private void jButtonCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearPedidoActionPerformed
 try {
        
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debe agregar productos antes de crear un pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        Mesa mesaSeleccionada = (Mesa) jComboBoxMesa.getSelectedItem();
        if (mesaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una mesa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DAOPedido daoPedido = new DAOPedido();
        Pedido pedidoActivo = daoPedido.verPedidoActivoDeMesa(mesaSeleccionada);
        if (pedidoActivo != null) {
            JOptionPane.showMessageDialog(this, "La mesa seleccionada ya tiene un pedido activo. No se puede crear otro pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int aplicarDescuento = JOptionPane.showConfirmDialog(this, "¿Desea aplicar un descuento?", "Descuento", JOptionPane.YES_NO_OPTION);
        float porcentajeDescuento = 0;
        if (aplicarDescuento == JOptionPane.YES_OPTION) {
            String input = JOptionPane.showInputDialog(this, "Ingrese el porcentaje de descuento (0 a 99):");
            if (input != null && !input.isEmpty()) {
                try {
                    porcentajeDescuento = Float.parseFloat(input);
                    if (porcentajeDescuento < 0 || porcentajeDescuento >= 100) {
                        JOptionPane.showMessageDialog(this, "El porcentaje de descuento debe estar entre 0 y 99.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Porcentaje de descuento no válido. Se aplicará 0% de descuento.", "Error", JOptionPane.ERROR_MESSAGE);
                    porcentajeDescuento = 0; 
                }
            }
        }

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setMesa(mesaSeleccionada);
        nuevoPedido.setDescuento(new Descuento(porcentajeDescuento));

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String nombreProducto = (String) modelo.getValueAt(i, 0);
            int cantidad = (int) modelo.getValueAt(i, 1);
            Producto producto = obtenerProductoPorNombre(nombreProducto);
            if (producto != null) {
                Item item = new Item(producto, cantidad);
                nuevoPedido.addItem(item); 
                DAOStock daoStock = new DAOStock();
                Stock stock = daoStock.obtenerStockPorProducto(producto.getId());
                if (stock != null) {
                    stock.setCantidad(stock.getCantidad() - cantidad);
                    daoStock.actualizarStock(stock);
                }
            }
        }

        controladoraPedido.crearPedido(nuevoPedido);
        for (Item item : nuevoPedido.getItems()) {
            controladoraPedido.insertarItem(nuevoPedido, item);
        }
        JOptionPane.showMessageDialog(this, "Pedido creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarFormulario();
        cargarDatosTabla();
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

    private void btnModificarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarItemActionPerformed
      DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
    int filaSeleccionada = jTable1.getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String nombreProductoActual = (String) modelo.getValueAt(filaSeleccionada, 0);
    int cantidadActual = (int) modelo.getValueAt(filaSeleccionada, 1);
    double precioActual = (double) modelo.getValueAt(filaSeleccionada, 2) / cantidadActual; // Calcular el precio por unidad actual

    JComboBox<String> comboBoxProducto = new JComboBox<>();
    for (Producto producto : productosDisponibles) {
        comboBoxProducto.addItem(producto.getNombre());
    }

    comboBoxProducto.setSelectedItem(nombreProductoActual);

    JPanel panel = new JPanel();
    panel.add(new JLabel("Seleccione el nuevo producto:"));
    panel.add(comboBoxProducto);
    JTextField txtCantidad = new JTextField(5);
    txtCantidad.setText(String.valueOf(cantidadActual));
    panel.add(new JLabel("Ingrese la nueva cantidad:"));
    panel.add(txtCantidad);

    int result = JOptionPane.showConfirmDialog(this, panel, "Modificar Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        String nuevoNombreProducto = (String) comboBoxProducto.getSelectedItem();
        if (nuevoNombreProducto == null || nuevoNombreProducto.isEmpty()) {
            return;
        }

        String nuevaCantidadStr = txtCantidad.getText();
        if (nuevaCantidadStr == null || nuevaCantidadStr.isEmpty()) {
            return; 
        }

        int nuevaCantidad;
        try {
            nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
            if (nuevaCantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto nuevoProducto = obtenerProductoPorNombre(nuevoNombreProducto);
        if (nuevoProducto == null) {
            JOptionPane.showMessageDialog(this, "El producto seleccionado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double nuevoPrecioPorUnidad = nuevoProducto.getPrecio();
        double nuevoTotal = nuevaCantidad * nuevoPrecioPorUnidad;

        // Actualizar la tabla
        modelo.setValueAt(nuevoNombreProducto, filaSeleccionada, 0);
        modelo.setValueAt(nuevaCantidad, filaSeleccionada, 1);
        modelo.setValueAt(nuevoTotal, filaSeleccionada, 2); 
    }
    }//GEN-LAST:event_btnModificarItemActionPerformed

    private void btnEliminarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarItemActionPerformed
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        int filaSeleccionada = jTable1.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este ítem?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            modelo.removeRow(filaSeleccionada);
        }
    }//GEN-LAST:event_btnEliminarItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarItem;
    private javax.swing.JButton btnModificarItem;
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
    private javax.swing.JTable jTablePedidos;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
