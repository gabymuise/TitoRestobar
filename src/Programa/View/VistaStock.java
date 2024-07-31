package Programa.View;

import Programa.Model.Conexion;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class VistaStock extends javax.swing.JPanel {

  public VistaStock() {
        initComponents();
        cargarDatosStock();
    }

    private void cargarDatosStock() {
        DefaultTableModel modelo = (DefaultTableModel) tablaStock.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar datos

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.Conectar();

            String sql = "SELECT productos.id, productos.nombre, stock.cantidad " +
                         "FROM stock " +
                         "JOIN productos ON stock.id_producto = productos.id";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Itera a través de los resultados y agrega cada registro a la tabla
            while (rs.next()) {
                int idProducto = rs.getInt("id");
                String nombreProducto = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                modelo.addRow(new Object[]{idProducto, nombreProducto, cantidad});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaStock = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
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
                        .addComponent(jButtonModificarStock, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGap(30, 30, 30)
                        .addComponent(jButtonModificarStock)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

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
                        cargarDatosStock();
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
    private javax.swing.JButton jButtonModificarStock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaStock;
    // End of variables declaration//GEN-END:variables
}
