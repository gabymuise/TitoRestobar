package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Descuento;
import Programa.Model.Item;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import Programa.Model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMesa {
    private Connection conexion;

    // Constructor que establece la conexión con la base de datos
    public DAOMesa() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }

    // Lista todas las mesas
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

    // Crea una nueva mesa
    public void crearMesa(Mesa mesa) throws SQLException {
        String consulta = "INSERT INTO mesas (nombre) VALUES (?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, mesa.getNombre());
            comando.executeUpdate();
        }
    }

    // Elimina una mesa por nombre
    public void eliminarMesa(String nombre) throws SQLException {
        String consulta = "DELETE FROM mesas WHERE nombre = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, nombre);
            comando.executeUpdate();
        }
    }

    // Obtiene el pedido activo en una mesa específica
    public Pedido verPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        String consultaPedido = "SELECT p.id, p.fechaHoraApertura, p.descuento FROM pedidos p " +
                                "JOIN mesa_pedido mp ON p.id = mp.id_pedido " +
                                "WHERE mp.id_mesa = ? AND p.fechaHoraCierre IS NULL " +
                                "ORDER BY p.fechaHoraApertura DESC LIMIT 1";
        Pedido pedido = null;

        try (PreparedStatement psPedido = conexion.prepareStatement(consultaPedido)) {
            psPedido.setInt(1, mesa.getId());
            try (ResultSet rsPedido = psPedido.executeQuery()) {
                if (rsPedido.next()) {
                    int pedidoId = rsPedido.getInt("id");
                    java.util.Date fechaHoraApertura = new java.util.Date(rsPedido.getTimestamp("fechaHoraApertura").getTime());
                    List<Item> items = new ArrayList<>();
                    float porcentajeDescuento = rsPedido.getFloat("descuento");
                    Descuento descuento = new Descuento(porcentajeDescuento);

                    String consultaItems = "SELECT i.*, p.nombre, p.descripcion, p.precio, p.costo, p.elaborado " +
                                           "FROM items i " +
                                           "JOIN productos p ON i.id_producto = p.id " +
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

                    pedido = new Pedido(mesa, fechaHoraApertura, items, descuento);
                    pedido.setId(pedidoId);
                }
            }
        }

        return pedido;
    }

    // Elimina el pedido asociado a una mesa específica
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        String consulta = "DELETE FROM mesa_pedido WHERE id_mesa = ? AND id_pedido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
        }
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
