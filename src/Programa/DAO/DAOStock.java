package Programa.DAO;

import Programa.Stock;
import Programa.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOStock {
    private Connection conexion;

    public DAOStock() {
        try {
            conexion = Conexion.Conectar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void aumentarStock(int productoId, int cantidad) throws SQLException {
        String consulta = "UPDATE stock SET cantidad = cantidad + ? WHERE producto_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, productoId);
            ps.executeUpdate();
        }
    }

    public void disminuirStock(int productoId, int cantidad) throws SQLException {
        String consulta = "UPDATE stock SET cantidad = cantidad - ? WHERE producto_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, productoId);
            ps.executeUpdate();
        }
    }

    public void actualizarStock(int productoId, int nuevaCantidad) throws SQLException {
        String consulta = "UPDATE stock SET cantidad = ? WHERE producto_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, productoId);
            ps.executeUpdate();
        }
    }

    public void eliminarStock(int productoId) throws SQLException {
        String consulta = "DELETE FROM stock WHERE producto_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setInt(1, productoId);
            ps.executeUpdate();
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

