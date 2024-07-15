package Programa.DAO;

import Programa.Pedido;
import Programa.Item;
import Programa.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPedido {
    private final Connection conexion;

    public DAOPedido(Connection conexion) {
        this.conexion = conexion;
    }

    public void crearPedido(Pedido pedido) throws SQLException {
        String consulta = "INSERT INTO pedidos (mesa_id, fecha_hora_apertura) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedido.getId());
            ps.setTimestamp(2, new java.sql.Timestamp(pedido.getFechaHoraApertura().getTime()));
            ps.executeUpdate();
        }
    }

    public void agregarItem(Pedido pedido, Item item) throws SQLException {
        String consulta = "INSERT INTO items_pedido (pedido_id, producto_id, cantidad, precio_total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.setInt(3, item.getCantidad());
            ps.setFloat(4, item.getPrecio());
            ps.executeUpdate();
        }
    }

    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        String consulta = "DELETE FROM items_pedido WHERE pedido_id = ? AND producto_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.executeUpdate();
        }
    }

    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String consulta = "SELECT * FROM pedidos WHERE mesa_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int pedidoId = rs.getInt("id");
                    java.util.Date fechaHoraApertura = new java.util.Date(rs.getTimestamp("fecha_hora_apertura").getTime());
                    Pedido pedido = new Pedido(fechaHoraApertura, new ArrayList<>());
                    pedido.setId(pedidoId);
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }
}
