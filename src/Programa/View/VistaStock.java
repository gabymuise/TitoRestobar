package Programa.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;

public class VistaStock extends javax.swing.JPanel {

  public VistaStock() {
        initComponents();
        cargarDatosStock();
    }

    private void cargarDatosStock() {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar datos

        try {
            // Conecta a la base de datos (asegúrate de tener el controlador JDBC cargado)
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/titorestobar", "root", "123456");

            // Consulta SQL para obtener los datos del stock junto con el nombre del producto
            String sql = "SELECT productos.id, productos.nombre, stock.cantidad " +
                         "FROM stock " +
                         "JOIN productos ON stock.id_producto = productos.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Itera a través de los resultados y agrega cada registro a la tabla
            while (rs.next()) {
                int idProducto = rs.getInt("id");
                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                modelo.addRow(new Object[]{idProducto, nombreProducto, cantidad});
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaStock = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButtonEliminarStock = new javax.swing.JButton();
        jButtonModificarStock = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(800, 500));

        tablaStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IdProducto", "Nombre", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(tablaStock);

        jLabel1.setText("PRODUCTO NO ELABORADO");

        jButtonEliminarStock.setText("ELIMINAR");
        jButtonEliminarStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarStockActionPerformed(evt);
            }
        });

        jButtonModificarStock.setText("MODIFICAR");
        jButtonModificarStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonEliminarStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonModificarStock, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEliminarStock)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonModificarStock)))
                .addContainerGap(183, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEliminarStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarStockActionPerformed
    int filaSeleccionada = tablaStock.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        int idProducto = (int) tablaStock.getValueAt(filaSeleccionada, 0);

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/titorestobar", "root", "123456")) {
            String sql = "DELETE FROM stock WHERE id_producto = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idProducto);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Producto eliminado del stock correctamente.", "Eliminar Producto", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosStock(); // Actualizar la tabla después de eliminar
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_jButtonEliminarStockActionPerformed

    private void jButtonModificarStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarStockActionPerformed
         int filaSeleccionada = tablaStock.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        int idProducto = (int) tablaStock.getValueAt(filaSeleccionada, 0);
        String nombreProducto = (String) tablaStock.getValueAt(filaSeleccionada, 1);
        int cantidad = (int) tablaStock.getValueAt(filaSeleccionada, 2);

        String nuevaCantidadStr = JOptionPane.showInputDialog(this, "Ingrese la nueva cantidad para " + nombreProducto + ":");
        if (nuevaCantidadStr != null && !nuevaCantidadStr.isEmpty()) {
            try {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                if (nuevaCantidad < 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/titorestobar", "root", "123456")) {
                        String sql = "UPDATE stock SET cantidad = ? WHERE id_producto = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, nuevaCantidad);
                        pstmt.setInt(2, idProducto);
                        pstmt.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Cantidad modificada correctamente.", "Modificar Cantidad", JOptionPane.INFORMATION_MESSAGE);
                        cargarDatosStock(); // Actualizar la tabla después de modificar
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al modificar cantidad: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    }//GEN-LAST:event_jButtonModificarStockActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonEliminarStock;
    private javax.swing.JButton jButtonModificarStock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaStock;
    // End of variables declaration//GEN-END:variables
}