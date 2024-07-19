package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPedido {
    private Connection conexion;

    // Constructor que establece la conexión con la base de datos
    public DAOPedido() throws SQLException {
        conexion = Conexion.Conectar();
    }

    // Crea un nuevo pedido y establece su ID en el objeto Pedido
    public void crearPedido(Pedido pedido) throws SQLException {
        String consulta = "INSERT INTO pedidos (fechaHoraApertura, descuento) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, new java.sql.Timestamp(pedido.getFechaHoraApertura().getTime()));
            ps.setFloat(2, pedido.getDescuento().getPorcentaje());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    pedido.setId(id);
                }
            }
        }
    }

    // Agrega un ítem a un pedido y asocia el pedido con una mesa
    public void agregarItem(Pedido pedido, Item item) throws SQLException {
        String consultaItem = "INSERT INTO items (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consultaItem)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.setInt(3, item.getCantidad());
            ps.executeUpdate();
        }

        String consultaMesaPedido = "INSERT INTO mesa_pedido (id_mesa, id_pedido) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(consultaMesaPedido)) {
            ps.setInt(1, pedido.getMesa().getId());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
        }
    }

    // Elimina un ítem de un pedido
    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        String consulta = "DELETE FROM items WHERE id_pedido = ? AND id_producto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.executeUpdate();
        }
    }

    // Lista todos los pedidos asociados a una mesa
    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String consulta = "SELECT p.id, p.fechaHoraApertura, p.descuento " +
                          "FROM pedidos p " +
                          "JOIN mesa_pedido mp ON p.id = mp.id_pedido " +
                          "WHERE mp.id_mesa = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int pedidoId = rs.getInt("id");
                    java.util.Date fechaHoraApertura = new java.util.Date(rs.getTimestamp("fechaHoraApertura").getTime());
                    float porcentajeDescuento = rs.getFloat("descuento");
                    Pedido pedido = new Pedido(mesa, fechaHoraApertura, new ArrayList<>(), new Descuento(porcentajeDescuento));
                    pedido.setId(pedidoId);
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }

    // Cierra la conexión con la base de datos
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }
}
