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
        String sql = "INSERT INTO pedidos (fechaHoraApertura, fechaHoraCierre, subtotal, total, descuento, idMesa) "
                + "VALUES (?, NULL, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, pedido.getFechaHoraApertura());
            stmt.setDouble(2, pedido.getSubtotal());  // Subtotal del pedido
            stmt.setDouble(3, pedido.getTotal());      // Total del pedido
            stmt.setFloat(4, pedido.getDescuento().getPorcentaje()); // Descuento aplicado
            stmt.setInt(5, pedido.getMesa().getId());  // ID de la mesa
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
    
    public List<Pedido> obtenerTodosLosPedidosActivos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT p.id, p.fechaHoraApertura, p.idMesa, p.descuento AS descuento "
                     + "FROM pedidos p "
                     + "WHERE p.fechaHoraCierre IS NULL "
                     + "ORDER BY p.fechaHoraApertura DESC";

        try (PreparedStatement stmt = conexion.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp fechaHoraApertura = rs.getTimestamp("fechaHoraApertura");
                int idMesa = rs.getInt("idMesa");
                float porcentajeDescuento = rs.getFloat("descuento");

                // Crear el objeto Pedido
                Pedido pedido = new Pedido();
                pedido.setId(id);
                pedido.setFechaHoraApertura(fechaHoraApertura);

                // Crear el objeto Mesa usando el ID y asignarlo al Pedido
                Mesa mesa = new Mesa();
                mesa.setId(idMesa); 
                pedido.setMesa(mesa); 

                // Obtener los items del pedido
                List<Item> items = obtenerItemsPorPedido(id);
                for (Item item : items) {
                    pedido.addItem(item); // Agregar los items al pedido
                }

                // Calcular el subtotal y el total
                pedido.getSubtotal(); 
                pedido.getTotal();

                // Configurar el descuento
                Descuento descuentoObj = new Descuento(porcentajeDescuento);
                pedido.setDescuento(descuentoObj);

                pedidos.add(pedido);
            }
        }
        return pedidos;
    }






    private Descuento obtenerDescuentoPorPedido(int pedidoId) throws SQLException {
        String query = "SELECT descuento FROM pedidos WHERE id = ?";
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
            e.printStackTrace();
            throw e;
        }
        return items;
    }


    public void insertarItem(Pedido pedido, Item item) throws SQLException {
        String sql = "INSERT INTO items (idPedido, idProducto, cantidad) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getId());
            stmt.setInt(2, item.getProducto().getId());
            stmt.setInt(3, item.getCantidad());

            stmt.executeUpdate();
        }
    }



    public void eliminarPedido(int idPedido) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        }
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
