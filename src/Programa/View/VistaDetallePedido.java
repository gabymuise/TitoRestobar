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
            cargarDatosTablaPedidosActivos();
            cargarDatosTablaPedidosCerrados();
            
            
            // Inicializar y programar el Timer para actualizaciones periódicas
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> cargarDatosTablaPedidosActivos());
                    SwingUtilities.invokeLater(()-> cargarDatosTablaPedidosCerrados());
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
    
    private void cargarDatosTablaPedidosActivos() {
        DefaultTableModel modelo = (DefaultTableModel) jTablePedidosActivos.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar datos

        try {
            // Consulta SQL para obtener los pedidos activos con sus detalles
            String sql = "SELECT m.nombre AS Mesa, p.fechaHoraApertura AS FechaHoraApertura, " +
                         "prod.nombre AS Producto, i.cantidad AS Cantidad, p.descuento AS Descuento, " +
                         "ROUND(p.total, 2) AS Total, p.id AS idPedido " + // Nota: Agregamos el id aquí
                         "FROM pedidos p " +
                         "JOIN mesas m ON p.idMesa = m.id " +
                         "JOIN items i ON p.id = i.idPedido " +
                         "JOIN productos prod ON i.idProducto = prod.id " +
                         "WHERE p.fechaHoraCierre IS NULL";

            // Usar la conexión desde la clase VistaDetallePedido
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Itera a través de los resultados y agrega cada registro a la tabla
            while (resultSet.next()) {
                String nombreMesa = resultSet.getString("Mesa");
                Timestamp fechaHoraApertura = resultSet.getTimestamp("FechaHoraApertura");
                String producto = resultSet.getString("Producto");
                int cantidad = resultSet.getInt("Cantidad");
                float descuento = resultSet.getFloat("Descuento");
                float total = resultSet.getFloat("Total");
                int id = resultSet.getInt("idPedido");

                modelo.addRow(new Object[]{nombreMesa, fechaHoraApertura, producto, cantidad, descuento, total, id});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de los pedidos activos: " + 
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarDatosTablaPedidosCerrados() {
        DefaultTableModel modelo = (DefaultTableModel) jTableDetallePedido.getModel();
        modelo.setRowCount(0); // Limpiar tabla antes de cargar datos

        String sql = "SELECT m.nombre AS mesa, p.fechaHoraApertura, p.fechaHoraCierre, " +
                     "prod.nombre AS producto, i.cantidad, p.descuento, p.total, p.id AS idPedido " + 
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
                int id = rs.getInt("idPedido");

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
                    totalFormateado,
                    id
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de los pedidos cerrados: " + 
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDetallePedido = new javax.swing.JTable();
        lblPedidosActivos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePedidosActivos = new javax.swing.JTable();
        lblPedidosCerrados = new javax.swing.JLabel();

        jTableDetallePedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mesa", "FechaHoraA", "FechaHoraC", "Producto", "Cantidad", "Descuento", "Total", "idPedido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            jTableDetallePedido.getColumnModel().getColumn(7).setResizable(false);
        }

        lblPedidosActivos.setText("PEDIDOS ACTIVOS");

        jTablePedidosActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mesa", "FechaHoraA", "Producto", "Cantidad", "Descuento", "Total", "idPedido"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTablePedidosActivos);
        if (jTablePedidosActivos.getColumnModel().getColumnCount() > 0) {
            jTablePedidosActivos.getColumnModel().getColumn(0).setResizable(false);
            jTablePedidosActivos.getColumnModel().getColumn(1).setResizable(false);
            jTablePedidosActivos.getColumnModel().getColumn(2).setResizable(false);
            jTablePedidosActivos.getColumnModel().getColumn(3).setResizable(false);
            jTablePedidosActivos.getColumnModel().getColumn(4).setResizable(false);
            jTablePedidosActivos.getColumnModel().getColumn(5).setResizable(false);
            jTablePedidosActivos.getColumnModel().getColumn(6).setResizable(false);
        }

        lblPedidosCerrados.setText("PEDIDOS CERRADOS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(398, 398, 398)
                            .addComponent(lblPedidosActivos))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(398, 398, 398)
                        .addComponent(lblPedidosCerrados)))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblPedidosActivos)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(lblPedidosCerrados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableDetallePedido;
    private javax.swing.JTable jTablePedidosActivos;
    private javax.swing.JLabel lblPedidosActivos;
    private javax.swing.JLabel lblPedidosCerrados;
    // End of variables declaration//GEN-END:variables
}
