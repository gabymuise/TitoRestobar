package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Pedido;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Producto;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPedido {
    private Connection conexion;
    private static final Logger logger = Logger.getLogger(DAOPedido.class.getName());

    public DAOPedido() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database", e);
        }
    }

    // Crea un nuevo pedido
  public void crearPedido(Pedido pedido) throws SQLException {
    String sql = "INSERT INTO pedidos (fechaHoraApertura, fechaHoraCierre, subtotal, total, descuento, idMesa) "
               + "VALUES (?, NULL, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        stmt.setTimestamp(1, pedido.getFechaHoraApertura());
        stmt.setDouble(2, pedido.getSubtotal());
        stmt.setDouble(3, pedido.getTotal());
        stmt.setFloat(4, pedido.getDescuento().getPorcentaje());
        stmt.setInt(5, pedido.getMesa().getId());

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getInt(1));
                }
            }
        } else {
            throw new SQLException("No se pudo crear el pedido.");
        }
    }
}

    public List<Pedido> obtenerTodosLosPedidosActivos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT p.id, p.fechaHoraApertura, p.idMesa, p.descuento AS descuento "
                     + "FROM pedidos p "
                     + "WHERE p.fechaHoraCierre IS NULL "
                     + "ORDER BY p.fechaHoraApertura DESC";

        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = mapPedido(rs);
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    private Pedido mapPedido(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Timestamp fechaHoraApertura = rs.getTimestamp("fechaHoraApertura");
        int idMesa = rs.getInt("idMesa");
        float porcentajeDescuento = rs.getFloat("descuento");

        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setFechaHoraApertura(fechaHoraApertura);
        pedido.setMesa(obtenerMesaPorId(idMesa));
        pedido.setItems(obtenerItemsPorPedido(id));
        pedido.setDescuento(new Descuento(porcentajeDescuento));

        return pedido;
    }

    public Mesa obtenerMesaPorId(int idMesa) throws SQLException {
        String query = "SELECT nombre FROM mesas WHERE id = ?";
        Mesa mesa = null;
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idMesa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    mesa = new Mesa(idMesa, nombre);
                }
            }
        }
        return mesa;
    }

    public List<Item> obtenerItemsPorPedido(int pedidoId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String consulta = "SELECT i.id AS idItem, i.idProducto, i.cantidad, p.nombre, p.descripcion, p.precio, p.costo, p.elaborado "
                         + "FROM items i "
                         + "JOIN productos p ON i.idProducto = p.id "
                         + "WHERE i.idPedido = ?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedidoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapItem(rs));
                }
            }
        }
        return items;
    }

    private Item mapItem(ResultSet rs) throws SQLException {
        int idProducto = rs.getInt("idProducto");
        int cantidad = rs.getInt("cantidad");

        Producto producto = new Producto(
            rs.getString("nombre"),
            rs.getString("descripcion"),
            rs.getFloat("precio"),
            rs.getFloat("costo"),
            rs.getBoolean("elaborado")
        );
        producto.setId(idProducto);

        return new Item(producto, cantidad);
    }

    // Método para insertar un item en un pedido
    public void insertarItem(Pedido pedido, Item item) throws SQLException {
        String query = "INSERT INTO items (idPedido, idProducto, cantidad, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getId());
            stmt.setInt(2, item.getProducto().getId());
            stmt.setInt(3, item.getCantidad());
            stmt.setDouble(4, item.getProducto().getPrecio());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                }
            }
        }
    }


    public void eliminarPedido(int idPedido) throws SQLException {
            String sql = "DELETE FROM pedidos WHERE id = ?";
            try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
                // Verificar si el pedido existe antes de intentar eliminarlo
                if (!pedidoExiste(idPedido)) {
                    throw new SQLException("El pedido con ID " + idPedido + " no existe.");
                }

                // Configurar el PreparedStatement
                stmt.setInt(1, idPedido);

                // Ejecutar la actualización
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("No se pudo eliminar el pedido con ID " + idPedido + ". Puede que ya haya sido eliminado.");
                }
            } catch (SQLException e) {
                throw new SQLException("Error al eliminar el pedido: " + e.getMessage(), e);
            }
        }

    
    public Pedido verPedidoActivoDeMesa(Mesa mesa) throws SQLException {
        String query = "SELECT p.id, p.idMesa, p.fechaHoraCierre " +
                       "FROM pedidos p " +
                       "WHERE p.idMesa = ? " +
                       "AND p.fechaHoraCierre IS NULL";

        try (PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, mesa.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Timestamp fechaHoraCierre = resultSet.getTimestamp("fechaHoraCierre");

                    return new Pedido(id, mesa, fechaHoraCierre);
                } else {
                    return null; // No hay pedido activo
                }
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al obtener el pedido activo de la mesa: " + ex.getMessage(), ex);
        }
    }


    // Método auxiliar para verificar si un pedido existe
    private boolean pedidoExiste(int idPedido) throws SQLException {
        String query = "SELECT 1 FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true si el pedido existe
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al verificar si el pedido existe: " + ex.getMessage(), ex);
        }
    }
    
    public void cerrarPedido(int idPedido) throws SQLException {
        String sql = "UPDATE pedidos SET fechaHoraCierre = NOW() WHERE id = ? AND fechaHoraCierre IS NULL";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Configurar el PreparedStatement
            stmt.setInt(1, idPedido);

            // Ejecutar la actualización
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se pudo cerrar el pedido con ID " + idPedido + ". Puede que ya haya sido cerrado.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error al cerrar el pedido: " + e.getMessage(), e);
        }
    }
    
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing connection", e);
        }
    }
}
