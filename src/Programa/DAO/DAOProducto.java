package Programa.DAO;

import Programa.Conexion;
import Programa.Producto;
import Programa.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProducto {
    private Connection conexion;

    public DAOProducto() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> listadoDeProductos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String consulta = "SELECT * FROM productos";
        try (PreparedStatement comando = conexion.prepareStatement(consulta);
             ResultSet lectura = comando.executeQuery()) {
            while (lectura.next()) {
                int id = lectura.getInt("id");
                String nombre = lectura.getString("nombre");
                String descripcion = lectura.getString("descripcion");
                float costo = lectura.getFloat("costo");
                float precio = lectura.getFloat("precio");
                boolean elaboracion = lectura.getBoolean("elaborado");

                Producto producto = new Producto(nombre, descripcion, precio, costo, elaboracion);
                producto.setId(id);
                lista.add(producto);
            }
        }
        return lista;
    }

    public void Guardar(Producto producto) throws SQLException {
        String consulta = "INSERT INTO productos (nombre, descripcion, precio, costo, elaborado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, producto.getNombre());
            comando.setString(2, producto.getDescripcion());
            comando.setFloat(3, producto.getPrecio());
            comando.setFloat(4, producto.getCosto());
            comando.setBoolean(5, producto.isElaboracion());
            comando.executeUpdate();

            try (ResultSet generatedKeys = comando.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void UpDate(Producto producto) throws SQLException {
        String consulta = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, costo = ?, elaborado = ? WHERE id = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, producto.getNombre());
            comando.setString(2, producto.getDescripcion());
            comando.setFloat(3, producto.getPrecio());
            comando.setFloat(4, producto.getCosto());
            comando.setBoolean(5, producto.isElaboracion());
            comando.setInt(6, producto.getId());
            comando.executeUpdate();
        }
    }

    public void eliminarStockPorIdProducto(int idProducto) throws SQLException {
        String consulta = "DELETE FROM stock WHERE id_producto = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, idProducto);
            comando.executeUpdate();
        }
    }

    public boolean EliminarProductoPorNombre(String nombre) throws SQLException {
        String consulta = "SELECT id FROM productos WHERE nombre = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, nombre);
            ResultSet rs = comando.executeQuery();
            if (rs.next()) {
                int idProducto = rs.getInt("id");

                // Eliminar stock asociado al producto
                eliminarStockPorIdProducto(idProducto);

                // Eliminar el producto
                String deleteProducto = "DELETE FROM productos WHERE id = ?";
                try (PreparedStatement deleteCommand = conexion.prepareStatement(deleteProducto)) {
                    deleteCommand.setInt(1, idProducto);
                    int filasAfectadas = deleteCommand.executeUpdate();
                    return filasAfectadas > 0;
                }
            } else {
                return false;
            }
        }
    }

    public void Ver(Producto producto) throws SQLException {
        String consulta = "SELECT * FROM productos WHERE nombre = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, producto.getNombre());
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    producto.setId(resultado.getInt("id"));
                    producto.setDescripcion(resultado.getString("descripcion"));
                    producto.setCosto(resultado.getFloat("costo"));
                    producto.setPrecio(resultado.getFloat("precio"));
                    producto.setElaboracion(resultado.getBoolean("elaborado"));
                }
            }
        }
    }

    public void guardarStock(Stock stock) throws SQLException {
        String consulta = "INSERT INTO stock (cantidad, id_producto) VALUES (?, ?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, stock.getCantidad());
            comando.setInt(2, stock.getProducto().getId());
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
}