package Programa.DAO;

import Programa.Model.Conexion;
import Programa.Model.Producto;
import Programa.Model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOStock {

    public Stock obtenerStockPorProducto(int idProducto) {
        Stock stock = null;
        String sql = "SELECT * FROM stock WHERE id_producto = ?";

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stock = new Stock(
                        rs.getInt("id"),
                        rs.getInt("cantidad"),
                        new Producto(idProducto)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return stock;
    }

    public void actualizarStock(Stock stock) {
        String sql = "UPDATE stock SET cantidad = ? WHERE id_producto = ?";

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stock.getCantidad());
            ps.setInt(2, stock.getProducto().getId());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No se actualizó ningún registro de stock.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }


   public void insertarStock(Stock stock) {
        String sql = "INSERT INTO stock (cantidad, id_producto) VALUES (?, ?)";

        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stock.getCantidad());
            ps.setInt(2, stock.getProducto().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }


    // Elimina el stock de un producto
    public void eliminarStock(int idProducto) {
        String sql = "DELETE FROM stock WHERE id_producto = ?";
        
        try (Connection con = Conexion.Conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
