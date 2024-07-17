package Programa.DAO;

import Programa.Conexion;
import Programa.Descuento;
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
    private Connection conexion;

    public DAOPedido() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crearPedido(Pedido pedido) throws SQLException {
        String consulta = "INSERT INTO pedidos (fechaHoraApertura, descuento) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setTimestamp(1, new java.sql.Timestamp(pedido.getFechaHoraApertura().getTime()));
            ps.setFloat(2, pedido.getDescuento().getPorcentaje());
            ps.executeUpdate();
        }
    }

    public void agregarItem(Pedido pedido, Item item) throws SQLException {
        String consulta = "INSERT INTO items (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.setInt(3, item.getCantidad());
            ps.executeUpdate();
        }
    }

    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        String consulta = "DELETE FROM items WHERE id_pedido = ? AND id_producto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.executeUpdate();
        }
    }

    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String consulta = "SELECT p.*, mp.id_mesa FROM pedidos p " +
                          "JOIN mesa_pedido mp ON p.id = mp.id_pedido " +
                          "WHERE mp.id_mesa = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int pedidoId = rs.getInt("id");
                    java.util.Date fechaHoraApertura = new java.util.Date(rs.getTimestamp("fechaHoraApertura").getTime());
                    float porcentajeDescuento = rs.getFloat("descuento");
                    Pedido pedido = new Pedido(fechaHoraApertura, new ArrayList<>(), new Descuento(porcentajeDescuento));
                    pedido.setId(pedidoId);
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
