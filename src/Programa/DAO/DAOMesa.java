package Programa.DAO;

import Programa.Mesa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMesa {
    private Connection conexion;

    public DAOMesa() {
        try {
            conexion = Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection Conectar() throws SQLException {
        String servidor = "localhost";
        String usuario = "root";
        String password = "";
        String baseDeDatos = "titorestobar";

        String cadenaConexion = "jdbc:mysql://" + servidor + "/" + baseDeDatos;
        Connection conexionDb = DriverManager.getConnection(cadenaConexion, usuario, password);

        return conexionDb;
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
        String consulta = "INSERT INTO `mesas` (nombre) VALUES (?)";
        Connection conexion = Conectar();
        PreparedStatement comando = conexion.prepareStatement(consulta);
        comando.setString(1, mesa.getNombre());
        comando.executeUpdate(); // Ejecuta la consulta
        conexion.close(); // No olvides cerrar la conexión
    }


    public void eliminarMesa(String nombre) throws SQLException {
        Connection conexion = Conectar();
        String consulta = "DELETE FROM `mesas` WHERE `nombre` = ?";
        PreparedStatement comando = conexion.prepareStatement(consulta);
        comando.setString(1, nombre);
        comando.executeUpdate(); // Ejecuta la consulta
        conexion.close(); // No olvides cerrar la conexión
    }


    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
