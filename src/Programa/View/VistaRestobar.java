package Programa.View;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class VistaRestobar extends javax.swing.JFrame {

    public VistaRestobar() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        btnVistaMesa = new javax.swing.JButton();
        btnVistaProducto = new javax.swing.JButton();
        content = new javax.swing.JScrollPane();
        btnVistaPedido = new javax.swing.JButton();
        btnVistaStock = new javax.swing.JButton();
        btnDetallePedido = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnVistaMesa.setText("MESA");
        btnVistaMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVistaMesaActionPerformed(evt);
            }
        });

        btnVistaProducto.setText("PRODUCTO");
        btnVistaProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVistaProductoActionPerformed(evt);
            }
        });

        btnVistaPedido.setText("PEDIDO");
        btnVistaPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVistaPedidoActionPerformed(evt);
            }
        });

        btnVistaStock.setText("STOCK");
        btnVistaStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVistaStockActionPerformed(evt);
            }
        });

        btnDetallePedido.setText("DETALLE PEDIDO");
        btnDetallePedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallePedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(btnVistaMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVistaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVistaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVistaStock, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDetallePedido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(content)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnVistaProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVistaMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnVistaPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVistaStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDetallePedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVistaMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVistaMesaActionPerformed
       try {
            VistaMesa mesa = new VistaMesa();
            ShowPanel(mesa); 
        } catch (SQLException ex) {
            Logger.getLogger(VistaRestobar.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }//GEN-LAST:event_btnVistaMesaActionPerformed

    private void btnVistaProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVistaProductoActionPerformed
            VistaProducto producto= new VistaProducto();
            ShowPanel(producto);
    }//GEN-LAST:event_btnVistaProductoActionPerformed

    private void btnVistaPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVistaPedidoActionPerformed
        try {
            VistaPedido pedido = new VistaPedido();
            ShowPanel(pedido);
        } catch (SQLException ex) {
            Logger.getLogger(VistaRestobar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVistaPedidoActionPerformed

    private void btnVistaStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVistaStockActionPerformed
        VistaStock stock = new VistaStock();
        ShowPanel(stock);
    }//GEN-LAST:event_btnVistaStockActionPerformed

    private void btnDetallePedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallePedidoActionPerformed
        VistaDetallePedido detallePedido = new VistaDetallePedido();
        ShowPanel(detallePedido);
    }//GEN-LAST:event_btnDetallePedidoActionPerformed

    public void ShowPanel(JPanel p){
        
         p.setSize(content.getSize());
        p.setLocation(0, 0);

        content.setViewportView(p);
        content.revalidate();
        content.repaint(); 
        
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new VistaRestobar().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetallePedido;
    private javax.swing.JButton btnVistaMesa;
    private javax.swing.JButton btnVistaPedido;
    private javax.swing.JButton btnVistaProducto;
    private javax.swing.JButton btnVistaStock;
    private javax.swing.JScrollPane content;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
