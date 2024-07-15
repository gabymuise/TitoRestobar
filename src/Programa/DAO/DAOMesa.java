package Programa.DAO;

import Programa.Mesa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private Connection Conectar() throws SQLException {
        String servidor = "localhost";
        String puerto = "3306"; // Puerto predeterminado de MySQL
        String usuario = "root";
        String password = "ViYu21040407";
        String baseDeDatos = "titorestobar";

        String cadenaConexion = "jdbc:mysql://" + servidor + ":" + puerto + "/" + baseDeDatos;
        return DriverManager.getConnection(cadenaConexion, usuario, password);
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
}
