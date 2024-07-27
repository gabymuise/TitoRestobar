package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Producto;
import Resources.PedidoNoActivoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// Interfaz que define las operaciones básicas para una entidad Mesa
interface IMesaDAO {
    List<Mesa> listarMesas() throws SQLException;
    void crearMesa(Mesa mesa) throws SQLException;
    void eliminarMesa(String nombre) throws SQLException;
    void modificarMesa(String nombreActual, String nuevoNombre) throws SQLException;
    Mesa obtenerMesaPorNombre(String nombre) throws SQLException;
    Mesa obtenerMesaPorId(int id) throws SQLException;
    Pedido verPedidoActivoEnMesa(Mesa mesa) throws SQLException, PedidoNoActivoException;
    void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException;
}

// Implementación concreta de IMesaDAO
public class DAOMesa implements IMesaDAO {
    private Connection conexion;

    public DAOMesa() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }

    @Override
    public List<Mesa> listarMesas() throws SQLException {
        List<Mesa> lista = new ArrayList<>();
        String consulta = "SELECT * FROM mesas";

        try (PreparedStatement comando = conexion.prepareStatement(consulta);
             ResultSet lectura = comando.executeQuery()) {
            while (lectura.next()) {
                int id = lectura.getInt("id");
                String nombre = lectura.getString("nombre");

                Mesa mesa = new Mesa(nombre);
                mesa.setId(id);
                lista.add(mesa);
            }
        }
        return lista;
    }

    @Override
    public void crearMesa(Mesa mesa) throws SQLException {
        String consulta = "INSERT INTO mesas (nombre) VALUES (?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, mesa.getNombre());
            comando.executeUpdate();
        }
    }

    @Override
    public void eliminarMesa(String nombre) throws SQLException {
        String consulta = "DELETE FROM mesas WHERE nombre = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, nombre);
            int filasAfectadas = comando.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("La mesa no se pudo eliminar porque no existe.");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                throw new SQLException("No se puede eliminar la mesa porque tiene pedidos asociados.");
            } else {
                throw e;
            }
        }
    }

    @Override
    public void modificarMesa(String nombreActual, String nuevoNombre) throws SQLException {
        String consulta = "UPDATE mesas SET nombre = ? WHERE nombre = ?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, nuevoNombre);
            ps.setString(2, nombreActual);
            ps.executeUpdate();
        }
    }

    @Override
    public Mesa obtenerMesaPorNombre(String nombre) throws SQLException {
        Mesa mesa = null;
        String query = "SELECT * FROM mesas WHERE nombre = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nombreMesa = rs.getString("nombre");
                    mesa = new Mesa(id, nombreMesa);
                }
            }
        }
        return mesa;
    }

    @Override
    public Mesa obtenerMesaPorId(int id) throws SQLException {
        Mesa mesa = null;
        String query = "SELECT * FROM mesas WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombreMesa = rs.getString("nombre");
                    mesa = new Mesa(id, nombreMesa);
                }
            } catch (SQLException e) {
                throw new SQLException("Error al obtener la mesa por ID: " + e.getMessage(), e);
            }
        }
        return mesa;
    }

    @Override
    public Pedido verPedidoActivoEnMesa(Mesa mesa) throws SQLException, PedidoNoActivoException {
        String consultaPedido = "SELECT p.id, p.fechaHoraApertura, d.descuento " +
                                "FROM pedidos p " +
                                "JOIN mesa_pedido mp ON p.id = mp.idPedido " +
                                "JOIN detalle_pedido d ON p.id = d.idPedido " +
                                "WHERE mp.idMesa = ? AND p.fechaHoraCierre IS NULL " +
                                "ORDER BY p.fechaHoraApertura DESC LIMIT 1";

        Pedido pedido = null;

        try (PreparedStatement psPedido = conexion.prepareStatement(consultaPedido)) {
            psPedido.setInt(1, mesa.getId());
            try (ResultSet rsPedido = psPedido.executeQuery()) {
                if (rsPedido.next()) {
                    int pedidoId = rsPedido.getInt("id");
                    Timestamp fechaHoraApertura = rsPedido.getTimestamp("fechaHoraApertura");
                    float porcentajeDescuento = rsPedido.getFloat("descuento");
                    Descuento descuento = new Descuento(porcentajeDescuento);

                    List<Item> items = obtenerItemsDelPedido(pedidoId);

                    pedido = new Pedido(mesa, fechaHoraApertura, items, descuento);
                    pedido.setId(pedidoId);
                } else {
                    throw new PedidoNoActivoException("No hay un pedido activo para la mesa seleccionada.");
                }
            }
        }

        return pedido;
    }

    private List<Item> obtenerItemsDelPedido(int pedidoId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String consultaItems = "SELECT i.*, p.nombre, p.descripcion, p.precio, p.costo, p.elaborado " +
                               "FROM items i " +
                               "JOIN productos p ON i.idProducto = p.id " +
                               "WHERE i.id_pedido = ?";

        try (PreparedStatement psItems = conexion.prepareStatement(consultaItems)) {
            psItems.setInt(1, pedidoId);
            try (ResultSet rsItems = psItems.executeQuery()) {
                while (rsItems.next()) {
                    String nombre = rsItems.getString("nombre");
                    String descripcion = rsItems.getString("descripcion");
                    float precio = rsItems.getFloat("precio");
                    float costo = rsItems.getFloat("costo");
                    boolean elaborado = rsItems.getBoolean("elaborado");

                    Producto producto = new Producto(nombre, descripcion, precio, costo, elaborado);
                    int cantidad = rsItems.getInt("cantidad");

                    Item item = new Item(producto, cantidad);
                    items.add(item);
                }
            }
        }
        return items;
    }

    @Override
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        String consulta = "DELETE FROM mesa_pedido WHERE idMesa = ? AND idPedido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
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
