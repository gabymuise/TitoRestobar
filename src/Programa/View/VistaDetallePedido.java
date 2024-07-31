package Programa.View;

import Programa.Model.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class VistaDetallePedido extends javax.swing.JPanel {

    private Connection conexion;
    private Timer timer;
    
    public VistaDetallePedido() {
        initComponents();
        try {
            // Establecer conexión a la base de datos
            conexion = Conexion.Conectar();
            
            // Cargar datos iniciales en la tabla
            cargarDatosTabla();
            
            // Inicializar y programar el Timer para actualizaciones periódicas
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> cargarDatosTabla());
                }
            }, 0, 30000); // Actualizar cada 30 segundos
        } catch (SQLException e) {
            try {
                JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + 
                        e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw e;
            } catch (SQLException ex) {
                Logger.getLogger(VistaDetallePedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void cargarDatosTabla() {
        DefaultTableModel modelo = (DefaultTableModel) jTableDetallePedido.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar datos

        String sql = "SELECT m.nombre AS mesa, p.fechaHoraApertura, p.fechaHoraCierre, " +
                     "prod.nombre AS producto, i.cantidad, p.descuento, p.total " +
                     "FROM pedidos p " +
                     "JOIN mesas m ON p.idMesa = m.id " +
                     "JOIN items i ON p.id = i.idPedido " +
                     "JOIN productos prod ON i.idProducto = prod.id " +
                     "WHERE p.fechaHoraCierre IS NOT NULL"; // Pedidos cerrados

        DecimalFormat formatoDecimal = new DecimalFormat("#.00");

        try (Connection conn = Conexion.Conectar(); // Usando tu clase de conexión
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombreMesa = rs.getString("mesa");
                Timestamp fechaHoraApertura = rs.getTimestamp("fechaHoraApertura");
                Timestamp fechaHoraCierre = rs.getTimestamp("fechaHoraCierre");
                String nombreProducto = rs.getString("producto");
                int cantidad = rs.getInt("cantidad");
                float descuento = rs.getFloat("descuento");
                double total = rs.getDouble("total");

                // Formatear el total a dos decimales
                String totalFormateado = formatoDecimal.format(total);

                // Añadir una fila a la tabla
                modelo.addRow(new Object[]{
                    nombreMesa,
                    fechaHoraApertura,
                    fechaHoraCierre,
                    nombreProducto,
                    cantidad,
                    descuento,
                    totalFormateado
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDetallePedido = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        jTableDetallePedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mesa", "FechaHoraA", "FechaHoraC", "Producto", "Cantidad", "Descuento", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableDetallePedido);
        if (jTableDetallePedido.getColumnModel().getColumnCount() > 0) {
            jTableDetallePedido.getColumnModel().getColumn(0).setResizable(false);
            jTableDetallePedido.getColumnModel().getColumn(1).setResizable(false);
            jTableDetallePedido.getColumnModel().getColumn(2).setResizable(false);
            jTableDetallePedido.getColumnModel().getColumn(3).setResizable(false);
            jTableDetallePedido.getColumnModel().getColumn(4).setResizable(false);
            jTableDetallePedido.getColumnModel().getColumn(5).setResizable(false);
            jTableDetallePedido.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel1.setText("PEDIDOS CERRADOS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(302, 302, 302)
                        .addComponent(jLabel1)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDetallePedido;
    // End of variables declaration//GEN-END:variables
}
