package Programa.DAO;

import Programa.Conexion;
import Programa.Item;
import Programa.Mesa;
import Programa.Pedido;
import Programa.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMesa {
    private Connection conexion;

    public DAOMesa() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void crearMesa(Mesa mesa) throws SQLException {
        String consulta = "INSERT INTO mesas (nombre) VALUES (?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, mesa.getNombre());
            comando.executeUpdate();
        }
    }

    public void eliminarMesa(String nombre) throws SQLException {
        String consulta = "DELETE FROM mesas WHERE nombre = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, nombre);
            comando.executeUpdate();
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

    public Pedido verPedidoActivoEnMesa(Mesa mesa) throws SQLException {
        String consultaPedido = "SELECT p.* FROM pedidos p " +
                                "JOIN mesa_pedido mp ON p.id = mp.id_pedido " +
                                "WHERE mp.id_mesa = ? ORDER BY p.fecha_hora_apertura DESC LIMIT 1";
        Pedido pedido = null;

        try (PreparedStatement psPedido = conexion.prepareStatement(consultaPedido)) {
            psPedido.setInt(1, mesa.getId());
            try (ResultSet rsPedido = psPedido.executeQuery()) {
                if (rsPedido.next()) {
                    int pedidoId = rsPedido.getInt("id");
                    java.util.Date fechaHoraApertura = new java.util.Date(rsPedido.getTimestamp("fecha_hora_apertura").getTime());
                    List<Item> items = new ArrayList<>();

                    String consultaItems = "SELECT ip.*, p.nombre, p.descripcion, p.precio, p.costo, p.elaboracion " +
                                           "FROM items_pedido ip " +
                                           "JOIN productos p ON ip.producto_id = p.id " +
                                           "WHERE ip.pedido_id = ?";

                    try (PreparedStatement psItems = conexion.prepareStatement(consultaItems)) {
                        psItems.setInt(1, pedidoId);
                        try (ResultSet rsItems = psItems.executeQuery()) {
                            while (rsItems.next()) {
                                String nombre = rsItems.getString("nombre");
                                String descripcion = rsItems.getString("descripcion");
                                float precio = rsItems.getFloat("precio");
                                float costo = rsItems.getFloat("costo");
                                boolean elaboracion = rsItems.getBoolean("elaboracion");

                                Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
                                int cantidad = rsItems.getInt("cantidad");
                                float precioTotal = rsItems.getFloat("precio_total");

                                Item item = new Item(producto, cantidad, precioTotal);
                                item.setPrecio(precioTotal);
                                items.add(item);
                            }
                        }
                    }

                    pedido = new Pedido(fechaHoraApertura, items);
                    pedido.setId(pedidoId);
                }
            }
        }

        return pedido;
    }
    
    public void eliminarPedidoDeMesa(Mesa mesa, Pedido pedido) throws SQLException {
        String consulta = "DELETE FROM mesa_pedido WHERE id_mesa = ? AND id_pedido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, mesa.getId());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
        }
    }
}
