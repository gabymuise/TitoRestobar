package Programa.View;

import Programa.Controller.ControladoraMesa;
import Programa.Controller.ControladoraPedido;
import Programa.Controller.ControladoraProducto;
import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Producto;
import Resources.PedidoNoActivoException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VistaPedido extends javax.swing.JPanel {
    private final ControladoraPedido controladoraPedido;
    private final ControladoraProducto controladoraProducto;
    private final ControladoraMesa controladoraMesa;
    private final List<Mesa> mesasDisponibles;
    private final List<Producto> productosDisponibles;
    private final List<Pedido> pedidos;
    private Pedido nuevoPedido;
    private Connection conexion; // Define la variable para la conexión

    public VistaPedido() throws SQLException {
        initComponents();
        try {
            this.conexion = Conexion.Conectar(); // Inicializa la conexión
            controladoraPedido = new ControladoraPedido();
            controladoraProducto = new ControladoraProducto();
            controladoraMesa = new ControladoraMesa();
            mesasDisponibles = cargarMesasDisponibles();
            productosDisponibles = cargarProductosDisponibles();
            pedidos = new ArrayList<>();
            actualizarComboBoxMesas();
            actualizarComboBoxProductos();
            limpiarFormulario();
            cargarDatosTabla(); // Asegúrate de que este método sea llamado para cargar los datos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Rethrow to handle further up the call stack if needed
        }
    }

    private void inicializarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0); // Limpia la tabla de productos
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
            Object value = modelo.getValueAt(i, 2);
            if (value instanceof Number) {
                total += ((Number) value).doubleValue();
            }
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

    private void cargarDatosTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
            modelo.setRowCount(0); // Limpiar la tabla

            // Obtener la lista de pedidos activos
           // List<Pedido> pedidos = controladoraPedido.obtenerTodosLosPedidosActivos();

            for (Pedido pedido : pedidos) {
                // Obtener todos los items del pedido para calcular subtotal
                List<Item> items = obtenerItemsDelPedido(pedido.getId());
                pedido.setItems(items);

                // Calcular subtotal y total
                float subtotal = 0;
                for (Item item : items) {
                    subtotal += item.getSubtotal();
                }
                double total = pedido.getTotal();

                // Crear fila y añadirla a la tabla
                Object[] fila = {
                    pedido.getId(),
                    pedido.getMesa().getNombre(),
                    pedido.getFechaHoraApertura(),
                    subtotal,
                    total,
                    pedido.getDescuento() != null ? pedido.getDescuento().getPorcentaje() : 0
                };
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener los pedidos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Item> obtenerItemsDelPedido(int pedidoId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT i.id, i.id_producto, i.cantidad, p.nombre, p.precio " +
                       "FROM items i " +
                       "JOIN productos p ON i.id_producto = p.id " +
                       "WHERE i.id_pedido = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, pedidoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Producto producto = new Producto(); // Asegúrate de tener un constructor adecuado en Producto
                    producto.setId(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getFloat("precio"));

                    int cantidad = rs.getInt("cantidad");

                    Item item = new Item(id, producto, cantidad);
                    items.add(item);
                }
            }
        }

        return items;
    }

    private List<Item> getItemsFromTable() {
        List<Item> items = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                String nombreProducto = (String) model.getValueAt(i, 0); // Asegúrate de que sea String
                int cantidad = (Integer) model.getValueAt(i, 1); // Cambiar de String a Integer
                Producto producto = obtenerProductoPorNombre(nombreProducto);
                if (producto != null) {
                    Item item = new Item(producto, cantidad);
                    items.add(item);
                }
            } catch (NumberFormatException ex) {
                // Manejo de error si la conversión a entero falla
                JOptionPane.showMessageDialog(this, "Error al convertir cantidad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        return null; // Retorna null si no se encuentra el producto
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
       try {
            String nombreProducto = (String) jComboBoxProducto.getSelectedItem();
            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = obtenerPrecioProducto(nombreProducto);

            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.addRow(new Object[]{nombreProducto, cantidad, precio * cantidad});
            actualizarTotal();
            txtCantidad.setText(""); // Limpia el campo de cantidad
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
 
    }//GEN-LAST:event_jButtonAgregarProductoActionPerformed
   
    private void jButtonCrearPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearPedidoActionPerformed
         try {
        // Obtener la mesa seleccionada
        int idMesa = ((Mesa) jComboBoxMesa.getSelectedItem()).getId();
        Mesa mesa = controladoraMesa.obtenerMesaPorId(idMesa);
        
        // Obtener los items de la tabla
        List<Item> items = getItemsFromTable();
        
        // Verificar si hay productos en el pedido
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos en el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Aplicar descuento
        Descuento descuento = aplicarDescuento();
        
        // Crear un nuevo pedido
        Pedido nuevoPedido = new Pedido(mesa, new Timestamp(System.currentTimeMillis()), items, descuento);
        
        // Establecer los items del pedido
        nuevoPedido.setItems(items);
        
        // Crear el pedido en la base de datos
        if (controladoraPedido.crearPedido(nuevoPedido)) {
            // Ahora insertar los detalles del pedido en la tabla Detalle_Pedido
            for (Item item : items) {
                controladoraPedido.insertarDetallePedido(nuevoPedido, item);
            }
            
            JOptionPane.showMessageDialog(this, "Pedido creado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar formulario y cargar datos
            limpiarFormulario();
            cargarDatosTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo crear el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }  
        }catch (SQLException e) {
            // Manejo de excepciones SQL
            JOptionPane.showMessageDialog(this, "Error al crear el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Manejo de excepción personalizada para pedido no activo
         catch (Exception e) {
            // Manejo de cualquier otra excepción
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonCrearPedidoActionPerformed

     private Descuento aplicarDescuento() {
        // Puedes reemplazar esto con tu lógica para obtener un descuento del usuario
        String input = JOptionPane.showInputDialog("Ingrese el porcentaje de descuento:");
        float porcentaje;

        try {
            porcentaje = Float.parseFloat(input);
            return new Descuento(porcentaje);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Porcentaje inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return new Descuento(); // Retorna un descuento sin aplicar
        }
    }
    
    
    
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
