package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Producto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class DAOPedido {
    private Connection conexion;
    private Pedido pedido;

    public DAOPedido() throws SQLException {
        // Inicializa la conexión a la base de datos
        conexion = Conexion.Conectar();
    }

    // Método para crear un nuevo pedido
    public boolean crearPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (fechaHoraApertura, descuento) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, pedido.getFechaHoraApertura());
            stmt.setFloat(2, pedido.getDescuento().getPorcentaje());
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Retrieve the generated id
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setId(generatedKeys.getInt(1)); // Set the generated ID to the Pedido object
                        return true;
                    }
                }
            }
            return false;
        }
    }

    // Método para asignar una mesa a un pedido
    public boolean asignarMesaAPedido(int pedidoId, int mesaId) throws SQLException {
        String query = "INSERT INTO mesa_pedido (id_mesa, id_pedido) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, mesaId);
            ps.setInt(2, pedidoId);
            return ps.executeUpdate() > 0;
        }
    }

    // Método para agregar un item a un pedido
    public boolean agregarItemAPedido(int pedidoId, int productoId, int cantidad) throws SQLException {
        String query = "INSERT INTO items (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, pedidoId);
            ps.setInt(2, productoId);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        }
    }

    // Método para eliminar un item de un pedido
    public void eliminarItem(Pedido pedido, Item item) throws SQLException {
        String query = "DELETE FROM items WHERE id_pedido = ? AND id_producto = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, pedido.getId());
            ps.setInt(2, item.getProducto().getId());
            ps.executeUpdate();
        }
    }

    // Método para listar todos los pedidos de una mesa específica
    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        String query = "SELECT p.id, p.fechaHoraApertura, p.fechaHoraCierre, p.descuento " +
                       "FROM pedidos p " +
                       "JOIN mesa_pedido mp ON p.id = mp.id_pedido " +
                       "WHERE mp.id_mesa = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, mesa.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedido.setId(rs.getInt("id"));
                    pedido.setFechaHoraApertura(rs.getTimestamp("fechaHoraApertura"));
                    pedido.setFechaHoraCierre(rs.getTimestamp("fechaHoraCierre"));

                    // Convertir BigDecimal a float y crear Descuento
                    BigDecimal descuentoBD = rs.getBigDecimal("descuento");
                    if (descuentoBD != null) {
                        float porcentajeDescuento = descuentoBD.floatValue();
                        pedido.setDescuento(new Descuento(porcentajeDescuento));
                    } else {
                        // Asumir descuento 0 si es NULL
                        pedido.setDescuento(new Descuento(0));
                    }

                    // Aquí puedes cargar los items del pedido si es necesario
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }
}
