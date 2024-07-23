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
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
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
    
     public boolean actualizarProducto(Producto producto) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Conexion.Conectar();
            String sql = "UPDATE productos SET Nombre = ?, Descripcion = ?, Precio = ?, Costo = ?, Elaborado = ? WHERE Id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setFloat(3, producto.getPrecio());
            stmt.setFloat(4, producto.getCosto());
            stmt.setBoolean(5, producto.isElaboracion());
            stmt.setInt(6, producto.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    // Actualiza la información de un producto existente
    public void actualizar(Producto producto) throws SQLException {
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

    // Elimina el stock asociado a un producto por su ID
    public void eliminarStockPorIdProducto(int idProducto) throws SQLException {
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
    public void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Considerar una mejor gestión de errores aquí
        }
    }
}
