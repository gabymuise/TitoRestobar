package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Mesa;
import Programa.Model.Pedido;
import java.sql.Timestamp;
import Programa.Model.Producto;

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
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
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

                Mesa mesa = new Mesa(id, nombre);
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

    public void eliminarMesa(int id) throws SQLException {
        if (tienePedidosActivos(id)) {
            throw new SQLException("No se puede eliminar la mesa porque tiene pedidos activos.");
        }
        String consulta = "DELETE FROM mesas WHERE id = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, id);
            int filasAfectadas = comando.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("La mesa no se pudo eliminar porque no existe.");
            }
        }
    }
    
    public void modificarMesa(int id, String nuevoNombre) throws SQLException {
        String consulta = "UPDATE mesas SET nombre = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    
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

    public Mesa obtenerMesaPorId(int id) throws SQLException {
        Mesa mesa = null;
        String query = "SELECT * FROM mesas WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    mesa = new Mesa(id, nombre);
                }
            }
        }
        return mesa;
    }
    
    public Pedido obtenerPedidoActivo(int idMesa) throws SQLException {
        Pedido pedido = null;
        String query = "SELECT * FROM pedidos WHERE idMesa = ? AND fechaHoraCierre IS NULL";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idMesa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idPedido = rs.getInt("id");
                    Timestamp fechaHoraApertura = rs.getTimestamp("fechaHoraApertura");
                    // Asegúrate de que los constructores se ajusten a estos parámetros
                    pedido = new Pedido(idPedido, obtenerMesaPorId(idMesa), fechaHoraApertura, null, null, null);
                }
            }
        }
        return pedido;
    }

    public void eliminarPedidoDeMesa(int idMesa) throws SQLException {
        Pedido pedidoActivo = obtenerPedidoActivo(idMesa);
        if (pedidoActivo != null) {
            DAOPedido daoPedido = new DAOPedido();
            daoPedido.eliminarPedido(pedidoActivo);
        } else {
            throw new SQLException("No hay pedido activo para eliminar en la mesa con ID: " + idMesa);
        }
    }

    
    public boolean tienePedidosActivos(int id) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM pedidos WHERE idMesa = ? AND fechaHoraCierre IS NULL";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, id);
            try (ResultSet lectura = comando.executeQuery()) {
                if (lectura.next()) {
                    return lectura.getInt(1) > 0;
                }
            }
        }
        return false;
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
