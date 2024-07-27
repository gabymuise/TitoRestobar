package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Item;
import Programa.Model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOItem {
    private Connection conexion;

    public DAOItem() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }

    // Obtiene los items de un pedido
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
        }
        return items;
    }

    // Agrega un nuevo item a un pedido
    public boolean agregarItem(Item item, int pedidoId) throws SQLException {
        String consulta = "INSERT INTO items (id, idProducto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, pedidoId);
            comando.setInt(2, item.getProducto().getId());
            comando.setInt(3, item.getCantidad());
            return comando.executeUpdate() > 0;
        }
    }

    // Elimina un item de un pedido
    public void eliminarItem(int pedidoId, int productoId) throws SQLException {
        String consulta = "DELETE FROM items WHERE id_pedido = ? AND idProducto = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, pedidoId);
            comando.setInt(2, productoId);
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

