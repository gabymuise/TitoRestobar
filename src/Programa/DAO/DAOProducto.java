package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Producto;
import Programa.Model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProducto {
    private Connection conexion;

    // Constructor que establece la conexión con la base de datos
    public DAOProducto() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lista todos los productos
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
                boolean elaborado = lectura.getBoolean("elaborado");

                Producto producto = new Producto(nombre, descripcion, precio, costo, elaborado);
                producto.setId(id);
                lista.add(producto);
            }
        }
        return lista;
    }

    // Guarda un nuevo producto en la base de datos
    public void guardar(Producto producto) throws SQLException {
        String consulta = "INSERT INTO productos (nombre, descripcion, precio, costo, elaborado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS)) {
            comando.setString(1, producto.getNombre());
            comando.setString(2, producto.getDescripcion());
            comando.setDouble(3, producto.getPrecio());
            comando.setDouble(4, producto.getCosto());
            comando.setBoolean(5, producto.isElaboracion());
            comando.executeUpdate();

            try (ResultSet generatedKeys = comando.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // Actualiza la información de un producto existente
    public void actualizar(Producto producto) throws SQLException {
        String consulta = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, costo = ?, elaborado = ? WHERE id = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, producto.getNombre());
            comando.setString(2, producto.getDescripcion());
            comando.setDouble(3, producto.getPrecio());
            comando.setDouble(4, producto.getCosto());
            comando.setBoolean(5, producto.isElaboracion());
            comando.setInt(6, producto.getId());
            comando.executeUpdate();
        }
    }

    // Elimina el stock asociado a un producto por su ID
    private void eliminarStockPorIdProducto(int idProducto) throws SQLException {
        String consulta = "DELETE FROM stock WHERE id_producto = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, idProducto);
            comando.executeUpdate();
        }
    }

    // Elimina un producto por su nombre, eliminando también su stock
    public boolean eliminarProductoPorNombre(String nombre) throws SQLException {
        String consulta = "SELECT id FROM productos WHERE nombre = ?";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, nombre);
            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    int idProducto = rs.getInt("id");

                    eliminarStockPorIdProducto(idProducto);

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
    }

    // Obtiene un producto por su nombre
    public void obtener(Producto producto) throws SQLException {
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

    // Guarda la información de stock en la base de datos
    public void guardarStock(Stock stock) throws SQLException {
        String consulta = "INSERT INTO stock (cantidad, id_producto) VALUES (?, ?)";
        try (PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setInt(1, stock.getCantidad());
            comando.setInt(2, stock.getProducto().getId());
            comando.executeUpdate();
        }
    }

    // Obtiene un producto por su ID
    public Producto ver(int id) throws SQLException {
        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto(rs.getInt("id"),
                                            rs.getString("nombre"),
                                            rs.getString("descripcion"),
                                            rs.getFloat("precio"),
                                            rs.getFloat("costo"),
                                            rs.getBoolean("elaborado"));
                }
            }
        }
        return producto;
    }

    // Cierra la conexión con la base de datos
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public boolean existeProducto(String nombre) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM productos WHERE nombre = ?";
        try (Connection conn = Conexion.Conectar();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
