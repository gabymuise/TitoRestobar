package Programa.DAO;

import Programa.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProducto {
    private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306"; // Puerto predeterminado de MySQL
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";
    private static final String BASE_DE_DATOS = "titorestobar";
    private static final String CADENA_CONEXION = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BASE_DE_DATOS;

    private Connection Conectar() throws SQLException {
        return DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
    }

    public List<Producto> listadoDeProductos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String consulta = "SELECT * FROM productos";

        try (Connection conexion = Conectar();
             PreparedStatement comando = conexion.prepareStatement(consulta);
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
        try (Connection conexion = Conectar();
             PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, producto.getNombre());
            comando.setString(2, producto.getDescripcion());
            comando.setFloat(3, producto.getPrecio());
            comando.setFloat(4, producto.getCosto());
            comando.setBoolean(5, producto.isElaboracion());
            comando.executeUpdate();
        }
    }

    public void Actualizar(Producto producto) throws SQLException {
        String consulta = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, costo = ?, elaborado = ? WHERE id = ?";
        try (Connection conexion = Conectar();
             PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, producto.getNombre());
            comando.setString(2, producto.getDescripcion());
            comando.setFloat(3, producto.getPrecio());
            comando.setFloat(4, producto.getCosto());
            comando.setBoolean(5, producto.isElaboracion());
            comando.setInt(6, producto.getId());
            comando.executeUpdate();
        }
    }

    public boolean EliminarProductoPorNombre(String nombre) throws SQLException {
        String consulta = "DELETE FROM productos WHERE nombre = ?";
        try (Connection conexion = Conectar();
             PreparedStatement comando = conexion.prepareStatement(consulta)) {
            comando.setString(1, nombre);
            int filasAfectadas = comando.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public void Ver(Producto producto) throws SQLException {
        String consulta = "SELECT * FROM productos WHERE nombre = ?";
        try (Connection conexion = Conectar();
             PreparedStatement comando = conexion.prepareStatement(consulta)) {
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
}
