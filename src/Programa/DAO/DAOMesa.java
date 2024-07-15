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
    private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306"; // Puerto predeterminado de MySQL
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";
    private static final String BASE_DE_DATOS = "titorestobar";
    private static final String CADENA_CONEXION = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BASE_DE_DATOS;

    private Connection conexion;

    public DAOMesa() {
        try {
            this.conexion = Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection Conectar() throws SQLException {
        return DriverManager.getConnection(CADENA_CONEXION, USUARIO, PASSWORD);
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
