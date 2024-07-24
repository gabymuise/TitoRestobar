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

    public Pedido obtenerPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        String query = "SELECT P.id, P.fechaHoraApertura, P.fechaHoraCierre "
                     + "FROM Pedidos P "
                     + "JOIN Mesa_Pedido MP ON P.id = MP.idPedido "
                     + "WHERE MP.idMesa = ? AND P.fechaHoraCierre IS NULL";

        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, mesa.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Timestamp fechaHoraApertura = resultSet.getTimestamp("fechaHoraApertura");
                Timestamp fechaHoraCierre = resultSet.getTimestamp("fechaHoraCierre");

                return new Pedido(id, fechaHoraApertura, fechaHoraCierre);
            } else {
                return null; // No hay pedido activo
            }
        }
    }
    
    // Método para listar todos los pedidos de una mesa específica
    public List<Pedido> listarPedidosDeMesa(Mesa mesa) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT P.id, P.fechaHoraApertura, P.fechaHoraCierre "
                     + "FROM Pedidos P "
                     + "JOIN Mesa_Pedido MP ON P.id = MP.idPedido "
                     + "WHERE MP.idMesa = ?";

        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, mesa.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Timestamp fechaHoraApertura = resultSet.getTimestamp("fechaHoraApertura");
                Timestamp fechaHoraCierre = resultSet.getTimestamp("fechaHoraCierre");

                // Crear y agregar el objeto Pedido a la lista
                Pedido pedido = new Pedido(id, fechaHoraApertura, fechaHoraCierre);
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }
    
    // Método para verificar todos los pedidos de una mesa específica
    public void verificarPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        String query = "SELECT P.id " +
                       "FROM Pedidos P " +
                       "INNER JOIN Mesa_Pedido MP ON P.id = MP.idPedido " +
                       "WHERE MP.idMesa = ? " +
                       "AND P.fechaHoraCierre IS NULL"; // Suponiendo que fechaHoraCierre es NULL para pedidos activos

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, mesa.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idPedido = rs.getInt("id");
                    // Lanza una excepción con el ID del pedido activo
                    throw new SQLException("La mesa con ID " + mesa.getId() + " tiene un pedido activo con ID: " + idPedido);
                }
            }
        } catch (SQLException e) {
            // Manejo de excepciones para identificar posibles problemas
            throw new SQLException("Error al verificar si la mesa tiene un pedido activo: " + e.getMessage(), e);
        }
    }
}
