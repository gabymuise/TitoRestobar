package Programa.View;

import Programa.Controller.ControladoraProducto;
import Programa.DAO.DAOProducto;
import Programa.Model.Conexion;
import Programa.Model.Producto;
import Programa.Model.Stock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VistaProducto extends javax.swing.JPanel {

    public VistaProducto() {
        initComponents();
        chkElaboracion.setSelected(true);
        chkElaboracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkElaboracionActionPerformed(evt);
            }
        });
        txtCantidadProducto.setEnabled(false);
        cargarProductos();
    }

    public void cargarProductos() {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.Conectar();
            String sql = "SELECT * FROM productos";
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
            model.setRowCount(0); 
            while (resultSet.next()) {
                int id = resultSet.getInt("id"); 
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                float precio = resultSet.getFloat("precio");
                float costo = resultSet.getFloat("costo");
                boolean elaborado = resultSet.getBoolean("elaborado");
                model.addRow(new Object[]{id, nombre, descripcion, precio, costo, elaborado});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGuardarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnLimpiarTexto = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblNombreProducto = new javax.swing.JLabel();
        lblDescripcionProducto = new javax.swing.JLabel();
        lblPrecioProducto = new javax.swing.JLabel();
        lblCostoProducto = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCostoProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        SPTableProductos = new javax.swing.JScrollPane();
        tableProductos = new javax.swing.JTable();
        lblElaboracionProducto = new javax.swing.JLabel();
        chkElaboracion = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtCantidadProducto = new javax.swing.JTextField();
        jBtnActualizarPrecios = new javax.swing.JButton();

        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 500));

        btnGuardarProducto.setText("GUARDAR");
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });

        btnEliminarProducto.setText("ELIMINAR");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });

        btnLimpiarTexto.setText("LIMPIAR");
        btnLimpiarTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTextoActionPerformed(evt);
            }
        });

        lblTitulo.setText("AGREGAR PRODUCTOS");

        lblNombreProducto.setText("Nombre:");

        lblDescripcionProducto.setText("Descripcion:");

        lblPrecioProducto.setText("Precio:");

        lblCostoProducto.setText("Costo:");

        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });

        txtPrecioProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProductoActionPerformed(evt);
            }
        });

        tableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nombre", "Descripcion", "Costo", "Precio", "Elaboracion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        // Llama a tu método para obtener la lista de productos desde la base de datos
        DAOProducto dp = new DAOProducto();
        List<Producto> listaDeProductos = new ArrayList<>(); // Inicializamos la lista vacía

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio");
        modelo.addColumn("Costo");
        modelo.addColumn("Elaborado");
        tableProductos.setModel(modelo);

        try {
            listaDeProductos = dp.listadoDeProductos(); // Intentamos obtener la lista de productos

            // Llena el modelo con los datos de la lista de productos
            for (Producto producto : listaDeProductos) {
                modelo.addRow(new Object[] {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getCosto(),
                    producto.isElaboracion()
                });
            }
        } catch (SQLException e) {
            // Manejo de excepciones: muestra un mensaje de error en caso de problemas con la base de datos
            JOptionPane.showMessageDialog(this, "Error al cargar los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Asigna el modelo a tu tabla tableProductos
        tableProductos.setModel(modelo);

        SPTableProductos.setViewportView(tableProductos);
        SPTableProductos.setViewportView(tableProductos);

        lblElaboracionProducto.setText("Elaboracion:");

        chkElaboracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkElaboracionActionPerformed(evt);
            }
        });

        jLabel1.setText("Cantidad:");

        txtCantidadProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadProductoActionPerformed(evt);
            }
        });

        jBtnActualizarPrecios.setText("ACTUALIZAR PRECIOS");
        jBtnActualizarPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarPreciosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jBtnActualizarPrecios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(174, 174, 174))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addComponent(lblTitulo))
                        .addComponent(btnGuardarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblCostoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPrecioProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addComponent(lblElaboracionProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDescripcionProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                .addComponent(txtNombreProducto)
                                .addComponent(txtPrecioProducto)
                                .addComponent(txtCostoProducto)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(chkElaboracion)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel1)
                                    .addGap(3, 3, 3)
                                    .addComponent(txtCantidadProducto))))
                        .addComponent(btnEliminarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiarTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(SPTableProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(376, 376, 376))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SPTableProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lblNombreProducto))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDescripcionProducto)
                            .addComponent(txtDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCostoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCostoProducto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrecioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecioProducto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblElaboracionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chkElaboracion))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtCantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiarTexto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBtnActualizarPrecios)))
                .addGap(0, 237, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if (btnEliminarProducto.isEnabled()) {
            int filaSeleccionada = tableProductos.getSelectedRow();
            ControladoraProducto controladoraProducto = new ControladoraProducto();
            if (filaSeleccionada >= 0) {
                try {
                    String nombreProducto = (String) tableProductos.getValueAt(filaSeleccionada, 1);
                    boolean eliminado = controladoraProducto.eliminarProductoPorNombre(nombreProducto);
                    if (eliminado) {
                        DefaultTableModel modelo = (DefaultTableModel) tableProductos.getModel();
                        modelo.removeRow(filaSeleccionada);
                        JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(VistaProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla antes de eliminarlo.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            cargarProductos();
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        if (btnGuardarProducto.isEnabled()) {
        try {
                if (txtNombreProducto.getText().isEmpty() || txtDescripcionProducto.getText().isEmpty() ||
                        txtCostoProducto.getText().isEmpty() || txtPrecioProducto.getText().isEmpty() ||
                        (!chkElaboracion.isSelected() && txtCantidadProducto.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                String nombre = txtNombreProducto.getText();
                String descripcion = txtDescripcionProducto.getText();
                float precio = Float.parseFloat(txtPrecioProducto.getText());
                float costo = Float.parseFloat(txtCostoProducto.getText());
                boolean elaboracion = chkElaboracion.isSelected();

                ControladoraProducto controladoraProducto = new ControladoraProducto();

                // Verificar si el producto ya existe
                if (controladoraProducto.existeProducto(nombre)) {
                    JOptionPane.showMessageDialog(this, "El producto ya existe en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);

                controladoraProducto.guardar(producto);
                if (!elaboracion) {
                    int cantidad = Integer.parseInt(txtCantidadProducto.getText());
                    Stock stock = new Stock(cantidad, producto);
                    controladoraProducto.guardarStock(stock);
                }

                txtNombreProducto.setText("");
                txtDescripcionProducto.setText("");
                txtCostoProducto.setText("");
                txtPrecioProducto.setText("");
                chkElaboracion.setSelected(true);
                txtCantidadProducto.setText("");

                cargarProductos();
            } catch (SQLException ex) {
                Logger.getLogger(VistaProducto.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido en los campos de precio, costo y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed
   
    private void btnLimpiarTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTextoActionPerformed
       if(btnLimpiarTexto.isEnabled()){
           txtNombreProducto.setText("");
            txtDescripcionProducto.setText("");
            txtCostoProducto.setText("");
            txtPrecioProducto.setText("");
            chkElaboracion.setSelected(true);
       }
    }//GEN-LAST:event_btnLimpiarTextoActionPerformed

    private void txtPrecioProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProductoActionPerformed

    private void chkElaboracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkElaboracionActionPerformed
       if (chkElaboracion.isSelected()) {
            txtCantidadProducto.setEnabled(false);
        } else {
            txtCantidadProducto.setEnabled(true);
        }
    }//GEN-LAST:event_chkElaboracionActionPerformed

    private void txtCantidadProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProductoActionPerformed

    }//GEN-LAST:event_txtCantidadProductoActionPerformed

    private void jBtnActualizarPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarPreciosActionPerformed
        int filaSeleccionada = tableProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                int id = (int) tableProductos.getValueAt(filaSeleccionada, 0);
                String nombre = (String) tableProductos.getValueAt(filaSeleccionada, 1);
                String descripcion = (String) tableProductos.getValueAt(filaSeleccionada, 2);

                String nuevoPrecioStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo precio para el producto:");
                if (nuevoPrecioStr == null || nuevoPrecioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El precio no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nuevoCostoStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo costo para el producto:");
                if (nuevoCostoStr == null || nuevoCostoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El costo no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                float nuevoPrecio = Float.parseFloat(nuevoPrecioStr);
                float nuevoCosto = Float.parseFloat(nuevoCostoStr);
                boolean elaborado = (boolean) tableProductos.getValueAt(filaSeleccionada, 5);

                Producto producto = new Producto(id, nombre, descripcion, nuevoPrecio, nuevoCosto, elaborado);
                ControladoraProducto controladoraProducto = new ControladoraProducto();
                controladoraProducto.actualizar(producto);

                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                cargarProductos(); 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Precio o costo deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla antes de actualizar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBtnActualizarPreciosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPTableProductos;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnLimpiarTexto;
    private javax.swing.JCheckBox chkElaboracion;
    private javax.swing.JButton jBtnActualizarPrecios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCostoProducto;
    private javax.swing.JLabel lblDescripcionProducto;
    private javax.swing.JLabel lblElaboracionProducto;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblPrecioProducto;
    private javax.swing.JLabel lblTitulo;
    public javax.swing.JTable tableProductos;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JTextField txtCostoProducto;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioProducto;
    // End of variables declaration//GEN-END:variables

}
