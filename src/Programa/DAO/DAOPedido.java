package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Pedido;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Producto;
import java.sql.Timestamp;
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
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }

    // Crea un nuevo pedido
    public void crearPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (fechaHoraApertura, fechaHoraCierre) VALUES (?, NULL)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, pedido.getFechaHoraApertura());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setId(generatedKeys.getInt(1)); // Set the generated ID to the Pedido object
                    }
                }
            } else {
                throw new SQLException("No se pudo crear el pedido.");
            }
        }
    }
    
    public void insertarDetallePedido(Pedido pedido, Item item) throws SQLException {
        String sql = "INSERT INTO detalle_pedido (idPedido, subtotal, total, descuento) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            stmt.setDouble(2, item.getSubtotal()); // Suponiendo que getSubtotal devuelve Double
            stmt.setDouble(3, pedido.getTotal());    // Suponiendo que getTotal devuelve Double
            stmt.setFloat(4, pedido.getDescuento().getPorcentaje()); // Suponiendo que el descuento es float

            stmt.executeUpdate();
        }
    }
    
    public List<Pedido> obtenerTodosLosPedidosActivos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT p.id, p.fechaHoraApertura, m.nombre AS nombre_mesa "
                     + "FROM pedidos p "
                     + "JOIN mesa_pedido mp ON p.id = mp.idPedido "
                     + "JOIN mesas m ON mp.idMesa = m.id "
                     + "WHERE p.fechaHoraCierre IS NULL "
                     + "ORDER BY p.fechaHoraApertura DESC";

        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp fechaHoraApertura = rs.getTimestamp("fechaHoraApertura");
                String nombreMesa = rs.getString("nombre_mesa");

                Pedido pedido = new Pedido();
                pedido.setId(id);
                pedido.setFechaHoraApertura(fechaHoraApertura);
                pedido.setMesa(new Mesa(nombreMesa));

                Descuento descuento = obtenerDescuentoPorPedido(id);
                pedido.setDescuento(descuento);

                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    private Descuento obtenerDescuentoPorPedido(int pedidoId) throws SQLException {
        String query = "SELECT descuento FROM detalle_pedido WHERE idPedido = ?";
        Descuento descuento = new Descuento(0.0f);

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, pedidoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    float porcentajeDescuento = rs.getFloat("descuento");
                    descuento.setPorcentaje(porcentajeDescuento);
                }
            }
        }
        return descuento;
    }

    public Pedido obtenerPedido(int pedidoId) throws SQLException {
        Pedido pedido = null;
        String consulta = "SELECT fechaHoraApertura, fechaHoraCierre FROM pedidos WHERE id = ?";

        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, pedidoId);
            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    pedido = new Pedido();
                    pedido.setId(pedidoId);
                    pedido.setFechaHoraApertura(rs.getTimestamp("fechaHoraApertura"));
                    pedido.setFechaHoraCierre(rs.getTimestamp("fechaHoraCierre"));
                }
            }
        }
        return pedido;
    }

    public List<Item> obtenerItemsPorPedido(int pedidoId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String consulta = "SELECT i.id, i.idProducto, i.cantidad, p.nombre, p.descripcion, p.precio, p.costo, p.elaborado " +
                           "FROM items i " +
                           "JOIN productos p ON i.idProducto = p.id " +
                           "WHERE i.id_pedido = ?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, pedidoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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

                    Item item = new Item(producto, cantidad);
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging the exception
            throw e; // Re-throwing the exception after logging it
        }
        return items;
    }

    public void insertarItem(Pedido pedido, Item item) throws SQLException {
        String sql = "INSERT INTO items (idProducto, cantidad) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, item.getProducto().getId());
            stmt.setInt(2, item.getCantidad());

            stmt.executeUpdate();
        }
    }

    public void insertarMesaPedido(Mesa mesa, Pedido pedido) throws SQLException {
        String sql = "INSERT INTO mesa_pedido (idMesa, idPedido) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, mesa.getId());
            stmt.setInt(2, pedido.getId());

            stmt.executeUpdate();
        }
    }

    public void eliminarPedido(int pedidoId) throws SQLException {
        String consulta = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, pedidoId);
            comando.executeUpdate();
        }
    }

    public void eliminarPedidoDeMesa(int mesaId, int pedidoId) throws SQLException {
        String consulta = "DELETE FROM mesa_pedido WHERE idMesa = ? AND idPedido = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, mesaId);
            comando.setInt(2, pedidoId);
            comando.executeUpdate();
        }
    }

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
