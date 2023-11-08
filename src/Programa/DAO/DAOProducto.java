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
    public Connection Conectar() throws SQLException {
        String servidor = "localhost";
        String usuario = "root";
        String password = "";
        String baseDeDatos = "titorestobar";

        String cadenaConexion = "jdbc:mysql://" + servidor + "/" + baseDeDatos;
        Connection conexionDb = DriverManager.getConnection(cadenaConexion, usuario, password);

        return conexionDb;
    }
   public List<Producto> listadoDeProductos() throws SQLException {
    List<Producto> lista = new ArrayList<>();

    String consulta = "SELECT * FROM `producto`";
    Connection conexion = Conectar();
    PreparedStatement comando = conexion.prepareStatement(consulta);
    ResultSet lectura = comando.executeQuery();

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

    conexion.close();
    return lista;
}


    public void Guardar(Producto producto) throws SQLException {
        String consulta = "INSERT INTO `producto` (`id`, `nombre`, `descripcion`, `precio`, `costo`, `elaborado`) VALUES (NULL, ?, ?, ?, ?, ?)";
        Connection conexion = Conectar();
        PreparedStatement comando = conexion.prepareStatement(consulta);
        comando.setString(1, producto.getNombre());
        comando.setString(2, producto.getDescripcion());
        comando.setFloat(3, producto.getCosto());
        comando.setFloat(4, producto.getPrecio());
        comando.setBoolean(5, producto.isElaboracion());
        comando.executeUpdate();

        conexion.close();
    }

    public void UpDate(Producto producto) throws SQLException {
        String consulta = "UPDATE `producto` SET `nombre` = ?, `descripcion` = ?, `precio` = ?, `costo` = ?,`stock` = ? WHERE `productos`.`idProducto` = ?";
        Connection conexion = Conectar();
        PreparedStatement comando = conexion.prepareStatement(consulta);
        comando.setString(1, producto.getNombre());
        comando.setString(2, producto.getDescripcion());
        comando.setFloat(3, producto.getPrecio());
        comando.setFloat(4, producto.getCosto());
        comando.setBoolean(5, producto.isElaboracion());
        comando.setInt(6, producto.getId());
        comando.executeUpdate();

        conexion.close();
    }

    
    public boolean EliminarProductoPorNombre(String nombre) throws SQLException {
       // Realiza la eliminación directamente sin crear una nueva instancia de DAOProducto
    Connection conexion = Conectar();
    String consulta = "DELETE FROM `producto` WHERE `nombre` = ?";
    PreparedStatement comando = conexion.prepareStatement(consulta);
    comando.setString(1, nombre);
    int filasAfectadas = comando.executeUpdate();
    
    conexion.close();
    
    // Devuelve true si se eliminó al menos una fila, de lo contrario, devuelve false
    return filasAfectadas > 0;
    }
    
   public void Ver(Producto producto) throws SQLException {
    String consulta = "SELECT * FROM `producto` WHERE `nombre` = ?";
    Connection conexion = Conectar();
    PreparedStatement comando = conexion.prepareStatement(consulta);
    comando.setString(1, producto.getNombre());
    
    ResultSet resultado = comando.executeQuery();
    
    // Aquí debes procesar el resultado si es necesario
    if (resultado.next()) {
        producto.setId(resultado.getInt("id"));
        producto.setDescripcion(resultado.getString("descripcion"));
        producto.setCosto(resultado.getFloat("costo"));
        producto.setPrecio(resultado.getFloat("precio"));
        producto.setElaboracion(resultado.getBoolean("elaborado"));
    }
    
    conexion.close();
}
}

