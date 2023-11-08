package Programa;

import Programa.Controller.ControladoraProducto;
import Programa.DAO.DAOProducto;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
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
        
        
    }
   
     public void cargarProductos() {
    try {
        // Conecta a la base de datos (asegúrate de tener el controlador JDBC cargado)
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/titorestobar", "root", "");

        // Consulta SQL para seleccionar todos los productos
        String sql = "SELECT * FROM producto";

        // Crea una declaración preparada
        PreparedStatement statement = conn.prepareStatement(sql);

        // Ejecuta la consulta
        ResultSet resultSet = statement.executeQuery();

        // Borra todos los registros existentes en la tabla
        DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
        model.setRowCount(0);

        // Itera a través de los resultados y agrega cada producto a la tabla
        while (resultSet.next()) {
            int id = resultSet.getInt("Id");
            String nombre = resultSet.getString("Nombre");
            String descripcion = resultSet.getString("Descripcion");
            float precio = resultSet.getFloat("Precio");
            float costo = resultSet.getFloat("Costo");
            boolean elaboracion = resultSet.getBoolean("elaborado");

            // Agrega el producto a la tabla
            model.addRow(new Object[]{id, nombre, descripcion, precio, costo, elaboracion});
        }

        // Cierra la conexión a la base de datos
        conn.close();
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(lblDescripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(174, 174, 174))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(57, 57, 57)
                            .addComponent(lblTitulo))
                        .addComponent(btnGuardarProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblCostoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPrecioProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addComponent(lblElaboracionProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDescripcionProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                    .addComponent(txtNombreProducto)
                                    .addComponent(txtPrecioProducto)
                                    .addComponent(txtCostoProducto))
                                .addComponent(chkElaboracion)))
                        .addComponent(btnEliminarProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiarTexto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(178, 178, 178)))
                .addComponent(SPTableProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(376, 376, 376))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblElaboracionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkElaboracion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarProducto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiarTexto)))
                .addGap(0, 239, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
  
        if (btnEliminarProducto.isEnabled()) {
        // Obtén la fila seleccionada de la tabla
        int filaSeleccionada = tableProductos.getSelectedRow();
 
   
        if (filaSeleccionada >= 0) {
            // Obtiene el nombre del producto de la fila seleccionada
            String nombreProducto = (String) tableProductos.getValueAt(filaSeleccionada, 1); // Nombre

            // Llama a la ControladoraProducto para eliminar el producto por nombre
            ControladoraProducto cp = new ControladoraProducto();
            boolean eliminado = cp.EliminarProductoPorNombre(nombreProducto);

            if (eliminado) {
                // Elimina la fila de la tabla
                DefaultTableModel modelo = (DefaultTableModel) tableProductos.getModel();
                modelo.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla antes de eliminarlo.", "Error", JOptionPane.WARNING_MESSAGE);
        }
             
    }
                cargarProductos();
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
   if (btnGuardarProducto.isEnabled()) {
        String nombre = txtNombreProducto.getText();
        String descripcion = txtDescripcionProducto.getText();
        float precio = Float.parseFloat(txtCostoProducto.getText());
        float costo = Float.parseFloat(txtPrecioProducto.getText());
        boolean elaboracion = chkElaboracion.isSelected();

        if (!nombre.isEmpty()) {
            ControladoraProducto controladoraProducto = new ControladoraProducto(); 
            try {
                Producto producto = controladoraProducto.CrearProducto(nombre, descripcion, precio, costo, elaboracion);
            } catch (SQLException ex) {
                Logger.getLogger(VistaProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        txtNombreProducto.setText("");
        txtDescripcionProducto.setText("");
        txtCostoProducto.setText("");
        txtPrecioProducto.setText("");
        chkElaboracion.setSelected(false);
    }
           cargarProductos();
    }//GEN-LAST:event_btnGuardarProductoActionPerformed
   
    private void btnLimpiarTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTextoActionPerformed
       if(btnLimpiarTexto.isEnabled()){
           txtNombreProducto.setText("");
            txtDescripcionProducto.setText("");
            txtCostoProducto.setText("");
            txtPrecioProducto.setText("");
            chkElaboracion.setSelected(false);
       }
    }//GEN-LAST:event_btnLimpiarTextoActionPerformed

    private void txtPrecioProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProductoActionPerformed

    private void chkElaboracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkElaboracionActionPerformed
//
    }//GEN-LAST:event_chkElaboracionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane SPTableProductos;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnLimpiarTexto;
    private javax.swing.JCheckBox chkElaboracion;
    private javax.swing.JLabel lblCostoProducto;
    private javax.swing.JLabel lblDescripcionProducto;
    private javax.swing.JLabel lblElaboracionProducto;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblPrecioProducto;
    private javax.swing.JLabel lblTitulo;
    public javax.swing.JTable tableProductos;
    private javax.swing.JTextField txtCostoProducto;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioProducto;
    // End of variables declaration//GEN-END:variables

}
